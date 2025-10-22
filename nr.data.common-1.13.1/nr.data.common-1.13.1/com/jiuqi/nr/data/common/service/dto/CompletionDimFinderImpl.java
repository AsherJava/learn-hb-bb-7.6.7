/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.data.common.service.dto;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.data.common.service.dto.CompletionDimFinder;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompletionDimFinderImpl
implements CompletionDimFinder {
    private String dwEntityId;
    private List<String> dw1v1DimNames;
    private List<String> attrCodes;
    private Map<String, String> arrtCodeMap;
    private String periodValue;
    private String taskKey;
    private volatile IEntityTable entityTable;

    public String getDwEntityId() {
        return this.dwEntityId;
    }

    public void setDwEntityId(String dwEntityId) {
        this.dwEntityId = dwEntityId;
    }

    public List<String> getDw1v1DimNames() {
        return this.dw1v1DimNames;
    }

    public String getPeriodValue() {
        return this.periodValue;
    }

    public void setPeriodValue(String periodValue) {
        this.periodValue = periodValue;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public List<String> getAttrCodes() {
        return this.attrCodes;
    }

    public void setAttrCodes(List<String> attrCodes) {
        this.attrCodes = attrCodes;
    }

    @Override
    public List<String> findByDw(String dw) {
        this.initEntityTable();
        IEntityRow row = this.entityTable.findByEntityKey(dw);
        if (row == null) {
            return Collections.emptyList();
        }
        ArrayList<String> values = new ArrayList<String>();
        for (String attrCode : this.attrCodes) {
            values.add(row.getAsString(attrCode));
        }
        return values;
    }

    private void initEntityTable() {
        if (this.entityTable == null) {
            try {
                IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringBeanUtils.getBean(IRunTimeViewController.class);
                TaskDefine taskDefine = runTimeViewController.queryTaskDefine(this.taskKey);
                String dataScheme = taskDefine.getDataScheme();
                IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)SpringBeanUtils.getBean(IRuntimeDataSchemeService.class);
                List dataSchemeDimension = runtimeDataSchemeService.getDataSchemeDimension(dataScheme, DimensionType.DIMENSION);
                IEntityMetaService entityMetaService = (IEntityMetaService)SpringBeanUtils.getBean(IEntityMetaService.class);
                this.arrtCodeMap = new HashMap<String, String>();
                for (DataDimension dataDimension : dataSchemeDimension) {
                    String dimAttribute = dataDimension.getDimAttribute();
                    if (dimAttribute == null) continue;
                    IEntityDefine entityDefine = entityMetaService.queryEntity(dataDimension.getDimKey());
                    this.arrtCodeMap.put(entityDefine.getDimensionName(), dimAttribute);
                }
                IEntityDataService entityDataService = (IEntityDataService)SpringBeanUtils.getBean(IEntityDataService.class);
                IEntityQuery entityQuery = entityDataService.newEntityQuery();
                DimensionValueSet masterKeys = new DimensionValueSet();
                masterKeys.setValue("DATATIME", (Object)this.periodValue);
                entityQuery.setMasterKeys(masterKeys);
                EntityViewDefine dwView = runTimeViewController.getViewByTaskDefineKey(this.taskKey);
                entityQuery.setEntityView(dwView);
                entityQuery.setAuthorityOperations(AuthorityType.None);
                entityQuery.sorted(false);
                IDataDefinitionRuntimeController runtimeController = (IDataDefinitionRuntimeController)SpringBeanUtils.getBean(IDataDefinitionRuntimeController.class);
                ExecutorContext executorContext = new ExecutorContext(runtimeController);
                this.entityTable = entityQuery.executeFullBuild((IContext)executorContext);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public String findByDw(String dw, String dimName) {
        this.initEntityTable();
        IEntityRow row = this.entityTable.findByEntityKey(dw);
        if (row == null) {
            return null;
        }
        String attrCode = this.arrtCodeMap.get(dimName);
        if (attrCode == null) {
            return null;
        }
        return row.getAsString(attrCode);
    }

    public void setDw1v1DimNames(List<String> dw1v1DimName) {
        this.dw1v1DimNames = dw1v1DimName;
    }
}

