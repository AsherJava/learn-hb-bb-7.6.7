/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 */
package com.jiuqi.np.dataengine.intf.impl;

import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.intf.IDataRowReader;
import com.jiuqi.np.dataengine.intf.impl.DataRowImpl;
import com.jiuqi.np.dataengine.intf.impl.TableResultSetReader;
import com.jiuqi.np.dataengine.query.DataQueryBuilder;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.query.QuerySqlBuilder;
import com.jiuqi.np.dataengine.reader.SteamJoinDataSetReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StreamJoinLoader
implements AutoCloseable {
    private DataQueryBuilder dataQueryBuilder = null;
    private QueryContext qContext = null;
    private List<TableResultSetReader> readers = new ArrayList<TableResultSetReader>();
    private List<TableResultSetReader> allReaders = new ArrayList<TableResultSetReader>();

    public StreamJoinLoader(QueryContext qContext, DataQueryBuilder dataQueryBuilder) {
        this.qContext = qContext;
        this.dataQueryBuilder = dataQueryBuilder;
    }

    public void loadToReader(IDataRowReader dataRowReader, SteamJoinDataSetReader steamJoinDataSetReader) throws Exception {
        List<QuerySqlBuilder> builders = this.dataQueryBuilder.getAllFullJoinSqlBuilders(this.qContext);
        List<String> compareDims = this.getCompareDims(builders);
        for (QuerySqlBuilder builder : builders) {
            builder.setUseDefaultOrderBy(true);
            builder.setIgnoreDefaultOrderBy(false);
            builder.setMemoryStartIndex(steamJoinDataSetReader.getColumnCount());
            builder.buildQuerySql(this.qContext, this.dataQueryBuilder.getSqlUpdater());
            TableResultSetReader reader = new TableResultSetReader(this.qContext, builder, compareDims);
            this.readers.add(reader);
        }
        this.allReaders.addAll(this.readers);
        dataRowReader.start(this.qContext, this.dataQueryBuilder.getTable().getSystemFields());
        ArrayList<TableResultSetReader> readSuccReaders = new ArrayList<TableResultSetReader>();
        while (this.readers.size() > 0) {
            Iterator<TableResultSetReader> iterator = this.readers.iterator();
            ArrayKey minKey = null;
            TableResultSetReader rowKeyReader = null;
            while (iterator.hasNext()) {
                TableResultSetReader reader = iterator.next();
                ArrayKey compareKey = reader.getCompareKey();
                if (compareKey == null) {
                    iterator.remove();
                    continue;
                }
                if (minKey != null && compareKey.compareTo(minKey) >= 0) continue;
                minKey = compareKey;
                rowKeyReader = reader;
            }
            if (minKey == null || this.readers.size() <= 0) break;
            readSuccReaders.clear();
            for (TableResultSetReader reader : this.readers) {
                if (!reader.readDataByCompareKey(minKey, steamJoinDataSetReader)) continue;
                readSuccReaders.add(reader);
            }
            DataRowImpl dataRow = this.dataQueryBuilder.loadRowDataForReader(this.qContext, rowKeyReader.getSqlBuilder(), rowKeyReader.getDataSetReader(), dataRowReader);
            try {
                dataRowReader.readRowData(this.qContext, dataRow);
            }
            catch (Exception e) {
                this.qContext.getMonitor().exception(e);
            }
            for (TableResultSetReader readSuccReader : readSuccReaders) {
                readSuccReader.next();
            }
            steamJoinDataSetReader.resetRow();
        }
        dataRowReader.finish(this.qContext);
    }

    private List<String> getCompareDims(List<QuerySqlBuilder> builders) {
        ArrayList<String> compareDims = new ArrayList<String>();
        DimensionSet tableDimensions = builders.get(0).getPrimaryTable().getTableDimensions();
        for (int i = 0; i < tableDimensions.size(); ++i) {
            String dim = tableDimensions.get(i);
            Object dimValue = this.qContext.getMasterKeys().getValue(dim);
            if (dimValue != null) {
                List values;
                if (!(dimValue instanceof List) || (values = (List)dimValue).size() <= 1) continue;
                compareDims.add(dim);
                continue;
            }
            compareDims.add(dim);
        }
        return compareDims;
    }

    @Override
    public void close() throws Exception {
        for (TableResultSetReader reader : this.allReaders) {
            reader.close();
        }
    }
}

