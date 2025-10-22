/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 */
package com.jiuqi.nr.attachment.transfer.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.attachment.transfer.common.Utils;
import com.jiuqi.nr.attachment.transfer.dto.TaskExecuteParam;
import com.jiuqi.nr.attachment.transfer.vo.ImportParamVO;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ImportParamDTO
extends TaskExecuteParam {
    private List<String> files;

    public List<String> getFiles() {
        if (this.files == null) {
            this.files = new ArrayList<String>(16);
        }
        return this.files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public String getHex() {
        StringBuilder sbs = new StringBuilder();
        sbs.append(this.getTaskKey());
        sbs.append(this.getPeriod());
        sbs.append(this.getMapping());
        sbs.append(this.getFilePath());
        return Utils.hashStr(sbs.toString());
    }

    public static ImportParamDTO getVOInstance(ImportParamVO importParamVO) {
        ImportParamDTO importParamDTO = new ImportParamDTO();
        importParamDTO.setTaskKey(importParamVO.getTaskKey());
        importParamDTO.setPeriod(importParamVO.getPeriod());
        importParamDTO.setMapping(importParamVO.getMapping());
        return importParamDTO;
    }
}

