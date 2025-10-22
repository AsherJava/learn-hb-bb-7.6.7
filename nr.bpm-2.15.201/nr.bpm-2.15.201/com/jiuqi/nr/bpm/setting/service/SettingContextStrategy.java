/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 */
package com.jiuqi.nr.bpm.setting.service;

import com.jiuqi.nr.bpm.setting.service.IBulidParam;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SettingContextStrategy {
    private Map<Integer, IBulidParam> sourceMap;

    @Autowired(required=true)
    public SettingContextStrategy(List<IBulidParam> list) {
        if (null != list) {
            this.sourceMap = new HashMap<Integer, IBulidParam>();
            for (IBulidParam e : list) {
                this.sourceMap.put(e.getType(), e);
            }
        }
    }

    public IBulidParam bulidParam(WorkFlowType startType) {
        if (WorkFlowType.ENTITY.equals((Object)startType)) {
            return this.sourceMap.get(startType.getValue());
        }
        if (WorkFlowType.FORM.equals((Object)startType) || WorkFlowType.GROUP.equals((Object)startType)) {
            return this.sourceMap.get(WorkFlowType.FORM.getValue());
        }
        return null;
    }
}

