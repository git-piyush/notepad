package com.notepad.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="tbl_article")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article implements Serializable {
    private static final long serialVersionUID = 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    //@Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    private Date publication_date;

    private String status;

    @Transient
    private Long categoryId;

    @Transient
    private String categoryName;
}
