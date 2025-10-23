/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 */
package com.jiuqi.nr.param.transfer.nvwaMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class NvwaMappingUtils {
    public static Object inputStreamToData(InputStream inputStreamData, ObjectMapper objectMapper, Class<?> clazz) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = inputStreamData.read(buffer, 0, 1024)) != -1) {
            bos.write(buffer, 0, len);
        }
        byte[] byteArray = bos.toByteArray();
        Object result = objectMapper.readValue(byteArray, clazz);
        return result;
    }
}

