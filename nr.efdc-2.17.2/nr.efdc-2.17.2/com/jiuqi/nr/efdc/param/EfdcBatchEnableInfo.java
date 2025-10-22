/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.infc.INRContext
 */
package com.jiuqi.nr.efdc.param;

import com.jiuqi.nr.context.infc.INRContext;
import java.util.List;
import java.util.Map;

public class EfdcBatchEnableInfo
implements INRContext {
    private List<String> linkKeys;
    private String formSchemeKey;
    private List<Map<String, String>> dimsList;
    private String contextEntityId;
    private String contextFilterExpression;

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }

    public List<String> getLinkKeys() {
        return this.linkKeys;
    }

    public void setLinkKeys(List<String> linkKeys) {
        this.linkKeys = linkKeys;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public List<Map<String, String>> getDimsList() {
        return this.dimsList;
    }

    public void setDimsList(List<Map<String, String>> dimsList) {
        this.dimsList = dimsList;
    }
}

