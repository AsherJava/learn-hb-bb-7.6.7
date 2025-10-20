/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 */
package com.jiuqi.va.join.api.extend;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.join.api.domain.JoinListener;
import com.jiuqi.va.join.api.domain.ListenerChangeStatus;
import com.jiuqi.va.join.api.domain.ListenerStatus;
import com.jiuqi.va.join.api.domain.ReplyStatus;
import com.jiuqi.va.join.api.domain.ReplyTo;
import com.jiuqi.va.join.api.extend.JoinPrimaryTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChangeStatusJoinListener
implements JoinListener {
    @Autowired
    private JoinPrimaryTemplate template;
    private ReplyTo noReply = new ReplyTo(ReplyStatus.SUCESS);

    @Override
    public String getJoinName() {
        return "CHANGE_JOIN_LISTENER_STATUS";
    }

    @Override
    public ReplyTo onMessage(String msg) {
        if (msg == null) {
            return null;
        }
        ListenerChangeStatus changeStatus = (ListenerChangeStatus)JSONUtil.parseObject((String)msg, ListenerChangeStatus.class);
        if (changeStatus == null) {
            return null;
        }
        if (changeStatus.getListenerStatus() == ListenerStatus.START) {
            this.template.startListener(changeStatus.getJoinName());
        } else {
            this.template.stopListener(changeStatus.getJoinName());
        }
        return this.noReply;
    }
}

