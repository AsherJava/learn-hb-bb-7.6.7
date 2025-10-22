/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.file.FileAreaConfig
 */
package com.jiuqi.nr.jtable.file;

import com.jiuqi.nr.file.FileAreaConfig;
import org.springframework.stereotype.Component;

@Deprecated
@Component
public class JtableFileAreaConfig
implements FileAreaConfig {
    public static final String JTABLE_FILE_AREA = "JTABLEAREA";
    private static final long MAXFILESIZE = 0x6400000L;

    public String getName() {
        return JTABLE_FILE_AREA;
    }

    public long getMaxFileSize() {
        return 0x6400000L;
    }
}

