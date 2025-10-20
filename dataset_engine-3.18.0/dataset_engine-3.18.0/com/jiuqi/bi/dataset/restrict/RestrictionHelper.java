/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.Expression
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.operator.Equal
 *  com.jiuqi.bi.syntax.operator.In
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.dataset.restrict;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.expression.DSFieldNode;
import com.jiuqi.bi.dataset.expression.DSFormulaContext;
import com.jiuqi.bi.dataset.expression.DSFunctionNode;
import com.jiuqi.bi.dataset.expression.RestrictTagNode;
import com.jiuqi.bi.dataset.function.Lag;
import com.jiuqi.bi.dataset.restrict.RestrictionDescriptor;
import com.jiuqi.bi.dataset.restrict.RestrictionTag;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.Expression;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.operator.Equal;
import com.jiuqi.bi.syntax.operator.In;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.ArrayList;
import java.util.Iterator;

public class RestrictionHelper {
    private RestrictionHelper() {
    }

    public static RestrictionDescriptor checkRestriction(DSFormulaContext context, IASTNode node) throws BIDataSetException {
        if (node instanceof Equal) {
            return RestrictionHelper.getEqualRestriction(context, (Equal)node);
        }
        if (node instanceof In) {
            return RestrictionHelper.getInRestriction(context, (In)node);
        }
        if (node instanceof DSFunctionNode) {
            return RestrictionHelper.getFunctionRestriction(context, (DSFunctionNode)node);
        }
        return RestrictionHelper.getExpressionRestriction(context, node);
    }

    private static RestrictionDescriptor getEqualRestriction(DSFormulaContext context, Equal node) throws BIDataSetException {
        IASTNode left = node.getChild(0);
        IASTNode right = node.getChild(1);
        if (right instanceof RestrictTagNode) {
            return RestrictionHelper.getTagRestriction(context, node);
        }
        if (right.isStatic((IContext)context) && left instanceof DSFieldNode) {
            return RestrictionHelper.getEqualValueRestriction(context, (IASTNode)node);
        }
        return RestrictionHelper.getExpressionRestriction(context, (IASTNode)node);
    }

    private static RestrictionDescriptor getInRestriction(DSFormulaContext context, In node) throws BIDataSetException {
        if (node.getChild(1).isStatic((IContext)context)) {
            return RestrictionHelper.getEqualValueRestriction(context, (IASTNode)node);
        }
        return RestrictionHelper.getExpressionRestriction(context, (IASTNode)node);
    }

    private static RestrictionDescriptor getFunctionRestriction(DSFormulaContext context, DSFunctionNode node) throws BIDataSetException {
        IFunction define = node.getDefine();
        if (!(define instanceof Lag)) {
            return RestrictionHelper.getExpressionRestriction(context, (IASTNode)node);
        }
        IASTNode p0 = node.getChild(0);
        if (p0 instanceof DSFieldNode) {
            return RestrictionHelper.getDimOffsetRestriction(context, node);
        }
        throw new BIDataSetException("\u975e\u6cd5\u7684\u9650\u5b9a\u6761\u4ef6\uff1a" + node.toString());
    }

    private static RestrictionDescriptor getExpressionRestriction(DSFormulaContext context, IASTNode node) throws BIDataSetException {
        try {
            int type = node.getType((IContext)context);
            if (type != 0 && type != 1) {
                throw new BIDataSetException("\u975e\u6cd5\u7684\u9650\u5b9a\u53c2\u6570\uff1a" + node.toString());
            }
        }
        catch (SyntaxException e) {
            throw new BIDataSetException("\u83b7\u53d6\u8282\u70b9\u7c7b\u578b\u51fa\u9519\uff0c" + e.getMessage(), e);
        }
        return new RestrictionDescriptor(3, null, -1, new Expression(null, node));
    }

