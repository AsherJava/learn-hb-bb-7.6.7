/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.internal.dto.DataSchemeDTO
 */
package com.jiuqi.nr.task.form.dto;

import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.internal.dto.DataSchemeDTO;

public class DataSchemeDefineDTO
extends DataSchemeDTO {
    public static DataSchemeDefineDTO valueOf(DataScheme o) {
        if (o == null) {
            return null;
        }
        DataSchemeDefineDTO dto = new DataSchemeDefineDTO();
        DataSchemeDefineDTO.copyProperties((DataScheme)o, (DataSchemeDTO)dto);
        return dto;
    }
}

