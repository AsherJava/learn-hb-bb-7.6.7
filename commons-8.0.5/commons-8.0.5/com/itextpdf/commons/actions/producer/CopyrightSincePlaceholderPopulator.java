/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.actions.producer;

import com.itextpdf.commons.actions.confirmations.ConfirmedEventWrapper;
import com.itextpdf.commons.actions.producer.IPlaceholderPopulator;
import com.itextpdf.commons.utils.MessageFormatUtil;
import java.util.List;

class CopyrightSincePlaceholderPopulator
implements IPlaceholderPopulator {
    @Override
    public String populate(List<ConfirmedEventWrapper> events, String parameter) {
        if (parameter != null) {
            throw new IllegalArgumentException(MessageFormatUtil.format("Invalid usage of placeholder \"{0}\": any configuration is forbidden", "copyrightSince"));
        }
        int earliestYear = Integer.MAX_VALUE;
        for (ConfirmedEventWrapper event : events) {
            int currentYear = event.getEvent().getProductData().getSinceCopyrightYear();
            if (currentYear >= earliestYear) continue;
            earliestYear = currentYear;
        }
        return String.valueOf(earliestYear);
    }
}

