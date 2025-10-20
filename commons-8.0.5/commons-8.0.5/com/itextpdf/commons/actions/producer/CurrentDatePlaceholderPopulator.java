/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.actions.producer;

import com.itextpdf.commons.actions.confirmations.ConfirmedEventWrapper;
import com.itextpdf.commons.actions.producer.AbstractFormattedPlaceholderPopulator;
import com.itextpdf.commons.utils.DateTimeUtil;
import com.itextpdf.commons.utils.MessageFormatUtil;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class CurrentDatePlaceholderPopulator
extends AbstractFormattedPlaceholderPopulator {
    private static final Set<String> ALLOWED_PATTERNS = new HashSet<String>(Arrays.asList("dd", "MM", "MMM", "MMMM", "yy", "yyyy", "ss", "mm", "HH"));

    @Override
    public String populate(List<ConfirmedEventWrapper> events, String parameter) {
        if (parameter == null) {
            throw new IllegalArgumentException(MessageFormatUtil.format("Invalid usage of placeholder \"{0}\": format is required", "currentDate"));
        }
        Date now = DateTimeUtil.getCurrentTimeDate();
        return this.formatDate(now, parameter);
    }

    private String formatDate(Date date, String format) {
        StringBuilder builder = new StringBuilder();
        char[] formatArray = format.toCharArray();
        for (int i = 0; i < formatArray.length; ++i) {
            if (formatArray[i] == '\'') {
                i = this.attachQuotedString(i, builder, formatArray);
                continue;
            }
            if (this.isLetter(formatArray[i])) {
                i = this.processDateComponent(i, date, builder, formatArray);
                continue;
            }
            builder.append(formatArray[i]);
        }
        return builder.toString();
    }

    private int processDateComponent(int index, Date date, StringBuilder builder, char[] formatArray) {
        StringBuilder peaceBuilder = new StringBuilder();
        char currentChar = formatArray[index];
        peaceBuilder.append(currentChar);
        while (index + 1 < formatArray.length && currentChar == formatArray[index + 1]) {
            peaceBuilder.append(formatArray[++index]);
        }
        String piece = peaceBuilder.toString();
        if (!ALLOWED_PATTERNS.contains(piece)) {
            throw new IllegalArgumentException(MessageFormatUtil.format("Pattern contains unexpected component {0}", piece));
        }
        builder.append(DateTimeUtil.format(date, piece));
        return index;
    }
}

