/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.dafafill.formula;

import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.dafafill.formula.DataFillWorksheet;
import com.jiuqi.nr.dafafill.formula.DatafillformulaCellContext;
import com.jiuqi.nr.dafafill.model.DimensionZbKeyDataMap;
import com.jiuqi.nr.dafafill.model.QueryField;
import com.jiuqi.nr.dafafill.model.table.DataFillBaseCell;
import java.util.List;
import java.util.Map;

public class DatafillFormulaContext
implements IContext {
    private Map<String, QueryField> queryFieldMap;
    private Map<String, QueryField> fullQueryFieldMap;
    private DatafillformulaCellContext cellContext;
    private DimensionZbKeyDataMap dimensionZbKeyDataMap;
    private Map<DimensionValueSet, List<DataFillBaseCell>> basicDataMap;
    private List<QueryField> zbQueryFields;
    private Map<DimensionValueSet, Position> dimensionPositionMap;
    private DataFillWorksheet curWorksheet;
    private boolean formulaCheck;
    private List<QueryField> hbQueryFields;

    public Map<String, QueryField> getQueryFieldMap() {
        return this.queryFieldMap;
    }

    public void setQueryFieldMap(Map<String, QueryField> queryFieldMap) {
        this.queryFieldMap = queryFieldMap;
    }

    public DimensionZbKeyDataMap getDimensionZbKeyDataMap() {
        return this.dimensionZbKeyDataMap;
    }

    public void setDimensionZbKeyDataMap(DimensionZbKeyDataMap dimensionZbKeyDataMap) {
        this.dimensionZbKeyDataMap = dimensionZbKeyDataMap;
    }

    public Map<DimensionValueSet, Position> getDimensionPositionMap() {
        return this.dimensionPositionMap;
    }

    public void setDimensionPositionMap(Map<DimensionValueSet, Position> dimensionPositionMap) {
        this.dimensionPositionMap = dimensionPositionMap;
    }

    public DataFillWorksheet getCurWorksheet() {
        return this.curWorksheet;
    }

    public void setCurWorksheet(DataFillWorksheet curWorksheet) {
        this.curWorksheet = curWorksheet;
    }

    public DatafillformulaCellContext getCellContext() {
        return this.cellContext;
    }

    public void setCellContext(DatafillformulaCellContext cellContext) {
        this.cellContext = cellContext;
    }

    public boolean isFormulaCheck() {
        return this.formulaCheck;
    }

    public void setFormulaCheck(boolean formulaCheck) {
        this.formulaCheck = formulaCheck;
    }

    public Map<String, QueryField> getFullQueryFieldMap() {
        return this.fullQueryFieldMap;
    }

    public void setFullQueryFieldMap(Map<String, QueryField> fullQueryFieldMap) {
        this.fullQueryFieldMap = fullQueryFieldMap;
    }

    public Map<DimensionValueSet, List<DataFillBaseCell>> getBasicDataMap() {
        return this.basicDataMap;
    }

    public void setBasicDataMap(Map<DimensionValueSet, List<DataFillBaseCell>> basicDataMap) {
        this.basicDataMap = basicDataMap;
    }

    public List<QueryField> getZbQueryFields() {
        return this.zbQueryFields;
    }

    public void setZbQueryFields(List<QueryField> zbQueryFields) {
        this.zbQueryFields = zbQueryFields;
    }

    public List<QueryField> getHbQueryFields() {
        return this.hbQueryFields;
    }

    public void setHbQueryFields(List<QueryField> hbQueryFields) {
        this.hbQueryFields = hbQueryFields;
    }
}

