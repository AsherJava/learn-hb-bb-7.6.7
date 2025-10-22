/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.data.excel.extend.export.cellstyle;

import com.jiuqi.nr.data.excel.obj.CustomGridCellStyle;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.Map;

public interface CustomCellStyleProvider {
    public Map<String, CustomGridCellStyle> provideCustomCellStyles(DimensionCombination var1, String var2);
}

