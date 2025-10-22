/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 */
package com.jiuqi.nr.datacrud.impl;

import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.impl.DataValue;
import com.jiuqi.nr.datacrud.impl.RegionDataSet;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RowData
implements IRowData {
    private static final Logger logger = LoggerFactory.getLogger(RowData.class);
    protected RegionDataSet regionDataSet;
    protected String recKey;
    protected List<IDataValue> dataValues;
    protected Map<String, IDataValue> dataValueMap;
    protected Map<String, IDataValue> field2DataValueMap;
    protected DimensionCombination masterDimension;
    protected DimensionCombination dimension;
    protected boolean filledRow;
    protected int detailSeqNum;
    protected int groupingFlag = -2;
    protected int groupTreeDeep = -1;
    protected String parentRecKey;

    public RowData() {
    }

    public RowData(RegionDataSet regionDataSet) {
        this.regionDataSet = regionDataSet;
    }

    public RowData(List<IDataValue> dataValues) {
        this.dataValues = dataValues;
    }

    @Override
    public DimensionCombination getMasterDimension() {
        if (this.masterDimension != null) {
            return this.masterDimension;
        }
        if (this.regionDataSet != null) {
            return this.regionDataSet.getMasterDimension();
        }
        return null;
    }

    public void setMasterDimension(DimensionCombination masterDimension) {
        this.masterDimension = masterDimension;
    }

    @Override
    public DimensionCombination getDimension() {
        if (this.dimension == null && this.getGroupTreeDeep() < 0 && this.regionDataSet != null) {
            String recKey;
            Set<Integer> rowDimIndex = this.regionDataSet.getRowDimIndex();
            DimensionCombinationBuilder builder = new DimensionCombinationBuilder();
            if (rowDimIndex != null) {
                DimensionCombination masterDim = this.getMasterDimension();
                for (FixedDimensionValue fixedDimensionValue : masterDim) {
                    builder.setValue(fixedDimensionValue);
                }
                for (Integer dimIndex : rowDimIndex) {
                    IDataValue iDataValue = this.getDataValues().get(dimIndex);
                    IMetaData metaData = iDataValue.getMetaData();
                    String dimName = this.regionDataSet.getDimNameMap().get(metaData.getFieldKey());
                    if (dimName == null) {
                        logger.warn("\u8868\u5185\u7ef4\u5ea6 {} \u672a\u627e\u5230\u7ef4\u5ea6\u540d", (Object)metaData);
                        continue;
                    }
                    builder.setValue(dimName, "", iDataValue.getAsObject());
                }
            }
            if ((recKey = this.getRecKey()) != null) {
                builder.setValue("RECORDKEY", "", (Object)recKey);
            }
            this.dimension = builder.getCombination();
        }
        return this.dimension;
    }

    public void setDimension(DimensionCombination dimension) {
        this.dimension = dimension;
    }

    @Override
    public List<IDataValue> getLinkDataValues() {
        return this.dataValues;
    }

    @Override
    public IDataValue getDataValueByLink(String link) {
        if (this.dataValues == null || link == null) {
            return null;
        }
        if (this.dataValueMap == null) {
            this.dataValueMap = new HashMap<String, IDataValue>();
            for (IDataValue dataValue : this.dataValues) {
                String linkKey = dataValue.getMetaData().getLinkKey();
                if (linkKey == null) continue;
                this.dataValueMap.put(linkKey, dataValue);
            }
        }
        return this.dataValueMap.get(link);
    }

    @Override
    public IDataValue getDataValueByField(String fieldKey) {
        if (this.dataValues == null || fieldKey == null) {
            return null;
        }
        if (this.field2DataValueMap == null) {
            this.field2DataValueMap = new HashMap<String, IDataValue>();
            for (IDataValue dataValue : this.dataValues) {
                String field = dataValue.getMetaData().getFieldKey();
                if (field == null) continue;
                this.field2DataValueMap.put(field, dataValue);
            }
        }
        return this.field2DataValueMap.get(fieldKey);
    }

    @Override
    public boolean isFilledRow() {
        return this.filledRow;
    }

    public void setFilledRow(boolean filledRow) {
        this.filledRow = filledRow;
    }

    public List<IDataValue> getDataValues() {
        return this.dataValues;
    }

    public void setDataValues(List<IDataValue> dataValues) {
        this.dataValues = dataValues;
    }

    @Override
    public int getDetailSeqNum() {
        return this.detailSeqNum;
    }

    public void setDetailSeqNum(int detailSeqNum) {
        this.detailSeqNum = detailSeqNum;
    }

    @Override
    public int getGroupingFlag() {
        return this.groupingFlag;
    }

    public void setGroupingFlag(int groupingFlag) {
        this.groupingFlag = groupingFlag;
    }

    @Override
    public int getGroupTreeDeep() {
        return this.groupTreeDeep;
    }

    public void setGroupTreeDeep(int groupTreeDeep) {
        this.groupTreeDeep = groupTreeDeep;
    }

    public String getParentRecKey() {
        return this.parentRecKey;
    }

    public void setParentRecKey(String parentRecKey) {
        this.parentRecKey = parentRecKey;
    }

    @Override
    public String getRecKey() {
        return this.recKey;
    }

    public void setRecKey(String recKey) {
        this.recKey = recKey;
    }

    public DataValue initDataValue(IMetaData metaData) {
        return new DataValue(metaData);
    }

    public DataValue initDataValue(int index) {
        IMetaData metaData = this.regionDataSet.getMetaData().get(index);
        return new DataValue(metaData);
    }

    public DataValue appendDataValue(IMetaData metaData) {
        DataValue dataValue = this.initDataValue(metaData);
        Integer index = this.regionDataSet.getMetaIndex().get(metaData);
        this.initDataValues();
        this.dataValues.set(index, dataValue);
        return dataValue;
    }

    public DataValue appendDataValue(int index) {
        DataValue dataValue = this.initDataValue(index);
        this.initDataValues();
        this.dataValues.set(index, dataValue);
        return dataValue;
    }

    public void initDataValues() {
        if (this.dataValues == null) {
            this.dataValues = new ArrayList<IDataValue>();
            for (IMetaData metaDatum : this.regionDataSet.getMetaData()) {
                this.dataValues.add(null);
            }
        }
    }
}

