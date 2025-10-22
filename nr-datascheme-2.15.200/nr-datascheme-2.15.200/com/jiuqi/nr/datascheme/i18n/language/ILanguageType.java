/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.i18n.language;

public interface ILanguageType {
    public String getKey();

    default public int getValue() {
        return Integer.valueOf(this.getKey());
    }

    public String getLanguage();

    public boolean isDefault();

    public String getMessage();
}

