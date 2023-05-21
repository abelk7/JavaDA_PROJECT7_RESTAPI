package com.nnk.springboot.controllers;

import com.nnk.springboot.config.SecurityConfig;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.ITradeService;
import com.nnk.springboot.services.ITradeService;
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
@WebMvcTest(TradeController.class)
public class TradeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ITradeService tradeService;
    @MockBean
    private IUserService userService;

    @DisplayName("1°) Tentative de recupérer la liste des Trade")
    @WithMockUser
    @Order(1)
    @Test
    void testGetAllTrade() throws Exception {
        List<Trade> tradeList = new ArrayList<>();

        Trade trade1 = new Trade();
        Trade trade2 = new Trade();
        Trade trade3 = new Trade();

        tradeList.add(trade1);
        tradeList.add(trade2);
        tradeList.add(trade3);

        when(tradeService.findAll()).thenReturn(tradeList);

        this.mockMvc
                .perform(get("/trade/list"))
                .andExpect(view().name("trade/list"))
                .andExpect(model().attribute("tradeList", tradeList))
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

        List<Trade> tradeList = new ArrayList<>();
        Trade trade2 = new Trade();
        Trade trade3 = new Trade();

        tradeList.add(trade2);
        tradeList.add(trade3);
        tradeList.add(trade);

        when(tradeService.findAll()).thenReturn(tradeList);

        this.mockMvc
                .perform(get("/trade/add")
                        .flashAttr("trade", trade)
                        .flashAttr("success", true))
                .andExpect(model().attribute("tradeList", tradeList))
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

        List<Trade> tradeList = new ArrayList<>();
        Trade trade2 = new Trade();
        Trade trade3 = new Trade();

        tradeList.add(trade2);
        tradeList.add(trade3);
        tradeList.add(trade);

        when(tradeService.findAll()).thenReturn(tradeList);

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

        when(tradeService.findById(anyInt())).thenReturn(trade);

        this.mockMvc
                .perform(get("/trade/update/{id}", 1))
                .andExpect(model().attribute("success", true))
                .andExpect(view().name("trade/update"))
                .andExpect(model().attribute("trade", trade))
                .andExpect(status().isOk());
    }

    @DisplayName("7°)  Tentative de recupérer un Trade inexistant via son Id")
    @WithMockUser
    @Order(7)
    @Test
    void testShowUpdateFormShouldFail() throws Exception {
        when(tradeService.findById(anyInt())).thenReturn(null);

        this.mockMvc
                .perform(get("/trade/update/{id}", 1))
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
        Trade trade = new Trade();
        trade.setTradeId(1);
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

        Trade trade2 = new Trade();
        trade2.setTradeId(1);
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

        when(tradeService.findById(anyInt())).thenReturn(trade);

        this.mockMvc
                .perform(post("/trade/update/{id}", 1)
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
        Trade trade = new Trade();
        trade.setTradeId(1);
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

        Trade trade2 = new Trade();
        trade.setTradeId(1);
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
                .perform(post("/trade/update/{id}", 1)
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
        Trade trade = new Trade();
        trade.setTradeId(1);
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

        when(tradeService.deleteById(anyInt())).thenReturn(true);

        this.mockMvc
                .perform(get("/trade/delete/{id}",1))
                .andExpect(model().attribute("success", true))
                .andExpect(model().attribute("message", "Trade avec id 1 à été supprimé"))
                .andExpect(view().name("trade/list"))
                .andExpect(status().isOk());
    }

    @DisplayName("11°) Test delete Trade inexistant par Id")
    @WithMockUser
    @Order(11)
    @Test
    void testDeleteTradeShouldFail() throws Exception {
        List<String> errorMessageList = new ArrayList<>();
        errorMessageList.add("Une erreur est survenue lors de la suppression du Trade");

        when(tradeService.deleteById(anyInt())).thenReturn(false);

        this.mockMvc.perform(get("/trade/delete/{id}", 999))
                .andExpect(model().attribute("success", false))
                .andExpect(model().attributeExists("messageList"))
                .andExpect(model().attribute("messageList", errorMessageList))
                .andExpect(view().name("trade/list"))
                .andExpect(status().isOk());
    }
}
