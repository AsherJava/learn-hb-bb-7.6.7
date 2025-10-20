/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.vo.SelectOptionVO
 *  com.jiuqi.dc.datamapping.client.dto.DataRefAutoMatchDTO
 *  com.jiuqi.dc.datamapping.client.dto.DataRefDTO
 *  com.jiuqi.dc.datamapping.client.dto.DataRefSaveDTO
 *  com.jiuqi.dc.datamapping.client.vo.DataRefAutoMatchVO
 *  com.jiuqi.dc.datamapping.client.vo.DataRefSaveVO
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 *  javax.validation.constraints.NotBlank
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.dc.datamapping.impl.service;

import com.jiuqi.dc.base.common.vo.SelectOptionVO;
import com.jiuqi.dc.datamapping.client.dto.DataRefAutoMatchDTO;
import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;
import com.jiuqi.dc.datamapping.client.dto.DataRefSaveDTO;
import com.jiuqi.dc.datamapping.client.vo.DataRefAutoMatchVO;
import com.jiuqi.dc.datamapping.client.vo.DataRefSaveVO;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface DataRefConfigureService {
    public List<DataMappingDefineDTO> listDefine(@NotBlank(message="\u6570\u636e\u6620\u5c04\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u6570\u636e\u6620\u5c04\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a") String var1);

    public List<DataMappingDefineDTO> listAllDefine();

    public DataRefDTO getByOdsId(String var1, @NotBlank(message="\u57fa\u7840\u6570\u636e\u9879\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u57fa\u7840\u6570\u636e\u9879\u4e0d\u80fd\u4e3a\u7a7a") String var2, @NotBlank(message="\u6e90\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u6e90\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a") String var3);

    public DataRefSaveVO save(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") DataRefSaveDTO var1);

    public Integer clean(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") DataRefSaveDTO var1);

    public DataRefAutoMatchVO autoMatch(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") DataRefAutoMatchDTO var1);

    public String getDataSchemeCodeByUnitCode(String var1);

    public void deleteByBaseDataRefDefine(@NotNull(message="\u57fa\u7840\u6570\u636e\u6620\u5c04\u5b9a\u4e49\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u57fa\u7840\u6570\u636e\u6620\u5c04\u5b9a\u4e49\u4e0d\u80fd\u4e3a\u7a7a") DataMappingDefineDTO var1);

    public List<SelectOptionVO> matchRuleList();

    public List<BaseDataMappingDefineDTO> listDefineBySchemeCode(List<String> var1);
}

