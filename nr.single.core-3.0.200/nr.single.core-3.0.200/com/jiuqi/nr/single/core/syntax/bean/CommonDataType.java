/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.syntax.bean;

import com.jiuqi.nr.single.core.syntax.common.CommonDataTypeType;
import java.util.Date;

public class CommonDataType {
    private CommonDataTypeType cdType;
    private int cdInt;
    private double cdReal;
    private String cdString;
    private boolean cdBool;
    private Date cdDate;
    private String cdBlob;
    private String cdText;
    private String cdBlob2;

    public CommonDataTypeType getCdType() {
        return this.cdType;
    }

    public void setCdType(CommonDataTypeType cdType) {
        this.cdType = cdType;
    }

    public int getCdInt() {
        return this.cdInt;
    }

    public void setCdInt(int cdInt) {
        this.cdInt = cdInt;
    }

    public double getCdReal() {
        return this.cdReal;
    }

    public void setCdReal(double cdReal) {
        this.cdReal = cdReal;
    }

    public String getCdString() {
        return this.cdString;
    }

    public void setCdString(String cdString) {
        this.cdString = cdString;
    }

    public boolean isCdBool() {
        return this.cdBool;
    }

    public Date getCdDate() {
        return this.cdDate;
    }

    public String getCdBlob() {
        return this.cdBlob;
    }

    public String getCdText() {
        return this.cdText;
    }

    public String getCdBlob2() {
        return this.cdBlob2;
    }

    public void setCdBool(boolean cdBool) {
        this.cdBool = cdBool;
    }

    public void setCdDate(Date cdDate) {
        this.cdDate = cdDate;
    }

    public void setCdBlob(String cdBlob) {
        this.cdBlob = cdBlob;
    }

    public void setCdText(String cdText) {
        this.cdText = cdText;
    }

    public void setCdBlob2(String cdBlob2) {
        this.cdBlob2 = cdBlob2;
    }
}

