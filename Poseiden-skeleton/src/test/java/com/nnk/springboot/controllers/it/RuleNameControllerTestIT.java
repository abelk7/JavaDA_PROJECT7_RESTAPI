package com.nnk.springboot.controllers.it;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.*;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(JUnitPlatform.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RuleNameControllerTestIT {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private RuleNameRepository ruleNameRepository;
    private MockMvc mockMvc;
    private RuleName ruleNameTest = ajouterRuleName();

    private static RuleName ajouterRuleName() {
        RuleName ruleName = new RuleName();
        ruleName.setName("nameTest");
        ruleName.setDescription("descriptionTest");
        ruleName.setJson("{ \"message\" : \"message breaks json\" }");
        ruleName.setTemplate("templateTest");
        ruleName.setSqlStr("sqlStrTest");
        ruleName.setSqlPart("sqlPartTest");
        return ruleName;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        ruleNameRepository.save(ruleNameTest);
    }

    @AfterEach
    public void tearDown() {
        ruleNameRepository.delete(ruleNameTest);
    }

    @DisplayName("1°) Tentative de recupérer la liste des RuleName")
    @WithMockUser
    @Order(1)
    @Test
    void testGetAllRuleName() throws Exception {
        this.mockMvc
                .perform(get("/ruleName/list"))
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attributeExists("ruleNameList"))
                .andExpect(status().isOk());
    }

    @DisplayName("2°) Ajout d'un RuleName")
    @WithMockUser
    @Order(2)
    @Test
    void addRuleNameForm() throws Exception {
        RuleName ruleName = new RuleName();
        ruleName.setName("nameTest2");
        ruleName.setDescription("descriptionTest2");
        ruleName.setJson("{ \"message\" : \"message breaks json2\" }");
        ruleName.setTemplate("templateTest2");
        ruleName.setSqlStr("sqlStrTest2");
        ruleName.setSqlPart("sqlPartTest2");

        this.mockMvc
                .perform(get("/ruleName/add")
                        .flashAttr("ruleName", ruleName)
                        .flashAttr("success", true))
                .andExpect(model().attributeExists("ruleNameList"))
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
                .andExpect(model().attribute("success", false))
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
        this.mockMvc
                .perform(get("/ruleName/update/{id}", ruleNameTest.getId()))
                .andExpect(model().attribute("success", true))
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attributeExists("ruleName"))
                .andExpect(status().isOk());
    }

    @DisplayName("7°)  Tentative de recupérer un RuleName inexistant via son Id")
    @WithMockUser
    @Order(7)
    @Test
    void testShowUpdateFormShouldFail() throws Exception {
        this.mockMvc
                .perform(get("/ruleName/update/{id}", 999))
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
        RuleName ruleName2 = new RuleName();
        ruleName2.setId(ruleNameTest.getId());
        ruleName2.setName("nameTestUpdated");
        ruleName2.setDescription("descriptionTestUpdated");
        ruleName2.setJson("{ \"message\" : \"message breaks json Updated\" }");
        ruleName2.setTemplate("templateTestUpdated");
        ruleName2.setSqlStr("sqlStrTestUpdated");
        ruleName2.setSqlPart("sqlPartTestUpdated");

        this.mockMvc
                .perform(post("/ruleName/update/{id}", ruleNameTest.getId())
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
        RuleName ruleName2 = new RuleName();
        ruleName2.setId(ruleNameTest.getId());
        ruleName2.setName(null);
        ruleName2.setDescription("descriptionTestUpdated");
        ruleName2.setJson("{ \"message\" : \"message breaks json Updated\" }");
        ruleName2.setTemplate("templateTestUpdated");
        ruleName2.setSqlStr("sqlStrTestUpdated");
        ruleName2.setSqlPart("sqlPartTestUpdated");

        this.mockMvc
                .perform(post("/ruleName/update/{id}", ruleNameTest.getId())
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
        this.mockMvc
                .perform(get("/ruleName/delete/{id}",ruleNameTest.getId()))
                .andExpect(model().attribute("success", true))
                .andExpect(model().attribute("message", "RuleName avec id " + ruleNameTest.getId() + " à été supprimé"))
                .andExpect(view().name("ruleName/list"))
                .andExpect(status().isOk());
    }

    @DisplayName("11°) Test delete RuleName inexistant par Id")
    @WithMockUser
    @Order(11)
    @Test
    void testDeleteRuleNameShouldFail() throws Exception {
        this.mockMvc.perform(get("/ruleName/delete/{id}", 999))
                .andExpect(model().attribute("success", false))
                .andExpect(model().attributeExists("messageList"))
                .andExpect(view().name("ruleName/list"))
                .andExpect(status().isOk());
    }

}
