package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;

import java.util.List;

public interface ITradeService {
    List<Trade> findAll();

    Trade findById(Integer id);

    Trade save(Trade trade);

    boolean deleteById(Integer id);
}
