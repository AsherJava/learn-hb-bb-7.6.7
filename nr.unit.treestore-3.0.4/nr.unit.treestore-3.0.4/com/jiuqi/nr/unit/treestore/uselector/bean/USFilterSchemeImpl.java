/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.unit.treestore.uselector.bean;

import com.jiuqi.nr.unit.treestore.uselector.bean.USFilterScheme;
import com.jiuqi.nr.unit.treestore.uselector.bean.USFilterTemplate;
import java.util.Date;

public class USFilterSchemeImpl
implements USFilterScheme {
    private String key;
    private String title;
    private String owner;
    private String entityId;
    private boolean shared;
    private USFilterTemplate template;
    private Date createTime;

    @Override
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    @Override
    public boolean isShared() {
        return this.shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    @Override
    public USFilterTemplate getTemplate() {
        return this.template;
    }

    public void setTemplate(USFilterTemplate template) {
        this.template = template;
    }

    @Override
    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

