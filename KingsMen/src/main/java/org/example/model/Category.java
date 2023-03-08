package org.example.model;



import lombok.Data;

import java.util.List;

import javax.persistence.*;


@Entity
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    private int id;

    private String name;

    @ManyToMany(cascade = CascadeType.MERGE,fetch = FetchType.EAGER )
    @JoinTable(
            name = "category_size",
            joinColumns = {@JoinColumn(name = "CATEGORY_ID", referencedColumnName = "category_id")},
            inverseJoinColumns = {@JoinColumn (name = "SIZE_ID", referencedColumnName = "ID")}
    )
    private List<Size> sizes;



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

