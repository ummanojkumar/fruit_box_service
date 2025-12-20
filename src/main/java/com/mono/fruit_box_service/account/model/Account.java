package com.mono.fruit_box_service.account.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;


@Entity
@Table(
        name = "accounts",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "google_id")
        }
)
@Data
public class Account {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String email;

    @Column(length = 100)
    private String name;

    @Column(name = "profile_image", length = 500)
    private String profileImage;

    @Column(name = "google_id", nullable = false)
    private String googleId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    private Instant lastLogin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountRole role;


}


