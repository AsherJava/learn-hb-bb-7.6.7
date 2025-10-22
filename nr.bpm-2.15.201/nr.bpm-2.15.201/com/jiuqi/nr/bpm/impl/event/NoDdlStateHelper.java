/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.internal.impl.DesignFlowSettingDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 */
package com.jiuqi.nr.bpm.impl.event;

import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.impl.common.NvwaDataModelCreateUtil;
import com.jiuqi.nr.bpm.impl.upload.dao.TableConstant;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.internal.impl.DesignFlowSettingDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class NoDdlStateHelper {
    private final NvwaDataModelCreateUtil nvwaDataModelCreateUtil;
    private final NRDesignTimeController nrDesignTimeController;
    private IWorkflow workflow;

    public NoDdlStateHelper(NvwaDataModelCreateUtil nvwaDataModelCreateUtil, NRDesignTimeController nrDesignTimeController, IWorkflow workflow) {
        this.nvwaDataModelCreateUtil = nvwaDataModelCreateUtil;
        this.nrDesignTimeController = nrDesignTimeController;
        this.workflow = workflow;
    }

    public String createState(DesignTaskDefine taskDefine, FormSchemeDefine scheme) throws Exception {
        String tableCode = TableConstant.getSysUploadStateTableName(scheme);
        String tableTitle = "\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u62a5\u8868\u65b9\u6848\u3010" + scheme.getTitle() + "\u3011\u4e0a\u62a5\u72b6\u6001\u8868";
        DesignTableModelDefine tableDefine = this.nvwaDataModelCreateUtil.initTableBase(tableCode, tableTitle);
        Map<String, DesignColumnModelDefine> filedMap = this.nvwaDataModelCreateUtil.getFiledMap(tableDefine.getID());
        StringBuffer tableEnityMasterKeys = this.nvwaDataModelCreateUtil.createBizkField(scheme, taskDefine, tableDefine, filedMap);
        String processKeyFieldId = this.nvwaDataModelCreateUtil.initField(tableDefine.getID(), "PROCESSKEY", null, "\u6d41\u7a0b\u5b9a\u4e49KEY", "'00000000-0000-0000-0000-000000000000'", ColumnModelType.STRING, 50, false, filedMap);
        WorkFlowType flowType = this.workflow.queryStartType(scheme.getKey());
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

    public String createHisState(DesignTaskDefine taskDefine, FormSchemeDefine formScheme) throws Exception {
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
            this.nvwaDataModelCreateUtil.updateTableModelDefine(tableDefine);
        } else {
            this.initHiTableOtherFiled(tableDefine.getID(), filedMap);
            this.nvwaDataModelCreateUtil.insertTableModelDefine(tableDefine);
        }
        return tableDefine.getID();
    }

    public String createSingleState(DesignTaskDefine taskDefine, FormSchemeDefine formScheme) throws Exception {
        DesignFlowSettingDefine flowSettings = taskDefine.getFlowsSetting().getDesignFlowSettingDefine();
        if (flowSettings.isAllowFormBack()) {
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
        return null;
    }

    private void initStateTableOtherFiled(String tableID, Map<String, DesignColumnModelDefine> filedMap) {
        this.nvwaDataModelCreateUtil.initField(tableID, "PREVEVENT", null, ColumnModelType.STRING, 50, false, filedMap, null);
        this.nvwaDataModelCreateUtil.initField(tableID, "CURNODE", null, ColumnModelType.STRING, 50, false, filedMap, null);
        this.nvwaDataModelCreateUtil.initField(tableID, "FORCE_STATE", null, "\u662f\u5426\u662f\u5f3a\u5236\u4e0a\u62a5", "0", ColumnModelType.STRING, 1, true, filedMap);
        this.nvwaDataModelCreateUtil.initField(tableID, "START_TIME", null, "\u6d41\u7a0b\u542f\u52a8\u65f6\u95f4", null, ColumnModelType.DATETIME, 50, true, filedMap);
        this.nvwaDataModelCreateUtil.initField(tableID, "UPDATE_TIME", null, "\u6d41\u7a0b\u66f4\u65b0\u65f6\u95f4", null, ColumnModelType.DATETIME, 50, true, filedMap);
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
    }

    private void initFormTableOtherField(String tableKey, Map<String, DesignColumnModelDefine> filedMap) {
        this.nvwaDataModelCreateUtil.initField(tableKey, "REJECT_USER", null, "\u9000\u56de\u53d1\u8d77\u4eba", null, ColumnModelType.STRING, 60, true, filedMap);
        this.nvwaDataModelCreateUtil.initField(tableKey, "REJECT_TIME", null, "\u9000\u56de\u53d1\u8d77\u65f6\u95f4", null, ColumnModelType.STRING, 50, true, filedMap);
        this.nvwaDataModelCreateUtil.initField(tableKey, "FORM_STATE", null, "\u72b6\u6001", "fm_reject", ColumnModelType.STRING, 50, true, filedMap);
    }
}

