package com.library.Library.Management.System.Challenge.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private int publicationYear;
    private String isbn;
    private String edition;

    @Column(length = 2000)
    private String summary;

    private String language;
    private String coverImageUrl;
    private boolean available = true;

    @ManyToMany
    @JoinTable(
            name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    @JsonIgnoreProperties("books") // ignore the 'books' field in Author to prevent recursion
    private Set<Author> authors = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    @JsonIgnoreProperties("books") // ignore books in Publisher
    private Publisher publisher;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties("books") // ignore books in Category
    private Category category;
}
