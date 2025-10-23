/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IMetaItem
 */
package com.jiuqi.nr.param.transfer.definition;

import com.jiuqi.np.definition.facade.IMetaItem;
import java.util.Date;

public class VNode
implements IMetaItem {
    private String key;
    private String title;
    private String order;
    private String version;
    private Date updateTime;

    public VNode() {
    }

    public VNode(String key) {
        this.key = key;
    }

    public VNode(String key, String title, String order, Date updateTime) {
        this.key = key;
        this.title = title;
        this.order = order;
        this.updateTime = updateTime;
    }

    public VNode(String key, String title, String order, String version, Date updateTime) {
        this.key = key;
        this.title = title;
        this.order = order;
        this.version = version;
        this.updateTime = updateTime;
    }

    public String getKey() {
        return this.key;
    }

    public String getTitle() {
        return this.title;
    }

    public String getOrder() {
        return this.order;
    }

    public String getVersion() {
        return this.version;
    }

    public String getOwnerLevelAndId() {
        return null;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }
}

