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

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContraseniaUsuario() {
        return contraseniaUsuario;
    }

    public void setContraseniaUsuario(String contraseniaUsuario) {
        this.contraseniaUsuario = contraseniaUsuario;
    }
}