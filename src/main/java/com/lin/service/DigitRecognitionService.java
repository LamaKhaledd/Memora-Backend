package com.lin.service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;

@Service
public class DigitRecognitionService {

    private static final String PYTHON_SCRIPT_PATH = "D:/Downloadss/Projectt/Ai/17-1/handwritten_digit_recognition-main/python_script.py";

    public String predictDigit(MultipartFile file) throws IOException, InterruptedException {
        // Save the uploaded file to a temporary location
        File tempFile = File.createTempFile("uploaded_digit", ".png");
        file.transferTo(tempFile);

        // Build the Python process
        ProcessBuilder processBuilder = new ProcessBuilder("python3", PYTHON_SCRIPT_PATH, tempFile.getAbsolutePath());
        processBuilder.redirectErrorStream(true); // Merge stdout and stderr

        // Start the process
        Process process = processBuilder.start();

        // Capture output from Python script
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        // Wait for the process to complete
        int exitCode = process.waitFor();

        // Clean up temporary file
        tempFile.delete();

        if (exitCode == 0) {
            // Return the Python script's output
            return output.toString().trim();
        } else {
            throw new RuntimeException("Python script execution failed with exit code: " + exitCode);
        }
    }
}
