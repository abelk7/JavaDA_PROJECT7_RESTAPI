package com.nnk.springboot.controllers.it;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.repositories.TradeRepository;
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
public class TradeControllerTestIT {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private TradeRepository tradeRepository;
    private MockMvc mockMvc;
    private Trade tradeTest = ajouterTrade();

    private static Trade ajouterTrade() {
        Trade trade = new Trade();
        trade.setAccount("accountTest");
        trade.setType("typeTest");
        trade.setBuyQuantity(15.00);
        trade.setSellQuantity(20.00);
        trade.setBuyPrice(40.00);
        trade.setSellPrice(80.00);
        trade.setBenchmark("benchmarkTest");
        trade.setTradeDate(LocalDateTime.now());
        trade.setSecurity("securityTest");
        trade.setStatus("statusTest");
        trade.setTrader("traderTest");
        trade.setBook("bookTest");
        trade.setCreationName("creationNameTest");
        trade.setCreationDate(LocalDateTime.now());
        trade.setRevisionName("revisionNameTest");
        trade.setRevisionDate(LocalDateTime.now());
        trade.setDealName("dealNameTest");
        trade.setDealType("dealTypeTest");
        trade.setSourceListId("sourceListIdTest");
        trade.setSide("sideTest");
        return trade;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        tradeRepository.save(tradeTest);
    }

    @AfterEach
    public void tearDown() {
        tradeRepository.delete(tradeTest);
    }

    @DisplayName("1°) Tentative de recupérer la liste des Trade")
    @WithMockUser
    @Order(1)
    @Test
    void testGetAllTrade() throws Exception {
        this.mockMvc
                .perform(get("/trade/list"))
                .andExpect(view().name("trade/list"))
                .andExpect(model().attributeExists("tradeList"))
                .andExpect(status().isOk());
    }

    @DisplayName("2°) Ajout d'un Trade")
    @WithMockUser
    @Order(2)
    @Test
    void addTradeForm() throws Exception {
        Trade trade = new Trade();
        trade.setAccount("accountTest");
        trade.setType("typeTest");
        trade.setBuyQuantity(15.00);
        trade.setSellQuantity(20.00);
        trade.setBuyPrice(40.00);
        trade.setSellPrice(80.00);
        trade.setBenchmark("benchmarkTest");
        trade.setTradeDate(LocalDateTime.now());
        trade.setSecurity("securityTest");
        trade.setStatus("statusTest");
        trade.setTrader("traderTest");
        trade.setBook("bookTest");
        trade.setCreationName("creationNameTest");
        trade.setCreationDate(LocalDateTime.now());
        trade.setRevisionName("revisionNameTest");
        trade.setRevisionDate(LocalDateTime.now());
        trade.setDealName("dealNameTest");
        trade.setDealType("dealTypeTest");
        trade.setSourceListId("sourceListIdTest");
        trade.setSide("sideTest");

        this.mockMvc
                .perform(get("/trade/add")
                        .flashAttr("trade", trade)
                        .flashAttr("success", true))
                .andExpect(model().attributeExists("tradeList"))
                .andExpect(status().isOk());
    }

    @DisplayName("3°) Ajout d'un Trade sans success")
    @WithMockUser
    @Order(3)
    @Test
    void testAddTradeFormShouldFail() throws Exception {
        Trade trade = new Trade();
        trade.setAccount("");
        trade.setType("typeTest");
        trade.setBuyQuantity(15.00);
        trade.setSellQuantity(20.00);
        trade.setBuyPrice(40.00);
        trade.setSellPrice(80.00);
        trade.setBenchmark("benchmarkTest");
        trade.setTradeDate(LocalDateTime.now());
        trade.setSecurity("securityTest");
        trade.setStatus("statusTest");
        trade.setTrader("traderTest");
        trade.setBook("bookTest");
        trade.setCreationName("creationNameTest");
        trade.setCreationDate(LocalDateTime.now());
        trade.setRevisionName("revisionNameTest");
        trade.setRevisionDate(LocalDateTime.now());
        trade.setDealName("dealNameTest");
        trade.setDealType("dealTypeTest");
        trade.setSourceListId("sourceListIdTest");
        trade.setSide("sideTest");

        this.mockMvc
                .perform(get("/trade/add")
                        .flashAttr("trade", trade))
                .andExpect(model().attribute("success", false))
                .andExpect(model().attribute("trade", trade))
                .andExpect(view().name("trade/add"))
                .andExpect(status().isOk());

    }

