package com.example.bcrypt2025.Controller;


import com.example.bcrypt2025.DTO.DeleteUserDTO;
import com.example.bcrypt2025.DTO.UpdateUserDTO;
import com.example.bcrypt2025.Model.User;

import com.example.bcrypt2025.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UserController {



    @Autowired

    private UserService userService;



    @GetMapping("/{idUsuario}")
    public ResponseEntity<?> getById(@PathVariable("idUsuario") int idUsuario) {
        Optional<User> user = userService.getUser(idUsuario);

        if (user.isPresent()) {
            User usuarioSinContrasenia = user.get();
            usuarioSinContrasenia.setContraseniaUsuario(null); // Ocultar la contraseña
            return ResponseEntity.ok(usuarioSinContrasenia);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(" Usuario no encontrado");
        }
    }

 //Registro de usuario
    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            userService.save(user);
            return ResponseEntity.ok("Usuario registrado correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno al registrar el usuario.");
        }
    }

//Cambio de usuario  o contraseña
    @PostMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody UpdateUserDTO data) {
        boolean updated = userService.updateUserWithValidation(data);

        if (updated) {
            return ResponseEntity.ok(" Usuario actualizado correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(" Contraseña actual incorrecta o usuario no encontrado.");
        }
    }


    //Eliminar usuario, se pide id y contraseña
    @PostMapping("/delete")
    public boolean deleteUserWithPassword(@RequestBody DeleteUserDTO data) {
        return userService.deleteWithPassword(data.getIdUsuario(), data.getContraseniaUsuario());
    }


    @PostMapping("/login")
    public boolean login(@RequestBody User user) {
        return userService.login(user);
    }




}

