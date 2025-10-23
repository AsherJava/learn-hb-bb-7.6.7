/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.vo;

import com.jiuqi.nr.transmission.data.dto.SyncSchemeParamDTO;
import org.springframework.beans.BeanUtils;

public class SyncSchemeParamVO
extends SyncSchemeParamDTO {
    public static SyncSchemeParamDTO syncSchemeParamVoToDto(SyncSchemeParamVO syncSchemeParamVO) {
        SyncSchemeParamDTO syncSchemeParamDTO = new SyncSchemeParamDTO();
        if (syncSchemeParamVO != null) {
            BeanUtils.copyProperties(syncSchemeParamVO, syncSchemeParamDTO);
        }
        return syncSchemeParamDTO;
    }
}

