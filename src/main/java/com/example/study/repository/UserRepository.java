package com.example.study.repository;


import com.example.study.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {


    User findFirstByPhoneNumberOrderByIdDesc(String phoneNumber);
    Optional<User> findByEmail(String email);


}
