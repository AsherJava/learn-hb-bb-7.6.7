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
import java.util.List;
import org.springframework.util.CollectionUtils;

public class ZeroNotEmptyFormValidator
implements IRegionDataValidator {
    private final boolean autoFillIsNullTable;

    public ZeroNotEmptyFormValidator(boolean autoFillIsNullTable) {
        this.autoFillIsNullTable = autoFillIsNullTable;
    }

    @Override
    public RegionValidateResult validate(IRegionDataSet regionDataSet) {
        boolean fillEmpty;
        if (regionDataSet == null || CollectionUtils.isEmpty(regionDataSet.getRowData())) {
            return new RegionValidateResult(true, 1);
        }
        boolean filledRegion = this.filledRegion(regionDataSet);
        boolean bl = fillEmpty = this.autoFillIsNullTable && filledRegion;
        if (fillEmpty) {
            return new RegionValidateResult(true, 1);
        }
        boolean contain0 = false;
        for (IRowData rowData : regionDataSet.getRowData()) {
            if (CollectionUtils.isEmpty(rowData.getLinkDataValues())) continue;
            for (IDataValue d : rowData.getLinkDataValues()) {
                if (d == null || d.getAsNull() || "FLOATORDER".equals(d.getMetaData().getLinkKey())) continue;
                DataField dataField = d.getMetaData().getDataField();
                if (dataField != null && (dataField.getDataFieldType() == DataFieldType.BIGDECIMAL || dataField.getDataFieldType() == DataFieldType.INTEGER)) {
                    BigDecimal asCurrency = d.getAsCurrency();
                    if (asCurrency == null) continue;
                    if (asCurrency.compareTo(BigDecimal.ZERO) != 0) {
                        return new RegionValidateResult(true, 0);
                    }
                    contain0 = true;
                    continue;
                }
                if (d.getAsObject() instanceof String && StringUtils.isNotEmpty((String)d.toString())) {
                    return new RegionValidateResult(true, 0);
                }
                if (d.getAsObject() == null) continue;
                return new RegionValidateResult(true, 0);
            }
        }
        return new RegionValidateResult(!contain0, contain0 ? 2 : 1);
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

    @Override
    public boolean validateForm(List<RegionValidateResult> regionValidateResults) {
        if (CollectionUtils.isEmpty(regionValidateResults)) {
            return false;
        }
        boolean contain0 = false;
        for (RegionValidateResult regionValidateResult : regionValidateResults) {
            if (regionValidateResult.isPass()) {
                if (regionValidateResult.getType() != 0) continue;
                return true;
            }
            contain0 = true;
        }
        return !contain0;
    }
}

