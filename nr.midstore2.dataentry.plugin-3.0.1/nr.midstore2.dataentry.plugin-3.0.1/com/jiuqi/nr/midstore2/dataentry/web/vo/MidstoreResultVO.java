/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.midstore2.dataentry.web.vo;

import com.jiuqi.nr.midstore2.dataentry.web.vo.FailedUnit;
import com.jiuqi.nr.midstore2.dataentry.web.vo.Lable;
import java.util.List;

public class MidstoreResultVO {
    private int allCount;
    private int successCount;
    private List<Lable> successUnits;
    private int failedCount;
    private List<FailedUnit> errors;
    private int ignoreCount;
    private List<Lable> ignoreUnits;

    public int getAllCount() {
        return this.allCount;
    }

    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }

    public int getSuccessCount() {
        return this.successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public List<Lable> getSuccessUnits() {
        return this.successUnits;
    }

    public void setSuccessUnits(List<Lable> successUnits) {
        this.successUnits = successUnits;
    }

    public int getFailedCount() {
        return this.failedCount;
    }

    public void setFailedCount(int failedCount) {
        this.failedCount = failedCount;
    }

    public List<FailedUnit> getErrors() {
        return this.errors;
    }

    public void setErrors(List<FailedUnit> errors) {
        this.errors = errors;
    }

    public int getIgnoreCount() {
        return this.ignoreCount;
    }

    public void setIgnoreCount(int ignoreCount) {
        this.ignoreCount = ignoreCount;
    }

    public List<Lable> getIgnoreUnits() {
        return this.ignoreUnits;
    }

    public void setIgnoreUnits(List<Lable> ignoreUnits) {
        this.ignoreUnits = ignoreUnits;
    }
}

