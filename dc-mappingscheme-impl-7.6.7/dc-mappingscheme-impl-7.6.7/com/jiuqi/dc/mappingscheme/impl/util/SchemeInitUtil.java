/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.mappingscheme.client.common.Columns
 *  com.jiuqi.dc.mappingscheme.client.common.FieldDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.vo.DimMappingVO
 */
package com.jiuqi.dc.mappingscheme.impl.util;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.mappingscheme.client.common.Columns;
import com.jiuqi.dc.mappingscheme.client.common.FieldDTO;
import com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.vo.DimMappingVO;
import com.jiuqi.dc.mappingscheme.impl.enums.FieldMappingType;
import java.util.ArrayList;
import java.util.List;

public class SchemeInitUtil {
    public static List<FieldMappingDefineDTO> commonFieldInfo() {
        ArrayList<FieldMappingDefineDTO> defineDTOList = new ArrayList<FieldMappingDefineDTO>();
        defineDTOList.add(new FieldMappingDefineDTO("ID", "\u6807\u8bc6", "ID"));
        defineDTOList.add(new FieldMappingDefineDTO("CODE", "\u4ee3\u7801", "CODE"));
        defineDTOList.add(new FieldMappingDefineDTO("NAME", "\u540d\u79f0", "NAME"));
        return defineDTOList;
    }

    public static List<Columns> commonColumnsInfo() {
        ArrayList<Columns> columnsList = new ArrayList<Columns>();
        columnsList.add(new Columns("ID", "\u6807\u8bc6", "ID"));
        columnsList.add(new Columns("CODE", "\u4ee3\u7801", "CODE"));
        columnsList.add(new Columns("NAME", "\u540d\u79f0", "NAME"));
        return columnsList;
    }

    public static FieldDTO createCustomField() {
        return new FieldDTO("CUSTOMFIELD", "\u81ea\u5b9a\u4e49");
    }

    public static DimMappingVO createDimMapping(String code, String name, String ruleType, String autoMatchDim, String odsFieldName, String isolationStrategy, String advancedSql, Boolean isolationStrategyFixedFlag) {
        DimMappingVO dimMappingVO = new DimMappingVO();
        dimMappingVO.setCode(code);
        dimMappingVO.setName(name);
        dimMappingVO.setRuleType(ruleType);
        dimMappingVO.setAutoMatchDim(autoMatchDim);
        dimMappingVO.setOdsFieldName(odsFieldName);
        dimMappingVO.setIsolationStrategy(isolationStrategy);
        dimMappingVO.setAdvancedSql(advancedSql);
        dimMappingVO.setIsolationStrategyFixedFlag(isolationStrategyFixedFlag);
        dimMappingVO.setOdsFieldFixedFlag(Boolean.valueOf(true));
        dimMappingVO.setBaseMapping(SchemeInitUtil.commonColumnsInfo());
        dimMappingVO.setFieldMappingType(FieldMappingType.SOURCE_FIELD.getCode());
        return dimMappingVO;
    }

    public static DimMappingVO createDimMapping(FieldDTO field, String code, String name) {
        return SchemeInitUtil.createDimMapping(field, code, name, new DimMappingVO());
    }

    public static <T extends DimMappingVO> T createDimMapping(FieldDTO field, String code, String name, T dimMappingVO) {
        dimMappingVO.setCode(code);
        dimMappingVO.setName(name);
        dimMappingVO.setOdsFieldName(field.getName());
        dimMappingVO.setOdsFieldTitle(field.getTitle());
        dimMappingVO.setRuleType(field.getRuleType());
        dimMappingVO.setIsolationStrategy(field.getIsolationStrategy());
        dimMappingVO.setIsolationStrategyFixedFlag(field.getIsolationStrategyFixedFlag());
        dimMappingVO.setAdvancedSql(field.getAdvancedSql());
        dimMappingVO.setOdsFieldFixedFlag(field.getOdsFieldFixedFlag());
        dimMappingVO.setBaseMapping(SchemeInitUtil.commonColumnsInfo());
        dimMappingVO.setFieldMappingType(StringUtils.isEmpty((String)field.getFieldMappingType()) ? FieldMappingType.SOURCE_FIELD.getCode() : field.getFieldMappingType());
        return dimMappingVO;
    }

    public static List<DimMappingVO> createDimMapping(List<FieldDTO> fields) {
        ArrayList<DimMappingVO> dimMappingVOList = new ArrayList<DimMappingVO>();
        for (FieldDTO field : fields) {
            dimMappingVOList.add(SchemeInitUtil.createDimMapping(field, field.getName(), field.getTitle()));
        }
        return dimMappingVOList;
    }
}

