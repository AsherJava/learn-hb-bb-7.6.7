/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.infc.INRContext
 *  org.json.JSONObject
 */
package com.jiuqi.nr.tag.management.environment;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.tag.management.environment.ITagConfigContext;
import java.util.Map;
import org.json.JSONObject;

public class BaseTagContextData
implements ITagConfigContext,
INRContext {
    private String entityId;
    private String period;
    private Map<String, DimensionValue> dimValueSet;
    private JSONObject customVariable;
    private String contextEntityId;
    private String contextFilterExpression;

    @Override
    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    @Override
    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    @Override
    public Map<String, DimensionValue> getDimValueSet() {
        return this.dimValueSet;
    }

    public void setDimValueSet(Map<String, DimensionValue> dimValueSet) {
        this.dimValueSet = dimValueSet;
    }

    @Override
    public JSONObject getCustomVariable() {
        return this.customVariable;
    }

    public void setCustomVariable(JSONObject customVariable) {
        this.customVariable = customVariable;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }
}

