/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  com.jiuqi.nr.definition.message.NrTaskDeleteEvent
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.common.TableModelKind
 *  com.jiuqi.nvwa.definition.common.TableModelType
 *  com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 */
package com.jiuqi.nr.bpm.setting.utils;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.nr.bpm.impl.common.NvwaDataModelCreateUtil;
import com.jiuqi.nr.bpm.setting.utils.SettingDeployUtil;
import com.jiuqi.nr.bpm.setting.utils.SettingRunException;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.definition.message.NrTaskDeleteEvent;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.common.TableModelKind;
import com.jiuqi.nvwa.definition.common.TableModelType;
import com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

@Deprecated
public class CreateSettingTable {
    @Autowired
    IDesignTimeViewController designTimeViewController;
    @Autowired
    IDesignTimeViewController nrDesignController;
    @Autowired
    NvwaDataModelCreateUtil nvwaDataModelCreateUtil;

    public boolean isAsyn() {
        return false;
    }

    public String getName() {
        return "\u81ea\u5b9a\u4e49\u6d41\u7a0b\u914d\u7f6e\u8868" + this.getClass().getName();
    }

    public void excute(Object[] objs) throws Exception {
        if (objs == null || objs.length == 0) {
            return;
        }
        for (Object obj : objs) {
            if (!(obj instanceof String)) continue;
            String taskID = (String)obj;
            DesignTaskDefine taskDefine = this.designTimeViewController.queryTaskDefine(taskID);
            TaskFlowsDefine flowsSetting = taskDefine.getFlowsSetting();
            if (FlowsType.NOSTARTUP.equals((Object)flowsSetting.getFlowsType())) {
                return;
            }
            List schemes = this.designTimeViewController.queryFormSchemeByTask(taskID);
            if (schemes == null) {
                return;
            }
            for (DesignFormSchemeDefine scheme : schemes) {
                this.deployDataPublish(taskDefine, (FormSchemeDefine)scheme);
            }
        }
    }

    private void deployDataPublish(DesignTaskDefine taskDefine, FormSchemeDefine scheme) throws Exception {
        String tableCode = "SYS_SETTING_" + scheme.getFormSchemeCode();
        DesignTableModelDefine tableDefine = this.nvwaDataModelCreateUtil.getDesTableByCode(tableCode);
        boolean doInsert = true;
        if (tableDefine == null) {
            tableDefine = this.nvwaDataModelCreateUtil.createTableDefine();
        } else {
            doInsert = false;
        }
        tableDefine.setCode(tableCode);
        tableDefine.setDesc("\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u62a5\u8868\u65b9\u6848\u3010" + scheme.getTitle() + "\u3011\u81ea\u5b9a\u4e49\u6d41\u7a0b\u4e0a\u62a5\u72b6\u6001\u8868");
        tableDefine.setTitle("\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u62a5\u8868\u65b9\u6848\u3010" + scheme.getTitle() + "\u3011\u81ea\u5b9a\u4e49\u6d41\u7a0b\u4e0a\u62a5\u72b6\u6001\u8868");
        tableDefine.setName(tableCode);
        tableDefine.setType(TableModelType.DEFAULT);
        tableDefine.setKind(TableModelKind.DEFAULT);
        tableDefine.setOwner("NR");
        String tableKey = tableDefine.getID();
        Map<String, DesignColumnModelDefine> filedMap = this.getFiledMap(tableKey);
        StringBuffer tableEnityMasterKeys = new StringBuffer();
        ArrayList<DesignColumnModelDefine> createFieldList = new ArrayList<DesignColumnModelDefine>();
        ArrayList<DesignColumnModelDefine> modifyFieldList = new ArrayList<DesignColumnModelDefine>();
        String settingId = SettingDeployUtil.initField_String(tableKey, "SETTINGID", null, createFieldList, modifyFieldList, 50, this.nvwaDataModelCreateUtil, filedMap);
        SettingDeployUtil.initField_String(tableKey, "REPORTID", null, createFieldList, modifyFieldList, 50, this.nvwaDataModelCreateUtil, filedMap);
        SettingDeployUtil.initField_String(tableKey, "STARTSTATE", null, createFieldList, modifyFieldList, 50, this.nvwaDataModelCreateUtil, filedMap);
        SettingDeployUtil.initField_Time_Stamp(tableKey, "STARTTIME", null, createFieldList, modifyFieldList, this.nvwaDataModelCreateUtil, filedMap);
        SettingDeployUtil.initField_Time_Stamp(tableKey, "UPDATETIME", null, createFieldList, modifyFieldList, this.nvwaDataModelCreateUtil, filedMap);
        SettingDeployUtil.initField_String(tableKey, "MARK", null, createFieldList, modifyFieldList, 1000, this.nvwaDataModelCreateUtil, filedMap);
        String entitiesKey = this.nrDesignController.getFormSchemeEntity(scheme.getKey());
        if (StringUtils.isEmpty((String)entitiesKey)) {
            entitiesKey = taskDefine.getMasterEntitiesKey();
        }
        if (StringUtils.isEmpty((String)entitiesKey)) {
            throw new Exception("\u62a5\u8868\u65b9\u6848\u3001\u4efb\u52a1\u6ca1\u6709\u8bbe\u7f6e\u4e3b\u952e\u65e0\u6cd5\u521b\u5efa\u6570\u636e\u53d1\u5e03\u72b6\u6001\u8868");
        }
        String[] entitiesKeyArr = entitiesKey.split(";");
        this.nvwaDataModelCreateUtil.initDwPeriodDimField(tableKey, tableEnityMasterKeys, entitiesKeyArr, createFieldList, modifyFieldList);
        tableEnityMasterKeys.append(settingId);
        tableDefine.setBizKeys(tableEnityMasterKeys.toString());
        tableDefine.setKeys(tableEnityMasterKeys.toString());
        if (createFieldList.size() > 0) {
            this.nvwaDataModelCreateUtil.insertFields(createFieldList.toArray(new DesignColumnModelDefine[1]));
        }
        if (modifyFieldList.size() > 0) {
            this.nvwaDataModelCreateUtil.updateFields(modifyFieldList.toArray(new DesignColumnModelDefine[1]));
        }
        if (doInsert) {
            DesignCatalogModelDefine sysTableGroup = this.nvwaDataModelCreateUtil.createCatlogModelDefine();
            tableDefine.setCatalogID(sysTableGroup.getID());
            this.nvwaDataModelCreateUtil.insertTableModelDefine(tableDefine);
        } else {
            this.nvwaDataModelCreateUtil.updateTableModelDefine(tableDefine);
        }
        this.nvwaDataModelCreateUtil.deployTable(tableDefine.getID());
    }

