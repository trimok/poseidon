package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.RuleName;

/**
 * interface IRuleNameService
 * 
 * @author trimok
 */
public interface IRuleNameService {

    /**
     * findAllRuleNames
     * 
     * @return : the list of all RuleName
     */
    List<RuleName> findAllRuleNames();

    /**
     * addRuleName
     * 
     * @param ruleName : the ruleName to be added
     * @return : the added ruleName
     */
    RuleName addRuleName(RuleName ruleName);

    /**
     * findRuleNameById
     * 
     * @param id : the id of the ruleName to be found
     * @return : the found ruleName
     */
    RuleName findRuleNameById(Integer id);

    /**
     * updateRuleName
     * 
     * @param ruleName : the updated ruleName
     * @return : the updated ruleName in the databse
     */
    RuleName updateRuleName(RuleName ruleName);

    /**
     * deleteRuleName
     * 
     * @param id : the id of the ruleName to be deleted
     * @return : true if deleted, false if not deleted
     */
    boolean deleteRuleName(Integer id);

    /**
     * deleteAllRuleName
     */
    void deleteAllRuleName();

}
