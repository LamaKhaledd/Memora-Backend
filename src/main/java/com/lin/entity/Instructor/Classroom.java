package com.lin.entity.Instructor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "classrooms")
public class Classroom {
    @Id
    private String classroomId; // Unique ID for each classroom
    private String name;
    private String description;
    private String instructorId; // Instructor who created the classroom
    private String invitationCode; // Unique code for inviting students
    private String createdAt; // Timestamp of when the classroom was created
    private int maxStudents; // Maximum number of students allowed (e.g., 20)
    private int currentStudents; // Current number of students enrolled in the classroom
    private boolean isOngoing; // Represents whether the class is ongoing
}
