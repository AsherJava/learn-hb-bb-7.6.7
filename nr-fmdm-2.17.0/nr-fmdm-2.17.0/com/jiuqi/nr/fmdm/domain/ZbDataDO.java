/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.fmdm.domain;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.fmdm.domain.EntityDataDO;
import java.util.Map;
import org.springframework.util.Assert;

public class ZbDataDO
extends EntityDataDO {
    private IDataRow dataRow;
    private Map<String, Integer> codeToIndex;

    public ZbDataDO(IDataRow dataRow, Map<String, Integer> codeToIndex, String entityKey) {
        super(null, entityKey);
        this.codeToIndex = codeToIndex;
        this.dataRow = dataRow;
    }

    public ZbDataDO(IDataRow dataRow, Map<String, Integer> codeToIndex, IEntityRow entityRow, String entityKey) {
        super(entityRow, entityKey);
        this.codeToIndex = codeToIndex;
        this.dataRow = dataRow;
    }

    public ZbDataDO(IEntityRow entityRow) {
        super(entityRow);
    }

    @Override
    public AbstractData getValue(String code) {
        Assert.notNull((Object)code, "parameter 'code' can not be empty.");
        Integer rowIndex = this.getRowIndex(code);
        if (rowIndex == null) {
            return super.getValue(code);
        }
        return this.getValueByIndex(code, rowIndex, this.dataRow);
    }

    protected AbstractData getValueByIndex(String code, Integer rowIndex, IDataRow dataRow) {
        if (dataRow == null) {
            return AbstractData.empty();
        }
        com.jiuqi.np.dataengine.data.AbstractData value = dataRow.getValue(rowIndex.intValue());
        return AbstractData.valueOf((Object)value.getAsObject(), (int)value.dataType);
    }

    @Override
    public AbstractData getDataValue(String code) {
        Assert.notNull((Object)code, "parameter 'code' can not be empty.");
        if (this.dataRow == null) {
            return AbstractData.empty();
        }
        Integer rowIndex = this.getRowIndex(code);
        return this.getDataValueByIndex(code, rowIndex, this.dataRow);
    }

    protected AbstractData getDataValueByIndex(String code, Integer rowIndex, IDataRow dataRow) {
        if (rowIndex == null) {
            throw new IllegalArgumentException("\u627e\u4e0d\u5230\u6307\u6807:" + code);
        }
        com.jiuqi.np.dataengine.data.AbstractData value = dataRow.getValue(rowIndex.intValue());
        return AbstractData.valueOf((Object)value.getAsObject(), (int)value.dataType);
    }

    private Integer getRowIndex(String code) {
        if (this.codeToIndex == null) {
            return null;
        }
        return this.codeToIndex.get(code);
    }

    public void setCodeToIndex(Map<String, Integer> codeToIndex) {
        this.codeToIndex = codeToIndex;
    }

    public Map<String, Integer> getCodeToIndex() {
        return this.codeToIndex;
    }

    @Override
    public Object getDataAsObject(String code) {
        return this.getDataValue(code).getAsObject();
    }

    @Override
    public DimensionValueSet getMasterKey() {
        if (this.dataRow == null) {
            return super.getMasterKey();
        }
        return this.dataRow.getRowKeys();
    }

    public void setDataRow(IDataRow dataRow) {
        this.dataRow = dataRow;
    }

    public IDataRow getDataRow() {
        return this.dataRow;
    }
}

