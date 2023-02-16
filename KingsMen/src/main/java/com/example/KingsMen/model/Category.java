package com.example.KingsMen.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


    @Data
    @Entity
    @Table(name = "category")
    public class Category {

        @Column(name = "id")
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;
        
        @Column(name = "category_name")
        private String categoryName;

        @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.ALL)
        private Set<Product> product = new HashSet<>();

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Category category = (Category) o;
            return id == category.getId();
        }

    }

