package com.mono.fruit_box_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class MainController {

    @GetMapping("/pincode/check")
    public ResponseEntity<?> checkPincode(@RequestParam(name = "pincode") String pincode) {
        return ResponseEntity.ok(Map.of(
                "pincode", pincode,
                "serviceable", true,
                "deliveryChargePaise", 0,
                "cutoffTime", "20:00"
        ));
    }
}
