/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 */
package com.jiuqi.nr.datascheme.api.core;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.jiuqi.nr.datascheme.api.core.INode;
import com.jiuqi.nr.datascheme.api.core.ITree;
import java.io.IOException;

public class ITreeJsonDeserializer<E extends INode>
extends JsonDeserializer<ITree<E>> {
    public ITree<E> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return null;
    }
}

