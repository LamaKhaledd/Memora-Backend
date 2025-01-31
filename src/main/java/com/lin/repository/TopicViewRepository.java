package com.lin.repository;

import com.lin.entity.TopicView;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TopicViewRepository extends MongoRepository<TopicView, String> {

    // Query to get the latest 4 topics viewed by a specific user
    List<TopicView> findTop4ByUserIdOrderByViewedAtDesc(String userId);
}
