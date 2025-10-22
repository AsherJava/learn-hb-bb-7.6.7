/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.service.FileWriter
 *  com.jiuqi.nr.data.common.service.ParamsMapping
 */
package com.jiuqi.nr.data.attachment.param;

import com.jiuqi.nr.data.attachment.param.AttFieldDataInfo;
import com.jiuqi.nr.data.common.service.FileWriter;
import com.jiuqi.nr.data.common.service.ParamsMapping;
import java.util.List;

public class ExpFileParams {
    private String dataSchemeKey;
    private List<AttFieldDataInfo> attFieldDataInfos;
    private ParamsMapping paramsMapping;
    @Deprecated
    private String filePath;
    private FileWriter fileWriter;
    private String csvFileName;

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public List<AttFieldDataInfo> getAttFieldDataInfos() {
        return this.attFieldDataInfos;
    }

    public void setAttFieldDataInfos(List<AttFieldDataInfo> attFieldDataInfos) {
        this.attFieldDataInfos = attFieldDataInfos;
    }

    public ParamsMapping getParamsMapping() {
        return this.paramsMapping;
    }

    public void setParamsMapping(ParamsMapping paramsMapping) {
        this.paramsMapping = paramsMapping;
    }

    @Deprecated
    public String getFilePath() {
        return this.filePath;
    }

    @Deprecated
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public FileWriter getFileWriter() {
        return this.fileWriter;
    }

    public void setFileWriter(FileWriter fileWriter) {
        this.fileWriter = fileWriter;
    }

    public String getCsvFileName() {
        return this.csvFileName;
    }

    public void setCsvFileName(String csvFileName) {
        this.csvFileName = csvFileName;
    }
}

