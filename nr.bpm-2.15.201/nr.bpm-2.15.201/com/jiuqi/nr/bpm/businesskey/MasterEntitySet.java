/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.businesskey;

import com.jiuqi.nr.bpm.businesskey.MasterEntityInfo;
import com.jiuqi.nr.bpm.businesskey.MasterEntitySetInfo;

public interface MasterEntitySet
extends MasterEntitySetInfo {
    public MasterEntitySet setMasterEntityDimessionValue(String var1, String var2);

    public MasterEntitySet addMasterEntity(MasterEntityInfo var1);

    public void removeCurrent();
}

