/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.define.datamodel;

import com.jiuqi.bde.bizmodel.define.datamodel.AbstractFinBizDataModel;

public class InvestBillDataModel
extends AbstractFinBizDataModel {
    public static final String FN_INVESTBILL_CODE = "INVESTBILLMODEL";
    public static final String FN_INVESTBILL_NAME = "\u6295\u8d44\u53f0\u8d26";

    public String getCode() {
        return FN_INVESTBILL_CODE;
    }

    public String getName() {
        return FN_INVESTBILL_NAME;
    }

    public int getOrder() {
        return 20;
    }

    public String getEffectScope() {
        return "BDE_INVESTBILL";
    }
}

