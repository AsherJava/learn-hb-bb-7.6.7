/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.bpm.service.SingleFormRejectService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.service.SingleFormRejectService;
import com.jiuqi.nr.dataentry.service.IRefreshStatus;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormRejectRefreshStatus
implements IRefreshStatus {
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private SingleFormRejectService singleFormRejectService;
    @Autowired
    private DefaultEngineVersionJudge defaultEngineVersionJudge;

    @Override
    public boolean getEnable(String taskKey, String formSchemeKey) {
        if (!this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(taskKey)) {
            return false;
        }
        FormSchemeDefine formSchemeDefine = this.runtimeView.getFormScheme(formSchemeKey);
        return formSchemeDefine != null && formSchemeDefine.getFlowsSetting() != null && formSchemeDefine.getFlowsSetting().isAllowFormBack();
    }

    @Override
    public String getName() {
        return "formReject";
    }

    @Override
    public Consts.RefreshStatusType getType() {
        return Consts.RefreshStatusType.UNIT;
    }

    public Object getStatus(JtableContext context) throws Exception {
        DimensionValueSet dimension = DimensionValueSetUtil.getDimensionValueSet((Map)context.getDimensionSet());
        Set rejectForms = this.singleFormRejectService.getFormKeysByAction(dimension, context.getFormSchemeKey(), "single_form_reject");
        return rejectForms;
    }
}

