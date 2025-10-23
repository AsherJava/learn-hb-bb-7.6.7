/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.bean;

import com.jiuqi.nr.zbquery.rest.vo.QueryParamVO;

public class ZBQueryParam {
    String linkMessage;

    public ZBQueryParam(QueryParamVO paramVo) {
        this.linkMessage = paramVo.getLinkMessage();
    }

    public String getLinkMessage() {
        return this.linkMessage;
    }

    public void setLinkMessage(String linkMessage) {
        this.linkMessage = linkMessage;
    }
}

