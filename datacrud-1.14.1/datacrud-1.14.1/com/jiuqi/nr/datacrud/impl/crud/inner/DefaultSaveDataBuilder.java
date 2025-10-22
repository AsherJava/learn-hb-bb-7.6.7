/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 */
package com.jiuqi.nr.datacrud.impl.crud.inner;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.nr.datacrud.ParseReturnRes;
import com.jiuqi.nr.datacrud.ReturnRes;
import com.jiuqi.nr.datacrud.SaveDataBuilder;
import com.jiuqi.nr.datacrud.SaveDataBuilderFactory;
import com.jiuqi.nr.datacrud.SaveReturnRes;
import com.jiuqi.nr.datacrud.SaveRowData;
import com.jiuqi.nr.datacrud.impl.Const;
import com.jiuqi.nr.datacrud.impl.MetaData;
import com.jiuqi.nr.datacrud.impl.crud.inner.BaseSaveDataBuilder;
import com.jiuqi.nr.datacrud.impl.out.CrudException;
import com.jiuqi.nr.datacrud.impl.out.ReturnResInstance;
import com.jiuqi.nr.datacrud.spi.TypeParseStrategy;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultSaveDataBuilder
extends BaseSaveDataBuilder {
    private static final Logger logger = LoggerFactory.getLogger(DefaultSaveDataBuilder.class);
    private SaveRowData currRow = null;

    public DefaultSaveDataBuilder(String regionKey, DimensionCombination dimension, SaveDataBuilderFactory beanGetter) {
        super(regionKey, dimension, beanGetter);
        this.resetRegionKey(this.regionKey);
    }

    @Override
    public SaveDataBuilder resetRegionKey(String regionKey) throws CrudException {
        super.resetRegionKey(regionKey);
        this.currRow = null;
        return this;
    }

    @Override
    public ReturnRes addRow(DimensionCombination combination, int type) {
        SaveReturnRes returnRes = this.checkDimension(combination = this.completionDimension(combination), type = this.fixRowType(type));
        if (returnRes != null) {
            return returnRes;
        }
        this.currRow = new SaveRowData();
        this.currRow.setRowIndex(this.saveData.getRowCount());
        this.currRow.setType(type);
        this.currRow.setCombination(combination);
        this.currRow.setSaveData(this.saveData);
        if (type != 3) {
            Object[] abstractData = new AbstractData[this.saveData.getLinks().size()];
            Arrays.fill(abstractData, (Object)Const.UNMODIFIED_VALUE);
            this.currRow.setLinkValues((AbstractData[])abstractData);
        }
        this.saveData.getTypeRows(type).add(this.currRow);
        for (TypeParseStrategy value : this.typeParseStrategyEnumMap.values()) {
            value.setRowKey(combination);
        }
        this.defaultParseStrategy.setRowKey(combination);
        this.nonTypeParseStrategy.setRowKey(combination);
        return ReturnResInstance.OK_INSTANCE;
    }

    protected int fixRowType(int type) {
        DataTable dataTable = this.regionRelation.getDataTable();
        if (dataTable == null) {
            return type;
        }
        boolean repeatCode = dataTable.isRepeatCode();
        if (repeatCode && type == 0) {
            type = 1;
        }
        return type;
    }

    @Override
    public ReturnRes setData(int index, Object data) {
        DataFieldType dataType;
        AbstractData[] linkValues = this.currRow.getLinkValues();
        if (linkValues == null) {
            return ReturnResInstance.ERR_INSTANCE;
        }
        if (index > linkValues.length || index < 0) {
            return ReturnRes.build(1001, "\u94fe\u63a5\u4e0d\u5b58\u5728\u6216\u5df2\u4e22\u5931");
        }
        String link = this.saveData.getLinks().get(index);
        DataField dataField = null;
        DataLinkDefine linkDefine = null;
        IFMDMAttribute fmAttribute = null;
        MetaData metaData = this.regionRelation.getMetaDataByLink(link);
        if (metaData != null) {
            dataType = metaData.getDataFieldType();
        } else if (this.floatOrderLinkIndex == index) {
            dataType = DataFieldType.BIGDECIMAL;
            dataField = this.regionRelation.getFloatOrderField();
        } else {
            dataType = null;
        }
        try {
            ParseReturnRes res;
            TypeParseStrategy typeParseStrategy;
            if (dataType != null) {
                typeParseStrategy = (TypeParseStrategy)this.typeParseStrategyEnumMap.get(dataType);
                if (typeParseStrategy == null) {
                    typeParseStrategy = this.defaultParseStrategy;
                }
            } else {
                typeParseStrategy = this.nonTypeParseStrategy;
            }
            if (metaData != null) {
                dataField = metaData.getDataField();
                linkDefine = metaData.getDataLinkDefine();
                fmAttribute = metaData.getFmAttribute();
            }
            if ((res = fmAttribute != null ? typeParseStrategy.checkParse(linkDefine, fmAttribute, data) : typeParseStrategy.checkParse(linkDefine, dataField, data)).isSuccess()) {
                linkValues[index] = res.getAbstractData();
                if (dataField != null) {
                    this.pkUpdate(data, dataField);
                }
            }
            return res;
        }
        catch (Exception e) {
            logger.warn("\u4fdd\u5b58\u6570\u636e,\u8f6c\u6362\u6570\u636e\u5931\u8d25", e);
            throw new CrudException(4601);
        }
    }

    private void pkUpdate(Object data, DataField dataField) {
        if (dataField.getDataFieldKind() == DataFieldKind.TABLE_FIELD_DIM) {
            boolean pkUpdate;
            Map<DataField, String> dimensionNameFieldMap = this.getField2DimensionName();
            String dimName = dimensionNameFieldMap.get(dataField);
            DimensionCombination oldMasterKey = this.currRow.getCombination();
            Object keyValue = oldMasterKey.getValue(dimName);
            boolean bl = pkUpdate = !Objects.equals(keyValue, data);
            if (pkUpdate) {
                DimensionValueSet newKey = this.currRow.getNewKey();
                if (newKey == null) {
                    DimensionValueSet oldKey = oldMasterKey.toDimensionValueSet();
                    newKey = new DimensionValueSet(oldKey);
                    this.currRow.setNewKey(newKey);
                }
                newKey.setValue(dimName, data);
            }
        }
    }
}

