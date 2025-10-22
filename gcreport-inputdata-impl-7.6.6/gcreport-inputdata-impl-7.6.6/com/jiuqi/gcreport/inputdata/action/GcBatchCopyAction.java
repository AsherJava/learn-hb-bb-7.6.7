/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.gcreport.common.action.env.GcActionItemEnv
 *  com.jiuqi.gcreport.common.action.impl.AbstractGcActionItem
 *  com.jiuqi.gcreport.inputdata.dto.GcBatchCopyActionParamDTO
 *  com.jiuqi.gcreport.nr.impl.constant.GcAsyncTaskPoolType
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 */
package com.jiuqi.gcreport.inputdata.action;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.common.action.env.GcActionItemEnv;
import com.jiuqi.gcreport.common.action.impl.AbstractGcActionItem;
import com.jiuqi.gcreport.inputdata.dto.GcBatchCopyActionParamDTO;
import com.jiuqi.gcreport.nr.impl.constant.GcAsyncTaskPoolType;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcBatchCopyAction
extends AbstractGcActionItem {
    @Autowired
    private AsyncTaskManager asyncTaskManager;

    protected GcBatchCopyAction() {
        super("gcBatchCopyAction", "\u6279\u91cf\u590d\u5236\uff08\u5408\u5e76\u62a5\u8868\uff09", "\u6309\u65f6\u671f\u6279\u91cf\u590d\u5236", "#icon-16_DH_A_GC_wbzs");
    }

    public Object execute(GcActionItemEnv actionItemEnv) {
        String paramJson = String.valueOf(actionItemEnv.getActionParam());
        GcBatchCopyActionParamDTO batchCopyActionParam = (GcBatchCopyActionParamDTO)JsonUtils.readValue((String)paramJson, GcBatchCopyActionParamDTO.class);
        String asynTaskID = this.asyncTaskManager.publishTask((Object)batchCopyActionParam, GcAsyncTaskPoolType.ASYNCTASK_GC_BATCH_COPY.getName());
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }
}

