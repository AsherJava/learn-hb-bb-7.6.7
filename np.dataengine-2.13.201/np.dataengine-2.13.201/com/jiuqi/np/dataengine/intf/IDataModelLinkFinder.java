/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.Region
 */
package com.jiuqi.np.dataengine.intf;

import com.jiuqi.bi.syntax.cell.Region;
import com.jiuqi.np.dataengine.common.DataModelLinkColumn;
import com.jiuqi.np.dataengine.common.ReportInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import java.util.List;
import java.util.Map;

public interface IDataModelLinkFinder {
    public DataModelLinkColumn findDataColumn(ReportInfo var1, int var2, int var3, boolean var4);

    public DataModelLinkColumn findDataColumn(ReportInfo var1, String var2);

    @Deprecated
    public DataModelLinkColumn findDataColumnByFieldName(ReportInfo var1, String var2);

    default public DataModelLinkColumn findDataColumnByFieldName(ExecutorContext context, ReportInfo reportInfo, String fieldName) {
        return this.findDataColumnByFieldName(reportInfo, fieldName);
    }

    default public DataModelLinkColumn findDataColumnByTableFieldName(ExecutorContext context, String tableCode, String fieldName) {
        return null;
    }

    default public DataModelLinkColumn findFMDMColumnByLinkAlias(ExecutorContext context, String fieldName, String linkAlias) {
        return this.findDataColumnByFieldName(null, fieldName);
    }

    public ReportInfo findReportInfo(String var1);

    public ReportInfo findReportInfo(String var1, String var2);

    default public ReportInfo findPeriodInfo(ExecutorContext context, String linkAlias) {
        return null;
    }

    default public Map<String, String> getDimValuesByLinkAlias(ExecutorContext context, String linkAlias) {
        return null;
    }

    public List<ReportInfo> findAllRelatedReportInfo(String var1);

    public Map<Object, List<Object>> findRelatedUnitKeyMap(ExecutorContext var1, String var2, String var3, List<Object> var4);

    public List<Object> findRelatedUnitKey(ExecutorContext var1, String var2, String var3, Object var4);

    public String getRelatedUnitDimName(ExecutorContext var1, String var2, String var3);

    public Map<String, List<Object>> expandByDimensions(ExecutorContext var1, DataModelLinkColumn var2);

    @Deprecated
    public boolean is1V1Related(ExecutorContext var1, String var2);

    default public boolean hasRegionCondition(ExecutorContext context, String region) {
        return false;
    }

    default public String getRegionCondition(ExecutorContext context, String region) {
        return null;
    }

    default public Region getCellRegion(ExecutorContext context, DataModelLinkColumn column) {
        return null;
    }

    default public List<String> getTableInnerKeys(ExecutorContext context, String tableName) {
        return null;
    }
}

