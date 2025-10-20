/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 */
package com.jiuqi.dc.mappingscheme.impl.util;

import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import java.util.List;

public interface InnerAssistDimProvider {
    public List<DimensionVO> getInnerAssistList();

    public boolean isInnerAssist(String var1);
}

