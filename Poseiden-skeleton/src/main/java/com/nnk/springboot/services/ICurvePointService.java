package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.CurvePoint;

import jakarta.validation.Valid;

public interface ICurvePointService {
    List<CurvePoint> findAllCurvePoints();

    CurvePoint addCurvePoint(CurvePoint rating);

    CurvePoint findCurvePointById(Integer id);

    CurvePoint updateCurvePoint(@Valid CurvePoint rating);

    boolean deleteCurvePoint(Integer id);

    void deleteAllCurvePoint();
}
