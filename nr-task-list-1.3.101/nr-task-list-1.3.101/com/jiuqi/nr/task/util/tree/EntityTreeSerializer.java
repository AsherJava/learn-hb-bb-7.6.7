/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 *  com.jiuqi.nr.common.itree.ITree
 */
package com.jiuqi.nr.task.util.tree;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.task.util.tree.EntityTree;
import java.io.IOException;

public class EntityTreeSerializer
extends JsonSerializer<EntityTree> {
    public static final String KEY = "key";
    public static final String CODE = "code";
    public static final String TITLE = "title";
    public static final String IS_LEAF = "isLeaf";
    public static final String COLOR = "color";
    public static final String ICONS = "icons";
    public static final String SELECTED = "selected";
    public static final String EXPAND = "expand";
    public static final String CHECKED = "checked";
    public static final String DATA = "data";
    public static final String NO_DRAG = "noDrag";
    public static final String NO_DROP = "noDrop";
    public static final String DISABLED = "disabled";
    public static final String CHILDREN = "children";

    public void serialize(EntityTree tree, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(KEY, tree.getKey());
        gen.writeStringField(CODE, tree.getCode());
        gen.writeStringField(TITLE, tree.getTitle());
        gen.writeBooleanField(IS_LEAF, tree.isLeaf());
        if (null != tree.getIcons()) {
            gen.writeObjectField(ICONS, tree.getIcons().length == 1 ? tree.getIcons()[0] : tree.getIcons());
        }
        if (tree.isExpanded()) {
            gen.writeBooleanField(EXPAND, tree.isExpanded());
        }
        if (tree.isSelected()) {
            gen.writeBooleanField(SELECTED, tree.isSelected());
        }
        if (tree.isChecked()) {
            gen.writeBooleanField(CHECKED, tree.isChecked());
        }
        if (tree.isNoDrag()) {
            gen.writeBooleanField(NO_DRAG, tree.isNoDrag());
        }
        if (tree.isNoDrop()) {
            gen.writeBooleanField(NO_DROP, tree.isNoDrop());
        }
        if (tree.isDisabled()) {
            gen.writeBooleanField(DISABLED, tree.isDisabled());
        }
        gen.writeObjectField(DATA, (Object)tree.getData());
        if (tree.hasChildren()) {
            gen.writeArrayFieldStart(CHILDREN);
            for (ITree child : tree.getChildren()) {
                gen.writeObject((Object)child);
            }
            gen.writeEndArray();
        } else {
            gen.writeStringField(CHILDREN, null);
        }
        gen.writeEndObject();
    }
}

