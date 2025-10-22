/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.nr.datascheme.api.DataField
 */
package com.jiuqi.nr.datacrud.impl.crud.strategy;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.nr.datacrud.impl.MetaData;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.service.DataEngineService;
import com.jiuqi.nr.datascheme.api.DataField;
import org.springframework.util.StringUtils;

public class DefaultValueEvaluate {
    protected final DataEngineService dataEngineService;

    public DefaultValueEvaluate(DataEngineService dataEngineService) {
        this.dataEngineService = dataEngineService;
    }

    protected AbstractData evaluateDefaultValue(ExecutorContext context, RegionRelation relation, DimensionValueSet rowKeys, MetaData metaData) {
        DataField dataField = metaData.getDataField();
        if (!StringUtils.hasLength(dataField.getDefaultValue())) {
            return null;
        }
        AbstractData value = null;
        if (metaData.getDataType() != 6) {
            AbstractData dev = this.dataEngineService.expressionEvaluate(dataField.getDefaultValue(), context, rowKeys, relation);
            if (dev != null) {
                value = AbstractData.valueOf((Object)dev.getAsObject(), (int)metaData.getDataType());
            }
        } else {
            String defaultValue = dataField.getDefaultValue();
            if (defaultValue.matches("-?\\d+(\\.\\d+)?") || defaultValue.matches("-?\\d{1,3}(,\\d{3})*(\\.\\d+)?")) {
                value = AbstractData.valueOf((String)dataField.getDefaultValue());
            } else {
                AbstractData dev = this.dataEngineService.expressionEvaluate(defaultValue, context, rowKeys, relation);
                if (dev != null) {
                    value = AbstractData.valueOf((Object)dev.getAsObject(), (int)metaData.getDataType());
                }
            }
        }
        return value;
    }
}

