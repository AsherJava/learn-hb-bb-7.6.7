/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.nr.vo.GcFormulaCheckDesCopyInfoParam
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpAsyncTaskExecutor
 */
package com.jiuqi.gcreport.nr.impl.asynctask;

import com.jiuqi.gcreport.nr.impl.constant.GcAsyncTaskPoolType;
import com.jiuqi.gcreport.nr.impl.dto.GcFormulaCheckDesCopyInfoDTO;
import com.jiuqi.gcreport.nr.impl.service.GcFormulaCheckDesCopyInfoService;
import com.jiuqi.gcreport.nr.vo.GcFormulaCheckDesCopyInfoParam;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpAsyncTaskExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class GcBatchFormulaCheckDesTaskExcutor
implements NpAsyncTaskExecutor {
    @Lazy
    @Autowired
    private GcFormulaCheckDesCopyInfoService gcFormulaCheckDesCopyInfoService;

    public void execute(Object args, AsyncTaskMonitor monitor) {
        if (!(args instanceof GcFormulaCheckDesCopyInfoParam)) {
            return;
        }
        GcFormulaCheckDesCopyInfoParam gcFormulaCheckDesCopyInfo = (GcFormulaCheckDesCopyInfoParam)args;
        GcFormulaCheckDesCopyInfoDTO formulaCheckDesCopyInfoDTO = new GcFormulaCheckDesCopyInfoDTO();
        formulaCheckDesCopyInfoDTO.setAsyncTaskMonitor(monitor);
        formulaCheckDesCopyInfoDTO.setFormulaCheckDesCopyInfoParam(gcFormulaCheckDesCopyInfo);
        this.gcFormulaCheckDesCopyInfoService.getFormulaCheckDesCopyInfoMassage(formulaCheckDesCopyInfoDTO);
        if (monitor.isCancel()) {
            String retStr = "\u4efb\u52a1\u53d6\u6d88";
            monitor.canceled(retStr, (Object)retStr);
        }
    }

    public String getTaskPoolType() {
        return GcAsyncTaskPoolType.ASYNCTASK_BATCHCHECKDESCOPYINFO.getName();
    }
}

