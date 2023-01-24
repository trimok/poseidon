package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.CurvePoint;

import jakarta.validation.Valid;

/**
 * Interface ICurvePointService
 * 
 * @author trimok
 */
public interface ICurvePointService {
    /**
     * findAllCurvePoints
     * 
     * @return : the list of all the CurvePoint
     */
    List<CurvePoint> findAllCurvePoints();

    /**
     * addCurvePoint
     * 
     * @param curvePoint : the rating object to be added
     * @return : the rating object in the database, or null
     */
    CurvePoint addCurvePoint(CurvePoint curvePoint);

    /**
     * findCurvePointById
     * 
     * @param id : the id of the CurvePoint to be found
     * @return : the CurvePoint object
     */
    CurvePoint findCurvePointById(Integer id);

    /**
     * updateCurvePoint
     * 
     * @param curvePoint : the curvePoint updated object
     * @return : the curvePoint updated object in the databse
     */
    CurvePoint updateCurvePoint(@Valid CurvePoint curvePoint);

    /**
     * deleteCurvePoint
     * 
     * @param id : the id of the CurvePoint object to be deleted
     * @return : true if deleted, false if not deleted
     */
    boolean deleteCurvePoint(Integer id);

    /**
     * deleteAllCurvePoint
     */
    void deleteAllCurvePoint();
}
