/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.efdc.pojo.EfdcInfo
 *  com.jiuqi.va.domain.common.JSONUtil
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.bde.fetch.impl.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.bde.fetch.impl.dao.GcFetchItemTaskLogDao;
import com.jiuqi.gcreport.bde.fetch.impl.dao.GcFetchTaskLogClobDao;
import com.jiuqi.gcreport.bde.fetch.impl.dao.GcFetchTaskLogDao;
import com.jiuqi.gcreport.bde.fetch.impl.entity.BatchBdeFetchLog;
import com.jiuqi.gcreport.bde.fetch.impl.entity.GcFetchItemTaskLogEO;
import com.jiuqi.gcreport.bde.fetch.impl.entity.GcFetchTaskLogClobEO;
import com.jiuqi.gcreport.bde.fetch.impl.entity.GcFetchTaskLogEO;
import com.jiuqi.gcreport.bde.fetch.impl.enums.FetchExecuteStateEnum;
import com.jiuqi.gcreport.bde.fetch.impl.service.GcFetchTaskLogService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.efdc.pojo.EfdcInfo;
import com.jiuqi.va.domain.common.JSONUtil;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GcFetchTaskLogServiceImpl
implements GcFetchTaskLogService {
    @Autowired
    private GcFetchTaskLogDao gcFetchTaskLogDao;
    @Autowired
    private GcFetchItemTaskLogDao gcFetchItemTaskLogDao;
    @Autowired
    private GcFetchTaskLogClobDao gcFetchTaskLogClobDao;

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void insertItemTaskLogs(List<GcFetchItemTaskLogEO> itemTaskLogList) {
        this.gcFetchItemTaskLogDao.saveAll(itemTaskLogList);
    }

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void updateItemTaskLog(GcFetchItemTaskLogEO itemTaskLog) {
        GcFetchTaskLogClobEO fetchTaskLogClob = null;
        if (!StringUtils.isEmpty((String)itemTaskLog.getErrorLog())) {
            itemTaskLog.setResultContent(itemTaskLog.getResultContent());
        }
        if (!StringUtils.isEmpty((String)itemTaskLog.getResultContent()) && itemTaskLog.getResultContent().length() > 1000) {
            fetchTaskLogClob = new GcFetchTaskLogClobEO(itemTaskLog.getId(), itemTaskLog.getResultContent());
            itemTaskLog.setResultContent("");
        }
        this.gcFetchItemTaskLogDao.update(itemTaskLog);
        if (fetchTaskLogClob != null) {
            this.gcFetchTaskLogClobDao.save(fetchTaskLogClob);
        }
    }

    @Override
    public GcFetchItemTaskLogEO getItemTask(String fetchTaskId, String formId, String regionId) {
        return this.gcFetchItemTaskLogDao.getItemTask(fetchTaskId, formId, regionId);
    }

    @Override
    public Double queryProcess(String fetchTaskId) {
        return this.gcFetchItemTaskLogDao.queryProcess(fetchTaskId);
    }

    @Override
    public String getLastFetchInfo(String fetchTaskId) {
        return this.gcFetchItemTaskLogDao.getLastFetchInfo(fetchTaskId);
    }

    @Override
    public List<GcFetchItemTaskLogEO> getErrorItemTaskList(String fetchTaskId) {
        List<GcFetchItemTaskLogEO> gcFetchItemTaskLogEOList = this.gcFetchItemTaskLogDao.getErrorItemTaskList(fetchTaskId);
        if (CollectionUtils.isEmpty(gcFetchItemTaskLogEOList)) {
            return gcFetchItemTaskLogEOList;
        }
        List<String> idList = gcFetchItemTaskLogEOList.stream().filter(item -> StringUtils.isEmpty((String)item.getResultContent())).map(GcFetchItemTaskLogEO::getId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(idList)) {
            return gcFetchItemTaskLogEOList;
        }
        List<GcFetchTaskLogClobEO> clobEOList = this.gcFetchTaskLogClobDao.listById(idList);
        if (CollectionUtils.isEmpty(clobEOList)) {
            return gcFetchItemTaskLogEOList;
        }
        Map<String, String> clobEOMap = clobEOList.stream().collect(Collectors.toMap(GcFetchTaskLogClobEO::getId, GcFetchTaskLogClobEO::getClobContent));
        for (GcFetchItemTaskLogEO gcFetchItemTaskLogEO : gcFetchItemTaskLogEOList) {
            if (!clobEOMap.containsKey(gcFetchItemTaskLogEO.getId())) continue;
            gcFetchItemTaskLogEO.setResultContent(clobEOMap.get(gcFetchItemTaskLogEO.getId()));
        }
        return gcFetchItemTaskLogEOList;
    }

    @Override
    public String getArgContentById(String requestTaskId) {
        GcFetchTaskLogEO gcFetchTaskLogEO = this.gcFetchTaskLogDao.get(requestTaskId);
        String argContent = null;
        if (gcFetchTaskLogEO.getId().equals(gcFetchTaskLogEO.getGroupId())) {
            GcFetchTaskLogClobEO gcFetchTaskLogClobEO = this.gcFetchTaskLogClobDao.get(requestTaskId);
            argContent = gcFetchTaskLogClobEO.getClobContent();
        } else {
            GcFetchTaskLogClobEO gcFetchTaskLogClobEO = this.gcFetchTaskLogClobDao.get(gcFetchTaskLogEO.getGroupId());
            argContent = gcFetchTaskLogClobEO.getClobContent();
        }
        return argContent;
    }

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void updateErrorStateByFetchId(String fetchTaskId) {
        this.gcFetchItemTaskLogDao.updateErrorStateByFetchId(fetchTaskId);
    }

    @Override
    public int countExecuteTask(String groupId) {
        return this.gcFetchTaskLogDao.countExecuteTask(groupId);
    }

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void updateFetchLog(GcFetchTaskLogEO fetchTaskLog, GcFetchTaskLogClobEO fetchTaskLogClob) {
        this.gcFetchTaskLogDao.update(fetchTaskLog);
        if (fetchTaskLogClob != null) {
            this.gcFetchTaskLogClobDao.save(fetchTaskLogClob);
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void saveGcFetchTaskLog(GcFetchTaskLogEO fetchTaskLog) {
        this.gcFetchTaskLogDao.save(fetchTaskLog);
    }

    @Override
    public List<GcFetchTaskLogEO> getTaskByState(String groupId, Integer executeState) {
        return this.gcFetchTaskLogDao.getTaskByState(groupId, executeState);
    }

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public List<GcFetchTaskLogClobEO> listById(List<String> idList) {
        return this.gcFetchTaskLogClobDao.listById(idList);
    }

    @Override
    public void saveClob(GcFetchTaskLogClobEO clobEO) {
        this.gcFetchTaskLogClobDao.save(clobEO);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public String saveFetchLog(EfdcInfo efdcInfo, String asyncTaskId) {
        String requestTaskId = null;
        if (efdcInfo.getVariableMap().get("REQUEST_TASK_ID") != null) {
            return efdcInfo.getVariableMap().get("REQUEST_TASK_ID").toString();
        }
        requestTaskId = UUIDUtils.newHalfGUIDStr();
        ObjectNode argContentJson = JSONUtil.parseObject((String)JSONUtil.toJSONString((Object)efdcInfo));
        argContentJson.set("user", (JsonNode)JSONUtil.parseObject((String)JSONUtil.toJSONString((Object)NpContextHolder.getContext().getUser())));
        String argContentStr = JSONUtil.toJSONString((Object)argContentJson);
        GcFetchTaskLogClobEO fetchTaskLogClob = null;
        GcFetchTaskLogEO fetchTaskLogEO = new GcFetchTaskLogEO();
        fetchTaskLogEO.setId(requestTaskId);
        fetchTaskLogEO.setCreateTime(new Date());
        fetchTaskLogEO.setExecuteState(FetchExecuteStateEnum.EXECUTE.getStateNum());
        fetchTaskLogEO.setGroupId(requestTaskId);
        fetchTaskLogEO.setAsyncTaskId(asyncTaskId);
        fetchTaskLogClob = new GcFetchTaskLogClobEO();
        fetchTaskLogClob.setId(requestTaskId);
        fetchTaskLogClob.setClobContent(argContentStr);
        this.saveGcFetchTaskLog(fetchTaskLogEO);
        this.gcFetchTaskLogClobDao.save(fetchTaskLogClob);
        return requestTaskId;
    }

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void updateFetchLog(EfdcInfo efdcInfo, String asyncTaskId, String requestTaskId, Integer executeState, String log) {
        if (StringUtils.isEmpty((String)asyncTaskId) || StringUtils.isEmpty((String)requestTaskId)) {
            return;
        }
        GcFetchTaskLogClobEO fetchTaskLogClob = null;
        GcFetchTaskLogEO fetchTaskLogEO = new GcFetchTaskLogEO();
        fetchTaskLogEO.setId(requestTaskId);
        fetchTaskLogEO.setExecuteState(executeState);
        fetchTaskLogEO.setAsyncTaskId(asyncTaskId);
        fetchTaskLogEO.setUnitCode(((DimensionValue)efdcInfo.getDimensionSet().get("MD_ORG")).getValue());
        if (efdcInfo.getVariableMap().get("GROUP_ID") != null) {
            BatchBdeFetchLog batchBdeFetchLog = new BatchBdeFetchLog();
            batchBdeFetchLog.setLog(log);
            batchBdeFetchLog.setRequestTaskId(requestTaskId);
            batchBdeFetchLog.setAsyncTaskId(asyncTaskId);
            batchBdeFetchLog.setUnitCode(((DimensionValue)efdcInfo.getDimensionSet().get("MD_ORG")).getValue());
            batchBdeFetchLog.setUnitName(efdcInfo.getVariableMap().get("UNIT_NAME") == null ? ((DimensionValue)efdcInfo.getDimensionSet().get("MD_ORG")).getValue() : efdcInfo.getVariableMap().get("UNIT_NAME").toString());
            batchBdeFetchLog.setPeriod(efdcInfo.getParas() == null || efdcInfo.getParas().get("DATATIME") == null ? "" : efdcInfo.getParas().get("DATATIME").toString());
            batchBdeFetchLog.setCurrency(efdcInfo.getDimensionSet().get("MD_CURRENCY") == null ? null : ((DimensionValue)efdcInfo.getDimensionSet().get("MD_CURRENCY")).getValue());
            String objectLog = JSONUtil.toJSONString((Object)batchBdeFetchLog);
            fetchTaskLogClob = new GcFetchTaskLogClobEO(requestTaskId, objectLog);
        }
        this.updateFetchLog(fetchTaskLogEO, fetchTaskLogClob);
    }
}

