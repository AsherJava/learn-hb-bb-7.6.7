/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.base.intf.FetchResultDim
 *  com.jiuqi.bde.bizmodel.execute.dto.FetchFloatRowDTO
 *  com.jiuqi.bde.bizmodel.execute.intf.FetchDataRequestDTO
 *  com.jiuqi.bde.bizmodel.execute.util.FloatRegionAnalyzeDimType
 *  com.jiuqi.bde.bizmodel.impl.orgmapping.service.IOrgMappingServiceProvider
 *  com.jiuqi.bde.common.constant.RequestSourceTypeEnum
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.dto.fetch.init.FetchFormDTO
 *  com.jiuqi.bde.common.dto.fetch.init.FetchInitTaskDTO
 *  com.jiuqi.bde.common.dto.fetch.init.FetchRegionDTO
 *  com.jiuqi.bde.common.dto.fetch.request.FetchRequestDTO
 *  com.jiuqi.bde.common.dto.fetch.result.FetchResultDTO
 *  com.jiuqi.bde.common.dto.fetch.result.FloatRegionResultDTO
 *  com.jiuqi.bde.common.util.BdeCommonUtil
 *  com.jiuqi.bde.common.util.LogUtil
 *  com.jiuqi.bde.log.enums.FetchDimType
 *  com.jiuqi.bde.log.enums.FetchTaskType
 *  com.jiuqi.bde.log.fetch.dto.FetchItemLogDTO
 *  com.jiuqi.bde.log.fetch.service.FetchTaskLogService
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.common.exception.CheckRuntimeException
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerClient
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory
 *  com.jiuqi.dc.taskscheduling.core.msg.TaskHandleMsg
 *  com.jiuqi.dc.taskscheduling.log.impl.data.TaskCountQueryDTO
 *  com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskItemLogEO
 *  com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskLogEO
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.DataHandleState
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType
 *  com.jiuqi.dc.taskscheduling.log.impl.util.TaskInfoUtil
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.apache.shiro.util.ThreadContext
 */
package com.jiuqi.bde.fetch.impl.request.service.impl;

