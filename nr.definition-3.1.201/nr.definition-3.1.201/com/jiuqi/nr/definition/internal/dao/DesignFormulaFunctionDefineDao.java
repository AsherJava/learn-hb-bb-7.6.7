/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.FormulaFunctionDefine;
import com.jiuqi.nr.definition.internal.impl.DesignFormulaFunctionDefineImpl;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class DesignFormulaFunctionDefineDao
extends BaseDao {
    private static String ATTR_TYPE = "functiontype";
    private Class<DesignFormulaFunctionDefineImpl> implClass = DesignFormulaFunctionDefineImpl.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public List<FormulaFunctionDefine> queryAllFormulaFunction() {
        List allDefines = this.list(this.implClass);
        return allDefines;
    }

    public List<FormulaFunctionDefine> queryAllFormulaFunctionByType(int type) {
        List allDefines = this.list(new String[]{ATTR_TYPE}, new Object[]{type}, this.implClass);
        return allDefines;
    }
}

