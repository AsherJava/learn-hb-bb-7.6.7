/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.nr.definition.facade.FormulaFunctionDefine;
import com.jiuqi.nr.definition.facade.FormulaFunctionParamterDefine;
import com.jiuqi.nr.definition.internal.dao.DesignFormulaFunctionDefineDao;
import com.jiuqi.nr.definition.internal.dao.DesignFormulaFunctionParamterDefineDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DesignFormulaFunctionDefineService {
    @Autowired
    private DesignFormulaFunctionDefineDao designFormulaFunctionDefineDao;
    @Autowired
    private DesignFormulaFunctionParamterDefineDao designFormulaFunctionParamterDefineDao;

    public List<FormulaFunctionDefine> queryAllFormulaFunction() {
        return this.designFormulaFunctionDefineDao.queryAllFormulaFunction();
    }

    public List<FormulaFunctionDefine> queryAllFormulaFunctionByType(int type) {
        return this.designFormulaFunctionDefineDao.queryAllFormulaFunctionByType(type);
    }

    public List<FormulaFunctionParamterDefine> queryAllFormulaFunctionParamter() {
        return this.designFormulaFunctionParamterDefineDao.queryAllFormulaFunctionParamter();
    }

    public void insertFormulaFunction(FormulaFunctionDefine[] formulaFunctionDefine) throws Exception {
        this.designFormulaFunctionDefineDao.insert(formulaFunctionDefine);
    }

    public void insertFormulaFunctionParamter(FormulaFunctionParamterDefine[] formulaFunctionParamterDefine) throws Exception {
        this.designFormulaFunctionParamterDefineDao.insert(formulaFunctionParamterDefine);
    }
}

