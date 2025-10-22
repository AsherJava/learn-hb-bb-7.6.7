/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 */
package com.jiuqi.nr.bpm.de.dataflow.step;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.de.dataflow.step.StepUtil;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchStepByStepParam;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.StepTree;
import com.jiuqi.nr.bpm.de.dataflow.step.provide.BuildAllTree;
import com.jiuqi.nr.bpm.de.dataflow.step.provide.BuildSingleTree;
import com.jiuqi.nr.bpm.de.dataflow.util.CommonUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.DeEntityHelper;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BuildStepTree {
    private static final Logger logger = LoggerFactory.getLogger(BuildStepTree.class);
    private StepUtil stepUtil;
    private DeEntityHelper deEntityHelper;
    private DimensionUtil dimensionUtil;
    private CommonUtil commonUtil;

    public BuildStepTree() {
    }

    public BuildStepTree(StepUtil stepUtil, CommonUtil commonUtil, DimensionUtil dimensionUtil, DeEntityHelper deEntityHelper) {
        this.stepUtil = stepUtil;
        this.commonUtil = commonUtil;
        this.dimensionUtil = dimensionUtil;
        this.deEntityHelper = deEntityHelper;
    }

    public List<StepTree> buildSingleStepTree(String formSchemeKey, DimensionValueSet dim, LinkedHashSet<String> formKeys, LinkedHashSet<String> groupKeys) {
        List<StepTree> stepTrees = new ArrayList<StepTree>();
        try {
            WorkFlowType uploadType = this.stepUtil.uploadType(formSchemeKey);
            LinkedHashSet<String> formOrGroupKey = this.formOrGroupKey(formSchemeKey, uploadType, formKeys, groupKeys);
            BuildSingleTree buildSingleTree = new BuildSingleTree(formOrGroupKey, formSchemeKey, dim, this.dimensionUtil, this.deEntityHelper, this.commonUtil);
            if (this.stepUtil.isUploadEntity(uploadType)) {
                stepTrees = buildSingleTree.buildUnitData();
            } else if (this.stepUtil.isUploadForm(uploadType) || this.stepUtil.isUploadGroup(uploadType) || this.stepUtil.isPartResource()) {
                stepTrees = buildSingleTree.buildResourceTree();
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return stepTrees;
    }

    public List<StepTree> buildBatchStepTree(BatchStepByStepParam stepByOptParam) {
        List<Object> stepTrees = new ArrayList();
        boolean partResource = this.stepUtil.isPartResource();
        stepTrees = partResource ? this.buildBatchMultiStepTree(stepByOptParam) : this.buildBatchAllStepTree(stepByOptParam);
        return stepTrees;
    }

    private List<StepTree> buildBatchMultiStepTree(BatchStepByStepParam stepByOptParam) {
        List<StepTree> stepTrees = new ArrayList<StepTree>();
        try {
            WorkFlowType uploadType = this.stepUtil.uploadType(stepByOptParam.getFormSchemeKey());
            FormSchemeDefine formScheme = this.commonUtil.getFormScheme(stepByOptParam.getFormSchemeKey());
            DimensionValueSet buildDimensionSet = this.dimensionUtil.mergeDimensionValueSet(stepByOptParam.getStepUnit(), stepByOptParam.getFormSchemeKey());
            Map<String, LinkedHashSet<String>> selectFormOrGroupKeyMap = this.stepFormOrGroupKeyMap(stepByOptParam.getFormSchemeKey(), uploadType, stepByOptParam.getFormKeys(), stepByOptParam.getGroupKeys());
            stepTrees = this.buildAllTree(stepByOptParam.getFormSchemeKey(), buildDimensionSet, selectFormOrGroupKeyMap);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return stepTrees;
    }

    private List<StepTree> buildBatchAllStepTree(BatchStepByStepParam stepByOptParam) {
        List<StepTree> stepTrees = new ArrayList<StepTree>();
        try {
            FormSchemeDefine formScheme = this.commonUtil.getFormScheme(stepByOptParam.getFormSchemeKey());
            WorkFlowType uploadType = this.stepUtil.uploadType(stepByOptParam.getFormSchemeKey());
            DimensionValueSet buildDimensionSet = this.dimensionUtil.mergeDimensionValueSet(stepByOptParam.getStepUnit(), stepByOptParam.getFormSchemeKey());
            Map<String, LinkedHashSet<String>> stepFormOrGroupKeyMap = this.stepFormOrGroupKeyMap(stepByOptParam.getFormSchemeKey(), uploadType, stepByOptParam.getStepFormKeyMap(), stepByOptParam.getGroupKeys());
            stepTrees = this.buildAllTree(stepByOptParam.getFormSchemeKey(), buildDimensionSet, stepFormOrGroupKeyMap);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return stepTrees;
    }

    private List<StepTree> buildAllTree(String formSchemeKey, DimensionValueSet dimensionSet, Map<String, LinkedHashSet<String>> formOrGroupKeyMap) {
        List<Object> stepTrees = new ArrayList();
        WorkFlowType uploadType = this.stepUtil.uploadType(formSchemeKey);
        BuildAllTree buildSingleTree = new BuildAllTree(formOrGroupKeyMap, formSchemeKey, dimensionSet, this.dimensionUtil, this.deEntityHelper, this.commonUtil);
        if (this.stepUtil.isUploadEntity(uploadType)) {
            stepTrees = buildSingleTree.buildUnitData();
        } else if (this.stepUtil.isUploadForm(uploadType) || this.stepUtil.isUploadGroup(uploadType) || this.stepUtil.isPartResource()) {
            stepTrees = buildSingleTree.buildResourceTree();
        }
        return stepTrees;
    }

    private LinkedHashSet<String> formOrGroupKey(String formSchemeKey, WorkFlowType uploadType, LinkedHashSet<String> formKeys, LinkedHashSet<String> groupKeys) {
        LinkedHashSet<String> ids = new LinkedHashSet<String>();
        ids.add(this.commonUtil.formId(formSchemeKey));
        return WorkFlowType.FORM.equals((Object)uploadType) ? formKeys : (WorkFlowType.GROUP.equals((Object)uploadType) ? groupKeys : ids);
    }

    public LinkedHashSet<String> formOrGroupKey(String formSchemeKey, LinkedHashSet<String> formKeys, LinkedHashSet<String> groupKeys) {
        LinkedHashSet<String> ids = new LinkedHashSet<String>();
        ids.add(this.commonUtil.formId(formSchemeKey));
        WorkFlowType uploadType = this.stepUtil.uploadType(formSchemeKey);
        return WorkFlowType.FORM.equals((Object)uploadType) ? formKeys : (WorkFlowType.GROUP.equals((Object)uploadType) ? groupKeys : ids);
    }

    private Map<String, LinkedHashSet<String>> stepFormOrGroupKeyMap(String formSchemeKey, WorkFlowType uploadType, Map<String, LinkedHashSet<String>> formMap, Map<String, LinkedHashSet<String>> groupMap) {
        return WorkFlowType.FORM.equals((Object)uploadType) ? formMap : (WorkFlowType.GROUP.equals((Object)uploadType) ? groupMap : null);
    }
}

