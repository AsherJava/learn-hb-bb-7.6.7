/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$DataEngineRunType
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$DebugLogType
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 *  com.jiuqi.np.dataengine.node.CheckExpression
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.nr.data.access.extend.param;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.node.CheckExpression;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.springframework.util.ObjectUtils;

public class BatchConditionMonitor
extends AbstractMonitor {
    private Map<DimensionValueSet, Set<String>> conditionResultList = new HashMap<DimensionValueSet, Set<String>>();
    private Set<String> dimNames = new HashSet<String>();
    private List<DimensionValueSet> allDimValues = new ArrayList<DimensionValueSet>();
    private Map<String, Map<Object, BitSet>> valueIndex = new HashMap<String, Map<Object, BitSet>>();

    public BatchConditionMonitor() {
        super(DataEngineConsts.DataEngineRunType.JUDGE);
    }

    public void error(CheckExpression expression, QueryContext context) throws SyntaxException, DataTypeException {
        DimensionValueSet rowKey = new DimensionValueSet(context.getRowKey());
        ArrayList<String> periodValues = new ArrayList<String>();
        Object periodValue = context.getDimensionValue("DATATIME");
        if (periodValue instanceof String) {
            periodValues.add((String)periodValue);
        } else if (periodValue instanceof Collection) {
            periodValues.addAll((List)periodValue);
        }
        ArrayList<DimensionValueSet> rowKeySplitByDate = new ArrayList<DimensionValueSet>();
        for (String dimensionValue : periodValues) {
            DimensionValueSet item = new DimensionValueSet(rowKey);
            item.setValue("DATATIME", (Object)dimensionValue);
            rowKeySplitByDate.add(item);
        }
        for (DimensionValueSet item : rowKeySplitByDate) {
            int i;
            DimensionValueSet dimensionValueSet = new DimensionValueSet();
            for (String dimName : this.dimNames) {
                Object value = item.getValue(dimName);
                if (ObjectUtils.isEmpty(value)) continue;
                dimensionValueSet.setValue(dimName, value);
            }
            BitSet result = new BitSet(this.allDimValues.size());
            result.set(0, this.allDimValues.size(), true);
            for (i = 0; i < dimensionValueSet.size(); ++i) {
                String dim = dimensionValueSet.getName(i);
                Object targetValue = dimensionValueSet.getValue(i);
                Map<Object, BitSet> dimIndex = this.valueIndex.get(dim);
                if (dimIndex == null) continue;
                BitSet matchBits = dimIndex.get(targetValue);
                if (matchBits == null) {
                    result.clear();
                    break;
                }
                result.and(matchBits);
                if (result.isEmpty()) break;
            }
            i = result.nextSetBit(0);
            while (i >= 0) {
                DimensionValueSet valueSet = this.allDimValues.get(i);
                Set formKeys = this.conditionResultList.computeIfAbsent(valueSet, k -> new HashSet());
                formKeys.add(expression.getSource().getFormKey());
                i = result.nextSetBit(i + 1);
            }
        }
    }

    public Map<DimensionValueSet, Set<String>> getConditionResultList() {
        return this.conditionResultList;
    }

    public void message(String msg, Object sender) {
    }

    public void onProgress(double progress) {
    }

    public void debug(String msg, DataEngineConsts.DebugLogType type) {
    }

    public void exception(Exception e) {
        this.errorMessage.add(e.getMessage());
        Logger logger = this.getLogger();
        if (logger.isDebugEnabled()) {
            logger.debug(e.getMessage(), e);
        }
    }

    public Set<String> getDimNames() {
        return this.dimNames;
    }

    public void setDimNames(Set<String> dimNames) {
        this.dimNames = dimNames;
    }

    public void setAllDimValues(List<DimensionValueSet> allDimValues) {
        this.allDimValues = allDimValues;
        this.rebuildIndex();
    }

    private void rebuildIndex() {
        this.valueIndex.clear();
        for (String dim : this.dimNames) {
            HashMap<Object, BitSet> dimMap = new HashMap<Object, BitSet>();
            for (int i = 0; i < this.allDimValues.size(); ++i) {
                Object value = this.allDimValues.get(i).getValue(dim);
                dimMap.computeIfAbsent(value, k -> new BitSet()).set(i);
            }
            this.valueIndex.put(dim, dimMap);
        }
    }
}

