Docs: API Spec [customer](/docs/customer.md), [vehicle](/docs/vehicle.md), [repair](docs/repair.md), [repair detail](docs/repair-detail.md)

sibebeng - RESTful API untuk Manajemen Bengkel Mobil
Deskripsi: Mengembangkan RESTful API untuk aplikasi manajemen bengkel mobil menggunakan Java dan Spring Boot. API ini dirancang untuk mengelola berbagai aspek operasional bengkel dengan efisien.


Fitur:
- Implementasi CRUD (Create, Read, Update, Delete) untuk entitas customer, vehicle, repair (Layanan Bengkel), dan repair detail.
- Validasi data menggunakan Hibernate Validator untuk memastikan integritas dan validitas input.
- Penggunaan DTO (Data Transfer Object) untuk memisahkan layer data dan bisnis.
- Pengujian unit dan integrasi untuk memastikan kualitas kode dan fungsionalitas aplikasi.


Teknologi:
- **Spring Boot** untuk pembuatan aplikasi RESTful.
- **Spring Data JPA/Hibernate** untuk manajemen data dan ORM.
- **Lombok** untuk mengurangi boilerplate code dan meningkatkan keterbacaan.
- **AssertJ** dan **JUnit** untuk penulisan dan eksekusi tes unit serta integrasi.
- **MySQL** sebagai sistem manajemen basis data.
- **JSON** untuk format data pertukaran.
- **RESTful Web Services** untuk komunikasi antar sistem.


Hasil:
- API ini memungkinkan integrasi yang mudah dengan frontend dan sistem lain.
- Mempercepat pengelolaan operasional harian bengkel dengan menyediakan endpoint yang efisien.
- Memudahkan pemeliharaan dan pengembangan berkat struktur kode yang terorganisir dan diuji secara menyeluruh.


### Install di local

clone repository `git clone ...` via ssh atau http.

navigasi ke folder clone nya.. `cd sibebeng`

buka di text editor atau IDE..

lalu ubah spring config [properties](src/main/resources/application.properties) nya sesuai database di local
```properties
spring.datasource.username=root # sesuaikan username
spring.datasource.password=password # sesuaikan password
spring.datasource.url=jdbc:mysql://localhost:3306/sibebeng #nama database   
```

di root project sibebeng nya run command `mvn validate clean package` lalu `mvn spring-boot:run`

lalu buka di `http://localhost:8181/api/` pake postman atau tools lainnya.. default portnya saya ubah pake `8181` dan untuk api spec nya tingggal di baca di [docs](docs/)
