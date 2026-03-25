# Employee Performance Tracker API

A production-ready Spring Boot 3.x REST API for tracking employee performance reviews, goals, and cycle analytics.

## Tech Stack

- **Java 21**, **Spring Boot 3.4**, Spring Data JPA
- **PostgreSQL** (primary) / **H2** (fallback — default profile)
- **Flyway** for schema migrations
- **Lombok** + **MapStruct** for boilerplate reduction
- **SpringDoc OpenAPI** (Swagger UI at `/swagger-ui.html`)

## Quick Start

```bash
# Run with H2 (default, zero setup)
./mvnw spring-boot:run

# Run with PostgreSQL
./mvnw spring-boot:run -Dspring-boot.run.profiles=postgres

# Run tests
./mvnw clean test
```

Swagger UI: [http://localhost:8181/swagger-ui.html](http://localhost:8181/swagger-ui.html)

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/employees` | Create an employee |
| GET | `/employees?department={dept}&minRating={x}` | Filter by dept & avg rating |
| GET | `/employees/{id}/reviews` | All reviews with cycle details |
| POST | `/reviews` | Submit a performance review |
| GET | `/cycles/{id}/summary` | Avg rating, top performer, goal counts |
| POST | `/cycles` | Create a new review cycle |

## Schema Design

Four normalized tables with strategic constraints:

- **`employees`** — indexed on `department` and `joining_date`
- **`review_cycles`** — CHECK ensures `end_date >= start_date`
- **`performance_reviews`** — `UNIQUE(employee_id, review_cycle_id)` prevents duplicate reviews; `CHECK(rating BETWEEN 1 AND 5)`
- **`goals`** — `CHECK(status IN ('pending','completed','missed'))`

All foreign keys are indexed for join performance.

---

## System Design Write-Up

### 1. Scaling for 500+ Concurrent Managers

To handle 500+ concurrent managers running reports during performance season:

- **Read Replicas**: Route all `GET` endpoints (reports, summaries) to PostgreSQL read replicas using Spring's `@Transactional(readOnly = true)` with a `RoutingDataSource`. Write operations (`POST`) stay on the primary.
- **Load Balancer**: Deploy multiple application instances behind an ALB/NGINX load balancer with sticky sessions disabled (API is stateless). HikariCP connection pool tuned to `maximumPoolSize = 20` per instance.
- **Connection Pooling**: Use PgBouncer in front of PostgreSQL to multiplex connections and prevent connection exhaustion at scale.

### 2. Optimizing `/cycles/{id}/summary` at 100k+ Reviews

When the summary query slows down with 100k+ reviews:

- **Materialized View**: Create a `cycle_summary_mv` materialized view that pre-aggregates `AVG(rating)`, top performer, and goal counts per cycle. Refresh via a scheduled job (`@Scheduled(cron = "0 0 * * * *")`) or trigger-based refresh on review inserts.
- **Pre-aggregated Table**: Alternatively, maintain a `cycle_summary_cache` table updated by a database trigger or application event listener on review submission. This trades write-time cost for instant read performance.
- **Pagination**: For the raw review list, add cursor-based pagination to limit result sets.

### 3. Caching Strategy

- **Redis Cache for Cycle Metadata**: `ReviewCycle` data rarely changes — cache with `@Cacheable("cycles")` and a 1-hour TTL. Invalidate on cycle creation/update.
- **Summary Endpoint Caching**: Cache `/cycles/{id}/summary` responses in Redis with a 15-minute TTL. Invalidate when a new review is submitted for the cycle (`@CacheEvict`).
- **HTTP Caching**: Add `Cache-Control` headers on GET responses for browser/CDN caching (`max-age=300` for summaries).

These strategies compose well: read replicas handle concurrent read load, materialized views eliminate expensive aggregation, and Redis eliminates database round-trips entirely for hot data.

### 4. Environments and Profiles

The application supports multi-environment configurations using Spring Profiles:
- **`dev`**: Local development with H2 (in-memory), enhanced logging (DEBUG), and formatted SQL.
- **`staging`**: Staging environment with H2 (file-based) and standard logging (INFO).
- **`prod`**: Production-ready configuration for PostgreSQL with batching optimizations and minimal logging (ERROR).

### 5. Application Profiling

A custom `PerformanceAspect` (AOP) is implemented to log the execution time of all service-layer methods. This helps identify slow business logic at runtime. Additionally, **Spring Boot Actuator** is integrated to provide production-grade monitoring via `/actuator` endpoints (health, metrics, etc.).

---

## Project Structure

```
src/main/java/com/hivel/employee_performance_tracker/
├── cycle/               # Cycle management (entity, repo, service, controller)
├── employee/            # Employee management (entity, repo, service, controller)
├── goal/                # Goal management (entity, repo)
├── review/              # Performance Review management (entity, repo, service, controller)
├── exception/           # Global error handling & custom exceptions
└── EmployeePerformanceTrackerApplication.java
```
Each module contains its own `dto`, `entity`, `mapper`, `repository`, and `service` packages where applicable, following a modular-by-feature design.

## Assumptions

- Employee IDs are auto-generated (no external ID system).
- One review per employee per cycle (enforced at DB and API level).
- Goal status is managed separately from the review submission flow.
- H2 runs in PostgreSQL compatibility mode for local development.
