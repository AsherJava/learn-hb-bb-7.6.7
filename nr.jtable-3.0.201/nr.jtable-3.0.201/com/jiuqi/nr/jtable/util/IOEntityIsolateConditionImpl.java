/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.io.service.IOEntityIsolateCondition
 */
package com.jiuqi.nr.jtable.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.io.service.IOEntityIsolateCondition;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IEntityIsolateConditionProvider;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IOEntityIsolateConditionImpl
implements IOEntityIsolateCondition {
    @Autowired(required=false)
    private List<IEntityIsolateConditionProvider> entityIsolateConditionProviders;

    public String queryIsoCondition(String taskId, String dataTime, String entityId) {
        if (this.entityIsolateConditionProviders != null && this.entityIsolateConditionProviders.size() > 0) {
            JtableContext jtableContext = new JtableContext();
            jtableContext.setTaskKey(taskId);
            DimensionValue dimensionValue = new DimensionValue();
            dimensionValue.setName("DATATIME");
            dimensionValue.setValue(dataTime);
            HashMap<String, DimensionValue> dimensionValueMap = new HashMap<String, DimensionValue>();
            dimensionValueMap.put("DATATIME", dimensionValue);
            jtableContext.setDimensionSet(dimensionValueMap);
            for (IEntityIsolateConditionProvider entityIsolateConditionProvider : this.entityIsolateConditionProviders) {
                String entityIsolateCondition = entityIsolateConditionProvider.getEntityIsolateCondition(entityId, jtableContext);
                if (!StringUtils.isNotEmpty((String)entityIsolateCondition)) continue;
                return entityIsolateCondition;
            }
        }
        return "";
    }
}

