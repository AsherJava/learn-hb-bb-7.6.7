/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist
 */
package com.jiuqi.bde.plugin.gs5.util;

import com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist;
import com.jiuqi.bde.plugin.gs5.util.ShareTypeEnum;

public class AssistPojo
extends BaseAcctAssist {
    private String odsTableName;
    private String tablePk;
    private String assField;
    private String assSql;
    private String classificationCode;
    private ShareTypeEnum shareTypeEnum;

    public String getOdsTableName() {
        return this.odsTableName;
    }

    public void setOdsTableName(String odsTableName) {
        this.odsTableName = odsTableName;
    }

    public String getTablePk() {
        return this.tablePk;
    }

    public void setTablePk(String tablePk) {
        this.tablePk = tablePk;
    }

    public String getAssField() {
        return this.assField;
    }

    public void setAssField(String assField) {
        this.assField = assField;
    }

    public String getAssSql() {
        return this.assSql;
    }

    public void setAssSql(String assSql) {
        this.assSql = assSql;
    }

    public String getClassificationCode() {
        return this.classificationCode;
    }

    public void setClassificationCode(String classificationCode) {
        this.classificationCode = classificationCode;
    }

    public ShareTypeEnum getShareTypeEnum() {
        return this.shareTypeEnum;
    }

    public void setShareTypeEnum(ShareTypeEnum shareTypeEnum) {
        this.shareTypeEnum = shareTypeEnum;
    }
}

