/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.para.parser.anal;

import com.jiuqi.nr.single.core.para.parser.anal.AnalTableInfo;
import java.util.ArrayList;
import java.util.List;

public class AnalTableSet {
    private String code;
    private String parentCode;
    private String title;
    private String descript;
    private int fitRange;
    private String filter;
    private List<AnalTableSet> subTabSets;
    private List<String> subRepCodes;
    private List<AnalTableInfo> subTables;
    private List<AnalTableInfo> subAllTables;
    private AnalTableSet parent;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentCode() {
        return this.parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescript() {
        return this.descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public int getFitRange() {
        return this.fitRange;
    }

    public void setFitRange(int fitRange) {
        this.fitRange = fitRange;
    }

    public String getFilter() {
        return this.filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public List<AnalTableSet> getSubTabSets() {
        if (this.subTabSets == null) {
            this.subTabSets = new ArrayList<AnalTableSet>();
        }
        return this.subTabSets;
    }

    public void setSubTabSets(List<AnalTableSet> subTabSets) {
        this.subTabSets = subTabSets;
    }

    public List<String> getSubRepCodes() {
        if (this.subRepCodes == null) {
            this.subRepCodes = new ArrayList<String>();
        }
        return this.subRepCodes;
    }

    public void setSubRepCodes(List<String> subRepCodes) {
        this.subRepCodes = subRepCodes;
    }

    public List<AnalTableInfo> getSubTables() {
        if (this.subTables == null) {
            this.subTables = new ArrayList<AnalTableInfo>();
        }
        return this.subTables;
    }

    public void setSubTables(List<AnalTableInfo> subTables) {
        this.subTables = subTables;
    }

    public List<AnalTableInfo> getSubAllTables() {
        if (this.subAllTables == null) {
            this.subAllTables = new ArrayList<AnalTableInfo>();
        }
        return this.subAllTables;
    }

    public void setSubAllTables(List<AnalTableInfo> subAllTables) {
        this.subAllTables = subAllTables;
    }

    public void loadAllSubTables() {
        ArrayList<AnalTableInfo> aSubAllTables = new ArrayList<AnalTableInfo>();
        this.loadSubTables(aSubAllTables);
    }

    public void loadSubTables(List<AnalTableInfo> aSubAllTables) {
        this.getSubAllTables().clear();
        this.getSubAllTables().addAll(this.getSubTables());
        for (AnalTableSet tableSet : this.getSubTabSets()) {
            tableSet.loadSubTables(aSubAllTables);
            this.getSubAllTables().addAll(tableSet.getSubAllTables());
        }
    }

    public AnalTableSet getParent() {
        return this.parent;
    }

    public void setParent(AnalTableSet parent) {
        this.parent = parent;
    }
}

