/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.paramlanguage.entity;

import java.util.Map;

public class LanguageMessage {
    private static Map<String, String> languageMessage;

    private LanguageMessage() {
    }

    public static final LanguageMessage getInstance() {
        return ParamLanguageMessage.INSTANCE;
    }

    public Map<String, String> getLanguageMessage() {
        return languageMessage;
    }

    public void setLanguageMessage(Map<String, String> languageMessage) {
        LanguageMessage.languageMessage = languageMessage;
    }

    private static class ParamLanguageMessage {
        private static final LanguageMessage INSTANCE = new LanguageMessage();

        private ParamLanguageMessage() {
        }
    }
}

