/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.va.message.domain.VaMessageChannelEntity
 *  com.jiuqi.va.message.domain.VaMessageChannelSetDTO
 *  com.jiuqi.va.message.domain.VaMessageOption$MsgChannel
 *  com.jiuqi.va.message.feign.client.VaMessageSettingClient
 */
package com.jiuqi.nr.workflow2.settings.event.source;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.workflow2.settings.event.source.WorkflowEventSource;
import com.jiuqi.va.message.domain.VaMessageChannelEntity;
import com.jiuqi.va.message.domain.VaMessageChannelSetDTO;
import com.jiuqi.va.message.domain.VaMessageOption;
import com.jiuqi.va.message.feign.client.VaMessageSettingClient;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SendMessageNoticeEventSource
implements WorkflowEventSource {
    public static final String KEY_OF_MESSAGE_CHANNEL = "messageChannel";
    public static final String KEY_OF_MESSAGE_ENABLE = "messageEnable";
    public static final String KEY_OF_EMAIL_ENABLE = "emailEnable";
    public static final String KEY_OF_SHORT_MESSAGE_ENABLE = "shortMessageEnable";
    @Autowired
    private VaMessageSettingClient vaMessageSettingClient;

    @Override
    public String getEventId() {
        return "send-message-notice-event";
    }

    @Override
    public Map<String, Object> getSource(String taskKey) {
        VaMessageChannelSetDTO dto = new VaMessageChannelSetDTO();
        dto.setUserId(NpContextHolder.getContext().getUserId());
        dto.setStatus(Integer.valueOf(1));
        Map channelEntityMap = this.vaMessageSettingClient.findChannel(dto).stream().collect(Collectors.toMap(VaMessageChannelEntity::getMsgChannel, Function.identity(), (v1, v2) -> v1));
        HashMap<String, Object> eventSource = new HashMap<String, Object>();
        HashMap<String, Boolean> messageChannel = new HashMap<String, Boolean>();
        messageChannel.put(KEY_OF_MESSAGE_ENABLE, true);
        messageChannel.put(KEY_OF_EMAIL_ENABLE, channelEntityMap.containsKey(VaMessageOption.MsgChannel.EMAIL));
        messageChannel.put(KEY_OF_SHORT_MESSAGE_ENABLE, channelEntityMap.containsKey(VaMessageOption.MsgChannel.SMS));
        eventSource.put(KEY_OF_MESSAGE_CHANNEL, messageChannel);
        return eventSource;
    }
}

