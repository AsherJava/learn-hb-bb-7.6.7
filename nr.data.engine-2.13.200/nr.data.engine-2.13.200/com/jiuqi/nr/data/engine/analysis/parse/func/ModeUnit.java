/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.data.ObjectData
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.executors.StatUnit
 */
package com.jiuqi.nr.data.engine.analysis.parse.func;

import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.ObjectData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.executors.StatUnit;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModeUnit
implements StatUnit {
    private static final Logger logger = LoggerFactory.getLogger(ModeUnit.class);
    private int maxCount = 1;
    private Map<Object, Integer> countMap = new HashMap<Object, Integer>();
    private Map<Integer, HashSet<Object>> valueCounts = new HashMap<Integer, HashSet<Object>>();
    private AbstractData result = null;

    public int getStatKind() {
        return -1;
    }

    public int getResultType() {
        return 11;
    }

    public AbstractData getResult() {
        if (this.result == null) {
            ArrayData array = new ArrayData(this.maxCount, (Collection)this.valueCounts.get(this.maxCount));
            this.result = new ObjectData((Object)array);
        }
        return this.result;
    }

    public void reset() {
        this.countMap.clear();
        this.valueCounts.clear();
        this.maxCount = 1;
    }

    public void statistic(AbstractData value) {
        if (!value.isNull) {
            try {
                HashSet<Object> valueSet;
                Object valueObj = value.getAsObject();
                Integer count = this.countMap.get(valueObj);
                if (count == null) {
                    count = 1;
                } else {
                    Integer n = count;
                    Integer n2 = count = Integer.valueOf(count + 1);
                }
                this.countMap.put(valueObj, count);
                if (count > this.maxCount) {
                    valueSet = this.valueCounts.get(this.maxCount);
                    valueSet.remove(valueObj);
                    this.maxCount = count;
                }
                if ((valueSet = this.valueCounts.get(count)) == null) {
                    valueSet = new HashSet();
                    this.valueCounts.put(count, valueSet);
                }
                valueSet.add(valueObj);
                if (this.maxCount > 1) {
                    ArrayData array = new ArrayData(this.maxCount, (Collection)this.valueCounts.get(this.maxCount));
                    this.result = new ObjectData((Object)array);
                } else {
                    this.result = new ObjectData((Object)new ArrayData(this.maxCount, 0));
                }
            }
            catch (DataTypeException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}

