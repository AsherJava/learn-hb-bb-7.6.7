/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskGroupDefine
 *  com.jiuqi.nr.definition.option.DimGroupOptionService
 *  com.jiuqi.nr.definition.option.dto.DimensionGroupDTO
 *  com.jiuqi.nr.definition.option.dto.ReferEntity
 *  com.jiuqi.nr.definition.option.treegroup.GroupInfo
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.summary.manage.provider;

import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskGroupDefine;
import com.jiuqi.nr.definition.option.DimGroupOptionService;
import com.jiuqi.nr.definition.option.dto.DimensionGroupDTO;
import com.jiuqi.nr.definition.option.dto.ReferEntity;
import com.jiuqi.nr.definition.option.treegroup.GroupInfo;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.summary.api.service.IDesignSummarySolutionService;
import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.model.soulution.SummarySolutionModel;
import com.jiuqi.nr.summary.service.IDataAccessService;
import com.jiuqi.nr.summary.service.SummaryParamService;
import com.jiuqi.nr.summary.vo.ListNode;
import com.jiuqi.nr.summary.vo.SummarySolutionModelForm;
import com.jiuqi.nr.summary.vo.TaskNodeData;
import com.jiuqi.nr.summary.vo.TaskParamVO;
import com.jiuqi.nr.summary.vo.TreeNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class SummaryParamVOProvider {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private SummaryParamService summaryParamService;
    @Autowired
    private IDesignSummarySolutionService designSolutionService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IDataAccessService dataAccessService;
    @Autowired
    private DefinitionAuthorityProvider authorityProvider;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private DimGroupOptionService dimGroupOptionService;

    public TaskParamVO getTaskParamForSolution(String taskKey) throws SummaryCommonException {
        TaskParamVO param = new TaskParamVO();
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        List dimensions = this.runtimeDataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme(), DimensionType.DIMENSION);
        HashMap dimensionMap = new HashMap();
        if (!CollectionUtils.isEmpty(dimensions)) {
            dimensions.forEach(dim -> dimensionMap.put(dim.getDimKey(), dim));
        }
        param.setRelatedTasks(this.getRelatedTasks(taskKey));
        param.setTargetDims(this.getTargetDims(param, taskKey));
        param.setPeriodType(taskDefine.getPeriodType());
        param.setSceneDims(this.summaryParamService.getSceneDimensions(taskKey).stream().filter(entityDefine -> !this.isMergeUnitScene(taskDefine, (IEntityDefine)entityDefine, dimensionMap)).map(this::buildListNodeFromEntity).collect(Collectors.toList()));
        return param;
    }

    private boolean isMergeUnitScene(TaskDefine taskDefine, IEntityDefine entityDefine, Map<String, DataDimension> dimensionMap) {
        String masterDim = taskDefine.getDw();
        IEntityModel entityModel = this.entityMetaService.getEntityModel(masterDim);
        DataDimension dataDimension = dimensionMap.get(entityDefine.getId());
        Iterator attributes = entityModel.getAttributes();
        while (attributes.hasNext()) {
            IEntityAttribute attribute = (IEntityAttribute)attributes.next();
            String dimAttribute = dataDimension.getDimAttribute();
            if (!StringUtils.hasLength(dimAttribute) || !dimAttribute.equals(attribute.getCode()) || attribute.isMultival()) continue;
            return true;
        }
        return false;
    }

    public List<TreeNode> getTasks(String taskGroupKey) {
        ArrayList<TreeNode> nodes = new ArrayList<TreeNode>();
        List<TreeNode> taskGroupNodes = this.runTimeViewController.getChildTaskGroups(taskGroupKey, false).stream().filter(taskGroup -> this.authorityProvider.canReadTaskGroup(taskGroup.getKey())).map(this::buildTaskTreeFromTaskGroup).collect(Collectors.toList());
        List<TreeNode> taskNodes = this.runTimeViewController.getAllRunTimeTasksInGroup(taskGroupKey).stream().filter(task -> this.authorityProvider.canReadTask(task.getKey())).map(this::buildTaskTreeFromTask).collect(Collectors.toList());
        if (taskGroupKey == null) {
            TreeNode root = this.buildTaskTreeRoot();
            root.addAllChild(taskGroupNodes);
            root.addAllChild(taskNodes);
            nodes.add(root);
        } else {
            nodes.addAll(taskGroupNodes);
            nodes.addAll(taskNodes);
        }
        return nodes;
    }

    public boolean relatedTaskUsed(String relatedTaskKey) {
        return false;
    }

    public SummarySolutionModelForm getSummarySolutionForm(String solutionKey) throws SummaryCommonException {
        SummarySolutionModel model = this.designSolutionService.getSummarySolutionModel(solutionKey);
        SummarySolutionModelForm solutionForm = new SummarySolutionModelForm();
        solutionForm.setModel(model);
        TaskDefine task = this.runTimeViewController.queryTaskDefine(model.getMainTask());
        List dimensions = this.runtimeDataSchemeService.getDataSchemeDimension(task.getDataScheme(), DimensionType.DIMENSION);
        HashMap dimensionMap = new HashMap();
        if (!CollectionUtils.isEmpty(dimensions)) {
            dimensions.forEach(dim -> dimensionMap.put(dim.getDimKey(), dim));
        }
        solutionForm.setMainTaskTitle(task.getTitle());
        TaskParamVO taskParam = new TaskParamVO();
        taskParam.setRelatedTasks(this.getRelatedTasks(task.getKey()));
        taskParam.setTargetDims(this.getTargetDims(taskParam, task.getKey()));
        taskParam.setPeriodType(task.getPeriodType());
        taskParam.setSceneDims(this.summaryParamService.getSceneDimensions(task.getKey()).stream().filter(entityDefine -> !this.isMergeUnitScene(task, (IEntityDefine)entityDefine, dimensionMap)).map(this::buildListNodeFromEntity).collect(Collectors.toList()));
        solutionForm.setTaskParam(taskParam);
        solutionForm.setDeployed(this.designSolutionService.isDeployed(solutionKey));
        solutionForm.setHasDataInTable(this.hasData(solutionKey));
        return solutionForm;
    }

    private List<ListNode> getRelatedTasks(String taskKey) throws SummaryCommonException {
        return this.summaryParamService.getRelTaskDefines(taskKey).stream().map(this::buildListNodeFromTask).collect(Collectors.toList());
    }

    private List<ListNode> getTargetDims(TaskParamVO taskParam, String taskKey) throws SummaryCommonException {
        List<ListNode> nodes = this.summaryParamService.getTargetDimensions(taskKey).stream().map(this::buildListNodeFromEntity).collect(Collectors.toList());
        GroupInfo groupInfo = this.dimGroupOptionService.getGroupInfo(taskKey);
        if (groupInfo != null) {
            IEntityDefine relateEntityDefine;
            String relateEntityId;
            String dimFieldCode = groupInfo.getDimFieldCode();
            DimensionGroupDTO dimensionGroup = this.dimGroupOptionService.getDimensionGroup(taskKey);
            Map referEntityMap = dimensionGroup.getReferEntityMap();
            if (!CollectionUtils.isEmpty(referEntityMap) && StringUtils.hasLength(relateEntityId = ((ReferEntity)referEntityMap.get(dimFieldCode)).getEntityId()) && (relateEntityDefine = this.entityMetaService.queryEntity(relateEntityId)) != null) {
                taskParam.setTargetDimDisabled(false);
                nodes.add(this.buildListNodeFromEntity(relateEntityDefine));
            }
        }
        return nodes;
    }

    private TreeNode buildTaskTreeRoot() {
        return new TreeNode("_task_root_key_", "\u5168\u90e8\u4efb\u52a1", false, true, true);
    }

    private TreeNode buildTaskTreeFromTaskGroup(TaskGroupDefine taskGroupDefine) {
        TreeNode treeNode = new TreeNode(taskGroupDefine.getKey(), taskGroupDefine.getTitle());
        treeNode.setDisabled(true);
        return treeNode;
    }

    private TreeNode buildTaskTreeFromTask(TaskDefine taskDefine) {
        TreeNode treeNode = new TreeNode(taskDefine.getKey(), taskDefine.getTitle());
        treeNode.setLeaf(true);
        TaskNodeData taskNodeData = new TaskNodeData();
        taskNodeData.setPeriodType(taskNodeData.getPeriodType());
        treeNode.setData(taskNodeData);
        return treeNode;
    }

    private ListNode buildListNodeFromTask(TaskDefine taskDefine) {
        return new ListNode(taskDefine.getKey(), taskDefine.getTaskCode(), taskDefine.getTitle());
    }

    private ListNode buildListNodeFromEntity(IEntityDefine entityDefine) {
        return new ListNode(entityDefine.getId(), entityDefine.getCode(), entityDefine.getTitle());
    }

    private boolean hasData(String solutionKey) throws SummaryCommonException {
        List dataTables = this.runtimeDataSchemeService.getAllDataTable(solutionKey);
        for (DataTable dataTable : dataTables) {
            int dataCount = this.dataAccessService.getDataCount(dataTable.getCode());
            if (dataCount <= 0) continue;
            return true;
        }
        return false;
    }
}

