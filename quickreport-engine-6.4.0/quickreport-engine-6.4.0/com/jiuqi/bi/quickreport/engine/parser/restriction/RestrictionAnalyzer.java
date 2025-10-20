/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.Expression
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.logic.InList
 *  com.jiuqi.bi.syntax.operator.Equal
 *  com.jiuqi.bi.syntax.operator.In
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.util.ASTHelper
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.quickreport.engine.parser.restriction;

import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.quickreport.engine.parser.IReportExpression;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpression;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpressionException;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DSFieldNode;
import com.jiuqi.bi.quickreport.engine.parser.dataset.RestrictedFieldNode;
import com.jiuqi.bi.quickreport.engine.parser.function.Lag;
import com.jiuqi.bi.quickreport.engine.parser.function.Lead;
import com.jiuqi.bi.quickreport.engine.parser.function.OffsetFunction;
import com.jiuqi.bi.quickreport.engine.parser.restriction.DSFieldInfo;
import com.jiuqi.bi.quickreport.engine.parser.restriction.RestrictionDescriptor;
import com.jiuqi.bi.quickreport.engine.parser.restriction.RestrictionNode;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.Expression;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.logic.InList;
import com.jiuqi.bi.syntax.operator.Equal;
import com.jiuqi.bi.syntax.operator.In;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.util.ASTHelper;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RestrictionAnalyzer {
    private final IContext context;
    private final String curDataSet;

    public static RestrictionDescriptor toDescriptor(IContext context, IASTNode filter, String curDataSet) throws ReportExpressionException {
        RestrictionAnalyzer analyzer = new RestrictionAnalyzer(context, curDataSet);
        return analyzer.analyse(filter);
    }

    public RestrictionAnalyzer(IContext context, String curDataSet) {
        this.context = context;
        this.curDataSet = curDataSet;
    }

    public RestrictionDescriptor analyse(IASTNode filter) throws ReportExpressionException {
        if (filter instanceof RestrictedFieldNode) {
            return this.getAsRestriction((RestrictedFieldNode)filter);
        }
        return this.getAsFilter(filter);
    }

    private RestrictionDescriptor getAsRestriction(RestrictedFieldNode node) throws ReportExpressionException {
        return this.toRestriction(node.getDataSet().getName(), node.getField(), node.getTag());
    }

    private RestrictionDescriptor toRestriction(String dataSetName, DSField field, String tag) throws ReportExpressionException {
        if ("ALL".equalsIgnoreCase(tag) || "MB".equalsIgnoreCase(tag)) {
            return new RestrictionDescriptor(1, new DSFieldInfo(dataSetName, field), null);
        }
        if ("NEXT".equalsIgnoreCase(tag)) {
            return new RestrictionDescriptor(2, new DSFieldInfo(dataSetName, field), 1);
        }
        if ("PREV".equalsIgnoreCase(tag)) {
            return new RestrictionDescriptor(2, new DSFieldInfo(dataSetName, field), -1);
        }
        throw new ReportExpressionException("\u672a\u652f\u6301\u7684\u9650\u5b9a\u6a21\u5f0f\uff1a" + tag);
    }

    private RestrictionDescriptor getAsFilter(IASTNode filter) throws ReportExpressionException {
        if (filter instanceof Equal) {
            return this.getAsEqual((Equal)filter);
        }
        if (filter instanceof In) {
            return this.getAsIn((In)filter);
        }
        if (filter instanceof FunctionNode) {
            IFunction func = ((FunctionNode)filter).getDefine();
            if (func instanceof OffsetFunction) {
                return this.getAsOffsetFunction((FunctionNode)filter);
            }
            if (func instanceof InList) {
                return this.getAsInListFunction((FunctionNode)filter);
            }
        }
        return this.getAsExpression(filter);
    }

    private RestrictionDescriptor getAsEqual(Equal filter) throws ReportExpressionException {
        ArrayList<DSFieldNode> fieldNodes = new ArrayList<DSFieldNode>(2);
        IASTNode valueNode = this.scanEqual(filter, fieldNodes);
        if (fieldNodes.size() == 1 && valueNode != null) {
            return this.getAsEqualStatic((DSFieldNode)((Object)fieldNodes.get(0)), valueNode);
        }
        if (fieldNodes.size() == 2 && !((DSFieldNode)((Object)fieldNodes.get(0))).getDataSet().getName().equalsIgnoreCase(((DSFieldNode)((Object)fieldNodes.get(1))).getDataSet().getName())) {
            return this.getAsMapping(fieldNodes);
        }
        return this.getAsExpression((IASTNode)filter);
    }

    private IASTNode scanEqual(Equal filter, List<DSFieldNode> fieldNodes) throws ReportExpressionException {
        IASTNode valueNode = null;
        for (int i = 0; i < 2; ++i) {
            IASTNode node = filter.getChild(i);
            if (node instanceof DSFieldNode) {
                DSFieldNode fieldNode = (DSFieldNode)node;
                if (!fieldNode.getRestrictions().isEmpty()) {
                    throw new ReportExpressionException("\u5728\u8fdb\u884c\u7b49\u503c\u5224\u65ad\u65f6\uff0c\u7981\u6b62\u5bf9\u5b57\u6bb5\u8fdb\u884c\u9650\u5b9a\u3002");
                }
                fieldNodes.add(fieldNode);
                continue;
            }
            if (!node.isStatic(this.context) && !(node instanceof RestrictionNode)) continue;
            valueNode = node;
        }
        return valueNode;
    }

    private RestrictionDescriptor getAsMapping(List<DSFieldNode> fieldNodes) throws ReportExpressionException {
        DSFieldNode mapNode;
        DSFieldNode curNode;
        if (StringUtils.isEmpty((String)this.curDataSet) || fieldNodes.get(0).getDataSet().getName().equalsIgnoreCase(this.curDataSet)) {
            curNode = fieldNodes.get(0);
            mapNode = fieldNodes.get(1);
        } else if (fieldNodes.get(1).getDataSet().getName().equalsIgnoreCase(this.curDataSet)) {
            curNode = fieldNodes.get(1);
            mapNode = fieldNodes.get(0);
        } else {
            throw new ReportExpressionException("\u5173\u8054\u7684\u6570\u636e\u96c6\u4e0e\u5f53\u524d\u6570\u636e\u96c6\u65e0\u5173\u3002");
        }
        DSFieldInfo curField = new DSFieldInfo(curNode.getDataSet().getName(), curNode.getField());
        DSFieldInfo mapField = new DSFieldInfo(mapNode.getDataSet().getName(), mapNode.getField());
        return new RestrictionDescriptor(6, curField, mapField);
    }

    private RestrictionDescriptor getAsEqualStatic(DSFieldNode fieldNode, IASTNode valueNode) throws ReportExpressionException {
        Object value;
        if (valueNode instanceof RestrictionNode) {
            String tag = ((RestrictionNode)valueNode).getTag();
            return this.toRestriction(fieldNode.getDataSet().getName(), fieldNode.getField(), tag);
        }
        try {
            value = valueNode.evaluate(this.context);
        }
        catch (SyntaxException e) {
            throw new ReportExpressionException(e);
        }
        DSFieldInfo fieldInfo = new DSFieldInfo(fieldNode.getDataSet().getName(), fieldNode.getField());
        if (value instanceof ArrayData) {
            return this.getAsArray(fieldInfo, (ArrayData)value);
        }
        return new RestrictionDescriptor(3, fieldInfo, value);
    }

    private RestrictionDescriptor getAsIn(In filter) throws ReportExpressionException {
        Object value;
        if (!(filter.getChild(0) instanceof DSFieldNode) || !filter.getChild(1).isStatic(this.context)) {
            return this.getAsExpression((IASTNode)filter);
        }
        DSFieldNode fieldNode = (DSFieldNode)filter.getChild(0);
        try {
            value = filter.getChild(1).evaluate(this.context);
        }
        catch (SyntaxException e) {
            throw new ReportExpressionException(e);
        }
        DSFieldInfo fieldInfo = new DSFieldInfo(fieldNode.getDataSet().getName(), fieldNode.getField());
        if (value instanceof ArrayData) {
            return this.getAsArray(fieldInfo, (ArrayData)value);
        }
        return new RestrictionDescriptor(3, fieldInfo, value);
    }

    private RestrictionDescriptor getAsArray(DSFieldInfo fieldInfo, ArrayData arr) {
        if (arr.size() == 1) {
            Object v = arr.ySize() == 0 ? arr.get(0) : arr.get(0, 0);
            return new RestrictionDescriptor(3, fieldInfo, v);
        }
        List values = arr.toList();
        return new RestrictionDescriptor(4, fieldInfo, values);
    }

    private RestrictionDescriptor getAsOffsetFunction(FunctionNode offsetNode) throws ReportExpressionException {
        int offsetVal;
        Number offset;
        try {
            offset = (Number)offsetNode.getChild(1).evaluate(this.context);
        }
        catch (SyntaxException e) {
            throw new ReportExpressionException(e);
        }
        if (offset == null || offset.intValue() <= 0) {
            throw new ReportExpressionException("\u504f\u79fb\u51fd\u6570\u672a\u6307\u5b9a\u6709\u6548\u7684\u504f\u79fb\u91cf\u3002");
        }
        if (offsetNode.getDefine() instanceof Lag) {
            offsetVal = -offset.intValue();
        } else if (offsetNode.getDefine() instanceof Lead) {
            offsetVal = offset.intValue();
        } else {
            throw new ReportExpressionException("\u672a\u77e5\u7684\u504f\u79fb\u51fd\u6570\uff1a" + offsetNode.getDefine().name());
        }
        DSFieldNode fieldNode = (DSFieldNode)offsetNode.getChild(0);
        return new RestrictionDescriptor(2, new DSFieldInfo(fieldNode.getDataSet().getName(), fieldNode.getField()), offsetVal);
    }

    private RestrictionDescriptor getAsInListFunction(FunctionNode inListNode) throws ReportExpressionException {
        if (!(inListNode.getChild(0) instanceof DSFieldNode)) {
            return this.getAsExpression((IASTNode)inListNode);
        }
        ArrayList<Object> values = new ArrayList<Object>();
        for (int i = 1; i < inListNode.childrenSize(); ++i) {
            try {
                Object value = inListNode.getChild(i).evaluate(this.context);
                if (value == null) continue;
                values.add(value);
                continue;
            }
            catch (SyntaxException e) {
                throw new ReportExpressionException(e);
            }
        }
        DSFieldNode fieldNode = (DSFieldNode)inListNode.getChild(0);
        return new RestrictionDescriptor(4, new DSFieldInfo(fieldNode.getDataSet().getName(), fieldNode.getField()), values);
    }

    private RestrictionDescriptor getAsExpression(IASTNode filter) throws ReportExpressionException {
        RestrictionDescriptor descriptor = this.tryAsOrValues(filter);
        if (descriptor != null) {
            return descriptor;
        }
        return this.toExpressionDescriptor(filter);
    }

    private RestrictionDescriptor toExpressionDescriptor(IASTNode filter) throws ReportExpressionException {
        IASTNode optFilter;
        try {
            optFilter = filter.optimize(this.context, 2);
        }
        catch (SyntaxException e) {
            throw new ReportExpressionException(e);
        }
        ReportExpression expr = new ReportExpression((IExpression)new Expression(null, optFilter));
        Map<String, Set<DSField>> fieldsMap = this.getFieldsMap(expr);
        DSFieldInfo field = this.getFieldInfo(fieldsMap);
        return new RestrictionDescriptor(5, field, expr);
    }

    private RestrictionDescriptor tryAsOrValues(IASTNode filter) throws ReportExpressionException {
        List orNodes = ASTHelper.getORList((IASTNode)filter);
        if (orNodes.size() <= 1) {
            return null;
        }
        RestrictionDescriptor descr = null;
        ArrayList<Object> values = new ArrayList<Object>();
        block4: for (IASTNode node : orNodes) {
            RestrictionDescriptor curDescr = RestrictionAnalyzer.toDescriptor(this.context, node, this.curDataSet);
            switch (curDescr.getMode()) {
                case 3: {
                    if (descr == null) {
                        descr = curDescr;
                    } else if (!curDescr.getField().equals(descr.getField())) {
                        return null;
                    }
                    values.add(curDescr.getInfo());
                    continue block4;
                }
                case 4: {
                    if (descr == null) {
                        descr = curDescr;
                    } else if (!curDescr.getField().equals(descr.getInfo())) {
                        return null;
                    }
                    values.addAll((List)curDescr.getInfo());
                    continue block4;
                }
            }
            return null;
        }
        if (descr == null) {
            return null;
        }
        return new RestrictionDescriptor(4, descr.getField(), values);
    }

    private Map<String, Set<DSField>> getFieldsMap(IReportExpression expr) throws ReportExpressionException {
        HashMap<String, Set<DSField>> fieldsMap = new HashMap<String, Set<DSField>>();
        for (IASTNode node : expr) {
            if (node instanceof DSFieldNode) {
                HashSet<DSField> fields;
                DSFieldNode fieldNode = (DSFieldNode)node;
                String dataSetName = fieldNode.getDataSet().getName();
                if (dataSetName != null) {
                    dataSetName = dataSetName.toUpperCase();
                }
                if ((fields = (HashSet<DSField>)fieldsMap.get(dataSetName)) == null) {
                    fields = new HashSet<DSField>();
                    fieldsMap.put(dataSetName, fields);
                }
                fields.add(fieldNode.getField());
                continue;
            }
            if (!(node instanceof RestrictedFieldNode)) continue;
            throw new ReportExpressionException("\u5728\u9650\u5b9a\u6761\u4ef6\u4e2d\u7981\u6b62\u4f7f\u7528\u5d4c\u5957\u7684\u9650\u5b9a\u6761\u4ef6\u3002");
        }
        return fieldsMap;
    }

    private DSFieldInfo getFieldInfo(Map<String, Set<DSField>> fieldsMap) throws ReportExpressionException {
        if (StringUtils.isEmpty((String)this.curDataSet)) {
            if (fieldsMap.entrySet().size() != 1) {
                return null;
            }
            Map.Entry<String, Set<DSField>> e = fieldsMap.entrySet().iterator().next();
            if (e.getValue().size() != 1) {
                return null;
            }
            return new DSFieldInfo(e.getKey(), e.getValue().iterator().next());
        }
        Set<DSField> fields = fieldsMap.get(this.curDataSet.toUpperCase());
        if (fields == null || fields.isEmpty()) {
            throw new ReportExpressionException("\u9650\u5b9a\u8868\u8fbe\u5f0f\u9519\u8bef\uff0c\u672a\u5bf9\u53d6\u6570\u6570\u636e\u96c6\u8fdb\u884c\u9650\u5b9a\u3002");
        }
        if (fields.size() == 1) {
            return new DSFieldInfo(this.curDataSet, fields.iterator().next());
        }
        return null;
    }
}

