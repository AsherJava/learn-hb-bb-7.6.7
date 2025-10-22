/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  org.json.JSONObject
 */
package com.jiuqi.nr.uselector.context;

import com.jiuqi.nr.common.params.DimensionValue;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public interface USelectorContext
extends Serializable {
    public static final String DISPLAY = "display";
    public static final String ENTITY_ID = "entityId";
    public static final String PERIOD_ENTITY_ID = "periodEntityId";
    public static final String PERIOD = "period";
    public static final String VER_END_DATE = "verEndDate";
    public static final String FORM_SCHEME = "formScheme";
    public static final String VARIABLE_MAP = "variableMap";
    public static final String CUSTOM_VARIABLE = "customVariable";
    public static final String DIM_VALUE_SET = "dimValueSet";

    public String getSelector();

    public String getDisplay();

    public String getTaskId();

    public String getEntityId();

    public String getPeriodEntityId();

    public String getPeriod();

    public String getVerEndDate();

    public String getFormScheme();

    public List<String> getChecklist();

    public Map<String, String> getVariableMap();

    public JSONObject getCustomVariable();

    public Map<String, DimensionValue> getDimValueSet();
}

