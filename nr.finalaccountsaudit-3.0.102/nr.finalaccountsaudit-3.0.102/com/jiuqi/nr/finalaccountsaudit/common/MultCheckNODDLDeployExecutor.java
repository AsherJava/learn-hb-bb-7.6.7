/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.paramcheck.NODDLDeployExecutor
 *  com.jiuqi.nvwa.definition.service.DataModelRegisterService
 */
package com.jiuqi.nr.finalaccountsaudit.common;

import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.paramcheck.NODDLDeployExecutor;
import com.jiuqi.nr.finalaccountsaudit.common.DataTableHelper;
import com.jiuqi.nr.finalaccountsaudit.multcheck.internal.controller.MultCheckObserver;
import com.jiuqi.nvwa.definition.service.DataModelRegisterService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MultCheckNODDLDeployExecutor
implements NODDLDeployExecutor {
    @Autowired
    IDesignTimeViewController nrDesignController;
    @Autowired
    MultCheckObserver multCheckObserver;
    @Autowired
    DataModelRegisterService dataModelRegisterService;
    @Autowired
    DataTableHelper dataTableHelper;

    public List<String> preDeploy(String taskKey) {
        try {
            return this.multCheckObserver.getDeployTable(taskKey);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void doDeploy(String taskKey) {
        this.dataTableHelper.registerTable(taskKey, "SYS_MULTCHECK_");
    }

    public double getOrder() {
        return 0.0;
    }
}

