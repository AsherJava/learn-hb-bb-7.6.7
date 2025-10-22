/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 */
package com.jiuqi.nr.query.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.nr.query.block.QueryBlockDefine;
import com.jiuqi.nr.query.common.QueryBlockType;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryBlockDefineSerializer
extends JsonSerializer<QueryBlockDefine> {
    private static final Logger log = LoggerFactory.getLogger(QueryBlockDefineSerializer.class);

    public void serialize(QueryBlockDefine define, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        try {
            gen.writeStartObject();
            gen.writeObjectField("id", (Object)define.getId());
            gen.writeObjectField("modalid", (Object)define.getModelID());
            gen.writeObjectField("title", (Object)define.getTitle());
            String data = new String(define.getGridData());
            if (define.getBlockType().equals((Object)QueryBlockType.QBT_CHART)) {
                data = "";
            }
            gen.writeObjectField("griddata", (Object)data);
            String formdata = new String(define.getFormdata());
            gen.writeObjectField("formdata", (Object)formdata);
            gen.writeObjectField("blockinfo", (Object)define.getBlockInfo());
            gen.writeObjectField("extension", (Object)define.getBlockExtension());
            gen.writeObjectField("masterinfo", define.getQueryMasters());
            gen.writeObjectField("masterkeys", (Object)define.getQueryMasterKeys());
            gen.writeObjectField("isuserform", (Object)define.getHasUserForm());
            gen.writeObjectField("page", (Object)define.getPageData());
            gen.writeObjectField("taskdefstartperiod", (Object)define.getTaskDefStartPeriod());
            gen.writeObjectField("taskdefendperiod", (Object)define.getTaskDefEndPeriod());
            gen.writeObjectField("isEnd", (Object)define.getEnd());
            gen.writeObjectField("x", (Object)define.getPosX());
            gen.writeObjectField("y", (Object)define.getPosY());
            gen.writeObjectField("w", (Object)define.getWidth());
            gen.writeObjectField("h", (Object)define.getHeight());
            gen.writeObjectField("i", (Object)define.getId());
            gen.writeObjectField("blocktype", (Object)define.getBlockType());
            gen.writeEndObject();
        }
        catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}

