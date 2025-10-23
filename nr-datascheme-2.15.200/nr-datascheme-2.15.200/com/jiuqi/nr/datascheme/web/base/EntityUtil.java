/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.format.FixMode
 *  com.jiuqi.np.definition.internal.format.NegativeStyle
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  com.jiuqi.np.definition.internal.parser.NumberFormatParser
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.ValidationRule
 *  com.jiuqi.nr.datascheme.api.service.FormatPropertiesBuilder
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.CompareType
 *  com.jiuqi.nr.datascheme.api.type.DataGroupKind
 *  com.jiuqi.nr.datascheme.api.type.DataSchemeType
 *  com.jiuqi.nvwa.definition.common.UUIDUtils
 */
package com.jiuqi.nr.datascheme.web.base;

import com.jiuqi.np.definition.internal.format.FixMode;
import com.jiuqi.np.definition.internal.format.NegativeStyle;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.np.definition.internal.parser.NumberFormatParser;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.ValidationRule;
import com.jiuqi.nr.datascheme.api.service.FormatPropertiesBuilder;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.CompareType;
import com.jiuqi.nr.datascheme.api.type.DataGroupKind;
import com.jiuqi.nr.datascheme.api.type.DataSchemeType;
import com.jiuqi.nr.datascheme.common.DataSchemeBeanUtils;
import com.jiuqi.nr.datascheme.internal.convert.Convert;
import com.jiuqi.nr.datascheme.internal.dto.ValidationRuleDTO;
import com.jiuqi.nr.datascheme.web.facade.BaseDataSchemeVO;
import com.jiuqi.nr.datascheme.web.facade.BatUpDataFieldVO;
import com.jiuqi.nr.datascheme.web.facade.DataFieldVO;
import com.jiuqi.nr.datascheme.web.facade.DataGroupVO;
import com.jiuqi.nr.datascheme.web.facade.DataTableVO;
import com.jiuqi.nr.datascheme.web.facade.FormatVO;
import com.jiuqi.nr.datascheme.web.facade.ValidationRuleVO;
import com.jiuqi.nvwa.definition.common.UUIDUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class EntityUtil {
    public static <T extends BaseDataSchemeVO> T schemeEntity2VO(T target, DesignDataScheme source) {
        target.setKey(source.getKey());
        target.setCode(source.getCode());
        target.setTitle(source.getTitle());
        target.setAutoGeneration(source.getAuto());
        target.setDesc(source.getDesc());
        target.setPrefix(source.getPrefix());
        target.setDataGroupKey(source.getDataGroupKey());
        target.setCreator(source.getCreator());
        target.setType(source.getType());
        target.setReadonly(!DataSchemeBeanUtils.getDataSchemeAuthService().canWriteScheme(target.getKey()));
        target.setLevel(source.getLevel());
        target.setEnableGatherDB(source.getGatherDB());
        target.setEncryptScene(source.getEncryptScene());
        target.setZbSchemeKey(source.getZbSchemeKey());
        target.setZbSchemeVersion(source.getZbSchemeVersion());
        if (StringUtils.hasLength(source.getCalibre())) {
            target.setCalibre(source.getCalibre());
        }
        return target;
    }

    public static DesignDataScheme schemeVO2Entity(IDesignDataSchemeService service, BaseDataSchemeVO source, DesignDataScheme old) {
        DesignDataScheme target = service.initDataScheme();
        if (Objects.nonNull(source.getKey())) {
            target.setKey(source.getKey());
        }
        target.setCode(source.getCode());
        target.setTitle(source.getTitle());
        target.setAuto(Boolean.valueOf(false));
        target.setDesc(source.getDesc());
        target.setPrefix(source.getPrefix());
        target.setGatherDB(Boolean.valueOf(source.isEnableGatherDB()));
        if (!StringUtils.hasLength(source.getDataGroupKey())) {
            target.setDataGroupKey("00000000-0000-0000-0000-000000000000");
        } else {
            target.setDataGroupKey(source.getDataGroupKey());
        }
        if (old != null) {
            target.setOrder(old.getOrder());
            target.setLevel(old.getLevel());
            target.setVersion(old.getVersion());
        }
        target.setType(null == source.getType() ? DataSchemeType.NR : source.getType());
        target.setEncryptScene(source.getEncryptScene());
        if (source.getZbSchemeKey() != null && source.getZbSchemeVersion() != null) {
            target.setZbSchemeKey(source.getZbSchemeKey());
            target.setZbSchemeVersion(source.getZbSchemeVersion());
        } else {
            target.setZbSchemeKey(null);
            target.setZbSchemeVersion(null);
        }
        String calibre = source.getCalibre();
        if (StringUtils.hasLength(calibre)) {
            target.setCalibre(calibre);
        }
        return target;
    }

    public static DataGroupVO groupEntity2VO(DesignDataGroup source) {
        DataGroupVO target = new DataGroupVO();
        BeanUtils.copyProperties(source, target);
        if (DataGroupKind.TABLE_GROUP.equals((Object)source.getDataGroupKind())) {
            target.setSchemeGroup(true);
        }
        if (target.getParentKey() == null) {
            target.setParentKey(target.getDataSchemeKey());
        }
        return target;
    }

    public static DesignDataGroup groupVO2Entity(IDesignDataSchemeService service, DataGroupVO source, DesignDataGroup old) {
        DesignDataGroup target = service.initDataGroup();
        BeanUtils.copyProperties(source, target);
        if (source.isSchemeGroup()) {
            target.setDataGroupKind(DataGroupKind.SCHEME_GROUP);
        } else {
            target.setDataGroupKind(DataGroupKind.TABLE_GROUP);
        }
        if (!StringUtils.hasLength(source.getParentKey())) {
            target.setParentKey("00000000-0000-0000-0000-000000000000");
        }
        if (old != null) {
            target.setLevel(old.getLevel());
            target.setVersion(old.getVersion());
            target.setOrder(old.getOrder());
        }
        return target;
    }

    public static DataTableVO tableEntity2VO(DesignDataTable source) {
        DataTableVO target = new DataTableVO();
        BeanUtils.copyProperties(source, target);
        if (target.getDataGroupKey() == null) {
            target.setDataGroupKey(target.getDataSchemeKey());
        }
        return target;
    }

    public static DesignDataTable tableVO2Entity(IDesignDataSchemeService service, DataTableVO source, DesignDataTable dataTable) {
        DesignDataTable target = service.initDataTable();
        if (!StringUtils.hasLength(source.getKey())) {
            source.setKey(UUIDUtils.getKey());
        }
        BeanUtils.copyProperties(source, target);
        if (dataTable != null) {
            target.setVersion(dataTable.getVersion());
            target.setLevel(dataTable.getLevel());
            target.setBizKeys(dataTable.getBizKeys());
            target.setOrder(dataTable.getOrder());
        }
        return target;
    }

    public static DataFieldVO fieldEntity2VO(DataField source) {
        DataFieldVO dataFieldVO = new DataFieldVO();
        EntityUtil.fieldEntity2VO(dataFieldVO, source);
        return dataFieldVO;
    }

    public static void fieldEntity2VO(DataFieldVO target, DataField source) {
        NegativeStyle negativeStyle;
        FixMode fixMode;
        Integer displayDigits;
        BeanUtils.copyProperties(source, target);
        String measureUnit = target.getMeasureUnit();
        if (StringUtils.hasLength(measureUnit)) {
            target.setMeasureUnit(measureUnit.replace("9493b4eb-6516-48a8-a878-25a63a23e63a;", ""));
        }
        target.setApplyType(source.getDataFieldApplyType());
        List validationRules = source.getValidationRules();
        if (!CollectionUtils.isEmpty(validationRules)) {
            ArrayList<ValidationRuleVO> list = new ArrayList<ValidationRuleVO>(validationRules.size());
            target.setValidationRules(list);
            for (ValidationRule validationRule : validationRules) {
                if (validationRule.getCompareType() == null || validationRule.getCompareType() == CompareType.NOTNULL) continue;
                ValidationRuleVO validationRuleVO = new ValidationRuleVO(validationRule);
                list.add(validationRuleVO);
            }
        }
        FormatProperties formatProperties = source.getFormatProperties();
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
        target.setFormatVO(formatVO);
        target.setEncrypted(source.getEncrypted());
        target.setZbSchemeVersion(source.getZbSchemeVersion());
    }

    public static DesignDataField fieldVO2Entity(IDesignDataSchemeService service, DataFieldVO source) {
        return EntityUtil.fieldVO2Entity(source, null, service, null);
    }

    public static DesignDataField fieldVO2Entity(DataFieldVO source, DesignDataTable dataTable, IDesignDataSchemeService service, FormatPropertiesBuilder formatPropertiesBuilder) {
        FormatVO formatVO;
        List<ValidationRuleVO> validationRules;
        DesignDataField dataField;
        DesignDataField target = service.initDataField();
        BeanUtils.copyProperties(source, target);
        if (source.getDimension() != null) {
            target.setMeasureUnit(source.getDimension() == 0 ? EntityUtil.solutionMeasureUnit(target.getMeasureUnit()) : "NotDimession");
        }
        target.setDataFieldApplyType(source.getApplyType());
        String key = source.getKey();
        if (key != null && (dataField = service.getDataField(key)) != null) {
            Convert.update(dataField, target);
        }
        if (!CollectionUtils.isEmpty(validationRules = source.getValidationRules())) {
            ArrayList<ValidationRuleDTO> rules = new ArrayList<ValidationRuleDTO>(validationRules.size());
            for (ValidationRuleVO validationRule : validationRules) {
                ValidationRuleDTO dto = validationRule.toValidationRuleDTO();
                if (dto == null) continue;
                rules.add(dto);
            }
            target.setValidationRules(rules);
        }
        if ((formatVO = source.getFormatVO()) != null && formatVO.getFormatType() != 0) {
            if (formatPropertiesBuilder == null) {
                formatPropertiesBuilder = new FormatPropertiesBuilder();
            }
            FormatProperties formatProperties = formatPropertiesBuilder.clean().setFormatType(formatVO.getFormatType()).setCurrency(formatVO.getCurrency()).setFieldType(target.getDataFieldType()).setDisplayDigits(formatVO.getDisplayDigits()).setFixMode(formatVO.getFixMode()).setThousands(formatVO.isThousands()).setNegativeStyle(formatVO.getNegativeStyle()).setPattern(formatVO.getPattern()).build();
            target.setFormatProperties(formatProperties);
        }
        target.setEncrypted(source.getEncrypted());
        return target;
    }

    private static String solutionMeasureUnit(String measureUnit) {
        if (measureUnit == null) {
            return "9493b4eb-6516-48a8-a878-25a63a23e63a;-";
        }
        return "9493b4eb-6516-48a8-a878-25a63a23e63a;" + measureUnit;
    }

    public static void updateDataField(DesignDataField field, BatUpDataFieldVO vo) {
        FormatVO formatVO;
        List<ValidationRuleVO> validationRules;
        if (StringUtils.hasText(vo.getDesc())) {
            field.setDesc(vo.getDesc());
        }
        if (null != vo.getDataFieldType()) {
            field.setDataFieldType(vo.getDataFieldType());
        }
        if (null != vo.getDecimal()) {
            field.setDecimal(vo.getDecimal());
        }
        if (null != vo.getPrecision()) {
            field.setPrecision(vo.getPrecision());
        }
        if (null != vo.getDefaultValue()) {
            field.setDefaultValue(vo.getDefaultValue());
        }
        if (null != vo.getNullable()) {
            field.setNullable(vo.getNullable());
        }
        if (null != vo.getRefDataEntityKey()) {
            field.setRefDataEntityKey(vo.getRefDataEntityKey());
            field.setRefDataFieldKey(null);
        }
        if (null != vo.getAllowMultipleSelect()) {
            field.setAllowMultipleSelect(vo.getAllowMultipleSelect());
        }
        if (null != vo.getAllowUndefinedCode()) {
            field.setAllowUndefinedCode(vo.getAllowUndefinedCode());
        }
        if (null != vo.getDimension()) {
            field.setMeasureUnit(vo.getDimension() == 0 ? EntityUtil.solutionMeasureUnit(vo.getMeasureUnit()) : "NotDimession");
        }
        if (null != vo.getMeasureUnit()) {
            field.setMeasureUnit(vo.getMeasureUnit());
        }
        if (null != vo.getDataFieldGatherType()) {
            field.setDataFieldGatherType(vo.getDataFieldGatherType());
        }
        if (null != vo.getUseAuthority()) {
            field.setUseAuthority(vo.getUseAuthority());
        }
        if (null != vo.getApplyType()) {
            field.setDataFieldApplyType(vo.getApplyType());
        }
        if (!CollectionUtils.isEmpty(validationRules = vo.getValidationRules())) {
            ArrayList<ValidationRuleDTO> rules = new ArrayList<ValidationRuleDTO>(validationRules.size());
            for (ValidationRuleVO validationRule : validationRules) {
                ValidationRuleDTO dto = validationRule.toValidationRuleDTO();
                if (dto == null) continue;
                rules.add(dto);
            }
            field.setValidationRules(rules);
        }
        if ((formatVO = vo.getFormatVO()) != null) {
            FormatPropertiesBuilder formatPropertiesBuilder = new FormatPropertiesBuilder();
            FormatProperties formatProperties = formatPropertiesBuilder.clean().setFormatType(formatVO.getFormatType()).setCurrency(formatVO.getCurrency()).setFieldType(vo.getDataFieldType()).setDisplayDigits(formatVO.getDisplayDigits()).setFixMode(formatVO.getFixMode()).setThousands(formatVO.isThousands()).setNegativeStyle(formatVO.getNegativeStyle()).setPattern(formatVO.getPattern()).build();
            field.setFormatProperties(formatProperties);
        }
        if (null != vo.getGenerateVersion()) {
            field.setGenerateVersion(vo.getGenerateVersion());
        }
        if (null != vo.getChangeWithPeriod()) {
            field.setChangeWithPeriod(vo.getChangeWithPeriod());
        }
        if (null != vo.getAllowTreeSum()) {
            field.setAllowTreeSum(vo.getAllowTreeSum());
        }
        if (null != vo.getVisible()) {
            field.setVisible(vo.getVisible());
        }
        if (null != vo.getEncrypted()) {
            field.setEncrypted(vo.getEncrypted());
        }
        if (null != vo.getFormula()) {
            field.setFormula(vo.getFormula());
        }
        if (null != vo.getFormulaDesc()) {
            field.setFormulaDesc(vo.getFormulaDesc());
        }
        field.setDataMaskCode(vo.getDataMaskCode());
    }
}

