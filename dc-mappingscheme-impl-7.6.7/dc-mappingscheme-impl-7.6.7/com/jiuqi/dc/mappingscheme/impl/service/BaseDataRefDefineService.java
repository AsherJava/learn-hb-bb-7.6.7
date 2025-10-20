/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.intf.impl.TreeDTO
 *  com.jiuqi.dc.base.common.vo.SelectOptionVO
 *  com.jiuqi.dc.mappingscheme.client.common.Columns
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataRefDefineListDTO
 *  com.jiuqi.dc.mappingscheme.client.vo.RuleTypeShowVO
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  javax.validation.constraints.NotBlank
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.dc.mappingscheme.impl.service;

import com.jiuqi.dc.base.common.intf.impl.TreeDTO;
import com.jiuqi.dc.base.common.vo.SelectOptionVO;
import com.jiuqi.dc.mappingscheme.client.common.Columns;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataRefDefineListDTO;
import com.jiuqi.dc.mappingscheme.client.vo.RuleTypeShowVO;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface BaseDataRefDefineService {
    public List<RuleTypeShowVO> ruleType();

    public List<TreeDTO> tree(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") DataRefDefineListDTO var1);

    public List<BaseDataMappingDefineDTO> list(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") DataRefDefineListDTO var1);

    public List<BaseDataMappingDefineDTO> listBySchemeCode(@NotBlank(message="\u6570\u636e\u6620\u5c04\u65b9\u6848\u7f16\u53f7\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u6570\u636e\u6620\u5c04\u65b9\u6848\u7f16\u53f7\u4e0d\u80fd\u4e3a\u7a7a") String var1);

    public BaseDataMappingDefineDTO findById(@NotBlank(message="\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a") String var1);

    public BaseDataMappingDefineDTO findByCode(@NotBlank(message="\u6570\u636e\u6620\u5c04\u65b9\u6848\u7f16\u53f7\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u6570\u636e\u6620\u5c04\u65b9\u6848\u7f16\u53f7\u4e0d\u80fd\u4e3a\u7a7a") String var1, @NotBlank(message="\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a") String var2);

    public BaseDataMappingDefineDTO getById(@NotBlank(message="\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a") String var1);

    public BaseDataMappingDefineDTO getByCode(@NotBlank(message="\u6570\u636e\u6620\u5c04\u65b9\u6848\u7f16\u53f7\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u6570\u636e\u6620\u5c04\u65b9\u6848\u7f16\u53f7\u4e0d\u80fd\u4e3a\u7a7a") String var1, @NotBlank(message="\u6570\u636e\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a\uff0c\u8bf7\u68c0\u67e5\u57fa\u7840\u6570\u636e\u6620\u5c04\u5b9a\u4e49") @NotBlank(message="\u6570\u636e\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a\uff0c\u8bf7\u68c0\u67e5\u57fa\u7840\u6570\u636e\u6620\u5c04\u5b9a\u4e49") String var2);

    public BaseDataMappingDefineDTO fixed(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") DataMappingDefineDTO var1);

    public List<DimensionVO> listAssistDim();

    public List<SelectOptionVO> parseSql(@NotBlank(message="\u6570\u636e\u6620\u5c04\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u6570\u636e\u6620\u5c04\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a") String var1, @NotBlank(message="SQL\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="SQL\u4e0d\u80fd\u4e3a\u7a7a") String var2);

    public List<SelectOptionVO> parseSqlByDataSource(@NotBlank(message="\u6570\u636e\u6e90\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u6570\u636e\u6e90\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a") String var1, @NotBlank(message="SQL\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="SQL\u4e0d\u80fd\u4e3a\u7a7a") String var2);

    public Boolean create(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") BaseDataMappingDefineDTO var1);

    public Boolean schemeInitCreate(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") BaseDataMappingDefineDTO var1);

    public Boolean modify(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") BaseDataMappingDefineDTO var1);

    public Boolean delete(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") BaseDataMappingDefineDTO var1);

    public BaseDataMappingDefineDTO schemeInitDelete(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") BaseDataMappingDefineDTO var1);

    public List<Columns> getRefTableColumns(String var1);
}

