/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.util.JsonUtils
 *  org.springframework.data.redis.core.StringRedisTemplate
 */
package com.jiuqi.common.datasync.discovery;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.datasync.dto.CommonDataSyncExecutorDTO;
import com.jiuqi.common.datasync.dto.CommonDataSyncMqTopicMessageDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

@Component
public class CommonDataSyncDiscovery {
    @Autowired
    private StringRedisTemplate redisTemplate;
    private static final String BASE_KEY = "gcreport:commondatasyncdiscovery";

    public Map<String, List<CommonDataSyncExecutorDTO>> getDiscoveryDataSyncExecutorDTOsMap() {
        Map serviceDataSyncExecutorDTOsMap = this.redisTemplate.opsForHash().entries((Object)BASE_KEY);
        if (CollectionUtils.isEmpty(serviceDataSyncExecutorDTOsMap)) {
            return Collections.emptyMap();
        }
        HashMap<String, List<CommonDataSyncExecutorDTO>> discoveryDataSyncExecutorDTOsMap = new HashMap<String, List<CommonDataSyncExecutorDTO>>();
        serviceDataSyncExecutorDTOsMap.forEach((serviceNameObj, serviceDataSyncExecutorDTOsJsonObj) -> {
            if (ObjectUtils.isEmpty(serviceNameObj) || ObjectUtils.isEmpty(serviceDataSyncExecutorDTOsJsonObj)) {
                return;
            }
            String serviceName = (String)serviceNameObj;
            String serviceDataSyncExecutorDTOsJson = (String)serviceDataSyncExecutorDTOsJsonObj;
            List serviceDataSyncExecutorDTOs = (List)JsonUtils.readValue((String)serviceDataSyncExecutorDTOsJson, (TypeReference)new TypeReference<ArrayList<CommonDataSyncExecutorDTO>>(){});
            discoveryDataSyncExecutorDTOsMap.put(serviceName, serviceDataSyncExecutorDTOs);
        });
        return discoveryDataSyncExecutorDTOsMap;
    }

    public void discovery(CommonDataSyncMqTopicMessageDTO topicMessageDTO) {
        String serviceName = topicMessageDTO.getServiceName();
        if (ObjectUtils.isEmpty(serviceName)) {
            return;
        }
        List<CommonDataSyncExecutorDTO> serviceDataSyncExecutorDTOs = topicMessageDTO.getDataSyncExecutorDTOs();
        if (CollectionUtils.isEmpty(serviceDataSyncExecutorDTOs)) {
            return;
        }
        String serviceDataSyncExecutorDTOsJson = JsonUtils.writeValueAsString(serviceDataSyncExecutorDTOs);
        this.redisTemplate.opsForHash().put((Object)BASE_KEY, (Object)serviceName, (Object)serviceDataSyncExecutorDTOsJson);
        this.redisTemplate.expire((Object)BASE_KEY, 3650L, TimeUnit.DAYS);
    }
}

