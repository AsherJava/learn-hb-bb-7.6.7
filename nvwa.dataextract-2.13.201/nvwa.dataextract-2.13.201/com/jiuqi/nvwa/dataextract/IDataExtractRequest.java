/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.nvwa.dataextract;

import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nvwa.dataextract.DataExtractResult;
import java.util.List;

public interface IDataExtractRequest {
    public String getType();

    public String getTitle();

    public DataExtractResult getResult(QueryContext var1, String var2, List<String> var3, IReportFunction var4) throws Exception;

    default public boolean checkDataExtract(QueryContext qContext, String queryName, List<Integer> columnIndexes, List<Integer> argParaTypes) throws Exception {
        return true;
    }
}

