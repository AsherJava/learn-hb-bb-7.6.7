/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.attachment.service.FilePoolService
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.output.EntityData
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.dataentry.attachment.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.attachment.service.FilePoolService;
import com.jiuqi.nr.dataentry.attachment.intf.IAttachmentContext;
import com.jiuqi.nr.dataentry.attachment.intf.SubordinateDWContext;
import com.jiuqi.nr.dataentry.attachment.message.CustomPeriodData;
import com.jiuqi.nr.dataentry.attachment.message.PeriodObj;
import com.jiuqi.nr.dataentry.attachment.message.SceneSubjectInfo;
import com.jiuqi.nr.dataentry.attachment.message.TaskObj;
import com.jiuqi.nr.dataentry.attachment.service.TaskOperationService;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskOperationServiceImpl
implements TaskOperationService {
    private static final Logger logger = LoggerFactory.getLogger(TaskOperationServiceImpl.class);
    @Autowired
    private IRunTimeViewController iRunTimeViewService;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Resource
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Resource
    private IEntityDataService dataService;
    @Resource
    private IEntityViewRunTimeController viewAdapter;
    @Resource
    private IDataDefinitionRuntimeController tbRtCtl;
    @Autowired
    private FilePoolService filePoolService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private INvwaSystemOptionService nvwaSystemOptionService;

    @Override
    public TaskObj getCurrentTaskInfo(IAttachmentContext context) {
        TaskDefine taskDefine = this.iRunTimeViewService.queryTaskDefine(context.getTask());
        TaskObj taskObj = this.getTaskObj(taskDefine);
        if (null != taskObj) {
            FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(context.getFormscheme());
            if (StringUtils.isEmpty((String)formSchemeDefine.getDw())) {
                taskObj.setEntityId(taskDefine.getDw());
            } else {
                taskObj.setEntityId(formSchemeDefine.getDw());
            }
        }
        return taskObj;
    }

    @Override
    public List<TaskObj> getAllRunTimeTasks() {
        ArrayList<TaskObj> taskObjList = new ArrayList<TaskObj>();
        List allTaskDefines = null;
        try {
            allTaskDefines = this.runTimeAuthViewController.getAllTaskDefines();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        for (TaskDefine taskDefine : allTaskDefines) {
            TaskObj taskObj = this.getTaskObj(taskDefine);
            if (null != taskObj) {
                taskObj.setEntityId(taskDefine.getDw());
                taskObj.setDataSchemeKey(taskDefine.getDataScheme());
                taskObj.setOpenAdjust(this.runtimeDataSchemeService.enableAdjustPeriod(taskDefine.getDataScheme()));
            }
            taskObjList.add(taskObj);
        }
        Collections.reverse(taskObjList);
        return taskObjList;
    }

    @Override
    public String getFormSchemeKey(IAttachmentContext context) {
        String formScheme = "";
        try {
            SchemePeriodLinkDefine schemePeriodLinkDefine = this.iRunTimeViewController.querySchemePeriodLinkByPeriodAndTask(context.getDimensionSet().get("DATATIME").getValue(), context.getTask());
            if (null != schemePeriodLinkDefine) {
                formScheme = schemePeriodLinkDefine.getSchemeKey();
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return formScheme;
    }

    @Override
    public boolean isOpenFilepool() {
        boolean openFilepool = this.filePoolService.isOpenFilepool();
        return openFilepool;
    }

    @Override
    public boolean isPreview() {
        String isPreviewOptio = this.nvwaSystemOptionService.get("attachmentManagement", "IS_PREVIEW_OPTIONS");
        return "1".equals(isPreviewOptio);
    }

    @Override
    public List<String> getSubordinateDW(SubordinateDWContext context) {
        ArrayList<String> dwKeys = new ArrayList<String>();
        dwKeys.add(context.getEntityKeyData());
        IEntityTable iEntityTable = this.getIEntityTable(context.getEntityId());
        if (2 == context.getType()) {
            List childRows = iEntityTable.getChildRows(context.getEntityKeyData());
            List childNodeKeys = childRows.stream().map(t -> t.getCode()).collect(Collectors.toList());
            dwKeys.addAll(childNodeKeys);
        } else if (3 == context.getType()) {
            List allChildRows = iEntityTable.getAllChildRows(context.getEntityKeyData());
            List childNodeKeys = allChildRows.stream().map(t -> t.getCode()).collect(Collectors.toList());
            dwKeys.addAll(childNodeKeys);
        }
        return dwKeys;
    }

    private TaskObj getTaskObj(TaskDefine taskDefine) {
        TaskObj taskObj = null;
        if (taskDefine != null) {
            try {
                taskObj = new TaskObj(taskDefine);
                IPeriodEntity iPeriodEntity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(taskDefine.getDateTime());
                PeriodObj periodObj = new PeriodObj();
                PeriodType periodType = iPeriodEntity.getPeriodType();
                if (periodType == PeriodType.CUSTOM) {
                    periodObj.setPeriodType(periodType.type());
                    periodObj.setDefaultPeriod(false);
                    ArrayList<CustomPeriodData> customPeriodDataList = new ArrayList<CustomPeriodData>();
                    List iPeriodRowList = this.periodEngineService.getPeriodAdapter().getPeriodProvider(iPeriodEntity.getKey()).getPeriodItems();
                    for (IPeriodRow index : iPeriodRowList) {
                        CustomPeriodData customPeriodData = new CustomPeriodData();
                        customPeriodData.setTitle(index.getTitle());
                        customPeriodData.setCode(index.getCode());
                        customPeriodDataList.add(customPeriodData);
                    }
                    periodObj.setCustomPeriodDataList(customPeriodDataList);
                } else {
                    periodObj.setPeriodType(periodType.type());
                    periodObj.setDefaultPeriod(true);
                }
                taskObj.setPeriodObj(periodObj);
                IEntityDefine entityDefine = this.entityMetaService.queryEntity(taskDefine.getDw());
                taskObj.setDWDimentionTitle(entityDefine.getTitle());
                taskObj.setDWDimentionName(entityDefine.getDimensionName());
                if (null != taskDefine.getDims() && !"".equals(taskDefine.getDims())) {
                    List dataSchemeDimension = this.runtimeDataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme());
                    HashMap<String, DataDimension> dataDimensionMap = new HashMap<String, DataDimension>();
                    IEntityModel entityModel = null;
                    for (DataDimension dataDimension : dataSchemeDimension) {
                        dataDimensionMap.put(dataDimension.getDimKey(), dataDimension);
                        if (DimensionType.UNIT != dataDimension.getDimensionType()) continue;
                        entityModel = this.entityMetaService.getEntityModel(dataDimension.getDimKey());
                    }
                    String[] entityViewKeys = taskDefine.getDims().split(";");
                    ArrayList<SceneSubjectInfo> sceneSubjectInfoList = new ArrayList<SceneSubjectInfo>();
                    for (String entityViewKey : entityViewKeys) {
                        IEntityAttribute attribute;
                        String refField;
                        IEntityDefine dimEntityDefine = this.entityMetaService.queryEntity(entityViewKey);
                        if (null == dimEntityDefine) continue;
                        String string = refField = null == dataDimensionMap.get(entityViewKey) ? "" : ((DataDimension)dataDimensionMap.get(entityViewKey)).getDimAttribute();
                        if (null != entityModel && org.springframework.util.StringUtils.hasText(refField) && null != (attribute = entityModel.getAttribute(refField)) && !attribute.isMultival()) continue;
                        SceneSubjectInfo sceneSubjectInfo = new SceneSubjectInfo();
                        sceneSubjectInfo.setEntitys(this.buildEntityDatas(entityViewKey));
                        sceneSubjectInfo.setDimensionName(dimEntityDefine.getDimensionName());
                        sceneSubjectInfo.setTitle(dimEntityDefine.getTitle());
                        sceneSubjectInfo.setEntityKey(entityViewKey);
                        sceneSubjectInfoList.add(sceneSubjectInfo);
                    }
                    taskObj.setSceneSubjectInfoList(sceneSubjectInfoList);
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return taskObj;
    }

    private List<EntityData> buildEntityDatas(String entityID) {
        ArrayList<EntityData> dataList = new ArrayList<EntityData>();
        IEntityTable dataTable = this.getIEntityTable(entityID);
        if (null != dataTable) {
            List allRows = dataTable.getAllRows();
            for (IEntityRow row : allRows) {
                EntityData data = new EntityData();
                data.setCode(row.getCode());
                data.setRowCaption(row.getTitle());
                dataList.add(data);
            }
        }
        return dataList;
    }

    private IEntityTable getIEntityTable(String entityID) {
        IEntityTable dataTable = null;
        EntityViewDefine viewDefine = this.viewAdapter.buildEntityView(entityID);
        IEntityQuery query = this.dataService.newEntityQuery();
        query.setEntityView(viewDefine);
        try {
            dataTable = query.executeReader((IContext)new ExecutorContext(this.tbRtCtl));
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return dataTable;
    }
}

