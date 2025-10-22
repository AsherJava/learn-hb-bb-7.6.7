/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.intf.IDataLinkFinder
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.AnalyseDataLinkFinder
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 */
package com.jiuqi.nr.form.analysis.common;

import com.jiuqi.np.dataengine.intf.IDataLinkFinder;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.env.AnalyseDataLinkFinder;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;

public class AnalysisFmlExecEnvironment
extends ReportFmlExecEnvironment {
    private String sourceFormSchemeKey;

    public AnalysisFmlExecEnvironment(IRunTimeViewController controller, IDataDefinitionRuntimeController runtimeController, IEntityViewRunTimeController entityViewRunTimeController, String formSchemeKey, String sourceFormSchemeKey) {
        super(controller, runtimeController, entityViewRunTimeController, formSchemeKey);
        this.sourceFormSchemeKey = sourceFormSchemeKey;
    }

    public FormSchemeDefine getFormSchemeDefine() {
        FormSchemeDefine formScheme = this.controller.getFormScheme(this.sourceFormSchemeKey);
        return formScheme;
    }

    public String getFormSchemeKey() {
        return this.sourceFormSchemeKey;
    }

    public IDataLinkFinder getDataLinkFinder() {
        if (this.datalinkFinder == null && this.formSchemeKey != null && this.sourceFormSchemeKey != null) {
            this.datalinkFinder = new AnalyseDataLinkFinder(this.controller, this.runtimeController, this.entityViewRunTimeController, this.formSchemeKey, this.sourceFormSchemeKey);
        }
        return this.datalinkFinder;
    }
}

