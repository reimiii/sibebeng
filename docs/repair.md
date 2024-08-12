## Repair API Spec

### Base URL

`http://localhost:8181/api/customers/{customerId}/vehicles/{vehicleId}`

## Endpoints

### Create Repair

Request :

- Method : POST
- Endpoint : `/repairs`
- Header :
    - Content-Type: application/json
    - Accept: application/json

Body :

```json 
{
  "entryDate": "datetime",
  "exitDate": "datetime",
  "description": "string"
}
```

Response - 201 Created :

```json 
{
  "message": "repair added successfully",
  "data": {
    "id": "uuid",
    "entryDate": "datetime",
    "exitDate": "datetime",
    "description": "string"
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
    "description": "required"
  }
}
```

---

### Get Repair

Request :

- Method : GET
- Endpoint : `/repairs/{repairId}`
- Header :
    - Accept: application/json

Response - 200 OK :

```json 
{
  "message": "repair retrieved successfully",
  "data": {
    "id": "uuid",
    "entryDate": "datetime",
    "exitDate": "datetime",
    "description": "string",
    "repairDetails": [
      {
        "id": "uuid",
        "issueDescription": "string",
        "repairAction": "string",
        "status": "string",
        "price": 11022
      }
    ]
  },
  "errors": null
}
```

Response - 404 Not Found :

```json 
{
  "message": "repair not found",
  "data": null,
  "errors": "404 NOT FOUND"
}
```

---

### Update Repair

Request :

- Method : PATCH
- Endpoint : `/repairs/{repairId}`
- Header :
    - Content-Type: application/json
    - Accept: application/json

Body :

```json 
{
  "entryDate": "datetime",
  "exitDate": "datetime",
  "description": "string"
}
```

Response - 200 OK :

```json 
{
  "message": "repair updated successfully",
  "data": {
    "id": "uuid",
    "entryDate": "datetime",
    "exitDate": "datetime",
    "description": "string"
  },
  "errors": null
}
```

Response - 404 Not Found :

```json 
{
  "message": "repair not found",
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
    "exitDate": "not valid time"
  }
}
```

---

### Delete Repair

Request :

- Method : DELETE
- Endpoint : `/repairs/{repairId}`
- Header :
    - Accept: application/json

Response - 200 OK :

```json
{
  "message": "repair deleted successfully",
  "data": "OK",
  "errors": null
}
```

Response - 404 Not Found :

```json 
{
  "message": "repair not found",
  "data": null,
  "errors": "404 NOT FOUND"
}
```

Response - 409 Conflict :

```json 
{
  "message": "cannot delete repair, as it is still linked to existing repair details.",
  "data": null,
  "errors": "409 CONFLICT"
}
```
