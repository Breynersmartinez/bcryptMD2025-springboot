package com.example.bcrypt2025.Model;

import jakarta.persistence.*;
import lombok.Data;


import jakarta.persistence.Id;
import jakarta.persistence.Table;



@Entity

// Nombre de la tabla de la base de datos que esta mapeada la entidad
@Table(name = "usuario")
@Data
public class User {

    @Id

    // Atributos y tablas relacionadas en la bd

    @Column(name="idUsuario", unique = true, nullable = false)
    private int idUsuario;
    private  String nombreUsuario;
    private String contraseniaUsuario;



}