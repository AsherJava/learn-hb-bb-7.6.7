/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.bean;

import com.jiuqi.nr.designer.bean.BaseDataFormat;
import java.text.SimpleDateFormat;

class FormatDateTime
extends BaseDataFormat {
    FormatDateTime() {
    }

    @Override
    public String getFormatDate(Object obj) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(obj);
    }
}

