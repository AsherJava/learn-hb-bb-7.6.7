/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.defaultlog.Logger
 *  com.jiuqi.nr.datascheme.api.DataScheme
 */
package com.jiuqi.nr.multcheck2.service;

import com.jiuqi.bi.core.jobs.defaultlog.Logger;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.multcheck2.bean.MultcheckResItem;
import com.jiuqi.nr.multcheck2.bean.MultcheckResRecord;
import com.jiuqi.nr.multcheck2.bean.MultcheckResScheme;
import com.jiuqi.nr.multcheck2.web.result.ResultDetailVO;
import java.util.Date;
import java.util.List;

public interface IMCResultService {
    public String getTableName(String var1, String var2, DataScheme var3);

    @Deprecated
    public List<String> getDynamicFieldsByTask(String var1);

    public void cleanRecord(Date var1, Logger var2);

    public void itemBatchAdd(List<MultcheckResItem> var1, String var2);

    public List<MultcheckResItem> getResultItem(String var1, String var2);

    public void schemeBatchAdd(List<MultcheckResScheme> var1, String var2);

    public List<MultcheckResScheme> getResultScheme(String var1, String var2);

    @Deprecated
    public void recordAdd(String var1, MultcheckResRecord var2, List<String> var3);

    public void recordAdd(String var1, MultcheckResRecord var2);

    public MultcheckResRecord getResultRecord(String var1, String var2, List<String> var3);

    public ResultDetailVO buildResult(String var1, String var2) throws Exception;

    public ResultDetailVO buildLastResult(String var1, String var2, String var3, String var4) throws Exception;

    public boolean hasLastResult(String var1, String var2, String var3, String var4);

    public void cleanAllRecords(DataScheme var1);
}

