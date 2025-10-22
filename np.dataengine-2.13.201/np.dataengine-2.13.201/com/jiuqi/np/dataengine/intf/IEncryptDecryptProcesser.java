/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.np.dataengine.intf;

import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.facade.FieldDefine;

public interface IEncryptDecryptProcesser {
    public String encrypt(QueryContext var1, FieldDefine var2, String var3);

    public String decrypt(QueryContext var1, FieldDefine var2, String var3);
}

