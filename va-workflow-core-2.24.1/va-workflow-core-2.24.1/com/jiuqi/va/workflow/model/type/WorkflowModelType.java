/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.model.ModelImpl
 *  com.jiuqi.va.biz.impl.model.ModelTypeBase
 *  com.jiuqi.va.biz.intf.autotask.AutoTaskManager
 *  com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService
 *  com.jiuqi.va.domain.workflow.service.WorkflowInfoService
 *  com.jiuqi.va.domain.workflow.service.WorkflowMetaService
 *  com.jiuqi.va.domain.workflow.service.WorkflowOptionService
 *  com.jiuqi.va.domain.workflow.service.WorkflowPlusApprovalService
 *  com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeService
 *  com.jiuqi.va.domain.workflow.service.WorkflowSevice
 *  com.jiuqi.va.domain.workflow.service.WorkflowSubProcessBranchStrategyService
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.EnumDataClient
 *  com.jiuqi.va.feign.client.MetaDataClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.feign.client.TodoClient
 *  com.jiuqi.va.feign.client.VaSystemOptionClient
 *  com.jiuqi.va.message.feign.client.VaMessageTemplateClient
 *  com.jiuqi.va.trans.service.VaTransMessageService
 */
package com.jiuqi.va.workflow.model.type;

import com.jiuqi.va.biz.impl.model.ModelImpl;
import com.jiuqi.va.biz.impl.model.ModelTypeBase;
import com.jiuqi.va.biz.intf.autotask.AutoTaskManager;
import com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService;
import com.jiuqi.va.domain.workflow.service.WorkflowInfoService;
import com.jiuqi.va.domain.workflow.service.WorkflowMetaService;
import com.jiuqi.va.domain.workflow.service.WorkflowOptionService;
import com.jiuqi.va.domain.workflow.service.WorkflowPlusApprovalService;
import com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeService;
import com.jiuqi.va.domain.workflow.service.WorkflowSevice;
import com.jiuqi.va.domain.workflow.service.WorkflowSubProcessBranchStrategyService;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.EnumDataClient;
import com.jiuqi.va.feign.client.MetaDataClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.feign.client.TodoClient;
import com.jiuqi.va.feign.client.VaSystemOptionClient;
import com.jiuqi.va.message.feign.client.VaMessageTemplateClient;
import com.jiuqi.va.trans.service.VaTransMessageService;
import com.jiuqi.va.workflow.config.AutoTaskModuleConfig;
import com.jiuqi.va.workflow.config.BizTypeConfig;
import com.jiuqi.va.workflow.model.WorkflowModelHelper;
import com.jiuqi.va.workflow.model.impl.WorkflowDefineImpl;
import com.jiuqi.va.workflow.model.impl.WorkflowModelImpl;
import com.jiuqi.va.workflow.service.impl.help.WorkflowParamService;
import com.jiuqi.va.workflow.service.plus.approval.WorkflowPlusApprovalConsultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkflowModelType
extends ModelTypeBase {
    @Autowired
    private MetaDataClient metaDataClient;
    @Autowired
    private WorkflowSevice workflowSevice;
    @Autowired
    private TodoClient todoClient;
    @Autowired
    private VaMessageTemplateClient vaMessageTemplateClient;
    @Autowired
    private VaSystemOptionClient vaSystemOptionClient;
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private EnumDataClient enumDataClient;
    @Autowired
    private AuthUserClient authUserClient;
    @Autowired
    private BaseDataClient baseDataClient;
    @Autowired
    private AutoTaskManager autoTaskManager;
    @Autowired
    private AutoTaskModuleConfig autoTaskModuleConfig;
    @Autowired
    private VaWorkflowProcessService vaWorkflowProcessService;
    @Autowired
    private WorkflowProcessNodeService workflowProcessNodeService;
    @Autowired
    private WorkflowOptionService workflowOptionService;
    @Autowired
    private WorkflowPlusApprovalService workflowPlusApprovalService;
    @Autowired
    private VaTransMessageService vaTransMessageService;
    @Autowired
    private BizTypeConfig bizTypeConfig;
    @Autowired
    private WorkflowMetaService workflowMetaService;
    @Autowired
    private WorkflowModelHelper workflowModelHelper;
    @Autowired
    private WorkflowInfoService workflowInfoService;
    @Autowired
    private WorkflowParamService workflowParamService;
    @Autowired
    private WorkflowSubProcessBranchStrategyService workflowSubProcessBranchStrategyService;
    @Autowired
    private WorkflowPlusApprovalConsultService workflowPlusApprovalConsultService;

    public String getName() {
        return "workflow";
    }

    public String getTitle() {
        return "\u5de5\u4f5c\u6d41\u6a21\u578b";
    }

    public String getMetaType() {
        return "workflow";
    }

    public String[] getDependPlugins() {
        return new String[]{"processDesignPlugin", "processParamPlugin"};
    }

    public Class<? extends WorkflowDefineImpl> getModelDefineClass() {
        return WorkflowDefineImpl.class;
    }

    public Class<? extends WorkflowModelImpl> getModelClass() {
        return WorkflowModelImpl.class;
    }

    public void ensureModel(ModelImpl model) {
        WorkflowModelImpl workflowModelImpl = (WorkflowModelImpl)model;
        workflowModelImpl.setWorkflowSevice(this.workflowSevice);
        workflowModelImpl.setTodoClient(this.todoClient);
        workflowModelImpl.setAutoTaskModuleConfig(this.autoTaskModuleConfig);
        workflowModelImpl.setAutoTaskManager(this.autoTaskManager);
        workflowModelImpl.setVaMessageTemplateClient(this.vaMessageTemplateClient);
        workflowModelImpl.setOrgDataClient(this.orgDataClient);
        workflowModelImpl.setBaseDataClient(this.baseDataClient);
        workflowModelImpl.setEnumDataClient(this.enumDataClient);
        workflowModelImpl.setAuthUserClient(this.authUserClient);
        workflowModelImpl.setVaSystemOptionClient(this.vaSystemOptionClient);
        workflowModelImpl.setVaWorkflowProcessService(this.vaWorkflowProcessService);
        workflowModelImpl.setWorkflowProcessNodeService(this.workflowProcessNodeService);
        workflowModelImpl.setMetaDataClient(this.metaDataClient);
        workflowModelImpl.setBizTypeConfig(this.bizTypeConfig);
        workflowModelImpl.setWorkflowOptionService(this.workflowOptionService);
        workflowModelImpl.setWorkflowPlusApprovalService(this.workflowPlusApprovalService);
        workflowModelImpl.setWorkflowMetaService(this.workflowMetaService);
        workflowModelImpl.setVaTransMessageService(this.vaTransMessageService);
        workflowModelImpl.setWorkflowModelHelper(this.workflowModelHelper);
        workflowModelImpl.setWorkflowInfoService(this.workflowInfoService);
        workflowModelImpl.setWorkflowParamService(this.workflowParamService);
        workflowModelImpl.setWorkflowSubProcessBranchStrategyService(this.workflowSubProcessBranchStrategyService);
        workflowModelImpl.setWorkflowPlusApprovalConsultService(this.workflowPlusApprovalConsultService);
    }
}

