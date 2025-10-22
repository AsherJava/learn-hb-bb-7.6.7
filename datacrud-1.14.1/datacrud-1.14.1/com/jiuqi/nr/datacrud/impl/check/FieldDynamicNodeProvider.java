/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 */
package com.jiuqi.nr.datacrud.impl.check;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import com.jiuqi.nr.datacrud.impl.check.DataFieldNode;
import com.jiuqi.nr.datacrud.impl.check.FieldValidationContext;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import java.util.List;

public class FieldDynamicNodeProvider
implements IReportDynamicNodeProvider {
    public IASTNode find(IContext context, Token token, String refName) {
        return null;
    }

    public IASTNode find(IContext context, Token token, List<String> objPath) {
        FieldValidationContext validationContext = (FieldValidationContext)context;
        DataField field = validationContext.getField();
        Object data = validationContext.getData();
        String kpiName = null;
        if (objPath.size() == 1) {
            kpiName = objPath.get(0);
        } else if (objPath.size() == 2) {
            kpiName = objPath.get(1);
        }
        if (!field.getCode().equals(kpiName)) {
            return null;
        }
        DataFieldType dataFieldType = field.getDataFieldType();
        ColumnModelType columnModelType = dataFieldType.toColumnModelType();
        if (columnModelType == ColumnModelType.INTEGER || columnModelType == ColumnModelType.DOUBLE) {
            columnModelType = ColumnModelType.BIGDECIMAL;
        }
        Integer decimal = field.getDecimal();
        int scale = 0;
        if (decimal != null) {
            scale = decimal;
        }
        return new DataFieldNode(token, columnModelType.getValue(), scale, data);
    }

    public IASTNode findSpec(IContext context, Token token, String refName, String spec) {
        return null;
    }

    public IASTNode findRestrict(IContext context, Token token, List<String> objPath, List<IASTNode> restrictItems) {
        return null;
    }

    public IASTNode findSpecial(IContext context, Token token, String refName) {
        return null;
    }
}

