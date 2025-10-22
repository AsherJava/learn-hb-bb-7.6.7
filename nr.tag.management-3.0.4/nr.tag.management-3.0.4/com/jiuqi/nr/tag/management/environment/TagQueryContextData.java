/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  org.json.JSONObject
 */
package com.jiuqi.nr.tag.management.environment;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.tag.management.intf.ITagQueryContext;
import java.util.Map;
import org.json.JSONObject;

public class TagQueryContextData
implements ITagQueryContext {
    private String formScheme;
    private String period;
    private String entityId;
    private Map<String, DimensionValue> dimValueSet;
    private JSONObject customVariable;

    @Override
    public String getFormScheme() {
        return this.formScheme;
    }

    public void setFormScheme(String formScheme) {
        this.formScheme = formScheme;
    }

    @Override
    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    @Override
    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
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
}

