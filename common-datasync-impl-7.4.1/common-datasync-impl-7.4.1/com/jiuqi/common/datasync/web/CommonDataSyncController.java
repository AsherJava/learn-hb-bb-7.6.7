/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.common.datasync.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.datasync.dto.CommonDataSyncExecutorDTO;
import com.jiuqi.common.datasync.producer.CommonDataSyncMessageProducer;
import com.jiuqi.common.datasync.service.CommonDataSyncService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"api/v1/commondatasync"})
public class CommonDataSyncController {
    @Autowired
    private CommonDataSyncService dataSyncService;

    @GetMapping(value={"getDataSyncExecutors"})
    public BusinessResponseEntity<List<CommonDataSyncExecutorDTO>> getDataSyncExecutors() {
        List<CommonDataSyncExecutorDTO> dataSyncExecutorDTOs = this.dataSyncService.getDataSyncExecutorDTOs();
        return BusinessResponseEntity.ok(dataSyncExecutorDTOs);
    }

    @GetMapping(value={"getDiscoveryDataSyncExecutors"})
    public BusinessResponseEntity<Map<String, List<CommonDataSyncExecutorDTO>>> getDiscoveryDataSyncExecutors() {
        Map<String, List<CommonDataSyncExecutorDTO>> serviceDataSyncExecutorDTOsMap = this.dataSyncService.getDiscoveryDataSyncExecutors();
        if (serviceDataSyncExecutorDTOsMap == null || serviceDataSyncExecutorDTOsMap.size() == 0) {
            CommonDataSyncMessageProducer producer = (CommonDataSyncMessageProducer)SpringContextUtils.getBean(CommonDataSyncMessageProducer.class);
            producer.publishCommonDataSyncMqTopicNoticeMessageDTO();
        }
        return BusinessResponseEntity.ok(serviceDataSyncExecutorDTOsMap);
    }
}

