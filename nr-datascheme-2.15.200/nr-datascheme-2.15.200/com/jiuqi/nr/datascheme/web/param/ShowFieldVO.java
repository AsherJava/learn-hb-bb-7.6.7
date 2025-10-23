/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 */
package com.jiuqi.nr.datascheme.web.param;

import com.jiuqi.nr.entity.model.IEntityAttribute;

public class ShowFieldVO {
    private String code;
    private String title;
    private Boolean multival = false;
    private String referEntityId;
    private String referEntityTitle;

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

    public static ShowFieldVO convert(IEntityAttribute attribute) {
        ShowFieldVO param = new ShowFieldVO();
        param.setCode(attribute.getCode());
        param.setTitle(attribute.getTitle());
        param.setMultival(attribute.isMultival());
        return param;
    }

    public Boolean isMultival() {
        return this.multival;
    }

    public void setMultival(Boolean multival) {
        this.multival = multival;
    }

    public String getReferEntityId() {
        return this.referEntityId;
    }

    public void setReferEntityId(String referEntityId) {
        this.referEntityId = referEntityId;
    }

    public String getReferEntityTitle() {
        return this.referEntityTitle;
    }

    public void setReferEntityTitle(String referEntityTitle) {
        this.referEntityTitle = referEntityTitle;
    }
}

