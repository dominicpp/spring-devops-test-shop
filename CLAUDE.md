# Spring DevOps Test Shop

Spring Boot Shop-Anwendung (Lern-/Portfolio-Projekt) mit Fokus auf DevOps-Praktiken.
Package: `com.dominic.shop` | Build: Gradle | Java 21

## Commands

```bash
./gradlew build                # Build + Tests
./gradlew test                 # Nur Tests
./gradlew bootRun              # Dev-Server starten
docker compose up -d           # PostgreSQL + App lokal starten
docker build -t shop .         # Docker Image bauen
```

## Projektstruktur

```
com.dominic.shop
├── config/          # Spring-Konfiguration, Beans, Security
├── controller/      # REST Controller – NUR DTOs rein/raus
├── dto/             # Request/Response DTOs
├── entity/          # JPA Entities
├── repository/      # Spring Data JPA Repositories
└── service/         # Business-Logik – Controller → Service → Repository
```

## Architektur-Regeln

- **Schichtenarchitektur einhalten**: Controller ruft IMMER Service auf, NIEMALS direkt Repository
- Controller empfangen und liefern DTOs, keine Entities
- Mapping zwischen DTO ↔ Entity gehört in den Service
- Neue Endpoints folgen dem REST-Standard: GET, POST, PUT, DELETE auf Ressource-Pfaden
- Jede Entity bekommt ein eigenes Repository-Interface

## Datenbank

- PostgreSQL als Produktiv-DB
- Flyway für Schema-Migrations unter `src/main/resources/db/migration`
- Naming: `V1__create_product_table.sql`, `V2__add_category.sql` usw.
- H2 für Tests (falls kein Testcontainers)

## DevOps / Deployment

- Docker + Docker Compose für lokale Entwicklung
- Jenkins als CI/CD Pipeline
- Ziel-Plattform: OpenShift
- Dockerfile im Projekt-Root, Multi-Stage-Build bevorzugt

## Testing

- JUnit 5 + Mockito für Unit Tests
- `@SpringBootTest` + Testcontainers für Integrationstests
- Testmethoden-Naming: `should_ergebnisErwarten_when_bedingung()`

## Konventionen

- Commit-Messages auf Englisch: `feat(product): add category field`
- Branches: `feature/kurze-beschreibung`, `fix/kurze-beschreibung`
- Keine Wildcard-Imports
- Constructor Injection, kein `@Autowired` auf Feldern
