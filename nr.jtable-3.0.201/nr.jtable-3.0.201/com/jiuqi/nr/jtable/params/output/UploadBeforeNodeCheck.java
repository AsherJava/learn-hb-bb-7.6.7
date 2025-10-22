/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.params.output;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UploadBeforeNodeCheck
implements Serializable {
    private static final long serialVersionUID = 1L;
    private int unPassEntityNum;
    private int unPassFormNum;
    private int unPassFormGroupNum;
    private Map<String, String> unPassEntity = new HashMap<String, String>();
    private String asyncTaskId;
    private Map<String, List<String>> unPassForms = new HashMap<String, List<String>>();
    private Map<String, List<String>> unPassFormGroup = new HashMap<String, List<String>>();

    public int getUnPassEntityNum() {
        return this.unPassEntityNum;
    }

    public void setUnPassEntityNum(int unPassEntityNum) {
        this.unPassEntityNum = unPassEntityNum;
    }

    public Map<String, String> getUnPassEntity() {
        return this.unPassEntity;
    }

    public void setUnPassEntity(Map<String, String> unPassEntity) {
        this.unPassEntity = unPassEntity;
    }

    public String getAsyncTaskId() {
        return this.asyncTaskId;
    }

    public void setAsyncTaskId(String asyncTaskId) {
        this.asyncTaskId = asyncTaskId;
    }

    public Map<String, List<String>> getUnPassForms() {
        return this.unPassForms;
    }

    public void setUnPassForms(Map<String, List<String>> unPassForms) {
        this.unPassForms = unPassForms;
    }

    public int getUnPassFormNum() {
        return this.unPassFormNum;
    }

    public void setUnPassFormNum(int unPassFormNum) {
        this.unPassFormNum = unPassFormNum;
    }

    public Map<String, List<String>> getUnPassFormGroup() {
        return this.unPassFormGroup;
    }

    public void setUnPassFormGroup(Map<String, List<String>> unPassFormGroup) {
        this.unPassFormGroup = unPassFormGroup;
    }

    public int getUnPassFormGroupNum() {
        return this.unPassFormGroupNum;
    }

    public void setUnPassFormGroupNum(int unPassFormGroupNum) {
        this.unPassFormGroupNum = unPassFormGroupNum;
    }
}

