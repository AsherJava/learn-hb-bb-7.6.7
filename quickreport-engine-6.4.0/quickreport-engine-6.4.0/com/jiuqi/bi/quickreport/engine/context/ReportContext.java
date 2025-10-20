/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataSet
 *  com.jiuqi.bi.dataset.BIDataSetFieldInfo
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.parameter.engine.EnhancedParameterEnvAdapter
 *  com.jiuqi.bi.parameter.engine.IParameterEnv
 *  com.jiuqi.bi.syntax.context.IConfigurableContext
 *  com.jiuqi.bi.syntax.context.IUserBindingContext
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 */
package com.jiuqi.bi.quickreport.engine.context;

import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.parameter.engine.EnhancedParameterEnvAdapter;
import com.jiuqi.bi.parameter.engine.IParameterEnv;
import com.jiuqi.bi.quickreport.ReportConfig;
import com.jiuqi.bi.quickreport.engine.IReportListener;
import com.jiuqi.bi.quickreport.engine.build.expanding.AxisDataNode;
import com.jiuqi.bi.quickreport.engine.context.EvalCellInfo;
import com.jiuqi.bi.quickreport.engine.context.ParseContext;
import com.jiuqi.bi.quickreport.engine.context.ReportContextException;
import com.jiuqi.bi.quickreport.engine.context.ReportNotFoundException;
import com.jiuqi.bi.quickreport.engine.context.cache.DSPagingInfo;
import com.jiuqi.bi.quickreport.engine.context.cache.DataSetCachePool;
import com.jiuqi.bi.quickreport.engine.context.filter.IFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.context.selection.CellSelection;
import com.jiuqi.bi.quickreport.engine.parser.IDataSetModelProvider;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpressionException;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DSFieldNode;
import com.jiuqi.bi.quickreport.engine.workbook.ReportWorkbook;
import com.jiuqi.bi.quickreport.model.QuickReport;
import com.jiuqi.bi.quickreport.model.WorksheetModel;
import com.jiuqi.bi.syntax.context.IConfigurableContext;
import com.jiuqi.bi.syntax.context.IUserBindingContext;
import com.jiuqi.bi.util.ArrayKey;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class ReportContext
extends ParseContext
implements IUserBindingContext,
IConfigurableContext {
    private QuickReport report;
    private DataSetCachePool cache;
    private ReportWorkbook workbook;
    private CellSelection pinyinOrdering;
    private Map<String, String> config;
    private List<IFilterDescriptor> currentFilters;
    private EvalCellInfo currentCell;
    private Deque<AxisDataNode> currentRestrictions;
    private Map<String, List<IFilterDescriptor>> dataSetFilters;
    private boolean formattable;
    private Locale locale;
    private int cellSize;

    public ReportContext(QuickReport report) {
        this.report = report;
        this.currentFilters = new ArrayList<IFilterDescriptor>();
        this.currentRestrictions = new ArrayDeque<AxisDataNode>();
        this.pinyinOrdering = new CellSelection();
        this.config = new HashMap<String, String>();
        this.cache = new DataSetCachePool();
        this.cache.setReport(report);
        this.cache.setRefFields(this.refFields);
        this.dataSetFilters = new HashMap<String, List<IFilterDescriptor>>();
        this.cache.setDataSetFilter(this.dataSetFilters::get);
        this.formattable = true;
        this.cellSize = report.getWorksheets().stream().map(WorksheetModel::getGriddata).mapToInt(g -> (g.getColCount() - 1) * (g.getRowCount() - 1)).sum();
    }

    public QuickReport getReport() {
        return this.report;
    }

    public com.jiuqi.nvwa.framework.parameter.IParameterEnv getParamEnv() {
        return this.cache.getParamEnv();
    }

    @Deprecated
    public void setParamEnv(IParameterEnv paramEnv) {
        this.setParamEnv((com.jiuqi.nvwa.framework.parameter.IParameterEnv)new EnhancedParameterEnvAdapter(paramEnv));
    }

    public void setParamEnv(com.jiuqi.nvwa.framework.parameter.IParameterEnv paramEnv) {
        this.cache.setParamEnv(paramEnv);
    }

    public Map<String, String> getConfig() {
        return this.config;
    }

    public String getLanguage() {
        return this.cache.getLanguage();
    }

    public void setLanguage(String language) {
        this.cache.setLanguage(language);
        this.locale = null;
    }

    public Locale getLocale() {
        if (this.locale == null) {
            String i18nLang = this.cache.getLanguage();
            this.locale = i18nLang == null || i18nLang.isEmpty() ? Locale.getDefault() : Locale.forLanguageTag(i18nLang.replace('_', '-'));
        }
        return this.locale;
    }

    @Deprecated
    public int getPageNum() {
        return this.cache.getPageNum();
    }

    @Deprecated
    public void setPageNum(int pageNum) {
        this.cache.setPageNum(pageNum);
    }

    public void setPageNums(Map<String, Integer> pageNums) {
        this.cache.setPageNums(pageNums == null ? Collections.emptyMap() : pageNums);
    }

    public String getUserID() {
        return this.cache.getUserID();
    }

    public void setUserID(String userID) {
        this.cache.setUserID(userID);
    }

    public ReportWorkbook getWorkbook() {
        return this.workbook;
    }

    public void setWorkbook(ReportWorkbook workbook) {
        this.workbook = workbook;
    }

    public IDataSetModelProvider getDataSetProvider() {
        return this.cache.getDataSetProvider();
    }

    public void setDataSetProvider(IDataSetModelProvider provider) {
        this.cache.setDataSetProvider(provider);
    }

    @Deprecated
    public int getPageSize() throws ReportContextException {
        return this.cache.getPageSize();
    }

    @Deprecated
    public int getRecordSize() throws ReportContextException {
        return this.cache.getRecordSize();
    }

    public Map<String, DSPagingInfo> getPagingInfos() throws ReportContextException {
        return this.cache.getPagingInfos();
    }

    public DSPagingInfo findPagingInfo(String dsName) throws ReportContextException {
        return this.cache.findPagingInfo(dsName);
    }

    public void validatePagingInfos(Collection<String> pagingDSNames) {
        this.cache.invalidatePagingInfos(pagingDSNames);
    }

    public Map<String, List<IFilterDescriptor>> getDataSetFilters() {
        return this.dataSetFilters;
    }

    public DSModel openDataSetModel(String dsName) throws ReportContextException, ReportNotFoundException {
        return this.cache.openModel(dsName);
    }

    public BIDataSet openDataSet(String dsName) throws ReportContextException {
        return this.cache.open(dsName);
    }

    public BIDataSet openDataSetWithoutParent(String dsName) throws ReportContextException {
        return this.cache.openNonParent(dsName);
    }

    public BIDataSet openDataSet(String dsName, List<IFilterDescriptor> filters) throws ReportContextException {
        return this.cache.open(dsName, filters);
    }

    public BIDataSet aggregateByTree(String dataDSName, List<String> dimDSNames) throws ReportContextException {
        return this.cache.aggregateByTree(dataDSName, dimDSNames);
    }

    public BIDataSet aggregateDataSet(String dsName, List<IFilterDescriptor> filters) throws ReportContextException {
        return this.cache.openCacheA(dsName, filters);
    }

    public MemoryDataSet<BIDataSetFieldInfo> distinct(DSModel dsModel, List<String> fieldNames, List<IFilterDescriptor> filters) throws ReportContextException {
        return this.cache.distinct(dsModel, fieldNames, filters);
    }

    public void clearCache(String dataSetName) {
        this.cache.clearCache(dataSetName);
    }

    public void clearCache() {
        this.cache.clearCache();
    }

    public IReportListener getListener() {
        return this.cache.getListener();
    }

    public void setListener(IReportListener listener) {
        this.cache.setListener(listener);
    }

    public boolean isExpandingNulls() {
        String expandingNulls = (String)this.report.getProperties().get("SYS_EXPANDING_NULLS");
        return "1".equals(expandingNulls);
    }

    public CellSelection getPinYinOrdering() {
        return this.pinyinOrdering;
    }

    public void resetPrimaryFields(List<DSFieldNode> primaryFields) {
        this.cache.getPrimaryKeys().clear();
        if (primaryFields != null) {
            for (DSFieldNode field : primaryFields) {
                ArrayKey key = ArrayKey.of((Object[])new Object[]{field.getDataSet().getName(), field.getField().getName()});
                this.cache.getPrimaryKeys().add(key);
            }
        }
    }

    public List<IFilterDescriptor> getCurrentFilters() {
        return this.currentFilters;
    }

    public void pushCurrentFilters(List<IFilterDescriptor> filters) {
        this.currentFilters.addAll(filters);
    }

    public void popCurrentFilters(List<IFilterDescriptor> filters) throws ReportContextException {
        for (int i = filters.size() - 1; i >= 0; --i) {
            IFilterDescriptor removed = this.currentFilters.remove(this.currentFilters.size() - 1);
            if (removed == filters.get(i)) continue;
            throw new ReportContextException("\u5904\u7406\u5f53\u524d\u8fc7\u6ee4\u6761\u4ef6\u9519\u8bef\uff0c\u5185\u5b58\u5bf9\u8c61\u5b58\u5728\u4e0d\u4e00\u81f4\u7684\u95ee\u9898\u3002");
        }
    }

    public EvalCellInfo getCurrentCell() {
        return this.currentCell;
    }

    public void setCurrentCell(EvalCellInfo cell) {
        this.currentCell = cell;
    }

    public Deque<AxisDataNode> getCurrentRestrictions() {
        return this.currentRestrictions;
    }

    public boolean isExcelMode() {
        return this.report.isExcelMode();
    }

    public void snapshot() throws ReportContextException {
        this.cache.snapshot();
    }

    public boolean refresh() throws ReportContextException {
        return this.cache.refresh();
    }

    public void resetDataSource(String dsName, String dataSrcName) {
        this.cache.resetDataSource(dsName, dataSrcName);
    }

    public boolean isFormattable() {
        return this.formattable;
    }

    public void setFormattable(boolean formattable) {
        this.formattable = formattable;
    }

    public CellSelection getTracingCells() throws ReportExpressionException {
        String traceCellFilters = this.config.get("tracing.cells");
        if (traceCellFilters == null) {
            traceCellFilters = this.report.getProperties().getProperty("tracing.cells");
        }
        CellSelection traceCells = new CellSelection();
        traceCells.parse(this, traceCellFilters);
        return traceCells;
    }

    public void estimateCells(int expandingSize) throws ReportContextException {
        this.cellSize += expandingSize;
        if (this.cellSize > ReportConfig.getMaxCellSize()) {
            throw new ReportContextException("\u5206\u6790\u8868\u751f\u6210\u8868\u683c\u8fc7\u5927\uff0c\u65e0\u6cd5\u5b8c\u6210\u5904\u7406\uff0c\u8bf7\u8c03\u6574\u67e5\u8be2\u6570\u636e\u91cf\u540e\u91cd\u8bd5");
        }
    }
}

