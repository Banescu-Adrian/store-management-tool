package com.store.store.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.store.store.entities.Inventory;
import com.store.store.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @JsonIgnore
    @OneToMany(mappedBy = "product", orphanRemoval = true)
    private Set<Inventory> inventory = new LinkedHashSet<>();

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "created_by", nullable = false)
    private User user;
}
