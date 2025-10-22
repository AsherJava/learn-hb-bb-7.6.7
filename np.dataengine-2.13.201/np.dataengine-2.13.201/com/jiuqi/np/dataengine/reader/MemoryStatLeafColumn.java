/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.common.AggrType
 */
package com.jiuqi.np.dataengine.reader;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.StatItem;
import com.jiuqi.np.dataengine.executors.StatUnit;
import com.jiuqi.np.dataengine.reader.QueryFieldInfo;
import com.jiuqi.nvwa.definition.common.AggrType;

public class MemoryStatLeafColumn {
    private QueryFieldInfo queryFieldInfo;
    private StatUnit statUnit;
    private Object value;

    public MemoryStatLeafColumn(QueryFieldInfo queryFieldInfo) {
        this.queryFieldInfo = queryFieldInfo;
        this.createStatUnit();
    }

    public Object getValue() {
        if (this.statUnit != null) {
            this.value = this.statUnit.getResult().getAsObject();
        }
        return this.value;
    }

    public void statistic(Object value) {
        if (this.statUnit != null) {
            this.statUnit.statistic(AbstractData.valueOf(value, this.queryFieldInfo.queryField.getDataType()));
        }
    }

    private void createStatUnit() {
        AggrType aggrType = this.queryFieldInfo.fieldDefine.getAggrType();
        int dataType = this.queryFieldInfo.queryField.getDataType();
        if (aggrType == null) {
            aggrType = AggrType.MIN;
        }
        switch (aggrType) {
            case NONE: {
                break;
            }
            case SUM: {
                this.statUnit = StatItem.createStatUnit(1, dataType);
                break;
            }
            case MAX: {
                this.statUnit = StatItem.createStatUnit(4, dataType);
                break;
            }
            case MIN: {
                this.statUnit = StatItem.createStatUnit(5, dataType);
                break;
            }
            case COUNT: {
                this.statUnit = StatItem.createStatUnit(2, dataType);
                break;
            }
            case AVERAGE: {
                this.statUnit = StatItem.createStatUnit(3, dataType);
                break;
            }
            default: {
                this.statUnit = StatItem.createStatUnit(5, dataType);
            }
        }
    }
}

