/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.mapping.bean.MappingScheme
 *  com.jiuqi.nvwa.mapping.provider.PluginInfo
 */
package com.jiuqi.nr.mapping2.provider.impl;

import com.jiuqi.nr.mapping2.dao.FormMappingDao;
import com.jiuqi.nr.mapping2.provider.INrMappingType;
import com.jiuqi.nr.mapping2.provider.NrMappingResource;
import com.jiuqi.nr.mapping2.provider.TypeOption;
import com.jiuqi.nvwa.mapping.bean.MappingScheme;
import com.jiuqi.nvwa.mapping.provider.PluginInfo;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MappingTypeNRDX
implements INrMappingType {
    public static final String TYPE_NRDX = "NRDX";
    @Autowired
    private FormMappingDao formMappingDao;

    @Override
    public String getTypeCode() {
        return TYPE_NRDX;
    }

    @Override
    public String getTypeTitle() {
        return "NRDX\u6620\u5c04";
    }

    @Override
    public double getOrder() {
        return 20.0;
    }

    @Override
    public ArrayList<NrMappingResource> getResources(MappingScheme scheme) {
        ArrayList<NrMappingResource> res = new ArrayList<NrMappingResource>();
        return res;
    }

    @Override
    public ArrayList<PluginInfo> getExtendButtons(MappingScheme scheme) {
        ArrayList<PluginInfo> res = new ArrayList<PluginInfo>();
        return res;
    }

    @Override
    public TypeOption getTypeOption(MappingScheme scheme) {
        return new TypeOption(false, false, true, false, false, true);
    }

    @Override
    public void deleteMapping(MappingScheme scheme) {
    }

    @Override
    public void modifyMapping(MappingScheme scheme, MappingScheme oldScheme) {
    }

    @Override
    public void copyMapping(MappingScheme sourceScheme, MappingScheme targetScheme) {
    }
}

