/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.dataentry.bean.UploadParam
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.single.core.file.SingleFile
 *  nr.single.map.configurations.bean.ISingleMappingConfig
 *  nr.single.map.configurations.bean.MappingConfig
 *  nr.single.map.configurations.bean.SingleConfigInfo
 *  nr.single.map.data.facade.SingleFileTaskInfo
 *  nr.single.map.data.service.SingleJioFileService
 *  nr.single.map.data.service.SingleMappingService
 */
package nr.single.client.internal.service.upload;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.dataentry.bean.UploadParam;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.single.core.file.SingleFile;
import java.util.ArrayList;
import java.util.List;
import nr.single.client.bean.JIOImportResultObject;
import nr.single.client.bean.JioMatchResult;
import nr.single.client.service.upload.IUploadJioMappingService;
import nr.single.map.configurations.bean.ISingleMappingConfig;
import nr.single.map.configurations.bean.MappingConfig;
import nr.single.map.configurations.bean.SingleConfigInfo;
import nr.single.map.data.facade.SingleFileTaskInfo;
import nr.single.map.data.service.SingleJioFileService;
import nr.single.map.data.service.SingleMappingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UploadJioMappingServiceImpl
implements IUploadJioMappingService {
    private static final Logger log = LoggerFactory.getLogger(UploadJioMappingServiceImpl.class);
    @Autowired
    private SingleMappingService mappingConfigService;
    @Autowired
    private SingleJioFileService jioService;
    @Autowired
    private IRunTimeViewController runtimeView;

    @Override
    public JIOImportResultObject queryMappingByFile(String jioFile, UploadParam param) {
        JIOImportResultObject res = new JIOImportResultObject();
        SingleFileTaskInfo taskInfo = null;
        try {
            taskInfo = this.jioService.getTaskInfoByJioFile(jioFile);
        }
        catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            res.setMessage("\u89e3\u6790JIO\u6587\u4ef6\u5931\u8d25\uff01");
        }
        if (taskInfo != null) {
            this.queryMappingByTaskInfo(taskInfo, param, res);
        }
        return res;
    }

    @Override
    public JIOImportResultObject queryMappingByTaskDir(String taskDir, UploadParam param) {
        JIOImportResultObject res = new JIOImportResultObject();
        SingleFileTaskInfo taskInfo = null;
        try {
            SingleFile singleFile = this.jioService.getSingleFileByTaskDir(taskDir);
            taskInfo = this.jioService.getTaskInfoFromSingle(singleFile);
        }
        catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            res.setMessage("\u89e3\u6790JIO\u6587\u4ef6\u5931\u8d25\uff01");
        }
        if (taskInfo != null) {
            this.queryMappingByTaskInfo(taskInfo, param, res);
        }
        return res;
    }

    @Override
    public void queryMappingByTaskInfo(SingleFileTaskInfo taskInfo, UploadParam param, JIOImportResultObject res) {
        ArrayList<JioMatchResult> resultList = new ArrayList<JioMatchResult>();
        StringBuilder configSb = new StringBuilder();
        if (null != taskInfo && StringUtils.isNotEmpty((String)taskInfo.getSingleTaskFlag())) {
            log.info("JIO\u4efb\u52a1\u6807\u8bc6\uff1a" + taskInfo.getSingleTaskFlag() + ",\u6587\u4ef6\u6807\u8bc6\uff1a" + taskInfo.getSingleFileFlag() + ",\u65f6\u671f\u7c7b\u578b\uff1a" + taskInfo.getSingleTaskPeriod());
            ArrayList singleConfigList = new ArrayList();
            List allMappingInReport = this.mappingConfigService.getAllMappingInReport(param.getFormSchemeKey());
            FormSchemeDefine formScheme = this.runtimeView.getFormScheme(param.getFormSchemeKey());
            if (formScheme == null) {
                log.info("\u62a5\u8868\u65b9\u6848\u4e0d\u5b58\u5728\uff1a" + param.getFormSchemeKey());
            }
            configSb.append("\u62a5\u8868\u65b9\u6848\uff1a" + param.getFormSchemeKey() + "\u5b58\u5728" + String.valueOf(allMappingInReport.size()) + "\u4e2a\u6620\u5c04\u65b9\u6848\u6570\uff1b");
            for (SingleConfigInfo singleConfigInfo : allMappingInReport) {
                if (null == singleConfigInfo) continue;
                configSb.append("\u6620\u5c04\u65b9\u6848\uff1a" + singleConfigInfo.getConfigName() + ",\u4efb\u52a1\u6807\u8bc6\uff1a" + singleConfigInfo.getTaskFlag() + ",\u6587\u4ef6\u6807\u8bc6\uff1a" + singleConfigInfo.getFileFlag() + ";");
                if (null == taskInfo.getSingleTaskFlag() || !taskInfo.getSingleTaskFlag().equals(singleConfigInfo.getTaskFlag())) continue;
                this.addMatchMapScheme(singleConfigInfo, resultList);
            }
            if (resultList.size() == 0 && singleConfigList.size() > 0) {
                for (SingleConfigInfo singleConfigInfo : allMappingInReport) {
                    if (!StringUtils.isNotEmpty((String)taskInfo.getSingleFileFlag()) || null == taskInfo.getSingleTaskFlag() || !taskInfo.getSingleFileFlag().equals(singleConfigInfo.getFileFlag())) continue;
                    this.addMatchMapScheme(singleConfigInfo, resultList);
                }
            }
            res.setJioTaskFlag(taskInfo.getSingleTaskFlag());
            res.setJioFileFlag(taskInfo.getSingleFileFlag());
            res.setJioTaskPeriod(taskInfo.getSingleTaskPeriod());
            res.setJioTaskTitle(taskInfo.getSingleTaskTitle());
        }
        if (resultList.size() > 0) {
            res.setSuccess(true);
            res.setJioMatchs(resultList);
            if (taskInfo != null) {
                log.info("\u5339\u914d\u5230\u6620\u5c04\u5173\u7cfb\uff01\u4e2a\u6570\uff1a" + resultList.size() + "\u5176\u4e2dJIO\u4efb\u52a1\u6807\u8bc6\uff1a" + taskInfo.getSingleTaskPeriod() + ",\u6587\u4ef6\u6807\u8bc6\uff1a" + taskInfo.getSingleFileFlag());
            }
            if (configSb.length() > 0) {
                log.info(configSb.toString());
            }
        } else {
            res.setSuccess(false);
            res.setMessage("\u65e0\u5bf9\u5e94\u7684\u6620\u5c04\u5173\u7cfb\uff01");
            if (taskInfo != null) {
                log.info("\u65e0\u5bf9\u5e94\u7684\u6620\u5c04\u5173\u7cfb\uff01\u5176\u4e2dJIO\u4efb\u52a1\u6807\u8bc6\uff1a" + taskInfo.getSingleTaskPeriod() + ",\u6587\u4ef6\u6807\u8bc6\uff1a" + taskInfo.getSingleFileFlag());
            }
            if (configSb.length() > 0) {
                log.info(configSb.toString());
            }
        }
    }

    private void addMatchMapScheme(SingleConfigInfo singleConfigInfo, List<JioMatchResult> resultList) {
        JioMatchResult one = new JioMatchResult();
        one.setConfigKey(singleConfigInfo.getConfigKey());
        one.setName(singleConfigInfo.getConfigName());
        one.setCode(singleConfigInfo.getConfigCode());
        one.setSingleFileFlag(singleConfigInfo.getFileFlag());
        one.setSingleTaskFlag(singleConfigInfo.getTaskFlag());
        resultList.add(one);
        ISingleMappingConfig singleConfig = this.mappingConfigService.getConfigByKeyAndType(singleConfigInfo.getConfigKey(), 1);
        if (singleConfig != null) {
            MappingConfig config = singleConfig.getConfig();
            if (config != null && config.getArithmetic() != null && !config.getArithmetic().isEmpty()) {
                one.setAutoCalc(true);
                one.setCalcSchemeKeys(String.join((CharSequence)";", config.getArithmetic()));
            } else {
                one.setAutoCalc(false);
            }
        }
        log.info("\u5339\u914d\u5230\u6620\u5c04\u65b9\u6848\uff1a" + one.getName() + "," + one.getCode() + ",\u5173\u8054\u5355\u673a\u7248\u4efb\u52a1\uff1a" + one.getSingleTaskFlag() + ",\u6587\u4ef6\u6807\u8bc6 \uff1a" + one.getSingleFileFlag());
    }
}

