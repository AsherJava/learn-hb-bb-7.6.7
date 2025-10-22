/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.businesskey;

import java.util.Collection;

public interface MasterEntityInfo {
    public Collection<String> getDimessionNames();

    public String getMasterEntityKey(String var1);

    public int dimessionSize();
}

