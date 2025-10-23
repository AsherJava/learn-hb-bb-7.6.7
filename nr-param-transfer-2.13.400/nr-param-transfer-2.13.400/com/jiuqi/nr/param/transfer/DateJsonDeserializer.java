/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 */
package com.jiuqi.nr.param.transfer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.jiuqi.nr.param.transfer.TransferConsts;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

public class DateJsonDeserializer
extends JsonDeserializer<Date> {
    public Date deserialize(JsonParser p, DeserializationContext ct) throws IOException {
        try {
            String text = p.getText();
            if (text == null) {
                return null;
            }
            return TransferConsts.DATE_FORMAT.get().parse(text);
        }
        catch (ParseException e) {
            throw new IOException(e);
        }
    }
}

