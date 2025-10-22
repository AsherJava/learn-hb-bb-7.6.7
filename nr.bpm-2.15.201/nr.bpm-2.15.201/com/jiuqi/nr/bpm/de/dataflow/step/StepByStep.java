/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 */
package com.jiuqi.nr.bpm.de.dataflow.step;

import com.jiuqi.nr.bpm.de.dataflow.forcecontrol.IForceControlService;
import com.jiuqi.nr.bpm.de.dataflow.step.MultiUpload;
import com.jiuqi.nr.bpm.de.dataflow.step.SingleUpload;
import com.jiuqi.nr.bpm.de.dataflow.step.StepUtil;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchStepByStepParam;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchStepByStepResult;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.StepByOptParam;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.StepByStepCheckResult;
import com.jiuqi.nr.bpm.de.dataflow.util.IWorkFlowDimensionBuilder;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StepByStep {
    private static final Logger logger = LoggerFactory.getLogger(StepByStep.class);
    @Autowired
    MultiUpload multiUpload;
    @Autowired
    SingleUpload singleUpload;
    @Autowired
    private IForceControlService forceControlService;
    @Autowired
    private IWorkFlowDimensionBuilder workFlowDimensionBuilder;
    @Autowired
    private StepUtil stepUtil;
    @Autowired
    private IRunTimeViewController runTimeViewController;

    public StepByStepCheckResult stepByOpt(StepByOptParam stepByOptParam) {
        boolean enableForceControl = this.stepUtil.enableForceControl(stepByOptParam.getFormSchemeKey(), stepByOptParam.getNodeId(), stepByOptParam.getActionId());
        String actionId = stepByOptParam.getActionId();
        boolean stepByStepBack = this.stepUtil.stepByStepBack(stepByOptParam.getFormSchemeKey(), stepByOptParam.getNodeId(), stepByOptParam.getActionId(), stepByOptParam);
        if (enableForceControl && stepByStepBack && ("act_reject".equals(actionId) || "cus_reject".equals(actionId))) {
            StepByStepCheckResult reject = this.forceControlService.reject(stepByOptParam);
            return reject;
        }
        StepByStepCheckResult equal = this.singleUpload.stepByOpt(stepByOptParam);
        return equal;
    }

    public BatchStepByStepResult batchStepByOpt(BatchStepByStepParam stepByOptParam) {
        boolean enableForceControl = this.stepUtil.enableForceControl(stepByOptParam.getFormSchemeKey(), stepByOptParam.getNodeId(), stepByOptParam.getActionId());
        String actionId = stepByOptParam.getActionId();
        boolean stepByStepBack = this.stepUtil.stepByStepBack(stepByOptParam.getFormSchemeKey(), stepByOptParam.getNodeId(), stepByOptParam.getActionId(), stepByOptParam);
        if (enableForceControl && stepByStepBack && ("act_reject".equals(actionId) || "cus_reject".equals(actionId))) {
            BatchStepByStepResult batchStepByStepResult = this.forceControlService.batchReject(stepByOptParam);
            return batchStepByStepResult;
        }
        BatchStepByStepResult batchStepByStepResult = new BatchStepByStepResult();
        if (stepByOptParam.getStepUnit() == null || stepByOptParam.getStepUnit().size() == 0) {
            logger.info("\u4e0a\u62a5\u5355\u4f4d\u4f20\u5165\u503c\u4e3a\u7a7a\uff0c\u8bf7\u6838\u5b9e\u4f20\u5165\u53c2\u6570");
            return batchStepByStepResult;
        }
        batchStepByStepResult = this.multiUpload.batchStepByOpt(stepByOptParam);
        return batchStepByStepResult;
    }
}

