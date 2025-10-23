/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 */
package com.jiuqi.nr.zb.scheme.utils.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropValueSerializer
extends JsonSerializer<Object> {
    private static final Logger log = LoggerFactory.getLogger(PropValueSerializer.class);

    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeObject(null);
        } else if (value instanceof BigDecimal) {
            gen.writeNumber((BigDecimal)value);
        } else if (value instanceof Blob) {
            log.debug("Blob\u7c7b\u578b\u4e0d\u652f\u6301");
            try (InputStream binaryStream = ((Blob)value).getBinaryStream();){
                int length;
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                while ((length = binaryStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, length);
                }
                byte[] bytes = outputStream.toByteArray();
                gen.writeUTF8String(bytes, 0, bytes.length);
            }
            catch (SQLException e) {
                log.error("\u83b7\u53d6Blob\u6570\u636e\u5f02\u5e38", e);
                gen.writeObject(null);
            }
        } else if (value instanceof byte[]) {
            log.debug("byte[]\u7c7b\u578b\u8f6c\u6587\u672c");
            byte[] bytes = (byte[])value;
            gen.writeUTF8String(bytes, 0, bytes.length);
        } else {
            gen.writeObject(value);
        }
    }
}

