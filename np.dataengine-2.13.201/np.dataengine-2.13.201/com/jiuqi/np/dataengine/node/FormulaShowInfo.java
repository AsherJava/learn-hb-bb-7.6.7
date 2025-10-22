/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTAdjustor
 */
package com.jiuqi.np.dataengine.node;

import com.jiuqi.bi.syntax.ast.IASTAdjustor;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.intf.ISheetNameProvider;
import com.jiuqi.np.dataengine.node.FormulaDataLinkPosition;
import java.util.ArrayList;
import java.util.List;

public class FormulaShowInfo {
    private DataEngineConsts.FormulaShowType formulaShowType;
    private boolean collectPositions = false;
    private List<FormulaDataLinkPosition> dataLinkPositions;
    private List<IASTAdjustor> adjustors;
    private ISheetNameProvider sheetNameProvider;
    private boolean isReportMode = true;

    public FormulaShowInfo(DataEngineConsts.FormulaShowType formulaShowType) {
        this.formulaShowType = formulaShowType;
    }

    public FormulaShowInfo(DataEngineConsts.FormulaShowType formulaShowType, boolean isReportMode) {
        this.formulaShowType = formulaShowType;
        this.isReportMode = isReportMode;
    }

    public boolean isCollectPositions() {
        return this.collectPositions;
    }

    public void setCollectPositions(boolean collectPositions) {
        this.collectPositions = collectPositions;
    }

    public DataEngineConsts.FormulaShowType getFormulaShowType() {
        return this.formulaShowType;
    }

    public List<FormulaDataLinkPosition> getDataLinkPositions() {
        if (this.dataLinkPositions == null && this.collectPositions) {
            this.dataLinkPositions = new ArrayList<FormulaDataLinkPosition>();
        }
        return this.dataLinkPositions;
    }

    public List<IASTAdjustor> getAdjustors() {
        return this.adjustors;
    }

    public void setAdjustors(List<IASTAdjustor> adjustors) {
        this.adjustors = adjustors;
    }

    public boolean isReportMode() {
        return this.isReportMode;
    }

    public void setReportMode(boolean isReportMode) {
        this.isReportMode = isReportMode;
    }

    public ISheetNameProvider getSheetNameProvider() {
        return this.sheetNameProvider;
    }

    public void setSheetNameProvider(ISheetNameProvider sheetNameProvider) {
        this.sheetNameProvider = sheetNameProvider;
    }
}

