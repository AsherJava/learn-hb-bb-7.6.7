/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.proxy;

public class ObjectArrayUtils {
    public static boolean isFirstArgString(Object[] args) {
        return args != null && args.length >= 1 && args[0] instanceof String;
    }
}

