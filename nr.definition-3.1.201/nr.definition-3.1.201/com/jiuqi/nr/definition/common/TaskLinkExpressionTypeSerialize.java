/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 */
package com.jiuqi.nr.definition.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.nr.definition.common.TaskLinkExpressionType;
import java.io.IOException;

public class TaskLinkExpressionTypeSerialize
extends JsonSerializer<TaskLinkExpressionType> {
    public static final String OLD_NAME0 = "equals";
    public static final String OLD_NAME1 = "include";
    public static final String OLD_NAME2 = "beginWith";
    public static final String OLD_NAME3 = "endWith";

    public void serialize(TaskLinkExpressionType value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String lowerCase = "";
        switch (value) {
            case EQUALS: {
                lowerCase = OLD_NAME0;
                break;
            }
            case INCLUDE: {
                lowerCase = OLD_NAME1;
                break;
            }
            case BEGIN_WITH: {
                lowerCase = OLD_NAME2;
                break;
            }
            case END_WITH: {
                lowerCase = OLD_NAME3;
                break;
            }
            default: {
                lowerCase = value.name();
            }
        }
        gen.writeString(lowerCase);
    }
}

