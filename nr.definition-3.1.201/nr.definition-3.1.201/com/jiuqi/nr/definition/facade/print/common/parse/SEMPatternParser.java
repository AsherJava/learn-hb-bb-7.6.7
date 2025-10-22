/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.definition.facade.print.common.parse;

import com.jiuqi.nr.definition.facade.print.common.parse.AbstractSETParser;
import com.jiuqi.nr.definition.facade.print.common.parse.ParseContext;
import com.jiuqi.util.StringUtils;

public class SEMPatternParser
extends AbstractSETParser {
    private static String pageNumberRegx = "^PageNumber(InPt)?\\+[1-9]{1}\\d*$";
    private static String SUB_PAGE_NUMBER_CHINESE = "(SUBPAGENUMBER)";
    private String[] middleTags;

    public SEMPatternParser(String startTag, String endTag, String[] middleTags) {
        super(startTag, endTag);
        this.middleTags = middleTags;
    }

    @Override
    protected String doParse(ParseContext context, String str) {
        String value = context.getPagePatternParser().parse(context, str);
        if (!StringUtils.isEmpty((String)value)) {
            return value;
        }
        if (str.matches(pageNumberRegx)) {
            String[] parts = str.split("\\+");
            Integer currPageIndex = Integer.valueOf(context.getString(parts[0]));
            Integer pageIndexOffset = Integer.parseInt(parts[1]);
            Integer pageIndex = currPageIndex + pageIndexOffset;
            str = pageIndex.toString();
        } else if (str.contains(SUB_PAGE_NUMBER_CHINESE)) {
            String currNumber = context.getString("PageNumber");
            int currNum = Integer.valueOf(currNumber);
            int subNumber = this.calculateSubNumber(currNum, context.isSplit());
            if (subNumber > 0) {
                String subPageNumberInChinese = this.getNumberInChinese(subNumber);
                str = str.replace(SUB_PAGE_NUMBER_CHINESE, subPageNumberInChinese);
            } else {
                str = "";
            }
        } else {
            if (str.contains("RPTMONEYUNIT")) {
                String title = this.getMoneyUnit(context, str);
                if (StringUtils.isEmpty((String)title)) {
                    title = "";
                }
                return title;
            }
            for (String middleTag : this.middleTags) {
                String replacer = "";
                replacer = context.getString(middleTag);
                if (StringUtils.isEmpty((String)replacer)) {
                    replacer = "";
                }
                str = str.replace(middleTag, replacer);
            }
        }
        return str;
    }

    private int calculateSubNumber(int currNum, boolean split) {
        int subNumber = 0;
        if (split) {
            if (currNum > 2) {
                subNumber = currNum % 2 != 0 ? (currNum - 1) / 2 : currNum / 2 - 1;
            }
        } else {
            subNumber = currNum - 1;
        }
        return subNumber;
    }

    private String getNumberInChinese(int subNum) {
        if (subNum > 999) {
            return "\u65e0\u6cd5\u8bc6\u522b\u9875\u6570";
        }
        String[] chineseNumber = new String[]{"", "\u4e00", "\u4e8c", "\u4e09", "\u56db", "\u4e94", "\u516d", "\u4e03", "\u516b", "\u4e5d", "\u5341", "\u767e"};
        int bw = subNum / 100;
        if (bw > 0) {
            int sw = subNum % 100 / 10;
            int gw = subNum % 10;
            String returnStr = chineseNumber[bw] + chineseNumber[11];
            if (sw == 0 && gw == 0) {
                return returnStr;
            }
            if (sw == 0) {
                return returnStr + "\u96f6" + chineseNumber[gw];
            }
            return returnStr + chineseNumber[sw] + chineseNumber[10] + chineseNumber[gw];
        }
        int sw = subNum / 10;
        int gw = subNum % 10;
        String returnStr = "";
        switch (sw) {
            case 0: {
                returnStr = chineseNumber[gw];
                break;
            }
            case 1: {
                returnStr = chineseNumber[10] + chineseNumber[gw];
                break;
            }
            default: {
                returnStr = chineseNumber[sw] + chineseNumber[10] + chineseNumber[gw];
            }
        }
        return returnStr;
    }

    private String getMoneyUnit(ParseContext context, String str) {
        String moneyUtit = context.getString("RPTMONEYUNIT");
        if (StringUtils.isEmpty((String)moneyUtit)) {
            return moneyUtit;
        }
        String[] split = moneyUtit.split(";");
        if (split.length > 1) {
            String muCode;
            switch (muCode = split[1]) {
                case "QIANYUAN": {
                    moneyUtit = "\u5343\u5143";
                    break;
                }
                case "WANYUAN": {
                    moneyUtit = "\u4e07\u5143";
                    break;
                }
                case "BAIWAN": {
                    moneyUtit = "\u767e\u4e07";
                    break;
                }
                case "QIANWAN": {
                    moneyUtit = "\u5343\u4e07";
                    break;
                }
                case "YIYUAN": {
                    moneyUtit = "\u4ebf\u5143";
                    break;
                }
                case "BAIYUAN": {
                    moneyUtit = "\u767e\u5143";
                    break;
                }
                case "YUAN": {
                    moneyUtit = "\u5143";
                    break;
                }
                case "NotDimession": {
                    moneyUtit = "";
                }
            }
        } else if (moneyUtit.endsWith("QIANYUAN")) {
            moneyUtit = "\u5343\u5143";
        } else if (moneyUtit.endsWith("WANYUAN")) {
            moneyUtit = "\u4e07\u5143";
        } else if (moneyUtit.endsWith("BAIWAN")) {
            moneyUtit = "\u767e\u4e07";
        } else if (moneyUtit.endsWith("QIANWAN")) {
            moneyUtit = "\u5343\u4e07";
        } else if (moneyUtit.endsWith("YIYUAN")) {
            moneyUtit = "\u4ebf\u5143";
        } else if (moneyUtit.endsWith("BAIYUAN")) {
            moneyUtit = "\u767e\u5143";
        } else if (moneyUtit.endsWith("YUAN")) {
            moneyUtit = "\u5143";
        } else if (moneyUtit.endsWith("NotDimession")) {
            moneyUtit = "";
        }
        return moneyUtit;
    }

    @Override
    public boolean canReuser(String str) {
        return !str.contains("PageNumber") && !str.contains(SUB_PAGE_NUMBER_CHINESE) && !"Counter".equals(str);
    }
}

