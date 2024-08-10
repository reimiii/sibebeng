## Vehicle API Spec

### Base URL

`http://localhost:8181/api/customers/{customerId}/`

## Endpoints

### Create Vehicle

Request :

- Method : POST
- Endpoint : `/vehicles`
- Header :
    - Content-Type: application/json
    - Accept: application/json

Body :

```json 
{
  "licensePlate": "AB123CD",
  "brand": "Toyota",
  "model": "Camry",
  "year": 2018,
  "color": "Blue"
}
```

Response - 201 Created :

```json 
{
  "message": "vehicle added successfully",
  "data": {
    "id": "uuid",
    "licensePlate": "AB123CD",
    "brand": "Toyota",
    "model": "Camry",
    "year": 2018,
    "color": "Blue"
  },
  "errors": null
}
```

Response - 400 Bad Request :

```json 
{
  "message": "validation errors",
  "data": null,
  "errors": {
    "brand": "brand is required",
    "model": "model is required"
  }
}
```

---

### Get Vehicle

Request :

- Method : GET
- Endpoint : `/vehicles/{vehicleId}`
- Header :
    - Accept: application/json

Response - 200 OK :

```json 
{
  "message": "vehicle retrieved successfully",
  "data": {
    "id": "uuid",
    "licensePlate": "AB123CD",
    "brand": "Toyota",
    "model": "Camry",
    "year": 2018,
    "color": "Blue",
    "repairs": [
      {
        "id": "string",
        "entryDate": "date time or instance",
        "exitDate": "date time or instance",
        "description": "string"
      }
    ]
  },
  "errors": null
}
```

Response - 404 Not Found :

```json 
{
  "message": "vehicle not found",
  "data": null,
  "errors": "404 NOT FOUND"
}
```

---

### Update Vehicle

Request :

- Method : PUT
- Endpoint : `/vehicles/{vehicleId}`
- Header :
    - Content-Type: application/json
    - Accept: application/json

Body :

```json 
{
  "licensePlate": "AB123CD",
  "brand": "Toyota",
  "model": "Camry",
  "year": 2018,
  "color": "Blue"
}
```

Response - 200 OK :

```json 
{
  "message": "vehicle updated successfully",
  "data": {
    "id": "uuid",
    "licensePlate": "MAP UPD",
    "brand": "Toyota",
    "model": "Camry",
    "year": 2018,
    "color": "Blue"
  },
  "errors": null
}
```

Response - 404 Not Found :

```json 
{
  "message": "vehicle not found",
  "data": null,
  "errors": "404 NOT FOUND"
}
```

Response - 400 Bad Request :

```json 
{
  "message": "validation errors",
  "data": null,
  "errors": {
    "brand": "brand is required",
    "model": "model is required"
  }
}
```

---

### Delete Vehicle

Request :

- Method : DELETE
- Endpoint : `/vehicles/{vehicleId}`
- Header :
    - Accept: application/json

Response - 200 OK :

```json
{
  "message": "vehicle deleted successfully",
  "data": "OK",
  "errors": null
}
```

Response - 404 Not Found :

```json 
{
  "message": "vehicle not found",
  "data": null,
  "errors": "404 NOT FOUND"
}
```

Response - 409 Conflict :

```json 
{
  "message": "vehicle still has repairs",
  "data": null,
  "errors": "409 CONFLICT"
}
```
