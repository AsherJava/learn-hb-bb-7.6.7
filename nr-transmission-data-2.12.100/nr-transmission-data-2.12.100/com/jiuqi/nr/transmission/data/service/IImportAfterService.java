/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.service;

import com.jiuqi.nr.transmission.data.var.ITransmissionContext;

public interface IImportAfterService {
    public Double getOrder();

    public String getTitle();

    public Object afterImport(ITransmissionContext var1) throws Exception;
}

