/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JacksonException
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.json.Grid2DataDeserialize
 *  com.jiuqi.nvwa.grid2.json.GridCellDataDeserialize
 */
package com.jiuqi.nr.designer.common;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.json.Grid2DataDeserialize;
import com.jiuqi.nvwa.grid2.json.GridCellDataDeserialize;
import java.io.IOException;

public class NrGrid2DataDeserialize
extends Grid2DataDeserialize {
    public Grid2Data deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        ObjectMapper mapper = (ObjectMapper)p.getCodec();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(GridCellData.class, (JsonDeserializer)new GridCellDataDeserialize());
        mapper.registerModule((Module)module);
        return super.deserialize(p, ctxt);
    }
}

