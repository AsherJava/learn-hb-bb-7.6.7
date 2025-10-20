/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 */
package com.jiuqi.np.util.tree;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.np.util.tree.Tree;
import java.io.IOException;
import java.util.List;

public class TreeJsonSerializer<E>
extends JsonSerializer<Tree<E>> {
    public void serialize(Tree<E> tree, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        List<Tree<E>> children;
        gen.writeStartObject();
        E data = tree.getData();
        if (data != null) {
            gen.writeObjectField("data", data);
        }
        if ((children = tree.getChildren()) != null && children.size() != 0) {
            gen.writeFieldName("children");
            gen.writeStartArray();
            for (Tree<E> child : children) {
                gen.writeObject(child);
            }
            gen.writeEndArray();
        }
        gen.writeEndObject();
    }
}

