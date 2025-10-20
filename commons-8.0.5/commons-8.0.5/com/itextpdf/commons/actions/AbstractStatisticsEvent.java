/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.actions;

import com.itextpdf.commons.actions.AbstractProductITextEvent;
import com.itextpdf.commons.actions.AbstractStatisticsAggregator;
import com.itextpdf.commons.actions.data.ProductData;
import com.itextpdf.commons.utils.MessageFormatUtil;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractStatisticsEvent
extends AbstractProductITextEvent {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractStatisticsEvent.class);

    protected AbstractStatisticsEvent(ProductData productData) {
        super(productData);
    }

    public AbstractStatisticsAggregator createStatisticsAggregatorFromName(String statisticsName) {
        LOGGER.warn(MessageFormatUtil.format("Statistics name {0} is invalid. Cannot find corresponding statistics aggregator.", statisticsName));
        return null;
    }

    public abstract List<String> getStatisticsNames();
}

