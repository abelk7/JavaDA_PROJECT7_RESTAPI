package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "bidlist")
@Getter
@Setter
public class BidList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bidListId;
    private String account;
    private String type;
    private Double bidQuantity;
    private Double askQuantity;
    private Double bid;
    private Double ask;
    private String benchmark;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime bidListDate;
    private String commentary;
    private String security;
    private String status;
    private String trader;
    private String book;
    @NotNull
    @NotEmpty
    private String creationName;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime creationDate;
    private String revisionName;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime revisionDate;
    private String dealName;
    private String dealType;
    private String sourceListId;
    private String side;
}
