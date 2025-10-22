/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
 */
package com.jiuqi.nr.table.io.json;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jiuqi.nr.table.df.DataFrame;
import com.jiuqi.nr.table.io.DataWriter;
import com.jiuqi.nr.table.io.Destination;
import com.jiuqi.nr.table.io.WriterRegistry;
import com.jiuqi.nr.table.io.json.DataFrameSerialize;
import com.jiuqi.nr.table.io.json.JsonWriteOptions;
import java.io.IOException;
import java.io.Writer;

public class JsonWriter
implements DataWriter<JsonWriteOptions> {
    private static final ObjectMapper mapper = new ObjectMapper().registerModule((Module)new JavaTimeModule());
    private static final JsonWriter INSTANCE = new JsonWriter();

    public static void register(WriterRegistry registry) {
        registry.registerExtension("json", INSTANCE);
        registry.registerOptions(JsonWriteOptions.class, INSTANCE);
    }

    public JsonWriter() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(new DataFrameSerialize());
        mapper.registerModule((Module)module);
    }

    public void register(Module module) {
        mapper.registerModule(module);
    }

    @Override
    public void write(DataFrame<?> df, JsonWriteOptions options) throws IOException {
        Writer writer = options.destination().createWriter();
        String jsonStr = mapper.writeValueAsString(df);
        writer.write(jsonStr);
    }

    @Override
    public void write(DataFrame<?> df, Destination dest) throws IOException {
        this.write(df, JsonWriteOptions.builder(dest).build());
    }
}

