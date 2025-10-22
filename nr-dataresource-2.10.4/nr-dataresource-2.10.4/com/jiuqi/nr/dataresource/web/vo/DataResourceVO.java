/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataresource.web.vo;

import com.jiuqi.nr.dataresource.DataResource;
import com.jiuqi.nr.dataresource.DataResourceKind;
import com.jiuqi.nr.dataresource.service.IDataResourceService;

public class DataResourceVO {
    private String key;
    private String title;
    private String desc;
    private String resourceDefineKey;
    private String parentKey;
    private String order;
    private String dimKey;
    private DataResourceKind resourceKind;
    private int kind;
    private String linkZb;
    private String linkZbTitle;
    private String linkZbCode;

    public DataResourceVO(DataResource dataResource) {
        this.key = dataResource.getKey();
        this.title = dataResource.getTitle();
        this.desc = dataResource.getDesc();
        this.resourceDefineKey = dataResource.getResourceDefineKey();
        this.parentKey = dataResource.getParentKey();
        this.order = dataResource.getOrder();
        if (this.parentKey == null) {
            this.parentKey = dataResource.getResourceDefineKey();
        }
        this.dimKey = dataResource.getDimKey();
        this.resourceKind = dataResource.getResourceKind();
        if (this.resourceKind != null) {
            this.kind = dataResource.getResourceKind().getValue();
        }
        this.linkZb = dataResource.getLinkZb();
    }

    public DataResourceVO() {
    }

    public DataResource toDr(IDataResourceService resourceService) {
        DataResource init = resourceService.init();
        init.setKey(this.key);
        init.setTitle(this.title);
        init.setDesc(this.desc);
        init.setResourceDefineKey(this.resourceDefineKey);
        if (this.resourceKind != null) {
            init.setResourceKind(this.resourceKind);
        } else {
            init.setResourceKind(DataResourceKind.RESOURCE_GROUP);
        }
        init.setParentKey(this.parentKey);
        init.setOrder(this.order);
        init.setDimKey(this.dimKey);
        init.setLinkZb(this.linkZb);
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

    public String getResourceDefineKey() {
        return this.resourceDefineKey;
    }

    public void setResourceDefineKey(String resourceDefineKey) {
        this.resourceDefineKey = resourceDefineKey;
    }

    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getDimKey() {
        return this.dimKey;
    }

    public void setDimKey(String dimKey) {
        this.dimKey = dimKey;
    }

    public DataResourceKind getResourceKind() {
        return this.resourceKind;
    }

    public void setResourceKind(DataResourceKind resourceKind) {
        this.resourceKind = resourceKind;
    }

    public int getKind() {
        return this.kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public String getLinkZb() {
        return this.linkZb;
    }

    public void setLinkZb(String linkZb) {
        this.linkZb = linkZb;
    }

    public String getLinkZbTitle() {
        return this.linkZbTitle;
    }

    public void setLinkZbTitle(String linkZbTitle) {
        this.linkZbTitle = linkZbTitle;
    }

    public String getLinkZbCode() {
        return this.linkZbCode;
    }

    public void setLinkZbCode(String linkZbCode) {
        this.linkZbCode = linkZbCode;
    }

    public String toString() {
        return "DataResourceVO{key='" + this.key + '\'' + ", title='" + this.title + '\'' + ", desc='" + this.desc + '\'' + ", resourceDefineKey='" + this.resourceDefineKey + '\'' + ", parentKey='" + this.parentKey + '\'' + ", order='" + this.order + '\'' + ", dimKey='" + this.dimKey + '\'' + ", resourceKind=" + (Object)((Object)this.resourceKind) + '}';
    }
}

