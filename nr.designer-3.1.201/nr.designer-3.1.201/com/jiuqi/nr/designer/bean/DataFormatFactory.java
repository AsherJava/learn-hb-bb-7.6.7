/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.FieldType
 */
package com.jiuqi.nr.designer.bean;

import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.nr.designer.bean.FormatDate;
import com.jiuqi.nr.designer.bean.FormatDateTime;

public class DataFormatFactory {
    public static Object formatData(FieldType type, Object data) {
        Object formatStr = data;
        switch (type) {
            case FIELD_TYPE_DATE: {
                return new FormatDate().getFormatDate(data);
            }
            case FIELD_TYPE_DATE_TIME: {
                return new FormatDateTime().getFormatDate(data);
            }
        }
        return formatStr;
    }
}

