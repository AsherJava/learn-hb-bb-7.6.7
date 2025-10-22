/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.datascheme.api.DataTableRel
 *  com.jiuqi.nr.datascheme.api.DesignDataTableRel
 */
package com.jiuqi.nr.datascheme.internal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.datascheme.api.DataTableRel;
import com.jiuqi.nr.datascheme.api.DesignDataTableRel;
import com.jiuqi.nr.datascheme.internal.dto.DataTableRelDTO;

@JsonIgnoreProperties(ignoreUnknown=true)
public class DataTableRelDesignDTO
extends DataTableRelDTO
implements DesignDataTableRel {
    private static final long serialVersionUID = 5719641451199718035L;

    public static DataTableRelDesignDTO valueOf(DataTableRel o) {
        if (null == o) {
            return null;
        }
        DataTableRelDesignDTO t = new DataTableRelDesignDTO();
        DataTableRelDesignDTO.copyProperties(o, t);
        return t;
    }
}

