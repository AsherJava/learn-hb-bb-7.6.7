/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSchemeDTO
 */
package com.jiuqi.nr.midstore2.batch.web.vo;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSchemeDTO;
import java.util.List;
import java.util.Map;

public class MidstoreSchemeVO {
    private String key;
    private String code;
    private String title;
    private String exchangeMode;
    private boolean hasDimension;
    private Map<String, DimensionValue> dimSetMap;
    private String orgName;
    private List<String> orgs;

    public MidstoreSchemeVO(MidstoreSchemeDTO dto) {
        this.key = dto.getKey();
        this.code = dto.getCode();
        this.title = dto.getTitle();
        this.exchangeMode = String.valueOf(dto.getExchangeMode().getValue());
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExchangeMode() {
        return this.exchangeMode;
    }

    public void setExchangeMode(String exchangeMode) {
        this.exchangeMode = exchangeMode;
    }

    public boolean isHasDimension() {
        return this.hasDimension;
    }

    public void setHasDimension(boolean hasDimension) {
        this.hasDimension = hasDimension;
    }

    public Map<String, DimensionValue> getDimSetMap() {
        return this.dimSetMap;
    }

    public void setDimSetMap(Map<String, DimensionValue> dimSetMap) {
        this.dimSetMap = dimSetMap;
    }

    public String getOrgName() {
        return this.orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public List<String> getOrgs() {
        return this.orgs;
    }

    public void setOrgs(List<String> orgs) {
        this.orgs = orgs;
    }
}

