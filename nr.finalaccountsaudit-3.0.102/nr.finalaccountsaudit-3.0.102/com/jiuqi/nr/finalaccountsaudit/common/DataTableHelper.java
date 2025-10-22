/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelRegisterService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.finalaccountsaudit.common;

import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelRegisterService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataTableHelper {
    @Autowired
    IDesignTimeViewController nrDesignController;
    @Autowired
    private DesignDataModelService designDataModelService;
    @Autowired
    DataModelRegisterService dataModelRegisterService;

    public void registerTable(String taskKey, String tableName) {
        if (taskKey.length() == 0) {
            return;
        }
        try {
            List schemes = this.nrDesignController.queryFormSchemeByTask(taskKey);
            if (schemes == null) {
                return;
            }
            for (DesignFormSchemeDefine scheme : schemes) {
                String tableCode = tableName + scheme.getFormSchemeCode();
                DesignTableModelDefine tableDefine = this.designDataModelService.getTableModelDefineByCode(tableCode);
                if (tableDefine == null) {
                    tableDefine = this.designDataModelService.createTableModelDefine();
                }
                this.dataModelRegisterService.registerTable(tableDefine.getID());
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

