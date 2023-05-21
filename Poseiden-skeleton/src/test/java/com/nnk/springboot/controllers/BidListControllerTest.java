package com.nnk.springboot.controllers;

import com.nnk.springboot.config.SecurityConfig;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.IBidListService;
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
@WebMvcTest(BidListController.class)
public class BidListControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IBidListService bidListService;
    @MockBean
    private IUserService userService;

    @DisplayName("1°) Tentative de recupérer la liste des BidList")
    @WithMockUser
    @Order(1)
    @Test
    void testGetAllBidList() throws Exception {
        List<BidList> bidListList = new ArrayList<>();

        BidList bidList1 = new BidList();
        BidList bidList2 = new BidList();
        BidList bidList3 = new BidList();

        bidListList.add(bidList1);
        bidListList.add(bidList2);
        bidListList.add(bidList3);

        when(bidListService.findAll()).thenReturn(bidListList);

        this.mockMvc
                .perform(get("/bidList/list"))
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attribute("bidList", bidListList))
                .andExpect(status().isOk());
    }

    @DisplayName("2°) Ajout d'un bidList")
    @WithMockUser
    @Order(2)
    @Test
    void testAddBidForm() throws Exception {
        BidList bidList3 = new BidList();
        bidList3.setAccount("testAccountTest");
        bidList3.setType("testTypeTest");
        bidList3.setBidQuantity(10.00);
        bidList3.setBid(1.0);
        bidList3.setAsk(10.00);
        bidList3.setBenchmark("testBencjmarkTest");
        bidList3.setBidListDate(LocalDateTime.now());
        bidList3.setAskQuantity(10.11);
        bidList3.setCommentary("testCommentaryTest");
        bidList3.setSecurity("testSecurityTest");
        bidList3.setStatus("testStatusTest");
        bidList3.setTrader("testTradeTest");
        bidList3.setBook("testBookTest");
        bidList3.setCreationName("testCreationName");
        bidList3.setCreationDate(LocalDateTime.now());
        bidList3.setRevisionName("testRevisionNameTest");
        bidList3.setRevisionDate(LocalDateTime.now());
        bidList3.setDealName("testDealNameTest");
        bidList3.setDealType("testDealTypeTest");
        bidList3.setSourceListId("testSourceListId");

        List<BidList> bidListList = new ArrayList<>();
        BidList bidList1 = new BidList();
        BidList bidList2 = new BidList();

        bidListList.add(bidList1);
        bidListList.add(bidList2);
        bidListList.add(bidList3);

        when(bidListService.findAll()).thenReturn(bidListList);


        this.mockMvc.perform(get("/bidList/add")
                        .flashAttr("bidList", bidList3)
                        .flashAttr("success", true))
                .andExpect(model().attribute("bidList", bidListList))
                .andExpect(status().isOk());
    }

    @DisplayName("3°) Ajout d'un bidList sans success")
    @WithMockUser
    @Order(3)
    @Test
    void testAddBidFormShouldFail() throws Exception {
        BidList bidList = new BidList();
        bidList.setAccount("testAccountTest");
        bidList.setType("testTypeTest");
        bidList.setBidQuantity(10.00);
        bidList.setBid(1.0);
        bidList.setAsk(10.00);
        bidList.setBenchmark("testBencjmarkTest");
        bidList.setBidListDate(LocalDateTime.now());
        bidList.setAskQuantity(10.11);
        bidList.setCommentary("testCommentaryTest");
        bidList.setSecurity("testSecurityTest");
        bidList.setStatus("testStatusTest");
        bidList.setTrader("testTradeTest");
        bidList.setBook("testBookTest");
        bidList.setCreationName(null);
        bidList.setCreationDate(null);
        bidList.setRevisionName("testRevisionNameTest");
        bidList.setRevisionDate(LocalDateTime.now());
        bidList.setDealName("testDealNameTest");
        bidList.setDealType("testDealTypeTest");
        bidList.setSourceListId("testSourceListId");

        this.mockMvc.perform(get("/bidList/add")
                        .flashAttr("bidList", bidList))
                .andExpect(model().attribute("bidList", bidList))
                .andExpect(view().name("bidList/add"))
                .andExpect(status().isOk());
    }

    @DisplayName("4°) Test de la validité d'un bidList correct")
    @WithMockUser
    @Order(4)
    @Test
    void testValidate() throws Exception {
        BidList bidList3 = new BidList();
        bidList3.setAccount("testAccountTest");
        bidList3.setType("testTypeTest");
        bidList3.setBidQuantity(10.00);
        bidList3.setBid(1.0);
        bidList3.setAsk(10.00);
        bidList3.setBenchmark("testBencjmarkTest");
        bidList3.setBidListDate(LocalDateTime.now());
        bidList3.setAskQuantity(10.11);
        bidList3.setCommentary("testCommentaryTest");
        bidList3.setSecurity("testSecurityTest");
        bidList3.setStatus("testStatusTest");
        bidList3.setTrader("testTradeTest");
        bidList3.setBook("testBookTest");
        bidList3.setCreationName("AAAA");
        bidList3.setCreationDate(LocalDateTime.now());
        bidList3.setRevisionName("testRevisionNameTest");
        bidList3.setRevisionDate(LocalDateTime.now());
        bidList3.setDealName("testDealNameTest");
        bidList3.setDealType("testDealTypeTest");
        bidList3.setSourceListId("testSourceListId");

        List<BidList> bidListList = new ArrayList<>();

        BidList bidList1 = new BidList();
        BidList bidList2 = new BidList();

        bidListList.add(bidList1);
        bidListList.add(bidList2);
        bidListList.add(bidList3);

        when(bidListService.findAll()).thenReturn(bidListList);

        this.mockMvc.perform(post("/bidList/validate/").flashAttr("bidList", bidList3))
                .andExpect(model().attribute("success", true))
                .andExpect(model().attribute("message", "BidList crée avec succès"))
                .andExpect(model().attribute("bidList", bidListList))
                .andExpect(view().name("bidList/list"))
                .andExpect(status().isCreated());
    }

    @DisplayName("5°) Test de la validité d'un bidList incorrect")
    @WithMockUser
    @Order(5)
    @Test
    void testValidateWithError() throws Exception {
        BidList bidList3 = new BidList();
        bidList3.setAccount("testAccountTest");
        bidList3.setType("testTypeTest");
        bidList3.setBidQuantity(10.00);
        bidList3.setBid(1.0);
        bidList3.setAsk(10.00);
        bidList3.setBenchmark("testBencjmarkTest");
        bidList3.setBidListDate(LocalDateTime.now());
        bidList3.setAskQuantity(10.11);
        bidList3.setCommentary("testCommentaryTest");
        bidList3.setSecurity("testSecurityTest");
        bidList3.setStatus("testStatusTest");
        bidList3.setTrader("testTradeTest");
        bidList3.setBook("testBookTest");
        //Champs ne doit pas être null ou empty
        bidList3.setCreationName(null);
        bidList3.setCreationDate(null);
        bidList3.setRevisionName("testRevisionNameTest");
        bidList3.setRevisionDate(LocalDateTime.now());
        bidList3.setDealName("testDealNameTest");
        bidList3.setDealType("testDealTypeTest");
        bidList3.setSourceListId("testSourceListId");

        List<BidList> bidListList = new ArrayList<>();

        BidList bidList1 = new BidList();
        BidList bidList2 = new BidList();

        bidListList.add(bidList1);
        bidListList.add(bidList2);

//        when(bidListService.findAll()).thenReturn(bidListList);

        this.mockMvc.perform(post("/bidList/validate/").flashAttr("bidList", bidList3))
                .andExpect(model().attribute("success", false))
                .andExpect(model().attributeExists("messageList"))
                .andExpect(model().attribute("bidList", bidList3))
                .andExpect(view().name("bidList/add"))
                .andExpect(status().isOk());
    }

    @DisplayName("6°)  Tentative de recupérer un BidList via son Id")
    @WithMockUser
    @Order(6)
    @Test
    void testShowUpdateForm() throws Exception {
        BidList bidList = new BidList();
        bidList.setBidListId(1);
        bidList.setAccount("testAccountTest");
        bidList.setType("testTypeTest");
        bidList.setBidQuantity(10.00);
        bidList.setBid(1.0);
        bidList.setAsk(10.00);
        bidList.setBenchmark("testBencjmarkTest");
        bidList.setBidListDate(LocalDateTime.now());
        bidList.setAskQuantity(10.11);
        bidList.setCommentary("testCommentaryTest");
        bidList.setSecurity("testSecurityTest");
        bidList.setStatus("testStatusTest");
        bidList.setTrader("testTradeTest");
        bidList.setBook("testBookTest");
        bidList.setCreationName("AAAA");
        bidList.setCreationDate(LocalDateTime.now());
        bidList.setRevisionName("testRevisionNameTest");
        bidList.setRevisionDate(LocalDateTime.now());
        bidList.setDealName("testDealNameTest");
        bidList.setDealType("testDealTypeTest");
        bidList.setSourceListId("testSourceListId");

        when(bidListService.findById(anyInt())).thenReturn(bidList);

        this.mockMvc
                .perform(get("/bidList/update/{id}", 1))
                .andExpect(model().attribute("success", true))
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attribute("bid", bidList))
                .andExpect(status().isOk());
    }

    @DisplayName("7°)  Tentative de recupérer un BidList inexistant via son Id")
    @WithMockUser
    @Order(7)
    @Test
    void testShowUpdateFormShouldFail() throws Exception {

        when(bidListService.findById(anyInt())).thenReturn(null);

        this.mockMvc
                .perform(get("/bidList/update/{id}", 1))
                .andExpect(model().attribute("success", false))
                .andExpect(model().attribute("message", "Aucun BidList n'a été trouvé avec l'id fourni"))
                .andExpect(view().name("bidList/list"))
                .andExpect(status().isOk());
    }

    @DisplayName("8°) Test update bidList avec les champs correct")
    @WithMockUser
    @Order(8)
    @Test
    void testUpdateBidlistShouldSuccess() throws Exception {

        BidList bidList = new BidList();
        bidList.setBidListId(1);
        bidList.setAccount("testAccountTest");
        bidList.setType("testTypeTest");
        bidList.setBidQuantity(10.00);
        bidList.setBid(1.0);
        bidList.setAsk(10.00);
        bidList.setBenchmark("testBencjmarkTest");
        bidList.setBidListDate(LocalDateTime.now());
        bidList.setAskQuantity(10.11);
        bidList.setCommentary("testCommentaryTest");
        bidList.setSecurity("testSecurityTest");
        bidList.setStatus("testStatusTest");
        bidList.setTrader("testTradeTest");
        bidList.setBook("testBookTest");
        bidList.setCreationName("AAAA");
        bidList.setCreationDate(LocalDateTime.now());
        bidList.setRevisionName("testRevisionNameTest");
        bidList.setRevisionDate(LocalDateTime.now());
        bidList.setDealName("testDealNameTest");
        bidList.setDealType("testDealTypeTest");
        bidList.setSourceListId("testSourceListId");


        BidList bid = new BidList();
        bid.setBidListId(1);
        bid.setAccount("testAccountTestUpdate");
        bid.setType("testTypeTestUpdate");
        bid.setBidQuantity(35.00);
        bid.setBid(14.0);
        bid.setAsk(26.00);
        bid.setBenchmark("testBencjmarkTestUpdate");
        bid.setBidListDate(LocalDateTime.now());
        bid.setAskQuantity(53.20);
        bid.setCommentary("testCommentaryTestUpdate");
        bid.setSecurity("testSecurityTestUpdate");
        bid.setStatus("testStatusTestUpdate");
        bid.setTrader("testTradeTestUpdate");
        bid.setBook("testBookTestUpdate");
        bid.setCreationName("AAAAUpdate");
        bid.setCreationDate(LocalDateTime.now());
        bid.setRevisionName("testRevisionNameTestUpdate");
        bid.setRevisionDate(LocalDateTime.now());
        bid.setDealName("testDealNameTestUpdate");
        bid.setDealType("testDealTypeTestUpdate");
        bid.setSourceListId("testSourceListIdUpdate");

        when(bidListService.findById(anyInt())).thenReturn(bidList);

        this.mockMvc.perform(post("/bidList/update/{id}", 1).flashAttr("bidList", bid))
                .andExpect(model().attribute("success", true))
                .andExpect(model().attribute("message", "Bidlist mis à jour avec succès"))
                .andExpect(view().name("bidList/list"))
                .andExpect(status().isOk());
    }

    @DisplayName("9°) Test update bidList avec un champ obligatoire incorrect/manquant")
    @WithMockUser
    @Order(9)
    @Test
    void testUpdateBidlistShouldFail() throws Exception {

        BidList bidList = new BidList();
        bidList.setBidListId(1);
        bidList.setAccount("testAccountTest");
        bidList.setType("testTypeTest");
        bidList.setBidQuantity(10.00);
        bidList.setBid(1.0);
        bidList.setAsk(10.00);
        bidList.setBenchmark("testBencjmarkTest");
        bidList.setBidListDate(LocalDateTime.now());
        bidList.setAskQuantity(10.11);
        bidList.setCommentary("testCommentaryTest");
        bidList.setSecurity("testSecurityTest");
        bidList.setStatus("testStatusTest");
        bidList.setTrader("testTradeTest");
        bidList.setBook("testBookTest");
        bidList.setCreationName("AAAA");
        bidList.setCreationDate(LocalDateTime.now());
        bidList.setRevisionName("testRevisionNameTest");
        bidList.setRevisionDate(LocalDateTime.now());
        bidList.setDealName("testDealNameTest");
        bidList.setDealType("testDealTypeTest");
        bidList.setSourceListId("testSourceListId");


        BidList bid = new BidList();
        bid.setBidListId(1);
        bid.setAccount("testAccountTestUpdate");
        bid.setType("testTypeTestUpdate");
        bid.setBidQuantity(35.00);
        bid.setBid(14.0);
        bid.setAsk(26.00);
        bid.setBenchmark("testBencjmarkTestUpdate");
        bid.setBidListDate(LocalDateTime.now());
        bid.setAskQuantity(53.20);
        bid.setCommentary("testCommentaryTestUpdate");
        bid.setSecurity("testSecurityTestUpdate");
        bid.setStatus("testStatusTestUpdate");
        bid.setTrader("testTradeTestUpdate");
        bid.setBook("testBookTestUpdate");
        bid.setCreationName(null);
        bid.setCreationDate(LocalDateTime.now());
        bid.setRevisionName("testRevisionNameTestUpdate");
        bid.setRevisionDate(LocalDateTime.now());
        bid.setDealName("testDealNameTestUpdate");
        bid.setDealType("testDealTypeTestUpdate");
        bid.setSourceListId("testSourceListIdUpdate");

        when(bidListService.findById(anyInt())).thenReturn(bidList);

        this.mockMvc.perform(post("/bidList/update/{id}", 1).flashAttr("bidList", bid))
                .andExpect(model().attribute("success", false))
                .andExpect(model().attributeExists("messageList"))
                .andExpect(view().name("bidList/update"))
                .andExpect(status().isOk());
    }

    @DisplayName("10°) Test delete bidList par Id")
    @WithMockUser
    @Order(10)
    @Test
    void testDeleteBidShouldSuccess() throws Exception {
        BidList bidList = new BidList();
        bidList.setBidListId(1);
        bidList.setAccount("testAccountTest");

        when(bidListService.deleteById(anyInt())).thenReturn(true);

        this.mockMvc.perform(get("/bidList/delete/{id}", 1))
                .andExpect(model().attribute("success", true))
                .andExpect(model().attribute("message", "BidList avec id 1 à été supprimé"))
                .andExpect(view().name("bidList/list"))
                .andExpect(status().isOk());
    }

    @DisplayName("11°) Test delete bidList inexistant par Id")
    @WithMockUser
    @Order(11)
    @Test
    void testDeleteBidShouldFail() throws Exception {
        List<String> errorMessageList = new ArrayList<>();
        errorMessageList.add("Une erreur est survenue lors de la suppression du Bidlist");

        BidList bidList = new BidList();
        bidList.setBidListId(1);
        bidList.setAccount("testAccountTest");

        when(bidListService.deleteById(anyInt())).thenReturn(false);

        this.mockMvc.perform(get("/bidList/delete/{id}", 1))
                .andExpect(model().attribute("success", false))
                .andExpect(model().attributeExists("messageList"))
                .andExpect(model().attribute("messageList", errorMessageList))
                .andExpect(view().name("bidList/list"))
                .andExpect(status().isOk());
    }

}
