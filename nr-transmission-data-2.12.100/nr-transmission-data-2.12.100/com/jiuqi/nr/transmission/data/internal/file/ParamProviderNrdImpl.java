/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.fielddatacrud.RegionPO
 *  com.jiuqi.nr.fielddatacrud.spi.ParamProvider
 */
package com.jiuqi.nr.transmission.data.internal.file;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.fielddatacrud.RegionPO;
import com.jiuqi.nr.fielddatacrud.spi.ParamProvider;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class ParamProviderNrdImpl
implements ParamProvider {
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    private IRunTimeViewController runTimeViewController;
    private String taskKey;
    private String formSchemeKey;

    public ParamProviderNrdImpl() {
    }

    public ParamProviderNrdImpl(IRuntimeDataSchemeService runtimeDataSchemeService, IRunTimeViewController runTimeViewController, String taskKey, String formSchemeKey) {
        this.runtimeDataSchemeService = runtimeDataSchemeService;
        this.runTimeViewController = runTimeViewController;
        this.taskKey = taskKey;
        this.formSchemeKey = formSchemeKey;
    }

    public Set<RegionPO> getRegions(String dataTableCode, List<String> dataFieldCodes) {
        List formDefines1 = this.runTimeViewController.queryAllFormDefinesByFormScheme(this.formSchemeKey);
        Set formKeySetForFormSchemeKey = formDefines1.stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet());
        List dataFieldByTableCode = this.runtimeDataSchemeService.getDataFieldByTableCode(dataTableCode);
        HashSet<String> dataFieldCodeSets = new HashSet<String>(dataFieldCodes);
        HashMap result = new HashMap();
        for (DataField dataField : dataFieldByTableCode) {
            Collection formKeysByField;
            if (!dataFieldCodeSets.contains(dataField.getCode()) || CollectionUtils.isEmpty(formKeysByField = this.runTimeViewController.getFormKeysByField(dataField.getKey()))) continue;
            formKeysByField.forEach(a -> {
                if (!result.containsKey(a) && formKeySetForFormSchemeKey.contains(a)) {
                    RegionPO regionPO = new RegionPO();
                    regionPO.setFormKey(a);
                    regionPO.setFormSchemeKey(this.formSchemeKey);
                    regionPO.setTaskKey(this.taskKey);
                    result.put(a, regionPO);
                }
            });
        }
        if (!CollectionUtils.isEmpty(result)) {
            Set<RegionPO> collect = result.values().stream().map(a -> a).collect(Collectors.toSet());
            return collect;
        }
        return null;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }
}

