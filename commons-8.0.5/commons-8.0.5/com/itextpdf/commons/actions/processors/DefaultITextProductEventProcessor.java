/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.actions.processors;

import com.itextpdf.commons.actions.AbstractProductProcessITextEvent;
import com.itextpdf.commons.actions.confirmations.ConfirmEvent;
import com.itextpdf.commons.actions.processors.AbstractITextProductEventProcessor;
import com.itextpdf.commons.utils.Base64;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultITextProductEventProcessor
extends AbstractITextProductEventProcessor {
    static final byte[] MESSAGE_FOR_LOGGING = Base64.decode("WW91IGFyZSB1c2luZyBpVGV4dCB1bmRlciB0aGUgQUdQTC4KCklmIHRoaXMgaXMgeW91ciBpbnRlbnRpb24sIHlvdSBoYXZlIHB1Ymxpc2hlZCB5b3VyIG93biBzb3VyY2UgY29kZSBhcyBBR1BMIHNvZnR3YXJlIHRvby4KUGxlYXNlIGxldCB1cyBrbm93IHdoZXJlIHRvIGZpbmQgeW91ciBzb3VyY2UgY29kZSBieSBzZW5kaW5nIGEgbWFpbCB0byBhZ3BsQGFwcnlzZS5jb20KV2UnZCBiZSBob25vcmVkIHRvIGFkZCBpdCB0byBvdXIgbGlzdCBvZiBBR1BMIHByb2plY3RzIGJ1aWx0IG9uIHRvcCBvZiBpVGV4dAphbmQgd2UnbGwgZXhwbGFpbiBob3cgdG8gcmVtb3ZlIHRoaXMgbWVzc2FnZSBmcm9tIHlvdXIgZXJyb3IgbG9ncy4KCklmIHRoaXMgd2Fzbid0IHlvdXIgaW50ZW50aW9uLCB5b3UgYXJlIHByb2JhYmx5IHVzaW5nIGlUZXh0IGluIGEgbm9uLWZyZWUgZW52aXJvbm1lbnQuCkluIHRoaXMgY2FzZSwgcGxlYXNlIGNvbnRhY3QgdXMgYnkgZmlsbGluZyBvdXQgdGhpcyBmb3JtOiBodHRwOi8vaXRleHRwZGYuY29tL3NhbGVzCklmIHlvdSBhcmUgYSBjdXN0b21lciwgd2UnbGwgZXhwbGFpbiBob3cgdG8gaW5zdGFsbCB5b3VyIGxpY2Vuc2Uga2V5IHRvIGF2b2lkIHRoaXMgbWVzc2FnZS4KSWYgeW91J3JlIG5vdCBhIGN1c3RvbWVyLCB3ZSdsbCBleHBsYWluIHRoZSBiZW5lZml0cyBvZiBiZWNvbWluZyBhIGN1c3RvbWVyLg==");
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultITextProductEventProcessor.class);
    private static final long[] REPEAT = new long[]{10000L, 5000L, 1000L};
    private static final int MAX_LVL = REPEAT.length - 1;
    private final Object lock = new Object();
    private final AtomicLong counter = new AtomicLong(0L);
    private final AtomicLong level = new AtomicLong(0L);
    private final AtomicLong repeatLevel = new AtomicLong(this.acquireRepeatLevel((int)this.level.get()));

    public DefaultITextProductEventProcessor(String productName) {
        super(productName);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void onEvent(AbstractProductProcessITextEvent event) {
        if (!(event instanceof ConfirmEvent)) {
            return;
        }
        boolean isNeededToLogMessage = false;
        Object object = this.lock;
        synchronized (object) {
            if (this.counter.incrementAndGet() > this.repeatLevel.get()) {
                this.counter.set(0L);
                if (this.level.incrementAndGet() > (long)MAX_LVL) {
                    this.level.set(MAX_LVL);
                }
                this.repeatLevel.set(this.acquireRepeatLevel((int)this.level.get()));
                isNeededToLogMessage = true;
            }
        }
        if (isNeededToLogMessage) {
            String message = new String(MESSAGE_FOR_LOGGING, StandardCharsets.ISO_8859_1);
            LOGGER.info(message);
            System.out.println(message);
        }
    }

    @Override
    public String getUsageType() {
        return "AGPL";
    }

    long acquireRepeatLevel(int lvl) {
        return REPEAT[lvl];
    }
}

