/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.gcreport.common.action.env.GcActionItemEnv
 *  com.jiuqi.gcreport.common.action.impl.AbstractGcActionItem
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 */
package com.jiuqi.gcreport.nr.impl.action;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.common.action.env.GcActionItemEnv;
import com.jiuqi.gcreport.common.action.impl.AbstractGcActionItem;
import com.jiuqi.gcreport.nr.impl.service.GcTaskDataCopyService;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcTaskDataCopyAction
extends AbstractGcActionItem {
    @Autowired
    private GcTaskDataCopyService gcTaskDataCopyService;

    protected GcTaskDataCopyAction(AsyncTaskManager asyncTaskManager) {
        super("gcTaskDataCopyAction", "\u51b3\u7b97\u835f\u5206\u6790", "", "#icon-_GJZshenhe");
    }

    public Object execute(GcActionItemEnv actionItemEnv) {
        Map actionParam = (Map)JsonUtils.readValue((String)actionItemEnv.getActionParam(), (TypeReference)new TypeReference<Map<String, Object>>(){});
        String taskCode = actionParam.get("taskCode").toString();
        String datatime = actionParam.get("datatime").toString();
        List orgIds = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString(actionParam.get("orgIds")), (TypeReference)new TypeReference<List<String>>(){});
        for (String orgId : orgIds) {
            this.gcTaskDataCopyService.copyData(taskCode, orgId, datatime);
        }
        return null;
    }
}

