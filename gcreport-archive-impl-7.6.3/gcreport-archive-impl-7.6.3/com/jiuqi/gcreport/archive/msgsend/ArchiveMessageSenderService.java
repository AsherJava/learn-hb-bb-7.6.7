/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.message.domain.UnreadType
 *  com.jiuqi.va.message.domain.VaMessageOption$MsgChannel
 *  com.jiuqi.va.message.domain.VaMessageSendDTO
 *  com.jiuqi.va.message.feign.client.VaMessageClient
 */
package com.jiuqi.gcreport.archive.msgsend;

import com.jiuqi.va.message.domain.UnreadType;
import com.jiuqi.va.message.domain.VaMessageOption;
import com.jiuqi.va.message.domain.VaMessageSendDTO;
import com.jiuqi.va.message.feign.client.VaMessageClient;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArchiveMessageSenderService {
    @Autowired
    private VaMessageClient vaMessageClient;

    public void sendSimpleMessage(List<String> userIds, String periodShowTitle, String taskTitle) {
        VaMessageSendDTO infoDO = new VaMessageSendDTO();
        infoDO.setReceiveUserIds(userIds);
        infoDO.setNoticeflag(false);
        infoDO.setMsgChannel(VaMessageOption.MsgChannel.AUTO);
        infoDO.setTitle("\u8ba1\u5212\u4efb\u52a1\u7535\u5b50\u6863\u6848\u5f52\u6863\u5b8c\u6210\u901a\u77e5");
        String content = String.format("\u4efb\u52a1\uff1a%s\uff0c%s\u5df2\u8fdb\u884c\u5f52\u6863\u3002", taskTitle, periodShowTitle);
        infoDO.setContent(content);
        infoDO.setGrouptype("\u7535\u5b50\u6863\u6848\u5f52\u6863");
        infoDO.setMsgtype("\u7535\u5b50\u6863\u6848\u901a\u77e5");
        infoDO.setDatasource("archive");
        infoDO.setUnreadType(UnreadType.NORMAL.getType());
        this.vaMessageClient.addMsg(infoDO);
    }
}

