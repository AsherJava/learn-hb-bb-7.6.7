/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataentry.internal.service.OverViewBaseServiceImpl
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge
 */
package com.jiuqi.nr.workflow2.converter.dataentry;

import com.jiuqi.nr.dataentry.internal.service.OverViewBaseServiceImpl;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class OverViewBaseServiceImplConverter
extends OverViewBaseServiceImpl {
    @Autowired
    protected DefaultEngineVersionJudge defaultEngineVersionJudge;
    @Autowired
    protected IRunTimeViewController runTimeViewController;
    @Autowired
    protected WorkflowSettingsService workflowSettingsService;

    public Map<String, String> getActionTitleMap(String formschemeKey, String period, boolean flowsType) {
        WorkflowObjectType workflowObjectType;
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formschemeKey);
        if (formScheme != null && this.defaultEngineVersionJudge.isDefaultEngineVersion_2_0(formScheme.getTaskKey()) && WorkflowObjectType.MD_WITH_SFR == (workflowObjectType = this.workflowSettingsService.queryTaskWorkflowObjectType(formScheme.getTaskKey()))) {
            Map actionTitleMap = super.getActionTitleMap(formschemeKey, period, flowsType);
            actionTitleMap.put("formReject-V2", "\u5f85\u66f4\u6b63");
            return actionTitleMap;
        }
        return super.getActionTitleMap(formschemeKey, period, flowsType);
    }
}

