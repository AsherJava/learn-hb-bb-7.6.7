/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.checkdes.internal.io;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CKDExpEntity
implements Serializable {
    private static final long serialVersionUID = -2380262444306588722L;
    private String mdCode;
    private String period;
    private Map<String, String> dims = new HashMap<String, String>();
    private String formulaSchemeTitle;
    private String formCode;
    private String formulaCode;
    private int globRow = -1;
    private int globCol = -1;
    private String dimStr;
    private String description;
    private String userId;
    private String userNickName;
    private long updateTime;

    public String getFormulaSchemeTitle() {
        return this.formulaSchemeTitle;
    }

    public void setFormulaSchemeTitle(String formulaSchemeTitle) {
        this.formulaSchemeTitle = formulaSchemeTitle;
    }

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getFormulaCode() {
        return this.formulaCode;
    }

    public void setFormulaCode(String formulaCode) {
        this.formulaCode = formulaCode;
    }

    public int getGlobRow() {
        return this.globRow;
    }

    public void setGlobRow(int globRow) {
        this.globRow = globRow;
    }

    public int getGlobCol() {
        return this.globCol;
    }

    public void setGlobCol(int globCol) {
        this.globCol = globCol;
    }

    public String getDimStr() {
        return this.dimStr;
    }

    public void setDimStr(String dimStr) {
        this.dimStr = dimStr;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMdCode() {
        return this.mdCode;
    }

    public void setMdCode(String mdCode) {
        this.mdCode = mdCode;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Map<String, String> getDims() {
        return this.dims;
    }

    public void setDims(Map<String, String> dims) {
        this.dims = dims;
    }

    public void addDim(String entityId, String dimValue) {
        this.dims.put(entityId, dimValue);
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNickName() {
        return this.userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public long getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}

