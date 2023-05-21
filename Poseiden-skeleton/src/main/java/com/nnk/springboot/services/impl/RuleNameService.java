package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.IRuleNameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RuleNameService implements IRuleNameService {
    private final RuleNameRepository ruleNameRepository;

    @Override
    public List<RuleName> findAll() {
        log.info("Fetching all ruleName");
        return ruleNameRepository.findAll();
    }

    @Override
    public RuleName findById(Integer id) {
        log.info("Fetching ruleName by Id in database");
        Optional<RuleName> ruleName = ruleNameRepository.findById(id);
        if (ruleName.isPresent()) {
            return ruleName.get();
        }
        return null;
    }

    @Override
    public RuleName save(RuleName ruleName) {
        log.info("Saving ruleName in database");
        return ruleNameRepository.save(ruleName);
    }

    @Override
    public boolean deleteById(Integer id) {
        log.info("deleting ruleName by Id in database");
        Optional<RuleName> ruleName = ruleNameRepository.findById(id);
        if (ruleName.isPresent()) {
            ruleNameRepository.delete(ruleName.get());
            return true;
        }
        return false;
    }
}
