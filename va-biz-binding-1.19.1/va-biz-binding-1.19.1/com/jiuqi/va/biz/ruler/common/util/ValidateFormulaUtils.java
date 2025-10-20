/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.ASTNodeType
 *  com.jiuqi.bi.syntax.ast.IExpression
 */
package com.jiuqi.va.biz.ruler.common.util;

import com.jiuqi.bi.syntax.ast.ASTNodeType;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.va.biz.impl.data.DataDefineImpl;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.intf.data.DataDefine;
import com.jiuqi.va.biz.ruler.ModelNode;
import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import com.jiuqi.va.biz.ruler.intf.Formula;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ValidateFormulaUtils {
    public static final void validateFormulas(ModelDataContext context, Map<UUID, Formula> formulas, Map<UUID, IExpression> expressions) throws Exception {
        DataDefine dataDefine = context.model.getDefine().getPlugins().get(DataDefineImpl.class);
        String masterTableName = dataDefine.getTables().getMasterTable().getName();
        ValidateFormulaUtils utils = new ValidateFormulaUtils();
        for (Map.Entry<UUID, Formula> formula : formulas.entrySet()) {
            if (!FormulaType.EXECUTE.equals((Object)formula.getValue().getFormulaType())) continue;
            ValidateFormulaUtils validateFormulaUtils = utils;
            validateFormulaUtils.getClass();
            FormulaNode formulaNode = validateFormulaUtils.new FormulaNode(new ArrayList<ModelNode>());
            IExpression expression = expressions.get(formula.getKey());
            expression.forEach(node -> {
                if (node.getNodeType().equals((Object)ASTNodeType.DYNAMICDATA) && node instanceof ModelNode) {
                    ModelNode modelNode = (ModelNode)((Object)node);
                    if (formulaNode.leftNode == null) {
                        formulaNode.leftNode = modelNode;
                    } else {
                        formulaNode.rigthNodes.add(modelNode);
                    }
                }
            });
            if (formulaNode.leftNode == null) {
                throw new Exception(BizBindingI18nUtil.getMessage("va.bizbinding.validateformulautils.illegalformula", new Object[]{formula.getValue().getName()}));
            }
            if (utils.validateAssign(masterTableName, formulaNode)) continue;
            throw new Exception(BizBindingI18nUtil.getMessage("va.bizbinding.validateformulautils.subtableunableputmastertable", new Object[]{formula.getValue().getName()}));
        }
    }

    private boolean validateAssign(String masterTableName, FormulaNode formulaNode) {
        if (!formulaNode.leftNode.tableDefine.getName().equals(masterTableName)) {
            return true;
        }
        for (ModelNode modelNode : formulaNode.rigthNodes) {
            if (modelNode.tableDefine.getName().equals(masterTableName)) continue;
            return false;
        }
        return true;
    }

    private class FormulaNode {
        ModelNode leftNode;
        List<ModelNode> rigthNodes;

        FormulaNode(List<ModelNode> rigthNodes) {
            this.rigthNodes = rigthNodes;
        }
    }
}

