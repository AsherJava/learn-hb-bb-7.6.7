/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.service.FormatPropertiesBuilder
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldApplyType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.web.facade.ValidationRuleVO
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 */
package com.jiuqi.nr.task.form.util;

import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.service.FormatPropertiesBuilder;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldApplyType;
import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.web.facade.ValidationRuleVO;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.task.form.field.dto.DataFieldDTO;
import com.jiuqi.nr.task.form.field.dto.DataFieldSettingDTO;
import com.jiuqi.nr.task.form.link.dto.FormatDTO;
import com.jiuqi.nr.task.form.util.DimensionWrapper;
import com.jiuqi.nr.task.form.util.FormatPropertiesUtils;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class FieldBeanUtils {
    public static DataFieldDTO toDto(DesignDataField designDataField) {
        DataFieldDTO dataFieldDTO = new DataFieldDTO();
        FieldBeanUtils.copyDtoProperties(dataFieldDTO, designDataField);
        return dataFieldDTO;
    }

    public static List<DataFieldDTO> toDtoList(List<DesignDataField> designDataFields) {
        if (CollectionUtils.isEmpty(designDataFields)) {
            return Collections.emptyList();
        }
        ArrayList<DataFieldDTO> list = new ArrayList<DataFieldDTO>(designDataFields.size());
        for (DesignDataField designDataField : designDataFields) {
            DataFieldDTO dataFieldDTO = new DataFieldDTO();
            FieldBeanUtils.copyDtoProperties(dataFieldDTO, designDataField);
            list.add(dataFieldDTO);
        }
        return list;
    }

    private static void copyDtoProperties(DataFieldDTO dataFieldDTO, DesignDataField designDataField) {
        dataFieldDTO.setKey(designDataField.getKey());
        dataFieldDTO.setOrder(designDataField.getOrder());
        dataFieldDTO.setCode(designDataField.getCode());
        dataFieldDTO.setTitle(designDataField.getTitle());
        dataFieldDTO.setDataFieldType(designDataField.getDataFieldType().getValue());
        dataFieldDTO.setPrecision(designDataField.getPrecision());
        dataFieldDTO.setDecimal(designDataField.getDecimal());
        dataFieldDTO.setNullable(designDataField.getNullable());
        dataFieldDTO.setAllowUndefinedCode(designDataField.getAllowUndefinedCode());
        dataFieldDTO.setDesc(designDataField.getDesc());
        dataFieldDTO.setDefaultValue(designDataField.getDefaultValue());
        if (designDataField.getDataFieldGatherType() != null) {
            dataFieldDTO.setDataFieldGatherType(designDataField.getDataFieldGatherType().getValue());
        }
        dataFieldDTO.setDataTableKey(designDataField.getDataTableKey());
        dataFieldDTO.setRefDataEntityKey(designDataField.getRefDataEntityKey());
        dataFieldDTO.setDataSchemeKey(designDataField.getDataSchemeKey());
        dataFieldDTO.setDataFieldKind(designDataField.getDataFieldKind().getValue());
        dataFieldDTO.setAllowMultipleSelect(designDataField.getAllowMultipleSelect());
        dataFieldDTO.setDataMaskCode(designDataField.getDataMaskCode());
    }

    public static DataFieldSettingDTO toSettingDto(DesignDataField designDataField) {
        if (designDataField == null) {
            return null;
        }
        DataFieldSettingDTO dataFieldSettingDTO = new DataFieldSettingDTO();
        FieldBeanUtils.copyFieldToSetting(designDataField, dataFieldSettingDTO);
        return dataFieldSettingDTO;
    }

    public static DataFieldSettingDTO toSettingDto(DesignDataField designDataField, IEntityMetaService entityMetaService, PeriodEngineService periodEngineService) {
        if (designDataField == null) {
            return null;
        }
        DataFieldSettingDTO dataFieldSettingDTO = new DataFieldSettingDTO();
        FieldBeanUtils.copyFieldToSetting(designDataField, dataFieldSettingDTO);
        if (StringUtils.hasLength(designDataField.getRefDataEntityKey()) && DataFieldKind.PUBLIC_FIELD_DIM.getValue() != designDataField.getDataFieldKind().getValue()) {
            dataFieldSettingDTO.setRefDataEntityKey(designDataField.getRefDataEntityKey());
            if (periodEngineService.getPeriodAdapter().isPeriodEntity(designDataField.getRefDataEntityKey())) {
                IPeriodEntity periodEntity = periodEngineService.getPeriodAdapter().getPeriodEntity(designDataField.getRefDataEntityKey());
                dataFieldSettingDTO.setRefDataEntityTitle(periodEntity.getTitle());
            } else {
                IEntityDefine queryEntity = entityMetaService.queryEntity(designDataField.getRefDataEntityKey());
                dataFieldSettingDTO.setRefDataEntityTitle(queryEntity.getTitle());
            }
        }
        return dataFieldSettingDTO;
    }

    public static List<DataFieldSettingDTO> toSettingList(List<DesignDataField> dataFields, IEntityMetaService entityMetaService, PeriodEngineService periodEngineService, IDesignDataSchemeService designDataSchemeService) {
        if (CollectionUtils.isEmpty(dataFields)) {
            return Collections.emptyList();
        }
        ArrayList<DataFieldSettingDTO> list = new ArrayList<DataFieldSettingDTO>(dataFields.size());
        HashMap<String, String> entityInfo = new HashMap<String, String>(10);
        HashMap<String, String> tableNameMap = new HashMap<String, String>(dataFields.size());
        for (DesignDataField designDataField : dataFields) {
            DataFieldSettingDTO dataFieldSettingDTO = new DataFieldSettingDTO();
            if (tableNameMap.containsKey(designDataField.getDataTableKey())) {
                dataFieldSettingDTO.setTableName((String)tableNameMap.get(designDataField.getDataTableKey()));
            } else {
                DesignDataTable dataTable = designDataSchemeService.getDataTable(designDataField.getDataTableKey());
                dataFieldSettingDTO.setTableName(dataTable.getTitle());
                tableNameMap.put(dataTable.getKey(), dataTable.getTitle());
            }
            FieldBeanUtils.copyFieldToSetting(designDataField, dataFieldSettingDTO);
            if (StringUtils.hasLength(designDataField.getRefDataEntityKey()) && DataFieldKind.PUBLIC_FIELD_DIM.getValue() != designDataField.getDataFieldKind().getValue()) {
                dataFieldSettingDTO.setRefDataEntityKey(designDataField.getRefDataEntityKey());
                if (!entityInfo.containsKey(designDataField.getRefDataEntityKey())) {
                    if (periodEngineService.getPeriodAdapter().isPeriodEntity(designDataField.getRefDataEntityKey())) {
                        IPeriodEntity periodEntity = periodEngineService.getPeriodAdapter().getPeriodEntity(designDataField.getRefDataEntityKey());
                        entityInfo.put(designDataField.getRefDataEntityKey(), periodEntity.getTitle());
                    } else {
                        IEntityDefine queryEntity = entityMetaService.queryEntity(designDataField.getRefDataEntityKey());
                        entityInfo.put(designDataField.getRefDataEntityKey(), queryEntity.getTitle());
                    }
                }
                dataFieldSettingDTO.setRefDataEntityTitle((String)entityInfo.get(designDataField.getRefDataEntityKey()));
            }
            list.add(dataFieldSettingDTO);
        }
        return list;
    }

    private static void copyFieldToSetting(DesignDataField designDataField, DataFieldSettingDTO dataFieldSettingDTO) {
        List validationRules;
        dataFieldSettingDTO.setKey(designDataField.getKey());
        dataFieldSettingDTO.setTitle(designDataField.getTitle());
        dataFieldSettingDTO.setOrder(designDataField.getOrder());
        dataFieldSettingDTO.setLevel(designDataField.getLevel());
        dataFieldSettingDTO.setDesc(designDataField.getDesc());
        Instant updateTime = designDataField.getUpdateTime();
        if (updateTime != null) {
            dataFieldSettingDTO.setUpdateTime(updateTime.toString());
        }
        dataFieldSettingDTO.setAlias(dataFieldSettingDTO.getAlias());
        dataFieldSettingDTO.setDataSchemeKey(designDataField.getDataSchemeKey());
        dataFieldSettingDTO.setDataTableKey(designDataField.getDataTableKey());
        dataFieldSettingDTO.setDataFieldKind(designDataField.getDataFieldKind().getValue());
        dataFieldSettingDTO.setVersion(designDataField.getVersion());
        dataFieldSettingDTO.setDefaultValue(designDataField.getDefaultValue());
        dataFieldSettingDTO.setNullable(designDataField.getNullable());
        DimensionWrapper wrapper = DimensionWrapper.build(designDataField.getMeasureUnit());
        dataFieldSettingDTO.setDimension(wrapper.getDimension());
        dataFieldSettingDTO.setMeasureUnit(wrapper.getMeasureUnit());
        dataFieldSettingDTO.setAllowMultipleSelect(designDataField.getAllowMultipleSelect());
        dataFieldSettingDTO.setSecretLevel(designDataField.getSecretLevel());
        dataFieldSettingDTO.setUseAuthority(designDataField.getUseAuthority());
        if (designDataField.getDataFieldApplyType() != null) {
            dataFieldSettingDTO.setApplyType(designDataField.getDataFieldApplyType().getValue());
        }
        dataFieldSettingDTO.setFieldName(designDataField.getTitle());
        if (designDataField.getFormatProperties() != null) {
            dataFieldSettingDTO.setFieldFormat(FormatPropertiesUtils.convert(designDataField.getFormatProperties()));
        }
        if (!CollectionUtils.isEmpty(validationRules = designDataField.getValidationRules())) {
            dataFieldSettingDTO.setValidationRules(validationRules.stream().map(ValidationRuleVO::new).collect(Collectors.toList()));
        }
        dataFieldSettingDTO.setAllowUndefinedCode(designDataField.getAllowUndefinedCode());
        dataFieldSettingDTO.setGenerateVersion(designDataField.getGenerateVersion());
        dataFieldSettingDTO.setChangeWithPeriod(designDataField.getChangeWithPeriod());
        dataFieldSettingDTO.setAllowTreeSum(designDataField.getAllowTreeSum());
        dataFieldSettingDTO.setCode(designDataField.getCode());
        if (designDataField.getDataFieldType() != null) {
            dataFieldSettingDTO.setDataFieldType(designDataField.getDataFieldType().getValue());
        }
        dataFieldSettingDTO.setPrecision(designDataField.getPrecision());
        dataFieldSettingDTO.setDecimal(designDataField.getDecimal());
        if (designDataField.getDataFieldGatherType() != null) {
            dataFieldSettingDTO.setDataFieldGatherType(designDataField.getDataFieldGatherType().getValue());
        }
        dataFieldSettingDTO.setRefDataEntityKey(designDataField.getRefDataEntityKey());
        dataFieldSettingDTO.setDataMaskCode(designDataField.getDataMaskCode());
    }

    public static void toDefine(DataFieldSettingDTO dataFieldSettingDTO, DesignDataField designDataField) {
        designDataField.setAlias(dataFieldSettingDTO.getAlias());
        designDataField.setDataSchemeKey(dataFieldSettingDTO.getDataSchemeKey());
        designDataField.setDataTableKey(dataFieldSettingDTO.getDataTableKey());
        designDataField.setDataFieldApplyType(DataFieldApplyType.valueOf((int)dataFieldSettingDTO.getApplyType()));
        designDataField.setDataFieldKind(DataFieldKind.valueOf((int)dataFieldSettingDTO.getDataFieldKind()));
        designDataField.setVersion(dataFieldSettingDTO.getVersion());
        designDataField.setLevel(dataFieldSettingDTO.getLevel());
        designDataField.setDefaultValue(dataFieldSettingDTO.getDefaultValue());
        designDataField.setPrecision(dataFieldSettingDTO.getPrecision());
        designDataField.setDataFieldType(DataFieldType.valueOf((int)dataFieldSettingDTO.getDataFieldType()));
        designDataField.setDecimal(dataFieldSettingDTO.getDecimal());
        designDataField.setRefDataEntityKey(dataFieldSettingDTO.getRefDataEntityKey());
        designDataField.setRefDataFieldKey(dataFieldSettingDTO.getRefDataFieldKey());
        List<ValidationRuleVO> ruleVOS = dataFieldSettingDTO.getValidationRules();
        if (!CollectionUtils.isEmpty(ruleVOS)) {
            designDataField.setValidationRules(ruleVOS.stream().map(ValidationRuleVO::toValidationRuleDTO).collect(Collectors.toList()));
        }
        designDataField.setNullable(dataFieldSettingDTO.getNullable());
        designDataField.setMeasureUnit(DimensionWrapper.toString(dataFieldSettingDTO.getDimension(), dataFieldSettingDTO.getMeasureUnit()));
        designDataField.setDataFieldGatherType(DataFieldGatherType.valueOf((int)dataFieldSettingDTO.getDataFieldGatherType()));
        designDataField.setAllowMultipleSelect(dataFieldSettingDTO.getAllowMultipleSelect());
        if (dataFieldSettingDTO.getFieldFormat() != null) {
            FormatDTO formatVO = dataFieldSettingDTO.getFieldFormat();
            FormatPropertiesBuilder builder = new FormatPropertiesBuilder();
            FormatProperties formatProperties = builder.clean().setFormatType(formatVO.getFormatType().intValue()).setCurrency(formatVO.getCurrency()).setFieldType(DataFieldType.valueOf((int)dataFieldSettingDTO.getDataFieldType())).setDisplayDigits(formatVO.getDisplayDigits().intValue()).setFixMode(formatVO.getFixMode()).setThousands(formatVO.getThousands().booleanValue()).setNegativeStyle(formatVO.getNegativeStyle()).build();
            designDataField.setFormatProperties(formatProperties);
        }
        designDataField.setSecretLevel(dataFieldSettingDTO.getSecretLevel());
        designDataField.setUseAuthority(dataFieldSettingDTO.getUseAuthority());
        designDataField.setAllowUndefinedCode(dataFieldSettingDTO.getAllowUndefinedCode());
        designDataField.setChangeWithPeriod(dataFieldSettingDTO.getChangeWithPeriod());
        designDataField.setGenerateVersion(dataFieldSettingDTO.getGenerateVersion());
        designDataField.setAllowTreeSum(dataFieldSettingDTO.getAllowTreeSum());
        designDataField.setCode(dataFieldSettingDTO.getCode());
        designDataField.setTitle(dataFieldSettingDTO.getTitle());
        designDataField.setKey(dataFieldSettingDTO.getKey());
        designDataField.setOrder(dataFieldSettingDTO.getOrder());
        designDataField.setDesc(dataFieldSettingDTO.getDesc());
        designDataField.setDataMaskCode(dataFieldSettingDTO.getDataMaskCode());
    }
}

