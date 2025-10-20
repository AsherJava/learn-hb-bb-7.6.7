/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.oss.encrypt;

import com.jiuqi.bi.oss.encrypt.EncryptContext;
import com.jiuqi.bi.oss.encrypt.IEncryptProvider;

public interface IEncryptFactory {
    public String typeId();

    public String title();

    public IEncryptProvider newEncryptProvider(EncryptContext var1);
}

