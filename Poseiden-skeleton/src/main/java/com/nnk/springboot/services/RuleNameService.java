package com.nnk.springboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;

@Service
public class RuleNameService implements IRuleNameService {

    @Autowired
    private RuleNameRepository ruleNameRepository;

    @Autowired
    public RuleNameService(RuleNameRepository ruleNameRepository) {
	this.ruleNameRepository = ruleNameRepository;
    }

    @Override
    public List<RuleName> findAllRuleNames() {
	return ruleNameRepository.findAll();
    }

    @Override
    public RuleName addRuleName(RuleName ruleName) {
	return ruleNameRepository.save(ruleName);
    }

    @Override
    public RuleName findRuleNameById(Integer id) {
	RuleName ruleName = ruleNameRepository.findById(id).orElse(null);
	return ruleName;
    }

    @Override
    public RuleName updateRuleName(RuleName ruleName) {
	return ruleNameRepository.save(ruleName);
    }

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

    @Override
    public void deleteAllRuleName() {
	ruleNameRepository.deleteAll();
    }
}