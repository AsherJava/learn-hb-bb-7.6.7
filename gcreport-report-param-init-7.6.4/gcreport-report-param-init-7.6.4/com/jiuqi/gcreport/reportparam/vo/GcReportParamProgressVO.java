/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.systemparam.enums.EntInitMsgType
 */
package com.jiuqi.gcreport.reportparam.vo;

import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.systemparam.enums.EntInitMsgType;

public class GcReportParamProgressVO {
    private String msg;
    private String type;

    public GcReportParamProgressVO() {
    }

    public GcReportParamProgressVO(String msg, EntInitMsgType type) {
        this.msg = DateUtils.nowTimeStr() + " " + type.getName() + msg;
        this.type = type.getCode();
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

