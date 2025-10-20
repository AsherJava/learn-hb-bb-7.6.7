/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.dc.integration.execute.impl.utils;

import org.apache.commons.lang3.StringUtils;

public class BaseDataConvertUtil {
    public static String getCodeWithOutODS(String isolationCode) {
        isolationCode = StringUtils.replace((String)isolationCode, (String)"DC_", (String)"");
        return StringUtils.remove((String)isolationCode, (String)"ODS_");
    }
}

