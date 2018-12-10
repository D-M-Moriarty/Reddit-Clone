package com.moriarty.couchdb_ca.restful_endpoint;

import com.moriarty.couchdb_ca.db_repository.UserRepository;
import com.moriarty.couchdb_ca.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.PermitAll;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public List<User> getAllUsers() {
        return userRepository.getAll();
    }

    @GetMapping("/{_id}")
    public User getUser(@PathVariable String _id) {
        return userRepository.get(_id);
    }

    @PostMapping("")
    public User addUser(@RequestBody User user) {
        userRepository.add(user);
        return user;
    }

    @GetMapping("/name={name}")
    public List<User> updateUser(@PathVariable String name) {
        return userRepository.findByName(name);
    }

    @GetMapping("/date={date}")
    public List<User> getByDate(@PathVariable String date) {
        return userRepository.findByCreatedAt(date);
    }

    @GetMapping("/get_name_cust")
    public String updateUser() {
        return userRepository.getNameCust();
    }

    @PutMapping("/image")
    public void addUserImage(@RequestParam("image") MultipartFile image) throws IOException {
        // TODO refactor this please
        String contentType = image.getContentType();
        byte[] encodedBytes = Base64.getEncoder().encode(image.getBytes());
        System.out.println("encodedBytes " + new String(encodedBytes));
        userRepository.addUserProfilePicture(new String(encodedBytes), contentType, image.getName());

    }

    @DeleteMapping("/{_id}")
    public void deleteUser(@PathVariable String _id) {
        User user = userRepository.get(_id);
        userRepository.remove(user);
    }
}
