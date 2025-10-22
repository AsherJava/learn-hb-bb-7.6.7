/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.datastatus.facade.obj.RefreshStatusPar
 *  com.jiuqi.nr.datastatus.facade.service.IDataStatusService
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.dataentry.bean.RefreshStatusObj;
import com.jiuqi.nr.dataentry.exception.RefreshStatusErrorEnum;
import com.jiuqi.nr.dataentry.service.SCBatchRefreshStatusService;
import com.jiuqi.nr.datastatus.facade.obj.RefreshStatusPar;
import com.jiuqi.nr.datastatus.facade.service.IDataStatusService;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SCBatchRefreshStatusServiceImpl
implements SCBatchRefreshStatusService {
    @Autowired
    private ITaskOptionController taskOptionController;
    @Autowired
    private IDataStatusService dataStatusService;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void refreshAllStatus(RefreshStatusObj refreshStatusObj, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        String updateState = "update_status_info";
        asyncTaskMonitor.progressAndMessage(0.05, updateState);
        if (refreshStatusObj == null || !refreshStatusObj.isNotEmpty()) throw new JQException((ErrorEnum)RefreshStatusErrorEnum.SYSTEM_CHECK_EXCEPTION_002);
        try {
            String checkStatusMessage = this.taskOptionController.getValue(refreshStatusObj.getTask(), "DATAENTRY_STATUS");
            if (checkStatusMessage != null && (checkStatusMessage.contains("0") || checkStatusMessage.contains("1") || checkStatusMessage.contains("2"))) {
                RefreshStatusPar refreshStatusPar = new RefreshStatusPar();
                refreshStatusPar.setTaskKey(refreshStatusObj.getTask());
                refreshStatusPar.setPeriods(refreshStatusObj.getPeriods());
                this.dataStatusService.refreshDataStatus(refreshStatusPar);
                if (asyncTaskMonitor.isCancel()) {
                    asyncTaskMonitor.canceled("stop_execute", (Object)"stop_execute");
                }
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)RefreshStatusErrorEnum.SYSTEM_CHECK_EXCEPTION_001, (Throwable)e);
        }
        if (asyncTaskMonitor.isFinish()) return;
        asyncTaskMonitor.finish("success", null);
    }
}

