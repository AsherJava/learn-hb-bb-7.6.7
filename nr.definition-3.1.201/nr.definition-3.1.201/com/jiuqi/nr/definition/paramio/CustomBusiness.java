/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IMetaItem
 */
package com.jiuqi.nr.definition.paramio;

import com.jiuqi.np.definition.facade.IMetaItem;

public interface CustomBusiness {
    public boolean checkBusiness(String var1, String var2);

    public IMetaItem getData(String var1);

    public byte[] exportData(String var1, String var2);

    public void importData(String var1, String var2, byte[] var3);
}

