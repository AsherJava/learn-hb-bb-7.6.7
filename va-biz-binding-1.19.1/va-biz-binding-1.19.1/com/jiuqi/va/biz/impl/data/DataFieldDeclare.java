/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.data;

import com.jiuqi.va.biz.impl.data.DataFieldDefineImpl;
import com.jiuqi.va.biz.intf.data.DataFieldDefine;
import com.jiuqi.va.biz.intf.data.DataFieldType;
import com.jiuqi.va.biz.intf.data.DataTableDefine;
import com.jiuqi.va.biz.intf.model.Declare;
import com.jiuqi.va.biz.intf.model.DeclareHost;
import com.jiuqi.va.biz.intf.value.ValueType;
import java.util.Map;
import java.util.UUID;

public class DataFieldDeclare<T extends DeclareHost<? super DataFieldDefineImpl>>
implements DataFieldDefine,
Declare<DataFieldDefineImpl, T> {
    private T host;
    private DataFieldDefineImpl impl;

    public DataFieldDeclare(T host) {
        this.host = host;
        this.impl = new DataFieldDefineImpl();
        this.impl.setId(UUID.randomUUID());
    }

    @Override
    public T endDeclare() {
        if (this.host != null && this.impl != null) {
            this.host.accept((DataFieldDefineImpl)this.impl);
            this.host = null;
            this.impl = null;
        }
        return this.host;
    }

    @Override
    public UUID getId() {
        return this.impl.getId();
    }

    public DataFieldDeclare<T> setId(UUID id) {
        this.impl.setId(id);
        return this;
    }

    @Override
    public String getName() {
        return this.impl.getName();
    }

    public DataFieldDeclare<T> setName(String name) {
        this.impl.setName(name);
        return this;
    }

    @Override
    public String getTitle() {
        return this.impl.getTitle();
    }

    public DataFieldDeclare<T> setTitle(String title) {
        this.impl.setTitle(title);
        return this;
    }

    public boolean isSolidified() {
        return this.impl.isSolidified();
    }

    public DataFieldDeclare<T> setSolidified(boolean solidified) {
        this.impl.setSolidified(solidified);
        return this;
    }

    @Override
    public DataFieldType getFieldType() {
        return this.impl.getFieldType();
    }

    public DataFieldDeclare<T> setFieldType(DataFieldType fieldType) {
        this.impl.setFieldType(fieldType);
        return this;
    }

    @Override
    public String getFieldName() {
        return this.impl.getFieldName();
    }

    public DataFieldDeclare<T> setFieldName(String fieldName) {
        this.impl.setFieldName(fieldName);
        return this;
    }

    @Override
    public ValueType getValueType() {
        return this.impl.getValueType();
    }

    public DataFieldDeclare<T> setValueType(ValueType valueType) {
        this.impl.setValueType(valueType);
        return this;
    }

    @Override
    public int getLength() {
        return this.impl.getLength();
    }

    public DataFieldDeclare<T> setLength(int length) {
        this.impl.setLength(length);
        return this;
    }

    @Override
    public int getDigits() {
        return this.impl.getDigits();
    }

    public DataFieldDeclare<T> setDigits(int digits) {
        this.impl.setDigits(digits);
        return this;
    }

    @Override
    public boolean isNullable() {
        return this.impl.isNullable();
    }

    public DataFieldDeclare<T> setNullable(boolean nullable) {
        this.impl.setNullable(nullable);
        return this;
    }

    @Override
    public boolean isRequired() {
        return this.impl.isRequired();
    }

    public DataFieldDeclare<T> setRequired(boolean required) {
        this.impl.setRequired(required);
        return this;
    }

    @Override
    public boolean isReadonly() {
        return this.impl.isReadonly();
    }

    public DataFieldDeclare<T> setReadonly(boolean readonly) {
        this.impl.setReadonly(readonly);
        return this;
    }

    @Override
    public int getRefTableType() {
        return this.impl.getRefTableType();
    }

    public DataFieldDeclare<T> setRefTableType(int refTableType) {
        this.impl.setRefTableType(refTableType);
        return this;
    }

    @Override
    public String getRefTableName() {
        return this.impl.getRefTableName();
    }

    public DataFieldDeclare<T> setRefTableName(String refTableName) {
        this.impl.setRefTableName(refTableName);
        return this;
    }

    @Override
    public String getShowType() {
        return this.impl.getShowType();
    }

    public DataFieldDeclare<T> setShowType(String showType) {
        this.impl.setShowType(showType);
        return this;
    }

    @Override
    public boolean isShowFullPath() {
        return this.impl.isShowFullPath();
    }

    public DataFieldDeclare<T> setShowFullPath(boolean showFullPath) {
        this.impl.setShowFullPath(showFullPath);
        return this;
    }

    @Override
    public DataTableDefine getTable() {
        return this.impl.getTable();
    }

    @Override
    public boolean isMultiChoice() {
        return this.impl.isMultiChoice();
    }

    @Override
    public boolean isMultiChoiceStore() {
        return this.impl.isMultiChoiceStore();
    }

    @Override
    public String getMask() {
        return this.impl.getMask();
    }

    @Override
    public String getUnitField() {
        return this.impl.getUnitField();
    }

    @Override
    public Map<String, String> getShareFieldMapping() {
        return this.impl.getShareFieldMapping();
    }

    @Override
    public String getSelectformat() {
        return this.impl.getSelectformat();
    }

    @Override
    public boolean isIgnoreOrgShareFiledMapping() {
        return this.impl.isIgnoreOrgShareFiledMapping();
    }

    @Override
    public boolean isEncryptedStorage() {
        return this.impl.isEncryptedStorage();
    }

    @Override
    public boolean isCrossOrgSelection() {
        return this.impl.isCrossOrgSelection();
    }

    @Override
    public boolean isDisZero() {
        return this.impl.isDisZero();
    }

    @Override
    public int getSsoParamGetType() {
        return this.impl.getSsoParamGetType();
    }

    @Override
    public boolean isQueryStop() {
        return this.impl.isQueryStop();
    }

    @Override
    public boolean isShowStop() {
        return this.impl.isShowStop();
    }

    @Override
    public boolean isShowBackgroundColorOnView() {
        return this.impl.isShowBackgroundColorOnView();
    }

    @Override
    public int getPenetrateType() {
        return this.impl.getPenetrateType();
    }

    @Override
    public Map<String, String> getShareFieldMappingGroup() {
        return this.impl.getShareFieldMappingGroup();
    }

    @Override
    public int getFilterChangeOpt() {
        return this.impl.getFilterChangeOpt();
    }

    @Override
    public boolean getMaskFlag() {
        return this.impl.getMaskFlag();
    }

    @Override
    public boolean isSelected() {
        return this.impl.isSelected();
    }

    @Override
    public boolean isInitial() {
        return this.impl.isInitial();
    }
}

