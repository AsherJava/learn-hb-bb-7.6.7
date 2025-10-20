/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.actions;

import com.itextpdf.commons.actions.AbstractContextBasedEventHandler;
import com.itextpdf.commons.actions.AbstractContextBasedITextEvent;
import com.itextpdf.commons.actions.AbstractProductProcessITextEvent;
import com.itextpdf.commons.actions.ProductNameConstant;
import com.itextpdf.commons.actions.ProductProcessorFactoryKeeper;
import com.itextpdf.commons.actions.confirmations.ConfirmEvent;
import com.itextpdf.commons.actions.confirmations.ConfirmedEventWrapper;
import com.itextpdf.commons.actions.contexts.UnknownContext;
import com.itextpdf.commons.actions.processors.ITextProductEventProcessor;
import com.itextpdf.commons.actions.sequence.SequenceId;
import com.itextpdf.commons.exceptions.ProductEventHandlerRepeatException;
import com.itextpdf.commons.exceptions.UnknownProductException;
import com.itextpdf.commons.utils.MessageFormatUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class ProductEventHandler
extends AbstractContextBasedEventHandler {
    static final ProductEventHandler INSTANCE = new ProductEventHandler();
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductEventHandler.class);
    private static final int MAX_EVENT_RETRY_COUNT = 4;
    private final ConcurrentHashMap<String, ITextProductEventProcessor> processors = new ConcurrentHashMap();
    private final WeakHashMap<SequenceId, List<AbstractProductProcessITextEvent>> events = new WeakHashMap();

    private ProductEventHandler() {
        super(UnknownContext.PERMISSIVE);
    }

    @Override
    protected void onAcceptedEvent(AbstractContextBasedITextEvent event) {
        for (int i = 0; i < 4; ++i) {
            try {
                this.tryProcessEvent(event);
                return;
            }
            catch (ProductEventHandlerRepeatException productEventHandlerRepeatException) {
                continue;
            }
        }
        this.tryProcessEvent(event);
    }

    ITextProductEventProcessor addProcessor(ITextProductEventProcessor processor) {
        return this.processors.put(processor.getProductName(), processor);
    }

    ITextProductEventProcessor removeProcessor(String productName) {
        return this.processors.remove(productName);
    }

    ITextProductEventProcessor getActiveProcessor(String productName) {
        ITextProductEventProcessor processor = this.processors.get(productName);
        if (processor != null) {
            return processor;
        }
        if (ProductNameConstant.PRODUCT_NAMES.contains(productName)) {
            processor = ProductProcessorFactoryKeeper.getProductProcessorFactory().createProcessor(productName);
            this.processors.put(productName, processor);
            return processor;
        }
        return null;
    }

    Map<String, ITextProductEventProcessor> getProcessors() {
        return Collections.unmodifiableMap(new HashMap<String, ITextProductEventProcessor>(this.processors));
    }

    void clearProcessors() {
        this.processors.clear();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    List<AbstractProductProcessITextEvent> getEvents(SequenceId id) {
        WeakHashMap<SequenceId, List<AbstractProductProcessITextEvent>> weakHashMap = this.events;
        synchronized (weakHashMap) {
            List<AbstractProductProcessITextEvent> listOfEvents = this.events.get(id);
            if (listOfEvents == null) {
                return Collections.emptyList();
            }
            return Collections.unmodifiableList(new ArrayList<AbstractProductProcessITextEvent>(listOfEvents));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void addEvent(SequenceId id, AbstractProductProcessITextEvent event) {
        WeakHashMap<SequenceId, List<AbstractProductProcessITextEvent>> weakHashMap = this.events;
        synchronized (weakHashMap) {
            List<AbstractProductProcessITextEvent> listOfEvents = this.events.get(id);
            if (listOfEvents == null) {
                listOfEvents = new ArrayList<AbstractProductProcessITextEvent>();
                this.events.put(id, listOfEvents);
            }
            listOfEvents.add(event);
        }
    }

    private void tryProcessEvent(AbstractContextBasedITextEvent event) {
        if (!(event instanceof AbstractProductProcessITextEvent)) {
            return;
        }
        AbstractProductProcessITextEvent productEvent = (AbstractProductProcessITextEvent)event;
        String productName = productEvent.getProductName();
        ITextProductEventProcessor productEventProcessor = this.getActiveProcessor(productName);
        if (productEventProcessor == null) {
            throw new UnknownProductException(MessageFormatUtil.format("Product {0} is unknown. Probably you have to register it.", productName));
        }
        productEventProcessor.onEvent(productEvent);
        if (productEvent.getSequenceId() != null) {
            if (productEvent instanceof ConfirmEvent) {
                this.wrapConfirmedEvent((ConfirmEvent)productEvent, productEventProcessor);
            } else {
                this.addEvent(productEvent.getSequenceId(), productEvent);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void wrapConfirmedEvent(ConfirmEvent event, ITextProductEventProcessor productEventProcessor) {
        WeakHashMap<SequenceId, List<AbstractProductProcessITextEvent>> weakHashMap = this.events;
        synchronized (weakHashMap) {
            List<AbstractProductProcessITextEvent> eventsList = this.events.get(event.getSequenceId());
            AbstractProductProcessITextEvent confirmedEvent = event.getConfirmedEvent();
            int indexOfReportedEvent = eventsList.indexOf(confirmedEvent);
            if (indexOfReportedEvent >= 0) {
                eventsList.set(indexOfReportedEvent, new ConfirmedEventWrapper(confirmedEvent, productEventProcessor.getUsageType(), productEventProcessor.getProducer()));
            } else {
                LOGGER.warn(MessageFormatUtil.format("Event for the product {0} with type {1} attempted to be confirmed but it had not been reported yet. Probably appropriate process fail", confirmedEvent.getProductName(), confirmedEvent.getEventType()));
            }
        }
    }
}

