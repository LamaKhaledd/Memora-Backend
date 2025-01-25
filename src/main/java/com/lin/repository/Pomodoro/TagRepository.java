package com.lin.repository.Pomodoro;

// TagRepository.java

import com.lin.entity.Pomodoro.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends MongoRepository<Tag, String> {
    // You can add custom query methods if needed, for example:
    Optional<Tag> findByName(String name);  // Find a tag by its name
}
