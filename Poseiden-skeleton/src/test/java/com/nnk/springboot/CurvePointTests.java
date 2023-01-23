package com.nnk.springboot;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

@SpringBootTest
@ActiveProfiles("test")
public class CurvePointTests {

    @Autowired
    private CurvePointRepository curvePointRepository;

    @Test
    public void curvePointTest() {
	CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);

	// Save
	curvePoint = curvePointRepository.save(curvePoint);
	assertNotNull(curvePoint.getId());
	assertTrue(curvePoint.getCurveId() == 10);

	// Update
	curvePoint.setCurveId(20);
	curvePoint = curvePointRepository.save(curvePoint);
	assertTrue(curvePoint.getCurveId() == 20);

	// Find
	List<CurvePoint> listResult = curvePointRepository.findAll();
	assertTrue(listResult.size() > 0);

	// Delete
	Integer id = curvePoint.getId();
	curvePointRepository.delete(curvePoint);
	Optional<CurvePoint> curvePointList = curvePointRepository.findById(id);
	assertFalse(curvePointList.isPresent());
    }
}
