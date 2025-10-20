/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.actions.producer;

import com.itextpdf.commons.actions.producer.IPlaceholderPopulator;

abstract class AbstractFormattedPlaceholderPopulator
implements IPlaceholderPopulator {
    protected static final char APOSTROPHE = '\'';
    private static final char ESCAPE_CHARACTER = '\\';
    private static final char A_UPPERCASE = 'A';
    private static final char Z_UPPERCASE = 'Z';
    private static final char A_LOWERCASE = 'a';
    private static final char Z_LOWERCASE = 'z';

    AbstractFormattedPlaceholderPopulator() {
    }

    protected int attachQuotedString(int index, StringBuilder builder, char[] formatArray) {
        boolean isEscaped = false;
        ++index;
        while (index < formatArray.length && (formatArray[index] != '\'' || isEscaped)) {
            boolean bl = isEscaped = formatArray[index] == '\\' && !isEscaped;
            if (!isEscaped) {
                builder.append(formatArray[index]);
            }
            ++index;
        }
        if (index == formatArray.length) {
            throw new IllegalArgumentException("Pattern contains open quotation!");
        }
        return index;
    }

    protected final boolean isLetter(char ch) {
        return 'a' <= ch && 'z' >= ch || 'A' <= ch && 'Z' >= ch;
    }
}

