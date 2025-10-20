/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.data.ObjectData
 *  com.jiuqi.np.dataengine.data.VoidData
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 */
package com.jiuqi.gcreport.common.formula;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.ObjectData;
import com.jiuqi.np.dataengine.data.VoidData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GcAbstractData {
    private static Logger logger = LoggerFactory.getLogger(GcAbstractData.class);

    public static String getStringValue(AbstractData data) {
        return data != null ? data.getAsString() : null;
    }

    public static double getDoubleValue(AbstractData data) {
        return GcAbstractData.getDoubleValue(data, 2);
    }

    public static double getDoubleValue(AbstractData data, int scale) {
        if (data == null || data instanceof VoidData) {
            return 0.0;
        }
        try {
            Number number = data instanceof ObjectData ? (Number)data.getAsObject() : data.getAsCurrency();
            number = number == null ? BigDecimal.ZERO : number;
            double roundValue = NumberUtils.round((double)number.doubleValue(), (int)scale);
            return roundValue;
        }
        catch (Exception e) {
            return 0.0;
        }
    }

    public static boolean getBooleanValue(AbstractData data) {
        if (data == null) {
            return false;
        }
        try {
            return data.getAsBool();
        }
        catch (DataTypeException e) {
            logger.error(e.getMessage(), e);
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
    }
}

