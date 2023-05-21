package com.nnk.springboot.controllers.it;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.*;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(JUnitPlatform.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RatingControllerTestIT {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private RatingRepository ratingRepository;
    private MockMvc mockMvc;
    private Rating ratingTest = ajouterRating();

    private static Rating ajouterRating() {
        Rating rating = new Rating();
        rating.setMoodysRating("moodyRatingTest");
        rating.setSandpRating("sandPTest");
        rating.setFitchRating("fitchTest");
        rating.setOrderNumber(2);
        return rating;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        ratingRepository.save(ratingTest);
    }

    @AfterEach
    public void tearDown() {
        ratingRepository.delete(ratingTest);
    }

    @DisplayName("1°) Tentative de recupérer la liste des Rating")
    @WithMockUser
    @Order(1)
    @Test
    void testGetAllRating() throws Exception {
        this.mockMvc
                .perform(get("/rating/list"))
                .andExpect(view().name("rating/list"))
                .andExpect(model().attributeExists("ratingList"))
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

        this.mockMvc.perform(get("/rating/add")
                        .flashAttr("rating", rating)
                        .flashAttr("success", true))
                .andExpect(model().attributeExists("ratingList"))
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
                .andExpect(model().attribute("success", false))
                .andExpect(model().attributeExists("rating"))
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

        this.mockMvc.perform(post("/rating/validate").flashAttr("rating", rating))
                .andExpect(model().attribute("success", true))
                .andExpect(model().attribute("message", "Rating crée avec succès"))
                .andExpect(model().attributeExists("rating"))
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
                .andExpect(model().attributeExists("rating"))
                .andExpect(view().name("rating/add"))
                .andExpect(status().isOk());
    }

    @DisplayName("6°)  Tentative de recupérer un Rating via son Id")
    @WithMockUser
    @Order(6)
    @Test
    void testShowUpdateForm() throws Exception {
        this.mockMvc
                .perform(get("/rating/update/{id}", ratingTest.getId()))
                .andExpect(model().attribute("success", true))
                .andExpect(view().name("rating/update"))
                .andExpect(model().attributeExists("rating"))
                .andExpect(status().isOk());
    }

    @DisplayName("7°)  Tentative de recupérer un Rating inexistant via son Id")
    @WithMockUser
    @Order(7)
    @Test
    void testShowUpdateFormShouldFail() throws Exception {
        this.mockMvc
                .perform(get("/rating/update/{id}", 999))
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
        Rating rating2 = new Rating();
        rating2.setId(ratingTest.getId());
        rating2.setMoodysRating("moodysRatingTestUpdated");
        rating2.setSandpRating("sandPTestUpdated");
        rating2.setFitchRating("fitchTestUpdated");
        rating2.setOrderNumber(2);

        this.mockMvc.perform(post("/rating/update/{id}", ratingTest.getId()).flashAttr("rating", rating2))
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
        Rating rating2 = new Rating();
        rating2.setId(ratingTest.getId());
        rating2.setMoodysRating(null);
        rating2.setSandpRating("sandPTestUpdated");
        rating2.setFitchRating("fitchTestUpdated");
        rating2.setOrderNumber(2);

        this.mockMvc.perform(post("/rating/update/{id}", ratingTest.getId()).flashAttr("rating", rating2))
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

        this.mockMvc.perform(get("/rating/delete/{id}", ratingTest.getId()))
                .andExpect(model().attribute("success", true))
                .andExpect(model().attribute("message", "Rating avec id " + ratingTest.getId()  + " à été supprimé"))
                .andExpect(view().name("rating/list"))
                .andExpect(status().isOk());
    }

    @DisplayName("11°) Test delete Rating inexistant par Id")
    @WithMockUser
    @Order(11)
    @Test
    void testDeleteRatingShouldFail() throws Exception {

        this.mockMvc.perform(get("/rating/delete/{id}", 999))
                .andExpect(model().attribute("success", false))
                .andExpect(model().attributeExists("messageList"))
                .andExpect(view().name("rating/list"))
                .andExpect(status().isOk());
    }
}
