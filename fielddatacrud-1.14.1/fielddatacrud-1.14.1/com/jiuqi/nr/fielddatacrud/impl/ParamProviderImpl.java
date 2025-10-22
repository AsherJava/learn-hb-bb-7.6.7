/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.fielddatacrud.impl;

import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.fielddatacrud.RegionPO;
import com.jiuqi.nr.fielddatacrud.spi.ParamProvider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class ParamProviderImpl
implements ParamProvider {
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IRunTimeViewController runTimeViewController;

    @Override
    public Set<RegionPO> getRegions(String dataTableCode, List<String> dataFieldCodes) {
        List fields = this.runtimeDataSchemeService.getDataFieldByTableCode(dataTableCode);
        HashSet formKeys = new HashSet();
        for (DataField field : fields) {
            Collection forms = this.runTimeViewController.getFormKeysByField(field.getKey());
            if (CollectionUtils.isEmpty(forms)) continue;
            formKeys.addAll(forms);
        }
        List formDefines = this.runTimeViewController.queryFormsById(new ArrayList(formKeys));
        HashMap<String, FormSchemeDefine> formSchemeDefineMap = new HashMap<String, FormSchemeDefine>();
        HashSet<RegionPO> regions = new HashSet<RegionPO>();
        for (FormDefine formDefine : formDefines) {
            RegionPO regionPO = new RegionPO();
            regionPO.setFormKey(formDefine.getKey());
            regionPO.setFormSchemeKey(formDefine.getFormScheme());
            FormSchemeDefine formScheme = (FormSchemeDefine)formSchemeDefineMap.get(formDefine.getFormScheme());
            if (formScheme == null) {
                formScheme = this.runTimeViewController.getFormScheme(formDefine.getFormScheme());
                if (formScheme == null) continue;
                formSchemeDefineMap.put(formDefine.getFormScheme(), formScheme);
            }
            String taskKey = formScheme.getTaskKey();
            regionPO.setTaskKey(taskKey);
            regions.add(regionPO);
        }
        return regions;
    }
}

