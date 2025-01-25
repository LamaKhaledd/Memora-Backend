package com.lin.controller.Instructor;


import com.lin.entity.Instructor.Announcement;
import com.lin.service.Instructor.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classrooms")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @PostMapping("/{classroomId}/announcements")
    public ResponseEntity<Announcement> createAnnouncement(
            @PathVariable String classroomId,
            @RequestBody Announcement announcement) {
        announcement.setClassroomId(classroomId);
        Announcement createdAnnouncement = announcementService.createAnnouncement(announcement);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAnnouncement);
    }

    @GetMapping("/{classroomId}/announcements")
    public ResponseEntity<List<Announcement>> getAnnouncementsByClassroomId(@PathVariable String classroomId) {
        List<Announcement> announcements = announcementService.getAnnouncementsByClassroomId(classroomId);
        return ResponseEntity.ok(announcements);
    }

    @DeleteMapping("/announcements/{announcementId}")
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable String announcementId) {
        announcementService.deleteAnnouncement(announcementId);
        return ResponseEntity.noContent().build();
    }
}

