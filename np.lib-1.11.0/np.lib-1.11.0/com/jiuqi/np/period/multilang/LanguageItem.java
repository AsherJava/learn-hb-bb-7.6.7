/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.period.multilang;

import com.jiuqi.np.period.PeriodLanguage;
import java.util.HashMap;
import java.util.Map;

public class LanguageItem {
    private Map<PeriodLanguage, String> map = new HashMap<PeriodLanguage, String>();

    public void put(PeriodLanguage language, String str) {
        this.map.put(language, str);
    }

    public String localize(PeriodLanguage language) {
        return this.map.get((Object)language);
    }

    public String localize(PeriodLanguage language, int num) {
        switch (language) {
            case English: {
                if (1 == num) {
                    return num + "st";
                }
                if (2 == num) {
                    return num + "nd";
                }
                if (3 == num) {
                    return num + "rd";
                }
                return num + "th";
            }
        }
        return String.valueOf(num);
    }
}

