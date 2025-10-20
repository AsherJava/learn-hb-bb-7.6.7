/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.bde.common.intf;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class Dimension
implements Serializable {
    private static final long serialVersionUID = -8997517958751112288L;
    private String dimCode;
    private String dimName;
    private String dimRule;
    private String dimValue;
    private Boolean assistDim;
    private String excludeValue;

    public Dimension() {
    }

    public Dimension(String dimCode, String dimRule) {
        this.dimCode = dimCode;
        this.dimRule = dimRule;
    }

    public Dimension(String dimCode) {
        this.dimCode = dimCode;
        this.assistDim = true;
    }

    public Dimension(String dimCode, Boolean assistDim) {
        this.dimCode = dimCode;
        this.assistDim = assistDim;
    }

    public Dimension(String dimCode, String dimRule, String dimValue) {
        this.dimCode = dimCode;
        this.dimRule = dimRule;
        this.dimValue = dimValue;
        this.assistDim = true;
    }

    public String getDimCode() {
        return this.dimCode;
    }

    public void setDimCode(String dimCode) {
        this.dimCode = dimCode;
    }

    public String getDimName() {
        return this.dimName;
    }

    public void setDimName(String dimName) {
        this.dimName = dimName;
    }

    public String getDimRule() {
        return this.dimRule;
    }

    public void setDimRule(String dimRule) {
        this.dimRule = dimRule;
    }

    public String getDimValue() {
        return this.dimValue;
    }

    public void setDimValue(String dimValue) {
        this.dimValue = dimValue;
    }

    public Boolean getAssistDim() {
        return this.assistDim;
    }

    public void setAssistDim(Boolean assistDim) {
        this.assistDim = assistDim;
    }

    public String getExcludeValue() {
        return this.excludeValue;
    }

    public void setExcludeValue(String excludeValue) {
        this.excludeValue = excludeValue;
    }

    public String toString() {
        return "Dimension{dimCode='" + this.dimCode + '\'' + ", dimName='" + this.dimName + '\'' + ", dimRule='" + this.dimRule + '\'' + ", dimValue='" + this.dimValue + '\'' + ", assistDim=" + this.assistDim + ", excludeValue='" + this.excludeValue + '\'' + '}';
    }
}

