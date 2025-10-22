/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.gather.impl;

import com.jiuqi.nr.dataentry.gather.ActionsHoldItem;
import com.jiuqi.nr.dataentry.gather.KeyStore;

public class CalcAllFormulaSchemeHoldItemImpl
implements ActionsHoldItem {
    @Override
    public String getCode() {
        return "calcAllFormulaScheme";
    }

    @Override
    public String getTitle() {
        return "\u5168\u7b97\u7279\u5b9a\u516c\u5f0f\u65b9\u6848";
    }

    @Override
    public String getDesc() {
        return "\u5168\u7b97\u7279\u5b9a\u516c\u5f0f\u65b9\u6848";
    }

    @Override
    public boolean isEnablePermission() {
        return false;
    }

    @Override
    public KeyStore getAccelerator() {
        return null;
    }

    @Override
    public String getParams() {
        return null;
    }

    @Override
    public String getParamsDesc() {
        return null;
    }

    @Override
    public String getBgColor() {
        return null;
    }

    @Override
    public String getParentCode() {
        return null;
    }

    @Override
    public String getIcon() {
        return "#icon-_GJTquansuan";
    }

    @Override
    public String getAlias() {
        return null;
    }
}

