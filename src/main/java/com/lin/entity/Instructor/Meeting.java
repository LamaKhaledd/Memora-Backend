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
@Document(collection = "meetings")
public class Meeting {
    @Id
    private String meetingId; // Unique ID for the meeting
    private String classroomId; // ID of the classroom this meeting is associated with
    private String link; // Unique meeting link
    private String status; // Status of the meeting (Scheduled, Live, Ended)
    private List<String> waitingRoom; // List of student IDs waiting to join
    private List<String> attendees; // List of student IDs who joined the meeting
    private String createdAt; // Timestamp of when the meeting was created
    private String startTime; // Scheduled start time of the meeting
    private String endTime; // Actual end time of the meeting
}
