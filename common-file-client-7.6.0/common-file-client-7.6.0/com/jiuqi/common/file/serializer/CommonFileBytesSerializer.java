/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.SerializerProvider
 *  com.fasterxml.jackson.databind.ser.std.StdSerializer
 *  org.apache.commons.codec.binary.Base64
 */
package com.jiuqi.common.file.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.jiuqi.common.file.dto.CommonFileDTO;
import com.jiuqi.common.file.dto.CommonFileSerializerDTO;
import java.io.IOException;
import org.apache.commons.codec.binary.Base64;

public class CommonFileBytesSerializer
extends StdSerializer<CommonFileDTO> {
    private static final long serialVersionUID = -5510353102817291511L;

    public CommonFileBytesSerializer() {
        super(CommonFileDTO.class);
    }

    public void serialize(CommonFileDTO value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        CommonFileSerializerDTO serializerDTO = new CommonFileSerializerDTO();
        serializerDTO.setName(value.getName());
        serializerDTO.setBase64Content(Base64.encodeBase64String((byte[])value.getBytes()));
        serializerDTO.setContentType(value.getContentType());
        serializerDTO.setOriginalFilename(value.getOriginalFilename());
        gen.writeObject((Object)serializerDTO);
    }
}

