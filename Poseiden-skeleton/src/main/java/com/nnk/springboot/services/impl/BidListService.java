package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.IBidListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BidListService implements IBidListService {
    private final BidListRepository bidListRepository;

    @Override
    public List<BidList> findAll() {
        log.info("Fetching all bidList");
        return bidListRepository.findAll();
    }

    @Override
    public BidList findById(Integer id) {
        log.info("Fetching bidList by Id in database");
        Optional<BidList> bidList = bidListRepository.findById(id);
        if (bidList.isPresent()) {
            return bidList.get();
        }
        return null;
    }

    @Override
    public BidList save(BidList bidList) {
        log.info("Saving bidList in database");
        return bidListRepository.save(bidList);
    }

    @Override
    public boolean deleteById(Integer id) {
        log.info("deleting bidList by Id in database");
        Optional<BidList> bidList = bidListRepository.findById(id);
        if (bidList.isPresent()) {
            bidListRepository.delete(bidList.get());
            return true;
        }
        return false;
    }
}
