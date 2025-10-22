/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 */
package com.jiuqi.np.definition.internal.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Map;

public class FormatProperties {
    private Integer formatType;
    private String pattern;
    private Map<String, String> properties;

    public String getPattern() {
        return this.pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public Map<String, String> getProperties() {
        return this.properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public Integer getFormatType() {
        return this.formatType;
    }

    public void setFormatType(Integer formatType) {
        this.formatType = formatType;
    }

    @JsonIgnore
    public String getProperty(String key) {
        if (this.properties != null && key != null) {
            return this.properties.get(key);
        }
        return null;
    }
}

