/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.FieldValidateResult
 *  com.jiuqi.np.dataengine.common.RowExpressionValidResult
 *  com.jiuqi.np.dataengine.common.RowValidateResult
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.exception.DataValidateException
 *  com.jiuqi.np.dataengine.exception.DuplicateRowKeysException
 *  com.jiuqi.np.dataengine.exception.ExpressionValidateException
 *  com.jiuqi.np.dataengine.exception.IncorrectQueryException
 *  com.jiuqi.np.dataengine.exception.IncorrectRowKeysException
 *  com.jiuqi.np.dataengine.exception.ValueValidateException
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataUpdator
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.common.constant.ErrorCode
 *  com.jiuqi.nr.common.importdata.SaveErrorDataInfo
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.util.EntityDefaultValue
 */
package com.jiuqi.nr.jtable.dataset.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.FieldValidateResult;
import com.jiuqi.np.dataengine.common.RowExpressionValidResult;
import com.jiuqi.np.dataengine.common.RowValidateResult;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.exception.DataValidateException;
import com.jiuqi.np.dataengine.exception.DuplicateRowKeysException;
import com.jiuqi.np.dataengine.exception.ExpressionValidateException;
import com.jiuqi.np.dataengine.exception.IncorrectQueryException;
import com.jiuqi.np.dataengine.exception.IncorrectRowKeysException;
import com.jiuqi.np.dataengine.exception.ValueValidateException;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataUpdator;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.common.constant.ErrorCode;
import com.jiuqi.nr.common.importdata.SaveErrorDataInfo;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.util.EntityDefaultValue;
import com.jiuqi.nr.jtable.dataset.AbstractRegionDataCommitStrategy;
import com.jiuqi.nr.jtable.dataset.AbstractRegionQueryTableStrategy;
import com.jiuqi.nr.jtable.dataset.AbstractRegionRelationEvn;
import com.jiuqi.nr.jtable.exception.DataEngineQueryException;
import com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost;
import com.jiuqi.nr.jtable.params.base.EnumLinkData;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo;
import com.jiuqi.nr.jtable.params.input.RegionDataCommitSet;
import com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo;
import com.jiuqi.nr.jtable.params.output.SaveResult;
import com.jiuqi.nr.jtable.service.IJtableDataEngineService;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.util.DataFormaterCache;
import com.jiuqi.nr.jtable.util.DateUtils;
import com.jiuqi.nr.jtable.util.RegionSettingUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class FloatRegionDataCommitStrategy
extends AbstractRegionDataCommitStrategy {
    private static final Logger logger = LoggerFactory.getLogger(FloatRegionDataCommitStrategy.class);
    private IDataUpdator tableUpdator;
    private List<String> deletedata = new ArrayList<String>();

    public FloatRegionDataCommitStrategy(AbstractRegionRelationEvn regionRelationEvn, AbstractRegionQueryTableStrategy regionQueryTableStrategy, DataFormaterCache dataFormaterCache) {
        super(regionRelationEvn, regionQueryTableStrategy, dataFormaterCache);
    }

    @Override
    public SaveResult commitRegionData(RegionDataCommitSet regionDataCommitSet) {
        SaveResult result = new SaveResult();
        this.regionDataCommitSet = regionDataCommitSet;
        List list = this.cells = regionDataCommitSet.getCells() == null ? null : regionDataCommitSet.getCells().get(this.regionRelationEvn.getRegionData().getKey());
        if (this.cells == null || this.cells.isEmpty()) {
            result.setMessage("NO_DATA");
            return result;
        }
        result.setMessage("HAVE_DATA");
        this.tableUpdator = this.regionQueryTableStrategy.getRegionModifyUpdator();
        this.deletedata = regionDataCommitSet.getDeletedata();
        this.modifyRegionData(result);
        this.deleteRegionData();
        this.insertRegionData(result);
        if (!result.getErrors().isEmpty()) {
            return result;
        }
        if (!this.regionRelationEvn.getAllowDuplicate()) {
            this.tableUpdator.needCheckDuplicateKeys(true);
        }
        try {
            this.tableUpdator.setValidExpression(this.regionRelationEvn.getExpressions());
            this.tableUpdator.commitChanges(true);
        }
        catch (Exception e) {
            result.getErrors().addAll(this.exceptionErrors(e));
            return result;
        }
        return result;
    }

    private void modifyRegionData(SaveResult result) {
        Map<String, List<List<Object>>> modifyDataMap = this.regionDataCommitSet.getModifyDataMap("ID", this.regionRelationEvn.getRegionData().getKey().toString());
        for (String bizKey : modifyDataMap.keySet()) {
            List<List<Object>> valueList = modifyDataMap.get(bizKey);
            if (bizKey.contains("FILL_ENTITY_EMPTY")) {
                this.regionDataCommitSet.getNewdata().add(valueList);
                continue;
            }
            if (!this.isValidDataRow(valueList)) {
                this.deletedata.add(bizKey);
                continue;
            }
            DimensionValueSet dimensionValueSet = this.getRowDimensionValueSet(bizKey);
            IDataRow modifiedRow = null;
            try {
                modifiedRow = this.tableUpdator.addModifiedRow(dimensionValueSet);
            }
            catch (IncorrectQueryException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            result.getErrors().addAll(this.setRowValue(modifiedRow, valueList));
            Object floatOrder = valueList.get(this.cells.indexOf("FLOATORDER")).get(1);
            result.getFloatOrderMap().put(bizKey, floatOrder);
        }
    }

    private void deleteRegionData() {
        for (String bizKey : this.deletedata) {
            if (bizKey.contains("FILL_ENTITY_EMPTY")) continue;
            DimensionValueSet dimensionValueSet = this.getRowDimensionValueSet(bizKey);
            try {
                this.tableUpdator.addDeletedRow(dimensionValueSet);
            }
            catch (IncorrectQueryException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
    }

    private void insertRegionData(SaveResult result) {
        List<List<List<Object>>> newdata = this.regionDataCommitSet.getNewdata();
        IJtableDataEngineService jtableDataEngineService = (IJtableDataEngineService)BeanUtil.getBean(IJtableDataEngineService.class);
        for (List<List<Object>> newDataRow : newdata) {
            if (!this.isValidDataRow(newDataRow)) continue;
            String bizKey = String.valueOf(newDataRow.get(this.cells.indexOf("ID")).get(1));
            StringBuffer bizKeyStrBuf = new StringBuffer();
            DimensionValueSet masterKeys = this.tableUpdator.getMasterKeys();
            DimensionValueSet dimensionValueSet = new DimensionValueSet(masterKeys);
            if (this.regionRelationEvn.getAllowDuplicate()) {
                if (StringUtils.isEmpty((String)bizKey) || bizKey.contains("FILL_ENTITY_EMPTY")) {
                    bizKey = UUID.randomUUID().toString();
                }
                dimensionValueSet.setValue("RECORDKEY", (Object)bizKey);
                if (bizKeyStrBuf.length() > 0) {
                    bizKeyStrBuf.append("#^$");
                }
                bizKeyStrBuf.append(bizKey);
            }
            StringBuffer emptyDimensionField = new StringBuffer();
            ArrayList<String> linkIds = new ArrayList<String>();
            for (FieldData fieldData : this.regionRelationEvn.getBizKeyOrderFields().get(0)) {
                String dimensionName = jtableDataEngineService.getDimensionName(fieldData);
                String dataLinkKey = this.regionRelationEvn.getDataLinkKeyByFiled(fieldData.getFieldKey());
                if (dataLinkKey == null || !this.cells.contains(dataLinkKey.toString())) continue;
                String dimensionValue = newDataRow.get(this.cells.indexOf(dataLinkKey.toString())).get(1).toString();
                if (dimensionValueSet.hasValue(dimensionName) && StringUtils.isEmpty((String)dimensionValue)) {
                    dimensionValue = dimensionValueSet.getValue(dimensionName).toString();
                    newDataRow.get(this.cells.indexOf(dataLinkKey.toString())).set(1, dimensionValue);
                }
                if (StringUtils.isEmpty((String)dimensionValue)) {
                    if (emptyDimensionField.length() > 0) {
                        emptyDimensionField.append("\u3001");
                    }
                    emptyDimensionField.append(fieldData.getFieldTitle());
                    linkIds.add(dataLinkKey);
                    continue;
                }
                LinkData dataLink = this.regionRelationEvn.getDataLinkByKey(dataLinkKey);
                SaveErrorDataInfo saveErrorDataInfo = new SaveErrorDataInfo();
                Object data = dataLink.getData(dimensionValue, this.dataFormaterCache, saveErrorDataInfo, false);
                if (null != saveErrorDataInfo.getDataError().getErrorCode()) {
                    this.setErrorDataInfo(saveErrorDataInfo, dataLinkKey, this.regionDataCommitSet.getRegionKey(), bizKey, fieldData);
                    result.getErrors().add(saveErrorDataInfo);
                    continue;
                }
                dimensionValueSet.setValue(dimensionName, data);
                if (bizKeyStrBuf.length() > 0) {
                    bizKeyStrBuf.append("#^$");
                }
                bizKeyStrBuf.append(data);
            }
            result.getRowKeyMap().put(bizKey, bizKeyStrBuf.toString());
            if (emptyDimensionField.length() > 0) {
                for (String linkId : linkIds) {
                    FieldData fieldData = this.regionRelationEvn.getFieldByDataLink(linkId);
                    SaveErrorDataInfo saveErrorDataInfo = new SaveErrorDataInfo();
                    saveErrorDataInfo.getDataError().setErrorCode(ErrorCode.DATAERROR);
                    saveErrorDataInfo.getDataError().setErrorInfo("\u6d6e\u52a8\u884c\u4e1a\u52a1\u4e3b\u952e " + emptyDimensionField.toString() + " \u4e0d\u80fd\u4e3a\u7a7a");
                    this.setErrorDataInfo(saveErrorDataInfo, linkId, this.regionDataCommitSet.getRegionKey(), bizKey, fieldData);
                    result.getErrors().add(saveErrorDataInfo);
                }
                return;
            }
            IDataRow appendRow = null;
            try {
                appendRow = this.tableUpdator.addInsertedRow(dimensionValueSet);
            }
            catch (IncorrectQueryException e) {
                result.getErrors().addAll(this.exceptionErrors((Exception)((Object)e)));
                return;
            }
            List<SaveErrorDataInfo> setRowValueErrors = this.setRowValue(appendRow, newDataRow);
            result.getErrors().addAll(setRowValueErrors);
            Object floatOrder = newDataRow.get(this.cells.indexOf("FLOATORDER")).get(1);
            result.getFloatOrderMap().put(bizKey, floatOrder);
        }
    }

    private boolean isValidDataRow(List<List<Object>> dataRow) {
        boolean isEmpty = true;
        List<String> calcDataLinks = this.regionRelationEvn.getCalcDataLinks();
        List<EntityDefaultValue> regionEntityDefaultValue = this.regionRelationEvn.getRegionData().getRegionEntityDefaultValue();
        for (int i = 0; i < dataRow.size(); ++i) {
            List<String> enumRelationLinks;
            String cell = (String)this.cells.get(i);
            if (!this.regionRelationEvn.getDataLinkFieldMap().containsKey(cell)) continue;
            String dataLinkKey = cell;
            FieldData fieldDefine = this.regionRelationEvn.getFieldByDataLink(dataLinkKey);
            LinkData dataLink = this.regionRelationEvn.getDataLinkByKey(dataLinkKey);
            Object value = dataRow.get(i).get(1);
            if (fieldDefine != null && dataLink != null && StringUtils.isNotEmpty((String)dataLink.getDefaultValue()) && dataLink.getDefaultValue().equals(value.toString()) || !CollectionUtils.isEmpty(regionEntityDefaultValue) && RegionSettingUtil.checkRegionSettingContainDefaultVal(this.regionRelationEvn.getRegionData(), dataLink) || calcDataLinks.contains(dataLinkKey) || (enumRelationLinks = this.regionRelationEvn.getEnumRelationLinks()).contains(dataLinkKey) || value == null || !StringUtils.isNotEmpty((String)value.toString())) continue;
            isEmpty = false;
        }
        return !isEmpty;
    }

    public DimensionValueSet getRowDimensionValueSet(String bizKeyStr) {
        IJtableDataEngineService jtableDataEngineService = (IJtableDataEngineService)BeanUtil.getBean(IJtableDataEngineService.class);
        DimensionValueSet masterKeys = this.tableUpdator.getMasterKeys();
        DimensionValueSet dimensionValueSet = new DimensionValueSet(masterKeys);
        String[] bizKeys = bizKeyStr.split("\\#\\^\\$");
        int i = 0;
        for (FieldData fieldData : this.regionRelationEvn.getBizKeyOrderFields().get(0)) {
            String dimensionName = jtableDataEngineService.getDimensionName(fieldData);
            if ("RECORDKEY".equals(dimensionName)) {
                dimensionValueSet.setValue("RECORDKEY", (Object)bizKeys[i]);
            } else if (fieldData.getFieldType() == FieldType.FIELD_TYPE_DATE.getValue()) {
                dimensionValueSet.setValue(dimensionName, (Object)DateUtils.stringToDate(bizKeys[i]));
            } else {
                dimensionValueSet.setValue(dimensionName, (Object)bizKeys[i]);
            }
            ++i;
        }
        return dimensionValueSet;
    }

    public String getBizKeyStr(IDataRow dataRow) {
        List<FieldData> bizKeyOrderFields = this.regionRelationEvn.getBizKeyOrderFields().get(0);
        StringBuffer bizKeyStrBuf = new StringBuffer();
        for (FieldData bizKeyField : bizKeyOrderFields) {
            LinkData dataLink = this.regionRelationEvn.getDataLinkByFiled(bizKeyField.getFieldKey());
            int cellIndex = -1;
            cellIndex = dataLink == null && bizKeyField.getFieldValueType() == FieldValueType.FIELD_VALUE_BIZKEY_ORDER.getValue() ? this.regionRelationEvn.getCellIndex("ID") : this.regionRelationEvn.getCellIndex(dataLink.getKey());
            if (cellIndex < 0) continue;
            try {
                String bizKeyValue = dataRow.getAsString(cellIndex);
                if (bizKeyStrBuf.length() > 0) {
                    bizKeyStrBuf.append("#^$");
                }
                bizKeyStrBuf.append(bizKeyValue);
            }
            catch (DataTypeException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                throw new DataEngineQueryException(JtableExceptionCodeCost.DATAENGINE_QUERY_EXCEPTION, new String[]{"\u83b7\u53d6\u6307\u6807\u6570\u636e\u51fa\u9519"});
            }
        }
        return bizKeyStrBuf.toString();
    }

    private List<SaveErrorDataInfo> exceptionErrors(Exception e) {
        ArrayList<SaveErrorDataInfo> errorList = new ArrayList<SaveErrorDataInfo>();
        IJtableDataEngineService jtableDataEngineService = (IJtableDataEngineService)BeanUtil.getBean(IJtableDataEngineService.class);
        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        if (e instanceof DuplicateRowKeysException) {
            DuplicateRowKeysException duplicateRowKeysException = (DuplicateRowKeysException)e;
            List duplicateKeys = duplicateRowKeysException.getDuplicateKeys();
            for (DimensionValueSet key : duplicateKeys) {
                StringBuilder duplicateStr = new StringBuilder("\u4e1a\u52a1\u4e3b\u952e\u91cd\u590d(");
                for (FieldData field : this.regionRelationEvn.getBizKeyOrderFields().get(0)) {
                    String dimensionName = jtableDataEngineService.getDimensionName(field);
                    String dimensionValue = key.getValue(dimensionName).toString();
                    String dataLinkKey = this.regionRelationEvn.getDataLinkKeyByFiled(field.getFieldKey());
                    LinkData dataLink = this.regionRelationEvn.getDataLinkByKey(dataLinkKey);
                    if (dataLink instanceof EnumLinkData) {
                        EnumLinkData enumLink = (EnumLinkData)dataLink;
                        IJtableEntityService jtableEntityService = (IJtableEntityService)BeanUtil.getBean(IJtableEntityService.class);
                        EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
                        entityQueryByKeyInfo.setEntityViewKey(enumLink.getEntityKey());
                        entityQueryByKeyInfo.setEntityKey(dimensionValue);
                        entityQueryByKeyInfo.setDataLinkKey(dataLinkKey);
                        EntityByKeyReturnInfo entityDataByKey = jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
                        if (entityDataByKey != null && entityDataByKey.getEntity() != null) {
                            dimensionValue = entityDataByKey.getEntity().getRowCaption();
                        }
                    }
                    duplicateStr.append(field.getFieldTitle()).append(":").append(dimensionValue).append(";");
                }
                duplicateStr.append(")");
                SaveErrorDataInfo saveErrorDataInfo = new SaveErrorDataInfo();
                saveErrorDataInfo.getDataError().setErrorInfo(duplicateStr.toString());
                saveErrorDataInfo.getDataError().setErrorCode(ErrorCode.SYSTEMERROR);
                errorList.add(saveErrorDataInfo);
            }
        } else if (e instanceof IncorrectRowKeysException) {
            IncorrectRowKeysException incorrectRowKeysException = (IncorrectRowKeysException)e;
            DimensionValueSet rowKey = incorrectRowKeysException.getRowKeys();
            StringBuilder emptyDimStr = new StringBuilder("\u4e1a\u52a1\u4e3b\u952e\u4e3a\u7a7a(");
            for (FieldData field : this.regionRelationEvn.getBizKeyOrderFields().get(0)) {
                String dimensionName = jtableDataEngineService.getDimensionName(field);
                emptyDimStr.append(field.getFieldTitle()).append(":").append(rowKey.getValue(dimensionName)).append(";");
            }
            emptyDimStr.append(")");
            SaveErrorDataInfo saveErrorDataInfo = new SaveErrorDataInfo();
            saveErrorDataInfo.getDataError().setErrorInfo(emptyDimStr.toString());
            saveErrorDataInfo.getDataError().setErrorCode(ErrorCode.SYSTEMERROR);
            errorList.add(saveErrorDataInfo);
        } else if (e instanceof ValueValidateException) {
            ValueValidateException valueValidateException = (ValueValidateException)e;
            List validateResults = valueValidateException.getRowValidateResults();
            for (RowValidateResult rowValidateResult : validateResults) {
                List fieldValidateResults = rowValidateResult.getFieldValidateResults();
                for (FieldValidateResult fieldValidateResult : fieldValidateResults) {
                    String resultMsg = this.getResultMsg(fieldValidateResult);
                    if (StringUtils.isEmpty((String)resultMsg)) continue;
                    FieldDefine errorField = fieldValidateResult.getFieldDefine();
                    String linkKey = this.regionRelationEvn.getDataLinkKeyByFiled(errorField.getKey());
                    SaveErrorDataInfo saveErrorDataInfo = new SaveErrorDataInfo();
                    this.setErrorDataInfo(saveErrorDataInfo, linkKey, this.regionDataCommitSet.getRegionKey(), rowValidateResult.getRecordKey(), errorField);
                    saveErrorDataInfo.getDataError().setErrorCode(ErrorCode.DATAERROR);
                    saveErrorDataInfo.getDataError().setErrorInfo(resultMsg);
                    errorList.add(saveErrorDataInfo);
                }
            }
        } else if (e instanceof ExpressionValidateException) {
            ExpressionValidateException expressionValidateException = (ExpressionValidateException)e;
            List rowExpressionValidResults = expressionValidateException.getRowExpressionValidResults();
            for (RowExpressionValidResult rowExpressionValidResult : rowExpressionValidResults) {
                List errorExpressions = rowExpressionValidResult.getErrorExpressions();
                for (IParsedExpression errorExpression : errorExpressions) {
                    Formula formula = errorExpression.getSource();
                    LinkData linkData = this.regionRelationEvn.getDataLinkByKey(formula.getId());
                    SaveErrorDataInfo saveErrorDataInfo = new SaveErrorDataInfo();
                    this.setErrorDataInfo(saveErrorDataInfo, linkData, this.regionDataCommitSet.getRegionKey(), rowExpressionValidResult.getRecordKey());
                    saveErrorDataInfo.getDataError().setErrorCode(ErrorCode.DATAERROR);
                    saveErrorDataInfo.getDataError().setErrorInfo(e.getMessage() + null == formula.getMeanning() ? formula.getFormula() : formula.getMeanning());
                    if (StringUtils.isEmpty((String)saveErrorDataInfo.getDataError().getErrorInfo())) {
                        saveErrorDataInfo.getDataError().setErrorInfo("\u6570\u636e\u6821\u9a8c\u5931\u8d25");
                    }
                    errorList.add(saveErrorDataInfo);
                }
            }
        } else if (e instanceof DataValidateException) {
            DataValidateException dataValidateException = (DataValidateException)e;
            dataValidateException.getMessage();
            SaveErrorDataInfo saveErrorDataInfo = new SaveErrorDataInfo();
            saveErrorDataInfo.getDataError().setErrorCode(ErrorCode.DATAERROR);
            saveErrorDataInfo.getDataError().setErrorInfo(dataValidateException.getMessage());
            if (StringUtils.isEmpty((String)saveErrorDataInfo.getDataError().getErrorInfo())) {
                saveErrorDataInfo.getDataError().setErrorInfo("\u6570\u636e\u6821\u9a8c\u5931\u8d25");
            }
            errorList.add(saveErrorDataInfo);
        }
        if (errorList.isEmpty()) {
            SaveErrorDataInfo saveErrorDataInfo = new SaveErrorDataInfo();
            saveErrorDataInfo.getDataError().setErrorInfo("\u6570\u636e\u4fdd\u5b58\u51fa\u9519");
            saveErrorDataInfo.getDataError().setErrorCode(ErrorCode.SYSTEMERROR);
            errorList.add(saveErrorDataInfo);
        }
        return errorList;
    }
}

