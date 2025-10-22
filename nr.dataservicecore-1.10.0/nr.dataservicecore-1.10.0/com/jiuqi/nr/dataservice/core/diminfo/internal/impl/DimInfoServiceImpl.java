/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.dataservice.core.diminfo.internal.impl;

import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.dataservice.core.diminfo.facade.service.IDimInfoService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class DimInfoServiceImpl
implements IDimInfoService {
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IRunTimeViewController runTimeViewController;

    @Override
    public List<String> queryPriDimByDataScheme(String dataSchemeKey) {
        return this.getPriDimKeys(dataSchemeKey);
    }

    @Override
    public List<String> queryPriDimByTask(String taskKey) {
        return this.getPriDimKeysByTask(taskKey);
    }

    @Override
    public List<String> queryPriDimByFormScheme(String formSchemeKey) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (formScheme != null) {
            return this.getPriDimKeysByTask(formScheme.getTaskKey());
        }
        return Collections.emptyList();
    }

    private List<String> getPriDimKeys(String dataSchemeKey) {
        ArrayList<String> result = new ArrayList<String>();
        List dataSchemeDimension = this.runtimeDataSchemeService.getDataSchemeDimension(dataSchemeKey);
        if (!CollectionUtils.isEmpty(dataSchemeDimension)) {
            List<DataDimension> unitScope = dataSchemeDimension.stream().filter(o -> o != null && DimensionType.UNIT_SCOPE == o.getDimensionType()).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(unitScope)) {
                List<DataDimension> unit = dataSchemeDimension.stream().filter(o -> o != null && DimensionType.UNIT == o.getDimensionType()).collect(Collectors.toList());
                unit.forEach(o -> result.add(o.getDimKey()));
            } else {
                unitScope.forEach(o -> result.add(o.getDimKey()));
            }
        }
        return result;
    }

    private List<String> getPriDimKeysByTask(String taskKey) {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        if (taskDefine != null) {
            return this.getPriDimKeys(taskDefine.getDataScheme());
        }
        return Collections.emptyList();
    }
}

