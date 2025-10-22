/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 */
package nr.midstore.design.service;

import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;

public interface IFileExecuteService {
    public AsyncTaskInfo executePublish(String var1) throws Exception;

    public AsyncTaskInfo doExportDocument(String var1) throws Exception;

    public AsyncTaskInfo doLinkBaseDataFromFields(String var1) throws Exception;

    public AsyncTaskInfo doGetDataFromMidstore(String var1) throws Exception;

    public AsyncTaskInfo doPostDataToMidstore(String var1) throws Exception;

    public AsyncTaskInfo doCheckParams(String var1) throws Exception;
}

