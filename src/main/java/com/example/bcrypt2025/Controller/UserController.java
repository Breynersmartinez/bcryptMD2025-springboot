package com.example.bcrypt2025.Controller;


import com.example.bcrypt2025.Model.User;

import com.example.bcrypt2025.Service.UserService;
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
@RequestMapping("/api/usuarios")
public class UserController {



    @Autowired

    private UserService userService;



    @GetMapping("/{idUsuario}")
    public Optional<User> getById(@PathVariable("idUsuario") int idUsuario) {
        return userService.getUser(idUsuario);
    }


    @PostMapping

    public void getAll(@RequestBody User user)
    {
        userService.saveOrUpdate(user);
    }

    @DeleteMapping("/{idUsuario}")
    public void saveOrUpdate(@PathVariable("idUsuario")int idUsuario)
    {
        userService.delete(idUsuario);
    }

}