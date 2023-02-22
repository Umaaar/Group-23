package com.example.KingsMen.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.Data;
@Entity
@Data

public class Sizes {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
@Column(name = "size_id")
private Long id;
private String size;
@ManyToOne(fetch =  FetchType.LAZY)
@JoinColumn(name = "category_id", referencedColumnName = "category_id")
private Category category; // this is the category that the size belongs to;

public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public String getSize() {
    return size;
}

public void setSize(String size) {
    this.size = size;
}


public Category getCategory() {
    return category;
}

public void setCategory(Category category) {
    this.category = category;
}
}

