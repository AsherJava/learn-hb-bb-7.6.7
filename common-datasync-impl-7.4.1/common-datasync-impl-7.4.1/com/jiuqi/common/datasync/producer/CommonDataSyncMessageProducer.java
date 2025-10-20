/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.va.join.api.common.JoinTemplate
 */
package com.jiuqi.common.datasync.producer;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.datasync.autoconfigure.CommonDataSyncAutoConfiguration;
import com.jiuqi.common.datasync.declare.CommonDataSyncQueueJoinDeclare;
import com.jiuqi.common.datasync.declare.CommonDataSyncTopicJoinDeclare;
import com.jiuqi.common.datasync.dto.CommonDataSyncExecutorDTO;
import com.jiuqi.common.datasync.dto.CommonDataSyncMqTopicMessageDTO;
import com.jiuqi.common.datasync.enums.CommonDataSyncMqTopicMessageType;
import com.jiuqi.common.datasync.message.CommonDataSyncMessage;
import com.jiuqi.common.datasync.service.CommonDataSyncService;
import com.jiuqi.va.join.api.common.JoinTemplate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class CommonDataSyncMessageProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonDataSyncMessageProducer.class);
    @Autowired
    private CommonDataSyncAutoConfiguration dataSyncAutoConfiguration;
    @Autowired
    private JoinTemplate joinTemplate;
    @Autowired
    private CommonDataSyncService commonDataSyncService;
    @Autowired
    private CommonDataSyncQueueJoinDeclare commonDataSyncQueueJoinDeclare;
    @Autowired
    private CommonDataSyncTopicJoinDeclare commonDataSyncTopicJoinDeclare;

    public void publishDataSyncQueueMessage(CommonDataSyncMessage dataSyncMessage) {
        if (dataSyncMessage == null || dataSyncMessage.getItemDTO() == null || ObjectUtils.isEmpty(dataSyncMessage.getItemDTO().getServiceName()) || ObjectUtils.isEmpty(dataSyncMessage.getItemDTO().getType())) {
            LOGGER.error("\u6570\u636e\u540c\u6b65\u53c2\u6570\u4e0d\u5408\u6cd5\uff0c\u8be6\u60c5\uff1a{}", (Object)JsonUtils.writeValueAsString((Object)dataSyncMessage));
            return;
        }
        this.joinTemplate.send(this.commonDataSyncQueueJoinDeclare.getName(dataSyncMessage.getItemDTO().getServiceName()), JsonUtils.writeValueAsString((Object)dataSyncMessage));
    }

    public void publishCommonDataSyncMqTopicNoticeMessageDTO() {
        CommonDataSyncMqTopicMessageDTO topicNoticeMessageDTO = new CommonDataSyncMqTopicMessageDTO();
        topicNoticeMessageDTO.setMsgType(CommonDataSyncMqTopicMessageType.NOTICE);
        topicNoticeMessageDTO.setServiceName(this.dataSyncAutoConfiguration.getDataSyncProperties().getServiceName());
        this.joinTemplate.send(this.commonDataSyncTopicJoinDeclare.getName(), JsonUtils.writeValueAsString((Object)topicNoticeMessageDTO));
    }

    public void publishCommonDataSyncMqTopicReportMessageDTO() {
        List<CommonDataSyncExecutorDTO> currentServerDataSyncExecutorDTOs = this.commonDataSyncService.getDataSyncExecutorDTOs();
        if (CollectionUtils.isEmpty(currentServerDataSyncExecutorDTOs)) {
            return;
        }
        String serviceName = this.dataSyncAutoConfiguration.getDataSyncProperties().getServiceName();
        if (ObjectUtils.isEmpty(serviceName)) {
            return;
        }
        CommonDataSyncMqTopicMessageDTO topicReportMessageDTO = new CommonDataSyncMqTopicMessageDTO();
        topicReportMessageDTO.setMsgType(CommonDataSyncMqTopicMessageType.REPORT);
        topicReportMessageDTO.setServiceName(this.dataSyncAutoConfiguration.getDataSyncProperties().getServiceName());
        topicReportMessageDTO.setDataSyncExecutorDTOs(currentServerDataSyncExecutorDTOs);
        this.joinTemplate.send(this.commonDataSyncTopicJoinDeclare.getName(), JsonUtils.writeValueAsString((Object)topicReportMessageDTO));
    }
}

