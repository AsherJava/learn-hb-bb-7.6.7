/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.actions.producer;

import com.itextpdf.commons.actions.confirmations.ConfirmedEventWrapper;
import com.itextpdf.commons.actions.producer.IPlaceholderPopulator;
import com.itextpdf.commons.utils.MessageFormatUtil;
import java.util.List;

class CopyrightToPlaceholderPopulator
implements IPlaceholderPopulator {
    @Override
    public String populate(List<ConfirmedEventWrapper> events, String parameter) {
        if (parameter != null) {
            throw new IllegalArgumentException(MessageFormatUtil.format("Invalid usage of placeholder \"{0}\": any configuration is forbidden", "copyrightTo"));
        }
        int latestYear = Integer.MIN_VALUE;
        for (ConfirmedEventWrapper event : events) {
            int currentYear = event.getEvent().getProductData().getToCopyrightYear();
            if (currentYear <= latestYear) continue;
            latestYear = currentYear;
        }
        return String.valueOf(latestYear);
    }
}

