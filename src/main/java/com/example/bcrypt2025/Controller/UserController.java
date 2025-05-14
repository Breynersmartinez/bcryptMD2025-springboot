package com.example.bcrypt2025.Controller;

import com.example.bcrypt2025.DTO.UpdateUserDTO;
import com.example.bcrypt2025.Model.User;
import com.example.bcrypt2025.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UserController {



    @Autowired
    private final UserService userService;

    
    // Constructor de la clase UserController
    public UserController(UserService userService)
    {
        this.userService = userService;
    }


    // Traer el usuario por id
    @GetMapping("/{idUsuario}")
    // @PathVariable indica que el valor de la variable idUsuario se extraerá de la URL y se pasará como argumento al método
    public ResponseEntity<?> getById(@PathVariable("idUsuario") int idUsuario) {

        // Se utiliza Optional para manejar la posibilidad de que el usuario no exista
        // Se utiliza el método getUser de la clase UserService para obtener el usuario
        Optional<User> user = userService.getUser(idUsuario);


        // Si el usuario existe, se devuelve el usuario sin la contraseña y con un estado 200 OK
        if (user.isPresent()) {
            User usuarioSinContrasenia = user.get();
            usuarioSinContrasenia.setContraseniaUsuario(null); // Ocultar la contraseña
            return ResponseEntity.ok(usuarioSinContrasenia);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(" Usuario no encontrado");
        }
    }

    /*
    @RequestBody
     indica que Spring debe realizar  el cuerpo de una solicitud en un objeto,
     que se pasa como parámetro del método controlador.
     */

    // Registro de usuario
    @PostMapping
    // @RequestBody indica que el cuerpo de la solicitud se debe mapear al objeto User
    public ResponseEntity<?> registerUser(@RequestBody User user) {


        // Verifica si el ID es nulo o negativo
        try {
            
            userService.save(user);
            return ResponseEntity.ok("Usuario registrado correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno al registrar el usuario.");
        }
    }

    // Cambio de usuario o contraseña - sin validación de contraseña
    @PostMapping("/update")

    // @RequestBody indica que el cuerpo de la solicitud se debe mapear al objeto UpdateUserDTO
    // Se utiliza UpdateUserDTO para actualizar el usuario
    public ResponseEntity<String> updateUser(@RequestBody UpdateUserDTO data) {


        boolean updated = userService.updateUser(data);

        if (updated) {
            return ResponseEntity.ok("Usuario actualizado correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        }
    }

    // Eliminar usuario - sin requerir contraseña
    @PostMapping("/delete")
    public boolean deleteUser(@RequestBody UpdateUserDTO data) {
        return userService.deleteUser(data.getIdUsuario());
    }

    // Login de usuario
    @PostMapping("/login")
    // @RequestBody indica que el cuerpo de la solicitud se debe mapear al objeto User
    // Se utiliza el objeto User para iniciar sesión
    public boolean login(@RequestBody User user) {

        
        return userService.login(user);
    }
}