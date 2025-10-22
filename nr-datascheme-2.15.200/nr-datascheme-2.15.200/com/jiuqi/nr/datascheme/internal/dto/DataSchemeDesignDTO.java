/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 */
package com.jiuqi.nr.datascheme.internal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.internal.dto.DataSchemeDTO;

@JsonIgnoreProperties(ignoreUnknown=true)
public class DataSchemeDesignDTO
extends DataSchemeDTO
implements DesignDataScheme {
    private static final long serialVersionUID = 1236658547381431103L;

    @Override
    public DataSchemeDesignDTO clone() {
        return (DataSchemeDesignDTO)super.clone();
    }

    public static DataSchemeDesignDTO valueOf(DataScheme o) {
        if (o == null) {
            return null;
        }
        DataSchemeDesignDTO dto = new DataSchemeDesignDTO();
        DataSchemeDesignDTO.copyProperties(o, dto);
        return dto;
    }

    @Override
    public String toString() {
        return "DataSchemeDesignDTO{key='" + this.key + '\'' + ", code='" + this.code + '\'' + ", title='" + this.title + '\'' + ", desc='" + this.desc + '\'' + ", dataGroupKey='" + this.dataGroupKey + '\'' + ", prefix='" + this.prefix + '\'' + ", auto=" + this.auto + ", version='" + this.version + '\'' + ", level='" + this.level + '\'' + ", order='" + this.order + '\'' + ", updateTime=" + this.updateTime + ", creator='" + this.creator + '\'' + ", type=" + this.type + ", bizCode='" + this.bizCode + '\'' + ", gatherDB=" + this.gatherDB + ", encryptScene='" + this.encryptScene + '\'' + '}';
    }
}

