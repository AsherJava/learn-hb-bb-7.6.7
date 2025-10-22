/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DataModelLinkColumn
 *  com.jiuqi.np.dataengine.common.ReportInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataModelLinkFinder
 */
package com.jiuqi.nr.definition.internal.env;

import com.jiuqi.np.dataengine.common.DataModelLinkColumn;
import com.jiuqi.np.dataengine.common.ReportInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataModelLinkFinder;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;

public class CacheableColumnLinkFinder
implements IDataModelLinkFinder {
    private final IDataModelLinkFinder innerFinder;
    private static final String CACHE_KEY_SPLITOR = "_";
    private final ConcurrentMap<String, CacheItem<DataModelLinkColumn>> dataLinkColumnCache = new ConcurrentHashMap<String, CacheItem<DataModelLinkColumn>>();
    private final ConcurrentMap<String, CacheItem<ReportInfo>> reportInfoCache = new ConcurrentHashMap<String, CacheItem<ReportInfo>>();
    private final ConcurrentMap<String, CacheItem<List<ReportInfo>>> reportInfoLinkCache = new ConcurrentHashMap<String, CacheItem<List<ReportInfo>>>();
    private final ConcurrentMap<String, CacheItem<Map<String, List<Object>>>> expandByDimensionsCache = new ConcurrentHashMap<String, CacheItem<Map<String, List<Object>>>>();

    public CacheableColumnLinkFinder(IDataModelLinkFinder innerFinder) {
        this.innerFinder = innerFinder;
    }

    public DataModelLinkColumn findDataColumn(ReportInfo reportInfo, int rowIndex, int colIndex, boolean isGridPosition) {
        return this.findDataColumn(this.buildCacheKeyForDataColumn(reportInfo, rowIndex, colIndex, isGridPosition), () -> this.innerFinder.findDataColumn(reportInfo, rowIndex, colIndex, isGridPosition));
    }

    public DataModelLinkColumn findDataColumn(ReportInfo reportInfo, String dataLinkCode) {
        return this.findDataColumn(this.buildCacheKeyForDataColumn(reportInfo, dataLinkCode), () -> this.innerFinder.findDataColumn(reportInfo, dataLinkCode));
    }

    public DataModelLinkColumn findDataColumnByFieldName(ReportInfo reportInfo, String fieldName) {
        return this.findDataColumn(this.buildCacheKeyForDataColumnByFieldName(reportInfo, fieldName), () -> this.innerFinder.findDataColumnByFieldName(reportInfo, fieldName));
    }

    public DataModelLinkColumn findFMDMColumnByLinkAlias(ExecutorContext context, String fieldName, String linkAlias) {
        return this.findDataColumn(this.buildCacheKeyForFMDMField(linkAlias, fieldName), () -> this.innerFinder.findFMDMColumnByLinkAlias(context, fieldName, linkAlias));
    }

    public ReportInfo findReportInfo(String reportName) {
        return this.findReportInfo(this.buildCacheKeyForReportInfo(reportName), () -> this.innerFinder.findReportInfo(reportName));
    }

    public ReportInfo findReportInfo(String linkAlias, String reportName) {
        return this.findReportInfo(this.buildCacheKeyForReportInfo(linkAlias, reportName), () -> {
            ReportInfo reportInfo = this.innerFinder.findReportInfo(linkAlias, reportName);
            reportInfo.setLinkAlias(linkAlias);
            return reportInfo;
        });
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

    public Map<Object, List<Object>> findRelatedUnitKeyMap(ExecutorContext context, String linkAlias, String dimensionName, List<Object> unitKeys) {
        return this.innerFinder.findRelatedUnitKeyMap(context, linkAlias, dimensionName, unitKeys);
    }

    public List<Object> findRelatedUnitKey(ExecutorContext context, String linkAlias, String dimensionName, Object unitKey) {
        return this.innerFinder.findRelatedUnitKey(context, linkAlias, dimensionName, unitKey);
    }

    public String getRelatedUnitDimName(ExecutorContext context, String linkAlias, String dimensionName) {
        return this.innerFinder.getRelatedUnitDimName(context, linkAlias, dimensionName);
    }

    public Map<String, List<Object>> expandByDimensions(ExecutorContext context, DataModelLinkColumn dataModelLinkColumn) {
        String cacheKey = "expandByDimensions" + CACHE_KEY_SPLITOR + dataModelLinkColumn.getRegion() + CACHE_KEY_SPLITOR + dataModelLinkColumn.getDataLinkCode();
        CacheItem cache = (CacheItem)this.expandByDimensionsCache.get(cacheKey);
        if (cache != null) {
            return (Map)cache.getCacheData();
        }
        HashMap<String, List<Object>> expandByDimensionsMap = this.innerFinder.expandByDimensions(context, dataModelLinkColumn);
        if (expandByDimensionsMap == null) {
            expandByDimensionsMap = new HashMap<String, List<Object>>();
        }
        this.expandByDimensionsCache.put(cacheKey, new CacheItem(expandByDimensionsMap));
        return expandByDimensionsMap;
    }

    public boolean is1V1Related(ExecutorContext context, String linkAlias) {
        return this.innerFinder.is1V1Related(context, linkAlias);
    }

    public ReportInfo findPeriodInfo(ExecutorContext context, String linkAlias) {
        return this.innerFinder.findPeriodInfo(context, linkAlias);
    }

    public boolean hasRegionCondition(ExecutorContext context, String region) {
        return this.innerFinder.hasRegionCondition(context, region);
    }

    public String getRegionCondition(ExecutorContext context, String region) {
        return this.innerFinder.getRegionCondition(context, region);
    }

    private String buildCacheKeyForDataColumn(ReportInfo reportInfo, int rowIndex, int colIndex, boolean isGridPosition) {
        String reportKey = reportInfo == null ? "null" : (reportInfo.getLinkAlias() == null ? reportInfo.getReportKey() : reportInfo.getReportKey() + reportInfo.getLinkAlias());
        return "rowcol" + CACHE_KEY_SPLITOR + reportKey + CACHE_KEY_SPLITOR + rowIndex + CACHE_KEY_SPLITOR + colIndex + CACHE_KEY_SPLITOR + isGridPosition;
    }

    private DataModelLinkColumn findDataColumn(String cacheKey, Supplier<DataModelLinkColumn> supplier) {
        DataModelLinkColumn foundDataLinkColumn;
        CacheItem cache = (CacheItem)this.dataLinkColumnCache.get(cacheKey);
        if (cache != null) {
            foundDataLinkColumn = (DataModelLinkColumn)cache.getCacheData();
        } else {
            foundDataLinkColumn = supplier.get();
            this.dataLinkColumnCache.put(cacheKey, new CacheItem<DataModelLinkColumn>(foundDataLinkColumn));
        }
        try {
            return foundDataLinkColumn == null ? null : (DataModelLinkColumn)foundDataLinkColumn.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    private ReportInfo findReportInfo(String cacheKey, Supplier<ReportInfo> supplier) {
        ReportInfo foundReportInfo;
        CacheItem cache = (CacheItem)this.reportInfoCache.get(cacheKey);
        if (cache != null) {
            foundReportInfo = (ReportInfo)cache.getCacheData();
        } else {
            foundReportInfo = supplier.get();
            this.reportInfoCache.put(cacheKey, new CacheItem<ReportInfo>(foundReportInfo));
        }
        return foundReportInfo;
    }

    private String buildCacheKeyForDataColumn(ReportInfo reportInfo, String dataLinkCode) {
        String reportKey = reportInfo == null ? "null" : (reportInfo.getLinkAlias() == null ? reportInfo.getReportKey() : reportInfo.getReportKey() + reportInfo.getLinkAlias());
        return "linkcode" + CACHE_KEY_SPLITOR + reportKey + CACHE_KEY_SPLITOR + dataLinkCode;
    }

    private String buildCacheKeyForDataColumnByFieldName(ReportInfo reportInfo, String fieldName) {
        String reportKey = reportInfo == null ? "null" : (reportInfo.getLinkAlias() == null ? reportInfo.getReportKey() : reportInfo.getReportKey() + reportInfo.getLinkAlias());
        return "fieldname" + CACHE_KEY_SPLITOR + reportKey + CACHE_KEY_SPLITOR + fieldName;
    }

    private String buildCacheKeyForReportInfo(String reportName) {
        return "reportName" + CACHE_KEY_SPLITOR + reportName;
    }

    private String buildCacheKeyForReportInfo(String linkAlias, String reportName) {
        return "aliasreportName" + CACHE_KEY_SPLITOR + linkAlias + CACHE_KEY_SPLITOR + reportName;
    }

    private String buildCacheKeyForReportInfoLiink(String reportName) {
        return "reportlink" + CACHE_KEY_SPLITOR + reportName;
    }

    private String buildCacheKeyForFMDMField(String linkAlias, String fieldName) {
        return "aliasfmdmField" + CACHE_KEY_SPLITOR + linkAlias + CACHE_KEY_SPLITOR + fieldName;
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

