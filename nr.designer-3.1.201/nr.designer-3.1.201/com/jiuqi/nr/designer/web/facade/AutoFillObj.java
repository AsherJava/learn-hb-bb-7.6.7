/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 */
package com.jiuqi.nr.designer.web.facade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.designer.web.facade.AutoFillFieldInfoObj;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class AutoFillObj {
    private List<AutoFillFieldInfoObj> fieldInfo;
    private String region;
    private boolean isCheck = true;
    private List<AutoFillFieldInfoObj> errorInfo;

    public boolean isCheck() {
        return this.isCheck;
    }

    public void setCheck(boolean check) {
        this.isCheck = check;
    }

    public List<AutoFillFieldInfoObj> getErrorInfo() {
        return this.errorInfo;
    }

    public void setErrorInfo(List<AutoFillFieldInfoObj> errorInfo) {
        this.errorInfo = errorInfo;
    }

    public List<AutoFillFieldInfoObj> getFieldInfo() {
        return this.fieldInfo;
    }

    public void setFieldInfo(List<AutoFillFieldInfoObj> fieldInfo) {
        this.fieldInfo = fieldInfo;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}

