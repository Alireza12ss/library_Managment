package com.example.library.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "book_groups")
public class BookGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books;
}
