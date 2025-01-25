package com.lin.service.Instructor;

import com.lin.entity.Instructor.Meeting;
import com.lin.repository.Instructor.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;


@Service
public class MeetingService {

    @Autowired
    private MeetingRepository meetingRepository;

    public Meeting scheduleMeeting(Meeting meeting) {
        meeting.setCreatedAt(Instant.now().toString());
        meeting.setStatus("Scheduled");
        return meetingRepository.save(meeting);
    }

    public List<Meeting> getMeetingsByClassroomId(String classroomId) {
        return meetingRepository.findByClassroomId(classroomId);
    }

    public Optional<Meeting> getMeetingById(String meetingId) {
        return meetingRepository.findById(meetingId);
    }

    public void updateMeetingStatus(String meetingId, String status) {
        Optional<Meeting> optionalMeeting = meetingRepository.findById(meetingId);
        if (optionalMeeting.isPresent()) {
            Meeting meeting = optionalMeeting.get();
            meeting.setStatus(status);
            meetingRepository.save(meeting);
        }
    }

    public void deleteMeeting(String meetingId) {
        meetingRepository.deleteById(meetingId);
    }
}