/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.internal.entity;

import com.jiuqi.nr.zb.scheme.core.PropLink;
import com.jiuqi.nr.zb.scheme.internal.anno.DBAnno;

@DBAnno.DBTable(dbTable="NR_ZB_SCHEME_PROP")
public class PropLinkDO
implements PropLink {
    @DBAnno.DBField(dbField="ZSP_KEY", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="ZSP_SCHEME_KEY")
    private String schemeKey;
    @DBAnno.DBField(dbField="ZSP_PROP_KEY")
    private String propKey;
    @DBAnno.DBField(dbField="ZSP_LEVEL")
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

