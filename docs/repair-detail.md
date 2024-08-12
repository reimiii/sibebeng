## Repair Detail API Spec

### Base URL

`http://localhost:8181/api/customers/{customerId}/vehicles/{vehicleId}/repairs/{repairId}`

## Endpoints

### Create Repair Detail

Request :

- Method : POST
- Endpoint : `/details`
- Header :
    - Content-Type: application/json
    - Accept: application/json

Body :

```json 
{
  "issueDescription": "string",
  "repairAction": "string",
  "status": "string",
  "price": 11222
}
```

Response - 201 Created :

```json 
{
  "message": "repair detail added successfully",
  "data": {
    "id": "uuid",
    "issueDescription": "string",
    "repairAction": "string",
    "status": "string",
    "price": 11222
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
    "repairAction": "required"
  }
}
```

---

### Update Repair Detail

Request :

- Method : PATCH
- Endpoint : `/details/{repairDetailId}`
- Header :
    - Content-Type: application/json
    - Accept: application/json

Body :

```json 
{
  "issueDescription": "string",
  "repairAction": "string",
  "status": "string",
  "price": 11222
}
```

Response - 200 OK :

```json 
{
  "message": "repair detail updated successfully",
  "data": {
    "id": "uuid",
    "issueDescription": "string",
    "repairAction": "string",
    "status": "string",
    "price": 11222
  },
  "errors": null
}
```

Response - 404 Not Found :

```json 
{
  "message": "repair detail not found",
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
    "price": "not valid price"
  }
}
```

---

### Delete Repair Detail

Request :

- Method : DELETE
- Endpoint : `/details/{repairDetailId}`
- Header :
    - Accept: application/json

Response - 200 OK :

```json
{
  "message": "repair detail deleted successfully",
  "data": "OK",
  "errors": null
}
```

Response - 404 Not Found :

```json 
{
  "message": "repair detail not found",
  "data": null,
  "errors": "404 NOT FOUND"
}
```