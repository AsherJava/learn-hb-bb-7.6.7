/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.operator.And
 *  com.jiuqi.bi.syntax.operator.Equal
 *  com.jiuqi.bi.syntax.operator.LessThan
 *  com.jiuqi.bi.syntax.operator.LessThanOrEqual
 *  com.jiuqi.bi.syntax.operator.MoreThan
 *  com.jiuqi.bi.syntax.operator.MoreThanOrEqual
 *  com.jiuqi.bi.syntax.operator.NotEqual
 *  com.jiuqi.bi.syntax.operator.Or
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 */
package com.jiuqi.nr.zb.scheme.service.impl;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.operator.And;
import com.jiuqi.bi.syntax.operator.Equal;
import com.jiuqi.bi.syntax.operator.LessThan;
import com.jiuqi.bi.syntax.operator.LessThanOrEqual;
import com.jiuqi.bi.syntax.operator.MoreThan;
import com.jiuqi.bi.syntax.operator.MoreThanOrEqual;
import com.jiuqi.bi.syntax.operator.NotEqual;
import com.jiuqi.bi.syntax.operator.Or;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.nr.zb.scheme.common.CompareType;
import com.jiuqi.nr.zb.scheme.core.ValidationRule;
import com.jiuqi.nr.zb.scheme.core.ZbInfo;
import com.jiuqi.nr.zb.scheme.dto.FieldContext;
import com.jiuqi.nr.zb.scheme.dto.FieldNode;
import com.jiuqi.nr.zb.scheme.internal.dto.ValidationRuleDTO;
import com.jiuqi.nr.zb.scheme.service.VerificationParser;
import com.jiuqi.nr.zb.scheme.service.impl.FieldDynamicNodeProvider;
import java.util.List;
import org.springframework.util.Assert;

public class DefaultVerificationParser
implements VerificationParser {
    private static final String EXPRESSION = "//";

    @Override
    public ValidationRule parse(String verification, ZbInfo dataField) throws SyntaxException {
        ValidationRuleDTO dto;
        Assert.notNull((Object)verification, "verification must not be null");
        if (verification.startsWith(EXPRESSION)) {
            return null;
        }
        ReportFormulaParser parser = ReportFormulaParser.getInstance();
        parser.registerDynamicNodeProvider((IReportDynamicNodeProvider)new FieldDynamicNodeProvider());
        FieldContext context = new FieldContext();
        context.setDataField(dataField);
        IExpression expression = parser.parseCond(verification, (IContext)context);
        if (expression == null) {
            return null;
        }
        IASTNode root = expression.getChild(0);
        if ((root instanceof And || root instanceof Or) && (dto = this.betweenParse(root)) != null) {
            dto.setVerification(verification);
        }
        if (root instanceof FunctionNode) {
            ValidationRuleDTO phoneParse = this.phoneParse(root);
            if (phoneParse != null) {
                phoneParse.setVerification(verification);
                return phoneParse;
            }
        } else {
            ValidationRuleDTO otherParse;
            IASTNode leftNode = root.getChild(0);
            if (leftNode instanceof FieldNode) {
                FieldNode dataNode = (FieldNode)leftNode;
                IASTNode rightNode = root.getChild(1);
                String value = rightNode.evaluate(null).toString();
                CompareType compareType = null;
                if (root instanceof Equal) {
                    compareType = CompareType.EQUAL;
                } else if (root instanceof NotEqual) {
                    compareType = CompareType.NOT_EQUAL;
                } else if (root instanceof LessThan) {
                    compareType = CompareType.LESS_THAN;
                } else if (root instanceof MoreThan) {
                    compareType = CompareType.MORE_THAN;
                } else if (root instanceof LessThanOrEqual) {
                    compareType = CompareType.LESS_THAN_OR_EQUAL;
                } else if (root instanceof MoreThanOrEqual) {
                    compareType = CompareType.MORE_THAN_OR_EQUAL;
                } else {
                    return null;
                }
                if (value != null) {
                    ValidationRuleDTO dto2 = new ValidationRuleDTO();
                    dto2.setCompareType(compareType);
                    dto2.setValue(value);
                    dto2.setVerification(verification);
                    return dto2;
                }
            } else if (leftNode instanceof FunctionNode && (otherParse = this.functionParse(root)) != null) {
                otherParse.setVerification(verification);
                return otherParse;
            }
        }
        return null;
    }

    private ValidationRuleDTO functionParse(IASTNode root) throws SyntaxException {
        if (!(root.getChild(0) instanceof FunctionNode)) {
            return null;
        }
        FunctionNode functionNode = (FunctionNode)root.getChild(0);
        if (CompareType.NOTNULL.getSign().toLowerCase().contains(functionNode.getDefine().name().toLowerCase())) {
            ValidationRuleDTO dto = new ValidationRuleDTO();
            dto.setCompareType(CompareType.NOTNULL);
            return dto;
        }
        if (CompareType.MAXLEN.getSign().toLowerCase().contains(functionNode.getDefine().name().toLowerCase())) {
            String text;
            Object evaluate = root.getChild(1).evaluate(null);
            if (evaluate instanceof Double) {
                Double aValue = (Double)evaluate;
                text = String.valueOf(aValue.intValue());
            } else {
                text = evaluate.toString();
            }
            ValidationRuleDTO dto = new ValidationRuleDTO();
            dto.setCompareType(CompareType.MAXLEN);
            dto.setValue(text);
            return dto;
        }
        if (functionNode.childrenSize() == 2) {
            CompareType compareType = root instanceof MoreThanOrEqual || root instanceof MoreThan ? CompareType.CONTAINS : CompareType.NOT_CONTAINS;
            String text = functionNode.getChild(0).evaluate(null).toString();
            ValidationRuleDTO dto = new ValidationRuleDTO();
            dto.setCompareType(compareType);
            dto.setValue(text);
            return dto;
        }
        return null;
    }

    private ValidationRuleDTO inParse(String dataField, ArrayData value) {
        List valueList = value.toList();
        StringBuilder message = new StringBuilder();
        ValidationRuleDTO dto = new ValidationRuleDTO();
        message.setLength(message.length() - 1);
        dto.setMessage(message.append("]").toString());
        return dto;
    }

    private ValidationRuleDTO phoneParse(IASTNode root) {
        FunctionNode functionNode = (FunctionNode)root;
        if (CompareType.MOBILEPHONE.getSign().toLowerCase().contains(functionNode.getDefine().name().toLowerCase())) {
            ValidationRuleDTO verificationDTO = new ValidationRuleDTO();
            verificationDTO.setCompareType(CompareType.MOBILEPHONE);
            return verificationDTO;
        }
        return null;
    }

    private ValidationRuleDTO betweenParse(IASTNode root) throws SyntaxException {
        IASTNode minNode = root.getChild(0);
        IASTNode maxNode = root.getChild(1);
        String min = minNode.getChild(1).evaluate(null).toString();
        String max = maxNode.getChild(1).evaluate(null).toString();
        CompareType compare = minNode instanceof MoreThanOrEqual ? CompareType.BETWEEN : CompareType.NOT_BETWEEN;
        ValidationRuleDTO validationRuleDTO = new ValidationRuleDTO();
        validationRuleDTO.setCompareType(compare);
        validationRuleDTO.setLeftValue(min);
        validationRuleDTO.setRightValue(max);
        return validationRuleDTO;
    }
}

