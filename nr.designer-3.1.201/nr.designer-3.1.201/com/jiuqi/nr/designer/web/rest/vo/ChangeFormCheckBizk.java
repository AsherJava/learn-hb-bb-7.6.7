/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.rest.vo;

public class ChangeFormCheckBizk {
    private String formEntityMaster;
    private String[] tableMaster;
    private String[] regionArray;

    public ChangeFormCheckBizk() {
    }

    public ChangeFormCheckBizk(String formEntityMaster, String[] tableMaster, String[] regionArray) {
        this.formEntityMaster = formEntityMaster;
        this.tableMaster = tableMaster;
        this.regionArray = regionArray;
    }

    public String getFormEntityMaster() {
        return this.formEntityMaster;
    }

    public void setFormEntityMaster(String formEntityMaster) {
        this.formEntityMaster = formEntityMaster;
    }

    public String[] getTableMaster() {
        return this.tableMaster;
    }

    public void setTableMaster(String[] tableMaster) {
        this.tableMaster = tableMaster;
    }

    public String[] getRegionArray() {
        return this.regionArray;
    }

    public void setRegionArray(String[] regionArray) {
        this.regionArray = regionArray;
    }
}

