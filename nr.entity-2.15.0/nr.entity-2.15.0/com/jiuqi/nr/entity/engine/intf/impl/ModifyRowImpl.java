/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.entity.engine.intf.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.entity.engine.intf.IModifyRow;
import com.jiuqi.nr.entity.engine.intf.impl.EntityRowImpl;
import com.jiuqi.nr.entity.engine.intf.impl.ModifyTableImpl;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;

public class ModifyRowImpl
extends EntityRowImpl
implements IModifyRow {
    private String tempId;
    private boolean needSync;
    private boolean ignoreCodeCheck;

    public ModifyRowImpl(ModifyTableImpl modifyTable, DimensionValueSet rowKeys, HashMap<String, Object> modifyData) {
        super(modifyTable, rowKeys, 0);
        this.modifiedDatas = modifyData;
    }

    @Override
    public void setValue(String code, Object value) {
        super.setValue(code, value);
    }

    public Object getModifyValue(String code) {
        return this.modifiedDatas.get(code.toLowerCase(Locale.ROOT));
    }

    @Override
    public DimensionValueSet getRowKeys() {
        return this.rowKeys;
    }

    @Override
    public String buildRow() {
        this.tempId = UUID.randomUUID().toString();
        return this.tempId;
    }

    public String getTempId() {
        return this.tempId;
    }

    public void setTempId(String tempId) {
        this.tempId = tempId;
    }

    public boolean isNeedSync() {
        return this.needSync;
    }

    @Override
    public void setNeedSync(boolean needSync) {
        this.needSync = needSync;
    }

    public boolean isIgnoreCodeCheck() {
        return this.ignoreCodeCheck;
    }

    @Override
    public void setIgnoreCodeCheck(boolean ignoreCodeCheck) {
        this.ignoreCodeCheck = ignoreCodeCheck;
    }
}

