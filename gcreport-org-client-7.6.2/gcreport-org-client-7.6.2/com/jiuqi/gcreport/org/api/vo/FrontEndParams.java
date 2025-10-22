/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.progress.common.ProgressDataImpl
 */
package com.jiuqi.gcreport.org.api.vo;

import com.jiuqi.common.expimp.progress.common.ProgressDataImpl;

public class FrontEndParams {
    private String sn;
    private int doneCount;
    private int totalCount;
    private ProgressDataImpl<Object> progressData;

    public FrontEndParams() {
    }

    public FrontEndParams(String sn, int totalCount, ProgressDataImpl<Object> progressData) {
        this.sn = sn;
        this.totalCount = totalCount;
        this.progressData = progressData;
    }

    public FrontEndParams(String sn, ProgressDataImpl<Object> progressData) {
        this.sn = sn;
        this.progressData = progressData;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public ProgressDataImpl<Object> getProgressData() {
        return this.progressData;
    }

    public void setProgressData(ProgressDataImpl<Object> progressData) {
        this.progressData = progressData;
    }

    public String getSn() {
        return this.sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public int getDoneCount() {
        return this.doneCount;
    }

    public void setDoneCount(int doneCount) {
        this.doneCount = doneCount;
    }

    public void addDoneCount(int i) {
        this.doneCount += i;
    }
}

