/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.utils;

import java.text.MessageFormat;
import java.util.Locale;

public final class MessageFormatUtil {
    private MessageFormatUtil() {
    }

    public static String format(String pattern, Object ... arguments) {
        return new MessageFormat(pattern.replace("'", "''").replace("{{{", "'{'{").replace("}}}", "}'}'").replace("{{", "'{'").replace("}}", "'}'"), Locale.ROOT).format(arguments);
    }
}

