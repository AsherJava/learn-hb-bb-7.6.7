/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.webserviceclient.utils.DataIntegrationUtil
 *  com.jiuqi.gcreport.webserviceclient.vo.DataIntegrationtkExecuteParams
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.etl.common.EtlServeEntity
 *  com.jiuqi.nr.etl.common.NrdlTask
 *  com.jiuqi.nr.etl.service.INrDataIntegrationService
 *  com.jiuqi.nr.etl.service.internal.EtlServeEntityDao
 *  com.jiuqi.nr.etl.service.internal.NrdlTaskExecutor
 *  com.jiuqi.va.basedata.service.BaseDataService
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  org.json.JSONObject
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.bde.fetch.impl.syncfetch.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.fetch.impl.entity.GcSyncEtlFetchVO;
import com.jiuqi.gcreport.bde.fetch.impl.handler.FetchDataFormDTO;
import com.jiuqi.gcreport.bde.fetch.impl.syncfetch.service.GcSyncEtlFetchService;
import com.jiuqi.gcreport.webserviceclient.utils.DataIntegrationUtil;
import com.jiuqi.gcreport.webserviceclient.vo.DataIntegrationtkExecuteParams;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.etl.common.EtlServeEntity;
import com.jiuqi.nr.etl.common.NrdlTask;
import com.jiuqi.nr.etl.service.INrDataIntegrationService;
import com.jiuqi.nr.etl.service.internal.EtlServeEntityDao;
import com.jiuqi.nr.etl.service.internal.NrdlTaskExecutor;
import com.jiuqi.va.basedata.service.BaseDataService;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.LockSupport;
import java.util.stream.Collectors;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GcSyncEtlFetchServiceImpl
implements GcSyncEtlFetchService {
    @Autowired
    private INrDataIntegrationService iNrDataIntegrationService;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private BaseDataService baseDataService;
    @Autowired
    private EtlServeEntityDao etlServeEntityDao;
    private Logger logger = LoggerFactory.getLogger(GcSyncEtlFetchServiceImpl.class);
    private static final String MD_BDE_ETL_FORM = "MD_BDE_ETL_FORM";
    private static final String API_JOBS_REMOTE_STATUS = "/api/jobs/remote/status/com.jiuqi.bi.dataintegration/";
    private static final String API_JOBS_REMOTE_LOGS = "/api/jobs/remote/logs/com.jiuqi.bi.dataintegration/";
    private static final String API_JOBS_REMOTE_EXEC_DATAINTEGRATION = "/api/jobs/remote/exec/com.jiuqi.bi.dataintegration/";

    @Override
    @Transactional(noRollbackFor={BusinessRuntimeException.class}, propagation=Propagation.REQUIRES_NEW)
    public void execute(GcSyncEtlFetchVO gcSyncEtlFetchVO) {
        if (Boolean.TRUE.equals(gcSyncEtlFetchVO.getFetchForm().getForceSkipEtlHandle())) {
            return;
        }
        String etlTaskKey = this.getEtlTaskKey(gcSyncEtlFetchVO.getFetchForm());
        if (!StringUtils.isEmpty((String)etlTaskKey) && this.isEtlProcess(gcSyncEtlFetchVO)) {
            DataIntegrationtkExecuteParams etlTaskExecuteParams = this.initEtlTaskExecuteParams(gcSyncEtlFetchVO.getFetchForm(), etlTaskKey);
            this.executeEtlTask(etlTaskExecuteParams);
        }
    }

    private boolean isEtlProcess(GcSyncEtlFetchVO gcSyncEtlFetchVO) {
        EtlServeEntity serveEntity = this.getEtlServeEntity();
        if (serveEntity == null) {
            this.logger.debug("\u672a\u5728\u7cfb\u7edf\u914d\u7f6e\u4e2d\u914d\u7f6eETL\u670d\u52a1\u4fe1\u606f");
            return false;
        }
        String address = serveEntity.getAddress();
        if (StringUtils.isEmpty((String)address)) {
            this.logger.debug("\u6570\u636e\u96c6\u6210\u670d\u52a1\u5730\u5740\u4e3a\u7a7a\uff01");
            return false;
        }
        String userName = serveEntity.getUserName();
        if (StringUtils.isEmpty((String)userName)) {
            this.logger.debug("\u6570\u636e\u96c6\u6210\u670d\u52a1\u7528\u6237\u540d\u4e3a\u7a7a\uff01");
            return false;
        }
        String etlTaskName = this.getEtlTaskName(gcSyncEtlFetchVO.getFetchForm());
        if (StringUtils.isEmpty((String)etlTaskName)) {
            this.logger.debug("\u672a\u5728\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u4e2d\u627e\u5230\u5f53\u524d\u53d6\u6570\u8868ETL\u914d\u7f6e\uff0c\u4e0d\u8c03\u7528ETL\u8fdb\u884c\u6570\u636e\u52a0\u5de5");
        }
        return false;
    }

    private EtlServeEntity getEtlServeEntity() {
        Optional etlServeEntity = this.etlServeEntityDao.getServerInfo();
        if (etlServeEntity.isPresent()) {
            EtlServeEntity serveEntity = (EtlServeEntity)etlServeEntity.get();
            return serveEntity;
        }
        return null;
    }

    private List<String> getEtlFormKeys(FetchDataFormDTO fetchDataFormDTO, String etlTaskName) {
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(fetchDataFormDTO.getTaskId());
        Assert.isNotNull((Object)taskDefine);
        BaseDataDTO condi = new BaseDataDTO();
        condi.setTableName(MD_BDE_ETL_FORM);
        condi.setAuthType(BaseDataOption.AuthType.NONE);
        List basedataList = this.baseDataService.list(condi).getRows();
        List baseDataList = basedataList.stream().filter(baseDataDO -> String.valueOf(baseDataDO.getValueOf("TASKCODE")).equals(taskDefine.getTaskCode())).filter(baseDataDO -> String.valueOf(baseDataDO.getValueOf("ETLTASKNAME")).equals(etlTaskName)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty((Collection)basedataList)) {
            return CollectionUtils.newArrayList((Object[])new String[]{fetchDataFormDTO.getFormId()});
        }
        ArrayList<String> formKeys = new ArrayList<String>();
        for (BaseDataDO fetchConfig : baseDataList) {
            FormDefine formDefine;
            String formCode = String.valueOf(fetchConfig.getValueOf("FORMCODE"));
            try {
                formDefine = this.iRunTimeViewController.queryFormByCodeInScheme(fetchDataFormDTO.getFormSchemeId(), formCode);
            }
            catch (Exception e) {
                this.logger.error("BDEETL\u8c03\u7528\u4e2d\u6839\u636e\u4efb\u52a1\u3010{}\u3011\u62a5\u8868\u65b9\u6848\u3010{}\u3011\u62a5\u8868\u4ee3\u7801\u3010{}\u3011\u672a\u83b7\u53d6\u5230\u62a5\u8868\uff0c\u81ea\u52a8\u8df3\u8fc7", taskDefine.getTaskCode(), fetchDataFormDTO.getFormSchemeId(), formCode);
                continue;
            }
            if (formDefine != null) {
                formKeys.add(formDefine.getKey());
                continue;
            }
            this.logger.error("BDEETL\u8c03\u7528\u4e2d\u6839\u636e\u4efb\u52a1\u3010{}\u3011\u62a5\u8868\u65b9\u6848\u3010{}\u3011\u62a5\u8868\u4ee3\u7801\u3010{}\u3011\u672a\u83b7\u53d6\u5230\u62a5\u8868\uff0c\u81ea\u52a8\u8df3\u8fc7", taskDefine.getTaskCode(), fetchDataFormDTO.getFormSchemeId(), formCode);
        }
        if (CollectionUtils.isEmpty(formKeys)) {
            return CollectionUtils.newArrayList((Object[])new String[]{fetchDataFormDTO.getFormId()});
        }
        return formKeys;
    }

    private String getEtlTaskKey(FetchDataFormDTO fetchDataFormDTO) {
        String etlTaskName = this.getEtlTaskName(fetchDataFormDTO);
        if (StringUtils.isEmpty((String)etlTaskName)) {
            return null;
        }
        return this.getEtlTaskKeyByName(etlTaskName);
    }

    private String getEtlTaskName(FetchDataFormDTO fetchDataFormDTO) {
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(fetchDataFormDTO.getTaskId());
        FormDefine formDefine = this.iRunTimeViewController.queryFormById(fetchDataFormDTO.getFormId());
        if (taskDefine == null || formDefine == null) {
            this.logger.error("BDE-ETL\u8c03\u7528\u4e2d\u6839\u636e\u4efb\u52a1\u3010{}\u3011\u6216\u62a5\u8868\u6807\u8bc6\u3010{}\u3011\u672a\u83b7\u53d6\u5230\u6570\u636e\u9879\uff0c\u81ea\u52a8\u8df3\u8fc7", (Object)fetchDataFormDTO.getTaskId(), (Object)fetchDataFormDTO.getFormId());
            return null;
        }
        BaseDataDTO condi = new BaseDataDTO();
        condi.setTableName(MD_BDE_ETL_FORM);
        condi.setAuthType(BaseDataOption.AuthType.NONE);
        List basedataList = this.baseDataService.list(condi).getRows();
        Optional<BaseDataDO> optionalBaseDataDO = basedataList.stream().filter(baseDataDO -> String.valueOf(baseDataDO.getValueOf("TASKCODE")).equals(taskDefine.getTaskCode())).filter(baseDataDO -> String.valueOf(baseDataDO.getValueOf("FORMCODE")).equals(formDefine.getFormCode())).findFirst();
        if (optionalBaseDataDO.isPresent()) {
            BaseDataDO matchedBaseDataDO = optionalBaseDataDO.get();
            return String.valueOf(matchedBaseDataDO.getValueOf("ETLTASKNAME"));
        }
        return null;
    }

    private String getEtlTaskKeyByName(String etlTaskName) {
        String etlTaskKey = "";
        for (NrdlTask task : this.iNrDataIntegrationService.getAllTask()) {
            if (!task.getTaskName().equals(etlTaskName)) continue;
            etlTaskKey = task.getTaskGuid();
        }
        if (StringUtils.isEmpty((String)etlTaskKey)) {
            throw new BusinessRuntimeException("etl\u670d\u52a1\u672a\u67e5\u8be2\u5230\u540d\u79f0\u4e3a" + etlTaskName + "\u7684\u4efb\u52a1");
        }
        return etlTaskKey;
    }

    private DataIntegrationtkExecuteParams initEtlTaskExecuteParams(FetchDataFormDTO fetchDataFormDTO, String etlTaskKey) {
        DataIntegrationtkExecuteParams etlTaskExecuteParams = new DataIntegrationtkExecuteParams();
        EtlServeEntity serveEntity = DataIntegrationUtil.getEtlServeEntity();
        etlTaskExecuteParams.setUrl(serveEntity.getAddress());
        etlTaskExecuteParams.setUserName(serveEntity.getUserName());
        etlTaskExecuteParams.setPw(DataIntegrationUtil.getPassword((EtlServeEntity)serveEntity));
        etlTaskExecuteParams.setEtlTaskKey(etlTaskKey);
        JSONObject etlTaskParam = new JSONObject();
        etlTaskParam.put("TASKKEY", (Object)fetchDataFormDTO.getTaskId());
        etlTaskParam.put("SCHEMEKEY", (Object)fetchDataFormDTO.getFetchSchemeId());
        etlTaskParam.put("DATATIME", (Object)fetchDataFormDTO.getPeriodScheme());
        Map dimensionSet = (Map)JsonUtils.readValue((String)fetchDataFormDTO.getDimensionSetStr(), (TypeReference)new TypeReference<Map<String, DimensionValue>>(){});
        if (dimensionSet.get("MD_CURRENCY") != null) {
            etlTaskParam.put("CURRENCY", (Object)((DimensionValue)dimensionSet.get("MD_CURRENCY")).getValue());
        }
        if (dimensionSet.get("MD_GCORGTYPE") != null) {
            etlTaskParam.put("ORGTYPE", (Object)((DimensionValue)dimensionSet.get("MD_GCORGTYPE")).getValue());
        }
        etlTaskParam.put("UNITCODE", (Object)fetchDataFormDTO.getUnitCode());
        etlTaskParam.put("FORMKEY", (Object)fetchDataFormDTO.getFormId());
        FormDefine formDefine = this.iRunTimeViewController.queryFormById(fetchDataFormDTO.getFormId());
        etlTaskParam.put("FORMCODE", (Object)formDefine.getFormCode());
        etlTaskParam.put("USER", (Object)NpContextHolder.getContext().getUserName());
        etlTaskExecuteParams.setParam(etlTaskParam);
        return etlTaskExecuteParams;
    }

    public void executeEtlTask(DataIntegrationtkExecuteParams etlTaskExecuteParams) {
        this.logger.info("\u5f00\u59cb\u8fdb\u884cETL\u4efb\u52a1\u6570\u636e\u52a0\u5de5");
        ArrayList executeMessages = CollectionUtils.newArrayList();
        NrdlTaskExecutor.login((String)etlTaskExecuteParams.getUrl(), (String)etlTaskExecuteParams.getUserName(), (String)etlTaskExecuteParams.getPw());
        String instanceGuid = this.getInstanceGuid(etlTaskExecuteParams.getEtlTaskKey(), etlTaskExecuteParams.getParam().toString());
        try {
            long timeStampPoint = new Date().getTime();
            int execStatus = this.getTaskExecStatus(instanceGuid);
            int calCount = 0;
            while (execStatus == 0 || execStatus == -1) {
                long nanos = 2000000000L;
                if (++calCount > 100) {
                    nanos = 10000000000L;
                } else if (calCount > 50) {
                    nanos = 6000000000L;
                } else if (calCount > 10) {
                    nanos = 4000000000L;
                }
                LockSupport.parkNanos(nanos);
                execStatus = (Integer)new JSONObject(NrdlTaskExecutor.doGet((String)(API_JOBS_REMOTE_STATUS + instanceGuid), null)).get("state");
                timeStampPoint = this.checkTaskLogInfo(timeStampPoint, instanceGuid, executeMessages);
            }
            if (execStatus == -2) {
                this.checkEtlExecuteResult(execStatus);
            } else {
                JSONObject json = new JSONObject(NrdlTaskExecutor.doGet((String)(API_JOBS_REMOTE_STATUS + instanceGuid), null));
                this.checkEtlExecuteResult(json.getInt("result"));
            }
            this.checkTaskLogInfo(timeStampPoint, instanceGuid, executeMessages);
            this.logger.info("ETL\u6d41\u7a0b\u65e5\u5fd7 " + executeMessages);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            this.logger.error("ETL\u6d41\u7a0b\u9519\u8bef\u65e5\u5fd7 " + executeMessages);
            throw new BusinessRuntimeException(((Object)executeMessages).toString(), (Throwable)e);
        }
    }

    public long checkTaskLogInfo(long timeStampPoint, String instanceGuid, List<String> executeMessages) {
        try {
            Thread.sleep(1000L);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            this.logger.error("\u4efb\u52a1\u6267\u884c\u5f02\u5e38: {}", (Object)e.getMessage(), (Object)e);
            throw new BusinessRuntimeException("\u4efb\u52a1\u6267\u884c\u5f02\u5e38: " + e.getMessage(), (Throwable)e);
        }
        List<LinkedHashMap<String, Object>> newTaskLogs = this.getTaskLogDetails(timeStampPoint, instanceGuid);
        if (CollectionUtils.isEmpty(newTaskLogs)) {
            return timeStampPoint;
        }
        timeStampPoint = (Long)newTaskLogs.get(newTaskLogs.size() - 1).get("timestamp");
        String errorMsg = "";
        for (LinkedHashMap<String, Object> taskLogDetail : newTaskLogs) {
            String level = (String)taskLogDetail.get("level");
            String message = (String)taskLogDetail.get("message");
            executeMessages.add(message);
            if (!"\u9519\u8bef".equals(level) || !StringUtils.isEmpty((String)errorMsg)) continue;
            errorMsg = message;
        }
        if (!StringUtils.isEmpty((String)errorMsg)) {
            this.logger.error("\u4efb\u52a1\u6267\u884c\u5f02\u5e38" + errorMsg);
            throw new BusinessRuntimeException("\u4efb\u52a1\u6267\u884c\u5f02\u5e38" + errorMsg);
        }
        return timeStampPoint;
    }

    public int getTaskExecStatus(String instanceGuid) {
        try {
            String msg = NrdlTaskExecutor.doGet((String)(API_JOBS_REMOTE_STATUS + instanceGuid), null);
            JSONObject json = new JSONObject(msg);
            if (json.has("error_code")) {
                throw new BusinessRuntimeException("\u6570\u636e\u96c6\u6210\u8c03\u7528\u6267\u884c\u5931\u8d25");
            }
            return json.getInt("state");
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            throw new BusinessRuntimeException("\u4efb\u52a1\u6267\u884c\u4e2d\uff0c\u8fdb\u5ea6\u67e5\u8be2\u5931\u8d25", (Throwable)e);
        }
    }

    private List<LinkedHashMap<String, Object>> getTaskLogDetails(long timeStampPoint, String instanceGuid) {
        String etlTaskLogs = NrdlTaskExecutor.doGet((String)(API_JOBS_REMOTE_LOGS + instanceGuid + "?requireType=afterAll&requireCount=5&timeStampPoint=" + timeStampPoint), null);
        if (etlTaskLogs.contains("error_code")) {
            throw new BusinessRuntimeException("\u6267\u884c\u65e5\u5fd7\u67e5\u8be2\u8c03\u7528\u6267\u884c\u5931\u8d25");
        }
        return (List)JsonUtils.readValue((String)etlTaskLogs, (TypeReference)new TypeReference<List<LinkedHashMap<String, Object>>>(){});
    }

    private String getInstanceGuid(String taskid, String param) {
        try {
            HashMap<String, String> paras = new HashMap<String, String>();
            paras.put("params", param);
            String instanceGuid = NrdlTaskExecutor.doPost((String)(API_JOBS_REMOTE_EXEC_DATAINTEGRATION + taskid), null, paras).replace("\"", "");
            return instanceGuid.replace("instanceGuid:", "").replace("{", "").replace("}", "");
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            throw new BusinessRuntimeException("\u6570\u636e\u96c6\u6210\u8c03\u7528\u5931\u8d25", (Throwable)e);
        }
    }

    public void checkEtlExecuteResult(int statusCode) {
        if (statusCode != 100) {
            String errorMsg = "\u6570\u636e\u96c6\u6210\u8c03\u7528\u6267\u884c\u5931\u8d25";
            switch (statusCode) {
                case 500: {
                    errorMsg = "\u4efb\u52a1\u53c2\u6570\u9519\u8bef\u6216\u670d\u52a1\u5668\u5f02\u5e38";
                    break;
                }
                case -9999: {
                    errorMsg = "\u672a\u8ba4\u8bc1\u901a\u8fc7";
                    break;
                }
                case -100: {
                    errorMsg = "\u4efb\u52a1\u8fd0\u884c\u5931\u8d25";
                    break;
                }
                case 1: {
                    errorMsg = "\u4efb\u52a1\u672a\u5b8c\u6210";
                    break;
                }
                case 2: {
                    errorMsg = "\u4efb\u52a1\u5df2\u53d6\u6d88";
                    break;
                }
                case 3: {
                    errorMsg = "\u4efb\u52a1\u5df2\u7ec8\u6b62";
                    break;
                }
                case 4: {
                    errorMsg = "\u4efb\u52a1\u5f02\u5e38";
                    break;
                }
                case -2: {
                    errorMsg = "\u4efb\u52a1\u6b63\u5728\u53d6\u6d88";
                    break;
                }
            }
            throw new BusinessRuntimeException(errorMsg);
        }
    }
}

