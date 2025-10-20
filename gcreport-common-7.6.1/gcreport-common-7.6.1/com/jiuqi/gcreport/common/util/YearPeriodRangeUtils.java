/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.DateUtils
 */
package com.jiuqi.gcreport.common.util;

import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.gcreport.common.SelectOptionVO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class YearPeriodRangeUtils {
    private static final int[] RANGEZEROTOTHIRTEEN = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
    private static final int[] RANGEZEROTOTWELVE = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    private static final int[] RANGEONETOTHIRTEEN = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
    private static final int[] RANGEONETOTWELVE = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

    public static List<SelectOptionVO> getPeriodRange(PeriodRangeType rangeType) {
        int[] range;
        switch (rangeType) {
            case ZEROTOTHIRTEEN: {
                range = RANGEZEROTOTHIRTEEN;
                break;
            }
            case ZEROTOTWELVE: {
                range = RANGEZEROTOTWELVE;
                break;
            }
            case ONETOTHIRTEEN: {
                range = RANGEONETOTHIRTEEN;
                break;
            }
            case ONETOTWELVE: {
                range = RANGEONETOTWELVE;
                break;
            }
            default: {
                range = RANGEONETOTWELVE;
            }
        }
        ArrayList<SelectOptionVO> selectOptions = new ArrayList<SelectOptionVO>();
        Arrays.stream(range).forEach(period -> {
            SelectOptionVO selectOption = new SelectOptionVO();
            selectOption.setValue(period);
            selectOption.setLabel(String.valueOf(period));
            selectOptions.add(selectOption);
        });
        return selectOptions;
    }

    public static List<SelectOptionVO> getYearRange(int beforeYear, int afterYear) {
        Calendar calendar = Calendar.getInstance();
        int year = DateUtils.getDateFieldValue((Calendar)calendar, (int)1);
        ArrayList<SelectOptionVO> selectOptions = new ArrayList<SelectOptionVO>();
        for (int curYear = year - beforeYear; curYear <= year + afterYear; ++curYear) {
            SelectOptionVO selectOption = new SelectOptionVO();
            selectOption.setValue(curYear);
            selectOption.setLabel(String.valueOf(curYear));
            selectOptions.add(selectOption);
        }
        return selectOptions;
    }

    public static enum PeriodRangeType {
        ZEROTOTHIRTEEN,
        ZEROTOTWELVE,
        ONETOTHIRTEEN,
        ONETOTWELVE;

    }
}

