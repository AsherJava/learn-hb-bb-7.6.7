/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.IParameter
 *  com.jiuqi.bi.syntax.interpret.ISQLInfo
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.va.formula.intf;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.IParameter;
import com.jiuqi.bi.syntax.interpret.ISQLInfo;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.formula.common.exception.ToFilterException;
import com.jiuqi.va.formula.common.exception.ToJavaScriptException;
import com.jiuqi.va.formula.common.exception.ToSqlException;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.intf.ToFilter;
import com.jiuqi.va.formula.intf.ToJavaScript;
import com.jiuqi.va.formula.provider.FilterNodeProvider;
import com.jiuqi.va.formula.provider.JavaScriptNodeProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.util.StringUtils;

public abstract class ModelFunction
extends Function {
    private static final long serialVersionUID = 7886214294306850785L;

    public String toDescription() {
        StringBuffer functionDescribe = new StringBuffer(super.toDescription());
        if (!StringUtils.isEmpty(this.addDescribe())) {
            functionDescribe.append("\u63cf\u8ff0\uff1a").append(FunctionUtils.LINE_SEPARATOR);
            functionDescribe.append("    ").append(this.addDescribe()).append(FunctionUtils.LINE_SEPARATOR);
        }
        return functionDescribe.toString();
    }

    public FormulaDescription toFormulaDescription() {
        return null;
    }

    public abstract String addDescribe();

    public void toJavaScript(StringBuilder builder, List<IASTNode> parameters) throws ToJavaScriptException {
        ModelFunction.toJavaScript(builder, parameters.stream(), this.name());
    }

    public static final void toJavaScript(StringBuilder builder, Stream<IASTNode> parameters, String functionName) throws ToJavaScriptException {
        StringBuilder paramBuilder = new StringBuilder();
        List iastNodes = parameters.collect(Collectors.toList());
        for (IASTNode iastNode : iastNodes) {
            ToJavaScript javaScript = null;
            try {
                javaScript = JavaScriptNodeProvider.get(iastNode.getNodeType());
            }
            catch (ToJavaScriptException e) {
                throw new ToJavaScriptException(String.format("\u4e0d\u652f\u6301\u5f53\u524d\u8bed\u6cd5\u3010%s\u3011", iastNode.toString()), e);
            }
            javaScript.toJavaScript(iastNode, paramBuilder, false);
            paramBuilder.append(", ");
        }
        String paramStr = "";
        if (paramBuilder.length() > 0) {
            paramStr = paramBuilder.substring(0, paramBuilder.length() - 2);
        }
        builder.append(String.format("this.%s(%s)", functionName, paramStr));
    }

    public void toFilter(IContext context, List<IASTNode> params, StringBuilder buffer, String functionName, Object info) throws ToFilterException {
        ModelFunction.baseFunctionToFilter(context, params, buffer, functionName, info);
    }

    public static void baseFunctionToFilter(IContext context, List<IASTNode> params, StringBuilder builder, String functionName, Object info) throws ToFilterException {
        StringBuilder paramBuilder = new StringBuilder();
        params.forEach(iastNode -> {
            ToFilter toFilter = null;
            try {
                toFilter = FilterNodeProvider.get(iastNode.getNodeType());
                toFilter.toFilter(context, (IASTNode)iastNode, paramBuilder, info);
                paramBuilder.append(", ");
            }
            catch (ToFilterException e) {
                throw new RuntimeException(String.format("\u4e0d\u652f\u6301\u5f53\u524d\u8bed\u6cd5\u3010%s\u3011", iastNode.toString()), e);
            }
        });
        String paramStr = "";
        if (paramBuilder.length() > 0) {
            paramStr = paramBuilder.substring(0, paramBuilder.length() - 2);
        }
        builder.append(String.format("%s(%s)", functionName, paramStr));
    }

    protected void printParamDeclaration(StringBuilder buffer) {
        boolean flag = false;
        for (IParameter p : this.parameters()) {
            if (flag) {
                buffer.append(", ");
            } else {
                flag = true;
            }
            buffer.append(DataType.toExpression((int)p.dataType())).append(' ').append(p.name());
        }
        if (this.isInfiniteParameter() && !this.parameters().isEmpty()) {
            buffer.append(", ...");
        }
    }

    public void toSQL(IContext context, List<IASTNode> params, StringBuilder buffer, String functionName, Object info) throws ToSqlException {
        throw new UnsupportedOperationException();
    }

    protected void toSQL(IContext context, List<IASTNode> parameters, StringBuilder buffer, ISQLInfo info) throws InterpretException {
        if (parameters.size() == 0) {
            try {
                buffer.append("'");
                buffer.append(this.evalute(context, parameters));
                buffer.append("'");
            }
            catch (SyntaxException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public boolean hasParamGuide() {
        return false;
    }

    public List<Map<String, Object>> buildParamGuide() {
        if (!this.hasParamGuide()) {
            return null;
        }
        ArrayList<Map<String, Object>> paramGuides = new ArrayList<Map<String, Object>>();
        this.parameters().forEach(param -> paramGuides.add(new HashMap<String, Object>(){
            private static final long serialVersionUID = 1L;
            {
                this.put("paramName", param.name());
                this.put("paramValue", "");
                this.put("valueRange", Collections.emptyList());
                this.put("description", param.title());
            }
        }));
        return paramGuides;
    }

    public boolean enableDebug() {
        return false;
    }

    public boolean autoOptimize() {
        return false;
    }
}

