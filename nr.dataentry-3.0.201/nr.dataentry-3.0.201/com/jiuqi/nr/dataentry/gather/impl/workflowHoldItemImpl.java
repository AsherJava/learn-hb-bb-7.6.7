/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.gather.impl;

import com.jiuqi.nr.dataentry.gather.ActionsHoldItem;
import com.jiuqi.nr.dataentry.gather.KeyStore;
import org.springframework.stereotype.Component;

@Component
public class workflowHoldItemImpl
implements ActionsHoldItem {
    @Override
    public String getCode() {
        return "workflow";
    }

    @Override
    public String getTitle() {
        return "\u4e0a\u62a5\u6d41\u7a0b";
    }

    @Override
    public String getDesc() {
        return "\u4e0a\u62a5\u6d41\u7a0b\u5360\u4f4d\u6309\u94ae\uff0c\u4e0d\u662f\u4e00\u4e2a\u771f\u6b63\u7684\u6309\u94ae\uff0c\u53ea\u662f\u7528\u6765\u6807\u8bc6\u6d41\u7a0b\u6309\u94ae\u548c\u5176\u4ed6\u6309\u94ae\u7684\u76f8\u5bf9\u4f4d\u7f6e";
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
        return "\u914d\u7f6e\u6b64\u53c2\u6570\uff1a{ \"all\":{ \"batch\": false}} \u4e0d\u663e\u793a\u6279\u91cf\u6d41\u7a0b\u6309\u94ae\uff0c\u4e0d\u914d\u7f6e\u9ed8\u8ba4\u663e\u793a\u6279\u91cf\u6d41\u7a0b\u6309\u94ae";
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
        return null;
    }

    @Override
    public String getAlias() {
        return null;
    }
}

