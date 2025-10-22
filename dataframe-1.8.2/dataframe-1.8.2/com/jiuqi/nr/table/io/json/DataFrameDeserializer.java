/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.TreeNode
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.deser.std.StdDeserializer
 *  com.fasterxml.jackson.databind.node.ArrayNode
 */
package com.jiuqi.nr.table.io.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.nr.table.df.DataFrame;
import java.io.IOException;
import java.util.ArrayList;

public class DataFrameDeserializer<E>
extends StdDeserializer<DataFrame<E>> {
    private static final long serialVersionUID = -8740340498326800936L;

    public DataFrameDeserializer() {
        this(DataFrameDeserializer.class);
    }

    protected DataFrameDeserializer(Class<?> vc) {
        super(vc);
    }

    public DataFrame<E> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        TreeNode row_levels;
        TreeNode tnode = p.readValueAsTree();
        TreeNode rows = tnode.get("rows");
        TreeNode row_names = rows.get("names");
        if (row_names.isArray()) {
            ArrayNode an = (ArrayNode)row_names;
            ArrayList names = new ArrayList(an.size());
            an.forEach(t -> names.add(t.textValue()));
        }
        if ((row_levels = rows.get("levels")).isArray()) {
            ArrayNode an = (ArrayNode)row_levels;
            ArrayList levels = new ArrayList(an.size());
            for (int i = 0; i < an.size(); ++i) {
                JsonNode jn = an.get(i);
                if (!jn.isArray()) continue;
                ArrayNode ajn = (ArrayNode)jn;
                for (int j = 0; j < ajn.size(); ++j) {
                    JsonNode jsonNode = ajn.get(j);
                }
            }
        }
        TreeNode columns = tnode.get("columns");
        TreeNode col_names = rows.get("names");
        TreeNode col_levels = rows.get("levels");
        TreeNode data = tnode.get("data");
        DataFrame df = new DataFrame();
        return df;
    }
}

