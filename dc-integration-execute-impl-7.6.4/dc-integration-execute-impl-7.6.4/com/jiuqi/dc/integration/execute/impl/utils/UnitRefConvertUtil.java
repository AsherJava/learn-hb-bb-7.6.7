/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.datamapping.client.dto.DataRefDTO
 *  com.jiuqi.dc.datamapping.client.dto.DataRefListDTO
 *  com.jiuqi.dc.datamapping.client.dto.IsolationParamContext
 *  com.jiuqi.dc.datamapping.client.enums.DataRefFilterType
 *  com.jiuqi.dc.datamapping.impl.gather.impl.DataRefConfigureServiceGather
 *  com.jiuqi.dc.datamapping.impl.service.impl.IsolateRefDefineCacheProvider
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.impl.common.RuleType
 *  com.jiuqi.dc.mappingscheme.impl.define.IRuleType
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.dc.integration.execute.impl.utils;

import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;
import com.jiuqi.dc.datamapping.client.dto.DataRefListDTO;
import com.jiuqi.dc.datamapping.client.dto.IsolationParamContext;
import com.jiuqi.dc.datamapping.client.enums.DataRefFilterType;
import com.jiuqi.dc.datamapping.impl.gather.impl.DataRefConfigureServiceGather;
import com.jiuqi.dc.datamapping.impl.service.impl.IsolateRefDefineCacheProvider;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.impl.common.RuleType;
import com.jiuqi.dc.mappingscheme.impl.define.IRuleType;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UnitRefConvertUtil {
    public static String convertUnitCode2SourceId(DataSchemeDTO dataScheme, String unitCode) {
        IsolationParamContext context = new IsolationParamContext(dataScheme.getCode(), null, null, null);
        List cache = ((IsolateRefDefineCacheProvider)ApplicationContextRegister.getBean(IsolateRefDefineCacheProvider.class)).getBaseMappingCache(context, "MD_ORG");
        for (DataRefDTO dataRefDTO : cache) {
            if (!dataRefDTO.getCode().equals(unitCode)) continue;
            return dataRefDTO.getOdsCode();
        }
        return null;
    }

    public static String convertUnitCode(String dataSchemeCode, IRuleType ruleType, String odsUnitCode, Map<String, DataRefDTO> odsUnitIdRefMap, Map<String, DataRefDTO> odsUnitCodeRefMap) {
        if (ruleType == RuleType.NONE || ruleType == RuleType.ID_TO_CODE) {
            return odsUnitCode;
        }
        if (ruleType == RuleType.ID_TO_DS_CODE || ruleType == RuleType.CODE_TO_DS_CODE) {
            return dataSchemeCode.concat("|").concat(odsUnitCode);
        }
        if (odsUnitIdRefMap.get(odsUnitCode) != null) {
            return odsUnitIdRefMap.get(odsUnitCode).getCode();
        }
        if (odsUnitCodeRefMap.get(odsUnitCode) != null) {
            return odsUnitCodeRefMap.get(odsUnitCode).getCode();
        }
        return null;
    }

    public static Map<String, DataRefDTO> getUnitCodeRefMap(String dataSchemeCode) {
        DataRefListDTO dto = new DataRefListDTO();
        dto.setDataSchemeCode(dataSchemeCode);
        dto.setTableName("MD_ORG");
        dto.setFilterType(DataRefFilterType.HASREF.getCode());
        DataSchemeDTO dataSchemeDTO = ((DataSchemeService)ApplicationContextRegister.getBean(DataSchemeService.class)).getByCode(dataSchemeCode);
        Map<String, DataRefDTO> unitRefMap = ((DataRefConfigureServiceGather)ApplicationContextRegister.getBean(DataRefConfigureServiceGather.class)).getDataRefConfigureServiceBySourceDataType(dataSchemeDTO.getSourceDataType()).list(dto).getPageVo().getRows().stream().collect(Collectors.toMap(DataRefDTO::getCode, item -> item, (k1, k2) -> k2));
        return unitRefMap;
    }

    public static Map<String, DataRefDTO> getOdsUnitIdRefMap(String dataSchemeCode) {
        DataRefListDTO dto = new DataRefListDTO();
        dto.setDataSchemeCode(dataSchemeCode);
        dto.setTableName("MD_ORG");
        dto.setFilterType(DataRefFilterType.HASREF.getCode());
        DataSchemeDTO dataSchemeDTO = ((DataSchemeService)ApplicationContextRegister.getBean(DataSchemeService.class)).getByCode(dataSchemeCode);
        Map<String, DataRefDTO> unitRefMap = ((DataRefConfigureServiceGather)ApplicationContextRegister.getBean(DataRefConfigureServiceGather.class)).getDataRefConfigureServiceBySourceDataType(dataSchemeDTO.getSourceDataType()).list(dto).getPageVo().getRows().stream().collect(Collectors.toMap(DataRefDTO::getOdsCode, item -> item, (k1, k2) -> k2));
        return unitRefMap;
    }

    public static Map<String, DataRefDTO> getOdsUnitCodeRefMap(String dataSchemeCode) {
        DataRefListDTO dto = new DataRefListDTO();
        dto.setDataSchemeCode(dataSchemeCode);
        dto.setTableName("MD_ORG");
        dto.setFilterType(DataRefFilterType.HASREF.getCode());
        DataSchemeDTO dataSchemeDTO = ((DataSchemeService)ApplicationContextRegister.getBean(DataSchemeService.class)).getByCode(dataSchemeCode);
        Map<String, DataRefDTO> unitRefMap = ((DataRefConfigureServiceGather)ApplicationContextRegister.getBean(DataRefConfigureServiceGather.class)).getDataRefConfigureServiceBySourceDataType(dataSchemeDTO.getSourceDataType()).list(dto).getPageVo().getRows().stream().collect(Collectors.toMap(DataRefDTO::getOdsCode, item -> item, (k1, k2) -> k2));
        return unitRefMap;
    }
}

