/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.IndexItem
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.ICommonQuery
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.intf.IDataUpdator
 *  com.jiuqi.np.dataengine.intf.IFieldValueProcessor
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 */
package com.jiuqi.nr.jtable.dataset;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.IndexItem;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.ICommonQuery;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.IDataUpdator;
import com.jiuqi.np.dataengine.intf.IFieldValueProcessor;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.jtable.dataset.AbstractRegionRelationEvn;
import com.jiuqi.nr.jtable.exception.DataEngineQueryException;
import com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.CellQueryInfo;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import com.jiuqi.nr.jtable.service.IJtableDataEngineService;
import com.jiuqi.nr.jtable.service.impl.MeasureFieldValueProcessor;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.jtable.util.SortingMethod;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractRegionQueryTableStrategy {
    private static final Logger logger = LoggerFactory.getLogger(AbstractRegionQueryTableStrategy.class);
    protected IJtableDataEngineService jtableDataEngineService;
    protected IDataQuery dataQuery;
    protected ExecutorContext context;
    protected AbstractRegionRelationEvn regionRelationEvn;
    protected RegionQueryInfo regionQueryInfo;
    protected List<String> cells = new ArrayList<String>();
    protected StringBuffer filterBuf = new StringBuffer();
    public static String firstItem = "firstitem";
    public double maxFloatOrder = -1.0;
    public boolean needBalance = false;
    protected IRuntimeDataSchemeService runtimeDataSchemeService;

    public AbstractRegionQueryTableStrategy(IDataQuery dataQuery, AbstractRegionRelationEvn regionRelationEvn, RegionQueryInfo regionQueryInfo) {
        this.jtableDataEngineService = (IJtableDataEngineService)BeanUtil.getBean(IJtableDataEngineService.class);
        JtableContext jtableContext = new JtableContext(regionQueryInfo.getContext());
        this.dataQuery = dataQuery;
        this.context = this.jtableDataEngineService.getExecutorContext(jtableContext);
        if (jtableContext.getMeasureMap() != null && !jtableContext.getMeasureMap().isEmpty() && this.context.getEnv() instanceof ReportFmlExecEnvironment) {
            ReportFmlExecEnvironment environment = (ReportFmlExecEnvironment)this.context.getEnv();
            MeasureFieldValueProcessor measureProcessor = new MeasureFieldValueProcessor(regionRelationEvn, jtableContext);
            if (measureProcessor.getMultiplier(null) != 1.0) {
                this.needBalance = true;
            }
            environment.setFieldValueProcessor((IFieldValueProcessor)measureProcessor);
        }
        this.regionRelationEvn = regionRelationEvn;
        this.regionQueryInfo = regionQueryInfo;
        this.runtimeDataSchemeService = (IRuntimeDataSchemeService)BeanUtil.getBean(IRuntimeDataSchemeService.class);
        this.initDataQuery();
    }

    public IReadonlyTable getRegionQueryTable() {
        if (this.regionRelationEvn.isPaginate()) {
            this.addPageInfo();
        }
        IReadonlyTable readonlyTable = null;
        try {
            readonlyTable = this.dataQuery.executeReader(this.context);
        }
        catch (Exception e) {
            DimensionValueSet masterKeys = this.dataQuery.getMasterKeys();
            logger.error("\u67e5\u8be2\u51fa\u9519\uff0c\u5f53\u524d\u7ef4\u5ea6\uff1a" + masterKeys.toString() + ";\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            throw new DataEngineQueryException(JtableExceptionCodeCost.DATAENGINE_QUERY_EXCEPTION, new String[]{"\u67e5\u8be2\u51fa\u9519\uff0c\u5f53\u524d\u7ef4\u5ea6\uff1a" + masterKeys.toString()});
        }
        return readonlyTable;
    }

    public IReadonlyTable getRegionLocatQueryTable(String floatOrder, int offset) {
        return null;
    }

    public IReadonlyTable getRegionLocatQueryTable(DimensionValueSet locateDimensionValueSet) {
        return null;
    }

    public IndexItem getRowIndex(DimensionValueSet locatDimensionValueSet) {
        return null;
    }

    public double getMaxFloatOrder() {
        if (this.maxFloatOrder > 0.0) {
            this.maxFloatOrder += 1000.0;
        } else {
            JtableContext jtableContext = this.regionQueryInfo.getContext();
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(jtableContext.getDimensionSet());
            IDataQuery floatOrderDataQuery = this.jtableDataEngineService.getDataQuery(jtableContext, this.regionRelationEvn.getRegionData().getKey());
            floatOrderDataQuery.setMasterKeys(dimensionValueSet);
            FieldData floatOrderField = this.regionRelationEvn.getFloatOrderFields().get(0);
            int flortOrderColumn = this.jtableDataEngineService.addQueryColumn((ICommonQuery)floatOrderDataQuery, floatOrderField.getFieldKey());
            this.jtableDataEngineService.addOrderByItem((ICommonQuery)floatOrderDataQuery, floatOrderField.getFieldKey(), true);
            floatOrderDataQuery.setPagingInfo(1, 0);
            IReadonlyTable readonlyTable = null;
            try {
                readonlyTable = floatOrderDataQuery.executeReader(this.context);
            }
            catch (Exception e) {
                DimensionValueSet masterKeys = floatOrderDataQuery.getMasterKeys();
                logger.error("\u67e5\u8be2\u51fa\u9519\uff0c\u5f53\u524d\u7ef4\u5ea6\uff1a" + masterKeys.toString() + ";\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                throw new DataEngineQueryException(JtableExceptionCodeCost.DATAENGINE_QUERY_EXCEPTION, new String[]{e.getMessage()});
            }
            if (readonlyTable.getCount() > 0) {
                IDataRow dataRow = readonlyTable.getItem(0);
                try {
                    AbstractData fieldValue = dataRow.getValue(flortOrderColumn);
                    this.maxFloatOrder = fieldValue.getAsFloat() + 1000.0;
                }
                catch (DataTypeException e) {
                    logger.error("\u67e5\u8be2\u51fa\u9519\uff0c\u5f53\u524d\u7ef4\u5ea6\uff1a" + dataRow.getRowDimensions().toString() + ";\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    throw new DataEngineQueryException(JtableExceptionCodeCost.DATAENGINE_QUERY_EXCEPTION, new String[]{"\u83b7\u53d6\u6307\u6807\u6570\u636e\u51fa\u9519"});
                }
            } else {
                this.maxFloatOrder = 1000.0;
            }
        }
        return this.maxFloatOrder;
    }

    public IDataTable getRegionModifyTable() {
        IDataTable dataTable = null;
        try {
            this.dataQuery.setStatic(false);
            dataTable = this.dataQuery.executeQuery(this.context);
        }
        catch (Exception e) {
            String contextStr = DimensionValueSetUtil.getContextStr(this.regionQueryInfo.getContext(), this.regionQueryInfo.getRegionKey(), null);
            String errorStr = "\u67e5\u8be2\u51fa\u9519\uff0c\u5f53\u524d\u73af\u5883\u4e3a" + contextStr + ";\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage();
            logger.error(errorStr, e);
            throw new DataEngineQueryException(JtableExceptionCodeCost.DATAENGINE_QUERY_EXCEPTION, new String[]{errorStr, e.getMessage()});
        }
        return dataTable;
    }

    public IDataUpdator getRegionModifyUpdator() {
        IDataUpdator dataUpdator = null;
        try {
            dataUpdator = this.dataQuery.openForUpdate(this.context);
        }
        catch (Exception e) {
            String contextStr = DimensionValueSetUtil.getContextStr(this.regionQueryInfo.getContext(), this.regionQueryInfo.getRegionKey(), null);
            String errorStr = "\u67e5\u8be2\u51fa\u9519\uff0c\u5f53\u524d\u73af\u5883\u4e3a" + contextStr + ";\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage();
            logger.error(errorStr, e);
            throw new DataEngineQueryException(JtableExceptionCodeCost.DATAENGINE_QUERY_EXCEPTION, new String[]{errorStr, e.getMessage()});
        }
        return dataUpdator;
    }

    public abstract void clearRegionTable();

    protected void initDataQuery() {
        this.addRegionFilter();
        this.addQueryColumn();
        this.addOrderColumn();
        if (this.filterBuf.length() > 0) {
            this.dataQuery.setRowFilter(this.filterBuf.toString());
        }
    }

    protected abstract void addQueryColumn();

    protected void addRegionFilter() {
    }

    protected void addPageInfo() {
    }

    protected void addOrderColumn() {
    }

    protected void addColumnFilter(String dataLinkKey, int columnIndex) {
        if (this.regionQueryInfo != null && this.regionQueryInfo.getFilterInfo().getCellQuerys() != null) {
            List<CellQueryInfo> cellQueryInfos = this.regionQueryInfo.getFilterInfo().getCellQuerys();
            for (CellQueryInfo cellQueryInfo : cellQueryInfos) {
                if (!dataLinkKey.equals(cellQueryInfo.getCellKey())) continue;
                SortingMethod sortingMe = new SortingMethod();
                StringBuffer cellFilterBuf = new StringBuffer();
                FieldData fieldData = this.regionRelationEvn.getFieldByDataLink(dataLinkKey);
                fieldData.setDataLinkKey(dataLinkKey);
                cellFilterBuf = sortingMe.sortingMethod(cellQueryInfo, fieldData, (ICommonQuery)this.dataQuery, columnIndex, this.regionQueryInfo.getContext());
                if (StringUtils.isNotEmpty((String)cellQueryInfo.getShortcuts()) && cellQueryInfo.getShortcuts().contains(SortingMethod.topTen)) {
                    this.regionRelationEvn.setPaginate(false);
                }
                if (this.filterBuf.length() == 0) {
                    this.filterBuf.append(cellFilterBuf);
                    break;
                }
                if (this.filterBuf.length() == 0 || cellFilterBuf.length() == 0) break;
                this.filterBuf.append(" AND " + cellFilterBuf);
                break;
            }
        }
    }

    public List<String> getCells() {
        return this.cells;
    }

    public RegionQueryInfo getRegionQueryInfo() {
        return this.regionQueryInfo;
    }

    public void setEnumFilledQuery(List<FieldDefine> enumFields, List<List<String>> allEntityData) {
        this.dataQuery.setFilledEnumLinks(enumFields, allEntityData);
    }
}

