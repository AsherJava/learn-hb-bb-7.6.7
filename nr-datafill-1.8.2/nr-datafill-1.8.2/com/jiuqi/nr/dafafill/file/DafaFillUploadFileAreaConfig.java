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
public class DafaFillUploadFileAreaConfig
implements FileAreaConfig {
    public static final String DATAFILL_UPLOAD_AREA = "DATA_FILL";

    public String getName() {
        return DATAFILL_UPLOAD_AREA;
    }

    public long getMaxFileSize() {
        return 0x40000000L;
    }
}

