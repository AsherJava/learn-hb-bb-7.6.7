/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.dataengine.var.VariableManager
 *  com.jiuqi.nr.analysisreport.utils.AnaUtils
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.nr.var.formula.common;

import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.dataengine.var.VariableManager;
import com.jiuqi.nr.analysisreport.utils.AnaUtils;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

public class ReportFormulaUtil {
    public static Boolean isValueEmpty(Object value) {
        if (value instanceof Double && Math.abs((Double)value) < 1.0E-10) {
            return true;
        }
        if (value instanceof Integer && (double)Math.abs((Integer)value) < 1.0E-10) {
            return true;
        }
        if (value instanceof BigDecimal && ((BigDecimal)value).compareTo(new BigDecimal(String.valueOf(BigDecimal.ZERO))) == 0) {
            return true;
        }
        if (value instanceof Float && (double)Math.abs(((Float)value).floatValue()) < 1.0E-10) {
            return true;
        }
        if (value instanceof Long && (double)Math.abs((Long)value) < 1.0E-10) {
            return true;
        }
        if (value instanceof Short && (double)Math.abs(((Short)value).shortValue()) < 1.0E-10) {
            return true;
        }
        if (value instanceof String && StringUtils.isEmpty((CharSequence)((String)value))) {
            return true;
        }
        return false;
    }

    public static String valueToSting(Object value, Boolean isScientific) {
        if (value == null) {
            return "";
        }
        String resultData = null;
        if (value instanceof Calendar) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            resultData = dateFormat.format(((Calendar)value).getTime());
        } else {
            resultData = AnaUtils.resultFormat((Object)value, (Boolean)isScientific);
        }
        return resultData;
    }

    public static void setFormulaVariable(VariableManager variableManager, Map<String, Object> map) {
        for (Map.Entry<String, Object> keyValue : map.entrySet()) {
            Object value = keyValue.getValue();
            String key = keyValue.getKey();
            if (value == null) continue;
            int dataType = ReportFormulaUtil.getDataType(value);
            variableManager.add(new Variable(key, key, dataType, value));
        }
    }

    public static int getDataType(Object value) {
        if (value instanceof Float) {
            return 3;
        }
        if (value instanceof String) {
            return 6;
        }
        if (value instanceof Integer) {
            return 4;
        }
        if (value instanceof Boolean) {
            return 1;
        }
        if (value instanceof Date) {
            return 5;
        }
        if (value instanceof Time) {
            return 2;
        }
        if (value instanceof Timestamp) {
            return 2;
        }
        if (value instanceof BigDecimal) {
            return 10;
        }
        if (value instanceof Arrays) {
            return 11;
        }
        if (value instanceof List) {
            return 65;
        }
        return 0;
    }

    public static <T> List<List<T>> splitList(ArrayList<T> list, int n) {
        ArrayList<List<T>> result = new ArrayList<List<T>>();
        if (CollectionUtils.isEmpty(list) || n == 0) {
            return result;
        }
        int size = list.size();
        if (n > size) {
            n = size;
        }
        int quotient = size / n;
        int remainder = size % n;
        for (int i = 0; i < n; ++i) {
            int start = i * quotient + Math.min(i, remainder);
            int end = (i + 1) * quotient + Math.min(i + 1, remainder);
            result.add(list.subList(start, end));
        }
        return result;
    }
}

