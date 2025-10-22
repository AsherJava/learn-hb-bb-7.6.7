/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.params.output;

import java.util.List;
import java.util.Map;

public class FindPageResult {
    private String areaKey;
    private List<Map<String, Object>> cellList;
    private int num;
    private boolean exceedFlag;

    public boolean isExceedFlag() {
        return this.exceedFlag;
    }

    public void setExceedFlag(boolean exceedFlag) {
        this.exceedFlag = exceedFlag;
    }

    public String getAreaKey() {
        return this.areaKey;
    }

    public void setAreaKey(String areaKey) {
        this.areaKey = areaKey;
    }

    public List<Map<String, Object>> getCellList() {
        return this.cellList;
    }

    public void setCellList(List<Map<String, Object>> cellList) {
        this.cellList = cellList;
    }

    public int getNum() {
        return this.num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}

