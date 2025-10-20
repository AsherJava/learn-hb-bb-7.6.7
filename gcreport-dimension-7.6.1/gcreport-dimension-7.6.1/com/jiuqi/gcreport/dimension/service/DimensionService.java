/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.dimension.service;

import com.jiuqi.gcreport.dimension.internal.entity.DimensionEO;
import com.jiuqi.gcreport.dimension.vo.DimSelectOptionVO;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import java.util.List;
import java.util.Set;

public interface DimensionService {
    public List<DimensionVO> loadAllDimensions();

    public DimensionVO getDimensionByCode(String var1);

    public List<DimensionVO> findDimFieldsVOByTableName(String var1);

    public List<DimensionVO> findAllDimFieldsVOByTableName(String var1);

    public List<DimensionEO> findDimFieldsByTableName(String var1);

    public List<DimensionEO> findAllDimFieldsByTableName(String var1);

    public List<DimSelectOptionVO> findManagementDimensionVO();

    public Set<String> listTableNamesByDimCode(String var1);
}

