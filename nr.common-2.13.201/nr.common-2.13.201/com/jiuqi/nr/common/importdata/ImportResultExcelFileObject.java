/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.importdata;

import com.jiuqi.nr.common.importdata.ImportResultSheetObject;
import com.jiuqi.nr.common.importdata.ResultErrorInfo;
import com.jiuqi.nr.common.params.DimensionValue;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ImportResultExcelFileObject
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String fileName;
    private String location;
    private Boolean successIs;
    private ResultErrorInfo fileError;
    private List<ImportResultSheetObject> importResultSheetObjectList;
    private List<Map<String, DimensionValue>> relationDimensions = new ArrayList<Map<String, DimensionValue>>();
    private boolean fmdmed;

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<ImportResultSheetObject> getImportResultSheetObjectList() {
        if (null == this.importResultSheetObjectList) {
            this.importResultSheetObjectList = new ArrayList<ImportResultSheetObject>();
        }
        return this.importResultSheetObjectList;
    }

    public void setImportResultSheetObjectList(List<ImportResultSheetObject> importResultSheetObjectList) {
        this.importResultSheetObjectList = importResultSheetObjectList;
    }

    public Boolean isSuccessIs() {
        if (null == this.successIs) {
            this.successIs = null != this.fileError && null != this.fileError.getErrorCode() || null != this.importResultSheetObjectList && this.importResultSheetObjectList.size() > 0 ? Boolean.valueOf(false) : Boolean.valueOf(true);
        }
        return this.successIs;
    }

    public ResultErrorInfo getFileError() {
        if (null == this.fileError) {
            this.fileError = new ResultErrorInfo();
        }
        return this.fileError;
    }

    public void setFileError(ResultErrorInfo fileError) {
        this.fileError = fileError;
    }

    public List<Map<String, DimensionValue>> getRelationDimensions() {
        return this.relationDimensions;
    }

    public void setRelationDimensions(List<Map<String, DimensionValue>> relationDimensions) {
        this.relationDimensions = relationDimensions;
    }

    public void addRelationDimensions(Map<String, DimensionValue> addDimension) {
        this.relationDimensions.add(addDimension);
    }

    public boolean isFmdmed() {
        return this.fmdmed;
    }

    public void setFmdmed(boolean fmdmed) {
        this.fmdmed = fmdmed;
    }
}

