/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.mapping.bean.MappingScheme
 *  com.jiuqi.nvwa.mapping.provider.PluginInfo
 *  org.json.JSONObject
 */
package com.jiuqi.nr.mapping2.provider;

import com.jiuqi.nr.mapping2.provider.NrMappingResource;
import com.jiuqi.nr.mapping2.provider.TypeOption;
import com.jiuqi.nvwa.mapping.bean.MappingScheme;
import com.jiuqi.nvwa.mapping.provider.PluginInfo;
import java.util.ArrayList;
import org.json.JSONObject;

public interface INrMappingType {
    public String getTypeCode();

    public String getTypeTitle();

    public double getOrder();

    public ArrayList<NrMappingResource> getResources(MappingScheme var1);

    default public ArrayList<PluginInfo> getExtendButtons(MappingScheme scheme) {
        return new ArrayList<PluginInfo>();
    }

    default public String getOrgMappingTip(MappingScheme scheme) {
        return "";
    }

    default public boolean showOrgParentMapping(MappingScheme scheme) {
        return false;
    }

    default public TypeOption getTypeOption(MappingScheme scheme) {
        return new TypeOption(false, true, true, true, true, true);
    }

    default public void deleteMapping(MappingScheme scheme) {
    }

    default public void modifyMapping(MappingScheme scheme, MappingScheme oldScheme) {
    }

    default public void copyMapping(MappingScheme sourceScheme, MappingScheme targetScheme) {
    }

    default public void exportResource(MappingScheme scheme, JSONObject parent) {
    }

    default public void importResource(MappingScheme scheme, JSONObject parent) {
    }
}

