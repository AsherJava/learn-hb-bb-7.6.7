/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.report.tree;

import com.jiuqi.bi.dataset.report.model.ReportDSModel;
import com.jiuqi.bi.dataset.report.tree.SortedUnitTreeInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SortedUnitTree {
    private Map<String, SortedUnitTreeInfo> unitTreeInfos = new HashMap<String, SortedUnitTreeInfo>();
    private ReportDSModel dsModel;

    public SortedUnitTree(ReportDSModel dsModel) {
        this.dsModel = dsModel;
    }

    public SortedUnitTreeInfo getUnitTreeInfo(Object[] originalRow) {
        return this.unitTreeInfos.get(originalRow[this.dsModel.getUnitKeyIndex()]);
    }

    public List<Object[]> getUnitTreeRows(SortedUnitTreeInfo info, Object[] originalRow) {
        ArrayList<Object[]> treeRows = new ArrayList<Object[]>();
        for (String parent : info.getParents()) {
            Object[] newRow = new Object[originalRow.length];
            System.arraycopy(originalRow, 0, newRow, 0, originalRow.length);
            newRow[this.dsModel.getUnitParentIndex()] = parent;
            newRow[this.dsModel.getUnitOrderIndex()] = info.getOrder();
            treeRows.add(newRow);
        }
        return treeRows;
    }

    public void resetOrder(Object[] row) {
        SortedUnitTreeInfo info = this.unitTreeInfos.get(row[this.dsModel.getUnitKeyIndex()]);
        row[this.dsModel.getUnitOrderIndex()] = info.getOrder();
    }

    public void putDetailUnitToTree(String unitKey, String unitParent, Object unitOrder) {
        SortedUnitTreeInfo info = this.unitTreeInfos.get(unitKey);
        if (info == null) {
            ArrayList<String> parents = new ArrayList<String>();
            info = new SortedUnitTreeInfo(unitKey, parents, unitOrder);
            parents.add(unitParent);
            this.unitTreeInfos.put(unitKey, info);
        }
    }

    public void putMainUnitToTree(String unitKey, Object unitOrder) {
        this.unitTreeInfos.put(unitKey, new SortedUnitTreeInfo(unitKey, unitOrder));
    }

    public List<String> getAllDetailUnits() {
        ArrayList<String> detailUnits = new ArrayList<String>();
        for (SortedUnitTreeInfo info : this.unitTreeInfos.values()) {
            if (info.getParents() == null) continue;
            detailUnits.add(info.getUnitKey());
        }
        return detailUnits;
    }

    public boolean isResetParent() {
        return this.dsModel.getReportDsModelDefine().isShowDetail();
    }
}

