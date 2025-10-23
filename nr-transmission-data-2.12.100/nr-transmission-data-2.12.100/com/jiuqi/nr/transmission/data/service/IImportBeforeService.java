/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.service;

import com.jiuqi.nr.transmission.data.var.ITransmissionContext;

public interface IImportBeforeService {
    public Double getOrder();

    public String getTitle();

    public Object beforeImport(ITransmissionContext var1) throws Exception;
}

