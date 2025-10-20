/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.DistributionException
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  com.jiuqi.bi.core.nodekeeper.Node
 */
package com.jiuqi.va.biz.utils;

import com.jiuqi.bi.core.nodekeeper.DistributionException;
import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.bi.core.nodekeeper.Node;
import com.jiuqi.va.biz.intf.data.DataTarget;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.biz.ruler.intf.CheckResult;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

public class Utils {
    private static final Logger logger = LoggerFactory.getLogger(Utils.class);
    private static ThreadLocal<DateFormat> localDateFormat = new ThreadLocal<DateFormat>(){

        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
    private static ThreadLocal<DateFormat> localDateFormat2 = new ThreadLocal<DateFormat>(){

        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy/MM/dd");
        }
    };
    private static ThreadLocal<DateFormat> localDateFormat3 = new ThreadLocal<DateFormat>(){

        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd");
        }
    };
    private static ThreadLocal<DateFormat> localDateTimeFormat = new ThreadLocal<DateFormat>(){

        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
    private static ThreadLocal<DateFormat> localDateTimeFormat2 = new ThreadLocal<DateFormat>(){

        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        }
    };
    private static ThreadLocal<DateFormat> localDateTimeFormat3 = new ThreadLocal<DateFormat>(){

        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMddHHmmss");
        }
    };
    private static ThreadLocal<DateFormat> localTimeFormat = new ThreadLocal<DateFormat>(){

        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("HH:mm:ss");
        }
    };
    private static ThreadLocal<DateFormat> localTimeMillisFormat = new ThreadLocal<DateFormat>(){

        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZ");
        }
    };
    private static ThreadLocal<NumberFormat> localNumberFormat = new ThreadLocal<NumberFormat>(){

        @Override
        protected NumberFormat initialValue() {
            NumberFormat format = NumberFormat.getInstance();
            format.setMaximumFractionDigits(2);
            format.setMinimumFractionDigits(2);
            if (format instanceof DecimalFormat) {
                ((DecimalFormat)format).setDecimalSeparatorAlwaysShown(true);
            }
            return format;
        }
    };
    private static ThreadLocal<DecimalFormat> localNumberToString = new ThreadLocal<DecimalFormat>(){

        @Override
        protected DecimalFormat initialValue() {
            return new DecimalFormat("###################.###########");
        }
    };
    private static ThreadLocal<Calendar> localCalendar = new ThreadLocal<Calendar>(){

        @Override
        protected Calendar initialValue() {
            return Calendar.getInstance();
        }
    };
    private static ThreadLocal<MessageDigest> localMD5 = new ThreadLocal<MessageDigest>(){

        @Override
        protected MessageDigest initialValue() {
            try {
                byte[] sec = new byte[]{77, 68, 53};
                return MessageDigest.getInstance(new String(sec));
            }
            catch (NoSuchAlgorithmException nsae) {
                throw new InternalError("MD5 not supported", nsae);
            }
        }
    };
    public static final Charset utf8 = Charset.forName("UTF-8");

    public static final String formatDate(Date date) {
        return localDateFormat.get().format(date);
    }

    public static final String formatDate2(Date date) {
        return localDateFormat2.get().format(date);
    }

    public static final String formatDate3(Date date) {
        return localDateFormat3.get().format(date);
    }

    public static Date parseDate(String s) {
        if (s.contains("/")) {
            if (s.contains("-")) {
                throw new RuntimeException();
            }
            return Utils.parseDate2(s);
        }
        if (!s.contains("-")) {
            return Utils.parseDate3(s);
        }
        if (s.contains(":")) {
            return Utils.parseDateTime(s);
        }
        DateFormat format = localDateFormat.get();
        try {
            return format.parse(s);
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date parseDate2(String s) {
        if (s.contains("-")) {
            if (s.contains("/")) {
                throw new RuntimeException();
            }
            return Utils.parseDate(s);
        }
        if (!s.contains("/")) {
            return Utils.parseDate3(s);
        }
        if (s.contains(":")) {
            return Utils.parseDateTime2(s);
        }
        DateFormat format = localDateFormat2.get();
        try {
            return format.parse(s);
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date parseDate3(String s) {
        if (s.contains("/")) {
            if (s.contains("-")) {
                throw new RuntimeException();
            }
            return Utils.parseDate2(s);
        }
        if (s.contains("-")) {
            return Utils.parseDate(s);
        }
        if (s.length() == 14) {
            return Utils.parseDateTime3(s);
        }
        DateFormat format = localDateFormat3.get();
        try {
            return format.parse(s);
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static final String formatDateTime(Date date) {
        return localDateTimeFormat.get().format(date);
    }

    public static final String formatDateTime2(Date date) {
        return localDateTimeFormat2.get().format(date);
    }

    public static final String formatDateTime3(Date date) {
        return localDateTimeFormat3.get().format(date);
    }

    public static Date parseDateTime(String s) {
        if (s.contains("/")) {
            if (s.contains("-")) {
                throw new RuntimeException();
            }
            return Utils.parseDateTime2(s);
        }
        if (!s.contains("-")) {
            return Utils.parseDateTime3(s);
        }
        if (!s.contains(":")) {
            return Utils.parseDate(s);
        }
        DateFormat format = localDateTimeFormat.get();
        try {
            return format.parse(s);
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date parseDateTime2(String s) {
        if (s.contains("-")) {
            if (s.contains("/")) {
                throw new RuntimeException();
            }
            return Utils.parseDateTime(s);
        }
        if (!s.contains("/")) {
            return Utils.parseDateTime3(s);
        }
        if (!s.contains(":")) {
            return Utils.parseDate2(s);
        }
        DateFormat format = localDateTimeFormat2.get();
        try {
            return format.parse(s);
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date parseDateTime3(String s) {
        if (s.contains("/")) {
            if (s.contains("-")) {
                throw new RuntimeException();
            }
            return Utils.parseDateTime2(s);
        }
        if (s.contains("-")) {
            return Utils.parseDateTime(s);
        }
        if (s.length() == 8) {
            return Utils.parseDate3(s);
        }
        DateFormat format = localDateTimeFormat3.get();
        try {
            return format.parse(s);
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static final String formatTime(Date date) {
        return localTimeFormat.get().format(date);
    }

    public static Date parseTime(String s) {
        DateFormat format = localTimeFormat.get();
        try {
            return format.parse(s);
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static final String formatTimeMillions(Date timeMillis) {
        return localTimeMillisFormat.get().format(timeMillis);
    }

    public static Date parseTimeMillions(String s) {
        DateFormat format = localTimeMillisFormat.get();
        try {
            return format.parse(s);
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static UUID toUUID(byte[] bytes) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        return new UUID(bb.getLong(), bb.getLong());
    }

    public static byte[] toBytes(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }

    public static final String formatCurrency(double value) {
        return localNumberFormat.get().format(value);
    }

    public static final String formatNumber(double value, int digits) {
        if (digits == 0) {
            return Math.round(value) + "";
        }
        if (digits == 2) {
            return localNumberFormat.get().format(value);
        }
        NumberFormat format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(digits);
        format.setMinimumFractionDigits(digits);
        if (format instanceof DecimalFormat) {
            ((DecimalFormat)format).setDecimalSeparatorAlwaysShown(true);
        }
        return format.format(value);
    }

    public static final String toString(double value) {
        return localNumberToString.get().format(value);
    }

    public static final String toString(Throwable e) {
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));
        return writer.toString();
    }

    public static final Date currentDate() {
        Calendar calendar = localCalendar.get();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar.getTime();
    }

    public static Date toDate(Date date) {
        Calendar calendar = localCalendar.get();
        calendar.setTime(date);
        boolean modified = false;
        if (calendar.get(11) != 0) {
            calendar.set(11, 0);
            modified = true;
        }
        if (calendar.get(12) != 0) {
            calendar.set(12, 0);
            modified = true;
        }
        if (calendar.get(13) != 0) {
            calendar.set(13, 0);
            modified = true;
        }
        if (calendar.get(14) != 0) {
            calendar.set(14, 0);
            modified = true;
        }
        return modified ? calendar.getTime() : date;
    }

    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }

    public static boolean isNotEmpty(String s) {
        return s != null && s.length() > 0;
    }

    public static byte[] md5Bytes(byte[] s) {
        MessageDigest md = localMD5.get();
        return md.digest(s);
    }

    public static UUID md5(byte[] s) {
        return Utils.toUUID(Utils.md5Bytes(s));
    }

    public static UUID normalizeId(Object id) {
        try {
            return Convert.cast(id, UUID.class);
        }
        catch (Exception e) {
            return Utils.md5(Convert.cast(id, String.class).getBytes(utf8));
        }
    }

    public static Map<String, Object> makeMap(Object ... keyValue) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < keyValue.length / 2; ++i) {
            map.put((String)keyValue[i * 2], keyValue[i * 2 + 1]);
        }
        return map;
    }

    public static void join(StringBuilder sb, List<String> values, String delimiter) {
        for (String value : values) {
            sb.append(value).append(delimiter);
        }
        if (values.size() > 0) {
            sb.setLength(sb.length() - 1);
        }
    }

    public static String join(List<String> values, String delimiter) {
        int size = values.size();
        if (size == 0) {
            return "";
        }
        if (size == 1) {
            return values.get(0);
        }
        StringBuilder sb = new StringBuilder();
        Utils.join(sb, values, delimiter);
        return sb.toString();
    }

    public static void setTraceId(String traceId) {
        if (StringUtils.hasText(traceId)) {
            MDC.put("traceid", traceId);
        }
    }

    public static String getTraceId() {
        return MDC.get("traceid");
    }

    public static String getHostName() {
        try {
            Node node = DistributionManager.getInstance().getSelfNode();
            return node.getIp() + ":" + node.getPort();
        }
        catch (DistributionException e) {
            logger.error("get hostname error", e);
            return "unknown";
        }
    }

    public static String convertBiDbType(String dbType) {
        if ("dameng".equals(dbType)) {
            return "DM";
        }
        return dbType;
    }

    public static List<CheckResult> handleErrorMsg(String masterTableName, List<CheckResult> checkMessages) {
        List<CheckResult> unique = checkMessages.stream().filter(Utils.distinctByKey(o -> o.getCheckMessage() + o.getTargetList().map(target -> {
            if (masterTableName.equals(target.getTableName())) {
                return target.getTableName() + target.getFieldName();
            }
            return target.getTableName() + target.getFieldName() + target.getRowIndex() + target.getRowID();
        }).collect(Collectors.toList()))).collect(Collectors.toList());
        unique.sort((result1, result2) -> {
            Integer n;
            Integer n2;
            String tableName;
            Integer o1 = 1;
            Integer o2 = 1;
            Optional<DataTarget> option1 = result1.getTargetList().findAny();
            Optional<DataTarget> option2 = result2.getTargetList().findAny();
            if (option1.isPresent() && (tableName = option1.get().getTableName()) != null && tableName.equals(masterTableName)) {
                n2 = o1;
                n = o1 = Integer.valueOf(o1 + 1);
            }
            if (option2.isPresent() && (tableName = option2.get().getTableName()) != null && tableName.equals(masterTableName)) {
                n2 = o2;
                n = o2 = Integer.valueOf(o2 + 1);
            }
            return o2 - o1;
        });
        return unique;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        return t -> concurrentHashMap.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}

