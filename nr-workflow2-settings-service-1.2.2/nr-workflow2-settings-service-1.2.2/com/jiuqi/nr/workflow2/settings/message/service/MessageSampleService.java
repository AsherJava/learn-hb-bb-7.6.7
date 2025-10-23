/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.settings.message.service;

import com.jiuqi.nr.workflow2.settings.message.domain.MessageSampleDO;
import com.jiuqi.nr.workflow2.settings.message.dto.MessageSampleSaveAsContext;
import com.jiuqi.nr.workflow2.settings.message.vo.MessageSampleVO;
import java.util.List;
import java.util.Map;

public interface MessageSampleService {
    public List<MessageSampleVO> queryMessageSample(String var1, String var2);

    public MessageSampleDO queryMessageSampleByTitle(String var1);

    public Map<String, Object> getFilterConditionSource(String var1, String var2);

    public boolean saveMessageSampleAs(MessageSampleSaveAsContext var1);

    public boolean deleteMessageSample(String var1);
}

