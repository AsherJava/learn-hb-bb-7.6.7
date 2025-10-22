/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.datacrud.IDataValue
 *  com.jiuqi.nr.datacrud.IRegionDataSet
 *  com.jiuqi.nr.datacrud.IRowData
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 */
package com.jiuqi.nr.data.excel.service.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.data.excel.obj.RegionValidateResult;
import com.jiuqi.nr.data.excel.service.internal.IRegionDataValidator;
import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IRegionDataSet;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import org.springframework.util.CollectionUtils;

public class EmptyZeroFormValidator
implements IRegionDataValidator {
    private final boolean autoFillIsNullTable;

    public EmptyZeroFormValidator(boolean autoFillIsNullTable) {
        this.autoFillIsNullTable = autoFillIsNullTable;
    }

    @Override
    public RegionValidateResult validate(IRegionDataSet regionDataSet) {
        IRowData data;
        boolean fillEmpty;
        if (regionDataSet == null || CollectionUtils.isEmpty(regionDataSet.getRowData())) {
            return new RegionValidateResult(false, 1);
        }
        boolean filledRegion = this.filledRegion(regionDataSet);
        boolean bl = fillEmpty = this.autoFillIsNullTable && filledRegion;
        if (fillEmpty) {
            return new RegionValidateResult(false, 1);
        }
        boolean haveData = false;
        Iterator iterator = regionDataSet.getRowData().iterator();
        while (iterator.hasNext() && !(haveData = this.validateRow(data = (IRowData)iterator.next()))) {
        }
        return new RegionValidateResult(haveData, haveData ? 0 : 3);
    }

    private boolean filledRegion(IRegionDataSet regionDataSet) {
        boolean filled = true;
        for (IRowData rowDatum : regionDataSet.getRowData()) {
            if (rowDatum.isFilledRow()) continue;
            filled = false;
            break;
        }
        return filled;
    }

    private boolean validateRow(IRowData rowData) {
        if (CollectionUtils.isEmpty(rowData.getLinkDataValues())) {
            return false;
        }
        boolean haveData = false;
        for (IDataValue d : rowData.getLinkDataValues()) {
            if ("FLOATORDER".equals(d.getMetaData().getLinkKey())) continue;
            if (haveData) break;
            haveData = this.validateCell(d);
        }
        return haveData;
    }

    private boolean validateCell(IDataValue dataValue) {
        return this.validateCellEmpty(dataValue) && this.validZero(dataValue);
    }

    private boolean validateCellEmpty(IDataValue dataValue) {
        if (dataValue == null || dataValue.getAsNull()) {
            return false;
        }
        boolean haveData = dataValue.getAsObject() instanceof String ? StringUtils.isNotEmpty((String)dataValue.toString()) : dataValue.getAsObject() != null;
        return haveData;
    }

    private boolean validZero(IDataValue dataValue) {
        DataField dataField = dataValue.getMetaData().getDataField();
        return dataField == null || dataField.getDataFieldType() != DataFieldType.BIGDECIMAL && dataField.getDataFieldType() != DataFieldType.INTEGER || dataValue.getAsCurrency().compareTo(BigDecimal.ZERO) != 0;
    }

    @Override
    public boolean validateForm(List<RegionValidateResult> regionValidateResults) {
        if (CollectionUtils.isEmpty(regionValidateResults)) {
            return false;
        }
        for (RegionValidateResult regionValidateResult : regionValidateResults) {
            if (!regionValidateResult.isPass()) continue;
            return true;
        }
        return false;
    }
}

