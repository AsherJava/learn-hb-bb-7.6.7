/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.mapping.bean.MappingScheme
 */
package com.jiuqi.nr.mapping2.provider.impl;

import com.jiuqi.nr.mapping2.provider.INrMappingType;
import com.jiuqi.nr.mapping2.provider.NrMappingResource;
import com.jiuqi.nvwa.mapping.bean.MappingScheme;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

@Service
public class MappingTypeMidstore
implements INrMappingType {
    public static final String TYPE_MIDSTORE = "MIDSTORE";

    @Override
    public String getTypeCode() {
        return TYPE_MIDSTORE;
    }

    @Override
    public String getTypeTitle() {
        return "\u4e2d\u95f4\u5e93\u6620\u5c04";
    }

    @Override
    public double getOrder() {
        return 30.0;
    }

    @Override
    public ArrayList<NrMappingResource> getResources(MappingScheme scheme) {
        return new ArrayList<NrMappingResource>();
    }
}

