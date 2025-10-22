/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.invest.formula.function.rule.invest.compreequityratio;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.invest.formula.function.rule.invest.compreequityratio.InvestRatioCalGraph;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class InvestCalculator {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String CONNECTOR = "|";
    private InvestRatioCalGraph graph;
    private int nodeCount;
    private Stack<Integer> theStack;
    private Map<String, Double> sourceIdAndTargetId2rate = new HashMap<String, Double>();
    private Map<String, Integer> unitId2nodeIndexMap = new HashMap<String, Integer>();
    private String baseUnitId;
    private BigDecimal compreEquityRatio;

    public InvestCalculator() {
        this.graph = new InvestRatioCalGraph();
    }

    public InvestCalculator(String baseUnitId) {
        this.graph = new InvestRatioCalGraph();
        this.baseUnitId = baseUnitId;
    }

    public String getBaseUnitId() {
        return this.baseUnitId;
    }

    public void setBaseUnitId(String baseUnitId) {
        this.baseUnitId = baseUnitId;
    }

    public double getRate(String unitId) {
        this.compreEquityRatio = BigDecimal.valueOf(0L);
        if (StringUtils.isEmpty((String)unitId)) {
            this.logger.error("\u6301\u80a1\u6bd4\u4f8b\u8ba1\u7b97\u76ee\u6807\u5355\u4f4d\u4e0d\u80fd\u4e3a\u7a7a");
            return 0.0;
        }
        if (StringUtils.isEmpty((String)this.baseUnitId)) {
            this.logger.error("\u8ba1\u7b97\u5de5\u5177\u7c7b\u4e2d\u672a\u8bbe\u7f6e\u672c\u90e8\u5355\u4f4d,\u6301\u80a1\u6bd4\u4f8b\u4e3a0", (Object)this.baseUnitId, (Object)unitId);
            return 0.0;
        }
        if (this.baseUnitId.equals(unitId)) {
            return 1.0;
        }
        if (!this.unitId2nodeIndexMap.containsKey(unitId) || !this.unitId2nodeIndexMap.containsKey(this.baseUnitId)) {
            this.logger.info("\u672c\u90e8\u5355\u4f4d[{}]\u548c\u76ee\u6807\u5355\u4f4d[{}]\u4e4b\u95f4\u6ca1\u6709\u6295\u8d44\u8def\u5f84\uff0c\u6301\u80a1\u6bd4\u4f8b\u4e3a0", (Object)this.baseUnitId, (Object)unitId);
            return 0.0;
        }
        int start = this.unitId2nodeIndexMap.get(this.baseUnitId);
        int end = this.unitId2nodeIndexMap.get(unitId);
        this.nodeCount = this.graph.getNodeList().size();
        this.theStack = new Stack();
        if (!this.isConnectable(start, end)) {
            this.logger.info("\u672c\u90e8\u5355\u4f4d[{}]\u548c\u76ee\u6807\u5355\u4f4d[{}]\u4e4b\u95f4\u6ca1\u6709\u6295\u8d44\u8def\u5f84\uff0c\u6301\u80a1\u6bd4\u4f8b\u4e3a0", (Object)this.baseUnitId, (Object)unitId);
            return 0.0;
        }
        for (int j = 0; j < this.nodeCount; ++j) {
            ArrayList<Integer> cleanAllVisitedList = new ArrayList<Integer>();
            for (int i = 0; i < this.nodeCount; ++i) {
                cleanAllVisitedList.add(0);
            }
            this.graph.getNodeList().get(j).setAllVisitedList(cleanAllVisitedList);
        }
        this.calculate(start, end);
        double ratio = this.compreEquityRatio.setScale(16, 4).doubleValue();
        return ratio;
    }

    private void calculate(int start, int end) {
        this.graph.getNodeList().get(start).setWasVisited(true);
        this.theStack.push(start);
        while (!this.theStack.isEmpty()) {
            int nextIndex = this.getAdjUnvisitedVertex(this.theStack.peek());
            if (nextIndex == -1) {
                ArrayList<Integer> cleanAllVisitedList = new ArrayList<Integer>();
                for (int j = 0; j < this.nodeCount; ++j) {
                    cleanAllVisitedList.add(0);
                }
                this.graph.getNodeList().get(this.theStack.peek()).setAllVisitedList(cleanAllVisitedList);
                this.theStack.pop();
            } else {
                this.theStack.push(nextIndex);
            }
            if (this.theStack.isEmpty() || end != this.theStack.peek()) continue;
            this.graph.getNodeList().get(end).setWasVisited(false);
            this.calculatorTheStackRatio(this.theStack);
            this.theStack.pop();
        }
    }

    private boolean isConnectable(int start, int end) {
        ArrayList<Integer> queue = new ArrayList<Integer>();
        ArrayList visited = new ArrayList();
        queue.add(start);
        while (!queue.isEmpty()) {
            for (int j = 0; j < this.nodeCount; ++j) {
                if (!this.graph.getNodeList().get(start).getAdjNodeList().contains(j) || visited.contains(j)) continue;
                queue.add(j);
            }
            if (queue.contains(end)) {
                return true;
            }
            visited.add(queue.get(0));
            queue.remove(0);
            if (queue.isEmpty()) continue;
            start = (Integer)queue.get(0);
        }
        return false;
    }

    public int getAdjUnvisitedVertex(int v) {
        List<Integer> visitedList = this.graph.getNodeList().get(v).getAllVisitedList();
        List<Integer> adjNodeList = this.graph.getNodeList().get(v).getAdjNodeList();
        for (Integer adjIndex : adjNodeList) {
            if (visitedList.get(adjIndex) != 0 || this.theStack.contains(adjIndex)) continue;
            this.graph.getNodeList().get(v).setVisited(adjIndex);
            return adjIndex;
        }
        return -1;
    }

    public void calculatorTheStackRatio(Stack<Integer> theStack2) {
        BigDecimal currCompreEquityRatio = BigDecimal.valueOf(1L);
        ArrayList<Integer> routeList = new ArrayList<Integer>(theStack2);
        StringBuffer investmentPathLog = new StringBuffer();
        for (int i = 0; i < routeList.size(); ++i) {
            investmentPathLog.append(this.graph.getNodeId((Integer)routeList.get(i)));
            if (i == routeList.size() - 1) continue;
            String sourceId = this.graph.getNodeId((Integer)routeList.get(i));
            String targetId = this.graph.getNodeId((Integer)routeList.get(i + 1));
            Double rate = this.sourceIdAndTargetId2rate.get(this.getSourceIdAndTargetId2RateKey(sourceId, targetId));
            currCompreEquityRatio = currCompreEquityRatio.multiply(BigDecimal.valueOf(rate));
            investmentPathLog.append("-->\u3010\u6301\u80a1\u6bd4\u4f8b").append(BigDecimal.valueOf(rate)).append("\u3011");
        }
        this.compreEquityRatio = this.compreEquityRatio.add(currCompreEquityRatio);
        this.logger.info("\u6295\u8d44\u8def\u5f84\u4fe1\u606f\uff1a[{}],\u6b64\u8def\u5f84\u6301\u80a1\u6bd4\u4f8b\uff1a[{}]", (Object)investmentPathLog, (Object)currCompreEquityRatio);
    }

    private String getSourceIdAndTargetId2RateKey(String sourceId, String targetId) {
        return sourceId + CONNECTOR + targetId;
    }

    public void addInvest(String sourceId, String targetId, double rate) {
        this.sourceIdAndTargetId2rate.put(this.getSourceIdAndTargetId2RateKey(sourceId, targetId), rate);
        int sourceIndex = this.getIndexById(sourceId);
        int targetIdIndex = this.getIndexById(targetId);
        this.graph.addEdge(sourceIndex, targetIdIndex);
    }

    private int getIndexById(String id) {
        int index;
        if (this.unitId2nodeIndexMap.containsKey(id)) {
            index = this.unitId2nodeIndexMap.get(id);
        } else {
            index = this.graph.addVertex(id);
            this.unitId2nodeIndexMap.put(id, index);
        }
        return index;
    }

    public static void main(String[] args) {
        InvestCalculator operation = new InvestCalculator("\u96c6\u56e2");
        operation.addInvest("\u96c6\u56e2", "\u6e24\u6d77", 1.0);
        operation.addInvest("\u96c6\u56e2", "\u897f\u90e8", 1.0);
        operation.addInvest("\u96c6\u56e2", "\u5de5\u7a0b", 0.483636588947804);
        operation.addInvest("\u96c6\u56e2", "\u8d22\u52a1", 0.628975265);
        operation.addInvest("\u96c6\u56e2", "\u6d77\u6d0b\u77f3\u6cb9", 0.652052992301119);
        operation.addInvest("\u96c6\u56e2", "\u77f3\u6cb9\u70bc\u5316", 1.0);
        operation.addInvest("\u77f3\u6cb9\u70bc\u5316", "\u8d22\u52a1", 0.035335689);
        operation.addInvest("\u6d77\u6d0b\u77f3\u6cb9", "\u8d22\u52a1", 0.3180212015);
        operation.addInvest("\u8d22\u52a1", "\u5de5\u7a0b", 3.555018927682E-4);
        operation.addInvest("\u5de5\u7a0b", "\u8d22\u52a1", 0.0177);
        operation.addInvest("\u897f\u90e8", "\u5de5\u7a0b", 0.0665442881896744);
        operation.addInvest("\u6e24\u6d77", "\u5de5\u7a0b", 0.0027647288111779);
        System.out.println("A = " + operation.getRate("\u5de5\u7a0b"));
        System.out.println("B = " + operation.getRate("\u8d22\u52a1"));
    }
}

