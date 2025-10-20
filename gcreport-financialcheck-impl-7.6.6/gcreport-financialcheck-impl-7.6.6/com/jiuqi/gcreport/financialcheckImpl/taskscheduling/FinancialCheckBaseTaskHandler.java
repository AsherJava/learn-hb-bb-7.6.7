/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.certify.BdeRequestCertifyConfig
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult
 *  com.jiuqi.dc.taskscheduling.core.intf.ITaskHandler
 *  javax.annotation.PostConstruct
 */
package com.jiuqi.gcreport.financialcheckImpl.taskscheduling;

import com.jiuqi.bde.common.certify.BdeRequestCertifyConfig;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult;
import com.jiuqi.dc.taskscheduling.core.intf.ITaskHandler;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public abstract class FinancialCheckBaseTaskHandler
implements ITaskHandler {
    protected String moduleName;
    @Value(value="${jiuqi.gc.financial-check.module.name:}")
    private String moduleNameValue;
    @Autowired
    private BdeRequestCertifyConfig certifyConfig;

    @PostConstruct
    public void init() {
        this.moduleName = this.moduleNameValue;
    }

    public String getSpecialQueueFlag() {
        return null;
    }

    @Deprecated
    public TaskHandleResult handleTask(String param) {
        return null;
    }

    public String getModule() {
        if (!StringUtils.isEmpty((String)this.moduleName)) {
            return this.moduleName;
        }
        FinancialCheckBaseTaskHandler financialCheckBaseTaskHandler = this;
        return financialCheckBaseTaskHandler.certifyConfig.getModuleName();
    }

    public boolean enable(String preTaskName, String preParam) {
        return true;
    }
}

