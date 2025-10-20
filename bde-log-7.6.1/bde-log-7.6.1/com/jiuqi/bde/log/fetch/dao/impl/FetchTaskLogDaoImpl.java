/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.FetchTaskState
 *  com.jiuqi.bde.common.constant.RequestSourceTypeEnum
 *  com.jiuqi.bde.common.dto.fetch.init.FetchFormDTO
 *  com.jiuqi.bde.common.dto.fetch.init.FetchInitTaskDTO
 *  com.jiuqi.bde.common.dto.fetch.query.FetchTaskInfoQueryDTO
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl
 *  com.jiuqi.dc.base.common.jdbc.extractor.IntegerResultSetExtractor
 *  com.jiuqi.dc.base.common.jdbc.extractor.StringResultSetExtractor
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerClient
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory
 *  com.jiuqi.dc.taskscheduling.log.impl.data.TaskCountQueryDTO
 *  com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskItemLogEO
 *  com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskLogEO
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.DataHandleState
 *  com.jiuqi.dc.taskscheduling.log.impl.vo.LogManagerDetailVO
 *  com.jiuqi.va.basedata.service.BaseDataService
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.data.util.Pair
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.bde.log.fetch.dao.impl;

import com.jiuqi.bde.common.constant.FetchTaskState;
import com.jiuqi.bde.common.constant.RequestSourceTypeEnum;
import com.jiuqi.bde.common.dto.fetch.init.FetchFormDTO;
import com.jiuqi.bde.common.dto.fetch.init.FetchInitTaskDTO;
import com.jiuqi.bde.common.dto.fetch.query.FetchTaskInfoQueryDTO;
import com.jiuqi.bde.log.enums.FetchDimType;
import com.jiuqi.bde.log.fetch.dao.FetchTaskLogDao;
import com.jiuqi.bde.log.fetch.dto.FetchItemLogDTO;
import com.jiuqi.bde.log.fetch.dto.FetchLogDTO;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl;
import com.jiuqi.dc.base.common.jdbc.extractor.IntegerResultSetExtractor;
import com.jiuqi.dc.base.common.jdbc.extractor.StringResultSetExtractor;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerClient;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory;
import com.jiuqi.dc.taskscheduling.log.impl.data.TaskCountQueryDTO;
import com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskItemLogEO;
import com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskLogEO;
import com.jiuqi.dc.taskscheduling.log.impl.enums.DataHandleState;
import com.jiuqi.dc.taskscheduling.log.impl.vo.LogManagerDetailVO;
import com.jiuqi.va.basedata.service.BaseDataService;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class FetchTaskLogDaoImpl
extends BaseDataCenterDaoImpl
implements FetchTaskLogDao {
    @Autowired
    @Lazy
    private TaskHandlerFactory taskHandlerFactory;
    private static final String FN_QUERY_TASK_ITEM = "SELECT ID FROM GC_LOG_TASKITEMINFO DLT WHERE RUNNERID = ? and DIMTYPE = 'FORM_REGION' and DIMCODE = ? ";

    @Override
    public DcTaskLogEO selectLog(String runnerId) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT  ");
        sql.append(" TASKINFO.ID,  ");
        sql.append(" CREATETIME,  ");
        sql.append(" MESSAGE,  ");
        sql.append(" RESULTLOG,  ");
        sql.append(" EXT_5  ");
        sql.append(" FROM GC_LOG_TASKINFO TASKINFO  ");
        sql.append(" WHERE 1 = 1  ");
        sql.append("   AND TASKINFO.ID = ? \n");
        List taskInfoList = OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (rs, rowNum) -> {
            DcTaskLogEO taskItemInfo = new DcTaskLogEO();
            taskItemInfo.setId(rs.getString(1));
            taskItemInfo.setCreateTime((Date)rs.getTimestamp(2));
            taskItemInfo.setMessage(rs.getString(3));
            taskItemInfo.setResultLog(rs.getString(4));
            taskItemInfo.setExt_5(rs.getString(5));
            return taskItemInfo;
        }, new Object[]{runnerId});
        return CollectionUtils.isEmpty((Collection)taskInfoList) ? null : (DcTaskLogEO)taskInfoList.get(0);
    }

    @Override
    public int getUnFinishTaskItemCount(String runnerId, String taskId) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT COUNT(*) \n");
        sql.append("  FROM ").append("GC_LOG_TASKITEMINFO").append(" \n");
        sql.append(" WHERE 1 = 1 \n");
        sql.append("   AND RUNNERID = ? \n");
        sql.append("   AND PRENODEID = ? \n");
        sql.append(String.format("   AND EXECUTESTATE IN (%1$d,%2$d) \n", DataHandleState.UNEXECUTE.getState(), DataHandleState.EXECUTING.getState()));
        return (Integer)OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (ResultSetExtractor)new IntegerResultSetExtractor(), new Object[]{runnerId, taskId});
    }

    @Override
    public List<DcTaskItemLogEO> getFailedTaskItem(String runnerId, String taskId) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * \n");
        sql.append("  FROM ").append("GC_LOG_TASKITEMINFO").append(" \n");
        sql.append(" WHERE 1 = 1 \n");
        sql.append("   AND RUNNERID = ? \n");
        sql.append("   AND PRENODEID = ? \n");
        sql.append("   AND EXECUTESTATE = ? \n");
        List taskItemList = OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (RowMapper)new BeanPropertyRowMapper(DcTaskItemLogEO.class), new Object[]{runnerId, taskId, DataHandleState.FAILURE.getState()});
        return taskItemList;
    }

    @Override
    public String selectInstanceIdByCondi(String runnerId, FetchTaskInfoQueryDTO taskInfoQueryDTO) {
        return (String)OuterDataSourceUtils.getJdbcTemplate().query(FN_QUERY_TASK_ITEM, (ResultSetExtractor)new StringResultSetExtractor(), new Object[]{runnerId, BdeLogUtil.getDimCode(taskInfoQueryDTO.getFormId(), taskInfoQueryDTO.getRegionId())});
    }

    @Override
    public int countUnFinishBatchTask(String runnerId) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT COUNT(*) \n");
        sql.append("  FROM ").append("GC_LOG_TASKINFO").append(" T \n");
        sql.append(" WHERE 1 = 1 \n");
        sql.append("   AND T.EXT_1 = ?  \n");
        sql.append("   AND T.ENDTIME IS NOT NULL \n");
        return (Integer)OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (ResultSetExtractor)new IntegerResultSetExtractor(), new Object[]{runnerId});
    }

    @Override
    public FetchLogDTO queryFailBatchTask(String runnerId) {
        StringBuffer sql = new StringBuffer();
        sql = new StringBuffer();
        sql.append("SELECT ITEM.ID ,ITEM.DIMCODE,ITEM.MESSAGE ,ITEM.DIMTYPE ,ITEM.INSTANCEID ,ITEM.RESULTLOG \n");
        sql.append("  FROM ").append("GC_LOG_TASKITEMINFO").append(" ITEM \n");
        sql.append(" WHERE 1 = 1 \n");
        sql.append("    AND ITEM.RUNNERID =  ? \n");
        sql.append(String.format("   AND (ITEM.EXECUTESTATE IN (%1$d,%2$d) OR DIMTYPE = '%3$s') \n", DataHandleState.FAILURE.getState(), DataHandleState.CANCELED.getState(), FetchDimType.BATCH.getName()));
        List taskItemList = OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (RowMapper)new BeanPropertyRowMapper(LogManagerDetailVO.class), new Object[]{runnerId});
        FetchLogDTO fetchLog = new FetchLogDTO();
        FetchInitTaskDTO fetchInitTask = null;
        ArrayList<FetchItemLogDTO> failedLogList = new ArrayList<FetchItemLogDTO>();
        FetchItemLogDTO fetchItemLog = null;
        for (LogManagerDetailVO logManagerDetailVO : taskItemList) {
            if (FetchDimType.BATCH.getName().equals(logManagerDetailVO.getDimType())) {
                fetchLog = (FetchLogDTO)JsonUtils.readValue((String)logManagerDetailVO.getResultLog(), FetchLogDTO.class);
                continue;
            }
            fetchInitTask = (FetchInitTaskDTO)JsonUtils.readValue((String)logManagerDetailVO.getMessage(), FetchInitTaskDTO.class);
            fetchItemLog = (FetchItemLogDTO)BeanConvertUtil.convert((Object)fetchInitTask, FetchItemLogDTO.class, (String[])new String[0]);
            fetchItemLog.setRunnerId(runnerId);
            fetchItemLog.setLog(logManagerDetailVO.getResultLog());
            failedLogList.add(fetchItemLog);
        }
        failedLogList.addAll(fetchLog.getFailedItemList());
        BaseDataDTO condi = new BaseDataDTO();
        condi.setTableName("MD_ORG");
        condi.setAuthType(BaseDataOption.AuthType.NONE);
        condi.setQueryDataStructure(BaseDataOption.QueryDataStructure.BASIC);
        final Map<String, BaseDataDO> baseDataMap = ((BaseDataService)ApplicationContextRegister.getBean(BaseDataService.class)).list(condi).getRows().stream().collect(Collectors.toMap(BaseDataDO::getCode, item -> item));
        failedLogList.stream().sorted(new Comparator<FetchItemLogDTO>(){

            @Override
            public int compare(FetchItemLogDTO o1, FetchItemLogDTO o2) {
                BigDecimal order1 = baseDataMap.get(o1.getUnitCode()) == null ? new BigDecimal(Integer.MAX_VALUE) : ((BaseDataDO)baseDataMap.get(o1.getUnitCode())).getOrdinal();
                BigDecimal order2 = baseDataMap.get(o2.getUnitCode()) == null ? new BigDecimal(Integer.MAX_VALUE) : ((BaseDataDO)baseDataMap.get(o2.getUnitCode())).getOrdinal();
                return order1.compareTo(order2);
            }
        });
        FetchLogDTO fetchLogDTO = new FetchLogDTO(fetchLog.getFetchSize());
        fetchLogDTO.setFailedItemList(failedLogList);
        return fetchLogDTO;
    }

    @Override
    public FetchItemLogDTO queryFailTask(String runnerId) {
        DcTaskLogEO taskLog = this.selectLog(runnerId);
        if (taskLog == null) {
            return null;
        }
        if (!FetchTaskState.isFinish((String)taskLog.getExt_5())) {
            return null;
        }
        TaskCountQueryDTO queryDto = new TaskCountQueryDTO();
        queryDto.setRunnerId(runnerId);
        ArrayList<DataHandleState> handleStateList = new ArrayList<DataHandleState>();
        handleStateList.add(DataHandleState.FAILURE);
        handleStateList.add(DataHandleState.CANCELED);
        queryDto.setDataHandleStates(handleStateList);
        TaskHandlerClient taskHandlerClient = this.taskHandlerFactory.getMainTaskHandlerClient();
        List logDetailList = (List)taskHandlerClient.queryTaskLog(queryDto).getData();
        FetchInitTaskDTO fetchInitTask = (FetchInitTaskDTO)JsonUtils.readValue((String)taskLog.getMessage(), FetchInitTaskDTO.class);
        logDetailList = logDetailList == null ? CollectionUtils.newArrayList() : logDetailList;
        FetchItemLogDTO fetchLog = (FetchItemLogDTO)BeanConvertUtil.convert((Object)fetchInitTask, FetchItemLogDTO.class, (String[])new String[0]);
        fetchLog.setRunnerId(runnerId);
        Pair<String, String> logPair = this.parseLog(fetchInitTask, logDetailList);
        fetchLog.setLogDigest((String)logPair.getFirst());
        fetchLog.setLog((String)logPair.getSecond());
        return fetchLog;
    }

    @Override
    public boolean existExecutingTask(String dimKey) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT COUNT(*) \n");
        sql.append("  FROM ").append("GC_LOG_TASKITEMINFO").append(" ITEM \n");
        sql.append(" WHERE 1 = 1 \n");
        sql.append("   AND ITEM.RUNNERID IN (\n");
        sql.append("                      SELECT ID FROM ").append("GC_LOG_TASKINFO").append(" T \n");
        sql.append("                               WHERE  T.EXT_4 = ? \n");
        sql.append("                               AND    T.CREATETIME >= ? \n");
        sql.append("                      )  \n");
        sql.append(String.format("   AND ITEM.EXECUTESTATE IN (%1$d,%2$d) \n", DataHandleState.EXECUTING.getState(), DataHandleState.UNEXECUTE.getState()));
        Timestamp min15TS = new Timestamp(System.currentTimeMillis() - 900000L);
        return (Integer)OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (ResultSetExtractor)new IntegerResultSetExtractor(), new Object[]{dimKey, min15TS}) > 0;
    }

    @Override
    public void updateTaskFinished(String runnerId) {
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE ").append("GC_LOG_TASKINFO").append(" \n");
        sql.append(" SET EXT_5 = ? , ENDTIME = ? WHERE ID = ? \n");
        OuterDataSourceUtils.getJdbcTemplate().update(sql.toString(), new Object[]{FetchTaskState.FINISHED.getCode(), new Timestamp(System.currentTimeMillis()), runnerId});
    }

    private Pair<String, String> parseLog(FetchInitTaskDTO fetchInitTask, List<LogManagerDetailVO> logDetailList) {
        StringBuffer taskLog;
        String newLine = System.lineSeparator();
        StringBuffer detailLog = new StringBuffer();
        detailLog.append(String.format("\u5355\u4f4d\uff1a%s ", fetchInitTask.getUnitName()));
        detailLog.append(newLine);
        detailLog.append(String.format("\u65f6\u671f\uff1a%s ", fetchInitTask.getPeriodScheme()));
        detailLog.append(newLine);
        HashMap failedMsgMap = CollectionUtils.newHashMap();
        HashMap failedFormMap = CollectionUtils.newHashMap();
        String formKey = null;
        for (LogManagerDetailVO logManagerDetailVO : logDetailList) {
            formKey = logManagerDetailVO.getDimType().equals(FetchDimType.FORM.getName()) ? logManagerDetailVO.getDimCode() : logManagerDetailVO.getDimCode().substring(0, logManagerDetailVO.getDimCode().indexOf(","));
            failedFormMap.put(formKey, logManagerDetailVO.getInstanceId());
            failedMsgMap.computeIfAbsent(logManagerDetailVO.getInstanceId(), key -> new StringBuilder());
            ((StringBuilder)failedMsgMap.get(logManagerDetailVO.getInstanceId())).append(FetchTaskLogDaoImpl.getErrorMsg(logManagerDetailVO.getResultLog()));
        }
        if (RequestSourceTypeEnum.PENETRATE.getCode().equals(fetchInitTask.getRequestSourceType()) || RequestSourceTypeEnum.TEST.getCode().equals(fetchInitTask.getRequestSourceType()) || RequestSourceTypeEnum.BUDGET_FETCH.getCode().equals(fetchInitTask.getRequestSourceType()) || RequestSourceTypeEnum.DATACHECK_FETCH.getCode().equals(fetchInitTask.getRequestSourceType())) {
            if (failedMsgMap.isEmpty()) {
                return Pair.of((Object)"", (Object)"");
            }
            taskLog = new StringBuffer();
            if (!failedMsgMap.isEmpty()) {
                for (FetchFormDTO fetchForm : fetchInitTask.getFetchForms()) {
                    taskLog.append("\u53d6\u6570\u5931\u8d25\uff0c\u5931\u8d25\u539f\u56e0\uff1a").append(newLine).append((CharSequence)failedMsgMap.get(failedFormMap.get(fetchForm.getId())));
                }
            }
            return Pair.of((Object)"", (Object)taskLog.toString());
        }
        if (RequestSourceTypeEnum.BILL_FETCH.getCode().equals(fetchInitTask.getRequestSourceType())) {
            if (failedMsgMap.isEmpty()) {
                return Pair.of((Object)"", (Object)"");
            }
            taskLog = new StringBuffer();
            if (!failedMsgMap.isEmpty()) {
                taskLog.append("\u53d6\u6570\u5931\u8d25\uff0c\u5931\u8d25\u539f\u56e0\uff1a");
                for (StringBuilder failedMsg : failedMsgMap.values()) {
                    if (StringUtils.isEmpty((String)failedMsg.toString())) continue;
                    taskLog.append(newLine).append((CharSequence)failedMsg);
                }
            }
            return Pair.of((Object)"", (Object)taskLog.toString());
        }
        taskLog = new StringBuffer();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("\u672c\u6b21\u5171\u53d6\u6570");
        stringBuffer.append(fetchInitTask.getFetchFormCt());
        stringBuffer.append("\u5f20\u8868\uff0c");
        stringBuffer.append("\u5176\u4e2d\u6210\u529f").append(fetchInitTask.getFetchFormCt() - failedFormMap.size()).append("\u5f20\u8868\uff0c").append("\u5931\u8d25").append(failedFormMap.size()).append("\u5f20\u8868\u3002");
        String logDigest = stringBuffer.toString();
        if (failedFormMap.size() == 0) {
            return Pair.of((Object)logDigest, (Object)"");
        }
        taskLog.append(stringBuffer).append(newLine);
        if (!failedMsgMap.isEmpty()) {
            for (FetchFormDTO fetchForm : fetchInitTask.getFetchForms()) {
                if (!failedFormMap.containsKey(fetchForm.getId()) || failedMsgMap.get(failedFormMap.get(fetchForm.getId())) == null) continue;
                taskLog.append("\u62a5\u8868\u3010").append(fetchForm.getFormTitle()).append("\u3011").append("\u53d6\u6570\u5931\u8d25\uff0c\u5931\u8d25\u539f\u56e0\uff1a").append(newLine).append((CharSequence)failedMsgMap.get(failedFormMap.get(fetchForm.getId())));
                taskLog.append(newLine);
            }
        }
        detailLog.append(taskLog);
        return Pair.of((Object)logDigest, (Object)detailLog.toString());
    }

    public static String getErrorMsg(String msg) {
        if (msg == null) {
            return "";
        }
        if (StringUtils.isEmpty((String)msg)) {
            return "";
        }
        if (msg.contains("\u5806\u6808\u4fe1\u606f:")) {
            msg = msg.substring(0, msg.indexOf("\u5806\u6808\u4fe1\u606f:"));
        }
        if (msg.contains("com.")) {
            msg = msg.substring(0, msg.indexOf("com."));
        }
        if (msg.endsWith("\n")) {
            msg = msg.replace("\n", "");
        }
        return msg.toString();
    }
}

