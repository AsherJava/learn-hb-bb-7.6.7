/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  org.json.JSONObject
 */
package com.jiuqi.nr.unit.uselector.filter.scheme;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.unit.uselector.common.VariableMapDeserializer;
import com.jiuqi.nr.unit.uselector.common.VariableMapSerialize;
import com.jiuqi.nr.unit.uselector.filter.scheme.FilterSchemeInfo;
import org.json.JSONObject;

public class FilterTemplateInfo
extends FilterSchemeInfo {
    private JSONObject template;

    @JsonSerialize(using=VariableMapSerialize.class)
    public JSONObject getTemplate() {
        return this.template;
    }

    @JsonDeserialize(using=VariableMapDeserializer.class)
    public void setTemplate(JSONObject template) {
        this.template = template;
    }
}

