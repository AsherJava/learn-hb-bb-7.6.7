/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.sql.DataTypes
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.executors.StatItem
 *  com.jiuqi.np.dataengine.executors.StatUnit
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.nr.data.engine.grouping.multi;

import com.jiuqi.bi.sql.DataTypes;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.executors.StatItem;
import com.jiuqi.np.dataengine.executors.StatUnit;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.data.engine.grouping.multi.IMultiGroupingColumn;

public class StatGroupingColumn
implements IMultiGroupingColumn {
    private StatUnit sumData;
    private int dataType;
    private int statKind;

    public StatGroupingColumn(int statKind, int dataType) {
        this.statKind = statKind;
        this.sumData = StatItem.createStatUnit((int)statKind, (int)dataType);
        this.dataType = dataType;
    }

    public StatGroupingColumn(int dataType) {
        this.sumData = this.createStatUnit(dataType);
        this.dataType = dataType;
    }

    @Override
    public Object readValue(QueryContext context) {
        try {
            return this.sumData.getResult().getAsObject();
        }
        catch (DataTypeException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void writeValue(QueryContext context, Object value) {
        try {
            AbstractData valueOf = AbstractData.valueOf((Object)value, (int)this.dataType);
            this.sumData.statistic(valueOf);
        }
        catch (DataTypeException e) {
            e.printStackTrace();
        }
    }

    private StatUnit createStatUnit(int dataType) {
        if (DataTypes.isNumber((int)dataType)) {
            return StatItem.createStatUnit((int)1, (int)dataType);
        }
        return StatItem.createStatUnit((int)6, (int)dataType);
    }

    @Override
    public int getDataType(QueryContext context) {
        return this.dataType;
    }

    @Override
    public void writeValue(Object value) {
        AbstractData valueOf = AbstractData.valueOf((Object)value, (int)this.dataType);
        this.sumData.statistic(valueOf);
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

