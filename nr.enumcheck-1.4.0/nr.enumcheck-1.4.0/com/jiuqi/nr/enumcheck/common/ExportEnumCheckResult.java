/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.enumcheck.common;

import com.jiuqi.nr.enumcheck.common.EnumCheckErrorKind;
import java.io.Serializable;
import java.util.Map;

public class ExportEnumCheckResult
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String entityTitle;
    private Map<String, String> dimNameValueTitleMap;
    private String enumTitle;
    private String enumCode;
    private String field;
    private String bbfz;
    private EnumCheckErrorKind errorKind;

    public String getEntityTitle() {
        return this.entityTitle;
    }

    public void setEntityTitle(String entityTitle) {
        this.entityTitle = entityTitle;
    }

    public Map<String, String> getDimNameValueTitleMap() {
        return this.dimNameValueTitleMap;
    }

    public void setDimNameValueTitleMap(Map<String, String> dimNameValueTitleMap) {
        this.dimNameValueTitleMap = dimNameValueTitleMap;
    }

    public String getEnumTitle() {
        return this.enumTitle;
    }

    public void setEnumTitle(String enumTitle) {
        this.enumTitle = enumTitle;
    }

    public String getEnumCode() {
        return this.enumCode;
    }

    public void setEnumCode(String enumCode) {
        this.enumCode = enumCode;
    }

    public String getField() {
        return this.field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getBbfz() {
        return this.bbfz;
    }

    public void setBbfz(String bbfz) {
        this.bbfz = bbfz;
    }

    public EnumCheckErrorKind getErrorKind() {
        return this.errorKind;
    }

    public void setErrorKind(EnumCheckErrorKind errorKind) {
        this.errorKind = errorKind;
    }
}

