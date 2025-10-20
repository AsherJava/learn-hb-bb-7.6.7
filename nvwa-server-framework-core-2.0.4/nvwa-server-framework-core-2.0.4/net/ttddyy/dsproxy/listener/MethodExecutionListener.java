/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.listener;

import net.ttddyy.dsproxy.listener.MethodExecutionContext;

public interface MethodExecutionListener {
    default public void beforeMethod(MethodExecutionContext executionContext) {
    }

    default public void afterMethod(MethodExecutionContext executionContext) {
    }
}

