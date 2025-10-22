/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.midstore2.dataentry.web.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MidstoreDataentryVO {
    private boolean success = true;
    private boolean hasDimension = true;
    private List<String> midstoreCodes;
    private String errorMsg;
    private Map<String, Object> defaultForm;
    private Map<String, Object> defaultEntity;
    private boolean overImport = false;

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean getHasDimension() {
        return this.hasDimension;
    }

    public void setHasDimension(boolean hasDimension) {
        this.hasDimension = hasDimension;
    }

    public List<String> getMidstoreCodes() {
        return this.midstoreCodes;
    }

    public void setMidstoreCodes(List<String> midstoreCodes) {
        this.midstoreCodes = midstoreCodes;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public boolean isHasDimension() {
        return this.hasDimension;
    }

    public Map<String, Object> getFormMap() {
        if (this.defaultForm == null) {
            this.defaultForm = new HashMap<String, Object>();
        }
        return this.defaultForm;
    }

    public Map<String, Object> getDefaultForm() {
        return this.defaultForm;
    }

    public void setDefaultForm(Map<String, Object> defaultForm) {
        this.defaultForm = defaultForm;
    }

    public Map<String, Object> getEntityMap() {
        if (this.defaultEntity == null) {
            this.defaultEntity = new HashMap<String, Object>();
        }
        return this.defaultEntity;
    }

    public Map<String, Object> getDefaultEntity() {
        return this.defaultEntity;
    }

    public void setDefaultEntity(Map<String, Object> defaultEntity) {
        this.defaultEntity = defaultEntity;
    }

    public boolean isOverImport() {
        return this.overImport;
    }

    public void setOverImport(boolean overImport) {
        this.overImport = overImport;
    }
}

