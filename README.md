# Inventory Management System
## Overview
Licious Inventory Management System which includes functionality for
deducting and adding inventory quantities based on various operations such as order
fulfillment, restocking, and product returns.

## Features
- **Inventory Management**: Add, Deduct, Get inventory and inventory transaction logs.
- **Product Management**: Get single or all the product details.

## Table of Contents

- [Setup](#setup)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
- [Usage](#usage)
- [SQL Scripts](#sql-scripts)
- [API Documentation](#api-documentation)
- [Design Notes](#design-notes)

## Setup:
### Prerequisites
Before you make use of the service, Please ensure the below tools and technologies are installed.
- JAVA 17
- Maven
- PostgresSQL/pgAdmin GUI
- Postman/Insomnia platform

### Installation
1. Clone the repository:

   ```bash
   git clone https://github.com/Shravan107/inventory-management.git

2. Navigate to the project directory:
    ```bash
    cd inventory-management

3. Use pgAdmin GUI or terminal and create new DB connection and update the connection string in resources/application.properties file or store in environment variables.
   
5. Build the project using Maven:
    ```bash
    mvn clean package | mvn clean install
   
6. Run the application:
    ```bash
   java -jar target/inventory-management.jar | mvn spring-boot:run 

The application will be accessible at http://localhost:8080
Spring actuator are in place so you can make use of http://localhost:8080/actuator/health to check the status of application.

## Usage
Use Postman Postman/Insomnia platform and simulate the APIs for deducting and adding inventory quantities based on various operations such as order fulfillment, restocking, and product returns. 

## SQL Scripts
- **CREATE**
  ```bash
  CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(19, 2) NOT NULL,
    max_quantity INT NOT NULL
   );

  CREATE TABLE inventory (
      product_id INT PRIMARY KEY,
      quantity INT NOT NULL,
      version INT DEFAULT 0 NOT NULL,
      CONSTRAINT fk_product FOREIGN KEY(product_id) REFERENCES product(id)
  );

  CREATE TABLE inventory_transaction (
      id SERIAL PRIMARY KEY,
      product_id INT NOT NULL,
      transaction_type VARCHAR(10) NOT NULL,
      quantity INT NOT NULL,
      timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      CONSTRAINT fk_product_transaction FOREIGN KEY(product_id) REFERENCES product(id)
  );

- **INSERT**
    - Create products table
      ```bash
      INSERT INTO products (name, price, max_quantity)
      VALUES
      ('Chicken Breast', 250.00, 100),
      ('Salmon Fillet', 650.00, 50),
      ('Seer Steak', 890.64, 80),
      ('Tuna', 350.99, 60);
    - Create initial entry in inventory table
      ```bash
      INSERT INTO inventory (product_id, quantity, version)
      VALUES
      (1, 50, 0),
      (2, 30, 0),
      (3, 60, 0),
      (4, 40, 0);
    - Create initial entry in inventory_transaction table
      ```bash
      INSERT INTO inventory_transaction (product_id, transaction_type, quantity, timestamp)
      VALUES
      (1, 'ADDITION', 50, CURRENT_TIMESTAMP),  
      (2, 'ADDITION', 30, CURRENT_TIMESTAMP),  
      (3, 'ADDITION', 60, CURRENT_TIMESTAMP),  
      (4, 'ADDITION', 40, CURRENT_TIMESTAMP);

## API Documentation

- #### Add inventory
This API is used to add quantity for a product in inventory when there is restocking or order has been returned.
| Endpoint | Response Format |
| :--- | :--- |
| POST /inventory/add | JSON |

Example Request:
```javascript
curl --location 'http://localhost:8080/inventory/add' \
--data '{
    "productId": 1,
    "quantity": 20
}'
```

ExampleResponse: 
```javascript
{
    "message": "Inventory added successfully."
}
```

- #### Deduct inventory
This API is used to deduct quantity for a product in inventory when there product fulfillment.
| Endpoint | Response Format |
| :--- | :--- |
| POST /inventory/deduct | JSON |

Sample Request:
```javascript
curl --location 'http://localhost:8080/inventory/deduct' \
--data '{
    "productId": 1,
    "quantity": 20
}'
```

Sample Response:
```javascript
{
    "message": "Inventory deducted successfully."
}
```

- #### Get all the products
This API is used to get all the products from the products table.
| Endpoint | Response Format |
| :--- | :--- |
| GET /products/all | JSON |

Sample Request:
```javascript
curl --location 'http://localhost:8080/products/all'
```

Sample Response:
```javascript
[
    {
        "id": 1,
        "name": "Chicken Breast",
        "price": 250.0,
        "maxQuantity": 100
    },
    {
        "id": 2,
        "name": "Salmon Fillet",
        "price": 650.0,
        "maxQuantity": 50
    },
    {
        "id": 3,
        "name": "Seer Steak",
        "price": 890.64,
        "maxQuantity": 80
    },
    {
        "id": 4,
        "name": "Tuna",
        "price": 350.99,
        "maxQuantity": 60
    }
]
```

- #### Get single product
This API is used to deduct quantity for a product in inventory when there product fulfillment.
| Endpoint | Response Format |
| :--- | :--- |
| GET /products/{id} | JSON |

Sample Request:
```javascript
curl --location 'http://localhost:8080/products/1'
```

Sample Response:
```javascript
{
    "id": 1,
    "name": "Chicken Breast",
    "price": 250.0,
    "maxQuantity": 100
}
```
#### Status codes
- `400`: Bad request, Insufficient stock availability & Exceeds maximum allowed inventory.
- `404`: Product not found.
- `409`: Conflict/resource already updated.
- `500`: Other errors.
  
## Design Notes
- Singleton pattern applied at the service layer via Spring's default bean scope, repository pattern for abstracting data access and Transactional pattern to ensure atomic and consistent operations.
- Ensure that only one thread can modify the inventory or process a request at a time and thread saftey using synchronized blocks, But better to avoid synchronized in high-concurrency systems as it reduces throughput and introduces performance bottlenecks.
- Optimistic Locking mechanisim with @Version is used for concurrency management.
- @Transactional annotation was used in service layer for data consistency and integrity.
