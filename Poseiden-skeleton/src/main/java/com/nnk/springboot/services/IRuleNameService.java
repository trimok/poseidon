package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.RuleName;

public interface IRuleNameService {

    List<RuleName> findAllRuleNames();

    RuleName addRuleName(RuleName rating);

    RuleName findRuleNameById(Integer id);

    RuleName updateRuleName(RuleName rating);

    boolean deleteRuleName(Integer id);

    void deleteAllRuleName();

}
