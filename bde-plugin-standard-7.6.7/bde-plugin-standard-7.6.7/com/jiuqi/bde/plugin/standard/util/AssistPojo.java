/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist
 */
package com.jiuqi.bde.plugin.standard.util;

import com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist;
import com.jiuqi.bde.plugin.standard.util.ShareTypeEnum;

public class AssistPojo
extends BaseAcctAssist {
    private String tableName;
    private ShareTypeEnum shareType;

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public ShareTypeEnum getShareType() {
        return this.shareType;
    }

    public void setShareType(String shareType) {
        this.shareType = ShareTypeEnum.fromCode(shareType);
    }
}

