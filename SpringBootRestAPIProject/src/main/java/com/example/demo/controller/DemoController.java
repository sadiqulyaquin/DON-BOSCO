package com.example.demo.controller;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DemoController {

    @GetMapping("/remove")
    public ResponseEntity<String> removeFirstAndLastChar(@RequestParam String original) {
        if (original == null || original.length() < 2) {
            return ResponseEntity.badRequest().body("Input must be at least 2 characters long.");
        }
        if (original.length() == 2) {
            return ResponseEntity.ok("");
        }
        String result = original.substring(1, original.length() - 1);
        return ResponseEntity.ok(result);
    }
}
