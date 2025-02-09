package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.impl.BidListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class BidListServiceTest {
    private IBidListService bidListService;
    @Mock
    private BidListRepository bidListRepository;

    @BeforeEach
    public void setup() {
        bidListService = new BidListService(bidListRepository);
    }

    @DisplayName(value = "1°) Recherche de tous les BidLists")
    @Order(1)
    @Test
    void test_findAll_should_FindAll_BidList() {
        List<BidList> bidListList = new ArrayList<>();

        BidList bidList = new BidList();
        BidList bidList2 = new BidList();
        BidList bidList3 = new BidList();
        bidListList.add(bidList);
        bidListList.add(bidList2);
        bidListList.add(bidList3);

        when(bidListRepository.findAll()).thenReturn(bidListList);

        List<BidList> result = bidListService.findAll();

        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(bidListList.size());
    }

    @DisplayName(value = "2°) Recherche de BidList par ID")
    @Order(2)
    @Test
    void test_findById_shoud_findBidList_By_Id() {
        BidList bidList = new BidList();
        bidList.setBidListId(1);

        when(bidListRepository.findById(anyInt())).thenReturn(Optional.of(bidList));

        BidList bidListResult = bidListService.findById(bidList.getBidListId());

        assertThat(bidListResult).isNotNull();
        assertThat(bidListResult.getBidListId()).isEqualTo(bidList.getBidListId());
    }

    @DisplayName(value = "3°) Mise à jour d'un BidList Existant")
    @Order(3)
    @Test
    void test_update_should_update_BidList() throws ParseException {
//        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        Date date = dateFormat.parse("02/09/2024");

        BidList bidList = new BidList();
        bidList.setBidListId(1);
        bidList.setAccount("testAccount");
        bidList.setType("testType");
        bidList.setBidQuantity(10.00);
        bidList.setBid(1.0);
        bidList.setAsk(10.00);
        bidList.setBenchmark("testBencjmark");
        bidList.setAskQuantity(10.11);
        bidList.setBidListDate(LocalDateTime.now());
        bidList.setCommentary("testCommentary");
        bidList.setSecurity("testSecurity");
        bidList.setStatus("testStatus");
        bidList.setTrader("testTrade");
        bidList.setBook("testBook");
        bidList.setCreationName("testCreationName");
        bidList.setCreationDate(LocalDateTime.now());
        bidList.setRevisionName("testRevisionName");
        bidList.setRevisionDate(LocalDateTime.now());
        bidList.setDealName("testDealName");
        bidList.setDealType("testDealType");
        bidList.setSourceListId("testSourceListId");
        bidList.setSide("testSide");

        BidList bidListUpdated = new BidList();
        bidListUpdated.setBidListId(1);
        bidListUpdated.setAccount("testSuccessAccountUpdated");
        bidListUpdated.setType("testSuccessTypeUpdated");
        bidListUpdated.setBidQuantity(20.00);
        bidListUpdated.setBid(20.0);
        bidListUpdated.setAsk(200.00);
        bidListUpdated.setBenchmark("testSuccessBencjmarkUpdated");
        bidListUpdated.setAskQuantity(22.22);
        bidListUpdated.setBidListDate(LocalDateTime.of(2024,9,02,16,30));
        bidListUpdated.setCommentary("testSuccessCommentaryUpdated");
        bidListUpdated.setSecurity("testSuccessSecurityUpdated");
        bidListUpdated.setStatus("testSuccessStatusUpdated");
        bidListUpdated.setTrader("testSuccessTradeUpdated");
        bidListUpdated.setBook("testSuccessBookUpdated");
        bidListUpdated.setCreationName("testSuccessCreationNameUpdated");
        bidListUpdated.setCreationDate(LocalDateTime.of(2024,9,02,16,30));
        bidListUpdated.setRevisionName("testSuccessRrevisionNameUpdated");
        bidListUpdated.setRevisionDate(LocalDateTime.of(2024,9,02,16,30));
        bidListUpdated.setDealName("testSuccessDealNameUpdated");
        bidListUpdated.setDealType("testSuccessDealTypeUpdated");
        bidListUpdated.setSourceListId("testSuccessSourceListIdUpdated");
        bidListUpdated.setSide("testSuccessSideUpdated");

        when(bidListRepository.save(any(BidList.class))).thenReturn(bidListUpdated);

        BidList bidListResult = bidListService.save(bidList);

        assertThat(bidListResult).isNotNull();
        assertThat(bidListResult.getBidListId()).isEqualTo(bidListUpdated.getBidListId());
        assertThat(bidListResult.getAccount()).isEqualTo(bidListUpdated.getAccount());
        assertThat(bidListResult.getType()).isEqualTo(bidListUpdated.getType());
        assertThat(bidListResult.getBidQuantity()).isEqualTo(bidListUpdated.getBidQuantity());
        assertThat(bidListResult.getBid()).isEqualTo(bidListUpdated.getBid());
        assertThat(bidListResult.getAsk()).isEqualTo(bidListUpdated.getAsk());
        assertThat(bidListResult.getBenchmark()).isEqualTo(bidListUpdated.getBenchmark());
        assertThat(bidListResult.getAskQuantity()).isEqualTo(bidListUpdated.getAskQuantity());
        assertThat(bidListResult.getBidListDate()).isEqualTo(bidListUpdated.getBidListDate());
        assertThat(bidListResult.getCommentary()).isEqualTo(bidListUpdated.getCommentary());
        assertThat(bidListResult.getSecurity()).isEqualTo(bidListUpdated.getSecurity());
        assertThat(bidListResult.getStatus()).isEqualTo(bidListUpdated.getStatus());
        assertThat(bidListResult.getTrader()).isEqualTo(bidListUpdated.getTrader());
        assertThat(bidListResult.getBook()).isEqualTo(bidListUpdated.getBook());
        assertThat(bidListResult.getCreationName()).isEqualTo(bidListUpdated.getCreationName());
        assertThat(bidListResult.getCreationDate()).isEqualTo(bidListUpdated.getCreationDate());
        assertThat(bidListResult.getRevisionName()).isEqualTo(bidListUpdated.getRevisionName());
        assertThat(bidListResult.getRevisionDate()).isEqualTo(bidListUpdated.getRevisionDate());
        assertThat(bidListResult.getDealName()).isEqualTo(bidListUpdated.getDealName());
        assertThat(bidListResult.getDealType()).isEqualTo(bidListUpdated.getDealType());
        assertThat(bidListResult.getSourceListId()).isEqualTo(bidListUpdated.getSourceListId());
        assertThat(bidListResult.getSide()).isEqualTo(bidListUpdated.getSide());
    }

    @DisplayName(value = "4°) Suppression de BidList par ID")
    @Order(4)
    @Test
    void test_delete_shoud_deleteBidList_By_Id() {
        BidList bidList = new BidList();
        bidList.setBidListId(1);
        bidList.setAccount("testAccount");
        bidList.setType("testType");
        bidList.setBidQuantity(10.00);
        bidList.setBid(1.0);
        bidList.setAsk(10.00);
        bidList.setBenchmark("testBencjmark");
        bidList.setAskQuantity(10.11);
        bidList.setBidListDate(LocalDateTime.now());
        bidList.setCommentary("testCommentary");
        bidList.setSecurity("testSecurity");
        bidList.setStatus("testStatus");
        bidList.setTrader("testTrade");
        bidList.setBook("testBook");
        bidList.setCreationName("testCreationName");
        bidList.setCreationDate(LocalDateTime.now());
        bidList.setRevisionName("testRevisionName");
        bidList.setRevisionDate(LocalDateTime.now());
        bidList.setDealName("testDealName");
        bidList.setDealType("testDealType");
        bidList.setSourceListId("testSourceListId");
        bidList.setSide("testSide");

        when(bidListRepository.findById(anyInt())).thenReturn(Optional.of(bidList));

        boolean result = bidListService.deleteById(bidList.getBidListId());

        assertThat(result).isTrue();
    }

}
