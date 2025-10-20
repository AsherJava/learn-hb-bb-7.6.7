/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.HashBasedTable
 *  com.google.common.collect.Table
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerClient
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory
 *  com.jiuqi.dc.taskscheduling.api.vo.TaskParamVO
 *  com.jiuqi.dc.taskscheduling.log.impl.data.TaskCountQueryDTO
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.DataHandleState
 *  com.jiuqi.dc.taskscheduling.log.impl.vo.LogManagerDetailVO
 *  com.jiuqi.gcreport.bde.common.utils.BdeClientUtil
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.utils.FetchTaskUtil
 *  com.jiuqi.gcreport.sdk.util.BdeSystemOptionTool
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.definition.internal.controller2.RunTimeViewController
 *  com.jiuqi.nr.efdc.pojo.EfdcInfo
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.etl.common.EtlServeEntity
 *  com.jiuqi.nr.etl.common.NrdlTask
 *  com.jiuqi.nr.etl.service.INrDataIntegrationService
 *  com.jiuqi.nr.etl.service.internal.EtlServeEntityDao
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.va.basedata.service.BaseDataService
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 */
package com.jiuqi.gcreport.bde.fetch.impl.ctcc.handler;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerClient;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory;
import com.jiuqi.dc.taskscheduling.api.vo.TaskParamVO;
import com.jiuqi.dc.taskscheduling.log.impl.data.TaskCountQueryDTO;
import com.jiuqi.dc.taskscheduling.log.impl.enums.DataHandleState;
import com.jiuqi.dc.taskscheduling.log.impl.vo.LogManagerDetailVO;
import com.jiuqi.gcreport.bde.common.utils.BdeClientUtil;
import com.jiuqi.gcreport.bde.fetch.impl.ctcc.intf.EtlBatchFetchInfo;
import com.jiuqi.gcreport.bde.fetch.impl.ctcc.intf.EtlBatchFetchKey;
import com.jiuqi.gcreport.bde.fetch.impl.ctcc.intf.EtlBatchFetchMessage;
import com.jiuqi.gcreport.bde.fetch.impl.ctcc.intf.EtlFormInfoKey;
import com.jiuqi.gcreport.bde.fetch.impl.ctcc.intf.EtlLoadUnitKey;
import com.jiuqi.gcreport.bde.fetch.impl.intf.IBeforeBatchFetchDataHandler;
import com.jiuqi.gcreport.bde.fetchsetting.impl.utils.FetchTaskUtil;
import com.jiuqi.gcreport.sdk.util.BdeSystemOptionTool;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.definition.internal.controller2.RunTimeViewController;
import com.jiuqi.nr.efdc.pojo.EfdcInfo;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.etl.common.EtlServeEntity;
import com.jiuqi.nr.etl.common.NrdlTask;
import com.jiuqi.nr.etl.service.INrDataIntegrationService;
import com.jiuqi.nr.etl.service.internal.EtlServeEntityDao;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.va.basedata.service.BaseDataService;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CtccBatchEtlLoadDataHandler
implements IBeforeBatchFetchDataHandler {
    @Autowired
    private EtlServeEntityDao etlServeEntityDao;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private BaseDataService baseDataService;
    @Autowired
    private INrDataIntegrationService iNrDataIntegrationService;
    @Autowired
    private RunTimeViewController runTimeViewController;
    @Autowired
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private TaskHandlerFactory taskHandlerFactory;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private INvwaSystemOptionService optionService;
    private static final Logger LOGGER = LoggerFactory.getLogger(CtccBatchEtlLoadDataHandler.class);
    private static final String MD_BDE_ETL_FORM = "MD_BDE_ETL_FORM";
    private static final String FN_UNITCODE_PATTERN = "\u5355\u4f4d\uff1a@@[a-zA-Z0-9_#]+@@\u6267\u884c\u5931\u8d25";
    private static final String TMPL_FORM_KEY = "%1$s|%2$s";

    @Override
    public String getTitle() {
        return "\u7535\u4fe1\u6279\u91cf\u8c03\u7528ETL\u52a0\u8f7d\u6570\u636e";
    }

    @Override
    public List<EfdcInfo> rewriteEfdcInfo(String taskId, List<EfdcInfo> srcEfdcInfoList) {
        BusinessResponseEntity businessResponseEntity;
        if (CollectionUtils.isEmpty(srcEfdcInfoList)) {
            return srcEfdcInfoList;
        }
        EtlServeEntity etlServer = this.findEtlServer();
        if (etlServer == null) {
            return srcEfdcInfoList;
        }
        Table<String, String, NrdlTask> etlFetchForms = this.findEtlFetchForms();
        if (etlFetchForms == null || etlFetchForms.isEmpty()) {
            return srcEfdcInfoList;
        }
        HashMap etlBatchFetchInfoMap = CollectionUtils.newHashMap();
        HashMap fetchFormMap = CollectionUtils.newHashMap();
        EtlBatchFetchKey fetchKey = null;
        for (EfdcInfo efdcInfo : srcEfdcInfoList) {
            fetchKey = this.convert2FetchKey(efdcInfo);
            etlBatchFetchInfoMap.computeIfAbsent(fetchKey, key -> new EtlBatchFetchInfo());
            EntityViewData dwEntity = this.jtableParamService.getDwEntity(efdcInfo.getFormSchemeKey());
            String string = ((DimensionValue)efdcInfo.getDimensionSet().get(dwEntity.getDimensionName())).getValue();
            ((EtlBatchFetchInfo)etlBatchFetchInfoMap.get(fetchKey)).addUnitCode(string);
            String fetchFormKey = this.buildFetchFormKey(efdcInfo);
            HashSet formKeySet = CollectionUtils.newHashSet();
            if (StringUtils.isEmpty((String)efdcInfo.getFormKey())) {
                try {
                    List formDefineList = this.runTimeAuthViewController.queryAllFormDefinesByFormScheme(efdcInfo.getFormSchemeKey());
                    for (FormDefine formDefine : formDefineList) {
                        formKeySet.add(formDefine.getKey());
                    }
                    fetchFormMap.computeIfAbsent(fetchFormKey, key -> formKeySet);
                }
                catch (Exception e) {
                    throw new BusinessRuntimeException(String.format("\u67e5\u8be2\u62a5\u8868\u65b9\u6848\u3010%1$s\u3011\u4e0b\u7684\u62a5\u8868\u51fa\u73b0\u9519\u8bef", efdcInfo.getFormSchemeKey()), (Throwable)e);
                }
            } else {
                String[] formKeyArr = efdcInfo.getFormKey().split(";");
                for (String formKey : formKeyArr) {
                    formKeySet.add(formKey);
                }
                fetchFormMap.computeIfAbsent(fetchFormKey, key -> formKeySet);
            }
            ((EtlBatchFetchInfo)etlBatchFetchInfoMap.get(fetchKey)).addFormKeys((Set)fetchFormMap.get(fetchFormKey));
            if (efdcInfo.getVariableMap() == null) {
                efdcInfo.setVariableMap((Map)CollectionUtils.newHashMap());
            }
            efdcInfo.getVariableMap().put("SKIP_ETL_HANDLE", true);
            efdcInfo.getVariableMap().put("FORMSET", formKeySet);
        }
        ArrayList<EtlBatchFetchMessage> messageList = new ArrayList<EtlBatchFetchMessage>();
        int threshold = Integer.parseInt(this.optionService.findValueById("ETL_LOAD_UNIT_THRESHOLD"));
        for (Map.Entry entry : etlBatchFetchInfoMap.entrySet()) {
            TaskDefine task = this.runTimeViewController.getTask(((EtlBatchFetchKey)entry.getKey()).getTaskKey());
            TableModelDefine tableDefine = this.entityMetaService.getTableModel(FetchTaskUtil.getEntityIdByTaskAndCtx((String)((EtlBatchFetchKey)entry.getKey()).getTaskKey()));
            String orgType = tableDefine.getName();
            HashSet<String> fetchEtlTaskSet = new HashSet<String>();
            for (String formKey : ((EtlBatchFetchInfo)entry.getValue()).getFormKeys()) {
                NrdlTask etlTask;
                FormDefine formDefine = this.runTimeViewController.getForm(formKey, ((EtlBatchFetchKey)entry.getKey()).getSchemeKey());
                if (formDefine == null || (etlTask = (NrdlTask)etlFetchForms.get((Object)task.getTaskCode(), (Object)formDefine.getFormCode())) == null || fetchEtlTaskSet.contains(etlTask.getTaskName())) continue;
                fetchEtlTaskSet.add(etlTask.getTaskName());
                if (CollectionUtils.isEmpty(((EtlBatchFetchInfo)entry.getValue()).getUnitCodes())) continue;
                int count = ((EtlBatchFetchInfo)entry.getValue()).getUnitCodes().size();
                List unitCodeList = ((EtlBatchFetchInfo)entry.getValue()).getUnitCodes().stream().collect(Collectors.toList());
                int group = count % threshold == 0 ? count / threshold : count / threshold + 1;
                for (int j = 0; j < group; ++j) {
                    EtlBatchFetchMessage msg = (EtlBatchFetchMessage)BeanConvertUtil.convert(entry.getKey(), EtlBatchFetchMessage.class, (String[])new String[0]);
                    msg.setOrgType(orgType);
                    msg.setFormCode(formDefine.getFormCode());
                    msg.setTaskCode(task.getTaskCode());
                    msg.setTaskTitle(task.getTitle());
                    msg.setEtlTaskId(etlTask.getTaskGuid());
                    msg.setEtlTaskName(etlTask.getTaskName());
                    msg.setFormKey(formKey);
                    msg.setFormTitle(formDefine.getTitle());
                    ArrayList<String> fetchUnitCodeList = new ArrayList<String>();
                    for (int i = j * threshold; i < count && i < threshold * (j + 1); ++i) {
                        fetchUnitCodeList.add((String)unitCodeList.get(i));
                    }
                    msg.setUnitCodes(fetchUnitCodeList);
                    messageList.add(msg);
                }
            }
        }
        if (CollectionUtils.isEmpty(messageList)) {
            return srcEfdcInfoList;
        }
        TaskHandlerClient taskHandlerClient = this.taskHandlerFactory.getMainTaskHandlerClient();
        Object var11_15 = null;
        TaskParamVO taskParam = new TaskParamVO();
        taskParam.setPreParam(JsonUtils.writeValueAsString(messageList));
        taskParam.setExt_1(taskId);
        try {
            businessResponseEntity = taskHandlerClient.startTaskWithExtInfo("CTCC_ETL_LOAD_DATA", taskParam);
        }
        catch (Exception e) {
            if (e.getMessage().contains("Feign - 500 Read Out Time")) {
                throw new BusinessRuntimeException("BDE\u5730\u5740\u8fde\u63a5\u8d85\u65f6\uff0c\u8bf7\u68c0\u67e5");
            }
            throw new BusinessRuntimeException((Throwable)e);
        }
        if (!businessResponseEntity.isSuccess()) {
            throw new BusinessRuntimeException(businessResponseEntity.getErrorMessage());
        }
        if (StringUtils.isEmpty((String)((String)businessResponseEntity.getData()))) {
            return srcEfdcInfoList;
        }
        long timeOutTime = this.getTimeOutTimeMillis();
        double successVal = 1.0;
        while (!(NumberUtils.compareDouble((double)this.getTaskProcess(taskHandlerClient, (String)businessResponseEntity.getData()), (double)successVal) || timeOutTime != 0L && System.currentTimeMillis() >= timeOutTime)) {
            try {
                Thread.sleep(1000L);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOGGER.error("BDE\u6279\u91cf\u53d6\u6570\u7b49\u5f85ETL\u5904\u7406\u7ed3\u679c\u8fc7\u7a0b\u4e2d\u51fa\u73b0\u9519\u8bef\uff0c\u81ea\u52a8\u8df3\u8fc7");
                return srcEfdcInfoList;
            }
        }
        TaskCountQueryDTO queryDto = new TaskCountQueryDTO();
        queryDto.setRunnerId((String)businessResponseEntity.getData());
        queryDto.setTaskName("CTCC_ETL_LOAD_DATA");
        ArrayList<DataHandleState> handleStateList = new ArrayList<DataHandleState>();
        handleStateList.add(DataHandleState.FAILURE);
        handleStateList.add(DataHandleState.CANCELED);
        queryDto.setDataHandleStates(handleStateList);
        List queryTaskLog = (List)BdeClientUtil.parseResponse((BusinessResponseEntity)this.taskHandlerFactory.getMainTaskHandlerClient().queryTaskLog(queryDto));
        HashMap failEtlLoadMap = CollectionUtils.newHashMap();
        if (!CollectionUtils.isEmpty((Collection)queryTaskLog)) {
            EtlBatchFetchMessage fetchMessage = null;
            EtlLoadUnitKey loadUnitKey = null;
            for (LogManagerDetailVO taskLog : queryTaskLog) {
                fetchMessage = (EtlBatchFetchMessage)JsonUtils.readValue((String)taskLog.getMessage(), EtlBatchFetchMessage.class);
                List<String> failUnitCodeList = this.parseFailUnitByLog(taskLog.getResultLog());
                if (CollectionUtils.isEmpty(failUnitCodeList)) continue;
                for (String unitCode : failUnitCodeList) {
                    loadUnitKey = this.buildLoadUnitKey(fetchMessage, unitCode);
                    failEtlLoadMap.computeIfAbsent(loadUnitKey, key -> new HashSet());
                    ((Set)failEtlLoadMap.get(loadUnitKey)).add(new EtlFormInfoKey(fetchMessage.getFormKey(), fetchMessage.getFormTitle()));
                }
            }
        }
        ArrayList<EfdcInfo> efdcInfoList = new ArrayList<EfdcInfo>(srcEfdcInfoList.size());
        EfdcInfo fetchEfdcInfo = null;
        Set failFormInfoSet = null;
        for (EfdcInfo srcEfdcInfo : srcEfdcInfoList) {
            fetchEfdcInfo = (EfdcInfo)BeanConvertUtil.convert((Object)srcEfdcInfo, EfdcInfo.class, (String[])new String[0]);
            EtlLoadUnitKey loadUnitKey = this.buildLoadUnitKey(fetchEfdcInfo);
            failFormInfoSet = (Set)failEtlLoadMap.get(loadUnitKey);
            if (!CollectionUtils.isEmpty((Collection)failFormInfoSet)) {
                Set formSet = (Set)fetchEfdcInfo.getVariableMap().get("FORMSET");
                ArrayList<String> failFormKeyList = new ArrayList<String>();
                ArrayList<String> failFormTitleList = new ArrayList<String>();
                for (EtlFormInfoKey failFormInfo : failFormInfoSet) {
                    failFormKeyList.add(failFormInfo.getFormKey());
                    failFormTitleList.add(failFormInfo.getFormTitle());
                }
                Collection formKeyCollection = CollectionUtils.diff((Collection)formSet, failFormKeyList);
                if (CollectionUtils.isEmpty((Collection)formKeyCollection)) {
                    fetchEfdcInfo.setFormKey("#");
                    fetchEfdcInfo.getVariableMap().put("ETL_SUCCESS", false);
                    fetchEfdcInfo.getVariableMap().put("ETL_INFO", this.formatErrFormInfo(failFormTitleList));
                    efdcInfoList.add(fetchEfdcInfo);
                    continue;
                }
                fetchEfdcInfo.setFormKey(String.join((CharSequence)";", formKeyCollection));
                fetchEfdcInfo.getVariableMap().put("ETL_SUCCESS", false);
                fetchEfdcInfo.getVariableMap().put("ETL_INFO", this.formatErrFormInfo(failFormTitleList));
                efdcInfoList.add(fetchEfdcInfo);
                continue;
            }
            fetchEfdcInfo.getVariableMap().put("FORMSET", null);
            efdcInfoList.add(fetchEfdcInfo);
        }
        return efdcInfoList;
    }

    private String formatErrFormInfo(List<String> failFormTitleList) {
        StringBuilder errInfo = new StringBuilder();
        errInfo.append(String.format("\u672c\u6b21\u6570\u636e\u52a0\u8f7d\u5931\u8d25%1$d\u5f20\u8868\r\n", failFormTitleList.size()));
        for (String formTitle : failFormTitleList) {
            errInfo.append(String.format("\u62a5\u8868\u3010%1$s\u3011\u6570\u636e\u52a0\u8f7d\u51fa\u9519\r\n", formTitle));
        }
        return errInfo.toString();
    }

    private List<String> parseFailUnitByLog(String resultLog) {
        if (StringUtils.isEmpty((String)resultLog)) {
            return null;
        }
        Pattern UNITCODE_PATTERN = Pattern.compile(FN_UNITCODE_PATTERN);
        Matcher matcher = UNITCODE_PATTERN.matcher(resultLog);
        HashSet unitCodeSet = CollectionUtils.newHashSet();
        String ctxParam = "";
        while (matcher.find()) {
            ctxParam = matcher.group();
            unitCodeSet.add(ctxParam.replace("\u5355\u4f4d\uff1a@@", "").replace("@@\u6267\u884c\u5931\u8d25", ""));
        }
        return unitCodeSet.stream().collect(Collectors.toList());
    }

    private EtlLoadUnitKey buildLoadUnitKey(EtlBatchFetchMessage fetchMessage, String unitCode) {
        return new EtlLoadUnitKey(fetchMessage.getTaskKey(), fetchMessage.getSchemeKey(), fetchMessage.getDataTime(), fetchMessage.getCurrency(), unitCode);
    }

    private EtlLoadUnitKey buildLoadUnitKey(EfdcInfo efdcInfo) {
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(efdcInfo.getFormSchemeKey());
        String unitKey = ((DimensionValue)efdcInfo.getDimensionSet().get(dwEntity.getDimensionName())).getValue();
        return new EtlLoadUnitKey(efdcInfo.getTaskKey(), efdcInfo.getFormSchemeKey(), ((DimensionValue)efdcInfo.getDimensionSet().get("DATATIME")).getValue(), efdcInfo.getDimensionSet().get("MD_CURRENCY") == null ? null : ((DimensionValue)efdcInfo.getDimensionSet().get("MD_CURRENCY")).getValue(), unitKey);
    }

    private Double getTaskProcess(TaskHandlerClient taskHandlerClient, String runnerId) {
        BusinessResponseEntity taskProcess = taskHandlerClient.getTaskProgress(runnerId);
        if (!taskProcess.isSuccess()) {
            throw new BusinessRuntimeException(taskProcess.getErrorMessage());
        }
        return (Double)taskProcess.getData();
    }

    private long getTimeOutTimeMillis() {
        Integer timeOutOptionValue = ConverterUtils.getAsInteger((Object)BdeSystemOptionTool.getOptionValue((String)"BDE_TIMEOUT_TIME"));
        if (timeOutOptionValue == null || timeOutOptionValue <= 0) {
            return 0L;
        }
        return System.currentTimeMillis() + (long)(timeOutOptionValue * 1000);
    }

    public String buildFetchFormKey(EfdcInfo efdcInfo) {
        return String.format(TMPL_FORM_KEY, efdcInfo.getFormSchemeKey(), !StringUtils.isEmpty((String)efdcInfo.getFormKey()) ? efdcInfo.getFormKey() : "#");
    }

    private EtlServeEntity findEtlServer() {
        Optional etlServeEntity = this.etlServeEntityDao.getServerInfo();
        if (etlServeEntity.isPresent()) {
            EtlServeEntity serveEntity = (EtlServeEntity)etlServeEntity.get();
            return serveEntity;
        }
        return null;
    }

    private Table<String, String, NrdlTask> findEtlFetchForms() {
        List allEtlTask;
        BaseDataDTO condi = new BaseDataDTO();
        condi.setTableName(MD_BDE_ETL_FORM);
        condi.setAuthType(BaseDataOption.AuthType.NONE);
        List eltFetchFormBaseDataList = this.baseDataService.list(condi).getRows();
        if (CollectionUtils.isEmpty((Collection)eltFetchFormBaseDataList)) {
            return null;
        }
        try {
            allEtlTask = this.iNrDataIntegrationService.getAllTask();
        }
        catch (Exception e) {
            LOGGER.error("BDE\u6279\u91cf\u53d6\u6570-\u83b7\u53d6ETL\u4efb\u52a1\u5931\u8d25\uff0c\u8df3\u8fc7ETL\u4efb\u52a1\u5904\u7406", e);
            return null;
        }
        if (CollectionUtils.isEmpty((Collection)allEtlTask)) {
            return null;
        }
        HashMap<String, NrdlTask> eltTaskMap = new HashMap<String, NrdlTask>(allEtlTask.size());
        for (NrdlTask task : allEtlTask) {
            eltTaskMap.put(task.getTaskName(), task);
        }
        HashBasedTable etlFetchForms = HashBasedTable.create();
        for (BaseDataDO etlFetchForm : eltFetchFormBaseDataList) {
            String taskCode = (String)etlFetchForm.getValueOf("TASKCODE");
            String formCode = (String)etlFetchForm.getValueOf("FORMCODE");
            String etlTaskName = (String)etlFetchForm.getValueOf("ETLTASKNAME");
            if (eltTaskMap.get(etlTaskName) == null || StringUtils.isEmpty((String)((NrdlTask)eltTaskMap.get(etlTaskName)).getTaskGuid())) {
                LOGGER.error("BDE\u6279\u91cf\u53d6\u6570-\u57fa\u7840\u6570\u636e\u3010{}\u3011\u4e2d\u4efb\u52a1\u3010{}\u3011\u62a5\u8868\u3010{}\u3011\u914d\u7f6e\u7684ETL\u4efb\u52a1\u3010{}\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u6570\u636e\u9879\uff0c\u8c03\u7528ETL\u53d6\u6570\u5df2\u81ea\u52a8\u8df3\u8fc7\uff0c\u8bf7\u68c0\u67e5\u57fa\u7840\u6570\u636e\u914d\u7f6e\u6216ETL\u4efb\u52a1\u914d\u7f6e", MD_BDE_ETL_FORM, taskCode, formCode, etlTaskName);
                continue;
            }
            etlFetchForms.put((Object)taskCode, (Object)formCode, eltTaskMap.get(etlTaskName));
        }
        return etlFetchForms;
    }

    private EtlBatchFetchKey convert2FetchKey(EfdcInfo efdcInfo) {
        Assert.isNotEmpty((Map)efdcInfo.getDimensionSet());
        Assert.isNotNull(efdcInfo.getDimensionSet().get("DATATIME"));
        EtlBatchFetchKey etlBatchFetchKey = new EtlBatchFetchKey(efdcInfo.getTaskKey(), efdcInfo.getFormSchemeKey(), ((DimensionValue)efdcInfo.getDimensionSet().get("DATATIME")).getValue(), efdcInfo.getDimensionSet().get("MD_CURRENCY") == null ? null : ((DimensionValue)efdcInfo.getDimensionSet().get("MD_CURRENCY")).getValue());
        return etlBatchFetchKey;
    }

    @Override
    public int getOrder() {
        return 1;
    }
}

