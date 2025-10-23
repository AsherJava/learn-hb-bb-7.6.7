/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.api.resource.dto;

import com.jiuqi.nr.task.api.resource.domain.ResourceDO;

public class ResourceDTO
extends ResourceDO {
    private String formSchemeKey;
    private String categoryKey;
    private String currentResource;

    public ResourceDTO() {
        super(null);
    }

    public ResourceDTO(String type) {
        super(type);
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getCategoryKey() {
        return this.categoryKey;
    }

    public void setCategoryKey(String categoryKey) {
        this.categoryKey = categoryKey;
    }

    public String getCurrentResource() {
        return this.currentResource;
    }

    public void setCurrentResource(String currentResource) {
        this.currentResource = currentResource;
    }

    public static ResourceDTO getInstance(ResourceDO resourceDO) {
        ResourceDTO dto = new ResourceDTO();
        dto.setFields(resourceDO.getFields());
        dto.setValues(resourceDO.getValues());
        return dto;
    }
}

