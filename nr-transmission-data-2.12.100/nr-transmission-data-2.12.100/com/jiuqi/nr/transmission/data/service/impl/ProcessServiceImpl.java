/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.monitor.JobMonitorManager
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJobManager
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nvwa.certification.dto.NvwaAppRequestDTO
 *  com.jiuqi.nvwa.certification.manage.INvwaAppRequestManage
 *  com.jiuqi.nvwa.subsystem.core.model.SubServer
 *  org.apache.http.client.utils.URIBuilder
 *  org.apache.http.entity.ContentType
 *  org.springframework.http.HttpEntity
 *  org.springframework.http.HttpHeaders
 *  org.springframework.http.HttpMethod
 *  org.springframework.http.ResponseEntity
 *  org.springframework.web.client.RestTemplate
 */
package com.jiuqi.nr.transmission.data.service.impl;

import com.jiuqi.bi.core.jobs.monitor.JobMonitorManager;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJobManager;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.transmission.data.common.MultilingualLog;
import com.jiuqi.nr.transmission.data.dto.SyncHistoryDTO;
import com.jiuqi.nr.transmission.data.intf.DataImportResult;
import com.jiuqi.nr.transmission.data.intf.InstanceResult;
import com.jiuqi.nr.transmission.data.monitor.TransmissionMonitorInfo;
import com.jiuqi.nr.transmission.data.monitor.TransmissionState;
import com.jiuqi.nr.transmission.data.service.IProcessService;
import com.jiuqi.nr.transmission.data.service.IReportParamService;
import com.jiuqi.nr.transmission.data.service.ISyncHistoryService;
import com.jiuqi.nvwa.certification.dto.NvwaAppRequestDTO;
import com.jiuqi.nvwa.certification.manage.INvwaAppRequestManage;
import com.jiuqi.nvwa.subsystem.core.model.SubServer;
import java.net.URI;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

