/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.bpm.common.UploadStateNew
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean
 *  com.jiuqi.nr.bpm.service.IBatchQueryUploadStateService
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 */
package com.jiuqi.nr.reminder.untils;

import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.service.IBatchQueryUploadStateService;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.reminder.untils.EntityQueryManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class EntityHelper {
    private static final Logger log = LoggerFactory.getLogger(EntityHelper.class);
    private final IDataDefinitionRuntimeController runtimeController;
    private final IDataAccessProvider dataAccessProvider;
    private final EntityQueryManager entityManager;
    private final IBatchQueryUploadStateService uploadStateService;
    @Autowired
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private IEntityViewRunTimeController npRunEntityController;
    @Autowired
    public IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    IRunTimeViewController controller;
    @Autowired
    private IEntityMetaService metaService;

    @Autowired
    public EntityHelper(IDataDefinitionRuntimeController runtimeController, IDataAccessProvider dataAccessProvider, @Qualifier(value="reminder.entityManager") EntityQueryManager entityManager, IBatchQueryUploadStateService uploadStateService) {
        this.runtimeController = runtimeController;
        this.dataAccessProvider = dataAccessProvider;
        this.entityManager = entityManager;
        this.uploadStateService = uploadStateService;
    }

    private IEntityTable getEntityTable(String entityViewKey, String period, String formSchemeKey) {
        try {
            IEntityTable entityTable;
            DimensionValueSet dimValueSet = new DimensionValueSet();
            if (period != null) {
                dimValueSet.setValue("DATATIME", (Object)period);
            }
            if ((entityTable = this.entityManager.entityQuerySet(entityViewKey, dimValueSet, formSchemeKey)) != null) {
                return entityTable;
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public List<String> getDirectSubordinate(String entityViewKey, String entityDataKey, String formSchemeKey, String period) throws Exception {
        IEntityTable entityTable = this.getEntityTable(entityViewKey, period, formSchemeKey);
        return entityTable.getChildRows(entityDataKey).stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
    }

    public List<String> getAllSubordinate(String entityViewKey, String entityDataKey, String formSchemeKey, String period) throws Exception {
        IEntityTable entityTable = this.getEntityTable(entityViewKey, period, formSchemeKey);
        return entityTable.getAllChildRows(entityDataKey).stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
    }

    public List<String> getAllUnUpload(List<String> unitIds, String unitId, String formSchemeKey, String period) {
        ArrayList<String> filteredUnitIds = new ArrayList<String>();
        try {
            FormSchemeDefine formSchemeDefine = this.runTimeAuthViewController.getFormScheme(formSchemeKey);
            for (String entityId : unitIds) {
                ActionStateBean actionStateBean;
                DimensionValueSet dimensionValueSet = this.buildDimension(formSchemeKey, Arrays.asList(entityId));
                UploadStateNew uploadState = this.uploadStateService.queryUploadStateNew(dimensionValueSet, null, formSchemeDefine);
                if (uploadState == null || (actionStateBean = uploadState.getActionStateBean()) == null || UploadState.UPLOADED.toString().equals(actionStateBean.getCode()) || UploadState.CONFIRMED.toString().equals(actionStateBean.getCode())) continue;
                filteredUnitIds.add(entityId);
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return filteredUnitIds;
    }

    public String getMainDimName(EntityViewDefine viewDefine) {
        ExecutorContext context = new ExecutorContext(this.runtimeController);
        IDataAssist dataAssist = this.dataAccessProvider.newDataAssist(context);
        return dataAssist.getDimensionName(viewDefine);
    }

    public String getMainDimName(String formSchemeKey) {
        EntityViewDefine entityViewDefine = this.getEntityViewDefine(formSchemeKey);
        ExecutorContext context = new ExecutorContext(this.runtimeController);
        IDataAssist dataAssist = this.dataAccessProvider.newDataAssist(context);
        return dataAssist.getDimensionName(entityViewDefine);
    }

    public DimensionValueSet buildDimension(String formSchemeKey, List<String> ids) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        EntityViewDefine entityViewDefine = this.getEntityViewDefine(formSchemeKey);
        if (entityViewDefine != null) {
            String mainDimName = this.getMainDimName(entityViewDefine);
            dimensionValueSet.setValue(mainDimName, ids);
            return dimensionValueSet;
        }
        return dimensionValueSet;
    }

    public EntityViewDefine getEntityViewDefine(String formSchemeId) {
        try {
            FormSchemeDefine formScheme = this.runTimeAuthViewController.getFormScheme(formSchemeId);
            if (formScheme != null) {
                String entitiesKey = formScheme.getMasterEntitiesKey();
                String[] entitiesKeyArr = entitiesKey.split(";");
                EntityViewDefine entityView = null;
                for (String entityKey : entitiesKeyArr) {
                    boolean periodView = this.periodEntityAdapter.isPeriodEntity(entityKey);
                    EntityViewDefine entityViewDefine = this.npRunEntityController.buildEntityView(entityKey);
                    if (periodView) continue;
                    return entityViewDefine;
                }
                return entityView;
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
        return null;
    }

    public EntityViewDefine getEntityViewDefineByEntityKey(String entityKey) {
        try {
            return this.npRunEntityController.buildEntityView(entityKey);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public EntityViewDefine getParentEntityViewDefine(String formSchemeId) {
        try {
            FormSchemeDefine formScheme = this.runTimeAuthViewController.getFormScheme(formSchemeId);
            if (formScheme != null) {
                String entitiesKey = formScheme.getMasterEntitiesKey();
                String[] entitiesKeyArr = entitiesKey.split(";");
                EntityViewDefine entityView = null;
                for (String entitiy : entitiesKeyArr) {
                    entityView = this.npRunEntityController.buildEntityView(entitiy);
                    boolean periodView = this.periodEntityAdapter.isPeriodEntity(entitiy);
                    if (periodView) continue;
                    return entityView;
                }
                return entityView;
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
        return null;
    }
}

