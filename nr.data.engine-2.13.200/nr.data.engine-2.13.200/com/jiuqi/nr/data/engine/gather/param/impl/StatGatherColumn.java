/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.data.DataTypes
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.executors.StatItem
 *  com.jiuqi.np.dataengine.executors.StatUnit
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.nr.data.engine.gather.param.impl;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.DataTypes;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.executors.StatItem;
import com.jiuqi.np.dataengine.executors.StatUnit;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.data.engine.gather.param.CountStringStatUnit;
import com.jiuqi.nr.data.engine.gather.param.IGatherColumn;
import java.math.BigDecimal;

public class StatGatherColumn
implements IGatherColumn {
    private StatUnit sumData;
    private int dataType;
    private int statKind;

    public StatGatherColumn(int statKind, int dataType) {
        this.statKind = statKind;
        this.sumData = statKind == 249 ? new CountStringStatUnit() : StatItem.createStatUnit((int)statKind, (int)dataType);
        this.dataType = dataType;
    }

    public StatGatherColumn(int dataType) {
        this.sumData = this.createStatUnit(dataType);
        this.dataType = dataType;
    }

    @Override
    public int getDataType() {
        return this.dataType;
    }

    @Override
    public AbstractData getAbstractData() {
        return this.sumData.getResult();
    }

    @Override
    public Object readValue() {
        try {
            if (DataTypes.isNum((int)this.sumData.getResultType())) {
                BigDecimal result = this.sumData.getResult().getAsCurrency();
                if (this.dataType == 4) {
                    result = result.setScale(0, 4);
                }
                return result;
            }
            return this.sumData.getResult().getAsObject();
        }
        catch (DataTypeException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void writeValue(Object value) {
        try {
            AbstractData valueOf = AbstractData.valueOf((Object)value, (int)this.dataType);
            this.sumData.statistic(valueOf);
        }
        catch (DataTypeException e) {
            e.printStackTrace();
        }
    }

    private StatUnit createStatUnit(int dataType) {
        if (DataTypes.isNum((int)dataType)) {
            return StatItem.createStatUnit((int)1, (int)dataType);
        }
        return StatItem.createStatUnit((int)6, (int)dataType);
    }

    public int getDataType(QueryContext context) {
        return this.dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public int getStatKind() {
        return this.statKind;
    }

    public void setStatKind(int statKind) {
        this.statKind = statKind;
    }
}

