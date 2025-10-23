/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.internal.dto;

import com.jiuqi.nr.zb.scheme.core.PropLink;

public class PropLinkDTO
implements PropLink {
    private String key;
    private String schemeKey;
    private String propKey;
    private String level;

    @Override
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    @Override
    public String getPropKey() {
        return this.propKey;
    }

    public void setPropKey(String propKey) {
        this.propKey = propKey;
    }

    @Override
    public String getLevel() {
        return this.level;
    }

    @Override
    public void setLevel(String level) {
        this.level = level;
    }
}

