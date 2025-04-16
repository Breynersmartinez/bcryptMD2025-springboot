
# 📦 Proyecto: `bcrypt2025`

## 🧾 Descripción general

Este proyecto es una aplicacion web construida con **Spring Boot**, que permite gestionar usuarios en una base de datos MySQL. Su enfoque principal es el almacenamiento y validación segura de contraseñas mediante **hashing recursivo con MD5** (10 iteraciones).

---

## 📂 Estructura del Proyecto

```
bcrypt2025
├── Controller
│   └── UserController.java
├── DTO
│   ├── DeleteUserDTO.java
│   └── UpdateUserDTO.java
├── MD5
│   └── MD5.java
├── Model
│   └── User.java
├── Repository
│   └── UserRepository.java
├── Service
│   └── UserService.java
├── resources
│   └── application.properties
└── pom.xml
```

---

##  Funcionalidades principales

###  Registro de usuario
- Se recibe un `User` con ID, nombre y contraseña.
- La contraseña es encriptada usando MD5 recursivo (10 veces) antes de ser almacenada.

###  Login
- Se verifica la combinación ID + contraseña ingresada (hasheada) con la almacenada en la base de datos.

### 🛠 Actualización de usuario
- Solo es posible si la contraseña actual es válida.
- Permite cambiar el nombre y la contraseña (ambos opcionales).

### ️ Eliminación de usuario
- Requiere ID y contraseña correctos para eliminar al usuario.

###  Consulta de usuario por ID
- Devuelve los datos del usuario, ocultando la contraseña.

---

##  Clases y responsabilidades

### `User.java` (Model)
- Entidad que representa la tabla `usuario` en la base de datos.
- Campos: `idUsuario`, `nombreUsuario`, `contraseniaUsuario`.

### `UserRepository.java`
- Repositorio de acceso a datos, que hereda de `JpaRepository`.

### `UserService.java`
- Contiene la lógica del negocio:
  - Registro con hashing.
  - Validación de credenciales.
  - Lógica para login, update y delete.

### `MD5.java`
- Implementa la función de hash MD5, con relleno para mantener los 32 caracteres.

### DTOs
- `UpdateUserDTO`: permite enviar datos para cambiar nombre o contraseña.
- `DeleteUserDTO`: permite enviar ID y contraseña para validar la eliminación.

### `UserController.java`
- Expone los endpoints REST para:
  - `GET /usuarios/{id}`: obtener usuario.
  - `POST /usuarios`: registrar.
  - `POST /usuarios/update`: actualizar.
  - `POST /usuarios/delete`: eliminar.
  - `POST /usuarios/login`: login.

---

## 🧪 Endpoints REST

| Método | Endpoint          | Descripción                            |
|--------|-------------------|----------------------------------------|
| GET    | `/usuarios/{idUsuario}` | Obtener usuario por ID (sin contraseña)|
| POST   | `/usuarios`       | Registrar nuevo usuario
| POST   | `/usuarios/login` | Validar login con ID y contraseña      |
| POST   | `/usuarios/update` | Actualizar nombre/contraseña           |
| POST   | `/usuarios/delete` | Eliminar usuario con contraseña        |

---

## ⚙️ Requisitos del sistema

- Java 17
- Maven 3.6+
- Base de datos MySQL
- Spring Boot 3.4.4

---

## 📦 Dependencias (en `pom.xml`)
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>8.3.0</version>
</dependency>
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
```

---
