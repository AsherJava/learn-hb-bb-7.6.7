/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 */
package com.jiuqi.nr.definition.internal.runtime.service;

import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.definition.facade.formula.FormulaCondition;
import com.jiuqi.nr.definition.facade.formula.FormulaConditionLink;
import com.jiuqi.nr.definition.internal.dao.formula.RunTimeFormulaConditionDao;
import com.jiuqi.nr.definition.internal.dao.formula.RunTimeFormulaConditionLinkDao;
import com.jiuqi.nr.definition.internal.runtime.controller.IRunTimeFormulaConditionService;
import com.jiuqi.nr.definition.internal.runtime.service.NrFormulaCacheService;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class RunTimeFormulaConditionService
implements IRunTimeFormulaConditionService {
    @Autowired
    private RunTimeFormulaConditionDao runTimeFormulaConditionDao;
    @Autowired
    private RunTimeFormulaConditionLinkDao runTimeFormulaConditionLinkDao;
    @Autowired
    private NrFormulaCacheService formulaCacheService;

    @Override
    public Map<String, List<IParsedExpression>> getParsedFormulaConditionExpression(String formulaSchemeKey, String ... formulaKeys) {
        if (formulaSchemeKey == null || formulaKeys == null) {
            return Collections.emptyMap();
        }
        return this.formulaCacheService.getParsedExpressionByFormulas(formulaSchemeKey, formulaKeys).stream().filter(f -> Objects.nonNull(f.getSource())).collect(Collectors.toMap(k -> k.getSource().getId(), IParsedExpression::getConditions, (k1, k2) -> k2));
    }

    @Override
    public List<FormulaCondition> listFormulaConditionByScheme(String formulaScheme, String ... formulaKeys) {
        List<String> keys;
        if (formulaScheme == null) {
            return Collections.emptyList();
        }
        List<FormulaConditionLink> formulaConditionLinks = this.runTimeFormulaConditionLinkDao.listConditionLinkByFormulaScheme(formulaScheme);
        if (formulaKeys == null || formulaKeys.length != 0) {
            keys = formulaConditionLinks.stream().map(FormulaConditionLink::getConditionKey).distinct().collect(Collectors.toList());
        } else {
            HashSet<String> strings = new HashSet<String>(Arrays.asList(formulaKeys));
            keys = formulaConditionLinks.stream().filter(l -> strings.contains(l.getFormulaKey())).map(FormulaConditionLink::getConditionKey).distinct().collect(Collectors.toList());
        }
        return this.runTimeFormulaConditionDao.listFormulaConditions(keys);
    }

    @Override
    public List<FormulaCondition> listFormulaConditionByTask(String task) {
        if (task == null) {
            return Collections.emptyList();
        }
        return this.runTimeFormulaConditionDao.listFormulaConditionsByTask(task);
    }

    @Override
    public List<FormulaCondition> listFormulaConditionByKey(List<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptyList();
        }
        return this.runTimeFormulaConditionDao.listFormulaConditions(keys);
    }

    @Override
    public List<FormulaConditionLink> listConditionLinkByScheme(String formulaScheme) {
        if (formulaScheme == null) {
            return Collections.emptyList();
        }
        return this.runTimeFormulaConditionLinkDao.listConditionLinkByFormulaScheme(formulaScheme);
    }

    @Override
    public List<FormulaConditionLink> listConditionLinksByCondition(List<String> conditionKeys) {
        if (CollectionUtils.isEmpty(conditionKeys)) {
            return Collections.emptyList();
        }
        return this.runTimeFormulaConditionLinkDao.listConditionLinksByCondition(conditionKeys);
    }
}

