package uz.library.national_library.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import uz.library.national_library.models.InputUser;
import uz.library.national_library.models.Role;
import uz.library.national_library.models.User;
import uz.library.national_library.services.UserService;

import java.util.HashSet;
import java.util.Objects;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/worker/register")
    public ResponseEntity<?> saveUser(@RequestBody InputUser inputUser) {

        if (inputUser.getUserName() == null || inputUser.getUserName().equals("") || inputUser.getPassword() == null)
            return new ResponseEntity<>("Siz hamma ma'lumotlarni kiritishingiz lozim!", HttpStatus.BAD_REQUEST);

        if (inputUser.getUserName().trim().contains(" ")) {
            return new ResponseEntity<>("Username faqat bitta so'zdan iborat bo'lishi kerak !", HttpStatus.BAD_REQUEST);
        }

        if (inputUser.getPassword().trim().contains(" ")) {
            return new ResponseEntity<>("Parol faqat bitta so'zdan iborat bo'lishi kerak !", HttpStatus.BAD_REQUEST);
        }

        if (!checkPasswordLength(inputUser.getPassword())) {
            return new ResponseEntity<>("Siznimg parolingiz eng kamida 4 ta belgidan iborat bo'lishi kerak!", HttpStatus.BAD_REQUEST);
        }

        if (userService.checkUserName(inputUser.getUserName())) {
            return new ResponseEntity<>("Bu nomdagi user allaqachon mavjud!", HttpStatus.BAD_REQUEST);
        }

        var user = new User();
        user.setFullName(inputUser.getFullName());
        user.setUserName(inputUser.getUserName());
        user.setPassword(inputUser.getPassword());
        user.setEmail(inputUser.getEmail());

        var role = new Role();
        role.setName("ROLE_USER");
        var set = new HashSet<Role>();
        set.add(role);
        user.setRoles(set);

        return ResponseEntity.ok(userService.saveUser(user));
    }

    @PostMapping("/admin/register")
    public ResponseEntity<?> saveAdmin(@RequestBody InputUser inputUser) {

        if (inputUser.getUserName() == null || inputUser.getPassword() == null || inputUser.getUserName().equals(""))
            return new ResponseEntity<>("Siz hamma ma'lumotlarni kiritishingiz lozim!", HttpStatus.BAD_REQUEST);

        if (inputUser.getUserName().trim().contains(" ")) {
            return new ResponseEntity<>("Username faqat bitta so'zdan iborat bo'lishi kerak !", HttpStatus.BAD_REQUEST);
        }

        if (inputUser.getPassword().trim().contains(" ")) {
            return new ResponseEntity<>("Parol faqat bitta so'zdan iborat bo'lishi kerak !", HttpStatus.BAD_REQUEST);
        }

        if (!checkPasswordLength(inputUser.getPassword())) {
            return new ResponseEntity<>("Siznimg parolingiz eng kamida 4 ta belgidan iborat bo'lishi kerak!", HttpStatus.BAD_REQUEST);
        }

        if (userService.checkUserName(inputUser.getUserName())) {
            return new ResponseEntity<>("Bu nomdagi user allaqachon mavjud!", HttpStatus.BAD_REQUEST);
        }

        var user = new User();
        user.setFullName(inputUser.getFullName());
        user.setUserName(inputUser.getUserName());
        user.setPassword(inputUser.getPassword());
        user.setEmail(inputUser.getEmail());

        var role = new Role();
        role.setName("ROLE_ADMIN");
        var set = new HashSet<Role>();
        set.add(role);
        user.setRoles(set);

        return ResponseEntity.ok(userService.saveUser(user));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody User user) {

        if (user.getUserName() == null || user.getPassword() == null || user.getUserName().equals(""))
            return new ResponseEntity<>("Siz hamma ma'lumotlarni kiritishingiz lozim!", HttpStatus.BAD_REQUEST);

        if (user.getUserName().trim().contains(" ")) {
            return new ResponseEntity<>("Username faqat bitta so'zdan iborat bo'lishi kerak !", HttpStatus.BAD_REQUEST);
        }

        if (user.getPassword().trim().contains(" ")) {
            return new ResponseEntity<>("Parol faqat bitta so'zdan iborat bo'lishi kerak !", HttpStatus.BAD_REQUEST);
        }

        if (!checkPasswordLength(user.getPassword())) {
            return new ResponseEntity<>("Siznimg parolingiz eng kamida 4 ta belgidan iborat bo'lishi kerak!", HttpStatus.BAD_REQUEST);
        }

        var bool = ! Objects.equals(user.getPassword(), userService.getUserById(user.getId()).getPassword());
        return ResponseEntity.ok(userService.update(user, bool));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        if (!userService.doesUserExist(id))
            return new ResponseEntity<>("Bunday foydalanuvchi mavjud emas!", HttpStatus.BAD_REQUEST);

        var msg = userService.delete(id);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
        if (!userService.doesUserExist(id))
            return new ResponseEntity<>("Bunday foydalanuvchi mavjud emas", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> getAllUserByName(@RequestParam("name") String name) {
        var list = userService.getAllUsersByName(name);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        var list = userService.getAllUsers();
        return ResponseEntity.ok(list);
    }

    private Boolean checkPasswordLength(String password) {
        return password.length() >= 4;
    }
}
