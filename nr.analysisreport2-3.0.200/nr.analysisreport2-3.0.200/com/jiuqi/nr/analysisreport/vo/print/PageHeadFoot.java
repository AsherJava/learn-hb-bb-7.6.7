/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonAlias
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 */
package com.jiuqi.nr.analysisreport.vo.print;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PageHeadFoot {
    private String data;
    private String oddData;
    private String evenData;
    private String scope;
    @JsonAlias(value={"showDiffHead", "showDiffFoot"})
    private Boolean showDiff;

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOddData() {
        return this.oddData;
    }

    public void setOddData(String oddData) {
        this.oddData = oddData;
    }

    public String getEvenData() {
        return this.evenData;
    }

    public void setEvenData(String evenData) {
        this.evenData = evenData;
    }

    public String getScope() {
        return this.scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Boolean getShowDiff() {
        return this.showDiff;
    }

    public void setShowDiff(Boolean showDiff) {
        this.showDiff = showDiff;
    }

    @JsonCreator
    public static PageHeadFoot fromJson(String value) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return (PageHeadFoot)objectMapper.readValue(value, (TypeReference)new TypeReference<PageHeadFoot>(){});
    }
}

