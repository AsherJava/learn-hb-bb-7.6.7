/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao.formula;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.formula.FormulaConditionLink;
import com.jiuqi.nr.definition.internal.impl.formula.FormulaConditionLinkImpl;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class RunTimeFormulaConditionLinkDao
extends BaseDao {
    public Class<FormulaConditionLinkImpl> getClz() {
        return FormulaConditionLinkImpl.class;
    }

    public List<FormulaConditionLink> listConditionLinkByFormulaScheme(String formulaScheme) {
        return this.list(new String[]{"formulaSchemeKey"}, new Object[]{formulaScheme}, this.getClz());
    }

    public List<FormulaConditionLink> listConditionLinksByCondition(List<String> conditionKeys) {
        String sbr = "CL_KEY IN (" + conditionKeys.stream().map(s -> "?").collect(Collectors.joining(",")) + ")";
        return this.list(sbr, conditionKeys.toArray(), this.getClz());
    }
}