    @DisplayName("4°) Test de la validité d'un Trade correct")
    @WithMockUser
    @Order(4)
    @Test
    void testValidate() throws Exception {
        Trade trade = new Trade();
        trade.setAccount("accountTest");
        trade.setType("typeTest");
        trade.setBuyQuantity(15.00);
        trade.setSellQuantity(20.00);
        trade.setBuyPrice(40.00);
        trade.setSellPrice(80.00);
        trade.setBenchmark("benchmarkTest");
        trade.setTradeDate(LocalDateTime.now());
        trade.setSecurity("securityTest");
        trade.setStatus("statusTest");
        trade.setTrader("traderTest");
        trade.setBook("bookTest");
        trade.setCreationName("creationNameTest");
        trade.setCreationDate(LocalDateTime.now());
        trade.setRevisionName("revisionNameTest");
        trade.setRevisionDate(LocalDateTime.now());
        trade.setDealName("dealNameTest");
        trade.setDealType("dealTypeTest");
        trade.setSourceListId("sourceListIdTest");
        trade.setSide("sideTest");

        this.mockMvc
                .perform(post("/trade/validate")
                        .flashAttr("success", true)
                        .flashAttr("trade", trade))
                .andExpect(model().attribute("message", "Trade crée avec succès"))
                .andExpect(model().attribute("trade", trade))
                .andExpect(view().name("trade/list"))
                .andExpect(status().isCreated());
    }

    @DisplayName("5°) Test de la validité d'un Trade incorrect")
    @WithMockUser
    @Order(5)
    @Test
    void testValidateWithError() throws Exception {
        Trade trade = new Trade();
        trade.setAccount("");
        trade.setType("typeTest");
        trade.setBuyQuantity(15.00);
        trade.setSellQuantity(20.00);
        trade.setBuyPrice(40.00);
        trade.setSellPrice(80.00);
        trade.setBenchmark("benchmarkTest");
        trade.setTradeDate(LocalDateTime.now());
        trade.setSecurity("securityTest");
        trade.setStatus("statusTest");
        trade.setTrader("traderTest");
        trade.setBook("bookTest");
        trade.setCreationName("creationNameTest");
        trade.setCreationDate(LocalDateTime.now());
        trade.setRevisionName("revisionNameTest");
        trade.setRevisionDate(LocalDateTime.now());
        trade.setDealName("dealNameTest");
        trade.setDealType("dealTypeTest");
        trade.setSourceListId("sourceListIdTest");
        trade.setSide("sideTest");

        this.mockMvc
                .perform(post("/trade/validate")
                        .flashAttr("success", false)
                        .flashAttr("trade", trade))
                .andExpect(model().attribute("success", false))
                .andExpect(model().attributeExists("messageList"))
                .andExpect(model().attribute("trade", trade))
                .andExpect(view().name("trade/add"))
                .andExpect(status().isOk());
    }

    @DisplayName("6°)  Tentative de recupérer un Trade via son Id")
    @WithMockUser
    @Order(6)
    @Test
    void testShowUpdateForm() throws Exception {
        this.mockMvc
                .perform(get("/trade/update/{id}", tradeTest.getTradeId()))
                .andExpect(model().attribute("success", true))
                .andExpect(view().name("trade/update"))
                .andExpect(model().attributeExists("trade"))
                .andExpect(status().isOk());
    }

    @DisplayName("7°)  Tentative de recupérer un Trade inexistant via son Id")
    @WithMockUser
    @Order(7)
    @Test
    void testShowUpdateFormShouldFail() throws Exception {
        this.mockMvc
                .perform(get("/trade/update/{id}", 999))
                .andExpect(model().attribute("success", false))
                .andExpect(model().attribute("message", "Aucun Trade n'a été trouvé avec l'id fourni"))
                .andExpect(view().name("trade/list"))
                .andExpect(status().isOk());
    }

