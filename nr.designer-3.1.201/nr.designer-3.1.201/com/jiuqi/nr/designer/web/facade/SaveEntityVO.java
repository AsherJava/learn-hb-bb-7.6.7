/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.facade;

import com.jiuqi.nr.designer.web.facade.EntityObj;

public class SaveEntityVO {
    private EntityObj[] entities;
    private String schemeKey;

    public EntityObj[] getEntities() {
        return this.entities;
    }

    public void setEntities(EntityObj[] entities) {
        this.entities = entities;
    }

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }
}

