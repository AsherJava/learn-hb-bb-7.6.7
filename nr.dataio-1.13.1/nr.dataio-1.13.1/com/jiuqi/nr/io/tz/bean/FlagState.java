/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.io.tz.bean;

public class FlagState {
    private Integer add;
    private Integer recordUpdate;
    private Integer noRecordUpdate;
    private Integer del;
    private Integer none;
    private Integer rptAdd;
    private Integer rptUpdate;
    private Integer rptDel;
    private Integer rptNone;

    public FlagState() {
    }

    public FlagState(Integer add, Integer recordUpdate, Integer noRecordUpdate, Integer del, Integer none) {
        this.add = add;
        this.recordUpdate = recordUpdate;
        this.noRecordUpdate = noRecordUpdate;
        this.del = del;
        this.none = none;
    }

    public Integer getAdd() {
        return this.add;
    }

    public void setAdd(Integer add) {
        this.add = add;
    }

    public Integer getRecordUpdate() {
        return this.recordUpdate;
    }

    public void setRecordUpdate(Integer recordUpdate) {
        this.recordUpdate = recordUpdate;
    }

    public Integer getNoRecordUpdate() {
        return this.noRecordUpdate;
    }

    public void setNoRecordUpdate(Integer noRecordUpdate) {
        this.noRecordUpdate = noRecordUpdate;
    }

    public Integer getDel() {
        return this.del;
    }

    public void setDel(Integer del) {
        this.del = del;
    }

    public Integer getNone() {
        return this.none;
    }

    public void setNone(Integer none) {
        this.none = none;
    }

    public Integer getRptAdd() {
        return this.rptAdd;
    }

    public void setRptAdd(Integer rptAdd) {
        this.rptAdd = rptAdd;
    }

    public Integer getRptUpdate() {
        return this.rptUpdate;
    }

    public void setRptUpdate(Integer rptUpdate) {
        this.rptUpdate = rptUpdate;
    }

    public Integer getRptDel() {
        return this.rptDel;
    }

    public void setRptDel(Integer rptDel) {
        this.rptDel = rptDel;
    }

    public Integer getRptNone() {
        return this.rptNone;
    }

    public void setRptNone(Integer rptNone) {
        this.rptNone = rptNone;
    }

    public boolean isChange() {
        if (this.add != null && this.noRecordUpdate != null && this.recordUpdate != null && this.del != null) {
            return this.add + this.noRecordUpdate + this.recordUpdate + this.del != 0;
        }
        return false;
    }

    public boolean isRptChange() {
        if (this.rptAdd != null && this.rptUpdate != null) {
            return this.rptAdd + this.rptUpdate != 0;
        }
        return false;
    }

    public boolean isContainsAdd() {
        if (this.add != null) {
            return this.add != 0;
        }
        return false;
    }

    public boolean isContainsDel() {
        if (this.del != null) {
            return this.del != 0;
        }
        return false;
    }

    public boolean isContainsRecordUpdate() {
        if (this.recordUpdate != null) {
            return this.recordUpdate != 0;
        }
        return false;
    }

    public boolean isContainsNoRecordUpdate() {
        if (this.noRecordUpdate != null) {
            return this.noRecordUpdate != 0;
        }
        return false;
    }

    public boolean isContainsNone() {
        if (this.none != null) {
            return this.none != 0;
        }
        return false;
    }

    public boolean isHaveData() {
        return this.isContainsAdd() || this.isContainsDel() || this.isContainsRecordUpdate() || this.isContainsNoRecordUpdate() || this.isContainsNone();
    }
}

