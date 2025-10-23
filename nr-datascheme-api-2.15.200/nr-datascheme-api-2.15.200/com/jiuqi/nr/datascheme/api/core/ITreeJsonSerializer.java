/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 */
package com.jiuqi.nr.datascheme.api.core;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.nr.datascheme.api.core.INode;
import com.jiuqi.nr.datascheme.api.core.ITree;
import java.io.IOException;

public class ITreeJsonSerializer<E extends INode>
extends JsonSerializer<ITree<E>> {
    public void serialize(ITree<E> tree, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("key", tree.getKey());
        gen.writeStringField("code", tree.getCode());
        gen.writeStringField("title", tree.getTitle());
        gen.writeObjectField("icons", (Object)tree.getIcons());
        gen.writeNumberField("childCount", tree.getChildCount());
        gen.writeBooleanField("isLeaf", tree.isLeaf());
        gen.writeBooleanField("expanded", tree.isExpanded());
        gen.writeBooleanField("checked", tree.isChecked());
        gen.writeBooleanField("selected", tree.isSelected());
        gen.writeBooleanField("noDrag", tree.isNoDrag());
        gen.writeBooleanField("noDrop", tree.isNoDrop());
        gen.writeBooleanField("disabled", tree.isDisabled());
        gen.writeObjectField("data", tree.getData());
        gen.writeArrayFieldStart("children");
        if (tree.hasChildren()) {
            for (ITree<E> child : tree.getChildren()) {
                gen.writeObject(child);
            }
        }
        gen.writeEndArray();
        gen.writeEndObject();
    }
}

