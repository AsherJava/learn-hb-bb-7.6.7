/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.var.VariableManagerBase
 *  com.jiuqi.nr.definition.controller.IFormulaDesignTimeController
 *  com.jiuqi.nr.definition.facade.FormulaVariDefine
 *  com.jiuqi.nr.definition.i18n.FuncTypeI18nHelper
 *  com.jiuqi.nr.definition.internal.impl.DesignFormulaVariableDefineImpl
 *  com.jiuqi.nr.definition.util.ServeCodeService
 *  com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.nr.designer.web.rest;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.var.VariableManagerBase;
import com.jiuqi.nr.definition.controller.IFormulaDesignTimeController;
import com.jiuqi.nr.definition.facade.FormulaVariDefine;
import com.jiuqi.nr.definition.i18n.FuncTypeI18nHelper;
import com.jiuqi.nr.definition.internal.impl.DesignFormulaVariableDefineImpl;
import com.jiuqi.nr.definition.util.ServeCodeService;
import com.jiuqi.nr.designer.web.facade.FormulaVariable;
import com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@JQRestController
@RequestMapping(value={"api/v1/designer/"})
@Api(tags={"\u516c\u5f0f\u53d8\u91cf"})
public class FormulaVariableController {
    @Autowired
    private IFormulaDesignTimeController formulaDesignTimeController;
    @Autowired
    private ServeCodeService serveCodeService;
    @Autowired
    private FuncTypeI18nHelper funcTypeI18nHelper;

    @ApiOperation(value="\u67e5\u8be2\u5f53\u524d\u65b9\u6848\u4e0b\u7684\u6240\u6709\u516c\u5f0f\u53d8\u91cf")
    @RequestMapping(value={"query_all_formula_variable/{formSchemeKey}"}, method={RequestMethod.GET})
    public List<FormulaVariable> queryAllFormulaVariable(@PathVariable String formSchemeKey) {
        ArrayList<FormulaVariable> list = new ArrayList<FormulaVariable>();
        List queryAllFormulaVariable = this.formulaDesignTimeController.queryAllFormulaVariable(formSchemeKey);
        queryAllFormulaVariable.stream().forEach(define -> list.add(new FormulaVariable().convertToFormulaVariable((FormulaVariDefine)define)));
        return list;
    }

    @ApiOperation(value="\u67e5\u8be2\u5f53\u524d\u65b9\u6848\u4e0b\u7684\u6240\u6709\u516c\u5f0f\u53d8\u91cf")
    @RequestMapping(value={"query_formula_system_variable/{formSchemeKey}"}, method={RequestMethod.GET})
    public List<FormulaVariable> queryAllFormulaVariableAndSystemVar(@PathVariable String formSchemeKey) {
        ArrayList<FormulaVariable> list = new ArrayList<FormulaVariable>();
        VariableManagerBase variableManager = (VariableManagerBase)ExecutorContext.contextVariableManagerProvider.getNormalContextVariableManager();
        List allVars = variableManager.getAllVars();
        if (!CollectionUtils.isEmpty(allVars)) {
            allVars.stream().forEach(var -> {
                FormulaVariable variable = new FormulaVariable();
                String sysVariable = this.funcTypeI18nHelper.getSysVariable(var.getVarName());
                variable.setCode(var.getVarName());
                if (StringUtils.hasLength(sysVariable)) {
                    variable.setTitle(sysVariable);
                } else {
                    variable.setTitle(var.getVarTitle());
                }
                variable.setValueType(var.getDataType());
                list.add(variable);
            });
        }
        List queryAllFormulaVariable = this.formulaDesignTimeController.queryAllFormulaVariable(formSchemeKey);
        for (FormulaVariDefine define : queryAllFormulaVariable) {
            FormulaVariable formulaVariable = new FormulaVariable().convertToFormulaVariable(define);
            String variable = this.funcTypeI18nHelper.getVariable(formulaVariable.getCode());
            if (StringUtils.hasLength(variable)) {
                formulaVariable.setTitle(variable);
            }
            list.add(formulaVariable);
        }
        return list;
    }

    @ApiOperation(value="\u6839\u636e\u516c\u5f0f\u53d8\u91cf\u6807\u8bc6\u6216\u8005\u540d\u79f0\u641c\u7d22\u516c\u5f0f\u53d8\u91cf")
    @RequestMapping(value={"query_condition_formula_variable"}, method={RequestMethod.GET})
    public List<FormulaVariable> queryFormulaVariableByCodeOrTitle(@RequestParam String formSchemeKey, String condition) {
        List queryFormulaVariableByCodeOrTitle = this.formulaDesignTimeController.queryFormulaVariableByCodeOrTitle(formSchemeKey, condition);
        ArrayList<FormulaVariable> list = new ArrayList<FormulaVariable>();
        queryFormulaVariableByCodeOrTitle.stream().forEach(define -> list.add(new FormulaVariable().convertToFormulaVariable((FormulaVariDefine)define)));
        return list;
    }

    @ApiOperation(value="\u65b0\u589e\u516c\u5f0f\u53d8\u91cf")
    @RequestMapping(value={"save_add_formula_variable"}, method={RequestMethod.POST})
    public void addFormulaVariable(@RequestBody FormulaVariable formulaVariable) throws JQException {
        FormulaVariDefine designFormulaVariableDefine = formulaVariable.convertToDefine((FormulaVariDefine)new DesignFormulaVariableDefineImpl());
        designFormulaVariableDefine.setOwnerLevelAndId(this.serveCodeService.getServeCode());
        this.formulaDesignTimeController.addFormulaVariable(designFormulaVariableDefine);
    }

    @ApiOperation(value="\u65b0\u589e\u516c\u5f0f\u53d8\u91cf")
    @RequestMapping(value={"save_add_formula_variable_rsa"}, method={RequestMethod.POST})
    public void addFormulaVariableRsa(@RequestBody @SFDecrypt FormulaVariable formulaVariable) throws JQException {
        this.addFormulaVariable(formulaVariable);
    }

    @ApiOperation(value="\u4fee\u6539\u516c\u5f0f\u53d8\u91cf")
    @RequestMapping(value={"save_update_formula_variable"}, method={RequestMethod.POST})
    public void updateFormulaVariable(@RequestBody FormulaVariable formulaVariable) throws JQException {
        FormulaVariDefine designFormulaVariableDefine = formulaVariable.convertToDefine((FormulaVariDefine)new DesignFormulaVariableDefineImpl());
        designFormulaVariableDefine.setOwnerLevelAndId(this.serveCodeService.getServeCode());
        this.formulaDesignTimeController.updateFormulaVariable(designFormulaVariableDefine);
    }

    @ApiOperation(value="\u4fee\u6539\u516c\u5f0f\u53d8\u91cf")
    @RequestMapping(value={"save_update_formula_variable_rsa"}, method={RequestMethod.POST})
    public void updateFormulaVariableRsa(@RequestBody @SFDecrypt FormulaVariable formulaVariable) throws JQException {
        this.updateFormulaVariable(formulaVariable);
    }

    @ApiOperation(value="\u5220\u9664\u516c\u5f0f\u53d8\u91cf")
    @RequestMapping(value={"delete_formula_variable"}, method={RequestMethod.POST})
    public void deleteFormulaVariable(@RequestBody String formulaVariableKey) throws JQException {
        this.formulaDesignTimeController.deleteFormulaVariable(formulaVariableKey);
    }
}

