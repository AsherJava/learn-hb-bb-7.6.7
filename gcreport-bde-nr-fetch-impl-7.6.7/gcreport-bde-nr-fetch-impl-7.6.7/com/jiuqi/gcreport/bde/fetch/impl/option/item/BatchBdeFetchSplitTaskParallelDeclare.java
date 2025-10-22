/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 */
package com.jiuqi.gcreport.bde.fetch.impl.option.item;

import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;

public class BatchBdeFetchSplitTaskParallelDeclare
implements ISystemOptionItem {
    public static final String POOL_PARALLEL_SIZE = "BATCH_GC_FETCH_PARALLEL";

    public String getId() {
        return POOL_PARALLEL_SIZE;
    }

    public String getTitle() {
        return "\u6279\u91cf\u53d6\u6570\u62c6\u5206\u5b50\u4efb\u52a1\u6570";
    }

    public String getDescribe() {
        return "\u6279\u91cf\u53d6\u6570\u4efb\u52a1\u6267\u884c\u65f6\u62c6\u5206\u51fa\u7684\u5e76\u884c\u5b50\u4efb\u52a1\u6570\u91cf";
    }

    public String getDefaultValue() {
        return "10";
    }

    public String getVerifyRegex() {
        return "^\\+?[1-9][0-9]*$";
    }

    public String getVerifyRegexMessage() {
        return "\u8bf7\u8f93\u5165\u5927\u4e8e\u96f6\u7684\u6b63\u6570";
    }
}

