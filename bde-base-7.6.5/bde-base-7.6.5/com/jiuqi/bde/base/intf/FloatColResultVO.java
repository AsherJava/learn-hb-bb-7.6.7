/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.bde.base.intf;

import com.jiuqi.bde.base.intf.FloatFieldDefineValPojo;
import com.jiuqi.common.base.util.CollectionUtils;
import java.util.LinkedHashMap;
import java.util.List;

public class FloatColResultVO {
    private LinkedHashMap<String, FloatFieldDefineValPojo> colInfoMap;
    List<Object[][]> colDataList;

    public FloatColResultVO() {
        this.colInfoMap = new LinkedHashMap();
        this.colDataList = CollectionUtils.newArrayList();
    }

    public FloatColResultVO(LinkedHashMap<String, FloatFieldDefineValPojo> colInfoMap, List<Object[][]> colDataList) {
        this.colInfoMap = colInfoMap;
        this.colDataList = colDataList;
    }

    public LinkedHashMap<String, FloatFieldDefineValPojo> getColInfoMap() {
        return this.colInfoMap;
    }

    public List<Object[][]> getColDataList() {
        return this.colDataList;
    }

    public boolean isEmpty() {
        return this.colDataList == null || this.colDataList.isEmpty();
    }

    public String toString() {
        return "FloatColResultVO [colInfoMap=" + this.colInfoMap + ", colDataList=" + this.colDataList + "]";
    }
}

