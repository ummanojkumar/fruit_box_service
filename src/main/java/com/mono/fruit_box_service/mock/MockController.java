package com.mono.fruit_box_service.mock;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mock/api")
@CrossOrigin("*")
public class MockController {

    // ================================
    // FRUITS
    // ================================
    @GetMapping("/fruits")
    public ResponseEntity<?> getFruits() {
        return ResponseEntity.ok(List.of(
                Map.of("id", 1, "name", "Apple", "imageUrl", "/apple.png", "available", true),
                Map.of("id", 2, "name", "Banana", "imageUrl", "/banana.png", "available", true),
                Map.of("id", 3, "name", "Grapes", "imageUrl", "/grapes.png", "available", true)
        ));
    }

    // ================================
    // PLANS
    // ================================
    @GetMapping("/box-plans")
    public ResponseEntity<?> getPlans() {
        return ResponseEntity.ok(List.of(
                Map.of("id", 1, "code", "5DAY", "title", "5 Days Plan", "pricePaise", 149900),
                Map.of("id", 2, "code", "6DAY", "title", "6 Days Plan", "pricePaise", 179900),
                Map.of("id", 3, "code", "7DAY", "title", "7 Days Plan", "pricePaise", 219900)
        ));
    }

    // ================================
    // ORDERS
    // ================================
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable String orderId) {
        return ResponseEntity.ok(Map.of(
                "orderId", orderId,
                "status", "PAID",
                "items", List.of(
                        Map.of("fruit", "Apple", "qty", 2),
                        Map.of("fruit", "Banana", "qty", 4)
                )
        ));
    }

    // ================================
    // SUBSCRIPTIONS
    // ================================
    @GetMapping("/subscriptions")
    public ResponseEntity<?> getSubscriptions() {
        return ResponseEntity.ok(List.of(
                Map.of(
                        "subscriptionId", "mock-sub-id",
                        "planCode", "5DAY",
                        "status", "ACTIVE",
                        "nextBillingDate", "2025-02-01"
                )
        ));
    }

    // ================================
    // ADDRESSES
    // ================================
    @GetMapping("/user/addresses")
    public ResponseEntity<?> getAddresses() {
        return ResponseEntity.ok(List.of(
                Map.of(
                        "id", "mock-address-1",
                        "line1", "Street 1",
                        "city", "Chennai",
                        "pincode", "600119",
                        "isDefault", true
                )
        ));
    }

    // ================================
    // REWARDS WALLET
    // ================================
    @GetMapping("/rewards/wallet")
    public ResponseEntity<?> getWallet() {
        return ResponseEntity.ok(Map.of(
                "balancePaise", 5000
        ));
    }

    // ================================
    // AUTH
    // ================================
    @PostMapping("/auth/signup")
    public ResponseEntity<?> signup(@RequestBody Map<String, Object> req) {
        return ResponseEntity.ok(Map.of(
                "userId", "mock-user-id",
                "email", req.get("email"),
                "token", "mock-jwt-token"
        ));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody Map<String, Object> req) {
        return ResponseEntity.ok(Map.of(
                "accessToken", "mock-access-token",
                "refreshToken", "mock-refresh-token"
        ));
    }

    // ================================
    // PINCODE CHECK
    // ================================
    @GetMapping("/pincode/check")
    public ResponseEntity<?> checkPincode(@RequestParam(name = "pincode") String pincode) {
        return ResponseEntity.ok(Map.of(
                "pincode", pincode,
                "serviceable", true,
                "deliveryChargePaise", 0,
                "cutoffTime", "20:00"
        ));
    }

    // ================================
    // ORDERS
    // ================================
    @PostMapping("/orders/draft")
    public ResponseEntity<?> createDraft(@RequestBody Map<String, Object> req) {
        return ResponseEntity.ok(Map.of(
                "draftId", "mock-draft-id",
                "amountPaise", 149900,
                "razorpayOrderId", "mock-razorpay-order-id"
        ));
    }

    // ================================
    // ADDRESSES
    // ================================
    @PostMapping("/user/addresses")
    public ResponseEntity<?> addAddress(@RequestBody Map<String, Object> req) {
        return ResponseEntity.ok(
                Map.of(
                        "id", "generated-address-id",
                        "line1", req.get("line1"),
                        "city", req.get("city"),
                        "pincode", req.get("pincode"),
                        "isDefault", false
                )
        );
    }

    // ================================
    // PAYMENTS
    // ================================
    @PostMapping("/payments/complete")
    public ResponseEntity<?> completePayment(@RequestBody Map<String, Object> req) {
        return ResponseEntity.ok(Map.of(
                "orderId", req.get("orderId"),
                "paymentStatus", "SUCCESS"
        ));
    }
}

