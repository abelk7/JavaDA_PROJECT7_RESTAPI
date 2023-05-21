package com.nnk.springboot.controllers;

import com.nnk.springboot.config.SecurityConfig;
import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.IRatingService;
import com.nnk.springboot.services.IUserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(SecurityConfig.class)
@WebMvcTest(RatingController.class)
public class RatingControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IRatingService ratingService;
    @MockBean
    private IUserService userService;

    @DisplayName("1°) Tentative de recupérer la liste des Rating")
    @WithMockUser
    @Order(1)
    @Test
    void testGetAllRating() throws Exception {
        List<Rating> ratingList = new ArrayList<>();

        Rating rating1 = new Rating();
        Rating rating2 = new Rating();
        Rating rating3 = new Rating();

        ratingList.add(rating1);
        ratingList.add(rating2);
        ratingList.add(rating3);

        when(ratingService.findAll()).thenReturn(ratingList);

        this.mockMvc
                .perform(get("/rating/list"))
                .andExpect(view().name("rating/list"))
                .andExpect(model().attribute("ratingList", ratingList))
                .andExpect(status().isOk());
    }

    @DisplayName("2°) Ajout d'un Rating")
    @WithMockUser
    @Order(2)
    @Test
    void addRatingForm() throws Exception {
        Rating rating = new Rating();
        rating.setMoodysRating("moddysTest");
        rating.setSandpRating("sandPTest");
        rating.setFitchRating("fitchTest");
        rating.setOrderNumber(2);

        List<Rating> ratingList = new ArrayList<>();
        Rating rating2 = new Rating();
        Rating rating3 = new Rating();

        ratingList.add(rating2);
        ratingList.add(rating3);
        ratingList.add(rating);

        when(ratingService.findAll()).thenReturn(ratingList);

        this.mockMvc.perform(get("/rating/add")
                        .flashAttr("rating", rating)
                        .flashAttr("success", true))
                .andExpect(model().attribute("ratingList", ratingList))
                .andExpect(status().isOk());
    }

    @DisplayName("3°) Ajout d'un Rating sans success")
    @WithMockUser
    @Order(3)
    @Test
    void testAddRatingFormShouldFail() throws Exception {
        Rating rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating(null);
        rating.setSandpRating("sandPTest");
        rating.setFitchRating("fitchTest");
        rating.setOrderNumber(2);

        this.mockMvc.perform(get("/rating/add")
                        .flashAttr("rating", rating))
                .andExpect(model().attribute("rating", rating))
                .andExpect(view().name("rating/add"))
                .andExpect(status().isOk());

    }

    @DisplayName("4°) Test de la validité d'un Rating correct")
    @WithMockUser
    @Order(4)
    @Test
    void testValidate() throws Exception {
        Rating rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("moddysTest");
        rating.setSandpRating("sandPTest");
        rating.setFitchRating("fitchTest");
        rating.setOrderNumber(2);

        List<Rating> ratingList = new ArrayList<>();
        Rating rating2 = new Rating();
        Rating rating3 = new Rating();

        ratingList.add(rating2);
        ratingList.add(rating3);
        ratingList.add(rating);

        when(ratingService.findAll()).thenReturn(ratingList);

        this.mockMvc.perform(post("/rating/validate").flashAttr("rating", rating))
                .andExpect(model().attribute("success", true))
                .andExpect(model().attribute("message", "Rating crée avec succès"))
                .andExpect(model().attribute("rating", rating))
                .andExpect(view().name("rating/list"))
                .andExpect(status().isCreated());
    }

    @DisplayName("5°) Test de la validité d'un Rating incorrect")
    @WithMockUser
    @Order(5)
    @Test
    void testValidateWithError() throws Exception {
        Rating rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating(null);
        rating.setSandpRating("sandPTest");
        rating.setFitchRating("fitchTest");
        rating.setOrderNumber(2);

        this.mockMvc.perform(post("/rating/validate").flashAttr("rating", rating))
                .andExpect(model().attribute("success", false))
                .andExpect(model().attributeExists("messageList"))
                .andExpect(model().attribute("rating", rating))
                .andExpect(view().name("rating/add"))
                .andExpect(status().isOk());
    }

    @DisplayName("6°)  Tentative de recupérer un Rating via son Id")
    @WithMockUser
    @Order(6)
    @Test
    void testShowUpdateForm() throws Exception {
        Rating rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating(null);
        rating.setSandpRating("sandPTest");
        rating.setFitchRating("fitchTest");
        rating.setOrderNumber(2);

        when(ratingService.findById(anyInt())).thenReturn(rating);

        this.mockMvc
                .perform(get("/rating/update/{id}", 1))
                .andExpect(model().attribute("success", true))
                .andExpect(view().name("rating/update"))
                .andExpect(model().attribute("rating", rating))
                .andExpect(status().isOk());
    }

    @DisplayName("7°)  Tentative de recupérer un Rating inexistant via son Id")
    @WithMockUser
    @Order(7)
    @Test
    void testShowUpdateFormShouldFail() throws Exception {
        when(ratingService.findById(anyInt())).thenReturn(null);

        this.mockMvc
                .perform(get("/rating/update/{id}", 1))
                .andExpect(model().attribute("success", false))
                .andExpect(model().attribute("message", "Aucun Rating n'a été trouvé avec l'id fourni"))
                .andExpect(view().name("rating/list"))
                .andExpect(status().isOk());
    }

    @DisplayName("8°) Test update Rating avec les champs correct")
    @WithMockUser
    @Order(8)
    @Test
    void testUpdateRatingShouldSuccess() throws Exception {
        Rating rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("moodysRatingTest");
        rating.setSandpRating("sandPTest");
        rating.setFitchRating("fitchTest");
        rating.setOrderNumber(2);

        Rating rating2 = new Rating();
        rating2.setId(1);
        rating2.setMoodysRating("moodysRatingTestUpdated");
        rating2.setSandpRating("sandPTestUpdated");
        rating2.setFitchRating("fitchTestUpdated");
        rating2.setOrderNumber(2);

        when(ratingService.findById(anyInt())).thenReturn(rating);

        this.mockMvc.perform(post("/rating/update/{id}", 1).flashAttr("rating", rating2))
                .andExpect(model().attribute("success", true))
                .andExpect(model().attribute("message", "Rating mis à jour avec succès"))
                .andExpect(view().name("rating/list"))
                .andExpect(status().isOk());
    }

    @DisplayName("9°) Test update Rating avec un champ obligatoire incorrect/manquant")
    @WithMockUser
    @Order(9)
    @Test
    void testUpdateRatingShouldFail() throws Exception {
        Rating rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("moodysRatingTest");
        rating.setSandpRating("sandPTest");
        rating.setFitchRating("fitchTest");
        rating.setOrderNumber(2);

        Rating rating2 = new Rating();
        rating2.setId(1);
        rating2.setMoodysRating(null);
        rating2.setSandpRating("sandPTestUpdated");
        rating2.setFitchRating("fitchTestUpdated");
        rating2.setOrderNumber(2);

        when(ratingService.findById(anyInt())).thenReturn(rating);

        this.mockMvc.perform(post("/rating/update/{id}", 1).flashAttr("rating", rating2))
                .andExpect(model().attribute("success", false))
                .andExpect(model().attributeExists("messageList"))
                .andExpect(view().name("rating/update"))
                .andExpect(status().isOk());
    }

    @DisplayName("10°) Test delete Rating par Id")
    @WithMockUser
    @Order(10)
    @Test
    void testDeleteRatingShouldSuccess() throws Exception {
        Rating rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("moodysRatingTest");
        rating.setSandpRating("sandPTest");
        rating.setFitchRating("fitchTest");
        rating.setOrderNumber(2);

        when(ratingService.deleteById(anyInt())).thenReturn(true);

        this.mockMvc.perform(get("/rating/delete/{id}", 1))
                .andExpect(model().attribute("success", true))
                .andExpect(model().attribute("message", "Rating avec id 1 à été supprimé"))
                .andExpect(view().name("rating/list"))
                .andExpect(status().isOk());
    }

    @DisplayName("11°) Test delete Rating inexistant par Id")
    @WithMockUser
    @Order(11)
    @Test
    void testDeleteRatingShouldFail() throws Exception {
        List<String> errorMessageList = new ArrayList<>();
        errorMessageList.add("Une erreur est survenue lors de la suppression du Rating");
//
//        Rating rating = new Rating();
//        rating.setId(1);
//        rating.setMoodysRating("moodysRatingTest");
//        rating.setSandPRating("sandPTest");
//        rating.setFitchRating("fitchTest");
//        rating.setOrderNumber(2);

        when(ratingService.deleteById(anyInt())).thenReturn(false);

        this.mockMvc.perform(get("/rating/delete/{id}", 999))
                .andExpect(model().attribute("success", false))
                .andExpect(model().attributeExists("messageList"))
                .andExpect(model().attribute("messageList", errorMessageList))
                .andExpect(view().name("rating/list"))
                .andExpect(status().isOk());
    }
}
