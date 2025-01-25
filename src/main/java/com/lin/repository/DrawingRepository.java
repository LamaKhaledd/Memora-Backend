package com.lin.repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.lin.entity.Drawing;

public interface DrawingRepository extends MongoRepository<Drawing, String> {

    Drawing findTopByOrderByIdDesc();
}
