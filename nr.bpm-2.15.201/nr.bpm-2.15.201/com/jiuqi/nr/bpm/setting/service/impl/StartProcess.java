/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 */
package com.jiuqi.nr.bpm.setting.service.impl;

import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.ProcessEngineProvider;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.businesskey.MasterEntity;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.process.consts.ProcessType;
import com.jiuqi.nr.bpm.service.RunTimeService;
import com.jiuqi.nr.bpm.setting.service.IStartProcess;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.bpm.upload.WorkflowStatus;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartProcess
implements IStartProcess {
    private static final Logger logger = LoggerFactory.getLogger(StartProcess.class);
    @Autowired
    private ProcessEngineProvider processEngineProvider;
    @Autowired
    private WorkflowSettingService workflowSettingService;
    @Autowired
    private NrParameterUtils nrParameterUtils;

    @Override
    public boolean startProcess(String fromSchemeKey, Map<BusinessKey, String> startParam, WorkflowStatus workflowType) {
        try {
            Optional<ProcessEngine> processEngine = null;
            if (WorkflowStatus.DEFAULT.equals((Object)workflowType)) {
                processEngine = this.processEngineProvider.getProcessEngine(ProcessType.DEFAULT);
            } else if (WorkflowStatus.WORKFLOW.equals((Object)workflowType)) {
                processEngine = this.processEngineProvider.getProcessEngine(ProcessType.COMPLETED_ACTIVIT);
            }
            RunTimeService runTimeService = processEngine.map(engine -> engine.getRunTimeService()).orElse(null);
            HashMap<String, Object> variables = new HashMap<String, Object>();
            Set<BusinessKey> batchStartProcessByBusinessKey = runTimeService.batchStartProcessByBusinessKey(startParam, variables);
            if (batchStartProcessByBusinessKey.isEmpty()) {
                logger.error("\u542f\u52a8\u5931\u8d25\u6216\u5df2\u7ecf\u542f\u52a8\u8fc7,\u8bf7\u524d\u5f80\u6d41\u7a0b\u7ba1\u7406\u6838\u5b9e");
                return false;
            }
            HashSet<BusinessKey> businessKeyList = new HashSet<BusinessKey>();
            for (Map.Entry<BusinessKey, String> business : startParam.entrySet()) {
                BusinessKey businessKey = business.getKey();
                businessKeyList.add(businessKey);
                MasterEntity masterEntity = businessKey.getMasterEntity();
                Collection<String> dimessionNames = masterEntity.getDimessionNames();
                String dimName = dimessionNames.stream().findAny().orElse(null);
                String formId = businessKey.getFormKey();
                this.workflowSettingService.deleteBindData(businessKey.getFormSchemeKey(), businessKey.getPeriod(), masterEntity.getMasterEntityKey(dimName), formId);
            }
            WorkFlowType workflowStartType = this.nrParameterUtils.getWorkflowStartType(fromSchemeKey);
            if (WorkFlowType.FORM.equals((Object)workflowStartType) || WorkFlowType.GROUP.equals((Object)workflowStartType)) {
                this.nrParameterUtils.updateUnitState(businessKeyList, workflowStartType, null);
            }
            return true;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }
}

