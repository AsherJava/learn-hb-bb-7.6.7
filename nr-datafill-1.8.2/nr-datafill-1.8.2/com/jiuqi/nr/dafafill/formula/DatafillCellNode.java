/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.cell.CellNode
 *  com.jiuqi.bi.syntax.cell.IWorksheet
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 */
package com.jiuqi.nr.dafafill.formula;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.cell.CellNode;
import com.jiuqi.bi.syntax.cell.IWorksheet;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.dafafill.formula.DatafillFormulaContext;
import com.jiuqi.nr.dafafill.model.DimensionZbKeyDataMap;
import com.jiuqi.nr.dafafill.model.QueryField;
import com.jiuqi.nr.dafafill.model.table.DataFillBaseCell;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class DatafillCellNode
extends CellNode {
    private static final long serialVersionUID = 1L;
    private DimensionValueSet dimensionValueSet;
    private QueryField queryField;

    public DatafillCellNode(Token token, IWorksheet workSheet, Position position, DimensionValueSet dimensionValueSet, QueryField queryField) {
        super(token, workSheet, position);
        this.dimensionValueSet = dimensionValueSet;
        this.queryField = queryField;
    }

    public Object evaluate(IContext var1) throws SyntaxException {
        DataFillBaseCell cellObject;
        DatafillFormulaContext context = (DatafillFormulaContext)var1;
        boolean formulaCheck = context.isFormulaCheck();
        if (formulaCheck) {
            return null;
        }
        DimensionZbKeyDataMap dimensionZbKeyDataMap = context.getDimensionZbKeyDataMap();
        if (null != dimensionZbKeyDataMap && null != (cellObject = dimensionZbKeyDataMap.getCell(this.dimensionValueSet))) {
            Serializable value = cellObject.getValue();
            if (value instanceof Integer) {
                return new BigDecimal((Integer)value);
            }
            return value;
        }
        Map<DimensionValueSet, List<DataFillBaseCell>> basicDataMap = context.getBasicDataMap();
        List<QueryField> zbQueryFields = context.getZbQueryFields();
        DimensionValueSet temp = new DimensionValueSet(this.dimensionValueSet);
        String zbId = (String)temp.getValue("ZB");
        temp.clearValue("ZB");
        int index = -1;
        for (int i = 0; i < zbQueryFields.size(); ++i) {
            QueryField zbQueryField = zbQueryFields.get(i);
            if (!zbQueryField.getId().equals(zbId)) continue;
            index = i;
            break;
        }
        if (index > -1) {
            DataFillBaseCell cellObject2;
            List<QueryField> hbQueryFields;
            List<DataFillBaseCell> list = basicDataMap.get(temp);
            if (list == null && null != (hbQueryFields = context.getHbQueryFields()) && !hbQueryFields.isEmpty()) {
                for (QueryField tQueryField : hbQueryFields) {
                    temp.clearValue(tQueryField.getCode());
                }
                list = basicDataMap.get(temp);
            }
            if (null != list && null != (cellObject2 = list.get(index))) {
                Serializable value = cellObject2.getValue();
                if (value instanceof Integer) {
                    return new BigDecimal((Integer)value);
                }
                return value;
            }
        }
        return null;
    }

    public int getType(IContext var1) throws SyntaxException {
        if (DataFieldType.INTEGER == this.queryField.getDataType()) {
            return DataFieldType.BIGDECIMAL.getValue();
        }
        return this.queryField.getDataType().getValue();
    }
}

