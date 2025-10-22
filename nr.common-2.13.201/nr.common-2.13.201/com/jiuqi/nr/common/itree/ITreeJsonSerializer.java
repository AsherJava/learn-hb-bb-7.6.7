/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.core.JsonGenerator$Feature
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 */
package com.jiuqi.nr.common.itree;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import java.io.IOException;

public class ITreeJsonSerializer<E extends INode>
extends JsonSerializer<ITree<E>> {
    public static final String N_KEY = "key";
    public static final String N_CODE = "code";
    public static final String N_TITLE = "title";
    public static final String N_ISLEAF = "isLeaf";
    public static final String N_COLOR = "color";
    public static final String N_ICONS = "icons";
    public static final String N_SELECTED = "selected";
    public static final String N_EXPANDED = "expanded";
    public static final String N_CHECKED = "checked";
    public static final String N_DATA = "data";
    public static final String N_NODRAG = "noDrag";
    public static final String N_NODROP = "noDrop";
    public static final String N_DISABLED = "disabled";
    public static final String N_CHILDREN = "children";

    public void serialize(ITree<E> tree, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.configure(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM, false);
        gen.writeStartObject();
        gen.writeStringField(N_KEY, tree.getKey());
        gen.writeStringField(N_CODE, tree.getCode());
        gen.writeStringField(N_TITLE, tree.getTitle());
        gen.writeBooleanField(N_ISLEAF, tree.isLeaf());
        if (null != tree.getColor()) {
            gen.writeStringField(N_COLOR, tree.getColor());
        }
        if (null != tree.getIcons()) {
            gen.writeObjectField(N_ICONS, tree.getIcons().length == 1 ? tree.getIcons()[0] : tree.getIcons());
        }
        if (tree.isExpanded()) {
            gen.writeBooleanField(N_EXPANDED, tree.isExpanded());
        }
        if (tree.isSelected()) {
            gen.writeBooleanField(N_SELECTED, tree.isSelected());
        }
        if (tree.isChecked()) {
            gen.writeBooleanField(N_CHECKED, tree.isChecked());
        }
        if (tree.isNoDrag()) {
            gen.writeBooleanField(N_NODRAG, tree.isNoDrag());
        }
        if (tree.isNoDrop()) {
            gen.writeBooleanField(N_NODROP, tree.isNoDrop());
        }
        if (tree.isDisabled()) {
            gen.writeBooleanField(N_DISABLED, tree.isDisabled());
        }
        gen.writeObjectField(N_DATA, tree.getData());
        gen.writeArrayFieldStart(N_CHILDREN);
        if (tree.hasChildren()) {
            for (ITree<E> child : tree.getChildren()) {
                this.serialize(child, gen, serializers);
            }
        }
        gen.writeEndArray();
        gen.writeEndObject();
    }
}

