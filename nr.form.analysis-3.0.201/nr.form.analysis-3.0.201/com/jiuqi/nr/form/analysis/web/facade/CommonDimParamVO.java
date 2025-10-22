/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.definition.common.DimensionType
 */
package com.jiuqi.nr.form.analysis.web.facade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.definition.common.DimensionType;
import com.jiuqi.nr.form.analysis.web.facade.CommonDimAttrParamVO;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CommonDimParamVO
implements Serializable {
    private static final long serialVersionUID = 6086370502242060231L;
    private String key;
    private String code;
    private DimensionType type;
    private CommonDimAttrParamVO config;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public DimensionType getType() {
        return this.type;
    }

    public void setType(DimensionType type) {
        this.type = type;
    }

    public CommonDimAttrParamVO getConfig() {
        return this.config;
    }

    public void setConfig(CommonDimAttrParamVO config) {
        this.config = config;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static long getSerialversionuid() {
        return 6086370502242060231L;
    }
}

