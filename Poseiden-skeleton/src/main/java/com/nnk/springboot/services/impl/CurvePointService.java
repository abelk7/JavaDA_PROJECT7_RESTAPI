package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.ICurvePointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurvePointService implements ICurvePointService {
    private final CurvePointRepository curvePointRepository;

    @Override
    public List<CurvePoint> findAll() {
        log.info("Fetching all curvePoint");
        return curvePointRepository.findAll();
    }

    @Override
    public CurvePoint findById(Integer id) {
        log.info("Fetching curvePoint by Id in database");
        Optional<CurvePoint> curvePoint = curvePointRepository.findById(id);
        if (curvePoint.isPresent()) {
            return curvePoint.get();
        }
        return null;
    }

    @Override
    public CurvePoint save(CurvePoint curvePoint) {
        log.info("Saving curvePoint in database");
        return curvePointRepository.save(curvePoint);
    }

    @Override
    public boolean deleteById(Integer id) {
        log.info("deleting curvePoint by Id in database");
        Optional<CurvePoint> curvePoint = curvePointRepository.findById(id);
        if (curvePoint.isPresent()) {
            curvePointRepository.delete(curvePoint.get());
            return true;
        }
        return false;
    }
}
