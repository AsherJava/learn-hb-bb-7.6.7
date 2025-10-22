/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  org.json.JSONObject
 */
package com.jiuqi.nr.lwtree.para;

import com.jiuqi.nr.common.params.DimensionValue;
import java.util.Map;
import org.json.JSONObject;

public interface ITreeLoadPara {
    public String getViewKey();

    public String getPeriod();

    public String getvStartDate();

    public String getvEndDate();

    public String getFormSchemeKey();

    public String getParentKey();

    public Map<String, DimensionValue> getDimValueSet();

    public JSONObject getCustomVariable();

    public String getExpression();
}

