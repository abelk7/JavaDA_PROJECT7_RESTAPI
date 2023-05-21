package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;

import java.util.List;

public interface ICurvePointService {
    List<CurvePoint> findAll();

    CurvePoint findById(Integer id);

    CurvePoint save(CurvePoint curvePoint);

    boolean deleteById(Integer id);
}
