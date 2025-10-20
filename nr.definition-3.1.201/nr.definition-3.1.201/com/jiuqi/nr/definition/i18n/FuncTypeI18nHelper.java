/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.i18n.helper.I18nHelper
 */
package com.jiuqi.nr.definition.i18n;

import com.jiuqi.np.i18n.helper.I18nHelper;
import com.jiuqi.nr.definition.i18n.I18nFuncRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
@DependsOn(value={"i18nHelperSupport"})
public class FuncTypeI18nHelper {
    @Autowired
    @Qualifier(value="func_type")
    private I18nHelper i18nHelper;

    public String getFunc(String code) {
        return this.i18nHelper.getMessage(I18nFuncRegister.getCategoryKey(code));
    }

    public String getDesc(String code) {
        return this.i18nHelper.getMessage("TASK#FUNC#" + I18nFuncRegister.getDescKey(code));
    }

    public String getParam(String func, String code) {
        return this.i18nHelper.getMessage("TASK#FUNC#" + I18nFuncRegister.getParamKey(func, code));
    }

    public String getVariable(String code) {
        return this.i18nHelper.getMessage(I18nFuncRegister.getVariableKey(code));
    }

    public String getSysVariable(String code) {
        return this.i18nHelper.getMessage(I18nFuncRegister.getSysVariableKey(code));
    }
}

