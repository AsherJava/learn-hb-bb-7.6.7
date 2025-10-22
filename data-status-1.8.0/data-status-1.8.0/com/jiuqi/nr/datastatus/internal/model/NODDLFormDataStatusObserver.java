/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.paramcheck.NODDLDeployExecutor
 *  com.jiuqi.nvwa.definition.service.DataModelRegisterService
 */
package com.jiuqi.nr.datastatus.internal.model;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.datastatus.internal.model.TableDeployService;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.paramcheck.NODDLDeployExecutor;
import com.jiuqi.nvwa.definition.service.DataModelRegisterService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NODDLFormDataStatusObserver
implements NODDLDeployExecutor {
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private TableDeployService tableDeployService;
    @Autowired
    private DataModelRegisterService dataModelRegisterService;
    private static final Logger logger = LoggerFactory.getLogger(NODDLFormDataStatusObserver.class);

    public List<String> preDeploy(String taskKey) {
        ArrayList<String> result = new ArrayList<String>();
        DesignTaskDefine taskDefine = this.nrDesignTimeController.queryTaskDefine(taskKey);
        List schemes = null;
        try {
            schemes = this.nrDesignTimeController.queryFormSchemeByTask(taskKey);
        }
        catch (JQException e) {
            logger.error(e.getMessage(), e);
        }
        if (schemes != null) {
            for (DesignFormSchemeDefine scheme : schemes) {
                result.addAll(this.tableDeployService.getPreDeployDdl((TaskDefine)taskDefine, (FormSchemeDefine)scheme));
            }
        }
        return result;
    }

    public void doDeploy(String taskKey) {
        ArrayList<String> deployTables = new ArrayList<String>();
        List schemes = null;
        try {
            schemes = this.nrDesignTimeController.queryFormSchemeByTask(taskKey);
        }
        catch (JQException e) {
            logger.error(e.getMessage(), e);
        }
        if (schemes != null) {
            for (DesignFormSchemeDefine scheme : schemes) {
                deployTables.addAll(this.tableDeployService.getRegisterTableId(scheme.getFormSchemeCode()));
            }
        }
        for (String deployTable : deployTables) {
            try {
                this.dataModelRegisterService.registerTable(deployTable);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public double getOrder() {
        return 2.0;
    }
}

