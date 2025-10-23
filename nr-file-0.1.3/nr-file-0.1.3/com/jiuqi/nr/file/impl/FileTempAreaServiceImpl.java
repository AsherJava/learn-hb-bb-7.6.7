/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.file.impl;

import com.jiuqi.nr.file.FileTempAreaService;
import org.springframework.stereotype.Service;

@Service
public class FileTempAreaServiceImpl
implements FileTempAreaService {
    private static final String TEMP_AREA_NAME = "NR_TEMP";

    @Override
    public String getTempAreaName() {
        return TEMP_AREA_NAME;
    }
}

