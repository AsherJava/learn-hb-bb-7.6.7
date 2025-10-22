/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.io.params.input.ImportResult
 */
package com.jiuqi.nr.data.excel.param.bean;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.excel.param.bean.ImportResultItem;
import com.jiuqi.nr.io.params.input.ImportResult;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ImportResultObject
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String message;
    private boolean success;
    private List<ImportResultItem> fails;
    private String importType;
    private String location;
    private boolean fmdmed;
    private List<ImportResult> importResults;
    private List<Map<String, DimensionValue>> relationDimensions = null;

    public List<Map<String, DimensionValue>> getRelationDimensions() {
        return this.relationDimensions;
    }

    public void setRelationDimensions(List<Map<String, DimensionValue>> relationDimensions) {
        this.relationDimensions = relationDimensions;
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

    public List<ImportResult> getImportResults() {
        return this.importResults;
    }

    public void setImportResults(List<ImportResult> importResults) {
        this.importResults = importResults;
    }
}

