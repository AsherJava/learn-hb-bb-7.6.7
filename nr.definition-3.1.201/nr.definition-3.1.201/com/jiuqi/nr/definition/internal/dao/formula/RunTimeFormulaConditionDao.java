/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao.formula;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.formula.FormulaCondition;
import com.jiuqi.nr.definition.internal.impl.formula.FormulaConditionImpl;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class RunTimeFormulaConditionDao
extends BaseDao {
    public Class<FormulaConditionImpl> getClz() {
        return FormulaConditionImpl.class;
    }

    public List<FormulaCondition> listFormulaConditions(List<String> keys) {
        if (!CollectionUtils.isEmpty(keys)) {
            String sbr = "FC_KEY IN (" + keys.stream().map(s -> "?").collect(Collectors.joining(",")) + ")";
            return this.list(sbr, keys.toArray(), this.getClz());
        }
        return Collections.emptyList();
    }

    public List<FormulaCondition> listFormulaConditionsByTask(String task) {
        return this.list("FC_TASK=?", new Object[]{task}, this.getClz());
    }
}

