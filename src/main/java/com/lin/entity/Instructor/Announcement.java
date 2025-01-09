package com.lin.entity.Instructor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "announcements")
public class Announcement {
    @Id
    private String announcementId; // Unique ID for the announcement
    private String classroomId; // ID of the classroom this announcement belongs to
    private String title; // Title of the announcement
    private String date; // Date of the meeting or announcement
    private String time; // Time of the meeting or announcement
    private String details; // Optional details for the announcement
    private String createdAt; // Timestamp of when the announcement was created
}
