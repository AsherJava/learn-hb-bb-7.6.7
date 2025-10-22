/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.nr.period.util.JacksonUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.definition.option.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.option.AuditSchemeOptionService;
import com.jiuqi.nr.definition.option.dto.AuditSchemeDTO;
import com.jiuqi.nr.period.util.JacksonUtils;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditSchemeOptionServiceImpl
implements AuditSchemeOptionService {
    @Autowired
    private ITaskOptionController taskOptionController;

    @Override
    public List<AuditSchemeDTO> getAllItemsInAuditScheme(String taskKey) {
        String value = this.taskOptionController.getValue(taskKey, "AUDIT_SCHEME");
        if (Objects.isNull(value) || StringUtils.isBlank((CharSequence)value)) {
            return Collections.emptyList();
        }
        return (List)JacksonUtils.jsonToObject((String)value, (TypeReference)new TypeReference<List<AuditSchemeDTO>>(){});
    }

    @Override
    public List<AuditSchemeDTO> getAllAuditTypesInScheme(String taskKey) {
        List<AuditSchemeDTO> allItemsInAuditScheme = this.getAllItemsInAuditScheme(taskKey);
        return allItemsInAuditScheme.stream().filter(AuditSchemeDTO::isAuditType).collect(Collectors.toList());
    }

    @Override
    public AuditSchemeDTO getAuditTypeInScheme(String taskKey, @NotNull String code) {
        for (AuditSchemeDTO dto : this.getAllItemsInAuditScheme(taskKey)) {
            if (!StringUtils.equals((CharSequence)code, (CharSequence)dto.getCode())) continue;
            return dto;
        }
        return null;
    }

    @Override
    public AuditSchemeDTO getConditionInScheme(String taskKey) {
        for (AuditSchemeDTO dto : this.getAllItemsInAuditScheme(taskKey)) {
            if (!dto.isCondition()) continue;
            return dto;
        }
        return null;
    }

    @Override
    public String getAuditTypeValueInScheme(String taskKey, @NotNull String code) {
        AuditSchemeDTO scheme = this.getAuditTypeInScheme(taskKey, code);
        if (Objects.nonNull(scheme)) {
            return scheme.getValue();
        }
        return null;
    }

    @Override
    public String getConditionValueInScheme(String taskKey) {
        AuditSchemeDTO scheme = this.getConditionInScheme(taskKey);
        if (Objects.nonNull(scheme)) {
            return scheme.getValue();
        }
        return null;
    }
}

