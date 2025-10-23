/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nrdt.parampacket.manage.bean;

import java.util.ArrayList;
import java.util.List;
import org.springframework.util.CollectionUtils;

public class ParamPacketQuery {
    private String guid;
    private String type;
    private String factoryId;
    private List<String> factoryIds;
    private String rootTitle;
    private String rootGuid;

    public String getGuid() {
        return this.guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFactoryId() {
        return this.factoryId;
    }

    public void setFactoryId(String factoryId) {
        this.factoryId = factoryId;
    }

    public List<String> getFactoryIds() {
        if (CollectionUtils.isEmpty(this.factoryIds)) {
            return new ArrayList<String>();
        }
        return this.factoryIds;
    }

    public void setFactoryIds(List<String> factoryIds) {
        this.factoryIds = factoryIds;
    }

    public String getRootTitle() {
        return this.rootTitle;
    }

    public void setRootTitle(String rootTitle) {
        this.rootTitle = rootTitle;
    }

    public String getRootGuid() {
        return this.rootGuid;
    }

    public void setRootGuid(String rootGuid) {
        this.rootGuid = rootGuid;
    }
}

