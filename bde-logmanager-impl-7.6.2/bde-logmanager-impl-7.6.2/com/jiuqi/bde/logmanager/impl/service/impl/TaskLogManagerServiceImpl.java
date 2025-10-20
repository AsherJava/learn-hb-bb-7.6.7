/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.intf.IBizComputationModel
 *  com.jiuqi.bde.bizmodel.define.gather.IBizModelGather
 *  com.jiuqi.bde.bizmodel.execute.util.FloatRegionAnalyzeDimType
 *  com.jiuqi.bde.bizmodel.execute.util.FormRegionDimType
 *  com.jiuqi.bde.common.constant.RequestSourceTypeEnum
 *  com.jiuqi.bde.common.dto.ExecuteSettingVO
 *  com.jiuqi.bde.common.dto.FetchDataExecuteContext
 *  com.jiuqi.bde.common.dto.fetch.init.FetchFormDTO
 *  com.jiuqi.bde.common.dto.fetch.init.FetchInitTaskDTO
 *  com.jiuqi.bde.common.dto.fetch.init.FetchRegionDTO
 *  com.jiuqi.bde.log.enums.FetchDimType
 *  com.jiuqi.bde.logmanager.client.LogDetailRefreshCondition
 *  com.jiuqi.bde.logmanager.client.LogManagerCondition
 *  com.jiuqi.bde.logmanager.client.LogManagerInfoItemVO
 *  com.jiuqi.bde.logmanager.client.LogManagerInfoVO
 *  com.jiuqi.bde.logmanager.client.LogResultMsgVO
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.definition.DcDefaultTableEntity
 *  com.jiuqi.dc.base.common.utils.CommonUtil
 *  com.jiuqi.dc.base.common.utils.CompressUtil
 *  com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskItemLogEO
 *  com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskLogEO
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.DataHandleState
 *  com.jiuqi.dc.taskscheduling.logquery.impl.service.impl.LogManagerServiceImpl
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.organization.service.OrgDataService
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.commons.compress.archivers.zip.Zip64Mode
 *  org.apache.commons.compress.archivers.zip.ZipArchiveEntry
 *  org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream
 */
package com.jiuqi.bde.logmanager.impl.service.impl;

