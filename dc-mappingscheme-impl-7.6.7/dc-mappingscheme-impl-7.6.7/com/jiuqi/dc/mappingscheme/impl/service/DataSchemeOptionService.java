/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeOptionDTO
 *  com.jiuqi.dc.mappingscheme.client.vo.DataSchemeOptionVO
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  javax.validation.constraints.NotEmpty
 */
package com.jiuqi.dc.mappingscheme.impl.service;

import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeOptionDTO;
import com.jiuqi.dc.mappingscheme.client.vo.DataSchemeOptionVO;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import java.util.List;
import javax.validation.constraints.NotEmpty;

public interface DataSchemeOptionService {
    public List<DataSchemeOptionVO> getListByPluginType(String var1);

    public List<DataSchemeOptionVO> getListByDataScheme(DataSchemeDTO var1);

    public void save(String var1, List<DataSchemeOptionVO> var2);

    public void delete(String var1);

    public DataSchemeOptionDTO getValueByDataSchemeCode(String var1, String var2);

    public List<DimensionVO> listOppositeDimensionByScheme(@NotEmpty(message="\u5355\u4f4d\u7f16\u53f7\u4e0d\u80fd\u4e3a\u7a7a") @NotEmpty(message="\u5355\u4f4d\u7f16\u53f7\u4e0d\u80fd\u4e3a\u7a7a") String var1);

    public Boolean isOppositeDimension(@NotEmpty(message="\u5355\u4f4d\u7f16\u53f7\u4e0d\u80fd\u4e3a\u7a7a") @NotEmpty(message="\u5355\u4f4d\u7f16\u53f7\u4e0d\u80fd\u4e3a\u7a7a") String var1, @NotEmpty(message="\u7ef4\u5ea6\u4e0d\u80fd\u4e3a\u7a7a") @NotEmpty(message="\u7ef4\u5ea6\u4e0d\u80fd\u4e3a\u7a7a") String var2);
}

