/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.api.resource.dto;

import com.jiuqi.nr.task.api.common.Constants;
import com.jiuqi.nr.task.api.resource.domain.ResourceCategoryDO;

public class ResourceCategoryDTO
extends ResourceCategoryDO {
    private String locationKey;
    private Constants.QueryType queryType;
    private String formSchemeKey;

    public ResourceCategoryDTO() {
        super(null);
    }

    public ResourceCategoryDTO(String type) {
        super(type);
    }

    public String getLocationKey() {
        return this.locationKey;
    }

    public void setLocationKey(String locationKey) {
        this.locationKey = locationKey;
    }

    public Constants.QueryType getQueryType() {
        return this.queryType;
    }

    public void setQueryType(Constants.QueryType queryType) {
        this.queryType = queryType;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public static ResourceCategoryDTO getInstance(ResourceCategoryDO categoryDO) {
        ResourceCategoryDTO dto = new ResourceCategoryDTO(categoryDO.getCategoryType());
        dto.setKey(categoryDO.getKey());
        dto.setTitle(categoryDO.getTitle());
        dto.setParent(categoryDO.getParent());
        dto.setIcon(categoryDO.getIcon());
        return dto;
    }
}

