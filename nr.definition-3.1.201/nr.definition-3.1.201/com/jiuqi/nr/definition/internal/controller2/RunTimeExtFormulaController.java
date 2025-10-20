/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.definition.internal.controller2;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.definition.api.IRunTimeExtFormulaController;
import com.jiuqi.nr.definition.controller.IExtFormulaRunTimeController;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaUnitGroup;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeExtFormulaService;
import com.jiuqi.nr.definition.internal.runtime.service.ExtRuntimeExpressionServiceV2;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RunTimeExtFormulaController
implements IRunTimeExtFormulaController {
    private static final Logger logger = LoggerFactory.getLogger(RunTimeExtFormulaController.class);
    @Autowired
    private IRuntimeExtFormulaService extFormulaService;
    @Autowired
    private ExtRuntimeExpressionServiceV2 expressionService;
    @Autowired
    private INvwaSystemOptionService systemOptionService;
    @Autowired
    private SystemIdentityService systemIdentityService;
    @Autowired
    private IExtFormulaRunTimeController oldExtFormulaRunTimeController;

    @Override
    public List<FormulaUnitGroup> get(List<String> unitCodes, String formulaSchemeKey, DataEngineConsts.FormulaType formulaType) {
        return this.oldExtFormulaRunTimeController.get(unitCodes, formulaSchemeKey, formulaType);
    }

    @Override
    public FormulaDefine getFormula(String key) {
        if (this.getExistPrivateFormula()) {
            return this.extFormulaService.queryFormula(key);
        }
        return null;
    }

    @Override
    public FormulaDefine getFormulaByCodeAndScheme(String formulaCode, String formulaScheme) {
        return this.extFormulaService.findFormula(formulaCode, formulaScheme);
    }

    @Override
    public IParsedExpression getExpressionBySchemeAndFormAndExpression(String formulaScheme, String form, String expression) {
        IParsedExpression parsedExpression2 = this.expressionService.getParsedExpression(formulaScheme, form, expression);
        if (parsedExpression2 != null) {
            return parsedExpression2;
        }
        return null;
    }

    @Override
    public List<FormulaDefine> listFormulaBySchemeAndEntity(String formulaScheme, Set<String> unit) {
        return this.extFormulaService.getFormulaByUnits(formulaScheme, unit);
    }

    @Override
    public boolean getExistPrivateFormula() {
        boolean isOpen = false;
        String enable = this.systemOptionService.get("PRIVATE_FORMULA", "PRIVATE_FORMULA_VALUE");
        if (StringUtils.isNotEmpty((String)enable) && "1".equals(enable)) {
            isOpen = true;
        }
        return isOpen;
    }
}

