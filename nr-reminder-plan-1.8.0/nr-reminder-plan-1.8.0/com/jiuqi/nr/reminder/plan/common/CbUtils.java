/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.message.constants.MessageTypeEnum
 *  com.jiuqi.np.period.PeriodConsts
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.reminder.plan.common;

import com.jiuqi.np.message.constants.MessageTypeEnum;
import com.jiuqi.np.period.PeriodConsts;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.util.StringUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CbUtils {
    public static final String UPLOADED = "UPLOADED";
    public static final String CONFIRMED = "CONFIRMED";
    public static final int MSG_TYPE = MessageTypeEnum.NOTICE.getCode();
    public static final String MSG_TYPE_STR = "\u901a\u77e5";
    public static final String REMINDER_TITLE = "\u50ac\u62a5\u901a\u77e5";
    public static final int TASK_GROUP = 1;
    public static final int TASK = 2;
    public static final int SCHEME = 3;
    public static final int OK = 1;
    public static final int FAIL = 0;

    public static PeriodWrapper getCurrPeriod(FormSchemeDefine formSchemeDefine) {
        char typeToCode;
        if (formSchemeDefine == null) {
            return null;
        }
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
        return CbUtils.getCurrPeriod(periodType.type(), periodOffset, fromPeriod, toPeriod);
    }

    private static PeriodWrapper getCurrPeriod(int periodType, int periodOffset, String fromPeriod, String toPeriod) {
        PeriodWrapper fromPeriodWrapper = new PeriodWrapper(fromPeriod);
        PeriodWrapper toPeriodWrapper = new PeriodWrapper(toPeriod);
        int fromYear = fromPeriodWrapper.getYear();
        int toYear = toPeriodWrapper.getYear();
        return PeriodUtil.currentPeriod((int)fromYear, (int)toYear, (int)periodType, (int)periodOffset);
    }

    public static String removeTag(String html) {
        if (html == null) {
            return "";
        }
        String htmlStr = html;
        String regEx_html = "<[^>]+>";
        Pattern p_html = Pattern.compile(regEx_html, 2);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll("");
        return htmlStr;
    }

    public static String getContextMainDimId(String dw) {
        DsContext dsContext = DsContextHolder.getDsContext();
        String entityId = dsContext.getContextEntityId();
        return StringUtils.isEmpty((String)entityId) ? dw : entityId;
    }
}

