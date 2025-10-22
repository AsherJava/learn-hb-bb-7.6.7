/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataresource.web.vo;

import com.jiuqi.nr.dataresource.DimAttribute;
import com.jiuqi.nr.dataresource.service.IDataLinkService;

public class DimAttributeVO {
    private String dimKey;
    private String key;
    private String code;
    private String title;
    private String resourceDefineKey;
    private boolean hidden;
    private String order;

    public DimAttributeVO(DimAttribute dimAttribute) {
        this.dimKey = dimAttribute.getDimKey();
        this.key = dimAttribute.getKey();
        this.code = dimAttribute.getCode();
        this.title = dimAttribute.getTitle();
        this.resourceDefineKey = dimAttribute.getResourceDefineKey();
        this.hidden = dimAttribute.isHidden();
        this.order = dimAttribute.getOrder();
    }

    public DimAttributeVO() {
    }

    public DimAttribute toDm(IDataLinkService linkService) {
        DimAttribute dimAttribute = linkService.initDimAttribute();
        dimAttribute.setDimKey(this.dimKey);
        dimAttribute.setHidden(this.hidden);
        dimAttribute.setKey(this.key);
        dimAttribute.setOrder(this.order);
        dimAttribute.setResourceDefineKey(this.resourceDefineKey);
        return dimAttribute;
    }

    public String getDimKey() {
        return this.dimKey;
    }

    public void setDimKey(String dimKey) {
        this.dimKey = dimKey;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getResourceDefineKey() {
        return this.resourceDefineKey;
    }

    public void setResourceDefineKey(String resourceDefineKey) {
        this.resourceDefineKey = resourceDefineKey;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isShow() {
        return !this.hidden;
    }

    public void setShow(boolean show) {
        this.hidden = !show;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String toString() {
        return "DimAttributeVO{dimKey='" + this.dimKey + '\'' + ", key='" + this.key + '\'' + ", code='" + this.code + '\'' + ", title='" + this.title + '\'' + ", resourceDefineKey='" + this.resourceDefineKey + '\'' + ", hidden=" + this.hidden + ", order='" + this.order + '\'' + '}';
    }
}

