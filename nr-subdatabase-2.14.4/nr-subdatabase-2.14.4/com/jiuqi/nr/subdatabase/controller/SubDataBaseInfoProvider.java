/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.subdatabase.controller;

import com.jiuqi.nr.subdatabase.facade.SubDataBase;

public interface SubDataBaseInfoProvider {
    public void setCurDataBase(String var1, String var2);

    public boolean isSubDataBase();

    public SubDataBase getCurDataBase();
}

