/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.value;

import com.jiuqi.va.biz.intf.value.Converter;
import com.jiuqi.va.biz.intf.value.convert.BigDecimalConvert;
import com.jiuqi.va.biz.intf.value.convert.BooleanConvert;
import com.jiuqi.va.biz.intf.value.convert.ByteArrayConvert;
import com.jiuqi.va.biz.intf.value.convert.ByteConvert;
import com.jiuqi.va.biz.intf.value.convert.CalendarConvert;
import com.jiuqi.va.biz.intf.value.convert.CharConvert;
import com.jiuqi.va.biz.intf.value.convert.DateConvert;
import com.jiuqi.va.biz.intf.value.convert.DoubleConvert;
import com.jiuqi.va.biz.intf.value.convert.EnumConvert;
import com.jiuqi.va.biz.intf.value.convert.FloatConvert;
import com.jiuqi.va.biz.intf.value.convert.GregorianCalendarConvert;
import com.jiuqi.va.biz.intf.value.convert.IntConvert;
import com.jiuqi.va.biz.intf.value.convert.LongConvert;
import com.jiuqi.va.biz.intf.value.convert.ObjectConvert;
import com.jiuqi.va.biz.intf.value.convert.ShortConvert;
import com.jiuqi.va.biz.intf.value.convert.SqlDateConvert;
import com.jiuqi.va.biz.intf.value.convert.StringConvert;
import com.jiuqi.va.biz.intf.value.convert.UUIDConvert;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum ConvertManager {
    INSTANCE;

    final Map<Class<?>, Object> primitiveNullMap = new HashMap();
    final Map<Class<?>, Class<?>> primitiveTypeMap = new HashMap();
    final Map<Class<?>, Map<Class<?>, Converter>> map = new ConcurrentHashMap();

    private ConvertManager() {
        this.primitiveNullMap.put(Boolean.TYPE, false);
        this.primitiveNullMap.put(Byte.TYPE, (byte)0);
        this.primitiveNullMap.put(Integer.TYPE, 0);
        this.primitiveNullMap.put(Short.TYPE, (short)0);
        this.primitiveNullMap.put(Long.TYPE, 0L);
        this.primitiveNullMap.put(Float.TYPE, Float.valueOf(0.0f));
        this.primitiveNullMap.put(Double.TYPE, 0.0);
        this.primitiveNullMap.put(Character.TYPE, Character.valueOf('\u0000'));
        this.primitiveTypeMap.put(Boolean.TYPE, Boolean.class);
        this.primitiveTypeMap.put(Byte.TYPE, Byte.class);
        this.primitiveTypeMap.put(Short.TYPE, Short.class);
        this.primitiveTypeMap.put(Integer.TYPE, Integer.class);
        this.primitiveTypeMap.put(Long.TYPE, Long.class);
        this.primitiveTypeMap.put(Float.TYPE, Float.class);
        this.primitiveTypeMap.put(Double.TYPE, Double.class);
        this.primitiveTypeMap.put(Character.TYPE, Character.class);
    }

    public static void register(Converter converter) {
        Class<?> targetType;
        Map<Class<?>, Converter> map;
        Class<?> sourceType = converter.getSourceType();
        if (sourceType.isPrimitive()) {
            sourceType = ConvertManager.INSTANCE.primitiveTypeMap.get(sourceType);
        }
        if ((map = ConvertManager.INSTANCE.map.get(sourceType)) == null) {
            map = new ConcurrentHashMap();
            ConvertManager.INSTANCE.map.put(sourceType, map);
        }
        if ((targetType = converter.getTargetType()).isPrimitive()) {
            targetType = ConvertManager.INSTANCE.primitiveTypeMap.get(targetType);
        }
        map.put(targetType, converter);
    }

    static {
        ConvertManager.register(BooleanConvert.toByte);
        ConvertManager.register(BooleanConvert.toShort);
        ConvertManager.register(BooleanConvert.toInt);
        ConvertManager.register(BooleanConvert.toLong);
        ConvertManager.register(BooleanConvert.toFloat);
        ConvertManager.register(BooleanConvert.toDouble);
        ConvertManager.register(BooleanConvert.toChar);
        ConvertManager.register(BooleanConvert.toString2);
        ConvertManager.register(ByteConvert.toBoolean);
        ConvertManager.register(ByteConvert.toShort);
        ConvertManager.register(ByteConvert.toInt);
        ConvertManager.register(ByteConvert.toLong);
        ConvertManager.register(ByteConvert.toFloat);
        ConvertManager.register(ByteConvert.toDouble);
        ConvertManager.register(ByteConvert.toString2);
        ConvertManager.register(ShortConvert.toBoolean);
        ConvertManager.register(ShortConvert.toByte);
        ConvertManager.register(ShortConvert.toInt);
        ConvertManager.register(ShortConvert.toLong);
        ConvertManager.register(ShortConvert.toFloat);
        ConvertManager.register(ShortConvert.toDouble);
        ConvertManager.register(ShortConvert.toString2);
        ConvertManager.register(IntConvert.toBoolean);
        ConvertManager.register(IntConvert.toByte);
        ConvertManager.register(IntConvert.toShort);
        ConvertManager.register(IntConvert.toLong);
        ConvertManager.register(IntConvert.toFloat);
        ConvertManager.register(IntConvert.toDouble);
        ConvertManager.register(IntConvert.toString2);
        ConvertManager.register(LongConvert.toBoolean);
        ConvertManager.register(LongConvert.toByte);
        ConvertManager.register(LongConvert.toShort);
        ConvertManager.register(LongConvert.toInt);
        ConvertManager.register(LongConvert.toFloat);
        ConvertManager.register(LongConvert.toDouble);
        ConvertManager.register(LongConvert.toString2);
        ConvertManager.register(FloatConvert.toBoolean);
        ConvertManager.register(FloatConvert.toByte);
        ConvertManager.register(FloatConvert.toShort);
        ConvertManager.register(FloatConvert.toInt);
        ConvertManager.register(FloatConvert.toLong);
        ConvertManager.register(FloatConvert.toDouble);
        ConvertManager.register(FloatConvert.toString2);
        ConvertManager.register(DoubleConvert.toBoolean);
        ConvertManager.register(DoubleConvert.toByte);
        ConvertManager.register(DoubleConvert.toShort);
        ConvertManager.register(DoubleConvert.toInt);
        ConvertManager.register(DoubleConvert.toLong);
        ConvertManager.register(DoubleConvert.toFloat);
        ConvertManager.register(DoubleConvert.toString2);
        ConvertManager.register(BigDecimalConvert.toBoolean);
        ConvertManager.register(BigDecimalConvert.toByte);
        ConvertManager.register(BigDecimalConvert.toShort);
        ConvertManager.register(BigDecimalConvert.toInt);
        ConvertManager.register(BigDecimalConvert.toLong);
        ConvertManager.register(BigDecimalConvert.toFloat);
        ConvertManager.register(BigDecimalConvert.toDouble);
        ConvertManager.register(BigDecimalConvert.toString2);
        ConvertManager.register(BigDecimalConvert.fromBoolean);
        ConvertManager.register(BigDecimalConvert.fromByte);
        ConvertManager.register(BigDecimalConvert.fromShort);
        ConvertManager.register(BigDecimalConvert.fromInt);
        ConvertManager.register(BigDecimalConvert.fromLong);
        ConvertManager.register(BigDecimalConvert.fromFloat);
        ConvertManager.register(BigDecimalConvert.fromDouble);
        ConvertManager.register(BigDecimalConvert.fromString);
        ConvertManager.register(CharConvert.toBoolean);
        ConvertManager.register(CharConvert.toString2);
        ConvertManager.register(StringConvert.toBoolean);
        ConvertManager.register(StringConvert.toByte);
        ConvertManager.register(StringConvert.toShort);
        ConvertManager.register(StringConvert.toInt);
        ConvertManager.register(StringConvert.toLong);
        ConvertManager.register(StringConvert.toFloat);
        ConvertManager.register(StringConvert.toDouble);
        ConvertManager.register(StringConvert.toChar);
        ConvertManager.register(StringConvert.toByteArray);
        ConvertManager.register(ByteArrayConvert.toString2);
        ConvertManager.register(UUIDConvert.toString2);
        ConvertManager.register(UUIDConvert.fromString);
        ConvertManager.register(UUIDConvert.toByteArray);
        ConvertManager.register(UUIDConvert.fromByteArray);
        ConvertManager.register(EnumConvert.toString2);
        ConvertManager.register(EnumConvert.fromString);
        ConvertManager.register(EnumConvert.toInt);
        ConvertManager.register(EnumConvert.fromInt);
        ConvertManager.register(DateConvert.toString2);
        ConvertManager.register(DateConvert.fromString);
        ConvertManager.register(DateConvert.toLong);
        ConvertManager.register(DateConvert.fromLong);
        ConvertManager.register(DateConvert.fromInteger);
        ConvertManager.register(SqlDateConvert.toString2);
        ConvertManager.register(SqlDateConvert.fromString);
        ConvertManager.register(SqlDateConvert.toLong);
        ConvertManager.register(SqlDateConvert.fromLong);
        ConvertManager.register(CalendarConvert.toString2);
        ConvertManager.register(CalendarConvert.fromString);
        ConvertManager.register(CalendarConvert.toDate);
        ConvertManager.register(CalendarConvert.fromDate);
        ConvertManager.register(CalendarConvert.toLong);
        ConvertManager.register(CalendarConvert.fromLong);
        ConvertManager.register(GregorianCalendarConvert.toString2);
        ConvertManager.register(GregorianCalendarConvert.fromString);
        ConvertManager.register(GregorianCalendarConvert.toDate);
        ConvertManager.register(GregorianCalendarConvert.fromDate);
        ConvertManager.register(GregorianCalendarConvert.toLong);
        ConvertManager.register(GregorianCalendarConvert.fromLong);
        ConvertManager.register(ObjectConvert.toString2);
        ConvertManager.register(ObjectConvert.fromString);
    }
}

