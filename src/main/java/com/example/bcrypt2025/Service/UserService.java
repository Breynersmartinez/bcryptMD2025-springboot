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

    public Optional<User> getUser(int idUsuario) {
        return userRepository.findById(idUsuario);
    }

    // Registro de usuario
    public void save(User user) {
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
        return MD5.getMd5("" + user.getIdUsuario() + user.getContraseniaUsuario());
    }




    // Cambio de nombre y contraseña - sin validación de contraseña
    public boolean updateUser(UpdateUserDTO data) {
        Optional<User> userOpt = userRepository.findById(data.getIdUsuario());

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            if (data.getNuevoNombreUsuario() != null && !data.getNuevoNombreUsuario().isEmpty()) {
                user.setNombreUsuario(data.getNuevoNombreUsuario());
            }

            if (data.getNuevaContrasenia() != null && !data.getNuevaContrasenia().isEmpty()) {
                // Creacion de  un usuario temporal para la nueva contraseña
                User newPasswordUser = new User();
                newPasswordUser.setIdUsuario(data.getIdUsuario());
                newPasswordUser.setContraseniaUsuario(data.getNuevaContrasenia());

                // Aplicacion de  bcrypt a la nueva contraseña
                String newHashedPassword = bcrypt(newPasswordUser, 10);
                user.setContraseniaUsuario(newHashedPassword);
            }

            userRepository.save(user);
            return true;
        }

        return false;
    }

    // Eliminar usuario - sin validación de contraseña
    public boolean deleteUser(int idUsuario) {
        Optional<User> userOpt = userRepository.findById(idUsuario);

        if (userOpt.isPresent()) {
            userRepository.deleteById(idUsuario);
            return true;
        }

        return false;
    }

    // Login
    public boolean login(User usuario) {
        Optional<User> userOpt = userRepository.findById(usuario.getIdUsuario());

        if (userOpt.isPresent()) {
            User userBD = userOpt.get();

            // Aplicacion de bcrypt exactamente igual que en la versión original
            String hashedPassword = bcrypt(usuario, 10);

            return userBD.getContraseniaUsuario().equals(hashedPassword);
        }
        return false;
    }
}