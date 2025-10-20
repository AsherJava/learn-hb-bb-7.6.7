/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.conversion.api.dto.ConversionInitEnvDTO
 *  com.jiuqi.gcreport.conversion.common.GcConversionContextEnv
 */
package com.jiuqi.gcreport.conversion.service;

import com.jiuqi.gcreport.conversion.api.dto.ConversionInitEnvDTO;
import com.jiuqi.gcreport.conversion.common.GcConversionContextEnv;

public interface ConversionService {
    public void conversion(GcConversionContextEnv var1);

    public ConversionInitEnvDTO getConversionInitEnv();
}

