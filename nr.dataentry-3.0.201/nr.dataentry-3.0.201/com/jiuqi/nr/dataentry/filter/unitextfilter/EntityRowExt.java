/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.dataentity_ext.dto.EntityDataType
 *  com.jiuqi.nr.dataentity_ext.dto.IEntityDataDTO
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.setting.IFieldsInfo
 */
package com.jiuqi.nr.dataentry.filter.unitextfilter;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.dataentity_ext.dto.EntityDataType;
import com.jiuqi.nr.dataentity_ext.dto.IEntityDataDTO;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.setting.IFieldsInfo;

public class EntityRowExt
implements IEntityRow {
    private IEntityDataDTO iEntityDataDTO;

    public EntityRowExt(IEntityDataDTO iEntityDataDTO) {
        this.iEntityDataDTO = iEntityDataDTO;
    }

    public EntityDataType getType() {
        return this.iEntityDataDTO.getType();
    }

    public String[] getParentsEntityKeyDataPath() {
        return this.iEntityDataDTO.getPath();
    }

    public boolean isLeaf() {
        return this.iEntityDataDTO.isLeaf();
    }

    public boolean hasChildren() {
        return !this.iEntityDataDTO.isLeaf();
    }

    public IFieldsInfo getFieldsInfo() {
        return null;
    }

    public DimensionValueSet getRowKeys() {
        return null;
    }

    public AbstractData getValue(String code) throws RuntimeException {
        if (code.equals("CODE")) {
            return AbstractData.valueOf((Object)this.iEntityDataDTO.getCode(), (int)6);
        }
        if (code.equals("NAME")) {
            return AbstractData.valueOf((Object)this.iEntityDataDTO.getTitle(), (int)6);
        }
        return null;
    }

    public String getAsString(String code) throws RuntimeException {
        return null;
    }

    public AbstractData getValue(int index) throws RuntimeException {
        return null;
    }

    public String getAsString(int index) throws RuntimeException {
        return null;
    }

    public Object getAsObject(int index) throws RuntimeException {
        return null;
    }

    public String getEntityKeyData() {
        return this.iEntityDataDTO.getKey();
    }

    public String getParentEntityKey() {
        return this.iEntityDataDTO.getParent();
    }

    public Object getEntityOrder() {
        return null;
    }

    public String getIconValue() {
        return null;
    }

    public String getTitle() {
        return this.iEntityDataDTO.getTitle();
    }

    public String getCode() {
        return this.iEntityDataDTO.getCode();
    }

    public boolean isStoped() {
        return false;
    }
}

