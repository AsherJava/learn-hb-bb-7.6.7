/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.org.impl.callback.autofiled;

import com.jiuqi.gcreport.org.impl.callback.autofiled.domain.GcParentsUpateDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.util.CollectionUtils;

public class OrgAutoCalcFieldContainer {
    private static final Map<String, List<GcParentsUpateDTO>> GCPARENTS_OPERATE_CACHE = new ConcurrentHashMap<String, List<GcParentsUpateDTO>>(500);

    public static void clear(String tableName) {
        GCPARENTS_OPERATE_CACHE.remove(tableName.toUpperCase());
    }

    public static List<GcParentsUpateDTO> getGcParentDataListByTableName(String tableName) {
        List<GcParentsUpateDTO> gcParentsUpateDTOS = GCPARENTS_OPERATE_CACHE.get(tableName.toUpperCase());
        if (CollectionUtils.isEmpty(gcParentsUpateDTOS)) {
            return new ArrayList<GcParentsUpateDTO>();
        }
        return gcParentsUpateDTOS;
    }

    public static void addGcParents(GcParentsUpateDTO dto) {
        String tableName = dto.getTableName().toUpperCase();
        List<GcParentsUpateDTO> gcParentsUpateDTOS = GCPARENTS_OPERATE_CACHE.get(tableName);
        if (CollectionUtils.isEmpty(gcParentsUpateDTOS)) {
            ArrayList<GcParentsUpateDTO> list = new ArrayList<GcParentsUpateDTO>();
            list.add(dto);
            GCPARENTS_OPERATE_CACHE.put(tableName, list);
        } else {
            gcParentsUpateDTOS.add(dto);
        }
    }
}

