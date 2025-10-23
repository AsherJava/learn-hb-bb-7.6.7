/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.reminder.plan.web;

import com.jiuqi.nr.reminder.plan.dao.impl.CbExecLogDO;

public class CbExecLogVO
extends CbExecLogDO {
    private String execUserName;

    public CbExecLogVO(CbExecLogDO cbExecLogDO) {
        this.setLogId(cbExecLogDO.getLogId());
        this.setPlanId(cbExecLogDO.getPlanId());
        this.setExecUser(cbExecLogDO.getExecUser());
        this.setMessage(cbExecLogDO.getMessage());
        this.setStatus(cbExecLogDO.getStatus());
        this.setStartTime(cbExecLogDO.getStartTime());
        this.setEndTime(cbExecLogDO.getEndTime());
    }

    public String getExecUserName() {
        return this.execUserName;
    }

    public void setExecUserName(String execUserName) {
        this.execUserName = execUserName;
    }

    public CbExecLogVO() {
    }
}

