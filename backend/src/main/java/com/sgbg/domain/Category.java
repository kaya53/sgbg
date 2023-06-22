package com.sgbg.domain;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "Category")
@DynamicUpdate
@DynamicInsert
public class Category {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "name")
    private String categoryName;

    @Column(name = "parent_id")
    @ColumnDefault(value = "null")
    private Long parentId;
}
