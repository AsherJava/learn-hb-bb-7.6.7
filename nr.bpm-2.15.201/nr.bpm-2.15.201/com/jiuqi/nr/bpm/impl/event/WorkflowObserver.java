/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  com.jiuqi.np.definition.deploy.DeployItem
 *  com.jiuqi.np.definition.observer.MessageType
 *  com.jiuqi.np.definition.observer.NpDefinitionObserver
 *  com.jiuqi.np.definition.observer.Observer
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.deploy.DeployParams
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.impl.DesignFlowSettingDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.definition.internal.runtime.service.DeployPrepareEventListener
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.bpm.impl.event;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.definition.deploy.DeployItem;
import com.jiuqi.np.definition.observer.MessageType;
import com.jiuqi.np.definition.observer.NpDefinitionObserver;
import com.jiuqi.np.definition.observer.Observer;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.common.NvwaDataModelCreateUtil;
import com.jiuqi.nr.bpm.impl.upload.dao.TableConstant;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.deploy.DeployParams;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.DesignFlowSettingDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.definition.internal.runtime.service.DeployPrepareEventListener;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;

@NpDefinitionObserver(type={MessageType.NRPUBLISHTASK})
public class WorkflowObserver
implements Observer,
DeployPrepareEventListener {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowObserver.class);
    private static final String TASK_VERSION_10 = "1.0";
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private NvwaDataModelCreateUtil nvwaDataModelCreateUtil;
    @Autowired
    private NrParameterUtils nrParameterUtils;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private IWorkflow workflow;
    @Autowired
    private IRunTimeViewController runTimeViewController;

    private static boolean taskIsMatchVersion1_0(TaskDefine task) {
        return TASK_VERSION_10.equals(task.getVersion());
    }

    public boolean isAsyn() {
        return false;
    }

    public void excute(Object[] objs) throws Exception {
        if (objs == null || objs.length == 0) {
            return;
        }
        for (Object obj : objs) {
            String taskKey;
            TaskDefine task;
            if (!(obj instanceof String) || !WorkflowObserver.taskIsMatchVersion1_0(task = this.nrParameterUtils.getTaskDefine(taskKey = (String)obj))) continue;
            this.maintainTableModel(taskKey);
        }
    }

    public String getName() {
        return "\u6d41\u7a0b\u72b6\u6001\u8868" + this.getClass().getName();
    }

    public void maintainTableModel(String taskKey) throws Exception {
        this.createTable(taskKey);
        this.checkProcessTableForceField(taskKey);
        this.checkProcessTableTypeField(taskKey);
        this.checkPrimaryKey(taskKey);
    }

    public void maintainTableModel(String taskKey, String formSchemeKey) throws Exception {
        this.createTable(taskKey, formSchemeKey);
        this.checkProcessTableForceField(taskKey, formSchemeKey);
        this.checkProcessTableTypeField(taskKey, formSchemeKey);
        this.checkPrimaryKey(taskKey, formSchemeKey);
    }

    private void createTable(String taskKey) throws Exception {
        List formSchemes = this.designTimeViewController.queryFormSchemeByTask(taskKey);
        for (DesignFormSchemeDefine formScheme : formSchemes) {
            this.createTable(formScheme);
        }
    }

    private void createTable(String taskKey, String formSchemeKey) throws Exception {
        List formSchemes = this.designTimeViewController.queryFormSchemeByTask(taskKey);
        for (DesignFormSchemeDefine formScheme : formSchemes) {
            if (!formScheme.getKey().equals(formSchemeKey)) continue;
            this.createTable(formScheme);
        }
    }

    private void createTable(DesignFormSchemeDefine formScheme) throws Exception {
        String stateTableID = this.createStateTable((FormSchemeDefine)formScheme);
        String hiTableID = this.createHiTableDefine((FormSchemeDefine)formScheme);
        this.nvwaDataModelCreateUtil.deployTable(stateTableID);
        this.nvwaDataModelCreateUtil.deployTable(hiTableID);
        DesignTaskDefine queryTaskDefine = this.designTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        DesignFlowSettingDefine flowSettings = queryTaskDefine.getFlowsSetting().getDesignFlowSettingDefine();
        if (flowSettings.isAllowFormBack()) {
            String fromTableId = this.createFormTable((FormSchemeDefine)formScheme);
            this.nvwaDataModelCreateUtil.deployTable(fromTableId);
        }
    }

    private String createFormTable(FormSchemeDefine formScheme) throws JQException {
        DesignTaskDefine taskDefine = this.designTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        String tableCode = TableConstant.getSysUploadFormTableName(formScheme);
        String tableTitle = "\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u62a5\u8868\u65b9\u6848\u3010" + formScheme.getTitle() + "\u3011\u5355\u8868\u9000\u56de\u8bb0\u5f55\u8868";
        DesignTableModelDefine tableDefine = this.nvwaDataModelCreateUtil.initTableBase(tableCode, tableTitle);
        Map<String, DesignColumnModelDefine> filedMap = this.nvwaDataModelCreateUtil.getFiledMap(tableDefine.getID());
        StringBuffer tableEnityMasterKeys = this.nvwaDataModelCreateUtil.createBizkField(formScheme, taskDefine, tableDefine, filedMap);
        String formField = this.nvwaDataModelCreateUtil.initField(tableDefine.getID(), "FORMID", null, ColumnModelType.STRING, 50, false, filedMap, null);
        tableEnityMasterKeys.append(formField).append(";");
        tableDefine.setBizKeys(tableEnityMasterKeys.toString());
        tableDefine.setKeys(tableEnityMasterKeys.toString());
        if (this.nvwaDataModelCreateUtil.getTable(tableDefine.getID()) != null) {
            this.nvwaDataModelCreateUtil.updateTableModelDefine(tableDefine);
        } else {
            this.initFormTableOtherField(tableDefine.getID(), filedMap);
            this.nvwaDataModelCreateUtil.insertTableModelDefine(tableDefine);
        }
        return tableDefine.getID();
    }

    private String createHiTableDefine(FormSchemeDefine formScheme) {
        DesignTaskDefine taskDefine = this.designTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        String tableCode = TableConstant.getSysUploadRecordTableName(formScheme);
        String tableTitle = "\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u62a5\u8868\u65b9\u6848\u3010" + formScheme.getTitle() + "\u3011\u4e0a\u62a5\u5386\u53f2\u8868";
        DesignTableModelDefine tableDefine = this.nvwaDataModelCreateUtil.initTableBase(tableCode, tableTitle);
        Map<String, DesignColumnModelDefine> filedMap = this.nvwaDataModelCreateUtil.getFiledMap(tableDefine.getID());
        StringBuffer tableEnityMasterKeys = this.nvwaDataModelCreateUtil.createBizkField(formScheme, taskDefine, tableDefine, filedMap);
        String bizkOrderFieldId = this.nvwaDataModelCreateUtil.initField(tableDefine.getID(), "BIZKEYORDER", null, ColumnModelType.STRING, 100, false, filedMap, null);
        String processKeyFieldId = this.nvwaDataModelCreateUtil.initField(tableDefine.getID(), "PROCESSKEY", null, "\u6d41\u7a0b\u5b9a\u4e49KEY", "'00000000-0000-0000-0000-000000000000'", ColumnModelType.STRING, 50, false, filedMap);
        String formFieldId = this.nvwaDataModelCreateUtil.initField(tableDefine.getID(), "FORMID", null, ColumnModelType.STRING, 50, true, filedMap, null);
        String returnTypeFieldId = this.nvwaDataModelCreateUtil.initField(tableDefine.getID(), "RETURN_TYPE", null, ColumnModelType.STRING, 100, true, filedMap, null);
        WorkFlowType flowType = this.workflow.queryStartType(formScheme.getKey());
        if (flowType != WorkFlowType.ENTITY) {
            tableEnityMasterKeys.append(formFieldId).append(";");
            DesignColumnModelDefine formField = this.nvwaDataModelCreateUtil.queryFieldDefine(formFieldId);
            formField.setNullAble(false);
            this.nvwaDataModelCreateUtil.updateField(formField);
        }
        tableEnityMasterKeys.append(processKeyFieldId).append(";");
        tableEnityMasterKeys.append(bizkOrderFieldId).append(";");
        tableDefine.setBizKeys(tableEnityMasterKeys.toString());
        tableDefine.setKeys(tableEnityMasterKeys.toString());
        if (this.nvwaDataModelCreateUtil.getTable(tableDefine.getID()) != null) {
            this.initHiTableOtherFiled(tableDefine.getID(), filedMap);
            this.nvwaDataModelCreateUtil.updateTableModelDefine(tableDefine);
        } else {
            this.initHiTableOtherFiled(tableDefine.getID(), filedMap);
            this.nvwaDataModelCreateUtil.insertTableModelDefine(tableDefine);
        }
        return tableDefine.getID();
    }

    private String createStateTable(FormSchemeDefine formScheme) {
        DesignTaskDefine taskDefine = this.designTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        String tableCode = TableConstant.getSysUploadStateTableName(formScheme);
        String tableVersion = "1.0.0";
        String tableTitle = "\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u62a5\u8868\u65b9\u6848\u3010" + formScheme.getTitle() + "\u3011\u4e0a\u62a5\u72b6\u6001\u8868";
        DesignTableModelDefine tableDefine = this.nvwaDataModelCreateUtil.initTableBase(tableCode, tableTitle);
        Map<String, DesignColumnModelDefine> filedMap = this.nvwaDataModelCreateUtil.getFiledMap(tableDefine.getID());
        StringBuffer tableEnityMasterKeys = this.nvwaDataModelCreateUtil.createBizkField(formScheme, taskDefine, tableDefine, filedMap);
        String processKeyFieldId = this.nvwaDataModelCreateUtil.initField(tableDefine.getID(), "PROCESSKEY", null, "\u6d41\u7a0b\u5b9a\u4e49KEY", "'00000000-0000-0000-0000-000000000000'", ColumnModelType.STRING, 50, false, filedMap);
        WorkFlowType flowType = this.workflow.queryStartType(formScheme.getKey());
        String formKeyField = this.nvwaDataModelCreateUtil.initField(tableDefine.getID(), "FORMID", null, "\u62a5\u8868\u6216\u5206\u7ec4key", "'00000000-0000-0000-0000-000000000000'", ColumnModelType.STRING, 50, true, filedMap);
        if (flowType == WorkFlowType.ENTITY) {
            tableEnityMasterKeys.append(processKeyFieldId).append(";");
        } else {
            tableEnityMasterKeys.append(formKeyField).append(";").append(processKeyFieldId).append(";");
            DesignColumnModelDefine formField = this.nvwaDataModelCreateUtil.queryFieldDefine(formKeyField);
            formField.setNullAble(false);
            this.nvwaDataModelCreateUtil.updateField(formField);
        }
        tableDefine.setBizKeys(tableEnityMasterKeys.toString());
        tableDefine.setKeys(tableEnityMasterKeys.toString());
        if (this.nvwaDataModelCreateUtil.getTable(tableDefine.getID()) != null) {
            this.nvwaDataModelCreateUtil.updateTableModelDefine(tableDefine);
        } else {
            this.initStateTableOtherFiled(tableDefine.getID(), filedMap);
            this.nvwaDataModelCreateUtil.insertTableModelDefine(tableDefine);
        }
        return tableDefine.getID();
    }

    private void initStateTableOtherFiled(String tableID, Map<String, DesignColumnModelDefine> filedMap) {
        this.nvwaDataModelCreateUtil.initField(tableID, "PREVEVENT", null, ColumnModelType.STRING, 50, false, filedMap, null);
        this.nvwaDataModelCreateUtil.initField(tableID, "CURNODE", null, ColumnModelType.STRING, 50, false, filedMap, null);
        this.nvwaDataModelCreateUtil.initField(tableID, "FORCE_STATE", null, "\u662f\u5426\u662f\u5f3a\u5236\u4e0a\u62a5", "0", ColumnModelType.STRING, 1, true, filedMap);
        this.nvwaDataModelCreateUtil.initField(tableID, "START_TIME", null, "\u6d41\u7a0b\u542f\u52a8\u65f6\u95f4", null, ColumnModelType.DATETIME, 50, true, filedMap);
        this.nvwaDataModelCreateUtil.initField(tableID, "UPDATE_TIME", null, "\u6d41\u7a0b\u66f4\u65b0\u65f6\u95f4", null, ColumnModelType.DATETIME, 50, true, filedMap);
        this.nvwaDataModelCreateUtil.initField(tableID, "SERIAL_NUMBER", null, ColumnModelType.STRING, 50, true, filedMap, null);
    }

    private void initHiTableOtherFiled(String tableID, Map<String, DesignColumnModelDefine> filedMap) {
        this.nvwaDataModelCreateUtil.initField(tableID, "CUREVENT", null, ColumnModelType.STRING, 50, false, filedMap, null);
        this.nvwaDataModelCreateUtil.initField(tableID, "CURNODE", null, ColumnModelType.STRING, 50, false, filedMap, null);
        this.nvwaDataModelCreateUtil.initField(tableID, "CMT", null, ColumnModelType.CLOB, 0, true, filedMap, null);
        this.nvwaDataModelCreateUtil.initField(tableID, "TIME_", null, ColumnModelType.DATETIME, 6, false, filedMap, null);
        this.nvwaDataModelCreateUtil.initField(tableID, "OPERATOR", null, ColumnModelType.STRING, 50, true, filedMap, null);
        this.nvwaDataModelCreateUtil.initField(tableID, "OPERATIONID", null, ColumnModelType.STRING, 50, true, filedMap, null);
        this.nvwaDataModelCreateUtil.initField(tableID, "FORCE_STATE", null, "\u662f\u5426\u662f\u5f3a\u5236\u4e0a\u62a5", "0", ColumnModelType.STRING, 1, false, filedMap);
        this.nvwaDataModelCreateUtil.initField(tableID, "EXECUTE_ORDER", null, "\u6267\u884c\u987a\u5e8f", "0", ColumnModelType.INTEGER, 11, false, filedMap);
        this.nvwaDataModelCreateUtil.initField(tableID, "SERIAL_NUMBER", null, ColumnModelType.STRING, 50, true, filedMap, null);
        this.nvwaDataModelCreateUtil.initField(tableID, "ROLE_KEY", null, ColumnModelType.STRING, 50, true, filedMap, null);
    }

    private void initFormTableOtherField(String tableKey, Map<String, DesignColumnModelDefine> filedMap) {
        this.nvwaDataModelCreateUtil.initField(tableKey, "REJECT_USER", null, "\u9000\u56de\u53d1\u8d77\u4eba", null, ColumnModelType.STRING, 60, true, filedMap);
        this.nvwaDataModelCreateUtil.initField(tableKey, "REJECT_TIME", null, "\u9000\u56de\u53d1\u8d77\u65f6\u95f4", null, ColumnModelType.STRING, 50, true, filedMap);
        this.nvwaDataModelCreateUtil.initField(tableKey, "FORM_STATE", null, "\u72b6\u6001", "fm_reject", ColumnModelType.STRING, 50, true, filedMap);
    }

    private void checkProcessTableForceField(String taskKey) throws Exception {
        List formSchemDefines = this.designTimeViewController.queryFormSchemeByTask(taskKey);
        for (FormSchemeDefine formScheme : formSchemDefines) {
            this.checkProcessTableForceField(formScheme);
        }
    }

    private void checkProcessTableForceField(String taskKey, String formSchemeKey) throws Exception {
        List formSchemDefines = this.designTimeViewController.queryFormSchemeByTask(taskKey);
        for (FormSchemeDefine formScheme : formSchemDefines) {
            if (!formScheme.getKey().equals(formSchemeKey)) continue;
            this.checkProcessTableForceField(formScheme);
        }
    }

    private void checkProcessTableForceField(FormSchemeDefine formScheme) throws Exception {
        String stateTableCode = TableConstant.getSysUploadStateTableName(formScheme);
        DesignTableModelDefine stateTable = this.nvwaDataModelCreateUtil.getDesTableByCode(stateTableCode);
        String hiTableCode = TableConstant.getSysUploadRecordTableName(formScheme);
        DesignTableModelDefine hiTable = this.nvwaDataModelCreateUtil.getDesTableByCode(hiTableCode);
        if (stateTable == null || hiTable == null) {
            return;
        }
        DesignColumnModelDefine forceField = this.nvwaDataModelCreateUtil.queryFieldDefinesByCode("FORCE_STATE", stateTable.getID());
        if (forceField != null) {
            ColumnModelDefine runtimeForceField = this.nvwaDataModelCreateUtil.getRunColumnDefinesByCode("FORCE_STATE", stateTable.getID());
            if (runtimeForceField == null) {
                this.nvwaDataModelCreateUtil.deployTable(stateTable.getID());
                this.nvwaDataModelCreateUtil.deployTable(hiTable.getID());
            }
            return;
        }
        Map<String, DesignColumnModelDefine> stateFiledMap = this.nvwaDataModelCreateUtil.getFiledMap(stateTable.getID());
        this.nvwaDataModelCreateUtil.initField(stateTable.getID(), "FORCE_STATE", null, "\u662f\u5426\u662f\u5f3a\u5236\u4e0a\u62a5", "0", ColumnModelType.STRING, 1, false, stateFiledMap);
        this.nvwaDataModelCreateUtil.deployTable(stateTable.getID());
        Map<String, DesignColumnModelDefine> hiFiledMap = this.nvwaDataModelCreateUtil.getFiledMap(hiTable.getID());
        this.nvwaDataModelCreateUtil.initField(hiTable.getID(), "FORCE_STATE", null, "\u662f\u5426\u662f\u5f3a\u5236\u4e0a\u62a5", "0", ColumnModelType.STRING, 1, false, hiFiledMap);
        this.nvwaDataModelCreateUtil.deployTable(hiTable.getID());
    }

    private void checkPrimaryKey(String taskKey) throws Exception {
        List formSchemDefines = this.designTimeViewController.queryFormSchemeByTask(taskKey);
        for (FormSchemeDefine formScheme : formSchemDefines) {
            this.checkPrimaryKey(formScheme);
        }
    }

    private void checkPrimaryKey(String taskKey, String formSchemeKey) throws Exception {
        List formSchemDefines = this.designTimeViewController.queryFormSchemeByTask(taskKey);
        for (FormSchemeDefine formScheme : formSchemDefines) {
            if (!formScheme.getKey().equals(formSchemeKey)) continue;
            this.checkPrimaryKey(formScheme);
        }
    }

    private void checkPrimaryKey(FormSchemeDefine formScheme) throws Exception {
        String stateTableCode = TableConstant.getSysUploadStateTableName(formScheme);
        String hiTableCode = TableConstant.getSysUploadRecordTableName(formScheme);
        DesignTableModelDefine stateTable = this.nvwaDataModelCreateUtil.getDesTableByCode(stateTableCode);
        DesignTableModelDefine hiTable = this.nvwaDataModelCreateUtil.getDesTableByCode(hiTableCode);
        WorkFlowType startType = this.workflow.queryStartType(formScheme.getKey());
        if (stateTable == null || hiTable == null) {
            return;
        }
        if (startType == WorkFlowType.ENTITY) {
            String bizKeysStr;
            DesignColumnModelDefine formField;
            Set bizKeys = Stream.of(stateTable.getBizKeys().split(";")).collect(Collectors.toSet());
            if (bizKeys.contains((formField = this.nvwaDataModelCreateUtil.queryFieldDefinesByCode("FORMID", stateTable.getID())).getID())) {
                bizKeys.remove(formField.getID());
                bizKeysStr = String.join((CharSequence)";", bizKeys);
                stateTable.setBizKeys(bizKeysStr);
                stateTable.setKeys(bizKeysStr);
                formField.setNullAble(true);
                this.deleteTable(stateTableCode);
                this.nvwaDataModelCreateUtil.updateField(formField);
                this.nvwaDataModelCreateUtil.updateTableModelDefine(stateTable);
                this.nvwaDataModelCreateUtil.deployTable(stateTable.getID());
            }
            if ((bizKeys = Stream.of(hiTable.getBizKeys().split(";")).collect(Collectors.toSet())).contains((formField = this.nvwaDataModelCreateUtil.queryFieldDefinesByCode("FORMID", hiTable.getID())).getID())) {
                bizKeys.remove(formField.getID());
                bizKeysStr = String.join((CharSequence)";", bizKeys);
                hiTable.setBizKeys(bizKeysStr);
                hiTable.setKeys(bizKeysStr);
                formField.setNullAble(true);
                this.deleteTable(hiTableCode);
                this.nvwaDataModelCreateUtil.updateField(formField);
                this.nvwaDataModelCreateUtil.updateTableModelDefine(hiTable);
                this.nvwaDataModelCreateUtil.deployTable(hiTable.getID());
            }
        } else {
            String bizKeysStr;
            DesignColumnModelDefine formField;
            Set bizKeys = Stream.of(stateTable.getBizKeys().split(";")).collect(Collectors.toSet());
            if (!bizKeys.contains((formField = this.nvwaDataModelCreateUtil.queryFieldDefinesByCode("FORMID", stateTable.getID())).getID())) {
                bizKeys.add(formField.getID());
                bizKeysStr = String.join((CharSequence)";", bizKeys);
                stateTable.setBizKeys(bizKeysStr);
                stateTable.setKeys(bizKeysStr);
                this.updateTable(stateTableCode);
                formField.setNullAble(false);
                this.nvwaDataModelCreateUtil.updateField(formField);
                this.nvwaDataModelCreateUtil.updateTableModelDefine(stateTable);
                this.nvwaDataModelCreateUtil.deployTable(stateTable.getID());
            }
            if (!(bizKeys = Stream.of(hiTable.getBizKeys().split(";")).collect(Collectors.toSet())).contains((formField = this.nvwaDataModelCreateUtil.queryFieldDefinesByCode("FORMID", hiTable.getID())).getID())) {
                bizKeys.add(formField.getID());
                this.updateTable(hiTableCode);
                bizKeysStr = String.join((CharSequence)";", bizKeys);
                hiTable.setBizKeys(bizKeysStr);
                hiTable.setKeys(bizKeysStr);
                formField.setNullAble(false);
                this.nvwaDataModelCreateUtil.updateField(formField);
                this.nvwaDataModelCreateUtil.updateTableModelDefine(hiTable);
                this.nvwaDataModelCreateUtil.deployTable(hiTable.getID());
            }
        }
    }

    private void checkProcessTableTypeField(String taskKey) throws Exception {
        List formSchemDefines = this.designTimeViewController.queryFormSchemeByTask(taskKey);
        for (FormSchemeDefine formScheme : formSchemDefines) {
            this.checkProcessTableTypeField(formScheme);
        }
    }

    private void checkProcessTableTypeField(String taskKey, String formSchemeKey) throws Exception {
        List formSchemDefines = this.designTimeViewController.queryFormSchemeByTask(taskKey);
        for (FormSchemeDefine formScheme : formSchemDefines) {
            if (!formScheme.getKey().equals(formSchemeKey)) continue;
            this.checkProcessTableTypeField(formScheme);
        }
    }

    private void checkProcessTableTypeField(FormSchemeDefine formScheme) throws Exception {
        String stateTableCode = TableConstant.getSysUploadStateTableName(formScheme);
        String hisTableCode = TableConstant.getSysUploadRecordTableName(formScheme);
        DesignTableModelDefine stateTable = this.nvwaDataModelCreateUtil.getDesTableByCode(stateTableCode);
        DesignTableModelDefine hiTable = this.nvwaDataModelCreateUtil.getDesTableByCode(hisTableCode);
        if (stateTable == null || hiTable == null) {
            return;
        }
        Map<String, DesignColumnModelDefine> hiFiledMap = this.nvwaDataModelCreateUtil.getFiledMap(hiTable.getID());
        DesignColumnModelDefine orderField = this.nvwaDataModelCreateUtil.queryFieldDefinesByCode("EXECUTE_ORDER", hiTable.getID());
        if (orderField != null) {
            ColumnModelDefine runtimeField = this.nvwaDataModelCreateUtil.getRunColumnDefinesByCode("EXECUTE_ORDER", hiTable.getID());
            if (runtimeField == null) {
                this.nvwaDataModelCreateUtil.deployTable(hiTable.getID());
            }
        } else {
            this.nvwaDataModelCreateUtil.initField(hiTable.getID(), "EXECUTE_ORDER", null, "\u6267\u884c\u987a\u5e8f", "0", ColumnModelType.INTEGER, 11, false, hiFiledMap);
            this.nvwaDataModelCreateUtil.deployTable(hiTable.getID());
        }
        Map<String, DesignColumnModelDefine> stateFiledMap = this.nvwaDataModelCreateUtil.getFiledMap(stateTable.getID());
        DesignColumnModelDefine timeField = this.nvwaDataModelCreateUtil.queryFieldDefinesByCode("START_TIME", stateTable.getID());
        if (timeField != null) {
            ColumnModelDefine runtimeField = this.nvwaDataModelCreateUtil.getRunColumnDefinesByCode("START_TIME", stateTable.getID());
            if (runtimeField == null) {
                this.nvwaDataModelCreateUtil.deployTable(stateTable.getID());
            }
            return;
        }
        this.nvwaDataModelCreateUtil.initField(stateTable.getID(), "START_TIME", null, "\u6d41\u7a0b\u542f\u52a8\u65f6\u95f4", null, ColumnModelType.DATETIME, 50, true, stateFiledMap);
        this.nvwaDataModelCreateUtil.initField(stateTable.getID(), "UPDATE_TIME", null, "\u6d41\u7a0b\u66f4\u65b0\u65f6\u95f4", null, ColumnModelType.DATETIME, 50, true, stateFiledMap);
        this.nvwaDataModelCreateUtil.deployTable(stateTable.getID());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void updateTable(String tableCode) {
        DataSource dataSource = this.jdbcTemplate.getDataSource();
        Connection conn = null;
        StringBuffer sql = new StringBuffer();
        sql.append("update ").append(tableCode).append(" set ");
        sql.append("FORMID").append("=? where ");
        sql.append("FORMID").append(" is null");
        Object[] args = new Object[]{"00000000-0000-0000-0000-000000000000"};
        try {
            conn = DataSourceUtils.getConnection((DataSource)dataSource);
            DataEngineUtil.executeUpdate((Connection)conn, (String)sql.toString(), (Object[])args);
            conn.commit();
        }
        catch (SQLException throwables) {
            logger.error(throwables.getMessage(), throwables);
        }
        finally {
            if (conn != null) {
                DataSourceUtils.releaseConnection((Connection)conn, (DataSource)dataSource);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void deleteTable(String tableCode) {
        DataSource dataSource = this.jdbcTemplate.getDataSource();
        Connection conn = null;
        StringBuffer sql = new StringBuffer();
        sql.append("delete from ").append(tableCode).append(" where ");
        sql.append("FORMID").append(" <> ?");
        Object[] args = new Object[]{"00000000-0000-0000-0000-000000000000"};
        try {
            conn = DataSourceUtils.getConnection((DataSource)dataSource);
            DataEngineUtil.executeUpdate((Connection)conn, (String)sql.toString(), (Object[])args);
            conn.commit();
        }
        catch (SQLException throwables) {
            logger.error(throwables.getMessage(), throwables);
        }
        finally {
            if (conn != null) {
                DataSourceUtils.releaseConnection((Connection)conn, (DataSource)dataSource);
            }
        }
    }

    public void dropTable(FormSchemeDefine scheme) {
        this.nvwaDataModelCreateUtil.deployDeleteTableByCode(TableConstant.getSysUploadStateTableName(scheme));
        this.nvwaDataModelCreateUtil.deployDeleteTableByCode(TableConstant.getSysUploadRecordTableName(scheme));
    }

    public void onDeploy(DeployParams deployParams) {
        DeployItem formScheme = deployParams.getFormScheme();
        if (null == formScheme) {
            return;
        }
        Set runTimeKeys = formScheme.getRunTimeKeys();
        Set designTimeKeys = formScheme.getDesignTimeKeys();
        HashSet runKeysCopy = new HashSet(runTimeKeys);
        runKeysCopy.removeAll(designTimeKeys);
        if (runKeysCopy.isEmpty()) {
            return;
        }
        for (String runTimeKey : runKeysCopy) {
            DesignFormSchemeDefine designFormSchemeDefine = this.designTimeViewController.queryFormSchemeDefine(runTimeKey);
            if (null == designFormSchemeDefine) continue;
            this.dropTable((FormSchemeDefine)designFormSchemeDefine);
        }
    }
}

