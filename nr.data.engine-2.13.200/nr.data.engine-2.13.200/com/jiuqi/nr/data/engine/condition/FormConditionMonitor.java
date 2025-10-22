/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$DataEngineRunType
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 *  com.jiuqi.np.dataengine.node.CheckExpression
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.nr.data.engine.condition;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.node.CheckExpression;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FormConditionMonitor
extends AbstractMonitor {
    private Map<DimensionValueSet, Set<String>> conditionResultList = new HashMap<DimensionValueSet, Set<String>>();
    private List<String> entityDimensions;

    public FormConditionMonitor() {
        super(DataEngineConsts.DataEngineRunType.JUDGE);
    }

    public void error(CheckExpression expression, QueryContext context) throws SyntaxException, DataTypeException {
        Set<String> formSet;
        DimensionValueSet rowKey = new DimensionValueSet(context.getRowKey());
        if (!rowKey.hasValue("DATATIME")) {
            rowKey.setValue("DATATIME", context.getDimensionValue("DATATIME"));
        }
        DimensionValueSet masterKeys = new DimensionValueSet();
        if (this.entityDimensions != null) {
            for (String entityDimension : this.entityDimensions) {
                Object value = rowKey.getValue(entityDimension);
                if (null == value) continue;
                masterKeys.setValue(entityDimension, value);
            }
        } else {
            masterKeys = rowKey;
        }
        if ((formSet = this.conditionResultList.get(masterKeys)) == null) {
            formSet = new HashSet<String>();
            this.conditionResultList.put(masterKeys, formSet);
        }
        formSet.add(expression.getSource().getFormKey());
    }

    public Map<DimensionValueSet, Set<String>> getConditionResultList() {
        return this.conditionResultList;
    }

    public void setEntityDimensions(List<String> entityDims) {
        if (entityDims == null || entityDims.size() <= 0) {
            return;
        }
        this.entityDimensions = new ArrayList<String>(entityDims);
    }
}

