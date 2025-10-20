/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataType
 */
package com.jiuqi.bi.quickreport.engine.writeback;

import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.quickreport.engine.writeback.ReportWritebackException;
import java.util.Calendar;

public abstract class ValueValidator {
    public final Object validate(Object value) throws NumberFormatException {
        if (value == null) {
            return null;
        }
        Object ret = this.valueOf(value);
        if (ret == null) {
            throw new NumberFormatException("\u8ba1\u7b97\u8fd4\u56de\u6570\u636e\u7c7b\u578b\u4e0e\u76ee\u6807\u5b57\u6bb5\u7c7b\u578b\u4e0d\u5339\u914d");
        }
        return ret;
    }

    protected abstract Object valueOf(Object var1);

    public static ValueValidator createValidator(DataType dataType) throws ReportWritebackException {
        switch (dataType) {
            case BOOLEAN: {
                return new BooleanValidator();
            }
            case DATETIME: {
                return new DateTimeValidator();
            }
            case DOUBLE: 
            case INTEGER: {
                return new IntegerValidator();
            }
            case STRING: {
                return new StringValidator();
            }
        }
        throw new ReportWritebackException("\u672a\u652f\u6301\u7684\u56de\u5199\u5b57\u6bb5\u7c7b\u578b\uff1a" + dataType);
    }

    private static final class StringValidator
    extends ValueValidator {
        private StringValidator() {
        }

        @Override
        protected Object valueOf(Object value) {
            return value instanceof String ? value : null;
        }
    }

    private static final class IntegerValidator
    extends ValueValidator {
        private IntegerValidator() {
        }

        @Override
        protected Object valueOf(Object value) {
            if (value instanceof Number) {
                return value;
            }
            if (value instanceof Boolean) {
                return (Boolean)value != false ? 1 : 0;
            }
            return null;
        }
    }

    private static final class DateTimeValidator
    extends ValueValidator {
        private DateTimeValidator() {
        }

        @Override
        protected Object valueOf(Object value) {
            return value instanceof Calendar ? value : null;
        }
    }

    private static final class BooleanValidator
    extends ValueValidator {
        private BooleanValidator() {
        }

        @Override
        protected Object valueOf(Object value) {
            if (value instanceof Boolean) {
                return value;
            }
            if (value instanceof Integer) {
                return (Integer)value != 0;
            }
            return null;
        }
    }
}

