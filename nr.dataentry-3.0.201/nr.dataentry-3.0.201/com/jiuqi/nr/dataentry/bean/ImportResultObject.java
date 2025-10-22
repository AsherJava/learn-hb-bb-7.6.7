/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.dataentry.bean.ImportResultItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ImportResultObject
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String message;
    private boolean success;
    private List<ImportResultItem> fails;
    private String importType;
    private String location;
    private boolean fmdmed;
    private String fileKey;
    private String jioSelectImportResourceId;

    public String getFileKey() {
        return this.fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getJioSelectImportResourceId() {
        return this.jioSelectImportResourceId;
    }

    public void setJioSelectImportResourceId(String jioSelectImportResourceId) {
        this.jioSelectImportResourceId = jioSelectImportResourceId;
    }

    public ImportResultObject() {
        this.fails = new ArrayList<ImportResultItem>();
    }

    public ImportResultObject(boolean success, String message) {
        this.message = message;
        this.success = success;
        this.fails = new ArrayList<ImportResultItem>();
    }

    public ImportResultObject(boolean success, String message, List<ImportResultItem> fails) {
        this.message = message;
        this.success = success;
        this.fails = fails;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<ImportResultItem> getFails() {
        if (null == this.fails) {
            this.fails = new ArrayList<ImportResultItem>();
        }
        return this.fails;
    }

    public void setFails(List<ImportResultItem> fails) {
        this.fails = fails;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isFmdmed() {
        return this.fmdmed;
    }

    public void setFmdmed(boolean fmdmed) {
        this.fmdmed = fmdmed;
    }

    public String getImportType() {
        return this.importType;
    }

    public void setImportType(String importType) {
        this.importType = importType;
    }
}

