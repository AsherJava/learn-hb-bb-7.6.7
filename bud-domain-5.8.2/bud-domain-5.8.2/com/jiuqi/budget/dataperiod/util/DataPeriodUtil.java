/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.common.exception.BudgetException
 *  com.jiuqi.budget.common.utils.CommonUtil
 */
package com.jiuqi.budget.dataperiod.util;

import com.jiuqi.budget.common.exception.BudgetException;
import com.jiuqi.budget.common.utils.CommonUtil;
import com.jiuqi.budget.components.SysDim;
import com.jiuqi.budget.dataperiod.DataPeriod;
import com.jiuqi.budget.dataperiod.DataPeriodFactory;
import com.jiuqi.budget.dataperiod.DataPeriodTypeRegistrar;
import com.jiuqi.budget.dataperiod.IDataPeriodType;
import com.jiuqi.budget.dimexp.DimExpUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.util.StringUtils;

public class DataPeriodUtil {
    private static final String[] TEMP_CN = new String[]{"\u672c", "\u4e00", "\u4e8c", "\u4e09", "\u56db", "\u4e94", "\u516d", "\u4e03", "\u516b", "\u4e5d", "\u5341", "\u5341\u4e00", "\u5341\u4e8c"};
    private static final Pattern ALLCELLREG = Pattern.compile("\\w+\\[[^\\[\\]]*(?:\\[[^\\[\\]]*\\][^\\[\\]]*)*\\]");

    private DataPeriodUtil() {
        throw new BudgetException("Utility class");
    }

    public static String getPeriodOffSetTitle(String periodOffSet) {
        if (periodOffSet == null) {
            return null;
        }
        if (periodOffSet.length() == 9) {
            return DataPeriodFactory.valueOf(periodOffSet).getTitle();
        }
        StringBuilder title = new StringBuilder();
        ArrayList arrayList = CommonUtil.splitStr((String)periodOffSet, (char)',');
        if (arrayList != null && !arrayList.isEmpty()) {
            for (String str : arrayList) {
                IDataPeriodType dataPeriodType = DataPeriodTypeRegistrar.typeOf(str.substring(str.length() - 1));
                int periodVal = Integer.parseInt(str.substring(0, str.length() - 1));
                if (periodVal == 0) {
                    title.append(TEMP_CN[0]);
                } else if (periodVal < 0) {
                    title.append("\u4e0a");
                    if (periodVal != -1) {
                        title.append(TEMP_CN[periodVal * -1]);
                    }
                } else if (str.charAt(0) == '+') {
                    title.append("\u4e0b");
                    if (periodVal != 1) {
                        title.append(TEMP_CN[periodVal]);
                    }
                } else {
                    title.append(TEMP_CN[periodVal]);
                }
                title.append(dataPeriodType.getTitle());
            }
        }
        return title.toString();
    }

    public static String getPeriodOffSetVal(String cellExp) {
        return DimExpUtil.getOffSetStr(cellExp);
    }

    public static List<String> getAllCellExp(String expression) {
        ArrayList<String> cellExps = new ArrayList<String>();
        Matcher matcher = ALLCELLREG.matcher(expression);
        while (matcher.find()) {
            String match = matcher.group();
            cellExps.add(match);
        }
        return cellExps;
    }

    public static String handlePeriodOffSet(String str, DataPeriod dataPeriod) {
        if (str == null) {
            return null;
        }
        List<String> allCellExp = DataPeriodUtil.getAllCellExp(str);
        StringBuilder newCellExpBuilder = new StringBuilder(str);
        for (String cellExp : allCellExp) {
            String offSetStr;
            if (cellExp.contains(SysDim.DATATIME.name()) || !StringUtils.hasLength(offSetStr = DimExpUtil.getOffSetStr(cellExp))) continue;
            StringBuilder periodStrBuilder = new StringBuilder();
            periodStrBuilder.append(SysDim.DATATIME.name());
            periodStrBuilder.append("=\"");
            periodStrBuilder.append(dataPeriod.offSet(offSetStr));
            periodStrBuilder.append("\"");
            String periodCellExp = cellExp.replace(offSetStr, periodStrBuilder.toString());
            newCellExpBuilder.replace(newCellExpBuilder.indexOf(cellExp), newCellExpBuilder.indexOf(cellExp) + cellExp.length(), periodCellExp);
        }
        return newCellExpBuilder.toString();
    }

    public static final String relativeToFix(String relVal, String referPeriod) {
        DataPeriod dataPeriod = DataPeriodFactory.valueOf(relVal);
        return dataPeriod.offSet(referPeriod).toString();
    }
}

