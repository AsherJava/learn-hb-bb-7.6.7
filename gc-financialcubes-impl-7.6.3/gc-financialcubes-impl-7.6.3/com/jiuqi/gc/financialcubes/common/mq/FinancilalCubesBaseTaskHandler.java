/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.certify.BdeRequestCertifyConfig
 *  com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult
 *  com.jiuqi.dc.taskscheduling.core.intf.ITaskHandler
 *  com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor
 *  com.jiuqi.gcreport.common.systemoption.util.GcSystermOptionTool
 */
package com.jiuqi.gc.financialcubes.common.mq;

import com.jiuqi.bde.common.certify.BdeRequestCertifyConfig;
import com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult;
import com.jiuqi.dc.taskscheduling.core.intf.ITaskHandler;
import com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor;
import com.jiuqi.gcreport.common.systemoption.util.GcSystermOptionTool;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class FinancilalCubesBaseTaskHandler
implements ITaskHandler {
    @Autowired
    private BdeRequestCertifyConfig certifyConfig;

    public String getSpecialQueueFlag() {
        return null;
    }

    @Deprecated
    public TaskHandleResult handleTask(String param) {
        return null;
    }

    public TaskHandleResult handleTask(String param, ITaskProgressMonitor monitor) {
        return this.handleTask(param);
    }

    public String getModule() {
        FinancilalCubesBaseTaskHandler financilalCubesBaseTaskHandler = this;
        return financilalCubesBaseTaskHandler.certifyConfig.getModuleName();
    }

    public boolean enable(String preTaskName, String preParam) {
        String optionValue = GcSystermOptionTool.getOptionValue((String)"FINANCIAL_CUBES_ENABLE");
        return "1".equals(optionValue);
    }
}

