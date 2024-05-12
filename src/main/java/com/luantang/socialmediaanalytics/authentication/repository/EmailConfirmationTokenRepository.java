package com.luantang.socialmediaanalytics.authentication.repository;

import com.luantang.socialmediaanalytics.authentication.model.EmailConfirmationToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmailConfirmationTokenRepository extends MongoRepository<EmailConfirmationToken, UUID> {
    Optional<EmailConfirmationToken> findByToken(String token);

}
