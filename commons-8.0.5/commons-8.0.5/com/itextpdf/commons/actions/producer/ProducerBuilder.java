/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.actions.producer;

import com.itextpdf.commons.actions.AbstractITextConfigurationEvent;
import com.itextpdf.commons.actions.AbstractProductProcessITextEvent;
import com.itextpdf.commons.actions.confirmations.ConfirmedEventWrapper;
import com.itextpdf.commons.actions.processors.ITextProductEventProcessor;
import com.itextpdf.commons.actions.producer.CopyrightSincePlaceholderPopulator;
import com.itextpdf.commons.actions.producer.CopyrightToPlaceholderPopulator;
import com.itextpdf.commons.actions.producer.CurrentDatePlaceholderPopulator;
import com.itextpdf.commons.actions.producer.IPlaceholderPopulator;
import com.itextpdf.commons.actions.producer.UsedProductsPlaceholderPopulator;
import com.itextpdf.commons.utils.MessageFormatUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ProducerBuilder
extends AbstractITextConfigurationEvent {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerBuilder.class);
    private static final ProducerBuilder INSTANCE = new ProducerBuilder();
    private static final String CURRENT_DATE = "currentDate";
    private static final String USED_PRODUCTS = "usedProducts";
    private static final String COPYRIGHT_SINCE = "copyrightSince";
    private static final String COPYRIGHT_TO = "copyrightTo";
    private static final char FORMAT_DELIMITER = ':';
    private static final String MODIFIED_USING = "; modified using ";
    private static final String PATTERN_STRING = "\\$\\{([^}]*)\\}";
    private static final Pattern PATTERN = Pattern.compile("\\$\\{([^}]*)\\}");
    private static final Map<String, IPlaceholderPopulator> PLACEHOLDER_POPULATORS;

    private ProducerBuilder() {
    }

    public static String modifyProducer(List<? extends AbstractProductProcessITextEvent> events, String oldProducer) {
        ArrayList<ConfirmedEventWrapper> confirmedEvents = new ArrayList<ConfirmedEventWrapper>();
        if (events != null) {
            for (AbstractProductProcessITextEvent abstractProductProcessITextEvent : events) {
                if (abstractProductProcessITextEvent instanceof ConfirmedEventWrapper) {
                    confirmedEvents.add((ConfirmedEventWrapper)abstractProductProcessITextEvent);
                    continue;
                }
                ITextProductEventProcessor processor = INSTANCE.getActiveProcessor(abstractProductProcessITextEvent.getProductName());
                confirmedEvents.add(new ConfirmedEventWrapper(abstractProductProcessITextEvent, processor.getUsageType(), processor.getProducer()));
            }
        }
        String newProducer = ProducerBuilder.buildProducer(confirmedEvents);
        if (oldProducer == null || oldProducer.isEmpty()) {
            return newProducer;
        }
        if (oldProducer.equals(newProducer) || oldProducer.endsWith(MODIFIED_USING + newProducer)) {
            return oldProducer;
        }
        return oldProducer + MODIFIED_USING + newProducer;
    }

    @Override
    protected void doAction() {
        throw new IllegalStateException("Configuration events for util internal purposes are not expected to be sent");
    }

    private static String buildProducer(List<ConfirmedEventWrapper> events) {
        if (events == null || events.isEmpty()) {
            throw new IllegalArgumentException("No events were registered for the document!");
        }
        String producer = events.get(0).getProducerLine();
        return ProducerBuilder.populatePlaceholders(producer, events);
    }

    private static String populatePlaceholders(String producerLine, List<ConfirmedEventWrapper> events) {
        int lastIndex = 0;
        Matcher matcher = PATTERN.matcher(producerLine);
        StringBuilder builder = new StringBuilder();
        while (matcher.find()) {
            String placeholderName;
            builder.append(producerLine.substring(lastIndex, matcher.start()));
            lastIndex = matcher.end();
            String placeholder = matcher.group(1);
            int delimiterPosition = placeholder.indexOf(58);
            String parameter = null;
            if (placeholder.indexOf(58) == -1) {
                placeholderName = placeholder;
            } else {
                placeholderName = placeholder.substring(0, delimiterPosition);
                parameter = placeholder.substring(delimiterPosition + 1);
            }
            IPlaceholderPopulator populator = PLACEHOLDER_POPULATORS.get(placeholderName);
            if (populator == null) {
                LOGGER.info(MessageFormatUtil.format("Unknown placeholder {0} was ignored", placeholderName));
                continue;
            }
            builder.append(populator.populate(events, parameter));
        }
        builder.append(producerLine.substring(lastIndex));
        return builder.toString();
    }

    static {
        HashMap<String, IPlaceholderPopulator> populators = new HashMap<String, IPlaceholderPopulator>();
        populators.put(CURRENT_DATE, new CurrentDatePlaceholderPopulator());
        populators.put(USED_PRODUCTS, new UsedProductsPlaceholderPopulator());
        populators.put(COPYRIGHT_SINCE, new CopyrightSincePlaceholderPopulator());
        populators.put(COPYRIGHT_TO, new CopyrightToPlaceholderPopulator());
        PLACEHOLDER_POPULATORS = Collections.unmodifiableMap(populators);
    }
}

