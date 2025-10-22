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
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.util.DimensionValueSetUtil
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
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
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.businesskey.BusinessKeyImpl;
import com.jiuqi.nr.bpm.businesskey.BusinessKeySet;
import com.jiuqi.nr.bpm.businesskey.BusinessKeySetImpl;
import com.jiuqi.nr.bpm.businesskey.MasterEntityImpl;
import com.jiuqi.nr.bpm.businesskey.MasterEntitySetImpl;
import com.jiuqi.nr.bpm.de.dataflow.util.CommonUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.IWorkFlowDimensionBuilder;
import com.jiuqi.nr.bpm.de.dataflow.util.WorkflowReportDimService;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.util.DimensionValueSetUtil;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BusinessGenerator {
    private static final Logger logger = LoggerFactory.getLogger(BusinessGenerator.class);
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IDimensionProvider dimensionProvider;
    @Autowired
    private DimensionUtil dimensionUtil;
    @Autowired
    private IDataDefinitionRuntimeController dataRunTimeController;
    @Resource
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private WorkflowReportDimService workflowReportDimService;
    @Autowired
    private IWorkFlowDimensionBuilder workFlowDimensionBuilder;

    public BusinessKey buildBusinessKey(String formSchemKey, DimensionValueSet dimSet, String formKey, String groupKey) {
        boolean isPrimary = this.commonUtil.checkFormIdIsPrimaryKey(formSchemKey);
        return this.buildBusinessKey(formSchemKey, dimSet, formKey, groupKey, isPrimary);
    }

    public BusinessKeySet buildBusinessKeySet(String formSchemKey, List<DimensionValueSet> dimSets, Set<String> formKeys, Set<String> formGroupKeys) {
        boolean isPrimary = this.commonUtil.checkFormIdIsPrimaryKey(formSchemKey);
        return this.buildBusinessKeySet(formSchemKey, dimSets, formKeys, formGroupKeys, isPrimary);
    }

    public BusinessKey buildBusinessKey(String formSchemeKey, DimensionValueSet dimSet, String formKey, String groupKey, boolean isPrimary) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        WorkFlowType startType = formScheme.getFlowsSetting().getWordFlowType();
        BusinessKeyImpl businessKey = new BusinessKeyImpl();
        if (isPrimary) {
            String formOrGroupKey = startType == WorkFlowType.FORM ? formKey : (startType == WorkFlowType.GROUP ? groupKey : "00000000-0000-0000-0000-000000000000");
            businessKey.setFormKey(formOrGroupKey);
        }
        businessKey.setFormSchemeKey(formSchemeKey);
        MasterEntityImpl masterEntity = new MasterEntityImpl();
        businessKey.setMasterEntity(masterEntity);
        try {
            List<DataDimension> dataSchemeDimension = this.getDataDimensions(formScheme.getTaskKey());
            for (DataDimension dimension : dataSchemeDimension) {
                String entityKey = dimension.getDimKey();
                if (this.periodEntityAdapter.isPeriodEntity(entityKey)) {
                    Object period = dimSet.getValue("DATATIME");
                    businessKey.setPeriod(period.toString());
                    continue;
                }
                String dimensionName = this.dimensionUtil.getDimensionName(entityKey);
                Object value = dimSet.getValue(dimensionName);
                if ("ADJUST".equals(entityKey)) {
                    if (value == null) continue;
                    masterEntity.setMasterEntityDimessionValue(entityKey, value.toString());
                    continue;
                }
                String tableName = this.getTableName(formSchemeKey, dimensionName);
                if (value == null) continue;
                masterEntity.setMasterEntityDimessionValue(tableName, value.toString());
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return businessKey;
    }

    private BusinessKeySet buildBusinessKeySet(String formSchemeKey, List<DimensionValueSet> dimSets, Set<String> formKeys, Set<String> formGroupKeys, boolean isPrimary) {
        BusinessKeySetImpl businessKeySet = new BusinessKeySetImpl();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        WorkFlowType startType = formScheme.getFlowsSetting().getWordFlowType();
        if (isPrimary) {
            HashSet<String> formOrGroupKeys = startType == WorkFlowType.FORM ? formKeys : (startType == WorkFlowType.GROUP ? formGroupKeys : new HashSet<String>(Arrays.asList("00000000-0000-0000-0000-000000000000")));
            businessKeySet.setFormKey((Set<String>)formOrGroupKeys);
        }
        businessKeySet.setFormSchemeKey(formSchemeKey);
        MasterEntitySetImpl masterEntitySetInfo = new MasterEntitySetImpl();
        List<DataDimension> dataSchemeDimension = this.getDataDimensions(formScheme.getTaskKey());
        try {
            for (DimensionValueSet dimensionValueSet : dimSets) {
                MasterEntityImpl masterEntity = new MasterEntityImpl();
                for (DataDimension dimension : dataSchemeDimension) {
                    String entityKey = dimension.getDimKey();
                    if (this.periodEntityAdapter.isPeriodEntity(entityKey)) continue;
                    String dimensionName = this.dimensionUtil.getDimensionName(entityKey);
                    Object value = dimensionValueSet.getValue(dimensionName);
                    if ("ADJUST".equals(entityKey)) {
                        masterEntity.setMasterEntityDimessionValue(entityKey, value.toString());
                        continue;
                    }
                    String tableName = this.getTableName(formSchemeKey, dimensionName);
                    if (null == value) continue;
                    masterEntity.setMasterEntityDimessionValue(tableName, value.toString());
                }
                masterEntitySetInfo.addMasterEntity(masterEntity);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        businessKeySet.setMasterEntitySet(masterEntitySetInfo);
        businessKeySet.setPeriod(((DimensionValueSet)dimSets.stream().findAny().get()).getValue("DATATIME").toString());
        return businessKeySet;
    }

    public List<BusinessKey> buildBusinessKey(String formSchemeKey, String unitId, String period, String formKey, String adjust, String dims) {
        ArrayList<BusinessKey> businessKeys = new ArrayList<BusinessKey>();
        boolean isPrimary = this.commonUtil.checkFormIdIsPrimaryKey(formSchemeKey);
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formSchemeDefine.getTaskKey());
        IEntityModel dwEntityModel = this.entityMetaService.getEntityModel(taskDefine.getDw());
        List<DataDimension> reportDimensions = this.getReportDimensions(formSchemeDefine.getTaskKey());
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        TableModelDefine entity = this.entityMetaService.getTableModel(formSchemeDefine.getDw());
        String tableName = entity.getName();
        dimensionValueSet.setValue(tableName, (Object)unitId);
        dimensionValueSet.setValue("DATATIME", (Object)period);
        for (DataDimension reportDimension : reportDimensions) {
            String dimKey = reportDimension.getDimKey();
            String entityKey = reportDimension.getDimKey();
            if ("ADJUST".equals(entityKey)) {
                dimensionValueSet.setValue(dimKey, (Object)adjust);
            }
            if (!this.workFlowDimensionBuilder.isCorporate(taskDefine, reportDimension, dwEntityModel)) continue;
            dimensionValueSet.setValue(dimKey, (Object)dims);
        }
        Map dimensionValueMap = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet);
        List dimensionSetList = DimensionValueSetUtil.getDimensionSetList((Map)dimensionValueMap);
        for (Map dimensionMap : dimensionSetList) {
            for (Map.Entry dimension : dimensionMap.entrySet()) {
                String dimKey = (String)dimension.getKey();
                DimensionValue value = (DimensionValue)dimension.getValue();
                BusinessKeyImpl businessKey = new BusinessKeyImpl();
                businessKey.setFormSchemeKey(formSchemeKey);
                if (isPrimary) {
                    businessKey.setFormKey(formKey);
                }
                MasterEntityImpl masterEntity = new MasterEntityImpl();
                businessKey.setMasterEntity(masterEntity);
                if (!"DATATIME".equals(dimKey)) {
                    masterEntity.setMasterEntityDimessionValue(dimKey, value.getValue());
                }
                businessKeys.add(businessKey);
            }
        }
        return businessKeys;
    }

    public List<BusinessKey> buildBusinessKey(String formSchemeKey, List<String> unitIds, String period, List<String> formKeys, String adjust, String dims) {
        ArrayList<BusinessKey> businessKeys = new ArrayList<BusinessKey>();
        boolean isPrimary = this.commonUtil.checkFormIdIsPrimaryKey(formSchemeKey);
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formSchemeDefine.getTaskKey());
        IEntityModel dwEntityModel = this.entityMetaService.getEntityModel(taskDefine.getDw());
        List<DataDimension> reportDimensions = this.getReportDimensions(formSchemeDefine.getTaskKey());
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        TableModelDefine entity = this.entityMetaService.getTableModel(formSchemeDefine.getDw());
        String tableName = entity.getName();
        dimensionValueSet.setValue(tableName, unitIds);
        dimensionValueSet.setValue("DATATIME", (Object)period);
        for (DataDimension reportDimension : reportDimensions) {
            String dimKey = reportDimension.getDimKey();
            String entityKey = reportDimension.getDimKey();
            if ("ADJUST".equals(entityKey)) {
                dimensionValueSet.setValue(dimKey, (Object)adjust);
            }
            if (!this.workFlowDimensionBuilder.isCorporate(taskDefine, reportDimension, dwEntityModel)) continue;
            dimensionValueSet.setValue(dimKey, (Object)dims);
        }
        Map dimensionValueMap = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet);
        List dimensionSetList = DimensionValueSetUtil.getDimensionSetList((Map)dimensionValueMap);
        for (Map dimensionMap : dimensionSetList) {
            for (Map.Entry dimension : dimensionMap.entrySet()) {
                String dimKey = (String)dimension.getKey();
                DimensionValue value = (DimensionValue)dimension.getValue();
                if (formKeys != null && formKeys.size() > 0) {
                    for (String formKey : formKeys) {
                        BusinessKeyImpl businessKey = new BusinessKeyImpl();
                        businessKey.setFormSchemeKey(formSchemeKey);
                        if (isPrimary) {
                            businessKey.setFormKey(formKey);
                        }
                        MasterEntityImpl masterEntity = new MasterEntityImpl();
                        businessKey.setMasterEntity(masterEntity);
                        if (!"DATATIME".equals(dimKey)) {
                            masterEntity.setMasterEntityDimessionValue(dimKey, value.getValue());
                        }
                        businessKeys.add(businessKey);
                    }
                    continue;
                }
                BusinessKeyImpl businessKey = new BusinessKeyImpl();
                businessKey.setFormSchemeKey(formSchemeKey);
                MasterEntityImpl masterEntity = new MasterEntityImpl();
                businessKey.setMasterEntity(masterEntity);
                if (!"DATATIME".equals(dimKey)) {
                    masterEntity.setMasterEntityDimessionValue(dimKey, value.getValue());
                }
                businessKeys.add(businessKey);
            }
        }
        return businessKeys;
    }

    public String getTableName(String formSchemeKey, String dimensionName) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        boolean enableTwoTree = this.workFlowDimensionBuilder.enableTwoTree(formScheme.getTaskKey());
        if (enableTwoTree) {
            return dimensionName;
        }
        com.jiuqi.nr.entity.engine.executors.ExecutorContext context = new com.jiuqi.nr.entity.engine.executors.ExecutorContext(this.dataRunTimeController);
        if (!StringUtils.isEmpty((String)formSchemeKey)) {
            context.setEnv((IFmlExecEnvironment)new ReportFmlExecEnvironment(this.runTimeViewController, this.dataRunTimeController, this.entityViewRunTimeController, formSchemeKey));
        }
        String tableName = this.dimensionProvider.getDimensionTableName((ExecutorContext)context, dimensionName);
        return tableName;
    }

    private List<DataDimension> getDataDimensions(String taskKey) {
        return this.workflowReportDimService.getDataDimension(taskKey);
    }

    private List<DataDimension> getReportDimensions(String taskKey) {
        return this.workflowReportDimService.getReportDimension(taskKey);
    }
}

