/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.datasync.dto;

import com.jiuqi.common.datasync.dto.CommonDataSyncExecutorDTO;
import com.jiuqi.common.datasync.enums.CommonDataSyncMqTopicMessageType;
import java.util.List;

public class CommonDataSyncMqTopicMessageDTO {
    private CommonDataSyncMqTopicMessageType msgType;
    private String serviceName;
    private List<CommonDataSyncExecutorDTO> dataSyncExecutorDTOs;

    public CommonDataSyncMqTopicMessageType getMsgType() {
        return this.msgType;
    }

    public void setMsgType(CommonDataSyncMqTopicMessageType msgType) {
        this.msgType = msgType;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public List<CommonDataSyncExecutorDTO> getDataSyncExecutorDTOs() {
        return this.dataSyncExecutorDTOs;
    }

    public void setDataSyncExecutorDTOs(List<CommonDataSyncExecutorDTO> dataSyncExecutorDTOs) {
        this.dataSyncExecutorDTOs = dataSyncExecutorDTOs;
    }
}

