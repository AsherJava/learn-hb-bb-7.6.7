/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.aidocaudit.dto;

import com.jiuqi.gcreport.aidocaudit.dto.ResultitemOrderDTO;
import java.util.List;

public class AnnotationParamDTO {
    private String fileId;
    private List<ResultitemOrderDTO> resultitemOrderDTOList;

    public String getFileId() {
        return this.fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public List<ResultitemOrderDTO> getResultitemOrderDTOList() {
        return this.resultitemOrderDTOList;
    }

    public void setResultitemOrderDTOList(List<ResultitemOrderDTO> resultitemOrderDTOList) {
        this.resultitemOrderDTOList = resultitemOrderDTOList;
    }
}

