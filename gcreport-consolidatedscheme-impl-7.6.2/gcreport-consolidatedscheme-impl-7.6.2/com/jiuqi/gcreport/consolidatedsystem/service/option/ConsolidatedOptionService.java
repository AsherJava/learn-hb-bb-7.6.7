/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.FieldDefineVO
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 */
package com.jiuqi.gcreport.consolidatedsystem.service.option;

import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.FieldDefineVO;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import java.util.List;

public interface ConsolidatedOptionService {
    public ConsolidatedOptionVO getOptionData(String var1);

    public ConsolidatedOptionVO getOptionDataBySchemeId(String var1, String var2);

    public void saveOptionData(String var1, ConsolidatedOptionVO var2);

    public void deleteOptionData(String var1);

    public Object getOptionItem(String var1, String var2);

    public boolean getOptionItemBooleanBySchemeId(String var1, String var2, String var3);

    public List<FieldDefineVO> getFieldDefineTree(String var1, String var2);

    public List<DimensionVO> getDimensionsByTableName(String var1, String var2);

    public List<DimensionVO> getAllDimensionsByTableName(String var1, String var2);

    public List<DimensionVO> getDimensionsByTableName(String var1, String var2, String var3);

    public List<DimensionVO> getAllDimensionsByTableName(String var1, String var2, String var3);
}

