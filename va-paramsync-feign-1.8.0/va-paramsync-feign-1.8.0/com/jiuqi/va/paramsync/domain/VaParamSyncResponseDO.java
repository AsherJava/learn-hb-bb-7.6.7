/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.paramsync.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class VaParamSyncResponseDO
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String mainfest;
    private Map<String, Map<String, String>> dataMap = new HashMap<String, Map<String, String>>();

    public String getMainfest() {
        return this.mainfest;
    }

    public void setMainfest(String mainfest) {
        this.mainfest = mainfest;
    }

    public Map<String, Map<String, String>> getAllData() {
        return this.dataMap;
    }

    public Map<String, String> getData(String key) {
        return this.dataMap.get(key);
    }

    public void setData(String key, Map<String, String> data) {
        this.dataMap.put(key, data);
    }

    @Deprecated
    public Map<String, String> getOrgcategory() {
        return this.getData("orgcategory");
    }

    @Deprecated
    public void setOrgcategory(Map<String, String> orgcategory) {
        this.setData("orgcategory", orgcategory);
    }

    @Deprecated
    public Map<String, String> getBasedata() {
        return this.getData("basedata");
    }

    @Deprecated
    public void setBasedata(Map<String, String> basedata) {
        this.setData("basedata", basedata);
    }

    @Deprecated
    public Map<String, String> getDatamodel() {
        return this.getData("datamodel");
    }

    @Deprecated
    public void setDatamodel(Map<String, String> datamodel) {
        this.setData("datamodel", datamodel);
    }

    @Deprecated
    public Map<String, String> getBill() {
        return this.getData("bill");
    }

    @Deprecated
    public void setBill(Map<String, String> bill) {
        this.setData("bill", bill);
    }

    @Deprecated
    public Map<String, String> getBilllist() {
        return this.getData("billlist");
    }

    @Deprecated
    public void setBilllist(Map<String, String> billlist) {
        this.setData("billlist", billlist);
    }

    @Deprecated
    public Map<String, String> getWorkflow() {
        return this.getData("workflow");
    }

    @Deprecated
    public void setWorkflow(Map<String, String> workflow) {
        this.setData("workflow", workflow);
    }

    @Deprecated
    public Map<String, String> getBillref() {
        return this.getData("billref");
    }

    @Deprecated
    public void setBillref(Map<String, String> billref) {
        this.setData("billref", billref);
    }
}

