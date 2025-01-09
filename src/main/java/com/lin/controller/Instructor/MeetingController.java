package com.lin.controller.Instructor;

import com.lin.entity.Instructor.Meeting;
import com.lin.service.Instructor.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classrooms")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @PostMapping("/{classroomId}/meetings")
    public ResponseEntity<Meeting> scheduleMeeting(
            @PathVariable String classroomId,
            @RequestBody Meeting meeting) {
        meeting.setClassroomId(classroomId);
        Meeting scheduledMeeting = meetingService.scheduleMeeting(meeting);
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduledMeeting);
    }

    @GetMapping("/{classroomId}/meetings")
    public ResponseEntity<List<Meeting>> getMeetingsByClassroomId(@PathVariable String classroomId) {
        List<Meeting> meetings = meetingService.getMeetingsByClassroomId(classroomId);
        return ResponseEntity.ok(meetings);
    }

    @PatchMapping("/meetings/{meetingId}/status")
    public ResponseEntity<Void> updateMeetingStatus(
            @PathVariable String meetingId,
            @RequestParam String status) {
        meetingService.updateMeetingStatus(meetingId, status);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/meetings/{meetingId}")
    public ResponseEntity<Void> deleteMeeting(@PathVariable String meetingId) {
        meetingService.deleteMeeting(meetingId);
        return ResponseEntity.noContent().build();
    }
}
