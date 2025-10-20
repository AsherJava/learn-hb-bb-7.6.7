/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.vo.SelectOptionVO
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 */
package com.jiuqi.bde.bizmodel.impl.dimension.service;

import com.jiuqi.dc.base.common.vo.SelectOptionVO;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import java.util.List;

public interface FetchDimensionService {
    public List<DimensionVO> listDimension();

    public List<DimensionVO> listDimensionByDataModel(String var1);

    public List<DimensionVO> listAllDimensionByDataModel(String var1);

    public List<DimensionVO> listAllDimension();

    public List<SelectOptionVO> listAllSelectOptionVO();
}

