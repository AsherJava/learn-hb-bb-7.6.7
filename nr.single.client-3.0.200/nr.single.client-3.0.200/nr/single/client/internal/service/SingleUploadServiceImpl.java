/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.core.application.NpApplication
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.dataentry.bean.UploadParam
 *  com.jiuqi.nr.dataentry.monitor.SimpleAsyncProgressMonitor
 *  com.jiuqi.nr.dataentry.service.IUploadService
 *  com.jiuqi.nr.dataentry.service.IUploadTypeService
 *  com.jiuqi.nr.dataentry.util.BatchExportConsts
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.single.core.exception.SingleFileException
 *  com.jiuqi.nr.single.core.util.SinglePathUtil
 *  javax.annotation.Resource
 *  nr.single.map.data.exception.SingleDataException
 */
package nr.single.client.internal.service;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.core.application.NpApplication;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.dataentry.bean.UploadParam;
import com.jiuqi.nr.dataentry.monitor.SimpleAsyncProgressMonitor;
import com.jiuqi.nr.dataentry.service.IUploadService;
import com.jiuqi.nr.dataentry.service.IUploadTypeService;
import com.jiuqi.nr.dataentry.util.BatchExportConsts;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.single.core.exception.SingleFileException;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import nr.single.client.bean.SingleUploadParam;
import nr.single.client.internal.service.SingleFuncExecuteServiceImpl;
import nr.single.client.service.ISingleUploadService;
import nr.single.map.data.exception.SingleDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class SingleUploadServiceImpl
implements ISingleUploadService {
    private static final Logger log = LoggerFactory.getLogger(SingleFuncExecuteServiceImpl.class);
    @Autowired
    private Map<String, IUploadTypeService> uploadTypeServiceMap;
    @Resource
    private IRunTimeViewController controller;
    @Autowired
    private NpApplication npApplication;
    @Resource
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private CacheObjectResourceRemote cacheObjectResourceRemote;
    @Autowired
    private IUploadService iUploadService;

    @Override
    public AsyncTaskInfo upload(File sourcefile, SingleUploadParam param) throws SingleDataException {
        String fileName = sourcefile.getName();
        String[] split = fileName.split("\\.");
        String suffix = split[split.length - 1];
        SimpleDateFormat sfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String fileLocation = sfDate.format(new Date()) + OrderGenerator.newOrder();
        String path = BatchExportConsts.UPLOADDIR + BatchExportConsts.SEPARATOR + fileLocation + BatchExportConsts.SEPARATOR;
        try {
            File pathFile = new File(SinglePathUtil.normalize((String)path));
            if (!pathFile.exists()) {
                pathFile.mkdirs();
            }
            File file = new File(SinglePathUtil.normalize((String)(pathFile.getPath() + BatchExportConsts.SEPARATOR + fileName)));
            param.setFileLocation(fileLocation);
            String taskId = UUID.randomUUID().toString();
            SimpleAsyncProgressMonitor asyncTaskMonitor = new SimpleAsyncProgressMonitor(taskId, this.cacheObjectResourceRemote);
            this.npApplication.asyncRun(() -> this.lambda$upload$0((AsyncTaskMonitor)asyncTaskMonitor, param, suffix, file));
            AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
            asyncTaskInfo.setId(taskId);
            asyncTaskInfo.setProcess(Double.valueOf(0.0));
            asyncTaskInfo.setResult("");
            asyncTaskInfo.setDetail((Object)"");
            asyncTaskInfo.setState(TaskState.PROCESSING);
            return asyncTaskInfo;
        }
        catch (SingleFileException e1) {
            log.error(e1.getMessage(), e1);
            throw new SingleDataException(e1.getMessage(), (Throwable)e1);
        }
    }

    private /* synthetic */ void lambda$upload$0(AsyncTaskMonitor asyncTaskMonitor, SingleUploadParam param, String suffix, File file) {
        try {
            this.iUploadService.uploadAsync(asyncTaskMonitor, (UploadParam)param, suffix, file);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}

