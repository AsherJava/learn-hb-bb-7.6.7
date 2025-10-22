/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.security.HtmlUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 */
package com.jiuqi.nr.entity.common;

import com.jiuqi.bi.security.HtmlUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.GregorianCalendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class Utils {
    private static final Logger log = LoggerFactory.getLogger(Utils.class);
    private static final String DATA_TIME = "DATATIME";

    public static String getId(String entityId) {
        Assert.notNull((Object)entityId, "\u5b9e\u4f53ID\u4e0d\u80fd\u4e3a\u7a7a.");
        return entityId.substring(0, !entityId.contains("@") ? entityId.length() : entityId.indexOf("@"));
    }

    public static String getCategory(String entityId) {
        Assert.notNull((Object)entityId, "\u5b9e\u4f53ID\u4e0d\u80fd\u4e3a\u7a7a.");
        return entityId.substring(entityId.indexOf("@") + 1);
    }

    public static String getEntityId(String defineId, String categoryId) {
        Assert.notNull((Object)defineId, "\u5b9e\u4f53\u5b9a\u4e49ID\u4e0d\u80fd\u4e3a\u7a7a.");
        Assert.notNull((Object)categoryId, "\u5b9e\u4f53\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a.");
        return defineId + "@" + categoryId;
    }

    public static void appendEntityId(String entityId, OutputStream outputStream) throws IOException {
        HtmlUtils.cleanUrlXSS((String)entityId);
        byte[] idByte = entityId.getBytes(Charset.defaultCharset());
        byte[] lengthByte = Utils.intToByteArray(entityId.length());
        outputStream.write(lengthByte);
        outputStream.write(idByte);
    }

    public static String readerEntityId(InputStream inputStream) throws IOException {
        byte[] lengthByte = new byte[4];
        inputStream.read(lengthByte);
        int idLength = Utils.byteArrayToInt(lengthByte);
        byte[] id = new byte[idLength];
        inputStream.read(id);
        return new String(id, Charset.defaultCharset());
    }

    public static void appendStream(String entityId, OutputStream outputStream) throws IOException {
        byte[] idByte = entityId.getBytes();
        byte[] lengthByte = Utils.intToByteArray(entityId.length());
        outputStream.write(lengthByte);
        outputStream.write(idByte);
    }

    public static String readerSteam(InputStream inputStream) throws IOException {
        byte[] lengthByte = new byte[4];
        inputStream.read(lengthByte);
        int idLength = Utils.byteArrayToInt(lengthByte);
        byte[] id = new byte[idLength];
        inputStream.read(id);
        return String.valueOf(id);
    }

    public static String generatorGroupId(String groupId, String category) {
        return new StringBuffer(category).append("_").append(groupId).toString();
    }

    public static String getGroupId(String groupId) {
        return groupId.contains("_") ? groupId.substring(groupId.indexOf("_") + 1) : groupId;
    }

    public static String getGroupCategory(String groupId) {
        return groupId.contains("_") ? groupId.substring(0, groupId.indexOf("_")) : null;
    }

    public static byte[] intToByteArray(int i) {
        byte[] result = new byte[]{(byte)(i >> 24 & 0xFF), (byte)(i >> 16 & 0xFF), (byte)(i >> 8 & 0xFF), (byte)(i & 0xFF)};
        return result;
    }

    public static int byteArrayToInt(byte[] bytes) {
        int value = 0;
        for (int i = 0; i < 4; ++i) {
            int shift = (3 - i) * 8;
            value += (bytes[i] & 0xFF) << shift;
        }
        return value;
    }

    public static Date getVersionDate(DimensionValueSet masterKeys, PeriodEngineService periodEngineService, String viewKey) {
        if (masterKeys == null) {
            return null;
        }
        Object value = masterKeys.getValue(DATA_TIME);
        if (value != null) {
            String[] timesArr;
            String periodCode = String.valueOf(value);
            if (!StringUtils.hasText(periodCode)) {
                return null;
            }
            if (StringUtils.hasText(viewKey)) {
                IPeriodProvider periodProvider = periodEngineService.getPeriodAdapter().getPeriodProvider(viewKey);
                PeriodType periodType = periodProvider.getPeriodEntity().getPeriodType();
                try {
                    if (PeriodUtils.isPeriod13((String)periodCode, (PeriodType)periodType)) {
                        return PeriodUtils.transPeriod13Data((String)periodCode)[1];
                    }
                    return periodProvider.getPeriodDateRegion(periodCode)[1];
                }
                catch (ParseException e) {
                    log.error(e.getMessage(), e);
                    return null;
                }
            }
            try {
                timesArr = PeriodUtil.getTimesArr((String)periodCode);
            }
            catch (Exception e) {
                throw new IllegalArgumentException("\u65e0\u6cd5\u89e3\u6790\u7684\u65f6\u671f\uff1a" + periodCode);
            }
            if (timesArr == null) {
                throw new IllegalArgumentException("\u65e0\u6cd5\u89e3\u6790\u65f6\u671f:" + periodCode);
            }
            if (timesArr.length != 2) {
                GregorianCalendar gregorianCalendar = PeriodUtil.period2Calendar((String)periodCode);
                return new Date(gregorianCalendar.getTimeInMillis());
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date entTime = new Date();
            try {
                entTime = simpleDateFormat.parse(timesArr[1]);
            }
            catch (ParseException e) {
                log.error(String.format("\u4e1a\u52a1\u65f6\u671f%s\u683c\u5f0f\u5316\u4e3a\u65f6\u95f4\u9519\u8bef\uff0c\u9519\u8bef\u65f6\u95f4\u5b57\u7b26\u4e32\u4e3a:%s", periodCode, timesArr[1]), e);
            }
            return Utils.getEndTimeOfDay(entTime);
        }
        return null;
    }

    public static Date getEndTimeOfDay(Date dayTime) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(dayTime.getTime()), ZoneId.systemDefault());
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }
}

