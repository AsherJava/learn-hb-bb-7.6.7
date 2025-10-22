/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.examine.web.bean;

public class MdInfoFieldUpgradeVO {
    private String sourceFieldName;
    private String sourceTableName;
    private String mdInfoFieldName;
    private String mdInfoTableName;

    public MdInfoFieldUpgradeVO() {
    }

    public MdInfoFieldUpgradeVO(String sourceFieldName, String sourceTableName, String mdInfoFieldName, String mdInfoTableName) {
        this.sourceFieldName = sourceFieldName;
        this.sourceTableName = sourceTableName;
        this.mdInfoFieldName = mdInfoFieldName;
        this.mdInfoTableName = mdInfoTableName;
    }

    public String getSourceFieldName() {
        return this.sourceFieldName;
    }

    public void setSourceFieldName(String sourceFieldName) {
        this.sourceFieldName = sourceFieldName;
    }

    public String getSourceTableName() {
        return this.sourceTableName;
    }

    public void setSourceTableName(String sourceTableName) {
        this.sourceTableName = sourceTableName;
    }

    public String getMdInfoFieldName() {
        return this.mdInfoFieldName;
    }

    public void setMdInfoFieldName(String mdInfoFieldName) {
        this.mdInfoFieldName = mdInfoFieldName;
    }

    public String getMdInfoTableName() {
        return this.mdInfoTableName;
    }

    public void setMdInfoTableName(String mdInfoTableName) {
        this.mdInfoTableName = mdInfoTableName;
    }
}

