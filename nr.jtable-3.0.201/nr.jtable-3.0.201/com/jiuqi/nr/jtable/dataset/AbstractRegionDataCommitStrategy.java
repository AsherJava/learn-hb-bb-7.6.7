/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.FieldValidateResult
 *  com.jiuqi.np.dataengine.common.ValidateResult
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.common.constant.ErrorCode
 *  com.jiuqi.nr.common.importdata.ResultErrorInfo
 *  com.jiuqi.nr.common.importdata.SaveErrorDataInfo
 */
package com.jiuqi.nr.jtable.dataset;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.FieldValidateResult;
import com.jiuqi.np.dataengine.common.ValidateResult;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.common.constant.ErrorCode;
import com.jiuqi.nr.common.importdata.ResultErrorInfo;
import com.jiuqi.nr.common.importdata.SaveErrorDataInfo;
import com.jiuqi.nr.jtable.dataset.AbstractRegionQueryTableStrategy;
import com.jiuqi.nr.jtable.dataset.AbstractRegionRelationEvn;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.input.RegionDataCommitSet;
import com.jiuqi.nr.jtable.params.output.SaveResult;
import com.jiuqi.nr.jtable.util.DataFormaterCache;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRegionDataCommitStrategy {
    protected DataFormaterCache dataFormaterCache;
    protected AbstractRegionRelationEvn regionRelationEvn;
    protected List<String> cells;
    protected RegionDataCommitSet regionDataCommitSet;
    protected AbstractRegionQueryTableStrategy regionQueryTableStrategy;

    public AbstractRegionDataCommitStrategy(AbstractRegionRelationEvn regionRelationEvn, AbstractRegionQueryTableStrategy regionQueryTableStrategy, DataFormaterCache dataFormaterCache) {
        this.regionRelationEvn = regionRelationEvn;
        this.dataFormaterCache = dataFormaterCache;
        this.regionQueryTableStrategy = regionQueryTableStrategy;
    }

    public abstract SaveResult commitRegionData(RegionDataCommitSet var1);

    public List<SaveErrorDataInfo> setRowValue(IDataRow dataRow, List<List<Object>> rowData) {
        ArrayList<SaveErrorDataInfo> errors = new ArrayList<SaveErrorDataInfo>();
        List<List<FieldData>> bizKeyOrderFieldsList = this.regionRelationEvn.getBizKeyOrderFields();
        List<Object> bizKeyOrderFields = new ArrayList();
        if (bizKeyOrderFieldsList.size() > 0) {
            bizKeyOrderFields = bizKeyOrderFieldsList.get(0);
        }
        StringBuffer emptyDimensionField = new StringBuffer();
        String rowId = "";
        for (int i = 0; i < this.cells.size(); ++i) {
            String cell = this.cells.get(i);
            if (cell.equals("SUM")) continue;
            Object value = rowData.get(i).get(1);
            if (cell.equals("ID")) {
                rowId = value.toString();
                continue;
            }
            int cellIndex = this.regionRelationEvn.getCellIndex(cell);
            if (cellIndex < 0) continue;
            Object data = null;
            LinkData linkData = this.regionRelationEvn.getDataLinkByKey(cell);
            if (linkData != null) {
                if (null == value || StringUtils.isEmpty((String)value.toString())) {
                    for (FieldData fieldData : bizKeyOrderFields) {
                        if (!fieldData.getFieldKey().equals(linkData.getZbid())) continue;
                        if (emptyDimensionField.length() > 0) {
                            emptyDimensionField.append("\u3001");
                        }
                        emptyDimensionField.append(linkData.getZbtitle());
                    }
                }
                SaveErrorDataInfo saveErrorDataInfo = new SaveErrorDataInfo();
                data = linkData.getData(value, this.dataFormaterCache, saveErrorDataInfo, false);
                if (null != saveErrorDataInfo.getDataError().getErrorCode()) {
                    saveErrorDataInfo.setFieldKey(linkData.getZbid());
                    saveErrorDataInfo.setDataLinkKey(linkData.getKey());
                    saveErrorDataInfo.setFieldCode(linkData.getZbcode());
                    saveErrorDataInfo.setRegionKey(this.regionDataCommitSet.getRegionKey());
                    saveErrorDataInfo.setRowId(rowId);
                    errors.add(saveErrorDataInfo);
                }
                dataRow.setValue(cellIndex, data);
                continue;
            }
            if (!cell.equals("FLOATORDER")) continue;
            FieldData fieldDefine = this.regionRelationEvn.getFloatOrderFields().get(0);
            if (null == value || StringUtils.isEmpty((String)value.toString())) {
                value = this.regionQueryTableStrategy.getMaxFloatOrder();
                rowData.get(i).set(1, value);
            }
            data = this.dataFormaterCache.getData(fieldDefine, value);
            dataRow.setValue(cellIndex, data);
        }
        if (emptyDimensionField.length() > 0) {
            SaveErrorDataInfo saveErrorDataInfo = new SaveErrorDataInfo();
            ResultErrorInfo dataError = new ResultErrorInfo();
            dataError.setErrorCode(ErrorCode.DATAERROR);
            dataError.setErrorInfo("\u6d6e\u52a8\u884c\u4e1a\u52a1\u4e3b\u952e " + emptyDimensionField.toString() + " \u4e0d\u80fd\u4e3a\u7a7a");
            saveErrorDataInfo.setDataError(dataError);
            saveErrorDataInfo.setRegionKey(this.regionDataCommitSet.getRegionKey());
            saveErrorDataInfo.setRowId(rowId);
            errors.add(saveErrorDataInfo);
        }
        dataRow.setRecordKey(rowId);
        return errors;
    }

    protected String getResultMsg(FieldValidateResult fieldValidateResult) {
        List validateResults = fieldValidateResult.getValidateResult();
        if (validateResults == null || validateResults.size() <= 0) {
            return null;
        }
        StringBuilder resultMsg = new StringBuilder();
        for (ValidateResult validateResult : validateResults) {
            if (validateResult.isResultValue()) continue;
            resultMsg.append(validateResult.getResultMsg());
        }
        return resultMsg.toString();
    }

    protected void setErrorDataInfo(SaveErrorDataInfo saveErrorDataInfo, String linkKey, String regionKey, String rowId, FieldData fieldData) {
        saveErrorDataInfo.setDataLinkKey(linkKey);
        saveErrorDataInfo.setRowId(rowId);
        saveErrorDataInfo.setRegionKey(regionKey);
        if (fieldData != null) {
            saveErrorDataInfo.setFieldKey(fieldData.getFieldKey());
            saveErrorDataInfo.setFieldTitle(fieldData.getFieldTitle());
            saveErrorDataInfo.setFieldCode(fieldData.getFieldCode());
        }
    }

    protected void setErrorDataInfo(SaveErrorDataInfo saveErrorDataInfo, String linkKey, String regionKey, String rowId, FieldDefine fieldDefine) {
        saveErrorDataInfo.setDataLinkKey(linkKey);
        saveErrorDataInfo.setRowId(rowId);
        saveErrorDataInfo.setRegionKey(regionKey);
        if (fieldDefine != null) {
            saveErrorDataInfo.setFieldKey(fieldDefine.getKey());
            saveErrorDataInfo.setFieldTitle(fieldDefine.getTitle());
            saveErrorDataInfo.setFieldCode(fieldDefine.getCode());
        }
    }

    protected void setErrorDataInfo(SaveErrorDataInfo saveErrorDataInfo, LinkData linkData, String regionKey, String rowId) {
        saveErrorDataInfo.setDataLinkKey(linkData.getKey());
        saveErrorDataInfo.setRowId(rowId);
        saveErrorDataInfo.setRegionKey(regionKey);
        saveErrorDataInfo.setFieldKey(linkData.getZbid());
        saveErrorDataInfo.setFieldTitle(linkData.getZbtitle());
        saveErrorDataInfo.setFieldCode(linkData.getZbcode());
    }
}

