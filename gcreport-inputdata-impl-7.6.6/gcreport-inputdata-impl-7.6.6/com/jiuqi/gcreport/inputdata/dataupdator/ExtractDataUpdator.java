/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.exception.IncorrectQueryException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.intf.IDataUpdator
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.efdc.extract.ExtractDataRow
 *  com.jiuqi.nr.efdc.extract.IExtractDataUpdator
 *  com.jiuqi.nr.efdc.extract.IExtractResult
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.output.EntityData
 *  com.jiuqi.nr.jtable.service.IJtableEntityQueryService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.jtable.util.EntityDataLoader
 */
package com.jiuqi.gcreport.inputdata.dataupdator;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.formsetting.service.OffsetDimSettingService;
import com.jiuqi.gcreport.inputdata.util.InputDataConver;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.exception.IncorrectQueryException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.IDataUpdator;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.efdc.extract.ExtractDataRow;
import com.jiuqi.nr.efdc.extract.IExtractDataUpdator;
import com.jiuqi.nr.efdc.extract.IExtractResult;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.service.IJtableEntityQueryService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.jtable.util.EntityDataLoader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Component
public class ExtractDataUpdator
implements IExtractDataUpdator {
    private OffsetDimSettingService offsetDimSettingService;
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    private IJtableParamService jtableParamService;
    private IEntityMetaService entityMetaService;
    private InputDataNameProvider inputDataNameProvider;
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    private IJtableEntityQueryService jtableEntityQueryService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ExtractDataUpdator.class);
    private static final String PLACEHOLDER_VALUE = "---";

    public ExtractDataUpdator(OffsetDimSettingService offsetDimSettingService, IDataDefinitionRuntimeController dataDefinitionRuntimeController, IJtableParamService jtableParamService, IEntityMetaService entityMetaService, InputDataNameProvider inputDataNameProvider, IRuntimeDataSchemeService runtimeDataSchemeService, IJtableEntityQueryService jtableEntityQueryService) {
        this.offsetDimSettingService = offsetDimSettingService;
        this.dataDefinitionRuntimeController = dataDefinitionRuntimeController;
        this.jtableParamService = jtableParamService;
        this.entityMetaService = entityMetaService;
        this.inputDataNameProvider = inputDataNameProvider;
        this.runtimeDataSchemeService = runtimeDataSchemeService;
        this.jtableEntityQueryService = jtableEntityQueryService;
    }

    public boolean changeData(IDataTable dataTable, IDataQuery dataQuery, ExecutorContext executorContext, IExtractResult fetchResult, DimensionValueSet dimensionValueSet, String formKey, String regionKey) {
        String taskId;
        ReportFmlExecEnvironment reportFmlExecEnvironment = (ReportFmlExecEnvironment)executorContext.getEnv();
        try {
            taskId = reportFmlExecEnvironment.getTaskDefine().getKey();
        }
        catch (Exception e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
        if (dataTable.getFieldsInfo().getFieldCount() <= 0) {
            return false;
        }
        FieldDefine fieldDefine = dataTable.getFieldsInfo().getFieldDefine(0);
        DataTable tableDefnie = this.runtimeDataSchemeService.getDataTable(fieldDefine.getOwnerTableKey());
        if (Objects.isNull(tableDefnie) || !tableDefnie.getCode().contains("GC_INPUTDATA")) {
            return false;
        }
        String inputTableName = this.inputDataNameProvider.getTableNameByTaskId(taskId);
        String tableName = this.inputDataNameProvider.getInputDataTableNameByDataTableKey(tableDefnie.getKey());
        if (ObjectUtils.isEmpty(inputTableName) || !inputTableName.equals(tableName)) {
            return false;
        }
        try {
            String formSchemeKey = reportFmlExecEnvironment.getFormSchemeKey();
            FieldDefine inputOrderField = this.getInputOrderField(dataTable);
            if (dataTable.getCount() <= 0) {
                this.addAll(dataTable, fetchResult, inputOrderField, dimensionValueSet, formSchemeKey, taskId);
            } else if (fetchResult.size() <= 0) {
                dataTable.deleteAll();
            } else {
                String systemId = ((ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class)).getSystemIdBySchemeId(formSchemeKey, (String)dimensionValueSet.getValue("DATATIME"));
                if (StringUtils.isEmpty(systemId)) {
                    throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.batchoffset.notsystemerrormsg"));
                }
                this.compareAndAdd(dataTable, fetchResult, inputOrderField, formKey, dataQuery.openForUpdate(executorContext), dimensionValueSet, taskId, systemId, formSchemeKey);
            }
            return true;
        }
        catch (Exception e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
    }

    private FieldDefine getInputOrderField(IDataTable dataTable) {
        for (int index = 0; index < dataTable.getFieldsInfo().getFieldCount(); ++index) {
            if (!"FLOATORDER".equals(dataTable.getFieldsInfo().getFieldDefine(index).getCode())) continue;
            return dataTable.getFieldsInfo().getFieldDefine(index);
        }
        return null;
    }

    private void addAll(IDataTable dataTable, IExtractResult fetchResult, FieldDefine inputOrderField, DimensionValueSet dimensionValueSet, String formSchemeKey, String taskId) throws Exception {
        if (fetchResult.size() <= 0) {
            return;
        }
        for (int i = 0; i < fetchResult.size(); ++i) {
            IDataRow destDataRow = this.appendRow(dataTable, dimensionValueSet, fetchResult.getRow(i), formSchemeKey, taskId);
            if (!Objects.nonNull(inputOrderField) || !Objects.nonNull(destDataRow)) continue;
            int floatorder = (i + 1) * 1000;
            destDataRow.setValue(inputOrderField, (Object)floatorder);
        }
    }

    private IDataRow appendRow(IDataTable dataTable, DimensionValueSet dimensionValueSet, ExtractDataRow srcDataRow, String formSchemeKey, String taskId) throws IncorrectQueryException {
        if (!this.checkRowData(dataTable, dimensionValueSet, srcDataRow, formSchemeKey, taskId)) {
            return null;
        }
        DimensionValueSet rowKey = new DimensionValueSet(dimensionValueSet);
        rowKey.setValue("RECORDKEY", (Object)UUIDUtils.newUUIDStr());
        IDataRow destDataRow = dataTable.appendRow(rowKey);
        for (int fieldIndex = 0; fieldIndex < srcDataRow.getFieldSize(); ++fieldIndex) {
            Object value = srcDataRow.getValue(fieldIndex);
            FieldDefine fieldDefine = destDataRow.getFieldsInfo().getFieldDefine(fieldIndex);
            if (StringUtils.hasText(fieldDefine.getEntityKey()) && null != value) {
                Object valueObj = this.getIsolationValue(fieldDefine, value, dimensionValueSet, taskId, formSchemeKey);
                value = Objects.nonNull(valueObj) ? valueObj : value;
            }
            destDataRow.setValue(fieldIndex, value);
        }
        return destDataRow;
    }

    private boolean checkRowData(IDataTable dataTable, DimensionValueSet dimensionValueSet, ExtractDataRow srcDataRow, String formSchemeKey, String taskId) {
        for (int fieldIndex = 0; fieldIndex < srcDataRow.getFieldSize(); ++fieldIndex) {
            Object entityDefineValue;
            Object value = srcDataRow.getValue(fieldIndex);
            FieldDefine fieldDefine = dataTable.getFieldsInfo().getFieldDefine(fieldIndex);
            if (!StringUtils.hasText(fieldDefine.getEntityKey()) || !"ORGCODE".equalsIgnoreCase(fieldDefine.getCode()) && !"OPPUNITID".equalsIgnoreCase(fieldDefine.getCode()) && !"SUBJECTOBJ".equalsIgnoreCase(fieldDefine.getCode()) || !Objects.isNull(entityDefineValue = this.getEntityDefineValue(taskId, formSchemeKey, fieldDefine, dimensionValueSet, value))) continue;
            return false;
        }
        return true;
    }

    private void compareAndAdd(IDataTable dataTable, IExtractResult fetchResult, FieldDefine inputOrderField, String formId, IDataUpdator tempTable, DimensionValueSet dimensionValueSet, String taskId, String systemId, String formSchemeKey) throws Exception {
        InputDataNameProvider inputDataNameProvider = (InputDataNameProvider)SpringContextUtils.getBean(InputDataNameProvider.class);
        String inputTableCode = inputDataNameProvider.getTableCodeByTaskId(taskId);
        Set<FieldDefine> offsetFields = this.getOffsetFields(formId, systemId, inputTableCode);
        LinkedMultiValueMap<String, IDataRow> oldRecordMapping = new LinkedMultiValueMap<String, IDataRow>();
        ArrayList<String> bizKeyOrders = new ArrayList<String>();
        for (int index = 0; index < dataTable.getCount(); ++index) {
            IDataRow dataRow = dataTable.getItem(index);
            bizKeyOrders.add(dataRow.getRowKeys().getValue("RECORDKEY").toString());
            String recordKey = this.getRecordKey(dataRow, offsetFields, dimensionValueSet, taskId, formSchemeKey);
            oldRecordMapping.add(recordKey, dataRow);
        }
        int insertFloatOrder = (dataTable.getCount() + 1) * 1000;
        for (int resultIndex = 0; resultIndex < fetchResult.size(); ++resultIndex) {
            IDataRow dataRowTemp = tempTable.addInsertedRow();
            ExtractDataRow srcDataRow = fetchResult.getRow(resultIndex);
            for (int fieldIndex = 0; fieldIndex < srcDataRow.getFieldSize(); ++fieldIndex) {
                dataRowTemp.setValue(fieldIndex, srcDataRow.getValue(fieldIndex));
            }
            String recordKey = this.getRecordKey(dataRowTemp, offsetFields, dimensionValueSet, taskId, formSchemeKey);
            List matchedHisRecords = (List)oldRecordMapping.get(recordKey);
            if (!CollectionUtils.isEmpty(matchedHisRecords)) {
                ((List)oldRecordMapping.get(recordKey)).remove(0);
                if (!CollectionUtils.isEmpty((Collection)oldRecordMapping.get(recordKey))) continue;
                oldRecordMapping.remove(recordKey);
                continue;
            }
            IDataRow destDataRow = this.appendRow(dataTable, dimensionValueSet, srcDataRow, formSchemeKey, taskId);
            if (!Objects.nonNull(inputOrderField) || !Objects.nonNull(destDataRow)) continue;
            destDataRow.setValue(inputOrderField, (Object)insertFloatOrder);
            insertFloatOrder += 1000;
        }
        for (List needDeleteRecords : oldRecordMapping.values()) {
            for (IDataRow dataRow : needDeleteRecords) {
                dataTable.deleteRow(dataRow.getRowKeys());
            }
        }
    }

    private Set<FieldDefine> getOffsetFields(String formId, String systemId, String tableCode) throws Exception {
        Set<String> offsetFieldCodes = this.offsetDimSettingService.getDimsByFormId(formId, systemId);
        String inputDataTableDefineId = this.dataDefinitionRuntimeController.queryTableDefineByCode(tableCode).getKey();
        HashSet<FieldDefine> allFields = new HashSet<FieldDefine>();
        for (String offsetFieldCode : offsetFieldCodes) {
            if ("ORGCODE".equals(offsetFieldCode)) {
                offsetFieldCode = "MDCODE";
            } else if ("SUBJECTCODE".equals(offsetFieldCode)) {
                offsetFieldCode = "SUBJECTOBJ";
            }
            FieldDefine fieldDefine = this.dataDefinitionRuntimeController.queryFieldByCodeInTable(offsetFieldCode, inputDataTableDefineId);
            if (fieldDefine == null) {
                Object[] args = new String[]{offsetFieldCode};
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.notdimensionfieldsemptymsg", (Object[])args));
            }
            allFields.add(fieldDefine);
        }
        return allFields;
    }

    private String getRecordKey(IDataRow dataRow, Set<FieldDefine> offsetFields, DimensionValueSet dimensionValueSet, String taskId, String formSchemeKey) throws DataTypeException {
        StringBuilder builder = new StringBuilder();
        Map<String, String> dimFieldValueGroupByCode = InputDataConver.getDimFieldValueMap(DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet), taskId);
        for (FieldDefine offsetField : offsetFields) {
            String fieldValue;
            int fieldIndex = dataRow.getFieldsInfo().indexOf(offsetField);
            if (!dimFieldValueGroupByCode.isEmpty() && dimFieldValueGroupByCode.containsKey(offsetField.getCode())) {
                fieldValue = String.valueOf(dimFieldValueGroupByCode.get(offsetField.getCode()));
            } else if (fieldIndex < 0) {
                fieldValue = PLACEHOLDER_VALUE;
            } else {
                fieldValue = dataRow.getAsString(offsetField);
                if (StringUtils.hasText(offsetField.getEntityKey()) && StringUtils.hasText(fieldValue)) {
                    Object fieldValueObj = this.getIsolationValue(offsetField, fieldValue, dimensionValueSet, taskId, formSchemeKey);
                    fieldValue = String.valueOf(fieldValueObj);
                }
                fieldValue = StringUtils.isEmpty(fieldValue) ? PLACEHOLDER_VALUE : fieldValue;
            }
            builder.append(fieldValue);
        }
        return DigestUtils.md5DigestAsHex(builder.toString().getBytes(Charset.defaultCharset()));
    }

    private Object getIsolationValue(FieldDefine fieldDefine, Object value, DimensionValueSet dimensionValueSet, String taskId, String formSchemeKey) {
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(fieldDefine.getEntityKey());
        if (null != entityDefine && 0 != entityDefine.getIsolation()) {
            return this.getEntityDefineValue(taskId, formSchemeKey, fieldDefine, dimensionValueSet, value);
        }
        return value;
    }

    private Object getEntityDefineValue(String taskId, String formSchemeKey, FieldDefine fieldDefine, DimensionValueSet dimensionValueSet, Object value) {
        String valueStr;
        EntityViewData entityView = this.jtableParamService.getEntity(fieldDefine.getEntityKey());
        String string = valueStr = Objects.nonNull(value) ? value.toString() : null;
        if (!StringUtils.hasText(valueStr) || Objects.isNull(entityView)) {
            LOGGER.error("EFDC\u63d0\u53d6\u679a\u4e3e\u503c\u4e3a\u7a7a\u6216\u8005\u83b7\u53d6\u5b9e\u4f53\u4fe1\u606f\u4e3a\u7a7a\uff0cvalue=" + valueStr + ",entityId=" + fieldDefine.getEntityKey());
            return null;
        }
        JtableContext context = new JtableContext();
        context.setTaskKey(taskId);
        context.setFormSchemeKey(formSchemeKey);
        Map dimensionSet = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet);
        context.setDimensionSet(dimensionSet);
        ArrayList referRelations = new ArrayList();
        IEntityTable entityTable = this.jtableEntityQueryService.queryEntity(entityView, referRelations, context, false, true, true, false);
        EntityDataLoader loader = new EntityDataLoader(entityTable, StringUtils.hasText(entityView.getRowFilter()), true);
        EntityData entityData = loader.getEntityDataByKey(valueStr);
        if (Objects.nonNull(entityData)) {
            return entityData.getId();
        }
        LOGGER.error("EFDC\u63d0\u53d6\u6839\u636e\u5173\u8054\u679a\u4e3e\u83b7\u53d6\u57fa\u7840\u6570\u636e\u4e3a\u7a7a\uff0cvalue=" + valueStr + ",entityId=" + fieldDefine.getEntityKey());
        return null;
    }
}

