/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.dataentry.bean.ImportResultObject
 *  com.jiuqi.nr.dataentry.bean.UploadParam
 *  com.jiuqi.nr.dataentry.funcVerificated.annotation.FuncVerificated
 *  com.jiuqi.nr.dataentry.service.IUploadTypeService
 *  com.jiuqi.nr.dataentry.util.BatchExportConsts
 *  com.jiuqi.nr.single.core.util.SinglePathUtil
 *  nr.single.map.configurations.bean.ISingleMappingConfig
 *  nr.single.map.data.PathUtil
 *  nr.single.map.data.service.SingleMappingService
 */
package nr.single.client.internal.service.upload;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.dataentry.bean.ImportResultObject;
import com.jiuqi.nr.dataentry.bean.UploadParam;
import com.jiuqi.nr.dataentry.funcVerificated.annotation.FuncVerificated;
import com.jiuqi.nr.dataentry.service.IUploadTypeService;
import com.jiuqi.nr.dataentry.util.BatchExportConsts;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import nr.single.client.bean.JIOImportResultObject;
import nr.single.client.service.upload.IUploadJioAfterService;
import nr.single.client.service.upload.IUploadJioFileService;
import nr.single.client.service.upload.IUploadJioMappingService;
import nr.single.map.configurations.bean.ISingleMappingConfig;
import nr.single.map.data.PathUtil;
import nr.single.map.data.service.SingleMappingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="upload_type_jio")
@FuncVerificated(value="uploadJIO")
public class UploadJioServiceImpl
implements IUploadTypeService {
    private static final Logger log = LoggerFactory.getLogger(UploadJioServiceImpl.class);
    @Autowired
    private IUploadJioFileService uploadTypeJioService;
    @Autowired
    private SingleMappingService mappingConfigService;
    @Autowired
    private IUploadJioMappingService jioMappingService;
    @Autowired
    private IUploadJioAfterService jioAfterService;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ImportResultObject upload(File file, UploadParam param, AsyncTaskMonitor asyncTaskMonitor) {
        JIOImportResultObject res = new JIOImportResultObject();
        try {
            String fileLocation = param.getFileLocation();
            if (StringUtils.isEmpty((String)fileLocation)) {
                SimpleDateFormat sfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                fileLocation = sfDate.format(new Date()) + OrderGenerator.newOrder();
            }
            String workPath = this.getJioWorkFilePath(param, BatchExportConsts.UPLOADDIR);
            String path = workPath + BatchExportConsts.SEPARATOR + fileLocation + BatchExportConsts.SEPARATOR;
            File pathFile = new File(SinglePathUtil.normalize((String)path));
            boolean needDeleteDir = false;
            if (!pathFile.exists()) {
                pathFile.mkdirs();
                needDeleteDir = true;
            }
            try {
                String fileName = file.getName();
                String fileFullName = file.getPath();
                this.info(asyncTaskMonitor, "\u5bfc\u5165JIO\u6570\u636e:" + fileName + " \uff0c\u6587\u4ef6\u5927\u5c0f:" + file.length());
                if (StringUtils.isEmpty((String)param.getConfigKey())) {
                    JIOImportResultObject jIOImportResultObject = this.jioMappingService.queryMappingByFile(fileFullName, param);
                    return jIOImportResultObject;
                }
                ISingleMappingConfig mapConfig = this.mappingConfigService.getConfigByKey(param.getConfigKey().toString());
                asyncTaskMonitor.progressAndMessage(0.0, "jio\u5bfc\u5165\u9636\u6bb5");
                res = (JIOImportResultObject)this.uploadTypeJioService.upload(fileName, path, fileFullName, param, asyncTaskMonitor, 0.01, 0.95);
                this.info(asyncTaskMonitor, "\u5bfc\u5165JIO\u6570\u636e\uff1a\u6210\u529f\u5355\u4f4d\u6570\uff1a" + String.valueOf(res.getSuccesssUnitNum()) + ",\u72b6\u6001\uff1a" + res.isSuccess());
                if (res.getSuccesssUnitNum() > 0 && res.getErrorLevel() <= 1) {
                    this.jioAfterService.uploadJioAfterSuccess(res, mapConfig, param, asyncTaskMonitor);
                }
                this.info(asyncTaskMonitor, "\u5bfc\u5165JIO\u6570\u636e\uff1ajio\u5bfc\u5165\u9636\u6bb5\u5b8c\u6210");
            }
            finally {
                if (needDeleteDir) {
                    PathUtil.deleteDir((String)path);
                }
            }
        }
        catch (Exception e) {
            this.error(asyncTaskMonitor, e.getMessage(), e);
        }
        finally {
            if (!StringUtils.isEmpty((String)param.getFilePath()) || file.exists()) {
                // empty if block
            }
        }
        return res;
    }

    private String getJioWorkFilePath(UploadParam param, String oldPath) {
        String workPath = null;
        if (param.getVariableMap().containsKey("JioUploadWorkPath")) {
            workPath = (String)param.getVariableMap().get("JioUploadWorkPath");
        }
        if (StringUtils.isEmpty(workPath)) {
            workPath = oldPath;
        }
        return workPath;
    }

    private void info(AsyncTaskMonitor asyncTaskMonitor, String message) {
        if (asyncTaskMonitor != null && asyncTaskMonitor.getBILogger() != null) {
            asyncTaskMonitor.getBILogger().info(message);
        }
        log.info(message);
    }

    private void error(AsyncTaskMonitor asyncTaskMonitor, String message, Throwable t) {
        if (asyncTaskMonitor != null && asyncTaskMonitor.getBILogger() != null) {
            asyncTaskMonitor.getBILogger().error(message, t);
        }
        log.error(message, t);
    }
}

