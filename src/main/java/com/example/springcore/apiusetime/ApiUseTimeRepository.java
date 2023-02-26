package com.example.springcore.apiusetime;

import com.example.springcore.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiUseTimeRepository extends JpaRepository<ApiUseTime, Long> {


    Optional<ApiUseTime> findByUser(User user);
}