    @DisplayName("8°) Test update Trade avec les champs correct")
    @WithMockUser
    @Order(8)
    @Test
    void testUpdateTradeShouldSuccess() throws Exception {
        Trade trade2 = new Trade();
        trade2.setTradeId(tradeTest.getTradeId());
        trade2.setAccount("accountTestUpdated");
        trade2.setType("typeTestUpdated");
        trade2.setBuyQuantity(15.00);
        trade2.setSellQuantity(20.00);
        trade2.setBuyPrice(40.00);
        trade2.setSellPrice(80.00);
        trade2.setBenchmark("benchmarkTestUpdated");
        trade2.setTradeDate(LocalDateTime.now());
        trade2.setSecurity("securityTestUpdated");
        trade2.setStatus("statusTestUpdated");
        trade2.setTrader("traderTestUpdated");
        trade2.setBook("bookTestUpdated");
        trade2.setCreationName("creationNameTestUpdated");
        trade2.setCreationDate(LocalDateTime.now());
        trade2.setRevisionName("revisionNameTestUpdated");
        trade2.setRevisionDate(LocalDateTime.now());
        trade2.setDealName("dealNameTestUpdated");
        trade2.setDealType("dealTypeTestUpdated");
        trade2.setSourceListId("sourceListIdTestUpdated");
        trade2.setSide("sideTestUpdated");

        this.mockMvc
                .perform(post("/trade/update/{id}", tradeTest.getTradeId())
                        .flashAttr("trade", trade2))
                .andExpect(model().attribute("success", true))
                .andExpect(model().attribute("message", "Trade mis à jour avec succès"))
                .andExpect(view().name("trade/list"))
                .andExpect(status().isCreated());
    }

    @DisplayName("9°) Test update Trade avec un champ obligatoire incorrect/manquant")
    @WithMockUser
    @Order(9)
    @Test
    void testUpdateTradeShouldFail() throws Exception {
        Trade trade2 = new Trade();
        trade2.setTradeId(tradeTest.getTradeId());
        trade2.setAccount("");
        trade2.setType("typeTestUpdated");
        trade2.setBuyQuantity(15.00);
        trade2.setSellQuantity(20.00);
        trade2.setBuyPrice(40.00);
        trade2.setSellPrice(80.00);
        trade2.setBenchmark("benchmarkTestUpdated");
        trade2.setTradeDate(LocalDateTime.now());
        trade2.setSecurity("securityTestUpdated");
        trade2.setStatus("statusTestUpdated");
        trade2.setTrader("traderTestUpdated");
        trade2.setBook("bookTestUpdated");
        trade2.setCreationName("creationNameTestUpdated");
        trade2.setCreationDate(LocalDateTime.now());
        trade2.setRevisionName("revisionNameTestUpdated");
        trade2.setRevisionDate(LocalDateTime.now());
        trade2.setDealName("dealNameTestUpdated");
        trade2.setDealType("dealTypeTestUpdated");
        trade2.setSourceListId("sourceListIdTestUpdated");
        trade2.setSide("sideTestUpdated");

        this.mockMvc
                .perform(post("/trade/update/{id}", tradeTest.getTradeId())
                        .flashAttr("trade", trade2))
                .andExpect(model().attribute("success", false))
                .andExpect(model().attributeExists("messageList"))
                .andExpect(view().name("trade/update"))
                .andExpect(status().isOk());
    }

    @DisplayName("10°) Test delete Trade par Id")
    @WithMockUser
    @Order(10)
    @Test
    void testDeleteTradeShouldSuccess() throws Exception {
        this.mockMvc
                .perform(get("/trade/delete/{id}",tradeTest.getTradeId()))
                .andExpect(model().attribute("success", true))
                .andExpect(model().attribute("message", "Trade avec id " + tradeTest.getTradeId() + " à été supprimé"))
                .andExpect(view().name("trade/list"))
                .andExpect(status().isOk());
    }

    @DisplayName("11°) Test delete Trade inexistant par Id")
    @WithMockUser
    @Order(11)
    @Test
    void testDeleteTradeShouldFail() throws Exception {
        this.mockMvc.perform(get("/trade/delete/{id}", 999))
                .andExpect(model().attribute("success", false))
                .andExpect(model().attributeExists("messageList"))
                .andExpect(model().attributeExists("messageList"))
                .andExpect(view().name("trade/list"))
                .andExpect(status().isOk());
    }
}
