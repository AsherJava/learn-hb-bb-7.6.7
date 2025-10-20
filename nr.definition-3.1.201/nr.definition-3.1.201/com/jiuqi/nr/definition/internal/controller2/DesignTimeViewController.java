/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.observer.MessageType
 *  com.jiuqi.np.definition.observer.NpDefinitionObserverable
 *  com.jiuqi.nr.period.util.JacksonUtils
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.util.OrderGenerator
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.definition.internal.controller2;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.observer.MessageType;
import com.jiuqi.np.definition.observer.NpDefinitionObserverable;
import com.jiuqi.nr.definition.api.IDesignTimeFormulaController;
import com.jiuqi.nr.definition.api.IDesignTimePrintController;
import com.jiuqi.nr.definition.api.IDesignTimeReportController;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.common.TaskType;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum2;
import com.jiuqi.nr.definition.exception.NrDefinitionRuntimeException;
import com.jiuqi.nr.definition.facade.DesignAnalysisFormParamDefine;
import com.jiuqi.nr.definition.facade.DesignAnalysisSchemeParamDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkMappingDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignDimensionFilter;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormFoldingDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignRegionSettingDefine;
import com.jiuqi.nr.definition.facade.DesignSchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.definition.facade.DesignTaskLinkDefine;
import com.jiuqi.nr.definition.facade.RegionEdgeStyleDefine;
import com.jiuqi.nr.definition.facade.RegionTabSettingDefine;
import com.jiuqi.nr.definition.facade.ReportTemplateDefine;
import com.jiuqi.nr.definition.facade.RowNumberSetting;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.internal.controller.event.FormSchemeUpdateEvent;
import com.jiuqi.nr.definition.internal.impl.AnalysisFormParamDefineImpl;
import com.jiuqi.nr.definition.internal.impl.AnalysisSchemeParamDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignDataLinkDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignDataLinkMappingDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignDataRegionDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignDimensionFilterImpl;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignFormGroupDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink;
import com.jiuqi.nr.definition.internal.impl.DesignFormSchemeDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignRegionSettingDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignSchemePeriodLink;
import com.jiuqi.nr.definition.internal.impl.DesignTaskDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignTaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.DesignTaskGroupDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignTaskGroupLink;
import com.jiuqi.nr.definition.internal.impl.DesignTaskLinkDefineImpl;
import com.jiuqi.nr.definition.internal.impl.RegionTabSettingData;
import com.jiuqi.nr.definition.internal.provider.ConditionalStyleProvider;
import com.jiuqi.nr.definition.internal.service.DesignBigDataService;
import com.jiuqi.nr.definition.internal.service.DesignDataLinkDefineService;
import com.jiuqi.nr.definition.internal.service.DesignDataLinkMappingDefineService;
import com.jiuqi.nr.definition.internal.service.DesignDataRegionDefineService;
import com.jiuqi.nr.definition.internal.service.DesignDimensionFilterService;
import com.jiuqi.nr.definition.internal.service.DesignFormDefineService;
import com.jiuqi.nr.definition.internal.service.DesignFormFoldingService;
import com.jiuqi.nr.definition.internal.service.DesignFormGroupDefineService;
import com.jiuqi.nr.definition.internal.service.DesignFormGroupLinkService;
import com.jiuqi.nr.definition.internal.service.DesignFormSchemeDefineService;
import com.jiuqi.nr.definition.internal.service.DesignRegionSettingDefineService;
import com.jiuqi.nr.definition.internal.service.DesignSchemeEffectivePeriodDefineService;
import com.jiuqi.nr.definition.internal.service.DesignTaskDefineService;
import com.jiuqi.nr.definition.internal.service.DesignTaskGroupDefineService;
import com.jiuqi.nr.definition.internal.service.DesignTaskGroupLinkDefineService;
import com.jiuqi.nr.definition.internal.service.DesignTaskLinkDefineService;
import com.jiuqi.nr.definition.internal.service.TaskOrgLinkService;
import com.jiuqi.nr.definition.internal.service.formula.DesignFormulaConditionService;
import com.jiuqi.nr.definition.paramcheck.IDesignParamCheckService;
import com.jiuqi.nr.definition.util.AttachmentObj;
import com.jiuqi.nr.definition.util.ParamPackage;
import com.jiuqi.nr.definition.util.RecordCard;
import com.jiuqi.nr.definition.util.SerializeListImpl;
import com.jiuqi.nr.definition.util.SerializeUtils;
import com.jiuqi.nr.period.util.JacksonUtils;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.util.OrderGenerator;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Component
public class DesignTimeViewController
implements IDesignTimeViewController {
    private static final Logger logger = LoggerFactory.getLogger(DesignTimeViewController.class);
    @Autowired
    private DesignTaskDefineService designTaskDefineService;
    @Autowired
    private DesignBigDataService designBigDataService;
    @Autowired
    private DesignTaskGroupDefineService designTaskGroupDefineService;
    @Autowired
    private DesignTaskGroupLinkDefineService designTaskGroupLinkDefineService;
    @Autowired
    private DesignFormSchemeDefineService designFormSchemeDefineService;
    @Autowired
    private IDesignTimeFormulaController iDesignTimeFormulaController;
    @Autowired
    private IDesignTimePrintController iDesignTimePrintController;
    @Autowired
    private IDesignTimeReportController iDesignTimeReportController;
    @Autowired
    private DesignFormGroupDefineService designFormGroupDefineService;
    @Autowired
    private DesignFormDefineService designFormDefineService;
    @Autowired
    private DesignDataRegionDefineService designDataRegionDefineService;
    @Autowired
    private DesignDataLinkDefineService designDataLinkDefineService;
    @Autowired
    private DesignDataLinkMappingDefineService designDataLinkMappingService;
    @Autowired
    private ConditionalStyleProvider conditionalStyleProvider;
    @Autowired
    private DesignFormGroupLinkService designFormGroupLinkService;
    @Autowired
    private DesignRegionSettingDefineService designRegionSettingDefineService;
    @Autowired
    private DesignTaskLinkDefineService designTaskLinkDefineService;
    @Autowired
    private DesignDimensionFilterService designDimensionFilterService;
    @Autowired
    private DesignDataLinkMappingDefineService designDataLinkMappingDefineService;
    @Autowired
    private DesignSchemeEffectivePeriodDefineService designSchemeEffectivePeriodDefineService;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private NpDefinitionObserverable observerable;
    @Autowired
    private IDesignParamCheckService iDesignParamCheckService;
    @Autowired
    private DesignFormFoldingService designFormFoldingService;
    @Autowired
    private TaskOrgLinkService taskOrgLinkService;
    @Autowired
    private DesignFormulaConditionService designFormulaConditionService;

    @Override
    public DesignTaskDefine initTask() {
        DesignTaskDefineImpl designTaskDefine = new DesignTaskDefineImpl();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        designTaskDefine.setKey(UUIDUtils.getKey());
        designTaskDefine.setTaskCode(OrderGenerator.newOrder());
        designTaskDefine.setCreateUserName(NpContextHolder.getContext().getUserName());
        designTaskDefine.setCreateTime(sdf.format(new Date()));
        designTaskDefine.setOrder(OrderGenerator.newOrder());
        designTaskDefine.setUpdateTime(new Date());
        return designTaskDefine;
    }

    @Override
    @Transactional
    public void insertTask(DesignTaskDefine taskDefine) {
        try {
            DesignTaskDefine designTaskDefine = ParamPackage.convertTask(taskDefine);
            this.iDesignParamCheckService.checkTaskTitle(designTaskDefine);
            this.designTaskDefineService.insertTaskDefine(designTaskDefine);
            this.updateTaskFlow(designTaskDefine);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.TASK_INSERT.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.TASK_INSERT, (Throwable)e);
        }
    }

    private void updateTaskFlow(DesignTaskDefine designTaskDefine) throws Exception {
        TaskFlowsDefine taskFlowsDefine = designTaskDefine.getFlowsSetting();
        byte[] data = null;
        if (taskFlowsDefine == null) {
            DesignTaskFlowsDefine designTaskFlowsDefine = new DesignTaskFlowsDefine();
            designTaskFlowsDefine.setDesignTableDefines(designTaskDefine.getDw() + ";" + designTaskDefine.getDateTime());
            data = DesignTaskFlowsDefine.designTaskFlowsDefineToBytes(designTaskFlowsDefine);
        } else {
            data = DesignTaskFlowsDefine.designTaskFlowsDefineToBytes(taskFlowsDefine);
        }
        this.designBigDataService.updateBigDataDefine(designTaskDefine.getKey(), "FLOWSETTING", data);
    }

    @Override
    @Transactional
    public void updateTask(DesignTaskDefine taskDefine) {
        try {
            DesignTaskDefine designTaskDefine = ParamPackage.convertTask(taskDefine);
            this.iDesignParamCheckService.checkTaskTitle(designTaskDefine);
            DesignTaskDefine oldTaskDefine = this.getTask(designTaskDefine.getKey());
            TaskFlowsDefine oldTaskFlowsDefine = oldTaskDefine.getFlowsSetting();
            TaskFlowsDefine designTaskFlowsDefine = designTaskDefine.getFlowsSetting();
            this.applicationContext.publishEvent(new FormSchemeUpdateEvent(designTaskDefine, oldTaskDefine, designTaskFlowsDefine, oldTaskFlowsDefine));
            this.designTaskDefineService.updateTaskDefine(designTaskDefine);
            this.updateTaskFlow(designTaskDefine);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.TASK_UPDATE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.TASK_UPDATE, (Throwable)e);
        }
    }

    @Override
    @Transactional
    public void deleteTask(String[] keys) {
        try {
            for (String key : keys) {
                this.designTaskGroupLinkDefineService.deleteLinkByTask(key);
                List<DesignFormSchemeDefine> formSchemeDefines = this.listFormSchemeByTask(key);
                String[] formSchemeKeys = (String[])formSchemeDefines.stream().map(IBaseMetaItem::getKey).toArray(String[]::new);
                this.deleteFormScheme(formSchemeKeys);
            }
            List<DesignTaskDefine> taskDefines = this.designTaskDefineService.queryTaskDefines(keys);
            for (String key : keys) {
                this.designFormulaConditionService.deleteFormulaConditionByTask(key);
            }
            this.designTaskDefineService.delete(keys);
            this.deleteBigData(keys, "FLOWSETTING");
            for (DesignTaskDefine taskDefine : taskDefines) {
                if (this.observerable == null) continue;
                this.observerable.notify(MessageType.NRDROPTASK, new Object[]{taskDefine});
            }
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.TASK_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.TASK_DELETE, (Throwable)e);
        }
    }

    private void deleteBigData(String[] keys, String code) throws Exception {
        for (String key : keys) {
            this.designBigDataService.deleteBigDataDefine(key, code);
        }
    }

    @Override
    public DesignTaskDefine getTask(String taskKey) {
        try {
            return ParamPackage.packageTask(this.designTaskDefineService.queryTaskDefine(taskKey), this.designBigDataService);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.TASK_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.TASK_QUERY, (Throwable)e);
        }
    }

    @Override
    public DesignTaskDefine getTaskByCode(String taskCode) {
        try {
            return ParamPackage.packageTask(this.designTaskDefineService.queryTaskDefineByCode(taskCode), this.designBigDataService);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.TASK_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.TASK_QUERY, (Throwable)e);
        }
    }

    @Override
    public DesignTaskDefine getTaskByFilePrefix(String filePrefix) {
        try {
            return ParamPackage.packageTask(this.designTaskDefineService.queryTaskDefineByfilePrefix(filePrefix), this.designBigDataService);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.TASK_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.TASK_QUERY, (Throwable)e);
        }
    }

    @Override
    public DesignTaskDefine getTaskByTitle(String taskTitle) {
        try {
            return ParamPackage.packageTask(this.designTaskDefineService.queryTaskDefineByTaskTitle(taskTitle), this.designBigDataService);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.TASK_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.TASK_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<DesignTaskDefine> listAllTask() {
        try {
            List<DesignTaskDefine> taskDefines = this.designTaskDefineService.queryAllTaskDefine();
            taskDefines = taskDefines.stream().sorted(Comparator.comparing(IBaseMetaItem::getOrder).reversed()).collect(Collectors.toList());
            return ParamPackage.packageTask(taskDefines, this.designBigDataService);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.TASK_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.TASK_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<DesignTaskDefine> listTaskByType(TaskType taskType) {
        try {
            List<DesignTaskDefine> taskDefines = this.designTaskDefineService.queryAllTaskDefinesByType(taskType);
            taskDefines = taskDefines.stream().sorted(Comparator.comparing(IBaseMetaItem::getOrder).reversed()).collect(Collectors.toList());
            return ParamPackage.packageTask(taskDefines, this.designBigDataService);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.TASK_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.TASK_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<DesignTaskDefine> listTaskByTaskGroup(String taskGroup) {
        try {
            return ParamPackage.packageTask(this.designTaskDefineService.getAllTasksInGroup(taskGroup), this.designBigDataService);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.TASK_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.TASK_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<DesignTaskDefine> listTaskByDataScheme(String dataScheme) {
        try {
            return ParamPackage.packageTask(this.designTaskDefineService.listTaskByDataScheme(dataScheme), this.designBigDataService);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.TASK_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.TASK_QUERY, (Throwable)e);
        }
    }

    @Override
    public void insertTaskFlows(String taskKey, DesignTaskFlowsDefine designTaskFlowsDefine) {
        this.updateTaskFlows(taskKey, designTaskFlowsDefine);
    }

    @Override
    public void updateTaskFlows(String taskKey, DesignTaskFlowsDefine designTaskFlowsDefine) {
        try {
            byte[] bytes = DesignTaskFlowsDefine.designTaskFlowsDefineToBytes(designTaskFlowsDefine);
            this.designBigDataService.updateBigDataDefine(taskKey, "FLOWSETTING", bytes);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.TASK_FLOWS_UPDATE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.TASK_FLOWS_UPDATE, (Throwable)e);
        }
    }

    @Override
    public void deleteTaskFlows(String taskKey) {
        try {
            this.designBigDataService.deleteBigDataDefine(taskKey, "FLOWSETTING");
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.TASK_FLOWS_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.TASK_FLOWS_DELETE, (Throwable)e);
        }
    }

    @Override
    public DesignTaskGroupDefine initTaskGroup() {
        DesignTaskGroupDefineImpl designTaskGroupDefine = new DesignTaskGroupDefineImpl();
        designTaskGroupDefine.setKey(UUIDUtils.getKey());
        designTaskGroupDefine.setCode(OrderGenerator.newOrder());
        designTaskGroupDefine.setOrder(OrderGenerator.newOrder());
        designTaskGroupDefine.setUpdateTime(new Date());
        return designTaskGroupDefine;
    }

    @Override
    public void insertTaskGroup(DesignTaskGroupDefine designTaskGroupDefine) {
        try {
            this.iDesignParamCheckService.checkTaskGroupTitle(designTaskGroupDefine);
            this.designTaskGroupDefineService.insertTaskGroupDefine(designTaskGroupDefine);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.TASK_GROUP_INSERT.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.TASK_GROUP_INSERT, (Throwable)e);
        }
    }

    @Override
    public void updateTaskGroup(DesignTaskGroupDefine designTaskGroupDefine) {
        try {
            this.iDesignParamCheckService.checkTaskGroupTitle(designTaskGroupDefine);
            this.designTaskGroupDefineService.updateTaskGroupDefine(designTaskGroupDefine);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.TASK_GROUP_UPDATE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.TASK_GROUP_UPDATE, (Throwable)e);
        }
    }

    @Override
    @Transactional
    public void deleteTaskGroup(String[] keys) {
        try {
            for (String key : keys) {
                this.designTaskGroupLinkDefineService.deleteLinkByGroup(key);
            }
            this.designTaskGroupDefineService.delete(keys);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.TASK_GROUP_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.TASK_GROUP_DELETE, (Throwable)e);
        }
    }

    @Override
    public DesignTaskGroupDefine getTaskGroup(String taskGroupKey) {
        try {
            return this.designTaskGroupDefineService.queryTaskGroupDefine(taskGroupKey);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.TASK_GROUP_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.TASK_GROUP_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<DesignTaskGroupDefine> listAllTaskGroup() {
        try {
            return this.designTaskGroupDefineService.queryAllTaskGroupDefine();
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.TASK_GROUP_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.TASK_GROUP_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<DesignTaskGroupDefine> listTaskGroupByTask(String taskKey) {
        try {
            return this.designTaskGroupDefineService.getGroupByTask(taskKey);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.TASK_GROUP_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.TASK_GROUP_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<DesignTaskGroupDefine> listTaskGroupByParentGroup(String taskGroup) {
        try {
            return this.designTaskGroupDefineService.queryTaskGroupDefineByGroupId(taskGroup);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.TASK_GROUP_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.TASK_GROUP_QUERY, (Throwable)e);
        }
    }

    @Override
    public DesignTaskGroupLink initTaskGroupLink() {
        DesignTaskGroupLink designTaskGroupLink = new DesignTaskGroupLink();
        designTaskGroupLink.setOrder(OrderGenerator.newOrder());
        designTaskGroupLink.setUpdateTime(new Date());
        return designTaskGroupLink;
    }

    @Override
    public void insertTaskGroupLink(DesignTaskGroupLink[] designTaskGroupLinks) {
        try {
            this.designTaskGroupLinkDefineService.insertLinks(designTaskGroupLinks);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.TASK_GROUP_LINK_INSERT.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.TASK_GROUP_LINK_INSERT, (Throwable)e);
        }
    }

    @Override
    public void updateTaskGroupLink(DesignTaskGroupLink[] designTaskGroupLinks) {
        try {
            this.designTaskGroupLinkDefineService.updateTaskGroupLink(designTaskGroupLinks);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.TASK_GROUP_LINK_UPDATE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.TASK_GROUP_LINK_UPDATE, (Throwable)e);
        }
    }

    @Override
    public void deleteTaskGroupLink(DesignTaskGroupLink[] designTaskGroupLinks) {
        try {
            this.designTaskGroupLinkDefineService.deleteTaskGroupLink(designTaskGroupLinks);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.TASK_GROUP_LINK_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.TASK_GROUP_LINK_DELETE, (Throwable)e);
        }
    }

    @Override
    public DesignFormSchemeDefine initFormScheme() {
        DesignFormSchemeDefineImpl designFormSchemeDefine = new DesignFormSchemeDefineImpl();
        designFormSchemeDefine.setKey(UUIDUtils.getKey());
        designFormSchemeDefine.setFormSchemeCode(OrderGenerator.newOrder());
        designFormSchemeDefine.setOrder(OrderGenerator.newOrder());
        designFormSchemeDefine.setUpdateTime(new Date());
        return designFormSchemeDefine;
    }

    @Override
    public void insertFormScheme(DesignFormSchemeDefine designFormSchemeDefine) {
        try {
            this.iDesignParamCheckService.checkFormScheme(designFormSchemeDefine);
            this.designFormSchemeDefineService.insertFormSchemeDefine(designFormSchemeDefine);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMSCHEME_INSERT.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMSCHEME_INSERT, (Throwable)e);
        }
    }

    @Override
    public void updateFormScheme(DesignFormSchemeDefine designFormSchemeDefine) {
        try {
            this.iDesignParamCheckService.checkFormScheme(designFormSchemeDefine);
            DesignFormSchemeDefine oldFormScheme = this.getFormScheme(designFormSchemeDefine.getKey());
            this.designFormSchemeDefineService.updateFormSchemeDefine(designFormSchemeDefine);
            byte[] flowsData = this.designBigDataService.getBigData(designFormSchemeDefine.getTaskKey(), "FLOWSETTING");
            DesignTaskFlowsDefine designTaskFlowsDefine = DesignTaskFlowsDefine.bytesToTaskFlowsData(flowsData);
            this.applicationContext.publishEvent(new FormSchemeUpdateEvent(designFormSchemeDefine, oldFormScheme, designTaskFlowsDefine, designTaskFlowsDefine));
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMSCHEME_UPDATE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMSCHEME_UPDATE, (Throwable)e);
        }
    }

    @Override
    @Transactional
    public void deleteFormScheme(String[] keys) {
        try {
            for (String formSchemeKey : keys) {
                DesignFormSchemeDefine schemeDefine = this.getFormScheme(formSchemeKey);
                this.designFormSchemeDefineService.delete(formSchemeKey);
                this.designFormSchemeDefineService.deleteByScheme(formSchemeKey);
                if (this.observerable != null) {
                    this.observerable.notify(MessageType.NRDROPSCHEME, new Object[]{schemeDefine});
                }
                List<DesignFormGroupDefine> formGroupDefines = this.listFormGroupByFormScheme(formSchemeKey);
                String[] formGroupKeys = (String[])formGroupDefines.stream().map(IBaseMetaItem::getKey).toArray(String[]::new);
                this.deleteFormGroup(formGroupKeys);
                String[] formulaSchemeKeys = (String[])this.iDesignTimeFormulaController.listFormulaSchemeByFormScheme(formSchemeKey).stream().map(IBaseMetaItem::getKey).toArray(String[]::new);
                this.iDesignTimeFormulaController.deleteFormulaScheme(formulaSchemeKeys);
                String[] printSchemeKeys = (String[])this.iDesignTimePrintController.listPrintTemplateSchemeByFormScheme(formSchemeKey).stream().map(IBaseMetaItem::getKey).toArray(String[]::new);
                this.iDesignTimePrintController.deletePrintTemplateScheme(printSchemeKeys);
                String[] reportTempKeys = (String[])this.iDesignTimeReportController.listReportTemplateByFormScheme(formSchemeKey).stream().map(ReportTemplateDefine::getKey).toArray(String[]::new);
                this.iDesignTimeReportController.deleteReportTemplate(reportTempKeys);
                this.iDesignTimeFormulaController.deleteFormulaVariableByFormScheme(formSchemeKey);
            }
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMSCHEME_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMSCHEME_DELETE, (Throwable)e);
        }
    }

    @Override
    public List<DesignFormSchemeDefine> listFormScheme(Collection<String> formSchemeKeys) {
        if (CollectionUtils.isEmpty(formSchemeKeys)) {
            return Collections.emptyList();
        }
        try {
            return this.designFormSchemeDefineService.queryFormSchemeDefines(formSchemeKeys.toArray(new String[0]));
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMSCHEME_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMSCHEME_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<DesignFormSchemeDefine> listFormSchemeByTask(String taskKey) {
        try {
            return this.designFormSchemeDefineService.queryFormSchemeDefineByTaskKey(taskKey);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMSCHEME_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMSCHEME_QUERY, (Throwable)e);
        }
    }

    @Override
    public DesignFormSchemeDefine getFormScheme(String formSchemeKey) {
        try {
            return this.designFormSchemeDefineService.queryFormSchemeDefine(formSchemeKey);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMSCHEME_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMSCHEME_QUERY, (Throwable)e);
        }
    }

    @Override
    public DesignFormSchemeDefine getFormSchemeByFilePrefix(String filePrefix) {
        try {
            return this.designFormSchemeDefineService.queryFormSchemeDefineByfilePrefix(filePrefix);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMSCHEME_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMSCHEME_QUERY, (Throwable)e);
        }
    }

    @Override
    public DesignFormSchemeDefine getFormSchemeByTaskPrefix(String taskPrefix) {
        try {
            return this.designFormSchemeDefineService.queryFormSchemeDefineByTaskPrefix(taskPrefix);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMSCHEME_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMSCHEME_QUERY, (Throwable)e);
        }
    }

    @Override
    public DesignFormSchemeDefine getFormSchemeByCode(String formSchemeCode) {
        try {
            return this.designFormSchemeDefineService.queryFormSchemeDefineByCode(formSchemeCode);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMSCHEME_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMSCHEME_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<DesignFormSchemeDefine> listAllFormScheme() {
        try {
            return this.designFormSchemeDefineService.queryAllFormSchemeDefine();
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMSCHEME_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMSCHEME_QUERY, (Throwable)e);
        }
    }

    @Override
    public DesignAnalysisSchemeParamDefine getAnalysisSchemeParamDefine(String formSchemeKey) {
        try {
            byte[] data = this.designBigDataService.getBigData(formSchemeKey, "ANALYSIS_PARAM");
            if (null != data && data.length > 0) {
                return SerializeUtils.jsonDeserialize(AnalysisSchemeParamDefineImpl.getDefaultModule(), data, AnalysisSchemeParamDefineImpl.class);
            }
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.ANALYSIS_QUERY.getMessage(), e);
        }
        return null;
    }

    @Override
    public void updateAnalysisSchemeParamDefine(String formSchemeKey, DesignAnalysisSchemeParamDefine anaParam) {
        try {
            byte[] data = SerializeUtils.jsonSerializeToByte(anaParam);
            this.designBigDataService.updateBigDataDefine(formSchemeKey, "ANALYSIS_PARAM", data);
        }
        catch (Exception e) {
            throw new RuntimeException(NrDefinitionErrorEnum2.ANALYSIS_UPDATE.getMessage(), e);
        }
    }

    @Override
    public void deleteAnalysisSchemeParamDefine(String formSchemeKey) {
        try {
            this.designBigDataService.deleteBigDataDefine(formSchemeKey, "ANALYSIS_PARAM");
        }
        catch (Exception e) {
            throw new RuntimeException(NrDefinitionErrorEnum2.ANALYSIS_DELETE.getMessage(), e);
        }
    }

    @Override
    public boolean enableAnalysisScheme(String formSchemeKey) {
        try {
            return null != this.designBigDataService.getBigData(formSchemeKey, "ANALYSIS_PARAM");
        }
        catch (Exception e) {
            throw new RuntimeException(NrDefinitionErrorEnum2.ANALYSIS_QUERY.getMessage(), e);
        }
    }

    @Override
    public DesignFormGroupDefine initFormGroup() {
        DesignFormGroupDefineImpl designFormGroupDefine = new DesignFormGroupDefineImpl();
        designFormGroupDefine.setKey(UUIDUtils.getKey());
        designFormGroupDefine.setCode(OrderGenerator.newOrder());
        designFormGroupDefine.setOrder(OrderGenerator.newOrder());
        designFormGroupDefine.setUpdateTime(new Date());
        return designFormGroupDefine;
    }

    @Override
    public void insertFormGroup(DesignFormGroupDefine designFormGroupDefine) {
        try {
            this.iDesignParamCheckService.checkFormGroup(designFormGroupDefine);
            this.designFormGroupDefineService.insertFormGroupDefine(designFormGroupDefine);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORM_GROUP_INSERT.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_GROUP_INSERT, (Throwable)e);
        }
    }

    @Override
    public void insertFormGroups(DesignFormGroupDefine[] designFormGroupDefine) {
        try {
            this.designFormGroupDefineService.insertFormGroupDefines(designFormGroupDefine);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORM_GROUP_INSERT.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_GROUP_INSERT, (Throwable)e);
        }
    }

    @Override
    public void updateFormGroup(DesignFormGroupDefine designFormGroupDefine) {
        try {
            this.iDesignParamCheckService.checkFormGroup(designFormGroupDefine);
            this.designFormGroupDefineService.updateFormGroupDefine(designFormGroupDefine);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORM_GROUP_UPDATE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_GROUP_UPDATE, (Throwable)e);
        }
    }

    @Override
    @Transactional
    public void deleteFormGroup(String[] keys) {
        try {
            for (String formGroupKey : keys) {
                String[] formKeys = (String[])this.listFormByGroup(formGroupKey).stream().map(IBaseMetaItem::getKey).toArray(String[]::new);
                this.deleteForm(formKeys);
                this.designFormGroupLinkService.deleteLinkByGroup(formGroupKey);
            }
            this.designFormGroupDefineService.delete(keys);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORM_GROUP_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_GROUP_DELETE, (Throwable)e);
        }
    }

    @Override
    public DesignFormGroupDefine getFormGroup(String formGroupKey) {
        try {
            return this.designFormGroupDefineService.queryFormGroupDefine(formGroupKey);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORM_GROUP_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_GROUP_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<DesignFormGroupDefine> listFormGroupByFormSchemeAndTitle(String formScheme, String groupTitle) {
        try {
            return this.designFormGroupDefineService.queryFormGroupDefinesByScheme(formScheme, groupTitle);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORM_GROUP_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_GROUP_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<DesignFormGroupDefine> listFormGroupByFormScheme(String formScheme) {
        try {
            return this.designFormGroupDefineService.queryFormGroupDefinesByScheme(formScheme);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORM_GROUP_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_GROUP_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<DesignFormGroupDefine> listFormGroupByForm(String formKey) {
        try {
            return this.designFormGroupDefineService.getAllGroupsFromForm(formKey);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORM_GROUP_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_GROUP_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<DesignFormGroupDefine> listAllFormGroupDefine() {
        try {
            return this.designFormGroupDefineService.queryAllFormGroupDefine();
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORM_GROUP_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_GROUP_QUERY, (Throwable)e);
        }
    }

    @Override
    public DesignFormDefine initForm() {
        DesignFormDefineImpl designFormDefine = new DesignFormDefineImpl();
        designFormDefine.setKey(UUIDUtils.getKey());
        designFormDefine.setFormCode(OrderGenerator.newOrder());
        designFormDefine.setOrder(OrderGenerator.newOrder());
        designFormDefine.setUpdateTime(new Date());
        designFormDefine.setUpdateUser(NpContextHolder.getContext().getUserName());
        return designFormDefine;
    }

    @Override
    public void insertForm(DesignFormDefine formDefine) {
        try {
            DesignFormDefine designFormDefine = ParamPackage.convertForm(formDefine);
            this.iDesignParamCheckService.checkFormTitleAndCode(designFormDefine);
            this.designFormDefineService.insertFormDefine(designFormDefine);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORM_INSERT.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_INSERT, (Throwable)e);
        }
    }

    @Override
    public void insertForms(DesignFormDefine[] designFormDefines) {
        try {
            this.designFormDefineService.insertFormDefines(designFormDefines);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORM_INSERT.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_INSERT, (Throwable)e);
        }
    }

    @Override
    public void updateForm(DesignFormDefine formDefine) {
        try {
            DesignFormDefine designFormDefine = ParamPackage.convertForm(formDefine);
            this.iDesignParamCheckService.checkFormTitleAndCode(designFormDefine);
            this.designFormDefineService.updateFormDefine(designFormDefine);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORM_UPDATE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_UPDATE, (Throwable)e);
        }
    }

    @Override
    public void updateFormTime(String formKey) {
        this.designFormDefineService.updateFormTime(formKey);
    }

    @Override
    public void batchUpdateFormTime(String ... formKeys) {
        this.designFormDefineService.batchUpdateFormTime(formKeys);
    }

    @Override
    @Transactional
    public void deleteForm(String[] keys) {
        try {
            for (String formKey : keys) {
                this.designDataLinkMappingService.deleteDataLinkMappingByFormKey(formKey);
                this.conditionalStyleProvider.deleteCSInForm(formKey);
                this.designFormFoldingService.deleteByFormKey(formKey);
                this.designFormGroupLinkService.deleteLinkByForm(formKey);
                String[] regionKeys = (String[])this.listDataRegionByForm(formKey).stream().map(IBaseMetaItem::getKey).toArray(String[]::new);
                this.deleteDataRegion(regionKeys);
                this.iDesignTimeFormulaController.deleteFormulaByForm(formKey);
                this.iDesignTimePrintController.deletePrintTemplateByForm(formKey);
                this.designBigDataService.deleteBigDataDefine(formKey, "FORM_DATA");
                this.designBigDataService.deleteBigDataDefine(formKey, "BIG_SURVEY_DATA");
                this.designBigDataService.deleteBigDataDefine(formKey, "BIG_SCRIPT_EDITOR");
                this.designBigDataService.deleteBigDataDefine(formKey, "FILLING_GUIDE");
            }
            this.designFormDefineService.delete(keys);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORM_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_DELETE, (Throwable)e);
        }
    }

    @Override
    @Transactional
    public void deleteFormDefine(String key) {
        try {
            this.designBigDataService.deleteBigDataDefine(key, "FORM_DATA");
            this.designBigDataService.deleteBigDataDefine(key, "BIG_SURVEY_DATA");
            this.designBigDataService.deleteBigDataDefine(key, "BIG_SCRIPT_EDITOR");
            this.designBigDataService.deleteBigDataDefine(key, "FILLING_GUIDE");
            this.designFormDefineService.delete(key);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORM_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_DELETE, (Throwable)e);
        }
    }

    @Override
    public List<DesignFormDefine> listFormByGroup(String formGroupKey) {
        ArrayList<DesignFormDefine> designFormDefines = new ArrayList<DesignFormDefine>();
        try {
            List<DesignFormDefine> queryForms = this.designFormDefineService.queryFormDefineByGroupId(formGroupKey, false);
            Map<Object, Object> formMap = !CollectionUtils.isEmpty(queryForms) ? queryForms.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, e -> e, (e1, e2) -> e2)) : new HashMap(queryForms.size());
            List<DesignFormGroupLink> formGroupLinksByGroupId = this.designFormGroupLinkService.getFormGroupLinksByGroupId(formGroupKey);
            formGroupLinksByGroupId.stream().sorted(Comparator.comparing(DesignFormGroupLink::getFormOrder)).forEach(e -> {
                DesignFormDefine formDefine = (DesignFormDefine)formMap.get(e.getFormKey());
                if (formDefine != null) {
                    designFormDefines.add(formDefine);
                }
            });
            return ParamPackage.packageForm(designFormDefines, this.designBigDataService);
        }
        catch (Exception e3) {
            logger.error(NrDefinitionErrorEnum2.FORM_QUERY.getMessage(), e3);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_QUERY, (Throwable)e3);
        }
    }

    @Override
    public DesignFormDefine getForm(String formKey) {
        try {
            return ParamPackage.packageForm(this.designFormDefineService.queryFormDefineContainsFormData(formKey, 0), this.designBigDataService);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORM_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_QUERY, (Throwable)e);
        }
    }

    @Override
    public DesignFormDefine getFormByFormSchemeAndCode(String formScheme, String formCode) {
        try {
            return ParamPackage.packageForm(this.designFormDefineService.getFormDefineByCodeInScheme(formScheme, formCode), this.designBigDataService);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORM_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<DesignFormDefine> listForm(Collection<String> formKeys) {
        if (CollectionUtils.isEmpty(formKeys)) {
            return Collections.emptyList();
        }
        try {
            return ParamPackage.packageForm(this.designFormDefineService.getFormDefines(new ArrayList<String>(formKeys)), this.designBigDataService);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORM_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<DesignFormDefine> listFormByFormSchemeAndType(String formScheme, FormType formType) {
        try {
            return ParamPackage.packageForm(this.designFormDefineService.queryFormsByTypeInScheme(formScheme, formType), this.designBigDataService);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORM_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<DesignFormDefine> listFormByFormScheme(String formScheme) {
        try {
            return ParamPackage.packageForm(this.designFormDefineService.listFormByFormScheme(formScheme), this.designBigDataService);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORM_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<DesignFormDefine> listAllFormDefine() {
        try {
            return ParamPackage.packageForm(this.designFormDefineService.queryAllFormDefine(), this.designBigDataService);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORM_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_QUERY, (Throwable)e);
        }
    }

    @Override
    public void insertFormStyle(String formKey, Grid2Data grid2Data) {
        try {
            this.designBigDataService.updateBigDataDefine(formKey, "FORM_DATA", Grid2Data.gridToBytes((Grid2Data)grid2Data));
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORM_FORMSTYLE_INSERT.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_FORMSTYLE_INSERT, (Throwable)e);
        }
    }

    @Override
    public void updateFormStyle(String formKey, Grid2Data grid2Data) {
        try {
            this.designBigDataService.updateBigDataDefine(formKey, "FORM_DATA", Grid2Data.gridToBytes((Grid2Data)grid2Data));
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORM_FORMSTYLE_UPDATE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_FORMSTYLE_UPDATE, (Throwable)e);
        }
    }

    @Override
    public Grid2Data getFormStyle(String formKey) {
        try {
            byte[] bigData = this.designBigDataService.getBigData(formKey, "FORM_DATA");
            return Grid2Data.bytesToGrid((byte[])bigData);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORM_FORMSTYLE_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_FORMSTYLE_QUERY, (Throwable)e);
        }
    }

    @Override
    public Grid2Data getFormStyle(String formKey, int language) {
        try {
            byte[] bigData = this.designBigDataService.queryBigDataDefine(formKey, "FORM_DATA", language);
            return Grid2Data.bytesToGrid((byte[])bigData);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORM_FORMSTYLE_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_FORMSTYLE_QUERY, (Throwable)e);
        }
    }

    @Override
    public void deleteFormStyle(String formKey) {
        try {
            this.designBigDataService.deleteBigDataDefine(formKey, "FORM_DATA");
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORM_FORMSTYLE_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_FORMSTYLE_DELETE, (Throwable)e);
        }
    }

    @Override
    public void insertSurveyData(String formKey, byte[] surveyData) {
        try {
            this.designBigDataService.updateBigDataDefine(formKey, "BIG_SURVEY_DATA", surveyData);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORM_SURVEY_INSERT.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_SURVEY_INSERT, (Throwable)e);
        }
    }

    @Override
    public void updateSurveyData(String formKey, byte[] surveyData) {
        try {
            this.designBigDataService.updateBigDataDefine(formKey, "BIG_SURVEY_DATA", surveyData);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORM_SURVEY_UPDATE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_SURVEY_UPDATE, (Throwable)e);
        }
    }

    @Override
    public void deleteSurveyData(String formKey) {
        try {
            this.designBigDataService.deleteBigDataDefine(formKey, "BIG_SURVEY_DATA");
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORM_SURVEY_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_SURVEY_DELETE, (Throwable)e);
        }
    }

    @Override
    public byte[] getSurveyData(String formKey) {
        try {
            return this.designBigDataService.getBigData(formKey, "BIG_SURVEY_DATA");
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORM_SURVEY_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_SURVEY_DELETE, (Throwable)e);
        }
    }

    @Override
    public DesignAnalysisFormParamDefine getAnalysisData(String formKey) {
        try {
            byte[] data = this.designBigDataService.getBigData(formKey, "ANALYSIS_FORM_PARAM");
            if (null != data && data.length > 0) {
                return SerializeUtils.jsonDeserialize(AnalysisFormParamDefineImpl.getDefaultModule(), data, AnalysisFormParamDefineImpl.class);
            }
            return null;
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORM_ANALYSIS_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_ANALYSIS_QUERY, (Throwable)e);
        }
    }

    @Override
    public void updataAnalysisData(String formKey, DesignAnalysisFormParamDefine analysisData) {
        try {
            byte[] data = SerializeUtils.jsonSerializeToByte(analysisData);
            this.designBigDataService.updateBigDataDefine(formKey, "ANALYSIS_FORM_PARAM", data);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORM_ANALYSIS_UPDATE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_ANALYSIS_UPDATE, (Throwable)e);
        }
    }

    @Override
    public void deleteAnalysisData(String formKey) {
        try {
            this.designBigDataService.deleteBigDataDefine(formKey, "ANALYSIS_FORM_PARAM");
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORM_ANALYSIS_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_ANALYSIS_DELETE, (Throwable)e);
        }
    }

    @Override
    public void insertAnalysisData(String formKey, DesignAnalysisFormParamDefine analysisData) {
        try {
            byte[] data = SerializeUtils.jsonSerializeToByte(analysisData);
            this.designBigDataService.updateBigDataDefine(formKey, "ANALYSIS_FORM_PARAM", data);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORM_ANALYSIS_INSERT.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_ANALYSIS_INSERT, (Throwable)e);
        }
    }

    @Override
    public DesignFormGroupLink initFormGroupLink() {
        DesignFormGroupLink link = new DesignFormGroupLink();
        link.setFormOrder(OrderGenerator.newOrder());
        link.setUpdateTime(new Date());
        return link;
    }

    @Override
    @Transactional
    public void insertFormGroupLink(DesignFormGroupLink[] designFormGroupLinks) {
        try {
            this.designFormGroupLinkService.insertLinks(designFormGroupLinks);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORM_GROUP_LINK_INSERT.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_GROUP_LINK_INSERT, (Throwable)e);
        }
    }

    @Override
    @Transactional
    public void updateFormGroupLink(DesignFormGroupLink[] designFormGroupLinks) {
        try {
            this.designFormGroupLinkService.updateLinks(designFormGroupLinks);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORM_GROUP_LINK_UPDATE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_GROUP_LINK_UPDATE, (Throwable)e);
        }
    }

    @Override
    @Transactional
    public void deleteFormGroupLink(DesignFormGroupLink[] designFormGroupLinks) {
        try {
            for (DesignFormGroupLink designFormGroupLink : designFormGroupLinks) {
                this.designFormGroupLinkService.deleteLink(designFormGroupLink);
            }
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORM_GROUP_LINK_UPDATE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_GROUP_LINK_UPDATE, (Throwable)e);
        }
    }

    @Override
    public List<DesignFormGroupLink> listFormGroupLinkByForm(String formKey) {
        try {
            return this.designFormGroupLinkService.getFormGroupLinksByFormId(formKey);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORM_GROUP_LINK_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_GROUP_LINK_QUERY, (Throwable)e);
        }
    }

    @Override
    public DesignDataRegionDefine initDataRegion() {
        DesignDataRegionDefineImpl designDataRegionDefine = new DesignDataRegionDefineImpl();
        designDataRegionDefine.setKey(UUIDUtils.getKey());
        designDataRegionDefine.setOrder(OrderGenerator.newOrder());
        designDataRegionDefine.setUpdateTime(new Date());
        return designDataRegionDefine;
    }

    @Override
    public void insertDataRegion(DesignDataRegionDefine[] designDataRegionDefines) {
        try {
            this.designDataRegionDefineService.insertDataRegionDefines(designDataRegionDefines);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.DATA_REGION_INSERT.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DATA_REGION_INSERT, (Throwable)e);
        }
    }

    @Override
    public void updateDataRegion(DesignDataRegionDefine[] designDataRegionDefines) {
        try {
            this.designDataRegionDefineService.updateDataRegionDefines(designDataRegionDefines);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.DATA_REGION_UPDATE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DATA_REGION_UPDATE, (Throwable)e);
        }
    }

    @Override
    @Transactional
    public void deleteDataRegion(String[] keys) {
        try {
            for (String regionKey : keys) {
                DesignDataRegionDefine dataRegion = this.getDataRegion(regionKey);
                this.deleteDataLinkByDataRegion(regionKey);
                if (null == dataRegion.getRegionSettingKey()) continue;
                this.deleteRegionSetting(dataRegion.getRegionSettingKey());
            }
            this.designDataRegionDefineService.delete(keys);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.DATA_REGION_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DATA_REGION_DELETE, (Throwable)e);
        }
    }

    @Override
    public List<DesignDataRegionDefine> listAllDataRegion() {
        try {
            return this.designDataRegionDefineService.listGhostRegion();
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.DATA_REGION_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DATA_REGION_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<DesignDataRegionDefine> listDataRegionByForm(String formKey) {
        try {
            return this.designDataRegionDefineService.getAllRegionsInForm(formKey);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.DATA_REGION_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DATA_REGION_QUERY, (Throwable)e);
        }
    }

    @Override
    public DesignDataRegionDefine getDataRegion(String regionKey) {
        try {
            return this.designDataRegionDefineService.queryDataRegionDefine(regionKey);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.DATA_REGION_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DATA_REGION_QUERY, (Throwable)e);
        }
    }

    @Override
    public DesignRegionSettingDefine initRegionSetting() {
        DesignRegionSettingDefineImpl designRegionSettingDefine = new DesignRegionSettingDefineImpl();
        designRegionSettingDefine.setKey(UUIDUtils.getKey());
        designRegionSettingDefine.setOrder(OrderGenerator.newOrder());
        designRegionSettingDefine.setUpdateTime(new Date());
        return designRegionSettingDefine;
    }

    @Override
    @Transactional
    public void insertRegionSetting(DesignRegionSettingDefine regionSettingDefine) {
        try {
            DesignRegionSettingDefine designRegionSettingDefine = ParamPackage.convertRegionSetting(regionSettingDefine);
            this.designRegionSettingDefineService.insertDefine(designRegionSettingDefine);
            this.updateRegionSettingBigData(designRegionSettingDefine);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.DATA_REGION_SETTING_INSERT.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DATA_REGION_SETTING_INSERT, (Throwable)e);
        }
    }

    private void updateRegionSettingBigData(DesignRegionSettingDefine designRegionSettingDefine) throws Exception {
        SerializeListImpl<Object> serializeUtil;
        List<RegionTabSettingDefine> regionTabSettingDataList = designRegionSettingDefine.getRegionTabSetting();
        if (regionTabSettingDataList != null && regionTabSettingDataList.size() > 0) {
            byte[] data = RegionTabSettingData.regionTabSettingDataToBytes(regionTabSettingDataList);
            this.designBigDataService.updateBigDataDefine(designRegionSettingDefine.getKey(), "REGION_TAB", data);
        } else {
            this.designBigDataService.deleteBigDataDefine(designRegionSettingDefine.getKey(), "REGION_TAB");
        }
        List<RowNumberSetting> rowNumberSettings = designRegionSettingDefine.getRowNumberSetting();
        if (rowNumberSettings != null && rowNumberSettings.size() > 0) {
            serializeUtil = new SerializeListImpl<RowNumberSetting>(RowNumberSetting.class);
            byte[] data = serializeUtil.serialize(rowNumberSettings);
            this.designBigDataService.updateBigDataDefine(designRegionSettingDefine.getKey(), "REGION_ORDER", 1, data);
        } else {
            this.designBigDataService.deleteBigDataDefine(designRegionSettingDefine.getKey(), "REGION_ORDER");
        }
        serializeUtil = new SerializeListImpl<RegionEdgeStyleDefine>(RegionEdgeStyleDefine.class);
        List<RegionEdgeStyleDefine> lastRowStyles = designRegionSettingDefine.getLastRowStyles();
        if (null != lastRowStyles && !lastRowStyles.isEmpty()) {
            byte[] data = serializeUtil.serialize(lastRowStyles);
            this.designBigDataService.updateBigDataDefine(designRegionSettingDefine.getKey(), "REGION_LT_ROW_STYLES", data);
        } else {
            this.designBigDataService.deleteBigDataDefine(designRegionSettingDefine.getKey(), "REGION_LT_ROW_STYLES");
        }
        RecordCard cardRecord = designRegionSettingDefine.getCardRecord();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(cardRecord);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        this.designBigDataService.updateBigDataDefine(designRegionSettingDefine.getKey(), "BIG_REGION_CARD", 1, byteArray);
    }

    @Override
    @Transactional
    public void updateRegionSetting(DesignRegionSettingDefine regionSettingDefine) {
        try {
            DesignRegionSettingDefine designRegionSettingDefine = ParamPackage.convertRegionSetting(regionSettingDefine);
            this.designRegionSettingDefineService.updateDefine(designRegionSettingDefine);
            this.updateRegionSettingBigData(designRegionSettingDefine);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.DATA_REGION_SETTING_UPDATE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DATA_REGION_SETTING_UPDATE, (Throwable)e);
        }
    }

    @Override
    @Transactional
    public void deleteRegionSetting(String key) {
        try {
            this.designRegionSettingDefineService.delete(key);
            this.designBigDataService.deleteBigDataDefine(key, "REGION_TAB");
            this.designBigDataService.deleteBigDataDefine(key, "REGION_ORDER");
            this.designBigDataService.deleteBigDataDefine(key, "BIG_REGION_CARD");
            this.designBigDataService.deleteBigDataDefine(key, "REGION_LT_ROW_STYLES");
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.DATA_REGION_SETTING_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DATA_REGION_SETTING_DELETE, (Throwable)e);
        }
    }

    @Override
    public DesignRegionSettingDefine getRegionSettingByRegion(String dataRegionKey) {
        DesignRegionSettingDefine designRegionSettingDefine = null;
        try {
            DesignDataRegionDefine regionDefine = this.designDataRegionDefineService.queryDataRegionDefine(dataRegionKey);
            if (null != regionDefine.getRegionSettingKey()) {
                designRegionSettingDefine = this.designRegionSettingDefineService.queryDefine(regionDefine.getRegionSettingKey());
            }
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.DATA_REGION_SETTING_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DATA_REGION_SETTING_QUERY, (Throwable)e);
        }
        return ParamPackage.packageRegionSetting(designRegionSettingDefine, this.designBigDataService);
    }

    @Override
    public List<DesignRegionSettingDefine> listRegionSetting(List<String> settingKeys) {
        return this.designRegionSettingDefineService.queryDefines(settingKeys);
    }

    @Override
    public DesignDataLinkDefine initDataLink() {
        DesignDataLinkDefineImpl designDataLinkDefine = new DesignDataLinkDefineImpl();
        designDataLinkDefine.setKey(UUIDUtils.getKey());
        designDataLinkDefine.setUniqueCode(OrderGenerator.newOrder());
        designDataLinkDefine.setOrder(OrderGenerator.newOrder());
        designDataLinkDefine.setUpdateTime(new Date());
        return designDataLinkDefine;
    }

    @Override
    public void insertDataLink(DesignDataLinkDefine[] dataLinkDefines) {
        try {
            DesignDataLinkDefine[] designDataLinkDefines = ParamPackage.convertDataLink(dataLinkDefines);
            this.designDataLinkDefineService.insert(designDataLinkDefines);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.DATA_LINK_INSERT.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DATA_LINK_INSERT, (Throwable)e);
        }
    }

    @Override
    public void updateDataLink(DesignDataLinkDefine[] dataLinkDefines) {
        try {
            DesignDataLinkDefine[] designDataLinkDefines = ParamPackage.convertDataLink(dataLinkDefines);
            this.designDataLinkDefineService.updateDataLinkDefines(designDataLinkDefines);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.DATA_LINK_UPDATE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DATA_LINK_UPDATE, (Throwable)e);
        }
    }

    @Override
    public void deleteDataLink(String[] keys) {
        try {
            this.designDataLinkDefineService.delete(keys);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.DATA_LINK_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DATA_LINK_DELETE, (Throwable)e);
        }
    }

    @Override
    public void deleteDataLinkByDataRegion(String dataRegionKey) {
        try {
            this.designDataLinkDefineService.deleteByRegion(dataRegionKey);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.DATA_LINK_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DATA_LINK_DELETE, (Throwable)e);
        }
    }

    @Override
    public DesignDataLinkDefine getDataLink(String dataLinkKey) {
        try {
            return ParamPackage.packageDataLink(this.designDataLinkDefineService.queryDataLinkDefine(dataLinkKey), this.designBigDataService);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.DATA_LINK_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DATA_LINK_DELETE, (Throwable)e);
        }
    }

    @Override
    public DesignDataLinkDefine getDataLinkByFormAndPos(String formKey, int posX, int posY) {
        DesignDataLinkDefine designDataLinkDefine = null;
        try {
            List<DesignDataLinkDefine> dataLinkDefines = this.listDataLinkByForm(formKey);
            for (DesignDataLinkDefine link : dataLinkDefines) {
                if (link.getPosX() != posX || link.getPosY() != posY) continue;
                designDataLinkDefine = link;
                break;
            }
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.DATA_LINK_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DATA_LINK_QUERY, (Throwable)e);
        }
        return ParamPackage.packageDataLink(designDataLinkDefine, this.designBigDataService);
    }

    @Override
    public DesignDataLinkDefine getDataLinkByFormAndColRow(String formKey, int colNum, int rowNum) {
        try {
            return ParamPackage.packageDataLink(this.designDataLinkDefineService.queryDataLinkDefineByColRow(formKey, colNum, rowNum), this.designBigDataService);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.DATA_LINK_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DATA_LINK_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<DesignDataLinkDefine> listDataLinkByForm(String formKey) {
        ArrayList<DesignDataLinkDefine> designDataLinkDefine = new ArrayList<DesignDataLinkDefine>();
        try {
            List<DesignDataRegionDefine> regionDefines = this.listDataRegionByForm(formKey);
            for (DesignDataRegionDefine designDataRegionDefine : regionDefines) {
                List<DesignDataLinkDefine> linkDefines = this.listDataLinkByDataRegion(designDataRegionDefine.getKey());
                designDataLinkDefine.addAll(linkDefines);
            }
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.DATA_LINK_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DATA_LINK_QUERY, (Throwable)e);
        }
        return ParamPackage.packageDataLink(designDataLinkDefine, this.designBigDataService);
    }

    @Override
    public List<DesignDataLinkDefine> listDataLinkByDataRegion(String dataRegionKey) {
        try {
            return ParamPackage.packageDataLink(this.designDataLinkDefineService.getAllLinksInRegion(dataRegionKey), this.designBigDataService);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.DATA_LINK_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DATA_LINK_QUERY, (Throwable)e);
        }
    }

    @Override
    public DesignDataLinkDefine getDataLinkByUniqueCode(String formKey, String uniqueCode) {
        try {
            return ParamPackage.packageDataLink(this.designDataLinkDefineService.queryDataLinkDefineByUniquecode(formKey, uniqueCode), this.designBigDataService);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.DATA_LINK_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DATA_LINK_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<DesignDataLinkDefine> listDataLinkByFormAndFieldKey(String formKey, String fieldKey) {
        try {
            return this.designDataLinkDefineService.getLinksInFormByField(formKey, fieldKey);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.DATA_LINK_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DATA_LINK_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<String> listFieldKeyByDataRegion(String dataRegionKey) {
        try {
            return this.designDataLinkDefineService.listFieldKeyByDataRegion(dataRegionKey);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.DATA_LINK_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DATA_LINK_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<String> listFieldKeyByForm(String formKey) {
        HashSet<String> fieldKeys = new HashSet<String>();
        try {
            List<DesignDataRegionDefine> regionsInForm = this.designDataRegionDefineService.getAllRegionsInForm(formKey);
            for (DesignDataRegionDefine regionDefine : regionsInForm) {
                fieldKeys.addAll(this.designDataLinkDefineService.listFieldKeyByDataRegion(regionDefine.getKey()));
            }
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.DATA_LINK_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DATA_LINK_QUERY, (Throwable)e);
        }
        return new ArrayList<String>(fieldKeys);
    }

    @Override
    public void insertAttachment(String linkKey, AttachmentObj attachment) {
        this.updateAttachment(linkKey, attachment);
    }

    @Override
    public void updateAttachment(String linkKey, AttachmentObj attachment) {
        try {
            if (null != attachment) {
                String attStr = JacksonUtils.objectToJson((Object)attachment);
                this.designBigDataService.updateBigDataDefine(linkKey, "ATTACHMENT", DesignFormDefineBigDataUtil.StringToBytes(attStr));
            }
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.DATA_LINK_UPDATE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DATA_LINK_UPDATE, (Throwable)e);
        }
    }

    @Override
    public void deleteAttachment(String linkKey) {
        try {
            this.designBigDataService.deleteBigDataDefine(linkKey, "ATTACHMENT");
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.DATA_LINK_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DATA_LINK_DELETE, (Throwable)e);
        }
    }

    @Override
    public AttachmentObj getAttachment(String linkKey) {
        AttachmentObj attachmentObj = null;
        try {
            byte[] bigData = this.designBigDataService.getBigData(linkKey, "ATTACHMENT");
            String attachment = DesignFormDefineBigDataUtil.bytesToString(bigData);
            if (!"".equals(attachment)) {
                attachmentObj = (AttachmentObj)JacksonUtils.jsonToObject((String)attachment, AttachmentObj.class);
            }
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.DATA_LINK_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DATA_LINK_DELETE, (Throwable)e);
        }
        return attachmentObj;
    }

    @Override
    public DesignTaskLinkDefine initTaskLink() {
        DesignTaskLinkDefineImpl designTaskLinkDefine = new DesignTaskLinkDefineImpl();
        designTaskLinkDefine.setKey(UUIDUtils.getKey());
        designTaskLinkDefine.setOrder(OrderGenerator.newOrder());
        designTaskLinkDefine.setUpdateTime(new Date());
        return designTaskLinkDefine;
    }

    @Override
    public void insertTaskLink(DesignTaskLinkDefine[] designTaskLinkDefines) {
        try {
            this.designTaskLinkDefineService.insertTaskLinkDefines(designTaskLinkDefines);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.TASK_LINK_INSERT.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.TASK_LINK_INSERT, (Throwable)e);
        }
    }

    @Override
    public void updateTaskLink(DesignTaskLinkDefine[] designTaskLinkDefines) {
        try {
            this.designTaskLinkDefineService.updateTaskLinkDefines(designTaskLinkDefines);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.TASK_LINK_UPDATE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.TASK_LINK_UPDATE, (Throwable)e);
        }
    }

    @Override
    public void deleteTaskLink(String[] keys) {
        try {
            this.designTaskLinkDefineService.delete(keys);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.TASK_LINK_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.TASK_LINK_DELETE, (Throwable)e);
        }
    }

    @Override
    public void deleteTaskLinkByFormScheme(String formSchemeKey) {
        try {
            this.designTaskLinkDefineService.deleteByCurrentFormSchemeKey(formSchemeKey);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.TASK_LINK_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.TASK_LINK_DELETE, (Throwable)e);
        }
    }

    @Override
    public DesignTaskLinkDefine getTaskLink(String taskLinkKey) {
        try {
            return this.designTaskLinkDefineService.queryTaskLinkDefine(taskLinkKey);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.TASK_LINK_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.TASK_LINK_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<DesignTaskLinkDefine> listTaskLinkByFormScheme(String formSchemeKey) {
        try {
            return this.designTaskLinkDefineService.queryDefinesByCurrentFormSchemeKey(formSchemeKey);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.TASK_LINK_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.TASK_LINK_QUERY, (Throwable)e);
        }
    }

    @Override
    public DesignTaskLinkDefine getTaskLinkByFormSchemeAndAlias(String formScheme, String alias) {
        try {
            return this.designTaskLinkDefineService.queryDefinesByCurrentFormSchemeKeyAndNumber(formScheme, alias);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.TASK_LINK_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.TASK_LINK_QUERY, (Throwable)e);
        }
    }

    @Override
    public DesignDimensionFilter initIDimensionFilter() {
        DesignDimensionFilterImpl dimensionFilter = new DesignDimensionFilterImpl();
        dimensionFilter.setKey(UUIDUtils.getKey());
        return dimensionFilter;
    }

    @Override
    public void insertIDimensionFilter(DesignDimensionFilter[] designDimensionFilters) {
        try {
            this.designDimensionFilterService.insertDimensionFilters(Arrays.asList(designDimensionFilters));
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.DIMEN_FILTER_INSERT.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DIMEN_FILTER_INSERT, (Throwable)e);
        }
    }

    @Override
    public void updateIDimensionFilter(DesignDimensionFilter[] designDimensionFilters) {
        try {
            this.designDimensionFilterService.updateDimensionFilters(Arrays.asList(designDimensionFilters));
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.DIMEN_FILTER_UPDATE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DIMEN_FILTER_UPDATE, (Throwable)e);
        }
    }

    @Override
    public void deleteIDimensionFilterByTask(String taskKey) {
        try {
            this.designDimensionFilterService.deleteDimensionFilter(taskKey);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.DIMEN_FILTER_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DIMEN_FILTER_DELETE, (Throwable)e);
        }
    }

    @Override
    public DesignDimensionFilter getDimensionFilterByTaskAndEntity(String taskKey, String entity) {
        try {
            return this.designDimensionFilterService.getDimensionFilterByTaskKey(taskKey, entity);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.DIMEN_FILTER_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DIMEN_FILTER_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<DesignDimensionFilter> listDimensionFilterByTask(String taskKey) {
        try {
            return this.designDimensionFilterService.getDimensionFilterByTaskKey(taskKey);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.DIMEN_FILTER_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DIMEN_FILTER_QUERY, (Throwable)e);
        }
    }

    @Override
    public DesignDataLinkMappingDefine initDataLinkMapping() {
        DesignDataLinkMappingDefineImpl designDataLinkMappingDefine = new DesignDataLinkMappingDefineImpl();
        designDataLinkMappingDefine.setId(UUIDUtils.getKey());
        return designDataLinkMappingDefine;
    }

    @Override
    public void insertDataLinkMapping(String formKey, DesignDataLinkMappingDefine[] designDataLinkMappingDefines) {
        try {
            this.designDataLinkMappingDefineService.saveOrUpdateDataLinkMapping(formKey, Arrays.asList(designDataLinkMappingDefines));
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.DATA_LINK_MAPP_INSERT.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DATA_LINK_MAPP_INSERT, (Throwable)e);
        }
    }

    @Override
    public void insertDataLinkMapping(DesignDataLinkMappingDefine[] designDataLinkMappingDefines) {
        try {
            this.designDataLinkMappingDefineService.insertDataLinkMappingDefine(designDataLinkMappingDefines);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.DATA_LINK_MAPP_INSERT.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DATA_LINK_MAPP_INSERT, (Throwable)e);
        }
    }

    @Override
    public void deleteDataLinkMappingByForm(String formKey) {
        try {
            this.designDataLinkMappingDefineService.deleteDataLinkMappingByFormKey(formKey);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.DATA_LINK_MAPP_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DATA_LINK_MAPP_DELETE, (Throwable)e);
        }
    }

    @Override
    public DesignSchemePeriodLinkDefine initSchemePeriodLink() {
        DesignSchemePeriodLink designSchemePeriodLinkDefine = new DesignSchemePeriodLink();
        return designSchemePeriodLinkDefine;
    }

    @Override
    public List<DesignDataLinkMappingDefine> listDataLinkMappingByForm(String formKey) {
        try {
            return this.designDataLinkMappingDefineService.getDataLinkMapping(formKey);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.DATA_LINK_MAPP_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DATA_LINK_MAPP_QUERY, (Throwable)e);
        }
    }

    @Override
    public void insertSchemePeriodLink(DesignSchemePeriodLinkDefine[] designSchemePeriodLinkDefines) {
        try {
            this.designSchemeEffectivePeriodDefineService.inserSchemePeriodLink(designSchemePeriodLinkDefines);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.SCHEME_EFFECTIVE_INSERT.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.SCHEME_EFFECTIVE_INSERT, (Throwable)e);
        }
    }

    @Override
    public void deleteSchemePeriodLinkByFormScheme(String formSchemeKey) {
        try {
            this.designSchemeEffectivePeriodDefineService.deleteByScheme(formSchemeKey);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.SCHEME_EFFECTIVE_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.SCHEME_EFFECTIVE_DELETE, (Throwable)e);
        }
    }

    @Override
    @Transactional
    public void deleteSchemePeriodLinkByTask(String taskKey) {
        try {
            List<DesignFormSchemeDefine> formSchemeDefines = this.listFormSchemeByTask(taskKey);
            for (DesignFormSchemeDefine formSchemeDefine : formSchemeDefines) {
                this.designSchemeEffectivePeriodDefineService.deleteByScheme(formSchemeDefine.getKey());
            }
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.SCHEME_EFFECTIVE_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.SCHEME_EFFECTIVE_DELETE, (Throwable)e);
        }
    }

    @Override
    public List<DesignSchemePeriodLinkDefine> listSchemePeriodLinkByFormScheme(String formSchemeKey) {
        try {
            return this.designSchemeEffectivePeriodDefineService.querySchemePeriodLinkByScheme(formSchemeKey);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.SCHEME_EFFECTIVE_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.SCHEME_EFFECTIVE_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<DesignSchemePeriodLinkDefine> listSchemePeriodLinkByTask(String taskKey) {
        try {
            return this.designSchemeEffectivePeriodDefineService.querySchemePeriodLinkByTask(taskKey);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.SCHEME_EFFECTIVE_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.SCHEME_EFFECTIVE_QUERY, (Throwable)e);
        }
    }

    @Override
    public DesignSchemePeriodLinkDefine getSchemePeriodLinkByPeriodAndFormScheme(String period, String formScheme) {
        DesignSchemePeriodLinkDefine periodLinkDefine = null;
        try {
            List<DesignSchemePeriodLinkDefine> periodLinkDefines = this.designSchemeEffectivePeriodDefineService.querySchemePeriodLinkByScheme(formScheme);
            for (DesignSchemePeriodLinkDefine linkDefine : periodLinkDefines) {
                if (!linkDefine.getPeriodKey().equals(period)) continue;
                periodLinkDefine = linkDefine;
            }
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.SCHEME_EFFECTIVE_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.SCHEME_EFFECTIVE_QUERY, (Throwable)e);
        }
        return periodLinkDefine;
    }

    @Override
    public DesignSchemePeriodLinkDefine getSchemePeriodLinkByPeriodAndTask(String period, String task) {
        try {
            return this.designSchemeEffectivePeriodDefineService.querySchemePeriodLinkByPeriodAndTask(period, task);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.SCHEME_EFFECTIVE_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.SCHEME_EFFECTIVE_QUERY, (Throwable)e);
        }
    }

    @Override
    public void insertFormFolding(DesignFormFoldingDefine[] foldingDefines) {
        try {
            this.designFormFoldingService.insert(foldingDefines);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORM_FOLDING_INSERT.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_FOLDING_INSERT, (Throwable)e);
        }
    }

    @Override
    public void deleteFormFolding(String[] formFoldingKeys) {
        try {
            this.designFormFoldingService.delete(formFoldingKeys);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORM_FOLDING_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_FOLDING_DELETE, (Throwable)e);
        }
    }

    @Override
    public void deleteFormFoldingByFormKey(String formKey) {
        try {
            this.designFormFoldingService.deleteByFormKey(formKey);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORM_FOLDING_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_FOLDING_DELETE, (Throwable)e);
        }
    }

    @Override
    public DesignFormFoldingDefine getFormFoldingByKey(String key) {
        return this.designFormFoldingService.getByKey(key);
    }

    @Override
    public List<DesignFormFoldingDefine> listFormFoldingByFormKey(String formKey) {
        return this.designFormFoldingService.getByFormKey(formKey);
    }

    @Override
    public void insertTaskOrgLink(TaskOrgLinkDefine[] orgLinkDefines) {
        try {
            this.taskOrgLinkService.insertTaskOrgLink(orgLinkDefines);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.TASK_ORG_LINK_INSERT.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.TASK_ORG_LINK_INSERT, (Throwable)e);
        }
    }

    @Override
    public void deleteTaskOrgLink(String[] orgLinkKeys) {
        try {
            this.taskOrgLinkService.deleteTaskOrgLink(orgLinkKeys);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.TASK_ORG_LINK_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.TASK_ORG_LINK_DELETE, (Throwable)e);
        }
    }

    @Override
    public void deleteTaskOrgLinkByTask(String task) {
        try {
            this.taskOrgLinkService.deleteTaskOrgLinkByTask(task);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.TASK_ORG_LINK_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.TASK_ORG_LINK_DELETE, (Throwable)e);
        }
    }

    @Override
    public void updateTaskOrgLink(TaskOrgLinkDefine[] orgLinkDefines) {
        try {
            this.taskOrgLinkService.updateTaskOrgLink(orgLinkDefines);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.TASK_ORG_LINK_UPDATE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.TASK_ORG_LINK_UPDATE, (Throwable)e);
        }
    }

    @Override
    public TaskOrgLinkDefine getTaskOrgLinkByKey(String key) {
        return this.taskOrgLinkService.getByKey(key);
    }

    @Override
    public List<TaskOrgLinkDefine> listTaskOrgLinkByTask(String task) {
        return this.taskOrgLinkService.getByTask(task);
    }

    @Override
    public TaskOrgLinkDefine getTaskOrgLinkByTaskAndEntity(String task, String entityID) {
        return this.getTaskOrgLinkByTaskAndEntity(task, entityID);
    }

    @Override
    public DesignDataRegionDefine getDataRegion(String regionCode, String formKey) {
        return this.designDataRegionDefineService.getDataRegion(formKey, regionCode);
    }

    @Override
    public List<String> listDataTableByForm(Set<String> forms) {
        return this.designDataLinkDefineService.listDataTableByForm(forms);
    }

    @Override
    public List<DesignDataLinkDefine> getReferencedDataLinkByFields(List<String> fields) {
        try {
            return this.designDataLinkDefineService.getDefinesByFieldKeys(fields);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.DATA_LINK_QUERY.getMessage(), e);
            return Collections.emptyList();
        }
    }
}