    private static RestrictionDescriptor getEqualValueRestriction(DSFormulaContext context, IASTNode node) throws BIDataSetException {
        IASTNode left = node.getChild(0);
        IASTNode right = node.getChild(1);
        if (left instanceof DSFieldNode) {
            DSFieldNode dsNode = (DSFieldNode)left;
            if (!dsNode.getFieldInfo().isDimention()) {
                return RestrictionHelper.getExpressionRestriction(context, node);
            }
            Object value = right;
            if (right.isStatic((IContext)context)) {
                value = RestrictionHelper.getNodeValue(context, right);
            }
            Metadata<BIDataSetFieldInfo> metadata = context.getDataSet().getMetadata();
            int colIdx = metadata.indexOf(dsNode.getName());
            return new RestrictionDescriptor(2, dsNode.getFieldInfo(), colIdx, value);
        }
        return RestrictionHelper.getExpressionRestriction(context, node);
    }

    private static RestrictionDescriptor getTagRestriction(DSFormulaContext context, Equal node) throws BIDataSetException {
        IASTNode left = node.getChild(0);
        IASTNode right = node.getChild(1);
        if (left instanceof DSFieldNode) {
            DSFieldNode dsNode = (DSFieldNode)left;
            int colIdx = context.getDataSet().getMetadata().indexOf(dsNode.getFieldInfo().getName());
            String tag = ((RestrictTagNode)right).getTag();
            if (RestrictionTag.isPREV(tag)) {
                return new RestrictionDescriptor(1, dsNode.getFieldInfo(), colIdx, -1);
            }
            if (RestrictionTag.isNEXT(tag)) {
                return new RestrictionDescriptor(1, dsNode.getFieldInfo(), colIdx, 1);
            }
            return new RestrictionDescriptor(0, dsNode.getFieldInfo(), colIdx, tag);
        }
        throw new BIDataSetException("\u975e\u6cd5\u7684\u9650\u5b9a\u6761\u4ef6\uff1a" + node.toString());
    }

    private static RestrictionDescriptor getDimOffsetRestriction(DSFormulaContext context, FunctionNode node) throws BIDataSetException {
        DSFieldNode dsNode = (DSFieldNode)node.getChild(0);
        int colIdx = context.getDataSet().getMetadata().indexOf(dsNode.getFieldInfo().getName());
        int offset = RestrictionHelper.getOffsetValue(context, node);
        return new RestrictionDescriptor(1, dsNode.getFieldInfo(), colIdx, offset);
    }

    private static int getOffsetValue(IContext context, FunctionNode node) throws BIDataSetException {
        IASTNode param1 = node.getChild(1);
        if (!param1.isStatic(context)) {
            throw new BIDataSetException("\u51fd\u6570" + node.getDefine().name() + "\u7684\u504f\u79fb\u91cf\u5fc5\u987b\u4e3a\u5e38\u91cf\u3002");
        }
        Object value = RestrictionHelper.getNodeValue(context, param1);
        if (node.getDefine() instanceof Lag) {
            return -Math.abs(((Number)value).intValue());
        }
        throw new BIDataSetException("\u672a\u652f\u6301\u7684\u504f\u79fb\u51fd\u6570\uff1a" + node.getDefine().name());
    }

    private static Object getNodeValue(IContext context, IASTNode node) throws BIDataSetException {
        Object[] value;
        try {
            value = node.evaluate(context);
        }
        catch (SyntaxException e) {
            throw new BIDataSetException("\u8ba1\u7b97\u8282\u70b9\u65f6\u51fa\u9519\uff0c" + e.getMessage(), e);
        }
        if (value instanceof ArrayData) {
            ArrayData array = (ArrayData)value;
            int baseType = array.baseType();
            if (baseType != 3 && baseType != 6) {
                throw new BIDataSetException("\u6570\u7ec4\u7c7b\u578b\u8282\u70b9\u4e0d\u5408\u6cd5\uff0c\u8282\u70b9\u4e2d\u7684\u57fa\u672c\u7c7b\u578b\u53ea\u80fd\u4e3a\u6570\u503c\u6216\u5b57\u7b26\u4e32\u7c7b\u578b");
            }
            Iterator itor = array.iterator();
            ArrayList list = new ArrayList();
            while (itor.hasNext()) {
                list.add(itor.next());
            }
            value = list.toArray(new Object[list.size()]);
        }
        return value;
    }
}

