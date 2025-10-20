/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbrbill.dispatcher.business;

public interface ClbrBusinessHandler<T, E> {
    public String[] getSysCode();

    public String getBusinessCode();

    public T beforeHandler(Object var1);

    public E handler(T var1);

    public E afterHandler(T var1, E var2);
}

