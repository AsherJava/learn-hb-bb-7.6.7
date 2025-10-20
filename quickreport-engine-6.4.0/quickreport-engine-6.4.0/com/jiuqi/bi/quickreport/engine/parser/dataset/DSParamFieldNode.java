/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 */
package com.jiuqi.bi.quickreport.engine.parser.dataset;

import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DSFieldNode;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DSFormulaInfo;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;

public final class DSParamFieldNode
extends DSFieldNode {
    private static final long serialVersionUID = 1L;
    private ParameterModel param;

    public DSParamFieldNode(Token token, DSModel dataset, DSField field, ParameterModel param) {
        super(token, dataset, field, true);
        this.param = param;
    }

    public ParameterModel getParam() {
        return this.param;
    }

    @Override
    protected void toFormula(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        if (info instanceof DSFormulaInfo) {
            super.toFormula(context, buffer, info);
            return;
        }
        if (!this.getRestrictions().isEmpty()) {
            buffer.append('[');
        }
        buffer.append(this.param.getName());
        if (!this.getRestrictions().isEmpty()) {
            for (IASTNode restriction : this.getRestrictions()) {
                buffer.append(", ");
                restriction.interpret(context, buffer, Language.FORMULA, info);
            }
            buffer.append(']');
        }
    }

    @Override
    protected void toExplain(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        if (StringUtils.isEmpty((String)this.param.getTitle())) {
            buffer.append(this.param.getName());
        } else {
            buffer.append(this.param.getTitle());
        }
    }

    @Override
    public void toString(StringBuilder buffer) {
        if (!this.getRestrictions().isEmpty()) {
            buffer.append('[');
        }
        buffer.append(this.param.getName());
        if (!this.getRestrictions().isEmpty()) {
            for (IASTNode restriction : this.getRestrictions()) {
                buffer.append(", ");
                restriction.toString(buffer);
            }
            buffer.append(']');
        }
    }
}

