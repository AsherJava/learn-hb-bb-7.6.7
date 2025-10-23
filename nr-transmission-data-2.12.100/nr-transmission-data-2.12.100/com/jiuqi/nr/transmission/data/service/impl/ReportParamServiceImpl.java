/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.np.util.tree.Tree
 *  com.jiuqi.nr.dataentry.bean.FormsQueryResult
 *  com.jiuqi.nr.dataentry.tree.FormTree
 *  com.jiuqi.nr.dataentry.tree.FormTreeItem
 *  com.jiuqi.nr.definition.common.TaskType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.mapping2.common.NrMappingUtil
 *  com.jiuqi.nr.mapping2.provider.NrMappingParam
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nr.period.util.JacksonUtils
 *  com.jiuqi.nvwa.mapping.bean.MappingScheme
 *  com.jiuqi.nvwa.mapping.service.IMappingSchemeService
 *  com.jiuqi.nvwa.subsystem.core.SubSystemException
 *  com.jiuqi.nvwa.subsystem.core.manage.ISubServerManager
 *  com.jiuqi.nvwa.subsystem.core.model.SubServer
 */
package com.jiuqi.nr.transmission.data.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.np.util.tree.Tree;
import com.jiuqi.nr.dataentry.bean.FormsQueryResult;
import com.jiuqi.nr.dataentry.tree.FormTree;
import com.jiuqi.nr.dataentry.tree.FormTreeItem;
import com.jiuqi.nr.definition.common.TaskType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.mapping2.common.NrMappingUtil;
import com.jiuqi.nr.mapping2.provider.NrMappingParam;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.period.util.JacksonUtils;
import com.jiuqi.nr.transmission.data.common.MultilingualLog;
import com.jiuqi.nr.transmission.data.common.Utils;
import com.jiuqi.nr.transmission.data.domain.TaskNodeParam;
import com.jiuqi.nr.transmission.data.domain.TransmissionTaskUserParam;
import com.jiuqi.nr.transmission.data.dto.SyncSchemeParamDTO;
import com.jiuqi.nr.transmission.data.intf.DimInfoParam;
import com.jiuqi.nr.transmission.data.intf.EntityInfoParam;
import com.jiuqi.nr.transmission.data.service.IReportParamService;
import com.jiuqi.nr.transmission.data.vo.MappingSchemeVO;
import com.jiuqi.nr.transmission.data.vo.ReselectPeriodVO;
import com.jiuqi.nvwa.mapping.bean.MappingScheme;
import com.jiuqi.nvwa.mapping.service.IMappingSchemeService;
import com.jiuqi.nvwa.subsystem.core.SubSystemException;
import com.jiuqi.nvwa.subsystem.core.manage.ISubServerManager;
import com.jiuqi.nvwa.subsystem.core.model.SubServer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class ReportParamServiceImpl
implements IReportParamService {
    private static final Logger logger = LoggerFactory.getLogger(ReportParamServiceImpl.class);
    @Autowired
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private ISubServerManager subServerManager;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IMappingSchemeService mappingSchemeService;

    @Override
    public List<TaskNodeParam> initTask(List<String> keys) {
        ArrayList<TaskNodeParam> params = new ArrayList<TaskNodeParam>();
        TaskNodeParam rootNode = this.buildRoot();
        if (keys == null) {
            keys = new ArrayList<String>();
        }
        List<TaskNodeParam> childrenNode = this.buildChildren(keys);
        rootNode.setChildren(childrenNode);
        params.add(rootNode);
        return params;
    }

    @Override
    public List<String> getTaskList() {
        String currectSubServerExtConfig;
        List allTaskDefinesByType = this.runTimeAuthViewController.getAllTaskDefinesByType(TaskType.TASK_TYPE_DEFAULT);
        if (CollectionUtils.isEmpty(allTaskDefinesByType)) {
            return Collections.emptyList();
        }
        List runtimeTaskKeys = allTaskDefinesByType.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
        try {
            currectSubServerExtConfig = this.subServerManager.getCurrectSubServerExtConfig("EXT_TASK_LIST");
        }
        catch (Exception e) {
            logger.error("\u591a\u7ea7\u90e8\u7f72\u5173\u8054\u4efb\u52a1\u67e5\u8be2\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5\u4e4b\u670d\u52a1\u662f\u5426\u542f\u52a8\u6216\u8005\u4e3b\u5b50\u670d\u52a1\u662f\u5426\u5173\u8054\u4e86\u4efb\u52a1" + e.getMessage(), e);
            return Collections.emptyList();
        }
        if (StringUtils.hasText(currectSubServerExtConfig)) {
            TransmissionTaskUserParam param = (TransmissionTaskUserParam)JacksonUtils.jsonToObject((String)currectSubServerExtConfig, TransmissionTaskUserParam.class);
            if (param != null && !CollectionUtils.isEmpty(param.getSelectedTask())) {
                List<String> taskKeys = param.getSelectedTask();
                return taskKeys.stream().filter(runtimeTaskKeys::contains).collect(Collectors.toList());
            }
            return Collections.emptyList();
        }
        return Collections.emptyList();
    }

    @Override
    public List<TaskNodeParam> initUser(List<String> userKeys) {
        ArrayList<TaskNodeParam> params = new ArrayList<TaskNodeParam>();
        ArrayList<TaskNodeParam> childNodeParams = new ArrayList<TaskNodeParam>();
        List allUsers = this.userService.getAllUsers();
        TaskNodeParam rootNode = this.buildRoot();
        rootNode.setTitle(MultilingualLog.reportParamServiceMessage(1));
        if (userKeys == null) {
            userKeys = new ArrayList<String>();
        }
        for (User user : allUsers) {
            TaskNodeParam node = new TaskNodeParam();
            if (userKeys.contains(user.getName())) {
                node.setChecked(true);
            }
            node.setKey(user.getId());
            node.setTitle(user.getName());
            childNodeParams.add(node);
        }
        rootNode.setChildren(childNodeParams);
        params.add(rootNode);
        return params;
    }

    @Override
    public List<String> checkUser(List<String> userKeys) {
        ArrayList<String> noexistUser = new ArrayList<String>();
        List allUsers = this.userService.getAllUsers();
        List allUserNames = allUsers.stream().map(User::getName).collect(Collectors.toList());
        for (String userKey : userKeys) {
            if (allUserNames.contains(userKey)) continue;
            noexistUser.add(userKey);
        }
        return noexistUser;
    }

    @Override
    public FormsQueryResult getForms(String formSchemeKey) {
        FormsQueryResult result = new FormsQueryResult();
        FormTree formTree = this.getFormTree(formSchemeKey);
        result.setTree(formTree);
        return result;
    }

    @Override
    public List<EntityInfoParam> getEntityList(DimensionValueSet dimensionValueSet, String formSchemeKey, AuthorityType authType, Boolean ignoreFilter) {
        IEntityTable iEntityTable;
        ArrayList<EntityInfoParam> entityList = new ArrayList<EntityInfoParam>();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (formScheme == null) {
            throw new IllegalArgumentException("\u627e\u4e0d\u5230\u62a5\u8868\u65b9\u6848:" + formSchemeKey);
        }
        if (authType == null) {
            authType = AuthorityType.None;
        }
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setEntityView(this.getEntityView(formScheme));
        entityQuery.setMasterKeys(dimensionValueSet);
        entityQuery.setAuthorityOperations(authType);
        entityQuery.setIgnoreViewFilter(ignoreFilter.booleanValue());
        ExecutorContext executorContext = this.buildContext(formScheme);
        try {
            iEntityTable = entityQuery.executeReader((IContext)executorContext);
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u4e3b\u7ef4\u5ea6\u4fe1\u606f\u9519\u8bef\uff1a" + e.getMessage(), e);
            return entityList;
        }
        List allRows = iEntityTable.getAllRows();
        for (IEntityRow row : allRows) {
            entityList.add(new EntityInfoParam(row.getCode(), row.getTitle(), row.getEntityKeyData()));
        }
        return entityList;
    }

    @Override
    public List<DimInfoParam> getDimValues(String entityId, String periodView) {
        ArrayList<DimInfoParam> dimLists = new ArrayList<DimInfoParam>();
        EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(entityId);
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setEntityView(entityViewDefine);
        entityQuery.setMasterKeys(new DimensionValueSet());
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        executorContext.setPeriodView(periodView);
        IEntityTable iEntityTable = null;
        try {
            iEntityTable = entityQuery.executeFullBuild((IContext)executorContext);
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u4e3b\u7ef4\u5ea6\u4fe1\u606f\u9519\u8bef\uff1a" + e.getMessage(), e);
        }
        List allRows = iEntityTable.getAllRows();
        for (IEntityRow row : allRows) {
            dimLists.add(new DimInfoParam(row.getEntityKeyData(), row.getCode(), row.getTitle()));
        }
        return dimLists;
    }

    @Override
    public IEntityTable getEntityList(DimensionValueSet dimensionValueSet, String formSchemeKey) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (formScheme == null) {
            throw new IllegalArgumentException("\u627e\u4e0d\u5230\u62a5\u8868\u65b9\u6848:" + formSchemeKey);
        }
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setEntityView(this.getEntityView(formScheme));
        entityQuery.setMasterKeys(dimensionValueSet);
        entityQuery.setAuthorityOperations(AuthorityType.None);
        entityQuery.setIgnoreViewFilter(true);
        ExecutorContext executorContext = this.buildContext(formScheme);
        IEntityTable iEntityTable = null;
        try {
            iEntityTable = entityQuery.executeReader((IContext)executorContext);
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u4e3b\u7ef4\u5ea6\u4fe1\u606f\u9519\u8bef\uff1a" + e.getMessage(), e);
            return iEntityTable;
        }
        return iEntityTable;
    }

    @Override
    public String doLogHelperMessage(SyncSchemeParamDTO syncSchemeParamDTO) {
        String schemeName = Utils.getSchemeName(syncSchemeParamDTO);
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(syncSchemeParamDTO.getTask());
        String periodValue = syncSchemeParamDTO.getPeriodValue();
        List<String> dimensionValue = null;
        String formSchemeKey = StringUtils.hasText(syncSchemeParamDTO.getFormSchemeKey()) ? syncSchemeParamDTO.getFormSchemeKey() : Utils.getFormScheme(syncSchemeParamDTO.getTask(), periodValue);
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        String dw = formScheme.getDw();
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        String dimensionName = this.entityMetaService.getDimensionName(dw);
        if (StringUtils.hasText(syncSchemeParamDTO.getEntity())) {
            dimensionValue = Arrays.asList(syncSchemeParamDTO.getEntity().split(";"));
        }
        dimensionValueSet.setValue(dimensionName, dimensionValue);
        dimensionValueSet.setValue("DATATIME", (Object)periodValue);
        List<EntityInfoParam> entityList = this.getEntityList(dimensionValueSet, formSchemeKey, AuthorityType.None, true);
        StringBuilder unitMessages = new StringBuilder();
        int i = 0;
        for (EntityInfoParam row : entityList) {
            unitMessages.append(row.getTitle()).append("[").append(row.getEntityKeyData()).append("]");
            if (i < entityList.size() - 1) {
                unitMessages.append("\u3001");
            } else {
                unitMessages.append("\uff0c");
            }
            ++i;
        }
        ArrayList<String> allforms = new ArrayList<String>();
        if (StringUtils.hasText(syncSchemeParamDTO.getForm())) {
            allforms.addAll(Arrays.asList(syncSchemeParamDTO.getForm().split(";")));
        }
        List formDefines = this.runTimeViewController.queryFormsById(allforms);
        int j = 0;
        StringBuilder formMessages = new StringBuilder();
        for (FormDefine formDefine : formDefines) {
            if (formDefine == null) continue;
            formMessages.append(formDefine.getTitle()).append("[").append(formDefine.getFormCode()).append("]");
            if (j < formDefines.size() - 1) {
                formMessages.append("\u3001");
            } else {
                formMessages.append("\uff0c");
            }
            ++j;
        }
        String sbs = MultilingualLog.reportParamServiceMessage1(schemeName, taskDefine.getTitle(), periodValue, unitMessages.toString(), formMessages.toString());
        return sbs;
    }

    @Override
    public ReselectPeriodVO reselectPeriod(ReselectPeriodVO reselectPeriodVO) {
        ReselectPeriodVO vo = new ReselectPeriodVO();
        String newFormSchemeKey = reselectPeriodVO.getFormSchemeKey();
        newFormSchemeKey = this.getFormSchemeKey(reselectPeriodVO.getPeriod(), reselectPeriodVO.getTaskKey());
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(newFormSchemeKey);
        vo.setFormSchemeKey(newFormSchemeKey);
        List allSchemes = this.mappingSchemeService.getAllSchemes();
        ArrayList<MappingSchemeVO> mappingSchemes = new ArrayList<MappingSchemeVO>();
        for (MappingScheme allScheme : allSchemes) {
            NrMappingParam nrMappingParam = NrMappingUtil.getNrMappingParam((MappingScheme)allScheme);
            if (nrMappingParam == null || !reselectPeriodVO.getTaskKey().equals(nrMappingParam.getTaskKey()) || !newFormSchemeKey.equals(nrMappingParam.getFormSchemeKey())) continue;
            mappingSchemes.add(new MappingSchemeVO(allScheme.getKey(), allScheme.getTitle()));
        }
        if (mappingSchemes.size() > 0) {
            vo.setMappingSchemes(mappingSchemes);
        }
        if (!CollectionUtils.isEmpty(reselectPeriodVO.getSelectEntity())) {
            String dimensionName = this.entityMetaService.getDimensionName(formScheme.getDw());
            DimensionValueSet dimensionValueSet = new DimensionValueSet();
            dimensionValueSet.setValue(dimensionName, reselectPeriodVO.getSelectEntity());
            dimensionValueSet.setValue("DATATIME", (Object)reselectPeriodVO.getPeriod());
            List<EntityInfoParam> entityList = this.getEntityList(dimensionValueSet, formScheme.getKey(), AuthorityType.None, false);
            List<String> entityKeys = entityList.stream().map(EntityInfoParam::getEntityKeyData).collect(Collectors.toList());
            vo.setSelectEntity(entityKeys);
        }
        return vo;
    }

    private String getFormSchemeKey(String periodValue, String task) {
        String formSchemeKey = "";
        try {
            SchemePeriodLinkDefine schemePeriodLinkDefine = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(periodValue, task);
            formSchemeKey = schemePeriodLinkDefine.getSchemeKey();
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u4efb\u52a1\uff1a" + task + "\uff0c\u65f6\u671f\uff1a" + periodValue + "\u5bf9\u5e94\u7684\u65b9\u6848\u9519\u8bef");
        }
        return formSchemeKey;
    }

    @Override
    public ReselectPeriodVO getPeriodValue(int period, String task) {
        String taskToPeriod;
        int i;
        ReselectPeriodVO result = new ReselectPeriodVO();
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(task);
        String dateTime = taskDefine.getDateTime();
        IPeriodProvider periodProvider = this.periodEntityAdapter.getPeriodProvider(dateTime);
        IPeriodRow curPeriod = periodProvider.getCurPeriod();
        String periodValue = -1 == period ? periodProvider.priorPeriod(curPeriod.getCode()) : (1 == period ? periodProvider.nextPeriod(curPeriod.getCode()) : curPeriod.getCode());
        int taskPeriodOffset = taskDefine.getTaskPeriodOffset();
        if (taskPeriodOffset > 0) {
            for (i = 0; i < taskPeriodOffset; ++i) {
                periodValue = periodProvider.nextPeriod(periodValue);
            }
        } else if (taskPeriodOffset < 0) {
            for (i = 0; i < -taskPeriodOffset; ++i) {
                periodValue = periodProvider.priorPeriod(periodValue);
            }
        }
        if (periodProvider.comparePeriod(taskToPeriod = taskDefine.getToPeriod(), periodValue) < 0) {
            periodValue = taskToPeriod;
            result.setPeriod(periodValue);
            result.setFormSchemeKey(this.getFormSchemeKey(periodValue, task));
            return result;
        }
        String tasFromPeriod = taskDefine.getFromPeriod();
        if (periodProvider.comparePeriod(tasFromPeriod, periodValue) > 0) {
            periodValue = tasFromPeriod;
            result.setPeriod(periodValue);
            result.setFormSchemeKey(this.getFormSchemeKey(periodValue, task));
            return result;
        }
        result.setPeriod(periodValue);
        result.setFormSchemeKey(this.getFormSchemeKey(periodValue, task));
        return result;
    }

    @Override
    public SubServer getParentServeNode() {
        SubServer currectSubServer;
        try {
            currectSubServer = this.subServerManager.getCurrectSubServer();
        }
        catch (SubSystemException e) {
            logger.error("\u4e0a\u7ea7\u670d\u52a1\u67e5\u8be2\u9519\u8bef", e);
            throw new RuntimeException(e);
        }
        return currectSubServer.getSupServer();
    }

    private ExecutorContext buildContext(FormSchemeDefine formScheme) {
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        executorContext.setPeriodView(formScheme.getDateTime());
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formScheme.getKey());
        executorContext.setEnv((IFmlExecEnvironment)environment);
        return executorContext;
    }

    private FormTree getFormTree(String formSchemeKey) {
        FormTreeItem rootItem = new FormTreeItem();
        rootItem.setCode("root");
        Tree tree = new Tree((Object)rootItem);
        List rootGroups = this.runTimeViewController.queryRootGroupsByFormScheme(formSchemeKey);
        this.addChildren((Tree<FormTreeItem>)tree, rootGroups);
        FormTree formTree = new FormTree();
        formTree.setTree(tree);
        return formTree;
    }

    private void addChildren(Tree<FormTreeItem> tree, List<FormGroupDefine> groups) {
        if (CollectionUtils.isEmpty(groups)) {
            return;
        }
        for (FormGroupDefine formGroup : groups) {
            List allFormsInGroup;
            int count = 0;
            FormTreeItem groupItem = new FormTreeItem();
            groupItem.setKey(formGroup.getKey());
            groupItem.setCode(formGroup.getCode());
            groupItem.setTitle(formGroup.getTitle());
            groupItem.setType("group");
            Tree groupTree = tree.addChild((Object)groupItem);
            List childrenGroups = this.runTimeViewController.getAllGroupsInGroup(formGroup.getKey());
            if (!CollectionUtils.isEmpty(childrenGroups)) {
                List children = groupTree.getChildren();
                int beforeSize = children.size();
                this.addChildren((Tree<FormTreeItem>)groupTree, childrenGroups);
                int afterSize = children.size();
                if (afterSize > beforeSize) {
                    ++count;
                }
            }
            try {
                allFormsInGroup = this.runTimeViewController.getAllFormsInGroup(formGroup.getKey());
            }
            catch (Exception e) {
                continue;
            }
            for (FormDefine form : allFormsInGroup) {
                FormTreeItem reportItem = new FormTreeItem();
                reportItem.setKey(form.getKey());
                reportItem.setCode(form.getFormCode());
                reportItem.setTitle(form.getTitle());
                reportItem.setSerialNumber(form.getSerialNumber());
                reportItem.setFormType(form.getFormType().name());
                reportItem.setGroupKey(formGroup.getKey());
                reportItem.setType("form");
                groupTree.addChild((Object)reportItem);
                ++count;
            }
            if (count != 0) continue;
            tree.pruneChild((Object)groupItem);
        }
    }

    public TaskNodeParam buildRoot() {
        TaskNodeParam taskNodeParam = new TaskNodeParam();
        taskNodeParam.setKey("--");
        taskNodeParam.setTitle("\u4efb\u52a1\u5217\u8868");
        taskNodeParam.setExpand(true);
        return taskNodeParam;
    }

    public List<TaskNodeParam> buildChildren(List<String> keys) {
        ArrayList<TaskNodeParam> taskNodeParams = new ArrayList<TaskNodeParam>();
        List allTask = this.runTimeAuthViewController.getAllTaskDefinesByType(TaskType.TASK_TYPE_DEFAULT);
        if (CollectionUtils.isEmpty(allTask)) {
            return Collections.emptyList();
        }
        for (TaskDefine taskDefine : allTask) {
            TaskNodeParam node = new TaskNodeParam();
            if (keys.contains(taskDefine.getKey())) {
                node.setChecked(true);
            }
            node.setKey(taskDefine.getKey());
            node.setTitle(taskDefine.getTitle());
            taskNodeParams.add(node);
        }
        Collections.reverse(taskNodeParams);
        return taskNodeParams;
    }

    private EntityViewDefine getEntityView(FormSchemeDefine formScheme) {
        String dw = formScheme.getDw();
        String filterExpression = formScheme.getFilterExpression();
        if (!StringUtils.hasText(dw)) {
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
            dw = taskDefine.getDw();
            filterExpression = taskDefine.getFilterExpression();
        }
        if (!StringUtils.hasText(dw)) {
            throw new IllegalArgumentException("\u62a5\u8868\u65b9\u6848\uff0c" + formScheme.getTitle() + "\uff0c\u83b7\u53d6\u4e0d\u5230\u4e3b\u7ef4\u5ea6\u4fe1\u606f");
        }
        return this.entityViewRunTimeController.buildEntityView(dw, filterExpression);
    }
}

