package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.IRatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RatingService implements IRatingService {
    private final RatingRepository ratingRepository;

    @Override
    public List<Rating> findAll() {
        log.info("Fetching all Rating");
        return ratingRepository.findAll();
    }

    @Override
    public Rating findById(Integer id) {
        log.info("Fetching Rating by Id in database");
        Optional<Rating> rating = ratingRepository.findById(id);
        if (rating.isPresent()) {
            return rating.get();
        }
        return null;
    }

    @Override
    public Rating save(Rating rating) {
        log.info("Saving Rating in database");
        return ratingRepository.save(rating);
    }

    @Override
    public boolean deleteById(Integer id) {
        log.info("deleting Rating by Id in database");
        Optional<Rating> rating = ratingRepository.findById(id);
        if (rating.isPresent()) {
            ratingRepository.delete(rating.get());
            return true;
        }
        return false;
    }
}
