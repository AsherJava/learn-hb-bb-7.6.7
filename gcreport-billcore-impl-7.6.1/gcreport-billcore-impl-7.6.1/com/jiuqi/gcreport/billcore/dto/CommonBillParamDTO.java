/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.billcore.dto;

import java.util.ArrayList;
import java.util.List;

public class CommonBillParamDTO {
    private String masterTable;
    private List<String> itemTables;
    private List<String> unitCodes;
    private Integer year;
    private String funcTitle;

    public CommonBillParamDTO() {
    }

    public CommonBillParamDTO(String masterTable, List<String> unitCodes, String funcTitle) {
        this.masterTable = masterTable;
        this.unitCodes = unitCodes;
        this.funcTitle = funcTitle;
    }

    public CommonBillParamDTO(String masterTable, List<String> unitCodes, Integer year) {
        this(masterTable, unitCodes, (String)null);
        this.year = year;
    }

    public String getMasterTable() {
        return this.masterTable;
    }

    public void setMasterTable(String masterTable) {
        this.masterTable = masterTable;
    }

    public List<String> getItemTables() {
        if (null == this.itemTables) {
            this.itemTables = new ArrayList<String>();
        }
        return this.itemTables;
    }

    public void setItemTables(List<String> itemTables) {
        this.itemTables = itemTables;
    }

    public void addItemTable(String itemTable) {
        if (null == this.itemTables) {
            this.itemTables = new ArrayList<String>();
        }
        this.itemTables.add(itemTable);
    }

    public List<String> getUnitCodes() {
        return this.unitCodes;
    }

    public void setUnitCodes(List<String> unitCodes) {
        this.unitCodes = unitCodes;
    }

    public String getFuncTitle() {
        return this.funcTitle;
    }

    public void setFuncTitle(String funcTitle) {
        this.funcTitle = funcTitle;
    }

    public Integer getYear() {
        return this.year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}

