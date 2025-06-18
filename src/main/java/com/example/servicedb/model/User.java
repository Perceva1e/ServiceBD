    package com.example.servicedb.model;

    import jakarta.persistence.*;
    import lombok.Data;

    import java.util.List;

    @Entity
    @Table(name = "app_user")
    @Data
    public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String email;

        @Column(nullable = false)
        private String name;

        @Column(name = "hashed_password", nullable = false)
        private String hashedPassword;

        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<Review> reviews;
    }