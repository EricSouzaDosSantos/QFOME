# QFome Backend (setup de infra)

Projeto backend inicial do QFome com Spring Boot + Maven.

## Stack
- Java 21
- Spring Boot 3.5
- Spring Web
- Spring Actuator
- Spring Data JPA
- Flyway
- H2 (dev)
- PostgreSQL (prod)

## Rodar local (dev)
```bash
./mvnw spring-boot:run
```

No Windows:
```bash
mvnw.cmd spring-boot:run
```

## Healthcheck
```txt
GET http://localhost:8080/actuator/health
```

## Banco Postgres (opcional no MVP)
Subir banco:
```bash
docker compose up -d
```

Rodar backend em prod:
```bash
mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=prod
```

## Arquivos de configuracao
- `src/main/resources/application.yml`: config base
- `src/main/resources/application-dev.yml`: profile dev com H2
- `src/main/resources/application-prod.yml`: profile prod com Postgres
- `.env.example`: variaveis de ambiente
