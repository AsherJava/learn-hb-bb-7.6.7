/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.web.result;

import com.jiuqi.nr.multcheck2.common.ResultType;
import com.jiuqi.nr.multcheck2.web.result.GatherAuto;
import com.jiuqi.nr.multcheck2.web.result.MultcheckContext;
import com.jiuqi.nr.multcheck2.web.result.MultiplScheme;
import com.jiuqi.nr.multcheck2.web.result.SingleOrg;
import com.jiuqi.nr.multcheck2.web.result.SingleScheme;

public class ResultDetailVO {
    private ResultType type;
    private MultcheckContext context;
    private SingleOrg singleDetail;
    private SingleScheme schemeDetail;
    private MultiplScheme multiplDetail;
    private GatherAuto gatherAutoDetail;
    private String lastMsg;

    public ResultDetailVO() {
    }

    public ResultDetailVO(ResultType type) {
        this.type = type;
    }

    public ResultType getType() {
        return this.type;
    }

    public void setType(ResultType type) {
        this.type = type;
    }

    public MultcheckContext getContext() {
        return this.context;
    }

    public void setContext(MultcheckContext context) {
        this.context = context;
    }

    public SingleOrg getSingleDetail() {
        return this.singleDetail;
    }

    public void setSingleDetail(SingleOrg singleDetail) {
        this.singleDetail = singleDetail;
    }

    public SingleScheme getSchemeDetail() {
        return this.schemeDetail;
    }

    public void setSchemeDetail(SingleScheme schemeDetail) {
        this.schemeDetail = schemeDetail;
    }

    public MultiplScheme getMultiplDetail() {
        return this.multiplDetail;
    }

    public void setMultiplDetail(MultiplScheme multiplDetail) {
        this.multiplDetail = multiplDetail;
    }

    public GatherAuto getGatherAutoDetail() {
        return this.gatherAutoDetail;
    }

    public void setGatherAutoDetail(GatherAuto gatherAutoDetail) {
        this.gatherAutoDetail = gatherAutoDetail;
    }

    public String getLastMsg() {
        return this.lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }
}

