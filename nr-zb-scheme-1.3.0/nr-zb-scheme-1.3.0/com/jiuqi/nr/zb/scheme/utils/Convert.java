/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.utils;

import com.jiuqi.nr.zb.scheme.common.CompareType;
import com.jiuqi.nr.zb.scheme.core.ValidationRule;
import com.jiuqi.nr.zb.scheme.internal.dto.ValidationRuleDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class Convert {
    public static boolean isNullable(List<ValidationRule> validationRules) {
        if (validationRules == null || validationRules.isEmpty()) {
            return true;
        }
        for (ValidationRule validationRule : validationRules) {
            if (!CompareType.NOTNULL.equals(validationRule.getCompareType())) continue;
            return false;
        }
        return true;
    }

    public static List<ValidationRule> setNullable(Boolean nullable, List<ValidationRule> validationRules) {
        if (nullable != null && nullable.booleanValue()) {
            if (validationRules == null || validationRules.isEmpty()) {
                return validationRules;
            }
            validationRules.removeIf(next -> CompareType.NOTNULL.equals(next.getCompareType()));
        } else {
            if (validationRules == null) {
                validationRules = new ArrayList<ValidationRule>();
            }
            for (ValidationRule next2 : validationRules) {
                if (!CompareType.NOTNULL.equals(next2.getCompareType())) continue;
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

