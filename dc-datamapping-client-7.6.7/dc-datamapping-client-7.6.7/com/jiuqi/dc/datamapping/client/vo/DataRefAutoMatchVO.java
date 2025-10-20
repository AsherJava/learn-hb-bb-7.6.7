/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.datamapping.client.vo;

import com.jiuqi.dc.datamapping.client.dto.DataRefSaveDTO;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DataRefAutoMatchVO
implements Serializable {
    private static final long serialVersionUID = 8233187828707022467L;
    private Map<String, Integer> countMap = new HashMap<String, Integer>();
    private DataRefSaveDTO tempData;

    public Map<String, Integer> getCountMap() {
        return this.countMap;
    }

    public void setCountMap(Map<String, Integer> countMap) {
        this.countMap = countMap;
    }

    public Map<String, Integer> record(String matchDim, Integer count) {
        this.countMap.put(matchDim, count);
        return this.countMap;
    }

    public DataRefSaveDTO getTempData() {
        return this.tempData;
    }

    public void setTempData(DataRefSaveDTO tempData) {
        this.tempData = tempData;
    }
}

