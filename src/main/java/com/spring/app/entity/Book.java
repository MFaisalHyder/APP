package com.spring.app.entity;

import com.spring.app.constant.BookTypes;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", nullable = false, updatable = false)
    @ApiModelProperty(notes = "The database generated Book ID")
    private Long id;

    @Column(name = "ISBN", nullable = false, updatable = false)
    @ApiModelProperty(notes = "ISBN of the Book", required = true)
    private String isbn;

    @Column(name = "Name", nullable = false, updatable = false)
    @ApiModelProperty(notes = "Title of the Book", required = true)
    private String name;

    @Column(name = "Description")
    @ApiModelProperty(notes = "Description of the Book")
    private String description;

    @Column(name = "Author", nullable = false, updatable = false)
    @ApiModelProperty(notes = "Author of the Book", required = true)
    private String author;

    @Column(name = "Type", nullable = false, updatable = false)
    @ApiModelProperty(notes = "Genre of the Book", required = true)
    @Enumerated(EnumType.STRING)
    private BookTypes type;

    @Column(name = "Price", nullable = false)
    @ApiModelProperty(notes = "Price of the Book", required = true)
    private BigDecimal price;

}