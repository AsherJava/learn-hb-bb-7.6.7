/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.oss.encrypt;

import com.jiuqi.bi.oss.encrypt.AESEncryptProvider;
import com.jiuqi.bi.oss.encrypt.EncryptContext;
import com.jiuqi.bi.oss.encrypt.IEncryptFactory;
import com.jiuqi.bi.oss.encrypt.IEncryptProvider;

public class AESEncryptFactory
implements IEncryptFactory {
    public static final String TYPE_ID = "TYPE_AES";

    @Override
    public String typeId() {
        return TYPE_ID;
    }

    @Override
    public String title() {
        return "AES";
    }

    @Override
    public IEncryptProvider newEncryptProvider(EncryptContext context) {
        return new AESEncryptProvider();
    }
}

