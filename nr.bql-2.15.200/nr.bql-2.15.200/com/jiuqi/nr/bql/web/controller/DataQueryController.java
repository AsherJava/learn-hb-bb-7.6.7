/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.cache.TableCache
 *  com.jiuqi.bi.adhoc.cache.graph.TableGraph
 *  com.jiuqi.bi.adhoc.cache.graph.TableNode
 *  com.jiuqi.bi.adhoc.datasource.reader.DataField
 *  com.jiuqi.bi.adhoc.datasource.reader.DataOrderBy
 *  com.jiuqi.bi.adhoc.datasource.reader.DataPage
 *  com.jiuqi.bi.adhoc.datasource.reader.DataQuery
 *  com.jiuqi.bi.adhoc.datasource.reader.DataTable
 *  com.jiuqi.bi.adhoc.datasource.reader.IDataSourceReader
 *  com.jiuqi.bi.adhoc.datasource.reader.IReadContext
 *  com.jiuqi.bi.adhoc.datasource.reader.ReadContext
 *  com.jiuqi.bi.adhoc.engine.AdHocEngineException
 *  com.jiuqi.bi.adhoc.engine.IDataListener
 *  com.jiuqi.bi.adhoc.model.FieldInfo
 *  com.jiuqi.bi.adhoc3.extend.DataFieldDTO
 *  com.jiuqi.bi.adhoc3.extend.DataOrderByDTO
 *  com.jiuqi.bi.adhoc3.extend.DataPageDTO
 *  com.jiuqi.bi.adhoc3.extend.DataQueryDTO
 *  com.jiuqi.bi.adhoc3.extend.DataQueryWrapper
 *  com.jiuqi.bi.adhoc3.extend.DataTableDTO
 *  com.jiuqi.bi.adhoc3.extend.ReadContextDTO
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.dataset.WritingDataSet
 *  com.jiuqi.bi.query.result.ColumnInfo
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody
 */
package com.jiuqi.nr.bql.web.controller;

import com.jiuqi.bi.adhoc.cache.TableCache;
import com.jiuqi.bi.adhoc.cache.graph.TableGraph;
import com.jiuqi.bi.adhoc.cache.graph.TableNode;
import com.jiuqi.bi.adhoc.datasource.reader.DataField;
import com.jiuqi.bi.adhoc.datasource.reader.DataOrderBy;
import com.jiuqi.bi.adhoc.datasource.reader.DataPage;
import com.jiuqi.bi.adhoc.datasource.reader.DataQuery;
import com.jiuqi.bi.adhoc.datasource.reader.DataTable;
import com.jiuqi.bi.adhoc.datasource.reader.IDataSourceReader;
import com.jiuqi.bi.adhoc.datasource.reader.IReadContext;
import com.jiuqi.bi.adhoc.datasource.reader.ReadContext;
import com.jiuqi.bi.adhoc.engine.AdHocEngineException;
import com.jiuqi.bi.adhoc.engine.IDataListener;
import com.jiuqi.bi.adhoc.model.FieldInfo;
import com.jiuqi.bi.adhoc3.extend.DataFieldDTO;
import com.jiuqi.bi.adhoc3.extend.DataOrderByDTO;
import com.jiuqi.bi.adhoc3.extend.DataPageDTO;
import com.jiuqi.bi.adhoc3.extend.DataQueryDTO;
import com.jiuqi.bi.adhoc3.extend.DataQueryWrapper;
import com.jiuqi.bi.adhoc3.extend.DataTableDTO;
import com.jiuqi.bi.adhoc3.extend.ReadContextDTO;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.WritingDataSet;
import com.jiuqi.bi.query.result.ColumnInfo;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.bql.datasource.DataSchemeDataSource;
import com.jiuqi.nr.bql.dsv.adapter.DSVAdapter;
import com.jiuqi.nr.bql.web.DSVErrorEnum;
import com.jiuqi.nr.bql.web.convert.DateConverter;
import com.jiuqi.nr.bql.web.convert.IDataConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@JQRestController
@RequestMapping(value={"api/v1/zbquery-engine/dsv/"})
@Api(tags={"\u62a5\u8868\u6570\u636e\u65b9\u6848\u67e5\u8be2\u6267\u884c\u670d\u52a1"})
public class DataQueryController {
    @Autowired
    private DataSchemeDataSource dataSchemeDataSource;

