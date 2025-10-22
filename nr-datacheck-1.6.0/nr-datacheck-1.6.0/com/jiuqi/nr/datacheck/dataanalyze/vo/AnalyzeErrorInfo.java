/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.multcheck2.bean.MCErrorDescription
 */
package com.jiuqi.nr.datacheck.dataanalyze.vo;

import com.jiuqi.nr.datacheck.dataanalyze.vo.CheckUnitState;
import com.jiuqi.nr.multcheck2.bean.MCErrorDescription;
import java.util.Map;
import org.springframework.beans.BeanUtils;

public class AnalyzeErrorInfo {
    private String key;
    private String org;
    private String orgStr;
    private CheckUnitState state;
    private Map<String, String> dims;
    private Map<String, String> dimStr;
    private String resource;
    private String resourceStr;
    private String description;
    private String tip;

    public AnalyzeErrorInfo() {
    }

    public AnalyzeErrorInfo(MCErrorDescription errorDescription) {
        BeanUtils.copyProperties(errorDescription, this);
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOrg() {
        return this.org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getOrgStr() {
        return this.orgStr;
    }

    public void setOrgStr(String orgStr) {
        this.orgStr = orgStr;
    }

    public CheckUnitState getState() {
        return this.state;
    }

    public void setState(CheckUnitState state) {
        this.state = state;
    }

    public Map<String, String> getDims() {
        return this.dims;
    }

    public void setDims(Map<String, String> dims) {
        this.dims = dims;
    }

    public Map<String, String> getDimStr() {
        return this.dimStr;
    }

    public void setDimStr(Map<String, String> dimStr) {
        this.dimStr = dimStr;
    }

    public String getResource() {
        return this.resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getResourceStr() {
        return this.resourceStr;
    }

    public void setResourceStr(String resourceStr) {
        this.resourceStr = resourceStr;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTip() {
        return this.tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}

