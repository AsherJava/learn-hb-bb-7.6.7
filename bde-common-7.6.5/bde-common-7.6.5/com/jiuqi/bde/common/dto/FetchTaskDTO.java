/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.dto;

import com.jiuqi.bde.common.dto.FormInfoVO;
import com.jiuqi.bde.common.intf.BaseFetchTaskInfo;
import java.util.List;

public class FetchTaskDTO
extends BaseFetchTaskInfo {
    private List<FormInfoVO> formInfoVOs;
    private String userName;

    public List<FormInfoVO> getFormInfoVOs() {
        return this.formInfoVOs;
    }

    public void setFormInfoVOs(List<FormInfoVO> formInfoVOs) {
        this.formInfoVOs = formInfoVOs;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return this.userName;
    }

    @Override
    public String toString() {
        return "FetchTaskDTO{formInfoVOs=" + this.formInfoVOs + ", userName='" + this.userName + '\'' + '}';
    }
}

