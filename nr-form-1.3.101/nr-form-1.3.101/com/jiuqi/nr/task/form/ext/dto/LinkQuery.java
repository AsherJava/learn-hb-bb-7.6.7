/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.DataLinkType
 */
package com.jiuqi.nr.task.form.ext.dto;

import com.jiuqi.nr.definition.common.DataLinkType;

public class LinkQuery {
    private String linkKey;
    private String formKey;
    private String linkExpression;
    private DataLinkType linkType;

    public String getLinkKey() {
        return this.linkKey;
    }

    public void setLinkKey(String linkKey) {
        this.linkKey = linkKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getLinkExpression() {
        return this.linkExpression;
    }

    public void setLinkExpression(String linkExpression) {
        this.linkExpression = linkExpression;
    }

    public DataLinkType getLinkType() {
        return this.linkType;
    }

    public void setLinkType(DataLinkType linkType) {
        this.linkType = linkType;
    }
}

