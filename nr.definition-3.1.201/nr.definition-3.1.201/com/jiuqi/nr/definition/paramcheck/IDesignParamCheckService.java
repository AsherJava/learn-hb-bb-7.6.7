/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.paramcheck;

import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.definition.facade.FormulaVariDefine;

public interface IDesignParamCheckService {
    public void checkTaskTitle(DesignTaskDefine var1);

    public void checkTaskCode(DesignTaskDefine var1);

    public void checkFormTitle(DesignFormDefine var1);

    public void checkFormCode(DesignFormDefine var1);

    public void checkFormTitleAndCode(DesignFormDefine var1);

    public void checkFormScheme(DesignFormSchemeDefine var1);

    public void checkFormSchemeCode(DesignFormSchemeDefine var1);

    public void checkFormGroup(DesignFormGroupDefine var1);

    public void checkFormulaSchemeName(DesignFormulaSchemeDefine var1);

    public void checkPrintTemplateScheme(DesignPrintTemplateSchemeDefine var1);

    public void checkTaskGroupTitle(DesignTaskGroupDefine var1);

    public void checkFormulaSchemeNameByType(DesignFormulaSchemeDefine var1);

    public void checkFormulaCode(DesignFormulaDefine[] var1);

    public void checkFormulaVariable(FormulaVariDefine var1);
}

