/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao.formula;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaCondition;
import com.jiuqi.nr.definition.internal.impl.formula.DesignFormulaConditionImpl;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class DesignFormulaConditionDao
extends BaseDao {
    public Class<DesignFormulaConditionImpl> getClz() {
        return DesignFormulaConditionImpl.class;
    }

    public DesignFormulaCondition getByTaskAndCode(String task, String code) {
        return (DesignFormulaCondition)this.getBy("FC_TASK=? AND FC_CODE=?", new Object[]{task, code}, this.getClz());
    }

    public List<DesignFormulaCondition> listFormulaConditions(List<String> keys) {
        String sbr = "FC_KEY IN (" + keys.stream().map(s -> "?").collect(Collectors.joining(",")) + ")";
        return this.list(sbr, keys.toArray(), this.getClz());
    }

    public List<DesignFormulaCondition> listFormulaConditions(String task) {
        return this.list("FC_TASK=?", new Object[]{task}, this.getClz());
    }

    public void updateFormulaConditions(List<DesignFormulaCondition> array) throws Exception {
        this.update(array.toArray());
    }

    public void deleteFormulaConditionByTask(String task) throws DBParaException {
        this.deleteBy(new String[]{"taskKey"}, new Object[]{task});
    }

    public void deleteFormulaConditions(List<String> keys) throws DBParaException {
        this.delete(keys.toArray());
    }
}

