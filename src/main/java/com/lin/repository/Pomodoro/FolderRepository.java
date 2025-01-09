package com.lin.repository.Pomodoro;

import com.lin.entity.Pomodoro.Folder;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FolderRepository extends MongoRepository<Folder, String> {
}
