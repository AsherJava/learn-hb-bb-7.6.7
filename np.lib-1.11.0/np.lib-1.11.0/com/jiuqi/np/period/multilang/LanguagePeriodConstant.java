/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.period.multilang;

import com.jiuqi.np.period.multilang.LanguageItem;
import com.jiuqi.np.period.multilang.period.ChinesePeriod;
import com.jiuqi.np.period.multilang.period.EnglishPeriod;

public class LanguagePeriodConstant {
    public static final LanguageItem JANUARY = new LanguageItem();
    public static final LanguageItem FEBRUARY = new LanguageItem();
    public static final LanguageItem MARCH = new LanguageItem();
    public static final LanguageItem APRIL = new LanguageItem();
    public static final LanguageItem MAY = new LanguageItem();
    public static final LanguageItem JUNE = new LanguageItem();
    public static final LanguageItem JULY = new LanguageItem();
    public static final LanguageItem AUGUST = new LanguageItem();
    public static final LanguageItem SEPTEMBER = new LanguageItem();
    public static final LanguageItem OCTOBER = new LanguageItem();
    public static final LanguageItem NOVEMBER = new LanguageItem();
    public static final LanguageItem DECEMBER = new LanguageItem();
    public static final LanguageItem[] MONTH = new LanguageItem[]{JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER};
    public static final LanguageItem QUARTER = new LanguageItem();
    public static final LanguageItem EARLY = new LanguageItem();
    public static final LanguageItem MID = new LanguageItem();
    public static final LanguageItem LATE = new LanguageItem();
    public static final LanguageItem[] TEN_DAY = new LanguageItem[]{EARLY, MID, LATE};
    public static final LanguageItem FIRST_HALF = new LanguageItem();
    public static final LanguageItem SECOND_HALF = new LanguageItem();
    public static final LanguageItem[] HALF_YEAR = new LanguageItem[]{FIRST_HALF, SECOND_HALF};
    public static final LanguageItem WEEK = new LanguageItem();
    public static final LanguageItem CUSTOM = new LanguageItem();
    public static final LanguageItem YEAR = new LanguageItem();
    public static final LanguageItem DAY = new LanguageItem();
    public static final LanguageItem SEPARATOR = new LanguageItem();
    public static final LanguageItem NUMBER = new LanguageItem();

    static {
        ChinesePeriod.initLanguageItem();
        EnglishPeriod.initLanguageItem();
    }
}

