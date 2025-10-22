/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.operator.Equal
 *  com.jiuqi.bi.syntax.operator.IfThenElse
 */
package com.jiuqi.np.dataengine.util;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.operator.Equal;
import com.jiuqi.bi.syntax.operator.IfThenElse;
import com.jiuqi.np.dataengine.common.DataModelLinkColumn;
import com.jiuqi.np.dataengine.node.CellDataNode;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.IParsedExpression;

public class ExpressionKeyUtil {
    public static String generateKey(IParsedExpression expression) {
        boolean iscolWildcard;
        StringBuilder buf = new StringBuilder();
        buf.append(expression.getSource().getId()).append("_").append(expression.getFormulaType().getValue());
        IExpression realExpression = expression.getRealExpression();
        boolean isRowWildcard = realExpression.getWildcardRow() >= 0;
        boolean bl = iscolWildcard = realExpression.getWildcardCol() >= 0;
        if (isRowWildcard || iscolWildcard) {
            boolean matched = false;
            for (IASTNode child : realExpression) {
                DataModelLinkColumn dataLink;
                CellDataNode cellDataNode;
                if (!(child instanceof CellDataNode) || (cellDataNode = (CellDataNode)child).isFromRowWildcard() != isRowWildcard || cellDataNode.isFromColWildcard() != iscolWildcard || (dataLink = cellDataNode.getDataModelLinkColumn()) == null) continue;
                matched = true;
                buf.append("_").append(dataLink.getDataLinkCode());
                break;
            }
            if (!matched) {
                if (expression.getAssignNode() != null) {
                    buf.append("_").append(expression.getAssignNode().getDataModelLink().getDataLinkCode());
                } else {
                    DynamicDataNode posNode = null;
                    for (IASTNode child : realExpression) {
                        if (!(child instanceof Equal)) continue;
                        IASTNode leftNode = child.getChild(0);
                        for (IASTNode leftChild : leftNode) {
                            if (!(leftChild instanceof DynamicDataNode)) continue;
                            posNode = (DynamicDataNode)leftChild;
                        }
                    }
                    if (posNode == null) {
                        posNode = ExpressionKeyUtil.findDataNode((IASTNode)realExpression);
                    }
                    if (posNode != null) {
                        buf.append("_").append(posNode.getDataLink().getDataLinkCode());
                    }
                }
            }
        }
        return buf.toString();
    }

    private static DynamicDataNode findDataNode(IASTNode node) {
        for (IASTNode child : node) {
            DynamicDataNode posNode;
            if (child instanceof IfThenElse) {
                return ExpressionKeyUtil.findDataNode(child.getChild(1));
            }
            if (!(child instanceof DynamicDataNode) || (posNode = (DynamicDataNode)child).getDataLink() == null) continue;
            return posNode;
        }
        return null;
    }

    public static String getFormulaKeyFromExpressionKey(String expressionKey) {
        int index = expressionKey.indexOf("_");
        return expressionKey.substring(0, index);
    }
}

