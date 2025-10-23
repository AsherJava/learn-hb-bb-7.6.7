/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.IProcessEngineFactory
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessConfig
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessDesignService
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.nodeconfig.AuditNodeConfig
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.property.AuditProperty
 *  com.jiuqi.nr.workflow2.service.IProcessMetaDataService
 *  com.jiuqi.nr.workflow2.service.para.IProcessExecutePara
 */
package com.jiuqi.nr.workflow2.form.reject.ext.service;

import com.jiuqi.nr.workflow2.engine.core.IProcessEngineFactory;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessConfig;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessDesignService;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.nodeconfig.AuditNodeConfig;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.property.AuditProperty;
import com.jiuqi.nr.workflow2.form.reject.ext.event.IFormRejectEvent;
import com.jiuqi.nr.workflow2.form.reject.ext.event.IFormRejectEventExecutor;
import com.jiuqi.nr.workflow2.form.reject.ext.service.StepByStepFormRejectEventExecutor;
import com.jiuqi.nr.workflow2.form.reject.ext.service.StepByStepFormRejectEventWithParentExecutor;
import com.jiuqi.nr.workflow2.service.IProcessMetaDataService;
import com.jiuqi.nr.workflow2.service.para.IProcessExecutePara;
import org.springframework.beans.factory.annotation.Autowired;

public class StepByStepFormRejectEvent
implements IFormRejectEvent {
    public static final String ID = "step-by-step-form-reject-event";
    public static final String TITLE = "\u5355\u8868\u9a73\u56de\uff0c\u5c42\u5c42\u9000\u56de\u68c0\u67e5";
    @Autowired
    private WorkflowSettingsService workflowSettingsService;
    @Autowired
    protected DefaultEngineVersionJudge defaultEngineVersionJudge;
    @Autowired
    protected IProcessEngineFactory processEngineFactory;
    @Autowired
    protected IProcessMetaDataService processMetaDataService;
    @Autowired
    protected DefaultProcessDesignService processDesignService;

    @Override
    public String getEventId() {
        return ID;
    }

    @Override
    public String getEventTitle() {
        return TITLE;
    }

    @Override
    public boolean isEnabled(IProcessExecutePara args) {
        WorkflowObjectType workflowObjectType = this.workflowSettingsService.queryTaskWorkflowObjectType(args.getTaskKey());
        if (WorkflowObjectType.MD_WITH_SFR == workflowObjectType && this.defaultEngineVersionJudge.isDefaultEngineVersion_2_0(args.getTaskKey())) {
            WorkflowSettingsDO flowSettings = this.workflowSettingsService.queryWorkflowSettings(args.getTaskKey());
            DefaultProcessConfig defaultProcessConfig = this.processDesignService.queryDefaultProcessConfig(flowSettings.getWorkflowDefine());
            AuditNodeConfig auditNodeConfig = defaultProcessConfig.getAuditNodeConfig();
            AuditProperty property = auditNodeConfig.getProperty();
            return property.isReturnLayerByLayer();
        }
        return false;
    }

    @Override
    public IFormRejectEventExecutor getEventExecutor(IProcessExecutePara args) {
        if (this.isRejectAllParent(args)) {
            return new StepByStepFormRejectEventWithParentExecutor(args);
        }
        return new StepByStepFormRejectEventExecutor(args);
    }

    private boolean isRejectAllParent(IProcessExecutePara args) {
        WorkflowSettingsDO flowSettings = this.workflowSettingsService.queryWorkflowSettings(args.getTaskKey());
        DefaultProcessConfig defaultProcessConfig = this.processDesignService.queryDefaultProcessConfig(flowSettings.getWorkflowDefine());
        AuditNodeConfig auditNodeConfig = defaultProcessConfig.getAuditNodeConfig();
        AuditProperty property = auditNodeConfig.getProperty();
        return property.isReturnLayerByLayer() && property.isReturnAllSuperior();
    }
}

