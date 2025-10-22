/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.consolidatedsystem.DTO.ManagementDim
 */
package com.jiuqi.gcreport.consolidatedsystem.service;

import com.jiuqi.gcreport.consolidatedsystem.DTO.ManagementDim;
import java.util.List;

public interface ManagementDimensionCacheService {
    public List<ManagementDim> getManagementDimsBySystemId(String var1);

    public List<ManagementDim> getOptionalManagementDims();
}

