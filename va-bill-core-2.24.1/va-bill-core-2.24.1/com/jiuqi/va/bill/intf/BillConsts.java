/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.model.ModelConsts
 */
package com.jiuqi.va.bill.intf;

import com.jiuqi.va.biz.intf.model.ModelConsts;

public class BillConsts
extends ModelConsts {
    public static final String FN_BILLCODE = "BILLCODE";
    public static final String FT_BILLCODE = "\u5355\u636e\u7f16\u53f7";
    public static final String FN_BILLDATE = "BILLDATE";
    public static final String FT_BILLDATE = "\u5355\u636e\u65e5\u671f";
    public static final String FN_DEFINECODE = "DEFINECODE";
    public static final String FT_DEFINECODE = "\u5355\u636e\u5b9a\u4e49";
    public static final String FN_BILLTYPE = "DEFINECODE";
    public static final String FT_BILLTYPE = "\u5355\u636e\u5b9a\u4e49";
    public static final String FN_BILLSTATE = "BILLSTATE";
    public static final String FT_BILLSTATE = "\u5355\u636e\u72b6\u6001";
    public static final String FN_BIZSTATE = "BIZSTATE";
    public static final String FN_TRANSSTATE = "TRANSSTATE";
    public static final String FN_UNITCODE = "UNITCODE";
    public static final String FT_UNITCODE = "\u7ec4\u7ec7\u673a\u6784";
    public static final String FN_CREATEUSER = "CREATEUSER";
    public static final String FT_CREATEUSER = "\u521b\u5efa\u4eba";
    public static final String FN_CREATETIME = "CREATETIME";
    public static final String FT_CREATETIME = "\u521b\u5efa\u65f6\u95f4";
    public static final String FN_ABOLISHUSER = "ABOLISHUSER";
    public static final String FT_ABOLISHUSER = "\u5e9f\u6b62\u4eba";
    public static final String FN_ABOLISHTIME = "ABOLISHTIME";
    public static final String FT_ABOLISHTIME = "\u5e9f\u6b62\u65f6\u95f4";
    public static final String FN_MODIFYUSER = "MODIFYUSER";
    public static final String FT_MODIFYUSER = "\u4fee\u6539\u4eba";
    public static final String FN_MODIFYTIME = "MODIFYTIME";
    public static final String FT_MODIFYTIME = "\u4fee\u6539\u65f6\u95f4";
    public static final String FN_CHECKUSER = "CHECKUSER";
    public static final String FT_CHECKUSER = "\u5ba1\u6838\u4eba";
    public static final String FN_CHECKTIME = "CHECKTIME";
    public static final String FT_CHECKTIME = "\u5ba1\u6838\u65f6\u95f4";
    public static final int CODE_DEFAULT_SIZE = 60;
    public static final String MODEL_BILLCODE = "master.BILLCODE";
    public static final String MODEL_BILLVER = "master.VER";
    public static final String[] ACTION_PARAM_BROWER = new String[]{"master.BILLCODE", "master.VER"};
    public static final String BILLUPDATEMODE_DATASYNC = "datasync";
    public static final String FN_DISABLESENDMAILFLAG = "DISABLESENDMAILFLAG";
    public static final String FT_DISABLESENDMAILFLAG = "\u7981\u6b62\u53d1\u9001\u90ae\u4ef6";
    public static final String FN_GOTOLASTREJECT = "GOTOLASTREJECT";
    public static final String CONTEXT_KEY_CONSISTENCY = "X--consistency";
    public static final String SCENE_KEY = "scene_key";
    public static final String SCENE_KEY_BILLLIST = "billlist";
    public static final String X_LOADCHANGEDATA = "X--loadChangeData";
    public static final String X_DATAID = "X--dataId";
    public static final String X_CHANGESTATUS = "X--changeStatus";
    public static final String X_TEMPSNAP = "X--tempSnap";
    public static final String X_BEFORECHANGEBILLMODEL = "X--beforeChangeBillModel";
    public static final String X_AFTERCHANGEBILLMODEL = "X--afterChangeBillModel";
    public static final String X_BEFOREORIGINBILLMODEL = "X--beforeOriginBillModel";
    public static final String X_EDITTABLEFIELDS = "X--editTableFields";
}

