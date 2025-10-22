/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 */
package com.jiuqi.nr.definition.internal.service.formula;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.definition.exception.DefinitonException;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaConditionLink;
import com.jiuqi.nr.definition.internal.dao.formula.DesignFormulaConditionLinkDao;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class DesignFormulaConditionLinkService {
    @Autowired
    private DesignFormulaConditionLinkDao conditionLinkDao;

    public List<DesignFormulaConditionLink> listConditionLinkByScheme(String formulaScheme) {
        if (formulaScheme == null) {
            return Collections.emptyList();
        }
        return this.conditionLinkDao.listConditionLinkByFormulaScheme(formulaScheme);
    }

    public int[] insert(List<DesignFormulaConditionLink> links) {
        if (CollectionUtils.isEmpty(links)) {
            return new int[0];
        }
        try {
            return this.conditionLinkDao.insert(links.toArray(new DesignFormulaConditionLink[0]));
        }
        catch (DBParaException e) {
            throw new DefinitonException(e);
        }
    }

    public void delete(List<DesignFormulaConditionLink> links) {
        if (CollectionUtils.isEmpty(links)) {
            return;
        }
        this.conditionLinkDao.delete(links);
    }

    public List<DesignFormulaConditionLink> listConditionLinkByCondition(List<String> conditionKeys) {
        if (CollectionUtils.isEmpty(conditionKeys)) {
            return Collections.emptyList();
        }
        return this.conditionLinkDao.listConditionLinksByCondition(conditionKeys);
    }

    public void deleteByConditionKey(String ... conditionKey) {
        if (conditionKey == null) {
            return;
        }
        this.conditionLinkDao.deleteByConditionKey(conditionKey);
    }

    public void deleteByScheme(String formulaScheme) {
        if (formulaScheme == null) {
            return;
        }
        try {
            this.conditionLinkDao.deleteByScheme(formulaScheme);
        }
        catch (DBParaException e) {
            throw new DefinitonException(e);
        }
    }

    public void deleteByFormula(String ... formulaKey) {
        if (formulaKey == null) {
            return;
        }
        this.conditionLinkDao.deleteByFormula(formulaKey);
    }
}

