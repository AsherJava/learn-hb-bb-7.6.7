/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.infc.INRContext
 */
package com.jiuqi.nr.tag.management.environment;

import com.jiuqi.nr.context.infc.INRContext;
import java.util.List;

public class TagAddMappingsContextData
implements INRContext {
    private String entityData;
    private List<String> tagKeys;
    public String contextEntityId;
    public String contextFilterExpression;

    public String getEntityData() {
        return this.entityData;
    }

    public void setEntityData(String entityData) {
        this.entityData = entityData;
    }

    public List<String> getTagKeys() {
        return this.tagKeys;
    }

    public void setTagKeys(List<String> tagKeys) {
        this.tagKeys = tagKeys;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }
}

