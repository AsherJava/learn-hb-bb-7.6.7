/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.bde.common.dto.fetch.init;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.bde.common.dto.fetch.init.FetchRegionDTO;
import com.jiuqi.common.base.util.CollectionUtils;
import java.util.List;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class FetchFormDTO {
    private String id;
    private String formCode;
    private String formTitle;
    private List<FetchRegionDTO> fetchRegions;

    public FetchFormDTO() {
    }

    public FetchFormDTO(String id, String formCode, String formTitle) {
        this.id = id;
        this.formCode = formCode;
        this.formTitle = formTitle;
    }

    public FetchFormDTO(String id, String formCode, String formTitle, List<FetchRegionDTO> fetchRegions) {
        this.id = id;
        this.formCode = formCode;
        this.formTitle = formTitle;
        this.fetchRegions = fetchRegions;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getFormTitle() {
        return this.formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }

    public List<FetchRegionDTO> getFetchRegions() {
        return this.fetchRegions;
    }

    public void addFetchRegion(FetchRegionDTO fetchRegion) {
        if (this.fetchRegions == null) {
            this.fetchRegions = CollectionUtils.newArrayList();
        }
        this.fetchRegions.add(fetchRegion);
    }
}

