/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.action;

import com.jiuqi.va.biz.intf.action.ActionReturnObject;
import com.jiuqi.va.biz.intf.action.ModalWidthEnum;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;

public class ActionReturnModalMessage
implements ActionReturnObject {
    protected String type = "ModalMessage";
    private String title = BizBindingI18nUtil.getMessage("va.bizbinding.hint");
    private String content;
    protected String messageType;
    private ModalWidthEnum width;
    private boolean scrollable;

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

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMessageType() {
        return this.messageType;
    }

    public void setMessageType(String messageType) {
        if ("confirm".equals(messageType)) {
            throw new UnsupportedOperationException();
        }
        this.messageType = messageType;
    }

    public ModalWidthEnum getWidth() {
        return this.width;
    }

    public void setWidth(ModalWidthEnum width) {
        this.width = width;
    }

    public boolean isScrollable() {
        return this.scrollable;
    }

    public void setScrollable(boolean scrollable) {
        this.scrollable = scrollable;
    }

    @Override
    public boolean isSuccess() {
        return !"error".equals(this.messageType);
    }
}

