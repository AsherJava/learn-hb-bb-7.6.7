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
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.ValidationRule
 *  com.jiuqi.nr.datascheme.api.service.FormatPropertiesBuilder
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.CompareType
 *  com.jiuqi.nr.datascheme.internal.convert.Convert
 *  com.jiuqi.nr.datascheme.internal.dto.ValidationRuleDTO
 *  com.jiuqi.nr.datascheme.web.base.EntityUtil
 *  com.jiuqi.nr.datascheme.web.facade.FormatVO
 *  com.jiuqi.nr.datascheme.web.facade.ValidationRuleVO
 */
package com.jiuqi.nr.query.datascheme.web.base;

import com.jiuqi.np.definition.internal.format.FixMode;
import com.jiuqi.np.definition.internal.format.NegativeStyle;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.np.definition.internal.parser.NumberFormatParser;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.ValidationRule;
import com.jiuqi.nr.datascheme.api.service.FormatPropertiesBuilder;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.CompareType;
import com.jiuqi.nr.datascheme.internal.convert.Convert;
import com.jiuqi.nr.datascheme.internal.dto.ValidationRuleDTO;
import com.jiuqi.nr.datascheme.web.base.EntityUtil;
import com.jiuqi.nr.datascheme.web.facade.FormatVO;
import com.jiuqi.nr.datascheme.web.facade.ValidationRuleVO;
import com.jiuqi.nr.query.datascheme.web.param.QueryBatUpDataFieldVO;
import com.jiuqi.nr.query.datascheme.web.param.QueryDataFieldVO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class QueryEntityUtil
extends EntityUtil {
    public static QueryDataFieldVO fieldEntity2VO(DataField source) {
        QueryDataFieldVO queryDataFieldVO = new QueryDataFieldVO();
        QueryEntityUtil.fieldEntity2VO(queryDataFieldVO, source);
        return queryDataFieldVO;
    }

    public static void fieldEntity2VO(QueryDataFieldVO target, DataField source) {
        NegativeStyle negativeStyle;
        FixMode fixMode;
        Integer displayDigits;
        BeanUtils.copyProperties(source, (Object)target);
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
        formatVO.setFormatType(parse.getFormatType().intValue());
        formatVO.setCurrency(parse.getCurrency());
        formatVO.setThousands(parse.isThousands().booleanValue());
        if (formatProperties != null) {
            formatVO.setPattern(formatProperties.getPattern());
        }
        if ((displayDigits = parse.getDisplayDigits()) != null) {
            formatVO.setDisplayDigits(displayDigits.intValue());
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
        target.setRefParameter(source.getRefParameter());
    }

    public static DesignDataField fieldVO2Entity(IDesignDataSchemeService service, QueryDataFieldVO source) {
        return QueryEntityUtil.fieldVO2Entity(source, null, service, null);
    }

    public static DesignDataField fieldVO2Entity(QueryDataFieldVO source, DesignDataTable dataTable, IDesignDataSchemeService service, FormatPropertiesBuilder formatPropertiesBuilder) {
        FormatVO formatVO;
        List validationRules;
        DesignDataField dataField;
        DesignDataField target = service.initDataField();
        BeanUtils.copyProperties((Object)source, target);
        if (source.getDimension() != null) {
            target.setMeasureUnit(source.getDimension() == 0 ? QueryEntityUtil.solutionMeasureUnit(target.getMeasureUnit()) : "NotDimession");
        }
        target.setDataFieldApplyType(source.getApplyType());
        String key = source.getKey();
        if (key != null && (dataField = service.getDataField(key)) != null) {
            Convert.update((DesignDataField)dataField, (DesignDataField)target);
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
        target.setRefParameter(source.getRefParameter());
        return target;
    }

    private static String solutionMeasureUnit(String measureUnit) {
        if (measureUnit == null) {
            return "9493b4eb-6516-48a8-a878-25a63a23e63a;-";
        }
        return "9493b4eb-6516-48a8-a878-25a63a23e63a;" + measureUnit;
    }

    public static void updateDataField(DesignDataField field, QueryBatUpDataFieldVO vo) {
        FormatVO formatVO;
        List validationRules;
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
            field.setMeasureUnit(vo.getDimension() == 0 ? QueryEntityUtil.solutionMeasureUnit(vo.getMeasureUnit()) : "NotDimession");
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
        if (null != vo.getRefParameter()) {
            field.setRefParameter(vo.getRefParameter());
        }
    }
}

