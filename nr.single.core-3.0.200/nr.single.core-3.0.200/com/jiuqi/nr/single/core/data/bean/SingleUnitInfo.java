/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.data.bean;

import java.io.Serializable;

public class SingleUnitInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String singleZdm;
    private String singlePeriod;
    private String singleCode;
    private String singleTitle;
    private String singleQYDM;
    private String singleBBLX;
    private String singleParent;
    private String tempQYDM;
    private String singleMapCode;

    public String getSingleCode() {
        return this.singleCode;
    }

    public void setSingleCode(String singleCode) {
        this.singleCode = singleCode;
    }

    public String getSingleZdm() {
        return this.singleZdm;
    }

    public void setSingleZdm(String singleZdm) {
        this.singleZdm = singleZdm;
    }

    public String getSinglePeriod() {
        return this.singlePeriod;
    }

    public void setSinglePeriod(String singlePeriod) {
        this.singlePeriod = singlePeriod;
    }

    public String getSingleTitle() {
        return this.singleTitle;
    }

    public void setSingleTitle(String singleTitle) {
        this.singleTitle = singleTitle;
    }

    public String getSingleQYDM() {
        return this.singleQYDM;
    }

    public void setSingleQYDM(String singleQYDM) {
        this.singleQYDM = singleQYDM;
    }

    public String getSingleBBLX() {
        return this.singleBBLX;
    }

    public void setSingleBBLX(String singleBBLX) {
        this.singleBBLX = singleBBLX;
    }

    public String getSingleParent() {
        return this.singleParent;
    }

    public void setSingleParent(String singleParent) {
        this.singleParent = singleParent;
    }

    public String getTempQYDM() {
        return this.tempQYDM;
    }

    public void setTempQYDM(String tempQYDM) {
        this.tempQYDM = tempQYDM;
    }

    public String getSingleMapCode() {
        return this.singleMapCode;
    }

    public void setSingleMapCode(String singleMapCode) {
        this.singleMapCode = singleMapCode;
    }
}

