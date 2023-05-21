package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.impl.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class RatingServiceTest {
    private IRatingService ratingService;
    @Mock
    private RatingRepository ratingRepository;

    @BeforeEach
    public void setup() {
        ratingService = new RatingService(ratingRepository);
    }

    @DisplayName(value = "1°) Recherche de tous les Ratings")
    @Order(1)
    @Test
    void test_findAll_should_FindAll_Rating() {
        List<Rating> ratingList = new ArrayList<>();

        Rating rating1 = new Rating();
        Rating rating2 = new Rating();
        Rating rating3 = new Rating();
        Rating rating4 = new Rating();

        ratingList.add(rating1);
        ratingList.add(rating2);
        ratingList.add(rating3);
        ratingList.add(rating4);

        when(ratingRepository.findAll()).thenReturn(ratingList);

        List<Rating> result = ratingService.findAll();

        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(ratingList.size());
    }

    @DisplayName(value = "2°) Recherche de Rating par ID")
    @Order(2)
    @Test
    void test_findById_shoud_findRating_By_Id() {
        Rating rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("moddysTest");
        rating.setSandpRating("sandPTest");
        rating.setFitchRating("fitchTest");
        rating.setOrderNumber(1);

        when(ratingRepository.findById(anyInt())).thenReturn(Optional.of(rating));

        Rating ratingResult = ratingService.findById(rating.getId());

        assertThat(ratingResult).isNotNull();
        assertThat(ratingResult.getId()).isEqualTo(rating.getId());
    }

    @DisplayName(value = "3°) Mise à jour d'un Rating Existant")
    @Order(3)
    @Test
    void test_update_should_update_Rating() {
        Rating rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("moddysTest");
        rating.setSandpRating("sandPTest");
        rating.setFitchRating("fitchTest");
        rating.setOrderNumber(2);

        Rating ratingUpdated = new Rating();
        ratingUpdated.setId(1);
        ratingUpdated.setMoodysRating("moddysTestUpdated");
        ratingUpdated.setSandpRating("sandPTestUpdated");
        ratingUpdated.setFitchRating("fitchTestUpdated");
        ratingUpdated.setOrderNumber(3);

        when(ratingRepository.save(any(Rating.class))).thenReturn(ratingUpdated);

        Rating ratingResult = ratingService.save(rating);

        assertThat(ratingResult).isNotNull();
        assertThat(ratingResult.getId()).isEqualTo(ratingUpdated.getId());
        assertThat(ratingResult.getMoodysRating()).isEqualTo(ratingUpdated.getMoodysRating());
        assertThat(ratingResult.getSandpRating()).isEqualTo(ratingUpdated.getSandpRating());
        assertThat(ratingResult.getFitchRating()).isEqualTo(ratingUpdated.getFitchRating());
        assertThat(ratingResult.getOrderNumber()).isEqualTo(ratingUpdated.getOrderNumber());
    }

    @DisplayName(value = "4°) Suppression de Rating par ID")
    @Order(4)
    @Test
    void test_delete_shoud_deleteRating_By_Id() {
        Rating rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("moddysTest");
        rating.setSandpRating("sandPTest");
        rating.setFitchRating("fitchTest");
        rating.setOrderNumber(1);

        when(ratingRepository.findById(anyInt())).thenReturn(Optional.of(rating));

        boolean result = ratingService.deleteById(rating.getId());

        assertThat(result).isTrue();
    }
}
