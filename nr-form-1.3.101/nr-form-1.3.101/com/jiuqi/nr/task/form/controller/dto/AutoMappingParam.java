/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.controller.dto;

import com.jiuqi.nr.task.form.controller.dto.AutoMappingDTO;
import java.util.List;

public class AutoMappingParam {
    private String formKey;
    private List<AutoMappingDTO> simpleMapping;
    private List<AutoMappingDTO> floatMapping;

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public List<AutoMappingDTO> getSimpleMapping() {
        return this.simpleMapping;
    }

    public void setSimpleMapping(List<AutoMappingDTO> simpleMapping) {
        this.simpleMapping = simpleMapping;
    }

    public List<AutoMappingDTO> getFloatMapping() {
        return this.floatMapping;
    }

    public void setFloatMapping(List<AutoMappingDTO> floatMapping) {
        this.floatMapping = floatMapping;
    }
}

