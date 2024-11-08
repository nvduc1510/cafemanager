package com.example.BackEnd.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "collections")
public class Collections {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "collection_id")
    private Integer collectionId;

    @Column(name = "collection_name")
    private String collectionName;

    @Column(name = "collection_description")
    private String collectionDescription;

    public Collections(Integer collectionId) {
        this.collectionId = collectionId;
    }
}

