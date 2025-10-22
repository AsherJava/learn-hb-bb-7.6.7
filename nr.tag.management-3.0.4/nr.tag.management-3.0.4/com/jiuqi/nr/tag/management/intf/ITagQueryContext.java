/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  org.json.JSONObject
 */
package com.jiuqi.nr.tag.management.intf;

import com.jiuqi.nr.common.params.DimensionValue;
import java.util.Map;
import org.json.JSONObject;

public interface ITagQueryContext {
    public String getFormScheme();

    public String getPeriod();

    public String getEntityId();

    public JSONObject getCustomVariable();

    public Map<String, DimensionValue> getDimValueSet();
}

