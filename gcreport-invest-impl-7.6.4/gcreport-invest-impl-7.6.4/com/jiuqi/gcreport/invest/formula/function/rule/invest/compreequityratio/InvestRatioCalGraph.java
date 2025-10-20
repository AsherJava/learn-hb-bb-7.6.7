/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.invest.formula.function.rule.invest.compreequityratio;

import com.jiuqi.gcreport.invest.formula.function.rule.invest.compreequityratio.InvestNodeInfo;
import java.util.ArrayList;
import java.util.List;

public class InvestRatioCalGraph {
    private List<InvestNodeInfo> nodeList = new ArrayList<InvestNodeInfo>();

    public List<InvestNodeInfo> getNodeList() {
        return this.nodeList;
    }

    private void delEdge(int start, int end) {
        List<Integer> adjNodeList = this.nodeList.get(start).getAdjNodeList();
        if (adjNodeList.get(end) < 0) {
            return;
        }
        adjNodeList.remove(adjNodeList.get(end));
    }

    public void addEdge(int start, int end) {
        this.nodeList.get(start).getAdjNodeList().add(end);
    }

    public int addVertex(String unitCode) {
        this.nodeList.add(new InvestNodeInfo(unitCode));
        return this.nodeList.size() - 1;
    }

    public String getNodeId(int i) {
        return this.nodeList.get(i).getUnitId();
    }

    public boolean displayVertexVisited(int i) {
        return this.nodeList.get(i).WasVisited();
    }
}

