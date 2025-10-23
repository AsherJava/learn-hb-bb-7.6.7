/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.xlib.utils.GUID
 */
package com.jiuqi.nr.migration.transferdata.bean;

import com.jiuqi.xlib.utils.GUID;
import java.time.Instant;

public class CheckErrorDesc {
    private GUID recid;
    private String formulaExp;
    private String formulaCode;
    private String floatId;
    private String floatCode;
    private String description;
    private Instant modifyTime;
    private String userId;

    public GUID getRecid() {
        return this.recid;
    }

    public void setRecid(GUID recid) {
        this.recid = recid;
    }

    public String getFormulaExp() {
        return this.formulaExp;
    }

    public void setFormulaExp(String formulaExp) {
        this.formulaExp = formulaExp;
    }

    public String getFormulaCode() {
        return this.formulaCode;
    }

    public void setFormulaCode(String formulaCode) {
        this.formulaCode = formulaCode;
    }

    public String getFloatId() {
        return this.floatId;
    }

    public void setFloatId(String floatId) {
        this.floatId = floatId;
    }

    public String getFloatCode() {
        return this.floatCode;
    }

    public void setFloatCode(String floatCode) {
        this.floatCode = floatCode;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Instant modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

