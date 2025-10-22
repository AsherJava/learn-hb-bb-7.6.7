/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.PagerInfo
 */
package com.jiuqi.nr.integritycheck.common;

import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.integritycheck.common.TableICRInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageTableICRInfo {
    private PagerInfo pagerInfo;
    private List<TableICRInfo> tableICRInfos = new ArrayList<TableICRInfo>();
    private int lackCount;
    private Map<String, Integer> formLackMap = new HashMap<String, Integer>();
    private Map<String, Integer> unitLackFormMap = new HashMap<String, Integer>();

    public PagerInfo getPagerInfo() {
        return this.pagerInfo;
    }

    public void setPagerInfo(PagerInfo pagerInfo) {
        this.pagerInfo = pagerInfo;
    }

    public List<TableICRInfo> getTableICRInfos() {
        return this.tableICRInfos;
    }

    public void setTableICRInfos(List<TableICRInfo> tableICRInfos) {
        this.tableICRInfos = tableICRInfos;
    }

    public int getLackCount() {
        return this.lackCount;
    }

    public void setLackCount(int lackCount) {
        this.lackCount = lackCount;
    }

    public Map<String, Integer> getFormLackMap() {
        return this.formLackMap;
    }

    public void setFormLackMap(Map<String, Integer> formLackMap) {
        this.formLackMap = formLackMap;
    }

    public Map<String, Integer> getUnitLackFormMap() {
        return this.unitLackFormMap;
    }

    public void setUnitLackFormMap(Map<String, Integer> unitLackFormMap) {
        this.unitLackFormMap = unitLackFormMap;
    }
}

