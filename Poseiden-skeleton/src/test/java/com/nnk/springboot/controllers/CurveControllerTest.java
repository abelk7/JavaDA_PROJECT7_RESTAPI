package com.nnk.springboot.controllers;

import com.nnk.springboot.config.SecurityConfig;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.ICurvePointService;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(SecurityConfig.class)
@WebMvcTest(CurveController.class)
public class CurveControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ICurvePointService curvePointService;
    @MockBean
    private IUserService userService;

    @DisplayName("1°) Tentative de recupérer la liste des CurvePoint")
    @WithMockUser
    @Order(1)
    @Test
    void testGetAllBidList() throws Exception {
        List<CurvePoint> curvePointList = new ArrayList<>();

        CurvePoint bidList1 = new CurvePoint();
        CurvePoint bidList2 = new CurvePoint();
        CurvePoint bidList3 = new CurvePoint();

        curvePointList.add(bidList1);
        curvePointList.add(bidList2);
        curvePointList.add(bidList3);

        when(curvePointService.findAll()).thenReturn(curvePointList);

        this.mockMvc
                .perform(get("/curvePoint/list"))
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attribute("curvPointList", curvePointList))
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

        List<CurvePoint> curvePointList = new ArrayList<>();
        CurvePoint curvePoint1 = new CurvePoint();
        CurvePoint curvePoint2 = new CurvePoint();

        curvePointList.add(curvePoint1);
        curvePointList.add(curvePoint2);
        curvePointList.add(curvePoint);

        when(curvePointService.findAll()).thenReturn(curvePointList);

        this.mockMvc.perform(get("/curvePoint/add")
                        .flashAttr("curvePoint", curvePoint)
                        .flashAttr("success", true))
                .andExpect(model().attribute("curvPointList", curvePointList))
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

        List<CurvePoint> curvePointList = new ArrayList<>();
        CurvePoint curvePoint1 = new CurvePoint();
        CurvePoint curvePoint2 = new CurvePoint();

        curvePointList.add(curvePoint1);
        curvePointList.add(curvePoint2);
        curvePointList.add(curvePoint);

        when(curvePointService.findAll()).thenReturn(curvePointList);

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

        List<CurvePoint> curvePointList = new ArrayList<>();
        CurvePoint curvePoint1 = new CurvePoint();
        CurvePoint curvePoint2 = new CurvePoint();

        curvePointList.add(curvePoint1);
        curvePointList.add(curvePoint2);

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
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(1);
        curvePoint.setAsOfDate(LocalDateTime.now());
        curvePoint.setTerm(5.0);
        curvePoint.setValue(15.50);
        curvePoint.setCreationDate(LocalDateTime.now());

        when(curvePointService.findById(anyInt())).thenReturn(curvePoint);

        this.mockMvc
                .perform(get("/curvePoint/update/{id}", 1))
                .andExpect(model().attribute("success", true))
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attribute("curvePoint", curvePoint))
                .andExpect(status().isOk());
    }

    @DisplayName("7°)  Tentative de recupérer un CurvePoint inexistant via son Id")
    @WithMockUser
    @Order(7)
    @Test
    void testShowUpdateFormShouldFail() throws Exception {
        when(curvePointService.findById(anyInt())).thenReturn(null);

        this.mockMvc
                .perform(get("/curvePoint/update/{id}", 1))
                .andExpect(model().attribute("success", false))
                .andExpect(model().attribute("message", "Aucun CurvePoint n'a été trouvé avec l'id fourni"))
                .andExpect(view().name("curvePoint/list"))
                .andExpect(status().isOk());
    }

    @DisplayName("8°) Test update CurvePoint avec les champs correct")
    @WithMockUser
    @Order(8)
    @Test
    void testUpdateCurvePointShouldSuccess() throws Exception {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(1);
        curvePoint.setAsOfDate(LocalDateTime.now());
        curvePoint.setTerm(5.0);
        curvePoint.setValue(15.50);
        curvePoint.setCreationDate(LocalDateTime.now());

        CurvePoint curvePointUpdated = new CurvePoint();
        curvePointUpdated.setCurveId(1);
        curvePointUpdated.setAsOfDate(LocalDateTime.now());
        curvePointUpdated.setTerm(30.0);
        curvePointUpdated.setValue(30.50);
        curvePointUpdated.setCreationDate(LocalDateTime.now());

        when(curvePointService.findById(anyInt())).thenReturn(curvePoint);

        this.mockMvc.perform(post("/curvePoint/update/{id}", 1).flashAttr("curvePoint", curvePointUpdated))
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
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(1);
        curvePoint.setAsOfDate(LocalDateTime.now());
        curvePoint.setTerm(5.0);
        curvePoint.setValue(15.50);
        curvePoint.setCreationDate(LocalDateTime.now());

        CurvePoint curvePoint2 = new CurvePoint();
        curvePoint2.setCurveId(1);
        curvePoint2.setAsOfDate(LocalDateTime.now());
        curvePoint2.setTerm(5.0);
        curvePoint2.setValue(null);
        curvePoint2.setCreationDate(LocalDateTime.now());

        when(curvePointService.findById(anyInt())).thenReturn(curvePoint);

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
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(1);
        curvePoint.setAsOfDate(LocalDateTime.now());
        curvePoint.setTerm(5.0);
        curvePoint.setValue(15.50);
        curvePoint.setCreationDate(LocalDateTime.now());

        when(curvePointService.deleteById(anyInt())).thenReturn(true);

        this.mockMvc.perform(get("/curvePoint/delete/{id}", 1))
                .andExpect(model().attribute("success", true))
                .andExpect(model().attribute("message", "CurvePoint avec id 1 à été supprimé"))
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

        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(1);
        curvePoint.setAsOfDate(LocalDateTime.now());
        curvePoint.setTerm(5.0);
        curvePoint.setValue(15.50);

        when(curvePointService.deleteById(anyInt())).thenReturn(false);

        this.mockMvc.perform(get("/curvePoint/delete/{id}", 999))
                .andExpect(model().attribute("success", false))
                .andExpect(model().attributeExists("messageList"))
                .andExpect(model().attribute("messageList", errorMessageList))
                .andExpect(view().name("curvePoint/list"))
                .andExpect(status().isOk());
    }
}
