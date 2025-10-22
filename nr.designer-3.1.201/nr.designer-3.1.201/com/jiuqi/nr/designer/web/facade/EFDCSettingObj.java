/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.internal.impl.MouldDefineImpl
 */
package com.jiuqi.nr.designer.web.facade;

import com.jiuqi.nr.definition.internal.impl.MouldDefineImpl;
import java.util.List;

public class EFDCSettingObj {
    private String key;
    private int type;
    private List<MouldDefineImpl> mouldData;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<MouldDefineImpl> getMouldData() {
        return this.mouldData;
    }

    public void setMouldData(List<MouldDefineImpl> mouldData) {
        this.mouldData = mouldData;
    }
}

