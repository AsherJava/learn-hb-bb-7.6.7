/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.SerializerProvider
 *  com.fasterxml.jackson.databind.ser.std.StdSerializer
 */
package com.jiuqi.nr.table.io.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.jiuqi.nr.table.df.DataFrame;
import com.jiuqi.nr.table.df.IKey;
import com.jiuqi.nr.table.df.Index;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class DataFrameSerialize<E>
extends StdSerializer<DataFrame<E>> {
    private static final long serialVersionUID = -8917232827525175031L;

    public DataFrameSerialize() {
        this(DataFrame.class, false);
    }

    protected DataFrameSerialize(Class<?> t, boolean dummy) {
        super(t, dummy);
    }

    public void serialize(DataFrame<E> df, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeFieldName("rows");
        this.writeIndex(df.rows(), gen);
        gen.writeFieldName("columns");
        this.writeIndex(df.columns(), gen);
        gen.writeFieldName("data");
        this.writeBlackData(df, gen);
        gen.writeEndObject();
    }

    private void writeIndex(Index index, JsonGenerator gen) throws IOException {
        gen.writeStartObject();
        gen.writeFieldName("names");
        gen.writeObject((Object)index.getNames());
        if (null != index.getSources() && index.getSources().length > 0) {
            gen.writeFieldName("sources");
            gen.writeObject((Object)index.getSources());
        }
        gen.writeFieldName("levels");
        Set<Object> levels = index.levels();
        gen.writeStartArray();
        for (Object key : levels) {
            gen.writeStartArray();
            if (index.isMultIndex()) {
                Object[] es;
                for (Object o : es = ((IKey)key).getElements()) {
                    gen.writeObject(o);
                }
            } else {
                gen.writeObject(key);
            }
            gen.writeEndArray();
        }
        gen.writeEndArray();
        gen.writeEndObject();
    }

    private void writeBlackData(DataFrame<E> df, JsonGenerator gen) throws IOException {
        gen.writeStartArray();
        for (List<E> row : df) {
            gen.writeStartArray();
            for (E e : row) {
                gen.writeObject(e);
            }
            gen.writeEndArray();
        }
        gen.writeEndArray();
    }
}

