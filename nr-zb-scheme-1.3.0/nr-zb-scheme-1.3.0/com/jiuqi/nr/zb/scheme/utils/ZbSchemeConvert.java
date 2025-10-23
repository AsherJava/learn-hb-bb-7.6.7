/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.format.FixMode
 *  com.jiuqi.np.definition.internal.format.FormatPropertiesBuilder
 *  com.jiuqi.np.definition.internal.format.NegativeStyle
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  com.jiuqi.np.definition.internal.parser.NumberFormatParser
 */
package com.jiuqi.nr.zb.scheme.utils;

import com.jiuqi.np.definition.internal.format.FixMode;
import com.jiuqi.np.definition.internal.format.FormatPropertiesBuilder;
import com.jiuqi.np.definition.internal.format.NegativeStyle;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.np.definition.internal.parser.NumberFormatParser;
import com.jiuqi.nr.zb.scheme.common.CompareType;
import com.jiuqi.nr.zb.scheme.core.PropInfo;
import com.jiuqi.nr.zb.scheme.core.PropLink;
import com.jiuqi.nr.zb.scheme.core.ValidationRule;
import com.jiuqi.nr.zb.scheme.core.ZbGroup;
import com.jiuqi.nr.zb.scheme.core.ZbInfo;
import com.jiuqi.nr.zb.scheme.core.ZbScheme;
import com.jiuqi.nr.zb.scheme.core.ZbSchemeGroup;
import com.jiuqi.nr.zb.scheme.core.ZbSchemeVersion;
import com.jiuqi.nr.zb.scheme.internal.dto.PropInfoDTO;
import com.jiuqi.nr.zb.scheme.internal.dto.PropLinkDTO;
import com.jiuqi.nr.zb.scheme.internal.dto.ZbGroupDTO;
import com.jiuqi.nr.zb.scheme.internal.dto.ZbInfoDTO;
import com.jiuqi.nr.zb.scheme.internal.dto.ZbSchemeDTO;
import com.jiuqi.nr.zb.scheme.internal.dto.ZbSchemeGroupDTO;
import com.jiuqi.nr.zb.scheme.internal.dto.ZbSchemeVersionDTO;
import com.jiuqi.nr.zb.scheme.internal.entity.PropInfoDO;
import com.jiuqi.nr.zb.scheme.internal.entity.PropLinkDO;
import com.jiuqi.nr.zb.scheme.internal.entity.ZbGroupDO;
import com.jiuqi.nr.zb.scheme.internal.entity.ZbInfoDO;
import com.jiuqi.nr.zb.scheme.internal.entity.ZbSchemeDO;
import com.jiuqi.nr.zb.scheme.internal.entity.ZbSchemeGroupDO;
import com.jiuqi.nr.zb.scheme.internal.entity.ZbSchemeVersionDO;
import com.jiuqi.nr.zb.scheme.utils.DimensionUtils;
import com.jiuqi.nr.zb.scheme.web.vo.BatchUpdateZbInfoVO;
import com.jiuqi.nr.zb.scheme.web.vo.FormatVO;
import com.jiuqi.nr.zb.scheme.web.vo.PropInfoVO;
import com.jiuqi.nr.zb.scheme.web.vo.ValidationRuleVO;
import com.jiuqi.nr.zb.scheme.web.vo.ZbGroupVO;
import com.jiuqi.nr.zb.scheme.web.vo.ZbInfoVO;
import com.jiuqi.nr.zb.scheme.web.vo.ZbSchemeGroupVO;
import com.jiuqi.nr.zb.scheme.web.vo.ZbSchemeVO;
import com.jiuqi.nr.zb.scheme.web.vo.ZbSchemeVersionVO;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;

public class ZbSchemeConvert {
    public static ZbSchemeDTO cto(ZbSchemeDO schemeDO) {
        if (schemeDO == null) {
            return null;
        }
        ZbSchemeDTO zbSchemeDTO = new ZbSchemeDTO();
        zbSchemeDTO.setKey(schemeDO.getKey());
        zbSchemeDTO.setTitle(schemeDO.getTitle());
        zbSchemeDTO.setDesc(schemeDO.getDesc());
        zbSchemeDTO.setCode(schemeDO.getCode());
        zbSchemeDTO.setParentKey(schemeDO.getParentKey());
        zbSchemeDTO.setUpdateTime(schemeDO.getUpdateTime());
        zbSchemeDTO.setLevel(schemeDO.getLevel());
        zbSchemeDTO.setOrder(schemeDO.getOrder());
        return zbSchemeDTO;
    }

