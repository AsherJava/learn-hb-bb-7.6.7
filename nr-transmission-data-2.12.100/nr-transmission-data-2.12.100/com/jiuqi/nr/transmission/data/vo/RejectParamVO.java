/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.vo;

import com.jiuqi.nr.transmission.data.reject.RejectParamDTO;

public class RejectParamVO
extends RejectParamDTO {
    public static RejectParamDTO rejectParamVoToDto(RejectParamVO rejectParam) {
        RejectParamDTO rejectParamDTO = new RejectParamDTO();
        if (rejectParam != null) {
            rejectParamDTO.setFormKeys(rejectParam.getFormKeys());
            rejectParamDTO.setPeriod(rejectParam.getPeriod());
            rejectParamDTO.setTask(rejectParam.getTask());
            rejectParamDTO.setUser(rejectParam.getUser());
            rejectParamDTO.setUnitIds(rejectParam.getUnitIds());
            rejectParamDTO.setUnitToflow(rejectParam.getUnitToflow());
            rejectParamDTO.setFormSchemeKey(rejectParam.getFormSchemeKey());
            rejectParamDTO.setFromGroupKeys(rejectParam.getFromGroupKeys());
            rejectParamDTO.setUserName(rejectParam.getUserName());
        }
        return rejectParamDTO;
    }
}

