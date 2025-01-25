package com.lin.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
class Sketch {
    private List<Point> points;
    private String color;
    private double size;
    private boolean filled;
    private String type;
    private int sides;

}