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
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.datascheme.common.DataSchemeUtils
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.multcheck2.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.common.DataSchemeUtils;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.multcheck2.service.IMCDimService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class MCDimServiceImpl
implements IMCDimService {
    @Autowired
    @Qualifier(value="RuntimeDataSchemeNoCacheServiceImpl-NO_CACHE")
    private IRuntimeDataSchemeService dataSchemeServiceNo;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    private com.jiuqi.nr.definition.controller.IRunTimeViewController runTimeViewController;
    @Autowired
    private IRunTimeViewController entityRunTimeController;
    @Autowired
    protected IEntityDataService entityDataService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;

    @Override
    public List<DataDimension> getDynamicDimsNoCache(String dataSchemeKey) {
        ArrayList<DataDimension> res = new ArrayList<DataDimension>();
        List dims = this.dataSchemeServiceNo.getDataSchemeDimension(dataSchemeKey, DimensionType.DIMENSION);
        if (CollectionUtils.isEmpty(dims)) {
            return null;
        }
        for (DataDimension dim : dims) {
            res.add(dim);
        }
        return res;
    }

    @Override
    public List<String> getDynamicFields(String dataSchemeKey) {
        ArrayList<String> res = new ArrayList<String>();
        List dims = this.dataSchemeService.getDataSchemeDimension(dataSchemeKey, DimensionType.DIMENSION);
        if (CollectionUtils.isEmpty(dims)) {
            return null;
        }
        for (DataDimension dim : dims) {
            String entityKey = dim.getDimKey();
            if ("ADJUST".equals(entityKey)) {
                res.add(entityKey);
                continue;
            }
            IEntityDefine entity = this.entityMetaService.queryEntity(entityKey);
            res.add(entity.getDimensionName());
        }
        return res;
    }

    @Override
    public List<String> getDynamicFieldsByTask(String task) {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(task);
        return this.getDynamicFields(taskDefine.getDataScheme());
    }

    @Override
    public List<DataDimension> getDynamicDimsForPage(String dataSchemeKey) {
        ArrayList<DataDimension> res = new ArrayList<DataDimension>();
        List dims = this.dataSchemeService.getDataSchemeDimension(dataSchemeKey, DimensionType.DIMENSION);
        if (CollectionUtils.isEmpty(dims)) {
            return null;
        }
        for (DataDimension dim : dims) {
            if ("ADJUST".equals(dim.getDimKey()) || DataSchemeUtils.isSingleSelect((DataDimension)dim)) continue;
            res.add(dim);
        }
        return res;
    }

    @Override
    public List<String> getDynamicDimNamesForPage(String dataSchemeKey) {
        ArrayList<String> res = new ArrayList<String>();
        List dims = this.dataSchemeService.getDataSchemeDimension(dataSchemeKey, DimensionType.DIMENSION);
        if (CollectionUtils.isEmpty(dims)) {
            return null;
        }
        for (DataDimension dim : dims) {
            if ("ADJUST".equals(dim.getDimKey()) || DataSchemeUtils.isSingleSelect((DataDimension)dim)) continue;
            IEntityDefine entity = this.entityMetaService.queryEntity(dim.getDimKey());
            res.add(entity.getDimensionName());
        }
        return res;
    }

    @Override
    public List<DataDimension> getOtherDimsForReport(String dataSchemeKey) {
        ArrayList<DataDimension> res = new ArrayList<DataDimension>();
        List dims = this.dataSchemeService.getDataSchemeDimension(dataSchemeKey, DimensionType.DIMENSION);
        if (CollectionUtils.isEmpty(dims)) {
            return null;
        }
        for (DataDimension dim : dims) {
            if ("ADJUST".equals(dim.getDimKey()) || dim.getReportDim().booleanValue() || DataSchemeUtils.isSingleSelect((DataDimension)dim)) continue;
            res.add(dim);
        }
        return res;
    }

    @Override
    public List<DataDimension> getReportDims(String dataSchemeKey) {
        ArrayList<DataDimension> res = new ArrayList<DataDimension>();
        List dims = this.dataSchemeService.getDataSchemeDimension(dataSchemeKey, DimensionType.DIMENSION);
        if (CollectionUtils.isEmpty(dims)) {
            return null;
        }
        for (DataDimension dim : dims) {
            if ("ADJUST".equals(dim.getDimKey()) || DataSchemeUtils.isSingleSelect((DataDimension)dim) || !dim.getReportDim().booleanValue()) continue;
            res.add(dim);
        }
        return res;
    }

    @Override
    public List<IEntityRow> getEntityAllRow(String entityId, String formSchemeKey, String taskKey, String period) throws Exception {
        return this.filterEntityValue(entityId, formSchemeKey, taskKey, period, null);
    }

    @Override
    public List<IEntityRow> filterEntityValue(String entityId, String formSchemeKey, String taskKey, String period, String sourceValue) throws Exception {
        EntityViewDefine entityViewDefine = this.entityRunTimeController.getDimensionView(formSchemeKey, entityId);
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)period);
        if (StringUtils.hasText(sourceValue)) {
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityId);
            dimensionValueSet.setValue(entityDefine.getDimensionName(), (Object)sourceValue);
        }
        IEntityQuery query = this.entityDataService.newEntityQuery();
        query.sorted(true);
        query.setEntityView(entityViewDefine);
        query.setMasterKeys(dimensionValueSet);
        query.setAuthorityOperations(AuthorityType.Read);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formSchemeKey);
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        context.setEnv((IFmlExecEnvironment)environment);
        context.setVarDimensionValueSet(dimensionValueSet);
        context.setPeriodView(taskDefine.getDateTime());
        IEntityTable entityTable = query.executeReader((IContext)context);
        return entityTable.getAllRows();
    }
}

