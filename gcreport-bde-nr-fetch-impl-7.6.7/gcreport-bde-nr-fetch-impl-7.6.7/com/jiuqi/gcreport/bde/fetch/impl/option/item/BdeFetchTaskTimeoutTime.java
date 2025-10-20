/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 */
package com.jiuqi.gcreport.bde.fetch.impl.option.item;

import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;

public class BdeFetchTaskTimeoutTime
implements ISystemOptionItem {
    public String getId() {
        return "BDE_TIMEOUT_TIME";
    }

    public String getTitle() {
        return "\u62a5\u8868\u53d6\u6570\u8d85\u65f6\u65f6\u95f4(\u79d2)";
    }

    public String getDescribe() {
        return "\u62a5\u8868\u53d6\u6570\u8d85\u65f6\u65f6\u95f4\uff0c\u8d85\u65f6\u540e\u53d6\u6570\u5931\u8d25\uff0c\u914d\u7f6e0\u4ee3\u8868\u6c38\u4e0d\u8d85\u65f6";
    }

    public String getDefaultValue() {
        return "1200";
    }

    public String getVerifyRegex() {
        return "^\\d+$";
    }

    public String getVerifyRegexMessage() {
        return "\u8bf7\u8f93\u5165\u975e\u8d1f\u6574\u6570";
    }
}

