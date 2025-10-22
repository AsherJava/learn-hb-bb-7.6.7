/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.datascheme.api.DataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 */
package com.jiuqi.nr.datascheme.internal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.internal.dto.DataGroupDTO;

@JsonIgnoreProperties(ignoreUnknown=true)
public class DataGroupDesignDTO
extends DataGroupDTO
implements DesignDataGroup {
    private static final long serialVersionUID = 6375890835775300235L;

    @Override
    public DataGroupDesignDTO clone() {
        return (DataGroupDesignDTO)super.clone();
    }

    @Override
    public String toString() {
        return "DataGroupDesignDTO{key='" + this.key + '\'' + ", dataSchemeKey='" + this.dataSchemeKey + '\'' + ", code='" + this.code + '\'' + ", title='" + this.title + '\'' + ", desc='" + this.desc + '\'' + ", dataGroupKind=" + this.dataGroupKind + ", parentKey='" + this.parentKey + '\'' + ", version='" + this.version + '\'' + ", level='" + this.level + '\'' + ", order='" + this.order + '\'' + ", updateTime=" + this.updateTime + '}';
    }

    public static DataGroupDesignDTO valueOf(DesignDataGroup dataGroup) {
        if (dataGroup == null) {
            return null;
        }
        DataGroupDesignDTO dto = new DataGroupDesignDTO();
        DataGroupDesignDTO.copyProperties((DataGroup)dataGroup, dto);
        return dto;
    }
}

