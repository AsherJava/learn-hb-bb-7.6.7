/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.ASTNodeType
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.operator.Equal
 *  com.jiuqi.va.formula.common.exception.ToJavaScriptException
 *  com.jiuqi.va.formula.intf.ToJavaScript
 *  com.jiuqi.va.formula.provider.JavaScriptNodeProvider
 */
package com.jiuqi.va.biz.ruler.impl;

import com.jiuqi.bi.syntax.ast.ASTNodeType;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.operator.Equal;
import com.jiuqi.va.biz.ruler.ModelNode;
import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import com.jiuqi.va.formula.common.exception.ToJavaScriptException;
import com.jiuqi.va.formula.intf.ToJavaScript;
import com.jiuqi.va.formula.provider.JavaScriptNodeProvider;

public class JavaScriptHandle {
    public static final String toJavaScript(IASTNode node, FormulaType formulaType) throws ToJavaScriptException {
        StringBuilder builder = new StringBuilder(64);
        try {
            IASTNode formulaNode = node.getChild(0);
            ToJavaScript javaScript = null;
            if (formulaType == FormulaType.EXECUTE && formulaNode instanceof Equal && formulaNode.getChild(0) instanceof ModelNode) {
                IASTNode leftNode = formulaNode.getChild(0);
                javaScript = JavaScriptNodeProvider.get((ASTNodeType)leftNode.getNodeType());
                javaScript.toJavaScript(leftNode, builder, false);
                builder.append(" = ");
                IASTNode rightNode = formulaNode.getChild(1);
                javaScript = JavaScriptNodeProvider.get((ASTNodeType)rightNode.getNodeType());
                javaScript.toJavaScript(rightNode, builder, false);
            } else {
                javaScript = JavaScriptNodeProvider.get((ASTNodeType)formulaNode.getNodeType());
                javaScript.toJavaScript(formulaNode, builder, false);
            }
        }
        catch (ToJavaScriptException e) {
            throw new ToJavaScriptException(BizBindingI18nUtil.getMessage("va.bizbinding.jshandle.currsyntaxnotsupport", new Object[]{node.toString()}), (Throwable)e);
        }
        return builder.toString();
    }
}

