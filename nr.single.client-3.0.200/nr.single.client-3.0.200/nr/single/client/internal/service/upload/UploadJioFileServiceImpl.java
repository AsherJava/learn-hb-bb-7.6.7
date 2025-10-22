/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.dataentry.bean.ImportResultObject
 *  com.jiuqi.nr.dataentry.bean.UploadParam
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.single.core.exception.SingleFileException
 *  com.jiuqi.nr.single.core.file.SingleFile
 *  com.jiuqi.nr.single.core.util.SinglePathUtil
 *  javax.annotation.Resource
 *  nr.single.data.datain.service.ITaskFileImportDataService
 *  nr.single.map.data.PathUtil
 *  nr.single.map.data.TaskDataContext
 */
package nr.single.client.internal.service.upload;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.dataentry.bean.ImportResultObject;
import com.jiuqi.nr.dataentry.bean.UploadParam;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.single.core.exception.SingleFileException;
import com.jiuqi.nr.single.core.file.SingleFile;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import nr.single.client.bean.JIOImportResultObject;
import nr.single.client.service.upload.IUploadJioDataService;
import nr.single.client.service.upload.IUploadJioFileService;
import nr.single.client.service.upload.IUploadJioTaskDataService;
import nr.single.data.datain.service.ITaskFileImportDataService;
import nr.single.map.data.PathUtil;
import nr.single.map.data.TaskDataContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UploadJioFileServiceImpl
implements IUploadJioFileService {
    private static final Logger logger = LoggerFactory.getLogger(UploadJioFileServiceImpl.class);
    @Autowired
    private ITaskFileImportDataService jioImportService;
    @Resource
    private IRunTimeViewController runtimeView;
    @Autowired
    private IUploadJioDataService uploadJioDataService;
    @Autowired
    private IUploadJioTaskDataService uploadTaskDataService;

    @Override
    public ImportResultObject upload(String fileName, String path, UploadParam param, AsyncTaskMonitor asyncTaskMonitor, double begin, double span) {
        String jioFile = StringUtils.isEmpty((String)param.getFilePath()) ? path + fileName : param.getFilePath();
        return this.upload(fileName, path, jioFile, param, asyncTaskMonitor, begin, span);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public ImportResultObject upload(String fileName, String path, String jioFilePath, UploadParam param, AsyncTaskMonitor asyncTaskMonitor, double begin, double span) {
        JIOImportResultObject result = new JIOImportResultObject();
        TaskDataContext context = new TaskDataContext();
        try {
            context.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a\u5f00\u59cb\u51c6\u5907,\u65f6\u95f4:" + new Date().toString());
            context.getDataOption().setUploadCheckData(this.uploadTaskDataService.isJioUseCheckData());
            if (null != asyncTaskMonitor) {
                context.setProgress(begin + 0.01);
                context.setNextProgressLen(0.04);
                context.setAsyncTaskMonitor(asyncTaskMonitor);
                asyncTaskMonitor.progressAndMessage(context.getProgress(), "\u5bfc\u5165\u51c6\u5907");
            }
            ArrayList<String> pathList = new ArrayList<String>();
            String newPath = this.getJioWorkFilePath(param, path, pathList);
            String workPath = PathUtil.createNewPath((String)newPath, (String)"JIOIMPORT");
            String taskFilePath = PathUtil.createNewPath((String)workPath, (String)(OrderGenerator.newOrder() + ".TSK"));
            String jioFile = jioFilePath;
            try {
                context.setTaskDocPath(this.uploadTaskDataService.getUploadTaskDocDir(param));
                context.setTaskTxtPath(this.uploadTaskDataService.getUploadTaskTxtDir(param));
                context.setTaskImgPath(this.uploadTaskDataService.getUploadTaskImgDir(param));
                context.setTaskRptPath(this.uploadTaskDataService.getUploadTaskRptDir(param));
                File jioFileObj = new File(SinglePathUtil.normalize((String)jioFile));
                context.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a\u89e3\u538bJIO\u6587\u4ef6,\u65f6\u95f4:" + new Date().toString() + ",\u6587\u4ef6\u5927\u5c0f\uff1a" + jioFileObj.length());
                SingleFile singleFile = this.jioImportService.exactSingleFileToPathReturn(context, taskFilePath, jioFile);
                if (null != asyncTaskMonitor) {
                    if (asyncTaskMonitor.isCancel()) {
                        result.setSuccess(false);
                        result.setMessage("\u5bfc\u5165\u5df2\u53d6\u6d88");
                        JIOImportResultObject jIOImportResultObject = result;
                        return jIOImportResultObject;
                    }
                    context.setProgress(begin + 0.05);
                    context.setNextProgressLen(0.03);
                    asyncTaskMonitor.progressAndMessage(context.getProgress(), "\u5bfc\u5165\u51c6\u5907");
                }
                result = this.uploadTaskDataService.uploadJioTaskData(context, taskFilePath, singleFile, param, asyncTaskMonitor, begin, span);
                return result;
            }
            finally {
                this.deleteTempPath(taskFilePath, workPath, pathList);
            }
        }
        catch (Exception ex) {
            result.setSuccess(false);
            try (StringWriter sw = new StringWriter();
                 PrintWriter pw = new PrintWriter(sw);){
                ex.printStackTrace(pw);
                LogHelper.info((String)"\u6570\u636e\u5f55\u5165", (String)"JIO\u5bfc\u5165\u6570\u636e\u5f02\u5e38", (String)("\u53d1\u751f\u5f02\u5e38\uff1a" + ex.getMessage() + ",\u8be6\u7ec6\uff1a" + sw.toString()));
            }
            catch (IOException e) {
                context.error(logger, e.getMessage(), (Throwable)e);
            }
            context.error(logger, ex.getMessage(), (Throwable)ex);
        }
        return result;
    }

    private String getJioWorkFilePath(UploadParam param, String oldPath, List<String> pathList) throws SingleFileException {
        String workPath = null;
        if (param.getVariableMap().containsKey("JioUploadWorkPath") && StringUtils.isNotEmpty((String)(workPath = (String)param.getVariableMap().get("JioUploadWorkPath")))) {
            SimpleDateFormat sfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String fileLocation = sfDate.format(new Date()) + OrderGenerator.newOrder();
            workPath = PathUtil.createNewPath((String)workPath, (String)fileLocation);
            pathList.add(workPath);
        }
        if (StringUtils.isEmpty(workPath)) {
            workPath = oldPath;
        }
        return workPath;
    }

    private void deleteTempPath(String taskFilePath, String workPath, List<String> pathList) {
        ArrayList<String> delPaths = new ArrayList<String>();
        delPaths.add(taskFilePath);
        delPaths.add(workPath);
        if (!pathList.isEmpty()) {
            delPaths.addAll(pathList);
        }
        this.uploadJioDataService.doDeleteDirs(delPaths);
    }
}

