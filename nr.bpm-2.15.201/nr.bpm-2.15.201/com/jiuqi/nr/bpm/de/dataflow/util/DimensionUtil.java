/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.ContextExtension
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDimensionProvider
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.bpm.de.dataflow.util;

import com.jiuqi.np.core.context.ContextExtension;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDimensionProvider;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.businesskey.BusinessKeySet;
import com.jiuqi.nr.bpm.businesskey.BusinessKeySetInfo;
import com.jiuqi.nr.bpm.businesskey.MasterEntityInfo;
import com.jiuqi.nr.bpm.businesskey.MasterEntitySetInfo;
import com.jiuqi.nr.bpm.de.dataflow.util.IWorkFlowDimensionBuilder;
import com.jiuqi.nr.bpm.de.dataflow.util.ReportDimensionParam;
import com.jiuqi.nr.bpm.de.dataflow.util.WorkflowReportDimService;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DimensionUtil {
    private static final Logger logger = LoggerFactory.getLogger(DimensionUtil.class);
    private static final String PROCESS_KEY = "PROCESSKEY";
    @Autowired
    private NrParameterUtils nrParameterUtils;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IDimensionProvider iDimensionProvider;
    @Resource
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private ReportDimensionParam reportDimensionParam;
    @Autowired
    private WorkflowReportDimService workflowReportDimService;
    @Autowired
    private IWorkFlowDimensionBuilder workFlowDimensionBuilder;

    public DimensionValueSet fliterDimensionValueSet(DimensionValueSet dimensionValueSet, FormSchemeDefine formSchemeDefine) {
        DimensionValueSet dimension = new DimensionValueSet();
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formSchemeDefine.getTaskKey());
        List<DataDimension> dataSchemeDimension = this.workflowReportDimService.getDataDimension(formSchemeDefine.getTaskKey());
        IEntityModel dwEntityModel = this.entityMetaService.getEntityModel(taskDefine.getDw());
        String dwMainDimName = this.getDwMainDimName(formSchemeDefine.getKey());
        for (DataDimension dataDimension : dataSchemeDimension) {
            String dimKey = dataDimension.getDimKey();
            String dimensionName = this.getDimensionName(dimKey);
            Object value = dimensionValueSet.getValue(dimensionName);
            if (dwMainDimName.equals(dimensionName)) {
                dimension.setValue(dimensionName, value);
                continue;
            }
            if ("DATATIME".equals(dimensionName)) {
                dimension.setValue(dimensionName, value);
                continue;
            }
            if ("ADJUST".equals(dimensionName)) {
                dimension.setValue(dimensionName, value);
                continue;
            }
            if (!this.workFlowDimensionBuilder.isCorporate(taskDefine, dataDimension, dwEntityModel)) continue;
            dimension.setValue(dimensionName, value);
        }
        return dimension;
    }

    public List<DimensionValueSet> fliterDimensionValueSet(List<DimensionValueSet> dimensionValueSets, FormSchemeDefine formSchemeDefine) {
        ArrayList<DimensionValueSet> filterDims = new ArrayList<DimensionValueSet>();
        try {
            for (DimensionValueSet dim : dimensionValueSets) {
                DimensionValueSet dimension = this.fliterDimensionValueSet(dim, formSchemeDefine);
                filterDims.add(dimension);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return filterDims;
    }

    public List<DimensionValueSet> buildDimensionValueSets(DimensionValueSet dimensionValueSet, String formSchemeKey) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        DimensionValueSet fliterDimensionValueSet = this.fliterDimensionValueSet(dimensionValueSet, formScheme);
        return DimensionValueSetUtil.getDimensionSetList((DimensionValueSet)fliterDimensionValueSet);
    }

    public List<DimensionValueSet> buildDimensionValueSetsNofliter(DimensionValueSet dimensionValueSet, String formSchemeKey) {
        return DimensionValueSetUtil.getDimensionSetList((DimensionValueSet)dimensionValueSet);
    }

    public DimensionValueSet fliterProcessKey(DimensionValueSet dimensionValueSet, String formSchemeKey) {
        DimensionValueSet dimensionValue = new DimensionValueSet();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        DimensionValueSet fliterDimensionValueSet = this.fliterDimensionValueSet(dimensionValueSet, formScheme);
        if (fliterDimensionValueSet != null) {
            DimensionSet dimensionSet = fliterDimensionValueSet.getDimensionSet();
            int size = dimensionSet.size();
            for (int i = 0; i < size; ++i) {
                String dimenName = dimensionSet.get(i);
                Object value = fliterDimensionValueSet.getValue(dimenName);
                if (PROCESS_KEY.equals(dimenName) || "FORMID".equals(dimenName)) continue;
                dimensionValue.setValue(dimenName, value);
            }
        }
        return dimensionValue;
    }

    public DimensionValueSet mergeDimensionValueSet(List<DimensionValueSet> dimensions, String formSchemeKey) {
        DimensionValueSet mergeDimensionValueSet = DimensionValueSetUtil.mergeDimensionValueSet(dimensions);
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        return this.fliterDimensionValueSet(mergeDimensionValueSet, formScheme);
    }

    public DimensionValueSet buildDimension(BusinessKey businessKey) {
        DimensionValueSet convertDimensionName = this.nrParameterUtils.convertDimensionName(businessKey);
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(businessKey.getFormSchemeKey());
        return this.fliterDimensionValueSet(convertDimensionName, formScheme);
    }

    public DimensionValueSet buildDimension(BusinessKeySetInfo businessKey) {
        businessKey.getMasterEntitySetInfo().reset();
        ArrayList<DimensionValueSet> dims = new ArrayList<DimensionValueSet>();
        String period = businessKey.getPeriod();
        MasterEntitySetInfo masterEntitySetInfo = businessKey.getMasterEntitySetInfo();
        if (masterEntitySetInfo.next()) {
            MasterEntityInfo currentEntityInfo = masterEntitySetInfo.getCurrent();
            Collection<String> dimensionNames = currentEntityInfo.getDimessionNames();
            DimensionValueSet dim = new DimensionValueSet();
            for (String name : dimensionNames) {
                String masterEntityKey = currentEntityInfo.getMasterEntityKey(name);
                dim.setValue(name, (Object)masterEntityKey);
            }
            dim.setValue("DATATIME", (Object)period);
            dims.add(dim);
        }
        DimensionValueSet convertDimensionName = DimensionValueSetUtil.mergeDimensionValueSet(dims);
        return convertDimensionName;
    }

    public DimensionValueSet buildDimension(BusinessKeySet businessKey) {
        businessKey.getMasterEntitySet().reset();
        ArrayList<DimensionValueSet> dims = new ArrayList<DimensionValueSet>();
        String period = businessKey.getPeriod();
        MasterEntitySetInfo masterEntitySetInfo = businessKey.getMasterEntitySetInfo();
        if (masterEntitySetInfo.next()) {
            MasterEntityInfo currentEntityInfo = masterEntitySetInfo.getCurrent();
            Collection<String> dimensionNames = currentEntityInfo.getDimessionNames();
            for (String name : dimensionNames) {
                String masterEntityKey = currentEntityInfo.getMasterEntityKey(name);
                DimensionValueSet dim = new DimensionValueSet();
                String dimensionName = this.convertDimNameToTableName(name, businessKey.getFormSchemeKey());
                dim.setValue(dimensionName, (Object)masterEntityKey);
                dim.setValue("DATATIME", (Object)period);
                dims.add(dim);
            }
        }
        DimensionValueSet convertDimensionName = this.mergeDimensionValueSet(dims, businessKey.getFormSchemeKey());
        return convertDimensionName;
    }

    public DimensionValueSet buildUploadMasterKey(MasterEntityInfo masterEntity, String formKey, String period) {
        DimensionValueSet dimensionValueSet = this.nrParameterUtils.convertDimensionName(masterEntity, period);
        if (formKey != null) {
            dimensionValueSet.setValue("FORMID", (Object)formKey);
        }
        return dimensionValueSet;
    }

    public DimensionValueSet buildDimension(String formSchemeKey, Map<String, DimensionValue> dimensionSet) {
        DimensionValueSet dimension = DimensionValueSetUtil.getDimensionValueSet(dimensionSet);
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        return this.fliterDimensionValueSet(dimension, formScheme);
    }

    public Map<String, DimensionValue> getDimensionSet(DimensionValueSet dimensionValueSet, String formSchemeKey) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        DimensionValueSet fliterDimensionValueSet = this.fliterDimensionValueSet(dimensionValueSet, formScheme);
        return DimensionValueSetUtil.getDimensionSet((DimensionValueSet)fliterDimensionValueSet);
    }

    public DimensionValueSet buildDimension(String formSchemeKey, Set<String> unitIds, String period, String adjust, boolean all) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        String dwMainDimName = this.getDwMainDimName(formSchemeKey);
        if (all) {
            dimensionValueSet.setValue(dwMainDimName, (Object)"");
        } else if (unitIds != null && unitIds.size() > 0) {
            if (unitIds.size() == 1) {
                dimensionValueSet.setValue(dwMainDimName, (Object)unitIds.iterator().next());
            } else {
                dimensionValueSet.setValue(dwMainDimName, new ArrayList<String>(unitIds));
            }
        } else {
            dimensionValueSet.setValue(dwMainDimName, (Object)"");
        }
        dimensionValueSet.setValue("DATATIME", (Object)period);
        dimensionValueSet.setValue("ADJUST", (Object)adjust);
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        return this.fliterDimensionValueSet(dimensionValueSet, formScheme);
    }

    public List<DimensionValueSet> appendReportDimension(String formSchemeKey, String unitId, String period) {
        ArrayList<DimensionValueSet> dims = new ArrayList<DimensionValueSet>();
        ArrayList<String> unitIds = new ArrayList<String>();
        unitIds.add(unitId);
        List<Map<String, DimensionValue>> reportDimension = this.reportDimensionParam.setReportDimension(formSchemeKey, unitIds, period);
        for (Map<String, DimensionValue> map : reportDimension) {
            DimensionValueSet dimension = new DimensionValueSet();
            for (Map.Entry<String, DimensionValue> dim : map.entrySet()) {
                String key = dim.getKey();
                DimensionValue dimValue = dim.getValue();
                dimension.setValue(key, (Object)dimValue.getValue());
            }
            dims.add(dimension);
        }
        return dims;
    }

    public EntityViewDefine getEntityView(String entityKey) {
        try {
            return this.entityViewRunTimeController.buildEntityView(entityKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public EntityViewDefine getDwEntityView(String formSchemeKey) {
        try {
            return this.runTimeViewController.getViewByFormSchemeKey(formSchemeKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public String getDimensionName(EntityViewDefine viewDefine) {
        return this.getDimensionName(viewDefine.getEntityId());
    }

    public String getDwMainDimName(String formSchemeKey) {
        EntityViewDefine dwEntityView = this.runTimeViewController.getViewByFormSchemeKey(formSchemeKey);
        return this.getDimensionName(dwEntityView);
    }

    public String getDwMainDimNameByTaskKey(String taskKey) {
        EntityViewDefine dwEntityView = this.runTimeViewController.getViewByTaskDefineKey(taskKey);
        return this.getDimensionName(dwEntityView);
    }

    public String getDimensionName(String entityKey) {
        String dimensionName = null;
        ContextExtension extension = NpContextHolder.getContext().getExtension("WORKFLOW_BPM_DIMENISONNAME");
        Object object = extension.get(entityKey);
        if (object != null && !"".equals(object)) {
            dimensionName = object.toString();
            return dimensionName;
        }
        dimensionName = this.periodEntityAdapter.isPeriodEntity(entityKey) ? this.periodEntityAdapter.getPeriodDimensionName() : ("ADJUST".equals(entityKey) ? "ADJUST" : this.entityMetaService.getDimensionName(entityKey));
        extension.put(entityKey, (Serializable)((Object)dimensionName));
        return dimensionName;
    }

    public String getTableName(String entityKey) {
        TableModelDefine entity = this.entityMetaService.getTableModel(entityKey);
        return entity.getName();
    }

    public String getDwTableNameByFormSchemeKey(String formSchemeKey) {
        try {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            boolean enableTwoTree = this.workFlowDimensionBuilder.enableTwoTree(formScheme.getTaskKey());
            if (enableTwoTree) {
                TableModelDefine tableModel = this.entityMetaService.getTableModel("MD_ORG");
                return tableModel.getName();
            }
            String contextMainDimId = this.workFlowDimensionBuilder.getContextMainDimId(formScheme.getDw());
            TableModelDefine tableModel = this.entityMetaService.getTableModel(contextMainDimId);
            return tableModel.getName();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public TableModelDefine getDwTableByFormSchemeKey(String formSchemeKey) {
        try {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            boolean enableTwoTree = this.workFlowDimensionBuilder.enableTwoTree(formScheme.getTaskKey());
            if (enableTwoTree) {
                TableModelDefine tableModel = this.entityMetaService.getTableModel("MD_ORG");
                return tableModel;
            }
            String contextMainDimId = this.workFlowDimensionBuilder.getContextMainDimId(formScheme.getDw());
            TableModelDefine tableModel = this.entityMetaService.getTableModel(contextMainDimId);
            return tableModel;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("\u672a\u627e\u5230\u8868\u5b9a\u4e49!");
        }
    }

    public String convertDimNameToTableName(String dimensionName, String formSchemeKey) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        boolean enableTwoTree = this.workFlowDimensionBuilder.enableTwoTree(formScheme.getTaskKey());
        if (enableTwoTree) {
            return dimensionName;
        }
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        return this.iDimensionProvider.getDimensionNameByEntityTableCode(context, dimensionName);
    }

    public DimensionValueSet fliterProcessKey(DimensionValueSet dimensionValueSet, String formSchemeKey, IEntityModel dwEntityModel) {
        DimensionValueSet dimensionValue = new DimensionValueSet();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        DimensionValueSet fliterDimensionValueSet = this.fliterDimensionValueSet(dimensionValueSet, formScheme, dwEntityModel);
        if (fliterDimensionValueSet != null) {
            DimensionSet dimensionSet = fliterDimensionValueSet.getDimensionSet();
            int size = dimensionSet.size();
            for (int i = 0; i < size; ++i) {
                String dimenName = dimensionSet.get(i);
                Object value = fliterDimensionValueSet.getValue(dimenName);
                if (PROCESS_KEY.equals(dimenName) || "FORMID".equals(dimenName)) continue;
                dimensionValue.setValue(dimenName, value);
            }
        }
        return dimensionValue;
    }

    public DimensionValueSet fliterDimensionValueSet(DimensionValueSet dimensionValueSet, FormSchemeDefine formSchemeDefine, IEntityModel dwEntityModel) {
        DimensionValueSet dimension = new DimensionValueSet();
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formSchemeDefine.getTaskKey());
        List<DataDimension> dataSchemeDimension = this.workflowReportDimService.getDataDimension(formSchemeDefine.getTaskKey());
        String dwMainDimName = this.getDwMainDimName(formSchemeDefine.getKey());
        for (DataDimension dataDimension : dataSchemeDimension) {
            String dimKey = dataDimension.getDimKey();
            String dimensionName = this.getDimensionName(dimKey);
            Object value = dimensionValueSet.getValue(dimensionName);
            if (dwMainDimName.equals(dimensionName)) {
                dimension.setValue(dimensionName, value);
                continue;
            }
            if ("DATATIME".equals(dimensionName)) {
                dimension.setValue(dimensionName, value);
                continue;
            }
            if ("ADJUST".equals(dimensionName)) {
                dimension.setValue(dimensionName, value);
                continue;
            }
            if (!this.workFlowDimensionBuilder.isCorporate(taskDefine, dataDimension, dwEntityModel)) continue;
            dimension.setValue(dimensionName, value);
        }
        return dimension;
    }
}

