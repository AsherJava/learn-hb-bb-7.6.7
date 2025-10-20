/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.gcreport.common.action.env.GcActionItemEnv
 *  com.jiuqi.gcreport.common.action.impl.AbstractGcActionItem
 *  com.jiuqi.gcreport.nr.vo.GcFormulaCheckDesGatherParam
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 */
package com.jiuqi.gcreport.nr.impl.action;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.common.action.env.GcActionItemEnv;
import com.jiuqi.gcreport.common.action.impl.AbstractGcActionItem;
import com.jiuqi.gcreport.nr.impl.asynctask.GcFormulaCheckDesGatherTaskExcutor;
import com.jiuqi.gcreport.nr.vo.GcFormulaCheckDesGatherParam;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import org.springframework.stereotype.Component;

@Component
public class GcFormulaCheckDesGatherAction
extends AbstractGcActionItem {
    private AsyncTaskManager asyncTaskManager;

    protected GcFormulaCheckDesGatherAction(AsyncTaskManager asyncTaskManager) {
        super("gcFormulaCheckDesGather", "\u51fa\u9519\u8bf4\u660e\u6c47\u603b", "", "#icon-_GJZzidingyihuizong");
        this.asyncTaskManager = asyncTaskManager;
    }

    public Object execute(GcActionItemEnv actionItemEnv) {
        GcFormulaCheckDesGatherParam formulaCheckDesGatherParam = (GcFormulaCheckDesGatherParam)JsonUtils.readValue((String)actionItemEnv.getActionParam(), GcFormulaCheckDesGatherParam.class);
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)formulaCheckDesGatherParam));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new GcFormulaCheckDesGatherTaskExcutor());
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        String asynTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }
}

