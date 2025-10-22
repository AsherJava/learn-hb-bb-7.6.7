/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.bean.ImportResultObject
 *  com.jiuqi.nr.dataentry.bean.UploadParam
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  nr.single.client.internal.service.upload.UploadJioServiceImpl
 */
package com.jiuqi.nr.attachment.transfer.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.attachment.transfer.common.Utils;
import com.jiuqi.nr.attachment.transfer.dto.ImportParamDTO;
import com.jiuqi.nr.attachment.transfer.dto.ImportRecordDTO;
import com.jiuqi.nr.attachment.transfer.exception.TaskException;
import com.jiuqi.nr.attachment.transfer.monitor.SimpleTaskMonitor;
import com.jiuqi.nr.attachment.transfer.monitor.TaskMonitor;
import com.jiuqi.nr.attachment.transfer.task.TransferTask;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.ImportResultObject;
import com.jiuqi.nr.dataentry.bean.UploadParam;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import java.io.File;
import java.util.HashMap;
import nr.single.client.internal.service.upload.UploadJioServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImportTask
extends TransferTask {
    private static final Logger log = LoggerFactory.getLogger(ImportTask.class);
    private final ImportRecordDTO record;
    private final ImportParamDTO importParamDTO;
    ObjectMapper mapper = new ObjectMapper();

    public ImportTask(ImportRecordDTO record, ImportParamDTO importParamDTO) {
        this.id = record.getKey();
        this.record = record;
        this.importParamDTO = importParamDTO;
    }

    public ImportRecordDTO getRecord() {
        return this.record;
    }

    @Override
    public void run(TaskMonitor monitor) {
        log.info("\u5f00\u59cb\u6267\u884c\u5bfc\u5165\u9644\u4ef6\u4efb\u52a1:{}", (Object)this.id);
        try {
            this.createTemp();
            this.importJIO();
        }
        catch (Exception e) {
            throw new TaskException(e);
        }
        finally {
            this.releaseTemp();
        }
        log.info("\u7ed3\u675f\u5bfc\u5165\u9644\u4ef6\u4efb\u52a1:{}", (Object)this.id);
    }

    private void importJIO() {
        ImportResultObject upload;
        String filePath = this.importParamDTO.getFilePath() + File.separator + this.record.getFileName();
        File file = new File(filePath);
        UploadJioServiceImpl jioService = (UploadJioServiceImpl)BeanUtil.getBean(UploadJioServiceImpl.class);
        UploadParam param = new UploadParam();
        param.setTaskKey(this.importParamDTO.getTaskKey());
        param.setConfigKey(this.importParamDTO.getMapping());
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        SchemePeriodLinkDefine schemeLink = runTimeViewController.getSchemePeriodLinkByPeriodAndTask(this.importParamDTO.getPeriod(), this.importParamDTO.getTaskKey());
        param.setFormSchemeKey(schemeLink.getSchemeKey());
        long timeMills = System.currentTimeMillis();
        param.setFileLocation(String.valueOf(timeMills));
        HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
        DimensionValue entity = new DimensionValue();
        entity.setName("MD_ORG");
        entity.setValue("1000");
        DimensionValue periodDim = new DimensionValue();
        periodDim.setName("DATATIME");
        periodDim.setValue(this.importParamDTO.getPeriod());
        dimensionSet.put("MD_ORG", entity);
        dimensionSet.put("DATATIME", periodDim);
        param.setDimensionSet(dimensionSet);
        HashMap<String, String> variable = new HashMap<String, String>();
        variable.put("JioUploadWorkPath", this.getTempPath());
        param.setVariableMap(variable);
        param.setFilePath(filePath);
        SimpleTaskMonitor monitor = new SimpleTaskMonitor();
        try {
            upload = jioService.upload(file, param, (AsyncTaskMonitor)monitor);
        }
        catch (Exception e) {
            throw new TaskException(e);
        }
        if (upload != null && !upload.isSuccess()) {
            try {
                String errorInfo = this.mapper.writeValueAsString((Object)upload);
                throw new TaskException(errorInfo);
            }
            catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String getTempPath() {
        return this.importParamDTO.getFilePath() + File.separator + "JIOIMPORTTEMP" + this.record.getKey();
    }

    private void createTemp() {
        File file = new File(this.getTempPath());
        if (!file.exists()) {
            file.mkdir();
        }
    }

    private void releaseTemp() {
        File file = new File(this.getTempPath());
        Utils.deleteFile(file);
    }
}

