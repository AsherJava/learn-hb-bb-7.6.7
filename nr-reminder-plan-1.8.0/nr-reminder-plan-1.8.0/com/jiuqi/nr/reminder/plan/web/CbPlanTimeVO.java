/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 */
package com.jiuqi.nr.reminder.plan.web;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.Objects;

public class CbPlanTimeVO {
    private int periodValue;
    private int periodValue2;
    @JsonFormat(pattern="HH:mm", timezone="GMT+8")
    private Date time;

    public int getPeriodValue() {
        return this.periodValue;
    }

    public void setPeriodValue(int periodValue) {
        this.periodValue = periodValue;
    }

    public int getPeriodValue2() {
        return this.periodValue2;
    }

    public void setPeriodValue2(int periodValue2) {
        this.periodValue2 = periodValue2;
    }

    public Date getTime() {
        return this.time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        CbPlanTimeVO timeVO = (CbPlanTimeVO)o;
        if (this.periodValue != timeVO.periodValue) {
            return false;
        }
        if (this.periodValue2 != timeVO.periodValue2) {
            return false;
        }
        return Objects.equals(this.time, timeVO.time);
    }

    public int hashCode() {
        int result = this.periodValue;
        result = 31 * result + this.periodValue2;
        result = 31 * result + (this.time != null ? this.time.hashCode() : 0);
        return result;
    }
}

