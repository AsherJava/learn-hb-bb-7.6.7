/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.systemcheck.CheckOptionType
 *  com.jiuqi.nr.common.systemcheck.spi.ICheckResource
 */
package com.jiuqi.nr.system.check2.vo;

import com.jiuqi.nr.common.systemcheck.CheckOptionType;
import com.jiuqi.nr.common.systemcheck.spi.ICheckResource;
import java.util.List;

public class ResourceVO {
    String key;
    String title;
    String icon;
    CheckOptionType checkOptionType;
    String message;
    String confirmMessage;
    List<String> tabInformation;

    public ResourceVO() {
    }

    public ResourceVO(ICheckResource checkResource) {
        this.key = checkResource.getKey();
        this.title = checkResource.getTitle();
        this.icon = checkResource.getIcon();
        if (checkResource.getCheckOption() != null) {
            this.checkOptionType = checkResource.getCheckOption().getOptionType();
            this.confirmMessage = checkResource.getCheckOption().getConfirmMessage();
        }
        this.message = checkResource.getMessage();
        this.tabInformation = checkResource.getTagMessages();
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public CheckOptionType getCheckOptionType() {
        return this.checkOptionType;
    }

    public void setCheckOptionType(CheckOptionType checkOptionType) {
        this.checkOptionType = checkOptionType;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getConfirmMessage() {
        return this.confirmMessage;
    }

    public void setConfirmMessage(String confirmMessage) {
        this.confirmMessage = confirmMessage;
    }

    public List<String> getTabInformation() {
        return this.tabInformation;
    }

    public void setTabInformation(List<String> tabInformation) {
        this.tabInformation = tabInformation;
    }
}

