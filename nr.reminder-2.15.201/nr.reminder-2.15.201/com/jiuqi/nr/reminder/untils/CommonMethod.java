/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.period.PeriodConsts
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.reminder.untils;

import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.period.PeriodConsts;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;

public class CommonMethod {
    public static ContextUser getCurrentUser() {
        NpContext context = NpContextHolder.getContext();
        ContextUser user = context.getUser();
        return user;
    }

    public static String getCurrentUserName() {
        ContextUser user = CommonMethod.getCurrentUser();
        if (user != null) {
            return user.getName();
        }
        return null;
    }

    public static PeriodWrapper getCurrPeriod(FormSchemeDefine formSchemeDefine) {
        char typeToCode;
        PeriodType periodType = formSchemeDefine.getPeriodType();
        int periodOffset = formSchemeDefine.getPeriodOffset();
        String fromPeriod = formSchemeDefine.getFromPeriod();
        String toPeriod = formSchemeDefine.getToPeriod();
        if (null == fromPeriod || null == toPeriod) {
            typeToCode = (char)PeriodConsts.typeToCode((int)periodType.type());
            fromPeriod = "1970" + typeToCode + "0001";
            toPeriod = "9999" + typeToCode + "0001";
        }
        if ("".equals(fromPeriod) || "".equals(toPeriod)) {
            typeToCode = (char)PeriodConsts.typeToCode((int)periodType.type());
            fromPeriod = "1970" + typeToCode + "0001";
            toPeriod = "9999" + typeToCode + "0001";
        }
        return CommonMethod.getCurrPeriod(periodType.type(), periodOffset, fromPeriod, toPeriod);
    }

    private static PeriodWrapper getCurrPeriod(int periodType, int periodOffset, String fromPeriod, String toPeriod) {
        PeriodWrapper fromPeriodWrapper = new PeriodWrapper(fromPeriod);
        PeriodWrapper toPeriodWrapper = new PeriodWrapper(toPeriod);
        int fromYear = fromPeriodWrapper.getYear();
        int toYear = toPeriodWrapper.getYear();
        return PeriodUtil.currentPeriod((int)fromYear, (int)toYear, (int)periodType, (int)periodOffset);
    }
}

