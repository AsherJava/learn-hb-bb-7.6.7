/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.data;

import com.jiuqi.va.biz.intf.data.DataFieldType;
import com.jiuqi.va.biz.intf.data.DataTableDefine;
import com.jiuqi.va.biz.intf.value.NamedElement;
import com.jiuqi.va.biz.intf.value.ValueType;
import java.util.Map;
import java.util.UUID;

public interface DataFieldDefine
extends NamedElement {
    public DataTableDefine getTable();

    public UUID getId();

    @Override
    public String getName();

    public String getTitle();

    public DataFieldType getFieldType();

    public String getFieldName();

    public ValueType getValueType();

    public int getLength();

    public int getDigits();

    public int getRefTableType();

    public String getRefTableName();

    public boolean isNullable();

    public boolean isRequired();

    public boolean isReadonly();

    public String getShowType();

    public boolean isShowFullPath();

    public boolean isMultiChoice();

    public boolean isMultiChoiceStore();

    public String getMask();

    public String getUnitField();

    public Map<String, String> getShareFieldMapping();

    public String getSelectformat();

    public boolean isIgnoreOrgShareFiledMapping();

    public boolean isEncryptedStorage();

    public boolean isCrossOrgSelection();

    public boolean isDisZero();

    public int getSsoParamGetType();

    public boolean isQueryStop();

    public boolean isShowStop();

    public boolean isShowBackgroundColorOnView();

    public int getPenetrateType();

    public Map<String, String> getShareFieldMappingGroup();

    public int getFilterChangeOpt();

    public boolean getMaskFlag();

    public boolean isSelected();

    public boolean isInitial();
}

