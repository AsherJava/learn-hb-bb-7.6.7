/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.financialcheckapi.common.vo.TableColumnVO
 */
package com.jiuqi.gcreport.financialcheckImpl.check.consts;

import com.jiuqi.gcreport.financialcheckapi.common.vo.TableColumnVO;
import java.util.ArrayList;
import java.util.List;

public class TransferConst {
    public static final List<TableColumnVO> allCheckedColumnVOS = new ArrayList<TableColumnVO>();
    public static final List<TableColumnVO> allUncheckedColumnVOS = new ArrayList<TableColumnVO>();

    static {
        allCheckedColumnVOS.add(new TableColumnVO("checkType", "\u5bf9\u8d26\u65b9\u5f0f", "left", Integer.valueOf(100)));
        allCheckedColumnVOS.add(new TableColumnVO("unitState", "\u5173\u8d26\u72b6\u6001", "left", Integer.valueOf(80)));
        allCheckedColumnVOS.add(new TableColumnVO("checkRuleId", "\u5bf9\u8d26\u65b9\u6848", "left", Integer.valueOf(150)));
        allCheckedColumnVOS.add(new TableColumnVO("gcNumber", "\u534f\u540c\u7801", "left", Integer.valueOf(150)));
        allCheckedColumnVOS.add(new TableColumnVO("vchrNum", "\u51ed\u8bc1\u53f7", "left", Integer.valueOf(100)));
        allCheckedColumnVOS.add(new TableColumnVO("acctPeriod", "\u671f\u95f4", "left", Integer.valueOf(50)));
        allCheckedColumnVOS.add(new TableColumnVO("unitId", "\u672c\u65b9\u5355\u4f4d", "left", Integer.valueOf(250)));
        allCheckedColumnVOS.add(new TableColumnVO("oppUnitId", "\u5bf9\u65b9\u5355\u4f4d", "left", Integer.valueOf(250)));
        allCheckedColumnVOS.add(new TableColumnVO("subjectCode", "\u79d1\u76ee", "left", Integer.valueOf(250)));
        allCheckedColumnVOS.add(new TableColumnVO("originalCurr", "\u539f\u5e01\u5e01\u79cd", "left", Integer.valueOf(80)));
        allCheckedColumnVOS.add(new TableColumnVO("debitOrig", "\u501f\u65b9\u91d1\u989d\uff08\u539f\u5e01\uff09", "right", Integer.valueOf(150), TypeEnum.NUMBER.name()));
        allCheckedColumnVOS.add(new TableColumnVO("creditOrig", "\u8d37\u65b9\u91d1\u989d\uff08\u539f\u5e01\uff09", "right", Integer.valueOf(150), TypeEnum.NUMBER.name()));
        allCheckedColumnVOS.add(new TableColumnVO("chkAmt", "\u5bf9\u8d26\u91d1\u989d", "right", Integer.valueOf(150), TypeEnum.NUMBER.name()));
        allCheckedColumnVOS.add(new TableColumnVO("diffAmount", "\u5bf9\u8d26\u5dee\u989d", "right", Integer.valueOf(100), TypeEnum.NUMBER.name()));
        allCheckedColumnVOS.add(new TableColumnVO("unCheckType", "\u5dee\u5f02\u7c7b\u578b", "left", Integer.valueOf(150)));
        allCheckedColumnVOS.add(new TableColumnVO("unCheckDesc", "\u5dee\u5f02\u8865\u5145\u8bf4\u660e", "left", Integer.valueOf(150)));
        allCheckedColumnVOS.add(new TableColumnVO("currency", "\u672c\u4f4d\u5e01\u5e01\u79cd", "left", Integer.valueOf(80)));
        allCheckedColumnVOS.add(new TableColumnVO("debit", "\u501f\u65b9\u91d1\u989d\uff08\u672c\u4f4d\u5e01\uff09", "right", Integer.valueOf(150), TypeEnum.NUMBER.name()));
        allCheckedColumnVOS.add(new TableColumnVO("credit", "\u8d37\u65b9\u91d1\u989d\uff08\u672c\u4f4d\u5e01\uff09", "right", Integer.valueOf(150), TypeEnum.NUMBER.name()));
        allCheckedColumnVOS.add(new TableColumnVO("checkPeriod", "\u5bf9\u8d26\u671f\u95f4", "left", Integer.valueOf(100)));
        allCheckedColumnVOS.add(new TableColumnVO("digest", "\u6458\u8981", "left", Integer.valueOf(150)));
        allCheckedColumnVOS.add(new TableColumnVO("vchrSourceType", "\u51ed\u8bc1\u7c7b\u578b", "right", Integer.valueOf(150)));
        allCheckedColumnVOS.add(new TableColumnVO("vchrType", "\u51ed\u8bc1\u5b57", "left", Integer.valueOf(100)));
        allCheckedColumnVOS.add(new TableColumnVO("createDate", "\u8bb0\u8d26\u65e5", "left", Integer.valueOf(100)));
        allCheckedColumnVOS.add(new TableColumnVO("billCode", "\u5355\u636e\u7f16\u53f7", "left", Integer.valueOf(100)));
        allCheckedColumnVOS.add(new TableColumnVO("inputWay", "\u6765\u6e90\u7c7b\u578b", "left", Integer.valueOf(150)));
        allCheckedColumnVOS.add(new TableColumnVO("cfItemCode", "\u73b0\u6d41\u9879\u76ee", "left", Integer.valueOf(150)));
        allUncheckedColumnVOS.add(new TableColumnVO("unitState", "\u5173\u8d26\u72b6\u6001", "left", Integer.valueOf(80)));
        allUncheckedColumnVOS.add(new TableColumnVO("checkRuleId", "\u5bf9\u8d26\u65b9\u6848", "left", Integer.valueOf(150)));
        allUncheckedColumnVOS.add(new TableColumnVO("gcNumber", "\u534f\u540c\u7801", "left", Integer.valueOf(150)));
        allUncheckedColumnVOS.add(new TableColumnVO("vchrNum", "\u51ed\u8bc1\u53f7", "left", Integer.valueOf(100)));
        allUncheckedColumnVOS.add(new TableColumnVO("acctPeriod", "\u671f\u95f4", "left", Integer.valueOf(50)));
        allUncheckedColumnVOS.add(new TableColumnVO("unitId", "\u672c\u65b9\u5355\u4f4d", "left", Integer.valueOf(250)));
        allUncheckedColumnVOS.add(new TableColumnVO("oppUnitId", "\u5bf9\u65b9\u5355\u4f4d", "left", Integer.valueOf(250)));
        allUncheckedColumnVOS.add(new TableColumnVO("subjectCode", "\u79d1\u76ee", "left", Integer.valueOf(250)));
        allUncheckedColumnVOS.add(new TableColumnVO("originalCurr", "\u539f\u5e01\u5e01\u79cd", "left", Integer.valueOf(80)));
        allUncheckedColumnVOS.add(new TableColumnVO("debitOrig", "\u501f\u65b9\u91d1\u989d\uff08\u539f\u5e01\uff09", "right", Integer.valueOf(150), TypeEnum.NUMBER.name()));
        allUncheckedColumnVOS.add(new TableColumnVO("creditOrig", "\u8d37\u65b9\u91d1\u989d\uff08\u539f\u5e01\uff09", "right", Integer.valueOf(150), TypeEnum.NUMBER.name()));
        allUncheckedColumnVOS.add(new TableColumnVO("unCheckType", "\u5dee\u5f02\u7c7b\u578b", "left", Integer.valueOf(150)));
        allUncheckedColumnVOS.add(new TableColumnVO("unCheckDesc", "\u5dee\u5f02\u8865\u5145\u8bf4\u660e", "left", Integer.valueOf(150)));
        allUncheckedColumnVOS.add(new TableColumnVO("currency", "\u672c\u4f4d\u5e01\u5e01\u79cd", "left", Integer.valueOf(80)));
        allUncheckedColumnVOS.add(new TableColumnVO("debit", "\u501f\u65b9\u91d1\u989d\uff08\u672c\u4f4d\u5e01\uff09", "right", Integer.valueOf(150), TypeEnum.NUMBER.name()));
        allUncheckedColumnVOS.add(new TableColumnVO("credit", "\u8d37\u65b9\u91d1\u989d\uff08\u672c\u4f4d\u5e01\uff09", "right", Integer.valueOf(150), TypeEnum.NUMBER.name()));
        allUncheckedColumnVOS.add(new TableColumnVO("digest", "\u6458\u8981", "left", Integer.valueOf(150)));
        allUncheckedColumnVOS.add(new TableColumnVO("vchrSourceType", "\u51ed\u8bc1\u7c7b\u578b", "left", Integer.valueOf(150)));
        allUncheckedColumnVOS.add(new TableColumnVO("vchrType", "\u51ed\u8bc1\u5b57", "left", Integer.valueOf(100)));
        allUncheckedColumnVOS.add(new TableColumnVO("createDate", "\u8bb0\u8d26\u65e5", "left", Integer.valueOf(100)));
        allUncheckedColumnVOS.add(new TableColumnVO("billCode", "\u5355\u636e\u7f16\u53f7", "left", Integer.valueOf(100)));
        allUncheckedColumnVOS.add(new TableColumnVO("inputWay", "\u6765\u6e90\u7c7b\u578b", "left", Integer.valueOf(150)));
        allUncheckedColumnVOS.add(new TableColumnVO("cfItemCode", "\u73b0\u6d41\u9879\u76ee", "left", Integer.valueOf(150)));
    }

    public static enum TypeEnum {
        STRING,
        NUMBER;

    }
}

