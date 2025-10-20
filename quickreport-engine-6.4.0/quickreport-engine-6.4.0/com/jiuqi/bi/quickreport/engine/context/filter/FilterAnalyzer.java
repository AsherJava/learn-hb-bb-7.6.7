/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.context.filter;

import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.quickreport.engine.context.ReportContextException;
import com.jiuqi.bi.quickreport.engine.context.filter.ExpressionFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.context.filter.FixedFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.context.filter.IFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.context.filter.MappingFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.context.filter.ValueFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.context.filter.ValuesFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.parser.CellBindingInfo;
import com.jiuqi.bi.quickreport.engine.parser.IReportExpression;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpression;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpressionException;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DSFieldNode;
import com.jiuqi.bi.quickreport.engine.parser.dataset.IDataSetNode;
import com.jiuqi.bi.quickreport.engine.parser.function.DataSetFunction;
import com.jiuqi.bi.quickreport.engine.parser.restriction.DSFieldInfo;
import com.jiuqi.bi.quickreport.engine.parser.restriction.RestrictionAnalyzer;
import com.jiuqi.bi.quickreport.engine.parser.restriction.RestrictionDescriptor;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class FilterAnalyzer {
    private FilterAnalyzer() {
    }

    public static List<IFilterDescriptor> createFilterDescriptor(IContext context, CellBindingInfo bindingInfo, DSField field) throws ReportContextException {
        IReportExpression filterExpression;
        try {
            filterExpression = bindingInfo.getFilter().optimize(context);
        }
        catch (ReportExpressionException e) {
            throw new ReportContextException(e.getMessage(), e);
        }
        if (filterExpression.isStatic(context)) {
            return FilterAnalyzer.createFixedFilter(context, filterExpression);
        }
        String dataSetName = FilterAnalyzer.checkRestrictedDataSet(bindingInfo, filterExpression);
        List<IReportExpression> andExprs = filterExpression.getANDList(context);
        if (andExprs.size() == 1) {
            return FilterAnalyzer.createSingleFilter(context, dataSetName, field, filterExpression);
        }
        return FilterAnalyzer.createMultiFilter(context, dataSetName, field, andExprs);
    }

    private static List<IFilterDescriptor> createFixedFilter(IContext context, IReportExpression filterExpression) throws ReportContextException {
        Object value;
        try {
            value = filterExpression.evaluate(context);
        }
        catch (ReportExpressionException e) {
            throw new ReportContextException(e.getMessage(), e);
        }
        boolean isTrue = value instanceof Boolean && (Boolean)value != false;
        ArrayList<IFilterDescriptor> filters = new ArrayList<IFilterDescriptor>(1);
        FixedFilterDescriptor filter = new FixedFilterDescriptor(isTrue);
        filters.add(filter);
        return filters;
    }

    public static List<IFilterDescriptor> createFilterDescriptor(IContext context, IReportExpression expression) throws ReportContextException {
        IReportExpression filterExpression;
        try {
            filterExpression = expression.optimize(context);
        }
        catch (ReportExpressionException e) {
            throw new ReportContextException(e.getMessage(), e);
        }
        if (filterExpression.isStatic(context)) {
            return FilterAnalyzer.createFixedFilter(context, filterExpression);
        }
        String dataSetName = FilterAnalyzer.checkRestrictedDataSet(null, filterExpression);
        List<IReportExpression> andExprs = filterExpression.getANDList(context);
        if (andExprs.size() == 1) {
            return FilterAnalyzer.createSingleFilter(context, dataSetName, null, filterExpression);
        }
        return FilterAnalyzer.createMultiFilter(context, dataSetName, null, andExprs);
    }

    public static List<IFilterDescriptor> createFilterDescriptor(IContext context, IASTNode exprNode) throws ReportContextException {
        ReportExpression expr = new ReportExpression(exprNode);
        return FilterAnalyzer.createFilterDescriptor(context, expr);
    }

    private static List<IFilterDescriptor> createMultiFilter(IContext context, String dataSetName, DSField field, List<IReportExpression> filters) throws ReportContextException {
        ArrayList<IFilterDescriptor> descrs = new ArrayList<IFilterDescriptor>(filters.size());
        for (IReportExpression expr : filters) {
            IFilterDescriptor filter = FilterAnalyzer.createFilterDescriptor(context, dataSetName, field, expr);
            descrs.add(filter);
        }
        return descrs;
    }

    private static List<IFilterDescriptor> createSingleFilter(IContext context, String dataSetName, DSField field, IReportExpression filterExpression) throws ReportContextException {
        ArrayList<IFilterDescriptor> filters = new ArrayList<IFilterDescriptor>(1);
        IFilterDescriptor filter = FilterAnalyzer.createFilterDescriptor(context, dataSetName, field, filterExpression);
        filters.add(filter);
        return filters;
    }

    private static String checkRestrictedDataSet(CellBindingInfo bindingInfo, IReportExpression filterExpression) throws ReportContextException {
        List<String> filterDSNames;
        try {
            filterDSNames = FilterAnalyzer.getRefDataSets(filterExpression);
        }
        catch (ReportExpressionException e) {
            throw new ReportContextException(e);
        }
        if (filterDSNames.isEmpty()) {
            throw new ReportContextException((bindingInfo == null ? "" : bindingInfo.getPosition()) + "\u5355\u5143\u683c\u8fc7\u6ee4\u6761\u4ef6\u672a\u9650\u5b9a\u4efb\u4f55\u6570\u636e\u96c6\u3002");
        }
        if (bindingInfo != null) {
            List<String> valDSNames;
            try {
                valDSNames = FilterAnalyzer.getRefDataSets(bindingInfo.getValue());
            }
            catch (ReportExpressionException e) {
                throw new ReportContextException(e);
            }
            if (!valDSNames.isEmpty()) {
                for (String dsName : filterDSNames) {
                    if (!valDSNames.contains(dsName)) continue;
                    return dsName;
                }
            }
        }
        return filterDSNames.get(0);
    }

    private static List<String> getRefDataSets(IReportExpression expression) throws ReportExpressionException {
        HashSet<String> datasets = new HashSet<String>();
        FilterAnalyzer.getRefDataSets(expression, datasets);
        return new ArrayList<String>(datasets);
    }

    private static void getRefDataSets(Iterable<IASTNode> expr, Set<String> datasets) {
        for (IASTNode node : expr) {
            if (node instanceof IDataSetNode) {
                datasets.add(((IDataSetNode)node).getDataSetModel().getName());
                continue;
            }
            if (node instanceof DSFieldNode) {
                datasets.add(((DSFieldNode)node).getDataSet().getName());
                continue;
            }
            if (!FunctionNode.isFunction((IASTNode)node, (Class[])new Class[]{DataSetFunction.class})) continue;
            for (int i = 0; i < node.childrenSize(); ++i) {
                FilterAnalyzer.getRefDataSets((Iterable<IASTNode>)node.getChild(i), datasets);
            }
        }
    }

    private static IFilterDescriptor createFilterDescriptor(IContext context, String dataSetName, DSField field, IReportExpression filterExpression) throws ReportContextException {
        RestrictionDescriptor restriction;
        try {
            restriction = RestrictionAnalyzer.toDescriptor(context, filterExpression.getRootNode(), dataSetName);
        }
        catch (ReportExpressionException e) {
            throw new ReportContextException(e);
        }
        switch (restriction.getMode()) {
            case 3: {
                return new ValueFilterDescriptor(restriction.getField().dataSetName, restriction.getField().field, restriction.getInfo());
            }
            case 4: {
                return new ValuesFilterDescriptor(restriction.getField().dataSetName, restriction.getField().field, (List)restriction.getInfo());
            }
            case 5: {
                String dsFilter = null;
                try {
                    dsFilter = filterExpression.toDSFormula(context, dataSetName);
                }
                catch (ReportExpressionException e) {
                    throw new ReportContextException(e);
                }
                DSFieldInfo restrictedField = FilterAnalyzer.getRestrictedField(filterExpression);
                if (restrictedField != null) {
                    field = restrictedField.field;
                }
                return new ExpressionFilterDescriptor(dataSetName, field, dsFilter, filterExpression);
            }
            case 6: {
                return new MappingFilterDescriptor(restriction.getField().dataSetName, restriction.getField().field, (DSFieldInfo)restriction.getInfo());
            }
        }
        throw new ReportContextException("\u9519\u8bef\u7684\u9650\u5b9a\u8868\u8fbe\u5f0f\uff1a" + filterExpression.toString());
    }

    private static DSFieldInfo getRestrictedField(IReportExpression filter) {
        DSFieldInfo field = null;
        for (IASTNode node : filter) {
            if (!(node instanceof DSFieldNode)) continue;
            DSFieldNode fieldNode = (DSFieldNode)node;
            if (field == null) {
                field = new DSFieldInfo(fieldNode.getDataSet().getName(), fieldNode.getField());
                continue;
            }
            if (field.dataSetName.equals(fieldNode.getDataSet().getName()) && field.fieldName.equals(fieldNode.getField().getName())) continue;
            return null;
        }
        return field;
    }

    public static void distinctFilters(List<IFilterDescriptor> filters) {
        HashSet<IFilterDescriptor> finder = new HashSet<IFilterDescriptor>(filters.size());
        Iterator<IFilterDescriptor> i = filters.iterator();
        while (i.hasNext()) {
            IFilterDescriptor filter = i.next();
            if (finder.add(filter)) continue;
            i.remove();
        }
    }
}

