/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.dataset.model.field.FieldType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.FunctionAdapter
 *  com.jiuqi.bi.syntax.function.FunctionProvider
 *  com.jiuqi.bi.syntax.function.IMultiInstance
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.parser.function;

import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DSFieldNode;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.FunctionAdapter;
import com.jiuqi.bi.syntax.function.FunctionProvider;
import com.jiuqi.bi.syntax.function.IMultiInstance;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.ArrayList;
import java.util.List;

public class NewSerialAdapter
extends FunctionAdapter
implements IMultiInstance {
    public NewSerialAdapter() {
        super(FunctionProvider.REFERENCE_PROVIDER, "NewSerial");
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return super.evalute(context, this.convertParams(parameters));
    }

    private List<IASTNode> convertParams(List<IASTNode> parameters) {
        ArrayList<IASTNode> newParams = new ArrayList<IASTNode>(parameters.size());
        for (IASTNode param : parameters) {
            if (param instanceof DSFieldNode) {
                IASTNode newParam = this.convertParam((DSFieldNode)param);
                newParams.add(newParam);
                continue;
            }
            newParams.add(param);
        }
        return newParams;
    }

    private IASTNode convertParam(DSFieldNode param) {
        DSField field = param.getField();
        if (field.getFieldType() != FieldType.GENERAL_DIM && field.getFieldType() != FieldType.TIME_DIM) {
            return param;
        }
        if (field.getName().equalsIgnoreCase(field.getKeyField())) {
            return param;
        }
        DSField keyField = param.getDataSet().findField(field.getKeyField());
        if (keyField == null) {
            return param;
        }
        return new DSFieldNode(null, param.getDataSet(), keyField, true);
    }
}

