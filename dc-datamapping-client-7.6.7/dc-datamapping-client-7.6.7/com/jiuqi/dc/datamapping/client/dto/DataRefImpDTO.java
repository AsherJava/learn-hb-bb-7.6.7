/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.enums.ImportModuleTypeEnum
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.dc.datamapping.client.dto;

import com.jiuqi.dc.base.common.enums.ImportModuleTypeEnum;
import org.springframework.web.multipart.MultipartFile;

public class DataRefImpDTO {
    private String dataSourceCode;
    private ImportModuleTypeEnum impMode;
    private MultipartFile file;

    public String getDataSourceCode() {
        return this.dataSourceCode;
    }

    public void setDataSourceCode(String dataSourceCode) {
        this.dataSourceCode = dataSourceCode;
    }

    public ImportModuleTypeEnum getImpMode() {
        return this.impMode;
    }

    public void setImpMode(ImportModuleTypeEnum impMode) {
        this.impMode = impMode;
    }

    public MultipartFile getFile() {
        return this.file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}

