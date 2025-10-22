/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.efdc.param;

public class EfdcEnableInfo {
    private int BBLX;
    private boolean EFDCEnable;
    private boolean EFDCSwitch;

    public boolean isEFDCSwitch() {
        return this.EFDCSwitch;
    }

    public void setEFDCSwitch(boolean EFDCSwitch) {
        this.EFDCSwitch = EFDCSwitch;
    }

    public int getBBLX() {
        return this.BBLX;
    }

    public void setBBLX(int BBLX) {
        this.BBLX = BBLX;
    }

    public boolean isEFDCEnable() {
        return this.EFDCEnable;
    }

    public void setEFDCEnable(boolean EFDCEnable) {
        this.EFDCEnable = EFDCEnable;
    }
}

