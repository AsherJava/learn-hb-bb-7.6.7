/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.businesskey;

import com.jiuqi.nr.bpm.businesskey.MasterEntityInfo;

public interface BusinessKeyInfo {
    public String getFormSchemeKey();

    public MasterEntityInfo getMasterEntityInfo();

    public String getPeriod();

    public String getFormKey();

    public String toString();
}

