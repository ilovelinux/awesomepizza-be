# Awesome Pizza BE

Reactive RESTful Web Service made with Spring WebFlux & R2DBC.

Used CockroachDB for scalabilify.

Implemented:
||Data|API CUSTOMER|API SHOP|
|-|-|-|-|
|Customer|X|X|
|Address|X|X|
|Products|X|X|
|Orders|X|X|X|
|Shop|X|X|X|

## OpenAPI

OpenAPI Swagger is at `http://localhost:8080/`

## Data

Data about the Shop, Shop opening hours, one customer (ID: 1), one address (ID: 1) and one product (ID: 1) are inserted during database initialization.

## Tests

Tests coverage is ~52%. A coverage report is generated by JaCoCo under `build/reports/jacoco/test/html/index.html` after running the tests.

---
Awesome Pizza BE © 2024 by Antonio Spadaro is licensed under CC BY-NC-ND 4.0
