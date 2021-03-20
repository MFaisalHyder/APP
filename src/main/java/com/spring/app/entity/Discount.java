package com.spring.app.entity;

import com.spring.app.constant.BookTypes;
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
import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "PromoCode", nullable = false, updatable = false)
    private String promoCode;

    @Column(name = "Percentage", nullable = false, updatable = false)
    private Double percentage;

    @Column(name = "Type", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private BookTypes type;

    @Column(name = "Validity", nullable = false)
    private LocalDate validity; //yyyy-MM-dd

}