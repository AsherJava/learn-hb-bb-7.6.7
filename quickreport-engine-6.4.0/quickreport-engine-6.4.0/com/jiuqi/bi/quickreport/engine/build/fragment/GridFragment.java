/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.format.IDataFormator
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.util.ASTHelper
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.bi.util.tuples.KeyValue
 *  com.jiuqi.bi.util.tuples.Triplet
 */
package com.jiuqi.bi.quickreport.engine.build.fragment;

import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.QuickReportError;
import com.jiuqi.bi.quickreport.engine.build.CellValue;
import com.jiuqi.bi.quickreport.engine.build.ReportBuildException;
import com.jiuqi.bi.quickreport.engine.build.ResultGridData;
import com.jiuqi.bi.quickreport.engine.build.handler.ICellContentHandler;
import com.jiuqi.bi.quickreport.engine.build.handler.IHandlerContext;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.context.ReportContextException;
import com.jiuqi.bi.quickreport.engine.context.filter.ExpressionFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.context.filter.FilterAnalyzer;
import com.jiuqi.bi.quickreport.engine.context.filter.FixedFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.context.filter.IFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.context.filter.MappingFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.parser.CellBindingInfo;
import com.jiuqi.bi.quickreport.engine.parser.FilterBindingInfo;
import com.jiuqi.bi.quickreport.engine.parser.IReportExpression;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpressionException;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DSFieldNode;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DSFormulaInfo;
import com.jiuqi.bi.quickreport.engine.parser.dataset.IDataSetNode;
import com.jiuqi.bi.quickreport.engine.result.TraceInfo;
import com.jiuqi.bi.quickreport.engine.workbook.EngineWorksheet;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.format.IDataFormator;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.util.ASTHelper;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.tuples.KeyValue;
import com.jiuqi.bi.util.tuples.Triplet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class GridFragment {
    private Set<DSField> filteredFields;
    protected EngineWorksheet worksheet;
    protected ReportContext context;
    protected ICellContentHandler cellHandler;
    protected IHandlerContext handlerContext = new IHandlerContext(){

        @Override
        public ResultGridData getResultGrid() {
            return GridFragment.this.worksheet.getResultGrid();
        }

        @Override
        public GridData getRawGrid() {
            return GridFragment.this.worksheet.getGridData();
        }

        @Override
        public ReportContext getContext() {
            return GridFragment.this.context;
        }
    };

    public GridFragment() {
        this.filteredFields = new HashSet<DSField>();
    }

    public EngineWorksheet getWorksheet() {
        return this.worksheet;
    }

    public void setWorksheet(EngineWorksheet worksheet) {
        this.worksheet = worksheet;
    }

    public ReportContext getContext() {
        return this.context;
    }

    public void setContext(ReportContext context) {
        this.context = context;
    }

    public ICellContentHandler getCellHandler() {
        return this.cellHandler;
    }

    public void setCellHandler(ICellContentHandler cellHandler) {
        this.cellHandler = cellHandler;
    }

    public abstract void build() throws ReportBuildException;

    protected List<IFilterDescriptor> getColRowFilters(Position pos) throws ReportBuildException {
        FilterBindingInfo colFilter;
        ArrayList<IFilterDescriptor> colRowFilters = new ArrayList<IFilterDescriptor>();
        FilterBindingInfo rowFilter = (FilterBindingInfo)this.worksheet.getGridData().getObj(0, pos.row());
        if (rowFilter != null) {
            List<IFilterDescriptor> filterDescrs;
            try {
                filterDescrs = FilterAnalyzer.createFilterDescriptor((IContext)this.context, rowFilter.expression);
            }
            catch (ReportContextException e) {
                throw new ReportBuildException("\u5206\u6790\u9875\u7b7e\u201c" + this.worksheet.name() + "\u201d\u4e2d" + pos.row() + "\u884c\u53e3\u5f84\u51fa\u9519\uff0c" + e.getMessage(), e);
            }
            colRowFilters.addAll(filterDescrs);
        }
        if ((colFilter = (FilterBindingInfo)this.worksheet.getGridData().getObj(pos.col(), 0)) != null) {
            List<IFilterDescriptor> filterDescrs;
            try {
                filterDescrs = FilterAnalyzer.createFilterDescriptor((IContext)this.context, colFilter.expression);
            }
            catch (ReportContextException e) {
                throw new ReportBuildException("\u5206\u6790\u9875\u7b7e\u201c" + this.worksheet.name() + "\u201d\u4e2d" + Position.nameOfCol((int)pos.col()) + "\u680f\u53e3\u5f84\u51fa\u9519\uff0c" + e.getMessage(), e);
            }
            colRowFilters.addAll(filterDescrs);
        }
        return colRowFilters;
    }

    protected void beginFilter() {
    }

    protected void pushFilters(List<IFilterDescriptor> filters) {
        for (IFilterDescriptor filter : filters) {
            if (filter.getField() != null && this.filteredFields.contains(filter.getField()) && !(filter instanceof MappingFilterDescriptor)) continue;
            this.context.getCurrentFilters().add(filter);
        }
        for (IFilterDescriptor filter : filters) {
            if (filter.getField() == null || filter instanceof MappingFilterDescriptor) continue;
            this.filteredFields.add(filter.getField());
        }
    }

    protected void endFilter() {
        this.filteredFields.clear();
    }

    protected CellValue calcCell(CellBindingInfo bindingInfo) throws ReportBuildException {
        CellValue cellValue = new CellValue(bindingInfo);
        try {
            cellValue.value = bindingInfo.isTraceable() && bindingInfo.getDisplay() == null ? this.traceValue(bindingInfo.getValue(), cellValue) : this.evalValue(bindingInfo.getValue());
        }
        catch (ReportExpressionException e) {
            throw new ReportBuildException(bindingInfo.getPosition() + "\u5355\u5143\u683c\u503c\u8ba1\u7b97\u9519\u8bef\uff0c" + e.getMessage(), e);
        }
        if (bindingInfo.getDisplay() == null) {
            cellValue.displayValue = cellValue.value;
        } else {
            try {
                cellValue.displayValue = bindingInfo.isTraceable() ? this.traceValue(bindingInfo.getDisplay(), cellValue) : this.evalValue(bindingInfo.getDisplay());
            }
            catch (ReportExpressionException e) {
                throw new ReportBuildException(bindingInfo.getPosition() + "\u5355\u5143\u683c\u663e\u793a\u503c\u8ba1\u7b97\u9519\u8bef\uff0c" + e.getMessage(), e);
            }
        }
        if (bindingInfo.getComment() != null) {
            Object comment;
            try {
                comment = bindingInfo.getComment().evaluate(this.context);
            }
            catch (ReportExpressionException e) {
                throw new ReportBuildException(bindingInfo.getPosition() + "\u5355\u5143\u683c\u6279\u6ce8\u503c\u8ba1\u7b97\u9519\u8bef\uff0c" + e.getMessage(), e);
            }
            cellValue.setComment(comment == null ? null : comment.toString());
        }
        return cellValue;
    }

    private Object evalValue(IReportExpression expression) throws ReportExpressionException {
        return expression.evaluate(this.context);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object traceValue(IReportExpression expression, CellValue cellValue) throws ReportExpressionException {
        Object value;
        TraceInfos traceInfos = new TraceInfos(expression);
        this.context.getCurrentCell().setTracer((node, filters, result) -> this.traceNode(traceInfos, node, filters, result));
        try {
            value = expression.evaluate(this.context);
        }
        finally {
            this.context.getCurrentCell().setTracer(null);
        }
        cellValue.setTraceInfos(traceInfos.toInfos());
        return value;
    }

    private TraceInfo traceNode(TraceInfos traceInfos, IASTNode node, List<IFilterDescriptor> filters, Object result) throws SyntaxException {
        String dsName;
        if (node instanceof DSFieldNode) {
            dsName = ((DSFieldNode)node).getDataSet().getName();
        } else if (node instanceof IDataSetNode) {
            dsName = ((IDataSetNode)node).getDataSetModel().getName();
        } else if (node.getType((IContext)this.context) == 6) {
            dsName = (String)node.evaluate((IContext)this.context);
        } else {
            throw new SyntaxException(node.getToken(), "\u53c2\u6570\u7c7b\u578b\u9519\u8bef\u3002");
        }
        List<IFilterDescriptor> orderedFilters = this.buildTraceFilters(filters, dsName);
        ArrayList<String> dsFilters = new ArrayList<String>(filters.size());
        DSFormulaInfo fmlInfo = new DSFormulaInfo(dsName, true);
        for (IFilterDescriptor filter : orderedFilters) {
            String dsFilter = this.toDSFilter(filter, fmlInfo);
            if (dsFilter == null) continue;
            dsFilters.add(dsFilter);
        }
        String traceExpr = node.interpret((IContext)this.context, Language.FORMULA, null);
        if (traceInfos.contains(dsName, traceExpr, dsFilters)) {
            return null;
        }
        int dataType = node.getType((IContext)this.context);
        String pattern = null;
        IDataFormator formatter = node.getDataFormator((IContext)this.context);
        if (formatter != null) {
            pattern = formatter.getPattern((IContext)this.context);
        }
        TraceInfo traceInfo = new TraceInfo();
        traceInfo.setDataSetName(dsName);
        traceInfo.setExpression(traceExpr);
        traceInfo.getFilters().addAll(dsFilters);
        traceInfo.setDataType(dataType);
        traceInfo.setValue(result);
        traceInfo.setShowPattern(pattern);
        traceInfos.add(node, traceInfo);
        return traceInfo;
    }

    private List<IFilterDescriptor> buildTraceFilters(List<IFilterDescriptor> filters, String dsName) {
        List<IFilterDescriptor> extraFilters = this.context.getDataSetFilters().get(dsName);
        Stream<IFilterDescriptor> filterStream = extraFilters == null ? filters.stream() : Stream.concat(filters.stream(), extraFilters.stream().filter(ef -> filters.stream().noneMatch(f -> StringUtils.equals((String)ef.getDataSetName(), (String)f.getDataSetName()) && StringUtils.equals((String)ef.getFieldName(), (String)f.getFieldName()))));
        return this.orderFilters(filterStream, dsName);
    }

    private List<IFilterDescriptor> orderFilters(Stream<IFilterDescriptor> filters, String dsName) {
        return filters.filter(f -> this.isFilterOn((IFilterDescriptor)f, dsName)).map(filter -> new KeyValue((Object)this.orderOfFilter((IFilterDescriptor)filter), filter)).sorted(this::compareFilter).map(KeyValue::getValue).collect(Collectors.toList());
    }

    private boolean isFilterOn(IFilterDescriptor filter, String dsName) {
        if (dsName.equals(filter.getDataSetName())) {
            return true;
        }
        if (filter instanceof MappingFilterDescriptor) {
            MappingFilterDescriptor mapping = (MappingFilterDescriptor)filter;
            return mapping.getMappingField().dataSetName.equals(dsName);
        }
        if (filter instanceof ExpressionFilterDescriptor) {
            ExpressionFilterDescriptor exprFilter = (ExpressionFilterDescriptor)filter;
            for (IASTNode node : exprFilter.getExpression()) {
                if (!(node instanceof DSFieldNode) || !((DSFieldNode)node).getDataSet().getName().equals(dsName)) continue;
                return true;
            }
            return false;
        }
        if (filter instanceof FixedFilterDescriptor) {
            return !((FixedFilterDescriptor)filter).getValue();
        }
        return false;
    }

    private int orderOfFilter(IFilterDescriptor filter) {
        if (filter.getField() == null) {
            return 10;
        }
        DSField field = filter.getField();
        switch (field.getFieldType()) {
            case TIME_DIM: {
                if (field.isTimekey()) {
                    return -10000;
                }
                return field.getTimegranularity() == null ? 0 : -field.getTimegranularity().days();
            }
            case GENERAL_DIM: {
                return this.isUnitDim(filter.getDataSetName(), field) ? 1 : 2;
            }
            case MEASURE: {
                return 3;
            }
            case DESCRIPTION: {
                return 4;
            }
        }
        return 10;
    }

    private boolean isUnitDim(String dsName, DSField field) {
        DSModel model;
        if (field.isUnitDim()) {
            return true;
        }
        if (field.getKeyField() == null || field.getName().equals(field.getKeyField())) {
            return false;
        }
        try {
            model = this.context.openDataSetModel(dsName);
        }
        catch (ReportContextException e) {
            throw new QuickReportError(e);
        }
        DSField keyField = model.findField(field.getKeyField());
        return keyField != null && keyField.isUnitDim();
    }

    private int compareFilter(KeyValue<Integer, IFilterDescriptor> filter1, KeyValue<Integer, IFilterDescriptor> filter2) {
        DSField field2;
        int cmp = ((Integer)filter1.getKey()).compareTo((Integer)filter2.getKey());
        if (cmp != 0) {
            return cmp;
        }
        DSField field1 = ((IFilterDescriptor)filter1.getValue()).getField();
        if (field1 == (field2 = ((IFilterDescriptor)filter2.getValue()).getField())) {
            return 0;
        }
        if (field1 == null) {
            return 1;
        }
        if (field2 == null) {
            return -1;
        }
        if (field1.getKeyField() != null && field2.getKeyField() != null) {
            cmp = field1.getKeyField().compareTo(field2.getKeyField());
            if (cmp != 0) {
                return cmp;
            }
            if (field1.getKeyField().equals(field1.getName())) {
                return -1;
            }
            if (field2.getKeyField().equals(field2.getName())) {
                return 1;
            }
        }
        return field1.getName().compareTo(field2.getName());
    }

    private String toDSFilter(IFilterDescriptor filter, DSFormulaInfo info) throws SyntaxException {
        IASTNode expr;
        try {
            expr = filter.toASTFilter(this.context);
        }
        catch (ReportContextException e) {
            throw new SyntaxException((Throwable)e);
        }
        if (expr.isStatic((IContext)this.context) && expr.judge((IContext)this.context)) {
            return null;
        }
        return expr.interpret((IContext)this.context, Language.FORMULA, (Object)info);
    }

    private static final class TraceInfos {
        private Map<IASTNode, Integer> orders = new HashMap<IASTNode, Integer>();
        private List<Triplet<Integer, Integer, TraceInfo>> infos = new ArrayList<Triplet<Integer, Integer, TraceInfo>>();

        public TraceInfos(IReportExpression expression) {
            this.buildOrders(expression);
        }

        private void buildOrders(IReportExpression expression) {
            int index = 0;
            for (IASTNode node : expression.getRootNode()) {
                this.orders.put(node, index);
                ++index;
            }
        }

        public boolean contains(String dsName, String expr, List<String> filters) {
            return this.infos.stream().map(Triplet::get_2).anyMatch(ti -> dsName.equals(ti.getDataSetName()) && expr.equals(ti.getExpression()) && filters.equals(ti.getFilters()));
        }

        public void add(IASTNode node, TraceInfo info) {
            Integer index = this.orders.get(node);
            if (index == null) {
                throw new QuickReportError("\u5b9a\u4f4d\u8bed\u6cd5\u8282\u70b9\u987a\u5e8f\u5931\u8d25\uff1a" + ASTHelper.toString(null, (IASTNode)node));
            }
            this.infos.add((Triplet<Integer, Integer, TraceInfo>)Triplet.with((Object)index, (Object)this.infos.size(), (Object)info));
        }

        public List<TraceInfo> toInfos() {
            return this.infos.stream().sorted((t1, t2) -> {
                int cmp = ((Integer)t1.get_0()).compareTo((Integer)t2.get_0());
                if (cmp != 0) {
                    return cmp;
                }
                return ((Integer)t1.get_1()).compareTo((Integer)t2.get_1());
            }).map(Triplet::get_2).collect(Collectors.toList());
        }

        public String toString() {
            return this.infos.stream().map(Triplet::get_2).map(TraceInfo::toString).collect(Collectors.joining(", ", "[", "]"));
        }
    }
}

