/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 */
package com.jiuqi.gcreport.samecontrol.vo;

import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;

public class SameCtrlQueryParamsVO
extends QueryParamsVO {
    private boolean isSameCtrl;
    private String sameCtrlPeriodStr;

    public boolean isSameCtrl() {
        return this.isSameCtrl;
    }

    public void setSameCtrl(boolean sameCtrl) {
        this.isSameCtrl = sameCtrl;
    }

    public String getSameCtrlPeriodStr() {
        return this.sameCtrlPeriodStr;
    }

    public void setSameCtrlPeriodStr(String sameCtrlPeriodStr) {
        this.sameCtrlPeriodStr = sameCtrlPeriodStr;
    }
}

