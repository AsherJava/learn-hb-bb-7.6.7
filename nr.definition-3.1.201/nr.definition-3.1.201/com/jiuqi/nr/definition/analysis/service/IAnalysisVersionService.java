/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.definition.analysis.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.definition.analysis.vo.AnalysisVersionVO;
import java.util.List;

public interface IAnalysisVersionService {
    public List<AnalysisVersionVO> queryVersionByEntity(String var1, String var2, DimensionValueSet var3) throws Exception;
}

