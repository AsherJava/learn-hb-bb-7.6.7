/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.TableKind
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.controller.IRunTimeTaskFlowController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 */
package com.jiuqi.nr.batchupload.service.impl;

import com.jiuqi.np.definition.common.TableKind;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.nr.batchupload.bean.TaskDefineData;
import com.jiuqi.nr.batchupload.bean.UploadResult;
import com.jiuqi.nr.batchupload.bean.UploadloadParam;
import com.jiuqi.nr.batchupload.service.IBatchUploadService;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.controller.IRunTimeTaskFlowController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatchUploadServiceImpl
implements IBatchUploadService {
    private static final Logger log = LoggerFactory.getLogger(BatchUploadServiceImpl.class);
    @Autowired
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private IRunTimeTaskFlowController runTimeTaskFlowController;
    @Autowired
    IDataDefinitionDesignTimeController npDesignController;
    @Autowired
    DefinitionAuthorityProvider definitionAuthorityProvider;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IEntityMetaService entityMetaService;

    @Override
    public List<TaskDefineData> getUploadTask() {
        ArrayList<TaskDefineData> taskList = new ArrayList<TaskDefineData>();
        TaskDefineData taskDefineData = null;
        List allTaskDefines = this.runTimeTaskFlowController.getEnableFlowTaskDefines();
        if (allTaskDefines != null && !allTaskDefines.isEmpty()) {
            for (TaskDefine taskDefine : allTaskDefines) {
                FlowsType flowsType;
                taskDefineData = new TaskDefineData();
                boolean canUploadFormScheme = this.definitionAuthorityProvider.canUploadTask(taskDefine.getKey());
                if (!canUploadFormScheme || !FlowsType.DEFAULT.equals((Object)(flowsType = taskDefine.getFlowsSetting().getFlowsType())) && !FlowsType.EXTEND.equals((Object)flowsType) && !FlowsType.WORKFLOW.equals((Object)flowsType)) continue;
                taskDefineData.setKey(taskDefine.getKey());
                taskDefineData.setCode(taskDefine.getTaskCode());
                taskDefineData.setTitle(taskDefine.getTitle());
                taskList.add(taskDefineData);
            }
        }
        return taskList;
    }

    @Override
    public List<UploadResult> getUploadEntities(UploadloadParam uploadloadParam) {
        ArrayList<UploadResult> uploadResultLisy = new ArrayList<UploadResult>();
        UploadResult uploadResult = null;
        try {
            if (uploadloadParam != null) {
                if ("task".equals(uploadloadParam.getType())) {
                    TaskDefine taskDefine = this.runTimeAuthViewController.queryTaskDefine(uploadloadParam.getKey());
                    if (taskDefine != null) {
                        String uploadEntities = taskDefine.getFlowsSetting().getDesignTableDefines();
                        String[] split = uploadEntities.split(";");
                        for (int i = 0; i < split.length; ++i) {
                            uploadResult = new UploadResult();
                            if (this.periodEntityAdapter.isPeriodEntity(split[i])) continue;
                            IEntityDefine entityTableDefine = this.entityMetaService.queryEntity(split[i]);
                            uploadResult.setKey(split[i]);
                            uploadResult.setTitle(entityTableDefine.getTitle());
                            uploadResult.setKind(TableKind.TABLE_KIND_ENTITY);
                            uploadResult.setDimensionName(entityTableDefine.getDimensionName());
                            uploadResultLisy.add(uploadResult);
                        }
                    }
                } else {
                    FormSchemeDefine formScheme = this.runTimeAuthViewController.getFormScheme(uploadloadParam.getKey());
                    if (formScheme != null) {
                        String uploadEntities = formScheme.getFlowsSetting().getDesignTableDefines();
                        String[] split = uploadEntities.split(";");
                        for (int i = 0; i < split.length; ++i) {
                            uploadResult = new UploadResult();
                            if (this.periodEntityAdapter.isPeriodEntity(split[i])) {
                                uploadResult.setKey(split[i]);
                                uploadResult.setTitle("\u65f6\u671f");
                                uploadResult.setKind(TableKind.TABLE_KIND_ENTITY_PERIOD);
                                uploadResult.setDimensionName("DATATIME");
                                uploadResultLisy.add(uploadResult);
                                continue;
                            }
                            IEntityDefine entityTableDefine = this.entityMetaService.queryEntity(split[i]);
                            uploadResult.setKey(split[i]);
                            uploadResult.setTitle(entityTableDefine.getTitle());
                            uploadResult.setKind(TableKind.TABLE_KIND_ENTITY);
                            uploadResult.setDimensionName(entityTableDefine.getDimensionName());
                            uploadResultLisy.add(uploadResult);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return uploadResultLisy;
    }

    @Override
    public List<TaskDefineData> getApprovalTask() {
        ArrayList<TaskDefineData> taskList = new ArrayList<TaskDefineData>();
        TaskDefineData taskDefineData = null;
        List allTaskDefines = this.runTimeTaskFlowController.getEnableFlowTaskDefines();
        if (allTaskDefines != null && !allTaskDefines.isEmpty()) {
            for (TaskDefine taskDefine : allTaskDefines) {
                FlowsType flowsType;
                taskDefineData = new TaskDefineData();
                boolean canUploadFormScheme = this.definitionAuthorityProvider.canAuditTask(taskDefine.getKey());
                if (!canUploadFormScheme || !FlowsType.DEFAULT.equals((Object)(flowsType = taskDefine.getFlowsSetting().getFlowsType())) && !FlowsType.EXTEND.equals((Object)flowsType) && !FlowsType.WORKFLOW.equals((Object)flowsType)) continue;
                taskDefineData.setKey(taskDefine.getKey());
                taskDefineData.setCode(taskDefine.getTaskCode());
                taskDefineData.setTitle(taskDefine.getTitle());
                taskList.add(taskDefineData);
            }
        }
        return taskList;
    }
}

