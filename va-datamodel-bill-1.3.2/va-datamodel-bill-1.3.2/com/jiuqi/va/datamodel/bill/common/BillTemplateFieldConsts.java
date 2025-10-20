/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.datamodel.bill.common;

import com.jiuqi.va.datamodel.bill.common.BillMessageSourceUtil;

public class BillTemplateFieldConsts {
    public static final String FN_ID = "ID";
    @Deprecated
    public static final String FT_ID = "\u552f\u4e00\u6807\u8bc6";
    public static final String FN_VER = "VER";
    @Deprecated
    public static final String FT_VER = "\u884c\u7248\u672c";
    public static final String FN_UNITCODE = "UNITCODE";
    @Deprecated
    public static final String FT_UNITCODE = "\u7ec4\u7ec7\u673a\u6784";
    public static final String FN_CREATEUSER = "CREATEUSER";
    @Deprecated
    public static final String FT_CREATEUSER = "\u521b\u5efa\u4eba";
    public static final String FN_CREATETIME = "CREATETIME";
    @Deprecated
    public static final String FT_CREATETIME = "\u521b\u5efa\u65f6\u95f4";
    public static final String FN_BILLCODE = "BILLCODE";
    @Deprecated
    public static final String FT_BILLCODE = "\u5355\u636e\u7f16\u53f7";
    public static final String FN_BILLDATE = "BILLDATE";
    @Deprecated
    public static final String FT_BILLDATE = "\u5355\u636e\u65e5\u671f";
    public static final String FN_DEFINECODE = "DEFINECODE";
    @Deprecated
    public static final String FT_DEFINECODE = "\u5355\u636e\u5b9a\u4e49";
    public static final String FN_BILLSTATE = "BILLSTATE";
    @Deprecated
    public static final String FT_BILLSTATE = "\u5355\u636e\u72b6\u6001";
    public static final String FN_QRCODE = "QRCODE";
    @Deprecated
    public static final String FT_QRCODE = " \u4e8c\u7ef4\u7801";
    public static final String FN_QUOTECODE = "QUOTECODE";
    @Deprecated
    public static final String FT_QUOTECODE = "\u9644\u4ef6\u5f15\u7528\u7801";
    public static final String FN_ATTACHNUM = "ATTACHNUM";
    @Deprecated
    public static final String FT_ATTACHNUM = "\u9644\u4ef6\u6570\u91cf";
    public static final String FN_IMAGETYPE = "IMAGETYPE";
    @Deprecated
    public static final String FT_IMAGETYPE = "\u5f71\u50cf\u7c7b\u578b";
    public static final String FN_IMAGESTATE = "IMAGESTATE";
    @Deprecated
    public static final String FT_IMAGESTATE = "\u5f71\u50cf\u72b6\u6001";
    public static final String FN_IMAGENUM = "IMAGENUM";
    @Deprecated
    public static final String FT_IMAGENUM = "\u5f71\u50cf\u5f20\u6570";
    public static final String FN_DISABLESENDMAILFLAG = "DISABLESENDMAILFLAG";
    @Deprecated
    public static final String FT_DISABLESENDMAILFLAG = "\u7981\u6b62\u53d1\u9001\u90ae\u4ef6";
    public static final String FN_GOTOLASTREJECT = "GOTOLASTREJECT";
    @Deprecated
    public static final String FT_GOTOLASTREJECT = "\u76f4\u8fbe\u9a73\u56de\u8282\u70b9";
    public static final String FN_GROUPID = "GROUPID";
    @Deprecated
    public static final String FT_GROUPID = "\u5206\u7ec4\u6807\u8bc6";
    public static final String FN_MASTERID = "MASTERID";
    @Deprecated
    public static final String FT_MASTERID = "\u4e3b\u8868ID";
    public static final String FN_ORDINAL = "ORDINAL";
    @Deprecated
    public static final String FT_ORDINAL = "\u6392\u5e8f";
    public static final String FN_BINDINGID = "BINDINGID";
    @Deprecated
    public static final String FT_BINDINGID = "\u5f15\u7528\u6570\u636eID";
    public static final String FN_BINDINGVALUE = "BINDINGVALUE";
    @Deprecated
    public static final String FT_BINDINGVALUE = "\u5f15\u7528\u6570\u636e\u503c";
    public static final String FN_TEMPSTEP = "TEMPSTEP";
    @Deprecated
    public static final String FT_TEMPSTEP = "\u5411\u5bfc\u5236\u5355\u6682\u5b58\u9875\u7d22\u5f15";
    public static final String FN_ABOLISHUSER = "ABOLISHUSER";
    public static final String FN_ABOLISHTIME = "ABOLISHTIME";
    @Deprecated
    public static final String FT_ABOLISHTIME = "\u5e9f\u6b62\u65f6\u95f4";
    public static final String FN_BILLDEFINE = "BILLDEFINE";
    public static final String FN_BILLITEMID = "BILLITEMID";
    public static final String FN_SRCBILLCODE = "SRCBILLCODE";
    public static final String FN_SRCBILLDEFINE = "SRCBILLDEFINE";
    public static final String FN_SRCBILLITEMID = "SRCBILLITEMID";
    public static final String FN_CURRMONEY = "CURRMONEY";
    public static final String FN_TEMPLATEID = "TEMPLATEID";
    public static final String FN_STATUS = "STATUS";

    public static String getFtID() {
        return BillMessageSourceUtil.getMessage("datamodel.consts.pubField.id", new Object[0]);
    }

