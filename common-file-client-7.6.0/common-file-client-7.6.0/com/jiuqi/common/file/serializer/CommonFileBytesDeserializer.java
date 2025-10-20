/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.deser.std.StdDeserializer
 *  com.jiuqi.common.base.util.JsonUtils
 *  org.apache.commons.codec.binary.Base64
 */
package com.jiuqi.common.file.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.file.dto.CommonFileDTO;
import com.jiuqi.common.file.dto.CommonFileSerializerDTO;
import java.io.IOException;
import org.apache.commons.codec.binary.Base64;

public class CommonFileBytesDeserializer
extends StdDeserializer<CommonFileDTO> {
    private static final long serialVersionUID = -5510353102817291511L;

    public CommonFileBytesDeserializer() {
        super(byte[].class);
    }

    public CommonFileDTO deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = (JsonNode)p.getCodec().readTree(p);
        CommonFileSerializerDTO serializerDTO = (CommonFileSerializerDTO)JsonUtils.readValue((String)node.toString(), CommonFileSerializerDTO.class);
        byte[] bytes = Base64.decodeBase64((String)serializerDTO.getBase64Content());
        CommonFileDTO commonFileDTO = new CommonFileDTO(serializerDTO.getName(), serializerDTO.getOriginalFilename(), serializerDTO.getContentType(), bytes);
        return commonFileDTO;
    }
}

