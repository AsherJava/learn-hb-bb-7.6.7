/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.fielddatacrud.spi.pp;

import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.fielddatacrud.RegionPO;
import com.jiuqi.nr.fielddatacrud.spi.ParamProvider;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FormParamProvider
implements ParamProvider {
    private final String taskKey;
    private final FormDefine formDefine;

    public FormParamProvider(String task, FormDefine formDefine) {
        this.taskKey = task;
        this.formDefine = formDefine;
    }

    @Override
    public Set<RegionPO> getRegions(String dataTableCode, List<String> dataFieldCodes) {
        HashSet<RegionPO> regions = new HashSet<RegionPO>();
        RegionPO regionPO = new RegionPO();
        regionPO.setFormKey(this.formDefine.getKey());
        regionPO.setFormSchemeKey(this.formDefine.getFormScheme());
        regionPO.setTaskKey(this.taskKey);
        regions.add(regionPO);
        return regions;
    }
}

