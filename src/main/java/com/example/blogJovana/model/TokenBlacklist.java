package com.example.blogJovana.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "token_blacklist")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TokenBlacklist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", nullable = false)
    private String token;

}
