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
import com.jiuqi.nr.finalaccountsaudit.enumcheck.internal.controller.EnumCheckObserver;
import com.jiuqi.nvwa.definition.service.DataModelRegisterService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnumCheckNODDLDeployExecutor
implements NODDLDeployExecutor {
    @Autowired
    IDesignTimeViewController nrDesignController;
    @Autowired
    EnumCheckObserver enumCheckObserver;
    @Autowired
    DataModelRegisterService dataModelRegisterService;
    @Autowired
    DataTableHelper dataTableHelper;
    private List<String> AllSql = new ArrayList<String>();

    public List<String> preDeploy(String taskKey) {
        try {
            return this.enumCheckObserver.getDeployTable(taskKey);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void doDeploy(String taskKey) {
        this.dataTableHelper.registerTable(taskKey, "SYS_MJZDJCJG_");
    }

    public double getOrder() {
        return 0.0;
    }
}

