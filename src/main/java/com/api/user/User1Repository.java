package com.api.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface User1Repository extends JpaRepository<User1, Long> {
    // Bạn có thể thêm các phương thức tùy chỉnh tại đây nếu cần
}

