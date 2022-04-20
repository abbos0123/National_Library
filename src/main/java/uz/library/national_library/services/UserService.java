package uz.library.national_library.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.library.national_library.models.User;
import uz.library.national_library.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User update(User user, boolean isPasswordChange) {
        if (isPasswordChange)
            user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public String delete(Long id) {
        User user = userRepository.findUserById(id);

        userRepository.delete(user);

        return user.getUserName() + " is deleted";
    }

    public User getUserById(Long id) {
        var user = userRepository.findUserById(id);
        return user;
    }


    public List<User> getAllUsersByName(String name) {
        if (name == null || name.equals(""))
            return getAllUsers();

        var users = userRepository.findAllByUserNameContainingIgnoreCase(name);
        return users;
    }

    public List<User> getAllUsers() {
        var userList = userRepository.findAll();
        return userList;
    }

    public User getUserByUsername(String username) {
        return userRepository.findUserByUserName(username);
    }


    public boolean checkUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

    public boolean doesUserExist(Long id) {
        return userRepository.existsUserById(id);
    }
}
