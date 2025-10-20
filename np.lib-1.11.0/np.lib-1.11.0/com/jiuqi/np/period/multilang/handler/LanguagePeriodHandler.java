/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.period.multilang.handler;

import com.jiuqi.np.period.PeriodLanguage;
import com.jiuqi.np.period.multilang.LanguagePeriodConstant;
import com.jiuqi.np.period.multilang.WrapperData;
import com.jiuqi.np.period.multilang.handler.DateAider;

public class LanguagePeriodHandler {
    public static String localizePeriod(WrapperData wraper, PeriodLanguage language) {
        StringBuffer buffer = new StringBuffer(80);
        switch (wraper.getType()) {
            case 1: {
                buffer.append(wraper.getYear() + LanguagePeriodConstant.YEAR.localize(language));
                break;
            }
            case 2: {
                buffer.append(wraper.getYear() + LanguagePeriodConstant.YEAR.localize(language));
                buffer.append(LanguagePeriodConstant.SEPARATOR.localize(language));
                buffer.append(LanguagePeriodConstant.HALF_YEAR[wraper.getPeriod() - 1].localize(language));
                break;
            }
            case 3: {
                buffer.append(wraper.getYear() + LanguagePeriodConstant.YEAR.localize(language));
                buffer.append(LanguagePeriodConstant.SEPARATOR.localize(language));
                buffer.append(LanguagePeriodConstant.NUMBER.localize(language, wraper.getPeriod()));
                buffer.append(LanguagePeriodConstant.SEPARATOR.localize(language));
                buffer.append(LanguagePeriodConstant.QUARTER.localize(language));
                break;
            }
            case 4: {
                buffer.append(wraper.getYear() + LanguagePeriodConstant.YEAR.localize(language));
                buffer.append(LanguagePeriodConstant.SEPARATOR.localize(language));
                buffer.append(LanguagePeriodConstant.MONTH[wraper.getPeriod() - 1].localize(language));
                break;
            }
            case 5: {
                int[] tenDay = DateAider.calcMonthTenDay(wraper.getYear(), wraper.getPeriod());
                buffer.append(wraper.getYear() + LanguagePeriodConstant.YEAR.localize(language));
                buffer.append(LanguagePeriodConstant.SEPARATOR.localize(language));
                buffer.append(LanguagePeriodConstant.MONTH[tenDay[0] - 1].localize(language));
                buffer.append(LanguagePeriodConstant.SEPARATOR.localize(language));
                buffer.append(LanguagePeriodConstant.TEN_DAY[tenDay[1] - 1].localize(language));
                break;
            }
            case 6: {
                int[] monthDay = DateAider.calcMonthDay(wraper.getYear(), wraper.getPeriod());
                buffer.append(wraper.getYear() + LanguagePeriodConstant.YEAR.localize(language));
                buffer.append(LanguagePeriodConstant.SEPARATOR.localize(language));
                buffer.append(LanguagePeriodConstant.MONTH[monthDay[0] - 1].localize(language));
                buffer.append(LanguagePeriodConstant.SEPARATOR.localize(language));
                buffer.append(monthDay[1]);
                buffer.append(LanguagePeriodConstant.SEPARATOR.localize(language));
                buffer.append(LanguagePeriodConstant.DAY.localize(language));
                break;
            }
            case 7: {
                buffer.append(wraper.getYear() + LanguagePeriodConstant.YEAR.localize(language));
                buffer.append(LanguagePeriodConstant.SEPARATOR.localize(language));
                buffer.append(LanguagePeriodConstant.NUMBER.localize(language, wraper.getPeriod()));
                buffer.append(LanguagePeriodConstant.SEPARATOR.localize(language));
                buffer.append(LanguagePeriodConstant.WEEK.localize(language));
                break;
            }
            case 8: {
                buffer.append(wraper.getYear() + LanguagePeriodConstant.YEAR.localize(language));
                buffer.append(LanguagePeriodConstant.SEPARATOR.localize(language));
                buffer.append(LanguagePeriodConstant.NUMBER.localize(language, wraper.getPeriod()));
                buffer.append(LanguagePeriodConstant.SEPARATOR.localize(language));
                buffer.append(LanguagePeriodConstant.CUSTOM.localize(language));
                break;
            }
            default: {
                buffer.append("Unsupported");
            }
        }
        return buffer.toString();
    }
}

