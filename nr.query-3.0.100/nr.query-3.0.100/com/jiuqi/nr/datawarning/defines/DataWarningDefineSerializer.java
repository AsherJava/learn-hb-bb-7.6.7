/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 */
package com.jiuqi.nr.datawarning.defines;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.nr.datawarning.defines.DataWarningDefine;
import java.io.IOException;

public class DataWarningDefineSerializer
extends JsonSerializer<DataWarningDefine> {
    public void serialize(DataWarningDefine dataWarningDefine, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("id", (Object)dataWarningDefine.getId());
        gen.writeObjectField("warnType", (Object)dataWarningDefine.getWarnType());
        gen.writeObjectField("properties", (Object)dataWarningDefine.getProperty());
        gen.writeObjectField("Key", (Object)dataWarningDefine.getKey());
        gen.writeObjectField("identify", (Object)dataWarningDefine.getIdentify());
        gen.writeObjectField("scop", (Object)dataWarningDefine.getScop());
        gen.writeObjectField("order", (Object)dataWarningDefine.getOrder());
        gen.writeObjectField("fieldCode", (Object)dataWarningDefine.getFieldCode());
        gen.writeObjectField("fieldSettingCode", (Object)dataWarningDefine.getFieldSettingCode());
        gen.writeObjectField("isSave", (Object)dataWarningDefine.getIsSave());
        gen.writeEndObject();
    }
}

