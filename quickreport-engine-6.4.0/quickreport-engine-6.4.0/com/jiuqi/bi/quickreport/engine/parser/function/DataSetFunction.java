/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataSet
 *  com.jiuqi.bi.dataset.BIDataSetException
 *  com.jiuqi.bi.dataset.FilterItem
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.model.field.CalcMode
 *  com.jiuqi.bi.dataset.model.field.DSCalcField
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.data.DataNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.parser.function;

import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.FilterItem;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.field.CalcMode;
import com.jiuqi.bi.dataset.model.field.DSCalcField;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.quickreport.engine.context.IEvalTracer;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.context.ReportContextException;
import com.jiuqi.bi.quickreport.engine.context.filter.IFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.context.filter.MappingFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpressionException;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DSFieldNode;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DSFormulaInfo;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DataSetNode;
import com.jiuqi.bi.quickreport.engine.parser.dataset.IDataSetNode;
import com.jiuqi.bi.quickreport.engine.parser.restriction.DSFilterBuilder;
import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.data.DataNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class DataSetFunction
extends Function {
    private static final long serialVersionUID = 1L;

    public String category() {
        return "\u6570\u636e\u96c6\u51fd\u6570";
    }

    public boolean support(Language lang) {
        return lang == Language.FORMULA || lang == Language.EXPLAIN;
    }

    protected void toFormula(IContext context, List<IASTNode> parameters, StringBuilder buffer, Object info) throws InterpretException {
        if (info instanceof DSFormulaInfo) {
            int dataType;
            Object value;
            try {
                value = this.evalute(context, parameters);
                dataType = this.getResultType(context, parameters);
            }
            catch (SyntaxException e) {
                throw new InterpretException((Throwable)e);
            }
            if (dataType == 0) {
                dataType = DataType.typeOf((Object)value);
            }
            DataNode data = new DataNode(null, dataType, value);
            data.interpret(context, buffer, Language.FORMULA, null);
        } else {
            super.toFormula(context, parameters, buffer, info);
        }
    }

    protected static int findColumn(IContext context, BIDataSet dataSet, IASTNode colNode) throws SyntaxException {
        String fieldName;
        if (colNode instanceof DSFieldNode) {
            fieldName = ((DSFieldNode)colNode).getField().getName();
        } else if (colNode.getType(context) == 6) {
            fieldName = (String)colNode.evaluate(context);
        } else {
            throw new SyntaxException(colNode.getToken(), "\u53c2\u6570\u7c7b\u578b\u9519\u8bef\u3002");
        }
        int colIndex = dataSet.getMetadata().indexOf(fieldName);
        if (colIndex == -1) {
            throw new SyntaxException(colNode.getToken(), "\u67e5\u627e\u6570\u636e\u96c6\u5b57\u6bb5\u4e0d\u5b58\u5728\uff1a" + fieldName);
        }
        return colIndex;
    }

    protected BIDataSet openDataSet(IContext context, IASTNode dsNode, IASTNode fieldNode, List<IASTNode> restrictions) throws SyntaxException {
        if (dsNode instanceof DataSetNode) {
            return this.directOpen((ReportContext)context, (DataSetNode)dsNode, fieldNode, restrictions);
        }
        return DataSetFunction.filterOpen((ReportContext)context, dsNode, restrictions);
    }

    private BIDataSet directOpen(ReportContext context, DataSetNode dsNode, IASTNode fieldNode, List<IASTNode> restrictions) throws SyntaxException {
        String dsName = dsNode.getDataSetModel().getName();
        DSField field = DataSetFunction.findField(context, dsNode.getDataSetModel(), fieldNode);
        List<IFilterDescriptor> filters = DataSetFunction.buildFilters(dsName, context, restrictions, false);
        try {
            if (this.needAggregation(field)) {
                return context.aggregateDataSet(dsName, filters);
            }
            return context.openDataSet(dsName, filters);
        }
        catch (ReportContextException e) {
            throw new SyntaxException((Throwable)e);
        }
    }

    protected boolean needAggregation(DSField field) {
        return field instanceof DSCalcField && ((DSCalcField)field).getCalcMode() == CalcMode.AGGR_THEN_CALC;
    }

    private static DSField findField(ReportContext context, DSModel dataSetModel, IASTNode fieldNode) throws SyntaxException {
        String fieldName;
        if (fieldNode == null) {
            return null;
        }
        if (fieldNode instanceof DSFieldNode) {
            fieldName = ((DSFieldNode)fieldNode).getField().getName();
        } else if (fieldNode.getType((IContext)context) == 6) {
            fieldName = (String)fieldNode.evaluate((IContext)context);
        } else {
            throw new SyntaxException(fieldNode.getToken(), "\u53c2\u6570\u7c7b\u578b\u9519\u8bef\u3002");
        }
        return dataSetModel.findField(fieldName);
    }

    protected void evalTracer(ReportContext context, IASTNode dsNode, IASTNode fieldNode, List<IASTNode> restrictions, Object result) throws SyntaxException {
        IEvalTracer tracer;
        IEvalTracer iEvalTracer = tracer = context.getCurrentCell() == null ? null : context.getCurrentCell().getTracer();
        if (tracer != null) {
            String dsName = null;
            if (dsNode instanceof IDataSetNode) {
                DSModel dsModel = ((IDataSetNode)dsNode).getDataSetModel();
                dsName = dsModel == null ? null : dsModel.getName();
            }
            DSFilterBuilder builder = new DSFilterBuilder(dsName);
            builder.setContext(context);
            builder.getRestrictions().addAll(restrictions);
            builder.setIgnoreContext(false);
            builder.setInlineMapping(true);
            try {
                builder.build();
            }
            catch (ReportExpressionException e) {
                throw new SyntaxException((Throwable)e);
            }
            List<IFilterDescriptor> filters = DataSetFunction.buildFilters(dsName, context, restrictions, false);
            tracer.evalNode(fieldNode == null ? dsNode : fieldNode, filters, result);
        }
    }

    private static List<IFilterDescriptor> buildFilters(String dsName, ReportContext context, List<IASTNode> restrictions, boolean ignoreContext) throws SyntaxException {
        DSFilterBuilder builder = new DSFilterBuilder(dsName);
        builder.setContext(context);
        builder.getRestrictions().addAll(restrictions);
        builder.setIgnoreContext(ignoreContext);
        builder.setInlineMapping(true);
        try {
            builder.build();
        }
        catch (ReportExpressionException e) {
            throw new SyntaxException((Throwable)e);
        }
        return builder.getFilters();
    }

    private static BIDataSet filterOpen(ReportContext context, IASTNode dsNode, List<IASTNode> restrictions) throws SyntaxException {
        DSModel dsModel;
        BIDataSet dataSet = (BIDataSet)dsNode.evaluate((IContext)context);
        String dsName = dsNode instanceof IDataSetNode ? ((dsModel = ((IDataSetNode)dsNode).getDataSetModel()) == null ? null : dsModel.getName()) : null;
        List<IFilterDescriptor> filters = DataSetFunction.buildFilters(dsName, context, restrictions, false);
        Iterator<IFilterDescriptor> i = filters.iterator();
        while (i.hasNext()) {
            IFilterDescriptor filter = i.next();
            if (!(filter instanceof MappingFilterDescriptor)) continue;
            i.remove();
        }
        if (filters.isEmpty()) {
            return dataSet;
        }
        try {
            return dataSet.filter(DataSetFunction.toDSFilters(dsName, filters));
        }
        catch (BIDataSetException e) {
            throw new SyntaxException((Throwable)e);
        }
    }

    private static List<FilterItem> toDSFilters(String dsName, List<IFilterDescriptor> filters) throws SyntaxException {
        ArrayList<FilterItem> dsFilters = new ArrayList<FilterItem>(filters.size());
        for (IFilterDescriptor filter : filters) {
            try {
                dsFilters.add(filter.toFilter());
            }
            catch (ReportContextException e) {
                throw new SyntaxException((Throwable)e);
            }
        }
        return dsFilters;
    }
}

