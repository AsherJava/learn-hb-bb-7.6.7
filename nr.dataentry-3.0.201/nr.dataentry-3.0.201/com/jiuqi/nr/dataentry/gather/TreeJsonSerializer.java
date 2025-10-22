/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 *  com.jiuqi.np.util.tree.Tree
 */
package com.jiuqi.nr.dataentry.gather;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.np.util.tree.Tree;
import com.jiuqi.nr.dataentry.bean.GatheredActions;
import com.jiuqi.nr.dataentry.gather.ActionItem;
import com.jiuqi.nr.dataentry.util.DataEntryUtil;
import java.io.IOException;
import java.util.List;

public class TreeJsonSerializer<E>
extends JsonSerializer<GatheredActions> {
    public void serialize(GatheredActions actions, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        List children;
        Tree<ActionItem> tree = actions.getNode();
        ActionItem data = (ActionItem)tree.getData();
        gen.writeStartObject();
        if (data != null) {
            DataEntryUtil.genereateActionItem(gen, data);
        }
        if ((children = tree.getChildren()) != null && children.size() != 0) {
            gen.writeFieldName("children");
            gen.writeStartArray();
            for (Tree child : children) {
                GatheredActions actionChildren = new GatheredActions();
                actionChildren.setNode((Tree<ActionItem>)child);
                gen.writeObject((Object)actionChildren);
            }
            gen.writeEndArray();
        }
        gen.writeEndObject();
    }
}

