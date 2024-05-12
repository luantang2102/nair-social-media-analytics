package com.luantang.socialmediaanalytics.authentication.repository;

import com.luantang.socialmediaanalytics.authentication.model.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);
}
