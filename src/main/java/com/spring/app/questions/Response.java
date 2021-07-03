package com.spring.app.questions;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter(AccessLevel.PRIVATE)
public class Response {

    private List<Data> data;
    private Links links;
    private Meta meta;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter(AccessLevel.PRIVATE)
    public static class Data {
        private String type;
        private Attributes attributes;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter(AccessLevel.PRIVATE)
    public static class Attributes {
        private String link;
        private Integer signed;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter(AccessLevel.PRIVATE)
    public static class Links {
        private String self;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter(AccessLevel.PRIVATE)
    public static class Meta {
        private Integer total;
    }

}