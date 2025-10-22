/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.common.constant.ErrorCode
 *  com.jiuqi.nr.common.importdata.ResultErrorInfo
 */
package com.jiuqi.nr.dafafill.service.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.common.constant.ErrorCode;
import com.jiuqi.nr.common.importdata.ResultErrorInfo;
import com.jiuqi.nr.dafafill.exception.DataFillRuntimeException;
import com.jiuqi.nr.dafafill.model.DFDimensionValue;
import com.jiuqi.nr.dafafill.model.DataFillContext;
import com.jiuqi.nr.dafafill.model.DataFillDataSaveRow;
import com.jiuqi.nr.dafafill.model.DataFillSaveErrorDataInfo;
import com.jiuqi.nr.dafafill.model.OrderField;
import com.jiuqi.nr.dafafill.model.QueryField;
import com.jiuqi.nr.dafafill.model.enums.FieldType;
import com.jiuqi.nr.dafafill.model.enums.OrderMode;
import com.jiuqi.nr.dafafill.model.enums.TableType;
import com.jiuqi.nr.dafafill.service.impl.DataFillFloatDataEnvServiceImpl;
import com.jiuqi.nr.dafafill.util.NrDataFillI18nUtil;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class DataFillAccountDataEnvServiceImpl
extends DataFillFloatDataEnvServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(DataFillAccountDataEnvServiceImpl.class);

    @Override
    public TableType getTableType() {
        return TableType.ACCOUNT;
    }

    @Override
    protected DimensionValueSet getAddRowDimensionValueSet(DataFillContext context, DataFillDataSaveRow dataFillDataSaveRow, DataFillFloatDataEnvServiceImpl.FloatTypeDTO floatTypeDTO, List<DataFillSaveErrorDataInfo> saveErrorDataInfo, Map<String, FieldDefine> addColumnMap, Map<String, QueryField> zbIdQueryField, QueryField master, QueryField period, List<QueryField> hiddenFields, List<String> hiddenIds) {
        StringBuilder errorBuilder = new StringBuilder();
        errorBuilder.append(NrDataFillI18nUtil.buildCode("nr.dataFill.floatRowBusiPK") + " ");
        List<DFDimensionValue> dimensionValues = dataFillDataSaveRow.getDimensionValues();
        String dwKey = this.dFDimensionParser.getSqlDimensionName(master);
        DimensionValueSet sqlDimensionValueSet = this.dFDimensionParser.entityIdToSqlDimension(context, dimensionValues);
        String dwValue = (String)sqlDimensionValueSet.getValue(dwKey);
        this.dFDimensionParser.searchDwHiddenDimensionValue(dwValue, sqlDimensionValueSet, master, period, hiddenFields, hiddenIds, true);
        List<String> zbs = dataFillDataSaveRow.getZbs();
        List<String> values = dataFillDataSaveRow.getValues();
        int idIndex = zbs.indexOf("ID");
        String idValue = "";
        if (idIndex > -1) {
            idValue = values.get(idIndex);
        }
        String bizKey = floatTypeDTO.getBizKey();
        int floatType = floatTypeDTO.getFloatType();
        if (floatType == 0) {
            if (!StringUtils.hasLength(idValue)) {
                idValue = UUID.randomUUID().toString();
            }
            sqlDimensionValueSet.setValue(bizKey, (Object)idValue);
        } else {
            String fieldKey;
            int i;
            List<String> tableDimensionNames = floatTypeDTO.getTableDimensionNames();
            List<String> fieldKeys = floatTypeDTO.getFieldKeys();
            boolean errorFlag = true;
            for (i = 0; i < fieldKeys.size(); ++i) {
                fieldKey = fieldKeys.get(i);
                int indexOf = zbs.indexOf(fieldKey);
                if (indexOf > -1) {
                    errorFlag = false;
                    String dimensionValue = values.get(indexOf);
                    FieldDefine zb = addColumnMap.get(fieldKey);
                    QueryField queryField = zbIdQueryField.get(fieldKey);
                    DataFillSaveErrorDataInfo saveErrorData = this.toSqlValue(dimensionValue, queryField, context, dataFillDataSaveRow.getDimensionValues(), null, zb, floatTypeDTO);
                    if (null != saveErrorData.getDataError()) {
                        DataFillSaveErrorDataInfo errorDataInfo = new DataFillSaveErrorDataInfo();
                        errorDataInfo.setDataError(saveErrorData.getDataError());
                        errorDataInfo.setZb(saveErrorData.getZb());
                        errorDataInfo.setDimensionValues(saveErrorData.getDimensionValues());
                        errorDataInfo.setValue(saveErrorData.getValue());
                        errorDataInfo.setErrorLocX(indexOf + 1 + 2);
                        saveErrorDataInfo.add(errorDataInfo);
                        return null;
                    }
                    sqlDimensionValueSet.setValue(tableDimensionNames.get(i), (Object)saveErrorData.getValue());
                    continue;
                }
                if (floatType == 1 && bizKey.equals(tableDimensionNames.get(i))) {
                    sqlDimensionValueSet.setValue(tableDimensionNames.get(i), (Object)UUID.randomUUID().toString());
                    continue;
                }
                sqlDimensionValueSet.setValue(tableDimensionNames.get(i), (Object)"");
            }
            if (errorFlag) {
                for (i = 0; i < fieldKeys.size(); ++i) {
                    fieldKey = fieldKeys.get(i);
                    if (floatType == 1 && bizKey.equals(tableDimensionNames.get(i))) continue;
                    DataFillSaveErrorDataInfo errorDataInfo = new DataFillSaveErrorDataInfo();
                    FieldDefine fieldDefine = null;
                    try {
                        fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(fieldKey);
                    }
                    catch (Exception e) {
                        StringBuilder logInfo = new StringBuilder();
                        logInfo.append("\u81ea\u5b9a\u4e49\u5f55\u5165\u83b7\u53d6\u6307\u6807").append("[").append(fieldKey).append("]\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a").append(e.getMessage());
                        logger.error(logInfo.toString(), e);
                        throw new DataFillRuntimeException(logInfo.toString());
                    }
                    if (!saveErrorDataInfo.isEmpty()) {
                        errorBuilder.append("\uff0c").append(fieldDefine.getTitle());
                    } else {
                        errorBuilder.append("\uff1a").append(fieldDefine.getTitle());
                    }
                    ResultErrorInfo dataError = new ResultErrorInfo();
                    dataError.setErrorCode(ErrorCode.DATAERROR);
                    errorDataInfo.setDataError(dataError);
                    errorDataInfo.setZb(fieldDefine.getKey());
                    saveErrorDataInfo.add(errorDataInfo);
                }
            }
        }
        errorBuilder.append(" ").append(NrDataFillI18nUtil.buildCode("nr.dataFill.cantAllNull"));
        if (!saveErrorDataInfo.isEmpty()) {
            for (DataFillSaveErrorDataInfo dataFillSaveErrorDataInfo : saveErrorDataInfo) {
                dataFillSaveErrorDataInfo.getDataError().setErrorInfo(errorBuilder.toString());
                dataFillSaveErrorDataInfo.setDimensionValues(dimensionValues);
            }
        }
        return sqlDimensionValueSet;
    }

    @Override
    protected void addOrder(DataFillContext context, Map<String, QueryField> queryFieldsMap, IDataQuery dataQuery, List<FieldDefine> floatOrderFields, DataFillFloatDataEnvServiceImpl.FloatTypeDTO floatType) {
        List<OrderField> orderFields = context.getModel().getOrderFields();
        if (null != orderFields && !orderFields.isEmpty()) {
            for (OrderField orderField : orderFields) {
                String fullCode = orderField.getFullCode();
                QueryField queryField = queryFieldsMap.get(fullCode);
                if (queryField.getFieldType() == FieldType.ZB || queryField.getFieldType() == FieldType.FIELD || queryField.getFieldType() == FieldType.TABLEDIMENSION) {
                    FieldDefine fieldDefine = null;
                    try {
                        fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(queryField.getId());
                    }
                    catch (Exception e) {
                        StringBuilder logInfo = new StringBuilder();
                        logInfo.append("\u81ea\u5b9a\u4e49\u5f55\u5165\u83b7\u53d6\u6307\u6807").append("[").append(queryField.getId()).append("]\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a").append(e.getMessage());
                        logger.error(logInfo.toString(), e);
                        throw new DataFillRuntimeException(logInfo.toString());
                    }
                    dataQuery.addOrderByItem(fieldDefine, orderField.getMode() == OrderMode.DESC);
                    continue;
                }
                if (queryField.getFieldType() == FieldType.PERIOD) continue;
                String dimensionName = this.dFDimensionParser.getDimensionNameByField(queryField);
                FieldDefine fieldDefine = floatType.getDimenNameFieldMap().get(dimensionName);
                dataQuery.addOrderByItem(fieldDefine, orderField.getMode() == OrderMode.DESC);
            }
            for (FieldDefine fieldDefine : floatOrderFields) {
                dataQuery.addOrderByItem(fieldDefine, false);
            }
        } else {
            Map<FieldType, List<QueryField>> fieldTypeQueryFields = this.dFDimensionParser.getFieldTypeQueryFields(context);
            String dimensionNameM = this.dFDimensionParser.getDimensionNameByField(fieldTypeQueryFields.get((Object)FieldType.MASTER).get(0));
            FieldDefine fieldDefineM = floatType.getDimenNameFieldMap().get(dimensionNameM);
            dataQuery.addOrderByItem(fieldDefineM, false);
            for (FieldDefine fieldDefine : floatOrderFields) {
                dataQuery.addOrderByItem(fieldDefine, false);
            }
        }
    }
}