import com.jiuqi.bde.base.intf.FetchResultDim;
import com.jiuqi.bde.bizmodel.execute.dto.FetchFloatRowDTO;
import com.jiuqi.bde.bizmodel.execute.intf.FetchDataRequestDTO;
import com.jiuqi.bde.bizmodel.execute.util.FloatRegionAnalyzeDimType;
import com.jiuqi.bde.bizmodel.impl.orgmapping.service.IOrgMappingServiceProvider;
import com.jiuqi.bde.common.constant.RequestSourceTypeEnum;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.dto.fetch.init.FetchFormDTO;
import com.jiuqi.bde.common.dto.fetch.init.FetchInitTaskDTO;
import com.jiuqi.bde.common.dto.fetch.init.FetchRegionDTO;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestDTO;
import com.jiuqi.bde.common.dto.fetch.result.FetchResultDTO;
import com.jiuqi.bde.common.dto.fetch.result.FloatRegionResultDTO;
import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.bde.common.util.LogUtil;
import com.jiuqi.bde.fetch.impl.request.service.FetchDataAnalyzeFloatRegionService;
import com.jiuqi.bde.fetch.impl.request.service.FetchDataRequestService;
import com.jiuqi.bde.fetch.impl.request.service.impl.FetchDataResultServiceImpl;
import com.jiuqi.bde.fetch.impl.request.util.FetchDataUtil;
import com.jiuqi.bde.log.enums.FetchDimType;
import com.jiuqi.bde.log.enums.FetchTaskType;
import com.jiuqi.bde.log.fetch.dto.FetchItemLogDTO;
import com.jiuqi.bde.log.fetch.service.FetchTaskLogService;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.common.exception.CheckRuntimeException;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerClient;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory;
import com.jiuqi.dc.taskscheduling.core.msg.TaskHandleMsg;
import com.jiuqi.dc.taskscheduling.log.impl.data.TaskCountQueryDTO;
import com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskItemLogEO;
import com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskLogEO;
import com.jiuqi.dc.taskscheduling.log.impl.enums.DataHandleState;
import com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType;
import com.jiuqi.dc.taskscheduling.log.impl.util.TaskInfoUtil;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.apache.shiro.util.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FetchDataRequestServiceImpl
implements FetchDataRequestService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FetchDataRequestServiceImpl.class);
    @Autowired
    private FetchTaskLogService fetchTaskLogService;
    @Autowired
    private FetchDataAnalyzeFloatRegionService floatRegionService;
    @Autowired
    private FetchDataResultServiceImpl fetchDataResultService;
    @Autowired
    private IOrgMappingServiceProvider orgMappingProvider;
    @Autowired
    private INvwaSystemOptionService optionService;
    @Autowired
    private FetchTaskLogService fetchLogService;
    @Autowired
    private TaskHandlerFactory taskHandlerFactory;

    @Override
    public Boolean doInit(FetchInitTaskDTO fetchInitTaskDTO) {
        this.doBasicCheck(fetchInitTaskDTO);
        String runnerId = fetchInitTaskDTO.getRequestRunnerId();
        String taskName = fetchInitTaskDTO.getRequestSourceType();
        DcTaskLogEO taskInfo = TaskInfoUtil.createTaskInfo((String)taskName);
        taskInfo.setId(runnerId);
        taskInfo.setMessage(JsonUtils.writeValueAsString((Object)fetchInitTaskDTO));
        taskInfo.setResultLog(null);
        taskInfo.setExt_1("#");
        taskInfo.setExt_2(fetchInitTaskDTO.getUnitCode());
        this.fetchTaskLogService.insertTaskLog(taskInfo);
        return true;
    }

    @Override
    public FetchResultDTO doFetch(FetchRequestDTO orgnFetchRequestDTO) {
        Integer unExecuteCount;
        FetchDataRequestDTO fetchRequestDTO = this.doBasicCheck(orgnFetchRequestDTO);
        this.doAnalyzeContext(fetchRequestDTO);
        this.doRecordLog(fetchRequestDTO);
        TaskHandleMsg taskHandleMsg = new TaskHandleMsg(FetchTaskType.NR_FETCH_BDE_EXECUTE.getCode(), fetchRequestDTO.getFetchContext().getRegionId(), fetchRequestDTO.getRequestInstcId(), fetchRequestDTO.getRequestTaskId(), JsonUtils.writeValueAsString((Object)fetchRequestDTO), 0, fetchRequestDTO.getRequestRunnerId());
        TaskHandlerClient taskHandlerClient = this.taskHandlerFactory.getMainTaskHandlerClient();
        BusinessResponseEntity startSubTask = taskHandlerClient.startSubTask(FetchTaskType.NR_FETCH_BDE_EXECUTE.getCode(), taskHandleMsg);
        TaskCountQueryDTO countQueryDTO = new TaskCountQueryDTO();
        countQueryDTO.setRunnerId(fetchRequestDTO.getRequestRunnerId());
        countQueryDTO.setInstanceIdList((Collection)startSubTask.getData());
        countQueryDTO.setDataHandleStates((Collection)CollectionUtils.newArrayList((Object[])new DataHandleState[]{DataHandleState.EXECUTING, DataHandleState.UNEXECUTE}));
        while ((unExecuteCount = (Integer)taskHandlerClient.countTask(countQueryDTO).getData()) != null && unExecuteCount != 0) {
            try {
                Thread.sleep(BdeCommonUtil.getExecuteSleepTime());
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new BusinessRuntimeException("\u7ebf\u7a0b\u7b49\u5f85\u5931\u8d25");
            }
        }
        this.fetchLogService.updateTaskFinished(fetchRequestDTO.getRequestRunnerId());
        FetchItemLogDTO fetchLog = this.fetchLogService.queryFailTask(fetchRequestDTO.getRequestRunnerId());
        if (fetchLog == null) {
            return this.getFetchResult(fetchRequestDTO);
        }
        if (!StringUtils.isEmpty((String)fetchLog.getLog())) {
            FetchInitTaskDTO requestDTO = this.fetchTaskLogService.getLogVo(fetchRequestDTO.getRequestRunnerId());
            LOGGER.error(this.getFetchMessage(requestDTO) + "\u53d6\u6570\u5931\u8d25\uff0c\u3010{}\u3011\u8be6\u7ec6\u4fe1\u606f\uff1a{}", (Object)JsonUtils.writeValueAsString((Object)fetchRequestDTO), (Object)fetchLog.getLog());
            this.fetchTaskLogService.updateTaskItemResultById(fetchRequestDTO.getRequestTaskId(), DataHandleState.FAILURE, "\u53d6\u6570\u5931\u8d25");
            throw new BusinessRuntimeException(fetchLog.getLog());
        }
        return this.getFetchResult(fetchRequestDTO);
    }

    @Override
    public List<Map<String, Object>> penetrateTable(FetchRequestDTO orgnFetchRequestDTO) {
        Integer unExecuteCount;
        orgnFetchRequestDTO.setRequestTaskId(UUIDUtils.newHalfGUIDStr());
        FetchDataRequestDTO fetchRequestDTO = this.doBasicCheck(orgnFetchRequestDTO);
        this.doAnalyzeContext(fetchRequestDTO);
        this.doRecordLog(fetchRequestDTO);
        if (fetchRequestDTO.getFloatSetting() == null || StringUtils.isEmpty((String)fetchRequestDTO.getFloatSetting().getQueryType())) {
            this.fetchTaskLogService.updateTaskItemResultById(fetchRequestDTO.getRequestTaskId(), DataHandleState.SUCCESS, "\u53d6\u6570\u5b8c\u6210");
            this.fetchLogService.updateTaskFinished(fetchRequestDTO.getRequestRunnerId());
            return CollectionUtils.newArrayList();
        }
        fetchRequestDTO.setRouteNum(Integer.valueOf(BdeCommonUtil.getRouteNum()));
        TaskHandleMsg taskHandleMsg = new TaskHandleMsg(FetchTaskType.NR_FETCH_BDE_EXECUTE.getCode(), fetchRequestDTO.getFetchContext().getRegionId(), fetchRequestDTO.getRequestInstcId(), fetchRequestDTO.getRequestTaskId(), JsonUtils.writeValueAsString((Object)fetchRequestDTO), 0, fetchRequestDTO.getRequestRunnerId());
        TaskHandlerClient taskHandlerClient = this.taskHandlerFactory.getMainTaskHandlerClient();
        BusinessResponseEntity startSubTask = taskHandlerClient.startSubTask(FetchTaskType.NR_FETCH_BDE_EXECUTE.getCode(), taskHandleMsg);
        TaskCountQueryDTO countQueryDTO = new TaskCountQueryDTO();
        countQueryDTO.setRunnerId(fetchRequestDTO.getRequestRunnerId());
        countQueryDTO.setInstanceIdList((Collection)startSubTask.getData());
        countQueryDTO.setDataHandleStates((Collection)CollectionUtils.newArrayList((Object[])new DataHandleState[]{DataHandleState.EXECUTING, DataHandleState.UNEXECUTE}));
        while ((unExecuteCount = (Integer)taskHandlerClient.countTask(countQueryDTO).getData()) != null && unExecuteCount != 0) {
            try {
                Thread.sleep(BdeCommonUtil.getExecuteSleepTime());
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new BusinessRuntimeException("\u7ebf\u7a0b\u7b49\u5f85\u5931\u8d25");
            }
        }
        this.fetchLogService.updateTaskFinished(fetchRequestDTO.getRequestRunnerId());
        FetchItemLogDTO fetchLog = this.fetchLogService.queryFailTask(fetchRequestDTO.getRequestRunnerId());
        if (fetchLog == null) {
            return this.getFloatTableResult(fetchRequestDTO);
        }
        if (!StringUtils.isEmpty((String)fetchLog.getLog())) {
            FetchInitTaskDTO requestDTO = this.fetchTaskLogService.getLogVo(fetchRequestDTO.getRequestRunnerId());
            LOGGER.error(this.getFetchMessage(requestDTO) + "\u53d6\u6570\u5931\u8d25\uff0c\u3010{}\u3011\u8be6\u7ec6\u4fe1\u606f\uff1a{}", (Object)JsonUtils.writeValueAsString((Object)fetchRequestDTO), (Object)fetchLog.getLog());
            throw new BusinessRuntimeException(fetchLog.getLog());
        }
        return this.getFloatTableResult(fetchRequestDTO);
    }

    private List<Map<String, Object>> getFloatTableResult(FetchDataRequestDTO fetchRequestDTO) {
        FloatRegionResultDTO floatResultList = this.fetchDataResultService.getFloatResults(fetchRequestDTO);
        FetchResultDim fetchResultDim = new FetchResultDim(fetchRequestDTO.getRequestTaskId(), fetchRequestDTO.getFetchContext().getFormId(), fetchRequestDTO.getFetchContext().getRegionId(), fetchRequestDTO.getRouteNum());
        List<Map<String, Object>> floatRowList = this.fetchDataResultService.getFloatRowResultsWithType(fetchResultDim);
        boolean floatRowEmpty = CollectionUtils.isEmpty(floatRowList);
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        Map floatColumns = floatResultList.getFloatColumns();
        List rowDatas = floatResultList.getRowDatas();
        for (int i = 0; i < rowDatas.size(); ++i) {
            Object[] rowData = (Object[])rowDatas.get(i);
            HashMap<String, Object> rowMap = new HashMap<String, Object>();
            result.add(rowMap);
            for (String columnCode : floatColumns.keySet()) {
                rowMap.put(columnCode, rowData[(Integer)floatColumns.get(columnCode)]);
            }
            if (floatRowEmpty) continue;
            rowMap.putAll(floatRowList.get(i));
        }
        result = this.cleanZeroRecord(result);
        return result;
    }

    private List<Map<String, Object>> cleanZeroRecord(List<Map<String, Object>> result) {
        if (CollectionUtils.isEmpty(result)) {
            return result;
        }
        if (!"1".equals(this.optionService.findValueById("CLEAN_ZERO_RECORDS"))) {
            return result;
        }
        ArrayList<Map<String, Object>> rowDatas = new ArrayList<Map<String, Object>>(result.size());
        boolean rowContainsNum = false;
        boolean rowNumAllZero = false;
        for (Map<String, Object> row : result) {
            rowContainsNum = false;
            rowNumAllZero = true;
            for (Object col : row.values()) {
                rowContainsNum = rowContainsNum || FetchDataUtil.valIsNum(col);
                rowNumAllZero = rowNumAllZero && FetchDataUtil.valIsZero(col);
            }
            if (rowContainsNum && rowNumAllZero) continue;
            rowDatas.add(row);
        }
        return rowDatas;
    }

    private String getFetchMessage(FetchInitTaskDTO requestDTO) {
        if (requestDTO == null) {
            return "";
        }
        StringBuffer message = new StringBuffer();
        if (!StringUtils.isEmpty((String)requestDTO.getUnitCode())) {
            message.append("\u5355\u4f4d\u4ee3\u7801\uff1a").append(requestDTO.getUnitCode()).append("\u3001");
        }
        if (!StringUtils.isEmpty((String)requestDTO.getUnitCode())) {
            message.append("\u5355\u4f4d\u540d\u79f0\uff1a").append(requestDTO.getUnitCode()).append("\u3001");
        }
        if (!StringUtils.isEmpty((String)requestDTO.getPeriodScheme())) {
            message.append("\u65f6\u671f\uff1a").append(requestDTO.getPeriodScheme()).append("\u3001");
        }
        if (message.length() > 0) {
            message.delete(message.length() - 1, message.length());
        }
        return message.toString();
    }

    private FetchResultDTO getFetchResult(FetchDataRequestDTO fetchRequestDTO) {
        FetchResultDTO fetchResult = this.fetchDataResultService.getFetchResult(fetchRequestDTO);
        this.fetchTaskLogService.updateTaskItemResultById(fetchRequestDTO.getRequestTaskId(), DataHandleState.SUCCESS, "\u53d6\u6570\u5b8c\u6210");
        this.cleanResultTable(fetchRequestDTO);
        return fetchResult;
    }

    private void cleanResultTable(FetchDataRequestDTO fetchRequestDTO) {
        ((FetchDataResultServiceImpl)ApplicationContextRegister.getBean(this.fetchDataResultService.getClass())).cleanResultTable(fetchRequestDTO.getRequestTaskId(), fetchRequestDTO.getFetchContext().getFormId(), fetchRequestDTO.getFetchContext().getRegionId(), fetchRequestDTO.getRouteNum(), fetchRequestDTO.getFloatSetting() != null && !StringUtils.isEmpty((String)fetchRequestDTO.getFloatSetting().getQueryType()));
    }

    public boolean doAnalyzeFloatRegion(FetchDataRequestDTO fetchRequestDTO) {
        DcTaskItemLogEO floatRegionAnalyzeItem = TaskInfoUtil.createTaskItemInfo((String)"FLOAT_REGION_ANALYZE", (String)fetchRequestDTO.getRequestInstcId(), (String)fetchRequestDTO.getRequestTaskId(), (IDimType)FloatRegionAnalyzeDimType.getInstance(), (String)FetchDataUtil.getDimCode(fetchRequestDTO.getFetchContext().getFormId(), fetchRequestDTO.getFetchContext().getRegionId()));
        floatRegionAnalyzeItem.setStartTime(new Date());
        floatRegionAnalyzeItem.setRunnerId(fetchRequestDTO.getRequestRunnerId());
        try {
            ThreadContext.put((Object)"SQLLOGID_KEY", (Object)floatRegionAnalyzeItem.getId());
            ArrayList floatRegionAnalyzeItemList = CollectionUtils.newArrayList();
            floatRegionAnalyzeItemList.add(floatRegionAnalyzeItem);
            this.fetchTaskLogService.insertTaskItemLog(floatRegionAnalyzeItem);
            FetchFloatRowDTO fetchFloatRowDTO = this.floatRegionService.analyzeFloatRegion(fetchRequestDTO);
            if (fetchFloatRowDTO == null) {
                this.fetchTaskLogService.updateTaskItemResultById(floatRegionAnalyzeItem.getId(), DataHandleState.SUCCESS, "\u6d6e\u52a8\u533a\u57df\u89e3\u6790\u5b8c\u6210");
                return false;
            }
            FetchResultDim fetchResultDim = new FetchResultDim(fetchRequestDTO.getRequestTaskId(), fetchRequestDTO.getFetchContext().getFormId(), fetchRequestDTO.getFetchContext().getRegionId(), fetchRequestDTO.getRouteNum());
            this.fetchDataResultService.insertFloatRowResult(fetchResultDim, fetchFloatRowDTO);
        }
        catch (Exception e) {
            this.fetchTaskLogService.updateTaskItemResultById(floatRegionAnalyzeItem.getId(), DataHandleState.FAILURE, "\u6d6e\u52a8\u533a\u57df\u89e3\u6790\u5931\u8d25\uff1a" + LogUtil.getExceptionStackStr((Throwable)e));
            throw new CheckRuntimeException("\u6d6e\u52a8\u533a\u57df\u89e3\u6790\u5931\u8d25\uff1a" + e.getMessage(), (Throwable)e);
        }
        this.fetchTaskLogService.updateTaskItemResultById(floatRegionAnalyzeItem.getId(), DataHandleState.SUCCESS, "\u6d6e\u52a8\u533a\u57df\u89e3\u6790\u5b8c\u6210");
        return true;
    }

    private void doBasicCheck(FetchInitTaskDTO fetchInitTaskDTO) {
        FetchDataUtil.assertIsNotNull(fetchInitTaskDTO, "\u521d\u59cb\u5316\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a", new Object[0]);
        FetchDataUtil.assertIsNotEmpty(fetchInitTaskDTO.getRequestRunnerId(), "RunnerId\u4e0d\u80fd\u4e3a\u7a7a", new Object[0]);
        FetchDataUtil.assertIsNotEmpty(fetchInitTaskDTO.getRequestSourceType(), "\u8bf7\u6c42\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a", new Object[0]);
        FetchDataUtil.assertIsNotEmpty(fetchInitTaskDTO.getUnitCode(), "\u7ec4\u7ec7\u673a\u6784\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a", new Object[0]);
        FetchDataUtil.assertIsNotEmpty(fetchInitTaskDTO.getPeriodScheme(), "\u671f\u95f4\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a", new Object[0]);
        FetchDataUtil.assertIsNotEmpty(fetchInitTaskDTO.getStartDateStr(), "\u5f00\u59cb\u65e5\u671f\u4e0d\u80fd\u4e3a\u7a7a", new Object[0]);
        FetchDataUtil.assertIsNotEmpty(fetchInitTaskDTO.getEndDateStr(), "\u5f00\u59cb\u65e5\u671f\u4e0d\u80fd\u4e3a\u7a7a", new Object[0]);
        FetchDataUtil.assertIsNotEmpty(fetchInitTaskDTO.getFormSchemeId(), "\u62a5\u8868\u65b9\u6848\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a", new Object[0]);
        FetchDataUtil.assertIsNotEmpty(fetchInitTaskDTO.getFormSchemeTitle(), "\u62a5\u8868\u65b9\u6848\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a", new Object[0]);
        FetchDataUtil.assertIsNotEmpty(fetchInitTaskDTO.getFetchSchemeId(), "\u53d6\u6570\u65b9\u6848\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a", new Object[0]);
        FetchDataUtil.assertIsNotEmpty(fetchInitTaskDTO.getFetchSchemeTitle(), "\u53d6\u6570\u65b9\u6848\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a", new Object[0]);
        FetchDataUtil.assertIsNotEmpty(fetchInitTaskDTO.getTaskId(), "\u62a5\u8868\u4efb\u52a1\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a", new Object[0]);
        FetchDataUtil.assertIsNotEmpty(fetchInitTaskDTO.getTaskTitle(), "\u62a5\u8868\u4efb\u52a1\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a", new Object[0]);
        FetchDataUtil.assertIsNotEmpty(fetchInitTaskDTO.getFetchForms(), "\u53d6\u6570\u62a5\u8868\u8303\u56f4\u4e0d\u80fd\u4e3a\u7a7a", new Object[0]);
        FetchDataUtil.assertIsNotEmpty(fetchInitTaskDTO.getUserName(), "\u6267\u884c\u4eba\u4e0d\u80fd\u4e3a\u7a7a", new Object[0]);
        RequestSourceTypeEnum.fromCode((String)fetchInitTaskDTO.getRequestSourceType());
        OrgMappingDTO orgMappingDTO = this.orgMappingProvider.getByCode(fetchInitTaskDTO.getBblx()).getOrgMapping(fetchInitTaskDTO.getUnitCode());
        if (orgMappingDTO == null) {
            if (fetchInitTaskDTO.getBblx() == null) {
                throw new BusinessRuntimeException(String.format("\u6839\u636e\u62a5\u8868\u5355\u4f4d\u3010%1$s\u3011\u83b7\u53d6\u7ec4\u7ec7\u673a\u6784\u6620\u5c04\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5BDE\u670d\u52a1\u5355\u4f4d\u6620\u5c04\u914d\u7f6e", fetchInitTaskDTO.getUnitCode()));
            }
            throw new BusinessRuntimeException(String.format("\u6839\u636e\u62a5\u8868\u7c7b\u578b\u3010%1$s\u3011\u62a5\u8868\u5355\u4f4d\u4ee3\u7801\u3010%2$s\u3011\u83b7\u53d6\u7ec4\u7ec7\u673a\u6784\u6620\u5c04\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5BDE\u670d\u52a1\u5355\u4f4d\u6620\u5c04\u914d\u7f6e", fetchInitTaskDTO.getBblx(), fetchInitTaskDTO.getUnitCode()));
        }
    }

    public FetchDataRequestDTO doBasicCheck(FetchRequestDTO orgnFetchRequestDTO) {
        FetchDataRequestDTO fetchRequestDTO;
        FetchDataUtil.assertIsNotNull(orgnFetchRequestDTO, "\u521d\u59cb\u5316\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a", new Object[0]);
        FetchDataUtil.assertIsNotEmpty(orgnFetchRequestDTO.getRequestSourceType(), "\u8bf7\u6c42\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a", new Object[0]);
        FetchDataUtil.assertIsNotNull(orgnFetchRequestDTO.getFetchContext(), "\u53d6\u6570\u4e0a\u4e0b\u6587\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a", new Object[0]);
        FetchDataUtil.assertIsNotEmpty(orgnFetchRequestDTO.getFetchContext().getUnitCode(), "\u7ec4\u7ec7\u673a\u6784\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a", new Object[0]);
        FetchDataUtil.assertIsNotEmpty(orgnFetchRequestDTO.getFetchContext().getStartDateStr(), "\u5f00\u59cb\u65e5\u671f\u4e0d\u80fd\u4e3a\u7a7a", new Object[0]);
        FetchDataUtil.assertIsNotEmpty(orgnFetchRequestDTO.getFetchContext().getEndDateStr(), "\u5f00\u59cb\u65e5\u671f\u4e0d\u80fd\u4e3a\u7a7a", new Object[0]);
        RequestSourceTypeEnum requestSourceType = RequestSourceTypeEnum.fromCode((String)orgnFetchRequestDTO.getRequestSourceType());
        if (requestSourceType == RequestSourceTypeEnum.NR_FETCH || requestSourceType == RequestSourceTypeEnum.BUDGET_FETCH) {
            FetchDataUtil.assertIsNotEmpty(orgnFetchRequestDTO.getRequestRunnerId(), "RunnerId\u4e0d\u80fd\u4e3a\u7a7a", new Object[0]);
            FetchDataUtil.assertIsNotEmpty(orgnFetchRequestDTO.getRequestInstcId(), "InstcId\u4e0d\u80fd\u4e3a\u7a7a", new Object[0]);
            FetchDataUtil.assertIsNotEmpty(orgnFetchRequestDTO.getFetchContext().getFormSchemeId(), "\u62a5\u8868\u65b9\u6848\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a", new Object[0]);
            FetchDataUtil.assertIsNotEmpty(orgnFetchRequestDTO.getFetchContext().getFetchSchemeId(), "\u53d6\u6570\u65b9\u6848\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a", new Object[0]);
            FetchDataUtil.assertIsNotEmpty(orgnFetchRequestDTO.getFetchContext().getTaskId(), "\u62a5\u8868\u4efb\u52a1\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a", new Object[0]);
            FetchDataUtil.assertIsNotEmpty(orgnFetchRequestDTO.getFetchContext().getFormId(), "\u53d6\u6570\u62a5\u8868\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a", new Object[0]);
            FetchDataUtil.assertIsNotEmpty(orgnFetchRequestDTO.getFetchContext().getRegionId(), "\u53d6\u6570\u533a\u57df\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a", new Object[0]);
        }
        String taskId = StringUtils.isEmpty((String)(fetchRequestDTO = (FetchDataRequestDTO)BeanConvertUtil.convert((Object)orgnFetchRequestDTO, FetchDataRequestDTO.class, (String[])new String[0])).getRequestTaskId()) ? UUIDUtils.newHalfGUIDStr() : fetchRequestDTO.getRequestTaskId();
        fetchRequestDTO.setRequestTaskId(taskId);
        if (requestSourceType == RequestSourceTypeEnum.PENETRATE || requestSourceType == RequestSourceTypeEnum.TEST) {
            fetchRequestDTO.setRequestInstcId(fetchRequestDTO.getRequestTaskId());
            fetchRequestDTO.setRequestRunnerId(fetchRequestDTO.getRequestTaskId());
            fetchRequestDTO.setRequestInstcId(fetchRequestDTO.getRequestTaskId());
        }
        if (fetchRequestDTO.getRouteNum() == null) {
            Random random = new Random();
            fetchRequestDTO.setRouteNum(Integer.valueOf(random.nextInt(10) + 1));
        }
        return fetchRequestDTO;
    }

    public void doAnalyzeContext(FetchDataRequestDTO fetchRequestDTO) {
        OrgMappingDTO orgMapping = fetchRequestDTO.getOrgMapping();
        if (orgMapping == null && (orgMapping = this.orgMappingProvider.getByCode(fetchRequestDTO.getFetchContext().getBblx()).getOrgMapping(fetchRequestDTO.getFetchContext().getUnitCode())) == null) {
            if (fetchRequestDTO.getFetchContext().getBblx() == null) {
                throw new CheckRuntimeException(String.format("\u6839\u636e\u62a5\u8868\u5355\u4f4d\u3010%1$s\u3011\u83b7\u53d6\u7ec4\u7ec7\u673a\u6784\u6620\u5c04\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5BDE\u670d\u52a1\u5355\u4f4d\u6620\u5c04\u914d\u7f6e", fetchRequestDTO.getFetchContext().getUnitCode()));
            }
            throw new CheckRuntimeException(String.format("\u6839\u636e\u62a5\u8868\u7c7b\u578b\u3010%1$s\u3011\u62a5\u8868\u5355\u4f4d\u4ee3\u7801\u3010%2$s\u3011\u83b7\u53d6\u7ec4\u7ec7\u673a\u6784\u6620\u5c04\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5BDE\u670d\u52a1\u5355\u4f4d\u6620\u5c04\u914d\u7f6e", fetchRequestDTO.getFetchContext().getBblx(), fetchRequestDTO.getFetchContext().getUnitCode()));
        }
        fetchRequestDTO.setOrgMapping(orgMapping);
        if (fetchRequestDTO.getRouteNum() == null) {
            fetchRequestDTO.setRouteNum(Integer.valueOf(BdeCommonUtil.getRouteNum()));
        }
    }

    public void doRecordLog(FetchDataRequestDTO fetchRequestDTO) {
        if (RequestSourceTypeEnum.PENETRATE.getCode().equals(fetchRequestDTO.getRequestSourceType()) || RequestSourceTypeEnum.TEST.getCode().equals(fetchRequestDTO.getRequestSourceType()) || RequestSourceTypeEnum.DATACHECK_FETCH.getCode().equals(fetchRequestDTO.getRequestSourceType())) {
            String runnerId = fetchRequestDTO.getRequestRunnerId();
            String instanceId = fetchRequestDTO.getRequestInstcId();
            String taskName = fetchRequestDTO.getRequestSourceType();
            DcTaskLogEO taskInfo = TaskInfoUtil.createTaskInfo((String)taskName);
            taskInfo.setId(runnerId);
            taskInfo.setExt_1("#");
            taskInfo.setExt_2(fetchRequestDTO.getFetchContext().getUnitCode());
            if (fetchRequestDTO.getExtInfo() != null && fetchRequestDTO.getExtInfo().get("userName") != null) {
                taskInfo.setExt_3(fetchRequestDTO.getExtInfo().get("userName").toString());
            }
            taskInfo.setExt_4("#");
            taskInfo.setExt_5("0");
            FetchInitTaskDTO initTask = (FetchInitTaskDTO)BeanConvertUtil.convert((Object)fetchRequestDTO.getFetchContext(), FetchInitTaskDTO.class, (String[])new String[0]);
            initTask.setRequestRunnerId(instanceId);
            initTask.setFetchForms((List)CollectionUtils.newArrayList());
            initTask.setRequestSourceType(fetchRequestDTO.getRequestSourceType());
            FetchFormDTO fetchFormDTO = new FetchFormDTO();
            fetchFormDTO.setId(fetchRequestDTO.getFetchContext().getFormId());
            fetchFormDTO.setFormCode(fetchRequestDTO.getFetchContext().getFormId());
            if (fetchRequestDTO.getExtInfo() != null && fetchRequestDTO.getExtInfo().get("formTitle") != null) {
                fetchFormDTO.setFormTitle(fetchRequestDTO.getExtInfo().get("formTitle").toString());
            } else {
                fetchFormDTO.setFormTitle(" ");
            }
            ArrayList fetchRegions = CollectionUtils.newArrayList();
            FetchRegionDTO fetchRegionDTO = new FetchRegionDTO();
            fetchRegionDTO.setId(fetchRequestDTO.getFetchContext().getRegionId());
            fetchRegionDTO.setRegionCode(fetchRequestDTO.getFetchContext().getRegionId());
            if (fetchRequestDTO.getExtInfo() != null && fetchRequestDTO.getExtInfo().get("regionTitle") != null) {
                fetchRegionDTO.setRegionTitle(fetchRequestDTO.getExtInfo().get("regionTitle").toString());
            } else {
                fetchRegionDTO.setRegionTitle(" ");
            }
            fetchRegions.add(fetchRegionDTO);
            fetchFormDTO.addFetchRegion(fetchRegionDTO);
            initTask.getFetchForms().add(fetchFormDTO);
            initTask.setFetchFormCt(initTask.getFetchForms().size());
            if (fetchRequestDTO.getExtInfo() != null && fetchRequestDTO.getExtInfo().get("userName") != null) {
                initTask.setUsername(fetchRequestDTO.getExtInfo().get("userName").toString());
            }
            taskInfo.setMessage(JsonUtils.writeValueAsString((Object)initTask));
            this.fetchTaskLogService.insertTaskLog(taskInfo);
            DcTaskItemLogEO taskItem = TaskInfoUtil.createTaskItemInfo((String)fetchRequestDTO.getRequestSourceType(), (String)instanceId, (String)UUIDUtils.emptyHalfGUIDStr(), (IDimType)FetchDimType.FORM, (String)fetchRequestDTO.getFetchContext().getFormId());
            taskItem.setId(fetchRequestDTO.getRequestTaskId());
            taskItem.setRunnerId(runnerId);
            taskItem.setInstanceId(instanceId);
            taskItem.setStartTime(new Date());
            taskItem.setMessage(JsonUtils.writeValueAsString((Object)fetchRequestDTO));
            this.fetchTaskLogService.insertTaskItemLog(taskItem);
        } else if (RequestSourceTypeEnum.BUDGET_FETCH.getCode().equals(fetchRequestDTO.getRequestSourceType())) {
            String runnerId = fetchRequestDTO.getRequestRunnerId();
            String instanceId = fetchRequestDTO.getRequestInstcId();
            DcTaskItemLogEO taskItem = TaskInfoUtil.createTaskItemInfo((String)fetchRequestDTO.getRequestSourceType(), (String)instanceId, (String)UUIDUtils.emptyHalfGUIDStr(), (IDimType)FetchDimType.FORM, (String)FetchDataUtil.getDimCode(fetchRequestDTO.getFetchContext().getFormId(), fetchRequestDTO.getFetchContext().getRegionId()));
            taskItem.setId(fetchRequestDTO.getRequestTaskId());
            taskItem.setRunnerId(runnerId);
            taskItem.setInstanceId(instanceId);
            taskItem.setStartTime(new Date());
            taskItem.setMessage(JsonUtils.writeValueAsString((Object)fetchRequestDTO));
            this.fetchTaskLogService.insertTaskItemLog(taskItem);
        }
    }
}

