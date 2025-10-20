/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.va.bizmeta.domain.metaauth;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.va.bizmeta.domain.metaauth.MetaAuthDim;
import java.util.List;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class MetaAuthVO {
    private Boolean flag;
    private String message;
    private MetaAuthDim metaAuthDim;
    private List<MetaAuthDim> metaAuthDims;
    private String inheritType;

    public String getInheritType() {
        return this.inheritType;
    }

    public void setInheritType(String inheritType) {
        this.inheritType = inheritType;
    }

    public Boolean getFlag() {
        return this.flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MetaAuthDim getMetaAuthDim() {
        return this.metaAuthDim;
    }

    public void setMetaAuthDim(MetaAuthDim metaAuthDim) {
        this.metaAuthDim = metaAuthDim;
    }

    public List<MetaAuthDim> getMetaAuthDims() {
        return this.metaAuthDims;
    }

    public void setMetaAuthDims(List<MetaAuthDim> metaAuthDims) {
        this.metaAuthDims = metaAuthDims;
    }
}

