## Customer API Spec

### Base URL

`http://localhost:8181/api`

## Endpoints

### Create Customer

Request :

- Method : POST
- Endpoint : `/customers`
- Header :
    - Content-Type: application/json
    - Accept: application/json

Body :

```json 
{
  "name": "John Doe",
  "address": "123 Main St",
  "phoneNumber": "555-1234",
  "email": "john.doe@example.com"
}
```

Response - 201 Created :

```json 
{
  "message": "customer added successfully",
  "data": {
    "id": "string, unique",
    "name": "string",
    "address": "string",
    "phoneNumber": "string",
    "email": "email",
    "vehicles": []
  },
  "errors": null
}
```

Response - 400 Bad Request :

```json 
{
  "message": "validation errors",
  "data": null,
  "errors": [
    "name required",
    "address required"
  ]
}
```

---

### Get Customer

Request :

- Method : GET
- Endpoint : `/customers/{customerId}`
- Header :
    - Accept: application/json

Response - 200 OK :

```json 
{
  "message": "customer retrieved successfully",
  "data": {
    "id": "string, unique",
    "name": "string",
    "address": "string",
    "phoneNumber": "string",
    "email": "email",
    "vehicles": [
      {
        "id": 1,
        "licensePlate": "AB123CD",
        "brand": "Toyota",
        "model": "Camry",
        "year": 2018,
        "color": "Blue"
      }
    ]
  },
  "errors": null
}
```

Response - 404 Not Found :

```json 
{
  "message": "customer not found",
  "data": null,
  "errors": "404 NOT FOUND"
}
```

---

### Update Customer

Request :

- Method : PUT
- Endpoint : `/customers/{customerId}`
- Header :
    - Content-Type: application/json
    - Accept: application/json

Body :

```json 
{
  "name": "John Doe",
  "address": "123 Main St",
  "phoneNumber": "555-1234",
  "email": "john.doe@example.com"
}
```

Response - 200 OK :

```json 
{
  "message": "customer updated successfully",
  "data": {
    "id": "string, unique",
    "name": "string, update",
    "address": "string",
    "phoneNumber": "string",
    "email": "email",
    "vehicles": []
  },
  "errors": null
}
```

Response - 404 Not Found :

```json 
{
  "message": "customer not found",
  "data": null,
  "errors": "404 NOT FOUND"
}
```

Response - 400 Bad Request :

```json 
{
  "message": "validation errors",
  "data": null,
  "errors": [
    "name required",
    "address required"
  ]
}
```

---

### List Customers with Search

Request :

- Method : GET
- Endpoint : `/customers`
- Header :
    - Accept: application/json
- Query Param - Optional :
    - keyword : string
    - size : number,
    - page : number

Response - 200 OK:

```json
{
  "message": "customers retrieved successfully",
  "data": {
    "content": [
      {
        "id": "03a8e9e7-d00d-49ad-af20-0a8e607ee56a",
        "name": "Test name: 33",
        "email": "main@mail33.com",
        "phoneNumber": "1234567891012",
        "address": "JKT: 33",
        "vehicles": [
          {
            "id": 1,
            "licensePlate": "AB123CD",
            "brand": "Toyota",
            "model": "Camry",
            "year": 2018,
            "color": "Red Blue"
          }
        ]
      },
      {
        "id": "1febd1d4-fb77-452e-82e3-e125e8cc8c01",
        "name": "Test name: 41",
        "email": "main@mail41.com",
        "phoneNumber": "1234567891012",
        "address": "JKT: 41",
        "vehicles": [
          {
            "id": 1,
            "licensePlate": "AB123CD",
            "brand": "Toyota",
            "model": "Camry",
            "year": 2018,
            "color": "Blue"
          }
        ]
      }
    ],
    "currentPage": 0,
    "currentSize": 2,
    "hasNext": true,
    "hasPrevious": false,
    "numberOfElements": 2,
    "totalPages": 26,
    "totalElements": 51
  },
  "errors": null
}
```

---

### Delete Customer

Request :

- Method : DELETE
- Endpoint : `/customers/{customerId}`
- Header :
    - Accept: application/json

Response - 200 OK :

```json
{
  "message": "customer deleted successfully",
  "data": "OK",
  "errors": null
}
```

Response - 404 Not Found :

```json 
{
  "message": "customer not found",
  "data": null,
  "errors": "404 NOT FOUND"
}
```

Response - 409 Conflict :

```json 
{
  "message": "customer still has vehicles",
  "data": null,
  "errors": "409 CONFLICT"
}
```