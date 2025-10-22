/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.consolidatedsystem.DTO;

public class ManagementDim {
    private final String id;
    private final String code;
    private final String title;
    private final Boolean nullAble;
    private final String referTable;

    public ManagementDim(String id, String code, String title, Boolean nullAble, String referTable) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.nullAble = nullAble;
        this.referTable = referTable;
    }

    public String getId() {
        return this.id;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public Boolean getNullAble() {
        return this.nullAble;
    }

    public String getReferTable() {
        return this.referTable;
    }
}

