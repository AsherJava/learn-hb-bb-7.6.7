/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.dataset.model.field.FieldType
 *  com.jiuqi.bi.syntax.ast.IASTIterator
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.cell.CellExcpetion
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.data.DataNode
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.operator.Equal
 *  com.jiuqi.bi.syntax.operator.In
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.interaction;

import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.context.ReportContextException;
import com.jiuqi.bi.quickreport.engine.context.filter.ExpressionFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.context.filter.IFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.context.filter.ValueFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.context.filter.ValuesFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.interaction.ReportInteractionException;
import com.jiuqi.bi.quickreport.engine.parser.CellBindingInfo;
import com.jiuqi.bi.quickreport.engine.parser.IReportExpression;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpressionException;
import com.jiuqi.bi.quickreport.engine.parser.ReportParser;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DSFieldNode;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DSFormulaInfo;
import com.jiuqi.bi.quickreport.engine.parser.function.Q_CurrentValue;
import com.jiuqi.bi.quickreport.engine.result.CellFilterInfo;
import com.jiuqi.bi.quickreport.engine.result.CellRestrictionInfo;
import com.jiuqi.bi.quickreport.engine.workbook.EngineWorksheet;
import com.jiuqi.bi.syntax.ast.IASTIterator;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.cell.CellExcpetion;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.data.DataNode;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.operator.Equal;
import com.jiuqi.bi.syntax.operator.In;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InteractionFilterBuilder {
    private final ReportContext context;
    private final Map<CellFilterInfo, Object> filters;
    private EngineWorksheet primarySheet;
    private ReportParser parser;

    public InteractionFilterBuilder(ReportContext context, Map<CellFilterInfo, Object> filters) {
        this.context = context;
        this.filters = filters;
    }

    public void build() throws ReportInteractionException {
        this.context.getDataSetFilters().clear();
        this.prepare();
        for (Map.Entry<CellFilterInfo, Object> entry : this.filters.entrySet()) {
            IFilterDescriptor filter;
            CellFilterInfo filterInfo = entry.getKey();
            Object filterObject = entry.getValue();
            if (filterObject instanceof List) {
                List values = (List)filterObject;
                filter = this.buildFilter(filterInfo, values);
            } else if (filterObject instanceof String) {
                filter = this.buildFilter(filterInfo, (String)filterObject);
            } else {
                throw new ReportInteractionException("\u65e0\u6cd5\u5904\u7406\u7684\u9650\u5b9a\u7c7b\u578b\uff1a" + filterInfo + " = " + filterObject);
            }
            List filters = this.context.getDataSetFilters().computeIfAbsent(filterInfo.getDataSetName(), k -> new ArrayList());
            filters.add(filter);
        }
    }

    private void prepare() throws ReportInteractionException {
        try {
            this.primarySheet = (EngineWorksheet)this.context.getWorkbook().find(this.context, this.context.getReport().getPrimarySheetName());
        }
        catch (CellExcpetion e) {
            throw new ReportInteractionException(e);
        }
    }

    private IFilterDescriptor buildFilter(CellFilterInfo filterInfo, List<Object> values) throws ReportInteractionException {
        CellBindingInfo bindingInfo = this.openCellBinding(filterInfo.getPosition());
        if (filterInfo.getRestrictions().isEmpty() && bindingInfo.getValue().isFieldRef()) {
            return this.buildValueFilter((DSFieldNode)bindingInfo.getValue().getRootNode(), values);
        }
        return this.buildExprFilter(filterInfo, bindingInfo, values);
    }

    private IFilterDescriptor buildValueFilter(DSFieldNode fieldNode, List<Object> values) {
        if (values.size() == 1) {
            return new ValueFilterDescriptor(fieldNode.getDataSet().getName(), fieldNode.getField(), values.get(0));
        }
        return new ValuesFilterDescriptor(fieldNode.getDataSet().getName(), fieldNode.getField(), values);
    }

    private IFilterDescriptor buildExprFilter(CellFilterInfo filterInfo, CellBindingInfo bindingInfo, List<Object> values) throws ReportInteractionException {
        String formula;
        In filterExpr;
        int dataType;
        IReportExpression valueExpr = this.createValueExpr(filterInfo, bindingInfo);
        try {
            dataType = bindingInfo.getValue().getDataType(this.context);
        }
        catch (ReportExpressionException e) {
            throw new ReportInteractionException(e);
        }
        if (values.size() == 1) {
            DataNode value = new DataNode(null, dataType, values.get(0));
            filterExpr = new Equal(null, valueExpr.getRootNode(), (IASTNode)value);
        } else {
            DataNode array = DataNode.valueOf((ArrayData)new ArrayData(dataType, values));
            filterExpr = new In(null, valueExpr.getRootNode(), (IASTNode)array);
        }
        try {
            formula = filterExpr.interpret((IContext)this.context, Language.FORMULA, (Object)new DSFormulaInfo(filterInfo.getDataSetName(), true));
        }
        catch (InterpretException e) {
            throw new ReportInteractionException(e);
        }
        return new ExpressionFilterDescriptor(filterInfo.getDataSetName(), null, formula, (IASTNode)filterExpr);
    }

    private IReportExpression createValueExpr(CellFilterInfo filterInfo, CellBindingInfo bindingInfo) throws ReportInteractionException {
        IReportExpression valueExpr = (IReportExpression)bindingInfo.getValue().clone();
        if (!filterInfo.getRestrictions().isEmpty()) {
            List<IASTNode> fieldFilters = this.createFilters(filterInfo);
            this.appendFieldFilter(valueExpr, fieldFilters);
        }
        return valueExpr;
    }

    private IFilterDescriptor buildFilter(CellFilterInfo filterInfo, String expression) throws ReportInteractionException {
        String formula;
        CellBindingInfo bindingInfo = this.openCellBinding(filterInfo.getPosition());
        IReportExpression filterExpr = this.parseCondition(expression, true);
        IReportExpression valueExpr = this.createValueExpr(filterInfo, bindingInfo);
        IASTIterator i = filterExpr.getRootNode().astIterator();
        while (i.hasNext()) {
            IASTNode node = (IASTNode)i.next();
            if (!FunctionNode.isFunction((IASTNode)node, (Class[])new Class[]{Q_CurrentValue.class})) continue;
            i.set((IASTNode)valueExpr.getRootNode().clone());
        }
        try {
            formula = filterExpr.toDSFormula(this.context, filterInfo.getDataSetName());
        }
        catch (ReportExpressionException e) {
            throw new ReportInteractionException(e);
        }
        return new ExpressionFilterDescriptor(filterInfo.getDataSetName(), null, formula, filterExpr);
    }

    private List<IASTNode> createFilters(CellFilterInfo filterInfo) throws ReportInteractionException {
        ArrayList<IASTNode> filters = new ArrayList<IASTNode>();
        for (CellRestrictionInfo restriction : filterInfo.getRestrictions()) {
            IASTNode filter = restriction.getExpression() == null ? this.createValueFilter(restriction) : this.createExprFilter(restriction);
            filters.add(filter);
        }
        return filters;
    }

    private IASTNode createValueFilter(CellRestrictionInfo restriction) throws ReportInteractionException {
        DSModel model;
        try {
            model = this.context.openDataSetModel(restriction.getDataSetName());
        }
        catch (ReportContextException e) {
            throw new ReportInteractionException(e);
        }
        DSField field = model.findField(restriction.getFieldName());
        DSFieldNode fieldNode = new DSFieldNode(null, model, field, true);
        DataNode value = new DataNode(null, field.getValType(), restriction.getValue());
        return new Equal(null, (IASTNode)fieldNode, (IASTNode)value);
    }

    private IASTNode createExprFilter(CellRestrictionInfo restriction) throws ReportInteractionException {
        IReportExpression expr = this.parseCondition(restriction.getExpression(), false);
        return expr.getRootNode();
    }

    private void appendFieldFilter(IReportExpression valueExpr, List<IASTNode> fieldFilters) {
        for (IASTNode node : valueExpr) {
            DSFieldNode fieldNode;
            if (!(node instanceof DSFieldNode) || (fieldNode = (DSFieldNode)node).getField().getFieldType() != FieldType.MEASURE) continue;
            fieldFilters.stream().map(f -> (IASTNode)f.clone()).forEach(fieldNode.getRestrictions()::add);
        }
    }

    private CellBindingInfo openCellBinding(Position postion) throws ReportInteractionException {
        CellBindingInfo bindingInfo = (CellBindingInfo)this.primarySheet.getGridData().getObj(postion.col(), postion.row());
        if (bindingInfo == null) {
            throw new ReportInteractionException("\u5b9a\u4f4d\u5355\u5143\u683c\u4fe1\u606f\u4e0d\u5b58\u5728\uff1a" + postion);
        }
        return bindingInfo;
    }

    private IReportExpression parseCondition(String expression, boolean supportCurrVal) throws ReportInteractionException {
        if (this.parser == null) {
            this.parser = new ReportParser(this.context);
        }
        if (supportCurrVal) {
            this.parser.beginAction(1);
            try {
                IReportExpression iReportExpression = this.parser.parseCond(expression);
                return iReportExpression;
            }
            catch (ReportExpressionException e) {
                throw new ReportInteractionException(e);
            }
            finally {
                this.parser.endAction(1);
            }
        }
        try {
            return this.parser.parseCond(expression);
        }
        catch (ReportExpressionException e) {
            throw new ReportInteractionException(e);
        }
    }
}

