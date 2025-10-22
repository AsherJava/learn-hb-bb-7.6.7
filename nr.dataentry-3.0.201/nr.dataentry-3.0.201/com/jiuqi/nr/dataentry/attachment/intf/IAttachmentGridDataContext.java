/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.output.SecretLevelItem
 */
package com.jiuqi.nr.dataentry.attachment.intf;

import com.jiuqi.nr.dataentry.attachment.intf.IAttachmentContext;
import com.jiuqi.nr.jtable.params.output.SecretLevelItem;

public class IAttachmentGridDataContext
extends IAttachmentContext {
    private String type;
    private String key;
    private String formKey;
    private Integer pageSize;
    private Integer currentPage;
    private String zbType;
    private String order;
    private String sortBy;
    private boolean writeable;
    private String message;
    private SecretLevelItem secretLevelItem;

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public String getZbType() {
        return this.zbType;
    }

    public void setZbType(String zbType) {
        this.zbType = zbType;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSortBy() {
        return this.sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public boolean isWriteable() {
        return this.writeable;
    }

    public void setWriteable(boolean writeable) {
        this.writeable = writeable;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SecretLevelItem getSecretLevelItem() {
        return this.secretLevelItem;
    }

    public void setSecretLevelItem(SecretLevelItem secretLevelItem) {
        this.secretLevelItem = secretLevelItem;
    }
}