import com.jiuqi.bde.bizmodel.client.intf.IBizComputationModel;
import com.jiuqi.bde.bizmodel.define.gather.IBizModelGather;
import com.jiuqi.bde.bizmodel.execute.util.FloatRegionAnalyzeDimType;
import com.jiuqi.bde.bizmodel.execute.util.FormRegionDimType;
import com.jiuqi.bde.common.constant.RequestSourceTypeEnum;
import com.jiuqi.bde.common.dto.ExecuteSettingVO;
import com.jiuqi.bde.common.dto.FetchDataExecuteContext;
import com.jiuqi.bde.common.dto.fetch.init.FetchFormDTO;
import com.jiuqi.bde.common.dto.fetch.init.FetchInitTaskDTO;
import com.jiuqi.bde.common.dto.fetch.init.FetchRegionDTO;
import com.jiuqi.bde.log.enums.FetchDimType;
import com.jiuqi.bde.logmanager.client.LogDetailRefreshCondition;
import com.jiuqi.bde.logmanager.client.LogManagerCondition;
import com.jiuqi.bde.logmanager.client.LogManagerInfoItemVO;
import com.jiuqi.bde.logmanager.client.LogManagerInfoVO;
import com.jiuqi.bde.logmanager.client.LogResultMsgVO;
import com.jiuqi.bde.logmanager.impl.dao.TaskLogManagerDao;
import com.jiuqi.bde.logmanager.impl.intf.FetchExecuteStatus;
import com.jiuqi.bde.logmanager.impl.intf.FetchSqlLogDTO;
import com.jiuqi.bde.logmanager.impl.service.TaskLogManagerService;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.definition.DcDefaultTableEntity;
import com.jiuqi.dc.base.common.utils.CommonUtil;
import com.jiuqi.dc.base.common.utils.CompressUtil;
import com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskItemLogEO;
import com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskLogEO;
import com.jiuqi.dc.taskscheduling.log.impl.enums.DataHandleState;
import com.jiuqi.dc.taskscheduling.logquery.impl.service.impl.LogManagerServiceImpl;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.organization.service.OrgDataService;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskLogManagerServiceImpl
implements TaskLogManagerService {
    @Autowired
    private TaskLogManagerDao taskLogManagerDao;
    @Autowired
    private OrgDataService orgDataService;
    @Autowired
    private IBizModelGather bizModelGather;
    private static Logger logger = LoggerFactory.getLogger(LogManagerServiceImpl.class);
    private static final String TMPL_UNITNAME = "%1$s|%2$s";

    @Override
    public PageVO<LogManagerInfoVO> listLogData(LogManagerCondition logManagerCondition) {
        Integer total = this.taskLogManagerDao.countLog(logManagerCondition);
        if (total == 0) {
            return new PageVO(true);
        }
        List<DcTaskLogEO> selectLog = this.taskLogManagerDao.selectLog(logManagerCondition);
        if (CollectionUtils.isEmpty(selectLog)) {
            return new PageVO((List)CollectionUtils.newArrayList(), total.intValue());
        }
        ArrayList<LogManagerInfoVO> logList = new ArrayList<LogManagerInfoVO>();
        HashMap<String, String> unitNameMap = new HashMap<String, String>();
        OrgDTO orgCondi = new OrgDTO();
        orgCondi.setAuthType(OrgDataOption.AuthType.NONE);
        for (OrgDO orgDO : this.orgDataService.list(orgCondi).getRows()) {
            unitNameMap.put(orgDO.getCode(), orgDO.getName());
        }
        List<String> instanceIdList = selectLog.stream().map(DcDefaultTableEntity::getId).collect(Collectors.toList());
        Map<String, FetchExecuteStatus> executeStatusMap = this.taskLogManagerDao.countExecuteStatus(instanceIdList);
        FetchInitTaskDTO fetchInitTaskDTO = null;
        LogManagerInfoVO logManagerInfoVO = null;
        for (DcTaskLogEO taskInfoLog : selectLog) {
            Object formInfoVO2;
            fetchInitTaskDTO = (FetchInitTaskDTO)JsonUtils.readValue((String)taskInfoLog.getMessage(), FetchInitTaskDTO.class);
            logManagerInfoVO = new LogManagerInfoVO();
            logManagerInfoVO.setRequestInstcId(taskInfoLog.getId());
            logManagerInfoVO.setUnitCode(fetchInitTaskDTO.getUnitCode());
            logManagerInfoVO.setUnitName(this.getUnitName(unitNameMap, fetchInitTaskDTO.getUnitCode()));
            logManagerInfoVO.setRequestSourceType(fetchInitTaskDTO.getRequestSourceType() == null ? "" : RequestSourceTypeEnum.fromCode((String)fetchInitTaskDTO.getRequestSourceType()).getTitle());
            StringBuffer formNames = new StringBuffer();
            if (fetchInitTaskDTO.getFetchForms().size() > 3) {
                for (int i = 0; i < fetchInitTaskDTO.getFetchForms().size(); ++i) {
                    if (i == 2) {
                        formNames.append(((FetchFormDTO)fetchInitTaskDTO.getFetchForms().get(i)).getFormTitle()).append("\u7b49").append(fetchInitTaskDTO.getFetchForms().size()).append("\u5f20\u8868");
                        break;
                    }
                    formNames.append(((FetchFormDTO)fetchInitTaskDTO.getFetchForms().get(i)).getFormTitle()).append("\u3001");
                }
                logManagerInfoVO.setExecuteInfo(formNames.toString());
            } else {
                for (Object formInfoVO2 : fetchInitTaskDTO.getFetchForms()) {
                    formNames.append(formInfoVO2.getFormTitle()).append("\u3001");
                }
                if (formNames.length() > 0) {
                    formNames.delete(formNames.length() - 1, formNames.length());
                }
                logManagerInfoVO.setExecuteInfo(formNames.toString());
            }
            int itemCount = 0;
            formInfoVO2 = fetchInitTaskDTO.getFetchForms().iterator();
            while (formInfoVO2.hasNext()) {
                FetchFormDTO fetchForm = (FetchFormDTO)formInfoVO2.next();
                if (CollectionUtils.isEmpty((Collection)fetchForm.getFetchRegions())) continue;
                ++itemCount;
            }
            logManagerInfoVO.setFetchCount(Integer.valueOf(itemCount));
            logManagerInfoVO.setStartTime(new Timestamp(taskInfoLog.getCreateTime().getTime()));
            if (executeStatusMap.get(taskInfoLog.getId()) != null) {
                logManagerInfoVO.setSuccessCount(Integer.valueOf(executeStatusMap.get(taskInfoLog.getId()).getSuccessCount()));
                logManagerInfoVO.setFailureCount(Integer.valueOf(executeStatusMap.get(taskInfoLog.getId()).getFailedCount()));
                logManagerInfoVO.setExecuteCount(Integer.valueOf(executeStatusMap.get(taskInfoLog.getId()).getExecuteCount()));
                int calcCount = executeStatusMap.get(taskInfoLog.getId()).getSuccessCount() + executeStatusMap.get(taskInfoLog.getId()).getFailedCount() + executeStatusMap.get(taskInfoLog.getId()).getExecuteCount();
                logManagerInfoVO.setEndTime(executeStatusMap.get(taskInfoLog.getId()).getEndDate());
                logManagerInfoVO.setProcess(Double.valueOf(Double.valueOf(executeStatusMap.get(taskInfoLog.getId()).getFailedCount() + executeStatusMap.get(taskInfoLog.getId()).getSuccessCount()) / Double.valueOf(itemCount == 0 ? (double)calcCount : (double)itemCount)));
            } else {
                logManagerInfoVO.setSuccessCount(Integer.valueOf(0));
                logManagerInfoVO.setFailureCount(Integer.valueOf(0));
                logManagerInfoVO.setExecuteCount(Integer.valueOf(0));
                logManagerInfoVO.setProcess(Double.valueOf(0.0));
            }
            logManagerInfoVO.setUserName(fetchInitTaskDTO.getUsername());
            logManagerInfoVO.setPeriodScheme(StringUtils.isEmpty((String)fetchInitTaskDTO.getPeriodScheme()) ? this.getPeriodSchemeByDate(fetchInitTaskDTO.getEndDateStr()) : fetchInitTaskDTO.getPeriodScheme());
            logList.add(logManagerInfoVO);
        }
        return new PageVO(logList, total.intValue());
    }

    private String getUnitName(Map<String, String> unitNameMap, String unitCode) {
        if (StringUtils.isEmpty((String)unitNameMap.get(unitCode))) {
            return unitCode;
        }
        return String.format(TMPL_UNITNAME, unitCode, unitNameMap.get(unitCode));
    }

    @Override
    public LogResultMsgVO getLogDetailListById(String requestInstcId) {
        LogResultMsgVO logResultMsgVO = new LogResultMsgVO();
        LogManagerCondition logManagerCondition = new LogManagerCondition();
        logManagerCondition.setRequestInstcId(requestInstcId);
        List<DcTaskLogEO> selectLog = this.taskLogManagerDao.selectLog(logManagerCondition);
        Assert.isNotEmpty(selectLog, (String)String.format("\u53d6\u6570\u4efb\u52a1\u3010%1$s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u6267\u884c\u8bb0\u5f55\uff0c\u8bf7\u7a0d\u540e\u91cd\u8bd5", requestInstcId), (Object[])new Object[0]);
        FetchInitTaskDTO fetchInitTaskDTO = (FetchInitTaskDTO)JsonUtils.readValue((String)selectLog.get(0).getMessage(), FetchInitTaskDTO.class);
        DcTaskItemLogEO condi = new DcTaskItemLogEO();
        condi.setInstanceId(requestInstcId);
        condi.setDimType(FetchDimType.REGION.getName());
        Map<String, FetchExecuteStatus> executeStatusMap = this.taskLogManagerDao.countTaskExecuteStatus(condi.getInstanceId());
        List<LogManagerInfoItemVO> itemInfoList = this.getItemInfoList(fetchInitTaskDTO, condi, executeStatusMap);
        logResultMsgVO.setLogInfoItemTableData(itemInfoList);
        HashMap formRegionMap = new HashMap();
        fetchInitTaskDTO.getFetchForms().stream().forEach(fetchForm -> {
            if (CollectionUtils.isEmpty((Collection)fetchForm.getFetchRegions())) {
                return;
            }
            if (fetchForm.getFetchRegions().size() == 1) {
                formRegionMap.put(fetchForm.getId() + "," + ((FetchRegionDTO)fetchForm.getFetchRegions().get(0)).getId(), fetchForm.getFormTitle());
                return;
            }
            fetchForm.getFetchRegions().stream().forEach(fetchRegion -> formRegionMap.put(fetchForm.getId() + "," + fetchRegion.getId(), fetchForm.getFormTitle() + "|" + fetchRegion.getRegionTitle()));
        });
        StringBuffer formNames = new StringBuffer();
        Iterator iterator = logResultMsgVO.getLogInfoItemTableData().iterator();
        while (iterator.hasNext()) {
            LogManagerInfoItemVO itemInfo;
            itemInfo.setFormName(formRegionMap.get((itemInfo = (LogManagerInfoItemVO)iterator.next()).getFormId()) == null ? itemInfo.getFormId() : (String)formRegionMap.get(itemInfo.getFormId()));
            formNames.append(itemInfo.getFormName()).append("\u3001");
        }
        if (formNames.length() > 0) {
            formNames.delete(formNames.length() - 1, formNames.length());
        }
        logResultMsgVO.setPeriodScheme(fetchInitTaskDTO.getPeriodScheme());
        logResultMsgVO.setUnitCode(fetchInitTaskDTO.getUnitCode());
        OrgDTO orgCondi = new OrgDTO();
        orgCondi.setAuthType(OrgDataOption.AuthType.NONE);
        orgCondi.setCode(fetchInitTaskDTO.getUnitCode());
        OrgDO orgDO = this.orgDataService.get(orgCondi);
        logResultMsgVO.setUnitName(orgDO == null ? fetchInitTaskDTO.getUnitCode() : String.format(TMPL_UNITNAME, fetchInitTaskDTO.getUnitCode(), orgDO.getName()));
        logResultMsgVO.setCurrency((String)fetchInitTaskDTO.getOtherEntity().get("MD_CURRENCY"));
        logResultMsgVO.setFormNames(formNames.toString());
        logResultMsgVO.setTaskName(fetchInitTaskDTO.getTaskTitle());
        logResultMsgVO.setSchemeName(fetchInitTaskDTO.getFormSchemeTitle());
        return logResultMsgVO;
    }

    @Override
    public Map<String, LogManagerInfoItemVO> refreshLogDetailData(LogDetailRefreshCondition logDetailRefreshCondition) {
        LogManagerCondition logManagerCondition = new LogManagerCondition();
        logManagerCondition.setRequestInstcId(logDetailRefreshCondition.getRequestInstcId());
        List<DcTaskLogEO> selectLog = this.taskLogManagerDao.selectLog(logManagerCondition);
        Assert.isNotEmpty(selectLog, (String)String.format("\u53d6\u6570\u4efb\u52a1\u3010%1$s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u6267\u884c\u8bb0\u5f55\uff0c\u8bf7\u7a0d\u540e\u91cd\u8bd5", logDetailRefreshCondition.getRequestInstcId()), (Object[])new Object[0]);
        FetchInitTaskDTO fetchInitTaskDTO = (FetchInitTaskDTO)JsonUtils.readValue((String)selectLog.get(0).getMessage(), FetchInitTaskDTO.class);
        DcTaskItemLogEO condi = new DcTaskItemLogEO();
        condi.setInstanceId(logDetailRefreshCondition.getRequestInstcId());
        condi.setDimType(FormRegionDimType.getInstance().getName());
        Map<String, FetchExecuteStatus> executeStatusMap = this.taskLogManagerDao.countItemExecuteStatus(condi.getInstanceId());
        return this.getItemInfoList(fetchInitTaskDTO, condi, executeStatusMap).stream().collect(Collectors.toMap(LogManagerInfoItemVO::getId, item -> item, (k1, k2) -> k2));
    }

    private List<LogManagerInfoItemVO> getItemInfoList(FetchInitTaskDTO fetchInitTaskDTO, DcTaskItemLogEO condi, Map<String, FetchExecuteStatus> executeStatusMap) {
        List<DcTaskItemLogEO> itemLogList = this.taskLogManagerDao.selectItemLog(condi);
        ArrayList<LogManagerInfoItemVO> itemInfoList = new ArrayList<LogManagerInfoItemVO>();
        LogManagerInfoItemVO itemInfo = null;
        FetchExecuteStatus executeStatus = null;
        for (DcTaskItemLogEO taskItemInfo : itemLogList) {
            executeStatus = executeStatusMap.get(taskItemInfo.getId());
            itemInfo = new LogManagerInfoItemVO();
            itemInfo.setId(taskItemInfo.getId());
            itemInfo.setDimCode(taskItemInfo.getDimCode());
            itemInfo.setDimType(taskItemInfo.getDimType());
            itemInfo.setExecuteState(taskItemInfo.getExecuteState());
            itemInfo.setRequestInstcId(condi.getInstanceId());
            itemInfo.setRequestTaskId(taskItemInfo.getId());
            itemInfo.setFormId(taskItemInfo.getDimCode());
            itemInfo.setStartTime(taskItemInfo.getStartTime() == null ? null : new Timestamp(taskItemInfo.getStartTime().getTime()));
            itemInfo.setResultLog(taskItemInfo.getResultLog());
            if (executeStatus != null) {
                itemInfo.setFormTaskCount(Integer.valueOf(executeStatus.getFailedCount() + executeStatus.getSuccessCount() + executeStatus.getExecuteCount()));
                itemInfo.setSuccessCount(Integer.valueOf(executeStatus.getSuccessCount()));
                itemInfo.setFailureCount(Integer.valueOf(executeStatus.getFailedCount()));
                itemInfo.setEndTime(executeStatus.getEndDate());
                itemInfo.setProcess(Double.valueOf(Double.valueOf(executeStatus.getFailedCount() + executeStatus.getSuccessCount()) / Double.valueOf(executeStatus.getTotal())));
            } else {
                itemInfo.setSuccessCount(Integer.valueOf(0));
                itemInfo.setFailureCount(Integer.valueOf(0));
                itemInfo.setProcess(Double.valueOf(0.0));
            }
            itemInfoList.add(itemInfo);
        }
        return itemInfoList;
    }

    @Override
    public List<LogManagerInfoItemVO> listLogItemData(String requestInstcId, String requestTaskId) {
        DcTaskItemLogEO condi = new DcTaskItemLogEO();
        condi.setInstanceId(requestInstcId);
        condi.setPreNodeId(requestTaskId);
        List<DcTaskItemLogEO> itemLogList = this.taskLogManagerDao.selectItemLog(condi);
        Map<String, FetchExecuteStatus> executeStatusMap = this.taskLogManagerDao.countItemExecuteStatus(requestInstcId);
        ArrayList<LogManagerInfoItemVO> itemInfoList = new ArrayList<LogManagerInfoItemVO>();
        LogManagerInfoItemVO itemInfo = null;
        FetchDataExecuteContext ctx = null;
        for (DcTaskItemLogEO taskItemInfo : itemLogList) {
            itemInfo = new LogManagerInfoItemVO();
            itemInfo.setId(taskItemInfo.getId());
            itemInfo.setExecuteState(taskItemInfo.getExecuteState());
            try {
                ctx = (FetchDataExecuteContext)JsonUtils.readValue((String)taskItemInfo.getMessage(), FetchDataExecuteContext.class);
            }
            catch (Exception e) {
                ctx = (FetchDataExecuteContext)JsonUtils.readValue((String)CompressUtil.deCompress((String)taskItemInfo.getMessage()), FetchDataExecuteContext.class);
            }
            if (ctx != null) {
                IBizComputationModel computationModel = this.bizModelGather.findComputationModelByCode(ctx.getComputationModelCode());
                itemInfo.setDimType(computationModel == null ? ctx.getComputationModelCode() : computationModel.getName());
                if (!CollectionUtils.isEmpty((Collection)ctx.getFixedSettingList())) {
                    itemInfo.setDimCode(((ExecuteSettingVO)ctx.getFixedSettingList().get(0)).getOptimizeRuleGroup());
                } else {
                    itemInfo.setDimType(taskItemInfo.getDimType().equals(FloatRegionAnalyzeDimType.getInstance().getName()) ? FloatRegionAnalyzeDimType.getInstance().getTitle() : taskItemInfo.getDimType());
                }
            } else {
                itemInfo.setDimCode(taskItemInfo.getDimCode());
                itemInfo.setDimType(taskItemInfo.getDimType().equals(FloatRegionAnalyzeDimType.getInstance().getName()) ? FloatRegionAnalyzeDimType.getInstance().getTitle() : taskItemInfo.getDimType());
            }
            itemInfo.setStartTime(taskItemInfo.getStartTime() == null ? null : new Timestamp(taskItemInfo.getStartTime().getTime()));
            itemInfo.setEndTime(taskItemInfo.getEndTime() != null ? new Timestamp(taskItemInfo.getEndTime().getTime()) : null);
            if (executeStatusMap.get(taskItemInfo.getId()) != null) {
                itemInfo.setFailureCount(Integer.valueOf(executeStatusMap.get(taskItemInfo.getId()).getFailedCount()));
                itemInfo.setEndTime(executeStatusMap.get(taskItemInfo.getId()).getEndDate());
                itemInfo.setProcess(Double.valueOf(Double.valueOf(executeStatusMap.get(taskItemInfo.getId()).getFailedCount() + executeStatusMap.get(taskItemInfo.getId()).getSuccessCount()) / Double.valueOf(executeStatusMap.get(taskItemInfo.getId()).getTotal())));
            }
            itemInfo.setResultLog(taskItemInfo.getResultLog());
            itemInfoList.add(itemInfo);
        }
        return itemInfoList;
    }

    @Override
    public String getLogResultLogById(String id) {
        return this.taskLogManagerDao.getResultLogById(id);
    }

    private Map<String, List<LogManagerInfoItemVO>> listFailedItemData(String requestInstcId, String requestTaskId) {
        LogManagerCondition logManagerCondition = new LogManagerCondition();
        logManagerCondition.setRequestInstcId(requestInstcId);
        List<DcTaskLogEO> selectLog = this.taskLogManagerDao.selectLog(logManagerCondition);
        Assert.isNotEmpty(selectLog, (String)String.format("\u53d6\u6570\u4efb\u52a1\u3010%1$s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u6267\u884c\u8bb0\u5f55\uff0c\u8bf7\u7a0d\u540e\u91cd\u8bd5", requestInstcId), (Object[])new Object[0]);
        FetchInitTaskDTO fetchInitTaskDTO = (FetchInitTaskDTO)JsonUtils.readValue((String)selectLog.get(0).getMessage(), FetchInitTaskDTO.class);
        DcTaskItemLogEO condi = new DcTaskItemLogEO();
        condi.setInstanceId(requestInstcId);
        if (!selectLog.get(0).getId().equals(requestTaskId)) {
            condi.setPreNodeId(requestTaskId);
        }
        condi.setExecuteState(Integer.valueOf(DataHandleState.FAILURE.getState()));
        List<LogManagerInfoItemVO> itemInfoList = this.getItemInfoList(fetchInitTaskDTO, condi, CollectionUtils.newHashMap());
        String formKey = null;
        HashMap<String, List<LogManagerInfoItemVO>> logManagerMap = new HashMap<String, List<LogManagerInfoItemVO>>();
        for (LogManagerInfoItemVO logManagerInfoItemVO : itemInfoList) {
            formKey = FetchDimType.FORM.getName().equals(logManagerInfoItemVO.getDimType()) ? logManagerInfoItemVO.getDimCode() : logManagerInfoItemVO.getDimCode().substring(0, logManagerInfoItemVO.getDimCode().indexOf(","));
            logManagerMap.computeIfAbsent(formKey, key -> new ArrayList());
            ((List)logManagerMap.get(formKey)).add(logManagerInfoItemVO);
        }
        return logManagerMap;
    }

    @Override
    public List<FetchSqlLogDTO> getExecuteSqlById(String requestInstcId, String requestTaskId) {
        List<FetchSqlLogDTO> executeSqlList = this.taskLogManagerDao.getExecuteSqlById(requestInstcId, "ALL".equals(requestTaskId) ? "" : requestTaskId);
        if (CollectionUtils.isEmpty(executeSqlList)) {
            return executeSqlList;
        }
        ArrayList result = CollectionUtils.newArrayList();
        HashSet<String> sqlSet = new HashSet<String>();
        for (FetchSqlLogDTO fetchSqlLogDTO : executeSqlList) {
            if (sqlSet.contains(fetchSqlLogDTO.getSql())) continue;
            if (StringUtils.isNull((String)fetchSqlLogDTO.getExecuteParam())) {
                fetchSqlLogDTO.setExecuteParam("");
            }
            sqlSet.add(fetchSqlLogDTO.getSql());
            result.add(fetchSqlLogDTO);
        }
        return result;
    }

    @Override
    public void exportErrorLog(String requestInstcId, String requestTaskId, HttpServletResponse response) {
        Map<String, List<LogManagerInfoItemVO>> failedFormLogMap = this.listFailedItemData(requestInstcId, "ALL".equals(requestTaskId) ? "" : requestTaskId);
        LogResultMsgVO logResultMsgVO = this.getLogDetailListById(requestInstcId);
        LogManagerCondition logManagerCondition = new LogManagerCondition();
        logManagerCondition.setRequestInstcId(requestInstcId);
        List<DcTaskLogEO> selectLog = this.taskLogManagerDao.selectLog(logManagerCondition);
        Assert.isNotEmpty(selectLog, (String)String.format("\u53d6\u6570\u4efb\u52a1\u3010%1$s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u6267\u884c\u8bb0\u5f55\uff0c\u8bf7\u7a0d\u540e\u91cd\u8bd5", requestInstcId), (Object[])new Object[0]);
        FetchInitTaskDTO fetchInitTaskDTO = (FetchInitTaskDTO)JsonUtils.readValue((String)selectLog.get(0).getMessage(), FetchInitTaskDTO.class);
        HashMap fetchFormMap = CollectionUtils.isEmpty((Collection)fetchInitTaskDTO.getFetchForms()) ? new HashMap() : fetchInitTaskDTO.getFetchForms().stream().collect(Collectors.toMap(FetchFormDTO::getId, item -> item));
        HashMap logManagerInfoItemVOMap = logResultMsgVO.getLogInfoItemTableData() == null ? new HashMap() : logResultMsgVO.getLogInfoItemTableData().stream().collect(Collectors.toMap(LogManagerInfoItemVO::getRequestTaskId, item -> item, (k1, k2) -> k2));
        ZipArchiveOutputStream zipArchiveOutputStream = null;
        ServletOutputStream servletOutputStream = null;
        try {
            String downLoadName;
            response.setContentType("multipart/form-data");
            if ("ALL".equals(requestTaskId) && failedFormLogMap.size() > 1) {
                zipArchiveOutputStream = new ZipArchiveOutputStream((OutputStream)response.getOutputStream());
                downLoadName = "\u9519\u8bef\u65e5\u5fd7\u4fe1\u606f.zip";
                response.setHeader("Content-Disposition", "attachment;filename=" + new String(downLoadName.getBytes(), StandardCharsets.ISO_8859_1));
                zipArchiveOutputStream.setUseZip64(Zip64Mode.AsNeeded);
                zipArchiveOutputStream.setEncoding("utf-8");
                for (Map.Entry<String, List<LogManagerInfoItemVO>> failedFormLogEntry : failedFormLogMap.entrySet()) {
                    String fileName = String.format("\u9519\u8bef\u65e5\u5fd7(%1$s).txt", fetchFormMap.get(failedFormLogEntry.getKey()) == null ? failedFormLogEntry.getKey() : ((FetchFormDTO)fetchFormMap.get(failedFormLogEntry.getKey())).getFormTitle());
                    ZipArchiveEntry entry = new ZipArchiveEntry(fileName);
                    zipArchiveOutputStream.putArchiveEntry(entry);
                    zipArchiveOutputStream.write(this.errorMsgHandle(logResultMsgVO, failedFormLogEntry.getValue(), fetchFormMap.get(failedFormLogEntry.getKey()) == null ? failedFormLogEntry.getKey() : ((FetchFormDTO)fetchFormMap.get(failedFormLogEntry.getKey())).getFormTitle(), fetchInitTaskDTO).getBytes(StandardCharsets.UTF_8));
                    zipArchiveOutputStream.closeArchiveEntry();
                }
            } else {
                servletOutputStream = response.getOutputStream();
                downLoadName = "\u9519\u8bef\u65e5\u5fd7.txt";
                response.setContentType("text/plain;UTF-8");
                response.setHeader("Content-Disposition", "attachment;filename=" + new String(downLoadName.getBytes(), StandardCharsets.ISO_8859_1));
                if ("ALL".equals(requestTaskId) && failedFormLogMap.size() == 1) {
                    for (Map.Entry<String, List<LogManagerInfoItemVO>> failedFormLogEntry : failedFormLogMap.entrySet()) {
                        servletOutputStream.write(this.errorMsgHandle(logResultMsgVO, failedFormLogEntry.getValue(), logManagerInfoItemVOMap.get(failedFormLogEntry.getKey()) == null ? failedFormLogEntry.getKey() : ((LogManagerInfoItemVO)logManagerInfoItemVOMap.get(failedFormLogEntry.getKey())).getFormName(), fetchInitTaskDTO).getBytes(StandardCharsets.UTF_8));
                    }
                } else {
                    servletOutputStream.write(this.errorMsgHandle(logResultMsgVO, failedFormLogMap.values().stream().findFirst().orElse(null), ((LogManagerInfoItemVO)logManagerInfoItemVOMap.get(requestTaskId)).getFormName(), fetchInitTaskDTO).getBytes(StandardCharsets.UTF_8));
                }
            }
        }
        catch (Exception e) {
            logger.error("\u5bfc\u51fa\u9519\u8bef\u65e5\u5fd7\u5931\u8d25", e);
            throw new BusinessRuntimeException("\u5bfc\u51fa\u9519\u8bef\u65e5\u5fd7\u5931\u8d25", (Throwable)e);
        }
        finally {
            if (zipArchiveOutputStream != null) {
                try {
                    zipArchiveOutputStream.close();
                }
                catch (IOException e) {
                    logger.error("\u538b\u7f29\u6587\u4ef6\u6d41\u5173\u95ed\u51fa\u73b0\u9519\u8bef", e);
                }
            }
            if (servletOutputStream != null) {
                try {
                    servletOutputStream.close();
                }
                catch (IOException e) {
                    logger.error("\u8bf7\u6c42\u54cd\u5e94\u6587\u4ef6\u6d41\u5173\u95ed\u51fa\u73b0\u9519\u8bef", e);
                }
            }
        }
    }

    private String errorMsgHandle(LogResultMsgVO logResultMsgVO, List<LogManagerInfoItemVO> logManagerInfoItemVOList, String tableName, FetchInitTaskDTO fetchInitTaskDTO) {
        String newLine = System.lineSeparator();
        String errorHeadMsg = String.format("%-50s\t\t", "\u4efb\u52a1\u540d\u79f0\uff1a" + (StringUtils.isEmpty((String)logResultMsgVO.getTaskName()) ? "" : logResultMsgVO.getTaskName())) + String.format("%-50s", "\u65b9\u6848\u540d\u79f0\uff1a" + (StringUtils.isEmpty((String)logResultMsgVO.getSchemeName()) ? "" : logResultMsgVO.getSchemeName())) + newLine + String.format("%-50s\t", "\u5355\u4f4d\uff1a" + (StringUtils.isEmpty((String)logResultMsgVO.getUnitCode()) ? "" : logResultMsgVO.getUnitCode())) + String.format("%-50s", "\u671f\u95f4\uff1a" + (StringUtils.isEmpty((String)logResultMsgVO.getPeriodScheme()) ? this.dateProcess(this.getPeriodSchemeByDate(fetchInitTaskDTO.getEndDateStr())) : this.dateProcess(logResultMsgVO.getPeriodScheme()))) + newLine + String.format("%-50s\t\t", "\u5e01\u79cd\uff1a" + (StringUtils.isEmpty((String)logResultMsgVO.getCurrency()) ? "" : logResultMsgVO.getCurrency())) + String.format("%-50s", "\u62a5\u8868\uff1a" + tableName) + newLine;
        StringBuilder errorContentMsg = new StringBuilder();
        errorContentMsg.append(errorHeadMsg);
        if (CollectionUtils.isEmpty(logManagerInfoItemVOList)) {
            return errorContentMsg.toString();
        }
        for (LogManagerInfoItemVO logManagerInfoItemVO : logManagerInfoItemVOList) {
            errorContentMsg.append(String.format("%-50s\t", "\u5f00\u59cb\u65f6\u95f4\uff1a" + DateUtils.format((Date)logManagerInfoItemVO.getStartTime(), (String)"yyyy-MM-dd HH:mm:ss")));
            errorContentMsg.append(String.format("%-50s", "\u7ed3\u675f\u65f6\u95f4\uff1a" + DateUtils.format((Date)logManagerInfoItemVO.getEndTime(), (String)"yyyy-MM-dd HH:mm:ss"))).append(newLine);
            errorContentMsg.append(String.format("%-50s", "\u65e5\u5fd7\u4fe1\u606f\uff1a" + logManagerInfoItemVO.getResultLog()));
            errorContentMsg.append(newLine).append(newLine).append(newLine).append(newLine);
        }
        return errorContentMsg.toString();
    }

    private String dateProcess(String date) {
        String year = date.substring(0, 4);
        String periodFlag = date.substring(4, 5);
        String period = date.substring(7, 9);
        if ("Y".equals(periodFlag)) {
            return year + "\u5e74" + period + "\u6708";
        }
        if ("J".equals(periodFlag)) {
            return year + "\u5e74" + period + "\u5b63\u5ea6";
        }
        if ("H".equals(periodFlag)) {
            return year + "\u5e74" + ("01".equals(period) ? "\u4e0a\u534a\u5e74" : "\u4e0b\u534a\u5e74");
        }
        return year + "\u5e74";
    }

    private String getPeriodSchemeByDate(String dateStr) {
        Date date = DateUtils.parse((String)dateStr);
        return String.format("%1$dY%2$s", DateUtils.getDateFieldValue((Date)date, (int)1), CommonUtil.lpad((String)String.valueOf(DateUtils.getDateFieldValue((Date)date, (int)2)), (String)"0", (int)4));
    }
}

