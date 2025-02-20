## User Management

### Feature

- Member CRUD, search LIKE name
- Login dan Register
- Saat create member, set reportsTo(hanya boleh kosong waktu signUp)

### Git Command

- git clone: mengunggah project backend ke local
- git pull: mengambil perubahan berdasarkan commmitan terbaru

### Maven Command

- mvn clean install: mengunggah dependency-dependency yang dibutuhkan pada project ini
- mvn spring-boot:run : menjalankan project backend springboot

### Note

- Project backend menggunakan database PostgreSQL dengan konfigurasi seperti :
  - databaseName = dika
  - port: 5432
  - username: postgres
  - password: admin
  - jika ingin menggunakan konfigurasi sendiri bisa diubah pada file application.properties

### Swagger Path

Path: http://localhost:8081/swagger-ui/index.html
