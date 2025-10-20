/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.va.paramsync.domain.VaParamSyncMultipartFile
 */
package com.jiuqi.common.file.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.common.file.serializer.CommonFileBytesDeserializer;
import com.jiuqi.common.file.serializer.CommonFileBytesSerializer;
import com.jiuqi.va.paramsync.domain.VaParamSyncMultipartFile;

@JsonSerialize(using=CommonFileBytesSerializer.class)
@JsonDeserialize(using=CommonFileBytesDeserializer.class)
public class CommonFileDTO
extends VaParamSyncMultipartFile {
    public CommonFileDTO() {
        super(null, null, null, null);
    }

    public CommonFileDTO(String name, String originalFilename, String contentType, byte[] bytes) {
        super(name, originalFilename, contentType, bytes);
    }
}

