### API Specification

#### Base URL
`http://yourdomain.com/api`

### Endpoints

#### 1. Add Customer and Vehicle

- **Endpoint**: `/customers`
- **Method**: `POST`
- **Description**: Menambahkan pelanggan baru beserta kendaraannya.
- **Request Body**:
    - **Content-Type**: `application/json`
    - **Schema**:

```json
{
    "customer": {
        "name": "John Doe",
        "address": "123 Main St",
        "phoneNumber": "555-1234",
        "email": "john.doe@example.com"
    }
}
```

- **Responses**:
    - **201 Created**: Jika pelanggan.
        - **Content-Type**: `application/json`
        - **Body**:

      ```json
      {
          "message": "Customer added successfully"
      }
      ```
    - **400 Bad Request**: Jika ada kesalahan pada data yang dikirim.
        - **Content-Type**: `application/json`
        - **Body**:

      ```json
      {
          "message": "Validation error",
          "details": ["Customer name is required", "Vehicle license plate is required"]
      }
      ```

#### 2. Get All Customers

- **Endpoint**: `/customers`
- **Method**: `GET`
- **Description**: Mendapatkan daftar semua pelanggan.
- **Responses**:
    - **200 OK**: Jika daftar pelanggan berhasil diambil.
        - **Content-Type**: `application/json`
        - **Body**:

      ```json
      {
          "message": "Customers retrieved successfully",
          "data": [
              {
                  "id": 1,
                  "name": "John Doe",
                  "address": "123 Main St",
                  "phoneNumber": "555-1234",
                  "email": "john.doe@example.com",
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
          "errors": []
      }
      ```

#### 3. Get Customer by ID

- **Endpoint**: `/customers/{id}`
- **Method**: `GET`
- **Description**: Mendapatkan detail pelanggan berdasarkan ID.
- **Path Parameters**:
    - `id`: ID dari pelanggan yang ingin diambil.
- **Responses**:
    - **200 OK**: Jika detail pelanggan berhasil diambil.
        - **Content-Type**: `application/json`
        - **Body**:

      ```json
      {
          "message": "Customer retrieved successfully",
          "data": {
              "id": 1,
              "name": "John Doe",
              "address": "123 Main St",
              "phoneNumber": "555-1234",
              "email": "john.doe@example.com",
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
          "errors": []
      }
      ```
    - **404 Not Found**: Jika pelanggan dengan ID tersebut tidak ditemukan.
        - **Content-Type**: `application/json`
        - **Body**:

      ```json
      {
          "message": "Customer not found",
          "data": null,
          "errors": ["Customer with ID {id} not found"]
      }
      ```

#### 4. Update Customer

- **Endpoint**: `/customers/{id}`
- **Method**: `PUT`
- **Description**: Memperbarui detail pelanggan berdasarkan ID.
- **Path Parameters**:
    - `id`: ID dari pelanggan yang ingin diperbarui.
- **Request Body**:
    - **Content-Type**: `application/json`
    - **Schema**:

```json
{
    "name": "Jane Doe",
    "address": "456 Elm St",
    "phoneNumber": "555-5678",
    "email": "jane.doe@example.com"
}
```

- **Responses**:
    - **200 OK**: Jika pelanggan berhasil diperbarui.
        - **Content-Type**: `application/json`
        - **Body**:

      ```json
      {
          "message": "Customer updated successfully",
          "data": {
              "id": 1,
              "name": "Jane Doe",
              "address": "456 Elm St",
              "phoneNumber": "555-5678",
              "email": "jane.doe@example.com"
          },
          "errors": []
      }
      ```
    - **404 Not Found**: Jika pelanggan dengan ID tersebut tidak ditemukan.
        - **Content-Type**: `application/json`
        - **Body**:

      ```json
      {
          "message": "Customer not found",
          "data": null,
          "errors": ["Customer with ID {id} not found"]
      }
      ```
    - **400 Bad Request**: Jika ada kesalahan pada data yang dikirim.
        - **Content-Type**: `application/json`
        - **Body**:

      ```json
      {
          "message": "Validation error",
          "data": null,
          "errors": ["Customer name is required", "Phone number must be between 10 and 15 characters"]
      }
      ```

#### 5. Delete Customer

- **Endpoint**: `/customers/{id}`
- **Method**: `DELETE`
- **Description**: Menghapus pelanggan berdasarkan ID.
- **Path Parameters**:
    - `id`: ID dari pelanggan yang ingin dihapus.
- **Responses**:
    - **200 OK**: Jika pelanggan berhasil dihapus.
        - **Content-Type**: `application/json`
        - **Body**:

      ```json
      {
          "message": "Customer deleted successfully",
          "data": "Success",
          "errors": []
      }
      ```
    - **404 Not Found**: Jika pelanggan dengan ID tersebut tidak ditemukan.
        - **Content-Type**: `application/json`
        - **Body**:

      ```json
      {
          "message": "Customer not found",
          "data": null,
          "errors": ["Customer with ID {id} not found"]
      }
      ```

### Contoh Implementasi JSON Request dan Response

#### Request untuk menambahkan Customer dan Vehicle:

```json
{
    "customer": {
        "name": "Jane Doe",
        "address": "456 Elm St",
        "phoneNumber": "555-5678",
        "email": "jane.doe@example.com"
    },
    "vehicle": {
        "licensePlate": "CD456EF",
        "brand": "Honda",
        "model": "Civic",
        "year": 2020,
        "color": "Red"
    }
}
```

#### Response untuk sukses menambahkan Customer dan Vehicle:

```json
{
    "message": "Customer and Vehicle added successfully",
    "data": "Success",
    "errors": []
}
```

#### Response untuk kesalahan validasi:

```json
{
    "message": "Validation error",
    "data": null,
    "errors": [
        "customer.name: Name is required",
        "vehicle.licensePlate: License plate is required"
    ]
}
```