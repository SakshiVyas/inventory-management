# Inventory Management API

A Spring Boot REST API for managing products, suppliers, stock operations, transaction history, and CSV imports.

## Requirements

- Java 21
- MySQL

Create the database before starting the application:

```sql
CREATE DATABASE inventory_db;
```

## Run from source

Set the database credentials and start the application:

```bash
export DB_USERNAME=your_mysql_user
export DB_PASSWORD=your_mysql_password
./mvnw spring-boot:run
```

## Build and run the JAR

The packaged JAR is also available from the [0.0.1 GitHub release](https://github.com/SakshiVyas/inventory-management/releases/tag/0.0.1).

```bash
./mvnw clean package -DskipTests

DB_USERNAME=your_mysql_user \
DB_PASSWORD=your_mysql_password \
java -jar target/inventory-management-0.0.1-SNAPSHOT.jar
```

The server listens on port `8080` by default.

## Swagger and OpenAPI

- Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- Root URL: [http://localhost:8080/](http://localhost:8080/) redirects to Swagger UI
- OpenAPI JSON: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

## API routes

### Products

- `GET /api/products`
- `POST /api/products`
- `GET /api/products/{id}`
- `PUT /api/products/{id}`
- `DELETE /api/products/{id}`
- `GET /api/products/low-stock`
- `GET /api/products/{id}/availability`
- `GET /api/products/{id}/transactions`
- `POST /api/products/{id}/stock-in`
- `POST /api/products/{id}/stock-out`

### Suppliers

- `GET /api/suppliers`
- `POST /api/suppliers`
- `GET /api/suppliers/{id}`
- `PUT /api/suppliers/{id}`
- `DELETE /api/suppliers/{id}`

### Import

- `POST /api/import/products` imports products from `src/main/resources/data/Products.csv`.

## Configuration

The application uses these environment variables:

| Variable | Description |
| --- | --- |
| `DB_USERNAME` | MySQL username |
| `DB_PASSWORD` | MySQL password |

Database URL: `jdbc:mysql://localhost:3306/inventory_db`