    public static ZbSchemeDO cdo(ZbScheme zbSchemeDTO) {
        if (zbSchemeDTO == null) {
            return null;
        }
        ZbSchemeDO zbSchemeDO = new ZbSchemeDO();
        zbSchemeDO.setKey(zbSchemeDTO.getKey());
        zbSchemeDO.setTitle(zbSchemeDTO.getTitle());
        zbSchemeDO.setDesc(zbSchemeDTO.getDesc());
        zbSchemeDO.setCode(zbSchemeDTO.getCode());
        zbSchemeDO.setParentKey(zbSchemeDTO.getParentKey());
        zbSchemeDO.setUpdateTime(zbSchemeDTO.getUpdateTime());
        zbSchemeDO.setLevel(zbSchemeDTO.getLevel());
        zbSchemeDO.setOrder(zbSchemeDTO.getOrder());
        return zbSchemeDO;
    }

    public static ZbSchemeVersionDTO cto(ZbSchemeVersionDO zbSchemeVersionDO) {
        if (zbSchemeVersionDO == null) {
            return null;
        }
        ZbSchemeVersionDTO zbSchemeVersionDTO = new ZbSchemeVersionDTO();
        zbSchemeVersionDTO.setKey(zbSchemeVersionDO.getKey());
        zbSchemeVersionDTO.setSchemeKey(zbSchemeVersionDO.getSchemeKey());
        zbSchemeVersionDTO.setCode(zbSchemeVersionDO.getCode());
        zbSchemeVersionDTO.setOrder(zbSchemeVersionDO.getOrder());
        zbSchemeVersionDTO.setTitle(zbSchemeVersionDO.getTitle());
        zbSchemeVersionDTO.setStartPeriod(zbSchemeVersionDO.getStartPeriod());
        zbSchemeVersionDTO.setEndPeriod(zbSchemeVersionDO.getEndPeriod());
        zbSchemeVersionDTO.setLevel(zbSchemeVersionDO.getLevel());
        zbSchemeVersionDTO.setUpdateTime(zbSchemeVersionDO.getUpdateTime());
        zbSchemeVersionDTO.setStatus(zbSchemeVersionDO.getStatus());
        return zbSchemeVersionDTO;
    }

    public static ZbGroupDTO cto(ZbGroupDO zbGroupDO) {
        if (zbGroupDO == null) {
            return null;
        }
        ZbGroupDTO zbGroup = new ZbGroupDTO();
        zbGroup.setKey(zbGroupDO.getKey());
        zbGroup.setTitle(zbGroupDO.getTitle());
        zbGroup.setParentKey(zbGroupDO.getParentKey());
        zbGroup.setVersionKey(zbGroupDO.getVersionKey());
        zbGroup.setSchemeKey(zbGroupDO.getSchemeKey());
        zbGroup.setLevel(zbGroupDO.getLevel());
        zbGroup.setUpdateTime(zbGroupDO.getUpdateTime());
        zbGroup.setOrder(zbGroupDO.getOrder());
        return zbGroup;
    }

    public static ZbGroupDO cdo(ZbGroup zbGroup) {
        ZbGroupDO zbGroupDO = new ZbGroupDO();
        zbGroupDO.setKey(zbGroup.getKey());
        zbGroupDO.setSchemeKey(zbGroup.getSchemeKey());
        zbGroupDO.setVersionKey(zbGroup.getVersionKey());
        zbGroupDO.setTitle(zbGroup.getTitle());
        zbGroupDO.setParentKey(zbGroup.getParentKey());
        zbGroupDO.setUpdateTime(zbGroup.getUpdateTime());
        zbGroupDO.setLevel(zbGroup.getLevel());
        zbGroupDO.setOrder(zbGroup.getOrder());
        return zbGroupDO;
    }

    public static ZbSchemeVersionDO cdo(ZbSchemeVersion version) {
        ZbSchemeVersionDO zbSchemeVersionDO = new ZbSchemeVersionDO();
        zbSchemeVersionDO.setKey(version.getKey());
        zbSchemeVersionDO.setSchemeKey(version.getSchemeKey());
        zbSchemeVersionDO.setCode(version.getCode());
        zbSchemeVersionDO.setOrder(version.getOrder());
        zbSchemeVersionDO.setTitle(version.getTitle());
        zbSchemeVersionDO.setStartPeriod(version.getStartPeriod());
        zbSchemeVersionDO.setEndPeriod(version.getEndPeriod());
        zbSchemeVersionDO.setLevel(version.getLevel());
        zbSchemeVersionDO.setUpdateTime(version.getUpdateTime());
        zbSchemeVersionDO.setStatus(version.getStatus());
        return zbSchemeVersionDO;
    }

