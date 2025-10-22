/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.gather;

import com.jiuqi.nr.dataentry.gather.ActionType;
import com.jiuqi.nr.dataentry.gather.KeyStore;

public interface ActionItem {
    public String getCode();

    public String getTitle();

    public String getDesc();

    public ActionType getActionType();

    public boolean isEnablePermission();

    public KeyStore getAccelerator();

    public String getParams();

    public String getParamsDesc();

    public String getBgColor();

    public String getParentCode();

    public String getIcon();

    public String getAlias();
}

