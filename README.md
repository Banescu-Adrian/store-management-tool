# Store management tool

### Requirments

1. Java 17
2. MariaDB 11.1.2

### Setup

1. Copy .env.example.properties and rename to env.properties
2. In env.properties fill DB_DATABASE, DB_USER, DB_PASSWORD with your values.


### After starting the application

1. Application is running on port 8000.
2. Swagger endpoint can be found [here](http://localhost:8000/swagger-ui/index.html#/);
3. An admin user with credentials admin@store.com, !@#Password is created by default.

### Features

Application has the following functionalities:
1. Adding new users.
2. View products
3. View product
4. Create product
5. Update product
6. Delete product
7. Add stock for product
8. Subtract from product stock
9. View all stock paginated
10. View all stock by product paginated


