/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.gcreport.common.action.env.GcActionItemEnv
 *  com.jiuqi.gcreport.common.action.impl.AbstractGcActionItem
 *  com.jiuqi.gcreport.nr.vo.GcFormulaCheckDesCopyInfoParam
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.gcreport.nr.impl.action;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.common.action.env.GcActionItemEnv;
import com.jiuqi.gcreport.common.action.impl.AbstractGcActionItem;
import com.jiuqi.gcreport.nr.impl.constant.GcAsyncTaskPoolType;
import com.jiuqi.gcreport.nr.vo.GcFormulaCheckDesCopyInfoParam;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class GcBatchFormulaCheckDesCopyAction
extends AbstractGcActionItem {
    private AsyncTaskManager asyncTaskManager;

    protected GcBatchFormulaCheckDesCopyAction(AsyncTaskManager asyncTaskManager) {
        super("gcBatchFormulaCheckDesCopyInfo", "\u6279\u91cf\u540c\u6b65\u51fa\u9519\u8bf4\u660e", "", "#icon-_GJZshenhe");
        this.asyncTaskManager = asyncTaskManager;
    }

    public Object execute(GcActionItemEnv actionItemEnv) {
        Map actionParam = (Map)JsonUtils.readValue((String)actionItemEnv.getActionParam(), (TypeReference)new TypeReference<Map<String, Object>>(){});
        JtableContext jtableContext = (JtableContext)JsonUtils.readValue((String)JsonUtils.writeValueAsString(actionParam.get("context")), JtableContext.class);
        List orgIds = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString(actionParam.get("orgIds")), (TypeReference)new TypeReference<List<String>>(){});
        List currncyCodes = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString(actionParam.get("currncyCodes")), (TypeReference)new TypeReference<List<String>>(){});
        GcFormulaCheckDesCopyInfoParam gcFormulaCheckDesCopyInfoParam = new GcFormulaCheckDesCopyInfoParam();
        gcFormulaCheckDesCopyInfoParam.setOrgIds(orgIds);
        gcFormulaCheckDesCopyInfoParam.setCurrncyCodes(currncyCodes);
        gcFormulaCheckDesCopyInfoParam.setJtableContext(jtableContext);
        String asynTaskId = this.asyncTaskManager.publishTask((Object)gcFormulaCheckDesCopyInfoParam, GcAsyncTaskPoolType.ASYNCTASK_BATCHCHECKDESCOPYINFO.getName());
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asynTaskId);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }
}

