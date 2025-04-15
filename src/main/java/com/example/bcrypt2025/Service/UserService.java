package com.example.bcrypt2025.Service;


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



    public Optional<User> getUser(int idUsuario)
    {
        return userRepository.findById(idUsuario);
    }


    public void saveOrUpdate(User user)
    {
        userRepository.save(user);
    }

    public void delete( int idUsuario)
    {
        userRepository.deleteById(idUsuario);
    }

}