/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.data.logic.facade.param.output;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResultData;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckResult
implements Serializable {
    private static final long serialVersionUID = -8309428428041380132L;
    private String message;
    private int totalCount;
    private Map<String, Integer> checkTypeCountMap = new HashMap<String, Integer>();
    private List<Map<String, DimensionValue>> dimensionList = new ArrayList<Map<String, DimensionValue>>();
    private List<String> ckdDescList = new ArrayList<String>();
    private List<CheckResultData> resultData = new ArrayList<CheckResultData>();

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public Map<String, Integer> getCheckTypeCountMap() {
        return this.checkTypeCountMap;
    }

    public void setCheckTypeCountMap(Map<String, Integer> checkTypeCountMap) {
        this.checkTypeCountMap = checkTypeCountMap;
    }

    public List<Map<String, DimensionValue>> getDimensionList() {
        return this.dimensionList;
    }

    public void setDimensionList(List<Map<String, DimensionValue>> dimensionList) {
        this.dimensionList = dimensionList;
    }

    public List<CheckResultData> getResultData() {
        return this.resultData;
    }

    public void setResultData(List<CheckResultData> resultData) {
        this.resultData = resultData;
    }

    public List<String> getCkdDescList() {
        return this.ckdDescList;
    }

    public void setCkdDescList(List<String> ckdDescList) {
        this.ckdDescList = ckdDescList;
    }
}

