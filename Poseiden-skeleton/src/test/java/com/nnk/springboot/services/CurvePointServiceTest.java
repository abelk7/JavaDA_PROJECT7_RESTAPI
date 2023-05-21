package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.impl.CurvePointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class CurvePointServiceTest {
    private ICurvePointService curvePointService;
    @Mock
    private CurvePointRepository curvePointRepository;

    @BeforeEach
    public void setup() {
        curvePointService = new CurvePointService(curvePointRepository);
    }

    @DisplayName(value = "1°) Recherche de tous les CurvePoints")
    @Order(1)
    @Test
    void test_findAll_should_FindAll_CurvePoint() {
        List<CurvePoint> curvePointList = new ArrayList<>();

        CurvePoint curvePoint = new CurvePoint();
        CurvePoint curvePoint2 = new CurvePoint();
        CurvePoint curvePoint3 = new CurvePoint();
        CurvePoint curvePoint4 = new CurvePoint();
        CurvePoint curvePoint5 = new CurvePoint();

        curvePointList.add(curvePoint);
        curvePointList.add(curvePoint2);
        curvePointList.add(curvePoint3);
        curvePointList.add(curvePoint4);
        curvePointList.add(curvePoint5);

        when(curvePointRepository.findAll()).thenReturn(curvePointList);

        List<CurvePoint> result = curvePointService.findAll();

        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(curvePointList.size());
    }

    @DisplayName(value = "2°) Recherche de CurvePoint par ID")
    @Order(2)
    @Test
    void test_findById_shoud_findCurvePoint_By_Id() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = dateFormat.parse("02/09/2023");

        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(1);
        curvePoint.setAsOfDate(LocalDateTime.of(2023,7,15,8,00));
        curvePoint.setTerm(1.0);
        curvePoint.setValue(5.2);
        curvePoint.setCreationDate(LocalDateTime.of(2023,7,15,8,00));

        when(curvePointRepository.findById(anyInt())).thenReturn(Optional.of(curvePoint));

        CurvePoint curvePointResult = curvePointService.findById(curvePoint.getId());

        assertThat(curvePointResult).isNotNull();
        assertThat(curvePointResult.getId()).isEqualTo(curvePoint.getId());
    }

    @DisplayName(value = "3°) Mise à jour d'un CurvePoint Existant")
    @Order(3)
    @Test
    void test_update_should_update_CurvePoint() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = dateFormat.parse("02/05/2023");

        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(1);
        curvePoint.setAsOfDate(LocalDateTime.now());
        curvePoint.setTerm(10.0);
        curvePoint.setValue(15.50);
        curvePoint.setCreationDate(LocalDateTime.now());

        CurvePoint curvePointUdate = new CurvePoint();
        curvePointUdate.setId(1);
        curvePointUdate.setCurveId(3);
        curvePointUdate.setAsOfDate(LocalDateTime.now());
        curvePointUdate.setTerm(50.0);
        curvePointUdate.setValue(40.50);
        curvePointUdate.setCreationDate(LocalDateTime.now());

        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePointUdate);

        CurvePoint curvePointResult = curvePointService.save(curvePoint);

        assertThat(curvePointResult).isNotNull();
        assertThat(curvePointResult.getId()).isEqualTo(curvePointUdate.getId());
        assertThat(curvePointResult.getCurveId()).isEqualTo(curvePointUdate.getCurveId());
        assertThat(curvePointResult.getAsOfDate()).isEqualTo(curvePointUdate.getAsOfDate());
        assertThat(curvePointResult.getTerm()).isEqualTo(curvePointUdate.getTerm());
        assertThat(curvePointResult.getValue()).isEqualTo(curvePointUdate.getValue());
        assertThat(curvePointResult.getCreationDate()).isEqualTo(curvePointUdate.getCreationDate());
    }

    @DisplayName(value = "4°) Suppression de CurvePoint par ID")
    @Order(4)
    @Test
    void test_delete_shoud_deleteCurvePoint_By_Id() {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(1);
        curvePoint.setAsOfDate(LocalDateTime.now());
        curvePoint.setTerm(10.0);
        curvePoint.setValue(15.50);
        curvePoint.setCreationDate(LocalDateTime.now());

        when(curvePointRepository.findById(anyInt())).thenReturn(Optional.of(curvePoint));

        boolean result = curvePointService.deleteById(curvePoint.getId());

        assertThat(result).isTrue();
    }
}
