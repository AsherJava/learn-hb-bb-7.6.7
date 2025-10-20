/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.action;

import com.jiuqi.va.biz.intf.action.ActionReturnObject;
import com.jiuqi.va.biz.intf.action.ModalWidthEnum;
import com.jiuqi.va.biz.intf.action.RenderVueOption;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import com.jiuqi.va.biz.view.intf.TemplateDeclare;
import java.beans.Transient;

public class ActionReturnModalTemplate
implements ActionReturnObject {
    protected String type = "ModalTemplate";
    private String title = BizBindingI18nUtil.getMessage("va.bizbinding.hint");
    protected String messageType;
    private TemplateDeclare templateDeclare = new TemplateDeclare();
    private String renderType;
    private ModalWidthEnum width;
    private boolean hideFooter;
    private boolean scrollable;
    private RenderVueOption renderVueOption;

    public RenderVueOption getRenderVueOption() {
        return this.renderVueOption;
    }

    public void setRenderVueOption(RenderVueOption renderVueOption) {
        this.renderVueOption = renderVueOption;
    }

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

    @Transient
    public TemplateDeclare getTemplateDeclare() {
        return this.templateDeclare;
    }

    public Object getTemplate() {
        return this.templateDeclare.getTemplate();
    }

    public ModalWidthEnum getWidth() {
        return this.width;
    }

    public void setWidth(ModalWidthEnum width) {
        this.width = width;
    }

    public boolean isHideFooter() {
        return this.hideFooter;
    }

    public void setHideFooter(boolean hideFooter) {
        this.hideFooter = hideFooter;
    }

    public boolean isScrollable() {
        return this.scrollable;
    }

    public void setScrollable(boolean scrollable) {
        this.scrollable = scrollable;
    }

    public String getRenderType() {
        return this.renderType;
    }

    public void setRenderType(String renderType) {
        this.renderType = renderType;
    }

    @Override
    public boolean isSuccess() {
        return !"error".equals(this.messageType);
    }

    public String getMessageType() {
        return this.messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}

