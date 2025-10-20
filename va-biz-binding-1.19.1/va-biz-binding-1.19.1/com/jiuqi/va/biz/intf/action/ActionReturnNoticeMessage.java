/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.action;

import com.jiuqi.va.biz.intf.action.ActionReturnObject;

public class ActionReturnNoticeMessage
implements ActionReturnObject {
    protected String type = "NoticeMessage";
    private String title;
    private String desc;
    protected String messageType;
    private Integer duration = 3;

    @Override
    public String getType() {
        return this.type;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessageType() {
        return this.messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Override
    public boolean isSuccess() {
        return !"error".equals(this.messageType);
    }
}

