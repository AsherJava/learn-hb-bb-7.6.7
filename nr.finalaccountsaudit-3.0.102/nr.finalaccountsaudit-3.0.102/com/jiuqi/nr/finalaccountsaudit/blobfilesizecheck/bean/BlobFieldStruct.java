/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean;

import java.io.Serializable;

public class BlobFieldStruct
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String flag;
    private String title;
    private String key;
    private String dataLinkKey;
    private String formKey;
    private String formTitle;
    private String formCode;
    private String itemType = "FIELD";
    private String groupKey;

    public String getFormTitle() {
        return this.formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getFlag() {
        return this.flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        this.groupKey = formKey;
    }

    public String getDataLinkKey() {
        return this.dataLinkKey;
    }

    public void setDataLinkKey(String dataLinkKey) {
        this.dataLinkKey = dataLinkKey;
    }

    public String getItemType() {
        return this.itemType;
    }

    private void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }
}

