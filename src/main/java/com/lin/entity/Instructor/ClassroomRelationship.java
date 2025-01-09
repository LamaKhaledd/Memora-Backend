package com.lin.entity.Instructor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "classroom_relationships")
public class ClassroomRelationship {
    @Id
    private String id;  // Relationship ID (optional, can auto-generate)
    private String classroomId;  // Classroom ID
    private String instructorId;  // Instructor ID
    private List<String> studentIds;  // List of Student IDs
}
