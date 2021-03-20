package com.spring.app.dto;

import com.spring.app.constant.BookTypes;
import com.spring.app.validator.ValidateEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Getter
@Setter
@RequiredArgsConstructor
public class BookDTO {
    private Long id;

    @NotBlank(message = "ISBN is required")
    @Pattern(regexp = "^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$", message = "Invalid format")
    private String isbn;

    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    @NotBlank(message = "Author is required")
    private String author;

    @ValidateEnum(targetClassType = BookTypes.class, message = "Invalid BookType")
    private String type;

    @Range(min = 0, max = 1000, message = "Price is required")
    private BigDecimal price;

}