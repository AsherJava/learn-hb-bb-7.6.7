/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.base.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.text.ParseException;

public class ConverterUtils {
    public static String getAsString(Object obj) {
        if (obj != null) {
            return obj.toString();
        }
        return null;
    }

    public static String getAsString(Object obj, String defaultValue) {
        String answer = ConverterUtils.getAsString(obj);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    public static Number getAsNumber(Object obj) {
        if (obj != null) {
            if (obj instanceof Number) {
                return (Number)obj;
            }
            if (obj instanceof Boolean) {
                Boolean flag = (Boolean)obj;
                return flag != false ? 1 : 0;
            }
            if (obj instanceof String) {
                try {
                    String text = (String)obj;
                    return NumberFormat.getInstance().parse(text);
                }
                catch (ParseException parseException) {
                    // empty catch block
                }
            }
        }
        return null;
    }

    public static Number getAsNumber(Object obj, Number defaultValue) {
        Number answer = ConverterUtils.getAsNumber(obj);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    public static Boolean getAsBoolean(Object obj) {
        if (obj != null) {
            if (obj instanceof Boolean) {
                return (Boolean)obj;
            }
            if (obj instanceof String) {
                return Boolean.valueOf((String)obj);
            }
            if (obj instanceof Number) {
                Number n = (Number)obj;
                return n.intValue() != 0 ? Boolean.TRUE : Boolean.FALSE;
            }
        }
        return null;
    }

    public static Boolean getAsBoolean(Object obj, Boolean defaultValue) {
        Boolean answer = ConverterUtils.getAsBoolean(obj);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    public static boolean getAsBooleanValue(Object obj) {
        Boolean booleanObject = ConverterUtils.getAsBoolean(obj);
        if (booleanObject == null) {
            return false;
        }
        return booleanObject;
    }

    public static boolean getAsBooleanValue(Object obj, boolean defaultValue) {
        Boolean booleanObject = ConverterUtils.getAsBoolean(obj);
        if (booleanObject == null) {
            return defaultValue;
        }
        return booleanObject;
    }

    public static Byte getAsByte(Object obj) {
        Number answer = ConverterUtils.getAsNumber(obj);
        if (answer == null) {
            return null;
        }
        if (answer instanceof Byte) {
            return (Byte)answer;
        }
        return answer.byteValue();
    }

    public static Byte getAsByte(Object obj, Byte defaultValue) {
        Byte answer = ConverterUtils.getAsByte(obj);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    public static byte getAsByteValue(Object obj) {
        Byte byteObject = ConverterUtils.getAsByte(obj);
        if (byteObject == null) {
            return 0;
        }
        return byteObject;
    }

    public static byte getAsByteValue(Object obj, byte defaultValue) {
        Byte byteObject = ConverterUtils.getAsByte(obj);
        if (byteObject == null) {
            return defaultValue;
        }
        return byteObject;
    }

    public static Short getAsShort(Object obj) {
        Number answer = ConverterUtils.getAsNumber(obj);
        if (answer == null) {
            return null;
        }
        if (answer instanceof Short) {
            return (Short)answer;
        }
        return answer.shortValue();
    }

    public static Short getAsShort(Object obj, Short defaultValue) {
        Short answer = ConverterUtils.getAsShort(obj);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    public static short getAsShortValue(Object obj) {
        Short shortObject = ConverterUtils.getAsShort(obj);
        if (shortObject == null) {
            return 0;
        }
        return shortObject;
    }

    public static short getAsShortValue(Object obj, short defaultValue) {
        Short shortObject = ConverterUtils.getAsShort(obj);
        if (shortObject == null) {
            return defaultValue;
        }
        return shortObject;
    }

    public static Integer getAsInteger(Object obj) {
        Number answer = ConverterUtils.getAsNumber(obj);
        if (answer == null) {
            return null;
        }
        if (answer instanceof Integer) {
            return (Integer)answer;
        }
        return answer.intValue();
    }

    public static Integer getAsInteger(Object obj, Integer defaultValue) {
        Integer answer = ConverterUtils.getAsInteger(obj);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    public static int getAsIntValue(Object obj) {
        Integer integerObject = ConverterUtils.getAsInteger(obj);
        if (integerObject == null) {
            return 0;
        }
        return integerObject;
    }

    public static int getAsIntValue(Object obj, int defaultValue) {
        Integer integerObject = ConverterUtils.getAsInteger(obj);
        if (integerObject == null) {
            return defaultValue;
        }
        return integerObject;
    }

    public static Long getAsLong(Object obj) {
        Number answer = ConverterUtils.getAsNumber(obj);
        if (answer == null) {
            return null;
        }
        if (answer instanceof Long) {
            return (Long)answer;
        }
        return answer.longValue();
    }

    public static Long getAsLong(Object obj, Long defaultValue) {
        Long answer = ConverterUtils.getAsLong(obj);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    public static long getAsLongValue(Object obj) {
        Long longObject = ConverterUtils.getAsLong(obj);
        if (longObject == null) {
            return 0L;
        }
        return longObject;
    }

    public static long getAsLongValue(Object obj, long defaultValue) {
        Long longObject = ConverterUtils.getAsLong(obj);
        if (longObject == null) {
            return defaultValue;
        }
        return longObject;
    }

    public static Float getAsFloat(Object obj) {
        Number answer = ConverterUtils.getAsNumber(obj);
        if (answer == null) {
            return null;
        }
        if (answer instanceof Float) {
            return (Float)answer;
        }
        return Float.valueOf(answer.floatValue());
    }

    public static Float getAsFloat(Object obj, Float defaultValue) {
        Float answer = ConverterUtils.getAsFloat(obj);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    public static float getAsFloatValue(Object obj) {
        Float floatObject = ConverterUtils.getAsFloat(obj);
        if (floatObject == null) {
            return 0.0f;
        }
        return floatObject.floatValue();
    }

    public static float getAsFloatValue(Object obj, float defaultValue) {
        Float floatObject = ConverterUtils.getAsFloat(obj);
        if (floatObject == null) {
            return defaultValue;
        }
        return floatObject.floatValue();
    }

    public static Double getAsDouble(Object obj) {
        Number answer = ConverterUtils.getAsNumber(obj);
        if (answer == null) {
            return null;
        }
        if (answer instanceof Double) {
            return (Double)answer;
        }
        return answer.doubleValue();
    }

    public static Double getAsDouble(Object obj, Double defaultValue) {
        Double answer = ConverterUtils.getAsDouble(obj);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    public static double getAsDoubleValue(Object obj) {
        Double doubleObject = ConverterUtils.getAsDouble(obj);
        if (doubleObject == null) {
            return 0.0;
        }
        return doubleObject;
    }

    public static double getAsDoubleValue(Object obj, double defaultValue) {
        Double doubleObject = ConverterUtils.getAsDouble(obj);
        if (doubleObject == null) {
            return defaultValue;
        }
        return doubleObject;
    }

    public static BigInteger getAsBigInteger(Object obj) {
        Long longObject = ConverterUtils.getAsLong(obj);
        if (longObject == null) {
            return BigInteger.ZERO;
        }
        return BigInteger.valueOf(longObject);
    }

    public static BigInteger getAsBigInteger(Object obj, BigInteger defaultValue) {
        Long longObject = ConverterUtils.getAsLong(obj);
        if (longObject == null) {
            return defaultValue;
        }
        return BigInteger.valueOf(longObject);
    }

    public static BigDecimal getAsBigDecimal(Object obj) {
        Double doubleObject = ConverterUtils.getAsDouble(obj);
        if (doubleObject == null) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(doubleObject);
    }

    public static BigDecimal getAsBigDecimal(Object obj, BigDecimal defaultValue) {
        Double doubleObject = ConverterUtils.getAsDouble(obj);
        if (doubleObject == null) {
            return defaultValue;
        }
        return BigDecimal.valueOf(doubleObject);
    }

    public static <R> R cast(Object obj, Class<R> clz) {
        Object result = null;
        if (Boolean.class.equals(clz) || Boolean.TYPE.equals(clz)) {
            result = ConverterUtils.getAsBoolean(obj);
        } else if (Byte.class.equals(clz) || Byte.TYPE.equals(clz)) {
            result = ConverterUtils.getAsByte(obj);
        } else if (Short.class.equals(clz) || Short.TYPE.equals(clz)) {
            result = ConverterUtils.getAsShort(obj);
        } else if (Integer.class.equals(clz) || Integer.TYPE.equals(clz)) {
            result = ConverterUtils.getAsInteger(obj);
        } else if (Long.class.equals(clz) || Long.TYPE.equals(clz)) {
            result = ConverterUtils.getAsLong(obj);
        } else if (Float.class.equals(clz) || Float.TYPE.equals(clz)) {
            result = ConverterUtils.getAsFloat(obj);
        } else if (Double.class.equals(clz) || Double.TYPE.equals(clz)) {
            result = ConverterUtils.getAsDouble(obj);
        } else if (BigInteger.class.equals(clz)) {
            result = ConverterUtils.getAsBigInteger(obj);
        } else if (BigDecimal.class.equals(clz)) {
            result = ConverterUtils.getAsBigDecimal(obj);
        } else if (String.class.equals(clz)) {
            result = ConverterUtils.getAsString(obj);
        }
        return (R)result;
    }

    public static <R> R cast(Object obj, R defaultValue) {
        Object result = null;
        if (defaultValue == null) {
            return (R)result;
        }
        Class<?> clz = defaultValue.getClass();
        if (Boolean.class.equals(clz) || Boolean.TYPE.equals(clz)) {
            result = ConverterUtils.getAsBoolean(obj, (Boolean)defaultValue);
        } else if (Byte.class.equals(clz) || Byte.TYPE.equals(clz)) {
            result = ConverterUtils.getAsByte(obj, (Byte)defaultValue);
        } else if (Short.class.equals(clz) || Short.TYPE.equals(clz)) {
            result = ConverterUtils.getAsShort(obj, (Short)defaultValue);
        } else if (Integer.class.equals(clz) || Integer.TYPE.equals(clz)) {
            result = ConverterUtils.getAsInteger(obj, (Integer)defaultValue);
        } else if (Long.class.equals(clz) || Long.TYPE.equals(clz)) {
            result = ConverterUtils.getAsLong(obj, (Long)defaultValue);
        } else if (Float.class.equals(clz) || Float.TYPE.equals(clz)) {
            result = ConverterUtils.getAsFloat(obj, (Float)defaultValue);
        } else if (Double.class.equals(clz) || Double.TYPE.equals(clz)) {
            result = ConverterUtils.getAsDouble(obj, (Double)defaultValue);
        } else if (BigInteger.class.equals(clz)) {
            result = ConverterUtils.getAsBigInteger(obj, (BigInteger)defaultValue);
        } else if (BigDecimal.class.equals(clz)) {
            result = ConverterUtils.getAsBigDecimal(obj, (BigDecimal)defaultValue);
        } else if (String.class.equals(clz)) {
            result = ConverterUtils.getAsString(obj, (String)defaultValue);
        }
        return (R)result;
    }
}

