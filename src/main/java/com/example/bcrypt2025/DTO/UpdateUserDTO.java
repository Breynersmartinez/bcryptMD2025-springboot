package com.example.bcrypt2025.DTO;

public class UpdateUserDTO {
    private int idUsuario;
    private String contraseniaActual;
    private String nuevoNombreUsuario;
    private String nuevaContrasenia;

    // Getters y setters

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getContraseniaActual() {
        return contraseniaActual;
    }

    public void setContraseniaActual(String contraseniaActual) {
        this.contraseniaActual = contraseniaActual;
    }

    public String getNuevoNombreUsuario() {
        return nuevoNombreUsuario;
    }

    public void setNuevoNombreUsuario(String nuevoNombreUsuario) {
        this.nuevoNombreUsuario = nuevoNombreUsuario;
    }

    public String getNuevaContrasenia() {
        return nuevaContrasenia;
    }

    public void setNuevaContrasenia(String nuevaContrasenia) {
        this.nuevaContrasenia = nuevaContrasenia;
    }
}
