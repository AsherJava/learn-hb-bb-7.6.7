/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.businesskey;

import com.jiuqi.nr.bpm.businesskey.MasterEntityInfo;

public interface MasterEntitySetInfo {
    public boolean next();

    public void reset();

    public int size();

    public MasterEntityInfo getCurrent();
}

