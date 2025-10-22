/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.formtype.common;

import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.formtype.common.UnitNature;
import com.jiuqi.nr.formtype.facade.FormTypeDataDefine;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EntityUnitNatureGetter {
    private String entityId;
    private String formTypeCode;
    private String formTypeZbName;
    private Map<String, FormTypeDataDefine> formTypeDataMap;

    public EntityUnitNatureGetter(String entityId, String formTypeCode, String formTypeZbName, List<FormTypeDataDefine> formTypeDatas) {
        this.entityId = entityId;
        this.formTypeCode = formTypeCode;
        this.formTypeZbName = formTypeZbName;
        this.formTypeDataMap = null == formTypeDatas ? Collections.emptyMap() : formTypeDatas.stream().collect(Collectors.toMap(FormTypeDataDefine::getCode, v -> v));
    }

    public String getEntityId() {
        return this.entityId;
    }

    public String getFormTypeCode() {
        return this.formTypeCode;
    }

    public String getformTypeZbName() {
        return this.formTypeZbName;
    }

    public UnitNature getUnitNature(IEntityRow iEntityRow) {
        String value = iEntityRow.getAsString(this.formTypeZbName);
        FormTypeDataDefine formTypeData = this.formTypeDataMap.get(value);
        return null == formTypeData ? null : formTypeData.getUnitNatrue();
    }

    public String getIcon(IEntityRow iEntityRow) {
        String value = iEntityRow.getAsString(this.formTypeZbName);
        FormTypeDataDefine formTypeData = this.formTypeDataMap.get(value);
        return null == formTypeData ? null : formTypeData.getIcon();
    }
}

