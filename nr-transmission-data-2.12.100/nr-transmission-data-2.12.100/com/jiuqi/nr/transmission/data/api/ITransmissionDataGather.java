/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.api;

import com.jiuqi.nr.transmission.data.intf.DataImportResult;
import com.jiuqi.nr.transmission.data.var.ITransmissionContext;
import java.io.InputStream;
import java.io.OutputStream;

public interface ITransmissionDataGather {
    public int getOrder();

    public String getCode();

    public String getTitle();

    public DataImportResult dataImport(InputStream var1, ITransmissionContext var2) throws Exception;

    public void dataExport(OutputStream var1, ITransmissionContext var2) throws Exception;
}