    public static ZbInfoDTO cto(ZbInfoDO zbInfoDO) {
        if (zbInfoDO == null) {
            return null;
        }
        ZbInfoDTO zbDTO = new ZbInfoDTO();
        zbDTO.setKey(zbInfoDO.getKey());
        zbDTO.setSchemeKey(zbInfoDO.getSchemeKey());
        zbDTO.setVersionKey(zbInfoDO.getVersionKey());
        zbDTO.setTitle(zbInfoDO.getTitle());
        zbDTO.setCode(zbInfoDO.getCode());
        zbDTO.setParentKey(zbInfoDO.getParentKey());
        zbDTO.setDesc(zbInfoDO.getDesc());
        zbDTO.setType(zbInfoDO.getType());
        zbDTO.setGatherType(zbInfoDO.getGatherType());
        zbDTO.setFormula(zbInfoDO.getFormula());
        zbDTO.setFormulaDesc(zbInfoDO.getFormulaDesc());
        zbDTO.setDefaultValue(zbInfoDO.getDefaultValue());
        zbDTO.setMeasureUnit(zbInfoDO.getMeasureUnit());
        zbDTO.setValidationRules(zbInfoDO.getValidationRules());
        zbDTO.setFormatProperties(zbInfoDO.getFormatProperties());
        zbDTO.setRefEntityId(zbInfoDO.getRefEntityId());
        zbDTO.setPrecision(zbInfoDO.getPrecision());
        zbDTO.setDecimal(zbInfoDO.getDecimal());
        zbDTO.setApplyType(zbInfoDO.getApplyType());
        zbDTO.setAllowUndefinedCode(zbInfoDO.isAllowUndefinedCode());
        zbDTO.setAllowMultipleSelect(zbInfoDO.isAllowMultipleSelect());
        zbDTO.setLevel(zbInfoDO.getLevel());
        zbDTO.setUpdateTime(zbInfoDO.getUpdateTime());
        zbDTO.setOrder(zbInfoDO.getOrder());
        zbDTO.setDataType(zbInfoDO.getDataType());
        zbDTO.setExtProp(zbInfoDO.getExtProp());
        return zbDTO;
    }

    public static ZbInfoDO cdo(ZbInfo zbInfo) {
        ZbInfoDO zbInfoDO = new ZbInfoDO();
        zbInfoDO.setKey(zbInfo.getKey());
        zbInfoDO.setSchemeKey(zbInfo.getSchemeKey());
        zbInfoDO.setVersionKey(zbInfo.getVersionKey());
        zbInfoDO.setTitle(zbInfo.getTitle());
        zbInfoDO.setCode(zbInfo.getCode());
        zbInfoDO.setParentKey(zbInfo.getParentKey());
        zbInfoDO.setDesc(zbInfo.getDesc());
        zbInfoDO.setType(zbInfo.getType());
        zbInfoDO.setGatherType(zbInfo.getGatherType());
        zbInfoDO.setFormula(zbInfo.getFormula());
        zbInfoDO.setFormulaDesc(zbInfo.getFormulaDesc());
        zbInfoDO.setDefaultValue(zbInfo.getDefaultValue());
        zbInfoDO.setMeasureUnit(zbInfo.getMeasureUnit());
        zbInfoDO.setValidationRules(zbInfo.getValidationRules());
        zbInfoDO.setFormatProperties(zbInfo.getFormatProperties());
        zbInfoDO.setRefEntityId(zbInfo.getRefEntityId());
        zbInfoDO.setPrecision(zbInfo.getPrecision());
        zbInfoDO.setDecimal(zbInfo.getDecimal());
        zbInfoDO.setNullable(zbInfo.isNullable());
        zbInfoDO.setApplyType(zbInfo.getApplyType());
        zbInfoDO.setAllowUndefinedCode(zbInfo.isAllowUndefinedCode());
        zbInfoDO.setAllowMultipleSelect(zbInfo.isAllowMultipleSelect());
        zbInfoDO.setUpdateTime(zbInfo.getUpdateTime());
        zbInfoDO.setOrder(zbInfo.getOrder());
        zbInfoDO.setLevel(zbInfo.getLevel());
        zbInfoDO.setDataType(zbInfo.getDataType());
        zbInfoDO.setExtProp(zbInfo.getExtProp());
        return zbInfoDO;
    }

