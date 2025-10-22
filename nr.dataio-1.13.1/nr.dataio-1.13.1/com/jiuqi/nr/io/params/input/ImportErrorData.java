/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.graphics.Point
 */
package com.jiuqi.nr.io.params.input;

import com.jiuqi.np.graphics.Point;
import com.jiuqi.nr.io.params.input.ImportErrorTypeEnum;

public class ImportErrorData {
    private String fieldCode;
    private String orgCode;
    private Point point;
    private ImportErrorTypeEnum errorType;
    private String errorMessage;

    public ImportErrorData() {
    }

    public ImportErrorData(String fieldCode, String orgCode, Point point, ImportErrorTypeEnum errorType, String errorMessage) {
        this.fieldCode = fieldCode;
        this.orgCode = orgCode;
        this.point = point;
        this.errorType = errorType;
        this.errorMessage = errorMessage;
    }

    public String getFieldCode() {
        return this.fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public Point getPoint() {
        return this.point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public ImportErrorTypeEnum getErrorType() {
        return this.errorType;
    }

    public void setErrorType(ImportErrorTypeEnum errorType) {
        this.errorType = errorType;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

