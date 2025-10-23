/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 */
package com.jiuqi.nr.param.transfer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.nr.param.transfer.TransferConsts;
import java.io.IOException;
import java.util.Date;

public class DateJsonSerializer
extends JsonSerializer<Date> {
    public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String str = TransferConsts.DATE_FORMAT.get().format(value);
        gen.writeString(str);
    }
}

