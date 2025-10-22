/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean;

import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean.BlobFieldStruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BlobFormStruct
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String flag;
    private String title;
    private String key;
    private String groupKey;
    private String itemType = "TABLE";
    private List<BlobFieldStruct> fields = new ArrayList<BlobFieldStruct>();

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public List<BlobFieldStruct> getChildren() {
        return this.fields;
    }

    public void setChildren(List<BlobFieldStruct> fields) {
        this.fields = fields;
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
        this.groupKey = key;
    }

    public String getItemType() {
        return this.itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
}

