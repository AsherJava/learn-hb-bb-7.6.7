/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping.util;

import com.jiuqi.nr.mapping.bean.UnitMapping;
import com.jiuqi.nr.mapping.web.dto.UnitMappingDTO;

public class ConvertUtils {
    public static UnitMapping DTO2UnitMapping(UnitMappingDTO mappingDTO) {
        UnitMapping um = new UnitMapping();
        um.setUnitCode(mappingDTO.getUnitCode());
        um.setMapping(mappingDTO.getMapping());
        return um;
    }
}

