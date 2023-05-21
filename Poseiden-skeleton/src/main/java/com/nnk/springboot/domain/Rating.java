package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "rating")
@NoArgsConstructor
@Getter
@Setter
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty
    @Column(name = "moodys_rating")
    private String moodysRating;
    @Column(name = "sandp_rating")
    private String sandpRating;
    @Column(name = "fitch_rating")
    private String fitchRating;
    @Column(name = "order_number")
    private Integer orderNumber;
}
