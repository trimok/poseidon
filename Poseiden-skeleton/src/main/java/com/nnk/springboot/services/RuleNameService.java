package com.nnk.springboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;

/**
 * the RuleName Service
 * 
 * @author trimok
 */
@Service
public class RuleNameService implements IRuleNameService {

    /**
     * ruleNameRepository
     */
    @Autowired
    private RuleNameRepository ruleNameRepository;

    /**
     * 
     * Constructor
     * 
     * @param ruleNameRepository : ruleNameRepository
     */
    @Autowired
    public RuleNameService(RuleNameRepository ruleNameRepository) {
	this.ruleNameRepository = ruleNameRepository;
    }

    /**
     * findAllRuleNames
     */
    @Override
    public List<RuleName> findAllRuleNames() {
	return ruleNameRepository.findAll();
    }

    /**
     * addRuleName
     */
    @Override
    public RuleName addRuleName(RuleName ruleName) {
	return ruleNameRepository.save(ruleName);
    }

    /**
     * findRuleNameById
     */
    @Override
    public RuleName findRuleNameById(Integer id) {
	RuleName ruleName = ruleNameRepository.findById(id).orElse(null);
	return ruleName;
    }

    /**
     * updateRuleName
     */
    @Override
    public RuleName updateRuleName(RuleName ruleName) {
	return ruleNameRepository.save(ruleName);
    }

    /**
     * deleteRuleName
     */
    @Override
    public boolean deleteRuleName(Integer id) {
	RuleName ruleName = ruleNameRepository.findById(id).orElse(null);

	if (ruleName != null) {
	    ruleNameRepository.delete(ruleName);
	    return true;
	} else {
	    return false;
	}
    }

    /**
     * deleteAllRuleName
     */
    @Override
    public void deleteAllRuleName() {
	ruleNameRepository.deleteAll();
    }
}