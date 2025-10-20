/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonAutoDetect
 *  com.fasterxml.jackson.annotation.JsonAutoDetect$Visibility
 *  com.jiuqi.va.i18n.utils.VaI18nParamUtils
 */
package com.jiuqi.va.biz.impl.data;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.jiuqi.va.biz.impl.data.DataTableDefineImpl;
import com.jiuqi.va.biz.intf.data.DataFieldDefine;
import com.jiuqi.va.biz.intf.data.DataFieldType;
import com.jiuqi.va.biz.intf.value.ValueType;
import com.jiuqi.va.i18n.utils.VaI18nParamUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.context.i18n.LocaleContextHolder;

@JsonAutoDetect(getterVisibility=JsonAutoDetect.Visibility.NONE, isGetterVisibility=JsonAutoDetect.Visibility.NONE, fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class DataFieldDefineImpl
implements DataFieldDefine {
    private UUID id;
    private String name;
    private String title;
    private transient Map<String, String> titleI18nMap = new HashMap<String, String>();
    private DataFieldType fieldType;
    private String fieldName;
    private ValueType valueType;
    private int length;
    private int digits;
    private boolean nullable = true;
    private boolean required;
    private boolean readonly;
    private int refTableType;
    private String refTableName;
    private String showType;
    private boolean selected;
    private boolean maskFlag;
    private String mask;
    private String mdControl;
    private boolean billPenetrate;
    private boolean multiChoice;
    private boolean multiChoiceStore;
    private String unitField;
    private Map<String, String> shareFieldMapping;
    private boolean ignorePermission;
    private String penetrateField;
    private Map<String, Object> extBillParam;
    private int penetrateType;
    private int ssoParamGetType;
    private boolean queryStop;
    private boolean showStop;
    private boolean imagePenetrate;
    private boolean showImageFullScreen;
    private boolean showFullPath;
    private boolean solidified;
    private transient DataTableDefineImpl table;
    private transient String maskName;
    private boolean crossOrgSelection;
    private boolean ignoreOrgShareFiledMapping;
    private boolean encryptedStorage;
    private boolean isDisZero;
    private int filterChangeOpt;
    private boolean initial;
    private Map<String, String> shareFieldMappingGroup;
    private transient Map<String, Object> refTableProps;
    private String selectformat;
    private transient String columnAttr;
    private boolean showBackgroundColorOnView;

    @Override
    public boolean getMaskFlag() {
        return this.maskFlag;
    }

    public void setMaskFlag(boolean maskFlag) {
        this.maskFlag = maskFlag;
    }

    public Map<String, Object> getExtBillParam() {
        return this.extBillParam;
    }

    public void setExtBillParam(Map<String, Object> extBillParam) {
        this.extBillParam = extBillParam;
    }

    @Override
    public int getPenetrateType() {
        return this.penetrateType;
    }

    public void setPenetrateType(int penetrateType) {
        this.penetrateType = penetrateType;
    }

    @Override
    public Map<String, String> getShareFieldMappingGroup() {
        return this.shareFieldMappingGroup;
    }

    public void setShareFieldMappingGroup(Map<String, String> shareFieldMappingGroup) {
        this.shareFieldMappingGroup = shareFieldMappingGroup;
    }

    @Override
    public boolean isIgnoreOrgShareFiledMapping() {
        return this.ignoreOrgShareFiledMapping;
    }

    @Override
    public boolean isEncryptedStorage() {
        return this.encryptedStorage;
    }

    @Override
    public boolean isCrossOrgSelection() {
        return this.crossOrgSelection;
    }

    @Override
    public boolean isDisZero() {
        return this.isDisZero;
    }

    @Override
    public int getSsoParamGetType() {
        return this.ssoParamGetType;
    }

    public void setSsoParamGetType(int ssoParamGetType) {
        this.ssoParamGetType = ssoParamGetType;
    }

    @Override
    public boolean isQueryStop() {
        return this.queryStop;
    }

    public void setQueryStop(boolean queryStop) {
        this.queryStop = queryStop;
    }

    @Override
    public boolean isShowStop() {
        return this.showStop;
    }

    public void setShowStop(boolean showStop) {
        this.showStop = showStop;
    }

    @Override
    public boolean isShowBackgroundColorOnView() {
        return this.showBackgroundColorOnView;
    }

    public boolean isSolidified() {
        return this.solidified;
    }

    public void setSolidified(boolean solidified) {
        this.solidified = solidified;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    void setName(String name) {
        this.name = name;
    }

    @Override
    public String getTitle() {
        String i18nTitle = this.titleI18nMap.get(LocaleContextHolder.getLocale().toLanguageTag());
        if (VaI18nParamUtils.getTranslationEnabled().booleanValue() && i18nTitle != null) {
            return i18nTitle;
        }
        return this.title;
    }

    public String getOriginalTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, String> getTitleI18nMap() {
        return this.titleI18nMap;
    }

    public void setTitleI18nMap(Map<String, String> titleI18nMap) {
        this.titleI18nMap = titleI18nMap;
    }

    @Override
    public DataFieldType getFieldType() {
        return this.fieldType;
    }

    void setFieldType(DataFieldType fieldType) {
        this.fieldType = fieldType;
    }

    @Override
    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public ValueType getValueType() {
        return this.valueType;
    }

    void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

    @Override
    public int getLength() {
        return this.length;
    }

    void setLength(int length) {
        this.length = length;
    }

    @Override
    public int getDigits() {
        return this.digits;
    }

    void setDigits(int digits) {
        this.digits = digits;
    }

    @Override
    public boolean isNullable() {
        return this.nullable;
    }

    void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    @Override
    public boolean isRequired() {
        return this.required;
    }

    void setRequired(boolean required) {
        this.required = required;
    }

    @Override
    public boolean isReadonly() {
        return this.readonly;
    }

    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }

    @Override
    public int getRefTableType() {
        if (this.refTableType == 0 && this.refTableName != null) {
            if (this.refTableName.equals("AUTH_USER")) {
                return 3;
            }
            if (this.refTableName.equals("MD_ORG") || this.refTableName.startsWith("MD_ORG_")) {
                return 4;
            }
            if (this.refTableName.startsWith("EM")) {
                return 2;
            }
            if (this.refTableName.startsWith("MD")) {
                return 1;
            }
        }
        return this.refTableType;
    }

    public void setRefTableType(int refTableType) {
        this.refTableType = refTableType;
    }

    @Override
    public String getRefTableName() {
        return this.refTableName;
    }

    void setRefTableName(String refTableName) {
        this.refTableName = refTableName;
    }

    @Override
    public String getShowType() {
        return this.showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    @Override
    public DataTableDefineImpl getTable() {
        return this.table;
    }

    void setTable(DataTableDefineImpl table) {
        this.table = table;
    }

    public String getMdControl() {
        return this.mdControl;
    }

    public void setMdControl(String mdControl) {
        this.mdControl = mdControl;
    }

    public boolean isBillPenetrate() {
        return this.billPenetrate;
    }

    public void setBillPenetrate(boolean billPenetrate) {
        this.billPenetrate = billPenetrate;
    }

    @Override
    public boolean isMultiChoice() {
        return this.multiChoice;
    }

    public void setMultiChoice(boolean multiChoice) {
        this.multiChoice = multiChoice;
    }

    @Override
    public boolean isMultiChoiceStore() {
        return this.multiChoiceStore;
    }

    public void setMultiChoiceStore(boolean multiChoiceStore) {
        this.multiChoiceStore = multiChoiceStore;
    }

    @Override
    public String getMask() {
        return this.mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    @Override
    public String getUnitField() {
        return this.unitField;
    }

    public void setUnitField(String unitField) {
        this.unitField = unitField;
    }

    public boolean isIgnorePermission() {
        return this.ignorePermission;
    }

    public void setIgnorePermission(boolean ignorePermission) {
        this.ignorePermission = ignorePermission;
    }

    public String getPenetrateField() {
        return this.penetrateField;
    }

    public void setPenetrateField(String penetrateField) {
        this.penetrateField = penetrateField;
    }

    public boolean isImagePenetrate() {
        return this.imagePenetrate;
    }

    public void setImagePenetrate(boolean imagePenetrate) {
        this.imagePenetrate = imagePenetrate;
    }

    public boolean isShowImageFullScreen() {
        return this.showImageFullScreen;
    }

    public void setShowImageFullScreen(boolean showImageFullScreen) {
        this.showImageFullScreen = showImageFullScreen;
    }

    @Override
    public boolean isShowFullPath() {
        return this.showFullPath;
    }

    public void setShowFullPath(boolean showFullPath) {
        this.showFullPath = showFullPath;
    }

    @Override
    public Map<String, String> getShareFieldMapping() {
        return this.shareFieldMapping;
    }

    public void setShareFieldMapping(Map<String, String> shareFieldMapping) {
        this.shareFieldMapping = shareFieldMapping;
    }

    @Override
    public String getSelectformat() {
        return this.selectformat;
    }

    public void setSelectformat(String selectformat) {
        this.selectformat = selectformat;
    }

    public String getMaskName() {
        if (this.maskName == null) {
            this.maskName = "CALC_" + this.table.getTableName() + "_" + this.name + "_MASK";
        }
        return this.maskName;
    }

    public void setFilterChangeOpt(int filterChangeOpt) {
        this.filterChangeOpt = filterChangeOpt;
    }

    @Override
    public int getFilterChangeOpt() {
        return this.filterChangeOpt;
    }

    public String getColumnAttr() {
        return this.columnAttr;
    }

    public void setColumnAttr(String columnAttr) {
        this.columnAttr = columnAttr;
    }

    @Override
    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean isInitial() {
        return this.initial;
    }

    public void setInitial(boolean initial) {
        this.initial = initial;
    }
}

