/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataGroup
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.DataTableRel
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.DesignDataTableRel
 *  com.jiuqi.nr.datascheme.api.ValidationRule
 *  com.jiuqi.nr.datascheme.api.type.CompareType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nvwa.definition.common.UUIDUtils
 */
package com.jiuqi.nr.datascheme.internal.convert;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.DataTableRel;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.DesignDataTableRel;
import com.jiuqi.nr.datascheme.api.ValidationRule;
import com.jiuqi.nr.datascheme.api.type.CompareType;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.internal.dto.DataDimDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataDimDesignDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDesignDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataGroupDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataGroupDesignDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataSchemeDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataSchemeDesignDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataTableDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataTableDesignDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataTableRelDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataTableRelDesignDTO;
import com.jiuqi.nr.datascheme.internal.dto.ValidationRuleDTO;
import com.jiuqi.nr.datascheme.internal.entity.DataTableRelDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataDimDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataFieldDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataGroupDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataSchemeDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataTableDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataTableRelDO;
import com.jiuqi.nvwa.definition.common.UUIDUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class Convert {
    public static DesignDataSchemeDO iDs2Do(DesignDataScheme dataScheme) {
        return DesignDataSchemeDO.valueOf(dataScheme);
    }

    public static DesignDataGroupDO iDg2Do(DesignDataGroup dataGroup) {
        return DesignDataGroupDO.valueOf(dataGroup);
    }

    public static DesignDataTableDO iDt2Do(DesignDataTable dataTable) {
        return DesignDataTableDO.valueOf((DataTable)dataTable);
    }

    public static DesignDataFieldDO iDf2Do(DesignDataField designDataField) {
        return DesignDataFieldDO.valueOf((DataField)designDataField);
    }

    public static DesignDataDimDO iDm2Do(DesignDataDimension dim) {
        return DesignDataDimDO.valueOf(dim);
    }

    public static DataSchemeDTO iDs2Dto(DataScheme dataScheme) {
        return DataSchemeDTO.valueOf(dataScheme);
    }

    public static DataSchemeDesignDTO iDs2Dto(DesignDataScheme dataScheme) {
        return DataSchemeDesignDTO.valueOf((DataScheme)dataScheme);
    }

    public static DataGroupDTO iDg2Dto(DataGroup dataGroup) {
        return DataGroupDTO.valueOf(dataGroup);
    }

    public static DataGroupDesignDTO iDg2Dto(DesignDataGroup dataGroup) {
        return DataGroupDesignDTO.valueOf(dataGroup);
    }

    public static DataTableDTO iDt2Dto(DataTable dataTable) {
        return DataTableDTO.valueOf(dataTable);
    }

    public static DataTableDesignDTO iDt2Dto(DesignDataTable dataTable) {
        return DataTableDesignDTO.valueOf((DataTable)dataTable);
    }

    public static DataFieldDTO iDf2Dto(DataField dataField) {
        return DataFieldDTO.valueOf(dataField);
    }

    public static DataFieldDesignDTO iDf2Dto(DesignDataField dataField) {
        return DataFieldDesignDTO.valueOf((DataField)dataField);
    }

    public static <E extends DataField> List<DesignDataFieldDO> iDf2Dto(List<E> dataFields) {
        ArrayList<DesignDataFieldDO> list = new ArrayList<DesignDataFieldDO>();
        if (dataFields == null) {
            return list;
        }
        for (DataField dataField : dataFields) {
            list.add(DesignDataFieldDO.valueOf(dataField));
        }
        return list;
    }

    public static DataDimDTO iDm2Dto(DataDimension dimension) {
        return DataDimDTO.valueOf(dimension);
    }

    public static DataDimDesignDTO iDm2Dto(DesignDataDimension dimension) {
        return DataDimDesignDTO.valueOf((DataDimension)dimension);
    }

    public static DataTableRelDTO iDtr2Dto(DataTableRel dataTableRel) {
        return DataTableRelDTO.valueOf(dataTableRel);
    }

    public static DataTableRelDesignDTO iDtr2Dto(DesignDataTableRel dataTableRel) {
        return DataTableRelDesignDTO.valueOf((DataTableRel)dataTableRel);
    }

    public static DataTableRelDO iDtr2Do(DataTableRel dataTableRel) {
        return DataTableRelDO.valueOf(dataTableRel);
    }

    public static DesignDataTableRelDO iDtr2Do(DesignDataTableRel dataTableRel) {
        return DesignDataTableRelDO.valueOf((DataTableRel)dataTableRel);
    }

    public static DesignDataFieldDO dimFieldBuild(DesignDataTable table, String code, String title, String refFieldKey, String refEntityKey, DataFieldKind dataFieldKind, Integer precision) {
        if (precision == null) {
            precision = 400;
        }
        DesignDataFieldDO fieldDO = new DesignDataFieldDO();
        fieldDO.setCode(code);
        fieldDO.setKey(UUIDUtils.getKey());
        fieldDO.setPrecision(precision);
        fieldDO.setDataFieldType(DataFieldType.STRING);
        fieldDO.setTitle(title);
        fieldDO.setDataTableKey(table.getKey());
        fieldDO.setDataFieldKind(dataFieldKind);
        fieldDO.setDataSchemeKey(table.getDataSchemeKey());
        fieldDO.setOrder(OrderGenerator.newOrder());
        fieldDO.setRefDataEntityKey(refEntityKey);
        fieldDO.setRefDataFieldKey(refFieldKey);
        fieldDO.setNullable(false);
        fieldDO.setUseAuthority(false);
        return fieldDO;
    }

    public static DesignDataField update(DesignDataField old, DesignDataField newField) {
        newField.setDataSchemeKey(old.getDataSchemeKey());
        newField.setDataTableKey(old.getDataTableKey());
        newField.setAlias(old.getAlias());
        newField.setLevel(old.getLevel());
        newField.setVersion(old.getVersion());
        return newField;
    }

    public static boolean isNullable(List<ValidationRule> validationRules) {
        if (validationRules == null || validationRules.isEmpty()) {
            return true;
        }
        for (ValidationRule validationRule : validationRules) {
            if (!CompareType.NOTNULL.equals((Object)validationRule.getCompareType())) continue;
            return false;
        }
        return true;
    }

    public static List<ValidationRule> setNullable(Boolean nullable, List<ValidationRule> validationRules) {
        if (nullable != null && nullable.booleanValue()) {
            if (validationRules == null || validationRules.isEmpty()) {
                return validationRules;
            }
            validationRules.removeIf(next -> CompareType.NOTNULL.equals((Object)next.getCompareType()));
        } else {
            if (validationRules == null) {
                validationRules = new ArrayList<ValidationRule>();
            }
            for (ValidationRule next2 : validationRules) {
                if (!CompareType.NOTNULL.equals((Object)next2.getCompareType())) continue;
                return validationRules;
            }
            ValidationRuleDTO notNull = new ValidationRuleDTO();
            notNull.setCompareType(CompareType.NOTNULL);
            validationRules.add(notNull);
        }
        return validationRules;
    }

    public static List<ValidationRule> setValidationRules(List<ValidationRule> oldValidationRules, List<ValidationRule> setValidationRules) {
        if (oldValidationRules == null) {
            if (setValidationRules != null) {
                // empty if block
            }
            return setValidationRules;
        }
        Optional<ValidationRule> notNull = oldValidationRules.stream().filter(validationRule -> validationRule.getCompareType() == CompareType.NOTNULL).findFirst();
        if (notNull.isPresent()) {
            Optional<ValidationRule> notNullvalue;
            if (setValidationRules == null) {
                setValidationRules = new ArrayList<ValidationRule>();
            }
            if (!(notNullvalue = setValidationRules.stream().filter(validationRule -> validationRule.getCompareType() == CompareType.NOTNULL).findFirst()).isPresent()) {
                setValidationRules.add(notNull.get());
            }
            return setValidationRules;
        }
        if (setValidationRules == null) {
            setValidationRules = new ArrayList<ValidationRule>();
        }
        return setValidationRules;
    }

    public static List<ValidationRule> getValidationRules(List<ValidationRule> validationRules) {
        if (CollectionUtils.isEmpty(validationRules)) {
            return Collections.emptyList();
        }
        return validationRules.stream().filter(Objects::nonNull).filter(validationRule -> validationRule.getCompareType() != CompareType.NOTNULL).collect(Collectors.toList());
    }
}

