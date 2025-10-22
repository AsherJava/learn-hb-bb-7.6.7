/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.event.DataSchemeDeployListener
 *  com.jiuqi.nr.datascheme.api.event.DataSchemeDeploySource
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.paramcheck.NODDLDeployExecutor
 *  com.jiuqi.nvwa.definition.service.DataModelRegisterService
 */
package com.jiuqi.nr.data.access.event;

import com.jiuqi.nr.data.access.event.StatusTableCreateUtil;
import com.jiuqi.nr.datascheme.api.event.DataSchemeDeployListener;
import com.jiuqi.nr.datascheme.api.event.DataSchemeDeploySource;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.paramcheck.NODDLDeployExecutor;
import com.jiuqi.nvwa.definition.service.DataModelRegisterService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DataStatusObserver
implements DataSchemeDeployListener,
NODDLDeployExecutor {
    @Autowired
    private StatusTableCreateUtil statusTableCreateUtil;
    @Autowired
    private IDesignTimeViewController timeViewController;
    @Autowired
    private DataModelRegisterService dataModelRegisterService;
    @Value(value="${jiuqi.nvwa.databaseLimitMode:false}")
    private boolean noDDL;
    private static final Logger logger = LoggerFactory.getLogger(DataStatusObserver.class);

    public void onDataSchemeDeploy(DataSchemeDeploySource source) {
        try {
            if (this.noDDL) {
                return;
            }
            this.statusTableCreateUtil.initDataPublishDeploy(source.getDataSchemeKey(), source.getDataScheme(), false);
            this.statusTableCreateUtil.initFormLockDeploy(source.getDataSchemeKey(), source.getDataScheme(), false);
            this.statusTableCreateUtil.initUnitStateDeploy(source.getDataSchemeKey(), source.getDataScheme(), false);
            this.statusTableCreateUtil.initSecretDeploy(source.getDataSchemeKey(), source.getDataScheme(), false);
        }
        catch (Exception e) {
            logger.error("\u53d1\u5e03\u8bfb\u5199\u6743\u9650\u76f8\u5173\u72b6\u6001\u8868\u5f02\u5e38\uff01", e);
        }
    }

    public List<String> preDeploy(String taskKey) {
        List<String> sql;
        if (!this.noDDL) {
            return Collections.emptyList();
        }
        DesignTaskDefine taskDefine = this.timeViewController.queryTaskDefine(taskKey);
        if (taskDefine == null) {
            return Collections.emptyList();
        }
        ArrayList<String> sqls = new ArrayList<String>();
        String dataScheme = taskDefine.getDataScheme();
        try {
            sql = this.statusTableCreateUtil.initDataPublishDeploy(dataScheme, null, true);
            sqls.addAll(sql);
        }
        catch (Exception e) {
            logger.error("\u53d1\u5e03\u8bfb\u5199\u6743\u9650\u76f8\u5173\u72b6\u6001\u8868\u5f02\u5e38\uff01", e);
        }
        try {
            sql = this.statusTableCreateUtil.initFormLockDeploy(dataScheme, null, true);
            sqls.addAll(sql);
        }
        catch (Exception e) {
            logger.error("\u53d1\u5e03\u8bfb\u5199\u6743\u9650\u76f8\u5173\u72b6\u6001\u8868\u5f02\u5e38\uff01", e);
        }
        try {
            sql = this.statusTableCreateUtil.initUnitStateDeploy(dataScheme, null, true);
            sqls.addAll(sql);
        }
        catch (Exception e) {
            logger.error("\u53d1\u5e03\u8bfb\u5199\u6743\u9650\u76f8\u5173\u72b6\u6001\u8868\u5f02\u5e38\uff01", e);
        }
        try {
            sql = this.statusTableCreateUtil.initSecretDeploy(dataScheme, null, true);
            sqls.addAll(sql);
        }
        catch (Exception e) {
            logger.error("\u53d1\u5e03\u8bfb\u5199\u6743\u9650\u76f8\u5173\u72b6\u6001\u8868\u5f02\u5e38\uff01", e);
        }
        return sqls;
    }

    public void doDeploy(String taskKey) {
        DesignTaskDefine taskDefine = this.timeViewController.queryTaskDefine(taskKey);
        if (taskDefine == null) {
            return;
        }
        String dataScheme = taskDefine.getDataScheme();
        List<String> tableIds = this.statusTableCreateUtil.getTableIds(dataScheme);
        for (String tableId : tableIds) {
            try {
                this.dataModelRegisterService.registerTable(tableId);
            }
            catch (Exception e) {
                logger.error("\u53d1\u5e03\u8bfb\u5199\u6743\u9650\u76f8\u5173\u72b6\u6001\u8868\u5f02\u5e38\uff01", e);
            }
        }
    }

    public double getOrder() {
        return 10.0;
    }
}

