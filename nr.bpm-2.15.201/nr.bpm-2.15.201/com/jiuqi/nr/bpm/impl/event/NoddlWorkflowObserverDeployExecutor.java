/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.paramcheck.NODDLDeployExecutor
 *  com.jiuqi.nr.definition.service.ITaskService
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DataModelRegisterService
 *  com.jiuqi.xlib.utils.CollectionUtils
 */
package com.jiuqi.nr.bpm.impl.event;

import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.impl.common.NvwaDataModelCreateUtil;
import com.jiuqi.nr.bpm.impl.event.NoDdlStateHelper;
import com.jiuqi.nr.bpm.impl.upload.dao.TableConstant;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.paramcheck.NODDLDeployExecutor;
import com.jiuqi.nr.definition.service.ITaskService;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DataModelRegisterService;
import com.jiuqi.xlib.utils.CollectionUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NoddlWorkflowObserverDeployExecutor
implements NODDLDeployExecutor {
    private static final Logger logger = LoggerFactory.getLogger(NoddlWorkflowObserverDeployExecutor.class);
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private DataModelDeployService dataModelDeployService;
    @Autowired
    private NvwaDataModelCreateUtil nvwaDataModelCreateUtil;
    @Autowired
    private DataModelRegisterService dataModelRegisterService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private ITaskService taskService;
    @Autowired
    private IWorkflow workflow;

    public List<String> preDeploy(String taskKey) {
        ArrayList<String> result = new ArrayList<String>();
        NoDdlStateHelper helper = new NoDdlStateHelper(this.nvwaDataModelCreateUtil, this.nrDesignTimeController, this.workflow);
        DesignTaskDefine taskDefine = this.nrDesignTimeController.queryTaskDefine(taskKey);
        List schemes = null;
        try {
            schemes = this.nrDesignTimeController.queryFormSchemeByTask(taskKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (schemes != null) {
            for (DesignFormSchemeDefine scheme : schemes) {
                if (this.isDimsChange(taskDefine)) {
                    this.appendStateDdl(taskDefine, scheme, helper, result);
                    this.appendHisStateDdl(taskDefine, scheme, helper, result);
                }
                if (!this.enableSingle(taskDefine)) continue;
                this.appendSingleStateDdl(taskDefine, scheme, helper, result);
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
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (schemes != null) {
            for (DesignFormSchemeDefine scheme : schemes) {
                String stateTableCode = TableConstant.getSysUploadStateTableName((FormSchemeDefine)scheme);
                this.appendDeployTableModel(deployTables, stateTableCode);
                String hisStateTableCode = TableConstant.getSysUploadRecordTableName((FormSchemeDefine)scheme);
                this.appendDeployTableModel(deployTables, hisStateTableCode);
                String fmStateTableCode = TableConstant.getSysUploadFormTableName((FormSchemeDefine)scheme);
                this.appendDeployTableModel(deployTables, fmStateTableCode);
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
        return 0.0;
    }

    private void appendStateDdl(DesignTaskDefine taskDefine, DesignFormSchemeDefine scheme, NoDdlStateHelper helper, List<String> result) {
        try {
            String tableId = helper.createState(taskDefine, (FormSchemeDefine)scheme);
            List ddl = this.dataModelDeployService.getDeployTableSqls(tableId);
            if (!CollectionUtils.isEmpty((Collection)ddl)) {
                result.addAll(ddl);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void appendHisStateDdl(DesignTaskDefine taskDefine, DesignFormSchemeDefine scheme, NoDdlStateHelper helper, List<String> result) {
        try {
            String tableId = helper.createHisState(taskDefine, (FormSchemeDefine)scheme);
            List ddl = this.dataModelDeployService.getDeployTableSqls(tableId);
            if (!CollectionUtils.isEmpty((Collection)ddl)) {
                result.addAll(ddl);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void appendSingleStateDdl(DesignTaskDefine taskDefine, DesignFormSchemeDefine scheme, NoDdlStateHelper helper, List<String> result) {
        try {
            List ddl;
            String tableId = helper.createSingleState(taskDefine, (FormSchemeDefine)scheme);
            if (tableId != null && !CollectionUtils.isEmpty((Collection)(ddl = this.dataModelDeployService.getDeployTableSqls(tableId)))) {
                result.addAll(ddl);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void appendDeployTableModel(List<String> deployTables, String tableCode) {
        DesignTableModelDefine table = this.nvwaDataModelCreateUtil.getDesTableByCode(tableCode);
        if (table != null) {
            deployTables.add(table.getID());
        }
    }

    private boolean enableSingle(DesignTaskDefine taskDefine) {
        TaskFlowsDefine flowsSetting = taskDefine.getFlowsSetting();
        if (flowsSetting != null) {
            return flowsSetting.isAllowFormBack();
        }
        return false;
    }

    private boolean isDimsChange(DesignTaskDefine taskDefine) {
        boolean dwchangeFlag = false;
        String designDw = taskDefine.getDw();
        TaskDefine runtimeTaskDefine = this.runTimeViewController.queryTaskDefine(taskDefine.getKey());
        if (runtimeTaskDefine != null) {
            String runtimeDw = runtimeTaskDefine.getDw();
            if (!designDw.equals(runtimeDw)) {
                dwchangeFlag = true;
            }
        } else {
            dwchangeFlag = true;
        }
        List reportDimension = this.taskService.getReportDimension(taskDefine.getKey());
        List<String> designDimkeys = reportDimension.stream().map(e -> e.getDimKey()).collect(Collectors.toList());
        List runtimeReportDimension = this.runtimeDataSchemeService.getReportDimension(taskDefine.getDataScheme());
        List<String> runtimeDimkeys = runtimeReportDimension.stream().map(e -> e.getDimKey()).collect(Collectors.toList());
        boolean dimsChangeFlag = !NoddlWorkflowObserverDeployExecutor.CollectionEqual(designDimkeys, runtimeDimkeys);
        return dwchangeFlag || dimsChangeFlag;
    }

    public static boolean CollectionEqual(List<String> list, List<String> list1) {
        list.sort(Comparator.comparing(String::hashCode));
        list1.sort(Comparator.comparing(String::hashCode));
        return list.toString().equals(list1.toString());
    }
}

