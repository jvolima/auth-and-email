package com.jvolima.authandemail.repositories;

import com.jvolima.authandemail.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);
    Optional<User> findByVerificationToken(String verificationCode);
    Optional<User> findByChangePasswordToken(String changePasswordToken);
}