    public static String getFtVer() {
        return BillMessageSourceUtil.getMessage("datamodel.consts.pubField.ver", new Object[0]);
    }

    public static String getFtUnitCode() {
        return BillMessageSourceUtil.getMessage("datamodel.consts.pubField.unitcode", new Object[0]);
    }

    public static String getFtCreateUser() {
        return BillMessageSourceUtil.getMessage("datamodel.consts.pubField.createuser", new Object[0]);
    }

    public static String getFtCreateTime() {
        return BillMessageSourceUtil.getMessage("datamodel.consts.pubField.createtime", new Object[0]);
    }

    public static String getFtBillCode() {
        return BillMessageSourceUtil.getMessage("datamodel.consts.pubField.billcode", new Object[0]);
    }

    public static String getFtTargetBillCode() {
        return BillMessageSourceUtil.getMessage("datamodel.consts.pubField.targetbillcode", new Object[0]);
    }

    public static String getFtBillDate() {
        return BillMessageSourceUtil.getMessage("datamodel.consts.pubField.billdate", new Object[0]);
    }

    public static String getFtDefineCode() {
        return BillMessageSourceUtil.getMessage("datamodel.consts.pubField.definecode", new Object[0]);
    }

    public static String getFtTargetDefineCode() {
        return BillMessageSourceUtil.getMessage("datamodel.consts.pubField.targetdefinecode", new Object[0]);
    }

    public static String getFtBillState() {
        return BillMessageSourceUtil.getMessage("datamodel.consts.pubField.billstate", new Object[0]);
    }

    public static String getFtQRCode() {
        return BillMessageSourceUtil.getMessage("datamodel.consts.pubField.qrcode", new Object[0]);
    }

    public static String getFtQuoteCode() {
        return BillMessageSourceUtil.getMessage("datamodel.consts.pubField.quotecode", new Object[0]);
    }

    public static String getFtAttachNum() {
        return BillMessageSourceUtil.getMessage("datamodel.consts.pubField.attachnum", new Object[0]);
    }

    public static String getFtImageType() {
        return BillMessageSourceUtil.getMessage("datamodel.consts.pubField.imagetype", new Object[0]);
    }

    public static String getFtImageState() {
        return BillMessageSourceUtil.getMessage("datamodel.consts.pubField.imagestate", new Object[0]);
    }

    public static String getFtImageNum() {
        return BillMessageSourceUtil.getMessage("datamodel.consts.pubField.imagenum", new Object[0]);
    }

    public static String getFtDisablesendMailFlag() {
        return BillMessageSourceUtil.getMessage("datamodel.consts.pubField.disablesendmailflag", new Object[0]);
    }

    public static String getFtGotoLastReject() {
        return BillMessageSourceUtil.getMessage("datamodel.consts.pubField.gotolastreject", new Object[0]);
    }

    public static String getFtGroupID() {
        return BillMessageSourceUtil.getMessage("datamodel.consts.pubField.groupid", new Object[0]);
    }

    public static String getFtMasterID() {
        return BillMessageSourceUtil.getMessage("datamodel.consts.pubField.masterid", new Object[0]);
    }

    public static String getFtOrdinal() {
        return BillMessageSourceUtil.getMessage("datamodel.consts.pubField.ordinal", new Object[0]);
    }

    public static String getFtBindingID() {
        return BillMessageSourceUtil.getMessage("datamodel.consts.pubField.bindingid", new Object[0]);
    }

    public static String getFtBindingValue() {
        return BillMessageSourceUtil.getMessage("datamodel.consts.pubField.bindingvalue", new Object[0]);
    }

    public static String getFtTempStep() {
        return BillMessageSourceUtil.getMessage("datamodel.consts.pubField.tempstep", new Object[0]);
    }

    public static String getFtAbolishUser() {
        return BillMessageSourceUtil.getMessage("datamodel.consts.pubField.abolishuser", new Object[0]);
    }

    public static String getFtAabolishTime() {
        return BillMessageSourceUtil.getMessage("datamodel.consts.pubField.abolishtime", new Object[0]);
    }

    public static String getFtBillDefine() {
        return BillMessageSourceUtil.getMessage("datamodel.consts.pubField.billdefine", new Object[0]);
    }

    public static String getFtBillItemId() {
        return BillMessageSourceUtil.getMessage("datamodel.consts.pubField.billitemid", new Object[0]);
    }

    public static String getFtSrcBillCode() {
        return BillMessageSourceUtil.getMessage("datamodel.consts.pubField.srcbillcode", new Object[0]);
    }

    public static String getFtSrcBillDefine() {
        return BillMessageSourceUtil.getMessage("datamodel.consts.pubField.srcbilldefine", new Object[0]);
    }

    public static String getFtSrcBillItemId() {
        return BillMessageSourceUtil.getMessage("datamodel.consts.pubField.srcbillitemid", new Object[0]);
    }

    public static String getFtCurrmoney() {
        return BillMessageSourceUtil.getMessage("datamodel.consts.pubField.currmoney", new Object[0]);
    }

    public static String getFtTemplateId() {
        return BillMessageSourceUtil.getMessage("datamodel.consts.pubField.templateid", new Object[0]);
    }

    public static String getFtStatus() {
        return BillMessageSourceUtil.getMessage("datamodel.consts.pubField.status", new Object[0]);
    }

    public static String getFtBillRefMasterId() {
        return BillMessageSourceUtil.getMessage("datamodel.consts.pubField.billrefmasterid", new Object[0]);
    }
}

