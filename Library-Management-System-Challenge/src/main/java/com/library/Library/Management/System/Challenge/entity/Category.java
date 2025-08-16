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
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonIgnoreProperties({"subCategories", "books", "parentCategory"})
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory")
    @JsonIgnoreProperties("parentCategory")
    private Set<Category> subCategories = new HashSet<>();

    @OneToMany(mappedBy = "category")
    @JsonIgnoreProperties({"category", "authors", "publisher"})
    private Set<Book> books = new HashSet<>();
}