    public static PropInfoDTO cto(PropInfoDO prop) {
        if (prop == null) {
            return null;
        }
        PropInfoDTO zbPropDTO = new PropInfoDTO();
        zbPropDTO.setKey(prop.getKey());
        zbPropDTO.setTitle(prop.getTitle());
        zbPropDTO.setOrder(prop.getOrder());
        zbPropDTO.setFieldName(prop.getFieldName());
        zbPropDTO.setDecimal(prop.getDecimal());
        zbPropDTO.setPrecision(prop.getPrecision());
        zbPropDTO.setDefaultValue(prop.getDefaultValue());
        zbPropDTO.setReferEntityId(prop.getReferEntityId());
        zbPropDTO.setDataType(prop.getDataType());
        zbPropDTO.setLevel(prop.getLevel());
        zbPropDTO.setUpdateTime(prop.getUpdateTime());
        zbPropDTO.setMultiple(prop.isMultiple());
        return zbPropDTO;
    }

    public static PropInfoDO cdo(PropInfo prop) {
        if (prop == null) {
            return null;
        }
        PropInfoDO propInfo = new PropInfoDO();
        propInfo.setKey(prop.getKey());
        propInfo.setTitle(prop.getTitle());
        propInfo.setOrder(prop.getOrder());
        propInfo.setFieldName(prop.getFieldName());
        propInfo.setDecimal(prop.getDecimal());
        propInfo.setPrecision(prop.getPrecision());
        propInfo.setDefaultValue(prop.getDefaultValue());
        propInfo.setReferEntityId(prop.getReferEntityId());
        propInfo.setDataType(prop.getDataType());
        propInfo.setLevel(prop.getLevel());
        propInfo.setUpdateTime(prop.getUpdateTime());
        propInfo.setMultiple(prop.isMultiple());
        return propInfo;
    }

    public static ZbSchemeGroupDTO cto(ZbSchemeGroupDO zbSchemeGroupDO) {
        if (zbSchemeGroupDO == null) {
            return null;
        }
        ZbSchemeGroupDTO zbSchemeGroupDTO = new ZbSchemeGroupDTO();
        zbSchemeGroupDTO.setKey(zbSchemeGroupDO.getKey());
        zbSchemeGroupDTO.setTitle(zbSchemeGroupDO.getTitle());
        zbSchemeGroupDTO.setDesc(zbSchemeGroupDO.getDesc());
        zbSchemeGroupDTO.setParentKey(zbSchemeGroupDO.getParentKey());
        zbSchemeGroupDTO.setUpdateTime(zbSchemeGroupDO.getUpdateTime());
        zbSchemeGroupDTO.setLevel(zbSchemeGroupDO.getLevel());
        zbSchemeGroupDTO.setOrder(zbSchemeGroupDO.getOrder());
        return zbSchemeGroupDTO;
    }

    public static ZbSchemeGroupDO cdo(ZbSchemeGroup groupDTO) {
        if (groupDTO == null) {
            return null;
        }
        ZbSchemeGroupDO zbSchemeGroupDO = new ZbSchemeGroupDO();
        zbSchemeGroupDO.setKey(groupDTO.getKey());
        zbSchemeGroupDO.setTitle(groupDTO.getTitle());
        zbSchemeGroupDO.setDesc(groupDTO.getDesc());
        zbSchemeGroupDO.setParentKey(groupDTO.getParentKey());
        zbSchemeGroupDO.setUpdateTime(groupDTO.getUpdateTime());
        zbSchemeGroupDO.setLevel(groupDTO.getLevel());
        zbSchemeGroupDO.setOrder(groupDTO.getOrder());
        return zbSchemeGroupDO;
    }

