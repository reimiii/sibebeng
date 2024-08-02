### API Specification

#### Base URL

`http://localhost:8181/api/customers/{customerId}/`

### Endpoints

#### 1. Add Vehicle

- **Endpoint**: `/vehicles`
- **Method**: `POST`
- **Description**: Menambahkan vehicle ke customer terkait
- **Request Body**:
  - **Content-Type**: `application/json`
- **Schema**:

```json
{
  "licensePlate": "B 1234 PP",
  "model": "Camry",
  "brand": "Toyota",
  "year": "2002",
  "color": "Red Blue"
}
```

- **Responses**:
- **201 Created**: jika success
  - **Content-Type**: `application/json`
- **Body**:

```json
{
  "message": "vehicle added successfully"
}
```

- **400 Bad Request**: Jika ada kesalahan pada data yang dikirim.
  - **Content-Type**: `application/json`
- **Body**:

```json
{
  "message": "validation error",
  "details": [
    "year only take 4 char",
    "license plate is required"
  ]
}
```

