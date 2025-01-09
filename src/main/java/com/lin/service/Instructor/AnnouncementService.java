package com.lin.service.Instructor;

import com.lin.entity.Instructor.Announcement;
import com.lin.repository.Instructor.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    public Announcement createAnnouncement(Announcement announcement) {
        announcement.setCreatedAt(Instant.now().toString());
        return announcementRepository.save(announcement);
    }

    public List<Announcement> getAnnouncementsByClassroomId(String classroomId) {
        return announcementRepository.findByClassroomId(classroomId);
    }

    public Optional<Announcement> getAnnouncementById(String announcementId) {
        return announcementRepository.findById(announcementId);
    }

    public void deleteAnnouncement(String announcementId) {
        announcementRepository.deleteById(announcementId);
    }
}