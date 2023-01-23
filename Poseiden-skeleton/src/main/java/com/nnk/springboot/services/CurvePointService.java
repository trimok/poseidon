package com.nnk.springboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

@Service
public class CurvePointService implements ICurvePointService {

    @Autowired
    private CurvePointRepository curvePointRepository;

    @Autowired
    public CurvePointService(CurvePointRepository curvePointRepository) {
	this.curvePointRepository = curvePointRepository;
    }

    @Override
    public List<CurvePoint> findAllCurvePoints() {
	return curvePointRepository.findAll();
    }

    @Override
    public CurvePoint addCurvePoint(CurvePoint curvePoint) {
	return curvePointRepository.save(curvePoint);
    }

    @Override
    public CurvePoint findCurvePointById(Integer id) {
	CurvePoint curvePoint = curvePointRepository.findById(id).orElse(null);
	return curvePoint;
    }

    @Override
    public CurvePoint updateCurvePoint(CurvePoint curvePoint) {
	return curvePointRepository.save(curvePoint);
    }

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

    @Override
    public void deleteAllCurvePoint() {
	curvePointRepository.deleteAll();
    }
}
