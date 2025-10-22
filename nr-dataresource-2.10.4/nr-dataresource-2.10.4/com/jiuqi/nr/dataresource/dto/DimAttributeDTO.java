/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.Ordered
 */
package com.jiuqi.nr.dataresource.dto;

import com.jiuqi.nr.dataresource.DimAttribute;
import com.jiuqi.nr.datascheme.api.core.Ordered;

public class DimAttributeDTO
implements DimAttribute {
    private static final long serialVersionUID = 8245463420610455826L;
    private String dimKey;
    private String key;
    private String code;
    private String title;
    private String resourceDefineKey;
    private boolean hidden;
    private String order;

    @Override
    public String getDimKey() {
        return this.dimKey;
    }

    @Override
    public void setDimKey(String dimKey) {
        this.dimKey = dimKey;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getResourceDefineKey() {
        return this.resourceDefineKey;
    }

    @Override
    public void setResourceDefineKey(String resourceDefineKey) {
        this.resourceDefineKey = resourceDefineKey;
    }

    @Override
    public boolean isHidden() {
        return this.hidden;
    }

    @Override
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int compareTo(Ordered o) {
        if (o == null || o.getOrder() == null) {
            return -1;
        }
        if (this.order == null) {
            return 1;
        }
        return this.order.compareTo(o.getOrder());
    }

    public String toString() {
        return "DimAttributeDTO{dimKey='" + this.dimKey + '\'' + ", key='" + this.key + '\'' + ", code='" + this.code + '\'' + ", title='" + this.title + '\'' + ", resourceDefineKey='" + this.resourceDefineKey + '\'' + ", hidden=" + this.hidden + ", order='" + this.order + '\'' + '}';
    }
}