    public static ZbInfoDTO cto(ZbInfoVO vo) {
        ArrayList<ValidationRule> list;
        ZbInfoDTO zbInfoDTO = new ZbInfoDTO();
        zbInfoDTO.setKey(vo.getKey());
        zbInfoDTO.setSchemeKey(vo.getSchemeKey());
        zbInfoDTO.setVersionKey(vo.getVersionKey());
        zbInfoDTO.setTitle(vo.getTitle());
        zbInfoDTO.setCode(vo.getCode());
        zbInfoDTO.setParentKey(vo.getParentKey());
        zbInfoDTO.setDesc(vo.getDesc());
        zbInfoDTO.setDataType(vo.getDataType());
        zbInfoDTO.setType(vo.getType());
        zbInfoDTO.setGatherType(vo.getGatherType());
        zbInfoDTO.setFormula(vo.getFormula());
        zbInfoDTO.setFormulaDesc(vo.getFormulaDesc());
        zbInfoDTO.setDefaultValue(vo.getDefaultValue());
        zbInfoDTO.setMeasureUnit(DimensionUtils.getMeasureUnit(vo.getDimension(), vo.getMeasureUnit()));
        FormatVO format = vo.getFormatVO();
        if (format != null && format.getFormatType() != 0) {
            FormatPropertiesBuilder builder = new FormatPropertiesBuilder();
            FormatProperties formatProperties = builder.setCurrency(format.getCurrency()).setDisplayDigits(format.getDisplayDigits()).setFixMode(format.getFixMode()).setFormatType(format.getFormatType()).setPattern(format.getPattern()).setCurrency(format.getCurrency()).setThousands(format.isThousands()).setNegativeStyle(format.getNegativeStyle()).build();
            zbInfoDTO.setFormatProperties(formatProperties);
        }
        if (!CollectionUtils.isEmpty(vo.getValidationRules())) {
            List<ValidationRuleVO> validationRules = vo.getValidationRules();
            list = new ArrayList<ValidationRule>(validationRules.size());
            for (ValidationRuleVO validationRule : validationRules) {
                list.add(validationRule.toValidationRuleDTO());
            }
            zbInfoDTO.setValidationRules(list);
        }
        zbInfoDTO.setRefEntityId(vo.getRefEntityId());
        zbInfoDTO.setPrecision(vo.getPrecision());
        zbInfoDTO.setDecimal(vo.getDecimal());
        zbInfoDTO.setNullable(vo.isNullable() != null && vo.isNullable() != false);
        zbInfoDTO.setApplyType(vo.getApplyType());
        zbInfoDTO.setAllowUndefinedCode(vo.isAllowUndefinedCode() != null && vo.isAllowUndefinedCode() != false);
        zbInfoDTO.setAllowMultipleSelect(vo.isAllowMultipleSelect() != null && vo.isAllowMultipleSelect() != false);
        zbInfoDTO.setUpdateTime(vo.getUpdateTime());
        zbInfoDTO.setLevel(vo.getLevel());
        zbInfoDTO.setOrder(vo.getOrder());
        Map<String, PropInfoVO> propData = vo.getPropData();
        if (!CollectionUtils.isEmpty(propData)) {
            list = new ArrayList(propData.size());
            for (PropInfoVO value : propData.values()) {
                list.add((ValidationRule)((Object)ZbSchemeConvert.cto(value)));
            }
            zbInfoDTO.setExtProp(list);
        }
        return zbInfoDTO;
    }

    public static PropInfoDTO cto(PropInfoVO value) {
        if (value == null) {
            return null;
        }
        PropInfoDTO propInfoDTO = new PropInfoDTO();
        propInfoDTO.setKey(value.getKey());
        propInfoDTO.setTitle(value.getTitle());
        propInfoDTO.setOrder(value.getOrder());
        propInfoDTO.setFieldName(value.getFieldName());
        propInfoDTO.setDecimal(value.getDecimal());
        propInfoDTO.setPrecision(value.getPrecision());
        propInfoDTO.setReferEntityId(value.getReferEntityId());
        propInfoDTO.setDataType(value.getDataType());
        propInfoDTO.setMultiple(value.isMultiple());
        propInfoDTO.setDefaultValue(value.getDefaultValue());
        propInfoDTO.setValue(value.getValue());
        return propInfoDTO;
    }

