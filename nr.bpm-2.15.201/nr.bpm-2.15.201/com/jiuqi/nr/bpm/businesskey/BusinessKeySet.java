/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.businesskey;

import com.jiuqi.nr.bpm.businesskey.BusinessKeySetInfo;
import com.jiuqi.nr.bpm.businesskey.MasterEntitySet;
import com.jiuqi.nr.bpm.businesskey.MasterEntitySetInfo;
import java.util.Set;

public interface BusinessKeySet
extends BusinessKeySetInfo {
    public BusinessKeySet setFormSchemeKey(String var1);

    public BusinessKeySet setMasterEntitySet(MasterEntitySetInfo var1);

    public BusinessKeySet setPeriod(String var1);

    public BusinessKeySet setFormKey(Set<String> var1);

    public MasterEntitySet getMasterEntitySet();
}

