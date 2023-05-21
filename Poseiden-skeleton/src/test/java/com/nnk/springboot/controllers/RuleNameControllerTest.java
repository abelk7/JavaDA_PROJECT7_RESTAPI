package com.nnk.springboot.controllers;

import com.nnk.springboot.config.SecurityConfig;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.IRatingService;
import com.nnk.springboot.services.IRuleNameService;
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
@WebMvcTest(RuleNameController.class)
public class RuleNameControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IRuleNameService ruleNameService;
    @MockBean
    private IUserService userService;

    @DisplayName("1°) Tentative de recupérer la liste des RuleName")
    @WithMockUser
    @Order(1)
    @Test
    void testGetAllRuleName() throws Exception {
        List<RuleName>  ruleNameList = new ArrayList<>();

        RuleName ruleName1 = new RuleName();
        RuleName ruleName2 = new RuleName();
        RuleName ruleName3 = new RuleName();

        ruleNameList.add(ruleName1);
        ruleNameList.add(ruleName2);
        ruleNameList.add(ruleName3);

        when(ruleNameService.findAll()).thenReturn(ruleNameList);

        this.mockMvc
                .perform(get("/ruleName/list"))
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attribute("ruleNameList", ruleNameList))
                .andExpect(status().isOk());
    }

    @DisplayName("2°) Ajout d'un RuleName")
    @WithMockUser
    @Order(2)
    @Test
    void addRuleNameForm() throws Exception {
        RuleName ruleName = new RuleName();
        ruleName.setName("nameTest");
        ruleName.setDescription("descriptionTest");
        ruleName.setJson("{ \"message\" : \"message breaks json\" }");
        ruleName.setTemplate("templateTest");
        ruleName.setSqlStr("sqlStrTest");
        ruleName.setSqlPart("sqlPartTest");

        List<RuleName> ruleNameList = new ArrayList<>();
        RuleName ruleName2 = new RuleName();
        RuleName ruleName3 = new RuleName();

        ruleNameList.add(ruleName2);
        ruleNameList.add(ruleName3);
        ruleNameList.add(ruleName);

        when(ruleNameService.findAll()).thenReturn(ruleNameList);

        this.mockMvc
                .perform(get("/ruleName/add")
                        .flashAttr("ruleName", ruleName)
                        .flashAttr("success", true))
                .andExpect(model().attribute("ruleNameList", ruleNameList))
                .andExpect(status().isOk());
    }

    @DisplayName("3°) Ajout d'un RuleName sans success")
    @WithMockUser
    @Order(3)
    @Test
    void testAddRuleNameFormShouldFail() throws Exception {
        RuleName ruleName = new RuleName();
        ruleName.setName("");
        ruleName.setDescription("descriptionTest");
        ruleName.setJson("{ \"message\" : \"message breaks json\" }");
        ruleName.setTemplate("templateTest");
        ruleName.setSqlStr("sqlStrTest");
        ruleName.setSqlPart("sqlPartTest");

        this.mockMvc
                .perform(get("/ruleName/add")
                        .flashAttr("ruleName", ruleName))
                .andExpect(model().attribute("ruleName", ruleName))
                .andExpect(view().name("ruleName/add"))
                .andExpect(status().isOk());

    }

    @DisplayName("4°) Test de la validité d'un RuleName correct")
    @WithMockUser
    @Order(4)
    @Test
    void testValidate() throws Exception {
        RuleName ruleName = new RuleName();
        ruleName.setName("nameTest");
        ruleName.setDescription("descriptionTest");
        ruleName.setJson("{ \"message\" : \"message breaks json\" }");
        ruleName.setTemplate("templateTest");
        ruleName.setSqlStr("sqlStrTest");
        ruleName.setSqlPart("sqlPartTest");

        List<RuleName> ruleNameList = new ArrayList<>();
        RuleName ruleName2 = new RuleName();
        RuleName ruleName3 = new RuleName();

        ruleNameList.add(ruleName2);
        ruleNameList.add(ruleName3);
        ruleNameList.add(ruleName);

        when(ruleNameService.findAll()).thenReturn(ruleNameList);

        this.mockMvc
                .perform(post("/ruleName/validate")
                        .flashAttr("success", true)
                        .flashAttr("ruleName", ruleName))
                .andExpect(model().attribute("message", "RuleName crée avec succès"))
                .andExpect(model().attribute("ruleName", ruleName))
                .andExpect(view().name("ruleName/list"))
                .andExpect(status().isCreated());
    }

    @DisplayName("5°) Test de la validité d'un RuleName incorrect")
    @WithMockUser
    @Order(5)
    @Test
    void testValidateWithError() throws Exception {
        RuleName ruleName = new RuleName();
        ruleName.setName(null);
        ruleName.setDescription("descriptionTest");
        ruleName.setJson("{ \"message\" : \"message breaks json\" }");
        ruleName.setTemplate("templateTest");
        ruleName.setSqlStr("sqlStrTest");
        ruleName.setSqlPart("sqlPartTest");

        this.mockMvc
                .perform(post("/ruleName/validate")
                        .flashAttr("success", false)
                        .flashAttr("ruleName", ruleName))
                .andExpect(model().attribute("success", false))
                .andExpect(model().attributeExists("messageList"))
                .andExpect(model().attribute("ruleName", ruleName))
                .andExpect(view().name("ruleName/add"))
                .andExpect(status().isOk());
    }

    @DisplayName("6°)  Tentative de recupérer un RuleName via son Id")
    @WithMockUser
    @Order(6)
    @Test
    void testShowUpdateForm() throws Exception {
        RuleName ruleName = new RuleName();
        ruleName.setId(1);
        ruleName.setName("nameTest");
        ruleName.setDescription("descriptionTest");
        ruleName.setJson("{ \"message\" : \"message breaks json\" }");
        ruleName.setTemplate("templateTest");
        ruleName.setSqlStr("sqlStrTest");
        ruleName.setSqlPart("sqlPartTest");

        when(ruleNameService.findById(anyInt())).thenReturn(ruleName);

        this.mockMvc
                .perform(get("/ruleName/update/{id}", 1))
                .andExpect(model().attribute("success", true))
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attribute("ruleName", ruleName))
                .andExpect(status().isOk());
    }

    @DisplayName("7°)  Tentative de recupérer un RuleName inexistant via son Id")
    @WithMockUser
    @Order(7)
    @Test
    void testShowUpdateFormShouldFail() throws Exception {
        when(ruleNameService.findById(anyInt())).thenReturn(null);

        this.mockMvc
                .perform(get("/ruleName/update/{id}", 1))
                .andExpect(model().attribute("success", false))
                .andExpect(model().attribute("message", "Aucun RuleName n'a été trouvé avec l'id fourni"))
                .andExpect(view().name("ruleName/list"))
                .andExpect(status().isOk());
    }

    @DisplayName("8°) Test update RuleName avec les champs correct")
    @WithMockUser
    @Order(8)
    @Test
    void testUpdateRuleNameShouldSuccess() throws Exception {
        RuleName ruleName = new RuleName();
        ruleName.setId(1);
        ruleName.setName("nameTest");
        ruleName.setDescription("descriptionTest");
        ruleName.setJson("{ \"message\" : \"message breaks json\" }");
        ruleName.setTemplate("templateTest");
        ruleName.setSqlStr("sqlStrTest");
        ruleName.setSqlPart("sqlPartTest");

        RuleName ruleName2 = new RuleName();
        ruleName2.setId(1);
        ruleName2.setName("nameTestUpdated");
        ruleName2.setDescription("descriptionTestUpdated");
        ruleName2.setJson("{ \"message\" : \"message breaks json Updated\" }");
        ruleName2.setTemplate("templateTestUpdated");
        ruleName2.setSqlStr("sqlStrTestUpdated");
        ruleName2.setSqlPart("sqlPartTestUpdated");

        when(ruleNameService.findById(anyInt())).thenReturn(ruleName);

        this.mockMvc
                .perform(post("/ruleName/update/{id}", 1)
                        .flashAttr("ruleName", ruleName2))
                .andExpect(model().attribute("success", true))
                .andExpect(model().attribute("message", "RuleName mis à jour avec succès"))
                .andExpect(view().name("ruleName/list"))
                .andExpect(status().isOk());
    }

    @DisplayName("9°) Test update RuleName avec un champ obligatoire incorrect/manquant")
    @WithMockUser
    @Order(9)
    @Test
    void testUpdateRuleNameShouldFail() throws Exception {
        RuleName ruleName = new RuleName();
        ruleName.setId(1);
        ruleName.setName("nameTest");
        ruleName.setDescription("descriptionTest");
        ruleName.setJson("{ \"message\" : \"message breaks json\" }");
        ruleName.setTemplate("templateTest");
        ruleName.setSqlStr("sqlStrTest");
        ruleName.setSqlPart("sqlPartTest");

        RuleName ruleName2 = new RuleName();
        ruleName2.setId(1);
        ruleName2.setName(null);
        ruleName2.setDescription("descriptionTestUpdated");
        ruleName2.setJson("{ \"message\" : \"message breaks json Updated\" }");
        ruleName2.setTemplate("templateTestUpdated");
        ruleName2.setSqlStr("sqlStrTestUpdated");
        ruleName2.setSqlPart("sqlPartTestUpdated");

        this.mockMvc
                .perform(post("/ruleName/update/{id}", 1)
                        .flashAttr("ruleName", ruleName2))
                .andExpect(model().attribute("success", false))
                .andExpect(model().attributeExists("messageList"))
                .andExpect(view().name("ruleName/update"))
                .andExpect(status().isOk());
    }

    @DisplayName("10°) Test delete RuleName par Id")
    @WithMockUser
    @Order(10)
    @Test
    void testDeleteRuleNameShouldSuccess() throws Exception {
        RuleName ruleName = new RuleName();
        ruleName.setId(1);
        ruleName.setName("nameTest");
        ruleName.setDescription("descriptionTest");
        ruleName.setJson("{ \"message\" : \"message breaks json\" }");
        ruleName.setTemplate("templateTest");
        ruleName.setSqlStr("sqlStrTest");
        ruleName.setSqlPart("sqlPartTest");

        when(ruleNameService.deleteById(anyInt())).thenReturn(true);

        this.mockMvc
                .perform(get("/ruleName/delete/{id}",1))
                .andExpect(model().attribute("success", true))
                .andExpect(model().attribute("message", "RuleName avec id 1 à été supprimé"))
                .andExpect(view().name("ruleName/list"))
                .andExpect(status().isOk());
    }

    @DisplayName("11°) Test delete RuleName inexistant par Id")
    @WithMockUser
    @Order(11)
    @Test
    void testDeleteRuleNameShouldFail() throws Exception {
        List<String> errorMessageList = new ArrayList<>();
        errorMessageList.add("Une erreur est survenue lors de la suppression du RuleName");

        when(ruleNameService.deleteById(anyInt())).thenReturn(false);

        this.mockMvc.perform(get("/ruleName/delete/{id}", 999))
                .andExpect(model().attribute("success", false))
                .andExpect(model().attributeExists("messageList"))
                .andExpect(model().attribute("messageList", errorMessageList))
                .andExpect(view().name("ruleName/list"))
                .andExpect(status().isOk());
    }
}
