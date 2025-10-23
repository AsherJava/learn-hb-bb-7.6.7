/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping2.provider;

import com.jiuqi.nr.mapping2.provider.INrMappingType;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class NrMappingCollector {
    private Map<String, INrMappingType> typeMap = new HashMap<String, INrMappingType>();
    private List<INrMappingType> typeList = new ArrayList<INrMappingType>();

    public NrMappingCollector(@Autowired(required=false) List<INrMappingType> iTypeList) {
        if (!CollectionUtils.isEmpty(iTypeList)) {
            for (INrMappingType type : iTypeList) {
                this.typeMap.put(type.getTypeCode(), type);
            }
            this.typeList = iTypeList.stream().sorted(Comparator.comparingDouble(INrMappingType::getOrder)).collect(Collectors.toList());
        }
    }

    public Map<String, INrMappingType> getTypeMap() {
        return this.typeMap;
    }

    public void setTypeMap(Map<String, INrMappingType> typeMap) {
        this.typeMap = typeMap;
    }

    public List<INrMappingType> getTypeList() {
        return this.typeList;
    }

    public void setTypeList(List<INrMappingType> typeList) {
        this.typeList = typeList;
    }
}

