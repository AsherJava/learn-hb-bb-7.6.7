/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.ast.ASTNodeType
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.FunctionProvider
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.IParameter
 *  com.jiuqi.bi.syntax.function.ParamOption
 *  com.jiuqi.bi.syntax.operator.OperatorNode
 */
package com.jiuqi.va.formula.common.utils;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.ast.ASTNodeType;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.FunctionProvider;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.IParameter;
import com.jiuqi.bi.syntax.function.ParamOption;
import com.jiuqi.bi.syntax.operator.OperatorNode;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.FormulasVO;
import com.jiuqi.va.formula.domain.ParameterDescription;
import com.jiuqi.va.formula.intf.ModelFunction;
import com.jiuqi.va.formula.provider.ModelFunctionProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.util.CollectionUtils;

public class FunctionUtils {
    public static final String LINE_SEPARATOR = System.getProperty("line.separator", "\r\n");

    public static boolean containPts(OperatorNode operatorNode, IASTNode node) {
        OperatorNode childNode;
        boolean pts = false;
        if (node.getNodeType().equals((Object)ASTNodeType.OPERATOR) && (childNode = (OperatorNode)node).level() > operatorNode.level()) {
            pts = true;
        }
        return pts;
    }

    public static final Map<String, List<FormulasVO>> gatherFormulas() {
        HashMap<String, List<FormulasVO>> formulaMap = new HashMap<String, List<FormulasVO>>();
        for (Map.Entry<String, FunctionProvider> functionProviderMap : ModelFunctionProvider.functionProviderMap.entrySet()) {
            ArrayList formulasVOList = new ArrayList();
            functionProviderMap.getValue().forEach(function -> {
                FormulasVO formulasVO;
                StringBuilder functionName = new StringBuilder(64);
                functionName.append(function.name()).append('(');
                FunctionUtils.printParamDeclaration(functionName, function.parameters(), function.isInfiniteParameter());
                functionName.append(")");
                if (function instanceof ModelFunction) {
                    FormulaDescription formulaDescription = ((ModelFunction)((Object)function)).toFormulaDescription();
                    if (formulaDescription == null) {
                        formulasVO = new FormulasVO(functionName.toString(), function.title(), function.toDescription());
                    } else {
                        formulasVO = new FormulasVO(functionName.toString(), function.title(), null);
                        formulasVO.setFormulaDescription(formulaDescription);
                    }
                    formulasVO.setParamGuide(((ModelFunction)((Object)function)).buildParamGuide());
                    formulasVO.setInfiniteParameter(function.isInfiniteParameter());
                    formulasVO.setFunctionName(function.name());
                } else {
                    formulasVO = new FormulasVO(functionName.toString(), function.title(), function.toDescription());
                    FormulaDescription formulaDescription = FunctionUtils.handleFormula(function);
                    formulasVO.setFormulaDescription(formulaDescription);
                }
                formulasVO.setFormulaText(FunctionUtils.executeFormulaText(function));
                formulasVO.setFunctionName(function.name());
                formulasVOList.add(formulasVO);
            });
            formulaMap.put(functionProviderMap.getKey(), formulasVOList);
        }
        return formulaMap;
    }

    private static String executeFormulaText(IFunction function) {
        StringBuilder sb = new StringBuilder();
        sb.append(function.name());
        sb.append("(");
        for (IParameter parameter : function.parameters()) {
            switch (parameter.dataType()) {
                case 0: 
                case 2: 
                case 11: {
                    sb.append("null");
                    break;
                }
                case 1: {
                    sb.append(true);
                    break;
                }
                case 3: 
                case 10: {
                    sb.append("0");
                    break;
                }
                default: {
                    sb.append("'").append("'");
                }
            }
            sb.append(",");
        }
        if (!CollectionUtils.isEmpty(function.parameters())) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append(")");
        return sb.toString();
    }

    public static FormulaDescription handleFormula(IFunction function) {
        FormulaDescription formulaDescription = new FormulaDescription();
        formulaDescription.setName(function.name());
        formulaDescription.setTitle(function.title());
        formulaDescription.setAliases(function.aliases());
        ArrayList<ParameterDescription> parameterDescriptions = new ArrayList<ParameterDescription>();
        formulaDescription.setParameters(parameterDescriptions);
        for (IParameter parameter : function.parameters()) {
            ParameterDescription parameterDescription = new ParameterDescription();
            parameterDescription.setName(parameter.name());
            parameterDescription.setType(DataType.toString((int)parameter.dataType()));
            parameterDescription.setRequired(!parameter.isOmitable());
            String title = parameter.title();
            if (!CollectionUtils.isEmpty(parameter.getOptions())) {
                StringBuilder sb = new StringBuilder(title);
                sb.append("\uff0c\u53ef\u9009\u503c\uff1a");
                for (ParamOption option : parameter.getOptions()) {
                    sb.append(option.toString());
                    sb.append("\uff1b");
                }
                sb.replace(sb.length() - 1, sb.length(), "\u3002");
                parameterDescription.setDescription(sb.toString());
            } else {
                parameterDescription.setDescription(title);
            }
            parameterDescriptions.add(parameterDescription);
        }
        return formulaDescription;
    }

    public static final Set<String> getFilterCategories() {
        return ModelFunctionProvider.isPrivateCategory;
    }

    private static final void printParamDeclaration(StringBuilder buffer, List<IParameter> parameters, boolean isInfiniteParameter) {
        boolean ommitable = false;
        boolean flag = false;
        for (IParameter p : parameters) {
            if (p.isOmitable() && !ommitable) {
                ommitable = true;
                buffer.append('[');
            }
            if (flag) {
                buffer.append(", ");
            } else {
                flag = true;
            }
            buffer.append(p.name());
        }
        if (isInfiniteParameter && !parameters.isEmpty()) {
            buffer.append(", ...");
        }
        if (ommitable) {
            buffer.append(']');
        }
    }
}

