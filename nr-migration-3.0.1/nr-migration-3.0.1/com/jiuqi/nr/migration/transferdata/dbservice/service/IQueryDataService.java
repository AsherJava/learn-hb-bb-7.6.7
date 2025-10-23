/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.datacrud.IRowData
 */
package com.jiuqi.nr.migration.transferdata.dbservice.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.migration.transferdata.bean.DimInfo;
import com.jiuqi.nr.migration.transferdata.bean.FetchDataParam;
import com.jiuqi.nr.migration.transferdata.bean.TransMainBody;
import com.jiuqi.nr.migration.transferdata.bean.TransMemo;
import java.text.ParseException;
import java.util.List;

public interface IQueryDataService {
    public TransMainBody queryFMDMData(FetchDataParam var1);

    public List<IRowData> queryAllReportData(String var1, List<DimInfo> var2);

    public List<IRowData> batchQueryReportData(FetchDataParam var1);

    public int queryDataState(String var1, DimensionValueSet var2);

    public List<TransMemo> queryWorkFlowDataCommitByForm(String var1, List<String> var2, DimensionValueSet var3) throws ParseException;

    public List<TransMemo> queryHistoryMemos(String var1, DimensionValueSet var2, boolean var3) throws ParseException;
}

