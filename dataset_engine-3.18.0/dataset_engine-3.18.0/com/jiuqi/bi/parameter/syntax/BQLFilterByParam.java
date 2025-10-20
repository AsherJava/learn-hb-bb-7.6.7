/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.interpret.ISQLInfo
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.parameter.syntax;

import com.jiuqi.bi.parameter.model.ParameterModel;
import com.jiuqi.bi.parameter.model.SmartSelector;
import com.jiuqi.bi.parameter.syntax.ParamFilterTranslator;
import com.jiuqi.bi.parameter.syntax.ParamNode;
import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.interpret.ISQLInfo;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.List;

public final class BQLFilterByParam
extends Function {
    private static final long serialVersionUID = 1L;

    public BQLFilterByParam() {
        this.parameters().add(new Parameter("valueField", 0, "\u88ab\u8fc7\u6ee4\u7684\u5b57\u6bb5\u6216\u503c"));
        this.parameters().add(new Parameter("param", 0, "\u9650\u5b9a\u7684\u53c2\u6570"));
    }

    public String name() {
        return "FilterByParam";
    }

    public String title() {
        return "\u6839\u636e\u53c2\u6570\u8fc7\u6ee4\u6570\u636e";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 1;
    }

    public String category() {
        return "\u8fc7\u6ee4\u51fd\u6570";
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        if (!(parameters.get(1) instanceof ParamNode)) {
            throw new SyntaxException(parameters.get(1).getToken(), "\u4f20\u5165\u53c2\u6570\u5fc5\u987b\u4e3a\u53c2\u6570\u7c7b\u578b\u5bf9\u8c61\u3002");
        }
        return super.validate(context, parameters);
    }

    public boolean support(Language lang) {
        return super.support(lang) || lang == Language.SQL;
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        Object value = parameters.get(0).evaluate(context);
        if (value == null) {
            return false;
        }
        ParamNode paramNode = (ParamNode)parameters.get(1);
        Object paramValue = paramNode.getParamValue(context);
        if (paramValue == null) {
            return false;
        }
        if (paramValue instanceof SmartSelector) {
            return this.evalSmart(value, (SmartSelector)paramValue, paramNode.getParam());
        }
        if (paramValue instanceof ArrayData) {
            return this.evalArray(value, (ArrayData)paramValue, paramNode.getParam());
        }
        return this.evalSimple(value, paramValue, paramNode.getParam());
    }

    private boolean evalSmart(Object value, SmartSelector paramValue, ParameterModel param) {
        return paramValue.isAvailable(value);
    }

    private boolean evalArray(Object value, ArrayData paramValue, ParameterModel param) throws SyntaxException {
        if (param.isRangeParameter()) {
            Object min = paramValue.get(0);
            Object max = paramValue.get(1);
            if (min != null && DataType.compare((int)param.getDataType().value(), (Object)value, (Object)min) < 0) {
                return false;
            }
            return max == null || DataType.compare((int)param.getDataType().value(), (Object)value, (Object)max) <= 0;
        }
        for (Object item : paramValue) {
            if (DataType.compare((int)param.getDataType().value(), (Object)value, item) != 0) continue;
            return true;
        }
        return false;
    }

    private boolean evalSimple(Object value, Object paramValue, ParameterModel param) throws SyntaxException {
        return DataType.compare((int)param.getDataType().value(), (Object)value, (Object)paramValue) == 0;
    }

    protected void toSQL(IContext context, List<IASTNode> parameters, StringBuilder buffer, ISQLInfo info) throws InterpretException {
        IASTNode valueNode = parameters.get(0);
        ParamNode paramNode = (ParamNode)parameters.get(1);
        BQLFilterByParam.toSQL(context, buffer, valueNode, paramNode, info);
    }

    public static void toSQL(IContext context, StringBuilder buffer, IASTNode valueNode, ParamNode paramNode, ISQLInfo info) throws InterpretException {
        ParamFilterTranslator translator = new ParamFilterTranslator(){

            @Override
            public String toValueFieldSQL(IContext context, IASTNode valueNode, ISQLInfo info) throws InterpretException {
                StringBuilder buffer = new StringBuilder();
                if (valueNode.childrenSize() > 0) {
                    buffer.append('(');
                }
                valueNode.interpret(context, buffer, Language.SQL, (Object)info);
                if (valueNode.childrenSize() > 0) {
                    buffer.append(')');
                }
                return buffer.toString();
            }
        };
        translator.toSQL(context, buffer, valueNode, paramNode, info);
    }
}

