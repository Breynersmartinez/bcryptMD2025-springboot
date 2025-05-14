package com.example.bcrypt2025.Service;

import com.example.bcrypt2025.DTO.UpdateUserDTO;
import com.example.bcrypt2025.MD5.MD5;
import com.example.bcrypt2025.Model.User;
import com.example.bcrypt2025.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    // Traer el usuario por id
    public Optional<User> getUser(int idUsuario) {
        return userRepository.findById(idUsuario);
    }

    // Registro de usuario
    public void save(User user) {
        // Verifica si el ID es nulo o negativo
        int id = user.getIdUsuario();

        // Verifica si el ID ya existe en la base de datos
        if (userRepository.existsById(id)) {
            throw new IllegalArgumentException("Este ID ya está en uso por otro usuario.");
        }

        // Encriptar contraseña usando el método bcrypt exactamente como en la versión original
        String hashedPassword = bcrypt(user, 10);
        user.setContraseniaUsuario(hashedPassword);
        userRepository.save(user);
    }

    // Método bcrypt recursivo que replica exactamente la lógica original
    private String bcrypt(User user, int cost) {
        if (cost >= 1) {
            // Aplicar salt y actualizar la contraseña en el objeto
            String salted = salt(user);
            user.setContraseniaUsuario(salted);
            // Llamada recursiva con cost-1
            return bcrypt(user, cost - 1);
        }
        return user.getContraseniaUsuario();
    }




    // Metodo salt que replica exactamente la logica original
    private String salt(User user) {
        // Aplicar el algoritmo MD5 a la contraseña y el ID del usuario 
        return MD5.getMd5("" + user.getIdUsuario() + user.getContraseniaUsuario());
    }




    // Cambio de nombre y contraseña - sin validación de contraseña
    public boolean updateUser(UpdateUserDTO data) {
        
        // Verifica si el usuario existe
        Optional<User> userOpt = userRepository.findById(data.getIdUsuario());

        // Verifica si el usuario existe
        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // Verifica si el nuevo nombre de usuario o la nueva contraseña no están vacíos
            if (data.getNuevoNombreUsuario() != null && !data.getNuevoNombreUsuario().isEmpty()) {
                user.setNombreUsuario(data.getNuevoNombreUsuario());
            }

            // Verifica si la nueva contraseña no está vacía
            if (data.getNuevaContrasenia() != null && !data.getNuevaContrasenia().isEmpty()) {
                // Creacion de  un usuario temporal para la nueva contraseña
                User newPasswordUser = new User();

                // Se asigna el id del usuario actual al nuevo usuario
                newPasswordUser.setIdUsuario(data.getIdUsuario());

                // Se asigna la nueva contraseña al nuevo usuario
                newPasswordUser.setContraseniaUsuario(data.getNuevaContrasenia());

                // Aplicacion de  bcrypt a la nueva contraseña
                String newHashedPassword = bcrypt(newPasswordUser, 10);

                // Se asigna la nueva contraseña al usuario actual
                user.setContraseniaUsuario(newHashedPassword);
            }

            // Guardar los cambios en la base de datos, se guarda el usuario actualizado
            userRepository.save(user);
            return true;
        }

        return false;
    }

    // Eliminar usuario - sin validación de contraseña
    public boolean deleteUser(int idUsuario) {
        // Verifica si el usuario existe
        Optional<User> userOpt = userRepository.findById(idUsuario);

     
       
         if (userOpt.isPresent()) {
             userRepository.deleteById(idUsuario);
             return true;
         }
        if (userOpt.isPresent()) {
            userRepository.deleteById(idUsuario);
            return true;
        }

        return false;
    }

    // Login
    public boolean login(User usuario) {
        Optional<User> userOpt = userRepository.findById(usuario.getIdUsuario());

        // Verifica si el usuario existe
        if (userOpt.isPresent()) {
            User userBD = userOpt.get();

            // Aplicacion de bcrypt exactamente igual que en la versión original
            String hashedPassword = bcrypt(usuario, 10);

            // Verifica si la contraseña coincide, si coincide devuelve true
            return userBD.getContraseniaUsuario().equals(hashedPassword);
        }
        return false;
    }
}