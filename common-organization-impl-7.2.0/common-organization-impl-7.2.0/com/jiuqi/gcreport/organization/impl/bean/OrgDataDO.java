/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.organization.impl.bean;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class OrgDataDO {
    private String key;
    private String code;
    private String name;
    private String shortname;
    private String parentcode;
    private String parents;
    private BigDecimal ordinal;
    private boolean leaf;
    private boolean stopflag;
    private boolean recoveryflag;
    private String categoryname;
    private Map<String, Object> fieldValMap = new HashMap<String, Object>();

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortname() {
        return this.shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getParentcode() {
        return this.parentcode;
    }

    public void setParentcode(String parentcode) {
        this.parentcode = parentcode;
    }

    public String getParents() {
        return this.parents;
    }

    public void setParents(String parents) {
        this.parents = parents;
    }

    public BigDecimal getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(BigDecimal ordinal) {
        this.ordinal = ordinal;
    }

    public boolean isStopflag() {
        return this.stopflag;
    }

    public void setStopflag(boolean stopflag) {
        this.stopflag = stopflag;
    }

    public boolean isRecoveryflag() {
        return this.recoveryflag;
    }

    public void setRecoveryflag(boolean recoveryflag) {
        this.recoveryflag = recoveryflag;
    }

    public String getCategoryname() {
        return this.categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public Map<String, Object> getFieldValMap() {
        return this.fieldValMap;
    }

    public void setFieldValMap(Map<String, Object> fieldValMap) {
        this.fieldValMap = fieldValMap;
    }

    public Object getFieldVal(String fieldName) {
        return this.fieldValMap.get(fieldName);
    }

    public String getValAsString(String fieldName) {
        Object objectVal = this.fieldValMap.get(fieldName);
        if (objectVal == null) {
            return null;
        }
        return objectVal.toString();
    }

    public void addFieldValue(String fieldName, Object value) {
        this.fieldValMap.put(fieldName, value);
    }

    public boolean isLeaf() {
        return this.leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }
}

