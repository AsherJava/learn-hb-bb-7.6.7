/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.utils;

public class DIContainerConfigurations {
    private static final String[] DEFAULT_CONFIGURATIONS_CLASS = new String[]{"com.itextpdf.forms.util.RegisterDefaultDiContainer"};

    private DIContainerConfigurations() {
    }

    public static void loadDefaultConfigurations() {
        for (String defaultConfigurationClass : DEFAULT_CONFIGURATIONS_CLASS) {
            try {
                Class.forName(defaultConfigurationClass);
            }
            catch (ClassNotFoundException classNotFoundException) {
                // empty catch block
            }
        }
    }
}

