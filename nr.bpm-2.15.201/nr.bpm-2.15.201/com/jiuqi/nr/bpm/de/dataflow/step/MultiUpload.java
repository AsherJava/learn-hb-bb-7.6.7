/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.data.engine.condition.IFormConditionService
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 */
package com.jiuqi.nr.bpm.de.dataflow.step;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.dataflow.service.IDataentryQueryStateService;
import com.jiuqi.nr.bpm.de.dataflow.step.BaseUpload;
import com.jiuqi.nr.bpm.de.dataflow.step.BuildStepTree;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchStepByStepParam;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchStepByStepResult;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.StepTree;
import com.jiuqi.nr.bpm.de.dataflow.step.inter.ContextStrategy;
import com.jiuqi.nr.bpm.de.dataflow.step.provide.StepQueryState;
import com.jiuqi.nr.bpm.de.dataflow.util.DeEntityQueryManager;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.WorkflowReportDimService;
import com.jiuqi.nr.bpm.service.SingleFormRejectService;
import com.jiuqi.nr.data.engine.condition.IFormConditionService;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MultiUpload
extends BaseUpload {
    private static final Logger logger = LoggerFactory.getLogger(MultiUpload.class);
    @Autowired
    IFormConditionService formConditionService;
    @Autowired
    DimensionUtil dimensionUtil;
    @Autowired
    IDataentryQueryStateService dataentryQueryStateService;
    @Autowired
    private WorkflowReportDimService workflowReportDimService;
    @Autowired
    private SingleFormRejectService singleFormRejectService;
    @Autowired
    private DeEntityQueryManager entityManager;

    public StepQueryState stepQueryState(String formSchemeKey, DimensionValueSet sourceDim, String period, WorkFlowType startType) {
        return new StepQueryState(formSchemeKey, sourceDim, period, startType, this.commonUtil, this.dimensionUtil, this.formConditionService, this.dataentryQueryStateService, this.workflowReportDimService, this.singleFormRejectService, this.entityManager);
    }

    public BatchStepByStepResult batchStepByOpt(BatchStepByStepParam stepByOptParam) {
        BatchStepByStepResult batchStepByStepResult = new BatchStepByStepResult();
        DimensionValueSet buildDimensionSet = this.dimensionUtil.mergeDimensionValueSet(stepByOptParam.getStepUnit(), stepByOptParam.getFormSchemeKey());
        String period = (String)buildDimensionSet.getValue("DATATIME");
        BuildStepTree buildStepTree = new BuildStepTree(this.stepUtil, this.commonUtil, this.dimensionUtil, this.deEntityHelper);
        List<StepTree> stepTreeData = buildStepTree.buildBatchStepTree(stepByOptParam);
        try {
            boolean stepByStepReport = this.stepUtil.stepByStepUpload(stepByOptParam.getFormSchemeKey(), stepByOptParam.getNodeId(), stepByOptParam.getActionId(), stepByOptParam);
            boolean stepByStepBack = this.stepUtil.stepByStepBack(stepByOptParam.getFormSchemeKey(), stepByOptParam.getNodeId(), stepByOptParam.getActionId(), stepByOptParam);
            boolean stepByStepBackAll = this.stepUtil.stepByStepBackAll(stepByOptParam.getFormSchemeKey(), stepByOptParam.getNodeId(), stepByOptParam.getActionId(), stepByOptParam);
            batchStepByStepResult = stepByStepReport && stepByStepBack ? (this.isUpload(stepByOptParam.getActionId()) ? this.batchStep(stepByOptParam, stepTreeData, period, true, stepByStepBackAll) : (this.isBack(stepByOptParam.getActionId()) ? this.batchStep(stepByOptParam, stepTreeData, period, false, stepByStepBackAll) : this.complete(stepByOptParam, buildDimensionSet))) : (stepByStepReport ? (this.isUpload(stepByOptParam.getActionId()) ? this.batchStep(stepByOptParam, stepTreeData, period, true, stepByStepBackAll) : this.complete(stepByOptParam, buildDimensionSet)) : (stepByStepBack ? (this.isBack(stepByOptParam.getActionId()) ? this.batchStep(stepByOptParam, stepTreeData, period, false, stepByStepBackAll) : this.complete(stepByOptParam, buildDimensionSet)) : this.complete(stepByOptParam, buildDimensionSet)));
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return batchStepByStepResult;
    }

    public BatchStepByStepResult batchStep(BatchStepByStepParam stepByOptParam, List<StepTree> stepTreeData, String period, boolean child, boolean stepByStepBackAll) {
        BatchStepByStepResult upload = new BatchStepByStepResult();
        WorkFlowType startType = this.stepUtil.startType(stepByOptParam.getFormSchemeKey());
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        List<DimensionValueSet> stepUnit = stepByOptParam.getStepUnit();
        if (stepUnit != null && stepUnit.size() > 0) {
            dimensionValueSet = stepUnit.get(0);
        }
        StepQueryState stepQueryState = this.stepQueryState(stepByOptParam.getFormSchemeKey(), dimensionValueSet, period, startType);
        upload = child ? this.batchStepByStepUpload(stepByOptParam, stepTreeData, period, stepQueryState, startType, stepByStepBackAll) : this.batchStepByStepBack(stepByOptParam, stepTreeData, period, stepQueryState, startType, stepByStepBackAll);
        return upload;
    }

    public BatchStepByStepResult batchStepByStepUpload(BatchStepByStepParam stepByOptParam, List<StepTree> stepTreeData, String period, StepQueryState stepQueryState, WorkFlowType startType, boolean stepByStepBackAll) {
        BatchStepByStepResult upload = new BatchStepByStepResult();
        Optional<StepTree> max = stepTreeData.stream().max(Comparator.comparingInt(StepTree::getLevel));
        Optional<StepTree> min = stepTreeData.stream().min(Comparator.comparingInt(StepTree::getLevel));
        StepTree maxStepTree = max.get();
        StepTree minStepTree = min.get();
        int level = maxStepTree.getLevel();
        int minLevel = minStepTree.getLevel();
        Map<Integer, List<StepTree>> sortByLevel = this.sortByLevel(stepTreeData);
        for (int i = level; i >= minLevel; --i) {
            List<StepTree> subList = sortByLevel.get(i);
            this.resultData(stepByOptParam, subList, period, stepQueryState, upload, true, startType, stepByStepBackAll);
        }
        return upload;
    }

    public BatchStepByStepResult batchStepByStepBack(BatchStepByStepParam stepByOptParam, List<StepTree> stepTreeData, String period, StepQueryState stepQueryState, WorkFlowType startType, boolean stepByStepBackAll) {
        BatchStepByStepResult back = new BatchStepByStepResult();
        Optional<StepTree> max = stepTreeData.stream().max(Comparator.comparingInt(StepTree::getLevel));
        Optional<StepTree> min = stepTreeData.stream().min(Comparator.comparingInt(StepTree::getLevel));
        StepTree maxStepTree = max.get();
        StepTree minStepTree = min.get();
        int maxlLevel = maxStepTree.getLevel();
        int minLevel = minStepTree.getLevel();
        Map<Integer, List<StepTree>> sortByLevel = this.sortByLevel(stepTreeData);
        for (int i = minLevel; i <= maxlLevel; ++i) {
            List<StepTree> subList = sortByLevel.get(i);
            this.resultData(stepByOptParam, subList, period, stepQueryState, back, false, startType, stepByStepBackAll);
        }
        return back;
    }

    private Map<Integer, List<StepTree>> sortByLevel(List<StepTree> stepTreeData) {
        HashMap<Integer, List<StepTree>> unitMap = new HashMap<Integer, List<StepTree>>();
        ArrayList<StepTree> subList = new ArrayList<StepTree>();
        for (int i = 0; i < stepTreeData.size(); ++i) {
            int level = stepTreeData.get(i).getLevel();
            List list = (List)unitMap.get(level);
            if (list == null || list.size() == 0) {
                subList = new ArrayList();
                subList.add(stepTreeData.get(i));
                unitMap.put(level, subList);
                continue;
            }
            List sub2 = (List)unitMap.get(level);
            sub2.add(stepTreeData.get(i));
            unitMap.put(level, sub2);
        }
        return unitMap;
    }

    public void resultData(BatchStepByStepParam stepByOptParam, List<StepTree> subList, String period, StepQueryState stepQueryState, BatchStepByStepResult upload, boolean isUpload, WorkFlowType startType, boolean stepByStepBackAll) {
        ContextStrategy context = new ContextStrategy();
        context.resultData(startType, stepByOptParam, subList, period, stepQueryState, upload, isUpload, stepByStepBackAll);
    }
}

