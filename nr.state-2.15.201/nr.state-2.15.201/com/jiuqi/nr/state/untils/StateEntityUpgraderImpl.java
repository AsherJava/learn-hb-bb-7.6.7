/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.common.exception.NotFoundTableDefineException
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.data.access.common.TableConsts
 *  com.jiuqi.nr.datascheme.internal.dto.DataSchemeDTO
 *  com.jiuqi.nr.datascheme.internal.service.DataSchemeService
 *  com.jiuqi.nr.definition.common.IEntityUpgrader
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.state.untils;

import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.common.exception.NotFoundTableDefineException;
import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.data.access.common.TableConsts;
import com.jiuqi.nr.datascheme.internal.dto.DataSchemeDTO;
import com.jiuqi.nr.datascheme.internal.service.DataSchemeService;
import com.jiuqi.nr.definition.common.IEntityUpgrader;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StateEntityUpgraderImpl
implements IEntityUpgrader {
    private static final Logger logger = LoggerFactory.getLogger(StateEntityUpgraderImpl.class);
    @Resource
    IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    IRunTimeViewController runTimeViewController;
    @Resource
    IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    RoleService roleService;
    @Autowired
    RuntimeViewController runtimeViewController;
    @Autowired
    INvwaDataAccessProvider iNvwaDataAccessProvider;
    @Autowired
    DataModelService dataModelService;
    @Autowired
    IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    DataEngineAdapter dataEngineAdapter;
    @Autowired
    DataSchemeService dataSchemeService;

    public int delete(FormSchemeDefine formScheme, DimensionValueSet dimensionValue) {
        int deleteRow = 0;
        try {
            TableModelDefine tableDefine = this.getTableDefineNew(formScheme);
            NvwaQueryModel queryModel = new NvwaQueryModel();
            List<ColumnModelDefine> allFields = this.getAllFieldsInTable(tableDefine.getID());
            for (ColumnModelDefine columnModelDefine : allFields) {
                queryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
            }
            DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableDefine.getName());
            DimensionSet dimensionSet = dimensionValue.getDimensionSet();
            for (int i = 0; i < dimensionSet.size(); ++i) {
                String name = dimensionSet.get(i);
                ColumnModelDefine column = dimensionChanger.getColumn(name);
                queryModel.getColumnFilters().put(column, dimensionValue.getValue(name));
            }
            DataAccessContext context = new DataAccessContext(this.dataModelService);
            INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
            INvwaDataUpdator dataUpdate = updatableDataAccess.openForUpdate(context);
            dataUpdate.deleteAll();
            dataUpdate.commitChanges(context);
        }
        catch (Exception e1) {
            logger.error(e1.getMessage(), e1);
        }
        return deleteRow;
    }

    public String getFristEntity(String formSchemeKey) {
        try {
            String[] entitiesKey;
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            String masterEntitiesKey = formScheme.getMasterEntitiesKey();
            for (String entityKey : entitiesKey = masterEntitiesKey.split(";")) {
                if (this.periodEntityAdapter.isPeriodEntity(entityKey)) continue;
                return entityKey;
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public boolean isFristEntity(String entityKey) {
        try {
            if (!this.periodEntityAdapter.isPeriodEntity(entityKey)) {
                return true;
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    public boolean isPeriod(String entityKey) {
        try {
            if (this.periodEntityAdapter.isPeriodEntity(entityKey)) {
                return true;
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    public EntityViewDefine getEntityViewDefine(String formSchemeId) {
        try {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeId);
            String entitiesKey = formScheme.getMasterEntitiesKey();
            String[] entitiesKeyArr = entitiesKey.split(";");
            EntityViewDefine entityView = null;
            for (String entitiy : entitiesKeyArr) {
                entityView = this.entityViewRunTimeController.buildEntityView(entitiy);
                if (this.periodEntityAdapter.isPeriodEntity(entitiy)) continue;
                return entityView;
            }
            return entityView;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public TableModelDefine getTableDefine(FormSchemeDefine formScheme) {
        TableModelDefine tableModelDefine = null;
        try {
            String tableCode = "SYS_STATE_" + formScheme.getFormSchemeCode();
            tableModelDefine = this.dataModelService.getTableModelDefineByCode(tableCode);
        }
        catch (Exception e) {
            throw new NotFoundTableDefineException(new String[]{"SYS_STATE_" + formScheme.getFormSchemeCode()});
        }
        return tableModelDefine;
    }

    public TableModelDefine getTableDefineNew(FormSchemeDefine formScheme) {
        TableModelDefine tableModelDefine = null;
        try {
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
            DataSchemeDTO dataScheme = this.dataSchemeService.getDataScheme(taskDefine.getDataScheme());
            String tableCode = TableConsts.getSysTableName((String)"NR_UNITSTATE_%s", (String)dataScheme.getBizCode());
            tableModelDefine = this.dataModelService.getTableModelDefineByCode(tableCode);
        }
        catch (Exception e) {
            throw new NotFoundTableDefineException(new String[]{"NR_UNITSTATE_%s" + formScheme.getFormSchemeCode()});
        }
        return tableModelDefine;
    }

    public List<ColumnModelDefine> getAllFieldsInTable(String tableKey) {
        try {
            return this.dataModelService.getColumnModelDefinesByTable(tableKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public boolean tableExist(FormSchemeDefine formScheme) {
        try {
            String tableCode = "SYS_STATE_" + formScheme.getFormSchemeCode();
            TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineByCode(tableCode);
            if (tableModelDefine != null) {
                return true;
            }
        }
        catch (Exception e) {
            throw new NotFoundTableDefineException(new String[]{"SYS_STATE_" + formScheme.getFormSchemeCode()});
        }
        return false;
    }
}

