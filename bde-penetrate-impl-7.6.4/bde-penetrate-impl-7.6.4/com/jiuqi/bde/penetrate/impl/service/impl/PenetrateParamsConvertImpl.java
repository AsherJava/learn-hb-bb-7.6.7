/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.dc.base.common.utils.CollectionUtil
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataRefDefineListDTO
 *  com.jiuqi.dc.mappingscheme.impl.common.RuleType
 *  com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService
 */
package com.jiuqi.bde.penetrate.impl.service.impl;

import com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.service.PenetrateParamsConvert;
import com.jiuqi.dc.base.common.utils.CollectionUtil;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataRefDefineListDTO;
import com.jiuqi.dc.mappingscheme.impl.common.RuleType;
import com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PenetrateParamsConvertImpl
implements PenetrateParamsConvert {
    @Autowired
    private BaseDataRefDefineService baseDataRefDefineService;

    @Override
    public <C extends PenetrateBaseDTO> void coverPenetrateBaseDTO(C condi) {
        DataRefDefineListDTO dataRefDefineListDTO = new DataRefDefineListDTO();
        String dataSchemeCode = condi.getOrgMapping().getDataSchemeCode();
        dataRefDefineListDTO.setDataSchemeCode(dataSchemeCode);
        List baseDataMappingDefineDTOs = this.baseDataRefDefineService.list(dataRefDefineListDTO).stream().filter(item -> !"MD_ORG".equals(item.getCode())).collect(Collectors.toList());
        Map dataIsMappingRefDTOMap = baseDataMappingDefineDTOs.stream().collect(Collectors.toMap(DataMappingDefineDTO::getCode, Function.identity(), (K1, K2) -> K1));
        BaseDataMappingDefineDTO subjectBaseDataMappingDefineDTO = (BaseDataMappingDefineDTO)dataIsMappingRefDTOMap.get("MD_ACCTSUBJECT");
        if (subjectBaseDataMappingDefineDTO != null && RuleType.ITEM_BY_ITEM.getCode().equals(subjectBaseDataMappingDefineDTO.getRuleType())) {
            List subjectCodes = ModelExecuteUtil.getReductionData((String)condi.getSubjectCode(), (String)dataSchemeCode, (String)"MD_ACCTSUBJECT");
            condi.setSubjectCode(CollectionUtil.join((List)subjectCodes, (String)","));
        }
        for (Dimension dimension : condi.getAssTypeList()) {
            BaseDataMappingDefineDTO baseDataMappingDefineDTO = (BaseDataMappingDefineDTO)dataIsMappingRefDTOMap.get(dimension.getDimCode());
            if (baseDataMappingDefineDTO == null || !RuleType.ITEM_BY_ITEM.getCode().equals(baseDataMappingDefineDTO.getRuleType())) continue;
            List dimValues = ModelExecuteUtil.getReductionData((String)dimension.getDimValue(), (String)dataSchemeCode, (String)baseDataMappingDefineDTO.getCode());
            dimension.setDimValue(CollectionUtil.join((List)dimValues, (String)","));
        }
    }
}

