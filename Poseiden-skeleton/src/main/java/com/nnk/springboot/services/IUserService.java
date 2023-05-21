package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;

import java.util.List;

public interface IUserService {
    List<User> findAll();

    User findById(Integer id);

    User findByUsername(String username);

    User save(User user);

    boolean deleteById(Integer id);
}
