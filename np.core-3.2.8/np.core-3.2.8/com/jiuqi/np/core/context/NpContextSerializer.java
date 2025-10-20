/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 */
package com.jiuqi.np.core.context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.core.context.NpContext;

public class NpContextSerializer {
    public static String serialize(NpContext context) {
        ObjectMapper om = new ObjectMapper();
        try {
            return om.writeValueAsString((Object)context);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException("failed to serialize context.", e);
        }
    }

    public static NpContext deserialize(String contextText) {
        ObjectMapper om = new ObjectMapper();
        try {
            return (NpContext)om.readValue(contextText, NpContext.class);
        }
        catch (Exception e) {
            throw new RuntimeException("failed to deserialize context.", e);
        }
    }
}

