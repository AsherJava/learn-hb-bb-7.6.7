/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.AdjustPeriod
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.entity.service.IEntityDefineAssist
 */
package com.jiuqi.nr.definition.service.impl;

import com.jiuqi.nr.datascheme.api.AdjustPeriod;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.nr.entity.service.IEntityDefineAssist;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class FormSchemeService
implements IFormSchemeService {
    @Autowired
    private IAdjustPeriodService adjustPeriodService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityDefineAssist entityDefineAssist;

    private String getDataSchemeKey(String formSchemeKey) {
        TaskDefine taskDefine;
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (formScheme != null && (taskDefine = this.runTimeViewController.getTask(formScheme.getTaskKey())) != null) {
            return taskDefine.getDataScheme();
        }
        return null;
    }

    @Override
    public boolean enableAdjustPeriod(String formSchemeKey) {
        String dataSchemeKey = this.getDataSchemeKey(formSchemeKey);
        if (Objects.isNull(dataSchemeKey)) {
            return Boolean.FALSE;
        }
        return this.runtimeDataSchemeService.enableAdjustPeriod(dataSchemeKey);
    }

    @Override
    public boolean isTaskEnableAdjustPeriod(String taskKey) {
        TaskDefine taskDefine = this.runTimeViewController.getTask(taskKey);
        if (taskDefine != null) {
            return this.runtimeDataSchemeService.enableAdjustPeriod(taskDefine.getDataScheme());
        }
        return false;
    }

    @Override
    @NonNull
    public <E extends AdjustPeriod> List<E> queryAdjustPeriods(String formSchemeKey) {
        String dataSchemeKey = this.getDataSchemeKey(formSchemeKey);
        if (dataSchemeKey == null) {
            return Collections.emptyList();
        }
        return this.castList(this.adjustPeriodService.queryAdjustPeriods(dataSchemeKey));
    }

    @Override
    @NonNull
    public <E extends AdjustPeriod> List<E> queryAdjustPeriods(String formSchemeKey, String period) {
        String dataSchemeKey = this.getDataSchemeKey(formSchemeKey);
        if (dataSchemeKey == null) {
            return Collections.emptyList();
        }
        return this.castList(this.adjustPeriodService.queryAdjustPeriods(dataSchemeKey, period));
    }

    @Override
    @Nullable
    public <E extends AdjustPeriod> E queryAdjustPeriods(String formSchemeKey, String period, String code) {
        String dataSchemeKey = this.getDataSchemeKey(formSchemeKey);
        if (dataSchemeKey == null) {
            return null;
        }
        return (E)this.adjustPeriodService.queryAdjustPeriods(dataSchemeKey, period, code);
    }

    @Override
    @NonNull
    public List<String> getReportDimensionKey(String formSchemeKey) {
        String dataSchemeKey = this.getDataSchemeKey(formSchemeKey);
        if (dataSchemeKey == null) {
            return Collections.emptyList();
        }
        List dimensions = this.runtimeDataSchemeService.getReportDimension(dataSchemeKey);
        return dimensions.stream().map(DataDimension::getDimKey).collect(Collectors.toList());
    }

    @Override
    @NonNull
    public List<String> getReportEntityKeys(String formSchemeKey) {
        List<String> list = this.getReportDimensionKey(formSchemeKey);
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (formScheme == null) {
            return list;
        }
        list.add(formScheme.getDw());
        list.add(formScheme.getDateTime());
        return list;
    }

    @Override
    @NonNull
    public Boolean isReportDimension(String formSchemeKey, String dimKey) {
        String dataSchemeKey = this.getDataSchemeKey(formSchemeKey);
        if (dataSchemeKey == null) {
            return Boolean.FALSE;
        }
        List dimensions = this.runtimeDataSchemeService.getReportDimension(dataSchemeKey);
        return dimensions.stream().anyMatch(dataDimension -> dimKey.equals(dataDimension.getDimKey()));
    }

    @Override
    @Nullable
    public String getDimAttributeByReportDim(String formSchemeKey, String dimKey) {
        String dataSchemeKey = this.getDataSchemeKey(formSchemeKey);
        if (dataSchemeKey == null) {
            return null;
        }
        List dimensions = this.runtimeDataSchemeService.getDataSchemeDimension(dataSchemeKey, DimensionType.DIMENSION);
        DataDimension report = dimensions.stream().filter(dataDimension -> dimKey.equals(dataDimension.getDimKey())).findFirst().orElse(null);
        return report == null ? null : report.getDimAttribute();
    }

    @Override
    public Boolean existCurrencyAttributes(String formSchemeKey) {
        String dw;
        FormSchemeDefine schemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (Objects.nonNull(schemeDefine) && StringUtils.hasLength(dw = schemeDefine.getDw())) {
            return this.entityDefineAssist.existCurrencyAttributes(dw);
        }
        return Boolean.FALSE;
    }

    @Override
    @NonNull
    public Boolean existCurrencyDim(String formSchemeKey) {
        String dims;
        FormSchemeDefine schemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (Objects.nonNull(schemeDefine) && StringUtils.hasLength(dims = schemeDefine.getDims())) {
            return dims.contains("MD_CURRENCY@BASE");
        }
        return Boolean.FALSE;
    }
}

