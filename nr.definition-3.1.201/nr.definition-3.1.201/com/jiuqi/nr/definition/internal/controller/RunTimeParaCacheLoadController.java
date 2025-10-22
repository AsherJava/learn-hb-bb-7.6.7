/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 */
package com.jiuqi.nr.definition.internal.controller;

import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeParaCacheLoadController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.runtime.service.NrFormulaCacheService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RunTimeParaCacheLoadController
implements IRunTimeParaCacheLoadController {
    @Autowired
    private IRunTimeViewController controller;
    @Autowired
    private IFormulaRunTimeController formulaController;
    @Autowired
    private NrFormulaCacheService formulaCacheService;
    private Logger logger = LogFactory.getLogger(RunTimeParaCacheLoadController.class);

    @Override
    public void loadTask(String taskKey) {
        this.controller.queryTaskDefine(taskKey);
        try {
            List<FormSchemeDefine> formSchemeDefines = this.controller.queryFormSchemeByTask(taskKey);
            for (FormSchemeDefine scheme : formSchemeDefines) {
                List<FormGroupDefine> formGroups = this.controller.getAllFormGroupsInFormScheme(scheme.getKey());
                for (FormGroupDefine formGroupDefine : formGroups) {
                    List<FormDefine> forms = this.controller.getAllFormsInGroup(formGroupDefine.getKey());
                    for (FormDefine form : forms) {
                        this.controller.getAllRegionsInForm(form.getKey());
                        this.controller.getAllLinksInForm(form.getKey());
                    }
                }
                List<FormulaSchemeDefine> formulaSchemeDefines = this.formulaController.getAllFormulaSchemeDefinesByFormScheme(scheme.getKey());
                for (FormulaSchemeDefine formulaSchemeDefine : formulaSchemeDefines) {
                    this.formulaCacheService.getFormulasInScheme(formulaSchemeDefine.getKey());
                }
            }
        }
        catch (Exception e) {
            this.logger.error("\u521d\u59cb\u5316\u4efb\u52a1\u7f13\u5b58\u51fa\u9519:" + taskKey, (Throwable)e);
        }
    }
}

