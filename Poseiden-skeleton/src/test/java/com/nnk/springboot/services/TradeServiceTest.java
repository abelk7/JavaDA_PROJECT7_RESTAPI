package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.impl.TradeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
public class TradeServiceTest {
    private ITradeService tradeService;
    @Mock
    private TradeRepository tradeRepository;

    @BeforeEach
    public void setup() {
        tradeService = new TradeService(tradeRepository);
    }

    @DisplayName(value = "1°) Recherche de tous les Trade")
    @Order(1)
    @Test
    void test_findAll_should_FindAll_Trade() {
        List<Trade> tradeList = new ArrayList<>();

        Trade trade1 = new Trade();
        Trade trade2 = new Trade();
        Trade trade3 = new Trade();
        Trade trade4 = new Trade();
        Trade trade5 = new Trade();

        tradeList.add(trade1);
        tradeList.add(trade2);
        tradeList.add(trade3);
        tradeList.add(trade4);
        tradeList.add(trade5);

        when(tradeRepository.findAll()).thenReturn(tradeList);

        List<Trade> result = tradeService.findAll();

        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(tradeList.size());
    }

    @DisplayName(value = "2°) Recherche de Trade par ID")
    @Order(2)
    @Test
    void test_findById_shoud_findTrade_By_Id() {
        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("accountTest");
        trade.setType("typeTest");
        trade.setBuyQuantity(5.00);
        trade.setSellQuantity(10.00);
        trade.setBuyPrice(30.00);
        trade.setSellPrice(60.00);
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

        when(tradeRepository.findById(anyInt())).thenReturn(Optional.of(trade));

        Trade tradeResult = tradeService.findById(trade.getTradeId());

        assertThat(tradeResult).isNotNull();
        assertThat(tradeResult.getTradeId()).isEqualTo(trade.getTradeId());
    }

    @DisplayName(value = "3°) Mise à jour d'un Trade Existant")
    @Order(3)
    @Test
    void test_update_should_update_Trade() {
        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("accountTest");
        trade.setType("typeTest");
        trade.setBuyQuantity(5.00);
        trade.setSellQuantity(10.00);
        trade.setBuyPrice(30.00);
        trade.setSellPrice(60.00);
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

        Trade tradeUpdated = new Trade();
        tradeUpdated.setTradeId(1);
        tradeUpdated.setAccount("accountTestUpdated");
        tradeUpdated.setType("typeTestUpdated");
        tradeUpdated.setBuyQuantity(15.00);
        tradeUpdated.setSellQuantity(20.00);
        tradeUpdated.setBuyPrice(40.00);
        tradeUpdated.setSellPrice(80.00);
        tradeUpdated.setBenchmark("benchmarkTestUpdated");
        tradeUpdated.setTradeDate(LocalDateTime.now());
        tradeUpdated.setSecurity("securityTestUpdated");
        tradeUpdated.setStatus("statusTestUpdated");
        tradeUpdated.setTrader("traderTestUpdated");
        tradeUpdated.setBook("bookTestUpdated");
        tradeUpdated.setCreationName("creationNameTestUpdated");
        tradeUpdated.setCreationDate(LocalDateTime.now());
        tradeUpdated.setRevisionName("revisionNameTestUpdated");
        tradeUpdated.setRevisionDate(LocalDateTime.now());
        tradeUpdated.setDealName("dealNameTestUpdated");
        tradeUpdated.setDealType("dealTypeTestUpdated");
        tradeUpdated.setSourceListId("sourceListIdTestUpdated");
        tradeUpdated.setSide("sideTestUpdated");

        when(tradeRepository.save(any(Trade.class))).thenReturn(tradeUpdated);

        Trade tradeResult = tradeService.save(trade);

        assertThat(tradeResult).isNotNull();
        assertThat(tradeResult.getTradeId()).isEqualTo(tradeUpdated.getTradeId());
        assertThat(tradeResult.getAccount()).isEqualTo(tradeUpdated.getAccount());
        assertThat(tradeResult.getType()).isEqualTo(tradeUpdated.getType());
        assertThat(tradeResult.getBuyQuantity()).isEqualTo(tradeUpdated.getBuyQuantity());
        assertThat(tradeResult.getSellQuantity()).isEqualTo(tradeUpdated.getSellQuantity());
        assertThat(tradeResult.getBuyPrice()).isEqualTo(tradeUpdated.getBuyPrice());
        assertThat(tradeResult.getSellPrice()).isEqualTo(tradeUpdated.getSellPrice());
        assertThat(tradeResult.getBenchmark()).isEqualTo(tradeUpdated.getBenchmark());
        assertThat(tradeResult.getTradeDate()).isEqualTo(tradeUpdated.getTradeDate());
        assertThat(tradeResult.getSecurity()).isEqualTo(tradeUpdated.getSecurity());
        assertThat(tradeResult.getStatus()).isEqualTo(tradeUpdated.getStatus());
        assertThat(tradeResult.getTrader()).isEqualTo(tradeUpdated.getTrader());
        assertThat(tradeResult.getBook()).isEqualTo(tradeUpdated.getBook());
        assertThat(tradeResult.getCreationName()).isEqualTo(tradeUpdated.getCreationName());
        assertThat(tradeResult.getCreationDate()).isEqualTo(tradeUpdated.getCreationDate());
        assertThat(tradeResult.getRevisionName()).isEqualTo(tradeUpdated.getRevisionName());
        assertThat(tradeResult.getRevisionDate()).isEqualTo(tradeUpdated.getRevisionDate());
        assertThat(tradeResult.getDealName()).isEqualTo(tradeUpdated.getDealName());
        assertThat(tradeResult.getDealType()).isEqualTo(tradeUpdated.getDealType());
        assertThat(tradeResult.getSourceListId()).isEqualTo(tradeUpdated.getSourceListId());
        assertThat(tradeResult.getSide()).isEqualTo(tradeUpdated.getSide());
    }

    @DisplayName(value = "4°) Suppression de Trade par ID")
    @Order(4)
    @Test
    void test_delete_shoud_deleteTrade_By_Id() {
        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("accountTest");
        trade.setType("typeTest");
        trade.setBuyQuantity(5.00);
        trade.setSellQuantity(10.00);
        trade.setBuyPrice(30.00);
        trade.setSellPrice(60.00);
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

        when(tradeRepository.findById(anyInt())).thenReturn(Optional.of(trade));

        boolean result = tradeService.deleteById(trade.getTradeId());

        assertThat(result).isTrue();
    }
}
