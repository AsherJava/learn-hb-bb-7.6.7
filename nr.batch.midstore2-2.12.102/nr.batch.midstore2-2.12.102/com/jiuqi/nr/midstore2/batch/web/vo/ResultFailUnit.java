/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.midstore2.batch.web.vo;

import com.jiuqi.nr.midstore2.batch.web.vo.ResultFailUnitInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultFailUnit
implements Serializable {
    private String code;
    private String title;
    private List<ResultFailUnitInfo> failDetails;
    private int unitErrorCount = 0;

    public ResultFailUnit() {
    }

    public ResultFailUnit(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public void add(ResultFailUnitInfo resultFailUnitScheme) {
        if (this.failDetails == null) {
            this.failDetails = new ArrayList<ResultFailUnitInfo>();
        }
        ++this.unitErrorCount;
        this.failDetails.add(resultFailUnitScheme);
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ResultFailUnitInfo> getFailDetails() {
        return this.failDetails;
    }

    public void setFailDetails(List<ResultFailUnitInfo> failDetails) {
        this.failDetails = failDetails;
    }

    public int getUnitErrorCount() {
        return this.unitErrorCount;
    }

    public void setUnitErrorCount(int unitErrorCount) {
        this.unitErrorCount = unitErrorCount;
    }
}

