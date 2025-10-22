/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.nr.snapshot.compare;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.facade.FieldDefine;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class CompareDiffenceUtil {
    public static String translateString(FieldDefine fieldDefine, String value) {
        return value;
    }

    public static String compareDifference(int fieldType, String initialValue, String compareValue) {
        String difference = "--";
        if (1 == fieldType || 3 == fieldType || 8 == fieldType) {
            BigDecimal initialBigDecimal = null;
            try {
                if (!StringUtils.isEmpty((String)initialValue)) {
                    if (initialValue.contains(",")) {
                        Number number = NumberFormat.getIntegerInstance(Locale.getDefault()).parse(initialValue);
                        initialBigDecimal = BigDecimal.valueOf(number.doubleValue());
                    } else {
                        initialBigDecimal = new BigDecimal(initialValue);
                    }
                }
            }
            catch (Exception e) {
                e.getMessage();
            }
            BigDecimal compareBigDecimal = null;
            try {
                if (!StringUtils.isEmpty((String)compareValue)) {
                    if (compareValue.contains(",")) {
                        Number number = NumberFormat.getIntegerInstance(Locale.getDefault()).parse(compareValue);
                        compareBigDecimal = BigDecimal.valueOf(number.doubleValue());
                    } else {
                        compareBigDecimal = new BigDecimal(compareValue);
                    }
                }
            }
            catch (Exception e) {
                e.getMessage();
            }
            if (compareBigDecimal == null) {
                return difference;
            }
            if (null != initialBigDecimal && null != compareBigDecimal && initialBigDecimal.compareTo(BigDecimal.ZERO) == 0 && compareBigDecimal.compareTo(BigDecimal.ZERO) == 0) {
                return difference;
            }
            if (null != initialBigDecimal && null != compareBigDecimal && initialBigDecimal.compareTo(compareBigDecimal) == 0) {
                return difference;
            }
            if (null == initialBigDecimal && null != compareBigDecimal) {
                difference = compareBigDecimal.toString();
            } else if (null != initialBigDecimal && null == compareBigDecimal) {
                difference = "-" + initialBigDecimal.toString();
            } else {
                BigDecimal differenceBigDecimal = compareBigDecimal.subtract(initialBigDecimal);
                if (differenceBigDecimal != null) {
                    difference = differenceBigDecimal.toString();
                }
            }
        }
        return difference;
    }
}

