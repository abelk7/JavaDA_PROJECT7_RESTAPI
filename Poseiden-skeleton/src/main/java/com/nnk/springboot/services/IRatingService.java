package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;

import java.util.List;

public interface IRatingService {
    List<Rating> findAll();

    Rating findById(Integer id);

    Rating save(Rating user);

    boolean deleteById(Integer id);
}
