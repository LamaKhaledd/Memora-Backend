package com.lin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lin.entity.Drawing;
import com.lin.repository.DrawingRepository;

@RestController
@RequestMapping("/drawings")
public class DrawingController {

    @Autowired
    private DrawingRepository drawingRepository;

    @PostMapping
    public ResponseEntity<?> saveDrawing(@RequestBody Drawing drawing) {
        Drawing savedDrawing = drawingRepository.save(drawing);
        return ResponseEntity.ok(savedDrawing);
    }

    @GetMapping
    public ResponseEntity<?> getAllDrawings() {
        return ResponseEntity.ok(drawingRepository.findAll());
    }

    @GetMapping("/last")
    public ResponseEntity<?> getLastDrawing() {
        Drawing lastDrawing = drawingRepository.findTopByOrderByIdDesc(); 
        if (lastDrawing != null) {
            return ResponseEntity.ok(lastDrawing);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
