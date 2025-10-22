/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.excelUpload;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.poi.ss.usermodel.DataFormatter;

public interface ExcelDateTypeConstants {
    public static final String COMMON_DATE_FORMAT_XQ = "\u661f\u671f";
    public static final String COMMON_DATE_FORMAT_Z = "\u5468";
    public static final List<Short> EXCEL_FORMAT_INDEX_07_TIME = Arrays.asList((short)18, (short)19, (short)20, (short)21, (short)32, (short)33, (short)45, (short)46, (short)47, (short)55, (short)56, (short)176, (short)177, (short)178, (short)179, (short)180, (short)181, (short)182, (short)183, (short)184, (short)185, (short)186);
    public static final List<Short> EXCEL_FORMAT_INDEX_07_DATE = Arrays.asList((short)14, (short)15, (short)16, (short)17, (short)22, (short)30, (short)31, (short)57, (short)58, (short)187, (short)188, (short)189, (short)190, (short)191, (short)192, (short)193, (short)194, (short)195, (short)196, (short)197, (short)198, (short)199, (short)200, (short)201, (short)202, (short)203, (short)204, (short)205, (short)206, (short)207, (short)208);
    public static final List<Short> EXCEL_FORMAT_INDEX_03_TIME = Arrays.asList((short)18, (short)19, (short)20, (short)21, (short)32, (short)33, (short)45, (short)46, (short)47, (short)55, (short)56, (short)176, (short)177, (short)178, (short)179, (short)180, (short)181, (short)182, (short)183, (short)184, (short)185, (short)186);
    public static final List<Short> EXCEL_FORMAT_INDEX_03_DATE = Arrays.asList((short)14, (short)15, (short)16, (short)17, (short)22, (short)30, (short)31, (short)57, (short)58, (short)187, (short)188, (short)189, (short)190, (short)191, (short)192, (short)193, (short)194, (short)195, (short)196, (short)197, (short)198, (short)199, (short)200, (short)201, (short)202, (short)203, (short)204, (short)205, (short)206, (short)207, (short)208);
    public static final List<String> EXCEL_FORMAT_INDEX_DATE_NYRSFM_STRING = Arrays.asList("yyyy-mm-dd\\ hh:mm", "yyyy-mm-dd\\ hh:mm:dd", "yyyy/m/d\\ h:mm;@", "m/d/yy h:mm", "yyyy/m/d\\ h:mm\\ AM/PM", "[$-409]yyyy/m/d\\ h:mm\\ AM/PM;@", "yyyy/mm/dd\\ hh:mm:dd", "yyyy/mm/dd\\ hh:mm", "yyyy/m/d\\ h:m", "yyyy/m/d\\ h:m:s", "yyyy/m/d\\ h:mm", "m/d/yy h:mm;@", "yyyy/m/d\\ h:mm\\ AM/PM;@");
    public static final List<String> EXCEL_FORMAT_INDEX_DATE_NYR_STRING = Arrays.asList("yyyy\\-mm\\-dd", "yyyy-mm-dd", "yyyy-m-d", "yyyy\\-m\\-d", "m/d/yy", "[$-F800]dddd\\,\\ mmmm\\ dd\\,\\ yyyy", "[DBNum1][$-804]yyyy\"\u5e74\"m\"\u6708\"d\"\u65e5\";@", "yyyy\"\u5e74\"m\"\u6708\"d\"\u65e5\";@", "yyyy/m/d;@", "yy/m/d;@", "m/d/yy;@", "[$-409]d/mmm/yy", "[$-409]dd/mmm/yy;@", "reserved-0x1F", "reserved-0x1E", "mm/dd/yy;@", "yyyy/mm/dd", "d-mmm-yy", "[$-409]d\\-mmm\\-yy;@", "[$-409]d\\-mmm\\-yy", "[$-409]dd\\-mmm\\-yy;@", "[$-409]dd\\-mmm\\-yy", "[DBNum1][$-804]yyyy\"\u5e74\"m\"\u6708\"d\"\u65e5\"", "yy/m/d", "mm/dd/yy", "dd\\-mmm\\-yy");
    public static final List<String> EXCEL_FORMAT_INDEX_DATE_NY_STRING = Arrays.asList("yyyy-mm", "yyyy-m", "[DBNum1][$-804]yyyy\"\u5e74\"m\"\u6708\";@", "[DBNum1][$-804]yyyy\"\u5e74\"m\"\u6708\"", "yyyy\"\u5e74\"m\"\u6708\";@", "yyyy\"\u5e74\"m\"\u6708\"", "[$-409]mmm\\-yy;@", "[$-409]mmm\\-yy", "[$-409]mmm/yy;@", "[$-409]mmm/yy", "[$-409]mmmm/yy;@", "[$-409]mmmm/yy", "[$-409]mmmmm/yy;@", "[$-409]mmmmm/yy", "mmm-yy", "yyyy/mm", "mmm/yyyy", "[$-409]mmmm\\-yy;@", "[$-409]mmmmm\\-yy;@", "mmmm\\-yy", "mmmmm\\-yy");
    public static final List<String> EXCEL_FORMAT_INDEX_DATE_YR_STRING = Arrays.asList("mm-dd", "m-d", "[DBNum1][$-804]m\"\u6708\"d\"\u65e5\";@", "[DBNum1][$-804]m\"\u6708\"d\"\u65e5\"", "m\"\u6708\"d\"\u65e5\";@", "m\"\u6708\"d\"\u65e5\"", "[$-409]d/mmm;@", "[$-409]d/mmm", "m/d;@", "m/d", "d-mmm", "d-mmm;@", "mm/dd", "mm/dd;@", "[$-409]d\\-mmm;@", "[$-409]d\\-mmm");
    public static final List<String> EXCEL_FORMAT_INDEX_DATE_XQ_STRING = Arrays.asList("[$-804]aaaa;@", "[$-804]aaaa");
    public static final List<String> EXCEL_FORMAT_INDEX_DATE_Z_STRING = Arrays.asList("[$-804]aaa;@", "[$-804]aaa");
    public static final List<String> EXCEL_FORMAT_INDEX_DATE_Y_STRING = Arrays.asList("[$-409]mmmmm;@", "mmmmm", "[$-409]mmmmm");
    public static final List<String> EXCEL_FORMAT_INDEX_TIME_STRING = Arrays.asList("mm:ss.0", "h:mm", "h:mm\\ AM/PM", "h:mm:ss", "h:mm:ss\\ AM/PM", "reserved-0x20", "reserved-0x21", "[DBNum1]h\"\u65f6\"mm\"\u5206\"", "[DBNum1]\u4e0a\u5348/\u4e0b\u5348h\"\u65f6\"mm\"\u5206\"", "mm:ss", "[h]:mm:ss", "h:mm:ss;@", "[$-409]h:mm:ss\\ AM/PM;@", "h:mm;@", "[$-409]h:mm\\ AM/PM;@", "h\"\u65f6\"mm\"\u5206\";@", "h\"\u65f6\"mm\"\u5206\"\\ AM/PM;@", "h\"\u65f6\"mm\"\u5206\"ss\"\u79d2\";@", "h\"\u65f6\"mm\"\u5206\"ss\"\u79d2\"_ AM/PM;@", "\u4e0a\u5348/\u4e0b\u5348h\"\u65f6\"mm\"\u5206\";@", "\u4e0a\u5348/\u4e0b\u5348h\"\u65f6\"mm\"\u5206\"ss\"\u79d2\";@", "[DBNum1][$-804]h\"\u65f6\"mm\"\u5206\";@", "[DBNum1][$-804]\u4e0a\u5348/\u4e0b\u5348h\"\u65f6\"mm\"\u5206\";@", "h:mm AM/PM", "h:mm:ss AM/PM", "[$-F400]h:mm:ss\\ AM/PM");
    public static final Short EXCEL_FORMAT_INDEX_DATA_EXACT_NY = 57;
    public static final Short EXCEL_FORMAT_INDEX_DATA_EXACT_YR = 58;
    public static final List<Short> EXCEL_FORMAT_INDEX_TIME_EXACT = Arrays.asList((short)55, (short)56);
    public static final String[] WEEK_DAYS = new String[]{"\u65e5", "\u4e00", "\u4e8c", "\u4e09", "\u56db", "\u4e94", "\u516d"};
    public static final DataFormatter EXCEL_07_DATA_FORMAT = new DataFormatter();
    public static final Pattern PATTERN_DECIMAL = Pattern.compile("^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+)$");
    public static final String EXCEL_SUFFIX_07 = "xlsx";
    public static final String EXCEL_SUFFIX_03 = "xls";

    public static String dateToWeek(Date date) {
        if (date == null) {
            return "";
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(7) - 1;
        if (w < 0) {
            w = 0;
        }
        return WEEK_DAYS[w];
    }
}

