/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.file.FileAreaConfig
 */
package com.jiuqi.nr.dafafill.file;

import com.jiuqi.nr.file.FileAreaConfig;
import org.springframework.stereotype.Component;

@Component
public class DataFillExportFileAreaConfig
implements FileAreaConfig {
    public static final String DATAFILL_EXPORT_AREA = "DATA_FILL_EXPORT";

    public String getName() {
        return DATAFILL_EXPORT_AREA;
    }

    public long getMaxFileSize() {
        return 0x40000000L;
    }
}

