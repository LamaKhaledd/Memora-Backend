package com.lin.controller;
import org.springframework.web.bind.annotation.*;

import com.lin.service.PredictionService;

@RestController
@RequestMapping("/predict")
public class PredictionController {

    private final PredictionService predictionService;

    public PredictionController(PredictionService predictionService) {
        this.predictionService = predictionService;
    }

    @PostMapping
    public String getPrediction(@RequestBody String text) {
        return predictionService.predictCategory(text);
    }
}
