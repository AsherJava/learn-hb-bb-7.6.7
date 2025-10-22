/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  org.json.JSONObject
 */
package com.jiuqi.nr.form.selector.context;

import com.jiuqi.nr.common.params.DimensionValue;
import java.util.Map;
import org.json.JSONObject;

public interface IFormQueryContext {
    public String getTaskKey();

    public String getFormSchemeKey();

    public String getPeriod();

    public Map<String, DimensionValue> getDimValueSet();

    public JSONObject getCustomVariable();
}

