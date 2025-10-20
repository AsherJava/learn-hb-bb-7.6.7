/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bill.domain.debug;

import java.util.ArrayList;
import java.util.List;

public class BillFormulaWhiteListConst {
    private static List<String> lists = new ArrayList<String>();

    public static boolean exist(String name) {
        return lists.contains(name);
    }

    static {
        lists.add("CheckActionExit");
        lists.add("ClearBillTableFields");
        lists.add("ClearBillDetailTableData");
        lists.add("GetAttachmentUrl");
        lists.add("GetBillContextVal");
        lists.add("getBillFieldValue");
        lists.add("GetCurViewCode");
        lists.add("GetRowDataByIndex");
        lists.add("ArrayToString");
        lists.add("CheckIdentityLegitimate");
        lists.add("CheckSocialCreditCodeLegitimate");
        lists.add("FirstDayOfMonth");
        lists.add("ForMap");
        lists.add("ForMap2");
        lists.add("GetBaseDataWholePath");
        lists.add("GetCurrLoginDate");
        lists.add("GetLoginOrgCode");
        lists.add("GetLoginUserName");
        lists.add("GetParentByCondition");
        lists.add("GetRefTableDataConditions");
        lists.add("GetRefTableDataField");
        lists.add("GetRefTableDataField2");
        lists.add("GetRowDataByIndex");
        lists.add("GetStaffRelatedUserRole");
        lists.add("GetUserIDByName");
        lists.add("GetUserLinkedStaff");
        lists.add("GetUserNameByID");
        lists.add("GetUserRoles");
        lists.add("InformationDesensitization");
        lists.add("InList");
        lists.add("IsUserRole");
        lists.add("MoneyStr");
        lists.add("MuiltDataToArray");
        lists.add("StringToArray");
        lists.add("TestRegular");
        lists.add("TFV");
        lists.add("ToArray");
        lists.add("ToMapArray");
        lists.add("ToStringWithDelimiter");
    }
}

