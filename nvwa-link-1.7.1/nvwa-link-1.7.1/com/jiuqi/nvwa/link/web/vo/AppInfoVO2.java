/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.link.web.vo;

import com.jiuqi.nvwa.link.web.vo.AppInfoVO;
import java.util.UUID;

public class AppInfoVO2
extends AppInfoVO {
    private String tmpId;

    public AppInfoVO2(AppInfoVO infoVO) {
        this.setId(infoVO.getId());
        this.setAppName(infoVO.getAppName());
        this.setFuncName(infoVO.getFuncName());
        this.setProdLine(infoVO.getProdLine());
        this.setType(infoVO.getType());
        this.tmpId = UUID.randomUUID().toString();
    }

    public String getTmpId() {
        return this.tmpId;
    }

    public void setTmpId(String tmpId) {
        this.tmpId = tmpId;
    }
}

