/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDimensionProvider
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataentity.entity.DataEntityType
 *  com.jiuqi.nr.dataentity.entity.IDataEntity
 *  com.jiuqi.nr.dataentity.entity.IDataEntityRow
 *  com.jiuqi.nr.dataentity.service.DataEntityService
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.util.StringUtils
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.bpm.de.dataflow.util;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDimensionProvider;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.bpm.de.dataflow.util.WorkflowReportDimService;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.dataentity.entity.DataEntityType;
import com.jiuqi.nr.dataentity.entity.IDataEntity;
import com.jiuqi.nr.dataentity.entity.IDataEntityRow;
import com.jiuqi.nr.dataentity.service.DataEntityService;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportDimensionParam {
    private static final Logger logger = LoggerFactory.getLogger(ReportDimensionParam.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private DataEntityService dataEntityService;
    @Autowired
    private IDimensionProvider dimensionProvider;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Resource
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private WorkflowReportDimService workflowReportDimService;

    public List<Map<String, DimensionValue>> setReportDimension(String formSchemeKey, List<String> unitIds, String period) {
        Map<String, List<String>> reportDimensionValue = this.getReportDimValueofDimName(formSchemeKey, period);
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        if (reportDimensionValue != null && reportDimensionValue.size() > 0) {
            for (Map.Entry<String, List<String>> value : reportDimensionValue.entrySet()) {
                dimensionValueSet.setValue(value.getKey(), value.getValue());
            }
        }
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        String tableName = this.dimensionProvider.getDimensionNameByEntityId(formScheme.getDw());
        dimensionValueSet.setValue(tableName, unitIds);
        dimensionValueSet.setValue("DATATIME", (Object)period);
        Map dimensionSet = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet);
        List dimensionSetList = DimensionValueSetUtil.getDimensionSetList((Map)dimensionSet);
        return dimensionSetList;
    }

    public List<Map<String, DimensionValue>> setReportDimension(String formSchemeKey, DimensionValueSet dimensionValueSet) {
        String period = dimensionValueSet.getValue("DATATIME").toString();
        Map<String, List<String>> reportDimensionValue = this.getReportDimValueofDimName(formSchemeKey, period);
        if (reportDimensionValue != null && reportDimensionValue.size() > 0) {
            for (Map.Entry<String, List<String>> value : reportDimensionValue.entrySet()) {
                dimensionValueSet.setValue(value.getKey(), value.getValue());
            }
        }
        Map dimensionSet = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet);
        List dimensionSetList = DimensionValueSetUtil.getDimensionSetList((Map)dimensionSet);
        return dimensionSetList;
    }

    private Map<String, List<String>> getReportDimValueofDimName(String formSchemeKey, String period) {
        HashMap<String, List<String>> valueMap = new HashMap<String, List<String>>();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        List<DataDimension> dataSchemeDimension = this.workflowReportDimService.getDataDimension(formScheme.getTaskKey());
        for (DataDimension dimension : dataSchemeDimension) {
            String entityKey = dimension.getDimKey();
            EntityViewDefine entityView = this.entityViewRunTimeController.buildEntityView(entityKey);
            String dimensionName = this.dimensionProvider.getDimensionNameByEntityId(entityKey);
            DimensionValueSet dimensionValueSet = new DimensionValueSet();
            dimensionValueSet.setValue("DATATIME", (Object)period);
            try {
                List keys;
                com.jiuqi.nr.entity.engine.executors.ExecutorContext executorContext = this.executorContext(formSchemeKey);
                IDataEntity iEntityTable = this.dataEntityService.getIEntityTable(entityView, executorContext, dimensionValueSet, formSchemeKey);
                DataEntityType type = iEntityTable.type();
                IDataEntityRow allRow = iEntityTable.getAllRow();
                if (allRow == null) continue;
                if (DataEntityType.DataEntity.equals((Object)type)) {
                    List rowList = allRow.getRowList();
                    keys = rowList.stream().map(e -> e.getEntityKeyData()).collect(Collectors.toList());
                    valueMap.put(dimensionName, keys);
                    continue;
                }
                if (!DataEntityType.DataEntityAdjust.equals((Object)type)) continue;
                List adjustPeriods = allRow.getAdjustPeriod();
                keys = adjustPeriods.stream().map(e -> e.getCode()).collect(Collectors.toList());
                if (!"ADJUST".equals(entityKey)) continue;
                valueMap.put(entityKey, keys);
            }
            catch (Exception e2) {
                logger.error(e2.getMessage(), e2);
            }
        }
        return valueMap;
    }

    public String getTableNameByEntityKey(String entityKey) {
        TableModelDefine entity = this.entityMetaService.getTableModel(entityKey);
        return entity.getName();
    }

    public String getTableNameByDimName(String dimensionName) {
        com.jiuqi.nr.entity.engine.executors.ExecutorContext context = new com.jiuqi.nr.entity.engine.executors.ExecutorContext(this.dataDefinitionRuntimeController);
        return this.dimensionProvider.getDimensionTableName((ExecutorContext)context, dimensionName);
    }

    private com.jiuqi.nr.entity.engine.executors.ExecutorContext executorContext(String formSchemeKey) {
        com.jiuqi.nr.entity.engine.executors.ExecutorContext context = new com.jiuqi.nr.entity.engine.executors.ExecutorContext(this.dataDefinitionRuntimeController);
        context.setVarDimensionValueSet(new DimensionValueSet());
        if (!StringUtils.isEmpty((String)formSchemeKey)) {
            context.setEnv((IFmlExecEnvironment)new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formSchemeKey));
        }
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        context.setPeriodView(formScheme.getDateTime());
        return context;
    }

    public List<Map<String, DimensionValue>> setReportDimensionToBusiness(String formSchemeKey, List<String> unitIds, String period, String adjust) {
        Map<String, List<String>> reportDimensionValue = this.getReportDimValueofTableName(formSchemeKey, period, adjust);
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        if (reportDimensionValue != null && reportDimensionValue.size() > 0) {
            for (Map.Entry<String, List<String>> value : reportDimensionValue.entrySet()) {
                dimensionValueSet.setValue(value.getKey(), value.getValue());
            }
        }
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        String tableName = this.getTableNameByEntityKey(formScheme.getDw());
        dimensionValueSet.setValue(tableName, unitIds);
        dimensionValueSet.setValue("DATATIME", (Object)period);
        Map dimensionSet = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet);
        List dimensionSetList = DimensionValueSetUtil.getDimensionSetList((Map)dimensionSet);
        return dimensionSetList;
    }

    private Map<String, List<String>> getReportDimValueofTableName(String formSchemeKey, String period, String adjust) {
        HashMap<String, List<String>> valueMap = new HashMap<String, List<String>>();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        List<DataDimension> dataSchemeDimension = this.workflowReportDimService.getDataDimension(formScheme.getTaskKey());
        for (DataDimension dimension : dataSchemeDimension) {
            String entityKey = dimension.getDimKey();
            EntityViewDefine entityView = this.entityViewRunTimeController.buildEntityView(entityKey);
            String tableName = "ADJUST";
            if (!"ADJUST".equals(entityKey)) {
                tableName = this.getTableNameByEntityKey(entityKey);
            }
            DimensionValueSet dimensionValueSet = new DimensionValueSet();
            dimensionValueSet.setValue("DATATIME", (Object)period);
            try {
                IDataEntity iEntityTable = this.dataEntityService.getIEntityTable(entityView, this.executorContext(formSchemeKey), dimensionValueSet, formSchemeKey);
                DataEntityType type = iEntityTable.type();
                IDataEntityRow allRow = iEntityTable.getAllRow();
                if (allRow == null) continue;
                if (DataEntityType.DataEntity.equals((Object)type)) {
                    List rowList = allRow.getRowList();
                    List keys = rowList.stream().map(e -> e.getEntityKeyData()).collect(Collectors.toList());
                    valueMap.put(tableName, keys);
                    continue;
                }
                if (!DataEntityType.DataEntityAdjust.equals((Object)type)) continue;
                ArrayList<String> keys = new ArrayList<String>();
                keys.add(adjust);
                valueMap.put(tableName, keys);
            }
            catch (Exception e2) {
                logger.error(e2.getMessage(), e2);
            }
        }
        return valueMap;
    }
}

