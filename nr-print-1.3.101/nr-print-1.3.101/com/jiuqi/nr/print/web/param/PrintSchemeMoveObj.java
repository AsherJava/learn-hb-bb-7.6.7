/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.print.web.param;

public class PrintSchemeMoveObj {
    private String[] printSchemeKey;
    private String formSchemeKey;
    private String moveType;

    public String[] getPrintSchemeKey() {
        return this.printSchemeKey;
    }

    public void setPrintSchemeKey(String[] printSchemeKey) {
        this.printSchemeKey = printSchemeKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String fromSchemeKey) {
        this.formSchemeKey = fromSchemeKey;
    }

    public String getMoveType() {
        return this.moveType;
    }

    public void setMoveType(String moveType) {
        this.moveType = moveType;
    }
}

