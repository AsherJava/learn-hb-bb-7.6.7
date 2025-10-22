/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 *  com.jiuqi.np.util.tree.Tree
 */
package com.jiuqi.nr.dataentry.tree;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.np.util.tree.Tree;
import com.jiuqi.nr.dataentry.tree.FormTree;
import com.jiuqi.nr.dataentry.tree.FormTreeItem;
import java.io.IOException;
import java.util.List;

public class TreeJsonSerializer<E>
extends JsonSerializer<FormTree> {
    public void serialize(FormTree formTree, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        Tree<FormTreeItem> tree = formTree.getTree();
        FormTreeItem data = (FormTreeItem)tree.getData();
        if (data != null) {
            gen.writeObjectField("id", (Object)data.getKey());
            gen.writeObjectField("key", (Object)data.getKey());
            gen.writeObjectField("title", (Object)data.getTitle());
            gen.writeObjectField("code", (Object)data.getCode());
            gen.writeObjectField("type", (Object)data.getType());
            gen.writeObjectField("serialNumber", (Object)data.getSerialNumber());
            gen.writeObjectField("formType", (Object)data.getFormType());
            gen.writeObjectField("measures", data.getMeasures());
            gen.writeObjectField("measureValues", data.getMeasureValues());
            gen.writeObjectField("groupKey", (Object)data.getGroupKey());
            gen.writeObjectField("analysisForm", (Object)data.isAnalysisForm());
            gen.writeObjectField("analysisReportKey", (Object)data.getAnalysisReportKey());
            gen.writeObjectField("needReload", (Object)data.isNeedReload());
        }
        gen.writeObjectField("isLeaf", (Object)tree.isLeaf());
        gen.writeObjectField("expanded", (Object)true);
        List children = tree.getChildren();
        if (children != null && children.size() != 0) {
            gen.writeFieldName("children");
            gen.writeStartArray();
            for (Tree child : children) {
                FormTree i18nTreeChildren = new FormTree();
                i18nTreeChildren.setTree((Tree<FormTreeItem>)child);
                gen.writeObject((Object)i18nTreeChildren);
            }
            gen.writeEndArray();
        }
        gen.writeEndObject();
    }
}

