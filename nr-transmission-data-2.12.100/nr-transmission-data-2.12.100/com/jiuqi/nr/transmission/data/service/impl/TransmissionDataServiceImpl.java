/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.period.util.JacksonUtils
 *  com.jiuqi.nvwa.certification.dto.NvwaAppRequestDTO
 *  com.jiuqi.nvwa.certification.manage.INvwaAppRequestManage
 *  com.jiuqi.nvwa.subsystem.core.SubSystemException
 *  com.jiuqi.nvwa.subsystem.core.manage.ISubServerManager
 *  com.jiuqi.nvwa.subsystem.core.model.SubServer
 *  org.apache.http.client.utils.URIBuilder
 *  org.apache.http.entity.ContentType
 *  org.springframework.http.HttpEntity
 *  org.springframework.http.HttpHeaders
 *  org.springframework.http.MediaType
 *  org.springframework.web.client.RestTemplate
 */
package com.jiuqi.nr.transmission.data.service.impl;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.period.util.JacksonUtils;
import com.jiuqi.nr.transmission.data.common.MultilingualLog;
import com.jiuqi.nr.transmission.data.dto.SyncSchemeDTO;
import com.jiuqi.nr.transmission.data.dto.SyncSchemeParamDTO;
import com.jiuqi.nr.transmission.data.intf.SyncFileParam;
import com.jiuqi.nr.transmission.data.intf.TransmissionDataParam;
import com.jiuqi.nr.transmission.data.intf.TransmissionResult;
import com.jiuqi.nr.transmission.data.intf.UserInfoParam;
import com.jiuqi.nr.transmission.data.service.ISyncHistoryService;
import com.jiuqi.nr.transmission.data.service.ISyncSchemeService;
import com.jiuqi.nr.transmission.data.service.ITransmissionDataService;
import com.jiuqi.nvwa.certification.dto.NvwaAppRequestDTO;
import com.jiuqi.nvwa.certification.manage.INvwaAppRequestManage;
import com.jiuqi.nvwa.subsystem.core.SubSystemException;
import com.jiuqi.nvwa.subsystem.core.manage.ISubServerManager;
import com.jiuqi.nvwa.subsystem.core.model.SubServer;
import java.io.File;
import java.net.URI;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

@Service
public class TransmissionDataServiceImpl
implements ITransmissionDataService {
    private static final Logger logger = LoggerFactory.getLogger(TransmissionDataServiceImpl.class);
    @Autowired
    private ISyncHistoryService syncHistoryService;
    @Qualifier(value="syncRestTemplate")
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private INvwaAppRequestManage requestManage;
    @Autowired
    private ISyncSchemeService syncSchemeService;
    @Autowired
    private ISubServerManager subServerManager;

    @Override
    public TransmissionResult pushData(TransmissionDataParam transmissionDataParam, SubServer targetServe, AsyncTaskMonitor monitor) throws Exception {
        NvwaAppRequestDTO authInfo;
        File file = transmissionDataParam.getFile();
        if (targetServe == null || !StringUtils.hasText(targetServe.getName())) {
            logger.info("\u65e0\u6cd5\u83b7\u53d6\u4e0a\u7ea7\u670d\u52a1");
            throw new RuntimeException(MultilingualLog.pushDataMessage(1));
        }
        try {
            authInfo = this.requestManage.getAuthInfo(targetServe.getName());
        }
        catch (Exception e) {
            logger.info("\u5f53\u524d\u7528\u6237\u6ca1\u6709\u6743\u9650\u4e0a\u4f20\u6570\u636e");
            throw new RuntimeException(MultilingualLog.pushDataMessage(2) + e.getMessage(), e);
        }
        if (authInfo == null) {
            logger.info("\u5f53\u524d\u7528\u6237\u6ca1\u6709\u6743\u9650\u4e0a\u4f20\u6570\u636e");
            throw new RuntimeException(MultilingualLog.pushDataMessage(2));
        }
        Map headerMap = authInfo.getHeaderMap();
        String uri = targetServe.getBackendAddr();
        String api = "api/v1/sync/scheme/sync/accept";
        HttpHeaders headers = new HttpHeaders();
        Set keySet = headerMap.keySet();
        for (String key : keySet) {
            headers.set(key, (String)headerMap.get(key));
        }
        headers.setContentType(MediaType.parseMediaType((String)"multipart/form-data; charset=UTF-8"));
        headers.set("tenant", "__default_tenant__");
        headers.add("Accept", ContentType.APPLICATION_JSON.toString());
        Locale locale = NpContextHolder.getContext().getLocale();
        String languageTag = locale.toLanguageTag();
        headers.set("Accept-Language", languageTag);
        FileSystemResource resource = new FileSystemResource(file);
        LinkedMultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
        SyncSchemeParamDTO syncSchemeParamDTO = transmissionDataParam.getSyncSchemeParamDTO().getSyncSchemeParamDTOAfterMapping();
        if (!StringUtils.hasText(syncSchemeParamDTO.getSchemeName())) {
            SyncSchemeDTO syncSchemeDTO = this.syncSchemeService.getWithOutParam(transmissionDataParam.getSyncSchemeParamDTO().getSchemeKey());
            syncSchemeParamDTO.setSchemeName(syncSchemeDTO.getTitle());
        }
        SyncFileParam syncFileParam = new SyncFileParam();
        syncFileParam.setSyncSchemeParamDTO(syncSchemeParamDTO);
        syncFileParam.setStartTimes(transmissionDataParam.getStartTime());
        SubServer currentService = this.getCurrectServeNode();
        syncFileParam.setSyncServiceName(currentService.getName());
        UserInfoParam userInfoParam = new UserInfoParam();
        userInfoParam.setSyncUserId(NpContextHolder.getContext().getUserId());
        userInfoParam.setSyncUserName(NpContextHolder.getContext().getUserName());
        syncFileParam.setUserInfo(userInfoParam);
        form.add("syncFileParam", JacksonUtils.objectToJson((Object)syncFileParam));
        form.add("file", resource);
        HttpEntity httpEntity = new HttpEntity(form, (MultiValueMap)headers);
        TransmissionResult transmissionResult = new TransmissionResult();
        try {
            URI url = new URIBuilder(uri).setPath(api).build();
            transmissionResult = (TransmissionResult)this.restTemplate.postForObject(url, (Object)httpEntity, TransmissionResult.class);
            assert (transmissionResult != null);
            if (StringUtils.hasText(transmissionResult.getInstanceId())) {
                this.syncHistoryService.updateField(syncSchemeParamDTO.getKey(), "TH_INSTANCE_ID", transmissionResult.getInstanceId());
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            transmissionResult.setSuccess(false);
            transmissionResult.setMessage(e.getMessage());
            throw e;
        }
        return transmissionResult;
    }

    private SubServer getCurrectServeNode() {
        SubServer currectSubServer;
        try {
            currectSubServer = this.subServerManager.getCurrectSubServer();
        }
        catch (SubSystemException e) {
            throw new RuntimeException(e);
        }
        return currectSubServer;
    }
}

