/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.bean;

import com.jiuqi.nr.designer.bean.BaseDataFormat;
import java.text.SimpleDateFormat;

class FormatDate
extends BaseDataFormat {
    FormatDate() {
    }

    @Override
    public String getFormatDate(Object obj) {
        return new SimpleDateFormat("yyyy-MM-dd").format(obj);
    }
}

