/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.ICommonQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IGroupingQuery
 *  com.jiuqi.np.dataengine.intf.IGroupingTable
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.nr.basedata.select.service.IBaseDataSelectFilter
 *  com.jiuqi.nr.datacrud.IDataValue
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.IRegionDataSet
 *  com.jiuqi.nr.datacrud.IRowData
 *  com.jiuqi.nr.datacrud.QueryInfoBuilder
 *  com.jiuqi.nr.datacrud.spi.RowFilter
 *  com.jiuqi.nr.datacrud.spi.filter.FormulaFilter
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.snapshot.service.DataOperationService
 */
package com.jiuqi.nr.jtable.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.ICommonQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IGroupingQuery;
import com.jiuqi.np.dataengine.intf.IGroupingTable;
import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.nr.basedata.select.service.IBaseDataSelectFilter;
import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.IRegionDataSet;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.QueryInfoBuilder;
import com.jiuqi.nr.datacrud.spi.RowFilter;
import com.jiuqi.nr.datacrud.spi.filter.FormulaFilter;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.jtable.common.LinkType;
import com.jiuqi.nr.jtable.exception.DataEngineQueryException;
import com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.input.CellQueryInfo;
import com.jiuqi.nr.jtable.params.input.CellValueQueryInfo;
import com.jiuqi.nr.jtable.service.IJtableDataEngineService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DataCrudUtil;
import com.jiuqi.nr.jtable.util.DataFormaterCache;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.jtable.util.FilterMethod;
import com.jiuqi.nr.jtable.util.SortingMethod;
import com.jiuqi.nr.snapshot.service.DataOperationService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseDataEntryFilter
implements IBaseDataSelectFilter {
    private static final Logger logger = LoggerFactory.getLogger(BaseDataEntryFilter.class);
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IJtableDataEngineService jtableDataEngineService;
    @Autowired
    private DataOperationService dataOperationService;
    private List<String> entityKeyDatas = new ArrayList<String>();
    public static final String FILTER_NAME = "ENTRY_FILTER";

    public String getFilterName() {
        return FILTER_NAME;
    }

    public void initFilterParams(Object params) {
        List<CellQueryInfo> cellQueryInfos;
        if (this.entityKeyDatas != null && this.entityKeyDatas.size() > 0) {
            this.entityKeyDatas.clear();
        }
        if (params == null) {
            return;
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String jsonStr = (String)params;
        CellValueQueryInfo cellValueQueryInfo = null;
        try {
            cellValueQueryInfo = (CellValueQueryInfo)mapper.readValue(jsonStr, CellValueQueryInfo.class);
        }
        catch (JsonProcessingException e) {
            logger.error("\u6392\u5e8f\u8fc7\u6ee4\u5668json\u8f6c\u5bf9\u8c61\u5931\u8d25\uff1a" + e.getMessage(), e);
        }
        if (cellValueQueryInfo == null) {
            return;
        }
        LinkData link = this.jtableParamService.getLink(cellValueQueryInfo.getCellKey());
        if (link.getType() != LinkType.LINK_TYPE\uff3fENUM.getValue()) {
            return;
        }
        JtableContext jtableContext = cellValueQueryInfo.getContext();
        DataFormaterCache dataFormaterCache = new DataFormaterCache(jtableContext);
        dataFormaterCache.jsonData();
        RegionData region = this.jtableParamService.getRegion(link.getRegionKey());
        IGroupingQuery groupingQuery = this.jtableDataEngineService.getGroupingQuery(jtableContext, link.getRegionKey());
        FieldData field = this.jtableParamService.getField(link.getZbid());
        int columnIndex = this.jtableDataEngineService.addQueryColumn((ICommonQuery)groupingQuery, field.getFieldKey());
        groupingQuery.addGroupColumn(columnIndex);
        StringBuffer filterBuf = new StringBuffer();
        if (StringUtils.isNotEmpty((String)cellValueQueryInfo.getFilter())) {
            filterBuf.append(" (" + cellValueQueryInfo.getFilter() + ") ");
        }
        if (StringUtils.isNotEmpty((String)region.getFilterCondition())) {
            if (filterBuf.length() != 0) {
                filterBuf.append(" AND ");
            }
            filterBuf.append(" (" + region.getFilterCondition() + ") ");
        }
        if ((cellQueryInfos = cellValueQueryInfo.getCells()) != null && cellQueryInfos.size() > 0) {
            SortingMethod sortingMe = new SortingMethod();
            for (CellQueryInfo cellQueryInfo : cellQueryInfos) {
                String dataLinkKey = cellQueryInfo.getCellKey();
                LinkData queryLink = this.jtableParamService.getLink(dataLinkKey);
                FieldData queryField = this.jtableParamService.getField(queryLink.getZbid());
                int queryColumnIndex = this.jtableDataEngineService.addQueryColumn((ICommonQuery)groupingQuery, queryField.getFieldKey());
                groupingQuery.setGatherType(queryColumnIndex, FieldGatherType.FIELD_GATHER_MIN);
                queryField.setDataLinkKey(dataLinkKey);
                StringBuffer cellFilterBuf = sortingMe.sortingMethod(cellQueryInfo, queryField, (ICommonQuery)groupingQuery, queryColumnIndex, jtableContext);
                if (filterBuf.length() == 0) {
                    filterBuf.append(cellFilterBuf);
                    continue;
                }
                if (filterBuf.length() == 0 || cellFilterBuf.length() == 0) continue;
                filterBuf.append(" AND " + cellFilterBuf);
            }
        }
        if (filterBuf.length() > 0) {
            groupingQuery.setRowFilter(filterBuf.toString());
        }
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(jtableContext);
        groupingQuery.setMasterKeys(dimensionValueSet);
        JtableContext jContext = new JtableContext(jtableContext);
        ExecutorContext executorContext = this.jtableDataEngineService.getExecutorContext(jContext);
        IGroupingTable readonlyTable = null;
        if (jtableContext.getDimensionSet().containsKey("DATASNAPSHOTID")) {
            int i;
            QueryInfoBuilder queryInfoBuilder = DataCrudUtil.getBaseQueryInfoBuilder(jtableContext, region.getKey(), null);
            queryInfoBuilder.select(cellValueQueryInfo.getCellKey());
            queryInfoBuilder.groupBy(cellValueQueryInfo.getCellKey());
            List<CellQueryInfo> cellQueryInfoList = cellValueQueryInfo.getCells();
            if (cellQueryInfoList != null && cellQueryInfoList.size() > 0) {
                FilterMethod filterMethod = new FilterMethod();
                for (CellQueryInfo cellQueryInfo : cellQueryInfoList) {
                    String otherLinkKey = cellQueryInfo.getCellKey();
                    LinkData otherLink = this.jtableParamService.getLink(otherLinkKey);
                    queryInfoBuilder.select(otherLinkKey);
                    FieldData otherField = this.jtableParamService.getField(otherLink.getZbid());
                    otherField.setDataLinkKey(otherLinkKey);
                    filterMethod.filterMethod(cellQueryInfo, queryInfoBuilder, jtableContext, otherField);
                }
            }
            if (!filterBuf.toString().isEmpty()) {
                FormulaFilter formulaFilter = new FormulaFilter(filterBuf.toString());
                queryInfoBuilder.where((RowFilter)formulaFilter);
            }
            IRegionDataSet iRegionDataSet = this.dataOperationService.querySanpshotCellData(queryInfoBuilder.build(), jtableContext.getDimensionSet().get("DATASNAPSHOTID").getValue());
            int queryIndex = 0;
            for (i = 0; i < iRegionDataSet.getMetaData().size(); ++i) {
                if (!((IMetaData)iRegionDataSet.getMetaData().get(i)).getDataField().getKey().equals(field.getFieldKey())) continue;
                queryIndex = i;
            }
            for (i = 0; i < iRegionDataSet.getRowCount(); ++i) {
                IRowData dataRow = (IRowData)iRegionDataSet.getRowData().get(i);
                AbstractData fieldValue = null;
                try {
                    fieldValue = ((IDataValue)dataRow.getLinkDataValues().get(queryIndex)).getAbstractData();
                }
                catch (DataTypeException e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    throw new DataEngineQueryException(JtableExceptionCodeCost.DATAENGINE_QUERY_EXCEPTION, new String[]{"\u83b7\u53d6\u6307\u6807\u6570\u636e\u51fa\u9519"});
                }
                Object value = link.getFormatData(fieldValue, dataFormaterCache, jtableContext);
                if (StringUtils.isNotEmpty((String)cellValueQueryInfo.getFuzzyValue()) && !link.checkFuzzyValue(fieldValue, value, dataFormaterCache, cellValueQueryInfo.getFuzzyValue()) || fieldValue.isNull || this.entityKeyDatas.contains(fieldValue.getAsString())) continue;
                this.entityKeyDatas.add(fieldValue.getAsString());
            }
        } else {
            try {
                readonlyTable = groupingQuery.executeReader(executorContext);
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                throw new DataEngineQueryException(JtableExceptionCodeCost.DATAENGINE_QUERY_EXCEPTION, new String[]{"\u5355\u5143\u683c\u679a\u4e3e\u67e5\u8be2\u51fa\u9519"});
            }
            for (int i = 0; i < readonlyTable.getCount(); ++i) {
                IDataRow dataRow = readonlyTable.getItem(i);
                AbstractData fieldValue = null;
                try {
                    fieldValue = dataRow.getValue(columnIndex);
                }
                catch (DataTypeException e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    throw new DataEngineQueryException(JtableExceptionCodeCost.DATAENGINE_QUERY_EXCEPTION, new String[]{"\u83b7\u53d6\u6307\u6807\u6570\u636e\u51fa\u9519"});
                }
                Object value = link.getFormatData(fieldValue, dataFormaterCache, jtableContext);
                if (StringUtils.isNotEmpty((String)cellValueQueryInfo.getFuzzyValue()) && !link.checkFuzzyValue(fieldValue, value, dataFormaterCache, cellValueQueryInfo.getFuzzyValue()) || fieldValue.isNull || this.entityKeyDatas.contains(fieldValue.getAsString())) continue;
                this.entityKeyDatas.add(fieldValue.getAsString());
            }
        }
    }

    public boolean accept(IEntityRow entityRow) {
        if (this.entityKeyDatas == null || this.entityKeyDatas.size() == 0) {
            return false;
        }
        return this.entityKeyDatas.contains(entityRow.getEntityKeyData());
    }

    public List<String> getEntryList() {
        return this.entityKeyDatas;
    }
}

