/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tag.manager.bean;

import com.jiuqi.nr.tag.manager.bean.TagObject;
import java.util.List;

public class TagManagerInitInfo {
    private boolean systemUser;
    private List<TagObject> rowDatas;
    private List<String> tagTitles;

    public boolean isSystemUser() {
        return this.systemUser;
    }

    public void setSystemUser(boolean systemUser) {
        this.systemUser = systemUser;
    }

    public List<TagObject> getRowDatas() {
        return this.rowDatas;
    }

    public void setRowDatas(List<TagObject> rowDatas) {
        this.rowDatas = rowDatas;
    }

    public List<String> getTagTitles() {
        return this.tagTitles;
    }

    public void setTagTitles(List<String> tagTitles) {
        this.tagTitles = tagTitles;
    }
}

