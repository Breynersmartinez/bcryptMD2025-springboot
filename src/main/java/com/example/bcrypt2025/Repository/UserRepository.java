package com.example.bcrypt2025.Repository;


import com.example.bcrypt2025.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
/*
* Para ver los metodos abstractos que se heredan de JpaRepository presione la tecla CONTROL+ Click derecho.
* No se ven directamente los métodos porque JpaRepository es una interfaz genérica que hereda métodos de otras interfaces.
*/

public interface UserRepository extends JpaRepository<User, Integer> {
}
