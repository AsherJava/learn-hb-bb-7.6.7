/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.mapping2.provider.INrMappingType
 *  com.jiuqi.nr.mapping2.provider.NrMappingResource
 *  com.jiuqi.nr.mapping2.provider.impl.MappingTypeConst
 *  com.jiuqi.nvwa.mapping.bean.MappingScheme
 */
package com.jiuqi.nr.migration.transferdata.jqrmapping;

import com.jiuqi.nr.mapping2.provider.INrMappingType;
import com.jiuqi.nr.mapping2.provider.NrMappingResource;
import com.jiuqi.nr.mapping2.provider.impl.MappingTypeConst;
import com.jiuqi.nr.migration.transferdata.jqrmapping.JQRResourceMapping2DO;
import com.jiuqi.nr.migration.transferdata.jqrmapping.JQRResourceMapping2ServiceImpl;
import com.jiuqi.nvwa.mapping.bean.MappingScheme;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class JQRMappingType
implements INrMappingType {
    @Autowired
    private JQRResourceMapping2ServiceImpl impl;
    public static final String TYPE_JQR = "JQR";

    public String getTypeCode() {
        return TYPE_JQR;
    }

    public String getTypeTitle() {
        return "JQR\u6620\u5c04";
    }

    public double getOrder() {
        return 40.0;
    }

    public ArrayList<NrMappingResource> getResources(MappingScheme scheme) {
        ArrayList<NrMappingResource> res = new ArrayList<NrMappingResource>();
        res.add(MappingTypeConst.JQR_CONFIG);
        return res;
    }

    public void deleteMapping(MappingScheme scheme) {
        this.impl.deleteMappingByMSKey(scheme.getKey());
    }

    public void copyMapping(MappingScheme sourceScheme, MappingScheme targetScheme) {
        if (sourceScheme == null || targetScheme == null || !StringUtils.hasLength(sourceScheme.getKey()) || !StringUtils.hasLength(targetScheme.getKey())) {
            return;
        }
        String targetSchemeKey = targetScheme.getKey();
        List<JQRResourceMapping2DO> byMSJqrCustom = this.impl.findByMSJqrCustom(sourceScheme.getKey());
        List<JQRResourceMapping2DO> targetSchemeObj = byMSJqrCustom.stream().peek(mapping -> mapping.setMappingSchemeKey(targetSchemeKey)).collect(Collectors.toList());
        this.impl.batchInsertOrUpdateJqrCustomMappings(targetSchemeObj, "insert");
    }
}

