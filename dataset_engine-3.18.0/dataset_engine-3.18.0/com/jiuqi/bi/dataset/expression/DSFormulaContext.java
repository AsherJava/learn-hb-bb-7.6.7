/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.dataset.expression;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.manager.TimeKeyBuilder;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.DSModelFactoryManager;
import com.jiuqi.bi.dataset.model.field.CalcMode;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.HashMap;
import java.util.Map;

public class DSFormulaContext
implements IContext {
    private BIDataSetImpl dataset;
    private BIDataRow curRow;
    private Map<Integer, Object> curValueMap = new HashMap<Integer, Object>();
    private CalcMode calcMode = CalcMode.CALC_THEN_AGGR;
    private String language;

    public DSFormulaContext(BIDataSetImpl dataset) {
        this(dataset, null);
    }

    public DSFormulaContext(BIDataSetImpl dataset, BIDataRow row) {
        this.dataset = dataset;
        this.curRow = row;
    }

    public DSFormulaContext(DSModel dsModel) {
        MemoryDataSet<BIDataSetFieldInfo> memoryDataSet = DSModelFactoryManager.createMemoryDataSet(dsModel);
        try {
            TimeKeyBuilder.buildTimeKey(memoryDataSet);
        }
        catch (BIDataSetException bIDataSetException) {
            // empty catch block
        }
        this.dataset = new BIDataSetImpl(memoryDataSet);
    }

    public BIDataSetImpl getDataSet() {
        return this.dataset;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public BIDataRow getCurRow() {
        return this.curRow;
    }

    public Object getCurrentValue(int colIdx) {
        if (this.curRow != null) {
            return this.curRow.getValue(colIdx);
        }
        return this.curValueMap.get(colIdx);
    }

    public CalcMode getCalcMode() {
        return this.calcMode;
    }

    public void setCalcMode(CalcMode calcMode) {
        this.calcMode = calcMode;
    }

    public void setCurrentValue(int colIdx, Object value) {
        this.curValueMap.put(colIdx, value);
    }

    public void setCurRow(BIDataRow curRow) {
        this.curRow = curRow;
    }

    public DSFormulaContext clone() {
        DSFormulaContext newDsCxt = new DSFormulaContext(this.dataset, this.curRow);
        newDsCxt.setCalcMode(this.getCalcMode());
        for (Map.Entry<Integer, Object> entry : this.curValueMap.entrySet()) {
            newDsCxt.curValueMap.put(entry.getKey(), entry.getValue());
        }
        return newDsCxt;
    }
}

