/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.encrypt;

import java.util.List;

public interface VaSymmetricEncryptService {
    public List<String> doEncrypt(List<String> var1);

    public List<String> doDecrypt(List<String> var1);
}