    @ApiOperation(value="\u83b7\u53d6\u62a5\u8868\u6570\u636e\u65b9\u6848\u67e5\u8be2\u7ed3\u679c")
    @PostMapping(value={"query/result"})
    public StreamingResponseBody query(@RequestBody DataQueryWrapper wrapper) throws JQException {
        try {
            NpContext npContext = NpContextHolder.getContext();
            return outputStream -> {
                NpContextHolder.setContext((NpContext)npContext);
                try {
                    IDataSourceReader reader = this.dataSchemeDataSource.createDataReader(null);
                    DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                    WritingDataSet dataSet = new WritingDataSet((OutputStream)dataOutputStream, ColumnInfo.class);
                    StreamingDataListener dataListener = new StreamingDataListener((WritingDataSet<ColumnInfo>)dataSet);
                    HashMap<String, DataTable> dataTableMap = new HashMap<String, DataTable>();
                    IReadContext readContext = this.createReadContext(wrapper.getReadContext());
                    DataQuery dataQuery = this.createDataQuery(dataTableMap, wrapper.getDataQuery());
                    DataPage dataPage = this.createDataPage(dataTableMap, wrapper.getDataPage());
                    int totalCount = (int)reader.readQuery(readContext, dataQuery, (IDataListener)dataListener, dataPage);
                    dataOutputStream.writeInt(totalCount);
                }
                catch (Exception e) {
                    throw new IOException(e.getMessage(), e);
                }
                finally {
                    NpContextHolder.clearContext();
                }
            };
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)DSVErrorEnum.DSV_QUERY, (Throwable)e);
        }
    }

    @ApiOperation(value="\u83b7\u53d6\u62a5\u8868\u6570\u636e\u65b9\u6848\u67e5\u8be2\u6240\u652f\u6301\u7684\u9009\u9879")
    @PostMapping(value={"query/options"})
    public int getOptions(@RequestBody DataQueryWrapper wrapper) throws JQException {
        try {
            IDataSourceReader reader = this.dataSchemeDataSource.createDataReader(null);
            return reader.getReadOptions(this.createReadContext(wrapper.getReadContext()), this.createDataQuery(new HashMap<String, DataTable>(), wrapper.getDataQuery()));
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)DSVErrorEnum.DSV_OPTIONS, (Throwable)e);
        }
    }

    private IReadContext createReadContext(ReadContextDTO readContextDTO) throws AdHocEngineException {
        ReadContext readContext = new ReadContext(new HashMap(), readContextDTO.getOptions());
        readContext.setUserID(readContextDTO.getUserId());
        readContext.setCache(TableCache.getGlobal());
        readContext.getHierarchies().addAll(readContextDTO.getHierarchies());
        readContext.setLogger(DSVAdapter.getLogger());
        return readContext;
    }

    private DataQuery createDataQuery(Map<String, DataTable> dataTableMap, DataQueryDTO dataQueryDTO) throws Exception {
        DataQuery dataQuery = new DataQuery();
        DataTableDTO dataTableDTO = dataQueryDTO.getDataTable();
        TableGraph tableGraph = TableCache.getGlobal().findGraph(dataTableDTO.getTable().getDsvName());
        if (tableGraph == null) {
            throw new Exception("\u672a\u627e\u5230\u6807\u8bc6\u4e3a   " + dataTableDTO.getTable().getDsvName() + " \u7684\u6570\u636e\u65b9\u6848\u4e1a\u52a1\u89c6\u56fe");
        }
        DataTable dataTable = this.createDataTable(tableGraph, dataTableDTO);
        dataTableMap.put(dataTable.getTable().getTableName(), dataTable);
        dataQuery.setDataTable(dataTable);
        dataQuery.setFilter(dataQueryDTO.getFilter());
        dataQuery.setRequireGroupBy(dataQueryDTO.isRequireGroupBy());
        for (DataTableDTO refTableDTO : dataQueryDTO.getRefTables()) {
            DataTable refTable = this.createDataTable(tableGraph, refTableDTO);
            dataTableMap.put(refTable.getTable().getTableName(), refTable);
            dataQuery.getRefTables().add(refTable);
        }
        return dataQuery;
    }

    private DataTable createDataTable(TableGraph tableGraph, DataTableDTO dataTableDTO) throws Exception {
        DataTable dataTable = new DataTable();
        dataTable.setFilter(dataTableDTO.getFilter());
        TableNode tableNode = tableGraph.findTable(dataTableDTO.getTable().getTableName());
        if (tableNode == null) {
            throw new Exception(dataTableDTO.getTable().getDsvName() + "\u672a\u627e\u5230\u6807\u8bc6\u4e3a   " + dataTableDTO.getTable().getTableName() + " \u7684\u8868");
        }
        dataTable.setTable(tableNode);
        for (DataFieldDTO dataFieldDTO : dataTableDTO.getFields()) {
            dataTable.getFields().add(this.createDataField(tableNode, dataFieldDTO));
        }
        return dataTable;
    }

    private DataField createDataField(TableNode tableNode, DataFieldDTO dataFieldDTO) throws Exception {
        DataField dataField = new DataField();
        dataField.setAggregationType(dataFieldDTO.getAggregationType());
        dataField.setAlias(dataFieldDTO.getAlias());
        dataField.setFilter(dataFieldDTO.getFilter());
        dataField.setVisible(dataFieldDTO.isVisible());
        dataField.getValues().addAll(dataFieldDTO.getValues());
        FieldInfo fieldInfo = tableNode.findField(dataFieldDTO.getField().getFieldName());
        if (fieldInfo == null) {
            throw new Exception(tableNode.getTableName() + "\u8868\u672a\u627e\u5230\u6807\u8bc6\u4e3a   " + dataFieldDTO.getField().getFieldName() + " \u7684\u5b57\u6bb5");
        }
        dataField.setField(fieldInfo);
        return dataField;
    }

    private DataPage createDataPage(Map<String, DataTable> dataTableMap, DataPageDTO dataPageDTO) throws Exception {
        if (dataPageDTO == null) {
            return null;
        }
        DataPage dataPage = new DataPage(dataPageDTO.getPageInfo());
        if (dataPageDTO.getOrderBys() != null) {
            for (DataOrderByDTO orderByDTO : dataPageDTO.getOrderBys()) {
                dataPage.getOrderBys().add(this.createDataOrderBy(dataTableMap, orderByDTO));
            }
        }
        return dataPage;
    }

    private DataOrderBy createDataOrderBy(Map<String, DataTable> dataTableMap, DataOrderByDTO orderByDTO) throws Exception {
        DataOrderBy orderBy = new DataOrderBy();
        DataTable table = dataTableMap.get(orderByDTO.getTable().getTableName());
        orderBy.setTable(table);
        orderBy.setMode(orderByDTO.getMode());
        FieldInfo fieldInfo = table.getTable().findField(orderByDTO.getField().getFieldName());
        if (fieldInfo == null) {
            throw new Exception(table.getTable().getTableName() + "\u8868\u672a\u627e\u5230\u6807\u8bc6\u4e3a   " + orderByDTO.getField().getFieldName() + " \u7684\u5b57\u6bb5");
        }
        orderBy.setField(fieldInfo);
        return orderBy;
    }

    private class StreamingDataListener
    implements IDataListener {
        private WritingDataSet<ColumnInfo> dataSet;
        private List<IDataConverter> dataConverters;

        private List<IDataConverter> getDataConverters() {
            if (this.dataConverters == null) {
                this.dataConverters = new ArrayList<IDataConverter>();
            }
            return this.dataConverters;
        }

        public StreamingDataListener(WritingDataSet<ColumnInfo> dataSet) {
            this.dataSet = dataSet;
        }

        public void start(Metadata<ColumnInfo> metadata) throws AdHocEngineException {
            this.dataSet.getMetadata().copyFrom(metadata);
            try {
                for (int i = 0; i < metadata.getColumnCount(); ++i) {
                    Column column = metadata.getColumn(i);
                    if (column.getDataType() != 2) continue;
                    this.getDataConverters().add(new DateConverter(column.getIndex()));
                }
                this.dataSet.beginWriting();
            }
            catch (DataSetException e) {
                throw new AdHocEngineException((Throwable)e);
            }
        }

        public boolean process(DataRow row) throws AdHocEngineException {
            try {
                Object[] rowData = row.getBuffer();
                if (this.dataConverters != null) {
                    for (IDataConverter converter : this.dataConverters) {
                        rowData[converter.getIndex()] = converter.convert(rowData[converter.getIndex()]);
                    }
                }
                return this.dataSet.add(rowData);
            }
            catch (DataSetException e) {
                throw new AdHocEngineException((Throwable)e);
            }
        }

        public void finish() throws AdHocEngineException {
            try {
                this.dataSet.endWriting();
            }
            catch (DataSetException e) {
                throw new AdHocEngineException((Throwable)e);
            }
        }
    }
}

