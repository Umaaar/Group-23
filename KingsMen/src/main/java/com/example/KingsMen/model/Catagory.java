package com.example.KingsMen.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
    @Table(name = "catagory")
    public class Catagory {

        @Id
        @Column(name = "catagory_id")
        @GeneratedValue(strategy = GenerationType.AUTO)
        private int id;

        @Column(name = "catagory_name")
        private String name;

        @Column(name = "catagory_description")
        private String description;

       /* @OneToMany(fetch = FetchType.LAZY, mappedBy = "catagory", cascade = CascadeType.ALL)
        private Set<Product> product = new HashSet<>();

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Catagory category = (Catagory) o;
            return id == category.getId();
        }*/

    }