@Service
public class ProcessServiceImpl
implements IProcessService {
    private static final Logger logger = LoggerFactory.getLogger(ProcessServiceImpl.class);
    @Autowired
    private CacheObjectResourceRemote cacheObjectResourceRemote;
    @Autowired
    private ISyncHistoryService syncHistoryService;
    @Autowired
    private IReportParamService reportParamService;
    @Qualifier(value="syncRestTemplate")
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private INvwaAppRequestManage requestManage;

    @Override
    public TransmissionMonitorInfo queryProcess(String executeKey, int type) {
        TransmissionMonitorInfo transmissionMonitorInfo = new TransmissionMonitorInfo();
        if (type == 0) {
            try {
                return this.queryRemoteProcess(executeKey);
            }
            catch (Exception e) {
                logger.info(e.getMessage());
            }
        } else {
            if (type == 1) {
                return this.queryLocalProcess(executeKey, 1);
            }
            if (type == 2) {
                return this.queryLocalProcess(executeKey, 2);
            }
        }
        return transmissionMonitorInfo;
    }

    public TransmissionMonitorInfo queryLocalProcess(String executeKey, int type) {
        int statusByTaskState;
        TransmissionState state;
        Object process = this.cacheObjectResourceRemote.find((Object)executeKey);
        TransmissionMonitorInfo processInfo = new TransmissionMonitorInfo();
        if (process != null) {
            processInfo = (TransmissionMonitorInfo)process;
        }
        if (type == 1 && (state = processInfo.getState()) != null && ((statusByTaskState = state.getValue()) == 3 || statusByTaskState == 4 || statusByTaskState == 6)) {
            SyncHistoryDTO syncHistoryDTO = this.syncHistoryService.get(executeKey);
            processInfo.setEndTime(syncHistoryDTO.getEndTime());
        }
        return processInfo;
    }

    public TransmissionMonitorInfo queryRemoteProcess(String executeKey) throws Exception {
        SubServer parentServeNode = null;
        try {
            parentServeNode = this.reportParamService.getParentServeNode();
        }
        catch (Exception e) {
            logger.info(e.getMessage());
        }
        return this.queryStatus(executeKey, parentServeNode);
    }

    public TransmissionMonitorInfo queryStatus(String executeKey, SubServer targetServe) throws Exception {
        TransmissionState state;
        TransmissionMonitorInfo monitorInfo = new TransmissionMonitorInfo();
        SyncHistoryDTO syncHistoryDTO = this.syncHistoryService.get(executeKey);
        monitorInfo.setExecuteKey(executeKey);
        monitorInfo.setSchemeKey(syncHistoryDTO.getSchemeKey());
        if (syncHistoryDTO.getStatus() == 3 || syncHistoryDTO.getStatus() == 4 || syncHistoryDTO.getStatus() == 6) {
            monitorInfo.setState(TransmissionState.getTaskStateByValue(syncHistoryDTO.getStatus()));
            monitorInfo.setThisHistory(syncHistoryDTO);
            monitorInfo.setProcess(1.0);
            return monitorInfo;
        }
        Object process = this.cacheObjectResourceRemote.find((Object)executeKey);
        TransmissionMonitorInfo monitorInfo2 = new TransmissionMonitorInfo();
        if (process != null) {
            monitorInfo2 = (TransmissionMonitorInfo)process;
        }
        if (TransmissionState.FINISHED.equals((Object)(state = monitorInfo2.getState())) || TransmissionState.SOMESUCCESS.equals((Object)state)) {
            NvwaAppRequestDTO authInfo;
            if (targetServe == null || !StringUtils.hasText(targetServe.getName())) {
                logger.info("\u65e0\u6cd5\u83b7\u53d6\u4e0a\u7ea7\u7528\u6237");
                String userErrorMessage = MultilingualLog.queryProcessMessage(1);
                this.updateHistory(syncHistoryDTO, syncHistoryDTO.getDetail() + "\r\n" + userErrorMessage);
                throw new RuntimeException(userErrorMessage);
            }
            try {
                authInfo = this.requestManage.getAuthInfo(targetServe.getName());
            }
            catch (Exception e) {
                logger.info("\u5f53\u524d\u7528\u6237\u6ca1\u6709\u6743\u9650\u4e0a\u4f20\u6570\u636e");
                throw new RuntimeException(MultilingualLog.queryProcessMessage(2), e);
            }
            if (authInfo == null) {
                logger.info("\u5f53\u524d\u7528\u6237\u6ca1\u6709\u6743\u9650\u4e0a\u4f20\u6570\u636e");
                throw new RuntimeException(MultilingualLog.queryProcessMessage(2));
            }
            Map headerMap = authInfo.getHeaderMap();
            Set keySet = headerMap.keySet();
            HttpHeaders headers = new HttpHeaders();
            for (String key : keySet) {
                headers.set(key, (String)headerMap.get(key));
            }
            String uri = targetServe.getBackendAddr();
            String api = "api/v1/sync/scheme/sync/process/" + executeKey + "/" + 1;
            try {
                URI url = new URIBuilder(uri).setPath(api).build();
                HttpEntity httpEntity = new HttpEntity((MultiValueMap)headers);
                ResponseEntity forEntity = this.restTemplate.exchange(url, HttpMethod.GET, httpEntity, TransmissionMonitorInfo.class);
                monitorInfo2 = (TransmissionMonitorInfo)forEntity.getBody();
            }
            catch (Exception e) {
                logger.error("\u591a\u7ea7\u90e8\u7f72\u8fdb\u5ea6\u67e5\u8be2\u5f02\u5e38:" + e.getMessage(), e);
                this.setMonitorInfoAndHistory(monitorInfo, syncHistoryDTO);
                return monitorInfo;
            }
            if (monitorInfo2 == null || !StringUtils.hasText(monitorInfo2.getId())) {
                InstanceResult instanceresult = new InstanceResult();
                try {
                    instanceresult = this.getInstance(syncHistoryDTO, targetServe);
                }
                catch (Exception e1) {
                    this.setMonitorInfoAndHistory(monitorInfo, syncHistoryDTO);
                    return monitorInfo;
                }
                if (StringUtils.hasText(instanceresult.getIntsance())) {
                    monitorInfo.setThisHistory(syncHistoryDTO);
                    monitorInfo.setState(TransmissionState.PROCESSING);
                    monitorInfo.setResult(syncHistoryDTO.getResult());
                    monitorInfo.setDetail(syncHistoryDTO.getDetail());
                } else {
                    this.setMonitorInfoAndHistory(monitorInfo, syncHistoryDTO);
                }
                return monitorInfo;
            }
            String detail = monitorInfo2.getDetail();
            if (detail != null) {
                syncHistoryDTO.setDetail(detail);
                monitorInfo.setDetail(detail);
            }
            monitorInfo.setStates(monitorInfo2.getStates());
            syncHistoryDTO.setStatus(monitorInfo2.getState().getValue());
            if (TransmissionState.FINISHED.equals((Object)monitorInfo2.getState()) || TransmissionState.ERROR.equals((Object)monitorInfo2.getState()) || TransmissionState.SOMESUCCESS.equals((Object)monitorInfo2.getState())) {
                syncHistoryDTO.setEndTime(monitorInfo2.getEndTime());
                syncHistoryDTO.setResult(monitorInfo2.getResult());
                monitorInfo.setThisHistory(syncHistoryDTO);
                logger.info(monitorInfo2.getDetail());
            }
            this.syncHistoryService.update(syncHistoryDTO);
            monitorInfo.setProcess(0.5 + monitorInfo2.getProcess() / 2.0);
        } else if (!StringUtils.hasText(monitorInfo2.getId()) || TransmissionState.ERROR.equals((Object)state)) {
            syncHistoryDTO.setEndTime(new Date());
            syncHistoryDTO.setStatus(4);
            this.syncHistoryService.update(syncHistoryDTO);
            monitorInfo.setExecuteKey(executeKey);
            monitorInfo.setThisHistory(syncHistoryDTO);
            monitorInfo.setState(TransmissionState.ERROR);
            monitorInfo.setProcess(1.0);
            monitorInfo.setResult(monitorInfo2.getResult());
            monitorInfo.setDetail(syncHistoryDTO.getDetail());
        } else {
            BeanUtils.copyProperties(monitorInfo2, monitorInfo);
            monitorInfo.setProcess(monitorInfo2.getProcess() / 2.0);
        }
        return monitorInfo;
    }

    private void setMonitorInfoAndHistory(TransmissionMonitorInfo monitorInfo, SyncHistoryDTO syncHistoryDTO) {
        this.updateHistory(syncHistoryDTO, syncHistoryDTO.getDetail());
        monitorInfo.setThisHistory(syncHistoryDTO);
        monitorInfo.setState(TransmissionState.ERROR);
        monitorInfo.setResult(new DataImportResult(false));
        monitorInfo.setDetail(syncHistoryDTO.getDetail());
        monitorInfo.setProcess(1.0);
        this.syncHistoryService.update(syncHistoryDTO);
    }

    private void updateHistory(SyncHistoryDTO syncHistoryDTO, String detail) {
        syncHistoryDTO.setEndTime(new Date());
        syncHistoryDTO.setStatus(TransmissionState.ERROR.getValue());
        syncHistoryDTO.setDetail(detail);
        this.syncHistoryService.update(syncHistoryDTO);
    }

    private HttpHeaders getHttpHeaders(SubServer targetServe) {
        NvwaAppRequestDTO authInfo = this.requestManage.getAuthInfo(targetServe.getName());
        Map headerMap = authInfo.getHeaderMap();
        HttpHeaders headers = new HttpHeaders();
        Set keySet = headerMap.keySet();
        for (String key : keySet) {
            headers.set(key, (String)headerMap.get(key));
        }
        headers.set("tenant", "__default_tenant__");
        headers.add("Accept", ContentType.APPLICATION_JSON.toString());
        return headers;
    }

    private InstanceResult getInstance(SyncHistoryDTO syncHistoryDTO, SubServer targetServe) throws Exception {
        String instanceId = syncHistoryDTO.getInstanceId();
        HttpHeaders headers = this.getHttpHeaders(targetServe);
        String uri = targetServe.getBackendAddr();
        String api1 = "api/v1/sync/scheme/sync/get_instance";
        LinkedMultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.add("instanceId", instanceId);
        HttpEntity httpEntity = new HttpEntity(form, (MultiValueMap)headers);
        InstanceResult instanceResult = new InstanceResult();
        try {
            URI url = new URIBuilder(uri).setPath(api1).build();
            instanceResult = (InstanceResult)this.restTemplate.postForObject(url, (Object)httpEntity, InstanceResult.class);
            assert (instanceResult != null);
            if (!StringUtils.hasText(instanceResult.getIntsance())) {
                this.updateHistory(syncHistoryDTO, syncHistoryDTO.getDetail() + MultilingualLog.queryProcessMessage(3));
            }
        }
        catch (Exception e) {
            this.updateHistory(syncHistoryDTO, syncHistoryDTO.getDetail() + MultilingualLog.queryProcessMessage(3) + e.getMessage());
            logger.error("\u4e3b\u670d\u52a1\u88c5\u5165\u6570\u636e\u5f02\u5e38", e);
            throw e;
        }
        return instanceResult;
    }

    @Override
    public String getInstance(String id) {
        RealTimeJobManager instance = RealTimeJobManager.getInstance();
        JobMonitorManager monitorManager = instance.getMonitorManager();
        String jobRunningInstanceID = null;
        try {
            jobRunningInstanceID = monitorManager.getJobRunningInstanceID(id);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return jobRunningInstanceID;
    }
}

