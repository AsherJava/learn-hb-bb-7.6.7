/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.migration.transferdata.bean;

import com.jiuqi.nr.migration.transferdata.bean.TransZbValue;
import java.util.ArrayList;
import java.util.List;

public class TransMainBody {
    private String formKey;
    private String formTitle;
    private String regionKey;
    private String regionCode;
    private String regionTitle;
    private boolean isFloat;
    private List<String> zbCodes = new ArrayList<String>();
    private List<List<TransZbValue>> fieldValuesRows = new ArrayList<List<TransZbValue>>();
    private int idx;

    public TransMainBody() {
        this.fieldValuesRows = new ArrayList<List<TransZbValue>>();
    }

    public TransMainBody(String formTitle, String regionKey, boolean isFloat) {
        this.formTitle = formTitle;
        this.regionKey = regionKey;
        this.isFloat = isFloat;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFormTitle() {
        return this.formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public String getRegionCode() {
        return this.regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getRegionTitle() {
        return this.regionTitle;
    }

    public void setRegionTitle(String regionTitle) {
        this.regionTitle = regionTitle;
    }

    public boolean isFloat() {
        return this.isFloat;
    }

    public void setFloat(boolean aFloat) {
        this.isFloat = aFloat;
    }

    public List<List<TransZbValue>> getRows() {
        return this.fieldValuesRows;
    }

    public List<TransZbValue> getFieldValues(int rowIndex) {
        return this.fieldValuesRows.get(rowIndex);
    }

    public void addFieldValues(List<TransZbValue> fieldValues) {
        this.fieldValuesRows.add(fieldValues);
    }

    public int getIdx() {
        return this.idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public List<String> getZbCodes() {
        return this.zbCodes;
    }

    public void setZbCodes(List<String> zbCodes) {
        this.zbCodes = zbCodes;
    }
}

