/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.DataModelExchangeTask
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.datamodel.service.impl.help;

import com.jiuqi.va.domain.common.DataModelExchangeTask;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class VaDataModelExchangeService {
    private Map<String, DataModelExchangeTask> exchangeTasks;

    public void exchange(DataModelDO dataModelDO) {
        if (this.exchangeTasks == null) {
            this.exchangeTasks = ApplicationContextRegister.getBeansOfType(DataModelExchangeTask.class);
        }
        for (DataModelExchangeTask dataModelExchangeTask : this.exchangeTasks.values()) {
            dataModelExchangeTask.publish(dataModelDO);
        }
    }
}

