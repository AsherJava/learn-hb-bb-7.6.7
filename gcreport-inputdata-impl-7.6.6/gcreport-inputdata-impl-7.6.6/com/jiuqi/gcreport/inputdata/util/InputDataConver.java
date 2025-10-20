/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.definition.impl.basic.base.provider.EntityTableDeclarator
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.nr.vo.InputDataChangeMonitorEnvVo
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.dataengine.update.UpdateDataRecord
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.bean.DataEntryContext
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 */
package com.jiuqi.gcreport.inputdata.util;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.definition.impl.basic.base.provider.EntityTableDeclarator;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.inputdata.enums.InputDataCheckStateEnum;
import com.jiuqi.gcreport.inputdata.inputdata.enums.InputDataSrcTypeEnum;
import com.jiuqi.gcreport.inputdata.inputdata.enums.ReportOffsetStateEnum;
import com.jiuqi.gcreport.inputdata.inputdata.service.TemplateEntDaoCacheService;
import com.jiuqi.gcreport.inputdata.query.base.GcDataEntryContext;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.gcreport.nr.vo.InputDataChangeMonitorEnvVo;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.dataengine.update.UpdateDataRecord;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.DataEntryContext;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class InputDataConver {
    public static List<InputDataEO> converToInputItem(Collection<UpdateDataRecord> updateRecords, GcDataEntryContext envContext, InputDataChangeMonitorEnvVo inputDataChangeMonitorEnvVo) {
        ArrayList<InputDataEO> inputItems = new ArrayList<InputDataEO>();
        if (CollectionUtils.isEmpty(updateRecords)) {
            return inputItems;
        }
        String reportSystemId = InputDataConver.getReportSystemId(envContext.getFormSchemeKey(), envContext.getYearPeriod().toString());
        InputDataNameProvider inputDataNameProvider = (InputDataNameProvider)SpringContextUtils.getBean(InputDataNameProvider.class);
        TemplateEntDaoCacheService templateEntDaoCacheService = (TemplateEntDaoCacheService)SpringContextUtils.getBean(TemplateEntDaoCacheService.class);
        String tableName = inputDataNameProvider.getTableNameByTaskId(envContext.getTaskKey());
        EntNativeSqlDefaultDao<InputDataEO> inputDataDao = templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        return updateRecords.stream().map(updateRecord -> InputDataConver.converToInputItem(updateRecord, envContext, reportSystemId, inputDataChangeMonitorEnvVo, inputDataDao)).collect(Collectors.toList());
    }

    private static InputDataEO converToInputItem(UpdateDataRecord updateRecord, DataEntryContext envContext, String reportSystemId, InputDataChangeMonitorEnvVo inputDataChangeMonitorEnvVo, EntNativeSqlDefaultDao<InputDataEO> inputDataDao) {
        Map<String, String> dimFieldValueMap = InputDataConver.getDimFieldValueMap(DimensionValueSetUtil.getDimensionSet((DimensionValueSet)updateRecord.getRowkeys()), envContext.getTaskKey());
        InputDataEO inputItem = InputDataConver.getItem(updateRecord, dimFieldValueMap, inputDataDao);
        InputDataConver.setCalcField(inputItem, envContext, reportSystemId, inputDataChangeMonitorEnvVo);
        return inputItem;
    }

    public static Map<String, List<InputDataEO>> converToInputItemGroupByOrgIds(Collection<UpdateDataRecord> updateRecords, GcDataEntryContext envContext, InputDataChangeMonitorEnvVo inputDataChangeMonitorEnvVo, Map<String, Map<String, DimensionValue>> dimFieldValueGroupByOrgId) {
        HashMap<String, List<InputDataEO>> inputItemGroupByOrgId = new HashMap<String, List<InputDataEO>>();
        if (CollectionUtils.isEmpty(updateRecords)) {
            return inputItemGroupByOrgId;
        }
        String reportSystemId = InputDataConver.getReportSystemId(envContext.getFormSchemeKey(), envContext.getYearPeriod().toString());
        InputDataNameProvider inputDataNameProvider = (InputDataNameProvider)SpringContextUtils.getBean(InputDataNameProvider.class);
        String tableName = inputDataNameProvider.getTableNameByTaskId(envContext.getTaskKey());
        TemplateEntDaoCacheService templateEntDaoCacheService = (TemplateEntDaoCacheService)SpringContextUtils.getBean(TemplateEntDaoCacheService.class);
        EntNativeSqlDefaultDao<InputDataEO> inputDataDao = templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        for (UpdateDataRecord updateDataRecord : updateRecords) {
            String unitCode = (String)updateDataRecord.getRowkeys().getValue("MD_ORG");
            Map<String, String> dimFieldValueMap = InputDataConver.getDimFieldValueMapByRowKeys(updateDataRecord.getRowkeys(), tableName);
            InputDataEO inputItem = InputDataConver.getItem(updateDataRecord, dimFieldValueMap, inputDataDao);
            InputDataConver.setCalcField(inputItem, envContext, reportSystemId, inputDataChangeMonitorEnvVo);
            if (inputItemGroupByOrgId.keySet().contains(unitCode)) {
                ((List)inputItemGroupByOrgId.get(unitCode)).add(inputItem);
                continue;
            }
            ArrayList<InputDataEO> inputDatas = new ArrayList<InputDataEO>();
            inputDatas.add(inputItem);
            inputItemGroupByOrgId.put(unitCode, inputDatas);
            Map dimensionValue = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)updateDataRecord.getRowkeys());
            dimFieldValueGroupByOrgId.put(unitCode, dimensionValue);
        }
        return inputItemGroupByOrgId;
    }

    private static String getReportSystemId(String schemeId, String periodStr) {
        ConsolidatedTaskService taskService = (ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class);
        return taskService.getSystemIdBySchemeId(schemeId, periodStr);
    }

    public static Map<String, String> getDimFieldValueMapByRowKeys(DimensionValueSet rowKeys, String tableName) {
        IDataAccessProvider dataAccessProvider = (IDataAccessProvider)SpringContextUtils.getBean(IDataAccessProvider.class);
        IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)SpringContextUtils.getBean(IDataDefinitionRuntimeController.class);
        IDataAssist newDataAssist = dataAccessProvider.newDataAssist(new ExecutorContext(dataDefinitionRuntimeController));
        Map dimension = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)rowKeys);
        HashMap<String, String> dimFieldValueMap = new HashMap<String, String>();
        for (Map.Entry e : dimension.entrySet()) {
            FieldDefine dimensionField = newDataAssist.getDimensionField(tableName, (String)e.getKey());
            if (dimensionField == null) continue;
            String dimValue = ((DimensionValue)e.getValue()).getValue();
            if (dimValue instanceof String) {
                dimFieldValueMap.put(dimensionField.getCode(), String.valueOf(dimValue));
                continue;
            }
            if (!(dimValue instanceof List)) continue;
            dimFieldValueMap.put(dimensionField.getCode(), String.valueOf(((List)((Object)dimValue)).get(0)));
        }
        return dimFieldValueMap;
    }

    public static Map<String, String> getDimFieldValueMap(Map<String, DimensionValue> dimension, String taskId) {
        IDataAccessProvider dataAccessProvider = (IDataAccessProvider)SpringContextUtils.getBean(IDataAccessProvider.class);
        IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)SpringContextUtils.getBean(IDataDefinitionRuntimeController.class);
        IDataAssist newDataAssist = dataAccessProvider.newDataAssist(new ExecutorContext(dataDefinitionRuntimeController));
        InputDataNameProvider inputDataNameProvider = (InputDataNameProvider)SpringContextUtils.getBean(InputDataNameProvider.class);
        String tableName = inputDataNameProvider.getTableNameByTaskId(taskId);
        HashMap<String, String> dimFieldValueMap = new HashMap<String, String>();
        for (Map.Entry<String, DimensionValue> e : dimension.entrySet()) {
            FieldDefine dimensionField = null;
            try {
                dimensionField = newDataAssist.getDimensionField(tableName, e.getKey());
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            if (dimensionField == null) continue;
            dimFieldValueMap.put(dimensionField.getCode(), e.getValue().getValue());
        }
        return dimFieldValueMap;
    }

    private static InputDataEO getItem(UpdateDataRecord updateRecord, Map<String, String> dimFieldValueMap, EntNativeSqlDefaultDao<InputDataEO> inputDataDao) {
        String bizkeyorder;
        InputDataEO inputItem = new InputDataEO();
        try {
            bizkeyorder = updateRecord.getRowkeys().getValue("RECORDKEY").toString();
        }
        catch (NullPointerException e) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.notbizkeyorderemptymsg"));
        }
        inputItem.addFieldValue("BIZKEYORDER", bizkeyorder);
        EntityTableDeclarator handle = inputDataDao.getTableDeclarator();
        updateRecord.getUpdateColumns().stream().forEach(updateColumn -> {
            Object value = updateColumn.getValue();
            switch (updateColumn.getName()) {
                case "AMT": 
                case "OFFSETAMT": 
                case "DIFFAMT": {
                    value = ConverterUtils.getAsDouble((Object)value, (Double)0.0);
                    break;
                }
            }
            handle.setValue((Object)inputItem, updateColumn.getName(), value);
        });
        dimFieldValueMap.forEach((key, value) -> handle.setValue((Object)inputItem, key, value));
        GcBaseData subject = GcBaseDataCenterTool.getInstance().queryBasedataByObjCode("MD_GCSUBJECT", inputItem.getSubjectObjCode());
        handle.setValue((Object)inputItem, "SUBJECTCODE", (Object)subject.getCode());
        handle.setValue((Object)inputItem, "ORGCODE", (Object)dimFieldValueMap.get("MDCODE"));
        return inputItem;
    }

    private static void setCalcField(InputDataEO inputItem, DataEntryContext envContext, String reportSystemId, InputDataChangeMonitorEnvVo inputDataChangeMonitorEnvVo) {
        String bizKeyOrder = (String)inputItem.getFields().get("BIZKEYORDER");
        inputItem.setId(bizKeyOrder);
        inputItem.setSrcType(InputDataSrcTypeEnum.MANUALENTRY.getValue());
        String taskId = envContext.getTaskKey();
        inputItem.setTaskId(taskId);
        inputItem.setFormId(envContext.getFormKey());
        inputItem.setReportSystemId(reportSystemId);
        inputItem.setCreateUser(NpContextHolder.getContext().getUser() == null ? "" : NpContextHolder.getContext().getUser().getName());
        inputItem.setCreateTime(new Date());
        inputItem.setCheckState(InputDataCheckStateEnum.NOTCHECK.getValue());
        if (inputDataChangeMonitorEnvVo.isCalcField()) {
            inputItem.setOffsetState(ReportOffsetStateEnum.NOTOFFSET.getValue());
            inputItem.setCheckGroupId(null);
            if (inputItem.getAmt() == null) {
                inputItem.setAmt(0.0);
            }
            inputItem.setOffsetAmt(0.0);
            inputItem.setDiffAmt(0.0);
        }
    }
}

