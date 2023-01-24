package com.nnk.springboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

/**
 * CurvePoint service
 * 
 * @author trimok
 */
@Service
public class CurvePointService implements ICurvePointService {

    /**
     * curvePointRepository
     */
    @Autowired
    private CurvePointRepository curvePointRepository;

    /**
     * Constructor
     * 
     * @param curvePointRepository : the curvePointRepository
     */
    @Autowired
    public CurvePointService(CurvePointRepository curvePointRepository) {
	this.curvePointRepository = curvePointRepository;
    }

    /**
     * findAllCurvePoints
     */
    @Override
    public List<CurvePoint> findAllCurvePoints() {
	return curvePointRepository.findAll();
    }

    /**
     * addCurvePoint
     */
    @Override
    public CurvePoint addCurvePoint(CurvePoint curvePoint) {
	return curvePointRepository.save(curvePoint);
    }

    /**
     * findCurvePointById
     */
    @Override
    public CurvePoint findCurvePointById(Integer id) {
	CurvePoint curvePoint = curvePointRepository.findById(id).orElse(null);
	return curvePoint;
    }

    /**
     * updateCurvePoint
     */
    @Override
    public CurvePoint updateCurvePoint(CurvePoint curvePoint) {
	return curvePointRepository.save(curvePoint);
    }

    /**
     * deleteCurvePoint
     */
    @Override
    public boolean deleteCurvePoint(Integer id) {
	CurvePoint curvePoint = curvePointRepository.findById(id).orElse(null);

	if (curvePoint != null) {
	    curvePointRepository.delete(curvePoint);
	    return true;
	} else {
	    return false;
	}
    }

    /**
     * deleteAllCurvePoint
     */
    @Override
    public void deleteAllCurvePoint() {
	curvePointRepository.deleteAll();
    }
}
