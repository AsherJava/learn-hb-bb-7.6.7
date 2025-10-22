/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignSchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskGroupDefine
 *  com.jiuqi.nr.definition.internal.controller.DesignTimeViewController
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.unit.uselector.cacheset.USelectorResultSet
 */
package com.jiuqi.nr.system.check.service.impl;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignSchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.definition.internal.controller.DesignTimeViewController;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.system.check.exception.SystemCheckException;
import com.jiuqi.nr.system.check.model.response.fieldupgrade.EntityDefineInfoVO;
import com.jiuqi.nr.system.check.model.response.fieldupgrade.EntityVO;
import com.jiuqi.nr.system.check.model.response.fieldupgrade.FormSchemeInfoVO;
import com.jiuqi.nr.system.check.model.response.fieldupgrade.TaskInfoVO;
import com.jiuqi.nr.system.check.model.response.fieldupgrade.TreeNode;
import com.jiuqi.nr.system.check.service.FieldUpgradeService;
import com.jiuqi.nr.unit.uselector.cacheset.USelectorResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class FieldUpgradeServiceImpl
implements FieldUpgradeService {
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private USelectorResultSet uSelectorResultSet;
    @Autowired
    private DesignTimeViewController desCtrl;
    @Autowired
    private IDataDefinitionRuntimeController dataRuntimeCtrl;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IEntityDataService entityDataService;

    @Override
    public List<TreeNode> treeNodeList() {
        List groupDefines = this.designTimeViewController.listAllTaskGroup();
        TreeNode root = new TreeNode();
        LinkedHashMap<String, TreeNode> treeNodeMap = new LinkedHashMap<String, TreeNode>();
        for (DesignTaskGroupDefine groupDefine : groupDefines) {
            treeNodeMap.put(groupDefine.getKey(), this.convertGroup(groupDefine));
            this.appendTaskNode(treeNodeMap, groupDefine.getKey());
        }
        this.appendTaskNode(treeNodeMap, null);
        this.build(treeNodeMap, root);
        return root.getChildren();
    }

    private void appendTaskNode(Map<String, TreeNode> treeNodeMap, String key) {
        List taskDefines = this.designTimeViewController.listTaskByTaskGroup(key);
        if (taskDefines != null) {
            for (DesignTaskDefine taskDefine : taskDefines) {
                treeNodeMap.put(taskDefine.getKey(), this.convertTask(taskDefine, key));
            }
        }
    }

    private void build(Map<String, TreeNode> treeNodeMap, TreeNode root) {
        for (TreeNode treeNode : treeNodeMap.values()) {
            String parent = treeNode.getParent();
            if (treeNodeMap.containsKey(parent)) {
                TreeNode temp = treeNodeMap.get(parent);
                temp.addChildren(treeNode);
                continue;
            }
            root.addChildren(treeNode);
        }
    }

    private TreeNode convertTask(DesignTaskDefine taskDefine, String parent) {
        TreeNode treeNode = new TreeNode();
        treeNode.setParent(parent);
        treeNode.setTitle(taskDefine.getTitle());
        treeNode.setKey(taskDefine.getKey());
        treeNode.setType(TreeNode.Type.TASK);
        return treeNode;
    }

    private TreeNode convertGroup(DesignTaskGroupDefine groupDefine) {
        TreeNode treeNode = new TreeNode();
        treeNode.setTitle(groupDefine.getTitle());
        treeNode.setKey(groupDefine.getKey());
        treeNode.setParent(groupDefine.getParentKey());
        treeNode.setType(TreeNode.Type.GROUP);
        return treeNode;
    }

    @Override
    public List<TaskInfoVO> getTaskInfo(List<String> keys) {
        ArrayList<TaskInfoVO> taskInfoVOList = new ArrayList<TaskInfoVO>(keys.size());
        for (String key : keys) {
            DesignTaskDefine task = this.designTimeViewController.getTask(key);
            TaskInfoVO taskInfoVO = new TaskInfoVO();
            taskInfoVO.setKey(key);
            taskInfoVO.setTitle(task.getTitle());
            List formSchemeDefines = this.designTimeViewController.listFormSchemeByTask(key);
            ArrayList<FormSchemeInfoVO> formSchemeInfoVOList = new ArrayList<FormSchemeInfoVO>(formSchemeDefines.size());
            for (DesignFormSchemeDefine formSchemeDefine : formSchemeDefines) {
                FormSchemeInfoVO formSchemeInfoVO = new FormSchemeInfoVO();
                List formDefines = this.designTimeViewController.listFormByFormSchemeAndType(formSchemeDefine.getKey(), FormType.FORM_TYPE_NEWFMDM);
                formSchemeInfoVO.setHasFMDM(!formDefines.isEmpty());
                formSchemeInfoVO.setKey(formSchemeDefine.getKey());
                formSchemeInfoVO.setTitle(formSchemeDefine.getTitle());
                formSchemeInfoVO.setEntityDefineInfos(this.solutionDim(task.getDims()));
                String period = this.queryPeriod(formSchemeDefine.getKey());
                formSchemeInfoVO.setPeriod(period);
                formSchemeInfoVOList.add(formSchemeInfoVO);
            }
            taskInfoVO.setFormSchemeInfos(formSchemeInfoVOList);
            taskInfoVOList.add(taskInfoVO);
        }
        return taskInfoVOList;
    }

    public String queryPeriod(String formSchemeKey) {
        List linkDefines;
        try {
            linkDefines = this.desCtrl.querySchemePeriodLinkByScheme(formSchemeKey);
        }
        catch (Exception e) {
            throw new SystemCheckException(e);
        }
        if (CollectionUtils.isEmpty(linkDefines)) {
            return null;
        }
        for (int i = 0; i < linkDefines.size(); ++i) {
            if (!((DesignSchemePeriodLinkDefine)linkDefines.get(i)).getIsdefault() && i != linkDefines.size() - 1) continue;
            return ((DesignSchemePeriodLinkDefine)linkDefines.get(i)).getPeriodKey();
        }
        return null;
    }

    private List<EntityDefineInfoVO> solutionDim(String dim) {
        ArrayList<EntityDefineInfoVO> entityDefineInfoVOList = new ArrayList<EntityDefineInfoVO>();
        if (StringUtils.hasLength(dim)) {
            String[] entityIds;
            for (String entityId : entityIds = dim.split(";")) {
                IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityId);
                EntityDefineInfoVO entityDefineInfoVO = new EntityDefineInfoVO();
                entityDefineInfoVO.setCode(entityDefine.getCode());
                entityDefineInfoVO.setTitle(entityDefine.getTitle());
                entityDefineInfoVOList.add(entityDefineInfoVO);
            }
        }
        return entityDefineInfoVOList;
    }

    @Override
    public List<EntityVO> getSelectedEntities(String unitSelectKey) {
        ArrayList<EntityVO> entityVOS = new ArrayList<EntityVO>();
        List filterSet = this.uSelectorResultSet.getFilterSet(unitSelectKey);
        if (CollectionUtils.isEmpty(filterSet)) {
            return entityVOS;
        }
        IUnitTreeContext runContext = this.uSelectorResultSet.getRunContext(unitSelectKey);
        String entityId = runContext.getEntityDefine().getId();
        String period = runContext.getPeriod();
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityId);
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue(entityDefine.getDimensionName(), (Object)filterSet);
        if (StringUtils.hasText(period)) {
            dimensionValueSet.setValue("DATATIME", (Object)period);
        }
        List<IEntityRow> allRows = this.listEntityRows(entityId, dimensionValueSet, null);
        allRows.forEach(r -> {
            EntityVO entityVO = new EntityVO();
            entityVO.setKey(r.getEntityKeyData());
            entityVO.setCode(r.getCode());
            entityVO.setTitle(r.getTitle());
            entityVOS.add(entityVO);
        });
        return entityVOS;
    }

    public List<IEntityRow> listEntityRows(String entityId, DimensionValueSet dimensionValueSet, String periodView) {
        IEntityTable iEntityTable;
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(entityId);
        entityQuery.setEntityView(entityViewDefine);
        entityQuery.setMasterKeys(dimensionValueSet);
        ExecutorContext context = new ExecutorContext(this.dataRuntimeCtrl);
        context.setPeriodView(periodView);
        try {
            iEntityTable = entityQuery.executeReader((IContext)context);
        }
        catch (Exception e) {
            throw new SystemCheckException(e);
        }
        if (iEntityTable == null) {
            return Collections.emptyList();
        }
        return iEntityTable.getAllRows();
    }

    private String getPeriodViewByFormSchemeKey(String formSchemeKey) {
        DesignFormSchemeDefine formSchemeDefine = this.desCtrl.queryFormSchemeDefine(formSchemeKey);
        String periodView = formSchemeDefine.getDateTime();
        if (!StringUtils.hasText(periodView)) {
            DesignTaskDefine designTaskDefine = this.desCtrl.queryTaskDefine(formSchemeDefine.getTaskKey());
            periodView = designTaskDefine.getDateTime();
        }
        return periodView;
    }
}

