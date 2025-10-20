/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.dto.fetch.init;

public class FetchRegionDTO {
    private String id;
    private String regionCode;
    private String regionTitle;

    public FetchRegionDTO() {
    }

    public FetchRegionDTO(String id, String regionCode, String regionTitle) {
        this.id = id;
        this.regionCode = regionCode;
        this.regionTitle = regionTitle;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegionCode() {
        return this.regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getRegionTitle() {
        return this.regionTitle;
    }

    public void setRegionTitle(String regionTitle) {
        this.regionTitle = regionTitle;
    }
}

