/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.nr.definition.facade.FormulaUnitGroup
 */
package com.jiuqi.nr.data.logic.internal.helper;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.definition.facade.FormulaUnitGroup;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class FormulaGetter {
    private final List<FormulaUnitGroup> formulaUnitGroups;

    public FormulaGetter(List<FormulaUnitGroup> formulaUnitGroups) {
        this.formulaUnitGroups = formulaUnitGroups;
    }

    public List<String> getFormFromFormula(DataEngineConsts.FormulaType formulaType, List<String> formulaKeys) {
        if (!CollectionUtils.isEmpty(this.formulaUnitGroups)) {
            HashSet all = new HashSet();
            this.formulaUnitGroups.forEach(o -> o.getFormulaList().forEach(e -> {
                if (formulaType == e.getFormulaType() && (o.getUnit() != null || CollectionUtils.isEmpty(formulaKeys) || formulaKeys.contains(e.getSource().getId()))) {
                    all.add(e);
                }
            }));
            return all.stream().map(IParsedExpression::getFormKey).distinct().collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public List<FormulaUnitGroup> getFormulaUnitGroups() {
        return this.formulaUnitGroups;
    }

    public Map<String, List<IParsedExpression>> toMap() {
        if (!CollectionUtils.isEmpty(this.formulaUnitGroups)) {
            HashMap<String, List<IParsedExpression>> result = new HashMap<String, List<IParsedExpression>>();
            this.formulaUnitGroups.forEach(o -> {
                if (!CollectionUtils.isEmpty(o.getFormulaList())) {
                    if (StringUtils.isEmpty((String)o.getUnit())) {
                        result.put("00000000-0000-0000-0000-000000000000", o.getFormulaList());
                    } else {
                        result.put(o.getUnit(), o.getFormulaList());
                    }
                }
            });
            return result;
        }
        return Collections.emptyMap();
    }
}

