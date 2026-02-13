package com.inventoryapi.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import java.util.List;

@Entity
public class Group {
    public Group(String name, List<Article> articulos) {
        this.name = name;
        this.articulos = articulos;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    /* 1:N RELATION
       - the article class has the group attribute, beign the relation owner
    */
    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Article> articulos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Article> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<Article> articulos) {
        this.articulos = articulos;
    }

    public Group() {

    }
}