    public String initField(String tableKey, String fieldCode, String referField, ColumnModelType fieldType, FieldValueType valueType, int size, boolean nullable) {
        DesignColumnModelDefine fieldDefine = null;
        boolean doInsert = true;
        DesignColumnModelDefine fieldDefineByCode = this.nvwaDataModelCreateUtil.queryFieldDefinesByCode(fieldCode, tableKey);
        if (fieldDefineByCode != null) {
            doInsert = false;
        }
        if (fieldDefine == null) {
            fieldDefine = this.nvwaDataModelCreateUtil.createField();
        }
        fieldDefine.setCode(fieldCode);
        fieldDefine.setColumnType(fieldType);
        fieldDefine.setTableID(tableKey);
        fieldDefine.setPrecision(size);
        fieldDefine.setNullAble(nullable);
        if (referField != null) {
            fieldDefine.setReferColumnID(referField);
        }
        if (doInsert) {
            try {
                this.nvwaDataModelCreateUtil.insertField(fieldDefine);
            }
            catch (Exception e) {
                throw new SettingRunException(String.format("insert field %s error.", fieldCode), e);
            }
        } else {
            this.nvwaDataModelCreateUtil.updateField(fieldDefine);
        }
        return fieldDefine.getID();
    }

    public void onApplicationEvent(NrTaskDeleteEvent event) {
        List formSchemeDefines = event.getFormSchemeDefines();
        if (formSchemeDefines == null || formSchemeDefines.size() <= 0) {
            return;
        }
        for (FormSchemeDefine scheme : formSchemeDefines) {
            this.nvwaDataModelCreateUtil.deployDeleteTableByCode("SYS_SETTING_" + scheme.getFormSchemeCode());
        }
    }

    private Map<String, DesignColumnModelDefine> getFiledMap(String tableKey) {
        HashMap<String, DesignColumnModelDefine> fieldMap = new HashMap<String, DesignColumnModelDefine>();
        List<DesignColumnModelDefine> allFields = this.nvwaDataModelCreateUtil.getAllFieldsInTable(tableKey);
        for (DesignColumnModelDefine designColumnModelDefine : allFields) {
            fieldMap.put(designColumnModelDefine.getCode(), designColumnModelDefine);
        }
        return fieldMap;
    }
}

