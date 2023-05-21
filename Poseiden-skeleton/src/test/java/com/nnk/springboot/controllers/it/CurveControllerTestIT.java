package com.nnk.springboot.controllers.it;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.*;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(JUnitPlatform.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CurveControllerTestIT {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private CurvePointRepository curvePointRepository;

    private MockMvc mockMvc;

    private CurvePoint curvePointTest = ajouterCurvePoint();

    private static CurvePoint ajouterCurvePoint() {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(1);
        curvePoint.setAsOfDate(LocalDateTime.now());
        curvePoint.setTerm(5.0);
        curvePoint.setValue(15.50);
        curvePoint.setCreationDate(LocalDateTime.now());
        return curvePoint;
    }
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        curvePointRepository.save(curvePointTest);
    }

    @AfterEach
    public void tearDown() {
        curvePointRepository.delete(curvePointTest);
    }

    @DisplayName("1°) Tentative de recupérer la liste des CurvePoint")
    @WithMockUser(username = "abel")
    @Order(1)
    @Test
    void testGetAllBidList() throws Exception {
        this.mockMvc
                .perform(get("/curvePoint/list"))
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attributeExists("curvPointList"))
                .andExpect(status().isOk());
    }

    @DisplayName("2°) Ajout d'un CurvePoint")
    @WithMockUser
    @Order(2)
    @Test
    void addCurvePointForm() throws Exception {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(1);
        curvePoint.setAsOfDate(LocalDateTime.now());
        curvePoint.setTerm(5.0);
        curvePoint.setValue(15.50);
        curvePoint.setCreationDate(LocalDateTime.now());

        this.mockMvc.perform(get("/curvePoint/add")
                        .flashAttr("curvePoint", curvePoint)
                        .flashAttr("success", true))
                .andExpect(model().attributeExists("curvPointList"))
                .andExpect(status().isOk());
    }

    @DisplayName("3°) Ajout d'un CurvePoint sans success")
    @WithMockUser
    @Order(3)
    @Test
    void testAddCurvePointFormShouldFail() throws Exception {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(1);
        curvePoint.setAsOfDate(LocalDateTime.now());
        curvePoint.setTerm(5.0);
        curvePoint.setValue(null);
        curvePoint.setCreationDate(LocalDateTime.now());

        this.mockMvc.perform(get("/curvePoint/add")
                        .flashAttr("curvePoint", curvePoint))
                .andExpect(model().attribute("curvePoint", curvePoint))
                .andExpect(view().name("curvePoint/add"))
                .andExpect(status().isOk());
    }

    @DisplayName("4°) Test de la validité d'un CurvePoint correct")
    @WithMockUser
    @Order(4)
    @Test
    void testValidate() throws Exception {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(1);
        curvePoint.setAsOfDate(LocalDateTime.now());
        curvePoint.setTerm(5.0);
        curvePoint.setValue(15.50);
        curvePoint.setCreationDate(LocalDateTime.now());

        this.mockMvc.perform(post("/curvePoint/validate").flashAttr("curvePoint", curvePoint))
                .andExpect(model().attribute("success", true))
                .andExpect(model().attribute("message", "CurvePoint crée avec succès"))
                .andExpect(model().attribute("curvePoint", curvePoint))
                .andExpect(view().name("curvePoint/list"))
                .andExpect(status().isCreated());
    }

    @DisplayName("5°) Test de la validité d'un CurvePoint incorrect")
    @WithMockUser
    @Order(5)
    @Test
    void testValidateWithError() throws Exception {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(1);
        curvePoint.setAsOfDate(LocalDateTime.now());
        curvePoint.setTerm(5.0);
        curvePoint.setValue(null);
        curvePoint.setCreationDate(LocalDateTime.now());

        this.mockMvc.perform(post("/curvePoint/validate").flashAttr("curvePoint", curvePoint))
                .andExpect(model().attribute("success", false))
                .andExpect(model().attributeExists("messageList"))
                .andExpect(model().attribute("curvePoint", curvePoint))
                .andExpect(view().name("curvePoint/add"))
                .andExpect(status().isOk());
    }

    @DisplayName("6°)  Tentative de recupérer un CurvePoint via son Id")
    @WithMockUser
    @Order(6)
    @Test
    void testShowUpdateForm() throws Exception {
        this.mockMvc
                .perform(get("/curvePoint/update/{id}", curvePointTest.getId()))
                .andExpect(model().attribute("success", true))
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeExists("curvePoint"))
                .andExpect(status().isOk());
    }

    @DisplayName("7°)  Tentative de recupérer un CurvePoint inexistant via son Id")
    @WithMockUser
    @Order(7)
    @Test
    void testShowUpdateFormShouldFail() throws Exception {
        this.mockMvc
                .perform(get("/curvePoint/update/{id}", 999))
                .andExpect(model().attribute("success", false))
                .andExpect(model().attribute("message", "Aucun CurvePoint n'a été trouvé avec l'id fourni"))
                .andExpect(view().name("curvePoint/list"))
                .andExpect(status().isOk());
    }

    @DisplayName("8°) Test update CurvePoinnt avec les champs correct")
    @WithMockUser
    @Order(8)
    @Test
    void testUpdateCurvePointShouldSuccess() throws Exception {
        CurvePoint curvePointUpdated = new CurvePoint();
        curvePointUpdated.setId(curvePointTest.getId());
        curvePointUpdated.setAsOfDate(LocalDateTime.now());
        curvePointUpdated.setTerm(30.0);
        curvePointUpdated.setValue(30.50);
        curvePointUpdated.setCreationDate(LocalDateTime.now());

        this.mockMvc.perform(post("/curvePoint/update/{id}", curvePointTest.getId()).flashAttr("curvePoint", curvePointUpdated))
                .andExpect(model().attribute("success", true))
                .andExpect(model().attribute("message", "CurvePoint mis à jour avec succès"))
                .andExpect(view().name("curvePoint/list"))
                .andExpect(status().isOk());
    }

    @DisplayName("9°) Test update CurvePoint avec un champ obligatoire incorrect/manquant")
    @WithMockUser
    @Order(9)
    @Test
    void testUpdateCurvePointShouldFail() throws Exception {
        CurvePoint curvePoint2 = new CurvePoint();
        curvePoint2.setCurveId(1);
        curvePoint2.setAsOfDate(LocalDateTime.now());
        curvePoint2.setTerm(5.0);
        curvePoint2.setValue(null);
        curvePoint2.setCreationDate(LocalDateTime.now());

        this.mockMvc.perform(post("/curvePoint/update/{id}", 1).flashAttr("curvePoint", curvePoint2))
                .andExpect(model().attribute("success", false))
                .andExpect(model().attributeExists("messageList"))
                .andExpect(view().name("curvePoint/update"))
                .andExpect(status().isOk());
    }

    @DisplayName("10°) Test delete CurvePoint par Id")
    @WithMockUser
    @Order(10)
    @Test
    void testDeleteCurvePointShouldSuccess() throws Exception {
        this.mockMvc.perform(get("/curvePoint/delete/{id}", curvePointTest.getId()))
                .andExpect(model().attribute("success", true))
                .andExpect(model().attribute("message", "CurvePoint avec id " + curvePointTest.getId() + " à été supprimé"))
                .andExpect(view().name("curvePoint/list"))
                .andExpect(status().isOk());
    }

    @DisplayName("11°) Test delete CurvePoint inexistant par Id")
    @WithMockUser
    @Order(11)
    @Test
    void testDeleteCurvePointShouldFail() throws Exception {
        List<String> errorMessageList = new ArrayList<>();
        errorMessageList.add("Une erreur est survenue lors de la suppression du CurvePoint");

        this.mockMvc.perform(get("/curvePoint/delete/{id}", 999))
                .andExpect(model().attribute("success", false))
                .andExpect(model().attributeExists("messageList"))
                .andExpect(model().attribute("messageList", errorMessageList))
                .andExpect(view().name("curvePoint/list"))
                .andExpect(status().isOk());
    }
}
