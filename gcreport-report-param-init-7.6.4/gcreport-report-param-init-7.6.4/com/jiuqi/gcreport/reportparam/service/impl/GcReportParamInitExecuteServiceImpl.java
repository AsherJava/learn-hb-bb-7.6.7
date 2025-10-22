/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.expimp.progress.common.ProgressDataImpl
 *  com.jiuqi.common.systemparam.enums.EntInitMsgType
 *  com.jiuqi.common.systemparam.util.EntLocalFileParamImportUtil
 *  com.jiuqi.common.systemparam.util.EntParamInitFileUtil
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSchemeNrService
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingService
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.bean.UploadParam
 *  com.jiuqi.nr.dataentry.service.IUploadService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.designer.planpublish.service.TaskPlanPublishService
 *  com.jiuqi.nr.designer.web.service.ReportTaskService
 *  org.springframework.mock.web.MockMultipartFile
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.reportparam.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.expimp.progress.common.ProgressDataImpl;
import com.jiuqi.common.systemparam.enums.EntInitMsgType;
import com.jiuqi.common.systemparam.util.EntLocalFileParamImportUtil;
import com.jiuqi.common.systemparam.util.EntParamInitFileUtil;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSchemeNrService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingService;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.reportparam.dao.GcReportParamInitDao;
import com.jiuqi.gcreport.reportparam.dto.GcReportParamInfoDTO;
import com.jiuqi.gcreport.reportparam.enums.GcReportParamType;
import com.jiuqi.gcreport.reportparam.eo.GcReportParamInitEO;
import com.jiuqi.gcreport.reportparam.service.GcReportParamInitExecuteService;
import com.jiuqi.gcreport.reportparam.service.impl.GcReportParamInfoUtils;
import com.jiuqi.gcreport.reportparam.vo.GcReportParamProgressVO;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.UploadParam;
import com.jiuqi.nr.dataentry.service.IUploadService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.designer.planpublish.service.TaskPlanPublishService;
import com.jiuqi.nr.designer.web.service.ReportTaskService;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class GcReportParamInitExecuteServiceImpl
implements GcReportParamInitExecuteService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private NRDesignTimeController designTimeController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private TaskPlanPublishService taskPlanPublishService;
    @Autowired
    private ReportTaskService reportTaskService;
    @Autowired
    private GcReportParamInitDao gcReportParamInitDao;
    @Autowired
    private GcReportParamInfoUtils gcReportParamInfoUtils;
    @Autowired
    private IUploadService iUploadService;
    @Autowired
    private FetchSchemeNrService fetchSchemeService;
    @Autowired
    private FetchSettingService fetchSettingService;

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void fileImport(ProgressDataImpl<List<GcReportParamProgressVO>> progressData, Map<String, List<GcReportParamInfoDTO>> fileNameToGcReportParamInfoMap, AtomicBoolean currentPackageSuccess, double singleFileProgress, Double currentParamStartProgress, String fileName) {
        Double finalCurrentParamStartProgress = currentParamStartProgress;
        String fullFilePatch = EntParamInitFileUtil.getFullFilePath((String)("report-param-init" + File.separator + fileName), (String)"gcreport");
        EntLocalFileParamImportUtil.importParam((String)fullFilePatch, (boolean)true, (boolean)true, item -> {
            ((List)progressData.getResult()).add(new GcReportParamProgressVO(item.getMsg(), item.getMsgType()));
            progressData.setProgressValueAndRefresh(finalCurrentParamStartProgress + singleFileProgress * item.getPosition());
            if (EntInitMsgType.ERROR.equals((Object)item.getMsgType())) {
                currentPackageSuccess.set(false);
            }
        });
    }

    private boolean needCommitAll(Map<String, List<GcReportParamInfoDTO>> fileNameToGcReportParamInfoMap, String fileName) {
        String paramType = fileNameToGcReportParamInfoMap.get(fileName).get(0).getParamType();
        return GcReportParamType.GZW_CX.getCode().equals(paramType);
    }

    private boolean needCommitData(Map<String, List<GcReportParamInfoDTO>> fileNameToGcReportParamInfoMap, String fileName) {
        String paramType = fileNameToGcReportParamInfoMap.get(fileName).get(0).getParamType();
        String taskName = fileNameToGcReportParamInfoMap.get(fileName).get(0).getTaskName();
        return GcReportParamType.GZW_CX.getCode().equals(paramType) && !StringUtils.isEmpty((String)taskName);
    }

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void movAndPublishTask(ProgressDataImpl<List<GcReportParamProgressVO>> progressData, String taskGroupKey, DesignTaskDefine taskDefine) {
        if (StringUtils.isEmpty((String)taskDefine.getGroupName())) {
            this.reportTaskService.setGroupForTask(taskDefine.getKey(), Arrays.asList(taskGroupKey));
        }
        ((List)progressData.getResult()).add(new GcReportParamProgressVO("[" + taskDefine.getTitle() + "]\u5b8c\u6210\u8fc1\u79fb\u5206\u7ec4\uff1a", EntInitMsgType.INFO));
        String publishTaskId = UUIDUtils.newUUIDStr();
        try {
            this.taskPlanPublishService.planPublishTask(taskDefine.getKey(), publishTaskId);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u53d1\u5e03\u4efb\u52a1\u5f02\u5e38\uff1a" + e.getMessage(), (Throwable)e);
        }
        ((List)progressData.getResult()).add(new GcReportParamProgressVO("[" + taskDefine.getTitle() + "]\u5b8c\u6210\u4efb\u52a1\u53d1\u5e03\uff1a", EntInitMsgType.INFO));
    }

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void importTaskData(ProgressDataImpl<List<GcReportParamProgressVO>> progressData, List<GcReportParamInfoDTO> gcReportParamInfoDTOS, Map<String, String> taskTitleToKeyMap) {
        try {
            for (GcReportParamInfoDTO paramInit : gcReportParamInfoDTOS) {
                String[] importDataFileName;
                String taskName;
                if (StringUtils.isEmpty((String)paramInit.getTaskDataFileName()) || !taskTitleToKeyMap.containsKey(taskName = paramInit.getTaskName())) continue;
                ((List)progressData.getResult()).add(new GcReportParamProgressVO("\u5f00\u59cb\u5bfc\u5165\u9ed8\u8ba4\u6570\u636e", EntInitMsgType.INFO));
                String taskKey = taskTitleToKeyMap.get(taskName);
                for (String dataFileName : importDataFileName = paramInit.getTaskDataFileName().split(",")) {
                    dataFileName = dataFileName.trim();
                    String filePath = "report-param-init" + File.separator + dataFileName;
                    String[] orgCodeAndDataTime = dataFileName.split("_");
                    UploadParam uploadParam = this.createUploadParam(taskKey, taskName, orgCodeAndDataTime[0], orgCodeAndDataTime[1]);
                    MultipartFile file = this.getMultipartFile(filePath);
                    this.iUploadService.upload(file, uploadParam);
                }
                ((List)progressData.getResult()).add(new GcReportParamProgressVO("\u9ed8\u8ba4\u6570\u636e\u5bfc\u5165\u5b8c\u6210", EntInitMsgType.INFO));
            }
        }
        catch (Exception e) {
            this.logger.error("\u5bfc\u5165\u6570\u636e\u6587\u4ef6\u5f02\u5e38\uff1a" + e.getMessage(), e);
            ((List)progressData.getResult()).add(new GcReportParamProgressVO("\u5bfc\u5165\u6570\u636e\u6587\u4ef6\u5f02\u5e38\uff1a" + e.getMessage(), EntInitMsgType.ERROR));
            progressData.setProgressValueAndRefresh(1.0);
        }
    }

    private MultipartFile getMultipartFile(String filePath) throws IOException {
        String fullFilePath = EntParamInitFileUtil.getFullFilePath((String)filePath, (String)"gcreport");
        try (InputStream resourceInputStream = EntParamInitFileUtil.getResourceInputStream((String)fullFilePath);){
            MockMultipartFile mockMultipartFile = new MockMultipartFile("bbdata.xlsx", "bbdata.xlsx", "text/plain", FileCopyUtils.copyToByteArray(resourceInputStream));
            return mockMultipartFile;
        }
    }

    private UploadParam createUploadParam(String taskKey, String taskName, String orgCode, String dataTime) throws Exception {
        UploadParam uploadParam = new UploadParam();
        HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
        DimensionValue orgDimension = new DimensionValue();
        orgDimension.setName("MD_ORG");
        orgDimension.setType(0);
        orgDimension.setValue(orgCode);
        dimensionSet.put("MD_ORG", orgDimension);
        DimensionValue dataTimeDimension = new DimensionValue();
        dataTimeDimension.setName("DATATIME");
        dataTimeDimension.setType(1);
        dataTimeDimension.setValue(dataTime);
        dimensionSet.put("DATATIME", dataTimeDimension);
        if (!"\u8d22\u52a1\u6307\u6807\u6620\u5c04".equals(taskName)) {
            DimensionValue currencyDimension = new DimensionValue();
            currencyDimension.setName("MD_CURRENCY");
            currencyDimension.setType(0);
            currencyDimension.setValue("CNY");
            dimensionSet.put("MD_CURRENCY", currencyDimension);
        }
        uploadParam.setDimensionSet(dimensionSet);
        uploadParam.setTaskKey(taskKey);
        SchemePeriodLinkDefine schemePeriodLinkDefine = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(dataTime, taskKey);
        uploadParam.setFormSchemeKey(schemePeriodLinkDefine.getSchemeKey());
        HashMap<String, Object> variableMap = new HashMap<String, Object>();
        variableMap.put("writeable", true);
        variableMap.put("DATA_STATE_CHECK_EDIT", "");
        uploadParam.setVariableMap(variableMap);
        return uploadParam;
    }

    @Override
    public void saveOrUpdate(String paramFileName, boolean currentPackageSuccess) {
        GcReportParamInitEO oldEo = this.gcReportParamInitDao.selectByName(paramFileName);
        if (oldEo == null) {
            GcReportParamInitEO eo = new GcReportParamInitEO();
            eo.setName(paramFileName);
            eo.setInitFlag(currentPackageSuccess ? 1 : 0);
            eo.setCreateTime(new Date());
            eo.setUserInfo(NpContextHolder.getContext().getUserName());
            this.gcReportParamInitDao.save(eo);
        } else {
            oldEo.setInitFlag(currentPackageSuccess ? 1 : 0);
            oldEo.setUserInfo(NpContextHolder.getContext().getUserName());
            oldEo.setCreateTime(new Date());
            this.gcReportParamInitDao.update((BaseEntity)oldEo);
        }
    }
}

