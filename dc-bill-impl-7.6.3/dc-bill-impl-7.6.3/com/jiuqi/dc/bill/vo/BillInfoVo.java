/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.dc.bill.vo;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class BillInfoVo {
    private String masterTableName;
    private LinkedHashSet<String> masterColumnCodes;
    private List<String> subTableNames;
    private List<LinkedHashSet<String>> subColumnCodes;
    private List<String> subPanelTitles;
    private String lastParsePanelTitleTemp;

    public String getMasterTableName() {
        return this.masterTableName;
    }

    public void setMasterTableName(String masterTableName) {
        this.masterTableName = masterTableName;
    }

    public List<String> getSubTableNames() {
        if (null == this.subTableNames) {
            this.subTableNames = new ArrayList<String>();
        }
        return this.subTableNames;
    }

    public List<String> getAllTableNames() {
        ArrayList<String> allTableNames = new ArrayList<String>();
        Assert.isNotNull((Object)this.masterTableName, (String)"\u672a\u89e3\u6790\u5230\u4e3b\u8868", (Object[])new Object[0]);
        allTableNames.add(this.masterTableName);
        allTableNames.addAll(this.subTableNames);
        return allTableNames;
    }

    public String getFirstSubTableName() {
        if (CollectionUtils.isEmpty(this.subTableNames)) {
            return null;
        }
        return this.subTableNames.get(0);
    }

    public void addSubTableName(String subTableName) {
        this.getSubTableNames().add(subTableName);
    }

    public LinkedHashSet<String> getMasterColumnCodes() {
        return this.masterColumnCodes;
    }

    public void setMasterColumnCodes(LinkedHashSet<String> masterColumnCodes) {
        this.masterColumnCodes = masterColumnCodes;
    }

    public List<LinkedHashSet<String>> getSubColumnCodes() {
        if (null == this.subColumnCodes) {
            this.subColumnCodes = new ArrayList<LinkedHashSet<String>>();
        }
        return this.subColumnCodes;
    }

    public void addSubColumnCodes(LinkedHashSet<String> subColumnCodeSet) {
        this.getSubColumnCodes().add(subColumnCodeSet);
    }

    public List<String> getSubPanelTitles() {
        if (null == this.subPanelTitles) {
            this.subPanelTitles = new ArrayList<String>();
        }
        return this.subPanelTitles;
    }

    public void addSubPanelTitles(String subPanelTitle) {
        this.getSubPanelTitles().add(subPanelTitle);
    }

    public String getLastParsePanelTitleTemp() {
        return this.lastParsePanelTitleTemp;
    }

    public void setLastParsePanelTitleTemp(String lastParsePanelTitleTemp) {
        this.lastParsePanelTitleTemp = lastParsePanelTitleTemp;
    }
}

