/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.businesskey;

import com.jiuqi.nr.bpm.businesskey.BusinessKeyInfo;
import com.jiuqi.nr.bpm.businesskey.MasterEntity;
import com.jiuqi.nr.bpm.businesskey.MasterEntityInfo;

public interface BusinessKey
extends BusinessKeyInfo {
    public BusinessKey setFormSchemeKey(String var1);

    public BusinessKey setMasterEntity(MasterEntityInfo var1);

    public BusinessKey setPeriod(String var1);

    public BusinessKey setFormKey(String var1);

    public MasterEntity getMasterEntity();
}

