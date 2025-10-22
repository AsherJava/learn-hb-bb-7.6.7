/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataresource.web.vo;

import com.jiuqi.nr.dataresource.DataResourceDefineGroup;
import com.jiuqi.nr.dataresource.service.IDataResourceDefineGroupService;

public class DataResourceDefineGroupVO {
    private String key;
    private String title;
    private String desc;
    private String order;
    private String parentKey;

    public DataResourceDefineGroupVO(DataResourceDefineGroup group) {
        this.key = group.getKey();
        this.title = group.getTitle();
        this.desc = group.getDesc();
        this.order = group.getOrder();
        this.parentKey = group.getParentKey();
    }

    public DataResourceDefineGroupVO() {
    }

    public DataResourceDefineGroup toDg(IDataResourceDefineGroupService groupService) {
        DataResourceDefineGroup init = groupService.init();
        init.setKey(this.key);
        init.setOrder(this.order);
        init.setParentKey(this.parentKey);
        init.setDesc(this.desc);
        init.setTitle(this.title);
        return init;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public String toString() {
        return "DataResourceDefineGroupVO{key='" + this.key + '\'' + ", title='" + this.title + '\'' + ", desc='" + this.desc + '\'' + ", order='" + this.order + '\'' + ", parentKey='" + this.parentKey + '\'' + '}';
    }
}

