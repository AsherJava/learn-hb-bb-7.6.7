/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  nr.single.map.configurations.bean.ISingleMappingConfig
 *  nr.single.map.configurations.bean.SingleConfigInfo
 *  nr.single.map.configurations.bean.SingleFileInfo
 */
package com.jiuqi.nr.param.transfer.definition.dto.singlemap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.param.transfer.definition.dto.BaseDTO;
import com.jiuqi.nr.param.transfer.definition.dto.singlemap.FileType;
import java.io.IOException;
import java.util.EnumMap;
import nr.single.map.configurations.bean.ISingleMappingConfig;
import nr.single.map.configurations.bean.SingleConfigInfo;
import nr.single.map.configurations.bean.SingleFileInfo;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SingleMappingDTO
extends BaseDTO {
    private SingleConfigInfo configInfo;
    private ISingleMappingConfig singleMappingConfig;
    private SingleFileInfo singleFileInfo;
    private EnumMap<FileType, byte[]> files;

    public ISingleMappingConfig getSingleMappingConfig() {
        return this.singleMappingConfig;
    }

    public void setSingleMappingConfig(ISingleMappingConfig singleMappingConfig) {
        this.singleMappingConfig = singleMappingConfig;
    }

    public SingleConfigInfo getConfigInfo() {
        return this.configInfo;
    }

    public void setConfigInfo(SingleConfigInfo configInfo) {
        this.configInfo = configInfo;
    }

    public SingleFileInfo getSingleFileInfo() {
        return this.singleFileInfo;
    }

    public void setSingleFileInfo(SingleFileInfo singleFileInfo) {
        this.singleFileInfo = singleFileInfo;
    }

    public EnumMap<FileType, byte[]> getFiles() {
        return this.files;
    }

    public void setFiles(EnumMap<FileType, byte[]> files) {
        this.files = files;
    }

    public static SingleMappingDTO valueOf(byte[] bytes, ObjectMapper objectMapper) throws IOException {
        return (SingleMappingDTO)objectMapper.readValue(bytes, SingleMappingDTO.class);
    }
}

