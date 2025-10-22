/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.common;

public class EntityCheckUpUnitInfo {
    private String dwmc;
    private String zjys;
    private String dwzdm;

    public String getDwzdm() {
        return this.dwzdm;
    }

    public void setDwzdm(String dwzdm) {
        this.dwzdm = dwzdm;
    }

    public String getDwmc() {
        return this.dwmc;
    }

    public void setDwmc(String dwmc) {
        this.dwmc = dwmc;
    }

    public String getZjys() {
        return this.zjys;
    }

    public void setZjys(String zjys) {
        this.zjys = zjys;
    }

    public boolean equals(Object o) {
        EntityCheckUpUnitInfo _this = (EntityCheckUpUnitInfo)o;
        if (_this == null) {
            return false;
        }
        return this.getDwzdm().equals(_this.getDwzdm());
    }
}

