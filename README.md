# Cryptocurrency watcher


###### This is an application for monitoring and alerting cryptocurrency changes.

-------------------------------------------------------------
- Stack: [JDK 17](http://jdk.java.net/17/), Spring Boot 2.6.3, Lombok, H2, Caffeine Cache, Swagger/OpenAPI 3.0
- Run: `mvn spring-boot:run` in root directory.
-----------------------------------------------------
[REST API documentation](http://localhost:8080/swagger-ui.html) 
## Credentials
    | Username                  |  Password |    Role    |
    |---------------------------|-----------|------------|
    | user@gmail.com            |   user    |    USER    |
    | admin@gmail.com           |   admin   | ADMIN/USER |
## Source of information about cryptocurrency rates:

[Crypto API | CoinLore](https://www.coinlore.com/cryptocurrency-data-api#3)

[Method Ticker (Specific Coin)](https://api.coinlore.net/api/ticker/?id=90)