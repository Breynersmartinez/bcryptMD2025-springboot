
---

###  1. **`User` - Modelo de usuario**
Está en el paquete `Model`. Representa una tabla de base de datos llamada `usuario`.

```java
@Table(name = "usuario")
public class User {
    private int idUsuario;
    private String nombreUsuario;
    private String contraseniaUsuario;
}
```

Se usa la anotación `@Entity` para decirle a Spring que es una entidad de base de datos. Y con `@Column`, se define cómo se mapea cada campo a la tabla SQL.

---

###  2. **`UserRepository` - Repositorio de usuario**
En el paquete `Repository`, hereda de `JpaRepository`, lo que le da métodos CRUD listos para usar (como `findById`, `save`, `deleteById`, etc.).

```java
public interface UserRepository extends JpaRepository<User, Integer> {}
```

---

###  3. **`UserService` - Lógica del negocio**
Aquí está la parte más importante del proyecto:

####  Registro
Cuando se registra un usuario, su contraseña se transforma recursivamente usando MD5 como si fuera bcrypt:

```java
String hashedPassword = bcrypt(user, 10);
```

####  Método `bcrypt(...)`
Este método aplica una lógica recursiva: toma la contraseña del usuario, la mezcla con el `idUsuario` (función `salt(...)`) y le aplica MD5. Lo hace 10 veces (simulando un cost alto como el bcrypt real):

```java
private String bcrypt(User user, int cost) {
    if (cost >= 1) {
        String salted = salt(user); // mezcla con ID y aplica MD5
        user.setContraseniaUsuario(salted);
        return bcrypt(user, cost - 1); // recursión
    }
    return user.getContraseniaUsuario();
}
```

#### 🔸 Cambiar usuario o contraseña
Usa un DTO (`UpdateUserDTO`) para recibir nuevos datos. Si se cambia la contraseña, se vuelve a aplicar `bcrypt(...)` antes de guardarla.

#### 🔸 Login
Durante el login, se encripta la contraseña del input **de la misma manera** que cuando se guardó, y se compara:

```java
String hashedPassword = bcrypt(usuario, 10);
return userBD.getContraseniaUsuario().equals(hashedPassword);
```

---

###  4. **`MD5` - Clase para encriptar**
Se encarga de aplicar el algoritmo **MD5** a un `String`.

```java
public static String getMd5(String input) {
    MessageDigest md = MessageDigest.getInstance("MD5");
    byte[] messageDigest = md.digest(input.getBytes());
    ...
}
```

---

### 🧾 5. **`UpdateUserDTO` - Objeto para actualizar**
Un objeto de transferencia de datos que recibe los cambios del usuario (nombre y/o contraseña).

---

### 🌐 6. **`UserController` - Controlador REST**
Expone varias rutas REST (`/usuarios`) para registrar, actualizar, eliminar, obtener por ID o hacer login.

#### Ejemplos:
- **`GET /usuarios/{idUsuario}`** → Trae usuario sin mostrar contraseña.
- **`POST /usuarios`** → Registra usuario nuevo.
- **`POST /usuarios/update`** → Cambia nombre o contraseña.
- **`POST /usuarios/delete`** → Elimina usuario.
- **`POST /usuarios/login`** → Hace login.

---
