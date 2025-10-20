/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.paramlanguage.entity;

public class DefaultLanguage {
    private static String defaultLanguage;

    private DefaultLanguage() {
    }

    public static final DefaultLanguage getInstance() {
        return DefaultParamLanguage.INSTANCE;
    }

    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    public void setDefaultLanguage(String defaultLanguage) {
        DefaultLanguage.defaultLanguage = defaultLanguage;
    }

    private static class DefaultParamLanguage {
        private static final DefaultLanguage INSTANCE = new DefaultLanguage();

        private DefaultParamLanguage() {
        }
    }
}

