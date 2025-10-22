/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.FormulaFunctionParamterDefine;
import com.jiuqi.nr.definition.internal.impl.DesignFormulaFunctionParamterDefineImpl;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class DesignFormulaFunctionParamterDefineDao
extends BaseDao {
    private Class<DesignFormulaFunctionParamterDefineImpl> implClass = DesignFormulaFunctionParamterDefineImpl.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public List<FormulaFunctionParamterDefine> queryAllFormulaFunctionParamter() {
        List allDefines = this.list(this.implClass);
        return allDefines;
    }
}

