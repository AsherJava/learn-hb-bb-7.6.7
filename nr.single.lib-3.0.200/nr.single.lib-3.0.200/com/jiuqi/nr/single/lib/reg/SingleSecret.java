/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.lib.reg;

import com.jiuqi.nr.single.lib.reg.exception.SingleSecretException;
import java.util.List;

public interface SingleSecret {
    public List<String> getNewKeys() throws SingleSecretException;

    public String publicEncrypt(String var1, String var2) throws SingleSecretException;

    public String privateDecEncrypt(String var1, String var2) throws SingleSecretException;
}

