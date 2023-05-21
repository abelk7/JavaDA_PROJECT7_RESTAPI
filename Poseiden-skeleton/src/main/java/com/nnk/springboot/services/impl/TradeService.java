package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.ITradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TradeService implements ITradeService {
    private final TradeRepository tradeRepository;

    @Override
    public List<Trade> findAll() {
        log.info("Fetching all trade");
        return tradeRepository.findAll();
    }

    @Override
    public Trade findById(Integer id) {
        log.info("Fetching trade by Id in database");
        Optional<Trade> trade = tradeRepository.findById(id);
        if (trade.isPresent()) {
            return trade.get();
        }
        return null;
    }

    @Override
    public Trade save(Trade trade) {
        log.info("Saving trade in database");
        return tradeRepository.save(trade);
    }

    @Override
    public boolean deleteById(Integer id) {
        log.info("deleting trade by Id in database");
        Optional<Trade> trade = tradeRepository.findById(id);
        if (trade.isPresent()) {
            tradeRepository.delete(trade.get());
            return true;
        }
        return false;
    }
}
