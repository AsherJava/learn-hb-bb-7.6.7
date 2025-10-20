/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 *  com.jiuqi.nvwa.framework.parameter.model.value.DefaultParameterValueFormat
 */
package com.jiuqi.bi.dataset.parameter.extend;

import com.jiuqi.bi.dataset.DSUtils;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.model.value.DefaultParameterValueFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DSFieldParameterValueFormat
extends DefaultParameterValueFormat {
    private DSField field;

    public DSFieldParameterValueFormat(DSField field) {
        super(field.getValType());
        this.field = field;
    }

    public Format getDataShowFormat(Locale locale) {
        return DSUtils.generateFormat(this.field, locale);
    }

    public String format(Object value) throws ParameterException {
        if (value == null) {
            return null;
        }
        int dataType = this.field.getValType();
        if (dataType == 2) {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
            if (value instanceof String) {
                return (String)value;
            }
            if (value instanceof Date) {
                return sdf1.format((Date)value);
            }
            if (value instanceof Calendar) {
                return sdf1.format(((Calendar)value).getTime());
            }
            throw new ParameterException("\u65e5\u671f\u53c2\u6570\u53d6\u503c\u5bf9\u8c61\u9519\u8bef");
        }
        return value.toString();
    }
}

