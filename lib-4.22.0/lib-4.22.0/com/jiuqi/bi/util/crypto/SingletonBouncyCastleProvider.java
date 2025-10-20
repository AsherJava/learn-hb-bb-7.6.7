/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.jce.provider.BouncyCastleProvider
 */
package com.jiuqi.bi.util.crypto;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class SingletonBouncyCastleProvider {
    private static final BouncyCastleProvider provider = new BouncyCastleProvider();

    private SingletonBouncyCastleProvider() {
    }

    public static BouncyCastleProvider getProvider() {
        return provider;
    }
}

