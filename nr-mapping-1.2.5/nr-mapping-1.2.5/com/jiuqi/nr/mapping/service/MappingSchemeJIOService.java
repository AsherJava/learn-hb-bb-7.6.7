/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping.service;

import com.jiuqi.nr.mapping.bean.JIOConfig;
import com.jiuqi.nr.mapping.dto.MappingSchemeWrapDTO;
import java.util.List;

public interface MappingSchemeJIOService {
    public void buildMapping(MappingSchemeWrapDTO var1);

    public JIOConfig getJIOConfig(String var1);

    public void deleteJIOConfig(String var1);

    public void deleteJIOConfigs(List<String> var1);

    public void saveJIOConfig(JIOConfig var1);

    public void updateJIOConfig(String var1, JIOConfig var2);
}

