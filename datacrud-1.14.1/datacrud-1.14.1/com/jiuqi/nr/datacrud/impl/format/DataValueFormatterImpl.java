/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 */
package com.jiuqi.nr.datacrud.impl.format;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.api.DataValueFormatter;
import com.jiuqi.nr.datacrud.common.DataTypeConvert;
import com.jiuqi.nr.datacrud.impl.MetaData;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.RegionRelationFactory;
import com.jiuqi.nr.datacrud.spi.TypeFormatStrategy;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataValueFormatterImpl
implements DataValueFormatter {
    private final EnumMap<DataFieldType, TypeFormatStrategy> typeFormatStrategyEnumMap;
    private final TypeFormatStrategy defaultTypeStrategy;
    private final RegionRelationFactory regionRelationFactory;
    private final Map<String, RegionRelation> relationMap = new HashMap<String, RegionRelation>();

    public DataValueFormatterImpl(EnumMap<DataFieldType, TypeFormatStrategy> typeFormatStrategyEnumMap, TypeFormatStrategy defaultTypeStrategy, RegionRelationFactory regionRelationFactory) {
        this.typeFormatStrategyEnumMap = typeFormatStrategyEnumMap;
        this.defaultTypeStrategy = defaultTypeStrategy;
        this.regionRelationFactory = regionRelationFactory;
    }

    @Override
    public String format(IDataValue value) {
        TypeFormatStrategy typeFormatStrategy;
        IMetaData metaData = value.getMetaData();
        DataLinkDefine dataLinkDefine = metaData.getDataLinkDefine();
        if (dataLinkDefine == null) {
            return value.getAsString();
        }
        AbstractData abstractData = value.getAbstractData();
        DataFieldType dataFieldType = metaData.getDataFieldType();
        if (dataFieldType == null && abstractData != null) {
            dataFieldType = DataTypeConvert.dataType2DataFieldType(abstractData.dataType);
        }
        if (dataFieldType == null) {
            typeFormatStrategy = this.defaultTypeStrategy;
        } else {
            typeFormatStrategy = this.typeFormatStrategyEnumMap.get(dataFieldType);
            if (typeFormatStrategy == null) {
                typeFormatStrategy = this.defaultTypeStrategy;
            }
        }
        IRowData rowData = value.getRowData();
        if (rowData != null) {
            typeFormatStrategy.setRowKey(rowData.getMasterDimension());
            typeFormatStrategy.setRowData(rowData);
        }
        return typeFormatStrategy.format(metaData, abstractData);
    }

    @Override
    public String format(String linkKey, Object value) {
        RegionRelation relation = this.relationMap.get(linkKey);
        if (relation == null) {
            relation = this.regionRelationFactory.getRegionRelationByLinkKey(linkKey);
            List<MetaData> cells = relation.getMetaData(null);
            for (MetaData cell : cells) {
                this.relationMap.put(cell.getLinkKey(), relation);
            }
        }
        MetaData metaData = relation.getMetaDataByLink(linkKey);
        DataField dataField = metaData.getDataField();
        DataFieldType type = metaData.getDataFieldType();
        TypeFormatStrategy typeFormatStrategy = this.typeFormatStrategyEnumMap.get(type);
        if (typeFormatStrategy == null) {
            typeFormatStrategy = this.defaultTypeStrategy;
        }
        int typeValue = DataTypeConvert.dataFieldType2DataType(type.getValue());
        return typeFormatStrategy.format(metaData.getDataLinkDefine(), dataField, AbstractData.valueOf((Object)value, (int)typeValue));
    }

    @Override
    public List<String> format(IRowData row) {
        List<IDataValue> linkDataValues = row.getLinkDataValues();
        ArrayList<String> list = new ArrayList<String>();
        for (IDataValue linkDataValue : linkDataValues) {
            list.add(this.format(linkDataValue));
        }
        return list;
    }
}

