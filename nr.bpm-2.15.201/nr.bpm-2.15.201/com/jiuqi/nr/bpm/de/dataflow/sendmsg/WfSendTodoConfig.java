/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.ContextExtension
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.nr.bpm.de.dataflow.sendmsg;

import com.jiuqi.np.core.context.ContextExtension;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.bpm.de.dataflow.util.DeEntityHelper;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WfSendTodoConfig {
    @Autowired
    private DeEntityHelper deEntityHelper;

    public Set<String> getEnableUnitCodes(String taskKey, String period) {
        String cacheKey = taskKey + ":" + period;
        ContextExtension extension = NpContextHolder.getContext().getExtension("NR_WF_TODO_CONFIG");
        Object object = extension.get(cacheKey);
        if (object == null) {
            List<String> dataList = this.deEntityHelper.queryBaseDataList(taskKey, period);
            object = dataList == null ? Collections.emptySet() : new HashSet<String>(dataList);
            extension.put(cacheKey, (Serializable)object);
        }
        return (Set)object;
    }

    public boolean get(String taskKey, String period, String unit) {
        Set<String> enableUnitCodesSet = this.getEnableUnitCodes(taskKey, period);
        return enableUnitCodesSet.isEmpty() || enableUnitCodesSet.contains(unit);
    }
}