    public static ZbInfoVO cvo(ZbInfo zbInfo) {
        NegativeStyle negativeStyle;
        FixMode fixMode;
        Integer displayDigits;
        ZbInfoVO zbInfoVO = new ZbInfoVO();
        zbInfoVO.setKey(zbInfo.getKey());
        zbInfoVO.setSchemeKey(zbInfo.getSchemeKey());
        zbInfoVO.setPeriod(zbInfo.getVersionKey());
        zbInfoVO.setTitle(zbInfo.getTitle());
        zbInfoVO.setCode(zbInfo.getCode());
        zbInfoVO.setParentKey(zbInfo.getParentKey());
        zbInfoVO.setDesc(zbInfo.getDesc());
        zbInfoVO.setType(zbInfo.getType());
        zbInfoVO.setDataType(zbInfo.getDataType());
        zbInfoVO.setGatherType(zbInfo.getGatherType());
        zbInfoVO.setFormula(zbInfo.getFormula());
        zbInfoVO.setFormulaDesc(zbInfo.getFormulaDesc());
        zbInfoVO.setDefaultValue(zbInfo.getDefaultValue());
        zbInfoVO.setMeasureUnit(DimensionUtils.getMeasureUnit(zbInfo.getMeasureUnit()));
        zbInfoVO.setDimension(DimensionUtils.getDimension(zbInfo.getMeasureUnit()));
        zbInfoVO.setRefEntityId(zbInfo.getRefEntityId());
        zbInfoVO.setPrecision(zbInfo.getPrecision());
        zbInfoVO.setDecimal(zbInfo.getDecimal());
        zbInfoVO.setNullable(zbInfo.isNullable());
        zbInfoVO.setApplyType(zbInfo.getApplyType());
        zbInfoVO.setAllowUndefinedCode(zbInfo.isAllowUndefinedCode());
        zbInfoVO.setAllowMultipleSelect(zbInfo.isAllowMultipleSelect());
        zbInfoVO.setUpdateTime(zbInfo.getUpdateTime());
        zbInfoVO.setLevel(zbInfo.getLevel());
        zbInfoVO.setOrder(zbInfo.getOrder());
        List<ValidationRule> validationRules = zbInfo.getValidationRules();
        if (!CollectionUtils.isEmpty(validationRules)) {
            ArrayList<ValidationRuleVO> list = new ArrayList<ValidationRuleVO>(validationRules.size());
            zbInfoVO.setValidationRules(list);
            for (ValidationRule validationRule : validationRules) {
                if (validationRule.getCompareType() == null || validationRule.getCompareType() == CompareType.NOTNULL) continue;
                ValidationRuleVO validationRuleVO = new ValidationRuleVO(validationRule);
                list.add(validationRuleVO);
            }
        }
        FormatProperties formatProperties = zbInfo.getFormatProperties();
        NumberFormatParser parse = NumberFormatParser.parse((FormatProperties)formatProperties);
        FormatVO formatVO = new FormatVO();
        formatVO.setFormatType(parse.getFormatType());
        formatVO.setCurrency(parse.getCurrency());
        formatVO.setThousands(parse.isThousands());
        if (formatProperties != null) {
            formatVO.setPattern(formatProperties.getPattern());
        }
        if ((displayDigits = parse.getDisplayDigits()) != null) {
            formatVO.setDisplayDigits(displayDigits);
        }
        if ((fixMode = parse.getFixMode()) != null) {
            formatVO.setFixMode(fixMode.getValue());
        }
        if ((negativeStyle = parse.getNegativeStyle()) != null) {
            formatVO.setNegativeStyle(negativeStyle.getValue());
        }
        zbInfoVO.setFormatVO(formatVO);
        LinkedHashMap<String, PropInfoVO> propInfoVOMap = new LinkedHashMap<String, PropInfoVO>();
        zbInfoVO.setPropData(propInfoVOMap);
        List<PropInfo> extProp = zbInfo.getExtProp();
        if (extProp != null) {
            for (PropInfo propInfo : extProp) {
                propInfoVOMap.put(propInfo.getKey(), ZbSchemeConvert.cvo(propInfo));
            }
        }
        zbInfoVO.setVersionKey(zbInfo.getVersionKey());
        return zbInfoVO;
    }

    public static PropLinkDTO cto(PropLinkDO propLinkDO) {
        if (propLinkDO == null) {
            return null;
        }
        PropLinkDTO dto = new PropLinkDTO();
        dto.setKey(propLinkDO.getKey());
        dto.setSchemeKey(propLinkDO.getSchemeKey());
        dto.setPropKey(propLinkDO.getPropKey());
        dto.setLevel(propLinkDO.getLevel());
        return dto;
    }

    public static PropLinkDO cdo(PropLink group) {
        PropLinkDO propLinkDO = new PropLinkDO();
        propLinkDO.setKey(group.getKey());
        propLinkDO.setSchemeKey(group.getSchemeKey());
        propLinkDO.setPropKey(group.getPropKey());
        propLinkDO.setLevel(group.getLevel());
        return propLinkDO;
    }

