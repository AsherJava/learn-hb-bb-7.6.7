/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.data.access.api.response.DataPublishReturnInfo
 *  com.jiuqi.nr.data.access.param.DataPublishBatchReadWriteResult
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.data.access.api.response.DataPublishReturnInfo;
import com.jiuqi.nr.data.access.param.DataPublishBatchReadWriteResult;
import com.jiuqi.nr.dataentry.bean.DataPublishParam;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.List;

public interface IDataPublishService {
    public boolean isEnableDataPublis(String var1);

    public Boolean isDataPublished(DataPublishParam var1);

    public List<DataPublishReturnInfo> dataPublish(DataPublishParam var1, AsyncTaskMonitor var2);

    public List<String> getPublishedFormKeys(DataPublishParam var1);

    public List<DataPublishBatchReadWriteResult> getBatchResult(JtableContext var1);
}

