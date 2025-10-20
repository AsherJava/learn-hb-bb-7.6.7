/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 */
package com.jiuqi.bi.dataset;

import com.jiuqi.bi.dataset.AggrMeasureItem;
import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetError;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.dataset.FilterItem;
import com.jiuqi.bi.dataset.IDSContext;
import com.jiuqi.bi.dataset.IDSFilter;
import com.jiuqi.bi.dataset.IDSTreeItem;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.SortItem;
import com.jiuqi.bi.dataset.executor.AggrExecutor;
import com.jiuqi.bi.dataset.executor.DistinctExecutor;
import com.jiuqi.bi.dataset.executor.FieldEvalExecutor;
import com.jiuqi.bi.dataset.executor.SortExecutor;
import com.jiuqi.bi.dataset.expression.DSExpression;
import com.jiuqi.bi.dataset.expression.DSFormulaContext;
import com.jiuqi.bi.dataset.expression.DSFormularManager;
import com.jiuqi.bi.dataset.expression.DatasetFormulaParser;
import com.jiuqi.bi.dataset.idx.DSIndex;
import com.jiuqi.bi.dataset.logger.DefaultLogger;
import com.jiuqi.bi.dataset.logger.ILogger;
import com.jiuqi.bi.dataset.manager.TimeKeyBuilder;
import com.jiuqi.bi.dataset.model.field.AggregationType;
import com.jiuqi.bi.dataset.model.field.CalcMode;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchyType;
import com.jiuqi.bi.dataset.restrict.FilterExecutor;
import com.jiuqi.bi.dataset.stat.tree.TreeHierarchyStatProcessor;
import com.jiuqi.bi.dataset.tree.CodeTreeBuilder;
import com.jiuqi.bi.dataset.tree.ColumnTreeBuilder;
import com.jiuqi.bi.dataset.tree.DSTreeBuilder;
import com.jiuqi.bi.dataset.tree.ParentTreeBuilder;
import com.jiuqi.bi.parameter.engine.EnhancedParameterEnvAdapter;
import com.jiuqi.bi.parameter.engine.IParameterEnv;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.util.StringUtils;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BIDataSetImpl
implements BIDataSet {
    public static final String PROPERTY_CALCFIELD_DEPEND = "calcFieldDependence";
    private final MemoryDataSet<BIDataSetFieldInfo> dataset;
    private final int startRownum;
    private SortItem[] sortItems;
    private final OriginalRowGetter rowGetter;
    private DSIndex dsIdx;
    private com.jiuqi.nvwa.framework.parameter.IParameterEnv parameterEnv;
    private ILogger logger;
    private Set<Integer> hasCalcFieldIdxes = new HashSet<Integer>();

    public BIDataSetImpl(MemoryDataSet<BIDataSetFieldInfo> dataset) {
        this(dataset, null);
    }

    public BIDataSetImpl(MemoryDataSet<BIDataSetFieldInfo> dataset, int startRownum) {
        this(dataset, null, null, startRownum);
    }

    public BIDataSetImpl(MemoryDataSet<BIDataSetFieldInfo> dataset, int[] rowIndex) {
        this(dataset, rowIndex, null, 0);
    }

    public BIDataSetImpl(MemoryDataSet<BIDataSetFieldInfo> dataset, int[] rowIndex, SortItem[] sortItems) {
        this(dataset, rowIndex, sortItems, 0);
    }

    public BIDataSetImpl(MemoryDataSet<BIDataSetFieldInfo> dataset, int[] rowIndex, SortItem[] sortItems, int startRownum) {
        this.dataset = dataset;
        this.rowGetter = rowIndex == null ? new DirectOriginalRowGetter(this) : new ArrayOriginalRowGetter(this, rowIndex);
        this.sortItems = sortItems;
        this.startRownum = startRownum;
        this.generateRownumColumn();
        this.dsIdx = new DSIndex(this);
    }

    public BIDataSetImpl(BIDataSetImpl ds, int[] rowIdx) {
        this.dataset = ds.dataset;
        int[] originalRowIdx = new int[rowIdx.length];
        for (int i = 0; i < rowIdx.length; ++i) {
            originalRowIdx[i] = ds.rowGetter.getOriginalIndex(rowIdx[i]);
        }
        this.rowGetter = new ArrayOriginalRowGetter(this, originalRowIdx);
        this.sortItems = null;
        this.startRownum = 0;
        this.generateRownumColumn();
        this.dsIdx = new DSIndex(this);
        this.parameterEnv = ds.parameterEnv;
        this.logger = ds.logger;
    }

    @Deprecated
    public void setParameterEnv(IParameterEnv parameterEnv) {
        this.parameterEnv = new EnhancedParameterEnvAdapter(parameterEnv);
    }

    public void setParameterEnv(com.jiuqi.nvwa.framework.parameter.IParameterEnv parameterEnv) {
        this.parameterEnv = parameterEnv;
    }

    public void setLogger(ILogger logger) {
        if (logger != null) {
            this.logger = logger;
        }
    }

    @Override
    @Deprecated
    public IParameterEnv getParameterEnv() {
        if (this.parameterEnv instanceof EnhancedParameterEnvAdapter) {
            return ((EnhancedParameterEnvAdapter)this.parameterEnv).getOriginalParameterEnv();
        }
        throw new RuntimeException();
    }

    @Override
    public com.jiuqi.nvwa.framework.parameter.IParameterEnv getEnhancedParameterEnv() {
        return this.parameterEnv;
    }

    public ILogger getLogger() {
        return this.logger;
    }

    public ILogger getOrDefaultLogger() {
        return this.logger != null ? this.logger : new DefaultLogger();
    }

    @Override
    public Metadata<BIDataSetFieldInfo> getMetadata() {
        return this.dataset.getMetadata();
    }

    @Override
    public int getRecordCount() {
        return this.rowGetter.getRecordCount();
    }

    @Override
    public BIDataRow get(int index) {
        int original = this.rowGetter.getOriginalIndex(index);
        return new BIDataRow(this.dataset.getBuffer(original), index + this.startRownum);
    }

    public boolean isReferencedDs() {
        return this.rowGetter instanceof ArrayOriginalRowGetter;
    }

    @Override
    public Iterator<BIDataRow> iterator() {
        return new Iterator<BIDataRow>(){
            private int counter;

            @Override
            public boolean hasNext() {
                return this.counter < BIDataSetImpl.this.getRecordCount();
            }

            @Override
            public BIDataRow next() {
                return BIDataSetImpl.this.get(this.counter++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("\u6570\u636e\u96c6\u4e0d\u652f\u6301\u5199\u64cd\u4f5c");
            }
        };
    }

    @Override
    public void compute(List<Integer> colIdxList) throws BIDataSetException {
        HashSet<Integer> aggrThenCalcFields = new HashSet<Integer>();
        if (colIdxList == null) {
            List columns = this.getMetadata().getColumns();
            Iterator iterator = columns.iterator();
            while (iterator.hasNext()) {
                Column column = (Column)iterator.next();
                BIDataSetFieldInfo info = (BIDataSetFieldInfo)column.getInfo();
                Integer idx = column.getIndex();
                if (!info.isCalcField() || info.getCalcMode() != CalcMode.AGGR_THEN_CALC || this.hasCalcFieldIdxes.contains(idx)) continue;
                aggrThenCalcFields.add(idx);
                this.hasCalcFieldIdxes.add(idx);
            }
        } else {
            if (colIdxList.size() == 0) {
                return;
            }
            for (Integer i : colIdxList) {
                BIDataSetFieldInfo info = (BIDataSetFieldInfo)this.getMetadata().getColumn(i.intValue()).getInfo();
                if (!info.isCalcField() || info.getCalcMode() != CalcMode.AGGR_THEN_CALC || this.hasCalcFieldIdxes.contains(i)) continue;
                aggrThenCalcFields.add(i);
                this.hasCalcFieldIdxes.add(i);
            }
        }
        FieldEvalExecutor evalExecutor = new FieldEvalExecutor(this);
        evalExecutor.calc(aggrThenCalcFields);
    }

    public void _markFieldHasCalc(int colIdx) {
        this.hasCalcFieldIdxes.add(colIdx);
    }

    @Override
    public BIDataSet sort(Comparator<BIDataRow> comparator) {
        if (comparator == null) {
            throw new BIDataSetError("\u4f20\u5165\u7684\u6bd4\u8f83\u5668\u5bf9\u8c61\u4e3a\u7a7a");
        }
        SortExecutor sortExecutor = new SortExecutor(this);
        return sortExecutor.sort(comparator);
    }

    @Override
    public BIDataSet sort(List<SortItem> sortItems) {
        if (sortItems == null || sortItems.size() == 0) {
            throw new BIDataSetError("\u4f20\u5165\u7684\u6392\u5e8f\u5b57\u6bb5\u4e0d\u80fd\u4e3a\u7a7a");
        }
        boolean hasSort = false;
        if (this.sortItems != null && sortItems.size() <= this.sortItems.length) {
            hasSort = true;
            for (int i = 0; i < sortItems.size(); ++i) {
                if (this.sortItems[i].equals(sortItems.get(i))) continue;
                hasSort = false;
                break;
            }
        }
        if (hasSort) {
            return this;
        }
        SortExecutor sortExecutor = new SortExecutor(this);
        BIDataSetImpl newset = sortExecutor.sort(sortItems);
        newset.sortItems = sortItems.toArray(new SortItem[sortItems.size()]);
        return newset;
    }

    public BIDataSet sort(int colIdx, int sortType) {
        SortExecutor sortExecutor = new SortExecutor(this);
        return sortExecutor.sort(colIdx, sortType);
    }

    public boolean _find(int colIdx, Object value) {
        List<Integer> rs = this.dsIdx.search(colIdx, value);
        return rs.size() > 0;
    }

    public void _calcField(IDSContext context) throws BIDataSetException {
        List columns = this.getMetadata().getColumns();
        HashSet<Integer> calcFields = new HashSet<Integer>();
        for (Column column : columns) {
            BIDataSetFieldInfo info = (BIDataSetFieldInfo)column.getInfo();
            if (!info.isCalcField() || info.getCalcMode() != CalcMode.CALC_THEN_AGGR) continue;
            calcFields.add(column.getIndex());
        }
        FieldEvalExecutor evalExecutor = new FieldEvalExecutor(this);
        if (context != null) {
            evalExecutor.setLanguage(context.getI18nLang());
        }
        evalExecutor.calc(calcFields);
    }

    @Override
    public BIDataSet filter(String expr) throws BIDataSetException {
        if (StringUtils.isEmpty((String)expr)) {
            throw new BIDataSetException("\u8fc7\u6ee4\u8868\u8fbe\u5f0f\u4e0d\u80fd\u4e3a\u7a7a");
        }
        FilterExecutor filterExecutor = new FilterExecutor(this);
        return filterExecutor.filter(expr);
    }

    @Override
    public BIDataSet filter(IDSFilter filter) throws BIDataSetException {
        if (filter == null) {
            throw new BIDataSetException("\u8fc7\u6ee4\u63a5\u53e3\u4e0d\u80fd\u4e3anull");
        }
        FilterExecutor filterExecutor = new FilterExecutor(this);
        return filterExecutor.filter(filter);
    }

    @Override
    public BIDataSet filter(List<FilterItem> filterItems) throws BIDataSetException {
        if (filterItems == null) {
            throw new BIDataSetException("\u8fc7\u6ee4\u9879\u5217\u8868\u4e3a\u7a7a");
        }
        FilterExecutor filterExecutor = new FilterExecutor(this);
        return filterExecutor.filter(filterItems);
    }

    @Override
    public BIDataSet distinct(List<String> returnFields) throws BIDataSetException {
        ArrayList<Integer> cols = new ArrayList<Integer>();
        for (String name : returnFields) {
            int col = this.getMetadata().indexOf(name);
            cols.add(col);
        }
        DistinctExecutor distinctExecutor = new DistinctExecutor(this);
        return distinctExecutor.distinct(cols, false);
    }

    public BIDataSet distinct(List<String> returnFields, boolean appendCounterColumn) throws BIDataSetException {
        ArrayList<Integer> cols = new ArrayList<Integer>();
        for (String name : returnFields) {
            int col = this.getMetadata().indexOf(name);
            cols.add(col);
        }
        DistinctExecutor distinctExecutor = new DistinctExecutor(this);
        return distinctExecutor.distinct(cols, appendCounterColumn);
    }

    @Override
    public int distinctCount(List<String> returnFields) throws BIDataSetException {
        ArrayList<Integer> cols = new ArrayList<Integer>();
        for (String name : returnFields) {
            int col = this.getMetadata().indexOf(name);
            cols.add(col);
        }
        DistinctExecutor distinctExecutor = new DistinctExecutor(this);
        return distinctExecutor.distinctCount(cols);
    }

    @Override
    public BIDataSet aggregateByItems(List<String> dimList, List<AggrMeasureItem> measureList, boolean isAutoFillParentColumn) throws BIDataSetException {
        ArrayList<Integer> colIdxList = new ArrayList<Integer>();
        for (String colName : dimList) {
            int idx = this.dataset.getMetadata().indexOf(colName);
            if (idx == -1) {
                throw new BIDataSetException("\u6570\u636e\u96c6\u4e2d\u4e0d\u5b58\u5728\u5b57\u6bb5\u3010" + colName + "\u3011");
            }
            colIdxList.add(idx);
        }
        AggrExecutor aggrExecutor = new AggrExecutor(this);
        return aggrExecutor.aggregateByItems(colIdxList, measureList, isAutoFillParentColumn);
    }

    @Override
    public BIDataSet aggregate(List<String> dimList, List<String> measureList, boolean isAutoFillParentColumn) throws BIDataSetException {
        AggrExecutor aggrExecutor = new AggrExecutor(this);
        return aggrExecutor.aggregate(dimList, measureList, isAutoFillParentColumn);
    }

    @Override
    public BIDataSet aggregate(List<String> dimList, boolean isAutoFillParentColumn) throws BIDataSetException {
        AggrExecutor aggrExecutor = new AggrExecutor(this);
        return aggrExecutor.aggregate(dimList, isAutoFillParentColumn);
    }

    @Override
    public BIDataSet aggregateByTree(BIDataSet referDimDataset, List<String> measureList) throws BIDataSetException {
        TreeHierarchyStatProcessor processor = new TreeHierarchyStatProcessor(this, (BIDataSetImpl)referDimDataset, measureList);
        return processor.stat();
    }

    @Override
    public BIDataSet aggregate(List<String> dimList) throws BIDataSetException {
        return this.aggregate(dimList, true);
    }

    @Override
    public BIDataSet aggregate(List<String> dimList, List<String> measureList) throws BIDataSetException {
        return this.aggregate(dimList, measureList, true);
    }

    @Override
    public List<Integer> lookup(String fieldName, Object value) {
        int colIndex = this.getMetadata().indexOf(fieldName);
        if (colIndex == -1) {
            throw new BIDataSetError("\u4f20\u5165\u7684\u5b57\u6bb5" + fieldName + "\u4e0d\u5b58\u5728");
        }
        return this.lookup(colIndex, value);
    }

    @Override
    public List<Integer> lookup(int colIndex, Object value) {
        List<Integer> list = this.dsIdx.search(colIndex, value);
        return new ArrayList<Integer>(list);
    }

    public int[] find(int[] colIdxes, Object[] value) {
        if (colIdxes == null || value == null) {
            throw new BIDataSetError("\u4f20\u5165\u7684\u5217\u7d22\u5f15\u53ca\u5f85\u67e5\u627e\u7684\u503c\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (colIdxes.length != value.length) {
            throw new BIDataSetError("\u4f20\u5165\u7684\u5217\u7d22\u5f15\u548c\u5f85\u67e5\u627e\u7684\u503c\u5217\u8868\u672a\u5bf9\u5e94");
        }
        List<Integer> rs = this.dsIdx.search(colIdxes, value);
        int[] rsAsArray = new int[rs.size()];
        for (int i = 0; i < rs.size(); ++i) {
            rsAsArray[i] = rs.get(i);
        }
        return rsAsArray;
    }

    @Override
    public Double sum(String fieldName) throws BIDataSetException {
        Column column = this.getMetadata().find(fieldName);
        if (column == null) {
            throw new BIDataSetException("\u6570\u636e\u96c6\u5b57\u6bb5\u4e0d\u5b58\u5728\uff1a" + fieldName);
        }
        return this.sum(column.getIndex());
    }

    @Override
    public Double sum(int colIndex) throws BIDataSetException {
        return this.stat(colIndex, AggregationType.SUM);
    }

    @Override
    public Double avg(String fieldName) throws BIDataSetException {
        Column column = this.getMetadata().find(fieldName);
        if (column == null) {
            throw new BIDataSetException("\u6570\u636e\u96c6\u5b57\u6bb5\u4e0d\u5b58\u5728\uff1a" + fieldName);
        }
        return this.avg(column.getIndex());
    }

    @Override
    public Double avg(int colIndex) throws BIDataSetException {
        return this.stat(colIndex, AggregationType.AVG);
    }

    @Override
    public Object max(String fieldName) throws BIDataSetException {
        Column column = this.getMetadata().find(fieldName);
        if (column == null) {
            throw new BIDataSetException("\u6570\u636e\u96c6\u5b57\u6bb5\u4e0d\u5b58\u5728\uff1a" + fieldName);
        }
        return this.max(column.getIndex());
    }

    @Override
    public Object max(int colIndex) throws BIDataSetException {
        return this.stat(colIndex, AggregationType.MAX);
    }

    @Override
    public Object min(String fieldName) throws BIDataSetException {
        Column column = this.getMetadata().find(fieldName);
        if (column == null) {
            throw new BIDataSetException("\u6570\u636e\u96c6\u5b57\u6bb5\u4e0d\u5b58\u5728\uff1a" + fieldName);
        }
        return this.min(column.getIndex());
    }

    @Override
    public Object min(int colIndex) throws BIDataSetException {
        return this.stat(colIndex, AggregationType.MIN);
    }

    private Double stat(int colIndex, AggregationType aggrType) throws BIDataSetException {
        if (this.getRecordCount() == 0) {
            return null;
        }
        Column column = this.getMetadata().getColumn(colIndex);
        AggrMeasureItem aggrItem = new AggrMeasureItem(column.getName(), null, null, aggrType);
        ArrayList<AggrMeasureItem> measureList = new ArrayList<AggrMeasureItem>(1);
        measureList.add(aggrItem);
        AggrExecutor aggrExecutor = new AggrExecutor(this);
        BIDataSetImpl ds = aggrExecutor.aggregateByItems(new ArrayList<Integer>(), measureList, false);
        BIDataRow dataRow = ds.get(0);
        if (dataRow.wasNull(0)) {
            return null;
        }
        return dataRow.getDouble(0);
    }

    @Override
    public DSIndex getDSIndex() {
        return this.dsIdx;
    }

    public BIDataSet doFilter(List<IASTNode> nodes, DSFormulaContext context) throws BIDataSetException {
        if (nodes == null || nodes.size() == 0) {
            return this;
        }
        FilterExecutor filterExecutor = new FilterExecutor(this);
        return filterExecutor.filter(nodes, context);
    }

    public BIDataSet topN(int n, String expr) throws BIDataSetException {
        return this.topN(n, expr, -1);
    }

    public BIDataSet topN(int n, String expr, int sortType) throws BIDataSetException {
        SortExecutor sortExecutor = new SortExecutor(this);
        List<Integer> sortedRowList = sortExecutor.sortByExpr(expr, sortType);
        if (n > sortedRowList.size()) {
            n = sortedRowList.size();
        }
        int[] rowIndexes = new int[n];
        if (sortType == -1) {
            for (int i = 0; i < n; ++i) {
                rowIndexes[i] = sortedRowList.get(i);
            }
        } else {
            for (int i = n - 1; i >= 0; --i) {
                rowIndexes[i] = sortedRowList.get(i);
            }
        }
        return new BIDataSetImpl(this, rowIndexes);
    }

    public BIDataSet bottomN(int n, String expr) throws BIDataSetException {
        return this.topN(n, expr, 1);
    }

    public BIDataSet bottomN(int n, String expr, int sortType) throws BIDataSetException {
        SortExecutor sortExecutor = new SortExecutor(this);
        List<Integer> sortedRowList = sortExecutor.sortByExpr(expr, sortType);
        if (n > sortedRowList.size()) {
            n = sortedRowList.size();
        }
        int[] rowIndexes = new int[n];
        if (sortType == 1) {
            for (int i = 0; i < n; ++i) {
                rowIndexes[i] = sortedRowList.get(i);
            }
        } else {
            for (int i = n - 1; i >= 0; --i) {
                rowIndexes[i] = sortedRowList.get(i);
            }
        }
        return new BIDataSetImpl(this, rowIndexes);
    }

    public Object[] getRowData(int index) {
        int original = this.rowGetter.getOriginalIndex(index);
        return this.dataset.getBuffer(original);
    }

    @Override
    public BIDataSet addCalcFields(Map<String, String> calcFieldMap) throws BIDataSetException {
        DatasetFormulaParser parser = DSFormularManager.getInstance().createParser(this);
        Metadata metadata = this.dataset.getMetadata();
        metadata = metadata.clone();
        ArrayList<DSExpression> calcExprs = new ArrayList<DSExpression>();
        Set<Map.Entry<String, String>> entrySet = calcFieldMap.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            try {
                DSExpression evalExpr = parser.parseEval(entry.getValue(), new DSFormulaContext(this));
                calcExprs.add(evalExpr);
                int valType = evalExpr.getType(new DSFormulaContext(this));
                String name = entry.getKey();
                BIDataSetFieldInfo info = new BIDataSetFieldInfo(name, valType, null);
                info.setCalcField(true);
                info.setFormula(entry.getValue());
                info.setAggregation(AggregationType.SUM);
                info.setCalcMode(CalcMode.CALC_THEN_AGGR);
                info.setFieldType(FieldType.MEASURE);
                info.setKeyField(name);
                info.setNameField(name);
                Column newCol = new Column(name, valType, (Object)info);
                metadata.addColumn(newCol);
            }
            catch (SyntaxException e) {
                throw new BIDataSetException("\u89e3\u6790\u8868\u8fbe\u5f0f\u51fa\u9519\uff0c" + e.getMessage(), e);
            }
        }
        try {
            MemoryDataSet newmemSet = new MemoryDataSet(null, metadata);
            for (BIDataRow dataRow : this) {
                int len = dataRow.getBuffer().length;
                Object[] newRowdata = new Object[len + calcExprs.size()];
                System.arraycopy(dataRow.getBuffer(), 0, newRowdata, 0, len);
                for (int i = 0; i < calcExprs.size(); ++i) {
                    newRowdata[len + i] = ((DSExpression)((Object)calcExprs.get(i))).evaluate(new DSFormulaContext(this, dataRow));
                }
                newmemSet.add(newRowdata);
            }
            return new BIDataSetImpl((MemoryDataSet<BIDataSetFieldInfo>)newmemSet);
        }
        catch (Exception e) {
            throw new BIDataSetException("\u5b57\u6bb5\u6dfb\u52a0\u5931\u8d25\uff0c" + e.getMessage(), e);
        }
    }

    @Override
    public void dump(Writer writer) throws IOException {
        this.dump(writer, true);
    }

    public void print(OutputStream stream, boolean isPrintTitle) throws IOException {
        try (OutputStreamWriter writer = new OutputStreamWriter(stream);){
            this.dump((Writer)writer, isPrintTitle);
        }
    }

    private void dump(Writer writer, int mode) throws IOException {
        if (mode == 1 || mode == 2) {
            for (Column column : this.dataset.getMetadata()) {
                String name = column.getName();
                if (name.equals("SYS_TIMEKEY") || name.equals("SYS_ROWNUM")) continue;
                if (column.getIndex() > 0) {
                    writer.write(",");
                }
                if (mode == 1) {
                    writer.write(this.tryQuoteString(column.getTitle()));
                    continue;
                }
                writer.write(this.tryQuoteString(column.getName()));
            }
            writer.write(StringUtils.LINE_SEPARATOR);
        }
        Iterator<BIDataRow> itor = this.iterator();
        int colNum = this.getMetadata().getColumnCount();
        while (itor.hasNext()) {
            BIDataRow row = itor.next();
            for (int i = 0; i < colNum; ++i) {
                String name = this.getMetadata().getColumn(i).getName();
                if (name.equals("SYS_TIMEKEY") || name.equals("SYS_ROWNUM")) continue;
                String value = com.jiuqi.bi.syntax.DataType.formatValue((int)0, (Object)row.getValue(i));
                if (i > 0) {
                    writer.write(",");
                }
                writer.write(this.tryQuoteString(value));
            }
            writer.write(StringUtils.LINE_SEPARATOR);
        }
    }

    public void dump(Writer writer, boolean isShowTitle) throws IOException {
        this.dump(writer, isShowTitle ? 1 : 0);
    }

    @Override
    public void dump(String fileName) throws IOException {
        try (FileOutputStream outStream = new FileOutputStream(fileName);
             OutputStreamWriter writer = new OutputStreamWriter(outStream);){
            this.dump(writer);
        }
    }

    public String toString() {
        StringWriter writer = new StringWriter();
        try {
            this.dump((Writer)writer, 2);
        }
        catch (IOException e) {
            return e.toString();
        }
        return writer.toString();
    }

    private String tryQuoteString(String s) {
        if (s == null) {
            return s;
        }
        if (s.indexOf(34) >= 0 || s.indexOf(44) >= 0) {
            return StringUtils.quote((String)s, (char)'\"');
        }
        return s;
    }

    @Override
    public void doCalcField(int[] colIdxes) throws BIDataSetException {
        ArrayList<Integer> cols = new ArrayList<Integer>();
        for (int i = 0; i < colIdxes.length; ++i) {
            cols.add(colIdxes[i]);
        }
        this.compute(cols);
    }

    @Override
    public BIDataSet selectFields(List<String> fields) throws BIDataSetException {
        Object v;
        ArrayList<Integer> showColList = new ArrayList<Integer>();
        Metadata metadata = this.dataset.getMetadata();
        Metadata newMetadata = new Metadata();
        List columns = metadata.getColumns();
        for (Column c : columns) {
            boolean isReserve = fields.contains(c.getName().toUpperCase());
            if (!isReserve) continue;
            Column newColumn = new Column(c.getName(), c.getDataType(), c.getTitle(), c.getInfo());
            newMetadata.addColumn(newColumn);
            showColList.add(c.getIndex());
        }
        Map properties = metadata.getProperties();
        List hiers = (List)properties.get("HIERARCHY");
        if (hiers != null) {
            ArrayList<DSHierarchy> newHiers = new ArrayList<DSHierarchy>();
            for (DSHierarchy hier : hiers) {
                if (hier.getType() == DSHierarchyType.PARENT_HIERARCHY) {
                    int col1 = metadata.indexOf(hier.getLevels().get(0));
                    int col2 = metadata.indexOf(hier.getParentFieldName());
                    if (!showColList.contains(col1) || !showColList.contains(col2)) continue;
                }
                newHiers.add(hier.clone());
            }
            newMetadata.getProperties().put("HIERARCHY", newHiers);
        }
        if ((v = properties.get("FiscalMonth")) != null) {
            newMetadata.getProperties().put("FiscalMonth", v);
        }
        MemoryDataSet newMemoryDataset = new MemoryDataSet(BIDataSetFieldInfo.class, newMetadata);
        for (BIDataRow row : this) {
            DataRow newRow = newMemoryDataset.add();
            for (int i = 0; i < showColList.size(); ++i) {
                Object value = row.getValue((Integer)showColList.get(i));
                newRow.setValue(i, value);
            }
        }
        TimeKeyBuilder.buildTimeKey((MemoryDataSet<BIDataSetFieldInfo>)newMemoryDataset);
        BIDataSetImpl newDs = new BIDataSetImpl((MemoryDataSet<BIDataSetFieldInfo>)newMemoryDataset);
        newDs.setParameterEnv(this.getEnhancedParameterEnv());
        newDs.logger = this.logger;
        return newDs;
    }

    @Override
    public BIDataSet aggregate(String[] dimList, String[] measureList, boolean isAutoFillParentColumn) throws BIDataSetException {
        return this.aggregate(Arrays.asList(dimList), Arrays.asList(measureList), isAutoFillParentColumn);
    }

    @Override
    public BIDataSet aggregate(String[] dimList, boolean isAutoFillParentColumn) throws BIDataSetException {
        return this.aggregate(Arrays.asList(dimList), isAutoFillParentColumn);
    }

    @Override
    public BIDataSet aggregate(String[] dimList) throws BIDataSetException {
        return this.aggregate(Arrays.asList(dimList));
    }

    @Override
    public BIDataSet aggregate(String[] dimList, String[] measureList) throws BIDataSetException {
        return this.aggregate(Arrays.asList(dimList), Arrays.asList(measureList));
    }

    @Override
    public IDSTreeItem createTree(DSHierarchy hierarchy, String retField) throws BIDataSetException {
        DSTreeBuilder builder;
        switch (hierarchy.getType()) {
            case CODE_HIERARCHY: {
                builder = new CodeTreeBuilder(this, hierarchy, retField);
                break;
            }
            case PARENT_HIERARCHY: {
                builder = new ParentTreeBuilder(this, hierarchy, retField);
                break;
            }
            case COLUMN_HIERARCHY: {
                builder = new ColumnTreeBuilder(this, hierarchy, retField);
                break;
            }
            default: {
                throw new BIDataSetException("\u672a\u652f\u6301\u7684\u6811\u5f62\u7c7b\u578b\uff1a" + (Object)((Object)hierarchy.getType()));
            }
        }
        return builder.build();
    }

    private void generateRownumColumn() {
        Metadata metadata = this.dataset.getMetadata();
        int pos = metadata.indexOf("SYS_ROWNUM");
        if (pos >= 0) {
            return;
        }
        Column<BIDataSetFieldInfo> column = this._buildRownumColumn();
        metadata.addColumn(column);
        pos = column.getIndex();
        metadata.getProperties().put("ROWNUM_INDEX", pos);
        Iterator iterator = this.dataset.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            DataRow row = (DataRow)iterator.next();
            row.setInt(pos, this.rowGetter.getOriginalIndex(index) + 1);
            ++index;
        }
    }

    private Column<BIDataSetFieldInfo> _buildRownumColumn() {
        Column column = new Column("SYS_ROWNUM", DataType.INTEGER.value(), "SYS_ROWNUM", null);
        BIDataSetFieldInfo info = new BIDataSetFieldInfo();
        info.setFieldType(FieldType.DESCRIPTION);
        info.setValType(DataType.INTEGER.value());
        info.setShowPattern("##");
        info.setKeyField("SYS_ROWNUM");
        info.setName("SYS_ROWNUM");
        info.setTitle("SYS_ROWNUM");
        column.setInfo((Object)info);
        return column;
    }

    private static final class ArrayOriginalRowGetter
    implements OriginalRowGetter {
        private int[] rowIndex;

        public ArrayOriginalRowGetter(BIDataSetImpl dataset, int[] rowIndex) {
            this.rowIndex = rowIndex;
        }

        @Override
        public int getOriginalIndex(int index) {
            return this.rowIndex[index];
        }

        @Override
        public int getRecordCount() {
            return this.rowIndex.length;
        }
    }

    private static final class DirectOriginalRowGetter
    implements OriginalRowGetter {
        private BIDataSetImpl dataset;
        private int size;

        public DirectOriginalRowGetter(BIDataSetImpl dataset) {
            this.dataset = dataset;
            this.size = this.dataset.dataset.size();
        }

        @Override
        public int getOriginalIndex(int index) {
            return index;
        }

        @Override
        public int getRecordCount() {
            return this.size;
        }
    }

    private static interface OriginalRowGetter {
        public int getRecordCount();

        public int getOriginalIndex(int var1);
    }
}

