/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.ASTNode
 *  com.jiuqi.bi.syntax.ast.ASTNodeType
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.data.DataNode
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.parameter.syntax;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.parameter.ParameterException;
import com.jiuqi.bi.parameter.engine.IParameterEnv;
import com.jiuqi.bi.parameter.model.ParameterModel;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.ASTNode;
import com.jiuqi.bi.syntax.ast.ASTNodeType;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.data.DataNode;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;

public final class ParamInfoNode
extends ASTNode {
    private static final long serialVersionUID = 7497921127171680814L;
    private final IParameterEnv paramEnv;
    private final ParameterModel paramModel;
    private String suffix;
    public static final String SUFFIX_CODE = "CODE";
    public static final String SUFFIX_TITLE = "TITLE";

    public ParamInfoNode(Token token, IParameterEnv paramEnv, ParameterModel paramModel) {
        super(token);
        this.paramEnv = paramEnv;
        this.paramModel = paramModel;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public ASTNodeType getNodeType() {
        return ASTNodeType.DATA;
    }

    public int getType(IContext context) throws SyntaxException {
        return 6;
    }

    public Object evaluate(IContext context) throws SyntaxException {
        MemoryDataSet value;
        try {
            value = (MemoryDataSet)this.paramEnv.getValue(this.paramModel.getName());
        }
        catch (ParameterException e) {
            throw new SyntaxException((Throwable)e);
        }
        if (value == null || value.size() == 0) {
            return null;
        }
        DataRow row = value.get(0);
        if (SUFFIX_CODE.equalsIgnoreCase(this.suffix)) {
            return row.getValue(0);
        }
        return row.getValue(1);
    }

    public boolean isStatic(IContext context) {
        return true;
    }

    public void toString(StringBuilder buffer) {
        buffer.append(this.paramModel.getName()).append('.').append(this.suffix);
    }

    public boolean support(Language lang) {
        return true;
    }

    public void interpret(IContext context, StringBuilder buffer, Language lang, Object info) throws InterpretException {
        Object dataValue;
        int dataType;
        try {
            dataType = this.getType(context);
            dataValue = this.evaluate(context);
        }
        catch (SyntaxException e) {
            throw new InterpretException((Throwable)e);
        }
        DataNode node = new DataNode(null, dataType, dataValue);
        node.interpret(context, buffer, lang, info);
    }
}

