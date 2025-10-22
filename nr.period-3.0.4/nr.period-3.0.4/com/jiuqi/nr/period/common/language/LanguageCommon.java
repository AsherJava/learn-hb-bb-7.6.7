/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.period.PeriodType
 */
package com.jiuqi.nr.period.common.language;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.modal.IPeriodRow;
import org.springframework.util.StringUtils;

public class LanguageCommon {
    private static final String ZH_CN = "zh";

    private static String getCurrentLanguageType() {
        String language = ZH_CN;
        try {
            language = NpContextHolder.getContext().getLocale().getLanguage();
        }
        catch (Exception exception) {
            // empty catch block
        }
        return language;
    }

    public static boolean isDefault() {
        return ZH_CN.equals(LanguageCommon.getCurrentLanguageType());
    }

    public static String getPeriodEntityTitle(PeriodType periodType, String title) {
        if (LanguageCommon.isDefault()) {
            return title;
        }
        String en = title;
        switch (periodType) {
            case YEAR: {
                en = "YEAR";
                break;
            }
            case HALFYEAR: {
                en = "HALFYEAR";
                break;
            }
            case SEASON: {
                en = "SEASON";
                break;
            }
            case MONTH: {
                en = "MONTH";
                break;
            }
            case TENDAY: {
                en = "TENDAY";
                break;
            }
            case WEEK: {
                en = "WEEK";
                break;
            }
            case DAY: {
                en = "DAY";
                break;
            }
        }
        return en;
    }

    public static String getPeriodItemTitle(PeriodType periodType, IPeriodRow row) {
        if (LanguageCommon.isDefault()) {
            return row.getTitle();
        }
        String en = row.getTitle();
        PeriodUtils.getDateStrFromPeriod(row.getCode());
        if (PeriodType.YEAR.equals((Object)periodType)) {
            en = row.getYear() + "";
        } else if (PeriodType.MONTH.equals((Object)periodType)) {
            switch (row.getMonth()) {
                case 1: {
                    en = "Jan".concat(" ").concat(row.getYear() + "");
                    break;
                }
                case 2: {
                    en = "Feb".concat(" ").concat(row.getYear() + "");
                    break;
                }
                case 3: {
                    en = "Mar".concat(" ").concat(row.getYear() + "");
                    break;
                }
                case 4: {
                    en = "Apr".concat(" ").concat(row.getYear() + "");
                    break;
                }
                case 5: {
                    en = "May".concat(" ").concat(row.getYear() + "");
                    break;
                }
                case 6: {
                    en = "Jun".concat(" ").concat(row.getYear() + "");
                    break;
                }
                case 7: {
                    en = "Jul".concat(" ").concat(row.getYear() + "");
                    break;
                }
                case 8: {
                    en = "Aug".concat(" ").concat(row.getYear() + "");
                    break;
                }
                case 9: {
                    en = "Sep".concat(" ").concat(row.getYear() + "");
                    break;
                }
                case 10: {
                    en = "Oct".concat(" ").concat(row.getYear() + "");
                    break;
                }
                case 11: {
                    en = "Nov".concat(" ").concat(row.getYear() + "");
                    break;
                }
                case 12: {
                    en = "Dec".concat(" ").concat(row.getYear() + "");
                    break;
                }
            }
        }
        return en;
    }

    public static String getPeriodEntityTitleRe(String title) {
        if (LanguageCommon.isDefault()) {
            return title;
        }
        String en = title;
        switch (title) {
            case "YEAR": {
                en = PeriodType.YEAR.title();
                break;
            }
            case "HALFYEAR": {
                en = PeriodType.HALFYEAR.title();
                break;
            }
            case "SEASON": {
                en = PeriodType.SEASON.title();
                break;
            }
            case "MONTH": {
                en = PeriodType.MONTH.title();
                break;
            }
            case "TENDAY": {
                en = PeriodType.TENDAY.title();
                break;
            }
            case "WEEK": {
                en = PeriodType.WEEK.title();
                break;
            }
            case "DAY": {
                en = PeriodType.DAY.title();
                break;
            }
        }
        return en;
    }

    public static String getPeriodItemTitleRe(String title) {
        if (LanguageCommon.isDefault()) {
            return title;
        }
        if (StringUtils.hasLength(title)) {
            String[] s = title.split(" ");
            if (s.length == 1) {
                return title.concat(PeriodType.YEAR.title());
            }
            if (s.length == 2 && StringUtils.hasLength(s[0]) && StringUtils.hasLength(s[1])) {
                String pre = s[1].concat(PeriodType.YEAR.title());
                String fix = "";
                switch (s[0]) {
                    case "Jan": {
                        fix = "1".concat(PeriodType.MONTH.title());
                        break;
                    }
                    case "Feb": {
                        fix = "2".concat(PeriodType.MONTH.title());
                        break;
                    }
                    case "Mar": {
                        fix = "3".concat(PeriodType.MONTH.title());
                        break;
                    }
                    case "Apr": {
                        fix = "4".concat(PeriodType.MONTH.title());
                        break;
                    }
                    case "May": {
                        fix = "5".concat(PeriodType.MONTH.title());
                        break;
                    }
                    case "Jun": {
                        fix = "6".concat(PeriodType.MONTH.title());
                        break;
                    }
                    case "Jul": {
                        fix = "7".concat(PeriodType.MONTH.title());
                        break;
                    }
                    case "Aug": {
                        fix = "8".concat(PeriodType.MONTH.title());
                        break;
                    }
                    case "Sep": {
                        fix = "9".concat(PeriodType.MONTH.title());
                        break;
                    }
                    case "Oct": {
                        fix = "10".concat(PeriodType.MONTH.title());
                        break;
                    }
                    case "Nov": {
                        fix = "11".concat(PeriodType.MONTH.title());
                        break;
                    }
                    case "Dec": {
                        fix = "12".concat(PeriodType.MONTH.title());
                        break;
                    }
                }
                return pre.concat(fix);
            }
        }
        return title;
    }
}

