package com.api.user;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;




import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class User1Controller {

    @Autowired
    private User1Repository userRepository;
    
    @Autowired
    private APIService apiService;
    
//    @Autowired
//    private PasswordEncoderImpl1 passwordEncoderImpl1;

    // Endpoint để lấy ra tất cả các user
    @GetMapping("")
    public ResponseEntity<List<User1>> getAllUsers() {
        List<User1> userList = userRepository.findAll();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    // Endpoint để lấy ra một user dựa trên id
    @GetMapping("/{id}")
    public ResponseEntity<User1> getUserById(@PathVariable("id") Long id) {
        Optional<User1> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint để tạo một user mới
    @PostMapping
    public ResponseEntity<User1> createUser(@RequestBody User1 user) {
//    	String passwordencode= passwordEncoderImpl1.encode(user.getPassword());
//    	user.setPassword(passwordencode);
    	apiService.createUser(user);
//    	User1 newUser = userRepository.save(user);
    	
        return new ResponseEntity<>( HttpStatus.CREATED);
    }

    // Endpoint để cập nhật thông tin của một user
    @PutMapping("/{id}")
    public ResponseEntity<User1> updateUser(@PathVariable("id") Long id, @RequestBody User1 user) {
        Optional<User1> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            user.setId(id);
//            String passwordencode= passwordEncoderImpl1.encode(user.getPassword());
//        	user.setPassword(passwordencode);
            User1 updatedUser = userRepository.save(user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint để xóa một user dựa trên id
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

