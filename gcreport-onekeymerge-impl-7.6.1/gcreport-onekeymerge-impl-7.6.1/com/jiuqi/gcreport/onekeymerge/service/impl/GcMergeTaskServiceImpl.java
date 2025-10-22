/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.common.enums.TaskStateEnum
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.onekeymerge.dto.MergeTaskProcessDTO
 *  com.jiuqi.gcreport.onekeymerge.dto.MergeTaskRecordDTO
 *  com.jiuqi.gcreport.onekeymerge.dto.MergeTaskResultLogDTO
 *  com.jiuqi.gcreport.onekeymerge.enums.MergeTypeEnum
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.temp.dto.Message
 *  com.jiuqi.gcreport.temp.dto.MessageTypeEnum
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncTaskTypeCollecter
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.asynctask.dao.AsyncTaskDao
 *  com.jiuqi.np.asynctask.exception.OutOfQueueException
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.common.params.DimensionValue
 *  org.apache.commons.lang3.ObjectUtils
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.onekeymerge.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.common.enums.TaskStateEnum;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.onekeymerge.dao.MergeTaskDao;
import com.jiuqi.gcreport.onekeymerge.dao.MergeTaskProcessDao;
import com.jiuqi.gcreport.onekeymerge.dao.MergeTaskRelDao;
import com.jiuqi.gcreport.onekeymerge.dao.NrAsyncTaskDao;
import com.jiuqi.gcreport.onekeymerge.dto.MergeTaskProcessDTO;
import com.jiuqi.gcreport.onekeymerge.dto.MergeTaskRecordDTO;
import com.jiuqi.gcreport.onekeymerge.dto.MergeTaskResultLogDTO;
import com.jiuqi.gcreport.onekeymerge.entity.MergeTaskEO;
import com.jiuqi.gcreport.onekeymerge.entity.MergeTaskProcessEO;
import com.jiuqi.gcreport.onekeymerge.entity.MergeTaskRelEO;
import com.jiuqi.gcreport.onekeymerge.enums.MergeTypeEnum;
import com.jiuqi.gcreport.onekeymerge.executor.model.MergeTaskLogsExcelModel;
import com.jiuqi.gcreport.onekeymerge.service.GcMergeTaskService;
import com.jiuqi.gcreport.onekeymerge.service.GcOnekeyMergeService;
import com.jiuqi.gcreport.onekeymerge.task.MergeTaskExecutor;
import com.jiuqi.gcreport.onekeymerge.task.MergeTaskTreeCreator;
import com.jiuqi.gcreport.onekeymerge.task.factory.MergeTaskExecutorFactory;
import com.jiuqi.gcreport.onekeymerge.util.OneKeyMergeUtils;
import com.jiuqi.gcreport.onekeymerge.util.OneKeyTaskPoolEnum;
import com.jiuqi.gcreport.onekeymerge.util.OrgUtils;
import com.jiuqi.gcreport.onekeymerge.util.TaskTypeEnum;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.temp.dto.Message;
import com.jiuqi.gcreport.temp.dto.MessageTypeEnum;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncTaskTypeCollecter;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.asynctask.dao.AsyncTaskDao;
import com.jiuqi.np.asynctask.exception.OutOfQueueException;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.common.params.DimensionValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GcMergeTaskServiceImpl
implements GcMergeTaskService {
    private static final Logger logger = LoggerFactory.getLogger(GcMergeTaskServiceImpl.class);
    @Autowired
    private MergeTaskTreeCreator mergeTaskTreeCreator;
    @Autowired
    private MergeTaskDao mergeTaskDao;
    @Autowired
    private MergeTaskRelDao mergeTaskRelDao;
    @Autowired
    private MergeTaskExecutorFactory mergeTaskExecutorFactory;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private MergeTaskProcessDao mergeTaskProcessDao;
    @Autowired
    private NrAsyncTaskDao nrAsyncTaskDao;
    @Autowired
    private AsyncTaskDao asyncTaskDao;
    @Autowired
    private AsyncTaskTypeCollecter asyncTaskTypeCollecter;
    @Autowired
    private GcOnekeyMergeService onekeyMergeService;

    @Override
    @Async
    public void mergeTask(List<GcOrgCacheVO> orgs, GcActionParamsVO param) {
        NpContextHolder.setContext((NpContext)param.getNpContext());
        String taskTreeGroupId = param.getTaskLogId();
        int totalCount = this.mergeTaskDao.countByGroupId(taskTreeGroupId);
        GcMergeTaskServiceImpl gcMergeTaskService = (GcMergeTaskServiceImpl)SpringBeanUtils.getBean(GcMergeTaskServiceImpl.class);
        MergeTaskProcessEO mergeTaskProcessEO = new MergeTaskProcessEO();
        mergeTaskProcessEO.setId(taskTreeGroupId);
        while (true) {
            try {
                gcMergeTaskService.updateTaskState(taskTreeGroupId, totalCount);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            MergeTaskProcessEO taskProcessEO = (MergeTaskProcessEO)this.mergeTaskProcessDao.selectByEntity((BaseEntity)mergeTaskProcessEO);
            if (TaskStateEnum.STOP.getCode().equals(taskProcessEO.getTaskState()) || TaskStateEnum.ERROR.getCode().equals(taskProcessEO.getTaskState())) {
                gcMergeTaskService.updateStateForWaiting(taskTreeGroupId);
                throw new BusinessRuntimeException("\u4efb\u52a1\u505c\u6b62");
            }
            MergeTaskEO mergeTaskParam = new MergeTaskEO();
            mergeTaskParam.setGroupId(taskTreeGroupId);
            mergeTaskParam.setTaskState(TaskStateEnum.WAITTING.getCode());
            int mergeTaskCount = this.mergeTaskDao.countByEntity((BaseEntity)mergeTaskParam);
            if (mergeTaskCount <= 0) break;
            List<MergeTaskEO> mergeTaskEOList = this.mergeTaskDao.listExecutableTask(taskTreeGroupId);
            Map<String, List<MergeTaskEO>> taskGroupByCodeMap = mergeTaskEOList.stream().collect(Collectors.groupingBy(MergeTaskEO::getTaskCode, Collectors.mapping(Function.identity(), Collectors.toList())));
            gcMergeTaskService.createAsyncTask(taskGroupByCodeMap, param);
            try {
                TimeUnit.SECONDS.sleep(10L);
            }
            catch (InterruptedException interruptedException) {}
        }
        while (true) {
            boolean existExecutingTaskFlag;
            if (!(existExecutingTaskFlag = gcMergeTaskService.updateTaskState(taskTreeGroupId, totalCount))) break;
            try {
                TimeUnit.MILLISECONDS.sleep(3000L);
            }
            catch (InterruptedException interruptedException) {}
        }
        MergeTaskProcessEO processEO = new MergeTaskProcessEO();
        processEO.setId(taskTreeGroupId);
        processEO.setFinishTime(new Date());
        processEO.setProcess(1.0);
        processEO.setTaskState(TaskStateEnum.SUCCESS.getCode());
        this.mergeTaskProcessDao.updateSelective((BaseEntity)processEO);
    }

    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void updateStateForWaiting(String groupId) {
        this.mergeTaskDao.updateStateForWaiting(groupId);
    }

    @Override
    public MergeTaskProcessDTO getProcess(String processId) {
        MergeTaskProcessEO mergeTaskProcessEO = new MergeTaskProcessEO();
        mergeTaskProcessEO.setId(processId);
        MergeTaskProcessEO mergeTaskProcessResult = (MergeTaskProcessEO)this.mergeTaskProcessDao.selectByEntity((BaseEntity)mergeTaskProcessEO);
        MergeTaskProcessDTO mergeTaskProcessDTO = new MergeTaskProcessDTO();
        BeanUtils.copyProperties((Object)mergeTaskProcessResult, mergeTaskProcessDTO);
        return mergeTaskProcessDTO;
    }

    @Override
    public void stopMergeTask(String processId, String taskCode) {
        MergeTaskProcessEO processEO = new MergeTaskProcessEO();
        processEO.setId(processId);
        processEO.setFinishTime(new Date());
        processEO.setTaskState(taskCode);
        this.mergeTaskProcessDao.updateSelective((BaseEntity)processEO);
    }

    @Override
    public MergeTaskResultLogDTO getMergeTaskLogs(String processId) {
        List<MergeTaskEO> mergeTaskEOList = this.mergeTaskDao.listExecuting5ErrorByGroupId(processId);
        ArrayList<Message> mergeTaskInfos = new ArrayList<Message>();
        ArrayList executingLogs = new ArrayList();
        ArrayList errorLogs = new ArrayList();
        Map state2TaskMap = mergeTaskEOList.stream().collect(Collectors.groupingBy(MergeTaskEO::getTaskState, Collectors.mapping(Function.identity(), Collectors.toList())));
        state2TaskMap.forEach((state, tasks) -> {
            if (TaskStateEnum.EXECUTING.getCode().equals(state)) {
                Map org2TaskMap = tasks.stream().collect(Collectors.groupingBy(MergeTaskEO::getOrgId, Collectors.mapping(Function.identity(), Collectors.toList())));
                org2TaskMap.forEach((orgId, mergeTasks) -> {
                    mergeTasks.sort(Comparator.comparing(MergeTaskEO::getTaskCode));
                    for (MergeTaskEO mergeTaskEO : mergeTasks) {
                        StringBuffer builder = new StringBuffer();
                        TaskTypeEnum taskType = TaskTypeEnum.getByCode(mergeTaskEO.getTaskCode());
                        builder.append(mergeTaskEO.getOrgTitle()).append(" | ").append(taskType.getStateInfo()).append(":\u6267\u884c\u4e2d...");
                        Message executingLog = new Message(MessageTypeEnum.INFO);
                        executingLog.setMessage(builder);
                        executingLogs.add(executingLog);
                    }
                });
            } else {
                this.buildLogsResult((List<MergeTaskEO>)tasks, errorLogs);
            }
        });
        MergeTaskProcessEO mergeTaskProcessEO = new MergeTaskProcessEO();
        mergeTaskProcessEO.setId(processId);
        mergeTaskProcessEO = (MergeTaskProcessEO)this.mergeTaskProcessDao.selectByEntity((BaseEntity)mergeTaskProcessEO);
        this.buildTaskInfoLogs(mergeTaskProcessEO, mergeTaskInfos);
        this.buildTaskResultLogs(processId, mergeTaskInfos);
        MergeTaskResultLogDTO mergeTaskResultLogDTO = new MergeTaskResultLogDTO();
        mergeTaskResultLogDTO.setErrorLogs(errorLogs);
        mergeTaskResultLogDTO.setExecutingLogs(executingLogs);
        mergeTaskResultLogDTO.setTaskProcessState(mergeTaskProcessEO.getTaskState());
        mergeTaskResultLogDTO.setMergeTaskInfos(mergeTaskInfos);
        return mergeTaskResultLogDTO;
    }

    private void buildTaskInfoLogs(MergeTaskProcessEO mergeTaskProcessEO, List<Message> mergeTaskInfos) {
        String orgIdStr = mergeTaskProcessEO.getOrgId();
        String dimStr = mergeTaskProcessEO.getDims();
        Map dims = (Map)JsonUtils.readValue((String)dimStr, (TypeReference)new TypeReference<Map<String, DimensionValue>>(){});
        String orgType = ((DimensionValue)dims.get("MD_GCORGTYPE")).getValue();
        String[] orgIdArr = orgIdStr.split(",");
        ArrayList<GcOrgCacheVO> gcOrgCacheVOList = new ArrayList<GcOrgCacheVO>();
        for (String orgId : orgIdArr) {
            GcOrgCacheVO orgByCode = OrgUtils.getOrgByCode(mergeTaskProcessEO.getDataTime(), orgType, orgId);
            gcOrgCacheVOList.add(orgByCode);
        }
        if (!CollectionUtils.isEmpty(gcOrgCacheVOList)) {
            List orgTitles = gcOrgCacheVOList.stream().map(GcOrgCacheVO::getTitle).collect(Collectors.toList());
            String orgTitleStr = String.join((CharSequence)",", orgTitles);
            Message orgMessage = new Message(MessageTypeEnum.INFO);
            StringBuffer orgBuffer = new StringBuffer();
            orgBuffer.append("\u6267\u884c\u5355\u4f4d\uff1a").append(orgTitleStr).append(";");
            orgMessage.setMessage(orgBuffer);
            mergeTaskInfos.add(orgMessage);
        }
        if (!StringUtils.isEmpty((String)mergeTaskProcessEO.getLogInfo())) {
            Message logInfoMessage = new Message(MessageTypeEnum.INFO);
            StringBuffer logInfoBuffer = new StringBuffer();
            logInfoBuffer.append(mergeTaskProcessEO.getLogInfo()).append(";");
            logInfoMessage.setMessage(logInfoBuffer);
            mergeTaskInfos.add(logInfoMessage);
        }
        Message userMessage = new Message(MessageTypeEnum.INFO);
        StringBuffer userBuffer = new StringBuffer();
        userBuffer.append("\u6267\u884c\u4eba\uff1a").append(mergeTaskProcessEO.getUserName()).append(";");
        userMessage.setMessage(userBuffer);
        mergeTaskInfos.add(userMessage);
        if (Objects.nonNull(mergeTaskProcessEO.getCreateTime())) {
            Message benigTimeMessage = new Message(MessageTypeEnum.INFO);
            StringBuffer beginTimeBuffer = new StringBuffer();
            beginTimeBuffer.append("\u6267\u884c\u5f00\u59cb\u65f6\u95f4\uff1a").append(DateUtils.format((Date)mergeTaskProcessEO.getCreateTime(), (String)"yyyy-MM-dd HH:mm:ss")).append(";");
            benigTimeMessage.setMessage(beginTimeBuffer);
            mergeTaskInfos.add(benigTimeMessage);
        }
        Message endTimeMessage = new Message(MessageTypeEnum.INFO);
        StringBuffer endTimeBuffer = new StringBuffer();
        endTimeBuffer.append("\u6267\u884c\u7ed3\u675f\u65f6\u95f4\uff1a");
        if (Objects.nonNull(mergeTaskProcessEO.getFinishTime())) {
            endTimeBuffer.append(DateUtils.format((Date)mergeTaskProcessEO.getFinishTime(), (String)"yyyy-MM-dd HH:mm:ss")).append(";");
        }
        endTimeMessage.setMessage(endTimeBuffer);
        mergeTaskInfos.add(endTimeMessage);
        if (!StringUtils.isEmpty((String)mergeTaskProcessEO.getConfigSchemeName())) {
            Message configSchemeMessage = new Message(MessageTypeEnum.INFO);
            StringBuffer configSchemeBuffer = new StringBuffer();
            configSchemeBuffer.append("\u5408\u5e76\u65b9\u6848\uff1a").append(mergeTaskProcessEO.getConfigSchemeName()).append(";");
            configSchemeMessage.setMessage(configSchemeBuffer);
            mergeTaskInfos.add(configSchemeMessage);
        }
        if (!StringUtils.isEmpty((String)mergeTaskProcessEO.getMergeType())) {
            Message mergeTypeMessage = new Message(MessageTypeEnum.INFO);
            StringBuffer mergeTypeBuffer = new StringBuffer();
            mergeTypeBuffer.append("\u5408\u5e76\u65b9\u5f0f\uff1a");
            MergeTypeEnum mergeTypeEnum = MergeTypeEnum.getEnumByCode((String)mergeTaskProcessEO.getMergeType());
            if (Objects.nonNull(mergeTypeEnum)) {
                mergeTypeBuffer.append(mergeTypeEnum.getTitle()).append(";");
            }
            mergeTypeMessage.setMessage(mergeTypeBuffer);
            mergeTaskInfos.add(mergeTypeMessage);
        }
        String[] taskCodeArr = mergeTaskProcessEO.getTaskCodes().split(",");
        String buildTaskCodeLog = OneKeyMergeUtils.buildTaskCodeLog(new ArrayList<String>(Arrays.asList(taskCodeArr)));
        Message mergeCodeMessage = new Message(MessageTypeEnum.INFO);
        StringBuffer mergeCodeBuffer = new StringBuffer();
        mergeCodeBuffer.append("\u5408\u5e76\u4e8b\u9879\uff1a").append(buildTaskCodeLog).append(";");
        mergeCodeMessage.setMessage(mergeCodeBuffer);
        mergeTaskInfos.add(mergeCodeMessage);
    }

    private void buildTaskResultLogs(String processId, List<Message> mergeTaskInfos) {
        List<Map<String, Object>> stateMaps = this.mergeTaskDao.countState(processId);
        Map<String, List<Map>> taskCode2StateCountMap = stateMaps.stream().collect(Collectors.groupingBy(item -> item.get("TASKCODE").toString()));
        StringBuffer message = new StringBuffer();
        message.append("\u6267\u884c\u7ed3\u679c\uff1a");
        ArrayList<String> sortedTaskCodeList = new ArrayList<String>();
        sortedTaskCodeList.add(TaskTypeEnum.DATAPICK.getCode());
        sortedTaskCodeList.add(TaskTypeEnum.CONVERSION.getCode());
        sortedTaskCodeList.add(TaskTypeEnum.CALC.getCode());
        sortedTaskCodeList.add(TaskTypeEnum.FINISHCALC.getCode());
        for (String taskCode : sortedTaskCodeList) {
            List<Map<String, Object>> maps = taskCode2StateCountMap.get(taskCode);
            if (CollectionUtils.isEmpty(maps)) continue;
            this.buildTaskStateCountMsg(taskCode, maps, message);
        }
        Message taskResultMessage = new Message(MessageTypeEnum.INFO);
        taskResultMessage.setMessage(message);
        mergeTaskInfos.add(taskResultMessage);
    }

    private void buildTaskStateCountMsg(String taskCode, List<Map<String, Object>> taskStateCountList, StringBuffer message) {
        String messageTmp = "%1$s\uff1a\u6210\u529f%2$s\u5bb6\u5355\u4f4d\uff0c\u5931\u8d25%3$s\u5bb6\u5355\u4f4d\uff0c\u672a\u6267\u884c%4$s\u5bb6\u5355\u4f4d\uff1b";
        int sucCount = 0;
        int errCount = 0;
        int unExeCount = 0;
        Map<String, Integer> taskState2CountMap = taskStateCountList.stream().collect(Collectors.toMap(data -> data.get("TASKSTATE").toString(), data -> (int)Double.parseDouble(data.get("COUNT").toString())));
        for (Map.Entry<String, Integer> entry : taskState2CountMap.entrySet()) {
            String taskState = entry.getKey();
            Integer count = entry.getValue();
            if (TaskStateEnum.SUCCESS.getCode().equals(taskState)) {
                sucCount = count;
                continue;
            }
            if (TaskStateEnum.ERROR.getCode().equals(taskState)) {
                errCount = count;
                continue;
            }
            unExeCount += count.intValue();
        }
        String format = String.format(messageTmp, TaskTypeEnum.getByCode(taskCode).getStateInfo(), sucCount, errCount, unExeCount);
        message.append(format);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void createTaskTree(List<GcOrgCacheVO> orgs, GcActionParamsVO param) {
        List<GcOrgCacheVO> filterOrgs = orgs.stream().filter(org -> GcOrgKindEnum.UNIONORG.equals((Object)org.getOrgKind())).collect(Collectors.toList());
        MergeTaskProcessEO mergeTaskProcessEO = OneKeyMergeUtils.buildMergeTaskProcess(filterOrgs, param);
        if (filterOrgs.size() < orgs.size()) {
            List orgList = orgs.stream().filter(org -> !GcOrgKindEnum.UNIONORG.equals((Object)org.getOrgKind())).collect(Collectors.toList());
            String str = orgList.stream().map(gcorgcacheVo -> gcorgcacheVo.getCode() + "|" + gcorgcacheVo.getTitle()).collect(Collectors.joining("\uff0c"));
            mergeTaskProcessEO.setLogInfo("\u975e\u5408\u5e76\u5355\u4f4d\u4e0d\u5141\u8bb8\u6267\u884c\uff0c\u5171" + orgList.size() + "\u5bb6\uff1a" + str);
        }
        this.mergeTaskProcessDao.add((BaseEntity)mergeTaskProcessEO);
        String taskTreeGroupId = this.mergeTaskTreeCreator.createTaskTree(filterOrgs, param);
    }

    @Override
    public List<MergeTaskRecordDTO> getTaskRecord(GcActionParamsVO param) {
        Map dimensionValueMap = DimensionUtils.buildDimensionMap((String)param.getTaskId(), (String)param.getCurrency(), (String)param.getPeriodStr(), (String)param.getOrgType(), (String)param.getOrgId(), (String)param.getSelectAdjustCode());
        List<MergeTaskProcessEO> taskRecords = this.mergeTaskProcessDao.getTaskRecord(param, JsonUtils.writeValueAsString((Object)dimensionValueMap));
        return taskRecords.stream().map(taskRecord -> {
            MergeTaskRecordDTO mergeTaskRecordDTO = new MergeTaskRecordDTO();
            BeanUtils.copyProperties(taskRecord, mergeTaskRecordDTO);
            String[] taskCodeArr = taskRecord.getTaskCodes().split(",");
            String buildTaskCode = OneKeyMergeUtils.buildTaskCodeLog(new ArrayList<String>(Arrays.asList(taskCodeArr)));
            mergeTaskRecordDTO.setTaskCodes(buildTaskCode);
            return mergeTaskRecordDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<MergeTaskLogsExcelModel> exportLogs(String processId) {
        MergeTaskEO mergeTaskParam = new MergeTaskEO();
        mergeTaskParam.setGroupId(processId);
        List mergeTaskEOS = this.mergeTaskDao.selectList((BaseEntity)mergeTaskParam);
        Map groupByOrgMap = mergeTaskEOS.stream().collect(Collectors.groupingBy(MergeTaskEO::getOrgId, Collectors.mapping(Function.identity(), Collectors.toList())));
        ArrayList<MergeTaskLogsExcelModel> mergeTaskLogsExcelModels = new ArrayList<MergeTaskLogsExcelModel>();
        groupByOrgMap.forEach((orgId, mergeTasks) -> mergeTasks.forEach(mergeTaskEO -> {
            MergeTaskLogsExcelModel model = new MergeTaskLogsExcelModel();
            BeanUtils.copyProperties(mergeTaskEO, model);
            model.setTaskTitle(TaskTypeEnum.getByCode(mergeTaskEO.getTaskCode()).getStateInfo());
            model.setTaskState(TaskStateEnum.getByCode((String)mergeTaskEO.getTaskState()).getTitle());
            mergeTaskLogsExcelModels.add(model);
        }));
        return mergeTaskLogsExcelModels;
    }

    @Override
    public String checkOrgInfo(GcActionParamsVO gcActionParamsVO) {
        YearPeriodObject yp = new YearPeriodObject(null, gcActionParamsVO.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)gcActionParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        ArrayList<String> notContainOrgList = new ArrayList<String>();
        for (String orgCode : gcActionParamsVO.getOrgIds()) {
            GcOrgCacheVO org = tool.getOrgByCode(orgCode);
            if (!ObjectUtils.isEmpty((Object)org)) continue;
            notContainOrgList.add(orgCode);
        }
        if (CollectionUtils.isEmpty(notContainOrgList)) {
            return "";
        }
        String result = notContainOrgList.stream().collect(Collectors.joining("\uff0c"));
        return "\u5355\u4f4d" + result + "\u5728\u5f53\u524d\u5355\u4f4d\u7248\u672c\u4e2d\u4e0d\u5b58\u5728\uff0c\u8bf7\u91cd\u65b0\u786e\u8ba4\u3002";
    }

    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void createAsyncTask(Map<String, List<MergeTaskEO>> taskGroupByCodeMap, GcActionParamsVO param) {
        ArrayList<MergeTaskRelEO> insertMergeTaskRelEOs = new ArrayList<MergeTaskRelEO>();
        ArrayList<MergeTaskEO> updateMergeTaskEOs = new ArrayList<MergeTaskEO>();
        block2: for (Map.Entry<String, List<MergeTaskEO>> entry : taskGroupByCodeMap.entrySet()) {
            String taskCode = entry.getKey();
            OneKeyTaskPoolEnum taskPoolEnum = OneKeyTaskPoolEnum.getByTaskCode(taskCode);
            List<MergeTaskEO> taskEOS = entry.getValue();
            Integer freePoolCount = this.getFreeTaskPoolSize(taskPoolEnum.getTaskPool());
            if (freePoolCount == 0) {
                logger.info("\u4efb\u52a1\uff1a" + taskCode + " \u5f02\u6b65\u4efb\u52a1\u961f\u5217\u5df2\u6ee1\uff0c\u6682\u65f6\u505c\u6b62\u521b\u5efa\u4efb\u52a1\u3002");
                continue;
            }
            if (taskEOS.size() > freePoolCount) {
                taskEOS = taskEOS.subList(0, freePoolCount);
            }
            MergeTaskExecutor mergeTaskExecutor = this.mergeTaskExecutorFactory.create(taskCode);
            for (MergeTaskEO mergeTaskEO : taskEOS) {
                GcActionParamsVO gcActionParamsVO = new GcActionParamsVO();
                BeanUtils.copyProperties(param, gcActionParamsVO);
                gcActionParamsVO.setOrgId(mergeTaskEO.getOrgId());
                try {
                    Object asyncTaskParam = mergeTaskExecutor.buildAsyncTaskParam(gcActionParamsVO, mergeTaskEO.getOrgId());
                    String asyncTaskId = mergeTaskExecutor.publishTask(gcActionParamsVO, asyncTaskParam, taskPoolEnum);
                    MergeTaskRelEO mergeTaskRelEO = this.buildMergeTaskRelEO(asyncTaskId, mergeTaskEO);
                    insertMergeTaskRelEOs.add(mergeTaskRelEO);
                    mergeTaskEO.setTaskState(TaskStateEnum.EXECUTING.getCode());
                    mergeTaskEO.setStartTime(new Date());
                    updateMergeTaskEOs.add(mergeTaskEO);
                }
                catch (OutOfQueueException e) {
                    continue block2;
                }
            }
        }
        if (!CollectionUtils.isEmpty(insertMergeTaskRelEOs)) {
            this.mergeTaskRelDao.addBatch(insertMergeTaskRelEOs);
        }
        if (!CollectionUtils.isEmpty(updateMergeTaskEOs)) {
            this.mergeTaskDao.updateBatch(updateMergeTaskEOs);
        }
    }

    private void buildLogsResult(List<MergeTaskEO> tasksGroupByState, List<Message> logs) {
        Map org2TaskMap = tasksGroupByState.stream().collect(Collectors.groupingBy(MergeTaskEO::getOrgId, Collectors.mapping(Function.identity(), Collectors.toList())));
        org2TaskMap.forEach((orgId, mergeTasks) -> {
            for (MergeTaskEO mergeTaskEO : mergeTasks) {
                StringBuilder logInfoBuilder;
                if (StringUtils.isEmpty((String)mergeTaskEO.getTaskData())) continue;
                StringBuffer builder = new StringBuffer();
                TaskTypeEnum taskType = TaskTypeEnum.getByCode(mergeTaskEO.getTaskCode());
                String taskLog = "";
                if (TaskTypeEnum.DATAPICK.equals((Object)taskType)) {
                    logInfoBuilder = new StringBuilder();
                    StringBuilder infoStr = null;
                    if (StringUtils.isEmpty((String)mergeTaskEO.getTaskData())) {
                        infoStr = new StringBuilder("\u4efb\u52a1\u6267\u884c\u5931\u8d25\u3002");
                    } else {
                        try {
                            Map logDetails = (Map)JsonUtils.readValue((String)mergeTaskEO.getTaskData(), Map.class);
                            Object formMessages = logDetails.get("formMessage");
                            if (Objects.isNull(formMessages)) continue;
                            infoStr = new StringBuilder();
                            HashMap messageMap = (HashMap)JsonUtils.readValue((String)JsonUtils.writeValueAsString(formMessages), (TypeReference)new TypeReference<HashMap<String, String>>(){});
                            for (String value : messageMap.values()) {
                                infoStr.append(value);
                            }
                        }
                        catch (Exception e) {
                            infoStr = new StringBuilder("\u4efb\u52a1\u6267\u884c\u5931\u8d25\u3002");
                        }
                    }
                    logInfoBuilder.append((CharSequence)infoStr);
                    taskLog = logInfoBuilder.toString();
                } else if (TaskTypeEnum.CALC.equals((Object)taskType)) {
                    String[] calcLogInfos;
                    logInfoBuilder = new StringBuilder();
                    for (String calcLogInfo : calcLogInfos = mergeTaskEO.getTaskData().split(";")) {
                        if (!calcLogInfo.contains("\u6267\u884c\u5931\u8d25")) continue;
                        logInfoBuilder.append(calcLogInfo).append(";\n");
                    }
                    taskLog = logInfoBuilder.toString();
                } else {
                    taskLog = mergeTaskEO.getTaskData();
                }
                builder.append(mergeTaskEO.getOrgTitle()).append(" | ").append(taskType.getStateInfo()).append(":\u6267\u884c\u5931\u8d25  ").append(taskLog);
                Message errorMessage = new Message(MessageTypeEnum.ERROR);
                errorMessage.setMessage(builder);
                logs.add(errorMessage);
            }
        });
    }

    private MergeTaskRelEO buildMergeTaskRelEO(String asyncTaskId, MergeTaskEO mergeTaskEO) {
        MergeTaskRelEO mergeTaskRelEO = new MergeTaskRelEO();
        mergeTaskRelEO.setId(UUIDUtils.newUUIDStr());
        mergeTaskRelEO.setMergeTaskId(mergeTaskEO.getId());
        mergeTaskRelEO.setAsyncTaskId(asyncTaskId);
        mergeTaskRelEO.setGroupId(mergeTaskEO.getGroupId());
        mergeTaskRelEO.setTaskState(TaskStateEnum.EXECUTING.getCode());
        mergeTaskRelEO.setTaskCode(mergeTaskEO.getTaskCode());
        return mergeTaskRelEO;
    }

    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public boolean updateTaskState(String groupId, int totalCount) {
        HashSet<String> updateSucMergeTaskIds = new HashSet<String>();
        HashSet<String> updateErrMergeTaskIds = new HashSet<String>();
        ArrayList<Object> updateMergeRels = new ArrayList<Object>();
        HashMap<String, String> mergeTaskId2AsyncTaskIdMap = new HashMap<String, String>();
        ArrayList<String> asyncTaskIds = new ArrayList<String>();
        List<MergeTaskRelEO> mergeTaskRelEOS = this.mergeTaskRelDao.listByGroupId(groupId);
        if (CollectionUtils.isEmpty(mergeTaskRelEOS)) {
            return false;
        }
        Map<String, Integer> asyncTaskId2State = this.mergeTaskRelDao.getAsyncTaskId2StateByNP(groupId);
        Map<String, Integer> asyncTaskId2StateByBI = this.mergeTaskRelDao.getAsyncTaskId2StateByBI(groupId);
        asyncTaskId2State.putAll(asyncTaskId2StateByBI);
        for (Object mergeTaskRelEO : mergeTaskRelEOS) {
            Integer asyncTaskState = asyncTaskId2State.get(mergeTaskRelEO.getAsyncTaskId());
            if (null == asyncTaskState) continue;
            if (TaskState.FINISHED.getValue() == asyncTaskState.intValue()) {
                mergeTaskRelEO.setTaskState(TaskStateEnum.SUCCESS.getCode());
                updateMergeRels.add(mergeTaskRelEO);
                updateSucMergeTaskIds.add(mergeTaskRelEO.getMergeTaskId());
                mergeTaskId2AsyncTaskIdMap.put(mergeTaskRelEO.getMergeTaskId(), mergeTaskRelEO.getAsyncTaskId());
                asyncTaskIds.add(mergeTaskRelEO.getAsyncTaskId());
                continue;
            }
            if (TaskState.ERROR.getValue() != asyncTaskState.intValue()) continue;
            if (TaskTypeEnum.CALC.getCode().equals(mergeTaskRelEO.getTaskCode()) || TaskTypeEnum.FINISHCALC.getCode().equals(mergeTaskRelEO.getTaskCode())) {
                this.stopMergeTask(groupId, TaskStateEnum.ERROR.getCode());
            }
            mergeTaskRelEO.setTaskState(TaskStateEnum.ERROR.getCode());
            updateMergeRels.add(mergeTaskRelEO);
            updateErrMergeTaskIds.add(mergeTaskRelEO.getMergeTaskId());
            mergeTaskId2AsyncTaskIdMap.put(mergeTaskRelEO.getMergeTaskId(), mergeTaskRelEO.getAsyncTaskId());
            asyncTaskIds.add(mergeTaskRelEO.getAsyncTaskId());
        }
        if (!CollectionUtils.isEmpty(updateMergeRels)) {
            HashMap<String, Object> queryDetailsMap = new HashMap<String, Object>(asyncTaskIds.size());
            for (String asyncTaskId : asyncTaskIds) {
                Object detail = this.asyncTaskManager.queryDetail(asyncTaskId);
                if (ObjectUtils.isEmpty((Object)detail)) {
                    detail = this.asyncTaskManager.queryResult(asyncTaskId);
                }
                queryDetailsMap.put(asyncTaskId, detail);
            }
            this.mergeTaskRelDao.updateBatch(mergeTaskRelEOS);
            HashSet<String> totalUpdateMergeTaskIds = new HashSet<String>(updateSucMergeTaskIds);
            totalUpdateMergeTaskIds.addAll(updateErrMergeTaskIds);
            List<MergeTaskEO> mergeTaskEOList = this.mergeTaskDao.listByIds(totalUpdateMergeTaskIds);
            Date now = new Date();
            for (MergeTaskEO mergeTaskEO : mergeTaskEOList) {
                if (updateSucMergeTaskIds.contains(mergeTaskEO.getId())) {
                    mergeTaskEO.setTaskState(TaskStateEnum.SUCCESS.getCode());
                } else {
                    mergeTaskEO.setTaskState(TaskStateEnum.ERROR.getCode());
                }
                mergeTaskEO.setFinishTime(now);
                String detail = queryDetailsMap.getOrDefault(mergeTaskId2AsyncTaskIdMap.getOrDefault(mergeTaskEO.getId(), ""), "");
                if (Objects.nonNull(detail)) {
                    mergeTaskEO.setTaskData(ConverterUtils.getAsString((Object)detail));
                    continue;
                }
                mergeTaskEO.setTaskData("\u4efb\u52a1\u6267\u884c\u5931\u8d25\u3002");
            }
            this.mergeTaskDao.updateBatch(mergeTaskEOList);
        }
        long finishedCount = updateSucMergeTaskIds.size() + updateErrMergeTaskIds.size();
        double process = NumberUtils.round((double)((double)finishedCount / (double)totalCount * 0.9), (int)2, (int)1);
        if (finishedCount > 0L && process > 0.0) {
            this.mergeTaskProcessDao.updateProcess(finishedCount, process, groupId);
        }
        return true;
    }

    private Integer getFreeTaskPoolSize(String taskPoolType) {
        Integer queueSize = this.asyncTaskTypeCollecter.getQueueSize(taskPoolType);
        if (queueSize < 0) {
            return -1;
        }
        List taskList = this.asyncTaskDao.queryByTaskPool(taskPoolType, TaskState.WAITING);
        return Math.max(0, queueSize - taskList.size());
    }
}

