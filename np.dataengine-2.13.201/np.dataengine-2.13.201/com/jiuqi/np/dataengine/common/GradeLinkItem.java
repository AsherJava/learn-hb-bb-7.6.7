/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 */
package com.jiuqi.np.dataengine.common;

import com.jiuqi.np.definition.facade.EntityViewDefine;

public class GradeLinkItem {
    private String key;
    private String linkExpression;
    private EntityViewDefine entityView;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLinkExpression() {
        return this.linkExpression;
    }

    public void setLinkExpression(String linkExpression) {
        this.linkExpression = linkExpression;
    }

    public EntityViewDefine getEntityView() {
        return this.entityView;
    }

    public void setEntityView(EntityViewDefine entityView) {
        this.entityView = entityView;
    }
}

