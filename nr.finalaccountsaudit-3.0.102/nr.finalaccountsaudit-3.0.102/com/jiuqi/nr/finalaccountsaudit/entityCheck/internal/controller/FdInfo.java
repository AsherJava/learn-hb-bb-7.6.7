/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.internal.controller;

import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.entity.model.IEntityAttribute;

public class FdInfo {
    private String fdId;
    private String code;

    public FdInfo(FieldDefine fd) {
        this.fdId = fd.getKey();
        this.code = fd.getCode();
    }

    public FdInfo(IEntityAttribute atr) {
        this.fdId = atr.getID();
        this.code = atr.getCode();
    }

    public String getFdId() {
        return this.fdId;
    }

    public void setFdId(String fdId) {
        this.fdId = fdId;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

