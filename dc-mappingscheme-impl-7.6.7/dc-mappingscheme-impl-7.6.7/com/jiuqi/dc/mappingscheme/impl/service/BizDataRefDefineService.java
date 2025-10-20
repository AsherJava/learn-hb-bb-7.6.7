/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.intf.impl.TreeDTO
 *  com.jiuqi.dc.base.common.vo.SelectOptionVO
 *  com.jiuqi.dc.mappingscheme.client.common.FieldDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataRefDefineListDTO
 *  javax.validation.constraints.NotBlank
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.dc.mappingscheme.impl.service;

import com.jiuqi.dc.base.common.intf.impl.TreeDTO;
import com.jiuqi.dc.base.common.vo.SelectOptionVO;
import com.jiuqi.dc.mappingscheme.client.common.FieldDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataRefDefineListDTO;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface BizDataRefDefineService {
    public List<TreeDTO> tree(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") DataRefDefineListDTO var1);

    public List<DataMappingDefineDTO> list(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") DataRefDefineListDTO var1);

    public List<DataMappingDefineDTO> listBySchemeCode(@NotBlank(message="\u6570\u636e\u6620\u5c04\u65b9\u6848\u7f16\u53f7\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u6570\u636e\u6620\u5c04\u65b9\u6848\u7f16\u53f7\u4e0d\u80fd\u4e3a\u7a7a") String var1);

    public DataMappingDefineDTO findById(@NotBlank(message="\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a") String var1);

    public DataMappingDefineDTO findByCode(@NotBlank(message="\u6570\u636e\u6620\u5c04\u65b9\u6848\u7f16\u53f7\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u6570\u636e\u6620\u5c04\u65b9\u6848\u7f16\u53f7\u4e0d\u80fd\u4e3a\u7a7a") String var1, @NotBlank(message="\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a") String var2);

    public DataMappingDefineDTO getById(@NotBlank(message="\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a") String var1);

    public DataMappingDefineDTO getByCode(@NotBlank(message="\u6570\u636e\u6620\u5c04\u65b9\u6848\u7f16\u53f7\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u6570\u636e\u6620\u5c04\u65b9\u6848\u7f16\u53f7\u4e0d\u80fd\u4e3a\u7a7a") String var1, @NotBlank(message="\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a") String var2);

    public List<SelectOptionVO> listMappingTable(@NotBlank(message="\u63d2\u4ef6\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u63d2\u4ef6\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a") String var1);

    public List<FieldDTO> listOdsFields(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") DataMappingDefineDTO var1);

    public Boolean create(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") DataMappingDefineDTO var1);

    public Boolean schemeInitCreate(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") DataMappingDefineDTO var1);

    public Boolean modify(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") DataMappingDefineDTO var1);

    public Boolean delete(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") DataMappingDefineDTO var1);

    public DataMappingDefineDTO schemeInitDelete(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") DataMappingDefineDTO var1);

    public String preview(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") DataMappingDefineDTO var1);
}

