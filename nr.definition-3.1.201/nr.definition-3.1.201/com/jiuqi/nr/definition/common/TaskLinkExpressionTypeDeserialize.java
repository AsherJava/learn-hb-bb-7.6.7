/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JacksonException
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.ObjectCodec
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonNode
 */
package com.jiuqi.nr.definition.common;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.nr.definition.common.TaskLinkExpressionType;
import java.io.IOException;
import org.springframework.util.StringUtils;

public class TaskLinkExpressionTypeDeserialize
extends JsonDeserializer<TaskLinkExpressionType> {
    public TaskLinkExpressionType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        TaskLinkExpressionType taskLinkExpressionType = null;
        ObjectCodec codec = p.getCodec();
        JsonNode jsonNode = (JsonNode)codec.readTree(p);
        String name = jsonNode.asText();
        if (StringUtils.hasLength(name)) {
            block6 : switch (name) {
                case "equals": {
                    taskLinkExpressionType = TaskLinkExpressionType.EQUALS;
                    break;
                }
                case "include": {
                    taskLinkExpressionType = TaskLinkExpressionType.INCLUDE;
                    break;
                }
                case "beginWith": {
                    taskLinkExpressionType = TaskLinkExpressionType.BEGIN_WITH;
                    break;
                }
                case "endWith": {
                    taskLinkExpressionType = TaskLinkExpressionType.END_WITH;
                    break;
                }
                default: {
                    for (TaskLinkExpressionType taskLinkExpressionTypeEnum : TaskLinkExpressionType.values()) {
                        if (!taskLinkExpressionTypeEnum.name().equals(name)) continue;
                        taskLinkExpressionType = taskLinkExpressionTypeEnum;
                        break block6;
                    }
                }
            }
        }
        return taskLinkExpressionType;
    }
}

