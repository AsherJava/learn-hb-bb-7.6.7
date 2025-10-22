/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 */
package com.jiuqi.nr.attachment.transfer.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.attachment.transfer.dto.TaskExecuteParam;
import com.jiuqi.nr.attachment.transfer.vo.GenerateParamVO;
import java.io.File;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class GenerateParamDTO
extends TaskExecuteParam {
    private boolean allEntity;
    private List<String> entityKeys;
    private int unitPage;

    public List<String> getEntityKeys() {
        return this.entityKeys;
    }

    public void setEntityKeys(List<String> entityKeys) {
        this.entityKeys = entityKeys;
    }

    public int getUnitPage() {
        return this.unitPage;
    }

    public void setUnitPage(int unitPage) {
        this.unitPage = unitPage;
    }

    public boolean isAllEntity() {
        return this.allEntity;
    }

    public void setAllEntity(boolean allEntity) {
        this.allEntity = allEntity;
    }

    public String getRealFilePath() {
        return this.getFilePath() + File.separator + "JIOEXPORT";
    }

    public static GenerateParamDTO getVOInstance(GenerateParamVO paramVO) {
        GenerateParamDTO dto = new GenerateParamDTO();
        dto.setTaskKey(paramVO.getTaskKey());
        dto.setPeriod(paramVO.getPeriod());
        dto.setMapping(paramVO.getMapping());
        dto.setAllEntity(paramVO.isAllEntity());
        dto.setEntityKeys(paramVO.getEntityKeys());
        dto.setUnitPage(paramVO.getUnitPage());
        return dto;
    }
}

