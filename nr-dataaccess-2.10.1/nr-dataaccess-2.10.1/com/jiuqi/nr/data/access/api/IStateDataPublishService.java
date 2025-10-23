/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.data.access.api;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.data.access.api.param.PublishParam;
import com.jiuqi.nr.data.access.api.response.DataPublishReturnInfo;
import com.jiuqi.nr.data.access.param.DataPublishBatchReadWriteResult;
import java.util.List;

public interface IStateDataPublishService {
    public boolean isEnableDataPublis(String var1);

    public Boolean isDataPublished(PublishParam var1);

    public List<DataPublishReturnInfo> dataPublish(PublishParam var1, AsyncTaskMonitor var2);

    public List<String> getPublishedFormKeys(PublishParam var1);

    public List<DataPublishBatchReadWriteResult> getBatchResult(PublishParam var1);
}

