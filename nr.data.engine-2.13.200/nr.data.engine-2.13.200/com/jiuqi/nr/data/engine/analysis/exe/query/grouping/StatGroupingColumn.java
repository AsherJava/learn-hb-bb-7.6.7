/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.sql.DataTypes
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.executors.StatItem
 *  com.jiuqi.np.dataengine.executors.StatUnit
 */
package com.jiuqi.nr.data.engine.analysis.exe.query.grouping;

import com.jiuqi.bi.sql.DataTypes;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.executors.StatItem;
import com.jiuqi.np.dataengine.executors.StatUnit;
import com.jiuqi.nr.data.engine.analysis.exe.AnalysisContext;
import com.jiuqi.nr.data.engine.analysis.exe.query.grouping.IAnalysisGroupingColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatGroupingColumn
implements IAnalysisGroupingColumn {
    private static final Logger logger = LoggerFactory.getLogger(StatGroupingColumn.class);
    private StatUnit sumData;
    private int dataType;

    public StatGroupingColumn(int dataType) {
        this.sumData = this.createStatUnit(dataType);
        this.dataType = dataType;
    }

    @Override
    public Object readValue(AnalysisContext context) {
        try {
            return this.sumData.getResult().getAsObject();
        }
        catch (DataTypeException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public void writeValue(AnalysisContext context, Object value) {
        try {
            AbstractData valueOf = AbstractData.valueOf((Object)value, (int)this.dataType);
            this.sumData.statistic(valueOf);
        }
        catch (DataTypeException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private StatUnit createStatUnit(int dataType) {
        if (DataTypes.isNumber((int)dataType)) {
            return StatItem.createStatUnit((int)1, (int)dataType);
        }
        return StatItem.createStatUnit((int)6, (int)dataType);
    }
}

