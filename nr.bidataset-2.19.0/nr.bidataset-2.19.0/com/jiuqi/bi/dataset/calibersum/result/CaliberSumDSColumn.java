/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.field.AggregationType
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.StatItem
 *  com.jiuqi.np.dataengine.executors.StatUnit
 */
package com.jiuqi.bi.dataset.calibersum.result;

import com.jiuqi.bi.dataset.calibersum.model.CaliberSumDSField;
import com.jiuqi.bi.dataset.model.field.AggregationType;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.StatItem;
import com.jiuqi.np.dataengine.executors.StatUnit;

public class CaliberSumDSColumn {
    private StatUnit statUnit;
    private CaliberSumDSField dsField;
    private Object value;

    public CaliberSumDSColumn() {
    }

    public CaliberSumDSColumn(CaliberSumDSField dsField) {
        this.dsField = dsField;
        this.createStatUnit();
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getValue() {
        if (this.statUnit != null) {
            this.value = this.statUnit.getResult().getAsObject();
        }
        return this.value;
    }

    public void statistic(Object value) {
        this.statUnit.statistic(AbstractData.valueOf((Object)value, (int)this.dsField.getValType()));
    }

    public Object adjust(Object value) {
        this.getValue();
        this.statUnit.reset();
        this.statUnit.statistic(AbstractData.valueOf((Object)this.value, (int)this.dsField.getValType()));
        this.statUnit.statistic(AbstractData.valueOf((Object)value, (int)this.dsField.getValType()));
        return this.getValue();
    }

    private void createStatUnit() {
        if (this.dsField.getColumnModel() == null) {
            if (StringUtils.isNotEmpty((String)this.dsField.getEvalExpression()) && !this.dsField.isDestEval()) {
                this.statUnit = StatItem.createStatUnit((int)1, (int)this.dsField.getValType());
            }
            return;
        }
        AggregationType aggrType = this.dsField.getAggregation();
        switch (aggrType) {
            case SUM: {
                this.statUnit = StatItem.createStatUnit((int)1, (int)this.dsField.getValType());
                break;
            }
            case MAX: {
                this.statUnit = StatItem.createStatUnit((int)4, (int)this.dsField.getValType());
                break;
            }
            case MIN: {
                this.statUnit = StatItem.createStatUnit((int)5, (int)this.dsField.getValType());
                break;
            }
            case COUNT: {
                this.statUnit = StatItem.createStatUnit((int)2, (int)this.dsField.getValType());
                break;
            }
            case AVG: {
                this.statUnit = StatItem.createStatUnit((int)3, (int)this.dsField.getValType());
            }
        }
    }
}

