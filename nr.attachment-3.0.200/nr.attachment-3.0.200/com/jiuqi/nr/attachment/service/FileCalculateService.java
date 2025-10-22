/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.attachment.service;

import com.jiuqi.nr.attachment.input.FileCalculateParam;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;

public interface FileCalculateService {
    @Deprecated
    public String copyFileGroup(String var1, String var2, String var3, DimensionCombination var4);

    public String copyFileGroup(FileCalculateParam var1);
}

