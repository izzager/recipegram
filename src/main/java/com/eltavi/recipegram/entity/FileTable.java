package com.eltavi.recipegram.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Type(type="org.hibernate.type.BinaryType")
    @Column(columnDefinition="text")
    private String photoBlob;

    private Integer photoContentLength;

    private String photoContentType;

    private String photoName;
}
