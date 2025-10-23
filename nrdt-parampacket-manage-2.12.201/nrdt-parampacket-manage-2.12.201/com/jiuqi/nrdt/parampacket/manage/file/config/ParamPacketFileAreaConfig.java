/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.file.FileAreaConfig
 */
package com.jiuqi.nrdt.parampacket.manage.file.config;

import com.jiuqi.nr.file.FileAreaConfig;
import org.springframework.stereotype.Component;

@Component
public class ParamPacketFileAreaConfig
implements FileAreaConfig {
    public static final String AREA_NAME = "PARAMTEMP";
    private static final long FILE_MAX_SIZE = 0x7D000000L;

    public String getName() {
        return AREA_NAME;
    }
}

