/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 */
package com.jiuqi.gcreport.offsetitem.dto;

import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OffsetItemExportParam
extends QueryParamsVO {
    private List<String> exportTabs = new ArrayList<String>();
    private Map<String, String> selectFilterMethodByTab = new HashMap<String, String>();
    private List<String> notOffsetParentOtherColumns;
    private List<String> notOffsetParentOtherTitles;

    public List<String> getExportTabs() {
        return this.exportTabs;
    }

    public void setExportTabs(List<String> exportTabs) {
        this.exportTabs = exportTabs;
    }

    public Map<String, String> getSelectFilterMethodByTab() {
        return this.selectFilterMethodByTab;
    }

    public void setSelectFilterMethodByTab(Map<String, String> selectFilterMethodByTab) {
        this.selectFilterMethodByTab = selectFilterMethodByTab;
    }

    public List<String> getNotOffsetParentOtherColumns() {
        return this.notOffsetParentOtherColumns;
    }

    public void setNotOffsetParentOtherColumns(List<String> notOffsetParentOtherColumns) {
        this.notOffsetParentOtherColumns = notOffsetParentOtherColumns;
    }

    public List<String> getNotOffsetParentOtherTitles() {
        return this.notOffsetParentOtherTitles;
    }

    public void setNotOffsetParentOtherTitles(List<String> notOffsetParentOtherTitles) {
        this.notOffsetParentOtherTitles = notOffsetParentOtherTitles;
    }
}

