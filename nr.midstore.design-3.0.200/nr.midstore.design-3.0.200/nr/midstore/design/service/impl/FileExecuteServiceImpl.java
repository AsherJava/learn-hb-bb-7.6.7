/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  nr.midstore.core.asyn.MidstoreBatchController
 *  nr.midstore.core.publish.service.IMidstorePublishTaskService
 */
package nr.midstore.design.service.impl;

import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import nr.midstore.core.asyn.MidstoreBatchController;
import nr.midstore.core.publish.service.IMidstorePublishTaskService;
import nr.midstore.design.service.IFileExecuteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileExecuteServiceImpl
implements IFileExecuteService {
    private static final Logger log = LoggerFactory.getLogger(FileExecuteServiceImpl.class);
    @Autowired
    private MidstoreBatchController midstoreBatchController;
    @Autowired
    private IMidstorePublishTaskService publishService;

    @Override
    public AsyncTaskInfo executePublish(String schemeKey) throws Exception {
        String taskId = this.midstoreBatchController.publishTasK(schemeKey);
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(taskId);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @Override
    public AsyncTaskInfo doExportDocument(String schemeKey) throws Exception {
        String taskId = this.midstoreBatchController.doExportDocument(schemeKey);
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(taskId);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @Override
    public AsyncTaskInfo doLinkBaseDataFromFields(String schemeKey) throws Exception {
        String taskId = this.midstoreBatchController.doLinkBaseDataFromFields(schemeKey);
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(taskId);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @Override
    public AsyncTaskInfo doPostDataToMidstore(String schemeKey) throws Exception {
        String taskId = this.midstoreBatchController.doPostDataToMidstore(schemeKey);
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(taskId);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @Override
    public AsyncTaskInfo doGetDataFromMidstore(String schemeKey) throws Exception {
        String taskId = this.midstoreBatchController.doGetDataFromMidstore(schemeKey);
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(taskId);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @Override
    public AsyncTaskInfo doCheckParams(String schemeKey) throws Exception {
        String taskId = this.midstoreBatchController.doCheckParams(schemeKey);
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(taskId);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }
}

