/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.formulamapping.IFormulaMappingController
 *  com.jiuqi.nr.definition.formulamapping.bean.FormulaMappingDefine
 *  com.jiuqi.nr.definition.formulamapping.bean.FormulaMappingSchemeDefine
 *  com.jiuqi.nr.definition.formulamapping.facade.FormSchemeAndTaskKeysObj
 *  com.jiuqi.nr.definition.formulamapping.facade.FormulaMappingObj
 *  com.jiuqi.nr.definition.formulamapping.facade.FormulaMappingSchemeObj
 *  com.jiuqi.nr.definition.formulamapping.facade.QueryFormulaObj
 */
package com.jiuqi.nr.formulamapping.service.impl;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.formulamapping.IFormulaMappingController;
import com.jiuqi.nr.definition.formulamapping.bean.FormulaMappingDefine;
import com.jiuqi.nr.definition.formulamapping.bean.FormulaMappingSchemeDefine;
import com.jiuqi.nr.definition.formulamapping.facade.FormSchemeAndTaskKeysObj;
import com.jiuqi.nr.definition.formulamapping.facade.FormulaMappingObj;
import com.jiuqi.nr.definition.formulamapping.facade.FormulaMappingSchemeObj;
import com.jiuqi.nr.definition.formulamapping.facade.QueryFormulaObj;
import com.jiuqi.nr.formulamapping.service.FormulaMappingSchemeService;
import com.jiuqi.nr.formulamapping.service.IFormulaMappingService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormulaMappingController
implements IFormulaMappingController {
    @Autowired
    private IFormulaMappingService iFormulaMappingService;
    @Autowired
    private FormulaMappingSchemeService formulaMappingSchemeService;

    public List<QueryFormulaObj> queryFormulas(String formulaSchemeKey, String groupKey, String formKey) throws JQException {
        return this.iFormulaMappingService.queryFormulas(formulaSchemeKey, groupKey, formKey);
    }

    public String querySourceFormulaSchemeKey(String targetFormulaSchemeKey) {
        return this.iFormulaMappingService.querySourceFormulaSchemeKey(targetFormulaSchemeKey);
    }

    public List<FormulaMappingDefine> queryFormulaMappings(String targetFormulaSchemeKey) {
        return this.iFormulaMappingService.queryFormulaMappings(targetFormulaSchemeKey);
    }

    public String querySourceFormulaSchemeKey(String targetFormulaSchemeKey, String sourceSchemeKey) {
        return this.iFormulaMappingService.querySourceFormulaSchemeKey(targetFormulaSchemeKey, sourceSchemeKey);
    }

    public List<FormulaMappingDefine> queryValidFormulaMappings(String targetFormulaSchemeKey, String sourceSchemeKey) {
        return this.iFormulaMappingService.queryValidFormulaMappings(targetFormulaSchemeKey, sourceSchemeKey);
    }

    public List<String> querySourceFormulaSchemes(String targetFormulaSchemeKey) {
        return this.iFormulaMappingService.querySourceFormulaSchemes(targetFormulaSchemeKey);
    }

    public List<FormulaMappingDefine> queryValidFormulaMappings(String targetFormulaSchemeKey) {
        return this.iFormulaMappingService.queryValidFormulaMappings(targetFormulaSchemeKey);
    }

    public List<FormulaMappingDefine> queryFormulaMappings(String targetFormulaSchemeKey, String formKey) {
        return this.iFormulaMappingService.queryFormulaMappings(targetFormulaSchemeKey, formKey);
    }

    public List<FormulaMappingDefine> queryFormulaMappings(String targetFormulaSchemeKey, String formKey, String targetCode) {
        return this.iFormulaMappingService.queryFormulaMappings(targetFormulaSchemeKey, formKey, targetCode);
    }

    public Long queryFormulaMappingsCount(String mappingSchemekey, String formGroupKey, String formKey, String keyword) {
        return this.iFormulaMappingService.queryFormulaMappingsCount(mappingSchemekey, formGroupKey, formKey, keyword);
    }

    public List<FormulaMappingObj> queryFormulaMappings(String mappingSchemekey, String formGroupKey, String formKey, String keyword, int startRow, int endRow) throws JQException {
        return this.iFormulaMappingService.queryFormulaMappings(mappingSchemekey, formGroupKey, formKey, keyword, startRow, endRow);
    }

    public List<FormulaMappingObj> queryFormulaMappingsByGroup(String schemeKey, String formKey, String groupkey) throws JQException {
        return this.iFormulaMappingService.queryFormulaMappingsByGroup(schemeKey, formKey, groupkey);
    }

    public double queryMaxOrder(String schemekey, String formKey) throws JQException {
        return this.iFormulaMappingService.queryMaxOrder(schemekey, formKey);
    }

    public FormSchemeAndTaskKeysObj getTaskAndFormSchemeObj(String formulaSchemeKey) {
        return this.formulaMappingSchemeService.getTaskAndFormSchemeObj(formulaSchemeKey);
    }

    public List<FormulaMappingSchemeObj> queryFormulaMappingSchemeObjs() {
        return this.formulaMappingSchemeService.queryFormulaMappingSchemeObjs();
    }

    public FormulaMappingSchemeDefine queryFormulaMappingSchemeObjsByKey(String key) {
        return this.formulaMappingSchemeService.queryFormulaMappingSchemeObjsByKey(key);
    }
}

