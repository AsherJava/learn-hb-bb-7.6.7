/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.FieldValidateResult
 *  com.jiuqi.np.dataengine.common.RowExpressionValidResult
 *  com.jiuqi.np.dataengine.common.RowValidateResult
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.exception.DataValidateException
 *  com.jiuqi.np.dataengine.exception.ExpressionValidateException
 *  com.jiuqi.np.dataengine.exception.IncorrectQueryException
 *  com.jiuqi.np.dataengine.exception.ValueValidateException
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.common.constant.ErrorCode
 *  com.jiuqi.nr.common.importdata.SaveErrorDataInfo
 */
package com.jiuqi.nr.jtable.dataset.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.FieldValidateResult;
import com.jiuqi.np.dataengine.common.RowExpressionValidResult;
import com.jiuqi.np.dataengine.common.RowValidateResult;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.exception.DataValidateException;
import com.jiuqi.np.dataengine.exception.ExpressionValidateException;
import com.jiuqi.np.dataengine.exception.IncorrectQueryException;
import com.jiuqi.np.dataengine.exception.ValueValidateException;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.common.constant.ErrorCode;
import com.jiuqi.nr.common.importdata.SaveErrorDataInfo;
import com.jiuqi.nr.jtable.dataset.AbstractRegionDataCommitStrategy;
import com.jiuqi.nr.jtable.dataset.AbstractRegionQueryTableStrategy;
import com.jiuqi.nr.jtable.dataset.AbstractRegionRelationEvn;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.input.RegionDataCommitSet;
import com.jiuqi.nr.jtable.params.output.SaveResult;
import com.jiuqi.nr.jtable.util.DataFormaterCache;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleRegionDataCommitStrategy
extends AbstractRegionDataCommitStrategy {
    private static final Logger logger = LoggerFactory.getLogger(SimpleRegionDataCommitStrategy.class);
    protected IDataTable dataTable;

    public SimpleRegionDataCommitStrategy(AbstractRegionRelationEvn regionRelationEvn, AbstractRegionQueryTableStrategy regionQueryTableStrategy, DataFormaterCache dataFormaterCache) {
        super(regionRelationEvn, regionQueryTableStrategy, dataFormaterCache);
    }

    @Override
    public SaveResult commitRegionData(RegionDataCommitSet regionDataCommitSet) {
        SaveResult result = new SaveResult();
        this.regionDataCommitSet = regionDataCommitSet;
        this.cells = regionDataCommitSet.getCells() == null ? null : regionDataCommitSet.getCells().get(this.regionRelationEvn.getRegionData().getKey());
        Map<String, LinkData> dataLinkMap = this.regionRelationEvn.getDataLinkMap();
        for (String linkKey : dataLinkMap.keySet()) {
            LinkData linkData = dataLinkMap.get(linkKey);
            if (linkData.isNullable() || this.cells.contains(linkKey)) continue;
            this.cells.add(linkKey);
        }
        if (this.cells == null || this.cells.isEmpty()) {
            result.setMessage("NO_DATA");
            return result;
        }
        result.setMessage("HAVE_DATA");
        this.dataTable = this.regionQueryTableStrategy.getRegionModifyTable();
        DimensionValueSet dimensionValueSet = this.dataTable.getMasterKeys();
        IDataRow dataRow = null;
        if (this.dataTable.getCount() > 0) {
            dataRow = this.dataTable.getItem(0);
        } else {
            try {
                dataRow = this.dataTable.appendRow(dimensionValueSet);
                this.setTableListValue(dataRow);
            }
            catch (IncorrectQueryException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                SaveErrorDataInfo saveErrorDataInfo = new SaveErrorDataInfo();
                saveErrorDataInfo.getDataError().setErrorInfo(e.getMessage());
                saveErrorDataInfo.getDataError().setErrorCode(ErrorCode.SYSTEMERROR);
                result.getErrors().add(saveErrorDataInfo);
                return result;
            }
        }
        this.addDatas(dataRow, regionDataCommitSet.getData().get(0));
        result.getErrors().addAll(this.setRowValue(dataRow, regionDataCommitSet.getData().get(0)));
        if (!result.getErrors().isEmpty()) {
            return result;
        }
        try {
            this.dataTable.setValidExpression(this.regionRelationEvn.getExpressions());
            this.dataTable.commitChanges(true);
        }
        catch (Exception e) {
            result.getErrors().addAll(this.exceptionErrors(e));
            return result;
        }
        return result;
    }

    private void addDatas(IDataRow dataRow, List<List<Object>> datas) {
        ArrayList<String> removeKeys = new ArrayList<String>();
        for (int i = 0; i < this.cells.size(); ++i) {
            if (i <= datas.size() - 1) continue;
            ArrayList<Object> newDataList = new ArrayList<Object>();
            String cell = (String)this.cells.get(i);
            int cellIndex = this.regionRelationEvn.getCellIndex(cell);
            if (cellIndex == -1) {
                removeKeys.add(cell);
                continue;
            }
            AbstractData value = dataRow.getValue(cellIndex);
            if (value.isNull) {
                newDataList.add(null);
                newDataList.add(null);
                datas.add(newDataList);
                continue;
            }
            removeKeys.add(cell);
        }
        for (String dataLinkKey : removeKeys) {
            this.cells.remove(dataLinkKey);
        }
    }

    private List<SaveErrorDataInfo> exceptionErrors(Exception e) {
        ArrayList<SaveErrorDataInfo> errorList = new ArrayList<SaveErrorDataInfo>();
        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        if (e instanceof ValueValidateException) {
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
                    if (saveErrorDataInfo.getDataError().getErrorInfo() == null || saveErrorDataInfo.getDataError().getErrorInfo().equals("")) {
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

    private void setTableListValue(IDataRow dataRow) {
        ArrayList<String> tableKeys = new ArrayList<String>();
        tableKeys.addAll(this.regionRelationEvn.getTableKeyList());
        block0: for (String tableKey : tableKeys) {
            for (FieldData field : this.regionRelationEvn.getDataLinkFieldMap().values()) {
                if (tableKey != field.getOwnerTableKey()) continue;
                String dataLinkKey = this.regionRelationEvn.getDataLinkKeyByFiled(field.getFieldKey());
                int cellIndex = this.regionRelationEvn.getCellIndex(dataLinkKey.toString());
                dataRow.setValue(cellIndex, null);
                continue block0;
            }
        }
    }
}

