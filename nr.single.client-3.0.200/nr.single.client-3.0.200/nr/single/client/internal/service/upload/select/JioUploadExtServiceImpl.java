/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.np.core.application.NpApplication
 *  com.jiuqi.nr.common.exception.NrCommonException
 *  com.jiuqi.nr.dataentry.asynctask.UploadAsyncTaskExecutor
 *  com.jiuqi.nr.dataentry.bean.IRepeatEntityNode
 *  com.jiuqi.nr.dataentry.bean.IRepeatFormNode
 *  com.jiuqi.nr.dataentry.bean.IRepeatImportParam
 *  com.jiuqi.nr.dataentry.bean.ImportResultObject
 *  com.jiuqi.nr.dataentry.bean.UploadParam
 *  com.jiuqi.nr.dataentry.service.IUploadTypeJioService
 *  com.jiuqi.nr.dataentry.service.JioUploadExtService
 *  com.jiuqi.nr.dataentry.util.BatchExportConsts
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.fileupload.service.FileUploadOssService
 *  nr.single.map.data.bean.RepeatFormNode
 *  nr.single.map.data.bean.RepeatImportParam
 */
package nr.single.client.internal.service.upload.select;

import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.np.core.application.NpApplication;
import com.jiuqi.nr.common.exception.NrCommonException;
import com.jiuqi.nr.dataentry.asynctask.UploadAsyncTaskExecutor;
import com.jiuqi.nr.dataentry.bean.IRepeatEntityNode;
import com.jiuqi.nr.dataentry.bean.IRepeatFormNode;
import com.jiuqi.nr.dataentry.bean.IRepeatImportParam;
import com.jiuqi.nr.dataentry.bean.ImportResultObject;
import com.jiuqi.nr.dataentry.bean.UploadParam;
import com.jiuqi.nr.dataentry.service.IUploadTypeJioService;
import com.jiuqi.nr.dataentry.service.JioUploadExtService;
import com.jiuqi.nr.dataentry.util.BatchExportConsts;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.fileupload.service.FileUploadOssService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import nr.single.client.bean.JIOImportResultObject;
import nr.single.map.data.bean.RepeatFormNode;
import nr.single.map.data.bean.RepeatImportParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JioUploadExtServiceImpl
implements JioUploadExtService {
    private static final Logger logger = LoggerFactory.getLogger(UploadAsyncTaskExecutor.class);
    @Autowired
    private IUploadTypeJioService uploadService;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public IRepeatImportParam getJioRepeatImportParam(UploadParam uploadParam) {
        IRepeatImportParam result = new IRepeatImportParam();
        if (uploadParam.getVariableMap() == null) {
            return result;
        }
        if (!uploadParam.getVariableMap().containsKey("jioNeedSelectImport")) {
            return result;
        }
        if (uploadParam.getVariableMap().containsKey("jioSelectImportParm")) {
            return result;
        }
        logger.info("\u83b7\u53d6JIO\u4e2d\u7684\u5355\u4f4d\u4fe1\u606f");
        FileService fileService = (FileService)BeanUtil.getBean(FileService.class);
        NpApplication npApplication = (NpApplication)BeanUtil.getBean(NpApplication.class);
        FileUploadOssService fileUploadOssService = (FileUploadOssService)BeanUtil.getBean(FileUploadOssService.class);
        ImportResultObject jioResult = null;
        AsyncTaskMonitor asyncTaskMonitor = null;
        String fileInfoKey = "";
        UploadParam param = uploadParam;
        File file = null;
        File pathFile = null;
        try {
            SimpleDateFormat sfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String fileLocation = sfDate.format(new Date()) + OrderGenerator.newOrder();
            String path = BatchExportConsts.UPLOADDIR + BatchExportConsts.SEPARATOR + fileLocation + BatchExportConsts.SEPARATOR;
            if (StringUtils.isNotEmpty((String)param.getFilePath())) {
                PathUtils.validatePathManipulation((String)param.getFilePath());
                jioResult = this.uploadService.upload(param.getFilePath(), path, param, asyncTaskMonitor, 0.1, 0.9);
            } else {
                pathFile = new File(path);
                if (!pathFile.exists()) {
                    pathFile.mkdirs();
                }
                if (StringUtils.isNotEmpty((String)param.getFileKeyOfSOss())) {
                    file = new File(pathFile.getPath() + BatchExportConsts.SEPARATOR + param.getFileNameInfo());
                    try (FileOutputStream fileOutputStream = new FileOutputStream(file);){
                        fileUploadOssService.downloadFileFormTemp(param.getFileKeyOfSOss(), (OutputStream)fileOutputStream);
                        param.setFileLocation(fileLocation);
                        jioResult = this.uploadService.upload(file.getName(), path, param, asyncTaskMonitor, 0.1, 0.9);
                    }
                }
                FileInfo fileInfo = fileService.tempArea().getInfo(param.getFileKey());
                file = new File(pathFile.getPath() + BatchExportConsts.SEPARATOR + fileInfo.getName());
                try (FileOutputStream fileOutputStream = new FileOutputStream(file);){
                    fileService.tempArea().download(fileInfo.getKey(), (OutputStream)fileOutputStream);
                    param.setFileLocation(fileLocation);
                    jioResult = this.uploadService.upload(file.getName(), path, param, asyncTaskMonitor, 0.1, 0.9);
                }
            }
            if (jioResult != null) {
                JIOImportResultObject jioResult2 = (JIOImportResultObject)jioResult;
                result = this.getSelectResult(jioResult2);
            }
        }
        catch (NrCommonException nrCommonException) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + nrCommonException.getMessage(), nrCommonException);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        finally {
            String finalFileInfoKey = fileInfoKey;
            if (StringUtils.isNotEmpty((String)finalFileInfoKey) && StringUtils.isEmpty((String)param.getFileKeyOfSOss())) {
                npApplication.asyncRun(() -> {
                    try {
                        fileService.tempArea().delete(finalFileInfoKey, Boolean.valueOf(false));
                    }
                    catch (Exception e) {
                        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    }
                });
            }
            try {
                if (!StringUtils.isNotEmpty((String)param.getFilePath()) && file != null && file.exists() && !file.delete()) {
                    logger.info("\u6587\u4ef6\u5220\u9664\u5931\u8d25:" + file.getName());
                }
                if (!StringUtils.isNotEmpty((String)param.getFilePath()) && pathFile != null && !pathFile.delete()) {
                    logger.info("\u6587\u4ef6\u5220\u9664\u5931\u8d25" + pathFile.getName());
                }
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
        return result;
    }

    private IRepeatImportParam getSelectResult(JIOImportResultObject jioResult) {
        IRepeatFormNode newItem;
        RepeatImportParam selectParam = jioResult.getJioSelectParm();
        IRepeatImportParam result = new IRepeatImportParam();
        result.setCurNetPeriod(selectParam.getCurNetPeriod());
        result.setFileKey(selectParam.getFileKey());
        result.setFilePath(selectParam.getFilePath());
        result.setFileName(selectParam.getFileName());
        result.setHasOtherPeriod(selectParam.isHasOtherPeriod());
        result.setKeepOldEnityTree(selectParam.isKeepOldEnityTree());
        result.setNetCorpCount(selectParam.getNetCorpCount());
        result.setRepeatByFile(selectParam.isRepeatByFile());
        if (selectParam.getSinglePeriods() != null && !selectParam.getSinglePeriods().isEmpty()) {
            ArrayList singlePeriods = new ArrayList();
            singlePeriods.addAll(selectParam.getSinglePeriods());
            result.setSinglePeriods(singlePeriods);
        }
        if (selectParam.getFormNodes() != null && !selectParam.getFormNodes().isEmpty()) {
            ArrayList<IRepeatFormNode> singleForms = new ArrayList<IRepeatFormNode>();
            for (RepeatFormNode oldItem : selectParam.getFormNodes()) {
                newItem = new IRepeatFormNode();
                newItem.setFormCode(oldItem.getFormCode());
                newItem.setFormKey(oldItem.getFormKey());
                newItem.setFormTitle(oldItem.getFormTitle());
                newItem.setFormType(oldItem.getFormType());
                newItem.setRepeatMode(oldItem.getRepeatMode());
                singleForms.add(newItem);
            }
            result.setFormNodes(singleForms);
        }
        if (selectParam.getEntityNodes() != null && !selectParam.getEntityNodes().isEmpty()) {
            ArrayList<IRepeatFormNode> singleEntitys = new ArrayList<IRepeatFormNode>();
            for (RepeatFormNode oldItem : selectParam.getEntityNodes()) {
                newItem = new IRepeatEntityNode();
                newItem.setNetBBLX(oldItem.getNetBBLX());
                newItem.setNetCode(oldItem.getNetCode());
                newItem.setNetMapCode(oldItem.getNetMapCode());
                newItem.setNetParent(oldItem.getNetParent());
                newItem.setNetQYDM(oldItem.getNetQYDM());
                newItem.setNetTitle(oldItem.getNetTitle());
                newItem.setNetPeriod(oldItem.getNetPeriod());
                newItem.setRepeatMode(oldItem.getRepeatMode());
                newItem.setSingleBBLX(oldItem.getSingleBBLX());
                newItem.setSingleCode(oldItem.getSingleCode());
                newItem.setSingleMapCode(oldItem.getSingleMapCode());
                newItem.setSingleParent(oldItem.getSingleParent());
                newItem.setSingleQYDM(oldItem.getSingleQYDM());
                newItem.setSingleTitle(oldItem.getSingleTitle());
                newItem.setSingleZdm(oldItem.getSingleZdm());
                newItem.setTempQYDM(oldItem.getTempQYDM());
                newItem.setSinglePeriod(oldItem.getSinglePeriod());
                singleEntitys.add(newItem);
            }
            result.setEntityNodes(singleEntitys);
        }
        return result;
    }
}

