/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.intf;

import com.jiuqi.np.dataengine.common.DataLinkColumn;
import com.jiuqi.np.dataengine.common.ReportInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import java.util.List;
import java.util.Map;

@Deprecated
public interface IDataLinkFinder {
    public DataLinkColumn findDataColumn(ReportInfo var1, int var2, int var3, boolean var4);

    public DataLinkColumn findDataColumn(ReportInfo var1, String var2);

    public DataLinkColumn findDataColumnByFieldName(ReportInfo var1, String var2);

    public ReportInfo findReportInfo(String var1);

    public ReportInfo findReportInfo(String var1, String var2);

    public List<ReportInfo> findAllRelatedReportInfo(String var1);

    public Map<Object, List<Object>> findRelatedUnitKeyMap(ExecutorContext var1, String var2, String var3, List<Object> var4);

    public List<Object> findRelatedUnitKey(ExecutorContext var1, String var2, String var3, Object var4);

    public String getRelatedUnitDimName(ExecutorContext var1, String var2, String var3);

    public Map<String, List<Object>> expandByDimensions(ExecutorContext var1, DataLinkColumn var2);

    public boolean is1V1Related(ExecutorContext var1, String var2);
}

