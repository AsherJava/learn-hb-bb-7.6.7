/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.period.PeriodLanguage
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.bean.BatchExportInfo
 *  com.jiuqi.nr.dataentry.model.BatchDimensionParam
 *  com.jiuqi.nr.dataentry.util.BatchExportConsts
 *  com.jiuqi.nr.dataentry.util.DataEntryUtil
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  nr.single.client.bean.JioExportData
 *  nr.single.client.service.export.IExportJioTaskDataService
 */
package com.jiuqi.nr.attachment.transfer.task;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.period.PeriodLanguage;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.attachment.transfer.common.Utils;
import com.jiuqi.nr.attachment.transfer.dao.IGenerateRecordDao;
import com.jiuqi.nr.attachment.transfer.dto.AttachmentRecordDTO;
import com.jiuqi.nr.attachment.transfer.dto.GenerateParamDTO;
import com.jiuqi.nr.attachment.transfer.exception.TaskException;
import com.jiuqi.nr.attachment.transfer.log.AttachmentLogHelper;
import com.jiuqi.nr.attachment.transfer.monitor.SimpleTaskMonitor;
import com.jiuqi.nr.attachment.transfer.monitor.TaskMonitor;
import com.jiuqi.nr.attachment.transfer.task.TransferTask;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.BatchExportInfo;
import com.jiuqi.nr.dataentry.model.BatchDimensionParam;
import com.jiuqi.nr.dataentry.util.BatchExportConsts;
import com.jiuqi.nr.dataentry.util.DataEntryUtil;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import nr.single.client.bean.JioExportData;
import nr.single.client.service.export.IExportJioTaskDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenerateTask
extends TransferTask {
    private static final Logger log = LoggerFactory.getLogger(GenerateTask.class);
    private final GenerateParamDTO generateParamDTO;
    private final AttachmentRecordDTO recordDTO;

    public GenerateTask(AttachmentRecordDTO recordDTO, GenerateParamDTO generateParamDTO) {
        this.recordDTO = recordDTO;
        this.id = recordDTO.getKey();
        this.generateParamDTO = generateParamDTO;
    }

    public AttachmentRecordDTO getRecord() {
        return this.recordDTO;
    }

    @Override
    public void run(TaskMonitor taskMonitor) {
        log.info("\u5f00\u59cb\u6267\u884c\u751f\u6210\u9644\u4ef6\u4efb\u52a1:{}", (Object)this.id);
        try {
            String workPath = this.createTempPath();
            String filePath = this.generateJIO(taskMonitor, workPath);
            this.renameFile(filePath);
        }
        catch (Exception e) {
            throw new TaskException(e);
        }
        finally {
            this.releaseWorkPath();
        }
        log.info("\u7ed3\u675f\u6267\u884c\u751f\u6210\u9644\u4ef6\u4efb\u52a1:{}", (Object)this.id);
    }

    private void releaseWorkPath() {
        String workPath = this.getWorkPath();
        File file = new File(workPath);
        Utils.deleteFile(file);
    }

    private String getWorkPath() {
        return this.generateParamDTO.getRealFilePath() + File.separator + this.recordDTO.getKey();
    }

    private String createTempPath() {
        String workPath = this.getWorkPath();
        File file = new File(workPath);
        if (!file.exists()) {
            file.mkdir();
        }
        return workPath;
    }

    private String generateJIO(TaskMonitor taskMonitor, String workPath) {
        JioExportData exportData;
        IExportJioTaskDataService jioService = (IExportJioTaskDataService)BeanUtil.getBean(IExportJioTaskDataService.class);
        BatchExportInfo info = this.buildExportInfo(workPath);
        SimpleTaskMonitor monitor = new SimpleTaskMonitor();
        List<BatchDimensionParam> dimensionInfo = this.getBatchDimensionParams();
        ArrayList<String> periodList = new ArrayList<String>();
        periodList.add(this.generateParamDTO.getPeriod());
        ArrayList formKeys = new ArrayList();
        String dateDir = "";
        if (StringUtils.isNotEmpty((String)dimensionInfo.get(0).getDate())) {
            String date = this.getPeriodTitle(info.getContext().getFormSchemeKey(), dimensionInfo.get(0).getDate());
            dateDir = date + BatchExportConsts.SEPARATOR;
        }
        ArrayList datas = new ArrayList();
        try {
            exportData = jioService.ExportBathchDataByPeriods(info, (AsyncTaskMonitor)monitor, dimensionInfo, periodList, formKeys, dateDir, datas);
        }
        catch (Exception e) {
            throw new TaskException(e);
        }
        if (exportData != null) {
            return exportData.getFileLocation();
        }
        return null;
    }

    private boolean renameFile(String sourceFilePath) {
        File sourceFile = new File(sourceFilePath);
        if (!sourceFile.exists()) {
            return false;
        }
        IGenerateRecordDao generateRecordDao = (IGenerateRecordDao)BeanUtil.getBean(IGenerateRecordDao.class);
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("AR_SIZE", sourceFile.length());
        generateRecordDao.updateField(this.getId(), param);
        File targetFile = this.tryRename(0);
        return sourceFile.renameTo(targetFile);
    }

    private File tryRename(int index) {
        String fileName = this.recordDTO.getFileName();
        String realName = fileName.substring(0, fileName.length() - 4);
        String suffix = index == 0 ? "" : index + "";
        File targetFile = new File(this.generateParamDTO.getRealFilePath() + File.separator + realName + suffix + ".jio");
        if (targetFile.exists()) {
            return this.tryRename(++index);
        }
        return targetFile;
    }

    private String getPeriodTitle(String formSchemeKey, String period) {
        IRunTimeViewController runtimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        PeriodEngineService periodEngineService = (PeriodEngineService)BeanUtil.getBean(PeriodEngineService.class);
        FormSchemeDefine formScheme = runtimeViewController.getFormScheme(formSchemeKey);
        IPeriodProvider periodProvider = periodEngineService.getPeriodAdapter().getPeriodProvider(formScheme.getDateTime());
        PeriodWrapper periodWrapper = new PeriodWrapper(period);
        if (DataEntryUtil.isChinese()) {
            periodWrapper.setLanguage(PeriodLanguage.Chinese);
        } else {
            periodWrapper.setLanguage(PeriodLanguage.English);
        }
        return periodProvider.getPeriodTitle(periodWrapper);
    }

    private List<BatchDimensionParam> getBatchDimensionParams() {
        ArrayList<BatchDimensionParam> dimensionInfo = new ArrayList<BatchDimensionParam>();
        BatchDimensionParam dimensionParam = new BatchDimensionParam();
        ArrayList list = new ArrayList(this.recordDTO.getEntityKeys().size());
        HashMap<String, DimensionValue> periodDim = new HashMap<String, DimensionValue>();
        DimensionValue periodDimValue = new DimensionValue();
        periodDimValue.setName("DATATIME");
        periodDimValue.setValue(this.generateParamDTO.getPeriod());
        periodDim.put("DATATIME", periodDimValue);
        for (String entityKey : this.recordDTO.getEntityKeys()) {
            HashMap<String, DimensionValue> dimensionMap = new HashMap<String, DimensionValue>();
            DimensionValue entityDimValue = new DimensionValue();
            entityDimValue.setName("MD_ORG");
            entityDimValue.setValue(entityKey);
            dimensionMap.put("MD_ORG", entityDimValue);
            dimensionMap.put("DATATIME", periodDimValue);
            AttachmentLogHelper.debug("\u7ef4\u5ea6\u96c6\u5408\u8bbe\u7f6e\u5355\u4f4d\uff1a{}", entityKey);
            list.add(dimensionMap);
        }
        dimensionParam.setList(list);
        dimensionParam.setDate("DATATIME");
        dimensionInfo.add(dimensionParam);
        return dimensionInfo;
    }

    private BatchExportInfo buildExportInfo(String workPath) {
        BatchExportInfo info = new BatchExportInfo();
        JtableContext jtableContext = new JtableContext();
        jtableContext.setTaskKey(this.generateParamDTO.getTaskKey());
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        SchemePeriodLinkDefine schemeLink = runTimeViewController.getSchemePeriodLinkByPeriodAndTask(this.generateParamDTO.getPeriod(), this.generateParamDTO.getTaskKey());
        jtableContext.setFormSchemeKey(schemeLink.getSchemeKey());
        HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
        DimensionValue entity = new DimensionValue();
        entity.setName("MD_ORG");
        String entityStr = String.join((CharSequence)";", this.recordDTO.getEntityKeys());
        AttachmentLogHelper.debug("\u751f\u6210jio\u8bbe\u7f6e\u7684\u5355\u4f4d\u4fe1\u606f\u4e3a\uff1a{}", entityStr);
        entity.setValue(entityStr);
        dimensionSet.put("MD_ORG", entity);
        DimensionValue period = new DimensionValue();
        period.setName("DATATIME");
        period.setValue(this.generateParamDTO.getPeriod());
        dimensionSet.put("DATATIME", period);
        jtableContext.setDimensionSet(dimensionSet);
        info.setContext(jtableContext);
        info.setLocation(workPath);
        info.setConfigKey(this.generateParamDTO.getMapping());
        info.setExportEnclosure(true);
        return info;
    }
}

