/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.businesskey;

import com.jiuqi.nr.bpm.businesskey.MasterEntitySetInfo;
import java.util.Set;

public interface BusinessKeySetInfo {
    public String getFormSchemeKey();

    public MasterEntitySetInfo getMasterEntitySetInfo();

    public String getPeriod();

    public Set<String> getFormKey();
}

