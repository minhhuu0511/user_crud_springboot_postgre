package com.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.Optional;

@Service
public class User1Service {

    @Autowired
    private User1Repository userRepository;

    // Phương thức để lấy ra tất cả các user
    public List<User1> getAllUsers() {
        return userRepository.findAll();
    }

    // Phương thức để lấy ra một user dựa trên id
    public Optional<User1> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Phương thức để tạo một user mới
    public User1 createUser(User1 user) {
    	
    	
        return userRepository.save(user);
    }

    // Phương thức để cập nhật thông tin của một user
    public User1 updateUser(Long id, User1 user) {
        user.setId(id);
        return userRepository.save(user);
    }

    // Phương thức để xóa một user dựa trên id
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}