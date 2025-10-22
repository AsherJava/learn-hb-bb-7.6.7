/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.reportparser.function.IDC
 *  com.jiuqi.bi.syntax.reportparser.function.IDCL
 */
package com.jiuqi.nr.entity.component.idc;

import com.jiuqi.bi.syntax.reportparser.function.IDC;
import com.jiuqi.bi.syntax.reportparser.function.IDCL;
import org.springframework.util.Assert;

public class IDCUtils {
    public static String getIDCCheckBit(String code) {
        Assert.notNull((Object)code, "\u83b7\u53d6\u6821\u9a8c\u7801\u7684\u5b57\u7b26\u4e32\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.isTrue(code.length() == 8, "\u83b7\u53d6\u6821\u9a8c\u7801\u7684\u5b57\u7b26\u4e32\u957f\u5ea6\u5e94\u4e3a8\u4f4d");
        return IDC.callFunction((String)code);
    }

    public static String getIDCLCheckBit(String code) {
        Assert.notNull((Object)code, "\u83b7\u53d6\u6821\u9a8c\u7801\u7684\u5b57\u7b26\u4e32\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.isTrue(code.length() == 16, "\u83b7\u53d6\u6821\u9a8c\u7801\u7684\u5b57\u7b26\u4e32\u5e94\u4e3a16\u4f4d");
        String dwdm = code.substring(code.length() - 8);
        String idcCheckBit = IDCUtils.getIDCCheckBit(dwdm);
        if (idcCheckBit == null) {
            throw new RuntimeException("\u83b7\u53d6\u6821\u9a8c\u7801\u65f6\u53d1\u751f\u9519\u8bef");
        }
        return idcCheckBit + IDCL.callFunction((String)(code + idcCheckBit));
    }

    public static boolean verifyIDCCode(String code, String verifyName) {
        Assert.notNull((Object)code, verifyName + "\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.isTrue(code.length() == 9, verifyName + "\u7684\u957f\u5ea6\u5e94\u4e3a9\u4f4d");
        int checkIndex = code.length() - 1;
        String dwdm = code.substring(0, checkIndex);
        String checkBit = code.substring(checkIndex);
        return checkBit.equals(IDCUtils.getIDCCheckBit(dwdm));
    }

    public static boolean verifyIDCLCode(String code, String verifyName) {
        Assert.notNull((Object)code, verifyName + "\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.isTrue(code.length() == 18, verifyName + "\u7684\u957f\u5ea6\u5e94\u4e3a18\u4f4d");
        int checkIndex = code.length() - 1;
        String xydm = code.substring(0, checkIndex);
        String checkBit = code.substring(checkIndex);
        if (!checkBit.equals(IDCL.callFunction((String)xydm))) {
            return false;
        }
        String dwdm = xydm.substring(xydm.length() - 9);
        return IDCUtils.verifyIDCCode(dwdm, verifyName);
    }
}

