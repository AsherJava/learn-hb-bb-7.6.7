/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.input;

import java.util.List;

public class FileCopyBaseParam {
    private String fromTaskKey;
    private List<String> oldFileGroupKeys;
    private List<String> oldPicGroupKeys;
    private String toTaskKey;
    private String toFormSchemeKey;

    public FileCopyBaseParam() {
    }

    public FileCopyBaseParam(String fromTaskKey, List<String> oldFileGroupKeys, List<String> oldPicGroupKeys, String toTaskKey, String toFormSchemeKey) {
        this.fromTaskKey = fromTaskKey;
        this.oldFileGroupKeys = oldFileGroupKeys;
        this.oldPicGroupKeys = oldPicGroupKeys;
        this.toTaskKey = toTaskKey;
        this.toFormSchemeKey = toFormSchemeKey;
    }

    public String getFromTaskKey() {
        return this.fromTaskKey;
    }

    public void setFromTaskKey(String fromTaskKey) {
        this.fromTaskKey = fromTaskKey;
    }

    public List<String> getOldFileGroupKeys() {
        return this.oldFileGroupKeys;
    }

    public void setOldFileGroupKeys(List<String> oldFileGroupKeys) {
        this.oldFileGroupKeys = oldFileGroupKeys;
    }

    public List<String> getOldPicGroupKeys() {
        return this.oldPicGroupKeys;
    }

    public void setOldPicGroupKeys(List<String> oldPicGroupKeys) {
        this.oldPicGroupKeys = oldPicGroupKeys;
    }

    public String getToTaskKey() {
        return this.toTaskKey;
    }

    public void setToTaskKey(String toTaskKey) {
        this.toTaskKey = toTaskKey;
    }

    public String getToFormSchemeKey() {
        return this.toFormSchemeKey;
    }

    public void setToFormSchemeKey(String toFormSchemeKey) {
        this.toFormSchemeKey = toFormSchemeKey;
    }
}

