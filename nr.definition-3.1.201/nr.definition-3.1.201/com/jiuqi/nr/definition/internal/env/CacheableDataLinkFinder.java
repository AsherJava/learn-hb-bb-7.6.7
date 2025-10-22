/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DataLinkColumn
 *  com.jiuqi.np.dataengine.common.ReportInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataLinkFinder
 */
package com.jiuqi.nr.definition.internal.env;

import com.jiuqi.np.dataengine.common.DataLinkColumn;
import com.jiuqi.np.dataengine.common.ReportInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataLinkFinder;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;

public class CacheableDataLinkFinder
implements IDataLinkFinder {
    private static final String CACHE_KEY_SPLITOR = "_";
    private final ConcurrentMap<String, CacheItem<DataLinkColumn>> dataLinkColumnCache = new ConcurrentHashMap<String, CacheItem<DataLinkColumn>>();
    private final ConcurrentMap<String, CacheItem<ReportInfo>> reportInfoCache = new ConcurrentHashMap<String, CacheItem<ReportInfo>>();
    private final ConcurrentMap<String, CacheItem<List<ReportInfo>>> reportInfoLinkCache = new ConcurrentHashMap<String, CacheItem<List<ReportInfo>>>();
    private final ConcurrentMap<String, CacheItem<Map<String, List<Object>>>> expandByDimensionsCache = new ConcurrentHashMap<String, CacheItem<Map<String, List<Object>>>>();
    private final IDataLinkFinder innerFinder;

    public CacheableDataLinkFinder(IDataLinkFinder innerFinder) {
        this.innerFinder = innerFinder;
    }

    private DataLinkColumn findDataColumn(String cacheKey, Supplier<DataLinkColumn> supplier) {
        DataLinkColumn foundDataLinkColumn = null;
        CacheItem cache = (CacheItem)this.dataLinkColumnCache.get(cacheKey);
        if (cache != null) {
            foundDataLinkColumn = (DataLinkColumn)cache.getCacheData();
        } else {
            foundDataLinkColumn = supplier.get();
            this.dataLinkColumnCache.put(cacheKey, new CacheItem<DataLinkColumn>(foundDataLinkColumn));
        }
        try {
            return foundDataLinkColumn == null ? null : (DataLinkColumn)foundDataLinkColumn.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    private ReportInfo findReportInfo(String cacheKey, Supplier<ReportInfo> supplier) {
        ReportInfo foundReportInfo = null;
        CacheItem cache = (CacheItem)this.reportInfoCache.get(cacheKey);
        if (cache != null) {
            foundReportInfo = (ReportInfo)cache.getCacheData();
        } else {
            foundReportInfo = supplier.get();
            this.reportInfoCache.put(cacheKey, new CacheItem<ReportInfo>(foundReportInfo));
        }
        return foundReportInfo;
    }

    public DataLinkColumn findDataColumn(ReportInfo reportInfo, int rowIndex, int colIndex, boolean isGridPosition) {
        return this.findDataColumn(this.buildCacheKeyForDataColumn(reportInfo, rowIndex, colIndex, isGridPosition), () -> this.innerFinder.findDataColumn(reportInfo, rowIndex, colIndex, isGridPosition));
    }

    private String buildCacheKeyForDataColumn(ReportInfo reportInfo, int rowIndex, int colIndex, boolean isGridPosition) {
        String reportKey = reportInfo == null ? "null" : reportInfo.getReportKey();
        return "rowcol" + CACHE_KEY_SPLITOR + reportKey + CACHE_KEY_SPLITOR + rowIndex + CACHE_KEY_SPLITOR + colIndex + CACHE_KEY_SPLITOR + isGridPosition;
    }

    public DataLinkColumn findDataColumn(ReportInfo reportInfo, String dataLinkCode) {
        return this.findDataColumn(this.buildCacheKeyForDataColumn(reportInfo, dataLinkCode), () -> this.innerFinder.findDataColumn(reportInfo, dataLinkCode));
    }

    private String buildCacheKeyForDataColumn(ReportInfo reportInfo, String dataLinkCode) {
        String reportKey = reportInfo == null ? "null" : reportInfo.getReportKey();
        return "linkcode" + CACHE_KEY_SPLITOR + reportKey + CACHE_KEY_SPLITOR + dataLinkCode;
    }

    public DataLinkColumn findDataColumnByFieldName(ReportInfo reportInfo, String fieldName) {
        return this.findDataColumn(this.buildCacheKeyForDataColumnByFieldName(reportInfo, fieldName), () -> this.innerFinder.findDataColumnByFieldName(reportInfo, fieldName));
    }

    private String buildCacheKeyForDataColumnByFieldName(ReportInfo reportInfo, String fieldName) {
        String reportKey = reportInfo == null ? "null" : reportInfo.getReportKey();
        return "fieldname" + CACHE_KEY_SPLITOR + reportKey + CACHE_KEY_SPLITOR + fieldName;
    }

    public ReportInfo findReportInfo(String reportName) {
        return this.findReportInfo(this.buildCacheKeyForReportInfo(reportName), () -> this.innerFinder.findReportInfo(reportName));
    }

    private String buildCacheKeyForReportInfo(String reportName) {
        return "reportName" + CACHE_KEY_SPLITOR + reportName;
    }

    public ReportInfo findReportInfo(String linkAlias, String reportName) {
        return this.findReportInfo(this.buildCacheKeyForReportInfo(linkAlias, reportName), () -> this.innerFinder.findReportInfo(linkAlias, reportName));
    }

    private String buildCacheKeyForReportInfo(String linkAlias, String reportName) {
        return "aliasreportName" + CACHE_KEY_SPLITOR + linkAlias + CACHE_KEY_SPLITOR + reportName;
    }

    public List<ReportInfo> findAllRelatedReportInfo(String reportName) {
        String cacheKey = this.buildCacheKeyForReportInfoLiink(reportName);
        CacheItem cache = (CacheItem)this.reportInfoLinkCache.get(cacheKey);
        if (cache != null) {
            return (List)cache.getCacheData();
        }
        List<ReportInfo> reportInfoLink = this.innerFinder.findAllRelatedReportInfo(reportName);
        if (reportInfoLink == null) {
            reportInfoLink = Collections.emptyList();
        }
        this.reportInfoLinkCache.put(cacheKey, new CacheItem(reportInfoLink));
        return reportInfoLink;
    }

    private String buildCacheKeyForReportInfoLiink(String reportName) {
        return "reportlink" + CACHE_KEY_SPLITOR + reportName;
    }

    public Map<Object, List<Object>> findRelatedUnitKeyMap(ExecutorContext context, String linkAlias, String dimensionName, List<Object> unitKeys) {
        return this.innerFinder.findRelatedUnitKeyMap(context, linkAlias, dimensionName, unitKeys);
    }

    public List<Object> findRelatedUnitKey(ExecutorContext context, String linkAlias, String dimensionName, Object unitKey) {
        return this.innerFinder.findRelatedUnitKey(context, linkAlias, dimensionName, unitKey);
    }

    public String getRelatedUnitDimName(ExecutorContext context, String linkAlias, String dimensionName) {
        return this.innerFinder.getRelatedUnitDimName(context, linkAlias, dimensionName);
    }

    public Map<String, List<Object>> expandByDimensions(ExecutorContext context, DataLinkColumn dataLinkColumn) {
        String cacheKey = "expandByDimensions" + CACHE_KEY_SPLITOR + dataLinkColumn.getRegion() + CACHE_KEY_SPLITOR + dataLinkColumn.getDataLinkCode();
        CacheItem cache = (CacheItem)this.expandByDimensionsCache.get(cacheKey);
        if (cache != null) {
            return (Map)cache.getCacheData();
        }
        HashMap<String, List<Object>> expandByDimensionsMap = this.innerFinder.expandByDimensions(context, dataLinkColumn);
        if (expandByDimensionsMap == null) {
            expandByDimensionsMap = new HashMap<String, List<Object>>();
        }
        this.expandByDimensionsCache.put(cacheKey, new CacheItem(expandByDimensionsMap));
        return expandByDimensionsMap;
    }

    public boolean is1V1Related(ExecutorContext context, String linkAlias) {
        return this.innerFinder.is1V1Related(context, linkAlias);
    }

    private static class CacheItem<T> {
        private T cacheData;

        public T getCacheData() {
            return this.cacheData;
        }

        public CacheItem(T cacheData) {
            this.cacheData = cacheData;
        }
    }
}

