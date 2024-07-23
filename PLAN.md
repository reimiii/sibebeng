sistem informasi untuk bengkel (mobil/motor)

di pakai untuk mencatat informasi seputar pelanggan, kendaraan pelanggan, kapan masuk ke bengkel dan kapan keluar dari bengkel

untuk saat ini itu saja..

jadi ada table `customers` yang mempunyai banyak `vehicle`  
biasanya mobil kalo ke bengkel pasti punya penyakit yang mau di benerin `repairs` untuk catet kapan dateng kapan keluar dan `repair_details` untk detail dari perbaikan nya

kira kira seperti ini schema db nya

1. **Tabel `customers`**:
    - `id` (Primary Key)
    - `name`
    - `address`
    - `phone_number`
    - `email`

2. **Tabel `vehicles`**:
    - `id` (Primary Key)
    - `customer_id` (Foreign Key yang merujuk ke `customers.id`)
    - `license_plate` (nomor plat kendaraan)
    - `brand`
    - `model`
    - `year`
    - `color`

3. **Tabel `repairs`**:
    - `id` (Primary Key)
    - `vehicle_id` (Foreign Key yang merujuk ke `vehicles.id`)
    - `entry_date` (tanggal masuk ke bengkel)
    - `exit_date` (tanggal keluar dari bengkel)
    - `description` (deskripsi masalah kendaraan)

4. **Tabel `repair_details`**:
    - `id` (Primary Key)
    - `repair_id` (Foreign Key yang merujuk ke `repairs.id`)
    - `issue_description` (deskripsi penyakit)
    - `repair_action` (aksi perbaikan yang dilakukan)
    - `cost` (biaya perbaikan)

contoh sql

```mysql
CREATE TABLE customers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    address VARCHAR(255),
    phone_number VARCHAR(15),
    email VARCHAR(100)
);

CREATE TABLE vehicles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    license_plate VARCHAR(10),
    brand VARCHAR(50),
    model VARCHAR(50),
    year INT,
    color VARCHAR(20),
    FOREIGN KEY (customer_id) REFERENCES customers(id)
);

CREATE TABLE repairs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    vehicle_id INT,
    entry_date DATE,
    exit_date DATE,
    description TEXT,
    FOREIGN KEY (vehicle_id) REFERENCES vehicles(id)
);

CREATE TABLE repair_details (
    id INT AUTO_INCREMENT PRIMARY KEY,
    repair_id INT,
    issue_description TEXT,
    repair_action TEXT,
    cost DECIMAL(10, 2),
    FOREIGN KEY (repair_id) REFERENCES repairs(id)
);
```