    public static ZbSchemeVO cvo(ZbScheme zbScheme) {
        if (zbScheme == null) {
            return null;
        }
        ZbSchemeVO zbSchemeVO = new ZbSchemeVO();
        zbSchemeVO.setKey(zbScheme.getKey());
        zbSchemeVO.setTitle(zbScheme.getTitle());
        zbSchemeVO.setDesc(zbScheme.getDesc());
        zbSchemeVO.setCode(zbScheme.getCode());
        zbSchemeVO.setParentKey(zbScheme.getParentKey());
        zbSchemeVO.setLevel(zbScheme.getLevel());
        zbSchemeVO.setOrder(zbScheme.getOrder());
        return zbSchemeVO;
    }

    public static ZbSchemeVersionVO cvo(ZbSchemeVersion zbSchemeVersion) {
        if (zbSchemeVersion == null) {
            return null;
        }
        ZbSchemeVersionVO zbSchemeVersionVO = new ZbSchemeVersionVO();
        zbSchemeVersionVO.setCurrentPeriod(zbSchemeVersion.getStartPeriod());
        zbSchemeVersionVO.setKey(zbSchemeVersion.getKey());
        zbSchemeVersionVO.setSchemeKey(zbSchemeVersion.getSchemeKey());
        zbSchemeVersionVO.setCode(zbSchemeVersion.getCode());
        zbSchemeVersionVO.setOrder(zbSchemeVersion.getOrder());
        zbSchemeVersionVO.setTitle(zbSchemeVersion.getTitle());
        zbSchemeVersionVO.setStartPeriod(zbSchemeVersion.getStartPeriod());
        zbSchemeVersionVO.setEndPeriod(zbSchemeVersion.getEndPeriod());
        zbSchemeVersionVO.setStatus(zbSchemeVersion.getStatus());
        return zbSchemeVersionVO;
    }

    public static PropInfoVO cvo(PropInfo propInfo) {
        if (propInfo == null) {
            return null;
        }
        PropInfoVO propInfoVO = new PropInfoVO();
        propInfoVO.setKey(propInfo.getKey());
        propInfoVO.setTitle(propInfo.getTitle());
        propInfoVO.setOrder(propInfo.getOrder());
        propInfoVO.setFieldName(propInfo.getFieldName());
        propInfoVO.setDecimal(propInfo.getDecimal());
        propInfoVO.setPrecision(propInfo.getPrecision());
        propInfoVO.setDefaultValue(propInfo.getDefaultValue());
        propInfoVO.setReferEntityId(propInfo.getReferEntityId());
        propInfoVO.setDataType(propInfo.getDataType());
        propInfoVO.setMultiple(propInfo.isMultiple());
        propInfoVO.setValue(propInfo.getValue());
        return propInfoVO;
    }

    public static ZbGroupVO cvo(ZbGroup zbGroup) {
        if (zbGroup == null) {
            return null;
        }
        ZbGroupVO zbGroupVO = new ZbGroupVO();
        zbGroupVO.setKey(zbGroup.getKey());
        zbGroupVO.setTitle(zbGroup.getTitle());
        zbGroupVO.setParentKey(zbGroup.getParentKey());
        zbGroupVO.setPeriod(zbGroup.getVersionKey());
        zbGroupVO.setSchemeKey(zbGroup.getSchemeKey());
        zbGroupVO.setUpdateTime(zbGroup.getUpdateTime());
        zbGroupVO.setLevel(zbGroup.getLevel());
        zbGroupVO.setOrder(zbGroup.getOrder());
        zbGroupVO.setVersionKey(zbGroup.getVersionKey());
        return zbGroupVO;
    }

    public static ZbSchemeGroupVO cvo(ZbSchemeGroup zbSchemeGroup) {
        if (zbSchemeGroup == null) {
            return null;
        }
        ZbSchemeGroupVO zbSchemeGroupVO = new ZbSchemeGroupVO();
        zbSchemeGroupVO.setKey(zbSchemeGroup.getKey());
        zbSchemeGroupVO.setTitle(zbSchemeGroup.getTitle());
        zbSchemeGroupVO.setDesc(zbSchemeGroup.getDesc());
        zbSchemeGroupVO.setParentKey(zbSchemeGroup.getParentKey());
        zbSchemeGroupVO.setUpdateTime(zbSchemeGroup.getUpdateTime());
        zbSchemeGroupVO.setLevel(zbSchemeGroup.getLevel());
        zbSchemeGroupVO.setOrder(zbSchemeGroup.getOrder());
        return zbSchemeGroupVO;
    }

