/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.QueryTable
 */
package com.jiuqi.nr.data.engine.analysis.exe.network;

import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.nr.data.engine.analysis.exe.network.AnalysisExprExecRegion;
import java.util.ArrayList;
import java.util.List;

public class AnalysisSortExecRegionItem {
    private List<AnalysisSortExecRegionItem> nexts;
    public int tag = 0;
    private int key;
    private AnalysisExprExecRegion region;

    public AnalysisSortExecRegionItem(int key, AnalysisExprExecRegion region) {
        this.key = key;
        this.region = region;
    }

    public AnalysisExprExecRegion getRegion() {
        return this.region;
    }

    public List<AnalysisSortExecRegionItem> getNexts() {
        if (this.nexts == null) {
            this.nexts = new ArrayList<AnalysisSortExecRegionItem>();
        }
        return this.nexts;
    }

    public int nextCount() {
        if (this.nexts == null) {
            return 0;
        }
        return this.nexts.size();
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + this.key;
        return result;
    }

    public void reset() {
        this.tag = 0;
        if (this.nexts != null) {
            this.nexts.clear();
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        AnalysisSortExecRegionItem other = (AnalysisSortExecRegionItem)obj;
        return this.key == other.key;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        for (QueryTable table : this.region.queryRegion.getAllTableFields().keySet()) {
            sb.append(table.getAlias());
            sb.append(',');
        }
        sb.setLength(sb.length() - 1);
        sb.append(')');
        return sb.toString();
    }
}

