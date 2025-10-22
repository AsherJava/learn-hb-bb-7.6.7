/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.transfer.dto;

import com.jiuqi.nr.attachment.transfer.domain.WorkSpaceDO;

public class WorkSpaceDTO
extends WorkSpaceDO {
    public static WorkSpaceDTO getInstance(WorkSpaceDO workSpaceDO) {
        WorkSpaceDTO workSpaceDTO = new WorkSpaceDTO();
        workSpaceDTO.setThread(workSpaceDO.getThread());
        workSpaceDTO.setFilePath(workSpaceDO.getFilePath());
        workSpaceDTO.setSpaceSize(workSpaceDO.getSpaceSize());
        workSpaceDTO.setServerId(workSpaceDO.getServerId());
        workSpaceDTO.setType(workSpaceDO.getType());
        workSpaceDTO.setUrl(workSpaceDO.getUrl());
        return workSpaceDTO;
    }
}

