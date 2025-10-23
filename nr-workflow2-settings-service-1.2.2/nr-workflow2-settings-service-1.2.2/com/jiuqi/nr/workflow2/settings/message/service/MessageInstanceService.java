/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.settings.message.service;

import com.jiuqi.nr.workflow2.settings.message.dto.MessageInstanceBaseContext;
import com.jiuqi.nr.workflow2.settings.message.dto.MessageInstanceQueryContext;
import com.jiuqi.nr.workflow2.settings.message.dto.MessageInstanceStrategyVerifyContext;
import com.jiuqi.nr.workflow2.settings.message.vo.MessageInstanceVO;
import java.util.List;
import java.util.Map;

public interface MessageInstanceService {
    public MessageInstanceVO messageInstanceInit(MessageInstanceQueryContext var1);

    public List<Map<String, Object>> getReceiverSource(MessageInstanceBaseContext var1);

    public List<Map<String, Object>> receiverDowngrade(MessageInstanceStrategyVerifyContext var1);

    public List<Map<String, Object>> getVariableSource(MessageInstanceBaseContext var1);
}

