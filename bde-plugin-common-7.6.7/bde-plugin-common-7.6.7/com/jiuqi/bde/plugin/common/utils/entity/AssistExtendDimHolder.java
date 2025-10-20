/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.vo.AssistExtendDimVO
 */
package com.jiuqi.bde.plugin.common.utils.entity;

import com.jiuqi.bde.bizmodel.client.vo.AssistExtendDimVO;
import java.util.Map;

public class AssistExtendDimHolder {
    private Map<String, AssistExtendDimVO> assistExtendDimCodeToAssistExtendDimMap;

    public AssistExtendDimHolder(Map<String, AssistExtendDimVO> assistExtendDimCodeToAssistExtendDimMap) {
        this.assistExtendDimCodeToAssistExtendDimMap = assistExtendDimCodeToAssistExtendDimMap;
    }

    public Map<String, AssistExtendDimVO> getAssistExtendDimCodeToAssistExtendDimMap() {
        return this.assistExtendDimCodeToAssistExtendDimMap;
    }

    public void setAssistExtendDimCodeToAssistExtendDimMap(Map<String, AssistExtendDimVO> assistExtendDimCodeToAssistExtendDimMap) {
        this.assistExtendDimCodeToAssistExtendDimMap = assistExtendDimCodeToAssistExtendDimMap;
    }
}

