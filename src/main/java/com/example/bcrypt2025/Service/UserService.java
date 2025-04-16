package com.example.bcrypt2025.Service;


import com.example.bcrypt2025.DTO.UpdateUserDTO;
import com.example.bcrypt2025.MD5.MD5;
import com.example.bcrypt2025.Model.User;
import com.example.bcrypt2025.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;


@Service
public class UserService {



    @Autowired
    UserRepository userRepository;

    public Optional<User> getUser(int idUsuario) {
        return userRepository.findById(idUsuario);
    }


    //Registro de usuario
    public void save(User user) {
        int id = user.getIdUsuario();

        // Verifica si el ID ya existe en la base de datos
        if (userRepository.existsById(id)) {
            throw new IllegalArgumentException("Este ID ya está en uso por otro usuario.");
        }

        String input = id + user.getContraseniaUsuario();
        String hashedPassword = aplicarMD5Recursivo(input, 10);
        user.setContraseniaUsuario(hashedPassword);
        userRepository.save(user);
    }

    //Cambio de nombre y contraseña
    public boolean updateUserWithValidation(UpdateUserDTO data) {
        Optional<User> userOpt = userRepository.findById(data.getIdUsuario());

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String hashedInput = aplicarMD5Recursivo(data.getIdUsuario() + data.getContraseniaActual(), 10);

            if (user.getContraseniaUsuario().equals(hashedInput)) {
                if (data.getNuevoNombreUsuario() != null && !data.getNuevoNombreUsuario().isEmpty()) {
                    user.setNombreUsuario(data.getNuevoNombreUsuario());
                }

                if (data.getNuevaContrasenia() != null && !data.getNuevaContrasenia().isEmpty()) {
                    String nuevaHash = aplicarMD5Recursivo(data.getIdUsuario() + data.getNuevaContrasenia(), 10);
                    user.setContraseniaUsuario(nuevaHash);
                }

                userRepository.save(user);
                return true;
            }
        }

        return false;
    }


    //Eliminar usuario
    public boolean deleteWithPassword(int idUsuario, String contraseniaUsuario) {
        Optional<User> userOpt = userRepository.findById(idUsuario);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String hashedInput = aplicarMD5Recursivo(idUsuario + contraseniaUsuario, 10);

            if (user.getContraseniaUsuario().equals(hashedInput)) {
                userRepository.deleteById(idUsuario);
                return true;
            }
        }

        return false;
    }


    //Login
    public boolean login(User usuario) {
        Optional<User> userOpt = userRepository.findById(usuario.getIdUsuario());

        if (userOpt.isPresent()) {
            User userBD = userOpt.get();
            String md5HashedInput = aplicarMD5Recursivo(usuario.getIdUsuario() + usuario.getContraseniaUsuario(), 10);

            return userBD.getContraseniaUsuario().equals(md5HashedInput);
        }
        return false;
    }


    // MD5 recursivo
    private String aplicarMD5Recursivo(String input, int veces) {
        String resultado = input;
        for (int i = 0; i < veces; i++) {
            resultado = MD5.getMd5(resultado);
        }
        return resultado;
    }

}
