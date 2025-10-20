/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataLinkMappingDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormFoldingDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.xlib.utils.StringUtils
 */
package com.jiuqi.nr.designer.formcopy.common;

import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkMappingDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormFoldingDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.designer.formcopy.common.DesignDataRegion;
import com.jiuqi.xlib.utils.StringUtils;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FormCopyContext {
    boolean matchByCode;
    private String formSchemeKey;
    private String taskKey;
    private String srcFormSchemeKey;
    private String srcTaskKey;
    private boolean copyDataModel;
    private String dataSchemeKey;
    private String formSchemeTitle;
    private String srcDataSchemeKey;
    private String prefix;
    private String srcPrefix;
    private Map<String, DesignFormGroupDefine> formGroupInfo = new HashMap<String, DesignFormGroupDefine>();
    private Map<String, DesignFormDefine> formInfo = new HashMap<String, DesignFormDefine>();
    private Map<String, String> formGroupLink = new HashMap<String, String>();
    private Map<String, DesignDataRegion> regionInfo = new HashMap<String, DesignDataRegion>();
    private Map<String, DesignDataLinkDefine> linkInfo = new HashMap<String, DesignDataLinkDefine>();
    private Map<String, DesignDataLinkMappingDefine> linkMappingInfo = new HashMap<String, DesignDataLinkMappingDefine>();
    private Map<String, DesignFormFoldingDefine> formFoldingInfo = new HashMap<String, DesignFormFoldingDefine>();
    private String mdInfoDataTableKey = "";
    private Map<String, String> dataGroupInfo = new HashMap<String, String>();
    private Map<String, DesignDataGroup> dataGroupDefineInfo = new HashMap<String, DesignDataGroup>();
    private Map<DesignDataTable, DesignDataTable> dataTableInfo = new HashMap<DesignDataTable, DesignDataTable>();
    private Map<DesignDataTable, DesignDataTable> dataTableCopyRecordInfo = new HashMap<DesignDataTable, DesignDataTable>();
    private Map<DesignDataField, DesignDataField> dataFieldInfo = new HashMap<DesignDataField, DesignDataField>();
    private Map<DesignDataField, DesignDataField> dataFieldCopyRecordInfo = new HashMap<DesignDataField, DesignDataField>();
    private Map<DesignDataField, DesignDataField> dataFieldInfoForForm = new HashMap<DesignDataField, DesignDataField>();
    private Map<String, DesignDataField> allDataFields = new HashMap<String, DesignDataField>();
    private Map<String, DesignDataField> allDataFieldCodes = new HashMap<String, DesignDataField>();
    private Set<DesignDataField> updateDataFields = new HashSet<DesignDataField>();
    private Map<String, DesignDataTable> allDataTables = new HashMap<String, DesignDataTable>();
    private Map<String, DesignDataTable> allDataTableCodeMaps = new HashMap<String, DesignDataTable>();
    private Set<DesignDataTable> updateDataTables = new HashSet<DesignDataTable>();
    private Map<String, String> copiedDataTableInfo = new HashMap<String, String>();
    private Map<String, String> copiedDataFieldInfo = new HashMap<String, String>();
    private Set<String> exsitZbCodeSet = new HashSet<String>();
    private Map<String, Set<String>> exsitFieldCodeSet = new HashMap<String, Set<String>>();
    private Map<String, String> i18nResourceMap = new HashMap<String, String>();

    public FormCopyContext() {
    }

    public FormCopyContext(String srcFormSchemeKey, String srcTaskKey, String formSchemeKey, String taskKey, boolean matchByCode) {
        this.srcFormSchemeKey = srcFormSchemeKey;
        this.formSchemeKey = formSchemeKey;
        this.srcTaskKey = srcTaskKey;
        this.taskKey = taskKey;
        this.matchByCode = matchByCode;
    }

    public boolean isMatchByCode() {
        return this.matchByCode;
    }

    public boolean getMatchByCode() {
        return this.matchByCode;
    }

    public void setMatchByCode(boolean matchByCode) {
        this.matchByCode = matchByCode;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getSrcTaskKey() {
        return this.srcTaskKey;
    }

    public void setSrcTaskKey(String srcTaskKey) {
        this.srcTaskKey = srcTaskKey;
    }

    public String getFormSchemeTitle() {
        return this.formSchemeTitle;
    }

    public String getSrcFormSchemeKey() {
        return this.srcFormSchemeKey;
    }

    public void setCopyDataModel(boolean copyDataModel, String srcDataSchemeKey, String dataSchemeKey, String formSchemeTitle, String srcPrefix, String prefix) {
        this.copyDataModel = copyDataModel;
        this.srcDataSchemeKey = srcDataSchemeKey;
        this.dataSchemeKey = dataSchemeKey;
        this.formSchemeTitle = formSchemeTitle;
        this.prefix = null == prefix ? "" : prefix + "_";
        this.srcPrefix = null == srcPrefix ? "" : srcPrefix + "_";
    }

    public boolean isCopyDataModel() {
        return this.copyDataModel;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public String getSrcDataSchemeKey() {
        return this.srcDataSchemeKey;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getSrcPrefix() {
        return this.srcPrefix;
    }

    public String getMdInfoDataTableKey() {
        return this.mdInfoDataTableKey;
    }

    public void setMdInfoDataTableKey(String mdInfoDataTableKey) {
        this.mdInfoDataTableKey = mdInfoDataTableKey;
    }

    public Map<String, DesignFormGroupDefine> getFormGroupInfo() {
        return this.formGroupInfo;
    }

    public void addFormGroupInfo(String srcFormGroupKey, DesignFormGroupDefine newFormGroup) {
        this.formGroupInfo.put(srcFormGroupKey, newFormGroup);
    }

    public Map<String, DesignFormDefine> getFormInfo() {
        return this.formInfo;
    }

    public void addFormInfo(String srcFormKey, DesignFormDefine newform) {
        this.formInfo.put(srcFormKey, newform);
    }

    public Map<String, String> getFormGroupLink() {
        return this.formGroupLink;
    }

    public void addFormGroupLink(String newFormKey, String newFormGroupKey) {
        this.formGroupLink.put(newFormKey, newFormGroupKey);
    }

    public Map<String, DesignDataRegion> getRegionInfo() {
        return this.regionInfo;
    }

    public void addRegionInfo(String srcRegionKey, DesignDataRegion newRegion) {
        this.regionInfo.put(srcRegionKey, newRegion);
    }

    public Map<String, DesignDataLinkDefine> getLinkInfo() {
        return this.linkInfo;
    }

    public void addLinkInfo(String srcLinkKey, DesignDataLinkDefine newLink) {
        this.linkInfo.put(srcLinkKey, newLink);
    }

    public Map<String, DesignDataLinkMappingDefine> getLinkMappingInfo() {
        return this.linkMappingInfo;
    }

    public void addLinkMappingInfo(String srcLinkMappingKey, DesignDataLinkMappingDefine newLinkMapping) {
        this.linkMappingInfo.put(srcLinkMappingKey, newLinkMapping);
    }

    public boolean containsDataGroup(String paramObjKey) {
        return this.dataGroupInfo.containsKey(paramObjKey);
    }

    public String getDataGroupInfo(String paramObjKey) {
        return this.dataGroupInfo.get(paramObjKey);
    }

    public DesignDataGroup getDataGroupDefineInfo(String paramObjKey) {
        return this.dataGroupDefineInfo.get(paramObjKey);
    }

    public Map<String, DesignDataGroup> getDataGroupDefineInfos() {
        return this.dataGroupDefineInfo;
    }

    public void addDataGroupInfo(String paramObjKey, DesignDataGroup dataGroup, boolean insertGroup) {
        this.dataGroupInfo.put(paramObjKey, dataGroup.getKey());
        if (insertGroup) {
            this.dataGroupDefineInfo.put(paramObjKey, dataGroup);
        }
    }

    public void setCopiedDataTableInfo(Map<String, String> copiedDataTableInfo) {
        if (null == copiedDataTableInfo || copiedDataTableInfo.isEmpty()) {
            return;
        }
        this.copiedDataTableInfo.putAll(copiedDataTableInfo);
    }

    public String getCopiedDataTableInfo(String srcDataTableKey) {
        return this.copiedDataTableInfo.get(srcDataTableKey);
    }

    public void addDataTableInfo(DesignDataTable srcDataTable, DesignDataTable newDataTable) {
        this.dataTableInfo.put(srcDataTable, newDataTable);
        this.addDataTable(newDataTable);
        this.copiedDataTableInfo.put(srcDataTable.getKey(), newDataTable.getKey());
    }

    public void addCopiedDataTableInfo(String srcKey, String desKey) {
        this.copiedDataTableInfo.put(srcKey, desKey);
    }

    public void addCopiedDataFieldInfo(String srcKey, String desKey) {
        this.copiedDataFieldInfo.put(srcKey, desKey);
    }

    public Collection<DesignDataTable> getNewDataTables() {
        return this.dataTableInfo.values();
    }

    public void setCopiedDataFieldInfo(Map<String, String> copiedDataFieldInfo) {
        if (null == copiedDataFieldInfo || copiedDataFieldInfo.isEmpty()) {
            return;
        }
        this.copiedDataFieldInfo.putAll(copiedDataFieldInfo);
    }

    public String getCopiedDataFieldInfo(String srcDataFieldKey) {
        return this.copiedDataFieldInfo.get(srcDataFieldKey);
    }

    public void addDataFieldInfo(DesignDataField srcDataField, DesignDataField newDataField) {
        this.dataFieldInfo.put(srcDataField, newDataField);
        this.copiedDataFieldInfo.put(srcDataField.getKey(), newDataField.getKey());
        this.addFieldCode(newDataField);
        this.addDataField(newDataField);
    }

    public Map<DesignDataField, DesignDataField> getDataFieldCopyRecordInfo() {
        return this.dataFieldCopyRecordInfo;
    }

    public void addDataFieldCopyRecordInfo(DesignDataField srcDataField, DesignDataField newDataField) {
        this.dataFieldCopyRecordInfo.put(srcDataField, newDataField);
    }

    public Map<DesignDataTable, DesignDataTable> getDataTableCopyRecordInfo() {
        return this.dataTableCopyRecordInfo;
    }

    public void addDataTableCopyRecordInfo(DesignDataTable srcDataTable, DesignDataTable newDataTable) {
        this.dataTableCopyRecordInfo.put(srcDataTable, newDataTable);
    }

    public void addDataFieldInfos(Map<DesignDataField, DesignDataField> newDataFields) {
        if (null == newDataFields || newDataFields.isEmpty()) {
            return;
        }
        for (Map.Entry<DesignDataField, DesignDataField> entry : newDataFields.entrySet()) {
            this.addDataFieldInfo(entry.getKey(), entry.getValue());
        }
    }

    public Map<DesignDataField, DesignDataField> getDataFieldInfo() {
        return this.dataFieldInfo;
    }

    public Collection<DesignDataField> getNewDataFields() {
        return this.dataFieldInfo.values();
    }

    public void addDataFieldInfoForForm(DesignDataField srcDataField, DesignDataField newDataField) {
        this.dataFieldInfoForForm.put(srcDataField, newDataField);
    }

    public Map<DesignDataField, DesignDataField> getDataFieldInfoForForm() {
        return this.dataFieldInfoForForm;
    }

    public void setDataFieldInfoForForm(Map<DesignDataField, DesignDataField> dataFieldInfoForForm) {
        this.dataFieldInfoForForm = dataFieldInfoForForm;
    }

    public Collection<DesignDataField> getDataFieldInfoForForms() {
        return this.dataFieldInfoForForm.values();
    }

    public Set<String> getZbFieldCodeSet() {
        return this.exsitZbCodeSet;
    }

    public Set<String> getFieldCodeSet(String srcTableKey) {
        return this.exsitFieldCodeSet.get(srcTableKey);
    }

    private void addFieldCode(DesignDataField field) {
        if (DataFieldKind.FIELD_ZB == field.getDataFieldKind()) {
            this.addZbFieldCode(field.getCode());
        } else {
            this.addFieldCode(field.getDataTableKey(), field.getCode());
        }
    }

    private void addZbFieldCode(String zbFieldCode) {
        this.exsitZbCodeSet.add(zbFieldCode);
    }

    private void addFieldCode(String dataTableKey, String fieldCode) {
        Set<String> codeSet = this.exsitFieldCodeSet.get(dataTableKey);
        if (null != codeSet) {
            codeSet.add(fieldCode);
        } else {
            codeSet = new HashSet<String>();
            codeSet.add(fieldCode);
            this.exsitFieldCodeSet.put(dataTableKey, codeSet);
        }
    }

    public void setZbFieldCodeSet(Set<String> zbFieldCodeSet) {
        if (null == zbFieldCodeSet || zbFieldCodeSet.isEmpty()) {
            return;
        }
        this.exsitZbCodeSet.addAll(zbFieldCodeSet);
    }

    public void setFieldCodeSet(Map<String, Set<String>> fieldCodeSet) {
        if (null == fieldCodeSet || fieldCodeSet.isEmpty()) {
            return;
        }
        this.exsitFieldCodeSet.putAll(fieldCodeSet);
    }

    public DesignDataField getDataField(String dataFieldKey) {
        return this.allDataFields.get(dataFieldKey);
    }

    public DesignDataField getDataFieldByCode(String dataFieldCode) {
        return this.allDataFieldCodes.get(dataFieldCode);
    }

    public void addAllDataField(Collection<DesignDataField> dataFields) {
        if (null == dataFields || dataFields.isEmpty()) {
            return;
        }
        for (DesignDataField designDataField : dataFields) {
            this.allDataFields.put(designDataField.getKey(), designDataField);
            this.allDataFieldCodes.put(designDataField.getCode(), designDataField);
        }
    }

    private void addDataField(DesignDataField dataField) {
        if (null == dataField) {
            return;
        }
        this.allDataFields.put(dataField.getKey(), dataField);
        this.allDataFieldCodes.put(dataField.getCode(), dataField);
    }

    public Set<DesignDataField> getUpdateDataFields() {
        return this.updateDataFields;
    }

    public void addUpdateDataField(DesignDataField dataField, String oldCode) {
        this.updateDataFields.add(dataField);
        if (StringUtils.hasText((String)oldCode)) {
            Set<String> fieldCodeSet = null;
            fieldCodeSet = DataFieldKind.FIELD_ZB == dataField.getDataFieldKind() ? this.getZbFieldCodeSet() : this.getFieldCodeSet(dataField.getDataTableKey());
            fieldCodeSet.remove(oldCode);
            fieldCodeSet.add(dataField.getCode());
        }
    }

    public DesignDataTable getDataTable(String dataTableKey) {
        return this.allDataTables.get(dataTableKey);
    }

    public Map<String, DesignDataTable> getAllDataTable() {
        return this.allDataTables;
    }

    public DesignDataTable getDataTableByCode(String dataTableCode) {
        return this.allDataTableCodeMaps.get(dataTableCode);
    }

    public void addAllDataTable(Collection<DesignDataTable> dataTables) {
        if (null == dataTables || dataTables.isEmpty()) {
            return;
        }
        for (DesignDataTable designDataTable : dataTables) {
            this.allDataTables.put(designDataTable.getKey(), designDataTable);
            this.allDataTableCodeMaps.put(designDataTable.getCode(), designDataTable);
        }
    }

    private void addDataTable(DesignDataTable dataTable) {
        if (null == dataTable) {
            return;
        }
        this.allDataTables.put(dataTable.getKey(), dataTable);
        this.allDataTableCodeMaps.put(dataTable.getCode(), dataTable);
    }

    public Set<DesignDataTable> getUpdateDataTables() {
        return this.updateDataTables;
    }

    public void addUpdateDataTable(DesignDataTable dataTable) {
        this.updateDataTables.add(dataTable);
    }

    public Map<String, String> getI18nResourceMap() {
        return this.i18nResourceMap;
    }

    public Map<String, DesignFormFoldingDefine> getFormFoldingInfo() {
        return this.formFoldingInfo;
    }

    public void setFormFoldingInfo(Map<String, DesignFormFoldingDefine> formFoldingInfo) {
        this.formFoldingInfo = formFoldingInfo;
    }

    public void addFormFoldingInfo(String formFoldingKey, DesignFormFoldingDefine formFoldingDefine) {
        this.formFoldingInfo.put(formFoldingKey, formFoldingDefine);
    }
}

