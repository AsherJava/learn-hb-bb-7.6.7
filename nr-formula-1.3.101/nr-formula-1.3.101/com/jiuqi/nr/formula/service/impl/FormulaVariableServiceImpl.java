/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.api.IDesignTimeFormulaController
 *  com.jiuqi.nr.definition.facade.FormulaVariDefine
 *  com.jiuqi.nr.definition.util.ServeCodeService
 */
package com.jiuqi.nr.formula.service.impl;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.api.IDesignTimeFormulaController;
import com.jiuqi.nr.definition.facade.FormulaVariDefine;
import com.jiuqi.nr.definition.util.ServeCodeService;
import com.jiuqi.nr.formula.dto.VariableDTO;
import com.jiuqi.nr.formula.service.IFormulaVariableService;
import com.jiuqi.nr.formula.utils.convert.VariableConvert;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class FormulaVariableServiceImpl
implements IFormulaVariableService {
    @Autowired
    private IDesignTimeFormulaController formulaDesignTimeController;
    @Autowired
    private ServeCodeService serveCodeService;

    @Override
    public void insertVariable(VariableDTO value) {
        this.setServeCode(value);
        FormulaVariDefine define = VariableConvert.dtoToDefine(value);
        this.formulaDesignTimeController.insertFormulaVariable(define);
    }

    private void setServeCode(VariableDTO value) {
        try {
            value.setLevel(this.serveCodeService.getServeCode());
        }
        catch (JQException e) {
            value.setLevel("0");
        }
    }

    @Override
    public void updateVariable(VariableDTO value) {
        this.setServeCode(value);
        FormulaVariDefine define = VariableConvert.dtoToDefine(value);
        this.formulaDesignTimeController.updateFormulaVariable(define);
    }

    @Override
    public void deleteVariable(String key) {
        this.formulaDesignTimeController.deleteFormulaVariable(key);
    }

    @Override
    public boolean existVariable(String formSchemeKey, String code, String key) {
        FormulaVariDefine variableDefine = this.formulaDesignTimeController.getFormulaVariByCodeAndFormScheme(code, formSchemeKey);
        if (variableDefine == null) {
            return false;
        }
        if (StringUtils.hasText(key)) {
            return !variableDefine.getKey().equals(key);
        }
        return true;
    }

    @Override
    public List<VariableDTO> listVariablesByFormScheme(String formSchemeKey) {
        List defines = this.formulaDesignTimeController.listFormulaVariByFormScheme(formSchemeKey);
        return VariableConvert.defineToDtoList(defines);
    }
}

