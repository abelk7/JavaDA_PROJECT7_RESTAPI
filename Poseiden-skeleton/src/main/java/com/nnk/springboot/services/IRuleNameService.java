package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;

import java.util.List;

public interface IRuleNameService {
    List<RuleName> findAll();

    RuleName findById(Integer id);

    RuleName save(RuleName user);

    boolean deleteById(Integer id);
}
