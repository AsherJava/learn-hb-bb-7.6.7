/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.common;

public class JSYYTableData {
    private String[] parentsEntityKeyDataPath;
    private int index;
    private int ID;
    private String DWZDM;
    private String DWMC;
    private String ZJYS;
    private boolean IsModify;
    private String orgCode;

    public int getID() {
        return this.ID;
    }

    public void setID(int iD) {
        this.ID = iD;
    }

    public String getDWZDM() {
        return this.DWZDM;
    }

    public void setDWZDM(String dWZDM) {
        this.DWZDM = dWZDM;
    }

    public String getDWMC() {
        return this.DWMC;
    }

    public void setDWMC(String dWMC) {
        this.DWMC = dWMC;
    }

    public String getZJYS() {
        return this.ZJYS;
    }

    public void setZJYS(String zJYS) {
        this.ZJYS = zJYS;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String[] getParentsEntityKeyDataPath() {
        return this.parentsEntityKeyDataPath;
    }

    public void setParentsEntityKeyDataPath(String[] parentsEntityKeyDataPath) {
        this.parentsEntityKeyDataPath = parentsEntityKeyDataPath;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public boolean isIsModify() {
        return this.IsModify;
    }

    public void setIsModify(boolean isModify) {
        this.IsModify = isModify;
    }
}

