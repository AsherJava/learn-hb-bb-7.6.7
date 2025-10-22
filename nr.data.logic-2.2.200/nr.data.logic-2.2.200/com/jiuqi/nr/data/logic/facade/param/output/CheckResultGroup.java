/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.param.output;

import com.jiuqi.nr.data.logic.facade.param.output.CheckResultGroupData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckResultGroup {
    private String message;
    private int totalCount;
    private int showCount;
    @Deprecated
    private Map<String, Integer> checkTypeCountMap = new HashMap<String, Integer>();
    private List<CheckResultGroupData> groupData = new ArrayList<CheckResultGroupData>();

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

    public int getShowCount() {
        return this.showCount;
    }

    public void setShowCount(int showCount) {
        this.showCount = showCount;
    }

    @Deprecated
    public Map<String, Integer> getCheckTypeCountMap() {
        return this.checkTypeCountMap;
    }

    @Deprecated
    public void setCheckTypeCountMap(Map<String, Integer> checkTypeCountMap) {
        this.checkTypeCountMap = checkTypeCountMap;
    }

    public List<CheckResultGroupData> getGroupData() {
        return this.groupData;
    }

    public void setGroupData(List<CheckResultGroupData> groupData) {
        this.groupData = groupData;
    }
}

