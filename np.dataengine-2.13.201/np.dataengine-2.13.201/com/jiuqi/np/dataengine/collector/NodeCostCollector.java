/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.reportparser.CostCalculator
 */
package com.jiuqi.np.dataengine.collector;

import com.jiuqi.bi.syntax.reportparser.CostCalculator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NodeCostCollector {
    private Map<String, CostCalculator> funcCostCalculators = new HashMap<String, CostCalculator>();
    private Map<String, CostCalculator> expCostCalculators = new HashMap<String, CostCalculator>();

    public CostCalculator getFuncCostCalculator(String funcName) {
        CostCalculator calculator = this.funcCostCalculators.get(funcName);
        if (calculator == null) {
            calculator = new CostCalculator(funcName);
            this.funcCostCalculators.put(funcName, calculator);
        }
        return calculator;
    }

    public CostCalculator getExpCostCalculator(String expKey) {
        CostCalculator calculator = this.expCostCalculators.get(expKey);
        if (calculator == null) {
            calculator = new CostCalculator(expKey);
            this.expCostCalculators.put(expKey, calculator);
        }
        return calculator;
    }

    public List<CostCalculator> getAllFuncCostCalculators() {
        return new ArrayList<CostCalculator>(this.funcCostCalculators.values());
    }

    public List<CostCalculator> getAllExpCostCalculators() {
        return new ArrayList<CostCalculator>(this.expCostCalculators.values());
    }

    public List<CostCalculator> getFuncCostTopN(int count) {
        long maxSize = this.funcCostCalculators.size() > count ? (long)count : (long)this.funcCostCalculators.size();
        return this.funcCostCalculators.values().stream().sorted(Comparator.comparing(CostCalculator::getTotalCost).reversed()).limit(maxSize).collect(Collectors.toList());
    }

    public List<CostCalculator> getFuncTimesTopN(int count) {
        long maxSize = this.funcCostCalculators.size() > count ? (long)count : (long)this.funcCostCalculators.size();
        return this.funcCostCalculators.values().stream().sorted(Comparator.comparing(CostCalculator::getTimes).reversed()).limit(maxSize).collect(Collectors.toList());
    }

    public List<CostCalculator> getFuncAvgCostTopN(int count) {
        long maxSize = this.funcCostCalculators.size() > count ? (long)count : (long)this.funcCostCalculators.size();
        return this.funcCostCalculators.values().stream().sorted(Comparator.comparing(CostCalculator::getAvgCost).reversed()).limit(maxSize).collect(Collectors.toList());
    }

    public List<CostCalculator> getExpCostTopN(int count) {
        long maxSize = this.expCostCalculators.size() > count ? (long)count : (long)this.expCostCalculators.size();
        return this.expCostCalculators.values().stream().sorted(Comparator.comparing(CostCalculator::getTotalCost).reversed()).limit(maxSize).collect(Collectors.toList());
    }

    public List<CostCalculator> getExpTimesTopN(int count) {
        long maxSize = this.expCostCalculators.size() > count ? (long)count : (long)this.expCostCalculators.size();
        return this.expCostCalculators.values().stream().sorted(Comparator.comparing(CostCalculator::getTimes).reversed()).limit(maxSize).collect(Collectors.toList());
    }

    public List<CostCalculator> getExpAvgCostTopN(int count) {
        long maxSize = this.expCostCalculators.size() > count ? (long)count : (long)this.expCostCalculators.size();
        return this.expCostCalculators.values().stream().sorted(Comparator.comparing(CostCalculator::getAvgCost).reversed()).limit(maxSize).collect(Collectors.toList());
    }
}

