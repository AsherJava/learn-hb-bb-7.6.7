/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.nr.definition.facade.FormulaVariDefine;
import com.jiuqi.nr.definition.internal.dao.DesignFormulaVariableDefineDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DesignFormulaVariableDefineService {
    @Autowired
    private DesignFormulaVariableDefineDao formulaVariableDefineDao;
    private final String FORMSCHEME = "formSchemeKey";

    public List<FormulaVariDefine> queryAllFormulaVariable(String formSchemeKey) {
        return this.formulaVariableDefineDao.queryAllFormulaVariable(formSchemeKey);
    }

    public List<FormulaVariDefine> queryFormulaVariableByCodeOrTitle(String formSchemeKey, String searchContent) throws Exception {
        return this.formulaVariableDefineDao.queryFormulaVariableByCodeOrTitle(formSchemeKey, searchContent);
    }

    public List<FormulaVariDefine> queryFormulaVariableByCode(String formSchemeKey, String code) {
        return this.formulaVariableDefineDao.queryFormulaVariableByCode(formSchemeKey, code);
    }

    public void addFormulaVariable(FormulaVariDefine designFormulaVariableDefine) throws Exception {
        this.formulaVariableDefineDao.insert(designFormulaVariableDefine);
    }

    public void updateFormulaVariable(FormulaVariDefine designFormulaVariableDefine) throws Exception {
        this.formulaVariableDefineDao.update(designFormulaVariableDefine);
    }

    public void deleteFormulaVariable(String formulaVariableKey) throws Exception {
        this.formulaVariableDefineDao.delete(formulaVariableKey);
    }

    public void deleteFormulaVariByFormScheme(String formSchemeKey) throws Exception {
        this.formulaVariableDefineDao.deleteBy(new String[]{"formSchemeKey"}, new Object[]{formSchemeKey});
    }
}

