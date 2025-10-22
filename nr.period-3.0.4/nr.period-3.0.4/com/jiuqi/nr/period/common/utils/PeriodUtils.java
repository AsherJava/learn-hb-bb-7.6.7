/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodConsts
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodWrapper
 */
package com.jiuqi.nr.period.common.utils;

import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodConsts;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.period.common.rest.PeriodDataObject;
import com.jiuqi.nr.period.common.rest.PeriodObject;
import com.jiuqi.nr.period.common.tree.Data;
import com.jiuqi.nr.period.common.tree.TreeObj;
import com.jiuqi.nr.period.common.utils.NrPeriodConst;
import com.jiuqi.nr.period.common.utils.StringUtils;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.period.modal.impl.PeriodDataDefineImpl;
import com.jiuqi.nr.period.util.TitleState;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PeriodUtils {
    private static final Logger logger = LoggerFactory.getLogger(PeriodUtils.class);
    public static String ZSplit = "?";

    public static boolean isPeriod(String type) {
        boolean flag = false;
        return flag;
    }

    public static boolean isDefault(PeriodType type) {
        boolean isDefault = false;
        switch (type) {
            case YEAR: 
            case HALFYEAR: 
            case SEASON: 
            case MONTH: 
            case TENDAY: 
            case WEEK: 
            case DAY: {
                isDefault = true;
            }
        }
        return isDefault;
    }

    public static String autoMonthSimpleTitle(PeriodType type, int month) {
        if (!PeriodType.MONTH.equals((Object)type)) {
            return null;
        }
        if (month <= 0 || month >= 13) {
            return month + "\u671f";
        }
        return month + "\u6708";
    }

    public static boolean isPeriod13(String code, PeriodType periodType) {
        if (PeriodType.CUSTOM.equals((Object)periodType)) {
            return false;
        }
        for (String t : NrPeriodConst.CREATE_PERIOD_DATA_TABLE) {
            if (!t.equals(code)) continue;
            return false;
        }
        return true;
    }

    public static boolean isPeriod13Data(PeriodType periodType, String code) {
        if (PeriodType.MONTH.equals((Object)periodType) && StringUtils.isNotEmpty(code) && code.length() == 9) {
            try {
                int num = Integer.parseInt(code.substring(5));
                if (num == 0 || num >= 13) {
                    return true;
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return false;
    }

    public static Date[] transPeriod13Data(String period) {
        if (StringUtils.isNotEmpty(period) && period.length() == 9) {
            String year = period.substring(0, 4);
            String type = period.substring(4, 5);
            if (type.equals(PeriodUtils.getPeriodType(PeriodType.MONTH))) {
                try {
                    int qi = Integer.parseInt(period.substring(5));
                    String newStr = "";
                    newStr = qi == 0 ? year + type + "0001" : (qi > 12 ? year + type + "0012" : period);
                    return new Date[]{PeriodUtils.getStartDateOfPeriod(newStr, true), PeriodUtils.getStartDateOfPeriod(newStr, false)};
                }
                catch (Exception e) {
                    throw new RuntimeException("\u65f6\u671f\u6570\u636e\u683c\u5f0f\u9519\u8bef");
                }
            }
            if (type.equals(PeriodUtils.getPeriodType(PeriodType.CUSTOM))) {
                throw new RuntimeException("\u672c\u63a5\u53e3\u4e0d\u652f\u6301\u4e0d\u5b9a\u671f\u8f6c\u6362");
            }
            return new Date[]{PeriodUtils.getStartDateOfPeriod(period, true), PeriodUtils.getStartDateOfPeriod(period, false)};
        }
        throw new RuntimeException("\u65f6\u671f\u6570\u636e\u683c\u5f0f\u9519\u8bef");
    }

    public static PeriodObject defineToObject(IPeriodEntity periodDefine) {
        PeriodObject periodObject = new PeriodObject();
        periodObject.setKey(periodDefine.getKey());
        periodObject.setCode(PeriodUtils.removePerfix(periodDefine.getCode()));
        periodObject.setTitle(periodDefine.getTitle());
        periodObject.setType(String.valueOf((char)periodDefine.getType().code()));
        periodObject.setPeriodPropertyGroup(periodDefine.getPeriodPropertyGroup());
        return periodObject;
    }

    public static PeriodDataObject defineToObject(IPeriodRow periodDataDefine, IPeriodEntity iPeriodEntity) {
        PeriodDataObject periodDataObject = new PeriodDataObject();
        periodDataObject.setKey(periodDataDefine.getKey());
        periodDataObject.setCode(periodDataDefine.getCode());
        periodDataObject.setTitle(periodDataDefine.getTitle());
        periodDataObject.setAlias(periodDataDefine.getAlias());
        periodDataObject.setType(PeriodUtils.getPeriodType(iPeriodEntity.getPeriodType()));
        if (periodDataDefine.getStartDate() != null) {
            periodDataObject.setStartdate(PeriodUtils.dateToString(periodDataDefine.getStartDate()));
        }
        if (periodDataDefine.getEndDate() != null) {
            periodDataObject.setEnddate(PeriodUtils.dateToString(periodDataDefine.getEndDate()));
        }
        if (StringUtils.isEmpty(periodDataDefine.getSimpleTitle())) {
            periodDataObject.setSimpleTitle(PeriodUtils.getDefaultShowTitle(iPeriodEntity.getType(), periodDataDefine));
        } else {
            periodDataObject.setSimpleTitle(periodDataDefine.getSimpleTitle());
        }
        return periodDataObject;
    }

    public static String getDefaultShowTitle(PeriodType type, IPeriodRow iPeriodRow) {
        String showTitle = iPeriodRow.getSimpleTitle();
        switch (type) {
            case YEAR: {
                showTitle = iPeriodRow.getYear() + "\u5e74";
                break;
            }
            case HALFYEAR: {
                if (iPeriodRow.getCode().contains("0001")) {
                    showTitle = "\u4e0a\u534a\u5e74";
                    break;
                }
                if (!iPeriodRow.getCode().contains("0002")) break;
                showTitle = "\u4e0b\u534a\u5e74";
                break;
            }
            case SEASON: {
                if (iPeriodRow.getQuarter() == 1) {
                    showTitle = "\u7b2c\u4e00\u5b63\u5ea6";
                    break;
                }
                if (iPeriodRow.getQuarter() == 2) {
                    showTitle = "\u7b2c\u4e8c\u5b63\u5ea6";
                    break;
                }
                if (iPeriodRow.getQuarter() == 3) {
                    showTitle = "\u7b2c\u4e09\u5b63\u5ea6";
                    break;
                }
                if (iPeriodRow.getQuarter() != 4) break;
                showTitle = "\u7b2c\u56db\u5b63\u5ea6";
                break;
            }
            case MONTH: {
                int qi = Integer.parseInt(iPeriodRow.getCode().substring(5));
                if (qi >= 1 && qi <= 12) {
                    showTitle = qi + "\u6708";
                    break;
                }
                showTitle = qi + "\u671f";
                break;
            }
            case TENDAY: {
                if (iPeriodRow.getDay() == 1) {
                    showTitle = "\u4e0a\u65ec";
                    break;
                }
                if (iPeriodRow.getDay() == 11) {
                    showTitle = "\u4e2d\u65ec";
                    break;
                }
                if (iPeriodRow.getDay() != 21) break;
                showTitle = "\u4e0b\u65ec";
                break;
            }
            case WEEK: {
                String substring = iPeriodRow.getCode().substring(5);
                try {
                    int z = Integer.parseInt(substring);
                    StringBuffer title = new StringBuffer("\u7b2c" + z + "\u5468").append(" ");
                    Calendar calendarStart = Calendar.getInstance();
                    calendarStart.setTime(iPeriodRow.getStartDate());
                    int startMonth = calendarStart.get(2) + 1;
                    int startDay = calendarStart.get(5);
                    Calendar calendarEnd = Calendar.getInstance();
                    calendarEnd.setTime(iPeriodRow.getEndDate());
                    int endMonth = calendarEnd.get(2) + 1;
                    int endDay = calendarEnd.get(5);
                    title.append(startMonth + "." + startDay).append('-');
                    title.append(endMonth + "." + endDay);
                    showTitle = title.toString();
                }
                catch (Exception e) {
                    showTitle = iPeriodRow.getTitle();
                }
                break;
            }
            case DAY: {
                showTitle = iPeriodRow.getDay() + "";
                break;
            }
            case CUSTOM: {
                showTitle = iPeriodRow.getTitle();
                break;
            }
        }
        return showTitle;
    }

    public static String dateToString(Date date) {
        String s = "";
        if (date != null) {
            SimpleDateFormat inSdf = new SimpleDateFormat("yyyyMMdd");
            s = inSdf.format(date);
        }
        return s;
    }

    public static List<TreeObj> ctrateTree() {
        ArrayList<TreeObj> tree = new ArrayList<TreeObj>();
        TreeObj treeNode1 = new TreeObj();
        treeNode1.setId(NrPeriodConst.DEFAULT_PERIOD_NODE);
        treeNode1.setCode(NrPeriodConst.DEFAULT_PERIOD_NODE);
        treeNode1.setTitle(NrPeriodConst.DEFAULT_PERIOD_NODE_TITLE);
        treeNode1.setIsLeaf(true);
        treeNode1.setType(NrPeriodConst.DEFAULT_PERIOD_NODE);
        Data data1 = new Data();
        data1.setKey(NrPeriodConst.DEFAULT_PERIOD_NODE);
        data1.setCode(NrPeriodConst.DEFAULT_PERIOD_NODE);
        data1.setTitle(NrPeriodConst.DEFAULT_PERIOD_NODE_TITLE);
        data1.setType(NrPeriodConst.DEFAULT_PERIOD_NODE);
        treeNode1.setData(data1);
        tree.add(treeNode1);
        TreeObj treeNode2 = new TreeObj();
        treeNode2.setId(NrPeriodConst.CUSTOM_PERIOD_NODE);
        treeNode2.setCode(NrPeriodConst.CUSTOM_PERIOD_NODE);
        treeNode2.setTitle(NrPeriodConst.CUSTOM_PERIOD_NODE_TITLE);
        treeNode2.setIsLeaf(false);
        treeNode2.setType(NrPeriodConst.CUSTOM_PERIOD_NODE);
        Data data2 = new Data();
        data2.setKey(NrPeriodConst.CUSTOM_PERIOD_NODE);
        data2.setCode(NrPeriodConst.CUSTOM_PERIOD_NODE);
        data2.setTitle(NrPeriodConst.CUSTOM_PERIOD_NODE_TITLE);
        data2.setType(NrPeriodConst.CUSTOM_PERIOD_NODE);
        treeNode2.setData(data2);
        treeNode2.setChildren(new ArrayList<TreeObj>());
        tree.add(treeNode2);
        TreeObj treeNode3 = new TreeObj();
        treeNode3.setId(NrPeriodConst.PERIOD_13Y_NODE);
        treeNode3.setCode(NrPeriodConst.PERIOD_13Y_NODE);
        treeNode3.setTitle(NrPeriodConst.PERIOD_13Y_NODE_TITLE);
        treeNode3.setIsLeaf(false);
        treeNode3.setType(NrPeriodConst.PERIOD_13Y_NODE);
        Data data3 = new Data();
        data3.setKey(NrPeriodConst.PERIOD_13Y_NODE);
        data3.setCode(NrPeriodConst.PERIOD_13Y_NODE);
        data3.setTitle(NrPeriodConst.PERIOD_13Y_NODE_TITLE);
        data3.setType(NrPeriodConst.PERIOD_13Y_NODE);
        treeNode3.setData(data3);
        treeNode3.setChildren(new ArrayList<TreeObj>());
        tree.add(treeNode3);
        return tree;
    }

    public static String typeToName(String s) {
        String name = "";
        switch (PeriodUtils.periodOfType(s)) {
            case YEAR: {
                name = PeriodType.YEAR.title();
                break;
            }
            case HALFYEAR: {
                name = PeriodType.HALFYEAR.title();
                break;
            }
            case SEASON: {
                name = PeriodType.SEASON.title();
                break;
            }
            case MONTH: {
                name = PeriodType.MONTH.title();
                break;
            }
            case TENDAY: {
                name = PeriodType.TENDAY.title();
                break;
            }
            case WEEK: {
                name = PeriodType.WEEK.title();
                break;
            }
            case DAY: {
                name = PeriodType.DAY.title();
                break;
            }
            case CUSTOM: {
                name = PeriodType.CUSTOM.title();
                break;
            }
        }
        return name;
    }

    public static String getPeriodFromDate(int periodtype, Date date) {
        if (date == null) {
            date = new Date();
        }
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        String formatDate = "";
        switch (PeriodType.fromType((int)periodtype)) {
            case YEAR: {
                formatDate = year + PeriodUtils.getPeriodType(PeriodType.YEAR) + "0001";
                break;
            }
            case HALFYEAR: {
                formatDate = year + PeriodUtils.getPeriodType(PeriodType.HALFYEAR) + "000" + (int)Math.ceil((double)month / 6.0);
                break;
            }
            case SEASON: {
                formatDate = year + PeriodUtils.getPeriodType(PeriodType.SEASON) + "000" + (int)Math.ceil((double)month / 3.0);
                break;
            }
            case MONTH: {
                formatDate = year + PeriodUtils.getPeriodType(PeriodType.MONTH) + (month < 10 ? "000" + month : "00" + month);
                break;
            }
            case TENDAY: {
                int dayOfMonth = localDate.getDayOfMonth();
                formatDate = year + PeriodUtils.getPeriodType(PeriodType.TENDAY) + PeriodUtils.doFormat((month - 1) * 3 + (dayOfMonth < 11 ? 1 : (dayOfMonth < 21 ? 2 : 3)));
                break;
            }
            case WEEK: {
                int weekNumber = localDate.get(WeekFields.of(DayOfWeek.MONDAY, 7).weekOfYear());
                LocalDate yesterday = localDate.plusDays(-(localDate.getDayOfWeek().getValue() - 1));
                if (year == yesterday.getYear()) {
                    formatDate = year + PeriodUtils.getPeriodType(PeriodType.WEEK) + PeriodUtils.doFormat(weekNumber);
                    break;
                }
                weekNumber = yesterday.get(WeekFields.of(DayOfWeek.MONDAY, 7).weekOfYear());
                formatDate = yesterday.getYear() + PeriodUtils.getPeriodType(PeriodType.WEEK) + PeriodUtils.doFormat(weekNumber);
                break;
            }
            case DAY: 
            case CUSTOM: {
                int dayOfYear = localDate.getDayOfYear();
                formatDate = year + PeriodUtils.getPeriodType(PeriodType.DAY) + PeriodUtils.doFormat(dayOfYear);
                break;
            }
            default: {
                formatDate = year + PeriodUtils.getPeriodType(PeriodType.YEAR) + "0001";
            }
        }
        return formatDate;
    }

    public static String doFormat(int num) {
        if (num < 10) {
            return "000" + num;
        }
        if (num < 100) {
            return "00" + num;
        }
        if (num < 1000) {
            return "0" + num;
        }
        return "" + num;
    }

    public static String removeSuffix(String entityId) {
        if (entityId.indexOf("@PERIOD") != -1) {
            return entityId.substring(0, entityId.length() - "@PERIOD".length());
        }
        return entityId;
    }

    public static String addSuffix(String entityId) {
        if (entityId.indexOf("@PERIOD") == -1) {
            return entityId + "@PERIOD";
        }
        return entityId;
    }

    public static String removeDataSuffix(String value) {
        if (value.indexOf("-DATA") != -1) {
            return value.substring(0, value.length() - "-DATA".length());
        }
        return value;
    }

    public static String addPrefix(String code) {
        if (code.length() <= NrPeriodConst.PREFIX_CODE.length()) {
            return NrPeriodConst.PREFIX_CODE + code;
        }
        if (NrPeriodConst.PREFIX_CODE.equals(code.substring(0, NrPeriodConst.PREFIX_CODE.length()))) {
            return code;
        }
        return NrPeriodConst.PREFIX_CODE + code;
    }

    public static String removePerfix(String code) {
        if (code.length() <= NrPeriodConst.PREFIX_CODE.length()) {
            return code;
        }
        if (NrPeriodConst.PREFIX_CODE.equals(code.substring(0, NrPeriodConst.PREFIX_CODE.length()))) {
            return code.substring(NrPeriodConst.PREFIX_CODE.length());
        }
        return code;
    }

    public static String getDateStrFromPeriod(String period) {
        String name = "";
        try {
            int y = Integer.parseInt(period.substring(0, 4));
            String periodType = period.substring(4, 5);
            int offset = Integer.parseInt(period.substring(5));
            switch (PeriodUtils.periodOfType(periodType)) {
                case YEAR: {
                    name = y + "\u5e74";
                    break;
                }
                case HALFYEAR: {
                    name = y + "\u5e74" + (offset == 1 ? "\u4e0a\u534a\u5e74" : "\u4e0b\u534a\u5e74");
                    break;
                }
                case SEASON: {
                    if (offset == 1) {
                        name = y + "\u5e74\u7b2c\u4e00\u5b63\u5ea6";
                        break;
                    }
                    if (offset == 2) {
                        name = y + "\u5e74\u7b2c\u4e8c\u5b63\u5ea6";
                        break;
                    }
                    if (offset == 3) {
                        name = y + "\u5e74\u7b2c\u4e09\u5b63\u5ea6";
                        break;
                    }
                    name = y + "\u5e74\u7b2c\u56db\u5b63\u5ea6";
                    break;
                }
                case MONTH: {
                    if (offset == 0 || offset > 12) {
                        name = y + "\u5e74" + offset + "\u671f";
                        break;
                    }
                    name = y + "\u5e74" + offset + "\u6708";
                    break;
                }
                case TENDAY: {
                    int m = (int)Math.floor((offset - 1) / 3) + 1;
                    int x = offset % 3;
                    if (x == 1) {
                        name = y + "\u5e74" + m + "\u6708\u4e0a\u65ec";
                        break;
                    }
                    if (x == 2) {
                        name = y + "\u5e74" + m + "\u6708\u4e2d\u65ec";
                        break;
                    }
                    name = y + "\u5e74" + m + "\u6708\u4e0b\u65ec";
                    break;
                }
                case DAY: 
                case CUSTOM: {
                    LocalDate localDate = LocalDate.of(y, Month.JANUARY, 1).plusDays(offset - 1);
                    name = localDate.getYear() + "\u5e74" + localDate.getMonthValue() + "\u6708" + localDate.getDayOfMonth() + "\u65e5";
                    break;
                }
                case WEEK: {
                    name = y + "\u5e74\u7b2c" + offset + "\u5468";
                    break;
                }
                default: {
                    name = y + "\u5e74";
                    break;
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return name;
    }

    public static Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(14, 0);
        calendar.set(calendar.get(1), calendar.get(2), calendar.get(5), 0, 0, 0);
        return calendar;
    }

    public static Calendar getCalendar(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, 0, 0, 0);
        calendar.set(14, 0);
        return calendar;
    }

    public static Date getStartDateOfPeriod(String period, boolean isStart) {
        Date date = null;
        try {
            int y = Integer.parseInt(period.substring(0, 4));
            String periodType = period.substring(4, 5);
            int offset = Integer.parseInt(period.substring(5));
            switch (PeriodUtils.periodOfType(periodType)) {
                case YEAR: {
                    if (isStart) {
                        Calendar calendar = PeriodUtils.getCalendar(y, 0, 1);
                        date = calendar.getTime();
                        break;
                    }
                    Calendar calendar = PeriodUtils.getCalendar(y, 11, Month.DECEMBER.maxLength());
                    date = calendar.getTime();
                    break;
                }
                case HALFYEAR: {
                    if (isStart) {
                        if (offset == 1) {
                            Calendar calendar = PeriodUtils.getCalendar(y, 0, 1);
                            date = calendar.getTime();
                            break;
                        }
                        Calendar calendar = PeriodUtils.getCalendar(y, 6, 1);
                        date = calendar.getTime();
                        break;
                    }
                    if (offset == 1) {
                        Calendar calendar = PeriodUtils.getCalendar(y, 5, Month.JUNE.maxLength());
                        date = calendar.getTime();
                        break;
                    }
                    Calendar calendar = PeriodUtils.getCalendar(y, 11, Month.DECEMBER.maxLength());
                    date = calendar.getTime();
                    break;
                }
                case SEASON: {
                    if (isStart) {
                        if (offset == 1) {
                            Calendar calendar = PeriodUtils.getCalendar(y, 0, 1);
                            date = calendar.getTime();
                            break;
                        }
                        if (offset == 2) {
                            Calendar calendar = PeriodUtils.getCalendar(y, 3, 1);
                            date = calendar.getTime();
                            break;
                        }
                        if (offset == 3) {
                            Calendar calendar = PeriodUtils.getCalendar(y, 6, 1);
                            date = calendar.getTime();
                            break;
                        }
                        Calendar calendar = PeriodUtils.getCalendar(y, 9, 1);
                        date = calendar.getTime();
                        break;
                    }
                    if (offset == 1) {
                        Calendar calendar = PeriodUtils.getCalendar(y, 2, Month.MARCH.maxLength());
                        date = calendar.getTime();
                        break;
                    }
                    if (offset == 2) {
                        Calendar calendar = PeriodUtils.getCalendar(y, 5, Month.JUNE.maxLength());
                        date = calendar.getTime();
                        break;
                    }
                    if (offset == 3) {
                        Calendar calendar = PeriodUtils.getCalendar(y, 8, Month.SEPTEMBER.maxLength());
                        date = calendar.getTime();
                        break;
                    }
                    Calendar calendar = PeriodUtils.getCalendar(y, 11, Month.DECEMBER.maxLength());
                    date = calendar.getTime();
                    break;
                }
                case MONTH: {
                    if (isStart) {
                        Calendar calendar = PeriodUtils.getCalendar(y, offset - 1, 1);
                        date = calendar.getTime();
                        break;
                    }
                    if (offset == 12) {
                        Calendar calendar = PeriodUtils.getCalendar(y + 1, 0, 1);
                        calendar.add(5, -1);
                        date = calendar.getTime();
                        break;
                    }
                    Calendar calendar = PeriodUtils.getCalendar(y, offset, 1);
                    calendar.add(5, -1);
                    date = calendar.getTime();
                    break;
                }
                case TENDAY: {
                    int m = (int)Math.floor((offset - 1) / 3);
                    int x = offset % 3;
                    if (isStart) {
                        if (x == 1) {
                            Calendar calendar = PeriodUtils.getCalendar(y, m, 1);
                            date = calendar.getTime();
                            break;
                        }
                        if (x == 2) {
                            Calendar calendar = PeriodUtils.getCalendar(y, m, 11);
                            date = calendar.getTime();
                            break;
                        }
                        Calendar calendar = PeriodUtils.getCalendar(y, m, 21);
                        date = calendar.getTime();
                        break;
                    }
                    if (x == 1) {
                        Calendar calendar = PeriodUtils.getCalendar(y, m, 10);
                        date = calendar.getTime();
                        break;
                    }
                    if (x == 2) {
                        Calendar calendar = PeriodUtils.getCalendar(y, m, 20);
                        date = calendar.getTime();
                        break;
                    }
                    if (m == 12) {
                        Calendar calendar = PeriodUtils.getCalendar(y + 1, 0, 1);
                        calendar.add(5, -1);
                        date = calendar.getTime();
                        break;
                    }
                    Calendar calendar = PeriodUtils.getCalendar(y, m + 1, 1);
                    calendar.add(5, -1);
                    date = calendar.getTime();
                    break;
                }
                case WEEK: {
                    LocalDate yearOfFirstDaty = LocalDate.of(y, Month.JANUARY, 1);
                    Calendar calendar = PeriodUtils.getCalendar(y, 0, 1);
                    if (yearOfFirstDaty.getDayOfWeek().getValue() != 1) {
                        calendar.add(5, 7 - (yearOfFirstDaty.getDayOfWeek().getValue() - 1));
                    }
                    if (isStart) {
                        calendar.add(5, (offset - 1) * 7);
                        date = calendar.getTime();
                        break;
                    }
                    calendar.add(5, offset * 7 - 1);
                    date = calendar.getTime();
                    break;
                }
                case DAY: 
                case CUSTOM: {
                    Calendar calendarDay = PeriodUtils.getCalendar(y, 0, 1);
                    if (isStart) {
                        calendarDay.add(5, offset - 1);
                        date = calendarDay.getTime();
                        break;
                    }
                    calendarDay.add(5, offset - 1);
                    date = calendarDay.getTime();
                    break;
                }
                default: {
                    if (isStart) {
                        Calendar calendarDef = PeriodUtils.getCalendar(y, 0, 1);
                        date = calendarDef.getTime();
                        break;
                    }
                    Calendar calendarDef = PeriodUtils.getCalendar(y + 1, 0, 1);
                    calendarDef.add(5, -1);
                    date = calendarDef.getTime();
                    break;
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return date;
    }

    public static PeriodType periodOfType(String type) {
        for (int x = 1; x <= 8; ++x) {
            if (!String.valueOf((char)PeriodConsts.typeToCode((int)x)).equals(type)) continue;
            return PeriodType.fromType((int)x);
        }
        return PeriodType.YEAR;
    }

    public static String removeLastPeriod(String code) {
        if ((code = code.toUpperCase()).length() <= NrPeriodConst.OLD_FIX_CODE.length()) {
            return code;
        }
        if (NrPeriodConst.OLD_FIX_CODE.equals(code.substring(code.length() - NrPeriodConst.OLD_FIX_CODE.length()))) {
            return code.substring(0, code.length() - NrPeriodConst.OLD_FIX_CODE.length());
        }
        return code;
    }

    public static String addLastPeriod(String code) {
        if ((code = code.toUpperCase()).length() < NrPeriodConst.OLD_FIX_CODE.length()) {
            return code.concat(NrPeriodConst.OLD_FIX_CODE);
        }
        if (!code.substring(code.length() - NrPeriodConst.OLD_FIX_CODE.length()).equals(NrPeriodConst.OLD_FIX_CODE)) {
            return code.concat(NrPeriodConst.OLD_FIX_CODE);
        }
        return code;
    }

    public static String getPeriodType(PeriodType periodType) {
        return String.valueOf((char)periodType.code());
    }

    public static int getNumberOfDays(IPeriodRow periodDataDefine) {
        Calendar localDateStart = Calendar.getInstance();
        localDateStart.setTime(periodDataDefine.getStartDate());
        Calendar localDateEnd = Calendar.getInstance();
        localDateEnd.setTime(periodDataDefine.getEndDate());
        int dayOfYear = localDateEnd.get(6);
        if (localDateStart.get(1) == periodDataDefine.getYear()) {
            dayOfYear = dayOfYear - localDateStart.get(6) + 1;
        }
        return dayOfYear;
    }

    public static int getMonthOfQuarter(int month) {
        int quarter = 0;
        switch (month) {
            case 1: 
            case 2: 
            case 3: {
                quarter = 1;
                break;
            }
            case 4: 
            case 5: 
            case 6: {
                quarter = 2;
                break;
            }
            case 7: 
            case 8: 
            case 9: {
                quarter = 3;
                break;
            }
            case 10: 
            case 11: 
            case 12: {
                quarter = 4;
                break;
            }
        }
        return quarter;
    }

    public static int getNumberOfDaysWeek(IPeriodRow periodDataDefine) {
        Date from = periodDataDefine.getStartDate();
        Date to = periodDataDefine.getEndDate();
        return (int)(to.getTime() - from.getTime()) / 86400000 + 1;
    }

    public static Date minDate(List<PeriodDataDefineImpl> periodDataDefines) {
        Date date = null;
        for (IPeriodRow iPeriodRow : periodDataDefines) {
            if (iPeriodRow.getStartDate() == null) continue;
            if (date != null) {
                if (!date.after(iPeriodRow.getStartDate())) continue;
                date = iPeriodRow.getStartDate();
                continue;
            }
            date = iPeriodRow.getStartDate();
        }
        return date;
    }

    public static Date maxDate(List<PeriodDataDefineImpl> periodDataDefines) {
        Date date = null;
        for (IPeriodRow iPeriodRow : periodDataDefines) {
            if (iPeriodRow.getEndDate() == null) continue;
            if (date != null) {
                if (!date.before(iPeriodRow.getEndDate())) continue;
                date = iPeriodRow.getEndDate();
                continue;
            }
            date = iPeriodRow.getEndDate();
        }
        return date;
    }

    public static IPeriodRow minDefine(List<IPeriodRow> periodDataDefines) {
        Date date = null;
        IPeriodRow define = null;
        for (IPeriodRow periodDataDefine : periodDataDefines) {
            if (periodDataDefine.getStartDate() == null) continue;
            if (date != null) {
                if (!date.after(periodDataDefine.getStartDate())) continue;
                define = periodDataDefine;
                date = periodDataDefine.getStartDate();
                continue;
            }
            define = periodDataDefine;
            date = periodDataDefine.getStartDate();
        }
        if (null == define && periodDataDefines.size() != 0) {
            define = periodDataDefines.get(0);
        }
        return define;
    }

    public static IPeriodRow minRow(List<PeriodDataDefineImpl> periodDataDefines) {
        Date date = null;
        IPeriodRow define = null;
        for (IPeriodRow iPeriodRow : periodDataDefines) {
            if (iPeriodRow.getStartDate() == null) continue;
            if (date != null) {
                if (!date.after(iPeriodRow.getStartDate())) continue;
                define = iPeriodRow;
                date = iPeriodRow.getStartDate();
                continue;
            }
            define = iPeriodRow;
            date = iPeriodRow.getStartDate();
        }
        if (null == define && periodDataDefines.size() != 0) {
            define = periodDataDefines.get(0);
        }
        return define;
    }

    public static IPeriodRow maxDefine(List<IPeriodRow> periodDataDefines) {
        Date date = null;
        IPeriodRow define = null;
        for (IPeriodRow periodDataDefine : periodDataDefines) {
            if (periodDataDefine.getEndDate() == null) continue;
            if (date != null) {
                if (!date.before(periodDataDefine.getEndDate())) continue;
                define = periodDataDefine;
                date = periodDataDefine.getEndDate();
                continue;
            }
            define = periodDataDefine;
            date = periodDataDefine.getEndDate();
        }
        if (null == define && periodDataDefines.size() != 0) {
            define = periodDataDefines.get(periodDataDefines.size() - 1);
        }
        return define;
    }

    public static IPeriodRow maxRow(List<PeriodDataDefineImpl> periodDataDefines) {
        Date date = null;
        IPeriodRow define = null;
        for (IPeriodRow iPeriodRow : periodDataDefines) {
            if (iPeriodRow.getEndDate() == null) continue;
            if (date != null) {
                if (!date.before(iPeriodRow.getEndDate())) continue;
                define = iPeriodRow;
                date = iPeriodRow.getEndDate();
                continue;
            }
            define = iPeriodRow;
            date = iPeriodRow.getEndDate();
        }
        if (null == define && periodDataDefines.size() != 0) {
            define = periodDataDefines.get(periodDataDefines.size() - 1);
        }
        return define;
    }

    public static void interfaceTransform(List a, List b) {
        a.forEach(p -> b.add(p));
    }

    public static String periodGetViewKey(String entityKey) {
        String viewKey = "";
        switch (entityKey) {
            case "N": {
                viewKey = "91152fb1-7ade-4a10-af50-de9559e3175c";
                break;
            }
            case "H": {
                viewKey = "d2a139dc-46b5-4c51-b544-aec599b44342";
                break;
            }
            case "J": {
                viewKey = "fec4de65-2c40-4f73-8968-b3b07ada2e70";
                break;
            }
            case "Y": {
                viewKey = "44ba5c4e-dd80-41a9-9e96-18d21489a927";
                break;
            }
            case "X": {
                viewKey = "934b4d14-1531-4ca3-9fe8-806897867027";
                break;
            }
            case "Z": {
                viewKey = "3fb1c7c9-7570-4c02-803a-1dc4d68175ef";
                break;
            }
            case "R": {
                viewKey = "e826706d-7a93-40b8-9e81-0cb107e38002";
                break;
            }
        }
        return viewKey;
    }

    public static int comparePeriod(String o1, String o2) {
        int res = 0;
        if (StringUtils.isNotEmpty(o1) && StringUtils.isNotEmpty(o2)) {
            if (o1.equals(o2)) {
                return 0;
            }
            try {
                String typeA = o1.substring(4, 5);
                String typeB = o2.substring(4, 5);
                if (typeA.equals(typeB)) {
                    int qB;
                    int qA;
                    int yearB;
                    int yearA = Integer.parseInt(o1.substring(0, 4));
                    res = yearA > (yearB = Integer.parseInt(o2.substring(0, 4))) ? 1 : (yearA < yearB ? -1 : ((qA = Integer.parseInt(o1.substring(5))) >= (qB = Integer.parseInt(o2.substring(5))) ? 1 : -1));
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return res;
    }

    public static String nextPeriod(String periodCode) {
        DefaultPeriodAdapter adapter = new DefaultPeriodAdapter();
        PeriodWrapper periodWrapper = new PeriodWrapper(periodCode);
        adapter.nextPeriod(periodWrapper);
        return periodWrapper.toString();
    }

    public static String priorPeriod(String periodCode) {
        DefaultPeriodAdapter adapter = new DefaultPeriodAdapter();
        PeriodWrapper periodWrapper = new PeriodWrapper(periodCode);
        adapter.priorPeriod(periodWrapper);
        return periodWrapper.toString();
    }

    public static String completionCode(int year, String type, int num) {
        StringBuffer sbf = new StringBuffer();
        sbf.append(year);
        sbf.append(type);
        if (num >= 0 && num <= 9) {
            sbf.append("000").append(num);
        } else if (num >= 10 && num <= 99) {
            sbf.append("00").append(num);
        } else if (num >= 100 && num <= 999) {
            sbf.append("0").append(num);
        } else if (num <= 9999) {
            sbf.append(num);
        }
        return sbf.toString();
    }

    public static Date completionDate(int year, int num, boolean isStart) {
        if (isStart) {
            if (num == 0) {
                return PeriodUtils.getCalendar(year, 0, 1).getTime();
            }
            if (num >= 13) {
                Calendar calendar = PeriodUtils.getCalendar(year, 11, 1);
                return calendar.getTime();
            }
        } else {
            if (num == 0) {
                Calendar calendar = PeriodUtils.getCalendar(year, 1, 1);
                calendar.add(5, -1);
                return calendar.getTime();
            }
            if (num >= 13) {
                Calendar calendar = PeriodUtils.getCalendar(year + 1, 0, 1);
                calendar.add(5, -1);
                return calendar.getTime();
            }
        }
        return null;
    }

    public static String completionTimeKey(int year, int num) {
        StringBuffer sbf = new StringBuffer();
        sbf.append(year);
        if (num >= 0 && num <= 9) {
            sbf.append("0").append(num);
        } else if (num >= 10 && num <= 99) {
            sbf.append(num);
        }
        sbf.append("01");
        return sbf.toString();
    }

    public static boolean checkTitle(IPeriodEntity periodEntity) {
        return (periodEntity.getDataType() & TitleState.TITLE.getValue()) == TitleState.TITLE.getValue();
    }

    public static boolean checkSimpleTitle(IPeriodEntity periodEntity) {
        return (periodEntity.getDataType() & TitleState.SIMPLE_TITLE.getValue()) == TitleState.SIMPLE_TITLE.getValue();
    }
}