    public static void uto(ZbInfo zbInfo, BatchUpdateZbInfoVO batchUpdateZbInfoVO) {
        Map<String, PropInfoVO> propData;
        FormatVO format;
        List<ValidationRuleVO> validationRules;
        zbInfo.setDesc(batchUpdateZbInfoVO.getDesc());
        if (batchUpdateZbInfoVO.getZbType() != null) {
            zbInfo.setType(batchUpdateZbInfoVO.getZbType());
        }
        if (batchUpdateZbInfoVO.getDataFieldType() != null) {
            zbInfo.setDataType(batchUpdateZbInfoVO.getDataFieldType());
        }
        if (batchUpdateZbInfoVO.getApplyType() != null) {
            zbInfo.setApplyType(batchUpdateZbInfoVO.getApplyType());
        }
        if (batchUpdateZbInfoVO.isAllowMultipleSelect() != null) {
            zbInfo.setAllowMultipleSelect(batchUpdateZbInfoVO.isAllowMultipleSelect());
        }
        if (batchUpdateZbInfoVO.isAllowUndefinedCode() != null) {
            zbInfo.setAllowUndefinedCode(batchUpdateZbInfoVO.isAllowUndefinedCode());
        }
        if (batchUpdateZbInfoVO.getDecimal() != null) {
            zbInfo.setDecimal(batchUpdateZbInfoVO.getDecimal());
        }
        if (batchUpdateZbInfoVO.getPrecision() != null) {
            zbInfo.setPrecision(batchUpdateZbInfoVO.getPrecision());
        }
        if (batchUpdateZbInfoVO.getDataFieldGatherType() != null) {
            zbInfo.setGatherType(batchUpdateZbInfoVO.getDataFieldGatherType());
        }
        if (batchUpdateZbInfoVO.isNullable() != null) {
            zbInfo.setNullable(batchUpdateZbInfoVO.isNullable());
        }
        if (!CollectionUtils.isEmpty(validationRules = batchUpdateZbInfoVO.getValidationRules())) {
            ArrayList<ValidationRule> list = new ArrayList<ValidationRule>(validationRules.size());
            for (ValidationRuleVO validationRule : validationRules) {
                list.add(validationRule.toValidationRuleDTO());
            }
            zbInfo.setValidationRules(list);
        }
        if ((format = batchUpdateZbInfoVO.getFormatVO()) != null && format.getFormatType() != 0) {
            FormatPropertiesBuilder builder = new FormatPropertiesBuilder();
            FormatProperties formatProperties = builder.setCurrency(format.getCurrency()).setDisplayDigits(format.getDisplayDigits()).setFixMode(format.getFixMode()).setFormatType(format.getFormatType()).setPattern(format.getPattern()).setCurrency(format.getCurrency()).setThousands(format.isThousands()).setNegativeStyle(format.getNegativeStyle()).build();
            zbInfo.setFormatProperties(formatProperties);
        }
        if (!CollectionUtils.isEmpty(propData = batchUpdateZbInfoVO.getExpandPropsObj())) {
            ArrayList<PropInfo> list = new ArrayList<PropInfo>(propData.size());
            for (PropInfoVO value : propData.values()) {
                list.add(ZbSchemeConvert.cto(value));
            }
            zbInfo.setExtProp(list);
        }
        if (batchUpdateZbInfoVO.getFormula() != null) {
            zbInfo.setFormula(batchUpdateZbInfoVO.getFormula());
        }
        if (batchUpdateZbInfoVO.getFormulaDesc() != null) {
            zbInfo.setFormulaDesc(batchUpdateZbInfoVO.getFormulaDesc());
        }
        if (batchUpdateZbInfoVO.getDimension() != null) {
            zbInfo.setMeasureUnit(DimensionUtils.getMeasureUnit(batchUpdateZbInfoVO.getDimension(), batchUpdateZbInfoVO.getMeasureUnit()));
        }
    }

    public static ZbGroup cto(ZbGroupVO zbGroup) {
        if (zbGroup == null) {
            return null;
        }
        ZbGroupDTO dto = new ZbGroupDTO();
        dto.setKey(zbGroup.getKey());
        dto.setTitle(zbGroup.getTitle());
        dto.setParentKey(zbGroup.getParentKey());
        dto.setSchemeKey(zbGroup.getSchemeKey());
        dto.setLevel(zbGroup.getLevel());
        dto.setOrder(zbGroup.getOrder());
        dto.setUpdateTime(zbGroup.getUpdateTime());
        return dto;
    }
}

