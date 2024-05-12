package com.luantang.socialmediaanalytics.dashboard.repository;

import com.luantang.socialmediaanalytics.dashboard.model.FacebookAuthDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FacebookAuthRepository extends MongoRepository<FacebookAuthDetails, String> {
    Optional<FacebookAuthDetails> findByAppUserEmail(String email);
}
